<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="project.107" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../project/js/project.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/ui/jquery-ui-1.8.16.custom.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../project/js/prjptl011.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../project/css/project.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<html:form action="/project/prjptl011">
<input type="hidden" name="prjPtl010PrjSid" value="">

<body class="m0 mt10">

  <bean:define id="kanryo" name="prjptl011Form" property="todoKanryoCnt" />
  <bean:define id="mikanryo" name="prjptl011Form" property="todoMikanryoCnt" />
  <bean:define id="sinkotyu" name="prjptl011Form" property="todoSinkotyuCnt" />

  <script type="text/javascript" src="../common/js/graph_circle_1_0_2/excanvas/excanvas.js?<%= GSConst.VERSION_PARAM %>"></script>

  <script type="text/javascript" src="../common/js/graph_circle_1_0_2/graph/circle.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript">
  $(function(){
      var cg = new html5jp.graph.circle("sample");
      if( ! cg ) { return; }
      var items = [
        ["<gsmsg:write key="cmn.complete" />", <bean:write name="kanryo" />, "#0c5bc7"],
        ["<gsmsg:write key="rng.application.ongoing" />", <bean:write name="sinkotyu" />, "#d6d4fe"],
        ["<gsmsg:write key="project.prj010.8" />", <bean:write name="mikanryo" />, "#828282"]
      ];
      var params = {
        shadow: false,
        caption: true,
        legend: false,
        captionNum: false,
        startAngle: -45
      };
      cg.draw(items, params);
  });
  </script>

  <table class="w100 bor1 mb0">
  <tr>
    <th class="table_title-color txt_l p5 fs_14">
      <img class="mainPlugin_icon" src="../project/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="fil.1" />">
      <a class="cl_fontOutline" href="../project/prj030.do?prj030scrId=main&prj030prjSid=<bean:write name="prjptl011Form" property="prjPtl010PrjSid" />" target="_parent">
        <gsmsg:write key="project.prj010.5" /> [ <bean:write name="prjptl011Form" property="prjPtl010prjName" /> ]
      </a>
    </th>
  </tr>
  <tr>
  <td class="prj_graph bor1" align="center">
    <div><canvas width="250" height="200" id="sample"></canvas></div>
  </td>
  </tr>
  <tr>
  <td class="prj_graph bor1 p5 fs_13">
    <div class="mb5">
      <span class="kanryo pr10 mr5">&nbsp;</span><gsmsg:write key="cmn.complete" />（<bean:write name="kanryo" /><gsmsg:write key="cmn.number" />）
    </div>
    <div class="mb5">
      <span class="sinkotyu pr10 mr5">&nbsp;</span><gsmsg:write key="rng.application.ongoing" />（<bean:write name="sinkotyu" /><gsmsg:write key="cmn.number" />）
    </div>
    <div>
      <span class="mikanryo pr10 mr5">&nbsp;</span><gsmsg:write key="project.prj010.8" />（<bean:write name="mikanryo" /><gsmsg:write key="cmn.number" />）
    </div>
  </td>
  </tr>
  </table>

</body>
</html:form>
</html:html>