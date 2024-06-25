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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/jquery-3.3.1.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>

<link rel=stylesheet href='../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>" type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="bmk.43" /></title>
</head>

<body>
  <html:form action="/bookmark/bmk090">

    <input type="hidden" name="CMD" value="">
    <html:hidden name="bmk090Form" property="bmk090GrpName" />
    <html:hidden name="bmk090Form" property="bmk090initFlg" />

    <%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp"%>

    <logic:notEqual name="bmk090Form" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER)%>">
      <html:hidden name="bmk090Form" property="bmk090GroupSid" />
      <logic:notEmpty name="bmk090Form" property="bmk090UserSid">
        <logic:iterate id="usid" name="bmk090Form" property="bmk090UserSid">
          <input type="hidden" name="bmk090UserSid" value="<bean:write name="usid" />">
        </logic:iterate>
      </logic:notEmpty>
    </logic:notEqual>

    <logic:notEqual name="bmk090Form" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP)%>">
      <logic:notEmpty name="bmk090Form" property="bmk090GrpSid">
        <logic:iterate id="gsid" name="bmk090Form" property="bmk090GrpSid">
          <input type="hidden" name="bmk090GrpSid" value="<bean:write name="gsid" />">
        </logic:iterate>
      </logic:notEmpty>
    </logic:notEqual>

    <logic:notEmpty name="bmk090Form" property="bmk010delInfSid" scope="request">
      <logic:iterate id="item" name="bmk090Form" property="bmk010delInfSid" scope="request">
        <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bookmark/images/classic/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />">
          <img class="header_pluginImg" src="../bookmark/images/original/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />"
        </li>
        <li>
          <gsmsg:write key="bmk.43" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.setting.permissions" />(<gsmsg:write key="bmk.51" />)
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('bmk090kakunin');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk090back');">
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

      <div class="txt_l">
        <bean:define id="grpNameStr" name="bmk090Form" property="bmk090GrpName" type="java.lang.String" />
        <%
          jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        %>
        <%
          String grpMsg = "<span class=\"attent1\">" + gsMsg.getMessage(request, "cmn.group")
                  + gsMsg.getMessage(request, "wml.215") + grpNameStr + "</span>";
        %>
        <gsmsg:write key="bmk.44" arg0="<%=grpMsg%>" />
      </div>

      <table class="table-left" cellpadding="5">
        <!-- グループブックマーク編集権限 -->
        <tr>
          <th class="w15 no_w">
            <span>
              <gsmsg:write key="cmn.edit.permissions" />
            </span>
          </th>
          <td class="txt_l no_w">
            <table class="w100" cellpadding="0" cellspacing="0" border="0">
              <tr class="border_none">
                <td class="lxt_l w100 border_none">
                  <span class="verAlignMid">
                    <html:radio name="bmk090Form" styleId="bmk090GrpEditKbn_01" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ADMIN)%>" onclick="buttonPush('redraw');" /><label for="bmk090GrpEditKbn_01"><gsmsg:write key="bmk.50" /></label>
                    <html:radio name="bmk090Form" styleId="bmk090GrpEditKbn_02" styleClass="ml10" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP)%>" onclick="buttonPush('redraw');" /><label for="bmk090GrpEditKbn_02"><gsmsg:write key="group.designation" /> </label>
                    <html:radio name="bmk090Form" styleId="bmk090GrpEditKbn_03" styleClass="ml10" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER)%>" onclick="buttonPush('redraw');" /><label for="bmk090GrpEditKbn_03"><gsmsg:write key="cmn.user.specified" /></label>
                    <html:radio name="bmk090Form" styleId="bmk090GrpEditKbn_04" styleClass="ml10" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ALL)%>" onclick="buttonPush('redraw');" /><label for="bmk090GrpEditKbn_04"><gsmsg:write key="bmk.33" /></label>
                  </span>
                </td>
              </tr>
              <tr class="border_none">
                <td class="txt_l w100 border_none">
                  <logic:equal name="bmk090Form" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ADMIN)%>">
                    <div>
                      <gsmsg:write key="bmk.bmk090.01" />
                    </div>
                  </logic:equal>
                  <logic:equal name="bmk090Form" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_ALL)%>">
                    <div>
                      <gsmsg:write key="bmk.19" />
                    </div>
                  </logic:equal>

                  <!-- グループブックマーク編集権限：グループ指定 -->
                  <logic:equal name="bmk090Form" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_GROUP)%>">
                    <ui:usrgrpselector name="bmk090Form" property="bmk090GrpSidUI" styleClass="hp215" />
                  </logic:equal>

                  <!-- グループブックマーク編集権限：ユーザ指定 -->
                  <logic:equal name="bmk090Form" property="bmk090GrpEditKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.EDIT_POW_USER)%>">
                    <ui:usrgrpselector name="bmk090Form" property="bmk090UserSidUI" styleClass="hp215" />
                  </logic:equal>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="return buttonPush('bmk090kakunin');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk090back');">
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