<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../circular/js/cir130.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../circular/js/ciraccountsel.js?<%= GSConst.VERSION_PARAM %>"></script>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/default.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../circular/css/circular.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION <gsmsg:write key="cir.23" /></title>
</head>

<body onload="changeEnableDisable();">
<html:form action="/circular/cir130">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cirViewAccount" />
<html:hidden property="cirAccountMode" />
<html:hidden property="cirAccountSid" />

<html:hidden property="cir010cmdMode" />
<html:hidden property="cir010orderKey" />
<html:hidden property="cir010sortKey" />
<html:hidden property="cir010pageNum1" />
<html:hidden property="cir010pageNum2" />
<html:hidden property="cir010SelectLabelSid"/>

<html:hidden property="cir130AccountSid" />
<html:hidden property="cir130AccountName" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->

<table align="center"  cellpadding="5" cellspacing="0" border="0" width="100%">
<tr>
<td width="100%" align="center">
  <table cellpadding="0" cellspacing="0" border="0" width="70%">
  <tr>
  <td>
    <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
    <td width="0%"><img src="../common/images/header_ktool_01.gif" border="0" alt=""></td>
    <td width="0%" class="header_ktool_bg_text2" align="left" valign="middle" nowrap><span class="settei_ttl"><gsmsg:write key="cmn.preferences2" /></span></td>
    <td width="100%" class="header_ktool_bg_text2">[ <gsmsg:write key="cir.23" /> ]</td>
    <td width="0%"><img src="../common/images/header_ktool_05.gif" border="0" alt=""></td>
    </tr>
    </table>

    <table cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
    <td width="50%"></td>
    <td width="0%"><img src="../common/images/header_glay_1.gif" border="0" alt=""></td>
    <td class="header_glay_bg" width="50%">
      <input type="button" value="OK" class="btn_ok1" onClick="buttonPush('init_change_kakunin');">
      <input type="button" value="<gsmsg:write key="cmn.back.preferences" />" class="btn_back_n3" onClick="buttonPush('backKtool');">
    </td>
    <td width="0%"><img src="../common/images/header_glay_3.gif" border="0" alt=""></td>
    </tr>
    </table>
    <logic:messagesPresent message="false">
      <table width="100%">
      <tr>
        <td align="left"><span class="TXT02"><html:errors/></span></td>
      </tr>
      </table>
    </logic:messagesPresent>
    <img src="../common/images/spacer.gif" width="1px" height="10px" border="0">

    <table width="100%" class="tl0" border="0" cellpadding="5">

    <tr>
    <td>
    <table cellpadding="10" cellspacing="0" border="0" width="100%" class="tl_u2">

    <tr>
    <th class="table_bg_A5B4E1" width="0%" nowrap><span class="text_bb1"><gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" /></span></th>
    <td align="left" class="td_wt" width="100%">
      <div>
        <html:radio name="cir130Form" property="cir130SelKbn" styleClass="accountSelKbn" styleId="cir130SelKbn_0"  value="0"/><span class="text_base"><label for="cir130SelKbn_0"><gsmsg:write key="wml.wml010.12" /></label></span>&nbsp;
        <html:radio name="cir130Form" property="cir130SelKbn" styleClass="accountSelKbn" styleId="cir130SelKbn_1"  value="1"/><span class="text_base"><label for="cir130SelKbn_1"><gsmsg:write key="cmn.all" /></label></span>&nbsp;
      </div>
      <div id="accountSelArea" class="account_name_area"><span id="selAccountNameArea"><bean:write name="cir130Form" property="cir130AccountName" /></span>　<input id="accountSelBtn" type="button" name="btn_add" class="btn_base1" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" ></div>
    </td>
    </tr>

    <tr>
    <td valign="middle" align="left" class="table_bg_A5B4E1" width="20%" nowrap id="cirEditArea1" rowspan="2">
      <span class="text_bb1"><gsmsg:write key="cir.cir040.2" /></span>
    </td>
    </tr>

    <logic:equal name="cir130Form" property="cir130memoKbn" value="0">
      <html:hidden property="cir130memoPeriod" />
      <!-- メモ欄修正区分 -->
      <tr>
      <td align="left" class="td_wt" nowrap><span class="text_base"><gsmsg:write key="cir.cir040.3" />&nbsp;&nbsp;</span>
        <html:radio property="cir130memoKbn" styleId="memoNg" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_NO) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoNg"><span class="text_base"><gsmsg:write key="cmn.not" /></span>&nbsp;&nbsp;</label>
        <html:radio property="cir130memoKbn" styleId="memoOk" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_YES) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoOk"><span class="text_base"><gsmsg:write key="cmn.accepted" /></span></label>
      </td>
      </tr>
    </logic:equal>

    <logic:equal name="cir130Form" property="cir130memoKbn" value="1">
      <!-- メモ欄修正区分 -->
      <tr>
      <td align="left" class="td_wt" nowrap><span class="text_base"><gsmsg:write key="cir.cir040.3" />&nbsp;&nbsp;</span>
        <html:radio property="cir130memoKbn" styleId="memoNg" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_NO) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoNg"><span class="text_base"><gsmsg:write key="cmn.not" /></span>&nbsp;&nbsp;</label>
        <html:radio property="cir130memoKbn" styleId="memoOk" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_YES) %>" onclick="buttonPush('memoKbnChange');" /><label for="memoOk"><span class="text_base"><gsmsg:write key="cmn.accepted" /></span></label>
      <!-- メモ欄修正期限設定 -->
      <br>
      <br>
      <span class="text_base"><gsmsg:write key="cir.54" /></span>
      <br>

        <html:radio property="cir130memoPeriod" styleId="today" value="1" /><label for="today"><span class="text_base"><gsmsg:write key="cmn.today" /></span></label>&nbsp;&nbsp;
        <html:radio property="cir130memoPeriod" styleId="1weeks" value="0" /><label for="1weeks"><span class="text_base">1<gsmsg:write key="cmn.weeks" /></span></label>&nbsp;&nbsp;
        <html:radio property="cir130memoPeriod" styleId="2weeks" value="2" /><label for="2weeks"><span class="text_base">2<gsmsg:write key="cmn.weeks" /></span></label>&nbsp;&nbsp;
        <html:radio property="cir130memoPeriod" styleId="months" value="3" /><label for="months"><span class="text_base"><gsmsg:write key="cmn.months" arg0="1" /></span></label>

      </td>
      </tr>
    </logic:equal>


    <!-- 回覧先確認編集権限区分 -->
    <tr>
    <td valign="middle" align="left" class="table_bg_A5B4E1" width="20%" nowrap id="cirEditArea1" rowspan="2">
      <span class="text_bb1"><gsmsg:write key="cir.cir030.3" /></span>
    </td>
    </tr>


    <!-- 回覧先確認状況区分 -->
    <tr>
    <td align="left" class="td_wt" nowrap>
      <html:radio name="cir130Form" property="cir130show" styleId="showPub" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC) %>" /><label for="showPub"><span class="text_base"><gsmsg:write key="cmn.public" /></span>&nbsp;&nbsp;</label>
      <html:radio name="cir130Form" property="cir130show" styleId="showPri" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PRIVATE) %>" /><label for="showPri"><span class="text_base"><gsmsg:write key="cmn.private" /></span></label>
    </td>
    </tr>



    </table>

  </td>
  </tr>




    </table>

    <img src="../common/images/spacer.gif" width="1px" height="10px" border="0">

    <table cellpadding="0" cellpadding="5" border="0" width="100%">
    <tr>
    <td align="right">
      <input type="button" value="OK" class="btn_ok1" onClick="buttonPush('init_change_kakunin');">
      <input type="button" value="<gsmsg:write key="cmn.back.preferences" />" class="btn_back_n3" onClick="buttonPush('backKtool');">
    </td>
    </tr>
    </table>

  </td>
  </tr>
  </table>
</td>
</tr>
</table>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

<div id="accountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" style="display:none">
  <input type="hidden" id="selAccountElm" value="cir130AccountSid" />
  <input type="hidden" id="selAccountSubmit" value="true" />
  <input type="hidden" id="cir150user" value="<bean:write name="cir130Form" property="cirViewUser" />" />
  <div style="height:460px;overflow-y:auto;">
  <table width="100%" height="100%">
    <tr>
      <td id="accountListArea" valign="top"></td>
    </tr>
  </table>
  </div>
</div>

</html:form>
</body>
</html:html>