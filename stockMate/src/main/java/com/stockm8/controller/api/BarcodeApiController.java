package com.stockm8.controller.api;
import java.io.File;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stockm8.service.BarcodeService;

@RestController
@RequestMapping("/api/barcode")
public class BarcodeApiController {

    @Autowired
    private BarcodeService barcodeService;

    /**
     * 상품 ID로 바코드 생성 요청을 처리하는 엔드포인트
     *
     * @param request JSON으로 상품 ID 전달
     * @return 바코드 생성 결과 및 파일 경로
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> generateBarcode(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String productBarcode = request.get("productBarcode");

            if (productBarcode == null || !productBarcode.matches("^880\\d{10}$")) {
                throw new IllegalArgumentException("유효하지 않은 바코드입니다. (880으로 시작하는 13자리)");
            }

            // 바코드 생성 및 저장
            String barcodePath = barcodeService.generateBarcode(productBarcode);  // productBarcode로 바코드 생성
            String fileName = new File(barcodePath).getName();

            response.put("success", true);
            response.put("message", "바코드가 성공적으로 생성되었습니다.");
            response.put("barcodePath", barcodePath.replace("/Users/Insung/Documents/upload", "/upload"));
            response.put("barcodeFileName", fileName);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "바코드 생성 실패: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
        return ResponseEntity.ok(response);
    }
    /**
     * 바코드 다운로드 엔드포인트
     *
     * @param request 바코드 경로를 담은 JSON 요청
     * @return 바코드 이미지 파일 다운로드
     */
    @PostMapping("/download")
    public ResponseEntity<Resource> downloadBarcode(@RequestBody Map<String, String> request) throws Exception {
        String barcodePath = request.get("barcodePath");

        if (barcodePath == null || barcodePath.isEmpty()) {
            throw new IllegalArgumentException("유효하지 않은 바코드 경로입니다.");
        }

        // Define the base directory for barcode files
        File baseDir = new File("/path/to/barcode/files").getCanonicalFile();
        File barcodeFile = new File(baseDir, barcodePath).getCanonicalFile();

        // Ensure the barcode file is within the base directory
        if (!barcodeFile.getPath().startsWith(baseDir.getPath())) {
            throw new IllegalArgumentException("유효하지 않은 바코드 경로입니다.");
        }

        if (!barcodeFile.exists()) {
            throw new IllegalArgumentException("바코드 파일이 존재하지 않습니다.");
        }

        // 바코드 파일 다운로드
        Resource resource = new FileSystemResource(barcodeFile);
        String fileName = URLEncoder.encode(barcodeFile.getName(), "UTF-8").replace("+", "%20");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(barcodeFile.length())
                .body(resource);
    }
}