function buttonPush(cmd) {
    document.forms[0].CMD.value=cmd;
    document.forms[0].submit();
    return false;
}

function cmn110Updated(){
    document.forms[0].CMD.value = 'reDisp';
    document.forms[0].submit();
    return true;
}
