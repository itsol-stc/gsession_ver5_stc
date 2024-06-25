function selectPortlet(potletSid) {
    document.forms[0].ptl081selectPortletSid.value=potletSid;
    document.forms[0].CMD.value='selectPortlet';
    document.forms[0].submit();
    return false;
}

function closeWindow() {

    var closeFlg = document.forms[0].ptl081selectFlg.value;

    if (closeFlg == 'true') {
        var parentDocument = window.opener.document;
        parentDocument.forms[0].CMD.value = 'init';
        parentDocument.forms[0].submit();
        window.close();
    }

    return false;
}

$(function(){
    /* hover */
    $('.js_listHover').live({
        mouseenter:function (e) {
            $(this).children().addClass("list_content-on");
            $(this).prev().children().addClass("list_content-topBorder");
        },
        mouseleave:function (e) {
            $(this).children().removeClass("list_content-on");
            $(this).prev().children().removeClass("list_content-topBorder");
        }
    });
    /* hover:click */
    $(".js_listClick").live("click", function(){
        var ptlSid = $(this).parent().data("ptlsid");
        selectPortlet(ptlSid);
    });
});
