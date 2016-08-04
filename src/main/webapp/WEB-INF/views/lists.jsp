<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Here is your ToDo lists:
<br/>
<br/>
<c:if test="${not empty lists}">
    <c:forEach var="list" items="${lists}">
        <a href="/list/<c:out value="${list.id}"/>"><c:out value="${list.name}"/></a>
        <a href="/list/<c:out value="${list.id}"/>/edit">Edit</a>
        <br/>
    </c:forEach>
</c:if>

<br/>
<br/>

<a href="/list/create">Create New List</a>