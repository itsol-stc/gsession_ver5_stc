<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.sml.GSConstSmail" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml380.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="sml.sml380.01" />
</title>
</head>

<body>
<html:form action="/smail/sml380">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="sml380EditBan" />
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />


<logic:notEmpty name="sml380Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml380Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml380svKeyword" />
<html:hidden property="sml380sortKey" />
<html:hidden property="sml380order" />
<html:hidden property="sml380searchFlg" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="sml.sml380.01" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addBan();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('banDelete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
    <div class="verAlignMid w100 mb10">
      <span class=" fs_13 ">
        <gsmsg:write key="sml.sml380.02" />
      </span>
      <html:text name="sml380Form" property="sml380keyword" maxlength="50" styleClass="wp180 ml_auto" />
      <button type="button" class="baseBtn " value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
        <gsmsg:write key="cmn.search" />
      </button>
    </div>

    <logic:notEmpty name="sml380Form" property="pageCombo">
    <div class="paging w100">
      <button type="button" class="webIconBtn ml_auto" onClick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select styleClass="paging_combo"  name="sml380Form" property="sml380pageTop" onchange="changePage(0);">
        <html:optionsCollection name="sml380Form" property="pageCombo" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
    </logic:notEmpty>


    <logic:notEmpty name="sml380Form" property="banList">
    <bean:define id="orderValue" name="sml380Form" property="sml380order" type="java.lang.Integer" />
    <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String destList = gsMsg.getMessage(request, "sml.sml380.03");
        String user = gsMsg.getMessage(request, "cmn.employer");
        String down = "<span class=\"classic-display\">▼</span><i class=\"original-display icon-sort_down\"></i>";
        String up   = "<span class=\"classic-display\">▲</span><i class=\"original-display icon-sort_up\"></i>";

    %>
    <% String orderLeft = ""; %>
    <% String orderRight = up; %>
    <% String nextOrder = String.valueOf(GSConstSmail.ORDER_KEY_DESC); %>
    <% if (orderValue.intValue() == GSConstSmail.ORDER_KEY_DESC) { %>
    <%    orderLeft = ""; %>
    <%    orderRight = down; %>
    <%    nextOrder = String.valueOf(GSConstSmail.ORDER_KEY_ASC); %>
    <% } %>

    <bean:define id="sortValue" name="sml380Form" property="sml380sortKey" type="java.lang.Integer" />
    <% String[] orderList = {String.valueOf(GSConstSmail.ORDER_KEY_ASC)}; %>
    <% String[] titleList = {destList}; %>
    <% int titleIndex = 0; %>
    <% titleList[titleIndex] = orderLeft + titleList[titleIndex] + orderRight; %>
    <% orderList[titleIndex] = nextOrder; %>

    <table class="m0 table-top w100">
      <tr>
        <th class="w5 txt_c js_tableTopCheck  js_tableTopCheck-header table_title-color cursor_p" onChange="chgCheckAll('sml380AllCheck', 'sml380selectBanList');"><input type="checkbox" name="sml380AllCheck" value="1" onChange=""></th>
        <th class="w45 txt_c table_title-color">
          <a href="#!" onClick="return sort(<%= String.valueOf(GSConstSmail.MSG_SORT_KEY_TITLE) %>, <%= orderList[0] %>);"><%= titleList[0] %></a>
        </th>
        <th class="w50 txt_c table_title-color"><gsmsg:write key="cmn.memo" /></th>
      </tr>
      <logic:iterate id="banData" name="sml380Form" property="banList" indexId="idx">
        <bean:define id="onClick">return editBan(<bean:write name="banData" property="sbcSid" />);</bean:define>
        <tr class="js_listHover cursor_p" data-sbcsid="<bean:write name="banData" property="sbcSid" />">
          <td class="txt_c js_tableTopCheck">
            <html:multibox name="sml380Form" property="sml380selectBanList">
              <bean:write name="banData" property="sbcSid" />
            </html:multibox>
          </td>
          <td class="txt_l js_listClick" onClick="<bean:write name="onClick"/>">
            <a href="#!" ><bean:write name="banData" property="dspSbcName" /></a>
          </td>
          <td class="txt_l js_listClick" onClick="<bean:write name="onClick"/>"><bean:write name="banData" property="dspSbcBiko" filter="false" /></td>
        </tr>
      </logic:iterate>
    </table>
    </logic:notEmpty>

    <logic:notEmpty name="sml380Form" property="pageCombo">
      <div class="paging w100">
        <button type="button" class="webIconBtn ml_auto" onClick="buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  name="sml380Form" property="sml380pageBottom" onchange="changePage(1);">
          <html:optionsCollection name="sml380Form" property="pageCombo" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
    </logic:notEmpty>

    <div class="txt_r mt10 footerBtn_block">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addBan();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('banDelete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('admTool');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>

  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>


</body>
</html:html>