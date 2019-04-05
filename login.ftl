<html> 
<head> 
<title>Bug Tracker</title> 
<link rel="stylesheet" type="text/css" href="css/main.css"> 
<meta charset="utf-8"> 
</head>
<body>
    <header class="header">
        <p class="header-logo">BUG TRACKER</p>
    </header>
    <div class="main">
        <div class="main-form">
            <#if error??>
                <div class="error-login">${error}</div>
            </#if>
            <form method="POST" action="login">
                <input type="text" name="login" id="login" placeholder="Логин">
                <input type="password" name="password" id="password" placeholder="Пароль">
                <input type="submit" value="Войти" id="button">
            </form>
        </div>
        <img src="images/bug.gif" style="position:absolute; top:0; left:0; z-index: 1;">
    </div>
    <footer></footer>
</body>
</html>