<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
registration form
<form:form method="post" action="/process-register" commandName="registrationRequest">
    <font color="Red">${wrongEmail}</font>
    <form:label id="emailLabel" for="email" path="email">Username (in the form of an email address) :</form:label><br/>
    <font color="Red">
        <form:errors path="email" /><br/>
    </font>
    <form:input path="email" /><br/>
    <br/>

    <form:label id="passwordLabel" for="password" path="password">Password :</form:label><br/>
    <font color="Red">
        <form:errors path="password" /><br/>
        ${passwordRepeatError} <br/>
    </font>
    <form:password path="password" /><br/>
    <br/>

    <form:label id="passwordRepeatLabel" for="passwordRepeat" path="passwordRepeat">Repeat password :</form:label><br/>
    <font color="Red">
        <form:errors path="passwordRepeat" /><br/>
    </font>
    <form:password path="passwordRepeat" /><br/>
    <br/>

    <input type="submit" />
</form:form>