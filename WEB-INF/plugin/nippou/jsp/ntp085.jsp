<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib tagdir="/WEB-INF/tags/nippou" prefix="ntp"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.ntp.GSConstNippou" %>

<script src="../nippou/js/ntp085.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/nippou/ntp085" styleClass="js_smailSetting">
<input type="hidden" name="plgId" value="ntp">
<div class="wrapper w100">
  
  <table class="table-left">
    <tr>
      <th class="w20" id="schRepertArea1">
        <gsmsg:write key="ntp.88" />
      </th>
      <td class="w80">
        <span class="verAlignMid">
          <html:radio name="ntp085Form" styleId="js_ntp085NoticeKbn_00" property="ntp085NoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_ADM) %>"/><label for="js_ntp085NoticeKbn_00"><span class="text_base6_2"><gsmsg:write key="cmn.set.the.admin" /></span></label>
          <html:radio name="ntp085Form" styleClass="ml10" styleId="js_ntp085NoticeKbn_01" property="ntp085NoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_USR) %>"/><label for="js_ntp085NoticeKbn_01"><span class="text_base6_2"><gsmsg:write key="cmn.set.eachuser" /></span></label>
        </span>
        <div class="settingForm_separator" id="js_ntpSmlNoticeKbnArea">
          <span class="verAlignMid">
            <html:radio name="ntp085Form" styleId="js_ntp085SmlNoticeKbn_00" property="ntp085SmlNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_NO) %>" /><label for="js_ntp085SmlNoticeKbn_00"><span class="text_base"><gsmsg:write key="cmn.dont.notify" /></span></label>
            <html:radio name="ntp085Form" styleClass="ml10" styleId="js_ntp085SmlNoticeKbn_01" property="ntp085SmlNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_YES) %>" /><label for="js_ntp085SmlNoticeKbn_01"><span class="text_base"><gsmsg:write key="cmn.notify" /></span></label>
          </span>
        </div>
        <div class="settingForm_separator" id="js_ntpSmlNoticeKbnPlace">
          <span class="verAlignMid">
            <html:radio name="ntp085Form" styleId="ntp085SmlNoticePlace_00" property="ntp085SmlNoticePlace" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_GROUP) %>" /><label for="ntp085SmlNoticePlace_00"><span class="text_base"><gsmsg:write key="ntp.160" /></span></label>
            <html:radio name="ntp085Form" styleClass="ml10" styleId="ntp085SmlNoticePlace_01" property="ntp085SmlNoticePlace" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_GROUP_ADM) %>" /><label for="ntp085SmlNoticePlace_01"><span class="text_base"><gsmsg:write key="ntp.161" /></span></label>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="ntp.89" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="ntp085Form" styleId="js_ntp085CmtNoticeKbn_00" property="ntp085CmtNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_ADM) %>"/><label for="js_ntp085CmtNoticeKbn_00"><span class="text_base6_2"><gsmsg:write key="cmn.set.the.admin" /></span></label>
          <html:radio name="ntp085Form" styleClass="ml10" styleId="js_ntp085CmtNoticeKbn_01" property="ntp085CmtNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_USR) %>"/><label for="js_ntp085CmtNoticeKbn_01"><span class="text_base6_2"><gsmsg:write key="cmn.set.eachuser" /></span></label>
        </span>
        <div class="settingForm_separator" id="js_cmtSmlNoticeKbnArea">
          <span class="verAlignMid">
            <html:radio name="ntp085Form" styleId="ntp085CmtSmlNoticeKbn_00" property="ntp085CmtSmlNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_NO) %>" /><label for="ntp085CmtSmlNoticeKbn_00"><span class="text_base"><gsmsg:write key="cmn.dont.notify" /></span></label>
            <html:radio name="ntp085Form" styleClass="ml10" styleId="ntp085CmtSmlNoticeKbn_01" property="ntp085CmtSmlNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_YES) %>" /><label for="ntp085CmtSmlNoticeKbn_01"><span class="text_base"><gsmsg:write key="cmn.notify" /></span></label>
          </span>
        </div>
      </td>
    </tr>
      <tr>
      <th>
        <gsmsg:write key="ntp.9" />
      </th>
      <td>
        <span class="verAlignMid">
          <html:radio name="ntp085Form" styleId="js_ntp085GoodNoticeKbn_00" property="ntp085GoodNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_ADM) %>"/><label for="js_ntp085GoodNoticeKbn_00"><span class="text_base6_2"><gsmsg:write key="cmn.set.the.admin" /></span></label>
          <html:radio name="ntp085Form" styleClass="ml10" styleId="js_ntp085GoodNoticeKbn_01" property="ntp085GoodNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_USR) %>"/><label for="js_ntp085GoodNoticeKbn_01"><span class="text_base6_2"><gsmsg:write key="cmn.set.eachuser" /></span></label>
        </span>
        <div class="settingForm_separator" id="js_goodSmlNoticeKbnArea">
          <span class="verAlignMid">
            <html:radio name="ntp085Form" styleId="ntp085GoodSmlNoticeKbn_00" property="ntp085GoodSmlNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_NO) %>" /><label for="ntp085GoodSmlNoticeKbn_00"><span class="text_base"><gsmsg:write key="cmn.dont.notify" /></span></label>
            <html:radio name="ntp085Form" styleClass="ml10" styleId="ntp085GoodSmlNoticeKbn_01" property="ntp085GoodSmlNoticeKbn" value="<%= String.valueOf(GSConstNippou.SML_NOTICE_YES) %>" /><label for="ntp085GoodSmlNoticeKbn_01"><span class="text_base"><gsmsg:write key="cmn.notify" /></span></label>
          </span>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>