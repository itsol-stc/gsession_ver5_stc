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
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [<gsmsg:write key="reserve.rsv080.1" />]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../reserve/js/rsv080.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? 500"> </script>

<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/reserve/rsv080">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050SortRadio" />
    <html:hidden property="rsv080EditGrpSid" />
    <html:hidden property="rsv080EditSisetuSid" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv080Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv080Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv080Form" property="rsv080KeyList" scope="request">
      <logic:iterate id="sort" name="rsv080Form" property="rsv080KeyList" scope="request">
        <input type="hidden" name="rsv080KeyList" value="<bean:write name="sort"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv080Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv080Form" property="rsvIkkatuTorokuKey" scope="request">
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
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="reserve.rsv080.1" />
        </li>
        <li>
          <div>
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('sisetu_add');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" name="btn_delete" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('sisetu_sakuzyo');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_group_settei');">
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

      <table class="table-left">
        <tr>
          <th class="w10 no_w">
            <span>
              <gsmsg:write key="cmn.facility.group" />
            </span>
          </th>
          <td>
            <bean:write name="rsv080Form" property="rsv080RsgName" />
          </td>
        </tr>
        <tr>
          <th>
            <span>
              <gsmsg:write key="reserve.47" />
            </span>
          </th>
          <td>
            <bean:write name="rsv080Form" property="rsv080RskName" />
          </td>
        <tr>
      </table>

      <div class="component_bothEnd">
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" class="btn_upper" onclick="buttonPush('ue_e');">
            <gsmsg:write key="cmn.up" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" class="btn_downer" onclick="buttonPush('sita_e');">
            <gsmsg:write key="cmn.down" />

          </button>
        </div>
        <div>
          <button type="button" class="baseBtn" value="<gsmsg:write key="reserve.61" />" onclick="buttonPush('move_ikkatu');">
            <gsmsg:write key="reserve.61" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="reserve.62" />" onclick="buttonPush('move_import');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="reserve.62" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="reserve.62" />">
            <gsmsg:write key="reserve.62" />
          </button>
        </div>
      </div>

      <table class="table-top" >
        <tr>
          <th class="txt_c"></th>
          <th class="txt_c w60 no_w">
            <span>
              <gsmsg:write key="cmn.facility.name" />
            </span>
          </th>
          <th class="txt_c w10 no_w">
            <span>
              <gsmsg:write key="reserve.55" />
            </span>
          </th>
          <th class="txt_c w15 no_w">
            <span>
              <gsmsg:write key="cmn.asset.register.num" />
            </span>
          </th>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName4">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName4" />
              </span>
            </th>
          </logic:notEmpty>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName5">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName5" />
              </span>
            </th>
          </logic:notEmpty>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName1">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName1" />
              </span>
            </th>
          </logic:notEmpty>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName2">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName2" />
              </span>
            </th>
          </logic:notEmpty>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName3">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName3" />
              </span>
            </th>
          </logic:notEmpty>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName7">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName7" />
              </span>
            </th>
          </logic:notEmpty>

          <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName6">
            <th class="w5 txt_c no_w">
              <span>
                <bean:write name="rsv080Form" property="rsv080PropHeaderName6" />
              </span>
            </th>
          </logic:notEmpty>
          <th class="w5 txt_c no_w">
            <span>
              <gsmsg:write key="reserve.appr.set.title" />
            </span>
          </th>
        </tr>

        <bean:define id="mod" value="0" />
        <logic:notEmpty name="rsv080Form" property="rsv080SisetuList" scope="request">
          <logic:iterate id="sisetu" name="rsv080Form" property="rsv080SisetuList" scope="request" indexId="idx">
            <bean:define id="index" value="<%=String.valueOf(((Integer) idx).intValue())%>" />

            <tr class="js_listHover cursor_p" onclick="if (!$(event.target).is('.js_tableTopCheck') && !$(event.target).is('input[type=radio]') ) { moveSisetuEdit('<bean:write name="sisetu" property="rsdSid" />');}">

              <td class="no_w txt_c js_tableTopCheck cursor_p">
                <bean:define id="sKey" name="sisetu" property="radioKey" />
                <html:radio name="rsv080Form" property="rsv080SortRadio" value="<%=String.valueOf(sKey)%>" />
              </td>
              <td class="txt_l">
                <a href="#!" >
                  <span class="cl_linkDef">
                    <bean:write name="sisetu" property="rsdName" />
                  </span>
                </a>
              </td>

              <td class="txt_l">
                <bean:write name="sisetu" property="rsdId" />
              </td>

              <td class="txt_l">
                <bean:write name="sisetu" property="rsdSnum" />
              </td>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName4">
                <td class="txt_c no_w">
                  <logic:empty name="sisetu" property="rsdProp4">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp4">
                    <bean:write name="sisetu" property="rsdProp4" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName5">
                <td class="txt_c no_w">
                  <logic:empty name="sisetu" property="rsdProp5">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp5">
                    <bean:write name="sisetu" property="rsdProp5" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName1">
                <td class="txt_r no_w">
                  <logic:empty name="sisetu" property="rsdProp1">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp1">
                    <bean:write name="sisetu" property="rsdProp1" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName2">
                <td class="txt_c no_w">
                  <logic:empty name="sisetu" property="rsdProp2">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp2">
                    <bean:write name="sisetu" property="rsdProp2" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName3">
                <td class="txt_c no_w">
                  <logic:empty name="sisetu" property="rsdProp3">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp3">
                    <bean:write name="sisetu" property="rsdProp3" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName7">
                <td class="txt_c no_w">
                  <logic:empty name="sisetu" property="rsdProp7">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp7">
                    <bean:write name="sisetu" property="rsdProp7" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <logic:notEmpty name="rsv080Form" property="rsv080PropHeaderName6">
                <td class="txt_r no_w">
                  <logic:empty name="sisetu" property="rsdProp6">&nbsp;</logic:empty>
                  <logic:notEmpty name="sisetu" property="rsdProp6">
                    <bean:write name="sisetu" property="rsdProp6" />
                  </logic:notEmpty>
                </td>
              </logic:notEmpty>

              <td class="txt_c no_w">
                <logic:equal name="sisetu" property="rsdApprKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSD_APPR_KBN_NOSET)%>">
                  <gsmsg:write key="reserve.appr.set.kbn2" />
                </logic:equal>
                <logic:notEqual name="sisetu" property="rsdApprKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSD_APPR_KBN_NOSET)%>">
                  <gsmsg:write key="reserve.appr.set.kbn1" />
                </logic:notEqual>
              </td>

            </tr>

          </logic:iterate>
        </logic:notEmpty>

        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('sisetu_add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" name="btn_delete" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('sisetu_sakuzyo');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_to_sisetu_group_settei');">
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