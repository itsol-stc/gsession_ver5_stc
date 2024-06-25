<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
</head>
<body>
  <html:form action="/anpi/anp150kn">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="anp150PassKbn" />
    <html:hidden property="anp150TargetKbn" />
    <html:hidden property="anp150SelectGroupSid" />
    <html:hidden property="anp150SelectMail" />
    <html:hidden property="anp150OtherMail" />
    <html:hidden property="anp150UpdateFlg" />
    <logic:notEmpty name="anp150knForm" property="anp150TargetList" scope="request">
      <logic:iterate id="ulBean" name="anp150knForm" property="anp150TargetList" scope="request">
        <bean:define id="userSid" name="ulBean" type="java.lang.String" />
        <html:hidden property="anp150TargetList" value="<%= userSid %>" />
      </logic:iterate>
    </logic:notEmpty>
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp150kn.01" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('anp150knexcute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
              <gsmsg:write key="cmn.final" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp150knback');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <div class="txt_l">
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>
      </div>
      <table class="table-left">
        <!-- 対象 -->
        <tr>
          <th class="w20">
            <gsmsg:write key="cmn.target" />
          </th>
          <td class="w80">
            <div class="cl_fontWarn fw_b">
              <gsmsg:write key="anp.anp150kn.02" />
            </div>
            <logic:equal name="anp150knForm" property="anp150TargetKbn" value="<%= String.valueOf(jp.groupsession.v2.anp.anp150.Anp150Form.TAISYO_ALL) %>">
              <gsmsg:write key="cmn.alluser" />
            </logic:equal>
            <logic:notEqual name="anp150knForm" property="anp150TargetKbn" value="<%= String.valueOf(jp.groupsession.v2.anp.anp150.Anp150Form.TAISYO_ALL) %>">
              <logic:notEmpty name="anp150knForm" property="anp150knOkUsrList">
                <logic:iterate id="usrName" name="anp150knForm" property="anp150knOkUsrList">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal value="1" name="usrName" property="usrUkoFlg">
                    <bean:define id="mukoUserClass" value="mukoUserOption" />
                  </logic:equal>
                  <div class="<%= mukoUserClass %>">
                    <bean:write name="usrName" property="label" />
                  </div>
                </logic:iterate>
              </logic:notEmpty>
            </logic:notEqual>
            <div>
              (<bean:write name="anp150knForm" property="anp150knOkUsrNum" />&nbsp;<gsmsg:write key="cmn.user" />)
            </div>
            <!-- 対象外ユーザ -->
            <logic:notEmpty name="anp150knForm" property="anp150knCutUsrList">
              <div class="cl_fontWarn fw_b mt20">
                <gsmsg:write key="anp.anp150kn.03" />
              </div>
              <logic:iterate id="cutUsrName" name="anp150knForm" property="anp150knCutUsrList">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal value="1" name="cutUsrName" property="usrUkoFlg">
                  <bean:define id="mukoUserClass" value="mukoUserOption" />
                </logic:equal>
                <div class="<%= mukoUserClass %>">
                  <bean:write name="cutUsrName" property="label" />
                </div>
              </logic:iterate>
              <div>
                (<bean:write name="anp150knForm" property="anp150knCutUsrNum" />&nbsp;<gsmsg:write key="cmn.user" />)
              </div>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w20">
            <gsmsg:write key="anp.anp150.03" />
          </th>
          <td class="w80">
            <logic:equal name="anp150knForm" property="anp150SelectMail" value="0">
              <gsmsg:write key="anp.anp150.07" />
              <span class="ml20">
                <bean:write name="anp150knForm" property="anp150OtherMail" />
              </span>
            </logic:equal>
            <logic:equal name="anp150knForm" property="anp150SelectMail" value="1">
              <gsmsg:write key="cmn.mailaddress1" />
            </logic:equal>
            <logic:equal name="anp150knForm" property="anp150SelectMail" value="2">
              <gsmsg:write key="cmn.mailaddress2" />
            </logic:equal>
            <logic:equal name="anp150knForm" property="anp150SelectMail" value="3">
              <gsmsg:write key="cmn.mailaddress3" />
            </logic:equal>
          </td>
        </tr>
        <!-- 上書き -->
        <tr>
          <th class="w20">
            <gsmsg:write key="cmn.overwrite" />
          </th>
          <td class="w80">
            <logic:equal name="anp150knForm" property="anp150UpdateFlg" value="1">
              <gsmsg:write key="anp.anp150kn.04" />
            </logic:equal>
            <logic:notEqual name="anp150knForm" property="anp150UpdateFlg" value="1">
              <gsmsg:write key="anp.anp150kn.05" />
            </logic:notEqual>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.final" />" class="baseBtn" onClick="buttonPush('anp150knexcute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp150knback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>
</html:html>