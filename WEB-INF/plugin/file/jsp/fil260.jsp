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

<script src="../file/js/fil260.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/file/fil260" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="fil">
<div class="wrapper w100">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="shortmail.notification" />
      </th>
      <td class="w80">
        <span class="verAlignMid">
          <html:radio name="fil260Form" styleId="js_filSmailSendKbn_01" property="fil260smlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.fil.GSConstFile.FAC_SMAIL_SEND_KBN_ADMIN) %>" /><label for="js_filSmailSendKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
          <html:radio styleClass="ml10" name="fil260Form" styleId="js_filSmailSendKbn_02" property="fil260smlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.fil.GSConstFile.FAC_SMAIL_SEND_KBN_USER) %>" /><label for="js_filSmailSendKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
        </span>
      </td>
    </tr>
    <tr id="js_filSmlNoticeKbnArea">
      <th class="w20">
        <gsmsg:write key="fil.fil260.1" />
      </th>
      <td class="w80">
        <div class="fs_13">
	      <gsmsg:write key="fil.fil260.2" />
	    </div>
        <span class="verAlignMid mt5">
          <html:radio name="fil260Form" styleId="filSmailSend_01" property="fil260smlSend" value="<%= String.valueOf(jp.groupsession.v2.fil.GSConstFile.FAC_SMAIL_SEND_NO) %>" /><label for="filSmailSend_01"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio styleClass="ml10" name="fil260Form" styleId="filSmailSend_02" property="fil260smlSend" value="<%= String.valueOf(jp.groupsession.v2.fil.GSConstFile.FAC_SMAIL_SEND_YES) %>" /><label for="filSmailSend_02"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
  </table>
</div>
</html:form>