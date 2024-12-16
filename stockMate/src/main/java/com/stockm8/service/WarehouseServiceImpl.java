package com.stockm8.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockm8.domain.dto.WarehouseDetailDTO;
import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.persistence.WarehouseDAO;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Override
	public void registerWarehouse(WarehouseVO warehouse) throws Exception {
		
		logger.info("createWarehouse(WarehouseVO warehouse) 호출");
		
		logger.info("Service -> DAO 메서드 호출");
		warehouseDAO.insertWarehouse(warehouse);
	}

	@Override
	public List<WarehouseVO> getWarehousesByBusinessId(Integer businessId) throws Exception {

		logger.info("getWarehousesByBusinessId(Long businessId) 호출");
		
		
		logger.info("Service -> DAO 메서드 호출");
		return warehouseDAO.selectWarehousesByBusinessId(businessId);
	}
	
    @Override
    public WarehouseDetailDTO getWarehouseDetail(int warehouseId, int businessId) throws Exception {
    	
    	WarehouseDetailDTO warehouseDetail = warehouseDAO.selectWarehouseDetailById(warehouseId);
        if (warehouseDetail == null) {
            throw new IllegalArgumentException("창고를 찾을 수 없습니다.");
        }
        
    	// 비즈니스 ID가 일치하지 않는 경우
        if (warehouseDetail.getBusinessId() != businessId) {
            throw new SecurityException("잘못된 접근입니다. 권한이 없습니다.");
        }

        return warehouseDetail;
    }
}