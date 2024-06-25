<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../nippou/js/ntp083.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/nippou/ntp083">
<ntp:conf_hidden name="ntp083Form"/>

<input type="hidden" name="plgId" value="ntp">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_ntpErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setNtpMessage('nippou', this);">
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
        <div><gsmsg:write key="ntp.87" /></div>
        <div class="mt5 verAlignMid">
        <!-- ”N -->
        <logic:notEmpty name="ntp083Form" property="ntp083DelYearLabel">
        <html:select property="ntp083DelYear" styleClass="ntp083DelYear">
        <html:optionsCollection name="ntp083Form" property="ntp083DelYearLabel" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
        <!-- ŒŽ -->
        <logic:notEmpty name="ntp083Form" property="ntp083DelMonthLabel">
        <html:select property="ntp083DelMonth" styleClass="ntp083DelMonth ml5 mr5">
        <html:optionsCollection name="ntp083Form" property="ntp083DelMonthLabel" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
        <gsmsg:write key="cmn.after.data" />
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>