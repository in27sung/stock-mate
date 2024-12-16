package com.stockm8.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.stockm8.domain.vo.ProductVO;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
@WebAppConfiguration
public class QRCodeGenerationTest {

    @Autowired
    private ProductService productService;

	private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerationTest.class);
	
    @Test
    public void testGenerateQRCode() throws Exception {
        // 준비 단계: 테스트용 상품 ID
        int testProductId = 17;
        
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/root-context.xml");
        logger.info("ApplicationContext Loaded: {}", context.getDisplayName());

        // QR 코드 생성 실행
        productService.generateQRCode(testProductId);

        // 상품 정보 확인
        ProductVO product = productService.getProductByID(testProductId);
        Assert.assertNotNull("상품 정보가 존재해야 합니다.", product);

        String qrCodePath = product.getProductQrCodePath();
        Assert.assertNotNull("QR 코드 경로가 설정되어야 합니다.", qrCodePath);

        // QR 코드 파일 존재 여부 확인
        Path qrCodeFilePath = Path.of(qrCodePath);
        Assert.assertTrue("QR 코드 파일이 생성되어야 합니다.", Files.exists(qrCodeFilePath));

        // 테스트 종료 후 QR 코드 파일 삭제
        File qrCodeFile = qrCodeFilePath.toFile();
        boolean deleted = qrCodeFile.delete();
        Assert.assertTrue("테스트 종료 후 QR 코드 파일을 삭제해야 합니다.", deleted);
    }
}