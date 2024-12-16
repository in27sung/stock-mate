package com.stockm8.service;

import java.util.List;

import com.stockm8.domain.dto.WarehouseDetailDTO;
import com.stockm8.domain.vo.WarehouseVO;

public interface WarehouseService {
	
	// 창고 등록 
	public void registerWarehouse(WarehouseVO warehouse) throws Exception;
	
	// 회사 ID를 통한 창고ID 정보 
	List<WarehouseVO> getWarehousesByBusinessId(Integer businessId) throws Exception;
	
	// 창고 ID를 통한 창고 정보 
    public WarehouseDetailDTO getWarehouseDetail(int warehouseId, int businessId) throws Exception;

}
