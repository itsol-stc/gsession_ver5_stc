<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<%
   String smailSendOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.SMAIL_SEND_OFF);
   String smailSendOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.SMAIL_SEND_ON);
%>

<html:form action="/file/fil130" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="fil">
<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="fil.fil130.2" />
      </th>
      <td class="w80">
        <div class="fs_13">
          <gsmsg:write key="fil.fil130.1" />
        </div>
        <div class="verAlignMid mt5">
          <html:radio name="fil130Form" styleId="filSmailSend_01" property="fil130SmailSend" value="<%= smailSendOff %>" /><label for="filSmailSend_01"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="fil130Form" styleId="filSmailSend_02" property="fil130SmailSend" value="<%= smailSendOn %>" styleClass="ml10" /><label for="filSmailSend_02"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>