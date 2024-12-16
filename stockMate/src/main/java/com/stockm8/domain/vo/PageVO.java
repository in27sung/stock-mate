package com.stockm8.domain.vo;

/**
 * 페이지 블럭을 처리하기위한 정보를 저장하는 객체
 * 
 * 총 글의 개수, 페이지 블럭의 크기
 * 블럭 시작번호, 블럭 끝번호, 이전버튼, 다음버튼
 * 
 *
 */

public class PageVO {
	
	private int totalCount; // 총 글의 개수
	private int pageBlock = 10; // 페이지 블럭의 크기
	private int startPage; // 블럭 시작 번호
	private int endPage; // 블럭 끝 번호
	private boolean next; // 다음버튼
	private boolean prev; // 이전버튼
	
	private Criteria cri;
	
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	
	}
	
	public void calcData() {
	
		// 페이징 블럭 끝 번호
		endPage = (int)(Math.ceil(cri.getPage() / (double)pageBlock)) * pageBlock;
		// 페이징 블럭 시작 번호
		startPage = (endPage - pageBlock) + 1;
		// 임시 끝 번호
		int tmpEndPage = (int)Math.ceil(totalCount / (double)cri.getPageSize());
		if(endPage > tmpEndPage) {
			endPage = tmpEndPage;
		}
		// 다음버튼 이전버튼
		prev = startPage != 1;
		next = endPage * cri.getPageSize() < totalCount;
		
	}
	///////////////////////////////////////////////////////////////////
	public int getTotalCount() {
		return totalCount;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public Criteria getCri() {
		return cri;
	}



	@Override
	public String toString() {
		return "PageVO [totalCount=" + totalCount + ", pageBlock=" + pageBlock + ", startPage=" + startPage
				+ ", endPage=" + endPage + ", next=" + next + ", prev=" + prev + ", cri=" + cri + "]";
	}
	
	
	
	
	
}