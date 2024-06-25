<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="ntp.1" /></title>
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/glayer.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../nippou/js/ntp200.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../nippou/css/nippou.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

</head>

<body onunload="calWindowClose();">

<html:form action="/nippou/ntp200">

<input type="hidden" name="CMD" value="">
<html:hidden property="ntp200NanSid" />
<html:hidden property="ntp200ProcMode" />
<html:hidden property="ntp200InitFlg" />
<html:hidden property="ntp200parentPageId" />
<html:hidden property="ntp200RowNumber" />
<html:hidden property="ntp200SortKey1" />
<html:hidden property="ntp200OrderKey1" />

<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../nippou/images/classic/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
      <img class="header_pluginImg" src="../nippou/images/original/header_nippou.png" alt="<gsmsg:write key="ntp.1" />">
    </li>
    <li>
      <gsmsg:write key="ntp.1" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.entry" />" onClick="buttonPush('add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
          <gsmsg:write key="cmn.add" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <table class="table-left w100">
    <tr>
      <th class="bgC_header1" colspan="4">
        &nbsp;
      </th>
    </tr>
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.29" />
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200NanCode" maxlength="8" styleClass="wp150" />
      </td>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.11" />ñº
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200NanName" maxlength="50" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="address.7" />
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200AcoCode" maxlength="8" styleClass="wp150" />
      </td>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="cmn.company.name" />
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200AcoName" maxlength="50" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="address.9" />
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200AcoNameKana" maxlength="100" styleClass="w100" />
      </td>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.150" />
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200AbaName" maxlength="50" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.154" /><gsmsg:write key="cmn.category" />
      </th>
      <td class="w35">
        <html:select name="ntp200Form" property="ntp200CatSid" styleClass="w100">
          <logic:notEmpty name="ntp200Form" property="ntp200CategoryList">
            <html:optionsCollection name="ntp200Form" property="ntp200CategoryList" value="value" label="label" />
          </logic:notEmpty>
        </html:select>
      </td>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.154" />
      </th>
      <td class="w35">
        <html:text name="ntp200Form" property="ntp200ShohinName" maxlength="50" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.71" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <logic:notEmpty name="ntp200Form" property="ntp200StateList">
            <logic:iterate id="stateMdl" name="ntp200Form" property="ntp200StateList" indexId="stateidx">
              <bean:define  id="stateVal" name="stateMdl" property="value" />
              <% String idForState = "ntp200State" + String.valueOf(stateidx); %>
              <html:radio name="ntp200Form" property="ntp200State" styleId="<%= idForState %>"  value="<%= String.valueOf(stateVal) %>" /><span class="mr10"><label for="<%= idForState %>"><bean:write  name="stateMdl" property="label" /></label></span>
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </td>
      <th class="w15 txt_c no_w">
        <gsmsg:write key="ntp.64" />
      </th>
      <td class="w35">
        <div class="verAlignMid">
          <logic:notEmpty name="ntp200Form" property="ntp200AnkenStateList">
            <logic:iterate id="anstateMdl" name="ntp200Form" property="ntp200AnkenStateList" indexId="anstateidx">
              <bean:define  id="anstateVal" name="anstateMdl" property="value" />
              <% String idForAnState = "ntp200AnkenState" + String.valueOf(anstateidx); %>
              <html:radio name="ntp200Form" property="ntp200AnkenState" styleId="<%= idForAnState %>"  value="<%= String.valueOf(anstateVal) %>" /><span class="mr10"><label for="<%= idForAnState %>"><bean:write  name="anstateMdl" property="label" /></label></span>
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </td>
    </tr>
  </table>
  <div class="mt10 txt_c w100">
    <button type="button" class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onclick="return buttonPush('search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <logic:notEmpty name="ntp200Form" property="ntp200AnkenList">
    <bean:size id="count1" name="ntp200Form" property="ntp200PageCmbList" scope="request" />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp200PageTop" onchange="changePage(0);" styleClass="paging_combo">
          <html:optionsCollection name="ntp200Form" property="ntp200PageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
  <table class="table-top w100">
    <tr>
      <th class="w30 no_w js_ankenSortLink cursor_p table_header-evt" id="0">
        <logic:equal name="ntp200Form" property="ntp200SortKey1" value="0">
          <logic:equal name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å£</span>
            <span class="original-display txt_m">
              <i class="icon-sort_up"></i>
            </span>
          </logic:equal>
          <logic:notEqual name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å•</span>
            <span class="original-display txt_m">
              <i class="icon-sort_down"></i>
            </span>
          </logic:notEqual>
        </logic:equal>
        <gsmsg:write key="ntp.11" />
      </th>
      <th class="w25 no_w js_ankenSortLink cursor_p table_header-evt" id="1">
        <logic:equal name="ntp200Form" property="ntp200SortKey1" value="1">
          <logic:equal name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å£</span>
            <span class="original-display txt_m">
              <i class="icon-sort_up"></i>
            </span>
          </logic:equal>
          <logic:notEqual name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å•</span>
            <span class="original-display txt_m">
              <i class="icon-sort_down"></i>
            </span>
          </logic:notEqual>
        </logic:equal>
        <gsmsg:write key="ntp.15" />/ãíì_
      </th>
      <th class="w10 no_w js_ankenSortLink cursor_p table_header-evt" id="2">
        <logic:equal name="ntp200Form" property="ntp200SortKey1" value="2">
          <logic:equal name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å£</span>
            <span class="original-display txt_m">
              <i class="icon-sort_up"></i>
            </span>
          </logic:equal>
          <logic:notEqual name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å•</span>
            <span class="original-display txt_m">
              <i class="icon-sort_down"></i>
            </span>
          </logic:notEqual>
        </logic:equal>
        <gsmsg:write key="ntp.155" />
      </th>
      <th class="w10 no_w js_ankenSortLink cursor_p table_header-evt" id="3">
        <logic:equal name="ntp200Form" property="ntp200SortKey1" value="3">
          <logic:equal name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å£</span>
            <span class="original-display txt_m">
              <i class="icon-sort_up"></i>
            </span>
          </logic:equal>
          <logic:notEqual name="ntp200Form" property="ntp200OrderKey1" value="0">
            <span class="classic-display">Å•</span>
            <span class="original-display txt_m">
              <i class="icon-sort_down"></i>
            </span>
          </logic:notEqual>
        </logic:equal>
        <gsmsg:write key="ntp.64" />
      </th>
      <th class="w25 no_w">
        <gsmsg:write key="ntp.58" />
      </th>
    </tr>
    <logic:notEmpty name="ntp200Form" property="ntp200AnkenList">
      <logic:iterate id="ankenMdl" name="ntp200Form" property="ntp200AnkenList" indexId="idx">
        <tr class="cursor_p js_listHover" onclick="return setParent(<bean:write name="ankenMdl" property="nanSid" />);">
          <td class="txt_l txt_m">
            <div>
              <input type="hidden" id="ankenCode_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="nanCode" />">
              <bean:write name="ankenMdl" property="nanCode" />
            </div>
            <div class="cl_linkDef">
              <input type="hidden" id="ankenName_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="nanName" />">
              <input type="hidden" id="ankenMikomido_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="nanMikomi" />">
              <bean:write name="ankenMdl" property="nanName" />
            </div>
          </td>
          <td class="txt_l txt_m">
            <div>
              <input type="hidden" id="ankenCompanySid_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="acoSid" />">
              <input type="hidden" id="ankenCompanyCode_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="ntp200CompanyCode" />">
              <input type="hidden" id="ankenCompanyName_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="ntp200CompanyName" />">
              <bean:write name="ankenMdl" property="ntp200CompanyName" />
            </div>
            <div>
              <input type="hidden" id="ankenBaseSid_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="abaSid" />">
              <input type="hidden" id="ankenBaseName_<bean:write name="ankenMdl" property="nanSid" />" value="<bean:write name="ankenMdl" property="ntp200BaseName" />">
              <bean:write name="ankenMdl" property="ntp200BaseName" />
            </div>
          </td>
          <td class="txt_c txt_m">
            <bean:write name="ankenMdl" property="ntp200Date" />
          </td>
          <td class="txt_c txt_m">
            <logic:equal name="ankenMdl" property="nanSyodan" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_CHU) %>">
              <gsmsg:write key="ntp.68" />
            </logic:equal>
            <logic:equal name="ankenMdl" property="nanSyodan" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_JYUCHU) %>">
              <gsmsg:write key="ntp.69" />
            </logic:equal>
            <logic:equal name="ankenMdl" property="nanSyodan" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.SYODAN_SICHU) %>">
              <gsmsg:write key="ntp.7" />
            </logic:equal>
          </td>
          <td class="txt_c txt_m">
            <logic:notEmpty  name="ankenMdl" property="ntp200ShohinList">
              <logic:iterate id="shMdl" name="ankenMdl" property="ntp200ShohinList">
                <bean:write name="shMdl" property="nhnName" />
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      </logic:iterate>
    </logic:notEmpty>
  </table>
  <logic:notEmpty name="ntp200Form" property="ntp200AnkenList">
    <bean:size id="count1" name="ntp200Form" property="ntp200PageCmbList" scope="request" />
    <logic:greaterThan name="count1" value="1">
      <div class="paging">
        <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left"></i>
        </button>
        <html:select property="ntp200PageBottom" onchange="changePage(1);" styleClass="paging_combo">
          <html:optionsCollection name="ntp200Form" property="ntp200PageCmbList" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:greaterThan>
  </logic:notEmpty>
  <div class="txt_c">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onclick="return window.close();">
      <img src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.close" />
    </button>
  </div>
</div>


</html:form>

</body>
</html:html>