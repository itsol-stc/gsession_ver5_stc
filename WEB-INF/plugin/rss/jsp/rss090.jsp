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
<title>GROUPSESSION <gsmsg:write key="rss.rss090.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../rss/js/rss090.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../rss/css/rss.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/rss/rss090">
    <input type="hidden" name="CMD" value="">
    <html:hidden name="rss090Form" property="rssSid" />

    <div class="w100">
      <logic:notEmpty name="rss090Form" property="userDataList">
        <bean:size id="count1" name="rss090Form" property="pageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging txt_r">
            <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="rss090page1" onchange="changePage(1);">
              <html:optionsCollection name="rss090Form" property="pageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <table class="table-top" cellpadding="0" cellspacing="0">
        <tr>
          <!-- 氏名 -->
          <th class="no_w">
            <span>
              <gsmsg:write key="rss.rss090.2" />
            </span>
          </th>

          <logic:notEmpty name="rss090Form" property="userDataList">
            <logic:iterate id="user" name="rss090Form" property="userDataList" scope="request" indexId="idx">
              <tr>
                <!-- 氏名 -->
                <td class="no_w">
                  <span>
                    <bean:write name="user" property="registSei" />
                    &nbsp;
                    <bean:write name="user" property="registMei" />
                  </span>
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
      </table>

      <logic:notEmpty name="rss090Form" property="userDataList">
        <bean:size id="count1" name="rss090Form" property="pageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging txt_r">
            <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="rss090page2" onchange="changePage(2);">
              <html:optionsCollection name="rss090Form" property="pageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <div class="txt_c">
        <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </div>

  </html:form>
</body>
</html:html>