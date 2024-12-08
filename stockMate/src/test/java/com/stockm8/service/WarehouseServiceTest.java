//package com.stockm8.service;
//
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.stockm8.domain.vo.WarehouseVO;
//import com.stockm8.persistence.WarehouseDAO;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring/root-context.xml", "classpath:spring/data-context.xml" })
//@Transactional
//public class WarehouseServiceTest {
//
//    @Autowired
//    private WarehouseService warehouseService;
//
//    @Autowired
//    private WarehouseDAO warehouseDAO;
//
//    @Test
//    public void testRegisterWarehouseSuccess() {
//        // Given: 테스트 데이터 준비
//        WarehouseVO warehouseVO = new WarehouseVO();
//        warehouseVO.setWarehouseName("서울 창고");
//        warehouseVO.setLocation("서울특별시");
//        warehouseVO.setBusinessId(1);
//        warehouseVO.setManagerId(1);
//
//        // When: 창고 등록
//        warehouseService.registerWarehouse(warehouseVO);
//
//        // Then: 데이터베이스에서 확인
//        List<WarehouseVO> warehouses = warehouseDAO.getWarehousesByBusinessId(1);
//        assertFalse("창고 목록이 비어 있지 않아야 합니다.", warehouses.isEmpty());
//        assertEquals("등록된 창고명이 일치해야 합니다.", "서울 창고", warehouses.get(0).getWarehouseName());
//    }
//
//    @Test
//    public void testRegisterWarehouseDuplicateName() {
//        // Given: 중복된 창고명 데이터
//        WarehouseVO warehouseVO1 = new WarehouseVO();
//        warehouseVO1.setWarehouseName("서울 창고");
//        warehouseVO1.setLocation("서울특별시");
//        warehouseVO1.setBusinessId(1);
//        warehouseVO1.setManagerId(1);
//
//        WarehouseVO warehouseVO2 = new WarehouseVO();
//        warehouseVO2.setWarehouseName("서울 창고"); // 중복 이름
//        warehouseVO2.setLocation("부산광역시");
//        warehouseVO2.setBusinessId(1);
//        warehouseVO2.setManagerId(1);
//
//        // When: 첫 번째 창고 등록
//        warehouseService.registerWarehouse(warehouseVO1);
//
//        // Then: 두 번째 창고 등록 시 예외 발생
//        try {
//            warehouseService.registerWarehouse(warehouseVO2);
//            fail("중복된 창고명으로 등록하면 예외가 발생해야 합니다.");
//        } catch (DataIntegrityViolationException e) {
//            // Expected behavior
//        }
//    }
//}