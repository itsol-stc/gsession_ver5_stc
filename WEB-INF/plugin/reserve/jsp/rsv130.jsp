<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script type="text/javascript" src="../reserve/js/rsv130.js?<%= GSConst.VERSION_PARAM %>"></script>

<html:form action="/reserve/rsv130">

<logic:notEmpty name="rsv130Form" property="rsv100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="rsv130Form" property="rsv100CsvOutField" scope="request">
    <input type="hidden" name="rsv100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="rsv130Form" property="rsvIkkatuTorokuKey" scope="request">
  <logic:iterate id="key" name="rsv130Form" property="rsvIkkatuTorokuKey" scope="request">
    <input type="hidden" name="rsvIkkatuTorokuKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="plgId" value="rsv">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_rsvErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setRsvMessage('reserve', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.manual.delete2" />
      </th>
      <td class="w80">
        <div class="fs_13"><gsmsg:write key="reserve.rsv130.2" /></div>
        <div class="mt5 verAlignMid">
          <logic:notEmpty name="rsv130Form" property="rsv130yearLabelList">
            <html:select property="rsv130year" styleClass="rsv130year">
              <html:optionsCollection name="rsv130Form" property="rsv130yearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="rsv130Form" property="rsv130monthLabelList">
            <html:select property="rsv130month" styleClass="rsv130month ml5 mr5">
              <html:optionsCollection name="rsv130Form" property="rsv130monthLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>