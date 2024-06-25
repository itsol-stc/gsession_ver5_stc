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
<title>GROUPSESSION <gsmsg:write key="cmn.select.label" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?500"></script>
<link rel=stylesheet href='../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<script src="../bookmark/js/bmk040.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/readOnly.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/freeze.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onload="freezeScreenParent('', false);parentReadOnly();">
  <html:form action="/bookmark/bmk040">

    <input type="hidden" name="CMD" value="">
    <html:hidden property="bmk040parentLabelName" />
    <html:hidden property="bmk040mode" />
    <html:hidden property="bmk040groupSid" />

    <table cellpadding="4" cellspacing="0" border="0" class="w100">
      <tr>
        <td>
          <table cellpadding="0" cellspacing="0" border="0" class="w100">
            <tr>
              <td class="w100 txt_r">
                <button type="button" value="OK" class="baseBtn" onClick="setParentLabel();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                  <gsmsg:write key="cmn.ok" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onclick="pushCancel();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
                  <gsmsg:write key="cmn.cancel" />
                </button>
              </td>
            </tr>
          </table>
        </td>
      </tr>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <logic:notEmpty name="bmk040Form" property="labelList">
        <tr>
          <td>
            <table cellpadding="0" cellspacing="0" class="table-top">
              <logic:iterate id="labelData" name="bmk040Form" property="labelList" indexId="idx">
                <tr class="js_listHover cursor_p">
                  <td class="w3 js_tableTopCheck">
                    <html:multibox name="bmk040Form" property="bmk040selectLabel" styleClass="m0 check">
                      <bean:write name="labelData" property="blbName" />
                    </html:multibox>
                  </td>
                  <td class="js_radio">
                    <bean:write name="labelData" property="blbName" />
                  </td>
                </tr>
              </logic:iterate>
            </table>
          </td>
        </tr>

        <tr>
          <td>
            <table class="w100" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td class="w100 txt_r">
                  <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="setParentLabel();">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                    <gsmsg:write key="cmn.ok" />
                  </button>
                  <button type="button" value="<gsmsg:write key="cmn.cancel" />" class="baseBtn" onclick="pushCancel();">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.cancel" />">
                    <gsmsg:write key="cmn.cancel" />
                  </button>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </logic:notEmpty>
    </table>

  </html:form>

</body>
</html:html>