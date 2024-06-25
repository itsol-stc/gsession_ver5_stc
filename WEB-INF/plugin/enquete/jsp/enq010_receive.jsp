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

      <table class="table-top w100">
      <tbody>
        <tr>
          <%
            jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
            String[] widthList = {"w5", "w3", "w15", "w30", "w15", "w15"};
            String[] titleList = {gsMsg.getMessage(request, "cmn.status"), gsMsg.getMessage(request, "enq.24"), gsMsg.getMessage(request, "enq.25"), gsMsg.getMessage(request, "cmn.title"), gsMsg.getMessage(request, "enq.19"), gsMsg.getMessage(request, "enq.enq210.11")};
            int[] sortKeyList = {Enq010Const.SORTKEY_STATUS,  Enq010Const.SORTKEY_PRIORITY,  Enq010Const.SORTKEY_SENDER,  Enq010Const.SORTKEY_TITLE,  Enq010Const.SORTKEY_ANSLIMIT,  Enq010Const.SORTKEY_OPEN};
            for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
              if (enqSortKey.intValue() == sortKeyList[titleIdx]) {
                if (enqOrder.intValue() == 1) { sortSign="<span class=\"classic-display\">▼</span><span class=\"original-display\"><i class=\"icon-sort_down\"></i></span>"; nextOrder = "0"; } else { sortSign="<span class=\"classic-display\">▲</span><span class=\"original-display\"><i class=\"icon-sort_up\"></i></span>"; nextOrder = "1"; }
                } else { nextOrder = "0"; sortSign = ""; }
          %>
          <th class="<%= widthList[titleIdx] %> no_w cursor_p"><a href="#" onClick="enq010ClickTitle(<%= String.valueOf(sortKeyList[titleIdx]) %>, <%= nextOrder %>);"><%= titleList[titleIdx] %><%= sortSign %></a>
          <% } %>
        </tr>
        <logic:notEmpty name="enq010Form" property="enq010EnqueteList">
        <logic:iterate id="enqData" name="enq010Form" property="enq010EnqueteList" indexId="idx" type="jp.groupsession.v2.enq.enq010.Enq010EnqueteModel">
          <tr>
            <td class="txt_c txt_m no_w">
              <logic:equal name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
                <span><gsmsg:write key="enq.14" /></span>
              </logic:equal>
              <logic:notEqual name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
                <span>
                  <gsmsg:write key="enq.21" />
                <logic:notEqual name="enqData" property="publicKbn" value="<%= String.valueOf(Enq010Const.PUBLIC_YES) %>">
                  <br><gsmsg:write key="cmn.expiration" />
                </logic:notEqual>
                </span>
              </logic:notEqual>
            </td>
            <td class="txt_c txt_m no_w">
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
              <span class="text_base2<% if (sdFlg) { %> text_deluser_enq<% } else if (enqData.getSenderUkoFlg() == 1) { %> mukoUser<% } %>">
              <bean:write name="enqData" property="sender" />
              </span>
            </td>
            <td class="txt_l">
              <logic:notEmpty name="enqData" property="typeName">
                <span class="baseLabel"><bean:write name="enqData" property="typeName" /></span>
              </logic:notEmpty>
              <span>
              <logic:equal name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
                <a href="#" onClick="ansEnquete(<bean:write name="enqData" property="enqSid" />);"><span class="cl_linkVisit cl_linkHoverChange"><bean:write name="enqData" property="title" /></span></a>
              </logic:equal>
              <logic:notEqual name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
                <logic:equal name="enqData" property="canAnsFlg" value="<%= String.valueOf(Enq010Const.PUBLIC_ANSFLG_NG) %>">
                  <bean:write name="enqData" property="title" />
                </logic:equal>
                <logic:notEqual name="enqData" property="canAnsFlg" value="<%= String.valueOf(Enq010Const.PUBLIC_ANSFLG_NG) %>">
                  <a href="#" onClick="ansEnquete(<bean:write name="enqData" property="enqSid" />);"><span class="cl_linkDef fw_bold cl_linkHoverChange"><bean:write name="enqData" property="title" /></span></a>
                </logic:notEqual>
              </logic:notEqual>
              <logic:equal name="enqData" property="annoy" value="<%= String.valueOf(GSConstEnquete.ANONYMUS_ON) %>">
              <br><gsmsg:write key="cmn.asterisk" /><gsmsg:write key="enq.06" />
              </logic:equal>
              </span>
            </td>
            <td class="txt_c no_w">
              <span>
                <bean:write name="enqData" property="ansLimitDate" />
              </span>
            </td>
            <td class="txt_c no_w">
              <span>
              <logic:equal name="enqData" property="ansOpen" value="<%= String.valueOf(GSConstEnquete.KOUKAI_ON) %>">
              <bean:write name="enqData" property="ansPubStartDate" />
              <logic:empty name="enqData" property="pubEndDateStr">
              &nbsp;～<br>
              <gsmsg:write key="main.man200.9" />
              </logic:empty>
              <logic:notEmpty name="enqData" property="pubEndDateStr">
              &nbsp;～<br>
              <bean:write name="enqData" property="pubEndDateStr" />
              </logic:notEmpty>
              </logic:equal>
              <logic:notEqual name="enqData" property="ansOpen" value="<%= String.valueOf(GSConstEnquete.KOUKAI_ON) %>">
              <gsmsg:write key="cmn.private" />
              </logic:notEqual>
                <logic:equal name="enqData" property="enqPublic" value="<%= String.valueOf(Enq010Const.PUBLIC_YES) %>">
                  <logic:equal name="enqData" property="ansOpen" value="<%= String.valueOf(GSConstEnquete.KOUKAI_ON) %>">
                   <logic:equal name="enqData" property="disClosureFlg" value="<%= String.valueOf(Enq010Const.RESULT_DISCLOSURE) %>">
                      <logic:equal name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
                      <logic:equal name="enqData" property="ansPubDateFlg" value="true">
                      <br><a href="#" onclick="enqueteResult(<bean:write name="enqData" property="enqSid" />);"><span class="cl_linkVisit cl_linkHoverChange"><gsmsg:write key="enq.logmsg.enq310" /></span></a>
                      </logic:equal>
                      </logic:equal>
                    </logic:equal>
                    <logic:equal name="enqData" property="disClosureFlg" value="<%= String.valueOf(Enq010Const.RESULT_DISCLOSURE) %>">
                    <logic:notEqual name="enqData" property="status" value="<%= String.valueOf(GSConstEnquete.EAM_STS_KBN_YES) %>">
                    <logic:equal name="enqData" property="ansPubDateFlg" value="true">
                    <br><a href="#" onclick="enqueteResult(<bean:write name="enqData" property="enqSid" />);"><span class="cl_linkHoverChange"><gsmsg:write key="enq.logmsg.enq310" /></span></a>
                    </logic:equal>
                    </logic:notEqual>
                    </logic:equal>
                  </logic:equal>
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:iterate>
        </logic:notEmpty>
      </tbody>
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