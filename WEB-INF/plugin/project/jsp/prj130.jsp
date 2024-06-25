<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>


<%-- CMD定数 --%>
<%
  String mode_kojin = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_KOJIN);
  String mode_kyoyu = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_KYOYU);
  String mode_select = String.valueOf(jp.groupsession.v2.prj.GSConstProject.MODE_TMP_SELECT);
  String back = jp.groupsession.v2.prj.prj130.Prj130Action.CMD_BACK;
  String addTmp = jp.groupsession.v2.prj.prj130.Prj130Action.CMD_ADD;
  String selectTmp = jp.groupsession.v2.prj.prj130.Prj130Action.CMD_TMP_SELECT;
  String checkTmp = jp.groupsession.v2.prj.prj130.Prj130Action.CMD_TMP_CHECK;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj130.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/project/prj130">
    <input type="hidden" name="helpPrm" value="<bean:write name="prj130Form" property="prj130cmdMode" />">

    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />
    <html:hidden property="prj010cmdMode" />
    <html:hidden property="prj010sort" />
    <html:hidden property="prj010order" />
    <html:hidden property="prj010page1" />
    <html:hidden property="prj010page2" />
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

    <logic:notEmpty name="prj130Form" property="prj040scMemberSid" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj040scMemberSid" scope="request">
        <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj040svScMemberSid" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj040svScMemberSid" scope="request">
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

    <logic:notEmpty name="prj130Form" property="prj030sendMember" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj030sendMember" scope="request">
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
    <html:hidden property="prj020prjMyKbn" />
    <html:hidden property="prj020smailKbn" />
    <html:hidden property="prj020IcoName" />
    <html:hidden property="prj020IcoSaveName" />
    <html:hidden property="prj020initFlg" />

    <logic:notEmpty name="prj130Form" property="prj020syozokuMember" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj020syozokuMember" scope="request">
        <input type="hidden" name="prj020syozokuMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj020user" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj020user" scope="request">
        <input type="hidden" name="prj020user" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj020hdnMember" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj020hdnMember" scope="request">
        <input type="hidden" name="prj020hdnMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj020adminMember" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj020adminMember" scope="request">
        <input type="hidden" name="prj020adminMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj020prjMember" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj020prjMember" scope="request">
        <input type="hidden" name="prj020prjMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj020hdnAdmin" scope="request">
      <logic:iterate id="item" name="prj130Form" property="prj020hdnAdmin" scope="request">
        <input type="hidden" name="prj020hdnAdmin" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj150AddressIdSv" scope="request">
      <logic:iterate id="addressId" name="prj130Form" property="prj150AddressIdSv" scope="request">
        <input type="hidden" name="prj150AddressIdSv" value="<bean:write name="addressId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj150CompanySid" scope="request">
      <logic:iterate id="coId" name="prj130Form" property="prj150CompanySid" scope="request">
        <input type="hidden" name="prj150CompanySid" value="<bean:write name="coId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj130Form" property="prj150CompanyBaseSid" scope="request">
      <logic:iterate id="coId" name="prj130Form" property="prj150CompanyBaseSid" scope="request">
        <input type="hidden" name="prj150CompanyBaseSid" value="<bean:write name="coId"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden property="prjTmpMode" />
    <html:hidden property="prtSid" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_kyoyu%>">
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
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><gsmsg:write key="project.prj130.1" />
          </li>
          <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
            <li>
              <div>
                <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="selectTemplate('<%=addTmp%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <gsmsg:write key="cmn.add" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
            </li>
          </logic:notEqual>

          <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
            <li>
              <div>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
            </li>
          </logic:equal>
        </ul>
    </logic:equal>

    <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_kojin%>">
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
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.project" /></span><gsmsg:write key="project.prj130.1" />
          </li>
          <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
            <li>
              <div>
                <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="selectTemplate('<%=addTmp%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <gsmsg:write key="cmn.add" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
            </li>
          </logic:notEqual>

          <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
            <li>
              <div>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
            </li>
          </logic:equal>
        </ul>
    </logic:equal>


    </div>

    <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
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
            <gsmsg:write key="select.template" />
          </li>
          <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
            <li>
              <div>
                <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="selectTemplate('<%=addTmp%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                  <gsmsg:write key="cmn.add" />
                </button>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
            </li>
          </logic:notEqual>

          <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
            <li>
              <div>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
            </li>
          </logic:equal>
        </ul>
      </div>
    </logic:equal>

    <div class="wrapper w80 mrl_auto">
      <div class="txt_l">
        <gsmsg:write key="project.prj130.2" />
      </div>

      <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_kojin%>">
        <table class="table-top mt0">
          <tr>
            <th>
              <gsmsg:write key="cmn.shared.template" />
            </th>
          </tr>
          </logic:notEqual>
          <logic:notEmpty name="prj130Form" property="prj130TmpKyoyuList">

            <bean:define id="mod1" value="0" />

            <logic:iterate id="kyoyuTmp" name="prj130Form" property="prj130TmpKyoyuList" indexId="idx">
              <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
                <tr class="js_listHover cursor_p js_listClick" data-check="<%=checkTmp%>" data-sid="<bean:write name="kyoyuTmp" property="prtSid" />">
                  <td>
                    <span class="cl_linkDef">
                      <bean:write name="kyoyuTmp" property="prtTmpName" />
                    </span>
                  </td>
                </tr>
              </logic:equal>

              <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
                <tr class="js_listHover cursor_p js_listClick" data-check="<%=selectTmp%>" data-sid="<bean:write name="kyoyuTmp" property="prtSid" />">
                  <td>
                    <span class="cl_linkDef">
                      <bean:write name="kyoyuTmp" property="prtTmpName" />
                    </span>
                  </td>
                </tr>
              </logic:notEqual>
            </logic:iterate>
          </logic:notEmpty>
          <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_kojin%>">
        </table>
      </logic:notEqual>

      <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_kyoyu%>">
        <table class="table-top mt0">
          <tr>
            <th>
              <gsmsg:write key="cmn.personal.template" />
            </th>
          </tr>
          </logic:notEqual>
          <logic:notEmpty name="prj130Form" property="prj130TmpKojinList">
            <bean:define id="mod1" value="0" />

            <logic:iterate id="kojinTmp" name="prj130Form" property="prj130TmpKojinList" indexId="idx">

              <logic:equal name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
                <tr class="js_listHover cursor_p js_listClick" data-check="<%=checkTmp%>" data-sid="<bean:write name="kojinTmp" property="prtSid" />">
                  <td>
                    <span class="cl_linkDef">
                      <bean:write name="kojinTmp" property="prtTmpName" />
                    </span>
                  </td>
                </tr>
              </logic:equal>

              <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
                <tr class="js_listHover cursor_p js_listClick" data-check="<%=selectTmp%>" data-sid="<bean:write name="kojinTmp" property="prtSid" />">
                  <td>
                    <span class="cl_linkDef">
                      <bean:write name="kojinTmp" property="prtTmpName" />
                    </span>
                  </td>
                </tr>
              </logic:notEqual>
            </logic:iterate>
          </logic:notEmpty>

          <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_kyoyu%>">
        </table>
      </logic:notEqual>

      <div class="footerBtn_block">
        <logic:notEqual name="prj130Form" property="prjTmpMode" value="<%=mode_select%>">
          <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="selectTemplate('<%=addTmp%>');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </logic:notEqual>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('<%=back%>');">
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