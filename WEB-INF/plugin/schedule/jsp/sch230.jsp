<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.sch.sch230.Sch230Const" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta name="format-detection" content="telephone=no">
  <title>GROUPSESSION<gsmsg:write key="schedule.sch230.01" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <theme:css filename="theme.css"/>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../schedule/js/sch230.js?<%= GSConst.VERSION_PARAM %>"></script>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../schedule/css/schedule.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>
</head>

<body>
<html:form action="/schedule/sch230">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="sch230editMode" value="">
<input type="hidden" name="sch230editData" value="">

<%@ include file="/WEB-INF/plugin/schedule/jsp/sch080_hiddenParams.jsp" %>
<logic:notEmpty name="sch230Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch230Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch230Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch230Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch230Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch230Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch230Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch230Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch230Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch230Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<html:hidden property="sch230svKeyword" />
<html:hidden property="sch230sortKey" />
<html:hidden property="sch230order" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="schedule.108" /></span><gsmsg:write key="schedule.sch230.01" />
    </li>
    <li>
      <div>
       <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('addSpAccess', 0);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" name="btn_delete" class="baseBtn" value="<gsmsg:write key="cmn.delete" />"   onClick="buttonPush('spAccessDelete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch230back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
  </div>
  <!-- 検索 -->
  <div class="txt_r mt10 mb10">
    <html:text name="sch230Form" property="sch230keyword" styleClass="wp200" maxlength="50" />
    <button class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <!-- ページング -->
  <div class="txt_r">
    <logic:notEmpty name="sch230Form" property="pageCombo">
      <span class="paging">
         <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
           <i class="icon-paging_left"></i>
         </button>
        <html:select name="sch230Form" property="sch230pageTop" styleClass="paging_combo" onchange="changePage(0);">
          <html:optionsCollection name="sch230Form" property="pageCombo" />
        </html:select>
         <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
           <i class="icon-paging_right"></i>
         </button>
      </span>
    </logic:notEmpty>
  </div>
  <!-- 一覧 -->
  <logic:notEmpty name="sch230Form" property="spAccessList">
    <bean:define id="orderValue" name="sch230Form" property="sch230order" type="java.lang.Integer" />
    <%
        jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String destList = gsMsg.getMessage(request, "schedule.sch240.04");
        String user = gsMsg.getMessage(request, "cmn.employer");
        String down = gsMsg.getMessage(request, "tcd.tcd040.23");
        String up = gsMsg.getMessage(request, "tcd.tcd040.22");
        String sortSign = up;
        String nextOrder = String.valueOf(Sch230Const.ORDER_DESC);
        if (orderValue.intValue() == Sch230Const.ORDER_DESC) {
            sortSign = down;
            nextOrder = String.valueOf(Sch230Const.ORDER_ASC);
        }
    %>
    <bean:define id="sortValue" name="sch230Form" property="sch230sortKey" type="java.lang.Integer" />
    <%
        String[] orderList = {String.valueOf(Sch230Const.ORDER_ASC)};
        String[] titleList = {destList};
        int titleIndex = 0;
        if (sortValue.intValue() == Sch230Const.SKEY_USER) {
            titleIndex = 1;
        }
        orderList[titleIndex] = nextOrder;
    %>
    <table class="table-top w100">
      <tr>
        <th class="w5 table_title-color js_tableTopCheck js_tableTopCheck-header cursor_p">
          <input type="checkbox" name="sch240AllCheck" value="1" />
        </th>
        <th class="w45 table_title-color cursor_p">
          <a class="table_headerSort-top" href="#" onClick="return sort(<%=String.valueOf(Sch230Const.SKEY_ACCOUNTNAME)%>, <%= orderList[0] %>);">
            <%= titleList[0] %><span class="classic-display"><%= sortSign %></span>
            <span class="original-display txt_m">
              <% if (sortSign.equals(down)) {%>
                <i class="icon-sort_down"></i>
              <%} else if (sortSign.equals(up)) { %>
                <i class="icon-sort_up"></i>
              <% } %>
            </span>
          </a>
        </th>
        <th class="w50 table_title-color">
          <gsmsg:write key="cmn.memo" />
        </th>
      </tr>
      <logic:iterate id="accessData" name="sch230Form" property="spAccessList" indexId="idx" type="jp.groupsession.v2.sch.sch230.Sch230SpAccessModel">
        <tr class="js_listHover cursor_p" id="<bean:write name="accessData" property="ssaSid" />">
          <td class="w5 txt_c js_tableTopCheck">
            <html:multibox name="sch230Form" property="sch230selectSpAccessList">
              <bean:write name="accessData" property="ssaSid" />
            </html:multibox>
          </td>
          <td class="w45 js_list_Click cl_linkDef">
            <bean:write name="accessData" property="name" />
          </td>
          <td class="w50 js_list_Click">
            <bean:write name="accessData" property="viewBiko" filter="false" />
          </td>
        </tr>
      </logic:iterate>
    </table>
  </logic:notEmpty>
  <!-- ページング -->
  <div class="txt_r mb10">
    <logic:notEmpty name="sch230Form" property="pageCombo">
      <span class="paging">
         <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
           <i class="icon-paging_left"></i>
         </button>
        <html:select name="sch230Form" property="sch230pageBottom" styleClass="paging_combo" onchange="changePage(1);">
          <html:optionsCollection name="sch230Form" property="pageCombo" />
        </html:select>
         <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
           <i class="icon-paging_right"></i>
         </button>
      </span>
    </logic:notEmpty>
  </div>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="buttonPush('addSpAccess', 0);">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
       <gsmsg:write key="cmn.add" />
     </button>
     <button type="button" name="btn_delete" class="baseBtn" value="<gsmsg:write key="cmn.delete" />"   onClick="buttonPush('spAccessDelete');">
       <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
       <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
       <gsmsg:write key="cmn.delete" />
     </button>
     <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('sch230back');">
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