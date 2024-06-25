<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.cir.GSConstCircular"%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION<gsmsg:write key="wml.wml020.08" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../circular/js/cir150.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form styleId="cir150Form" action="/circular/cir150">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />

    <html:hidden property="cirViewAccount" />
    <html:hidden property="cirAccountMode" />
    <html:hidden property="cirAccountSid" />
    <input type="hidden" name="cirCmdMode" value="0">

    <html:hidden property="cir010cmdMode" />
    <html:hidden property="cir010orderKey" />
    <html:hidden property="cir010sortKey" />
    <html:hidden property="cir010pageNum1" />
    <html:hidden property="cir010pageNum2" />
    <html:hidden property="cir010SelectLabelSid" />

    <html:hidden property="cir150svKeyword" />
    <html:hidden property="cir150svGroup" />
    <html:hidden property="cir150svUser" />
    <html:hidden property="cir150sortKey" />
    <html:hidden property="cir150order" />
    <html:hidden property="cir150searchFlg" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w85 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="cir.cir180.1" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('acntImport');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
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

    <div class="wrapper w85 mrl_auto">

        <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left mb20">
        <tr>
          <th class="w25">
            <html:text name="cir150Form" property="cir150keyword" maxlength="50" styleClass="wp200"/>
            <button type="button" onclick="buttonPush('search');" class="baseBtn" value="<gsmsg:write key="cmn.search" />">
              <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
              <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
              <gsmsg:write key="cmn.search" />
            </button>
            <span class="fs_13">
              <gsmsg:write key="cmn.group" />
            </span>

            <div class="verAlignMid">
              <html:select styleClass="wp200" name="cir150Form" property="cir150group" onchange="buttonPush('init');">
                <html:optionsCollection name="cir150Form" property="groupCombo" value="value" label="label" />
              </html:select>
              <button class="iconBtn-border ml5" type="button" id="cir150GroupBtn" value="" onClick="openGroupWindow(this.form.cir150group, 'cir150group', '0', 'init');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </div>
            <span class="fs_13">
              <gsmsg:write key="cmn.user" />
            </span>

            <html:select name="cir150Form" property="cir150user" styleClass="wp200">
              <logic:iterate id="user" name="cir150Form" property="userCombo">
                <bean:define id="mukoUserClass" value="" />
                <bean:define id="userValue">
                  <bean:write name="user" property="value" />
                </bean:define>
                <logic:equal name="user" property="usrUkoFlg" value="1">
                  <bean:define id="mukoUserClass">mukoUserOption</bean:define>
                </logic:equal>
                <html:option styleClass="<%=mukoUserClass%>" value="<%=userValue%>">
                  <bean:write name="user" property="label" />
                </html:option>
              </logic:iterate>
            </html:select>
          </th>
        </tr>
      </table>

      <div class="component_bothEnd">

        <div>
          <logic:notEmpty name="cir150Form" property="accountList">
            <span>
              <gsmsg:write key="wml.wml030.03" />
            </span>
          </logic:notEmpty>
        </div>
        <logic:equal name="cir150Form" property="cir150pageDspFlg" value="true">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="cir150pageTop" onchange="changePage(0);">
              <html:optionsCollection name="cir150Form" property="pageCombo" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:equal>
      </div>

      <logic:notEmpty name="cir150Form" property="accountList">

        <bean:define id="orderValue" name="cir150Form" property="cir150order" type="java.lang.Integer" />

        <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String account = gsMsg.getMessage(request, "wml.96");
        String mail = gsMsg.getMessage(request, "cmn.mailaddress");
        String user = gsMsg.getMessage(request, "cmn.employer");
        String disk = gsMsg.getMessage(request, "wml.88");
        String date = gsMsg.getMessage(request, "cmn.received.date");
        String down = "<span class=\"classic-display\">"
                + gsMsg.getMessage(request, "tcd.tcd040.23")
                + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_down\"></i></span>";
        String up = "<span class=\"classic-display\">"
                + gsMsg.getMessage(request, "tcd.tcd040.22")
                + "</span><span class=\"original-display txt_m\"><i class=\"icon-sort_up\"></i></span>";
        %>
        <% String orderLeft = ""; %>
        <% String orderRight = up; %>
        <% String nextOrder = String.valueOf(GSConstCircular.ORDER_DESC); %>
        <% if (orderValue.intValue() == GSConstCircular.ORDER_DESC) { %>
        <%    orderLeft = ""; %>
        <%    orderRight = down; %>
        <%    nextOrder = String.valueOf(GSConstCircular.ORDER_ASC); %>
        <% } %>

        <bean:define id="sortValue" name="cir150Form" property="cir150sortKey" type="java.lang.Integer" />
        <% String[] orderList = {String.valueOf(GSConstCircular.ORDER_ASC), String.valueOf(GSConstCircular.ORDER_ASC), String.valueOf(GSConstCircular.ORDER_ASC), String.valueOf(GSConstCircular.ORDER_ASC), String.valueOf(GSConstCircular.ORDER_ASC)}; %>
        <% String[] titleList = {account, mail, user, disk, date}; %>
        <% int titleIndex = 0; %>
        <% if (sortValue.intValue() == GSConstCircular.SKEY_USER) { titleIndex = 2; } %>
        <% if (sortValue.intValue() == GSConstCircular.SKEY_DISKSIZE) { titleIndex = 3; } %>
        <% if (sortValue.intValue() == GSConstCircular.SKEY_RECEIVEDATE) { titleIndex = 4; } %>
        <% titleList[titleIndex] = orderLeft + titleList[titleIndex] + orderRight; %>
        <% orderList[titleIndex] = nextOrder; %>

        <table class="table-top">
          <tr>
            <th class="cursor_p js_tableTopCheck js_tableTopCheck-header no_w table_header-evt js_table_header-evt">
              <input type="checkbox" name="cir150AllCheck" value="1" onClick="chgCheckAll('cir150AllCheck', 'cir150selectAcount');">
            </th>
            <th class="w45 cursor_p table_header-evt js_table_header-evt">
              <a href="#" onClick="return sort(<%=String.valueOf(GSConstCircular.SKEY_ACCOUNTNAME)%>, <%=orderList[0]%>);"><%=titleList[0]%></a>
            </th>
            <th class="w20 cursor_p table_header-evt js_table_header-evt">
              <a href="#" onClick="return sort(<%=String.valueOf(GSConstCircular.SKEY_USER)%>, <%=orderList[2]%>);"><%=titleList[2]%></a>
            </th>
            <th class="w35">
              <gsmsg:write key="cmn.memo" />
            </th>

            <th></th>

          </tr>

         <logic:iterate id="accountData" name="cir150Form" property="accountList" indexId="idx">
            <bean:define id="backclass" value="" />
            <bean:define id="backclass_no_edit" value="" />
            <bean:define id="backpat" value="<%=String.valueOf((idx.intValue() % 2) + 1)%>" />
            <bean:define id="back" value="<%=String.valueOf(backclass) + String.valueOf(backpat)%>" />
            <bean:define id="back_no_edit" value="<%=String.valueOf(backclass_no_edit) + String.valueOf(backpat)%>" />

            <tr class="<%=String.valueOf(back)%> js_listHover cursor_p" id="<bean:write name="accountData" property="accountSid" />">

              <td class="txt_c cursor_p js_tableTopCheck">
                <% if (Integer.valueOf(backpat) == 1) { %>
                <html:multibox name="cir150Form" property="cir150selectAcount">
                  <bean:write name="accountData" property="accountSid" />
                </html:multibox>
                <% } else { %>
                <html:multibox name="cir150Form" property="cir150selectAcount">
                  <bean:write name="accountData" property="accountSid" />
                </html:multibox>
                <% } %>
              </td>

              <td class="txt_l js_listClick">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal name="accountData" property="usrUkoFlg" value="1">
                  <bean:define id="mukoUserClass">mukoUser</bean:define>
                </logic:equal>
                  <span class="cl_linkDef <%=mukoUserClass%>">
                    <bean:write name="accountData" property="accountName" />
                  </span>
              </td>
              <td class="txt_l js_listClick">
                <logic:greaterThan name="accountData" property="accountUserCount" value="0">
                  <div>
                  <span class="mr5">
                    <bean:write name="accountData" property="accountUserCount" />
                  </span>
                    <gsmsg:write key="cmn.user" />
                  </div>
                </logic:greaterThan>
                <logic:greaterThan name="accountData" property="accountGroupCount" value="0">
                  <span class="mr5">
                    <bean:write name="accountData" property="accountGroupCount" />
                  </span>
                    <gsmsg:write key="cmn.group" />
                </logic:greaterThan>
              </td>

              <td class="txt_l js_listClick">
                <span>
                  <bean:write name="accountData" property="viewBiko" filter="false" />
                </span>
              </td>
              <td class="txt_l">
                <button type="button" onclick="confLabel(<bean:write name="accountData" property="accountSid" />);" class="baseBtn no_w" value="<gsmsg:write key="cmn.label" />"><gsmsg:write key="cmn.label" /></button>
              </td>
            </tr>
          </logic:iterate>

        </table>
      </logic:notEmpty>
      <logic:equal name="cir150Form" property="cir150pageDspFlg" value="true">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="cir150pageBottom" onchange="changePage(1);">
            <html:optionsCollection name="cir150Form" property="pageCombo" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:equal>
    </div>

    <!-- 削除出来ないユーザがいれば表示される -->
    <div id="messagePop" title="" class="display_n">
      <table id="cir150_errorList"class="w100">
        <tr>
          <td class="w15">
            <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
          </td>
          <td class="w85">
            <b id="messageArea" class="fs_13"></b>
          </td>
        </tr>
      </table>
    </div>

  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

</body>
</html:html>