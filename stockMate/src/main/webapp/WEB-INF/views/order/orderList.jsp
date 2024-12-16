<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 목록</title>
<style>
   table { width: 100%; border-collapse: collapse; }
   th, td { border: 1px solid #ddd; padding: 8px; text-align: center; }
   th { background-color: #f2f2f2; }
</style>
</head>
<body>
   <h2>주문 목록</h2>
   
   <table>
       <tr>
           <th>주문번호</th>
           <th>발주일자</th>
           <th>총액</th>
           <th>주문유형</th>
        <!--    <th>상태</th> -->
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
                       <c:when test="${not empty order.createdAt}">${order.createdAt}</c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td>
               <td>
                   <c:choose>
                       <c:when test="${not empty order.totalPrice}">${order.totalPrice}</c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td>
               <td>
                   <c:choose>
                       <c:when test="${not empty order.orderType}">${order.orderType}</c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td>
<%--                <td>
                   <c:choose>
                       <c:when test="${not empty order.status}">${order.status}</c:when>
                       <c:otherwise>값이 없습니다</c:otherwise>
                   </c:choose>
               </td> --%>
               <td>
                   <a href="/order/detail?orderId=${order.orderId}">상세</a>
               </td>
           </tr>
       </c:forEach>
   </table>
</body>
</html>
