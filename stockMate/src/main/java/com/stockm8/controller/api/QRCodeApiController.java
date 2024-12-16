package com.stockm8.controller.api;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.stockm8.domain.dto.QRCodeDTO;
import com.stockm8.domain.dto.ScanProductDTO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.QRCodeVO;
import com.stockm8.exceptions.QRCodeGenerationException;
import com.stockm8.exceptions.QRCodeNotFoundException;
import com.stockm8.service.ProductService;
import com.stockm8.service.QRCodeService;

@RestController
@RequestMapping("/api/qrcode")
public class QRCodeApiController {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeApiController.class);

    @Autowired
    private QRCodeService qrCodeService;
    
    @Autowired
    private ProductService productService;

    /**
     * 비동기 방식으로 QR 코드 생성 요청 처리
     * 
     * @param productId 생성할 상품 ID
     * @return 즉시 처리 시작 메시지 반환
     * @throws Exception 
     * @throws UnsupportedEncodingException 
     */
    @PostMapping("/generate")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> generateQRCode(@RequestBody QRCodeDTO qrCodeDTO) throws Exception {
        Map<String, Object> response = new HashMap<>();
        try {
            qrCodeService.generateQRCode(qrCodeDTO.getProductId());
            response.put("success", true);
            response.put("message", "QR 코드가 성공적으로 생성되었습니다!");
            return ResponseEntity.ok(response); // 200 OK
        } catch (QRCodeNotFoundException e) {
            response.put("success", false);
            response.put("message", "QR 코드 생성 실패: 제품 ID를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // 400 Bad Request
        } catch (QRCodeGenerationException e) {
            response.put("success", false);
            response.put("message", "QR 코드 생성 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // 500 Internal Server Error
        }
    }


    /**
     * JSON QR 코드 다운로드 처리
     * JSON 형식의 QR 코드만 다운로드하도록 수정
     *
     * @param productId 상품 ID
     * @return QR 코드 이미지 파일 (JSON QR 코드만)
     */
    @PostMapping("/download")
    public ResponseEntity<Resource> downloadQRCode(@RequestBody QRCodeDTO qrCodeDTO) throws Exception {
        
	    // 상품 정보 가져오기
	    ProductVO product = productService.getProductByID(qrCodeDTO.getProductId());

        // QR 코드 정보 가져오기
        QRCodeVO qrCode = qrCodeService.getQRCodeByProductId(qrCodeDTO.getProductId());

        // QR 코드 파일 확인
        File qrCodeFile = new File(qrCode.getQrCodePath());
        if (!qrCodeFile.exists()) {
            throw new IllegalArgumentException("QR 코드 파일이 존재하지 않습니다.");
        }

        // QR 코드 파일 이름 생성 (서버에서 사용하는 동일한 형식 적용)
        String safeProductName = product.getProductName().replaceAll("[\\\\/:*?\"<>|]", "_");
        String qrCodeFileName = qrCode.getProductId() + "_scan_" + safeProductName + ".png";

        // 파일명 인코딩 (다운로드 파일 이름에 반영)
        String encodedFileName = URLEncoder.encode(qrCodeFileName, "UTF-8").replace("+", "%20");
        Resource resource = new FileSystemResource(qrCodeFile);
        
        // Content-Disposition 헤더 설정
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFileName) // UTF-8 호환
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(qrCodeFile.length())
                .body(resource);
    }
    

    // QR 코드 스캔 후 DB 조회
    @PostMapping("/scan")
    public Map<String, Object> scanProduct(@RequestBody Map<String, String> qrData) {
        Map<String, Object> response = new HashMap<>();
        Gson gson = new Gson();

        try {
            // Step 1: 원본 데이터 확인 (디버깅 로그 추가)
            logger.info("Received QR Data: " + qrData);

            // Step 2: productId 문자열이 JSON 객체인 경우 파싱
            String rawProductId = qrData.get("productId");
            logger.info("Raw productId: " + rawProductId);

            // JSON 객체를 ScanProductDTO로 변환
            ScanProductDTO scanProduct = gson.fromJson(rawProductId, ScanProductDTO.class);
            int productId = scanProduct.getProductId();
            logger.info("Parsed productId: " + productId);

            // Step 3: DB에서 상품 정보 조회
            ProductVO product = productService.getProductByID(productId);
            if (product != null) {
            	logger.info("Product Found: " + product);
                response.put("success", true);
                response.put("productName", product.getProductName());
                response.put("productPrice", product.getProductPrice());
                response.put("productUnit", product.getBaseUnit());
                response.put("productBarcode", product.getProductBarcode());
            } else {
                logger.warn("Product Not Found with ID: " + productId);
                response.put("success", false);
                response.put("message", "해당 상품을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
        	logger.error("Error Occurred: " + e.getMessage());
            response.put("success", false);
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
        }

        return response;
    }

}