<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String maxLengthSyosai = String.valueOf(1000);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery.bgiframe.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../address/js/adr240.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp210.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>

<body onload="" onunload="calWindowClose();companyWindowClose();">

<html:form action="/nippou/ntp210">

<input type="hidden" name="CMD" value="">


<div class="pageTitle mrl_auto">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li><gsmsg:write key="ntp.1" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.information2" />
    </li>
    <li>
    </li>
  </ul>
</div>
<div class="wrapper mrl_auto">
<div class="txt_l">
<logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
</div>
<table class="table-left">
    <tr>
      <th class="w30">
        <gsmsg:write key="ntp.29" />
      </th>
      <td class="w70">
        <bean:write name="ntp210Form" property="ntp210NanCode" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.57" />
      </th>
      <td>
        <bean:write name="ntp210Form" property="ntp210NanName" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.73" />
      </th>
      <td>
        <bean:write name="ntp210Form" property="ntp210NanSyosai" filter="false" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.15" />(<gsmsg:write key="ntp.16" />)
      </th>
      <td>
        <gsmsg:write key="address.7" />：<logic:notEmpty name="ntp210Form" property="ntp210AcoCode"><bean:write name="ntp210Form" property="ntp210AcoCode" /></logic:notEmpty>
        <div>
          <logic:notEmpty name="ntp210Form" property="ntp210CompanyName">
          <bean:write name="ntp210Form" property="ntp210CompanyName" /><br><bean:write name="ntp210Form" property="ntp210CompanyBaseName" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.58" />
      </th>
      <td>
        <logic:notEmpty name="ntp210Form" property="ntp210ShohinList">
          <logic:iterate id="shohinName" name="ntp210Form" property="ntp210ShohinList">
            <div>
              <bean:write name="shohinName" property="label" />
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.61" />／<gsmsg:write key="ntp.62" />
      </th>
      <td>
        <!-- 業務コンボ -->
        <logic:notEmpty name="ntp210Form" property="ntp210GyoushuName">
          <bean:write name="ntp210Form" property="ntp210GyoushuName" />
        </logic:notEmpty>
        &nbsp;
        <!-- プロセスコンボ -->
        <logic:notEmpty name="ntp210Form" property="ntp210ProcessName">
          <bean:write name="ntp210Form" property="ntp210ProcessName" />
        </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.63" />
      </th>
      <td>
        <logic:equal name="ntp210Form" property="ntp210NanMikomi" value="0">
          10%
        </logic:equal>
        <logic:equal name="ntp210Form" property="ntp210NanMikomi" value="1">
          30%
        </logic:equal>
        <logic:equal name="ntp210Form" property="ntp210NanMikomi" value="2">
          50%
        </logic:equal>
        <logic:equal name="ntp210Form" property="ntp210NanMikomi" value="3">
         70%
        </logic:equal>
        <logic:equal name="ntp210Form" property="ntp210NanMikomi" value="4">
          100%
       </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.53" />
      </th>
      <td>
        <bean:write name="ntp210Form" property="ntp210NanKinMitumori" />&nbsp;<gsmsg:write key="project.103" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.54" />
      </th>
      <td>
        <bean:write name="ntp210Form" property="ntp210NanKinJutyu" />&nbsp;<gsmsg:write key="project.103" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.64" />
      </th>
      <td>
        <logic:equal name="ntp210Form" property="ntp210NanSyodan" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_CHU) %>">
          <gsmsg:write key="ntp.68" />
        </logic:equal>
        <logic:equal name="ntp210Form" property="ntp210NanSyodan" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_JYUCHU) %>">
          <gsmsg:write key="ntp.69" />
        </logic:equal>
        <logic:equal name="ntp210Form" property="ntp210NanSyodan" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_SICHU) %>">
          <gsmsg:write key="ntp.7" />
        </logic:equal>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.65" />
      </th>
      <td>
       <!-- <gsmsg:write key="ntp.65" />コンボ -->
       <logic:notEmpty name="ntp210Form" property="ntp210ContactName">
         <bean:write name="ntp210Form" property="ntp210ContactName" />
       </logic:notEmpty>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.72" />
      </th>
      <td>
        <bean:write name="ntp210Form" property="ntp210Date" />
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.registant" />
      </th>
      <td>
        <bean:write name="ntp210Form" property="ntp210TourokuName" />
      </td>
    </tr>
  </table>

  <div class="txt_c">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>


</html:form>


</body>
</html:html>