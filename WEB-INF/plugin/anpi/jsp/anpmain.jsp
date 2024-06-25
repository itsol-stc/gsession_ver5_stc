<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.anp010.01" /></title>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>
<body>
  <html:form action="/anpi/anpmain">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>
    <logic:notEmpty name="anpMainForm" property="anpMainAnpiSid">
      <table class="w100 table-left no_w mb0">
        <tr>
          <th class="table_title-color w100 bgC_header1" colspan="3">
            <img class=mainPlugin_icon src="../anpi/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="anp.plugin"/>">
            <a class="main_pluginTitle th_hovu" href="../anpi/anp010.do">
              <span class="pr5"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp010.06" />
            </a>
          </th>
        </tr>
        <logic:notEmpty name="anpMainForm" property="anpMainState">
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="anp.date.send" />
            </th>
            <td colspan="2">
              <bean:write name="anpMainForm" property="anpMainState.haisinDate" />
            </td>
          </tr>
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="anp.date.resend" />
            </th>
            <td colspan="2">
              <bean:write name="anpMainForm" property="anpMainState.resendDate" />
            </td>
          </tr>
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="anp.date.end" />
            </th>
            <td  colspan="2">
              <bean:write name="anpMainForm" property="anpMainState.lastDate" />
            </td>
          </tr>
          <tr>
            <th class="txt_c no_w">
              <gsmsg:write key="anp.ans.state" />
            </th>
            <td colspan="2">
              <bean:write name="anpMainForm" property="anpMainState.replyState" />
            </td>
          </tr>
          <tr>
            <th class="txt_c no_w" rowspan="3">
              <gsmsg:write key="anp.state" />
            </th>
            <th class="w10 fw_n txt_l no_w">
              <gsmsg:write key="anp.jokyo.good" />
            </th>
            <td class="txt_r">
              <bean:write name="anpMainForm" property="anpMainState.jokyoGood" />
            </td>
          </tr>
          <tr>
            <th class="w10 fw_n no_w">
              <gsmsg:write key="anp.jokyo.keisyo" />
            </th>
            <td class="txt_r">
              <bean:write name="anpMainForm" property="anpMainState.jokyoKeisyo" />
            </td>
          </tr>
          <tr>
            <th class="w10 fw_n no_w">
              <gsmsg:write key="anp.jokyo.jusyo" />
            </th>
            <td class="txt_r">
              <bean:write name="anpMainForm" property="anpMainState.jokyoJusyo" />
            </td>
          </tr>
          <tr>
            <th class="txt_c no_w" rowspan="2">
              <gsmsg:write key="anp.syusya" />
            </th>
            <th class="w10 txt_l fw_n no_w">
              <gsmsg:write key="anp.syusya.ok2" />
            </th>
            <td class="txt_r">
              <bean:write name="anpMainForm" property="anpMainState.syusyaOk" />
            </td>
          </tr>
          <tr>
            <th class="w10 fw_n no_w">
              <gsmsg:write key="anp.syusya.no" />
            </th>
            <td class="txt_r">
              <bean:write name="anpMainForm" property="anpMainState.syusyaNo" />
            </td>
          </tr>
        </logic:notEmpty>
      </table>
    </logic:notEmpty>
  </html:form>
</body>
</html:html>