const a = () => {
    var vision = window.innerHeight;
    var indent = document.getElementsByClassName('header')[0].offsetHeight + 60 + 43;
    var height = vision - indent;
    var all_cols = document.getElementsByClassName('main-table-col-body');
    for (var i=0;i<all_cols.length; i++){
        all_cols[i].style.height = height + "px";
    }
}

a();

addEventListener('mouseover', a);