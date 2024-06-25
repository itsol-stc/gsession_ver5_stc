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
<title>GROUPSESSION <gsmsg:write key="cmn.help" /> <gsmsg:write key="cmn.search.result" /></title>
<link rel=stylesheet href="../help/css/default.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../help/css/help.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../common/css/theme_classic/theme_base.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../common/css/theme_classic/default/theme.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../help/js/help.js?<%=GSConst.VERSION_PARAM%>"></script>

</head>
<body onLoad="ajaxGetContents('help-search-plugin', '../help/hlp000.do');">
<html:form action="/help/hlp020">
<input type="hidden" name="CMD" value="init">
<html:hidden property="hlp002SearchText"/>
<html:hidden property="hlp020DispPage"/>
<html:hidden property="hlp020PageCount"/>
<html:hidden property="hlp020searchFlg"/>
<bean:define id="bhlp020DispPage" name="hlp020Form" property="hlp020DispPage" />
<% String dispPage = bhlp020DispPage.toString(); %>
<bean:define id="bhlp020PageCount" name="hlp020Form" property="hlp020PageCount" />
<% String pageCount = bhlp020PageCount.toString(); %>
<div id="container"><a name="top"></a>
    <div align="right" class="help-align-right"><input type="button" value="<gsmsg:write key="main.src.man250.16" /><gsmsg:write key="cmn.list" />" class="btn_base0" onClick="commonHelpLocationChange('../help/hlp010.do');"></div>

    <!-- CONTENT -->
    <div class="help-content-tr">
      <div class="help-content-tl" >
        <table width="100%">
          <tr>
            <td class="help-content-tl-td">
              <span class="help-header-text"><gsmsg:write key="bbs.bbs041.2" /></span>
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="help-content-mr"><div class="help-content-ml pad">

<!--  SERACH RESULT -->
<div id="help-search-result"><!-- //STTART help-search-result -->
<html:messages id="msg" message="false" >
    <p style="text-align:left;"><span class="textError"><bean:write name="msg" ignore="true" /></span></p>
</html:messages>

<logic:equal name="hlp020Form" property="hlp020searchFlg" value="1">
<h1><gsmsg:write key="cmn.search.result" /><span> [ <bean:write name="hlp020Form" property="hlp020dspWord"/> ] で検索した結果 <bean:write name="hlp020Form" property="hlp020dspCount"/>件中、<bean:write name="hlp020Form" property="hlp020dspPage"/>件表示</span></h1>
<ol>
<logic:iterate id="result" name="hlp020Form" property="hlp020resultList">
<li>
<a href="<bean:write name='result' property='path'/>"><bean:write name="result" property="title"/></a>
<p><bean:write name="result" property="content"/></p>
<div><em><bean:write name="result" property="path"/></em></div>
</li>
</logic:iterate>
</ol>
</logic:equal>
</div><!-- //END help-search-result -->


<div id="help-search-navi"><!-- START help-search-navi -->
    <!-- help-search-plugin -->
    <div class="help-content-tr">
      <div class="help-content-tl">
        <table width="100%">
          <tr>
            <td class="help-content-tl-td">
              <span class="help-header-text2"><gsmsg:write key="cmn.plugin.list" /></span>
            </td>
          </tr>
        </table>
      </div>
    </div>

      <div class="help-content-mr"><div class="help-content-ml"><div id="help-search-plugin">
      <!-- ここにプラグインリストが挿入されます -->
      </div></div></div>
    <div class="help-content-br"><div class="help-content-bl"><img src="../help/images/spacer.gif" width="1" height="15" alt=""></div></div>

</div><!-- END help-search-navi -->
<div class="help-clear pageLink" align="center">
<logic:equal name="hlp020Form" property="hlp020searchFlg" value="1">

<logic:notEqual name="hlp020Form" property="hlp020DispPage" value="1">
    &nbsp;<a href="#" onclick="buttonPush('prev');"><span class="text_bold"><gsmsg:write key="cmn.previous" /></span></a>
</logic:notEqual>

<logic:iterate id="pageList" name="hlp020Form" property="hlp020pageList">
<logic:equal name="pageList" value="<%= dispPage %>">
    <span class="pageLinkBoxDispPage"><bean:write name="pageList"/></span>
</logic:equal>
<logic:notEqual name="pageList" value="<%= dispPage %>">
    <span class="pageLinkBox" id="box<bean:write name='pageList'/>" onmouseover="changeSelected(<bean:write name='pageList'/>);" onmouseout="changeUnSelected(<bean:write name='pageList'/>);"><a href="#" onclick="movePage(<bean:write name='pageList'/>);" id="link<bean:write name='pageList'/>"><bean:write name="pageList"/></a></span>
</logic:notEqual>
</logic:iterate>

<logic:notEqual name="hlp020Form" property="hlp020PageCount" value="<%= dispPage %>">
    &nbsp;<a href="#" onclick="buttonPush('next');"><span class="text_bold"><gsmsg:write key="cmn.next" /></span></a>
</logic:notEqual>

</logic:equal>
</div>
        </div></div>
    <div class="help-content-br"><div class="help-content-bl"><img src="../help/images/spacer.gif" width="1" height="15" alt=""></div></div>
    <div class="help-align-right"><a href="#top"><gsmsg:write key="cmn.top.back" /></a></div>

</div>
<footer class="txt_c">
  <a href="https://www.sjts.co.jp/" target="_blank">
    <span class="copyright2">GroupSession Ver.<%= version %> &copy;<gsmsg:write key="cmn.cmn001.10" /></span>
  </a>
</footer>
</html:form>

</body>
</html:html>