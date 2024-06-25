<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <title>GROUPSESSION<gsmsg:write key="wml.wml100.02" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../circular/js/cir180.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>

<body>

  <html:form action="/circular/cir180">

    <input type="hidden" name="CMD" value="">

    <html:hidden property="cirCmdMode" />
    <html:hidden property="cirViewAccount" />
    <html:hidden property="cirAccountMode" />
    <html:hidden property="cirAccountSid" />
    <html:hidden property="cir010cmdMode" />
    <html:hidden property="cir010orderKey" />
    <html:hidden property="cir010sortKey" />
    <html:hidden property="cir010pageNum1" />
    <html:hidden property="cir010pageNum2" />
    <html:hidden property="cir010SelectLabelSid" />
    <html:hidden property="backScreen" />

    <logic:notEmpty name="cir180Form" property="accountList" scope="request">
      <logic:iterate id="sort" name="cir180Form" property="accountList" scope="request">
        <input type="hidden" name="cir180sortLabel" value="<bean:write name="sort" property="acValue" />">
      </logic:iterate>
    </logic:notEmpty>

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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cir.cir180.1" />
        </li>
        <li>
          <div>
            <logic:equal name="cir180Form" property="cir180MakeAcntHnt" value="0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="accountConf(0, 0);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('psnTool');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper w80 mrl_auto">
      <!-- 上・下ボタン -->
      <div class="txt_l">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" onClick="buttonPush('upFilterData');">
          <gsmsg:write key="cmn.up" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onClick="buttonPush('downFilterData');">
          <gsmsg:write key="cmn.down" />
        </button>
      </div>
      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="w100 table-top">
        <tr>
          <th class="txt_c"></th>
          <th class="w20">
            <gsmsg:write key="wml.96" />
          </th>
          <th class="w70">
            <gsmsg:write key="cmn.memo" />
          </th>
          <th class="w5">
            <gsmsg:write key="cmn.edit" />
          </th>
          <th class="w5">
          </th>
        </tr>
        <logic:iterate id="acuntData" name="cir180Form" property="accountList">
          <bean:define id="acValue" name="acuntData" property="acValue" />
          <bean:define id="mukoUserClass" value="" />
          <logic:equal name="acuntData" property="usrUkoFlg" value="1">
            <bean:define id="mukoUserClass" value="mukoUser" />
          </logic:equal>
          <tr>
            <td class="txt_c cursor_p js_tableTopCheck">
              <html:radio property="cir180sortAccount" value="<%=String.valueOf(acValue)%>" />
            </td>
            <td>
              <span class="<%=mukoUserClass%> no_w">
                <bean:write name="acuntData" property="accountName" />
              </span>
            </td>
            <td>
              <span>
                <bean:write name="acuntData" property="accountBiko" />
              </span>
            </td>
            <td class="txt_c">
              <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.edit" />" onClick="return accountEdit(1, <bean:write name="acuntData" property="accountSid" />);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.edit" />
              </button>
            </td>
            <td class="txt_c">
              <button type="button" class="baseBtn no_w" value="<gsmsg:write key="cmn.label" />" onClick="confLabel(<bean:write name="acuntData" property="accountSid" />);">
              <gsmsg:write key="cmn.label" />
              </button>
            </td>
          </tr>
        </logic:iterate>
      </table>
      </td>
      </tr>
      </table>
      </td>
      </tr>
      </table>
    </div>
  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>