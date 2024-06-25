<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../schedule/js/sch082.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/schedule/sch082" styleClass="js_delForm">
<logic:notEmpty name="sch082Form" property="sch100SvSearchTarget" scope="request">
  <logic:iterate id="svTarget" name="sch082Form" property="sch100SvSearchTarget" scope="request">
    <input type="hidden" name="sch100SvSearchTarget" value="<bean:write name="svTarget"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch082Form" property="sch100SearchTarget" scope="request">
  <logic:iterate id="target" name="sch082Form" property="sch100SearchTarget" scope="request">
    <input type="hidden" name="sch100SearchTarget" value="<bean:write name="target"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch082Form" property="sch100SvBgcolor" scope="request">
  <logic:iterate id="svBgcolor" name="sch082Form" property="sch100SvBgcolor" scope="request">
    <input type="hidden" name="sch100SvBgcolor" value="<bean:write name="svBgcolor"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="sch082Form" property="sch100Bgcolor" scope="request">
  <logic:iterate id="bgcolor" name="sch082Form" property="sch100Bgcolor" scope="request">
    <input type="hidden" name="sch100Bgcolor" value="<bean:write name="bgcolor"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="sch082Form" property="sch100CsvOutField" scope="request">
  <logic:iterate id="csvOutField" name="sch082Form" property="sch100CsvOutField" scope="request">
    <input type="hidden" name="sch100CsvOutField" value="<bean:write name="csvOutField"/>">
  </logic:iterate>
</logic:notEmpty>
<input type="hidden" name="plgId" value="sch">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_schErrorArea"></div>
  <table class="table-left">
    <tr>
      <th class="w20">
      <gsmsg:write key="cmn.autodelete" />
      </th>
      <td class="w80">
        <div><gsmsg:write key="schedule.sch082.1" /></div>
        <div class="mt5">
          <div class="verAlignMid">
            <html:radio name="sch082Form" property="sch082AtdelFlg" styleId="sch082AtdelFlg0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.AUTO_DELETE_OFF) %>" onclick="schChangeDisable();" />
            <label for="sch082AtdelFlg0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="sch082Form" property="sch082AtdelFlg" styleId="sch082AtdelFlg1" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.AUTO_DELETE_ON) %>" onclick="schChangeEnable();" />
            <label for="sch082AtdelFlg1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <!-- 年 -->
            <logic:notEmpty name="sch082Form" property="sch082AtdelYearLabel" scope="request">
              <html:select property="sch082AtdelYear" styleClass="sch082AtdelYear">
                <html:optionsCollection name="sch082Form" property="sch082AtdelYearLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <!-- 月 -->
            <logic:notEmpty name="sch082Form" property="sch082AtdelMonthLabel" scope="request">
              <html:select property="sch082AtdelMonth" styleClass="sch082AtdelMonth ml5 mr5">
                <html:optionsCollection name="sch082Form" property="sch082AtdelMonthLabel" value="value" label="label" />
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