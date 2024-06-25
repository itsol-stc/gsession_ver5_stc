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
<title>GROUPSESSION <gsmsg:write key="bmk.43" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>

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

  <html:form action="/bookmark/bmk060">

    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="helpPrm" value="<bean:write name="bmk060Form" property="bmk050ProcMode" />">

    <logic:notEmpty name="bmk060Form" property="bmk050DelSidList" scope="request">
      <logic:iterate id="delSid" name="bmk060Form" property="bmk050DelSidList" scope="request">
        <input type="hidden" name="bmk050DelSidList" value="<bean:write name="delSid"/>">
      </logic:iterate>
    </logic:notEmpty>

    <html:hidden name="bmk060Form" property="bmk050LblSid" />
    <html:hidden name="bmk060Form" property="bmk050ProcMode" />

    <logic:notEmpty name="bmk060Form" property="bmk010delInfSid" scope="request">
      <logic:iterate id="item" name="bmk060Form" property="bmk010delInfSid" scope="request">
        <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp"%>

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
          <logic:equal name="bmk060Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>">
            <gsmsg:write key="cmn.entry.label" />(<gsmsg:write key="bmk.30" />)
        </logic:equal>
          <logic:equal name="bmk060Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>">
            <gsmsg:write key="cmn.entry.label" />(<gsmsg:write key="bmk.51" />)
        </logic:equal>
          <logic:equal name="bmk060Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>">
            <gsmsg:write key="cmn.entry.label" />(<gsmsg:write key="bmk.34" />)
        </logic:equal>
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('bmk060ok');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:equal name="bmk060Form" property="bmk050ProcMode" value="1">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="return buttonPush('bmk060del');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('bmk060back');">
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

        <%
          jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        %>
        <%
          String targetLabel = "";
        %>
        <logic:equal name="bmk060Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>">
          <%
            targetLabel = "<span class=\"attent1\">" + gsMsg.getMessage(request, "bmk.30") + "</span>";
          %>
          <gsmsg:write key="bmk.45" arg0="<%=targetLabel%>" />
        </logic:equal>
        <logic:equal name="bmk060Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>">
          <bean:define id="grpNameStr" name="bmk060Form" property="bmk050GrpName" type="java.lang.String" />
          <%
            targetLabel = "<span class=\"attent1\">" + gsMsg.getMessage(request, "cmn.group")
                      + gsMsg.getMessage(request, "wml.215") + grpNameStr + "</span>";
          %>
          <gsmsg:write key="bmk.45" arg0="<%=targetLabel%>" />
        </logic:equal>
        <logic:equal name="bmk060Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>">
          <%
            targetLabel = "<span class=\"attent1\">" + gsMsg.getMessage(request, "bmk.34") + "</span>";
          %>
          <gsmsg:write key="bmk.45" arg0="<%=targetLabel%>" />
        </logic:equal>
      </div>

      <table class="table-left" border="0" cellpadding="5">
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.label" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div>
              <html:text name="bmk060Form" property="bmk060LblName" maxlength="20" styleClass="wp350" />
            </div>
            <div>
              <div class="verAlignMid mt5">
                <html:radio name="bmk060Form" property="bmk060LblKbn" styleId="bmk060LblKbn0" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.LABEL_TOGO_NO)%>" onclick="buttonPush('redraw');" /><label for="bmk060LblKbn0"><gsmsg:write key="bmk.bmk060.01" /></label>
                <html:radio name="bmk060Form" styleClass="ml10" property="bmk060LblKbn" styleId="bmk060LblKbn1" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.LABEL_TOGO_YES)%>" onclick="buttonPush('redraw');" /><label for="bmk060LblKbn1"><gsmsg:write key="bmk.bmk060.02" /></label>
              </div>
            </div>

            <logic:equal name="bmk060Form" property="bmk060LblKbn" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.LABEL_TOGO_YES)%>">
              <ui:multiselector name="bmk060Form" property="bmk060LabelListUI" styleClass="hp215" />
            </logic:equal>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('bmk060ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="bmk060Form" property="bmk050ProcMode" value="1">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="return buttonPush('bmk060del');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('bmk060back');">
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