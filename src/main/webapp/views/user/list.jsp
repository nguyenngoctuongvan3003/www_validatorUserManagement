<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

    <div align="center">
    <h1>User Management</h1>
        <h2>
         <a href="${pageContext.request.contextPath}/users?action=new">Add New User</a>
         &nbsp;&nbsp;&nbsp;
         <a href="${pageContext.request.contextPath}/users">List All Users</a>
        </h2>
        <h2>List Users</h2>
        <table border="1">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
            <c:forEach var="user" items="${listUser}">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.country}" /></td>
                    <td>
                     <a href="${pageContext.request.contextPath}/users?action=edit&id=<c:out value='${user.id}' />">Edit</a>
                     <a href="${pageContext.request.contextPath}/users?action=delete&id=<c:out value='${user.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div> 
</body>
</html>