package com.stockm8.controller.api;

import java.io.File;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.stockm8.domain.dto.ScanProductDTO;
import com.stockm8.domain.dto.StockQRCodeDTO;
import com.stockm8.domain.vo.ProductVO;
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
     * QR 코드 생성 및 업데이트 (stocks 테이블에 데이터 저장)
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateQRCode(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer productId = (Integer) request.get("productId");
            if (productId == null || productId <= 0) {
                throw new IllegalArgumentException("유효하지 않은 productId입니다.");
            }

            // QR 코드 생성 및 파일 경로 반환
            String qrCodePath = qrCodeService.generateQRCode(productId);

            // 상품 정보 조회
            ProductVO product = productService.getProductByID(productId);
            if (product == null) {
                throw new IllegalArgumentException("해당 productId의 상품을 찾을 수 없습니다.");
            }

            // 파일명 추출
            String qrCodeFileName = new File(qrCodePath).getName();

            // 클라이언트에 응답 반환
            response.put("success", true);
            response.put("message", "QR 코드가 성공적으로 생성되었습니다.");
            response.put("qrCodePath", qrCodePath.replace("/Users/Insung/Documents/upload", "/upload"));
            response.put("qrCodeFileName", qrCodeFileName);  // 파일명 직접 반환

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", "QR 코드 생성 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "QR 코드 생성 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * QR 코드 다운로드 (바코드 기반 조회)
     */
    @PostMapping("/download")
    public ResponseEntity<Resource> downloadQRCode(@RequestBody Map<String, String> request) throws Exception {
        String productBarcode = request.get("productBarcode");
        Integer productId = request.containsKey("productId") ? Integer.parseInt(request.get("productId")) : null;

        if (productBarcode == null && productId == null) {
            throw new IllegalArgumentException("바코드 또는 productId가 필요합니다.");
        }

        // QR 코드 경로 조회
        StockQRCodeDTO stockBarcode = (productBarcode != null) ? 
                qrCodeService.getQRCodeByBarcode(productBarcode) : 
                qrCodeService.getQRCodeByProductId(productId);

        if (stockBarcode == null || stockBarcode.getStockQrCodePath() == null) {
            throw new IllegalArgumentException("QR 코드 파일을 찾을 수 없습니다.");
        }

        // 파일 경로
        File qrCodeFile = new File(stockBarcode.getStockQrCodePath());
        if (!qrCodeFile.exists()) {
            throw new IllegalArgumentException("QR 코드 파일이 존재하지 않습니다.");
        }

        // 파일 리소스
        Resource resource = new FileSystemResource(qrCodeFile);
        String fileName = qrCodeFile.getName();
        String encodedFileName = fileName.replace(" ", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(qrCodeFile.length())
                .body(resource);
    }
    
    // 다운로드 상태 확인 API
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getQRCodeStatus(@RequestParam("productId") int productId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 재고 테이블에서 QR 코드 정보 조회
            StockQRCodeDTO stockBarcode = qrCodeService.getQRCodeByProductId(productId);
            if (stockBarcode != null && stockBarcode.getStockQrCodePath() != null) {
                response.put("success", true);
                response.put("qrCodePath", stockBarcode.getStockQrCodePath().replace("/Users/Insung/Documents/upload", "/upload"));
                response.put("productBarcode", stockBarcode.getProductBarcode());
            } else {
                response.put("success", false);
                response.put("message", "QR 코드 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "QR 코드 상태 조회 실패: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }
    

    /**
     * QR 코드 스캔 후 바코드 기반 상품 조회
     */
    @PostMapping("/scan")
    public Map<String, Object> scanProduct(@RequestBody Map<String, String> qrData) {
        Map<String, Object> response = new HashMap<>();
        Gson gson = new Gson();

        try {
            // QR 데이터 수신
            logger.info("Received QR Data: " + qrData);

            String rawQrData = qrData.get("qrCodeData");
            logger.info("Raw QR Data: " + rawQrData);

            // JSON 데이터 파싱
            ScanProductDTO scanProduct = gson.fromJson(rawQrData, ScanProductDTO.class);
            String productBarcode = scanProduct.getProductBarcode();
            logger.info("Parsed Barcode: " + productBarcode);

            // 바코드로 상품 조회 (stocks 테이블과 연결)
            ProductVO product = productService.getProductByBarcode(productBarcode);

            if (product != null) {
                logger.info("Product Found: " + product);
                response.put("success", true);
                response.put("productName", product.getProductName());
                response.put("productPrice", product.getProductPrice());
                response.put("productUnit", product.getBaseUnit());
                response.put("productBarcode", product.getProductBarcode());
            } else {
                logger.warn("Product Not Found with Barcode: " + productBarcode);
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