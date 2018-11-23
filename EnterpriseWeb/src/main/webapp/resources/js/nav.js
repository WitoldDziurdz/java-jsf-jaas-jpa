jQuery(document).ready(function ($) {
    var $anchors = $('ul.nav-sidebar a, ul.navbar-nav a');

    $anchors.each(function () {
        var href = $(this).attr('href');
        var pathname = window.location.pathname;

        if ((href.length > 1 && pathname.indexOf(href) === 0) || pathname === href) {
            $(this).parent().addClass('active');
        }
    });
});

function focusAuthorInplaceInput() {
    $(function(){
        $('.ui-editable-column:last').prev().click();
    });
}
