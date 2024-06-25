<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp095.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<html:form action="/nippou/ntp095" styleClass="js_smailSetting">
<html:hidden property="ntp095InitFlg" />
<html:hidden property="ntp095NtpDspKbn" />
<html:hidden property="ntp095CmtDspKbn" />
<html:hidden property="ntp095GoodDspKbn" />
<input type="hidden" name="plgId" value="ntp">
<div class="wrapper w100 mrl_auto">
  <table class="table-left w100">
    <logic:equal name="ntp095Form" property="ntp095NtpDspKbn" value="1">
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.88" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="ntp095Form" styleId="js_ntp095Smail_02" property="ntp095Smail" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SMAIL_NOT_USE) %>" />
            <label for="js_ntp095Smail_02"><gsmsg:write key="cmn.dont.notify" /></label>
            <html:radio name="ntp095Form" styleId="js_ntp095Smail_01" styleClass="ml10" property="ntp095Smail" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SMAIL_USE) %>" />
            <label for="js_ntp095Smail_01"><gsmsg:write key="cmn.notify" /></label>
          </div>
        </div>
        <div class="settingForm_separator js_ntpNoticeUsrArea">
          <div class="fs_13">
            <gsmsg:write key="ntp.114" />
          </div>
          <div class="mt5">
            <ui:usrgrpselector name="ntp095Form" property="ntp095memberUI" styleClass="hp215" />
          </div>
        </div>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="ntp095Form" property="ntp095CmtDspKbn" value="1">
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.89" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ntp095Form" styleId="ntp095CmtSmail_02" property="ntp095CmtSmail" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SMAIL_NOT_USE) %>" />
          <label for="ntp095CmtSmail_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="ntp095Form" styleId="ntp095CmtSmail_01" styleClass="ml10" property="ntp095CmtSmail" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SMAIL_USE) %>" />
          <label for="ntp095CmtSmail_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
    </logic:equal>
    <logic:equal name="ntp095Form" property="ntp095GoodDspKbn" value="1">
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.9" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="ntp095Form" styleId="ntp095GoodSmail_02" property="ntp095GoodSmail" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SMAIL_NOT_USE) %>" />
          <label for="ntp095GoodSmail_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="ntp095Form" styleId="ntp095GoodSmail_01" styleClass="ml10" property="ntp095GoodSmail" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SMAIL_USE) %>" />
          <label for="ntp095GoodSmail_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
    </logic:equal>    
  </table>
</div>
</html:form>