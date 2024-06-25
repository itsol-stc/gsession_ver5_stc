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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="ipk.12" /></title>
</head>
<body>
  <html:form action="/ipkanri/ipk010">
    <input type="hidden" name="CMD" value="search">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../ipkanri/images/classic/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
          <img class="header_pluginImg" src="../ipkanri/images/original/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
        </li>
        <li>
          <gsmsg:write key="cmn.ipkanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="ipk.12" />
        </li>
        <li>
          <div>
            <logic:equal value="true" name="ipk010Form" property="allAdm">
              <button type="button" class="baseBtn" name="new" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('networkAdd', '');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
          </div>
        </li>
      </ul>
    </div>

    <html:hidden property="netSid" />

    <div class="txt_r">
      <html:text styleClass="wp200" name="ipk010Form" property="ipk070KeyWord" maxlength="50" />
      <button type="submit" name="btn_search" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush2('search');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </div>
    <div class="wrapper">
      <table class="table-top">
        <tr>
          <th class="txt_c no_w">
            <span>
              <gsmsg:write key="ipk.1" />
            </span>
          </th>
          <th class="txt_c no_w">
            <span>
              <gsmsg:write key="ipk.2" />
            </span>
          </th>
          <th class="txt_c no_w">
            <span>
              <gsmsg:write key="ipk.3" />
            </span>
          </th>
          <th class="txt_c no_w">
            <span>
              <gsmsg:write key="cmn.comment" />
            </span>
          </th>
          <th class="txt_c no_w">
            <span>
              <gsmsg:write key="cmn.detail" />
            </span>
          </th>
        </tr>
        <logic:notEmpty name="ipk010Form" property="ipkNetList">
          <logic:iterate id="param" name="ipk010Form" property="ipkNetList" indexId="idx">
            <tr>
              <td class="txt_l no_w">
                <a href="#" onClick="buttonPush('ipList', '<bean:write name="param" property="netSid" />');">
                  <img class="btn_classicImg-display" src="../ipkanri/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.ipkanri" />">
                  <img class="btn_originalImg-display" src="../ipkanri/images/original/icon_network.png" alt="<gsmsg:write key="cmn.ipkanri" />">
                  <bean:write name="param" property="netName" />
                </a>
              </td>
              <td class="no_w">
                <bean:write name="param" property="netNetad" />
              </td>
              <td class="no_w">
                <bean:write name="param" property="netSabnet" />
              </td>
              <td class="txt_t">
                <bean:write name="param" property="netMsg" filter="false" />
              </td>
              <td class="txt_c txt_m w5 no_w">
                <div>
                  <button type="button" class="baseBtn" name="edit" value="<gsmsg:write key="cmn.detail" />" onClick="buttonPush('networkEdit', '<bean:write name="param" property="netSid" />');">
                    <gsmsg:write key="cmn.detail" />
                  </button>
                </div>
                <logic:equal name="param" property="netAdm" value="true">
                  <div>
                    <button type="button" class="baseBtn mt10" name="delete" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('networkDelete', '<bean:write name="param" property="netSid" />');">
                      <gsmsg:write key="cmn.delete" />
                    </button>
                  </div>
                </logic:equal>
              </td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
      </table>
    </div>
    <div class="txt_r">
      <logic:equal value="true" name="ipk010Form" property="allAdm">
        <button type="button" class="baseBtn" name="new" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('networkAdd', '');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt=" <gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
      </logic:equal>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>