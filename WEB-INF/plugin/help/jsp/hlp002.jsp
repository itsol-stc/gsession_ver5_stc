<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<!DOCTYPE html>

<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<meta http-equiv="Content-Style-Type" content="text/css">
<title>GROUPSESSION <gsmsg:write key="cmn.help" /><gsmsg:write key="main.src.man250.16" /><gsmsg:write key="cmn.list" /></title>
<link rel=stylesheet href="../help/css/default.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../help/css/help.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../common/css/theme_classic/theme_base.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../common/css/theme_classic/default/theme.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../help/js/help.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript">
<!--
  function myHeaderTextChange(value) {
      document.getElementById('header_text').innerHTML = value;
  }
//-->
</script>
</head>
<body>

<html:form action="/help/hlp002" target="body" method="post">
<input type="hidden" name="CMD" value="search">

<div id="container"><a name="top"></a>
    <table>
      <tr>
        <td>
          <a href="../help/hlp010.do" target="body" onclick="myHeaderTextChange('[ <gsmsg:write key="main.src.man250.16" /><gsmsg:write key="cmn.list" /> ]');"><img src="../help/images/header_plugin_list_01.gif" border="0" alt="<gsmsg:write key="cmn.help" />"></a>
        </td>
        <td class="v_align-top">
          <div class="header_white_bg_text plugin_ttl hp40">
            <div class="fs_20 pt5 pr15">
              <gsmsg:write key="cmn.help" />
            </div>
          </div>
        </td>
        <td class="v_align-top">
          <div class="header_white_bg_text hp40">
            <div class="fs_16 pt8" id="header_text">
              [ <gsmsg:write key="cmn.help.function" /> ]
            </div>
          </div>
        </td>
        <td width="100%" class="v_align-top">
          <div class="header_white_bg_text hp40">
            <div class="pt8 txt_r">
              <html:text name="hlp002Form" property="hlp002SearchText" maxlength="100" style="width:155px;" styleClass="fs_100" />
              <input type="submit" value="<gsmsg:write key="cmn.search" />" class="btn_base1s line_height-normal" onclick="myHeaderTextChange('[ <gsmsg:write key="cmn.search" /> ]'); buttonPush('search');">
            </div>
          </div>
        </td>
        <td>
          <img src="../help/images/header_white_end.gif" border="0" alt="<gsmsg:write key="cmn.help" />">
        </td>
      </tr>
    </table>
    <img src="../help/images/spacer.gif" width="100" height="10" alt="">
</div>

</html:form>
</body>
</html:html>