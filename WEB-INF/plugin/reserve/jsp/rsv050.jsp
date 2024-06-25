<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [<gsmsg:write key="reserve.rsv050.1" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?500'></script>
<script src="../common/js/tableTop.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/rsv050.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/reserve/rsv050">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050EditGrpSid" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv050Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv050Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv050Form" property="rsv050KeyList" scope="request">
      <logic:iterate id="sort" name="rsv050Form" property="rsv050KeyList" scope="request">
        <input type="hidden" name="rsv050KeyList" value="<bean:write name="sort"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv050Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv050Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
            <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li><gsmsg:write key="cmn.reserve" /></li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="reserve.rsv050.1" />
        </li>
        <li>
          <div>
            <logic:equal name="rsv050Form" property="rsvAdmFlg" value="true">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.export" />" onclick="buttonPush('sisetu_group_all_export');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                <gsmsg:write key="cmn.export" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" class="btn_upper" onClick="buttonPush('sisetu_group_all_tuika');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                <gsmsg:write key="cmn.import" />
              </button>
              <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('sisetu_group_tuika');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_menu');">
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

      <logic:equal name="rsv050Form" property="rsvAdmFlg" value="true">
      <div class="txt_l">
        <button type="button" class="baseBtn ml0" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onclick="buttonPush('ue_e');">
          <gsmsg:write key="cmn.up" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onclick="buttonPush('sita_e');">
          <gsmsg:write key="cmn.down" />
        </button>
      </div>
      </logic:equal>

      <table class="table-top" >
        <tr>
          <th class="no_w"></th>
          <th class="w90 txt_c">
            <span class="text_tlw">
              <gsmsg:write key="cmn.group.name" />
            </span>
          </th>
          <th class="w5"></th>
        </tr>

        <bean:define id="mod" value="0" />
        <logic:notEmpty name="rsv050Form" property="rsv050GroupList" scope="request">
          <logic:iterate id="grp" name="rsv050Form" property="rsv050GroupList" scope="request" indexId="idx">
            <tr class="js_listHover cursor_p">
              <td class="js_tableTopCheck ">
                <bean:define id="sKey" name="grp" property="radioKey" />
                <html:radio name="rsv050Form" property="rsv050SortRadio" value="<%=String.valueOf(sKey)%>" />
              </td>
              <td class="txt_l" onclick="moveSisetuGroup('<bean:write name="grp" property="rsgSid" />')">
                <a href="#!" >
                  <span class="cl_linkDef">
                    <bean:write name="grp" property="rsgName" />
                  </span>
                </a>
              </td>
              <td class="txt_c no_w"  onclick="moveSisetu('<bean:write name="grp" property="rsgSid" />')">
                <button type="button" value="<gsmsg:write key="reserve.settings" />" class="baseBtn">
                  <gsmsg:write key="reserve.settings" />
                </button>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </div>


    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>