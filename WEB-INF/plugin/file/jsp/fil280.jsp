<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
  String accessOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.ACCESS_KBN_OFF);
  String accessOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.ACCESS_KBN_ON);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.143" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil280.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form styleId="fil280Form" action="/file/fil280">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="cmnMode" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="backDsp" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil030SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil010DspCabinetKbn" />
    <html:hidden property="filSearchWd" />

    <html:hidden property="fil280svKeyword" />
    <html:hidden property="fil280svGroup" />
    <html:hidden property="fil280svUser" />
    <html:hidden property="fil280searchFlg" />

    <logic:notEmpty name="fil280Form" property="fil040SelectDel" scope="request">
      <logic:iterate id="del" name="fil280Form" property="fil040SelectDel" scope="request">
        <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="fil280Form" property="fil010SelectDelLink" scope="request">
      <logic:iterate id="del" name="fil280Form" property="fil010SelectDelLink" scope="request">
        <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->

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
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.filekanri" /></span><gsmsg:write key="fil.143" />
        </li>
        <li>
          <div>
            <logic:notEmpty name="fil280Form" property="fil280cabinetList">
              <button type="button" class="baseBtn" value="<gsmsg:write key="fil.fil220.1" />" onclick="CabinetDetailMulti();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="fil.fil220.1" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="fil.fil220.1" />">
                <gsmsg:write key="fil.fil220.1" />
              </button>
            </logic:notEmpty>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil280back');">
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
        <html:text name="fil280Form" property="fil280keyword" maxlength="50" styleClass="wp150" />
        <button type="button" onclick="buttonPush('fil280search');" class="baseBtn mr20" value="<gsmsg:write key="cmn.search" />">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png">
          <gsmsg:write key="cmn.search" />
        </button>
        <gsmsg:write key="cmn.group" />
        <html:select name="fil280Form" property="fil280group" styleClass="wp200" onchange="buttonPush('init');">
          <html:optionsCollection name="fil280Form" property="groupCombo" value="value" label="label" />
        </html:select>
        <button type="button" onclick="openGroupWindow(this.form.fil280group, 'fil280group', '0', 'init')" class="iconBtn-border" value="&nbsp;&nbsp;" id="fil280GroupBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>
        <gsmsg:write key="cmn.user" />
        <html:select name="fil280Form" property="fil280user" styleClass="wp200">
          <logic:iterate id="user" name="fil280Form" property="userCombo">
            <bean:define id="usrSidVal" name="user" property="value" />
            <logic:equal name="user" property="usrUkoFlg" value="1">
              <html:option styleClass="mukoUserOption" value="<%=String.valueOf(usrSidVal)%>">
                <bean:write name="user" property="label" />
              </html:option>
            </logic:equal>
            <logic:notEqual name="user" property="usrUkoFlg" value="1">
              <html:option value="<%=String.valueOf(usrSidVal)%>">
                <bean:write name="user" property="label" />
              </html:option>
            </logic:notEqual>
          </logic:iterate>
        </html:select>
      </div>

      <logic:notEmpty name="fil280Form" property="fil280cabinetList">
        <div class="txt_l">
          <button type="button" class="baseBtn ml0" value="<gsmsg:write key="cmn.up" />" onclick="buttonPush('fil280up');">
            <gsmsg:write key="cmn.up" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" onclick="buttonPush('fil280down');">
            <gsmsg:write key="cmn.down" />
          </button>
        </div>
      </logic:notEmpty>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      </td>
      </tr>

      <logic:notEmpty name="fil280Form" property="fil280cabinetList">
        <div class="txt_l">※
          <gsmsg:write key="fil.fil220.2" />
        </div>
      </logic:notEmpty>

      <logic:notEmpty name="fil280Form" property="fil280cabinetList">
        <!-- ページコンテンツ start -->

        <!-- 一覧 -->
        <table class="table-top" cellpadding="0" cellspacing="0">

          <tr>
            <th></th>
            <th class="js_tableTopCheck js_tableTopCheck-header cursor_p">
              <html:checkbox property="fil280allCheck" onclick="changeChk();" />
            </th>
            <th class="w75">
              <span>
                <gsmsg:write key="cmn.name4" />
              </span>
            </th>
            <th class="w15">
              <span>
                <gsmsg:write key="fil.103" />
              </span>
            </th>
            <th class="w10"></th>
          </tr>

          <logic:iterate id="cabinetModel" name="fil280Form" property="fil280cabinetList" indexId="idx">
            <bean:define id="cabinetSid" name="cabinetModel" property="fcbSid" />

            <tr class="js_listHover" data-sid="<bean:write name="cabinetModel" property="fcbSid" />">
            <td class="txt_c js_tableTopCheckRadio cursor_p">
              <html:radio property="fil280sltRadio" value="<%=String.valueOf(cabinetSid)%>" />
            </td>
            <td class="txt_c js_tableTopCheck cursor_p">
              <html:multibox name="fil280Form" property="fil220sltCheck" value="<%=String.valueOf(cabinetSid)%>" />
            </td>
            <td class="txt_l js_listClick cursor_p">
              <img class="btn_classicImg-display" src="../file/images/classic/icon_cabinet.gif">
              <img class="btn_originalImg-display" src="../file/images/original/icon_cabinet_32.png">
              <span>
                <bean:write name="cabinetModel" property="fcbName" />
              </span>
            </td>
            <td class="txt_c js_listClick cursor_p">
              <logic:equal name="cabinetModel" property="fcbAccessKbn" value="<%=accessOn%>">
                <img class="classic-display" src="../file/images/classic/icon_file_lock.gif" border="0">
                <img class="original-display" src="../common/images/original/icon_lock.png" border="0">
              </logic:equal>
            </td>
            <td class="txt_c">
              <button type="button" class="baseBtn" name="btnChange" value="<gsmsg:write key="cmn.change" />" onClick="CabinetDetail('<bean:write name="cabinetModel" property="fcbSid" />');">
                <gsmsg:write key="cmn.change" />
              </button>
            </td>
            </tr>

          </logic:iterate>

        </table>
        <div class="txt_l">
          <img class="classic-display" src="../file/images/classic/icon_file_lock.gif" border="0">
          <img class="original-display" src="../common/images/original/icon_lock.png" border="0">
          <span>：
            <gsmsg:write key="fil.fil220.3" />
          </span>
        </div>
      </logic:notEmpty>

      <div class="footerBtn_block">
        <logic:notEmpty name="fil280Form" property="fil280cabinetList">
          <button type="button" class="baseBtn" value="<gsmsg:write key="fil.fil220.1" />" onclick="CabinetDetailMulti();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="fil.fil220.1" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="fil.fil220.1" />">
            <gsmsg:write key="fil.fil220.1" />
          </button>
        </logic:notEmpty>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil280back');">
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
