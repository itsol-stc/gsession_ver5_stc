<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../nippou/js/ntp082.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/nippou/ntp082" styleClass="js_delForm">
<ntp:conf_hidden name="ntp082Form"/>
<input type="hidden" name="plgId" value="ntp">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_ntpErrorArea"></div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
      </th>
      <td class="w80">
        <div><gsmsg:write key="ntp.86" /></div>
        <div class="mt5">
          <div class="verAlignMid">
            <html:radio name="ntp082Form" property="ntp082AtdelFlg" styleId="ntp082AtdelFlg0" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.AUTO_DELETE_OFF) %>" onclick="ntpChangeDisable();" />
            <label for="ntp082AtdelFlg0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="ntp082Form" property="ntp082AtdelFlg" styleId="ntp082AtdelFlg1" value="<%= String.valueOf(jp.groupsession.v2.ntp.GSConstNippou.AUTO_DELETE_ON) %>" onclick="ntpChangeEnable();" />
            <label for="ntp082AtdelFlg1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <!-- ”N -->
            <html:select property="ntp082AtdelYear" styleClass="ntp082AtdelYear">
              <html:optionsCollection name="ntp082Form" property="ntp082AtdelYearLabel" value="value" label="label" />
            </html:select>
            <!-- ŒŽ -->
            <html:select property="ntp082AtdelMonth" styleClass="ntp082AtdelMonth ml5 mr5">
              <html:optionsCollection name="ntp082Form" property="ntp082AtdelMonthLabel" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>
