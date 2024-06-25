<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>

<script type="text/javascript" src="../enquete/js/enq960.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/enquete/enq960" styleClass="js_delForm">
<input type="hidden" name="plgId" value="enq">

<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <!-- 自動削除 発信 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="enq.enq960.01"/>
      </th>
      <td class="w80">
        <div><gsmsg:write key="enq.enq950.03"/></div>
        <div class="mt5">
          <html:radio styleId="sendDelKbn0" name="enq960Form" property="enq960SendDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_OFF) %>" onclick="sendChangeDisable();" />
          <label for="sendDelKbn0"><gsmsg:write key="cmn.noset" /></label>
          <html:radio styleClass="ml10" styleId="sendDelKbn1" name="enq960Form" property="enq960SendDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_ON) %>" onclick="sendChangeEnable();" />
          <label for="sendDelKbn1"><gsmsg:write key="cmn.automatically.delete"/></label>
          <div class="ml20">
            <logic:notEmpty name="enq960Form" property="enq960YearLabel" scope="request">
              <html:select property="enq960SelectSendYear" styleClass="enq960SelectSendYear">
                <html:optionsCollection name="enq960Form" property="enq960YearLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="enq960Form" property="enq960MonthLabel" scope="request">
              <html:select property="enq960SelectSendMonth" styleClass="enq960SelectSendMonth ml5 mr5">
                <html:optionsCollection name="enq960Form" property="enq960MonthLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data"/>
          </div>
        </div>
      </td>
    </tr>
    <!-- 自動削除 草稿 -->
    <tr>
      <th>
        <gsmsg:write key="enq.enq960.02"/>
      </th>
      <td>
        <div><gsmsg:write key="enq.enq950.04"/></div>
        <div class="mt5">
          <html:radio styleId="draftDelKbn0" name="enq960Form" property="enq960DraftDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_OFF) %>" onclick="draftChangeDisable();" />
          <label for="draftDelKbn0"><gsmsg:write key="cmn.noset" /></label>
          <html:radio styleClass="ml10" styleId="draftDelKbn1" name="enq960Form" property="enq960DraftDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_ON) %>" onclick="draftChangeEnable();" />
          <label for="draftDelKbn1"><gsmsg:write key="cmn.automatically.delete"/></label>
          <div class="ml20">
            <logic:notEmpty name="enq960Form" property="enq960YearLabel" scope="request">
              <html:select property="enq960SelectDraftYear" styleClass="enq960SelectDraftYear">
                <html:optionsCollection name="enq960Form" property="enq960YearLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="enq960Form" property="enq960MonthLabel" scope="request">
              <html:select property="enq960SelectDraftMonth" styleClass="enq960SelectDraftMonth ml5 mr5">
                <html:optionsCollection name="enq960Form" property="enq960MonthLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data"/>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>