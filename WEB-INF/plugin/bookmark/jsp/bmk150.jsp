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
<title>GROUPSESSION <gsmsg:write key="bmk.new.list" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bookmark/js/bmk150.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>

  <html:form action="/bookmark/bmk150">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="bmk150Form" property="bmk150PageNum" />
    <html:hidden name="bmk150Form" property="bmk070ReturnPage" />
    <logic:notEmpty name="bmk150Form" property="bmk010delInfSid" scope="request">
      <logic:iterate id="item" name="bmk150Form" property="bmk010delInfSid" scope="request">
        <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp"%>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../bookmark/images/classic/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />">
            <img class="header_pluginImg" src="../bookmark/images/original/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />"
        </li>
        <li>
          <gsmsg:write key="bmk.43" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="bmk.new.list" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backPage');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">
      <logic:notEmpty name="bmk150Form" property="bmk150NewList">
        <bean:size id="count1" name="bmk150Form" property="bmk150PageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging mb5">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="bmk150Slt_page1" onchange="changePage1();">
              <html:optionsCollection name="bmk150Form" property="bmk150PageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <table class="w100 table-top mt0 mb0 txt_c">

        <tr>
          <th class="w100" colspan="4">
            <span>
              <gsmsg:write key="bmk.new.list" />
            </span>
          </th>

          <logic:notEmpty name="bmk150Form" property="bmk150NewList">
            <logic:iterate id="resultMdl" name="bmk150Form" property="bmk150NewList" indexId="idx">
              <bean:define id="tdColor" value="" />
              <% String[] tdColors = new String[] { "bgC_tableCell", "bgC_tableCellEvn" }; %>
              <% tdColor = tdColors[(idx.intValue() % 2)]; %>

              <tr>

                <!-- タイトル・URL -->
                <td class="txt_m w65 <%=tdColor%> border_right_none word_b-all">
                  <div>
                    <a href="<bean:write name="resultMdl" property="bmuUrl" />" target="_blank"><bean:write name="resultMdl" property="bmuTitle" /></a>
                  </div>
                  <div>
                    <bean:write name="resultMdl" property="bmuUrl" />
                  </div>
                </td>

                <!-- 登録人数 -->
                <td class="<%=tdColor%> txt_m txt_r w20 no_w border_right_none border_left_none">
                  <bean:define id="perCnt" name="resultMdl" property="bmkPerCount" type="java.lang.Integer" />
                  <% String perCntMsg = "<b>" + String.valueOf(perCnt.intValue()) + "</b>"; %>
                  <a href="#" onClick="selPerCount(<bean:write name="resultMdl" property="bmuSid" />);" class="main_bmkCount">
                    <gsmsg:write key="bmk.22" arg0="<%=perCntMsg%>" />
                  </a>
                </td>

                <!-- 追加ボタン・追加済み -->
                <td class="w15 txt_c no_w <%=tdColor%> border_left_none">
                  <logic:equal name="resultMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                    <button type="button" value="<gsmsg:write key="cmn.add" />" onClick="bmkAdd(<bean:write name="resultMdl" property="bmuSid" />);" class="baseBtn">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                      <gsmsg:write key="cmn.add" />
                    </button>
                  </logic:equal>

                  <logic:notEqual name="resultMdl" property="bmkMyKbn" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.TOROKU_NO)%>'>
                    <span class="cl_fontWarn">
                      <gsmsg:write key="cmn.add" /><gsmsg:write key="cmn.pre" />
                    </span>
                  </logic:notEqual>
                </td>
              </tr>

            </logic:iterate>
          </logic:notEmpty>
          </td>
        </tr>
      </table>

      <logic:notEmpty name="bmk150Form" property="bmk150NewList">
        <bean:size id="count2" name="bmk150Form" property="bmk150PageLabel" scope="request" />
        <logic:greaterThan name="count2" value="1">
          <div class="paging mt5">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="bmk150Slt_page2" onchange="changePage2();">
              <html:optionsCollection name="bmk150Form" property="bmk150PageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <div class="footerBtn_block mt10">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backPage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>