<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bookmark/js/bmk050.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="bmk.43" /></title>
</head>

<body>
  <html:form action="/bookmark/bmk050">

    <input type="hidden" name="CMD" value="">

    <html:hidden property="bmk050LblSid" />
    <html:hidden property="bmk050ProcMode" />

    <logic:notEmpty name="bmk050Form" property="bmk010delInfSid" scope="request">
      <logic:iterate id="item" name="bmk050Form" property="bmk010delInfSid" scope="request">
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
          <logic:equal name="bmk050Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>">
            <gsmsg:write key="cmn.label.management" />(<gsmsg:write key="bmk.30" />)
           </logic:equal>
          <logic:equal name="bmk050Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>">
            <gsmsg:write key="cmn.label.management" />(<gsmsg:write key="bmk.51" />)
          </logic:equal>
          <logic:equal name="bmk050Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>">
            <gsmsg:write key="cmn.label.management" />(<gsmsg:write key="bmk.34" />)
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="return buttonSubmit('bmk050add','-1', '0');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush('bmk050del');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk050back');">
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
        <logic:equal name="bmk050Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>">
          <%
          targetLabel = "<span class=\"attent1\">" + gsMsg.getMessage(request, "bmk.30") + "</span>";
        %>
          <gsmsg:write key="bmk.bmk050.03" arg0="<%=targetLabel%>" />
        </logic:equal>
        <logic:equal name="bmk050Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>">
          <bean:define id="grpNameStr" name="bmk050Form" property="bmk050GrpName" type="java.lang.String" />
          <%
          targetLabel = "<span class=\"attent1\">" + gsMsg.getMessage(request, "cmn.group")
                    + gsMsg.getMessage(request, "wml.215") + grpNameStr + "</span>";
        %>
          <gsmsg:write key="bmk.bmk050.03" arg0="<%=targetLabel%>" />
        </logic:equal>
        <logic:equal name="bmk050Form" property="bmk010mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>">
          <%
          targetLabel = "<span class=\"attent1\">" + gsMsg.getMessage(request, "bmk.34") + "</span>";
        %>
          <gsmsg:write key="bmk.bmk050.03" arg0="<%=targetLabel%>" />
        </logic:equal>

      </div>

      <table class="table-top" cellpadding="5" cellspacing="0" border="0">

        <tr>
          <th class="txt_c w80">
            <span>
              <gsmsg:write key="cmn.label" />
            </span>
          </th>
          <th class="txt_c w10">
            <span>
              <gsmsg:write key="bmk.bmk050.01" />
            </span>
          </th>
          <th class="w10 no_w">
            <span>
              <gsmsg:write key="cmn.delete" />
            </span>
          </th>
        </tr>

        <logic:notEmpty name="bmk050Form" property="bmk050LabelList">
          <logic:iterate id="labelMdl" name="bmk050Form" property="bmk050LabelList" indexId="idx">

            <tr class="txt_c js_listHover cursor_p" id="<bean:write name="labelMdl" property="blbSid" />">
              <!-- ラベル名 -->
              <td class="txt_l js_listClick">
                  <span class="cl_linkDef">
                    <bean:write name="labelMdl" property="blbName" />
                  </span>
              </td>
              <!-- 数 -->
              <td class="txt_c js_listClick">
                <bean:write name="labelMdl" property="blbCnt" filter="false" />
              </td>
              <!-- チェックボックス -->
              <td class="no_w txt_c js_tableTopCheck">
                <bean:define id="sid" name="labelMdl" property="blbSid" type="java.lang.Integer" />
                <html:multibox property="bmk050DelSidList" value="<%=Integer.toString(sid.intValue())%>" />
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="return buttonSubmit('bmk050add','-1', '0');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="return buttonPush('bmk050del');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk050back');">
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