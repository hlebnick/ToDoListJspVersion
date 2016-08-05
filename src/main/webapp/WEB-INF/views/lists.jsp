<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
Here is your ToDo lists:
<br/>
<br/>
<c:if test="${not empty lists}">
    <c:forEach var="list" items="${lists}">
        <a href="/list/<c:out value="${list.id}"/>"><c:out value="${list.name}"/></a>
        <a href="/list/<c:out value="${list.id}"/>/edit">Edit</a>
        <a href="/list/<c:out value="${list.id}/remove"/>">Remove</a>
        <br/>
    </c:forEach>
</c:if>

<br/>
<br/>


<form:form method="post" action="/list/create" commandName="listRequest">
    <font color="Red">
        <form:errors path="name" /><br/>
    </font>
    <form:input path="name" /><br/>

    <input type="submit" value="Create new list"/>
</form:form>