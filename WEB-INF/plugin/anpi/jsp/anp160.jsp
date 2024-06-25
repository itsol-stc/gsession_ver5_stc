<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.plugin" /></title>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp160.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/anpi/anp160">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="anpiSid" />
    <html:hidden property="anp160ProcMode" />
    <html:hidden property="anp160GrpSid" />
    <html:hidden property="anp160NowPage" />
    <html:hidden property="anp160DspPage1" />
    <html:hidden property="anp160DspPage2" />

    <div class="pageTitle">
      <img class="header_pluginImg-classic" src="../anpi/images/classic/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
      <img class="header_pluginImg" src="../anpi/images/original/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
      <gsmsg:write key="anp.plugin" />
      <span class="pageTitle_subFont">
        <gsmsg:write key="anp.anp060kn.01" />
        <gsmsg:write key="anp.send.dest" />
      </span>
    </div>

    <div class="wrapper">
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.group" />
          </th>
          <td class="w75">
            <bean:write name="anp160Form" property="anp160DispGrpName" />
          </td>
        </tr>
      </table>

      <!-- ページング -->
      <bean:size id="pageCount" name="anp160Form" property="anp160PageLabel" scope="request" />
      <logic:greaterThan name="pageCount" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('anp160pageLast');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="anp160DspPage1" onchange="changePage(this);">
            <html:optionsCollection name="anp160Form" property="anp160PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('anp160pageNext');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <table class="table-left mt0 mt5 mb0 mb5">
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.send.dest" />
          </th>
          <td>
            <table>
              <logic:iterate id="urBean" name="anp160Form" property="anp160DspSenderList" indexId="idx">
                <tr class="border_none">
                  <td class="border_none p0">
                    <bean:define id="mukoUserClass" value="" />
                    <logic:equal value="1" name="urBean" property="anp160usrUkoFlg">
                      <bean:define id="mukoUserClass" value="mukoUser" />
                    </logic:equal>
                    <span class="<%=mukoUserClass%>">
                      <bean:write name="urBean" property="anp160Name" />
                    </span>
                  <td class="border_none p0 pl5">
                    <logic:notEqual name="urBean" property="anp160MailFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.anp160.Anp160Form.MAIL_NOSET_USER_YES)%>">
                      <img class="btn_classicImg-display" src="../anpi/images/classic/icon_nomail.gif" border="0" alt="!">
                      <img class="btn_originalImg-display" src="../anpi/images/original/icon_senderr.png" border="0" alt="!">
                    </logic:notEqual>
                  </td>
                </tr>
              </logic:iterate>
            </table>
            <logic:equal name="anp160Form" property="anp160NosetMailFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.anp160.Anp160Form.MAIL_NOSET_USER_YES)%>">
              <div class="txt_r">
                <span class="fs_13 btn_classicImg-display">
                  <img src="../anpi/images/classic/icon_nomail.gif" border="0" alt="!" class="txt_m">：
                  <gsmsg:write key="anp.anp060kn.08" />
                </span>
                <span class="fs_13 btn_originalImg-display">
                  <img src="../anpi/images/original/icon_senderr.png" border="0" alt="!" class="txt_m">：
                  <gsmsg:write key="anp.anp060kn.08" />
                </span>
              </div>
            </logic:equal>
          </td>
        </tr>
      </table>

      <!-- ページング -->
      <bean:size id="pageCount" name="anp160Form" property="anp160PageLabel" scope="request" />
      <logic:greaterThan name="pageCount" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('anp160pageLast');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="anp160DspPage2" onchange="changePage(this);">
            <html:optionsCollection name="anp160Form" property="anp160PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('anp160pageNext');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>
    </div>
    <!-- 閉じるボタン -->
    <div class="txt_c">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    </div>
  </html:form>
</body>
</html:html>