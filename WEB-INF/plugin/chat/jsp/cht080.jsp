<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cht.cht080.Cht080Const" %>

<!DOCTYPE html>


<html:html>
<head>

<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">

<title>GROUPSESSION<gsmsg:write key="schedule.sch080.10" /></title>
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/check.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?500'></script>
<script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../chat/js/cht080.js?<%= GSConst.VERSION_PARAM %>2'></script>
</head>

<body>

<html:form action="/chat/cht080">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<input type="hidden" name="cht080editMode" value="">
<input type="hidden" name="cht080editData" value="">
<input type="hidden" name="cht080sortKey" value="">
<input type="hidden" name="cht080order" value="">
<html:hidden property="cht080svKeyword" />


<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cht.01" /></span><gsmsg:write key="cht.cht080.01" />
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
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('cht080back');">
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
    <html:text name="cht080Form" property="cht080keyword" styleClass="wp200" maxlength="50" />
    <button class="baseBtn" name="btn_usrimp" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush('search');">
      <img src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_classicImg-display">
      <img src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />" class="btn_originalImg-display">
      <gsmsg:write key="cmn.search" />
    </button>
  </div>
  <!-- ページング -->
  <div class="txt_r">
    <logic:notEmpty name="cht080Form" property="pageCombo">
      <span class="paging">
        <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select name="cht080Form" property="cht080pageTop" styleClass="text_i" onchange="changePage(0);">
          <html:optionsCollection name="cht080Form" property="pageCombo" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </span>
    </logic:notEmpty>
  </div>

  <logic:notEmpty name="cht080Form" property="spAccessList">

    <bean:define id="orderValue" name="cht080Form" property="cht080order" type="java.lang.Integer" />
    <%  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
        String destList = gsMsg.getMessage(request, "schedule.sch240.04");
        String down = gsMsg.getMessage(request, "tcd.tcd040.23");
        String up = gsMsg.getMessage(request, "tcd.tcd040.22");
        String sortSign = up;

        String nextOrder = String.valueOf(Cht080Const.ORDER_DESC);
        if (orderValue.intValue() == Cht080Const.ORDER_DESC) {
            sortSign = down;
            nextOrder = String.valueOf(Cht080Const.ORDER_ASC);
        }
    %>

    <table class="table-top w100">
      <tr>
        <th class="w5 table_title-color js_tableTopCheck js_tableTopCheck-header cursor_p">
          <input type="checkbox" name="cht080AllCheck" value="1" onclick="chgCheckAll('cht080AllCheck', 'cht080selectSpAccessList');" />
        </th>

        <th class="w45 table_title-color cursor_p">
          <a class="table_headerSort-top" href="#" onClick="return sort(0, <%= nextOrder %>);">
            <gsmsg:write key="schedule.sch240.04" /><span class="classic-display"><%= sortSign %></span>
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

      <logic:iterate id="accessData" name="cht080Form" property="spAccessList" indexId="idx" type="jp.groupsession.v2.cht.cht080.Cht080SpAccessModel">
        <tr class="js_listHover cursor_p" id="<bean:write name="accessData" property="ssaSid" />">
          <td class="w5 txt_c js_tableTopCheck">
            <html:multibox name="cht080Form" property="cht080selectSpAccessList">
              <bean:write name="accessData" property="ssaSid" />
            </html:multibox>
          </td>
          <td class="w45 js_list_Click cl_linkDef">
             <a href="#" class="cl_linkDef" onClick="return editAccess(<bean:write name="accessData" property="ssaSid" />);"><bean:write name="accessData" property="name" /></a>
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
    <logic:notEmpty name="cht080Form" property="pageCombo">
      <span class="paging">
        <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
          <i class="icon-paging_left"></i>
        </button>
        <html:select name="cht080Form" property="cht080pageBottom" styleClass="text_i" onchange="changePage(1);">
          <html:optionsCollection name="cht080Form" property="pageCombo" />
        </html:select>
        <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
          <i class="icon-paging_right"></i>
        </button>
      </span>
    </logic:notEmpty>
  </div>
</div>
</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>
</html:html>