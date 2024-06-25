<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:form action="/schedule/sch095" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="sch">
<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch095.8" />
      </th>
      <td class="w80">
        <div class="fs_13">
          <gsmsg:write key="schedule.sch095.3" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio name="sch095Form" styleId="sch095Smail_02" property="sch095Smail" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_NOT_USE) %>" />
          <label for="sch095Smail_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="sch095Form" styleId="sch095Smail_01" styleClass="ml10" property="sch095Smail" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_USE) %>" />
          <label for="sch095Smail_01"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch095.9" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="schedule.sch095.2" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio name="sch095Form" styleId="sch095SmailGroup_02" property="sch095SmailGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_NOT_USE) %>" />
          <label for="sch095SmailGroup_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="sch095Form" styleId="sch095SmailGroup_01" styleClass="ml10" property="sch095SmailGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_USE) %>" />
          <label for="sch095SmailGroup_01"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="schedule.sch095.7" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="schedule.sch095.4" />
        </div>
        <span class="verAlignMid mt5">
          <html:radio name="sch095Form" styleId="sch095SmailAttend_02" property="sch095SmailAttend" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_NOT_USE) %>" />
          <label for="sch095SmailAttend_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="sch095Form" styleId="sch095SmailAttend_01" styleClass="ml10" property="sch095SmailAttend" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_USE) %>" />
          <label for="sch095SmailAttend_01"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
  </table>
</div>
</html:form>