<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%-- CMD定数 --%>
<%
  String backClick = jp.groupsession.v2.prj.prj023.Prj023Action.CMD_BACK_CLICK;
  String editClick = jp.groupsession.v2.prj.prj021.Prj021Action.CMD_EDIT_CLICK;
  String tmpEditClick = jp.groupsession.v2.prj.prj140.Prj140Action.CMD_EDIT_CLICK;
  String addValueClick = jp.groupsession.v2.prj.prj023.Prj023Action.CMD_ADD_VALUE_CLICK;
  String removeValueClick = jp.groupsession.v2.prj.prj023.Prj023Action.CMD_REMOVE_VALUE_CLICK;
  String sortUpClick = jp.groupsession.v2.prj.prj023.Prj023Action.CMD_SORT_UP_CLICK;
  String sortDownClick = jp.groupsession.v2.prj.prj023.Prj023Action.CMD_SORT_DOWN_CLICK;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/project.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/project/prj023">

    <input type="hidden" name="CMD" id="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="prj010cmdMode" />
    <html:hidden property="prj010page1" />
    <html:hidden property="prj010page2" />
    <html:hidden property="prj010sort" />
    <html:hidden property="prj010order" />
    <html:hidden property="prj010Init" />
    <html:hidden property="selectingProject" />
    <html:hidden property="selectingTodoDay" />
    <html:hidden property="selectingTodoPrj" />
    <html:hidden property="selectingTodoSts" />
    <html:hidden property="prj040searchFlg" />
    <html:hidden property="prj040scPrjId" />
    <html:hidden property="prj040scStatusFrom" />
    <html:hidden property="prj040scStatusTo" />
    <html:hidden property="prj040scPrjName" />
    <html:hidden property="prj040scStartYearFrom" />
    <html:hidden property="prj040scStartMonthFrom" />
    <html:hidden property="prj040scStartDayFrom" />
    <html:hidden property="prj040scStartYearTo" />
    <html:hidden property="prj040scStartMonthTo" />
    <html:hidden property="prj040scStartDayTo" />
    <html:hidden property="prj040scEndYearFrom" />
    <html:hidden property="prj040scEndMonthFrom" />
    <html:hidden property="prj040scEndDayFrom" />
    <html:hidden property="prj040scEndYearTo" />
    <html:hidden property="prj040scEndMonthTo" />
    <html:hidden property="prj040scEndDayTo" />
    <html:hidden property="prj040svScPrjId" />
    <html:hidden property="prj040svScStatusFrom" />
    <html:hidden property="prj040svScStatusTo" />
    <html:hidden property="prj040svScPrjName" />
    <html:hidden property="prj040svScStartYearFrom" />
    <html:hidden property="prj040svScStartMonthFrom" />
    <html:hidden property="prj040svScStartDayFrom" />
    <html:hidden property="prj040svScStartYearTo" />
    <html:hidden property="prj040svScStartMonthTo" />
    <html:hidden property="prj040svScStartDayTo" />
    <html:hidden property="prj040svScEndYearFrom" />
    <html:hidden property="prj040svScEndMonthFrom" />
    <html:hidden property="prj040svScEndDayFrom" />
    <html:hidden property="prj040svScEndYearTo" />
    <html:hidden property="prj040svScEndMonthTo" />
    <html:hidden property="prj040svScEndDayTo" />
    <html:hidden property="prj040page1" />
    <html:hidden property="prj040page2" />
    <html:hidden property="prj040sort" />
    <html:hidden property="prj040order" />
    <html:hidden property="prj040scYosanFr" />
    <html:hidden property="prj040scYosanTo" />
    <html:hidden property="prj040svScYosanFr" />
    <html:hidden property="prj040svScYosanTo" />

    <logic:notEmpty name="prj023Form" property="prj040scMemberSid" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj040scMemberSid" scope="request">
        <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj040svScMemberSid" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj040svScMemberSid" scope="request">
        <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prj030scrId" />
    <html:hidden property="prj030prjSid" />
    <html:hidden property="prj030sort" />
    <html:hidden property="prj030order" />
    <html:hidden property="prj030page1" />
    <html:hidden property="prj030page2" />
    <html:hidden property="prj030Init" />
    <html:hidden property="selectingDate" />
    <html:hidden property="selectingStatus" />
    <html:hidden property="selectingCategory" />
    <html:hidden property="selectingMember" />

    <logic:notEmpty name="prj023Form" property="prj030sendMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj030sendMember" scope="request">
        <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prj020scrId" />
    <html:hidden property="prj020cmdMode" />
    <html:hidden property="prj020prjSid" />
    <html:hidden property="prj020prjId" />
    <html:hidden property="prj020prjName" />
    <html:hidden property="prj020prjNameS" />
    <html:hidden property="prj020yosan" />
    <html:hidden property="prj020koukai" />
    <html:hidden property="prj020startYear" />
    <html:hidden property="prj020startMonth" />
    <html:hidden property="prj020startDay" />
    <html:hidden property="prj020endYear" />
    <html:hidden property="prj020endMonth" />
    <html:hidden property="prj020endDay" />
    <html:hidden property="prj020status" />
    <html:hidden property="prj020mokuhyou" />
    <html:hidden property="prj020naiyou" />
    <html:hidden property="prj020group" />
    <html:hidden property="prj020kengen" />
    <html:hidden property="prj020smailKbn" />
    <html:hidden property="prj020prjMyKbn" />
    <html:hidden property="prj020IcoName" />
    <html:hidden property="prj020IcoSaveName" />
    <html:hidden property="prj020initFlg" />

    <logic:notEmpty name="prj023Form" property="prj020syozokuMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj020syozokuMember" scope="request">
        <input type="hidden" name="prj020syozokuMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj020user" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj020user" scope="request">
        <input type="hidden" name="prj020user" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj020hdnMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj020hdnMember" scope="request">
        <input type="hidden" name="prj020hdnMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj020adminMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj020adminMember" scope="request">
        <input type="hidden" name="prj020adminMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj020prjMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj020prjMember" scope="request">
        <input type="hidden" name="prj020prjMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj020hdnAdmin" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj020hdnAdmin" scope="request">
        <input type="hidden" name="prj020hdnAdmin" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prjTmpMode" />
    <html:hidden property="prtSid" />

    <html:hidden property="prj140prtTmpName" />
    <html:hidden property="prj140prtId" />
    <html:hidden property="prj140prtName" />
    <html:hidden property="prj140prtNameS" />
    <html:hidden property="prj140yosan" />
    <html:hidden property="prj140koukai" />
    <html:hidden property="prj140startYear" />
    <html:hidden property="prj140startMonth" />
    <html:hidden property="prj140startDay" />
    <html:hidden property="prj140endYear" />
    <html:hidden property="prj140endMonth" />
    <html:hidden property="prj140endDay" />
    <html:hidden property="prj140status" />
    <html:hidden property="prj140mokuhyou" />
    <html:hidden property="prj140naiyou" />
    <html:hidden property="prj140group" />
    <html:hidden property="prj140kengen" />
    <html:hidden property="prj140smailKbn" />

    <logic:notEmpty name="prj023Form" property="prj140syozokuMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj140syozokuMember" scope="request">
        <input type="hidden" name="prj140syozokuMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj140user" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj140user" scope="request">
        <input type="hidden" name="prj140user" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj140hdnMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj140hdnMember" scope="request">
        <input type="hidden" name="prj140hdnMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj140adminMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj140adminMember" scope="request">
        <input type="hidden" name="prj140adminMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj140prjMember" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj140prjMember" scope="request">
        <input type="hidden" name="prj140prjMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj140hdnAdmin" scope="request">
      <logic:iterate id="item" name="prj023Form" property="prj140hdnAdmin" scope="request">
        <input type="hidden" name="prj140hdnAdmin" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj150AddressIdSv">
      <logic:iterate id="addressId" name="prj023Form" property="prj150AddressIdSv">
        <input type="hidden" name="prj150AddressIdSv" value="<bean:write name="addressId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj150CompanySid">
      <logic:iterate id="coId" name="prj023Form" property="prj150CompanySid">
        <input type="hidden" name="prj150CompanySid" value="<bean:write name="coId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj023Form" property="prj150CompanyBaseSid">
      <logic:iterate id="coId" name="prj023Form" property="prj150CompanyBaseSid">
        <input type="hidden" name="prj150CompanyBaseSid" value="<bean:write name="coId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="selectDir" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="cmn.project" />">
          <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="cmn.project" />">
        </li>
        <li>
          <gsmsg:write key="cmn.project" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="project.prj023.1" />
        </li>
        <li>
          <div>
            <logic:lessEqual name="prj023Form" property="prjTmpMode" value="0">
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=editClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
            </logic:lessEqual>

            <logic:greaterThan name="prj023Form" property="prjTmpMode" value="0">
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=tmpEditClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
            </logic:greaterThan>

            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=backClick%>');">
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
          <th class="no_w" rowspan="3">
            <span>
              <gsmsg:write key="project.src.46" />
            </span>
            <span class="cl_fontWarn">※</span>
          </th>
          <td class="txt_l">
            <div class="display_inline-block wp50">
              0%
            </div>
              ：<html:text property="prj023name0" styleClass="wp200" maxlength="20" />
          </td>
        </tr>

        <tr>
          <td class="txt_l">

            <table>
              <tr>
                <td class="border_none">
                  <span>
                    <gsmsg:write key="project.src.34" />
                  </span>
                  <html:text property="prj023rate" styleClass="wp50" maxlength="2" />%
                  <span class="ml10">
                    <gsmsg:write key="project.src.42" />
                  </span>
                  <html:text property="prj023name" styleClass="wp150" maxlength="20" />
                </td>
                <td class="border_none">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" name="adduserBtn" onClick="buttonPush('<%=addValueClick%>');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_d.png" alt="<gsmsg:write key="cmn.add" />">
                    <i class="icon-down"></i>
                    <gsmsg:write key="cmn.add" />
                  </button>
                </td>
              </tr>

              <tr class="border_none">
                <td class="border_none">
                  <html:select property="prj023state" size="5" styleClass="wp350 hp100 p0" multiple="true">
                    <html:optionsCollection name="prj023Form" property="statusLabel" value="value" label="label" />
                  </html:select>
                </td>
                <td class="txt_b border_none">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" name="dellBtn" onClick="buttonPush('<%=removeValueClick%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
                </td>
              </tr>
            </table>

          </td>
        </tr>

        <tr>
          <td class="txt_l">
            <div class="display_inline-block wp50">
              100%
            </div>
              ：<html:text property="prj023name100" styleClass="wp200" maxlength="20" />
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <logic:lessEqual name="prj023Form" property="prjTmpMode" value="0">
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=editClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
        </logic:lessEqual>

        <logic:greaterThan name="prj023Form" property="prjTmpMode" value="0">
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=tmpEditClick%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
        </logic:greaterThan>

        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=backClick%>');">
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
