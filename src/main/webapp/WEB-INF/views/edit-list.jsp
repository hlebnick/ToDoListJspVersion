<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
Edit Your List:
<br/>
<br/>


<form:form method="post" action="/list/processEdit" commandName="listRequest">
    <form:hidden path="id"/>

    <font color="Red">
        <form:errors path="name" /><br/>
    </font>
    <form:input path="name" /><br/>

    <input type="submit" value="Edit list"/>
</form:form>