package com.ele.project.util;

public class Pagination {
	private String url; 		//<a> 的href

	private int currentPage = 1; 	//当前页

	private int rowsCount = 0; 		//最大行数

	private int rowsPerPage = 5; 	//每页行数
	
	private int totalPage = 0;		//总页数
	
	private String pagination;
	
	public void init(){
		if(rowsPerPage != 0){
			totalPage = rowsCount / rowsPerPage 
			+ (rowsCount % rowsPerPage > 0 ? 1 : 0); 
			
		}else{
			totalPage = 0;
		}
	}

	public void setPagination(String pagination){
		this.pagination = pagination;
	}
	public String getPagination() {
		init();
		if(totalPage < 1)
			return "";
		
	    StringBuffer str=new StringBuffer();
	    str.append(getFirstPage(currentPage, totalPage));
	    str.append(getPreviousPage(currentPage, totalPage));
	    str.append(getCurrPage(currentPage, totalPage));
	    str.append(getNextPage(currentPage, totalPage));
	    str.append(getLastPage(currentPage, totalPage));
//		System.out.println(str.toString());
		pagination = str.toString();
		return pagination;
	}
	/**
	 * 获取首页
	 * @param currPage
	 * @param totalPage   href='#' onClick='parent.mainFrame.location.href=
	 * @return
	 */
	private String getFirstPage(int currPage, int totalPage){
		/**
		 * 如果当前是第一页或者总共就只有一页，则不显示首页
		 */
		if(currPage==1 || totalPage<=1){
			return "<li class='prev disabled' style=\"font-size:18px;\"><a href='#'><i class='icon-double-angle-left'></i></a></li>";
		}else{
			return "<li class='prev' style=\"font-size:18px;\"><a href='" + url + "&pn=1'><i class='icon-double-angle-left'></i></a></li>";
		}		
	}
	/**
	 * 获取上一页
	 * @param currPage
	 * @param totalPage
	 * @return
	 */
	private String getPreviousPage(int currPage, int totalPage){
		//如果当前页小于等于第一页或者总页数为1,则不出现
		if(currPage<=1 || totalPage==1){
			return "";
		}
		return "<li><a href='" + url + "&pn=" + (currPage - 1)+ "'>"+(currPage - 1)+"</a></li>";
		
	}
	/**
	 * 获取当前页
	 * @param currPage
	 * @param totalPage
	 * @return
	 */
	private String getCurrPage(int currPage, int totalPage){
		//如果当前页小于等于第一页或者总页数为1,则不出现
		
//		return "<li class='active'><a href='" + url + "&pn=" + currPage+ ">"+currPage+"</a></li>";
		return "<li class='active'><a href='#'>"+currPage+"</a></li>";
	}
	/**
	 * 获取下一页
	 * @param currPage
	 * @param totalPage
	 * @return
	 */
	private String getNextPage(int currPage, int totalPage){
		//如果当前页大于等于总页数，则不显示下一页
		if(currPage>=totalPage){
			return "";
		}		
		return "<li><a href='" + url + "&pn=" + (currPage + 1) + "'>"+(currPage+ 1)+"</a></li>";
		
		
	}
	/**
	 * 获取尾页
	 * @param currPage
	 * @param totalPage
	 * @return
	 */
	private String getLastPage(int currPage, int totalPage){
		//如果总共只有一页或者当前页已经大于等于总页数，则不显示尾页
		if(totalPage==1 || currPage>=totalPage){
			return "<li class='next disabled' style=\"font-size:18px;\"><a href='#'><i class='icon-double-angle-right'></i></a></li>";
		}else{
			return "<li class='next' style=\"font-size:18px;\"><a href='" + url + "&pn=" + totalPage	+ "'><i class='icon-double-angle-right'></i></a></li>";
		}
	}
	private String getPage(){
		StringBuffer sb = new StringBuffer();
		
		int from = 0;
		int end = 0;
		
		if(currentPage <= 11){
			from = 1;
			end = currentPage + 9;
		}else{
			from = currentPage - 10;
			end = currentPage + 9;
		}
			
		for(int i = from ; i <= (end <= totalPage ? end : totalPage) ; i++){
			String numStr;
			if( i != currentPage){
				numStr = "[" + i + "]";
			}else{
				numStr = i+"";
			}
			//sb.append("<a href=" + URLRewriteManager.getPaginationUrl(url, i)+ ">"+ numStr +"</a>&nbsp;");
			sb.append("<a href=" + url+"&pn="+ i+ ">"+ numStr +"</a>&nbsp;");
		}
		return sb.toString();
	}
	
	
	
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPageNo) {
		this.currentPage = currentPageNo;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int maxPageNo) {
		this.rowsCount = maxPageNo;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTotalPage() {
		init() ;
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public static void main(String [] args){
		Pagination p = new Pagination();
		p.setUrl("/cw/channel/apply/");
		p.setRowsCount(30);
		p.setRowsPerPage(5);
		p.setCurrentPage(1);
	}

}
