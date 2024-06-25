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
<script src="../ipkanri/js/ipk090.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="ipk.ipk090.1" /></title>
</head>
<body>
  <html:form action="/ipkanri/ipk090">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <html:hidden property="CMD" />
    <html:hidden property="backScreen" />
    <html:hidden property="editMode" />
    <html:hidden property="specKbn" />
    <html:hidden property="ismSid" />
    <input type="hidden" name="helpPrm" value="<bean:write name='ipk090Form' property='ipk090helpMode' />">


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
          <span class="pageTitle_subFont-plugin">
            <gsmsg:write key="cmn.ipkanri" />
          </span>
          <gsmsg:write key="ipk.10" />
        </li>
        <li>
          <div>
            <button type="button" name="add" value="<gsmsg:write key="cmn.add" />" onClick="ipk090ButtonPush('specMstEdit', '1', '');" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>

            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk090Return');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper w80 mrl_auto">

      <div class="wrapper">
        <ul class="tabHeader w100">
          <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none" id="tab1" onclick="changeMode('1');">CPU</li>
          <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2" onclick="changeMode('2');">
            <gsmsg:write key="cmn.memory" />
          </li>
          <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab3" onclick="changeMode('3');">H D</li>
        </ul>

        <table id="tab1_table" class="table-top w100 mt0">

          <tr>
            <th class="w70">
              <span>
                <gsmsg:write key="cmn.name4" />
              </span>
            </th>
            <th class="w30">
              <span>
                <gsmsg:write key="cmn.memo" />
              </span>
            </th>
          </tr>

          <logic:iterate id="param" name="ipk090Form" property="specInfList" indexId="idx">
            <tr class="js_listHover cursor_p" id="<bean:write name="param" property="ismSid" />">
              <td class="js_listClick">
                <span class="cl_linkDef"><bean:write name="param" property="ipk100name" filter="false" /></span>
              </td>
              <td class="js_listClick">
                <bean:write name="param" property="ipk100biko" filter="false" />
              </td>
            </tr>
          </logic:iterate>
        </table>

        <table id="tab2_table" class="table-top display_none w100 mt0">
          <tr>
            <th class="w70">
              <span>
                <gsmsg:write key="cmn.name4" />
              </span>
            </th>
            <th class="w30">
              <span>
                <gsmsg:write key="cmn.memo" />
              </span>
            </th>
          </tr>

          <logic:iterate id="param" name="ipk090Form" property="specInfList" indexId="idx">

            <tr class="js_listHover cursor_p" id="<bean:write name="param" property="ismSid" />">
              <td class="js_listClick">
                 <span class="cl_linkDef"><bean:write name="param" property="ipk100name" filter="false" /></span>
              </td>
              <td class="js_listClick">
                <bean:write name="param" property="ipk100biko" filter="false" />
              </td>
            </tr>
          </logic:iterate>
        </table>

        <table id="tab3_table" class="table-top display_none w100 mt0">
          <tr>
            <th class="w70">
              <span>
                <gsmsg:write key="cmn.name4" />
              </span>
            </th>
            <th class="w30">
              <span>
                <gsmsg:write key="cmn.memo" />
              </span>
            </th>
          </tr>
          <logic:iterate id="param" name="ipk090Form" property="specInfList" indexId="idx">
            <tr class="js_listHover cursor_p" id="<bean:write name="param" property="ismSid" />">
              <td class="js_listClick">
                <span class="cl_linkDef"><bean:write name="param" property="ipk100name" filter="false" /></span>
              </td>
              <td class="js_listClick">
                <bean:write name="param" property="ipk100biko" filter="false" />
              </td>
            </tr>
          </logic:iterate>
        </table>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>