<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.sortable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jtooltip_main.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../portal/js/ptl070.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/search.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../portal/css/ptl010_bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/ptl010_layout.css?<%= GSConst.VERSION_PARAM %>20210805' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../main/css/main.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/portal.css?<%= GSConst.VERSION_PARAM %>20210806' type='text/css'>
<theme:css filename="theme.css"/>

<logic:notEmpty name="ptl070Form" property="jsList">
  <logic:iterate id="scriptPath" name="ptl070Form" property="jsList">
    <script src="<bean:write name="scriptPath" />"></script>
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="ptl070Form" property="cssList">
  <logic:iterate id="stylePath" name="ptl070Form" property="cssList">
    <link rel=stylesheet href='<bean:write name="stylePath" />' type='text/css'>
  </logic:iterate>
</logic:notEmpty>

<script type="text/javascript">
<!--

function init() {

  var url = '';
  var pars = '';
  var screenId = '';

  <logic:notEmpty name="ptl070Form" property="urlList">
  <logic:iterate id="screenMdl" name="ptl070Form" property="urlList">

    <logic:equal name="screenMdl" property="partsKbn" value="1">
    ptpItemid = '<bean:write name="screenMdl" property="pluginId" />' + '_' + '<bean:write name="screenMdl" property="id" />'
    url = '<bean:write name="screenMdl" property="screenUrl" />';
    if (url != "") {
      $('#' + ptpItemid).load(url);
    }
    </logic:equal>

    <logic:equal name="screenMdl" property="partsKbn" value="2">
    ptpItemid = '<bean:write name="screenMdl" property="ptpItemid" />'
    url = '<bean:write name="screenMdl" property="screenUrl" />';
    if (url != "") {
      $('#' + ptpItemid).load(url);
    }
    </logic:equal>

  </logic:iterate>
  </logic:notEmpty>

  window.scroll( 0, 0 );
}

-->
</script>

<title>GROUPSESSION <gsmsg:write key="ptl.6" /></title>
</head>

<!-- body -->
<body onload="init();initArea();">
<html:form action="/portal/ptl070">
<input type="hidden" name="CMD" value="init">
<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl070areaTop" />
<html:hidden property="ptl070areaBottom" />
<html:hidden property="ptl070areaLeft" />
<html:hidden property="ptl070areaCenter" />
<html:hidden property="ptl070areaRight" />
</html:form>

<div class="pageTitle pluginPortlet">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../main/images/classic/header_main.png">
      <img class="header_pluginImg" src="../main/images/original/header_main.png">
    </li>
    <li><gsmsg:write key="cmn.main" /></li>
    <li class="pageTitle_subFont"><gsmsg:write key="ptl.ptl070.1" /></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper w100">
<table class="w100 preview_content">
  <tr>
    <!-- 上 -->
    <td id="mainScreenListTop" class="w100" colspan="3">
      <logic:notEmpty name="ptl070Form" property="ptl070topList">
        <logic:iterate id="topModel" name="ptl070Form" property="ptl070topList">
          <logic:equal name="topModel" property="partsKbn" value="3">
            <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl070_information.jsp" %></div>
          </logic:equal>
          <logic:equal name="topModel" property="partsKbn" value="2">
            <div class="pluginPortlet" >
              <span id="<bean:write name="topModel" property="ptpItemid" />"><bean:write name="topModel" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
            </div>
          </logic:equal>
          <logic:equal name="topModel" property="partsKbn" value="1">
            <div class="pluginPortlet" >
              <span id="<bean:write name='topModel' property='pluginId' />_<bean:write name='topModel' property='id' />"><bean:write name="topModel" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
            </div>
          </logic:equal>

          <logic:equal name="topModel" property="partsKbn" value="0">
            <!-- ポートレット -->
            <bean:define id="border" name="topModel" property="ptlBorderKbn" />
            <% String t_borderKbn = (border).toString(); %>
            <% String t_className = "";%>
            <% if (t_borderKbn.equals("0")) { %>
              <table class="table-top w100 m0">
                <tr>
                  <th>
                    <bean:write name="topModel" property="ptlTitle" />
                  </th>
                </tr>
              </table>
            <% } else { %>
            <%     t_className = "table-noBorder"; %>
            <% } %>
            <table class="<%= t_className %> table-top m0 w100 border_top_none" id="<bean:write name='topModel' property='ptpItemid' />">
              <tr>
                <td class="bgC_tableCell">
                  <logic:equal name="topModel" property="ptlType" value="0">
                      <bean:write name="topModel" property="ptlContent" filter="false"/>
                  </logic:equal>
                  <logic:notEqual name="topModel" property="ptlType" value="0">
                      <bean:write name="topModel" property="ptlContent" filter="false"/>
                  </logic:notEqual>
                </td>
              </tr>
            </table>
          </logic:equal>
        </logic:iterate>
      </logic:notEmpty>
    </td>
  </tr>
  <tr>
    <td>
      <table class="w100">
        <tr>
          <!-- 左-->
          <td id="mainScreenListLeft" class="js_column3 txt_t pr10 mwp100">
            <logic:notEmpty name="ptl070Form" property="ptl070leftList">
              <logic:iterate id="leftMdl" name="ptl070Form" property="ptl070leftList">
                <logic:equal name="leftMdl" property="partsKbn" value="3">
                  <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl070_information.jsp" %></div>
                </logic:equal>
                <logic:equal name="leftMdl" property="partsKbn" value="2">
                  <div class="pluginPortlet" >
                    <span id="<bean:write name='leftMdl' property='ptpItemid' />"><bean:write name="leftMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                  </div>
                </logic:equal>
                <logic:equal name="leftMdl" property="partsKbn" value="1">
                  <div class="pluginPortlet" >
                    <span id="<bean:write name='leftMdl' property='pluginId' />_<bean:write name='leftMdl' property='id' />"><bean:write name="leftMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                  </div>
                </logic:equal>
                <logic:equal name="leftMdl" property="partsKbn" value="0">
                  <!-- ポートレット -->
                  <bean:define id="border" name="leftMdl" property="ptlBorderKbn" />
                  <% String borderKbn = (border).toString(); %>
                  <% String className = "";%>
                  <% if (borderKbn.equals("0")) { %>
                  <table class="table-top w100 m0">
                    <tr>
                      <th>
                        <bean:write name="leftMdl" property="ptlTitle" />
                      </th>
                    </tr>
                  </table>
                  <% } else { %>
                  <%     className = "table-noBorder"; %>
                  <% } %>
                  <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='leftMdl' property='ptpItemid' />">
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
            <logic:notEmpty name="ptl070Form" property="ptl070centerList">
              <logic:iterate id="centerMdl" name="ptl070Form" property="ptl070centerList">
                <logic:equal name="centerMdl" property="partsKbn" value="3">
                  <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl070_information.jsp" %></div>
                </logic:equal>
                <logic:equal name="centerMdl" property="partsKbn" value="2">
                  <div class="pluginPortlet" >
                    <span id="<bean:write name='centerMdl' property='ptpItemid' />"><bean:write name="centerMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                  </div>
                </logic:equal>
                <logic:equal name="centerMdl" property="partsKbn" value="1">
                  <div class="pluginPortlet" >
                    <span id="<bean:write name='centerMdl' property='pluginId' />_<bean:write name='centerMdl' property='id' />"><bean:write name="centerMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                  </div>
                </logic:equal>
                <logic:equal name="centerMdl" property="partsKbn" value="0">
                  <!-- ポートレット -->
                  <bean:define id="border" name="centerMdl" property="ptlBorderKbn" />
                  <% String borderKbn = (border).toString(); %>
                  <% String className = "";%>
                  <% if (borderKbn.equals("0")) { %>
                  <table class="table-top w100 m0">
                    <tr>
                      <th>
                        <bean:write name="centerMdl" property="ptlTitle" />
                      </th>
                    </tr>
                  </table>
                  <% } else { %>
                  <%     className = "table-noBorder"; %>
                  <% } %>
                  <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='centerMdl' property='ptpItemid' />">
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
            <logic:notEmpty name="ptl070Form" property="ptl070rightList">
              <logic:iterate id="rightMdl" name="ptl070Form" property="ptl070rightList">
                <logic:equal name="rightMdl" property="partsKbn" value="3">
                  <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl070_information.jsp" %></div>
                </logic:equal>
                <logic:equal name="rightMdl" property="partsKbn" value="2">
                  <div class="pluginPortlet" >
                    <span id="<bean:write name='rightMdl' property='ptpItemid' />"><bean:write name="rightMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                  </div>
                </logic:equal>
                <logic:equal name="rightMdl" property="partsKbn" value="1">
                  <div class="pluginPortlet" >
                    <span id="<bean:write name='rightMdl' property='pluginId' />_<bean:write name='rightMdl' property='id' />"><bean:write name="rightMdl" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
                  </div>
                </logic:equal>
                <logic:equal name="rightMdl" property="partsKbn" value="0">
                  <!-- ポートレット -->
                  <bean:define id="border" name="rightMdl" property="ptlBorderKbn" />
                  <% String borderKbn = (border).toString(); %>
                  <% String className = "";%>
                  <% if (borderKbn.equals("0")) { %>
                  <table class="table-top w100 m0">
                    <tr>
                      <th>
                        <bean:write name="rightMdl" property="ptlTitle" />
                      </th>
                    </tr>
                  </table>
                  <% } else { %>
                  <%     className = "table-noBorder"; %>
                  <% } %>
                  <table class="<%= className %> table-top m0 w100 border_top_none" id="<bean:write name='rightMdl' property='ptpItemid' />">
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
    <!-- 下 -->
    <td id="mainScreenListBottom" class="w100" colspan="3">
      <logic:notEmpty name="ptl070Form" property="ptl070bottomList">
        <logic:iterate id="bottomModel" name="ptl070Form" property="ptl070bottomList" indexId="idxtop">
          <logic:equal name="bottomModel" property="partsKbn" value="3">
            <div class="pluginPortlet" ><%@ include file="/WEB-INF/plugin/portal/jsp/ptl070_information.jsp" %></div>
          </logic:equal>
          <logic:equal name="bottomModel" property="partsKbn" value="2">
            <div class="pluginPortlet" >
              <span id="<bean:write name="bottomModel" property="ptpItemid" />"><bean:write name="bottomModel" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
            </div>
          </logic:equal>
          <logic:equal name="bottomModel" property="partsKbn" value="1">
            <div class="pluginPortlet" >
              <span id="<bean:write name='bottomModel' property='pluginId' />_<bean:write name='bottomModel' property='id' />"><bean:write name="bottomModel" property="pluginName" /><gsmsg:write key="cmn.reload" />...</span>
            </div>
          </logic:equal>

          <logic:equal name="bottomModel" property="partsKbn" value="0">
            <!-- ポートレット -->
            <bean:define id="border" name="bottomModel" property="ptlBorderKbn" />
            <% String t_borderKbn = (border).toString(); %>
            <% String t_className = "";%>
            <% if (t_borderKbn.equals("0")) { %>
              <table class="table-top w100 m0">
                <tr>
                  <th>
                    <bean:write name="bottomModel" property="ptlTitle" />
                  </th>
                </tr>
              </table>
            <% } else { %>
            <%     t_className = "table-noBorder"; %>
            <% } %>
            <table class="<%= t_className %> table-top m0 w100 border_top_none" id="<bean:write name='bottomModel' property='ptpItemid' />">
              <tr>
                <td class="bgC_tableCell">
                  <logic:equal name="bottomModel" property="ptlType" value="0">
                      <bean:write name="bottomModel" property="ptlContent" filter="false"/>
                  </logic:equal>
                  <logic:notEqual name="bottomModel" property="ptlType" value="0">
                      <bean:write name="bottomModel" property="ptlContent" filter="false"/>
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
</div>
<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>

</html:html>
