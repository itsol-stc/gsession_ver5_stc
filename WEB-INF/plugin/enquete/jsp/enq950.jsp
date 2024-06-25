<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>

<script type="text/javascript" src="../enquete/js/enq950.js?<%= GSConst.VERSION_PARAM %>"></script>

<html:form action="/enquete/enq950">

<logic:notEmpty name="enq950Form" property="enq010priority">
<logic:iterate id="svPriority" name="enq950Form" property="enq010priority">
  <input type="hidden" name="enq010priority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq950Form" property="enq010status">
<logic:iterate id="svStatus" name="enq950Form" property="enq010status">
  <input type="hidden" name="enq010status" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq950Form" property="enq010svPriority">
<logic:iterate id="svPriority" name="enq950Form" property="enq010svPriority">
  <input type="hidden" name="enq010svPriority" value="<bean:write name="svPriority" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq950Form" property="enq010svStatus">
<logic:iterate id="svStatus" name="enq950Form" property="enq010svStatus">
  <input type="hidden" name="enq010svStatus" value="<bean:write name="svStatus" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq950Form" property="enq010statusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq950Form" property="enq010statusAnsOver">
  <input type="hidden" name="enq010statusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="enq950Form" property="enq010svStatusAnsOver">
<logic:iterate id="svStatusAnsOver" name="enq950Form" property="enq010svStatusAnsOver">
  <input type="hidden" name="enq010svStatusAnsOver" value="<bean:write name="svStatusAnsOver" />">
</logic:iterate>
</logic:notEmpty>

<input type="hidden" name="plgId" value="enq">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_enqErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setEnqMessage('enquete', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left">
  <!-- 手動削除 発信 -->
    <tr>
      <th class="w20">
        <gsmsg:write key="enq.enq950.01"/>
      </th>
      <td class="w80">
        <gsmsg:write key="enq.enq950.03"/><br>
        <div class="mt5 verAlignMid">
          <html:radio styleId="sendDelKbn0" name="enq950Form" property="enq950SendDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_OFF) %>" onclick="sendChangeDisable();" />
          <label for="sendDelKbn0"><gsmsg:write key="cmn.dont.delete"/></label>
          <html:radio styleClass="ml10" styleId="sendDelKbn1" name="enq950Form" property="enq950SendDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_ON) %>" onclick="sendChangeEnable();" />
          <label for="sendDelKbn1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="enq950Form" property="enq950SelectSendYear" scope="request">
          <html:select property="enq950SelectSendYear" styleClass="enq950SelectSendYear ml20">
            <html:optionsCollection name="enq950Form" property="enq950YearLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="enq950Form" property="enq950SelectSendMonth" scope="request">
            <html:select property="enq950SelectSendMonth" styleClass="enq950SelectSendMonth ml5 mr5">
            <html:optionsCollection name="enq950Form" property="enq950MonthLabel" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data"/>
        </div>
      </td>
    </tr>
    <!-- 手動削除 草稿 -->
    <tr>
      <th>
        <gsmsg:write key="enq.enq950.02"/>
      </th>
      <td>
        <gsmsg:write key="enq.enq950.04"/><br>
        <div class="verAlignMid">
          <html:radio styleId="draftDelKbn0" name="enq950Form" property="enq950DraftDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_OFF) %>" onclick="draftChangeDisable();" />
          <label for="draftDelKbn0"><gsmsg:write key="cmn.dont.delete"/></label>
          <html:radio styleClass="ml10" styleId="draftDelKbn1" name="enq950Form" property="enq950DraftDelKbn" value="<%= String.valueOf(GSConstEnquete.DELETE_KBN_ON) %>" onclick="draftChangeEnable();" />
          <label for="draftDelKbn1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="enq950Form" property="enq950SelectDraftYear" scope="request">
          <html:select property="enq950SelectDraftYear" styleClass="enq950SelectDraftYear ml20">
            <html:optionsCollection name="enq950Form" property="enq950YearLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="enq950Form" property="enq950SelectDraftMonth" scope="request">
          <html:select property="enq950SelectDraftMonth" styleClass="enq950SelectDraftMonth ml5 mr5">
            <html:optionsCollection name="enq950Form" property="enq950MonthLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data"/>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>