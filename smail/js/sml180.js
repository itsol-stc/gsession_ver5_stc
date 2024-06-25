function buttonPush(cmd) {
    document.forms[0].CMD.value=cmd;
    document.forms[0].submit();
    return false;
}

function changeEnableDisable() {

    var bool1 = false;
    if (document.forms[0].sml180MailFw[0].checked) {
        bool1 = true;
        document.forms[0].sml180MailDf.value = '';
        document.forms[0].sml180SmailOp[0].checked = true;
        if (document.forms[0].sml180ZaisekiPlugin.value == 0) {
            document.forms[0].sml180HuriwakeKbn[0].checked = true;
            document.forms[0].sml180Zmail1.value = '';
            document.forms[0].sml180Zmail2.value = '';
            document.forms[0].sml180Zmail3.value = '';
        }
    }
    document.forms[0].sml180MailDf.disabled = bool1;
    document.forms[0].sml180MailDfSelected.disabled = bool1;

    if (!bool1) {
        if (document.forms[0].sml180MailDfSelected.value != 0) {
             document.forms[0].sml180MailDf.disabled = true;
        }
    }

    document.forms[0].sml180SmailOp[0].disabled = bool1;
    document.forms[0].sml180SmailOp[1].disabled = bool1;

    if (document.forms[0].sml180ZaisekiPlugin.value == 1) {
        return;
    }
    document.forms[0].sml180HuriwakeKbn[0].disabled = bool1;
    document.forms[0].sml180HuriwakeKbn[1].disabled = bool1;
    document.forms[0].sml180HuriwakeKbn[2].disabled = bool1;

    if (document.forms[0].sml180HuriwakeKbn[0].checked) {
        document.forms[0].sml180Zmail1.value = '';
        document.forms[0].sml180Zmail2.value = '';
        document.forms[0].sml180Zmail3.value = '';
        document.forms[0].sml180Zmail1Selected.disabled = true;
        document.forms[0].sml180Zmail2Selected.disabled = true;
        document.forms[0].sml180Zmail3Selected.disabled = true;
        document.forms[0].sml180Zmail1.disabled = true;
        document.forms[0].sml180Zmail2.disabled = true;
        document.forms[0].sml180Zmail3.disabled = true;
    } else if (document.forms[0].sml180HuriwakeKbn[1].checked) {
        document.forms[0].sml180Zmail1Selected.disabled = false;
        document.forms[0].sml180Zmail2Selected.disabled = false;
        document.forms[0].sml180Zmail3Selected.disabled = false;
        document.forms[0].sml180Zmail1.disabled = false;
        document.forms[0].sml180Zmail2.disabled = false;
        document.forms[0].sml180Zmail3.disabled = false;
    } else if (document.forms[0].sml180HuriwakeKbn[2].checked) {
        document.forms[0].sml180Zmail1.value = '';
        document.forms[0].sml180Zmail3.value = '';
        document.forms[0].sml180Zmail1Selected.disabled = true;
        document.forms[0].sml180Zmail2Selected.disabled = false;
        document.forms[0].sml180Zmail3Selected.disabled = true;
        document.forms[0].sml180Zmail1.disabled = true;
        document.forms[0].sml180Zmail2.disabled = false;
        document.forms[0].sml180Zmail3.disabled = true;
    }



    if (document.forms[0].sml180HuriwakeKbn[1].checked) {
        if (document.forms[0].sml180Zmail1Selected.value != 0) {
            document.forms[0].sml180Zmail1.disabled = true;
        }
        if (document.forms[0].sml180Zmail2Selected.value != 0) {
            document.forms[0].sml180Zmail2.disabled = true;
        }
        if (document.forms[0].sml180Zmail3Selected.value != 0) {
            document.forms[0].sml180Zmail3.disabled = true;
        }
    } else if (document.forms[0].sml180HuriwakeKbn[2].checked) {
        if (document.forms[0].sml180Zmail2Selected.value != 0) {
            document.forms[0].sml180Zmail2.disabled = true;
        }
    }

    return;
}
