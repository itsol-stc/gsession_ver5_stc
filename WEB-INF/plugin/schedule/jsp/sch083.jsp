<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../schedule/js/sch083.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/schedule/sch083">

<logic:notEmpty name="sch083Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch083Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch083Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch083Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch083Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch083Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch083Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch083Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch083Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch083Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="plgId" value="sch">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_schErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setSchMessage('schedule', this);">
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
        <div>
          <gsmsg:write key="schedule.sch083.2" />
        </div>
        <div class="mt5 verAlignMid">
          <gsmsg:write key="cmn.after.data.head" />
          <!-- 年 -->
          <logic:notEmpty name="sch083Form" property="sch083DelYearLabel">
          <html:select property="sch083DelYear" styleClass="sch083DelYear">
            <html:optionsCollection name="sch083Form" property="sch083DelYearLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <!-- 月 -->
          <logic:notEmpty name="sch083Form" property="sch083DelMonthLabel">
          <html:select property="sch083DelMonth" styleClass="sch083DelMonth ml5 mr5">
            <html:optionsCollection name="sch083Form" property="sch083DelMonthLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.after.data" />
      </td>
    </tr>
  </table>
</div>
</html:form>