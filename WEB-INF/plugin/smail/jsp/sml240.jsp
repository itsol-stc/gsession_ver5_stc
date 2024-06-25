<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.sml.GSConstSmail" %>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION<gsmsg:write key="wml.wml020.08" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../smail/js/sml240.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<body>

<html:form styleId="sml240Form" action="/smail/sml240">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<input type="hidden" name="smlCmdMode" value="0">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountMode" />
<html:hidden property="smlAccountSid" />

<html:hidden property="sml240svKeyword" />
<html:hidden property="sml240svGroup" />
<html:hidden property="sml240svUser" />
<html:hidden property="sml240sortKey" />
<html:hidden property="sml240order" />
<html:hidden property="sml240searchFlg" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent w90">
    <div class="kanriPageTitle">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li><gsmsg:write key="cmn.admin.setting" /></li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><gsmsg:write key="wml.100" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('acntImport');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png " alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('accountDetail', 0);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="accountDelete();">
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
      <div class="txt_l">
        <html:errors/>
      </div>
    </logic:messagesPresent>
    <table class="table-left">
      <tr>
        <th>
          <html:text name="sml240Form" property="sml240keyword" maxlength="50" styleClass="wp180" />
          <button type="button" class="baseBtn " value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
          <span class="fs_13"><gsmsg:write key="cmn.group" /></span>
          <html:select name="sml240Form" property="sml240group" onchange="buttonPush('init');" styleClass="wp180">
            <html:optionsCollection name="sml240Form" property="groupCombo" value="value" label="label" />
          </html:select>
          <button class="iconBtn-border" type="button" id="sml240GroupBtn" value="" onClick="openGroupWindow(this.form.sml240group, 'sml240group', '0', 'init');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>

          <span class="fs_13"><gsmsg:write key="cmn.user" /></span>
          <html:select name="sml240Form" property="sml240user" styleClass="wp180">
            <logic:iterate id="user" name="sml240Form" property="userCombo">
              <bean:define id="usrSidVal" name="user" property="value" />
              <logic:equal name="user" property="usrUkoFlg" value="1">
                  <html:option styleClass="mukoUserOption" value="<%=String.valueOf(usrSidVal) %>"><bean:write name="user" property="label" /></html:option>
              </logic:equal>
              <logic:notEqual name="user" property="usrUkoFlg" value="1">
                <html:option value="<%=String.valueOf(usrSidVal) %>"><bean:write name="user" property="label" /></html:option>
              </logic:notEqual>
            </logic:iterate>
          </html:select>
        </th>
      </tr>
    </table>
    <logic:notEmpty name="sml240Form" property="accountList">
    <div>
      <span class="flo_l fs_13 mb5">
        <gsmsg:write key="wml.wml030.03" />
      </span>
      <logic:equal name="sml240Form" property="sml240pageDspFlg" value="true">
        <span class="flo_r paging">
          <button type="button" class="webIconBtn ml_auto" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select name="sml240Form" property="sml240pageTop" styleClass="paging_combo" onchange="changePage(0);">
            <html:optionsCollection name="sml240Form" property="pageCombo" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </span>
      </logic:equal>
    </div>

    <bean:define id="orderValue" name="sml240Form" property="sml240order" type="java.lang.Integer" />
    <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String account = gsMsg.getMessage(request, "wml.96");
        String mail = gsMsg.getMessage(request, "cmn.mailaddress");
        String user = gsMsg.getMessage(request, "cmn.employer");
        String disk = gsMsg.getMessage(request, "wml.88");
        String date = gsMsg.getMessage(request, "cmn.received.date");
        String down = "<span class=\"classic-display\">▼</span><i class=\"original-display icon-sort_down\"></i>";
        String up   = "<span class=\"classic-display\">▲</span><i class=\"original-display icon-sort_up\"></i>";
    %>
    <% String orderLeft = up; %>
    <% String orderRight = ""; %>
    <% String nextOrder = String.valueOf(GSConstSmail.ORDER_DESC); %>
    <% if (orderValue.intValue() == GSConstSmail.ORDER_DESC) { %>
    <%    orderLeft = ""; %>
    <%    orderRight = down; %>
    <%    nextOrder = String.valueOf(GSConstSmail.ORDER_ASC); %>
    <% } %>

    <bean:define id="sortValue" name="sml240Form" property="sml240sortKey" type="java.lang.Integer" />
    <% String[] orderList = {String.valueOf(GSConstSmail.ORDER_ASC), String.valueOf(GSConstSmail.ORDER_ASC), String.valueOf(GSConstSmail.ORDER_ASC), String.valueOf(GSConstSmail.ORDER_ASC), String.valueOf(GSConstSmail.ORDER_ASC)}; %>
    <% String[] titleList = {account, mail, user, disk, date}; %>
    <% int titleIndex = 0; %>
    <% if (sortValue.intValue() == GSConstSmail.SKEY_MAIL) { titleIndex = 1; } %>
    <% if (sortValue.intValue() == GSConstSmail.SKEY_USER) { titleIndex = 2; } %>
    <% if (sortValue.intValue() == GSConstSmail.SKEY_DISKSIZE) { titleIndex = 3; } %>
    <% if (sortValue.intValue() == GSConstSmail.SKEY_RECEIVEDATE) { titleIndex = 4; } %>
    <% titleList[titleIndex] = titleList[titleIndex] + orderLeft + orderRight; %>
    <% orderList[titleIndex] = nextOrder; %>

    <table class="table-top table-fixed mt0 mb0 w100">
      <tr>
        <th class="wp25 txt_c js_tableTopCheck  js_tableTopCheck-header cursor_p" onChange="chgCheckAll('sml240AllCheck', 'sml240selectAcount');chgCheckAllChange('sml240AllCheck', 'sml240selectAcount');" ><input type="checkbox" name="sml240AllCheck" value="1" ></th>
        <th class="w25 txt_c  cursor_p">
          <a href="#" onClick="return sort(<%= String.valueOf(GSConstSmail.SKEY_ACCOUNTNAME) %>, <%= orderList[0] %>);"><%= titleList[0] %></a>
        </th>
        <th class="wp120 txt_c  cursor_p">
          <a href="#" onClick="return sort(<%= String.valueOf(GSConstSmail.SKEY_USER) %>, <%= orderList[2] %>);"><%= titleList[2] %></a>
        </th>
        <th class="wp130 txt_c  cursor_p">
          <a href="#" onClick="return sort(<%= String.valueOf(GSConstSmail.SKEY_DISKSIZE) %>, <%= orderList[3] %>);"><%= titleList[3] %></a>
        </th>
        <th class="txt_c "><gsmsg:write key="cmn.memo" /></th>
        <th class="wp80 "></th>
        <th class="wp80 "></th>
      </tr>

      <logic:iterate id="accountData" name="sml240Form" property="accountList" indexId="idx">
        <bean:define id="mukoUserClass"  >&nbsp;</bean:define>
        <logic:equal name="accountData" property="usrUkoFlg" value="1">
          <bean:define id="mukoUserClass"  >mukoUser</bean:define>
        </logic:equal>
        <tr class="js_listHover cursor_p" data-sid="<bean:write name="accountData" property="accountSid" />">
          <td class="txt_c js_tableTopCheck">
            <html:multibox name="sml240Form" property="sml240selectAcount" onclick="">
              <bean:write name="accountData" property="accountSid" />
            </html:multibox>
          </td>
          <td class="js_click cl_linkDef">
            <span class="<%=mukoUserClass %>"><bean:write name="accountData" property="accountName" /></span>
          </td>
          <td class="js_click ">
            <logic:greaterThan name="accountData" property="accountUserCount" value="0">
              <bean:write name="accountData" property="accountUserCount" />&nbsp;<gsmsg:write key="cmn.user" /><br>
            </logic:greaterThan>
            <logic:greaterThan name="accountData" property="accountGroupCount" value="0">
              <bean:write name="accountData" property="accountGroupCount" />&nbsp;<gsmsg:write key="cmn.group" />
            </logic:greaterThan>
          </td>
          <td class="js_click txt_r" ><bean:write name="accountData" property="diskSizeUse" />MB</td>
          <td class="js_click" ><bean:write name="accountData" property="viewBiko" filter="false" /></td>
          <td class="txt_c p0 no_w">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.label" />" onClick="confLabel(<bean:write name="accountData" property="accountSid" />);">
              <gsmsg:write key="cmn.label" />
            </button>
          </td>
          <td class="txt_c p0 no_w">
            <button type="button" class="baseBtn" value="<gsmsg:write key="wml.248" />" onClick="confFilter(<bean:write name="accountData" property="accountSid" />);">
              <gsmsg:write key="wml.248" />
            </button>
          </td>
        </tr>
      </logic:iterate>
    </table>
    </logic:notEmpty>

    <logic:equal name="sml240Form" property="sml240pageDspFlg" value="true">
    <div class="txt_r paging">
      <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select name="sml240Form" property="sml240pageBottom" styleClass="paging_combo" onchange="changePage(1);">
        <html:optionsCollection name="sml240Form" property="pageCombo" />
      </html:select>
      <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
        <i class="icon-paging_right"></i>
      </button>

    </div>
    </logic:equal>
  </div>
</div>
<%-- ダイアログ --%>
<div class="display_none">
  <div id="messagePop" title="" >
    <ul class="p0 verAlignMid w100">
      <li class="" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="pl10 dialog_msgbody">
         <span id="messageArea"></span>
      </li>
    </ul>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>