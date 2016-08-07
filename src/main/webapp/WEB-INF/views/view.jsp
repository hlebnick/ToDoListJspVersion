<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

viewing your list ${list.name}

<br/>
<br/>
<c:if test="${not empty items}">
    <c:forEach var="item" items="${items}">
        <a href="/item/<c:out value="${item.id}"/>"><c:out value="${item.name}"/></a> <c:out value="${item.done}"/>
        <a href="/item/<c:out value="${item.id}"/>/edit">Edit</a>
        <a href="/list/<c:out value="${list.id}"/>/item/<c:out value="${item.id}"/>/remove">Remove</a>
        <br/>
    </c:forEach>
</c:if>

<br/>
<br/>

<form:form method="post" action="/list/${list.id}/item/create" commandName="itemRequest">
    <font color="Red">
        <form:errors path="name" /><br/>
    </font>
    <form:input path="name" /><br/>

    <input type="submit" value="Create new item"/>
</form:form>