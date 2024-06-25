<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/smail/" prefix="smail" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ taglib tagdir="/WEB-INF/tags/common/" prefix="common" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<!DOCTYPE html>

<% String jusin = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.TAB_DSP_MODE_JUSIN); %>
<% String sosin = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.TAB_DSP_MODE_SOSIN); %>
<% String soko = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.TAB_DSP_MODE_SOKO); %>
<% String gomi = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.TAB_DSP_MODE_GOMIBAKO); %>
<% String mark = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_SORT_KEY_MARK); %>
<% String ttl = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_SORT_KEY_TITLE); %>
<% String kname = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_SORT_KEY_NAME); %>
<% String date = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MSG_SORT_KEY_DATE); %>
<% String asc = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC); %>
<% String desc = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC); %>

<% String unopend = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_UNOPENED); %>
<% String opend = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_OPENED); %>
<% String toroku = String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU); %>
<% String delete = String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE); %>

<% String hkbnPri = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI); %>

<% String markTel = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_TEL); %>
<% String markImp = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_INP); %>
<% String markSmaily    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SMAILY);  %>
<% String markWorry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_WORRY);   %>
<% String markAngry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ANGRY);   %>
<% String markSadly     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SADRY);   %>
<% String markBeer      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_BEER);    %>
<% String markHart      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_HART);    %>
<% String markZasetsu   = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ZASETSU); %>

<% String maxLengthTitle = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAX_LENGTH_SMLTITLE); %>


<%-- マーク画像定義 --%>

<%
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String phone = gsMsg.getMessage(request, "cmn.phone");
        String important = gsMsg.getMessage(request, "sml.61");
        String smile = gsMsg.getMessage(request, "sml.11");
        String worry = gsMsg.getMessage(request, "sml.86");
        String angry = gsMsg.getMessage(request, "sml.83");
        String sad = gsMsg.getMessage(request, "sml.87");
        String beer = gsMsg.getMessage(request, "sml.15");
        String hart = gsMsg.getMessage(request, "sml.13");
        String tired = gsMsg.getMessage(request, "sml.88");

  java.util.HashMap imgMap = new java.util.HashMap();
  imgMap.put(markTel, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../common/images/classic/icon_call.png\" alt=\"" + phone + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../common/images/original/icon_call.png\" alt=\"" + phone +"\">");
  imgMap.put(markImp, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../common/images/classic/icon_zyuu.png\" alt=\"" + important + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../common/images/original/icon_zyuu.png\" alt=\"" + important +"\">");
  imgMap.put(markSmaily, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face01.png\" alt=\"" + smile + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_smil.png\" alt=\"" + smile +"\">");
  imgMap.put(markWorry, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face02.png\" alt=\"" + worry + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_confu.png\" alt=\"" + worry +"\">");
  imgMap.put(markAngry, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face03.png\" alt=\"" + angry + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_angry.png\" alt=\"" + angry +"\">");
  imgMap.put(markSadly, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face04.png\" alt=\"" + sad + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_sad.png\" alt=\"" + sad +"\">");
  imgMap.put(markBeer, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_beer.png\" alt=\"" + beer + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_beer.png\" alt=\"" + beer +"\">");
  imgMap.put(markHart, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_hart.png\" alt=\"" + hart +"\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_hart.png\" alt=\"" + hart +"\">");
  imgMap.put(markZasetsu, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_zasetsu.png\" alt=\"" + tired +"\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_zasetu.png\" alt=\"" + tired +"\">");
  imgMap.put("none", "");
%>

<%
  String targetTitle   = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SEARCH_TARGET_TITLE);
  String targetHonbun  = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SEARCH_TARGET_HONBUN);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery.contextmenu.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<jsp:include page="/WEB-INF/plugin/smail/jsp/sml010_message.jsp" />
<jsp:include page="/WEB-INF/plugin/smail/jsp/smlatesakisel.jsp" />
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../smail/js/sml010.js?<%= GSConst.VERSION_PARAM %>20210719"></script>

<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="sml010Form" property="sml010Reload" value="0">
    var reloadinterval = <bean:write name="sml010Form" property="sml010Reload" />;
    setInterval("reloadData()",reloadinterval);
  </logic:notEqual>
-->
</script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<bean:define id="selectThemePath" value="" type="String"/>
<logic:notEmpty name="sml010Form" property="sml010AccountTheme" scope="request">
  <bean:define id="selectThemePath" name="sml010Form" property="sml010AccountTheme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="sml010Form" property="sml010AccountTheme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION <gsmsg:write key="sml.19" /></title>
</head>

<body id="sml010body" class="yui-skin-sam" onload="showLengthId($('#inputstr')[0], <bean:write name="sml010Form" property="sml010MailBodyLimit"/>, 'inputlength');" onunload="windowClose();">

<html:form styleId="smlForm" action="/smail/sml010">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="SERCHFLG" value="">

<input type="hidden" name="PROCMODE" value="">
<input type="hidden" name="SELECTSID" value="">
<input type="hidden" name="SELECTKBN" value="">
<input type="hidden" name="ACCOUNT" value="">
<input type="hidden" name="selectEsc" value="">
<input type="hidden" name="editEsc" value="">
<input type="hidden" name="processEsc" value="">

<html:hidden property="smlAccountSid" />
<html:hidden property="sml010AccountSendMailType" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml010SelectedMailKbn" />
<html:hidden property="sml010EditSid" />
<html:hidden property="sml010scriptSelUsrSid" styleId="sml010scriptSelUsrSid" />
<html:hidden property="sml010scriptSelUsrName" styleId="sml010scriptSelUsrName" />
<html:hidden property="sml010scriptSelSacSid" styleId="sml010scriptSelSacSid" />
<html:hidden property="sml010usrSid" />
<html:hidden property="sml010scriptFlg"/>
<html:hidden property="sml010scriptKbn"/>
<html:hidden property="sml050HinaKbn" />
<html:hidden property="tempDspFlg"/>

<html:hidden property="sml010SelectLabelSid"/>
<html:hidden property="sml010addLabelType"/>
<html:hidden property="sml010addLabel"/>
<html:hidden property="sml010addLabelName"/>
<html:hidden property="sml010delLabel"/>
<html:hidden property="sml010webmailShareFlg"/>

<div id="sml010banUserSidArea"></div>
<div id="sml010disableGroupSidArea"></div>

<html:hidden property="sml020webmail"/>
<html:hidden property="sml020webmailId"/>
<html:hidden property="sml020ProcMode" />
<html:hidden property="sml010MailBodyLimit"/>

<div id="sml010LabelTabSelArea"></div>
<div id="sml020ProcModeArea"></div>
<div id="sml020SelectHinaIdArea"></div>


<logic:notEmpty name="sml010Form" property="sml010SelectedDelSid" scope="request">
  <logic:iterate id="select" name="sml010Form" property="sml010SelectedDelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="select"/>">
  </logic:iterate>
</logic:notEmpty>

<div id="scriptSelUsrArea">
<logic:notEmpty name="sml010Form" property="sml010scriptUsrList" scope="request">
  <logic:iterate id="scriptSelUsrMdl" name="sml010Form" property="sml010scriptUsrList" scope="request">
    <input type="hidden" class="scriptSelUsrParams" value="<bean:write name="scriptSelUsrMdl" property="usrSid" />:<bean:write name="scriptSelUsrMdl" property="sacName" />" />
  </logic:iterate>
</logic:notEmpty>
</div>

<%-- 検索画面で使用 --%>


<input type="hidden" name="helpPrm" value="<bean:write name="sml010Form" property="sml010ProcMode" />">

<% boolean callWebmail = true; %>
<logic:notEqual name="sml010Form" property="sml020webmail" value="1">
<% callWebmail = false; %>
</logic:notEqual>

<% boolean callScript = false; %>
<logic:notEmpty name="sml010Form" property="sml010scriptSelUsrSid">
<logic:greaterThan name="sml010Form" property="sml010scriptSelUsrSid" value="0">
<% callScript = true; %>
</logic:greaterThan>
</logic:notEmpty>

<% if (!callWebmail && !callScript) { %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
<% } %>

<common:toast toastId="sml010MessageToast">
  <span id="toastMessageBody">
  </span>
</common:toast>

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../smail/images/classic/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
      <img class="header_pluginImg" src="../smail/images/original/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
    </li>
    <li><gsmsg:write key="cmn.shortmail" /></li>
    <li>
      <div>
<% if (callWebmail) { %>
        <button class="baseBtn" type="button" value="<gsmsg:write key="cmn.close" />" onClick="sml010delNewCreateMailDialog();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
<% } else if (callScript) { %>
        <button class="baseBtn" type="button" value="<gsmsg:write key="cmn.close" />" onClick="warning_alert();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
<% } else { %>
        <button type="button" class="baseBtn" id="btn_reload" value="<gsmsg:write key="cmn.reload" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <gsmsg:write key="cmn.reload" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.account" />" onClick="return buttonPush('accountConf');">
          <img class="btn_classicImg-display"  src="../common/images/classic/icon_account_setting.png" alt="<gsmsg:write key="cmn.account" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_account_setting.png" alt="<gsmsg:write key="cmn.account" />">
          <gsmsg:write key="cmn.account" />
        </button>
<% } %>
        <% boolean originalTheme = jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
      </div>
    </li>
  </ul>
</div>
<div>
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
</div>
<div class="wrapper_2column">
  <div class="side-left fs_13 lh130">
    <div id="sml_account_area" class="side_header cursor_p side_header-folding">
      <span class="side_headerTitle side_header-open">
        <span class="side_headerArrow"></span>
        <gsmsg:write key="cmn.account" />
      </span>
    </div>
    <div id="sml_account_child_area" class="side_content">
      <input type="hidden" id="smlViewAccountSv" value="<bean:write name="sml010Form" property="smlViewAccount" />" />
      <html:select property="smlViewAccount" styleId="account_comb_box" >
        <logic:notEmpty name="sml010Form" property="sml010AccountList">
          <logic:iterate id="accountMdl" name="sml010Form" property="sml010AccountList">
            <bean:define id="accoutVal" name="accountMdl" property="accountSid" type="java.lang.Integer" />
            <bean:define id="optClass" value="" />
            <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
            <bean:define id="optClass" value="mukoUserOption" />
            </logic:equal>
            <bean:define id="optClass" value="" />
            <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
            <bean:define id="optClass" value="mukoUserOption" />
            </logic:equal>
            <option value="<%= String.valueOf(accoutVal) %>" class="<bean:write name="optClass" />"  <logic:equal name="sml010Form" property="smlViewAccount" value="<%= String.valueOf(accoutVal) %>">selected</logic:equal>><bean:write name="accountMdl" property="accountName" /></option>
          </logic:iterate>
        </logic:notEmpty>
      </html:select>
      <logic:notEmpty name="sml010Form" property="sml010AccountList">
        <div>
          <bean:define id="viewAccoutVal" name="sml010Form" property="smlViewAccount" type="java.lang.Integer" />
          <logic:iterate id="accountMdl" name="sml010Form" property="sml010AccountList">
            <logic:notEqual name="accountMdl" property="accountSid" value="<%= String.valueOf(viewAccoutVal) %>">
              <div class="js_side_accountName side_accountName mb5 <logic:equal name="accountMdl" property="usrUkoFlg" value="1">mukoUser</logic:equal>" data-accountid="<bean:write name="accountMdl" property="accountSid" />">
                <span class="verAlignMid">
                  <img class="classic-display" src="../common/images/classic/icon_account_sel.png" >
                  <i class="original-display icon-account_sel"  ></i>
                  <bean:write name="accountMdl" property="accountName" />
                </span>
              </div>
            </logic:notEqual>
          </logic:iterate>
        </div>
      </logic:notEmpty>
    </div>
    <%--メールボックス --%>
    <div id="sml_mailbox_area" class="side_header cursor_p side_header-folding">
       <span class="side_headerTitle side_header-open fs_13">
         <span class="side_headerArrow"></span>
         <gsmsg:write key="sml.sml010.06" />
       </span>
    </div>

    <div id="sml_mailbox_child_area" class="side_content p0 fs_13 ">
      <div class="side_folder-focus" onclick="javascript:changeModeDir(<%= String.valueOf(jusin) %>);">
        <div class="side_folderImg side_folderImg-lineTop classic-display"></div>
        <div class="side_folderImg side_folderImg-jushin"></div>
        <div id="menu_jushin_txt" class="side-folderText js_file_hover">
          <gsmsg:write key="cmn.receive" />&nbsp;<span id="midoku_txt" class="fs_11 fw_b"></span>
        </div>
      </div>
      <div class="side_folder-focus" onclick="javascript:changeModeDir(<%= String.valueOf(sosin) %>);">
        <div class="side_folderImg side_folderImg-line classic-display"></div>
        <div class="side_folderImg side_folderImg-soshin"></div>
        <div class="side-folderText js_file_hover"><gsmsg:write key="cmn.sent" /></div>
      </div>

      <div class="side_folder-focus" onclick="javascript:changeModeDir(<%= String.valueOf(soko) %>);">
        <div class="side_folderImg side_folderImg-line classic-display"></div>
        <div class="side_folderImg side_folderImg-folder"></div>
        <div class="side-folderText js_file_hover">
        <gsmsg:write key="cmn.draft" />&nbsp;<span id="soko_txt" class="fs_11 fw_b"></span></div>
      </div>

      <% String leftLineClass = "side_folderImg-lineBottom classic-display";%>

      <logic:notEmpty name="sml010Form" property="sml010LabelList">
        <% leftLineClass =  "side_folderImg-line";%>
      </logic:notEmpty>

      <div class="side_folder-focus" onclick="javascript:changeModeDir(<%= String.valueOf(gomi) %>);">
        <div id="gomibako_bottom_div" class="side_folderImg <%= leftLineClass %>"></div>
        <div class="side_folderImg side_folderImg-dust"></div>
        <div class="side-folderText js_file_hover">
          <gsmsg:write key="cmn.trash" />&nbsp;<span id="gomi_txt" class="fs_11 fw_b"></span>
          <span>
            &nbsp;[<a href="#!" class="" id="head_menu_empty_trash_btn"><gsmsg:write key="sml.sml010.07" /></a>]
          </span>
        </div>
      </div>

      <%-- ラベル  --%>
      <div id="labelArea" class="m0 p0 lh100"></div>
      <div class=" clear_both mb5" ></div>
      <%-- ディスク使用量  --%>
      <div class="smlDiskUse cl_fontMiddle verAlignMid">
        <img class="classic-display" src="../common/images/classic/icon_disk_capacity.png"/>
        <img class="original-display"src="../common/images/original/icon_disk.png"/>
        <gsmsg:write key="sml.217" />:<wbr><span id="disk_use"><bean:write name="sml010Form" property="sml010AccountDisk" /></span><gsmsg:write key="cmn.mb" />
      </div>
      <div class='contextMenu' id='context_menu1'>
        <ul>
          <li id='all_read' class="fs_12"><gsmsg:write key="cmn.all" /><gsmsg:write key="cmn.mark.read" /></li>
          <li id='all_no_read' class="fs_12"><gsmsg:write key="cmn.all" /><gsmsg:write key="sml.sml010.02" /></li>
        </ul>
      </div>
     <div class='contextMenu' id='context_menu2'>
        <ul>
          <li id='mail_read'><gsmsg:write key="cmn.mark.read" /></li>
        </ul>
      </div>
      <div class='contextMenu' id='context_menu3'>
        <ul>
          <li id='mail_no_read'><gsmsg:write key="sml.sml010.02" /></li>
        </ul>
      </div>
    </div>
    <%--ユーザ情報 --%>
    <div id="sml_shain_area" class="side_header cursor_p side_header-folding">
       <span class="side_headerTitle side_header-open fs_13">
         <span class="side_headerArrow"></span>
         <gsmsg:write key="cmn.shain.info" />
      </span>
    </div>

    <div id="sml_shain_child_area" class="side_content">
     <div class="bor_b1">
       <div class="verAlignMid w100 mb5">
         <html:select name="sml010Form" property="sml010groupSid" styleId="sml010ChangeGrp" styleClass="wp130 m0">
            <logic:notEmpty name="sml010Form" property="sml010groupList">
              <logic:iterate id="gpBean" name="sml010Form" property="sml010groupList" scope="request">
              <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                  <logic:equal name="gpBean" property="styleClass" value="0">
                    <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:equal name="gpBean" property="styleClass" value="1">
                    <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
                  <logic:equal name="gpBean" property="styleClass" value="2">
                    <html:option styleClass="select_daihyo-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                  </logic:equal>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
          <span class="ml5">
          <button class="iconBtn-border" type="button" id="sml010GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.sml010groupSid, 'sml010groupSid', '1', 'changeGrp', '1', 'fakeGrpButton', 'sml010disableGroupSid');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
          <span class="mrl_auto"></span>
        </div>
        <input type="button" class="display_none" id="fakeGrpButton" name="fakeGrpButton" />
        <div class="verAlignMid">
          <button type="button" class="mailMenu_button js_mailToAddBtn wp50 ml0" value="0"><span class="mrl_auto"/><gsmsg:write key="cmn.from" /><span class="mrl_auto"/></button>
          <button type="button" class="mailMenu_button js_mailToAddBtn wp50 ml5" value="1"><span class="mrl_auto"/><gsmsg:write key="cmn.cc" /><span class="mrl_auto"/></button>
          <button type="button" class="mailMenu_button js_mailToAddBtn wp50 ml5" value="2"><span class="mrl_auto"/><gsmsg:write key="cmn.bcc" /><span class="mrl_auto"/></button>
        </div>
        <div class="syain_checkbox_area verAlignMid pl5">
          <input class="txt_m" type="checkbox" name="usrAllCheck" onClick="return changeChkAdd();" id="all" />
          <label for="all" class="mt5 mb5 cursor_p textLink">
            <gsmsg:write key="sml.66" />
          </label>
        </div>
      </div>

      <div id="selGrpUsrArea" class="mt5">
        <logic:notEmpty name="sml010Form" property="sml010userList" >
        <logic:iterate id="usrList" name="sml010Form" property="sml010userList" >
          <div class="syain_checkbox_area pl5 mt5 ">
            <div class="verAlignMid">
              <!-- ユーザ画像公開の場合  -->
              <logic:equal name="usrList" property="usiPictKf" value="0">

                <logic:notEmpty name="usrList" property="sacSid">
                  <input class="txt_m cursor_p " type="checkbox" name="sml010usrSids" value="sac<bean:write name="usrList" property="sacSid" />" />
                  <a href="#" class="js_syain_sel_check_a w100 verAlignMid" data-accountsid="sac<bean:write name="usrList" property="sacSid" />">
                </logic:notEmpty>
                <logic:empty name="usrList" property="sacSid">
                  <input class="txt_m cursor_p " type="checkbox" name="sml010usrSids" value="<bean:write name="usrList" property="usrSid" />" />
                  <a href="#" class="js_syain_sel_check_a w100 verAlignMid <logic:equal name="usrList" property="usrUkoFlg" value="1">mukoUser</logic:equal>" data-accountsid="<bean:write name="usrList" property="usrSid" />">
                </logic:empty>

                <logic:equal name="sml010Form" property="photoDspFlg" value="0">
                  <logic:equal name="usrList" property="binSid" value="0">
                    <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 noresize btn_classicImg-display ml5 mr5"/>
                    <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 noresize btn_originalImg-display ml5 mr5"/>
                  </logic:equal>
                  <logic:notEqual name="usrList" property="binSid" value="0">
                    <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="usrList" property="binSid"/>" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 ml5 mr5"/>
                  </logic:notEqual>
                </logic:equal>
                <span class="js_syain_sel_check_txt">
                  <logic:notEmpty name="usrList" property="sacSid">
                    <bean:write name="usrList" property="sacName" />
                  </logic:notEmpty>
                  <logic:empty name="usrList" property="sacSid">
                    <bean:write name="usrList" property="usiSei" />&nbsp;<bean:write name="usrList" property="usiMei" />
                  </logic:empty>
                </span>
                </a>
              </logic:equal>

              <!-- ユーザ画像非表示の場合  -->
              <logic:notEqual name="usrList" property="usiPictKf" value="0">
                <input class="txt_m cursor_p" type="checkbox" name="sml010usrSids" value="<bean:write name="usrList" property="usrSid" />" />
                <a href="#" id=<bean:write name="usrList" property="usrSid"/> class="js_syain_sel_check_a verAlignMid  <logic:equal name="usrList" property="usrUkoFlg" value="1">mukoUser</logic:equal>" data-accountsid="<bean:write name="usrList" property="usrSid" />">
                  <logic:equal name="sml010Form" property="photoDspFlg" value="0">
                    <div class="hikokai_photo-s wp25 ml5 mr5 verAlignMid js_syain_sel_check_img"><span class="hikokai_text mrl_auto "><gsmsg:write key="cmn.private.photo" /></span></div>
                  </logic:equal>
                  <span class="js_syain_sel_check_txt ">
                    <bean:write name="usrList" property="usiSei" />&nbsp;<bean:write name="usrList" property="usiMei" />
                  </span>
                </a>
              </logic:notEqual>
            </div>
          </div>
        </logic:iterate>
        </logic:notEmpty>
      </div>
    </div>
    <%--雛形共通 --%>
    <logic:notEmpty name="sml010Form" property="sml010HinaList">
      <div id="sml_hina_kyotu_area" class="side_header cursor_p side_header-folding">
         <span class="side_headerTitle side_header-close fs_13">
           <span class="side_headerArrow"></span>
           <gsmsg:write key="sml.sml020.03" />
        </span>
      </div>
      <div id="sml_hina_kyotu_child_area" class="side_content display_n">
        <div>
          <logic:notEmpty name="sml010Form" property="sml010HinaList">
            <table>
            <bean:define id="mod" value="0" />
            <logic:iterate id="hinaModel" name="sml010Form" property="sml010HinaList" indexId="hinaidx">
              <tr class="js_hinaSelTxt  cursor_p"  data-hinaid="<bean:write name="hinaModel" property="shnSid" />">
                <td class="pt5">
                  <%-- マーク  --%>
                  <bean:define id="imgMark"><bean:write name="hinaModel" property="shnMark" /></bean:define>
                  <% java.lang.String key = "none";  if (imgMap.containsKey(imgMark)) { key = imgMark; } %> <%= (java.lang.String) imgMap.get(key) %>
                </td>
                <td class="w100 pt5">
                  <logic:notEmpty name="hinaModel" property="shnBody">
                  <bean:define id="hinabody" name="hinaModel" property="shnBody" />
                  </logic:notEmpty>
                  <a  href="#" >
                    <bean:write name="hinaModel" property="shnHnameDsp" />
                    <span class="tooltips display_n" ><gsmsg:write key="cmn.subject" />：<bean:write name="hinaModel" property="shnTitle" />
                      <gsmsg:write key="cmn.content" />：<bean:write name="hinaModel" property="shnBody" filter="false"/>
                    </span>
                  </a>
                </td>
              </tr>
            </logic:iterate>
            </table>
          </logic:notEmpty>
        </div>
      </div>
    </logic:notEmpty>
    <%--雛形個人 --%>
    <div id="sml_hina_kojin_area" class="side_header cursor_p side_header-folding">
       <span class="side_headerTitle side_header-close fs_13">
         <span class="side_headerArrow"></span>
         <gsmsg:write key="sml.sml020.04" />
         <span class="side_confGear" id="hinagata_add" onClick="buttonPush('hina_edit'); event.stopPropagation(); return false;">
           <span class="flo_r mr10 fs_12 classic-display"><gsmsg:write key="cmn.add" /></span>
           <span class="flo_r mr10 fs_18 original-display"><i class="icon-setting"></i></span>
         </span>
       </span>
    </div>
    <div id="sml_hina_kojin_child_area" class="side_content display_n">
      <div>
        <logic:empty name="sml010Form" property="sml010HinaListKjn">
          <gsmsg:define id="msgarg1" msgkey="sml.sml010.03" type="String" />
          <bean:message key="error.not.exist.userid" arg0="<%=msgarg1 %>"/>
        </logic:empty>
        <logic:notEmpty name="sml010Form" property="sml010HinaListKjn">
          <table>
          <bean:define id="mod" value="0" />
          <logic:iterate id="hinaModelKjn" name="sml010Form" property="sml010HinaListKjn" indexId="hinaidxkjn">
            <tr class="js_hinaSelTxt cursor_p" data-hinaid="<bean:write name="hinaModelKjn" property="shnSid" />">
              <td class="pt5">

                <%-- マーク  --%>
                <bean:define id="imgMark"><bean:write name="hinaModelKjn" property="shnMark" /></bean:define>
                <% java.lang.String key = "none";  if (imgMap.containsKey(imgMark)) { key = imgMark; } %> <%= (java.lang.String) imgMap.get(key) %>
              </td>
              <td class="w100 pt5">

                <logic:notEmpty name="hinaModelKjn" property="shnBody">
                <bean:define id="hinabody" name="hinaModelKjn" property="shnBody" />
                </logic:notEmpty>
                <a   href="#">
                  <bean:write name="hinaModelKjn" property="shnHnameDsp" />
                  <span class="tooltips display_none"><gsmsg:write key="cmn.subject" />：<bean:write name="hinaModelKjn" property="shnTitle" />
                    <gsmsg:write key="cmn.content" />：<bean:write name="hinaModelKjn" property="shnBody" filter="false"/>
                  </span>
                </a>
              </td>
            </tr>

          </logic:iterate>
          </table>
        </logic:notEmpty>
      </div>
    </div>
  </div>
  <div class="main display_tbl">

    <%-- 検索  --%>

    <%-- 検索SVパラメータ start -------------------------------------------------------------------------- --%>
    <html:hidden property="sml090SvSltGroup" />
    <html:hidden property="sml090SvSltUser" />
    <div id="svAtesakiArea"></div>
    <html:hidden property="sml090SvMailSyubetsu" />
    <html:hidden property="sml090SvMailMark" />
    <html:hidden property="sml090SvKeyWord" />
    <html:hidden property="sml090SvKeyWordkbn" />
    <div id="svTargetArea"></div>
    <html:hidden property="sml090SvSearchOrderKey1" />
    <html:hidden property="sml090SvSearchSortKey1" />
    <html:hidden property="sml090SvSearchOrderKey2" />
    <html:hidden property="sml090SvSearchSortKey2" />
    <input type="hidden" name="sml090page1" value="1" />
    <input type="hidden" name="sml090page2" value="1" />
    <%-- 検索SVパラメータ end ---------------------------------------------------------------------------- --%>

    <div id="search_area_table" class="display_none">
      <div class="table_title-color display_flex bor1 border_bottom_none w100 pr5">
        <div class="ml_auto"></div>
        <div class="js_searchHeadBtn eventImg cursor_p">
          <span class="js_searchAreaDelBtn">
            <img class="btn_classicImg-display" src="../smail/images/classic/icon_search_area_del.png" >
            <img class="btn_originalImg-display" src="../smail/images/original/icon_toggle_searcharea.png">
          </span>
          <span class="js_searchAreaDelBtn-on display_none">
            <img class="btn_classicImg-display" src="../smail/images/classic/icon_search_area_del_on.png" >
            <img class="btn_originalImg-display" src="../smail/images/original/icon_toggle_searcharea.png">
          </span>
        </div>
      </div>
      <table class="table-left m0"  class="w100">
        <tr>
          <th class="w10 fs_13"><gsmsg:write key="sml.sml090.10" /></th>
          <td class="w40 fs_13">
            <div class="verAlignMid">
              <input type="radio" name="sml090MailSyubetsu" value="0" checked="checked" id="radio_jushin" ><label for="radio_jushin"><gsmsg:write key="cmn.receive" /></label>
              <input type="radio" name="sml090MailSyubetsu" value="1" id="radio_soushin" class="ml10"><label for="radio_soushin"><gsmsg:write key="cmn.sent" /></label>
              <input type="radio" name="sml090MailSyubetsu" value="2" id="radio_soukou" class="ml10"><label for="radio_soukou"><gsmsg:write key="cmn.draft" /></label>
              <input type="radio" name="sml090MailSyubetsu" value="4" id="radio_gomi" class="ml10"><label for="radio_gomi"><gsmsg:write key="cmn.trash" /></label>
            </div>
          </td>

          <th class="w10 fs_13 js_searchToggleArea"><gsmsg:write key="cmn.sendfrom" /></th>
          <td class="w40 fs_13 js_searchToggleArea">
            <div class="verAlignMid">
              <div class="wp55" ><gsmsg:write key="cmn.group" /></div>
              <gsmsg:write key="wml.215" />
              <html:select property="sml090SltGroup" styleId="sml090SltGroup" styleClass="wp150 ">
                <logic:notEmpty name="sml010Form" property="sml090GroupLabel">
                  <logic:iterate id="labelMdl" name="sml010Form" property="sml090GroupLabel">
                    <bean:define id="labelVal" name="labelMdl" property="value" type="java.lang.String" />
                    <logic:notEqual name="labelMdl" property="value" value="sac">
                      <html:option value="<%= labelVal %>"><bean:write name="labelMdl" property="label" /></html:option>
                    </logic:notEqual>
                    <logic:equal name="labelMdl" property="value" value="sac">
                      <html:option styleClass="select_daihyo-bgc" value="<%= labelVal %>"><bean:write name="labelMdl" property="label" /></html:option>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
              <button class="js_searchGrpSelBtn ml5 iconBtn-border" type="button" id="sml090GroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.sml090SltGroup, 'sml090SltGroup', '0', 'changeGrp', '1', 'fakeSearchGrpButton', 'sml010disableGroupSid');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
              <input type="button" class="display_none" id="fakeSearchGrpButton" name="fakeSearchGrpButton" />
            </div>
            <div class="mt5 verAlignMid">
              <div class="wp55" ><gsmsg:write key="cmn.user" /></div><gsmsg:write key="wml.215" />
              <html:select property="sml090SltUser" styleId="sml090SltUser" styleClass="wp150 ">
                <html:optionsCollection name="sml010Form" property="sml090UserLabel" value="value" label="label" />
              </html:select>
            </div>
          </td>
        </tr>
        <tr class="js_searchToggleArea">
          <th class="w10 fs_13"><gsmsg:write key="cmn.mark" /></th>
          <td class="w40 fs_13">
            <span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="-1" checked="checked" id="radio_all">
              <label for="radio_all" class="ml5"><gsmsg:write key="cmn.without.specifying" /></label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="0" id="radio_none">
              <label for="radio_none"  class="ml5"><gsmsg:write key="cmn.no3" /></label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="4" id="radio_tell">
              <label for="radio_tell" class="no_w verAlignMid"><%=imgMap.get(markTel) %>
                <gsmsg:write key="cmn.phone" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="8" id="radio_important">
              <label for="radio_important"  class="no_w verAlignMid"><%=imgMap.get(markImp) %>
                <gsmsg:write key="sml.61" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="101" id="radio_Smaily">
              <label for="radio_Smaily"  class="no_w verAlignMid"><%=imgMap.get(markSmaily) %>
                <gsmsg:write key="sml.11" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="102" id="radio_Worry">
              <label for="radio_Worry"  class="no_w verAlignMid"><%=imgMap.get(markWorry) %>
                <gsmsg:write key="sml.86" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="103" id="radio_Angry">
              <label for="radio_Angry"  class="no_w verAlignMid"><%=imgMap.get(markAngry) %>
                <gsmsg:write key="sml.83" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="104" id="radio_Sadly">
              <label for="radio_Sadly"  class="no_w verAlignMid"><%=imgMap.get(markSadly) %>
                <gsmsg:write key="sml.87" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="201" id="radio_Beer">
              <label for="radio_Beer"  class="no_w verAlignMid"><%=imgMap.get(markBeer) %>
                <gsmsg:write key="sml.15" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="202" id="radio_Hart">
              <label for="radio_Hart"  class="no_w verAlignMid"><%=imgMap.get(markHart) %>
                <gsmsg:write key="sml.13" />
              </label>
            </span><!--
         --><span class="no_w mr10 pr5 verAlignMid">
              <input type="radio" name="sml090MailMark" value="203" id="radio_markZasetsu">
              <label for="radio_markZasetsu"  class="no_w verAlignMid"><%=imgMap.get(markZasetsu) %>
                <gsmsg:write key="sml.88" />
              </label>
            </span>
          </td>

          <th class="w10 fs_13"><gsmsg:write key="cmn.from" /></th>
          <td class="w40 fs_13 txt_t">
            <button type="button" class="js_mailSendSelBtn baseBtn" value="<gsmsg:write key="cmn.select" />" id="btnSearchAtesakiSelect">
              <gsmsg:write key="cmn.select" />
              <span class="js_mailSendSelBtn_data"
                     data-displayname="<gsmsg:write key="cmn.from" />"
                     data-addarea="#atesaki_search_area"
                     data-inputname="sml090Atesaki"
                   />
            </button>
            <input type="hidden" id="btnSearchAtesakiSelectVal" value="<gsmsg:write key="cmn.from" />" />
            <input type="hidden" id="btnSearchAtesakiSelectKbn" value="3" />
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.clear" />" id="btnSearchAtesakiClear">
              <gsmsg:write key="cmn.clear" />
            </button>
            <div class="lh200 pl5" id="atesaki_search_area"></div>
          </td>
        </tr>

        <tr>
          <th class="w10 fs_13"><gsmsg:write key="cmn.keyword" /></th>
          <td class="w40 fs_13">
            <input type="text" name="sml090KeyWord" maxlength="100" class="wp300" value="">
            <div class="verAlignMid">
              <input type="radio" name="sml090KeyWordkbn" value="0" checked="checked" id="keyKbn_01">
              <label for="keyKbn_01"><gsmsg:write key="cmn.contains.all.and" /></label>

              <input type="radio" name="sml090KeyWordkbn" value="1" id="keyKbn_02" class="ml10">
              <label for="keyKbn_02"><gsmsg:write key="cmn.orcondition" /></label>
            </div>
          </td>

          <th class="w10 fs_13 js_searchToggleArea"><gsmsg:write key="cmn.search2" /></th>
          <td class="w40 fs_13 js_searchToggleArea ">
            <div class="verAlignMid">
              <input type="checkbox" name="sml090SearchTarget" value="1" checked="checked" id="search_scope_01">
              <label for="search_scope_01"><gsmsg:write key="cmn.subject" /></label>

              <input type="checkbox" name="sml090SearchTarget" value="2" checked="checked" id="search_scope_02" class="ml10">
              <label for="search_scope_02"><gsmsg:write key="cmn.body" /></label>
            </div>
          </td>

        </tr>

        <tr class="js_searchToggleArea">
          <th class="w10 fs_13"><gsmsg:write key="cmn.sort.order" /></th>
          <td class="w90 fs_13" colspan="3">
            <div class="verAlignMid">
              <gsmsg:write key="cmn.first.key" />
              <span class="ml5"></span>
              <html:select property="sml090SearchSortKey1" styleClass="mr5">
                  <html:optionsCollection name="sml010Form" property="sml090SortKeyLabelList" value="value" label="label" />
              </html:select>
              <input type="radio" name="sml090SearchOrderKey1" value="0" id="sort1_up"><label for="sort1_up" class="mr10"><gsmsg:write key="cmn.order.asc" /></label>
              <span class="ml5"></span>
              <input type="radio" name="sml090SearchOrderKey1" value="1" checked="checked" id="sort1_dw"><label for="sort1_dw" class="mr10"><gsmsg:write key="cmn.order.desc" /></label>
              <span class="ml10"></span>
              <gsmsg:write key="cmn.second.key" />
              <span class="ml5"></span>
              <html:select property="sml090SearchSortKey2" styleClass="mr5">
                <html:optionsCollection name="sml010Form" property="sml090SortKeyLabelList" value="value" label="label" />
              </html:select>
              <input type="radio" name="sml090SearchOrderKey2" value="0" checked="checked" id="sort2_up"><label for="sort2_up" class="mr10"><gsmsg:write key="cmn.order.asc" /></label>
              <span class="ml10"></span>
              <input type="radio" name="sml090SearchOrderKey2" value="1" id="sort2_dw"><label for="sort2_dw" class="mr10"><gsmsg:write key="cmn.order.desc" /></label>
            </div>
          </td>
        </tr>
      </table>

      <div class="txt_c mt10">
        <button type="button" class="baseBtn" id="head_menu_search_btn2" value="<gsmsg:write key="cmn.search" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>

    </div>
    <!-- タブヘッダー -->
    <ul class="tabHeader fs_13 w100">
      <li id="mail_list_tab" class="tabHeader_tab-on bgC_header2 bgI_none js_mailAreaHead2 pt5 pr10 pl10" >
        <logic:equal name="sml010Form" property="sml010ProcMode" value="<%= jusin %>">
         <gsmsg:write key="cmn.receive" />
        </logic:equal>
        <logic:equal name="sml010Form" property="sml010ProcMode" value="<%= sosin %>">
          <gsmsg:write key="cmn.sent" />
        </logic:equal>
        <logic:equal name="sml010Form" property="sml010ProcMode" value="<%= soko %>">
          <gsmsg:write key="cmn.draft" />
        </logic:equal>
        <logic:equal name="sml010Form" property="sml010ProcMode" value="<%= gomi %>">
          <gsmsg:write key="cmn.trash" />
        </logic:equal>
      </li>
      <li class="classic-display bor_b1 wp5 m0"></li>
      <li class="tabHeader_space verAlignMid w100">
        <div class="mrl_auto"></div>
        <input
          id="search_text_val" class="wp120 hp22 js_tabHeader_serchSpace" type="text"
          value="" maxlength="100"> <span class="ml5"></span>
        <button type="button" class="baseBtn hp22 js_tabHeader_serchSpace" id="sml_search_btn" value="<gsmsg:write key="cmn.search" />" onClick="return false">
           <img class="btn_classicImg-display hp15" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
           <img class="btn_originalImg-display hp15" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
           <gsmsg:write key="cmn.search" />
        </button>
       </li>
    </ul>

    <!-- 一覧 メニュー -->
    <div class="js_mailListArea w100 mailMenu bgC_header2 ">
      <bean:define id="showLabelButton" value =" " />
      <bean:define id="showKidokuButton" value =" " />
      <bean:define id="showDustButton" value ="" />
      <bean:define id="showDelButton" value ="display_none" />
      <bean:define id="showReturnButton" value ="display_none" />
      <logic:equal name="sml010Form" property="sml010ProcMode" value="<%= gomi %>">
        <bean:define id="showLabelButton" value ="display_none" />
        <bean:define id="showKidokuButton" value ="display_none" />
        <bean:define id="showDustButton" value ="display_none" />
        <bean:define id="showDelButton" value ="" />
        <bean:define id="showReturnButton" value ="" />
      </logic:equal>

      <button type="button" class="mailMenu_button js_headMenuAddBtn" value="<gsmsg:write key="cmn.create.new" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add_15.png" alt="<gsmsg:write key="cmn.create.new" />">
        <gsmsg:write key="cmn.create.new" />
      </button>
      <div class="ml5 mailMenu_buttonSet">
        <button type="button" id="head_menu_list_pdf_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf_15.png" alt="<gsmsg:write key="cmn.pdf" />">
          <gsmsg:write key="cmn.pdf" />
        </button>
        <button type="button" id="head_menu_list_eml_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_eml_15.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <gsmsg:write key="cmn.output.eml" />
        </button>
      </div>
      <div class="ml5 mailMenu_buttonSet <%=showLabelButton%>">
        <button type="button" id="head_menu_list_label_add_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <button type="button" id="head_menu_list_label_del_btn" class="mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>
      <div class="ml5 mailMenu_buttonSet <%=showKidokuButton%>">
        <button type="button" id="head_menu_list_kidoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kidoku.png" alt="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_checked_15.png" alt="<gsmsg:write key="cmn.read.already" />">
          <gsmsg:write key="cmn.read.already" />
        </button>
        <button type="button" id="head_menu_list_midoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <gsmsg:write key="cmn.read.yet" />
        </button>
      </div>
      <button type="button" id="head_menu_list_dust_btn" class="ml5 mailMenu_button <%=showDustButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" id="head_menu_list_del_btn" class="ml5 mailMenu_button <%=showDelButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" id="head_menu_return_btn" class="ml5 mailMenu_button <%=showReturnButton%>" value="<gsmsg:write key="cmn.undo" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_return_15.png" alt="<gsmsg:write key="cmn.undo" />">
        <gsmsg:write key="cmn.undo" />
      </button>
      <div id="sml_page_top_area" class="paging js_paging ml_auto display_none js_paging-mail">
        <button type="button" class="webIconBtn js_paging_prevBtn" >
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
          <i class="icon-paging_left"></i>
        </button>
        <select  name="sml010Slt_page1" class="paging_combo js_paging_combo">
        </select>
        <button type="button" class="webIconBtn js_paging_nextBtn" >
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </div>

    <!-- リストBODY  -->
    <table id="mail_list_draw_table" class="w100 js_mailListArea table-top table-fixed mt0 mb0 lh100"></table>
    <!-- 一覧 ページコンボ下部 -->
    <div class="verAlignMid w100 js_mailListArea">
      <div class="paging js_paging ml_auto display_none js_paging-mail">
          <button type="button" class="webIconBtn js_paging_prevBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
            <i class="icon-paging_left"></i>
          </button>
          <select  name="sml010Slt_page2" class="paging_combo js_paging_combo">
          </select>
          <button type="button" class="webIconBtn js_paging_nextBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
            <i class="icon-paging_right"></i>
          </button>
      </div>
    </div>

    <!-- 検索結果 メニュー -->
    <div class="js_mailSearchListArea w100 mailMenu bgC_header2 display_none ">
      <bean:define id="showLabelButton" value =" " />
      <bean:define id="showKidokuButton" value =" " />
      <bean:define id="showDustButton" value ="" />
      <bean:define id="showDelButton" value ="display_none" />

      <button type="button" class="mailMenu_button js_headMenuAddBtn" value="<gsmsg:write key="cmn.create.new" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.create.new" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add_15.png" alt="<gsmsg:write key="cmn.create.new" />">
        <gsmsg:write key="cmn.create.new" />
      </button>
      <div class="ml5 mailMenu_buttonSet">
        <button type="button" id="head_menu_search_list_pdf_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf_15.png" alt="<gsmsg:write key="cmn.pdf" />">
          <gsmsg:write key="cmn.pdf" />
        </button>
        <button type="button" id="head_menu_search_list_eml_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_mail.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_eml_15.png" alt="<gsmsg:write key="cmn.output.eml" />">
          <gsmsg:write key="cmn.output.eml" />
        </button>
      </div>
      <div class="ml5 mailMenu_buttonSet <%=showLabelButton%>">
        <button type="button" id="head_menu_search_list_label_add_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.add.label2" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_add_15.png" alt="<gsmsg:write key="cmn.add.label2" />">
          <gsmsg:write key="cmn.add.label2" />
        </button>
        <button type="button" id="head_menu_search_list_label_del_btn" class="mailMenu_button" value="<gsmsg:write key="wml.js.108" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_label_del_15.png" alt="<gsmsg:write key="wml.js.108" />">
          <gsmsg:write key="wml.js.108" />
        </button>
      </div>
      <div class="ml5 mailMenu_buttonSet <%=showKidokuButton%>">
        <button type="button" id="head_menu_search_list_kidoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kidoku.png" alt="<gsmsg:write key="cmn.read.already" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei_15.png" alt="<gsmsg:write key="cmn.read.already" />">
          <gsmsg:write key="cmn.read.already" />
        </button>
        <button type="button" id="head_menu_search_list_midoku_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_midoku.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_midoku_15.png" alt="<gsmsg:write key="cmn.read.yet" />">
          <gsmsg:write key="cmn.read.yet" />
        </button>
      </div>
      <button type="button" id="head_menu_search_list_dust_btn" class="ml5 mailMenu_button <%=showDustButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" id="head_menu_search_list_del_btn" class="ml5 mailMenu_button <%=showDelButton%>" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <div id="sml_search_page_top_area" class="paging js_paging ml_auto display_none js_paging-search">
        <button type="button" class="webIconBtn js_paging_prevBtn">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
          <i class="icon-paging_left"></i>
        </button>
        <select  name="sml090Slt_page1" class="paging_combo js_paging_combo">
        </select>
        <button type="button" class="webIconBtn js_paging_nextBtn">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </div>

    <!-- 検索結果リストBODY  -->
    <table id="mail_search_list_draw_table" class="js_mailSearchListArea table-top table-fixed mt0 mb0 lh100" cellpadding="0" cellspacing="0"></table>
    <div class="js_mailSearchListArea bor_t1 display_n"></div>
    <!-- 検索結果ページング 下部  -->
    <div class="verAlignMid w100 js_mailSearchListArea display_none">
      <div class="paging js_paging ml_auto display_none js_paging-search">
          <button type="button" class="webIconBtn js_paging_prevBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
            <i class="icon-paging_left"></i>
          </button>
          <select  name="sml090Slt_page2" class="paging_combo js_paging_combo">
          </select>
          <button type="button" class="webIconBtn  js_paging_nextBtn" >
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
            <i class="icon-paging_right"></i>
          </button>
      </div>
    </div>


    <!-- 新規作成 メニュー -->
    <div class="js_mailCreateArea w100 mailMenu bgC_header2 display_none ">
      <button type="button" id="head_menu_send_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.ok" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check_15.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <button type="button" id="head_menu_soko_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.save.draft" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_save_soukou.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_save_soukou_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.save.draft" />
      </button>
      <button type="button" id="head_menu_del_soko_btn" class="mailMenu_button mr5 display_none" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" id="head_menu_hinagata_btn" class="mailMenu_button" value="<gsmsg:write key="sml.sml010.03" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_template.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_template_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="sml.sml010.03" />
      </button>
    </div>
    <!-- 新規作成 Body -->
    <div class="bor1 w100 js_mailCreateArea bgC_tableCell display_none" >
      <div class="w100 " id="attachment_FormArea1">
        <!-- 差出人 -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="wp60 pl15 fw_b no_w"><gsmsg:write key="cmn.sendfrom"/></div>
          <div class="m5 ">
            <html:select property="sml020SendAccount" styleId="send_account_comb_box" styleClass="wp180">
              <logic:notEmpty name="sml010Form" property="sml010AccountList">
                <logic:iterate id="accountMdl" name="sml010Form" property="sml010AccountList">
                  <bean:define id="accoutVal" name="accountMdl" property="accountSid" type="java.lang.Integer" />
                  <bean:define id="optClass" value="" />
                  <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
                    <bean:define id="optClass" value="mukoUserOption" />
                  </logic:equal>
                  <bean:define id="optClass" value="" />
                  <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
                    <bean:define id="optClass" value="mukoUserOption" />
                  </logic:equal>
                  <option value="<%= String.valueOf(accoutVal) %>" class="<bean:write name="optClass" />"<logic:equal name="sml010Form" property="sml020SendAccount" value="<%= String.valueOf(accoutVal) %>">selected</logic:equal>><bean:write name="accountMdl" property="accountName" /></option>
                </logic:iterate>
              </logic:notEmpty>
            </html:select>
          </div>
        </div>
        <!-- 宛先 -->
        <div class="display_tbl w100 table-fixed bor_b1 borC_weak">
          <div class="wp60 pl15 fw_b no_w display_tbl_c txt_m"><gsmsg:write key="cmn.from"/><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></div>
          <div class="pl5 pt5 pb5 wp30 display_tbl_c  txt_m">
            <button type="button" class="js_mailSendSelBtn iconBtn-border wp23hp23 ">
              <img class="btn_classicImg-display m0" src="../common/images/classic/icon_add.png" >
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" >
              <span class="js_mailSendSelBtn_data"
                     data-displayname="<gsmsg:write key="cmn.from" />"
                     data-addarea="#atesaki_to_area"
                     data-inputname="sml020userSid"
                   />
            </button>
          </div>
          <div class=" w100 display_tbl_c pl5">
            <div id="atesaki_to_area" class="js_selectAtesakiArea pt5 pr5 mb5"></div>
            <a href="#!" class="js_atesakiAllDisp fs_12 "></a>
          </div>
        </div>
        <!-- CC -->
        <div class="display_tbl w100 table-fixed bor_b1 borC_weak">
          <div class="wp60 pl15 fw_b no_w display_tbl_c  txt_m"><gsmsg:write key="cmn.cc"/></span></div>
          <div class="pl5 pt5 pb5 wp30 display_tbl_c  txt_m">
            <button type="button" class="js_mailSendSelBtn iconBtn-border wp23hp23 ">
              <img class="btn_classicImg-display m0" src="../common/images/classic/icon_add.png" >
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" >
              <span class="js_mailSendSelBtn_data"
                     data-displayname="<gsmsg:write key="cmn.cc" />"
                     data-addarea="#atesaki_cc_area"
                     data-inputname="sml020userSidCc"
                   />
            </button>
          </div>
          <div class="w100 display_tbl_c pl5">
            <div id="atesaki_cc_area" class="js_selectAtesakiArea pt5 mb5 pr5"></div>
            <a href="#!" class="js_atesakiAllDisp fs_12 "></a>
          </div>
        </div>
        <!-- BCC -->
        <div class="display_tbl w100 table-fixed bor_b1 borC_weak">
          <div class="wp60 pl15 fw_b no_w display_tbl_c  txt_m"><gsmsg:write key="cmn.bcc"/></span></div>
          <div class="pl5 pt5 pb5 wp30 display_tbl_c  txt_m">
            <button type="button" class="js_mailSendSelBtn iconBtn-border wp23hp23 mr5">
              <img class="btn_classicImg-display m0" src="../common/images/classic/icon_add.png" >
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" >
              <span class="js_mailSendSelBtn_data"
                     data-displayname="<gsmsg:write key="cmn.bcc" />"
                     data-addarea="#atesaki_bcc_area"
                     data-inputname="sml020userSidBcc"
                   />
            </button>
          </div>
          <div class="w100 display_tbl_c pl5">
            <div id="atesaki_bcc_area" class="js_selectAtesakiArea pt5 mb5 pr5"></div>
            <a href="#!" class="js_atesakiAllDisp fs_12 "></a>
          </div>
        </div>
        <!-- 件名 -->
        <div class="verAlignMid w100 bor_b1 borC_weak">
          <div class="wp60 pl15 fw_b no_w"><gsmsg:write key="cmn.subject" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></div>
          <div class="m5 ">
            <input type="text" name="sml020Title" maxlength="<%= maxLengthTitle %>" value="" class="wp600">
          </div>
        </div>
        <%-- マーク --%>
        <div class="p5 pl15 bor_b1 borC_weak  ">
          <span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="0" id="sml020Mark_0">
            <label for="sml020Mark_0" class="ml5" ><gsmsg:write key="cmn.no3" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markTel %>" id="sml020Mark_1">
            <label for="sml020Mark_1" class="no_w verAlignMid"><%=imgMap.get(markTel) %>
            <gsmsg:write key="cmn.phone" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markImp %>" id="sml020Mark_2">
            <label for="sml020Mark_2" class="no_w verAlignMid"><%=imgMap.get(markImp) %>
            <gsmsg:write key="sml.61" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markSmaily %>" id="sml020Mark_3">
            <label for="sml020Mark_3" class="no_w verAlignMid"><%=imgMap.get(markSmaily) %>
            <gsmsg:write key="sml.11" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markWorry %>" id="sml020Mark_4">
            <label for="sml020Mark_4" class="no_w verAlignMid"><%=imgMap.get(markWorry) %>
            <gsmsg:write key="sml.86" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markAngry %>" id="sml020Mark_5">
            <label for="sml020Mark_5" class="no_w verAlignMid"><%=imgMap.get(markAngry) %>
            <gsmsg:write key="sml.83" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markSadly %>" id="sml020Mark_6">
            <label for="sml020Mark_6" class="no_w verAlignMid"><%=imgMap.get(markSadly) %>
            <gsmsg:write key="sml.87" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markBeer %>" id="sml020Mark_7">
            <label for="sml020Mark_7" class="no_w verAlignMid"><%=imgMap.get(markBeer) %>
            <gsmsg:write key="sml.15" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markHart %>" id="sml020Mark_8">
            <label for="sml020Mark_8" class="no_w verAlignMid"><%=imgMap.get(markHart) %>
            <gsmsg:write key="sml.13" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <input type="radio" name="sml020Mark" value="<%=markZasetsu %>" id="sml020Mark_9">
            <label for="sml020Mark_9" class="no_w verAlignMid"><%=imgMap.get(markZasetsu) %>
            <gsmsg:write key="sml.88" /></label>
          </span>
        </div>
        <%-- 添付ファイル --%>
        <span id="smlAttachmentIdArea"></span>

        <div class="bor_b1 w100 p5 pl15">
          <button type="button" class="baseBtn js_attacheBtn" value="<gsmsg:write key="cmn.attached" />">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
            <gsmsg:write key="cmn.attached" />
          </button><span class="txt_c ml5 fs_12"><gsmsg:write key="cmn.attach.drag.drop"/></span>
          <input type="file" id="attachmentAreaBtn1" class="display_none" onchange="attachFileSelect(this, 1);" multiple="">
          <div id="composeTempFile" class="ml5"></div>
        </div>
      </div>
      <!-- テキスト入力切り替え -->
      <div class="bor_b1 w100 display_inline bgC_header2">
        <div class="ml_auto bor_r1"></div>
        <a id="text_html" href="#!" class="js_mailCreateBottomSelTextForm wp140 cursor_p fw_b cl_fontBlock txt_c"><gsmsg:write key="wml.109" /><gsmsg:write key="sml.sml010.08" /></a>
        <a id="text_text" href="#!" class="js_mailCreateBottomSelTextForm wp140 cursor_p fw_b cl_fontBlock txt_c display_none"><gsmsg:write key="wml.js.12" /><gsmsg:write key="sml.sml010.08" /></a>
        <input type="hidden" name="sml020MailType" value="0" />
      </div>

      <div class="fw_b pl15">
        <span id="attachmentFileErrorArea2"></span>
        <gsmsg:write key="cmn.body" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </div>

      <div id="html_input_area" class="pl15 pr10 mb5 display_none">

        <div id="attachment_FormArea2" class="p0 m0">
        <textarea id="html_input" ></textarea>
        <input type="hidden" name="sml020BodyHtml" value="" />
        </div>
        <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">

        <div id="smlComposeBodyContent" class="p0 m0">
        </div>
      </div>
      <bean:define id="maxLengthText" name="sml010Form" property="sml010MailBodyLimit"/>
      <div id="text_input_area" class="pl15 pr10">
        <textarea id="text_input" class="html_text_input w100 m0" name="sml020Body" rows="20" wrap="soft" onkeyup="showLengthStr(value, <%=maxLengthText  %>, 'inputlength');" id="inputstr"></textarea>
        <logic:notEqual name="sml010Form" property="sml010MailBodyLimit" value="0">
          <div id="text_count_area" class="fs_13 w100 pl15 pr10 display_inline">
            <div class="ml_auto"></div>
            <div>
              <gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength">0</span><span class="ml5">/&nbsp;<bean:write name="sml010Form" property="sml010MailBodyLimit"/>&nbsp;<gsmsg:write key="cmn.character" /></span>
            </div>
          </div>
        </logic:notEqual>
      </div>
    </div>

    <%-- 新規作成 確認 メニュー --%>
    <div class="js_mailCreateKakuninArea w100 mailMenu bgC_header2 display_none" >
      <button type="button" id="head_menu_send_kakunin_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.final" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_sent.png" alt="<gsmsg:write key="cmn.final" />">
        <gsmsg:write key="cmn.sent" />
      </button>

      <button type="button" class="mailMenu_button mr5"  id="head_menu_back_kakunin_btn" value="<gsmsg:write key="cmn.back" />">
        <img class="btn_classicImg-display" src="../smail/images/classic/icon_mail_back.gif" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back_15.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>

    </div>
    <%-- 新規作成 確認 BODY --%>
    <div id="sml_create_kakunin_body" class="js_mailCreateKakuninArea bgC_tableCell w100 bor1 display_none">
    </div>
    <%-- 新規作成 確認 内容部 template --%>
    <div id="sml_create_kakunin_body_htmlframe" class="display_none">
      <htmlframe:write attrClass="mt5 mb5 pl20 w100 word_b-all tinymce-inherit" themePath="<%=selectThemePath %>">
      </htmlframe:write>
    </div>

    <!-- 受信確認テンプレート -->
    <div id="sml_create_kakunin_body-template" class="display_none" >

      <!-- 差出人 -->
      <div  class="js_atesakiSenduserKakuninArea display_tbl table-fixed w100 bor_b1">
        <div class="wp60 txt_m pl15 fw_b no_w display_tbl_c"><gsmsg:write key="cmn.sendfrom"/></div>
        <div class="p5 txt_m js_mailCreateKakunin_content display_tbl_c">
        </div>
      </div>

      <!-- 宛先 -->
      <div  class="js_atesakiToKakuninArea  display_tbl table-fixed w100 bor_b1">
        <div class="wp60 txt_m pl15 fw_b no_w display_tbl_c txt_m"><gsmsg:write key="cmn.from"/></div>
        <div class="p5 js_mailCreateKakunin_content display_tbl_c ">
        </div>
      </div>

      <!-- CC -->
      <div  class="js_atesakiCcKakuninArea  display_tbl table-fixed w100 bor_b1">
        <div class="wp60 pt5 pl15 fw_b no_w display_tbl_c txt_m"><gsmsg:write key="cmn.cc"/></div>
        <div class="p5 js_mailCreateKakunin_content display_tbl_c">
        </div>
      </div>

      <!-- BCC -->
      <div class="js_atesakiBccKakuninArea  display_tbl table-fixed w100 bor_b1">
        <div class="wp60 pt5 pl15 fw_b no_w display_tbl_c txt_m"><gsmsg:write key="cmn.bcc"/></div>
        <div class="p5 js_mailCreateKakunin_content display_tbl_c">
        </div>
      </div>

      <!-- 件名 -->
      <div class=" display_tbl table-fixed w100 bor_b1">
        <div class="wp60 txt_m pl15 fw_b no_w display_tbl_c txt_m"><gsmsg:write key="cmn.subject" /></div>
        <div class="p5 txt_m display_tbl_c">
            <%-- マーク --%>
            <span  class="js_atesakiMarkKakuninArea">
            </span>
            <span class="js_atesakiTitleKakuninArea js_mailCreateKakunin_content">
            </span>
          </span>
        </div>
      </div>
      <!-- 添付ファイル -->
      <div class="js_atesakiTmpKakuninArea w100 pl15 bor_b1">
      </div>
      <div class="js_atesakiBodyKakuninArea">
      </div>
    </div>


    <!-- メール確認 メニュー -->
    <div class="js_mailKakuninArea js_mailMenu w100 mailMenu bgC_header2 display_none ">
      <!-- 複写して新規作成-->
      <button type="button" id="head_menu_copy_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.register.copy.new" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.register.copy.new" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_copy_15.png" alt="<gsmsg:write key="cmn.register.copy.new" />">
        <gsmsg:write key="cmn.register.copy.new" />
      </button>
      <!-- 返信-->
      <button type="button" id="head_menu_replay_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.reply" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_replay.png" alt="<gsmsg:write key="cmn.reply" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_replay_15.png" alt="<gsmsg:write key="cmn.reply" />">
        <gsmsg:write key="cmn.reply" />
      </button>
      <!-- 全返信-->
      <button type="button" id="head_menu_all_replay_btn" class="mailMenu_button mr5" value="<gsmsg:write key="sml.67" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_all_replay.png" alt="<gsmsg:write key="sml.67" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_all_replay_15.png" alt="<gsmsg:write key="sml.67" />">
        <gsmsg:write key="sml.67" />
      </button>
      <!-- 転送-->
      <button type="button" id="head_menu_forward_btn" class="mailMenu_button mr5" value="<gsmsg:write key="cmn.forward" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_forward.png" alt="<gsmsg:write key="cmn.forward" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_forward_15.png" alt="<gsmsg:write key="cmn.forward" />">
        <gsmsg:write key="cmn.forward" />
      </button>
      <!-- 削除 -->
      <button type="button" id="head_menu_dust_btn" class="mr5 mailMenu_button" value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_trash_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <!-- 全て削除 -->
      <button type="button" id="head_menu_alldel_btn" class="mr5 mailMenu_button" value="<gsmsg:write key="cmn.delete.all" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete.all" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete.all" />">
        <gsmsg:write key="cmn.delete.all" />
      </button>
      <!-- 元に戻す -->
      <button type="button" id="head_menu_revived_btn" class="mr5 mailMenu_button" value="<gsmsg:write key="cmn.undo" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_return_15.png" alt="<gsmsg:write key="cmn.undo" />">
        <gsmsg:write key="cmn.undo" />
      </button>
      <!-- 削除 （ゴミ箱）-->
      <button type="button" id="head_menu_del_btn" class="mr5 mailMenu_button " value="<gsmsg:write key="cmn.delete" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete_15.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>

      <div class="mr5 mailMenu_buttonSet">
        <button type="button" id="head_menu_prev_btn" class="mailMenu_button" value="<gsmsg:write key="cmn.previous"/>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l.png" alt="<gsmsg:write key="cmn.previous"/>">
          <i class="icon-arrow_left bgC_darkGray"></i>
          <gsmsg:write key="cmn.previous"/>
        </button>
        <button type="button" id="head_menu_next_btn"
          class="mailMenu_button" value="<gsmsg:write key="cmn.next"/>">
          <gsmsg:write key="cmn.next"/>
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r.png" alt="<gsmsg:write key="cmn.next"/>">
          <i class="icon-arrow_right bgC_darkGray"></i>
        </button>
      </div>
      <button type="button" id="head_menu_webmail_share_btn" class="mr5 mailMenu_button" value="<gsmsg:write key="sml.sml010.12" />">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_send_mail.png" alt="<gsmsg:write key="sml.sml010.12" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_send_mail_15.png" alt="<gsmsg:write key="sml.sml010.12" />">
        <gsmsg:write key="sml.sml010.12" />
      </button>
    </div>
    <!-- メール確認 -->
    <div id="sml_kakunin_body" class="bor1 w100 bgC_tableCell js_mailKakuninArea display_none" >
    </div>
    <!-- メール確認フッター -->
    <div class="js_mailKakuninArea w100 mailMenu bor_b1 bgC_header3 display_none ">
      <div class="ml_auto"></div>
      <a href="#!" class="verAlignMid js_mailCheckBodyBottomPdf">
        <img class="btn_classicImg-display "
          src="../common/images/classic/icon_pdf.png"
          alt="<gsmsg:write key="wml.237" />">
        <img
          class="btn_originalImg-display  "
          src="../common/images/original/icon_pdf.png"
          alt="<gsmsg:write key="wml.237" />">
        <gsmsg:write key="wml.237" />
      </a>
      <a href="#!" class="verAlignMid js_mailCheckBodyBottomEml">
        <img class="btn_classicImg-display  "
          src="../common/images/classic/icon_mail.png"
          alt="<gsmsg:write key="cmn.export.eml" />">
        <img
          class="btn_originalImg-display ml5"
          src="../common/images/original/icon_eml.png"
          alt="<gsmsg:write key="cmn.export.eml" />">
        <gsmsg:write key="cmn.export.eml" />
      </a>
    </div>

    <!-- 受信確認テンプレート -->
    <div id="sml_kakunin_body-jusin" class="js_mailKakuninArea-jusin display_none" >
      <!-- メールヘッダ情報 -->
      <div class="p5 pl20 bgC_header3 display_tbl table-fixed w100">
        <!-- 受信時差出人画像 -->
        <div class="h100 bgC_header3 js_dspFromImg display_tbl_c txt_t wp80"></div>
        <div class="bgC_header3 pl5 display_tbl_c">
          <!-- 件名 -->
          <div class="fs_16 fw_b js_mailKakunin_title" ></div>
          <table>
            <!-- 差出人 -->
            <tr >
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.sendfrom"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_from">
              </td>
            </tr>
            <!-- 日次 -->
            <tr >
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.date"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_date">
              </td>
            </tr>
            <!-- 宛先 -->
            <tr >
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.from"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 ">
                <div class="js_mailKakunin_to js_mailKakunin-expandable"></div>
              </td>
            </tr>
            <!-- CC -->
            <tr >
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.cc"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 ">
                <div class="js_mailKakunin_cc js_mailKakunin-expandable"></div>
              </td>
            </tr>
            <tr >
              <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.mark"/></th>
              <td class="txt_t fw_b">:</td>
              <td class="pl5 js_mailKakunin_mark">
              </td>
            </tr>
          </table>
          <!-- 添付ファイル -->
          <div class="fs_16 fw_b js_mailKakunin_temp" ></div>
        </div>
      </div>
      <!-- 本文 -->
      <div class="p5 pl20 bor_t1word_b-all tinymce-inherit">
        <htmlframe:write attrClass="w100  js_mailKakunin_body " themePath="<%=selectThemePath %>"></htmlframe:write>
      </div>
    </div>
    <!-- 送信確認テンプレート -->
    <div id="sml_kakunin_body-sosin" class="js_mailKakuninArea-sosin display_none" >
      <!-- メールヘッダ情報 -->
      <div class="p5 pl20 bgC_header3">
        <table>
          <!-- 宛先 -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.from"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_to">
            </td>
          </tr>
          <!-- CC -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.cc"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_cc">
            </td>
          </tr>
          <!-- BCC -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.bcc"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_bcc">
            </td>
          </tr>
          <!-- 件名 -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.subject"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_title">
            </td>
          </tr>
          <!-- 日時 -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.date"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_date">
            </td>
          </tr>
          <!-- マーク -->
          <tr >
            <th class="txt_t fw_b no_w"><gsmsg:write key="cmn.mark"/></th>
            <td class="txt_t fw_b">:</td>
            <td class="pl5 js_mailKakunin_mark">
            </td>
          </tr>
        </table>
        <!-- 添付ファイル -->
        <div class="fs_16 fw_b js_mailKakunin_temp" ></div>
      </div>
      <!-- 本文 -->
      <div class="p5 pl20 bor_t1 word_b-all tinymce-inherit">
        <htmlframe:write attrClass="js_mailKakunin_body w100" themePath="<%=selectThemePath %>"></htmlframe:write>
      </div>

      <!-- 開封確認 -->
      <div class="p5 pl20 bor_t1 js_mailKakunin_opnChk">
      </div>

    </div>
  </div>
</div>

<div class="display_none">

  <div id="sendMailPop" title="">
    <ul class="p0 verAlignMid  w100 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="pl10 dialog_msgbody word_b-all">
        <span id="sendMailPopMsg">
        </span>
      </li>
    </ul>
  </div>

  <div id="messagePop" title="" class="">
    <ul class="p0 mb0 verAlignMid w100 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="pl10 dialog_msgbody word_b-all">
         <span id="messageArea"></span>
      </li>
    </ul>
  </div>

  <div id="delKakuninPop" title="" >
    <ul class="verAlignMid  w100 p0 pt10">
     <li class="" >
       <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
       <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
     </li>
     <li class=" txt_t pl10">
       <gsmsg:write key="sml.sml010.09" />
     </li>
    </ul>
  </div>

  <div id="delMailMsgPop" title="" >
    <ul class="p0 verAlignMid w100 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="pl10 dialog_msgbody word_b-all">
        <span id="delMailMsgArea">
        </span>
      </li>
    </ul>
  </div>

  <div id="revivedMailPop" title="" >
    <ul class="verAlignMid  w100 p0 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="dialog_msgbody word_b-all pl10">
         <span id="revivedMailMsgArea" ></span>
      </li>
    </ul>
  </div>


  <div id="hinaOverWritePop" title="" >
    <ul class="verAlignMid mb0 p0 w100 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class=" txt_t pl10">
         <gsmsg:write key="sml.sml010.10" />
      </li>
    </ul>
  </div>

  <div id="contextAllReadPop" title="" >
    <ul class="verAlignMid mb0 p0 w100 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class=" txt_t pl10">
         <span id="allReadMsgArea"></span>
      </li>
    </ul>
  </div>

  <div id="contextAllNoReadPop" title="" >
    <ul class="verAlignMid  w100 p0 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class=" txt_t pl10">
         <gsmsg:write key="sml.sml010.11" />
      </li>
    </ul>
  </div>

  <div id="warningPop" title="" >
    <ul class="verAlignMid  w100 p0 pt10">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class=" txt_t pl10">
         <gsmsg:write key="sml.sml010.09" />
      </li>
    </ul>

  </div>



  <div id="labelAddPop" title="<gsmsg:write key="wml.wml010.16" />" >

    <table id="labelAddContentArea" class="w100 h100"></table>

  </div>

  <div id="labelDelPop" title="<gsmsg:write key="wml.js.108" />" >

    <table id="labelDelContentArea" class="w100 h100"></table>

  </div>

  <div id="hinagata_pop" title="" >
    <ul class="tabHeader w100">
      <li id="hina_kyotu_tab" class="tabHeader_tab-on border_bottom_none"><gsmsg:write key="cmn.common" /></li>
      <li class="classic-display border_bottom_none wp5 m0"></li>
      <li id="hina_kojin_tab" class="tabHeader_tab-off border_bottom_none"><gsmsg:write key="cmn.individual" /></li>
      <li class="classic-display border_bottom_none wp5 m0"></li>
      <li class="tabHeader_ tabHeader_space border_bottom_none"></li>
    </ul>
    <div id="hinagata_scroll" class="w100 hp300 of_a">
      <bean:define id="hinaList" name="sml010Form" property="sml010HinaList" type="List"/>
      <smail:sml010_hinapop tabId="popHinaKyotu" hinaList="<%=hinaList %>" imgMap="<%=imgMap%>"/>
      <bean:define id="hinaList" name="sml010Form" property="sml010HinaListKjn" type="List" />
      <smail:sml010_hinapop tabId="popHinaKojin" hinaList="<%=hinaList %>" imgMap="<%=imgMap%>" disp="false"/>
    </div>
  </div>

  <div id="loading_pop" title="" >
    <table class="w100 h100">
      <tr>
        <td class="txt_m txt_c">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
          <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
        </td>
      </tr>
    </table>
  </div>
</div>
<div id="sml_markList" class="display_none">
  <div name="<%= markTel %>"><%=imgMap.get(markTel) %></div>
  <div name="<%= markImp %>"><%=imgMap.get(markImp) %></div>
  <div name="<%= markSmaily %>"><%=imgMap.get(markSmaily) %></div>
  <div name="<%= markWorry %>"><%=imgMap.get(markWorry) %></div>
  <div name="<%= markAngry %>"><%=imgMap.get(markAngry) %></div>
  <div name="<%= markSadly %>"><%=imgMap.get(markSadly) %></div>
  <div name="<%= markHart %>"><%=imgMap.get(markHart) %></div>
  <div name="<%= markBeer %>"><%=imgMap.get(markBeer) %></div>
  <div name="<%= markZasetsu %>"><%=imgMap.get(markZasetsu) %></div>
</div>
<div id="sml_markList_mlnon" class="display_none">

  <div name="<%= markTel %>"><img class="btn_classicImg-display mr5 txt_m" src="../common/images/classic/icon_call.png" alt="<%= phone %>"><img class="btn_originalImg-display mr5 txt_m" src="../common/images/original/icon_call.png" alt="<%= phone %>"></div>
  <div name="<%= markImp %>"><img class="btn_classicImg-display mr5 txt_m" src="../common/images/classic/icon_zyuu.png" alt="<%= important %>"><img class="btn_originalImg-display mr5 txt_m" src="../common/images/original/icon_zyuu.png" alt="<%= important %>"></div>
  <div name="<%= markSmaily %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_face01.png" alt="<%= smile %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_face_smil.png" alt="<%= smile %>"></div>
  <div name="<%= markWorry %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_face02.png" alt="<%= worry %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_face_confu.png" alt="<%= worry %>"></div>
  <div name="<%= markAngry %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_face03.png" alt="<%= angry %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_face_angry.png" alt="<%= angry %>"></div>
  <div name="<%= markSadly %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_face04.png" alt="<%= sad %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_face_sad.png" alt="<%= sad %>"></div>
  <div name="<%= markBeer %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_beer.png" alt="<%= beer %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_beer.png" alt="<%= beer %>"></div>
  <div name="<%= markHart %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_hart.png" alt="<%= hart %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_hart.png" alt="<%= hart %>"></div>
  <div name="<%= markZasetsu %>"><img class="btn_classicImg-display mr5 txt_m" src="../smail/images/classic/icon_zasetsu.png" alt="<%= tired %>"><img class="btn_originalImg-display mr5 txt_m" src="../smail/images/original/icon_zasetu.png" alt="<%= tired %>"></div>
</div>

<span id="tooltip_area"></span>

<logic:notEqual name="sml010Form" property="sml020webmail" value="1">
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</logic:notEqual>

</html:form>
<smail:accountSelector name="sml010Form" property="sml010AtesakiUI"/>

<div id='shareDialog' title="<gsmsg:write key="cmn.share" />" class="display_n">
  <iframe id="shareFrame" name=shareFrameName class="w100 h100 border_none"></iframe>
</div>

<form id="tempSendForm">
</form>

<iframe id="sml010Export" src="" class="display_n" />

</body>
</html:html>