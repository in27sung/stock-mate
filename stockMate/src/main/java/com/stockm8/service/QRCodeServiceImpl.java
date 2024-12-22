package com.stockm8.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.stockm8.domain.dto.QRCodeDTO;
import com.stockm8.domain.dto.StockQRCodeDTO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.persistence.CategoryDAO;
import com.stockm8.persistence.ProductDAO;
import com.stockm8.persistence.QRCodeDAO;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private QRCodeDAO qrCodeDAO;
    
	@Autowired
	private CategoryDAO categoryDAO;

    /**
     * 상품 ID에 해당하는 JSON 형식의 QR 코드를 생성하고, 저장된 경로를 데이터베이스에 기록
     *
     * @param productId 생성할 상품 ID
     * @throws Exception 예외 발생 시 처리
     */
//    @Async
    @Override
    public String generateQRCode(int productId) throws Exception {
        logger.info("QR 코드 생성 요청 (동기): Product ID = {}", productId);

        // 상품 정보 조회
        ProductVO product = productDAO.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 ID: " + productId);
        }

        // JSON 형식의 QR 코드 데이터 생성
        String qrCodeData = createJSONContent(product);

        // QR 코드 저장 경로 생성
        String filePath = createQRCodePath(product);

        // QR 코드 이미지 생성 및 저장
        generateQRCodeImage(qrCodeData, filePath);

        // QRCodeVO 객체 생성 및 데이터베이스 저장
        StockQRCodeDTO stockBarcode = new StockQRCodeDTO();
        stockBarcode.setProductId(productId);
        stockBarcode.setStockQrCodeData(qrCodeData);
        stockBarcode.setStockQrCodePath(filePath);
        qrCodeDAO.updateQRCodePathByProductId(stockBarcode);

        logger.info("QR 코드 생성 및 저장 완료. 파일 경로: {}", filePath);
        
        return filePath;
    }

    /**
     * 상품 정보를 기반으로 JSON 형식의 QR 코드 데이터를 생성
     *
     * @param product 상품 정보
     * @return JSON 형식의 QR 코드 데이터
     */
    private String createJSONContent(ProductVO product) {
        return String.format(
            "{\"productId\":%d,\"productBarcode\":\"%s\",\"businessId\":%d,\"baseUnit\":\"%s\",\"setSize\":%d}",
            product.getProductId(),
            product.getProductBarcode(),
            product.getBusinessId(),
            product.getBaseUnit(),
            product.getSetSize()
        );
    }

    /**
     * QR 코드 저장 경로 생성 (상품의 카테고리 및 이름 기반)
     *
     * @param product 상품 정보
     * @return QR 코드 파일의 절대 경로
     * @throws Exception 
     */
    private String createQRCodePath(ProductVO product) throws Exception {
        
        // 카테고리명을 ID를 참조하여 가져오기
        String categoryName = categoryDAO.selectCategoryNameById(product.getCategoryId());

        // 상품명, 카테고리명에서 파일 이름에 사용할 수 없는 문자를 제거
        String safeProductName = product.getProductName().replaceAll("[\\\\/:*?\"<>|]", "_");
        String safeCategoryName = categoryName.replaceAll("[\\\\/:*?\"<>|]", "_");

        // QR 코드 저장 경로 생성
        int businessId = product.getBusinessId(); // 상품의 비즈니스 ID
        int categoryId = product.getCategoryId(); // 상품의 카테고리 ID
        String basePath = "/Users/Insung/Documents/upload"; // QR 코드 기본 저장 경로
//      String basePath = "/usr/local/tomcat/webapps/upload";

        // 디렉토리 경로 생성
        String directoryPath = basePath + File.separator 
                + "qrcodes" + File.separator 
                + "scans" + File.separator 
                + businessId + File.separator 
                + safeCategoryName;  

        // QR 코드 파일 이름: productId_scan_상품명.png
        String qrCodeFileName = product.getProductId() + "_scan_" + safeProductName + ".png";
        String qrCodePath = directoryPath + File.separator + qrCodeFileName;
        String relativeQrCodePath = "/qrcodes/scans" 
                                    + businessId + "/"
                                    + categoryId + "/" + safeCategoryName + "/"
                                    + qrCodeFileName;

        // 디렉토리 생성
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("QR 코드 디렉토리를 생성할 수 없습니다: " + directoryPath);
        }

        return qrCodePath;
    }

    /**
     * QR 코드 이미지를 생성하여 지정된 경로에 저장
     *
     * @param qrContent QR 코드 내용
     * @param qrCodePath 저장할 파일 경로
     * @throws WriterException QR 코드 생성 시 오류
     * @throws IOException 파일 저장 시 오류
     */
    private void generateQRCodeImage(String qrContent, String qrCodePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
        	    qrContent, // UTF-8 인코딩된 데이터 그대로 사용
        	    BarcodeFormat.QR_CODE,
        	    200, // QR 코드 크기
        	    200
        		);
        Path path = Paths.get(qrCodePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path); // PNG 형식으로 저장
    }

    /**
     * 상품 ID에 해당하는 QR 코드 정보 조회
     *
     * @param productId 상품 ID
     * @return QR 코드 정보
     * @throws Exception QR 코드 조회 실패 시 예외
     */
    @Override
    public StockQRCodeDTO getQRCodeByBarcode(String productBarcode) throws Exception {
        return qrCodeDAO.selectQRCodeByBarcode(productBarcode);
    }

	@Override
	public StockQRCodeDTO getQRCodeByProductId(Integer productId) throws Exception {
        return qrCodeDAO.selectQRCodeByProductId(productId);
	}
    
    
}