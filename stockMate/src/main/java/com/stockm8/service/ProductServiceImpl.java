package com.stockm8.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.stockm8.domain.dto.QRCodeDTO;
import com.stockm8.domain.vo.ProductVO;
import com.stockm8.persistence.CategoryDAO;
import com.stockm8.persistence.ProductDAO;
import com.stockm8.persistence.QRCodeDAO;

/**
 * ProductService 인터페이스를 구현한 클래스.
 * DAO를 호출하여 실제 비즈니스 로직을 수행한다.
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private QRCodeDAO qrCodeDAO;

	@Override
	public void registerProduct(ProductVO product) throws Exception {
	    logger.info("registerProduct() 호출");
	    
	    if (product == null || product.getProductName() == null) {
	        throw new IllegalArgumentException("상품 정보가 유효하지 않습니다.");
	    }

	    // DAO를 통해 상품 등록
	    productDAO.insertProduct(product);
	    logger.info("상품 등록 완료: {}", product);
	}
	
    @Override
    public void generateQRCode(int productId) throws Exception {
    	// 상품 정보 조회
        ProductVO product = productDAO.getProductById(productId);
        
        if (product != null) {
            String qrContent = "http://c7d2408t1p1.itwillbs.com/product/detail?productId=" + productId;
            
            // 카테고리명을 ID를 참조하여 가져오기
            String categoryName = categoryDAO.selectCategoryNameById(product.getCategoryId());
            
            // 상품명, 카테고리명에서 파일 이름에 사용할 수 없는 문자를 제거
            String safeProductName = product.getProductName().replaceAll("[\\\\/:*?\"<>|]", "_");
            String safeCategoryName = categoryName.replaceAll("[\\\\/:*?\"<>|]", "_");

            // QR 코드 저장 경로 생성
            int businessId = product.getBusinessId(); // 상품의 비즈니스 ID
            int categoryId = product.getCategoryId(); // 상품의 카테고리 ID
            String basePath = "/Users/Insung/Documents/upload"; // QR 코드 기본 저장 경로
//          String basePath = "/usr/local/tomcat/webapps/upload";

            // 디렉토리 경로 생성
            String directoryPath = basePath + File.separator 
                    + "qrcodes" + File.separator 
                    + "details" + File.separator 
                    + businessId + File.separator 
                    + safeCategoryName;   
            
            // QR 코드 파일 이름: productId_detail_상품명.png
            String qrCodeFileName = productId + "_detail_" + safeProductName + ".png";
            String qrCodePath = directoryPath + File.separator + qrCodeFileName;
            String relativeQrCodePath = "/qrcodes/details" 
                                        + businessId + "/"
                                        + categoryId + "/" + safeCategoryName + "/"
                                        + qrCodeFileName;
            
            // 디렉토리 생성
            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.mkdirs()) {
                throw new IOException("QR 코드 디렉토리를 생성할 수 없습니다: " + directoryPath);
            }

            try {
                // QR 코드 생성
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);
                Path path = Paths.get(qrCodePath);
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
                logger.info("QR 코드가 생성된 경로: {}", path.toString());

                // DB에 경로 저장
                product.setProductQrCodePath(qrCodePath);
                productDAO.updateQRCodePath(product);

                logger.info("QR 코드 생성 성공. 경로: {}", qrCodePath);
            } catch (WriterException | IOException e) {
                logger.error("QR 코드 생성 실패: {}", e.getMessage());
                throw new RuntimeException("QR 코드 생성 중 오류 발생.", e);
            }
        } else {
            throw new IllegalArgumentException("상품 정보가 없습니다. 상품 ID: " + productId);
        }
    }

	@Override
	public ProductVO getProductByID(int productId) throws Exception {
	    logger.info("getProductByID(int productId) 호출");
		return productDAO.getProductById(productId);
	}
	
    @Override
    public List<ProductVO> getProductsWithQRCode(int businessId) throws Exception{
        return productDAO.selectProductsWithQRCode(businessId);
    }

    @Override
    public Map<Integer, String> getQRCodePathsByBusinessId(int businessId) throws Exception {
        List<QRCodeDTO> qrCodes = qrCodeDAO.selectQRCodePathsByBusinessId(businessId);
        return qrCodes.stream()
                .collect(Collectors.toMap(QRCodeDTO::getProductId, QRCodeDTO::getQrCodePath));
    }

	@Override
	public List<ProductVO> getProductsByBusinessId(int businessId) throws Exception {
		return productDAO.selectProductsByBusinessId(businessId);
	}
    
    @Override
    public ProductVO getProductByBarcode(String productBarcode) throws Exception {
        ProductVO product = productDAO.getProductByBarcode(productBarcode);
        if (product == null) {
            throw new Exception("해당 바코드의 상품을 찾을 수 없습니다.");
        }
        return product;
    }
}
