<div id="login-error">${loginError}</div>

<form action="../j_spring_security_check" method="post">
    <p>
        <label for="j_username">Email</label>
        <input id="j_username" name="j_username" type="text"/>
    </p>

    <p>
        <label for="j_password">Password</label>
        <input id="j_password" name="j_password" type="password"/>
    </p>
    <p>
        <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
    </p>
    <input type="submit" value="Login"/>
</form>