<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
int batchOff = jp.groupsession.v2.cmn.GSConstReserve.RSV_RADIO_OFF;
%>
<%
int batchOn = jp.groupsession.v2.cmn.GSConstReserve.RSV_RADIO_ON;
%>

<script type="text/javascript" src="../reserve/js/rsv120.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/reserve/rsv120" styleClass="js_delForm">
<html:hidden property="rsv120initDspFlg" />

<logic:notEmpty name="rsv120Form" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv120Form" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv120Form" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv120Form" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="plgId" value="rsv">

<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
      </th>
      <td class="w80">
        <div><gsmsg:write key="reserve.rsv120.1" /></div>
        <div class="mt5">
          <div class="verAlignMid">
            <html:radio name="rsv120Form" property="rsv120batchKbn" value="<%= String.valueOf(batchOff) %>" styleId="rsv120batchKbn0" onclick="rsvChangeDisable()" />
            <label for="rsv120batchKbn0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="rsv120Form" styleClass="ml10" property="rsv120batchKbn" value="<%= String.valueOf(batchOn) %>" styleId="rsv120batchKbn1" onclick="rsvChangeEnable()" />
            <label for="rsv120batchKbn1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="rsv120Form" property="rsv120yearLabelList" scope="request">
              <html:select property="rsv120year" styleClass="rsv120year">
                <html:optionsCollection name="rsv120Form" property="rsv120yearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="rsv120Form" property="rsv120monthLabelList" scope="request">
              <html:select property="rsv120month" styleClass="rsv120month ml5 mr5">
                <html:optionsCollection name="rsv120Form" property="rsv120monthLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>