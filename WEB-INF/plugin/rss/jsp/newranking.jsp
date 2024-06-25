<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="rss.newranking.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<body class="body_03">

<html:form action="/rss/rssnewranking">
<input type="hidden" name="CMD" value="">
<html:hidden name="rssNewRankingForm" property="rssSid" />
<html:hidden name="rssNewRankingForm" property="rssTitle" />
<logic:notEmpty name="rssNewRankingForm" property="newRankingList">

<table class="table-top main_oddcol_table w100 mwp300">
  <tr>
    <th class="table_title-color txt_l" colspan="4">
      <img class="mainPlugin_icon" src="../rss/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="bbs.bbsMain.5" />">
      <logic:equal name='rssNewRankingForm' property='rssTopUrl' value=''>
      <a href="#!">
      </logic:equal>
      <logic:notEqual name='rssNewRankingForm' property='rssTopUrl' value=''>
      <a href="<bean:write name="rssNewRankingForm" property="rssTopUrl" />">  
      </logic:notEqual>
        <gsmsg:write key="rss.9" />
      </a>
    </th>
  </tr>
  <logic:iterate id="rankingMdl" name="rssNewRankingForm" property="newRankingList" indexId="index">
    <tr>
      <td class="w90 border_right_none">
        <logic:equal name='rankingMdl' property='url' value=''>
        <a href="#!">
        </logic:equal>
        <logic:notEqual name='rankingMdl' property='url' value=''>
        <a href="<bean:write name="rankingMdl" property="url" />" target="_blank">  
        </logic:notEqual>
          <bean:write name="rankingMdl" property="title" />
        </a>
      </td>
      <td class="w10 no_w border_left_none">
        <logic:equal name="rankingMdl" property="koudokuCount" value="0">
          <button class="iconBtn-border" type="button" onclick="return rssAdd('<bean:write name="rankingMdl" property="rssSid" />', '<bean:write name="rankingMdl" property="title" />');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_rss_2.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png">
          </button>
        </logic:equal>
        <logic:greaterThan name="rankingMdl" property="koudokuCount" value="0">
          <span class="cl_fontWarn"><gsmsg:write key="rss.1" /><br><gsmsg:write key="cmn.pre" /></span>
        </logic:greaterThan>
      </td>
    </tr>
  </logic:iterate>
</table>

</logic:notEmpty>
</html:form>

</body>
</html:html>