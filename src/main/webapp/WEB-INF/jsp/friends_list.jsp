<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp" />


  <form action="user/listFriends" class="form-inline">
    <label class="control-label" for="id">Id : </label> <input type="text" name="id" value="${user.id}" id="id" placeholder="请输入 id …"/>
    <button type="submit" class="btn">Submit</button>
  </form>
  
  <h2>遍历 ${user.name} 好友关系</h2>
  
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
    <c:forEach var="friend" items="${friendsList}">
      <tr>
        <td><a href="user/listFriends?id=${friend.id}">${friend.name}</a></td>
        <td>${friend.age}</td>
        <td>${friend.gender}</td>
        <td>${friend.profession}</td>
        <td><a  href="user/viewChain?startId=${user.id}&endId=${friend.id}">${friend.depth}</a></td>
      </tr>
    </c:forEach>
  </table>

<jsp:include page="footer.jsp" />