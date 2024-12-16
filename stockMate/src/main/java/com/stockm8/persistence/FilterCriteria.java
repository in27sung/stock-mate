package com.stockm8.persistence;

public class FilterCriteria {
    private Integer warehouseId;
    private String categoryName;
    private Integer minStock;
    private Integer maxStock;
    private String sortOrder;  // 추가된 필드
    private Integer businessId;  // 추가된 필드

    // 생성자
    public FilterCriteria(Integer warehouseId, String categoryName, Integer minStock, Integer maxStock, String sortOrder, Integer businessId) {
        this.warehouseId = warehouseId;
        this.categoryName = categoryName;
        this.minStock = minStock;
        this.maxStock = maxStock;
        this.sortOrder = sortOrder;  // 생성자에서 초기화
        this.businessId = businessId;  // 생성자에서 초기화
    }

    // Getter, Setter
    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getMinStock() {
        return minStock;
    }

    public void setMinStock(Integer minStock) {
        this.minStock = minStock;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public void setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getBusinessId() {  // 추가된 getter
        return businessId;
    }

    public void setBusinessId(Integer businessId) {  // 추가된 setter
        this.businessId = businessId;
    }
}