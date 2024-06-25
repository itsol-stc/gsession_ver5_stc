<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

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
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp110.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
<body >
<html:form action="/anpi/anp110">
<!-- BODY -->
<input type="hidden" name="CMD">
<html:hidden property="backScreen" />
<html:hidden property="anp110SelectUserSid" />
<html:hidden property="anp110SelectUserNm" />
<html:hidden property="anp110SortKeyIndex" />
<html:hidden property="anp110OrderKey" />
<html:hidden property="anp110NowPage" />
<html:hidden property="anp110DspPage1" />
<html:hidden property="anp110DspPage2" />
<!-- ヘッダー -->
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin"/></span><gsmsg:write key="anp.anp110.01"/>
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp110import');">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp110back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <!-- エラーメッセージ -->
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  <!-- コンテンツ -->
  <table class="txt_l">
    <tr>
      <td class="fw_b">
        <gsmsg:write key="cmn.mailaddress"/>
      </td>
      <td class="fw_b">
        ：
      </td>
      <td class>
        <span class="verAlignMid">
          <html:radio styleId="mailAll" name="anp110Form" property="anp110SelectMailFilter" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.FILTER_FLG_ALL) %>" onclick="buttonPush('anp110filter');" /><label for="mailAll"><gsmsg:write key="cmn.all"/></label>
          <html:radio styleClass="ml10" styleId="mailReg" name="anp110Form" property="anp110SelectMailFilter" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.FILTER_FLG_REG) %>" onclick="buttonPush('anp110filter');" /><label for="mailReg"><gsmsg:write key="anp.anp110.02"/></label>
          <html:radio styleClass="ml10" styleId="mailNone" name="anp110Form" property="anp110SelectMailFilter" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.FILTER_FLG_NONE) %>" onclick="buttonPush('anp110filter');" /><label for="mailNone"><gsmsg:write key="anp.anp110.03"/></label>
        </span>
      </td>
    </tr>
    <td class="fw_b">
      <gsmsg:write key="cmn.tel"/>
    </td>
    <td class="fw_b">
      ：
    </td>
    <td>
    <span class="verAlignMid">
      <html:radio styleId="tellAll" name="anp110Form" property="anp110SelectTellFilter" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.FILTER_FLG_ALL) %>" onclick="buttonPush('anp110filter');" /><label for="tellAll"><gsmsg:write key="cmn.all"/></label>
      <html:radio styleClass="ml10" styleId="tellReg" name="anp110Form" property="anp110SelectTellFilter" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.FILTER_FLG_REG) %>" onclick="buttonPush('anp110filter');" /><label for="tellReg"><gsmsg:write key="anp.anp110.02"/></label>
      <html:radio styleClass="ml10" styleId="tellNone" name="anp110Form" property="anp110SelectTellFilter" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.FILTER_FLG_NONE) %>" onclick="buttonPush('anp110filter');" /><label for="tellNone"><gsmsg:write key="anp.anp110.03"/></label>
    </span>
    </td>
    </table>
    <table class="txt_l w100">
      <td class="fw_b no_w w5">
        <gsmsg:write key="cmn.show.group" />
      </td>
      <td>
        <logic:notEmpty name="anp110Form" property="anp110GroupLabel" scope="request">
          <html:select property="anp110SelectGroupSid" onchange="buttonPush('anp110group');" >
            <logic:iterate id="gpBean" name="anp110Form" property="anp110GroupLabel" scope="request">
              <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                <logic:equal name="gpBean" property="styleClass" value="0">
                  <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                </logic:equal>
                <logic:notEqual name="gpBean" property="styleClass" value="0">
                  <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                </logic:notEqual>
              </logic:iterate>
            </html:select>
            <button class="iconBtn-border" type="button" id="groupBtn" value="  " onClick="openGroupWindow(this.form.anp110SelectGroupSid, 'anp110SelectGroupSid', '1', 'anp110group');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </logic:notEmpty>
        </td>
      <td class = "txt_r">
        <!-- ページング -->
        <bean:size id="pageCount" name="anp110Form" property="anp110PageLabel" scope="request" />
        <logic:greaterThan name="pageCount" value="1">
          <div class="paging txt_r w100">
            <button type="button" class="webIconBtn" onClick="buttonPush('anp110pageLast');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo"  property="anp110DspPage1" onchange="changePage(this);">
              <html:optionsCollection name="anp110Form" property="anp110PageLabel" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('anp110pageNext');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </td>
    </table>

     <table class="table-top w100">
       <tr>
         <!-- 氏名列オーダーキー -->
         <bean:define id="sortKeyIndex" name="anp110Form" property="anp110SortKeyIndex" />
         <bean:define id="orderKey" name="anp110Form" property="anp110OrderKey" />
         <gsmsg:define id="h_num" msgkey="cmn.employee.staff.number"/>
         <gsmsg:define id="h_name" msgkey="cmn.name"/>
         <gsmsg:define id="h_post" msgkey="cmn.post"/>
         <gsmsg:define id="h_mail" msgkey="cmn.mailaddress"/>
         <gsmsg:define id="h_tel" msgkey="cmn.tel"/>
         <% int iSortKeyIndex = ((Integer) sortKeyIndex).intValue();   %>
         <% int iOrderKey = ((Integer) orderKey).intValue();     %>
         <% String[] colTitle = new String[] {h_num, h_name, h_post, h_mail, h_tel}; %>
         <% String[] colWidth = new String[] {"w15", "w20", "w10", "w35", "w20"}; %>
         <% Integer[] colOrder = new Integer[] {1, 1, 1, 1, 1}; %>
         <% for (int i = 0; i < colTitle.length; i++) {      %>
           <%   String title = colTitle[i];                   %>
           <%   Integer order = -1;                           %>
           <%   String sortCla = "";                           %>
           <%   String sortOri = "";                           %>
           <%   if (iSortKeyIndex == i) {                     %>
           <%     if (iOrderKey == GSConst.ORDER_KEY_ASC) {   %>
           <%       title = title      ;                      %>
           <%       sortCla = "▲";                            %>
           <%       sortOri = "<i class=\"icon-sort_up\"></i>";%>
           <%     } else {                                    %>
           <%       title = title;                            %>
           <%       sortCla = "▼";                            %>
           <%       sortOri = "<i class=\"icon-sort_down\"></i>";%>
           <%     }                                           %>
           <%     order = iOrderKey;                          %>
           <%   }                                             %>
              <th class="<%= colWidth[i] %> txt_c no_w cursor_p table_header-evt js_table_header-evt">
            <% if (colOrder[i] == 1) { %><a href="#" onClick="return sortList(<%= i %>, <%= order %>);"><% } %>
              <span class="classic-display"><%= title %><%= sortCla %></span>
            <% if (iOrderKey == GSConst.ORDER_KEY_ASC) { %>
              <span class="original-display txt_m"><%= title %><%= sortOri %></span>
            <% } else { %>
              <span class="original-display txt_m"><%= title %><%= sortOri %></span>
            <% } %>
            <% if (colOrder[i] == 1) { %></a><% } %></th>
          <% } %>
          </tr>
        <logic:notEmpty name="anp110Form" property="anp110List">
          <logic:iterate id="detailModel" name="anp110Form" property="anp110List" indexId="idx">
            <bean:define id="mukoUserClass" value="" />
            <bean:define id="backclass" value="td_line_color" />
            <bean:define id="backclass_no_edit" value="td_line_no_edit_color" />
            <bean:define id="backpat" value="<%= String.valueOf((idx.intValue() % 2) + 1) %>" />
            <bean:define id="back" value="<%= String.valueOf(backclass) + String.valueOf(backpat) %>" />
            <bean:define id="back_no_edit" value="<%= String.valueOf(backclass_no_edit) + String.valueOf(backpat) %>" />
            <logic:equal value="1" name="detailModel" property="usrUkoFlg"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>
            <tr class = "js_listHover cursor_p" id="<bean:write name="detailModel" property="usrSid" />,<bean:write name="detailModel" property="name" />"/>
              <td class="js_listClick"><span class="<%=mukoUserClass%>"><bean:write name="detailModel" property="syainNo" /></span></td>
              <td class="js_listClick cl_linkDef"><span class="<%=mukoUserClass%>"><bean:write name="detailModel" property="name" /></span></td>
              <td class="js_listClick"><span class="<%=mukoUserClass%>"><bean:write name="detailModel" property="post" /></span></td>
              <td class="js_listClick"><span class="<%=mukoUserClass%>"><bean:write name="detailModel" property="mailAdr" /></span></td>
              <td class="js_listClick"><bean:write name="detailModel" property="telNo" /></td>
            </tr>
          </logic:iterate>
        </logic:notEmpty>
    </table>
      <!-- ページング -->
      <bean:size id="pageCount" name="anp110Form" property="anp110PageLabel" scope="request" />
      <logic:greaterThan name="pageCount" value="1">
      <div class="paging mb10">
        <button type="button" class="webIconBtn" onClick="buttonPush('anp110pageLast');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select styleClass="paging_combo"  property="anp110DspPage1" onchange="changePage(this);">
          <html:optionsCollection name="anp110Form" property="anp110PageLabel" value="value" label="label" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="buttonPush('anp110pageNext');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </div>
      </logic:greaterThan>
    <!-- ボタン -->
  <div class="footerBtn_block">
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp110import');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
      <gsmsg:write key="cmn.import" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp110back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>