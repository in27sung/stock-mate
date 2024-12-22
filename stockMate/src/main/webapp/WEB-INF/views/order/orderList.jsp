<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- [신규] 금액, 날짜 포맷팅을 위한 fmt 태그 추가 -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>주문 목록</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/OrderStyle.css' />">
</head>
<body>
   <h2>주문 목록</h2>
   <a class="btn btn-success" href="/order/register" style="text-decoration: none;">주문</a>
   <table>
       <tr>
           <th>주문번호</th> 
           <th>오더생성 일자</th>
           <th>총액</th>
           <th>주문유형</th>
           <th>상세보기</th>
       </tr>
       <c:forEach var="order" items="${orderList}">
           <tr>
               <td>
                   <c:choose>
                       <c:when test="${not empty order.orderNumber}">${order.orderNumber}</c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td>
               <td>
                   <c:choose>
                       <c:when test="${not empty order.createdAt}">
                           <!-- 날짜 포맷팅 - yyyy-MM-dd HH:mm:ss 형식으로 통일 -->
                           <fmt:formatDate value="${order.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/>
                       </c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td>
               <td>
                   <c:choose>
                       <c:when test="${not empty order.totalPrice}">
                           <!-- 금액 포맷팅 - 천단위 구분자와 원화 표시 추가 pattern="#,###" 하면... 되는데.. 문제가... -->
                           <fmt:formatNumber value="${order.totalPrice}"  />원  
                       </c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td>
               <td>
                   <c:choose>
                       <c:when test="${order.orderType =='INBOUND'}">발주</c:when>
                       <c:when test="${order.orderType =='OUTBOUND'}">수주</c:when>
                     <%--   <c:otherwise>값이 없습니다</c:otherwise> --%>
                   </c:choose>
               </td>
               <td>
                   <a href="/order/orderDetail?orderId=${order.orderId}">상세</a>
               </td>
           </tr>
       </c:forEach>
   </table>
   <!-- 페이징 처리 UI -->
   <div class="pagination">
       <!-- 이전 페이지 -->
       <c:if test="${pageVO.prev}">
           <a href="/order/orderList?page=${pageVO.startPage-1}&pageSize=${pageVO.cri.pageSize}">&laquo; 이전</a>
       </c:if>
       
       <!-- 페이지 번호 -->
       <c:forEach begin="${pageVO.startPage}" end="${pageVO.endPage}" var="idx">
           <a href="/order/orderList?page=${idx}&pageSize=${pageVO.cri.pageSize}" 
              ${pageVO.cri.page == idx ? 'class="active"' : ''}>
               ${idx}
           </a>
       </c:forEach>
       
       <!-- 다음 페이지 -->
       <c:if test="${pageVO.next}">
           <a href="/order/orderList?page=${pageVO.endPage+1}&pageSize=${pageVO.cri.pageSize}">다음 &raquo;</a>
       </c:if>
   </div>

   <!-- 페이지당 표시할 항목 수 선택 -->
   <div class="page-size-selector">
       <select onchange="changePageSize(this.value)">
           <option value="10" ${pageVO.cri.pageSize == 10 ? 'selected' : ''}>10개씩 보기</option>
           <option value="20" ${pageVO.cri.pageSize == 20 ? 'selected' : ''}>20개씩 보기</option>
           <option value="30" ${pageVO.cri.pageSize == 30 ? 'selected' : ''}>30개씩 보기</option>
       </select>
   </div>

   <script>
   function changePageSize(size) {
       location.href = '/order/orderList?page=1&pageSize=' + size;
   }
   </script>
</body>
</html>