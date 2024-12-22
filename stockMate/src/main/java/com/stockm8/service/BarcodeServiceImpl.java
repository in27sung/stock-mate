package com.stockm8.service;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.stockm8.domain.dto.BarcodeDTO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.persistence.CategoryDAO;
import com.stockm8.persistence.ProductDAO;

@Service
public class BarcodeServiceImpl implements BarcodeService {
	
    private static final String BARCODE_BASE_PATH = "/Users/Insung/Documents/upload/barcodes";

    @Autowired
    private ProductDAO productDAO;

	@Autowired
	private CategoryDAO categoryDAO;
	
    /**
     * 상품 ID에 해당하는 바코드를 생성하고 저장 경로를 데이터베이스에 기록
     *
     * @param productId 생성할 상품 ID
     * @throws Exception 예외 발생 시 처리
     */
	public String generateBarcode(String productBarcode) throws Exception {
	    // 바코드로 상품 정보 조회
	    ProductVO product = productDAO.getProductByBarcode(productBarcode);
	    
	    if (product == null) {
	        throw new IllegalArgumentException("존재하지 않는 바코드: " + productBarcode);
	    }

	    // EAN-13 형식 유효성 검사
	    if (productBarcode == null || productBarcode.length() != 13) {
	        throw new IllegalArgumentException("유효하지 않은 바코드 형식: " + productBarcode);
	    }

	    // 바코드 저장 경로 생성
	    String filePath = createBarcodePath(product);

	    // 바코드 이미지 생성 및 저장
	    generateBarcodeImage(productBarcode, filePath);

	    // 바코드 정보 업데이트
	    BarcodeDTO barcode = new BarcodeDTO();
	    barcode.setProductId(product.getProductId());
	    barcode.setStockQrCodePath(filePath);
	    barcode.setStockQrCodeData(productBarcode);  // 바코드 데이터 저장

	    productDAO.updateBarcodePathByProductId(barcode);  // DB 업데이트

	    return filePath;
	}

    /**
     * 바코드 저장 경로 생성 (상품명 및 카테고리명 기반)
     *
     * @param product 상품 정보
     * @return 바코드 이미지 파일 경로
     * @throws Exception 
     */
    private String createBarcodePath(ProductVO product) throws Exception {
        String categoryName = categoryDAO.selectCategoryNameById(product.getCategoryId());
        String safeProductName = product.getProductName().replaceAll("[\\\\/:*?\"<>|]", "_");
        String safeCategoryName = categoryName.replaceAll("[\\\\/:*?\"<>|]", "_");

        // 저장 경로 설정
        int businessId = product.getBusinessId();
        String directoryPath = BARCODE_BASE_PATH + File.separator +
                businessId + File.separator +
                safeCategoryName;

        // 파일명 설정
        String barcodeFileName = product.getProductId() + "_barcode_" + safeProductName + ".png";
        String barcodePath = directoryPath + File.separator + barcodeFileName;

        // 디렉토리 생성
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("바코드 디렉토리를 생성할 수 없습니다: " + directoryPath);
        }

        return barcodePath;
    }

    /**
     * 바코드 이미지를 생성하여 저장
     *
     * @param barcodeData 바코드 데이터 (13자리)
     * @param barcodePath 저장 경로
     */
    private void generateBarcodeImage(String barcodeData, String barcodePath) throws Exception {
        int width = 300;  
        int height = 150;  // 바코드 자체 높이
        int fontSize = 24;  // 바코드 숫자 폰트 크기
        int padding = 60;   // 바코드 아래 여백 (텍스트 공간)

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        // 바코드 생성
        BitMatrix bitMatrix = new MultiFormatWriter().encode(
                barcodeData,
                BarcodeFormat.EAN_13,
                width,
                height,
                hints
        );

        // 바코드 이미지 렌더링
        BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // 바코드와 텍스트를 결합한 최종 이미지 생성
        BufferedImage combinedImage = new BufferedImage(width, height + padding, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = combinedImage.createGraphics();

        // 배경 흰색 설정
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height + padding);

        // 바코드 이미지 그리기
        g2d.drawImage(barcodeImage, 0, 0, null);

        // 텍스트 설정 (바코드 아래 번호)
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, fontSize));

        // 텍스트 위치 계산 (가운데 정렬)
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(barcodeData);
        int x = (width - textWidth) / 2;  // 가로 중앙 정렬
        int y = height + (padding / 2) + (fm.getAscent() / 2) - 5;  // 세로 위치 조정

        // 바코드 번호 추가
        g2d.drawString(barcodeData, x, y);
        g2d.dispose();

        // 이미지 저장
        Path path = Paths.get(barcodePath);
        ImageIO.write(combinedImage, "PNG", path.toFile());
    }

}