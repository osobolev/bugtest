<html>
<head>
<title>Bug Tracker</title>
<link rel="stylesheet" type="text/css" href="css/main.css">
<meta charset="utf-8">
</head>
<body>
    <header class="header">
        <p class="header-logo">BUG TRACKER</p>
        <a href="#addbug"><div class="add-bug">Добавить баг</div></a>
    </header>
    <div class="main">
        <div class="main-table">
            <#list data.states as state>
            <div class="main-table-col">
                <div class="main-table-col-name">${state.name}</div>
                <div class="main-table-col-body">
                    <#list state.bugs as bug>
                        <div class="main-table-col-cell">
                            <p>${bug.shortText}</p>
                            <#list state.buttons as buttons>
                                <input type="button" class="button-do" value=${db.text}>
                            </#list>
                        </div>
                    </#list>
                </div>
            </div>
            </#list>
        </div>
        <img src="images/bug.gif" style="position:absolute; top:0; left:0; z-index: 1;">
    </div>
    <footer></footer>
</body>
<script src="js/script.js"></script>
</html>