package com.stockm8.domain.vo;

/**
 * 페이징 처리에 필요한 정보를 저장하는 객체
 *
 */


public class Criteria {
	
	private int page; // 페이지 정보
	private int pageSize; // 페이지 크기 정보
	
	public Criteria() {
		this.page = 1;
		this.pageSize = 20;
	}

	
	public int getPage() {
		
		return page;
	}
	public int getPageSize() {
		
		return pageSize;
	}
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return ;
		}
//		this.page = (page - 1) * 10;
		this.page = page;
	}
	public void setPageSize(int pageSize) {
		if(pageSize <= 0 || pageSize > 100) {
			this.pageSize = 20;
			return ;
		}
		this.pageSize = pageSize;
	}
	
	// 페이지 번호를 계산하는 메서드
	public int getStartPage() {
		
		return (page - 1) * pageSize;
	}


	@Override
	public String toString() {
		return "Criteria [page=" + page + ", pageSize=" + pageSize + "]";
	}
	
	
	

} // Criteria end
