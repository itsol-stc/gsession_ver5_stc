<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>

<script src='../reserve/js/rsv320.js?<%= GSConst.VERSION_PARAM %>'></script>
<html:form action="/reserve/rsv320" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="rsv">
<div class="wrapper w100">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="shortmail.notification" />
      </th>
      <td class="w80">
	    <span class="verAlignMid">
	      <html:radio name="rsv320Form" styleId="js_rsvSmailSendKbn_01" property="rsv320SmailSendKbn" value="<%= String.valueOf(GSConstReserve.RAC_SMAIL_SEND_KBN_ADMIN) %>"/>
	      <label for="js_rsvSmailSendKbn_01"><gsmsg:write key="cmn.set.the.admin" /></label>
	      <html:radio name="rsv320Form" styleId="js_rsvSmailSendKbn_02" property="rsv320SmailSendKbn" styleClass="ml10" value="<%= String.valueOf(GSConstReserve.RAC_SMAIL_SEND_KBN_USER) %>"/>
	      <label for="js_rsvSmailSendKbn_02"><gsmsg:write key="cmn.set.eachuser" /></label>
	    </span>
      </td>
    </tr>
    
    <tr id="js_rsvSmlNoticeKbnArea">
      <th class="w20">
        <gsmsg:write key="reserve.rsv320.1" />
      </th>
      <td class="w80">
        <div class="fs_13">
	      <gsmsg:write key="reserve.rsv320.2" />
	    </div>
        <span class="verAlignMid mt5">
          <html:radio name="rsv320Form" styleId="rsvSmailSend_01" property="rsv320SmailSend" value="<%= String.valueOf(GSConstReserve.RAC_SMAIL_SEND_NO) %>" />
          <label for="rsvSmailSend_01"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="rsv320Form" styleClass="ml10" styleId="rsvSmailSend_02" property="rsv320SmailSend" value="<%= String.valueOf(GSConstReserve.RAC_SMAIL_SEND_YES) %>" />
          <label for="rsvSmailSend_02"><gsmsg:write key="cmn.notify" /></label>
        </span>
      </td>
    </tr>
  </table>
</div>
</html:form>