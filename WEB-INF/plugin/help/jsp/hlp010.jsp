<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<% String version = jp.groupsession.v2.cmn.GSConst.VERSION; %>
<!DOCTYPE html>

<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION <gsmsg:write key="cmn.help" /><gsmsg:write key="cmn.main" /></title>
<link rel=stylesheet href="../common/css/theme_classic/theme_base.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../common/css/theme_classic/default/theme.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../help/css/default.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../help/css/help.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../help/js/help.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>

<title>GROUPSESSION <gsmsg:write key="cmn.help" /><gsmsg:write key="cmn.main" /><gsmsg:write key="cmn.display" /></title>
</head>

<%-- ここを呼び出された状況に応じて切り替える start --%>

<logic:empty name="hlp010Form" property="hlp010HelpUrl">
  <body onLoad="ajaxGetContents('help-left-plugin-list', '../help/hlp000.do'); ajaxGetContents('right-content', '../help/hlp000top.do');">

  <div id="container"><a name="top"></a>
    <div id="help-left">
    <div class="help-content-tr">
      <div class="help-content-tl" >
        <table width="100%">
          <tr>
            <td class="help-content-tl-td">
              <span class="help-header-text2"><gsmsg:write key="cmn.plugin.list" /></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="help-content-mr"><div class="help-content-ml">
        <div id="help-left-plugin-list"></div>

    </div></div>
    <div class="help-content-br"><div class="help-content-bl"><img src="../help/images/spacer.gif" width="1" height="15" alt=""></div></div>
  </div>
    <!-- コンテンツ -->
  <div id="right-content">
  <!-- ここにAJAX.UPDATERの結果を挿入 -->
  </div>
  </div>

</logic:empty>

<logic:notEmpty name="hlp010Form" property="hlp010HelpUrl">
  <body onLoad="location.href='<bean:write name="hlp010Form" property="hlp010HelpUrl" />'">
</logic:notEmpty>

<%-- ここを呼び出された状況に応じて切り替える end --%>


<footer class="txt_c">
  <a href="https://www.sjts.co.jp/" target="_blank">
    <span class="copyright2">GroupSession Ver.<%= version %> &copy;<gsmsg:write key="cmn.cmn001.10" /></span>
  </a>
</footer>
</body>

</html:html>