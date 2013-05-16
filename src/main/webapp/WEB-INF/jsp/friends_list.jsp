<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />

 <ul class="breadcrumb">
    <li class="active">遍历好友关系</li>
  </ul>
  <form action="user/listFriends">
    <input type="text" name="id"/>
    <input type="submit"/>
  </form>
  
  <table class="table table-striped">
    <thead>
    <tr>
        <th>姓名</th>
        <th>年龄</th>
        <th>性别</th>
        <th>职业</th>
        <th>深度</th>
      </tr>
    </thead>
    <c:forEach var="friend" items="${obj}">
      <tr>
        <td><a>${friend.name}</a></td>
        <td><a>${friend.age}</a></td>
        <td><a>${friend.gender}</a></td>
        <td><a>${friend.profession}</a></td>
        <td><a>${friend.depth}</a></td>
      </tr>
    </c:forEach>
  </table>

<jsp:include page="footer.jsp" />