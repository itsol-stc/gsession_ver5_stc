<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.ntp.ntp088.Ntp088Const" %>
<!DOCTYPE html>
<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <title>GROUPSESSION<gsmsg:write key="schedule.sch080.10" /></title>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
</head>
  <script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../nippou/js/ntp088.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body>

<html:form action="/nippou/ntp088">
<input type="hidden" name="CMD" value="">
<input type="hidden" name="ntp088editMode" value="">
<input type="hidden" name="ntp088editData" value="">

<ntp:conf_hidden name="ntp088Form"/>

<html:hidden property="ntp088svKeyword" />
<html:hidden property="ntp088sortKey" />
<html:hidden property="ntp088order" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="ntp.1" /></span><gsmsg:write key="schedule.sch230.01" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('addSpAccess', 0);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('spAccessDelete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ntp088back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </li>
  </ul>
</div>

<div class="wrapper w80 mrl_auto">
  <div class="txt_r mt10 mb10">
    <html:text name="ntp088Form" property="ntp088keyword" maxlength="50" styleClass="wp200" />
    <button class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onclick="buttonPush('search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <logic:notEmpty name="ntp088Form" property="pageCombo">
    <div class="paging">
      <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="前頁">
        <i class="icon-paging_left"></i>
      </button>
      <html:select name="ntp088Form" property="ntp088pageTop" styleClass="paging_combo" onchange="changePage(0);">
        <html:optionsCollection name="ntp088Form" property="pageCombo" />
      </html:select>
      <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="次頁">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>

  <logic:notEmpty name="ntp088Form" property="spAccessList">
    <bean:define id="orderValue" name="ntp088Form" property="ntp088order" type="java.lang.Integer" />
    <%
        jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String destList = gsMsg.getMessage(request, "schedule.sch240.04");
        String user = gsMsg.getMessage(request, "cmn.employer");
        String down = "<span class=\"classic-display\">▼</span><span class=\"original-display txt_m\"><i class=\"icon-sort_down\"></i></span>";
        String up = "<span class=\"classic-display\">▲</span><span class=\"original-display txt_m\"><i class=\"icon-sort_up\"></i></span>";
        String orderLeft = "";
        String orderRight = up;
        String nextOrder = String.valueOf(Ntp088Const.ORDER_DESC);
        if (orderValue.intValue() == Ntp088Const.ORDER_DESC) {
          orderLeft = "";
          orderRight = down;
          nextOrder = String.valueOf(Ntp088Const.ORDER_ASC);
        }
    %>
    <bean:define id="sortValue" name="ntp088Form" property="ntp088sortKey" type="java.lang.Integer" />
    <%
        String[] orderList = {String.valueOf(Ntp088Const.ORDER_ASC)};
        String[] titleList = {destList};
        int titleIndex = 0;
        if (sortValue.intValue() == Ntp088Const.SKEY_USER) { titleIndex = 1; }
        titleList[titleIndex] = orderLeft + titleList[titleIndex] + orderRight;
        orderList[titleIndex] = nextOrder;
    %>
    <table class="table-top w100">
      <tr>
        <th class="w5 cursor_p table_header-evt js_tableTopCheck js_tableTopCheck-header txt_c">
          <input type="checkbox" name="ntp088AllCheck" value="1">
        </th>
        <th class="txt_c w50 cursor_p table_header-evt js_table_header-evt">
          <div onClick="return sort(<%=String.valueOf(Ntp088Const.SKEY_ACCOUNTNAME)%>, <%= orderList[0] %>);"><%= titleList[0] %></div>
        </th>
        <th class="txt_c w45">
          <gsmsg:write key="cmn.memo" />
        </th>
      </tr>
      <logic:iterate id="accessData" name="ntp088Form" property="spAccessList" indexId="idx" type="jp.groupsession.v2.ntp.ntp088.Ntp088SpAccessModel">
        <tr id="<bean:write name="accessData" property="ssaSid" />" class="js_listHover cursor_p">
          <td class="w5 js_tableTopCheck txt_c">
            <html:multibox name="ntp088Form" property="ntp088selectSpAccessList">
              <bean:write name="accessData" property="ssaSid" />
            </html:multibox>
          </td>
          <td class="w50 js_listClick cl_linkDef">
            <bean:write name="accessData" property="name" />
          </td>
          <td class="w45 js_listClick">
            <bean:write name="accessData" property="viewBiko" filter="false" />
          </td>
        </tr>
      </logic:iterate>
    </table>
  </logic:notEmpty>

  <logic:notEmpty name="ntp088Form" property="pageCombo">
    <div class="paging">
      <button type="button" class="webIconBtn" onclick="buttonPush('prevPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="前頁">
        <i class="icon-paging_left"></i>
      </button>
      <html:select name="ntp088Form" property="ntp088pageBottom" styleClass="paging_combo" onchange="changePage(1);">
        <html:optionsCollection name="ntp088Form" property="pageCombo" />
      </html:select>
      <button type="button" class="webIconBtn" onclick="buttonPush('nextPage');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="次頁">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>