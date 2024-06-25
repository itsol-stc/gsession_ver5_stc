<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<body>
<html:form action="/reserve/rsv300" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="rsv">
<div class="wrapper w100 mrl_auto">
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="reserve.rsv300.2" />
      </th>
      <td class="w80">
        <div class="fs_13">
          <gsmsg:write key="reserve.rsv300.1" />
        </div>
        <div class="verAlignMid mt5">
          <html:radio name="rsv300Form" property="rsv300smailKbn" value="0" styleId="smailKbn0">
          <label for="smailKbn0" class=""><gsmsg:write key="cmn.dont.notify" /></label></html:radio>
          <html:radio name="rsv300Form" styleClass="ml10" property="rsv300smailKbn" value="1" styleId="smailKbn1">
          <label for="smailKbn1"><gsmsg:write key="cmn.notify" /></label></html:radio>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>