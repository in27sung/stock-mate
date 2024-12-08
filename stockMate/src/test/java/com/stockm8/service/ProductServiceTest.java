package com.stockm8.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.stockm8.domain.vo.ProductVO;
import com.stockm8.persistence.ProductDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@Transactional
public class ProductServiceTest {

	@Autowired
	private ProductService productService; // 서비스 계층

	@Autowired
	private ProductDAO productDAO; // 상품 DAO

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Before
	public void setup() {
		// 기존 데이터를 유지하며 추가적인 데이터 정리 작업을 생략
		System.out.println("기존 데이터 기반으로 테스트를 진행합니다.");
	}

	@After
	public void teardown() {
		// 테스트 중 생성된 데이터만 삭제하도록 수정
		System.out.println("테스트 종료 후 생성된 데이터 정리 중...");
		jdbcTemplate.update("DELETE FROM test_products WHERE name = ?", "테스트 상품"); // 테스트에서 생성된 상품만 삭제
		System.out.println("테스트 데이터 정리 완료.");
	}

	@Test //테스트 완료
	public void testRegisterProductWithExistingData() throws Exception {
		// Given
		Integer businessId = jdbcTemplate
				.queryForObject("SELECT business_id FROM test_businesses WHERE business_number = '001'", Integer.class);
		Integer warehouseId = jdbcTemplate.queryForObject(
				"SELECT warehouse_id FROM test_warehouses WHERE warehouse_name = '창고A' AND business_id = ?",
				Integer.class, businessId);
		Integer categoryId = jdbcTemplate.queryForObject(
				"SELECT category_id FROM test_categories WHERE category_name = '전자제품' AND business_id = ?",
				Integer.class, businessId);

		ProductVO productVO = new ProductVO();
		productVO.setName("테스트 상품");
		productVO.setBarcode("123456789");
		productVO.setBusinessId(businessId);
		productVO.setCategoryId(categoryId);
		productVO.setBaseUnit("개");
		productVO.setSetSize(10);
		productVO.setPrice(new BigDecimal("19999.00"));
		productVO.setDescription("테스트 상품 설명");

		// When
		productService.registerProduct(productVO);

		// Then
		ProductVO savedProduct = productDAO.getProductById(productVO.getProductId());
		assertNotNull("등록된 상품은 null이 아니어야 합니다.", savedProduct);
		assertEquals("상품명이 일치해야 합니다.", "테스트 상품", savedProduct.getName());
	}

//	@Test
//	(expected = IllegalArgumentException.class)
//	public void testRegisterProductInvalidWarehouse() throws Exception {
//		// Given: 유효하지 않은 창고 ID
//		int invalidWarehouseId = -1; // 유효하지 않은 창고 ID
//		int businessId = 1; // 매니저가 속한 회사 ID
//		int categoryId = 1; // 등록된 카테고리 ID
//
//		// 상품 등록 데이터 생성
//		ProductVO productVO = new ProductVO();
//		productVO.setName("MacBook Air");
//		productVO.setBarcode("987654321");
//		productVO.setBusinessId(businessId);
//		productVO.setWarehouseId(invalidWarehouseId);
//		productVO.setCategoryId(categoryId);
//		productVO.setBaseUnit("개");
//		productVO.setSetSize(1);
//		productVO.setPrice(new BigDecimal("1500000.00"));
//		productVO.setDescription("Apple MacBook Air 13-inch");
//
//		// When: 상품 등록
//		productService.registerProduct(productVO);
//
//		// Then: 예외 발생 확인
//	}
}