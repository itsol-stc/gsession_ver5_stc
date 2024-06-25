<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> <logic:equal name="rsv060Form" property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_SINKI)%>"> [<gsmsg:write key="reserve.rsv060.1" />]</logic:equal> <logic:equal name="rsv060Form"
    property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>"> [<gsmsg:write key="reserve.rsv060.2" />]</logic:equal>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../reserve/js/rsv060.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../reserve/js/sisetuPopup.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

</head>

<body onload="bodyLoad();showOrHide();" onunload="windowClose();">
  <html:form action="/reserve/rsv060">
    <input type="hidden" name="CMD" value="sisetu_toroku_kakunin">
    <html:hidden property="backScreen" />
    <html:hidden property="rsvBackPgId" />
    <html:hidden property="rsvBackToAdminSetting" />
    <html:hidden property="rsvDspFrom" />
    <html:hidden property="rsvSelectedGrpSid" />
    <html:hidden property="rsvSelectedSisetuSid" />
    <html:hidden property="rsv050SortRadio" />
    <html:hidden property="rsv060InitFlg" />
    <html:hidden property="rsv060ProcMode" />
    <html:hidden property="rsv060EditGrpSid" />
    <html:hidden property="rsv060AccessDspFlg" />

    <%@ include file="/WEB-INF/plugin/reserve/jsp/rsvHidden.jsp"%>

    <logic:notEmpty name="rsv060Form" property="rsv100CsvOutField" scope="request">
      <logic:iterate id="csvOutField" name="rsv060Form" property="rsv100CsvOutField" scope="request">
        <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="rsv060Form" property="rsvIkkatuTorokuKey" scope="request">
      <logic:iterate id="key" name="rsv060Form" property="rsvIkkatuTorokuKey" scope="request">
        <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
      </logic:iterate>
    </logic:notEmpty>

    <input type="hidden" name="helpPrm" value="<bean:write name="rsv060Form" property="rsv060ProcMode" />">

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
          <logic:equal name="rsv060Form" property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_SINKI)%>">
            <gsmsg:write key="reserve.rsv060.1" />
          </logic:equal>
          <logic:equal name="rsv060Form" property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
            <gsmsg:write key="reserve.rsv060.2" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:equal name="rsv060Form" property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="buttonPush('sisetu_sakuzyo_kakunin');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
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

      <table class="table-left w100 table-fixed"  >
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.group.name" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="txt_l w80">
            <html:text name="rsv060Form" styleClass="wp300" property="rsv060GrpName" maxlength="20" />
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.group.id" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="txt_l w80">
            <html:text name="rsv060Form" styleClass="wp300" property="rsv060GrpId" maxlength="15" />
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.47" />
            </span>
          </th>
          <td class="txt_l">
            <html:select property="rsv060SelectedSisetuKbn" styleClass="select01">
              <html:optionsCollection name="rsv060Form" property="rsv060SisetuLabelList" value="value" label="label" />
            </html:select>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.162" />
            </span>
          </th>
          <td class="txt_l">
            <bean:define id="admOk" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_ADM_KBN_OK)%>" />
            <bean:define id="admNo" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_ADM_KBN_NO)%>" />
            <span class="verAlignMid">
              <html:radio name="rsv060Form" styleId="doSet" property="rsv060GrpAdmKbn" value="<%=admOk%>" onclick="admChange(0);" />
              <span>
                <label for="doSet">
                  <gsmsg:write key="reserve.163" />
                </label>
              </span>
              <html:radio name="rsv060Form" styleClass="ml10" styleId="notSet" property="rsv060GrpAdmKbn" value="<%=admNo%>" onclick="admChange(1);" />
              <span>
                <label for="notSet">
                  <gsmsg:write key="reserve.164" />
                </label>
              </span>
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.group.admin" />
            </span>
          </th>

          <td class="txt_l">
            <ui:usrgrpselector name="rsv060Form" property="saveUserUI" styleClass="hp215" />
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.appr.set.title" />
            </span>
          </th>

          <td class="txt_l">
            <bean:define id="apprKbn_appr" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_APPR_KBN_APPR)%>" />
            <bean:define id="apprKbn_sisetsu" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSG_APPR_KBN_SISETSU)%>" />
            <span class="verAlignMid">
              <html:radio name="rsv060Form" styleId="apprKbn1" property="rsv060apprKbn" value="<%=apprKbn_appr%>" />
              <span>
                <label for="apprKbn1">
                  <gsmsg:write key="reserve.appr.set.kbn1" />
                </label>
              </span>
              <html:radio name="rsv060Form" styleClass="ml10" styleId="apprKbn2" property="rsv060apprKbn" value="<%=apprKbn_sisetsu%>" />
              <span>
                <label for="apprKbn2">
                  <gsmsg:write key="reserve.appr.set.kbn3" />
                </label>
              </span>
            </span>
          </td>
        </tr>

      </table>

      <div class="txt_l">
        <span id="text1">
          <a href="#!" onClick="changeShowOrHide()">
            <span class="cl_linkDef">
              <gsmsg:write key="cmn.not.setting.access.conf" />
            </span>
          </a>
        </span>

        <span id="text2">
          <a href="#!" onClick="changeShowOrHide()">
            <span class="cl_linkDef">
              <gsmsg:write key="cmn.setting.access.conf" />
            </span>
          </a>
        </span>
      </div>

      <div id="show0">
        <table class="table-left" >
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="cmn.access.auth" />
              </span>
            </th>

            <td class="txt_l w80">
              <div>
                <span>
                  <gsmsg:write key="cmn.howto.limit" />
                </span>
                <br>
                <bean:define id="textPermit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_ACCESS_MODE_PERMIT)%>" />
                <bean:define id="textLimit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_ACCESS_MODE_LIMIT)%>" />
                <span class="verAlignMid">
                  <html:radio name="rsv060Form" styleId="permit" property="rsv060AccessKbn" value="<%=textPermit%>" />
                  <span>
                    <label for="permit">
                      <gsmsg:write key="cmn.access.permit" />
                    </label>
                  </span>
                  <br>
                  <html:radio name="rsv060Form" styleClass="ml10" styleId="limit" property="rsv060AccessKbn" value="<%=textLimit%>" />
                  <span>
                    <label for="limit">
                      <gsmsg:write key="cmn.access.limit" />
                    </label>
                  </span>
                </span>
              </div>

              <ui:usrgrpselector name="rsv060Form" property="rsv060memberUI" styleClass="hp300" />

            </td>
          </tr>
        </table>
      </div>

      <logic:equal name="rsv060Form" property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
        <table class="table-top" >
          <tr>
            <th class="txt_c" colspan="2">
              <span>
                <gsmsg:write key="reserve.rsv060.4" />
              </span>
            </th>
          </tr>

          <bean:define id="mod" value="0" />

          <logic:notEmpty name="rsv060Form" property="rsv060SyozokuList" scope="request">
            <logic:iterate id="syozoku" name="rsv060Form" property="rsv060SyozokuList" scope="request" indexId="idx">
              <tr class="js_listHover js_listClick cursor_p" id="<bean:write name="syozoku" property="rsdSid" />">
                <td class="txt_l" colspan="2">
                  <span class="cl_linkDef">
                    <bean:write name="syozoku" property="rsdName" />
                  </span>
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </logic:equal>

      <div class="footerBtn_block">
        <button type="submit" value="<gsmsg:write key="cmn.ok" />" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="rsv060Form" property="rsv060ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_EDIT)%>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onclick="buttonPush('sisetu_sakuzyo_kakunin');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
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