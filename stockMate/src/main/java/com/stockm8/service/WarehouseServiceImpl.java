package com.stockm8.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockm8.domain.vo.WarehouseVO;
import com.stockm8.persistence.WarehouseDAO;

@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	private static final Logger logger = LoggerFactory.getLogger(WarehouseServiceImpl.class);

	@Autowired
	private WarehouseDAO warehouseDAO;
	
	@Override
	public void createWarehouse(WarehouseVO warehouse) throws Exception {
		
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
	
	

}