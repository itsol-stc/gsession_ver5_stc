<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-dailyScheduleRow.tld" prefix="dailyScheduleRow" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../schedule/js/sch088.js?<%=GSConst.VERSION_PARAM%>"></script>
<html:form action="/schedule/sch088" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="sch">
<div class="wrapper w100">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="shortmail.notification" />
      </th>
      <td class="w80">
        <span class="verAlignMid">
          <html:radio name="sch088Form" styleId="js_schSmailSendKbn_01" property="sch088smlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_SEND_KBN_ADMIN) %>" />
          <label for="js_schSmailSendKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio name="sch088Form" styleId="js_schSmailSendKbn_02" property="sch088smlSendKbn" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_SEND_KBN_USER) %>" />
          <label for="js_schSmailSendKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
      </td>
    </tr>
    
    <tr class="schSmlNoticeKbnArea">
      <th class="w20">
        <gsmsg:write key="schedule.sch088.4" />
      </th>
      <td class="w80">
        <div class="fs_13">
          <gsmsg:write key="schedule.sch095.3" />
        </div>
        <div class="mt5 verAlignMid">
          <html:radio name="sch088Form" styleId="sch088Smail_02" property="sch088Smail" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_NOT_USE) %>" />
          <label for="sch088Smail_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="sch088Form" styleId="sch088Smail_01" property="sch088Smail" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_USE) %>" />
          <label for="sch088Smail_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
    
    <tr class="schSmlNoticeKbnArea">
      <th class="w20">
        <gsmsg:write key="schedule.sch088.5" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="schedule.sch095.2" />
        </div>
        <div class="mt5 verAlignMid">
          <html:radio name="sch088Form" styleId="sch088SmailGroup_02" property="sch088SmailGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_NOT_USE) %>" />
          <label for="sch088SmailGroup_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="sch088Form" styleId="sch088SmailGroup_01" styleClass="ml10" property="sch088SmailGroup" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_USE) %>" />
          <label for="sch088SmailGroup_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
    
    <tr class="schSmlNoticeKbnArea">
      <th class="w20">
        <gsmsg:write key="schedule.sch088.3" />
      </th>
      <td>
        <div class="fs_13">
          <gsmsg:write key="schedule.sch095.4" />
        </div>
        <div class="mt5 verAlignMid">
          <html:radio name="sch088Form" styleId="sch088SmailAttend_02" property="sch088SmailAttend" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_NOT_USE) %>" />
          <label for="sch088SmailAttend_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="sch088Form" styleId="sch088SmailAttend_01" property="sch088SmailAttend" styleClass="ml10" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SMAIL_USE) %>" />
          <label for="sch088SmailAttend_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>