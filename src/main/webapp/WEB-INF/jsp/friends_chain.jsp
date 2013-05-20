<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />


  <form action="user/viewChain" class="form-inline">
    <label class="control-label" for="startId">startId : </label> <input type="text" name="startId" value="${user.id}" id="startId" placeholder="请输入 startId …"/>    
    <label class="control-label" for="id">endId : </label> <input type="text" name="endId" value="${targetUser.id}" id="endId" placeholder="请输入 endId …"/>
    <button type="submit" class="btn">Submit</button>
  </form>
  
  <h2>计算 ${user.name}和${targetUser.name}  之间的人脉链</h2>
  
  通过${length}个人认识
  
  <c:forEach var="friend" items="${chain}">
    ${friend.name}  →  
  </c:forEach>

<jsp:include page="footer.jsp" />