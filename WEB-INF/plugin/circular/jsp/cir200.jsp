<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../circular/js/cir200.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/circular/cir200" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="cir">
<div class="wrapper w100">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="shortmail.notification" />
      </th>
      <td class="w80">
      <div class="verAlignMid" >
        <html:radio name="cir200Form" styleId="js_cirSmailSendKbn_01" property="cir200SmlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_ADMIN) %>" /><label for="js_cirSmailSendKbn_01""><gsmsg:write key="cmn.set.the.admin" /></label>
        <html:radio name="cir200Form" styleClass="ml10" styleId="js_cirSmailSendKbn_02" property="cir200SmlSendKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_USER) %>" /><label for="js_cirSmailSendKbn_02"><gsmsg:write key="cmn.set.eachaccount" /></label>
      </div>
      </td>
    </tr>
    <% String tuuchi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_KBN_YES); %>
    <% String notuuchi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_KBN_NO); %>
    <tr name="js_cirSmlNoticeKbnArea">
      <th>
        <gsmsg:write key="cir.cir160.2" />
      </th>
      <td>
        <div class="fs_13"><gsmsg:write key="cir.cir160.3" /></div>
        <div class="verAlignMid mt5" >
          <html:radio name="cir200Form" styleId="cir200smlNtf_02" property="cir200SmlSend" value="<%= notuuchi %>" /><label for="cir200smlNtf_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="cir200Form" styleId="cir200smlNtf_01" property="cir200SmlSend" styleClass="ml10" value="<%= tuuchi %>" /><label for="cir200smlNtf_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
    <tr name="js_cirSmlNoticeKbnArea">
      <th>
      <gsmsg:write key="cir.cir160.4" />
      </th>
      <td>
        <div class="fs_13"><gsmsg:write key="cir.cir160.5" /></div>
        <div class="verAlignMid mt5" >
          <html:radio name="cir200Form" styleId="cir200smlMemo_02" property="cir200SmlMemo" value="<%= notuuchi %>" /><label for="cir200smlMemo_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="cir200Form" styleId="cir200smlMemo_01" property="cir200SmlMemo" styleClass="ml10" value="<%= tuuchi %>" /><label for="cir200smlMemo_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
    <tr name="js_cirSmlNoticeKbnArea">
      <th>
        <gsmsg:write key="cir.cir160.6" />
      </th>
      <td>
        <div class="fs_13"><gsmsg:write key="cir.cir160.7" /></div>
        <div class="verAlignMid mt5" >
          <html:radio name="cir200Form" styleId="cir200smlEdt_02" property="cir200SmlEdt" value="<%= notuuchi %>" /><label for="cir200smlEdt_02"><gsmsg:write key="cmn.dont.notify" /></label>
          <html:radio name="cir200Form" styleId="cir200smlEdt_01" property="cir200SmlEdt" styleClass="ml10"  value="<%= tuuchi %>" /><label for="cir200smlEdt_01"><gsmsg:write key="cmn.notify" /></label>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>