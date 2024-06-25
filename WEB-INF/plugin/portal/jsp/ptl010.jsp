<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.man.GSConstMain" %>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%=GSConst.VERSION_PARAM%>">
<!--  日本語化 -->
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" type="text/css" href="../common/js/clockpiker/jquery-clockpicker.min.css?<%=GSConst.VERSION_PARAM%>">
<script type="text/javascript" src="../common/js/clockpiker/jquery-clockpicker.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jtooltip_main.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../portal/css/ptl010_bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/ptl010_layout.css?<%= GSConst.VERSION_PARAM %>20210805' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/portal.css?<%= GSConst.VERSION_PARAM %>2' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<logic:notEmpty name="ptl010Form" property="ptl010scriptPathList">
  <logic:iterate id="jsPath" name="ptl010Form" property="ptl010scriptPathList">
    <script src="<bean:write name="jsPath" />"></script>
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ptl010Form" property="ptl010stylePath">
  <logic:iterate id="cssPath" name="ptl010Form" property="ptl010stylePath">
    <link rel=stylesheet href='<bean:write name="cssPath" />' type='text/css'>
  </logic:iterate>
</logic:notEmpty>

<script type="text/javascript">
<!--

$(function() {

  <logic:notEmpty name="ptl010Form" property="ptl010allList">
  <logic:iterate id="screenAllMdl" name="ptl010Form" property="ptl010allList" indexId="idx">

    var screenId<bean:write name="idx" /> = '';
    var url<bean:write name="idx" /> = "";

    <logic:equal name="screenAllMdl" property="ptpType" value="1">
    screenId<bean:write name="idx" /> = '<bean:write name="screenAllMdl" property="pluginId" />' + '_' + '<bean:write name="screenAllMdl" property="screenId" />'
    url<bean:write name="idx" /> = '<bean:write name="screenAllMdl" property="screenUrl" />';
    if (url<bean:write name="idx" /> != "") {
        $.ajaxSetup({async:true});
        $.post(url<bean:write name="idx" />, function(data){
            if ($('#' + screenId<bean:write name="idx" />)[0] != null) {
<logic:equal name="screenAllMdl" property="loadScript" value="true">
                $('#' + screenId<bean:write name="idx" />).html(data);
</logic:equal>
<logic:notEqual name="screenAllMdl" property="loadScript" value="true">
                $('#' + screenId<bean:write name="idx" />)[0].innerHTML = data;
</logic:notEqual>
            }
        });
    }
    </logic:equal>
    <logic:equal name="screenAllMdl" property="ptpType" value="2">
    screenId<bean:write name="idx" /> = '<bean:write name="screenAllMdl" property="pluginId" />' + '_' + '<bean:write name="screenAllMdl" property="itemId" />'
    url<bean:write name="idx" /> = '<bean:write name="screenAllMdl" property="screenUrl" filter="false" />';
    if (url<bean:write name="idx" /> != "") {
        $.ajaxSetup({async:true});
        $.post(url<bean:write name="idx" />, function(data2){
            if ($('#' + screenId<bean:write name="idx" />)[0] != null) {
                $('#' + screenId<bean:write name="idx" />).html(data2);
            }
        });
    }
    </logic:equal>
  </logic:iterate>
  </logic:notEmpty>
});

  <logic:notEqual name="ptl010Form" property="ptl010Reload" value="0">
    var reloadinterval = <bean:write name="ptl010Form" property="ptl010Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
  </logic:notEqual>
-->
</script>


<title>GROUPSESSION <gsmsg:write key="ptl.2" /></title>
</head>

<body onload="initArea();">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!--BODY -->

<div class="pageTitle pluginPortlet">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_main.png">
      <img class="header_pluginImg" src="../main/images/original/header_main.png">
    </li>
    <li><gsmsg:write key="cmn.main" /></li>
    <li class="pageTitle_subFont"><bean:write name="ptl010Form" property="dspPtlName" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onClick="buttonPush2('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
          <gsmsg:write key="cmn.reload" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w100">
  <div class="portalTabList">
    <ul>
      <bean:define id="selectPtlSid" name="ptl010Form" property="dspPtlSid" />
      <% String sltPtlSid = (selectPtlSid).toString(); %>
      <logic:notEmpty name="ptl010Form" property="ptl010ptlList">
        <logic:iterate id="ptlMdl" name="ptl010Form" property="ptl010ptlList">
          <logic:equal name="ptlMdl" property="ptlSid" value="<%= sltPtlSid %>">
            <li class="portalTabList_tab-forcus cl_fontOutline">
              <span>
                <bean:write name="ptlMdl" property="ptlName" />
              </span>
            </li>
          </logic:equal>
          <logic:notEqual name="ptlMdl" property="ptlSid" value="<%= sltPtlSid %>">
            <li class="portalTabList_tab-nonforcus cl_fontOutline">
              <a href="#!" onClick="return movePortal(<bean:write name="ptlMdl" property="ptlSid" />);">
                <bean:write name="ptlMdl" property="ptlName" />
              </a>
            </li>
          </logic:notEqual>
        </logic:iterate>
      </logic:notEmpty>
    </ul>
  </div>

<html:form action="/portal/ptl010">
<input type="hidden" name="CMD" value="">
<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl010areaTop" />
<html:hidden property="ptl010areaBottom" />
<html:hidden property="ptl010areaLeft" />
<html:hidden property="ptl010areaCenter" />
<html:hidden property="ptl010areaRight" />
<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
</html:form>

<table class="w100">
  <tr>
    <td id="mainScreenListTop" class="w100" colspan="3">
    <logic:iterate id="topMdl" name="ptl010Form" property="ptl010topList">
      <logic:equal name="topMdl" property="ptpType" value="3">
        <div class="pluginPortlet"><%@ include file="/WEB-INF/plugin/portal/jsp/ptl010_information.jsp" %></div>
      </logic:equal>
      <logic:equal name="topMdl" property="ptpType" value="2">
        <div class="pluginPortlet" id="<bean:write name='topMdl' property='itemId' />"><span id="<bean:write name='topMdl' property='pluginId' />_<bean:write name='topMdl' property='itemId' />"><bean:write name="topMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
      </logic:equal>
      <logic:equal name="topMdl" property="ptpType" value="1">
        <div class="pluginPortlet" id="<bean:write name='topMdl' property='itemId' />"><span id="<bean:write name='topMdl' property='pluginId' />_<bean:write name='topMdl' property='screenId' />"><bean:write name="topMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
      </logic:equal>

      <logic:equal name="topMdl" property="ptpType" value="0">
        <!-- ポートレット -->
        <bean:define id="border" name="topMdl" property="ptlBorderKbn" />
        <% String borderKbn = (border).toString(); %>
        <% String className = "";%>
        <% if (borderKbn.equals("0")) { %>
          <table class="table-top w100 mb0">
            <tr>
              <th class="txt_l">
                <bean:write name="topMdl" property="ptlTitle" />
              </th>
            </tr>
          </table>
        <% } else { %>
        <%     className = "table-noBorder"; %>
        <% } %>
        <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='topMdl' property='itemId' />">
          <tr>
            <td class="bgC_tableCell">
              <logic:equal name="topMdl" property="ptlType" value="0">
                <bean:write name="topMdl" property="ptlContent" filter="false"/>
              </logic:equal>
              <logic:notEqual name="topMdl" property="ptlType" value="0">
                  <bean:write name="topMdl" property="ptlContent" filter="false"/>
              </logic:notEqual>
            </td>
          </tr>
        </table>
      </logic:equal>
    </logic:iterate>
    </td>
  </tr>
  <tr>
    <td>
      <table class="w100">
        <tr>
          <td id="mainScreenListLeft" class="js_column3 txt_t pr10 mwp100">
            <logic:notEmpty name="ptl010Form" property="ptl010leftList">
              <logic:iterate id="leftMdl" name="ptl010Form" property="ptl010leftList">
                <logic:equal name="leftMdl" property="ptpType" value="3">
                  <div class="pluginPortlet"><%@ include file="/WEB-INF/plugin/portal/jsp/ptl010_information.jsp" %></div>
                </logic:equal>
                <logic:equal name="leftMdl" property="ptpType" value="2">
                  <div class="pluginPortlet" id="<bean:write name='leftMdl' property='itemId' />">
                    <span id="<bean:write name='leftMdl' property='pluginId' />_<bean:write name='leftMdl' property='itemId' />"><bean:write name="leftMdl" property="pluginName" /><gsmsg:write key="main.10" /></span>
                  </div>
                </logic:equal>
                <logic:equal name="leftMdl" property="ptpType" value="1">
                  <div class="pluginPortlet" id="<bean:write name='leftMdl' property='itemId' />"><span id="<bean:write name='leftMdl' property='pluginId' />_<bean:write name='leftMdl' property='screenId' />"><bean:write name="leftMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
                </logic:equal>
                <logic:equal name="leftMdl" property="ptpType" value="0">
                  <!-- ポートレット -->
                  <bean:define id="border" name="leftMdl" property="ptlBorderKbn" />
                  <% String borderKbn = (border).toString(); %>
                  <% String className = "";%>
                  <% if (borderKbn.equals("0")) { %>
                  <table class="table-top w100 mb0">
                    <tr>
                      <th class="txt_l">
                        <bean:write name="leftMdl" property="ptlTitle" />
                      </th>
                    </tr>
                  </table>
                  <% } else { %>
                  <%     className = "table-noBorder"; %>
                  <% } %>
                  <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='leftMdl' property='itemId' />">
                    <tr>
                      <td class="bgC_tableCell">
                        <logic:equal name="leftMdl" property="ptlType" value="0">
                          <bean:write name="leftMdl" property="ptlContent" filter="false"/>
                        </logic:equal>
                        <logic:notEqual name="leftMdl" property="ptlType" value="0">
                            <bean:write name="leftMdl" property="ptlContent" filter="false"/>
                        </logic:notEqual>
                      </td>
                    </tr>
                  </table>
                </logic:equal>
              </logic:iterate>
            </logic:notEmpty>
          </td>
          <td id="mainScreenListCenter" class="js_column3 txt_t pr10 mwp100">
            <logic:notEmpty name="ptl010Form" property="ptl010centerList">
              <logic:iterate id="centerMdl" name="ptl010Form" property="ptl010centerList">
                <logic:equal name="centerMdl" property="ptpType" value="3">
                  <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl010_information.jsp" %></div>
                </logic:equal>
                <logic:equal name="centerMdl" property="ptpType" value="2">
                  <div class="pluginPortlet" id="<bean:write name='centerMdl' property='itemId' />">
                    <span class="pluginPortlet" id="<bean:write name='centerMdl' property='pluginId' />_<bean:write name='centerMdl' property='itemId' />"><bean:write name="centerMdl" property="pluginName" /><gsmsg:write key="main.10" /></span>
                  </div>
                </logic:equal>
                <logic:equal name="centerMdl" property="ptpType" value="1">
                  <div class="pluginPortlet" id="<bean:write name='centerMdl' property='itemId' />"><span id="<bean:write name='centerMdl' property='pluginId' />_<bean:write name='centerMdl' property='screenId' />"><bean:write name="centerMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
                </logic:equal>
                <logic:equal name="centerMdl" property="ptpType" value="0">
                  <!-- ポートレット -->
                  <bean:define id="border" name="centerMdl" property="ptlBorderKbn" />
                  <% String borderKbn = (border).toString(); %>
                  <% String className = "";%>
                  <% if (borderKbn.equals("0")) { %>
                  <table class="table-top w100 mb0">
                    <tr>
                      <th class="txt_l">
                        <bean:write name="centerMdl" property="ptlTitle" />
                      </th>
                    </tr>
                  </table>
                  <% } else { %>
                  <%     className = "table-noBorder"; %>
                  <% } %>
                  <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='centerMdl' property='itemId' />">
                    <tr>
                      <td class="bgC_tableCell">
                        <logic:equal name="centerMdl" property="ptlType" value="0">
                            <bean:write name="centerMdl" property="ptlContent" filter="false"/>
                        </logic:equal>
                        <logic:notEqual name="centerMdl" property="ptlType" value="0">
                            <bean:write name="centerMdl" property="ptlContent" filter="false"/>
                        </logic:notEqual>
                      </td>
                    </tr>
                  </table>
                </logic:equal>
              </logic:iterate>
            </logic:notEmpty>
          </td>
          <td id="mainScreenListRight" class="js_column3 txt_t pr10 mwp100">
            <logic:notEmpty name="ptl010Form" property="ptl010rightList">
              <logic:iterate id="rightMdl" name="ptl010Form" property="ptl010rightList">
                <logic:equal name="rightMdl" property="ptpType" value="3">
                  <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl010_information.jsp" %></div>
                </logic:equal>
                <logic:equal name="rightMdl" property="ptpType" value="2">
                  <div class="pluginPortlet" id="<bean:write name='rightMdl' property='itemId' />">
                    <span id="<bean:write name='rightMdl' property='pluginId' />_<bean:write name='rightMdl' property='itemId' />"><bean:write name="rightMdl" property="pluginName" /><gsmsg:write key="main.10" /></span>
                  </div>
                </logic:equal>
                <logic:equal name="rightMdl" property="ptpType" value="1">
                  <div class="pluginPortlet" id="<bean:write name='rightMdl' property='itemId' />"><span id="<bean:write name='rightMdl' property='pluginId' />_<bean:write name='rightMdl' property='screenId' />"><bean:write name="rightMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
                </logic:equal>
                <logic:equal name="rightMdl" property="ptpType" value="0">
                  <!-- ポートレット -->
                  <bean:define id="border" name="rightMdl" property="ptlBorderKbn" />
                  <% String borderKbn = (border).toString(); %>
                  <% String className = "";%>
                  <% if (borderKbn.equals("0")) { %>
                  <table class="table-top w100 mb0">
                    <tr>
                      <th class="txt_l">
                        <bean:write name="rightMdl" property="ptlTitle" />
                      </th>
                    </tr>
                  </table>
                  <% } else { %>
                  <%     className = "table-noBorder"; %>
                  <% } %>
                  <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='rightMdl' property='itemId' />">
                    <tr>
                      <td class="bgC_tableCell">
                        <logic:equal name="rightMdl" property="ptlType" value="0">
                            <bean:write name="rightMdl" property="ptlContent" filter="false"/>
                        </logic:equal>
                        <logic:notEqual name="rightMdl" property="ptlType" value="0">
                            <bean:write name="rightMdl" property="ptlContent" filter="false"/>
                        </logic:notEqual>
                      </td>
                    </tr>
                  </table>
                </logic:equal>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td id="mainScreenListBottom" class="w100" colspan="3">
    <logic:notEmpty name="ptl010Form" property="ptl010bottomList">
    <logic:iterate id="bottomMdl" name="ptl010Form" property="ptl010bottomList">
      <logic:equal name="bottomMdl" property="ptpType" value="3">
        <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl010_information.jsp" %></div>
      </logic:equal>
      <logic:equal name="bottomMdl" property="ptpType" value="2">
        <div class="pluginPortlet"  id="<bean:write name='bottomMdl' property='itemId' />"><span id="<bean:write name='bottomMdl' property='pluginId' />_<bean:write name='bottomMdl' property='itemId' />"><bean:write name="bottomMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
      </logic:equal>
      <logic:equal name="bottomMdl" property="ptpType" value="1">
        <div class="pluginPortlet"  id="<bean:write name='bottomMdl' property='itemId' />"><span id="<bean:write name='bottomMdl' property='pluginId' />_<bean:write name='bottomMdl' property='screenId' />"><bean:write name="bottomMdl" property="pluginName" /><gsmsg:write key="main.10" /></span></div>
      </logic:equal>

      <logic:equal name="bottomMdl" property="ptpType" value="0">
        <!-- ポートレット -->
        <bean:define id="border" name="bottomMdl" property="ptlBorderKbn" />
        <% String borderKbn = (border).toString(); %>
        <% String className = "";%>
        <% if (borderKbn.equals("0")) { %>
          <table class="table-top w100 mb0">
            <tr>
              <th class="txt_l">
                <bean:write name="bottomMdl" property="ptlTitle" />
              </th>
            </tr>
          </table>
        <% } else { %>
        <%     className = "table-noBorder"; %>
        <% } %>
        <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='bottomMdl' property='itemId' />" width="100%">
          <tr>
            <td class="bgC_tableCell">
              <logic:equal name="bottomMdl" property="ptlType" value="0">
                  <bean:write name="bottomMdl" property="ptlContent" filter="false"/>
              </logic:equal>
              <logic:notEqual name="bottomMdl" property="ptlType" value="0">
                  <bean:write name="bottomMdl" property="ptlContent" filter="false"/>
              </logic:notEqual>
            </td>
          </tr>
        </table>
      </logic:equal>
    </logic:iterate>
    </logic:notEmpty>
    </td>
  </tr>
</table>
  <logic:equal name="ptl010Form" property="ptl010ptlAdminFlg" value="true">
    <div class="mt20 txt_r">
      <button class="baseBtn" type="button" value="<gsmsg:write key="ptl.7" />" onClick="movePortalSetting();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_portal.png" alt="<gsmsg:write key="ptl.7" />">
        <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.7" />">
        <gsmsg:write key="ptl.7" />
      </button>
    </div>
  </logic:equal>

</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>
