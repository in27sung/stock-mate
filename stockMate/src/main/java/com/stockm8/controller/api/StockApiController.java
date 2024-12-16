package com.stockm8.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stockm8.domain.vo.ProductVO;
import com.stockm8.domain.vo.UserVO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.service.ProductService;
import com.stockm8.service.UserService;
import com.stockm8.service.WarehouseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/stock")
public class StockApiController {

    private static final Logger logger = LoggerFactory.getLogger(StockApiController.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private WarehouseService warehouseService;

    /**
     * /api/stock/products 
     * - JSON 형태로 상품 리스트 반환
     */
    @GetMapping("/products")
    public ResponseEntity<List<ProductVO>> getProductList(@SessionAttribute("userId") Long userId) throws Exception {
        logger.info("getProductList() 호출 (API)");

        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();

        List<ProductVO> productList = productService.getProductsByBusinessId(businessId);

        return ResponseEntity.ok(productList);
    }

    /**
     * /api/stock/warehouses
     * - JSON 형태로 창고 리스트 반환
     */
    @GetMapping("/warehouses")
    public ResponseEntity<List<WarehouseVO>> getWarehouseList(@SessionAttribute("userId") Long userId) throws Exception {
        logger.info("getWarehouseList() 호출 (API)");

        UserVO user = userService.getUserById(userId);
        int businessId = user.getBusinessId();
        
        List<WarehouseVO> warehouseList = warehouseService.getWarehousesByBusinessId(businessId);
        return ResponseEntity.ok(warehouseList);
    }
}
