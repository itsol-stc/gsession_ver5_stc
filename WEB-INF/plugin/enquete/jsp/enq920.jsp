<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>

<!DOCTYPE html>

<html:html lang="true">
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Content-Script-Type" content="text/javascript">
<META http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enquete.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../enquete/js/enq920.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../enquete/css/enquete.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body class="body_03">
  <html:form action="/enquete/enq920">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="cmd" />
    <html:hidden property="backScreen" />

    <!-- 検索条件hidden -->
    <%@ include file="/WEB-INF/plugin/enquete/jsp/enq010_hiddenParams.jsp"%>

    <logic:notEmpty name="enq920Form" property="enq010priority">
      <logic:iterate id="svPriority" name="enq920Form" property="enq010priority">
        <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq920Form" property="enq010status">
      <logic:iterate id="svStatus" name="enq920Form" property="enq010status">
        <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq920Form" property="enq010svPriority">
      <logic:iterate id="svPriority" name="enq920Form" property="enq010svPriority">
        <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq920Form" property="enq010svStatus">
      <logic:iterate id="svStatus" name="enq920Form" property="enq010svStatus">
        <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq920Form" property="enq010statusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq920Form" property="enq010statusAnsOver">
        <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>
    <logic:notEmpty name="enq920Form" property="enq010svStatusAnsOver">
      <logic:iterate id="svStatusAnsOver" name="enq920Form" property="enq010svStatusAnsOver">
        <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="enq920Form" property="enq920TypeListToList">
      <logic:iterate id="typeData" name="enq920Form" property="enq920TypeListToList" indexId="idx">
        <input type="hidden" name="enq920TypeList[<%=String.valueOf(idx.intValue())%>].emnCnt" value='<bean:write name="typeData" property="emnCnt"/>'>
        <input type="hidden" name="enq920TypeList[<%=String.valueOf(idx.intValue())%>].emnResEnd" value='<bean:write name="typeData" property="emnResEnd"/>'>
        <input type="hidden" name="enq920TypeList[<%=String.valueOf(idx.intValue())%>].emnOpenEnd" value='<bean:write name="typeData" property="emnOpenEnd"/>'>
      </logic:iterate>
    </logic:notEmpty>

    <div id="saveTbl">
      <logic:notEmpty name="enq920Form" property="enq920DelListToList">
        <logic:iterate id="delList" name="enq920Form" property="enq920DelListToList" indexId="idx">
          <div class="save">
            <input type="hidden" name="enq920DelList[<%=String.valueOf(idx.intValue())%>].etpSid" value='<bean:write name="delList" property="etpSid"/>'>
          </div>
        </logic:iterate>
      </logic:notEmpty>
    </div>

    <!-- header -->
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
        <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="enq.plugin" /></span><gsmsg:write key="enq.enq900.03" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('enq920ok');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq920back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <div class="component_bothEnd">
        <!-- 上・下ボタン -->
        <div>
          <button type="button" class="baseBtn" id="upList" value="<gsmsg:write key="cmn.up" />">
            <gsmsg:write key="cmn.up" />
          </button>
          <button type="button" class="baseBtn" id="downList" value="<gsmsg:write key="cmn.down" />">
            <gsmsg:write key="cmn.down" />
          </button>
        </div>
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key='enq.02' />" onclick="addRow();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key='enq.02' />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key='enq.02' />">
            <gsmsg:write key='enq.02' />
          </button>
        </div>
      </div>

      <table cellpadding="10" cellspacing="0" border="0" class="t10 w100 table-top">
        <tr>
          <th class="w5 no_w"></th>
          <th class="w40 no_w">
            <span>
              <gsmsg:write key="enq.enq920.01" />
            </span>
          </th>
          <th class="w10 no_w">
            <span>
              <gsmsg:write key="enq.enq920.02" />
            </span>
          </th>
          <th class="w15 no_w">
            <span>
              <gsmsg:write key="enq.enq920.03" />
            </span>
          </th>
          <th class="w15">
            <span>
              <gsmsg:write key="enq.enq920.04" />
            </span>
          </th>
          <th class="w10 no_w">
            <span>
              <gsmsg:write key="cmn.delete" />
            </span>
          </th>
        </tr>
        <!-- アンケート種類 -->
        <tbody id="typeTbl">
          <logic:notEmpty name="enq920Form" property="enq920TypeList">
            <%
              String[] paramName = { "lineNo", "etpSid", "etpDspSeq", "etpName", "emnCnt", "emnResEnd",
                        "emnOpenEnd" };
            %>
            <logic:iterate id="subForm" name="enq920Form" property="enq920TypeList" indexId="lineIdx">
              <%
                String lineNo = String.valueOf(lineIdx.intValue());
              %>
              <%
                String lineFrmName = "enq920TypeList[" + lineNo + "].";
              %>
              <%
                String trNo = "typeRow_" + lineNo;
              %>
              <%
                String radioNo = "radioNo_" + lineNo;
              %>
              <tr class="bgC_tableCell" id="<%=trNo%>">
                <input type="hidden" name="rowIdx" value="<%=lineNo%>">
                <html:hidden property="<%=lineFrmName + paramName[1]%>" />
                <html:hidden property="<%=lineFrmName + paramName[2]%>" styleClass="etpDspSec" />
                <td class="txt_c txt_m js_tableTopCheck cursor_p">
                  <input type="radio" name="nowRow" id="<%=radioNo%>" value="<%=lineNo%>">
                </td>
                <td class="txt_c txt_m no_w">
                  <html:text property="<%=lineFrmName + paramName[3]%>" maxlength="100" styleClass="w100" />
                </td>
                <td class="txt_c txt_m no_w">
                  <logic:greaterEqual name="subForm" property="emnCnt" value="0">
                    <bean:write name="subForm" property="emnCnt" />
                    <gsmsg:write key="cmn.number" />
                  </logic:greaterEqual>
                </td>
                <td class="txt_c txt_m no_w">
                  <logic:notEmpty name="subForm" property="emnResEnd">
                    <bean:write name="subForm" property="emnResEnd" />
                    <gsmsg:write key="enq.03" />
                  </logic:notEmpty>

                </td>
                <td class="txt_c txt_m no_w">
                  <logic:notEmpty name="subForm" property="emnOpenEnd">
                    <bean:write name="subForm" property="emnOpenEnd" />
                    <gsmsg:write key="enq.03" />
                  </logic:notEmpty>
                </td>
                <td class="txt_c txt_m no_w">
                  <logic:lessEqual name="subForm" property="emnCnt" value="0">
                    <button type="button" class="baseBtn" name="enqTypeDelBtn" value="<gsmsg:write key='cmn.delete' />" title="<gsmsg:write key='cmn.delete' />" id='<%=lineNo%>:<bean:write name="subForm" property="etpSid"/>'>
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key='cmn.delete' />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key='cmn.delete' />">
                      <gsmsg:write key='cmn.delete' />
                    </button>
                  </logic:lessEqual>
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </tbody>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('enq920ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('enq920back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>

  <!-- アンケート種類削除ダイアログ -->
  <div id="dialogDeleteOk" title="アンケート種類名削除確認" class="display_n">
    <table class="w100 h100">
      <tr>
        <td class="w15">
           <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
        </td>
        <td class="pl5 w85">
          種類名を削除します。よろしいですか？
        </td>
      </tr>
    </table>
  </div>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>