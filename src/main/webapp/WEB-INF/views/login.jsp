<div id="login-error">${loginError}</div>

<form action="/process-login" method="post">
    <p>
        <label for="username">Email</label>
        <input id="username" name="username" type="text"/>
    </p>

    <p>
        <label for="password">Password</label>
        <input id="password" name="password" type="password"/>
    </p>
    <p>
        <label><input type="checkbox" id="rememberme" name="remember-me"> Remember Me</label>
    </p>
    <input type="submit" value="Login"/>
</form>

<br/>
<br/>

Or <a href="/register">Register</a>