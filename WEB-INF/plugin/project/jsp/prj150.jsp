<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%-- 定数 --%>
<%
  String tabNaibuClick = jp.groupsession.v2.prj.prj150.Prj150Action.CMD_TAB_NAIBU;
  String tabGaibuClick = jp.groupsession.v2.prj.prj150.Prj150Action.CMD_TAB_GAIBU;
  String okClick = jp.groupsession.v2.prj.prj150.Prj150Action.CMD_OK_CLICK;
  String okTmpClick = jp.groupsession.v2.prj.prj150.Prj150Action.CMD_OK_TMP_CLICK;
  String backClick = jp.groupsession.v2.prj.prj150.Prj150Action.CMD_BACK_CLICK;
  String back020 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ020;
  String back030 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ030;
  String back140 = jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ140;
%>

<%-- 処理(タブ)モード --%>
<%
  String modeNaibu = jp.groupsession.v2.prj.GSConstProject.MODE_NAIBU;
  String modeGaibu = jp.groupsession.v2.prj.GSConstProject.MODE_GAIBU;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>

<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/userpopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj150.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<logic:equal name="prj150Form" property="addressPluginKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.PLUGIN_USE)%>">
  <script src="../address/js/adr240.js?<%=GSConst.VERSION_PARAM%>"></script>
</logic:equal>

<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onunload="windowClose();">

  <html:form action="/project/prj150">
    <input type="hidden" name="helpPrm" value="<bean:write name="prj150Form" property="prj150cmdMode" />">
    <input type="hidden" name="CMD" value="">

    <jsp:include page="/WEB-INF/plugin/project/jsp/prj150_hiddenparam.jsp" />

    <logic:notEmpty name="prj150Form" property="prj040scMemberSid" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj040scMemberSid" scope="request">
        <input type="hidden" name="prj040scMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj040svScMemberSid" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj040svScMemberSid" scope="request">
        <input type="hidden" name="prj040svScMemberSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj030sendMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj030sendMember" scope="request">
        <input type="hidden" name="prj030sendMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <input type="hidden" name="prj150delCompanyId" value="">
    <input type="hidden" name="prj150delCompanyBaseId" value="">
    <input type="hidden" name="prj150UsrDelFlg" value="">

    <logic:notEmpty name="prj150Form" property="prj020syozokuMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj020syozokuMember" scope="request">
        <input type="hidden" name="prj020syoz<gsmsg:write key="cmn.ok" />uMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj020user" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj020user" scope="request">
        <input type="hidden" name="prj020user" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <div id="hiddenList">

      <logic:notEmpty name="prj150Form" property="prj020hdnMember" scope="request">
        <logic:iterate id="item" name="prj150Form" property="prj020hdnMember" scope="request">
          <input type="hidden" name="prj020hdnMember" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="prj150Form" property="prj140hdnMember" scope="request">
        <logic:iterate id="item" name="prj150Form" property="prj140hdnMember" scope="request">
          <input type="hidden" name="prj140hdnMember" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="prj150Form" property="prj020hdnMemberSv" scope="request">
        <logic:iterate id="item" name="prj150Form" property="prj020hdnMemberSv" scope="request">
          <input type="hidden" name="prj020hdnMemberSv" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="prj150Form" property="prj140hdnMemberSv" scope="request">
        <logic:iterate id="item" name="prj150Form" property="prj140hdnMemberSv" scope="request">
          <input type="hidden" name="prj140hdnMemberSv" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>

    </div>

    <div id="prj150CompanyIdArea">
      <logic:notEmpty name="prj150Form" property="prj150CompanySid">
        <logic:iterate id="coId" name="prj150Form" property="prj150CompanySid">
          <input type="hidden" name="prj150CompanySid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="prj150Form" property="prj150CompanySidSv">
        <logic:iterate id="coId" name="prj150Form" property="prj150CompanySidSv">
          <input type="hidden" name="prj150CompanySidSv" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

    </div>

    <div id="prj150CompanyBaseIdArea">
      <logic:notEmpty name="prj150Form" property="prj150CompanyBaseSid">
        <logic:iterate id="coId" name="prj150Form" property="prj150CompanyBaseSid">
          <input type="hidden" name="prj150CompanyBaseSid" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

      <logic:notEmpty name="prj150Form" property="prj150CompanyBaseSidSv">
        <logic:iterate id="coId" name="prj150Form" property="prj150CompanyBaseSidSv">
          <input type="hidden" name="prj150CompanyBaseSidSv" value="<bean:write name="coId"/>">
        </logic:iterate>
      </logic:notEmpty>

    </div>

    <div id="prj150AddressIdArea">
      <logic:notEmpty name="prj150Form" property="prj150AddressId">
        <logic:iterate id="addressId" name="prj150Form" property="prj150AddressId">
          <input type="hidden" name="prj150AddressId" value="<bean:write name="addressId"/>">
        </logic:iterate>
      </logic:notEmpty>
    </div>

    <div id="prj150AddressIdSvArea">
      <logic:notEmpty name="prj150Form" property="prj150AddressIdSv">
        <logic:iterate id="addressId" name="prj150Form" property="prj150AddressIdSv">
          <input type="hidden" name="prj150AddressIdSv" value="<bean:write name="addressId"/>">
        </logic:iterate>
      </logic:notEmpty>
    </div>

    <logic:notEmpty name="prj150Form" property="prj020adminMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj020adminMember" scope="request">
        <input type="hidden" name="prj020adminMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj020prjMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj020prjMember" scope="request">
        <input type="hidden" name="prj020prjMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj020hdnAdmin" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj020hdnAdmin" scope="request">
        <input type="hidden" name="prj020hdnAdmin" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj140syozokuMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj140syozokuMember" scope="request">
        <input type="hidden" name="prj140syoz<gsmsg:write key="cmn.ok" />uMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj140user" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj140user" scope="request">
        <input type="hidden" name="prj140user" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj140adminMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj140adminMember" scope="request">
        <input type="hidden" name="prj140adminMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj140prjMember" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj140prjMember" scope="request">
        <input type="hidden" name="prj140prjMember" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="prj150Form" property="prj140hdnAdmin" scope="request">
      <logic:iterate id="item" name="prj150Form" property="prj140hdnAdmin" scope="request">
        <input type="hidden" name="prj140hdnAdmin" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="project.29" />">
          <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="project.29" />">
        </li>
        <li>
          <gsmsg:write key="cmn.project" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.project" /><gsmsg:write key="project.29" />
        </li>
        <li>
          <div>
            <%-- 内部タブ --%>
            <logic:equal name="prj150Form" property="prj150cmdMode" value="0">
              <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
                <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=okClick%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                  <gsmsg:write key="cmn.ok" />
                </button>
              </logic:equal>
              <logic:equal name="prj150Form" property="movedDspId" value="<%=back020%>">
                <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prj150ButtonPush('<%=okClick%>', 1);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                  <gsmsg:write key="cmn.ok" />
                </button>
              </logic:equal>
              <logic:equal name="prj150Form" property="movedDspId" value="<%=back140%>">
                <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prj150ButtonPush('<%=okTmpClick%>', 1);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                  <gsmsg:write key="cmn.ok" />
                </button>
              </logic:equal>

              <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
                <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('<%=backClick%>');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>

              <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back030%>">
                <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="prj150ButtonPush('<%=backClick%>', 2);">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:notEqual>
            </logic:equal>

            <%-- 外部タブ --%>
            <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back140%>">
              <logic:notEqual name="prj150Form" property="prj150cmdMode" value="0">
                <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
                  <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=okClick%>');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                    <gsmsg:write key="cmn.ok" />
                  </button>
                </logic:equal>
                <logic:equal name="prj150Form" property="movedDspId" value="<%=back020%>">
                  <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prj150GaiButtonPush('<%=okClick%>', 1);">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                    <gsmsg:write key="cmn.ok" />
                  </button>
                </logic:equal>

                <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
                  <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('<%=backClick%>');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                    <gsmsg:write key="cmn.back" />
                  </button>
                </logic:equal>

                <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back030%>">
                  <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="prj150GaiButtonPush('<%=backClick%>', 2);">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                    <gsmsg:write key="cmn.back" />
                  </button>
                </logic:notEqual>
              </logic:notEqual>
            </logic:notEqual>

          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

      <logic:equal name="prj150Form" property="prj150cmdMode" value="<%=modeNaibu%>">
        <%@ include file="/WEB-INF/plugin/project/jsp/prj150_sub01.jsp"%>
      </logic:equal>
      <logic:notEqual name="prj150Form" property="movedDspId" value="<%= back140 %>">
        <logic:equal name="prj150Form" property="prj150cmdMode" value="<%= modeGaibu %>">
          <%@ include file="/WEB-INF/plugin/project/jsp/prj150_sub02.jsp"%>
        </logic:equal>
      </logic:notEqual>

      <div class="footerBtn_block txt_r">
        <%-- 内部タブ --%>
        <logic:equal name="prj150Form" property="prj150cmdMode" value="0">
          <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=okClick%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
          </logic:equal>
          <logic:equal name="prj150Form" property="movedDspId" value="<%=back020%>">
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prj150ButtonPush('<%=okClick%>', 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
          </logic:equal>
          <logic:equal name="prj150Form" property="movedDspId" value="<%=back140%>">
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prj150ButtonPush('<%=okTmpClick%>', 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
          </logic:equal>

          <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('<%=backClick%>');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:equal>

          <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back030%>">
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="prj150ButtonPush('<%=backClick%>', 2);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:notEqual>
        </logic:equal>

        <%-- 外部タブ --%>
        <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back140%>">
          <logic:notEqual name="prj150Form" property="prj150cmdMode" value="0">
            <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="buttonPush('<%=okClick%>');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
            </logic:equal>
            <logic:equal name="prj150Form" property="movedDspId" value="<%=back020%>">
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onclick="prj150GaiButtonPush('<%=okClick%>', 1);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
            </logic:equal>

            <logic:equal name="prj150Form" property="movedDspId" value="<%=back030%>">
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="buttonPush('<%=backClick%>');">
                <img class="btn_classicImg-display"  src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display"  src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:equal>

            <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back030%>">
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onclick="prj150GaiButtonPush('<%=backClick%>', 2);">
                <img class="btn_classicImg-display"  src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:notEqual>
          </logic:notEqual>
        </logic:notEqual>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>

</body>
</html:html>