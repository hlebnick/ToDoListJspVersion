<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <style>
            span.done {
                color: gray;
                text-decoration: line-through;
            }

            span.undone {
                color: black;
                text-decoration: none;
            }
        </style>
    </head>
    <body>
        viewing your list ${list.name}

        <br/>
        <br/>
        <c:if test="${not empty items}">
            <c:forEach var="item" items="${items}">
                <p>
                    <c:set var="class">undone</c:set>
                    <c:if test="${item.done}">
                        <c:set var="class">done</c:set>
                    </c:if>

                <span class="<c:out value="${class}"/> ">
                    <c:out value="${item.name}"/>
                </span>
                    <a href="/item/<c:out value="${item.id}"/>/edit">Edit</a>
                    <a href="/list/<c:out value="${list.id}"/>/item/<c:out value="${item.id}"/>/remove">Remove</a>
                    <a href="/list/<c:out value="${list.id}"/>/item/<c:out value="${item.id}"/>/status/<c:out value="${!item.done}"/>">Change status</a>
                </p>
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
    </body>
</html>