<html>
<head>
    <title>Bug Tracker</title>
    <link rel="stylesheet" type="text/css" href="../css/main.css">
    <meta charset="utf-8">
</head>
<body>
<header class="header">
    <a class="add-bug" href="../addbug.html">
        Добавить баг
    </a>
    <p class="header-logo">BUG TRACKER</p>
    <div class="header-info">
        <div class="header-info-username">Привет, ${login}</div>
         <a class="add-bug" href="../logout">
            Выйди
        </a>
    </div>
</header>
<div class="main">
    <div class="main-table">
        <div class="table-outer">
            <#list data.states as state>
                <div class="main-table-col">
                    <div class="main-table-col-name">${state.name}</div>
                    <div class="main-table-col-body">
                    <#list state.bugs as bug>
                        <div class="main-table-col-cell">
                            <p>${bug.shortText}</p>
                            <#list state.buttons as button>
                            <form method="post" action="movebug">
                                <input type="hidden" name="bugId" value="${bug.id}">
                                <button type="submit" class="button-do" name="newStateId"
                                        value="${button.stateTo}" title="${button.text}">
                                    ${button.text}
                                </button>
                            </form>
                            </#list>
                        </div>
                    </#list>
                    </div>
                </div>
            </#list>
         </div>
    </div>
    <img src="../images/bug.gif" style="position:absolute; top:0; left:0; z-index: 1;">
</div>
<footer></footer>
</body>
<script src="../js/script.js"></script>
</html>