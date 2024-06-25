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
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html;" charset="UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.plugin" /> <gsmsg:write key="cmn.display.settings" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/anpi/anp040">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp040UserSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.preferences2" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="cmn.display.settings" />
        </li>
        <li>
          <!-- ボタン -->
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp040excute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp040back');">
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
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.main.view2" />
          </th>
          <td class="w75">
            <div class="verAlignMid">
              <html:radio name="anp040Form" property="anp040MainDispFlg" value="0" styleId="anp040MainDispFlg_01">
                <label for="anp040MainDispFlg_01" class="mr5">
                  <gsmsg:write key="cmn.dont.show" />
                </label>
              </html:radio>
              <html:radio name="anp040Form" styleClass="ml10" property="anp040MainDispFlg" value="1" styleId="anp040MainDispFlg_02">
                <label for="anp040MainDispFlg_02">
                  <gsmsg:write key="cmn.display.ok" />
                </label>
              </html:radio>
            </div>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="anp.number.display" />
          </th>
          <td>
            <gsmsg:write key="anp.anp040.01" />
            <br>
            <html:select name="anp040Form" property="anp040SelectDispCnt">
              <html:optionsCollection name="anp040Form" property="anp040DsipCntList" value="value" label="label" />
            </html:select>
            <span class="mr10">
              <gsmsg:write key="cmn.number" />
            </span>
          </td>
        </tr>
        <tr>
          <th>
            <gsmsg:write key="anp.anp040.02" />
          </th>
          <td>
            <gsmsg:write key="anp.anp040.03" />
            <br>
            <logic:notEmpty name="anp040Form" property="anp040GroupLabel" scope="request">
              <html:select name="anp040Form" property="anp040SelectGroupSid">
                <logic:notEmpty name="anp040Form" property="anp040GroupLabel" scope="request">
                  <logic:iterate id="gpBean" name="anp040Form" property="anp040GroupLabel" scope="request">
                    <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                    <logic:equal name="gpBean" property="styleClass" value="0">
                      <html:option value="<%=gpValue%>">
                        <bean:write name="gpBean" property="label" />
                      </html:option>
                    </logic:equal>
                    <logic:notEqual name="gpBean" property="styleClass" value="0">
                      <html:option styleClass="select_mygroup-bgc" value="<%=gpValue%>">
                        <bean:write name="gpBean" property="label" />
                      </html:option>
                    </logic:notEqual>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
              <!-- グループ選択ボタン-->
              <button class="iconBtn-border ml5" type="button" id="groupBtn" value="" onClick="openGroupWindow(this.form.anp040SelectGroupSid, 'anp040SelectGroupSid', '1');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </logic:notEmpty>
          </td>
        </tr>
      </table>
      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp040excute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp040back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>