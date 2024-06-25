<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.ConfigBundle" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<% String gsurl = jp.groupsession.v2.cmn.GSConst.GS_HOMEPAGE_URL; %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="anp.plugin"/></title>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" Content="no-cache">
<META HTTP-EQUIV="Cache-Control" Content="no-cache">
<META HTTP-EQUIV="Expires" Content="-1">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/popup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/common.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body>

<html:form action="/anpi/anp999" target="_self">
<html:hidden property="anpiSid" />
<html:hidden property="userSid" />


<div id="contair">
  <div class="information_body">
    <div class="information_top">
      <gsmsg:write key="cmn.warning" />
    </div>
    <div class="information_middle bgC_body">
      <table class="tl0">
        <tr>
          <td class="pl20">
            <logic:equal name="anp999Form" property="icon" value="<%= String.valueOf(jp.groupsession.v2.anp.anp999.Anp999Form.ICON_WARN) %>">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_warn_2.gif" alt="<gsmsg:write key="cmn.warning" />">
              <img class="header_pluginImg" src="../common/images/original/icon_warn_63.png" alt="<gsmsg:write key="cmn.warning" />">
            </logic:equal>
            <logic:equal name="anp999Form" property="icon" value="<%= String.valueOf(jp.groupsession.v2.anp.anp999.Anp999Form.ICON_INFO) %>">
              <img class="header_pluginImg-classic" src="../common/images/classic/icon_info_2.gif" alt="<gsmsg:write key="cmn.information" />">
              <img class="header_pluginImg" src="../common/images/original/icon_info_63.png" alt="<gsmsg:write key="cmn.information" />">
            </logic:equal>


          </td>
          <td class="information_messageArea">
            <span class="fs_16">
              <bean:write name="anp999Form" property="message" filter="false"/></div>
            </span>
          </td>
        </tr>
      </table>
    </div>
    <div class="information_middle bgC_body">
      <div class="txt_c">
         <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.open('about:blank','_self').close();">
           <img class="header_pluginImg-classic" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.cancel" />">
           <img class="header_pluginImg" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.cancel" />">
           <gsmsg:write key="cmn.close" />
         </button>
      </div>
    </div>
    <div class="information_bottom bgC_body"></div>
  </div>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>



</html:html>