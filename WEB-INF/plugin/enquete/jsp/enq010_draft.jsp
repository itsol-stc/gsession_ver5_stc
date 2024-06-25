<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>
<%@ page import="jp.groupsession.v2.enq.enq010.Enq010Const" %>


<bean:define id="enq010crtUser" name="enq010Form" property="enq010crtUser" type="java.lang.Boolean" />
<% boolean enqCrtUser = enq010crtUser.booleanValue(); %>
<bean:define id="enqSortKey" name="enq010Form" property="enq010sortKey" type="java.lang.Integer" />
<bean:define id="enqOrder" name="enq010Form" property="enq010order" type="java.lang.Integer" />
<% String sortSign = ""; %>
<% String nextOrder = ""; %>
<% int titleSortKey = Enq010Const.SORTKEY_OPEN; %>

      <logic:notEmpty name="enq010Form" property="pageList">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />"> <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="enq010pageTop" onchange="enq010changePage('0');">
            <html:optionsCollection name="enq010Form" property="pageList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />"> <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>

     <!-- 一覧 -->

      <table class="table-top w100" cellpadding="5" cellspacing="0">
        <tr>
        <% if (enqCrtUser) { %>
          <th class="no_w js_tableTopCheck js_tableTopCheck-header cursor_p"><input type="checkbox" name="enq010allCheck" value="1" onclick="chgCheckAll('enq010allCheck', 'enq010selectEnqSid');"></th>
        <% } %>

          <%
            jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
            String[] widthList = {"w0", "w15", "w30", "w20", "w20", "w15"};
            String[] titleList = {gsMsg.getMessage(request, "enq.24"),  gsMsg.getMessage(request, "enq.25"), gsMsg.getMessage(request, "cmn.title"), gsMsg.getMessage(request, "enq.53"), "&nbsp;" + gsMsg.getMessage(request, "enq.19"), gsMsg.getMessage(request, "enq.enq210.11")};
            int[] sortKeyList = {Enq010Const.SORTKEY_PRIORITY,  Enq010Const.SORTKEY_SENDER,  Enq010Const.SORTKEY_TITLE,  Enq010Const.SORTKEY_OPEN,  Enq010Const.SORTKEY_ANSLIMIT,  Enq010Const.SORTKEY_ANS_OPEN};
            for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
              if (enqSortKey.intValue() == sortKeyList[titleIdx]) {
                if (enqOrder.intValue() == 1) { sortSign="<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>"; nextOrder = "0"; } else { sortSign="<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>"; nextOrder = "1"; }
                } else { nextOrder = "0"; sortSign = ""; }
          %>
          <th class="<%= widthList[titleIdx] %> no_w cursor_p">
          <a href="#" onClick="enq010ClickTitle(<%= String.valueOf(sortKeyList[titleIdx]) %>, <%= nextOrder %>);" class=""><%= titleList[titleIdx] %><%= sortSign %></a>
          <% if (titleIdx == 1 && enqSortKey.intValue() == Enq010Const.SORTKEY_MAKEDATE) { if (enqOrder.intValue() == 1) { sortSign="<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>"; nextOrder = "0"; } else { sortSign="<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>"; nextOrder = "1"; }%><br><a href="#" onClick="enq010ClickTitle(<%= String.valueOf(Enq010Const.SORTKEY_MAKEDATE) %>, <%= nextOrder %>);" class=""><gsmsg:write key="man.creation.date" /><%= sortSign %></a>
          <% } else if (titleIdx == 1) {  { nextOrder = "0"; sortSign = ""; } %><br><a href="#" onClick="enq010ClickTitle(<%= String.valueOf(Enq010Const.SORTKEY_MAKEDATE) %>, <%= nextOrder %>);" class=""><gsmsg:write key="man.creation.date" /><%= sortSign %></a>         <% } %>
          </th>
          <% } %>

        <% if (enqCrtUser) { %>
          <th class="no_w"><span>&nbsp;</span></th>
        <% } %>
        </tr>
        <logic:notEmpty name="enq010Form" property="enq010EnqueteList">
        <logic:iterate id="enqData" name="enq010Form" property="enq010EnqueteList" indexId="idx">
        <tr>
        <% if (enqCrtUser) { %>
          <td class="txt_m txt_c no_w js_tableTopCheck cursor_p">
            <html:multibox name="enq010Form" property="enq010selectEnqSid">
              <bean:write name="enqData"  property="enqSid" />
            </html:multibox>
          </td>
        <% } %>
          <td class="txt_m txt_c no_w">
           <bean:define id="enqPriority" name="enqData" property="priority" type="java.lang.Integer" />
           <% if (enqPriority.intValue() == GSConstEnquete.JUUYOU_0) { %>
             <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star4" border="0" alt="<gsmsg:write key="project.58" />">
             <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
             <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star4" border="0" alt="<gsmsg:write key="project.58" />">
             <span class="original-display">
               <i class="icon-star importance-blue"></i>
               <i class="icon-star_line"></i>
               <i class="icon-star_line"></i>
             </span>
           <% } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_1) { %>
             <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
             <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
             <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star4" border="0" alt="<gsmsg:write key="project.59" />">
             <span class="original-display">
               <i class="icon-star importance-yellow"></i>
               <i class="icon-star importance-yellow"></i>
               <i class="icon-star_line"></i>
             </span>
           <% } else if (enqPriority.intValue() == GSConstEnquete.JUUYOU_2) { %>
             <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star3" border="0" alt="<gsmsg:write key="project.60" />">
             <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star3" border="0" alt="<gsmsg:write key="project.60" />">
             <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star3" border="0" alt="<gsmsg:write key="project.60" />">
             <span class="original-display">
               <i class="icon-star importance-red"></i>
               <i class="icon-star importance-red"></i>
               <i class="icon-star importance-red"></i>
             </span>
            <% } %>
          </td>
          <td class="txt_l txt_m">
            <bean:define id="sdFlg" name="enqData" property="senderDelFlg" type="java.lang.Boolean" />
            <span class="no_w <% if (sdFlg) { %> text_deluser_enq<% } %>">
              <bean:write name="enqData" property="sender" />
            </span>
            <br>
            <span>
              <bean:write name="enqData" property="makeDate" />
            </span>
          </td>
          <td class="txt_l txt_m">
            <logic:notEmpty name="enqData" property="typeName">
              <span class="baseLabel"><bean:write name="enqData" property="typeName" /></span>
            </logic:notEmpty>
            <span><bean:write name="enqData" property="title" /></span>
          </td>
          <td class="txt_c txt_m">
          <span>
          <bean:write name="enqData" property="pubStartDateStr" filter='false'/>
          </span>
          </td>
          <td class="txt_c txt_m" >
            <span><bean:write name="enqData" property="ansLimitDate" /></span>
          </td>
            <td class="txt_c">
              <logic:equal name="enqData" property="ansOpen" value="<%= String.valueOf(GSConstEnquete.KOUKAI_ON) %>">
              <span class="no_w">
              <bean:write name="enqData" property="ansPubStartDate" />
              </span>
              <logic:empty name="enqData" property="pubEndDateStr">
              &nbsp;～<br>
              <gsmsg:write key="main.man200.9" />
              </logic:empty>
              <logic:notEmpty name="enqData" property="pubEndDateStr">
              &nbsp;～<br>
              <span class="no_w"><bean:write name="enqData" property="pubEndDateStr" /></span>
              </logic:notEmpty>
              </logic:equal>
              <logic:notEqual name="enqData" property="ansOpen" value="<%= String.valueOf(GSConstEnquete.KOUKAI_ON) %>">
              <gsmsg:write key="cmn.private" />
              </logic:notEqual>
            </td>

        <% if (enqCrtUser) { %>
          <td class="txt_c txt_m no_w">
            <button type="button" onclick="editEnquete(<bean:write name="enqData" property="enqSid" />);" class="baseBtn" value="<gsmsg:write key="cmn.edit" />">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.edit" />
            </button>
          </td>
        <% } %>
        </tr>

        </logic:iterate>
        </logic:notEmpty>
      </table>

      <logic:notEmpty name="enq010Form" property="pageList">
        <div class="paging">
         <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />"> <i class="icon-paging_left"></i>
          </button>
         <html:select styleClass="paging_combo" property="enq010pageBottom" onchange="enq010changePage('1');">
           <html:optionsCollection name="enq010Form" property="pageList" value="value" label="label" />
         </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
           <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />"> <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>
