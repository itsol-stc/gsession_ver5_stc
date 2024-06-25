<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../circular/js/cir120.js?<%= GSConst.VERSION_PARAM %>"></script>

<html:form action="/circular/cir120">
<html:hidden property="cir120AccountName" />
<html:hidden property="cir120AccountSid" />
<input type="hidden" name="plgId" value="cir">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_cirErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setCirMessage('circular', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio name="cir120Form" property="cir120SelKbn" styleClass="js_accountSelKbn" styleId="cir120SelKbn_0"  value="0"/><label for="cir120SelKbn_0"><gsmsg:write key="wml.wml010.12" /></label>
          <html:radio name="cir120Form" property="cir120SelKbn" styleClass="js_accountSelKbn ml10" styleId="cir120SelKbn_1"  value="1"/><label for="cir120SelKbn_1"><gsmsg:write key="cmn.all" /></label>
        </div>
      <div id="cirAccountSelArea" class="account_name_area"><span id="selCirAccountNameArea"><bean:write name="cir120Form" property="cir120AccountName" /></span>
        <button id="cirAccountSelBtn" name="btn_add" class="baseBtn ml20" type="button" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
          <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
        </button>
      </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.25" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir120Form" property="cir120JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir120JdelKbn_0" onclick="cirDispState(this.form.cir120JdelKbn, this.form.cir120JYear, this.form.cir120JMonth)" /><label for="cir120JdelKbn_0" ><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="cir120Form" styleClass="ml10" property="cir120JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir120JdelKbn_1" onclick="cirDispState(this.form.cir120JdelKbn, this.form.cir120JYear, this.form.cir120JMonth)" /><label for="cir120JdelKbn_1"><gsmsg:write key="wml.60" /></label>

          <gsmsg:write key="cmn.after.data.head" />

          <logic:notEmpty name="cir120Form" property="cir120YearLabelList">
            <html:select property="cir120JYear" styleClass="cir120JYear ml20">
              <html:optionsCollection name="cir120Form" property="cir120YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="cir120Form" property="cir120MonthLabelList">
            <html:select property="cir120JMonth" styleClass="cir120JMonth ml5 mr5">
              <html:optionsCollection name="cir120Form" property="cir120MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.26" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir120Form" property="cir120SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir120SdelKbn_0" onclick="cirDispState(this.form.cir120SdelKbn, this.form.cir120SYear, this.form.cir120SMonth)" /><label for="cir120SdelKbn_0" ><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="cir120Form" styleClass="ml10" property="cir120SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir120SdelKbn_1" onclick="cirDispState(this.form.cir120SdelKbn, this.form.cir120SYear, this.form.cir120SMonth)" /><label for="cir120SdelKbn_1"><gsmsg:write key="wml.60" /></label>

          <gsmsg:write key="cmn.after.data.head" />

          <logic:notEmpty name="cir120Form" property="cir120YearLabelList">
            <html:select property="cir120SYear" styleClass="cir120SYear ml20">
              <html:optionsCollection name="cir120Form" property="cir120YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="cir120Form" property="cir120MonthLabelList">
           <html:select property="cir120SMonth" styleClass="cir120SMonth ml5 mr5">
              <html:optionsCollection name="cir120Form" property="cir120MonthLabelList" value="value" label="label" />
            </html:select>
             <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="cmn.manual.delete2" /> <gsmsg:write key="cir.27" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="cir120Form" property="cir120DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO) %>" styleId="cir120DdelKbn_0" onclick="cirDispState(this.form.cir120DdelKbn, this.form.cir120DYear, this.form.cir120DMonth)" /><label for="cir120DdelKbn_0" ><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="cir120Form" styleClass="ml10" property="cir120DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT) %>" styleId="cir120DdelKbn_1" onclick="cirDispState(this.form.cir120DdelKbn, this.form.cir120DYear, this.form.cir120DMonth)" /><label for="cir120DdelKbn_1"><gsmsg:write key="wml.60" /></label>

          <gsmsg:write key="cmn.after.data.head" />

          <logic:notEmpty name="cir120Form" property="cir120YearLabelList">
            <html:select property="cir120DYear" styleClass="cir120DYear ml20">
            <html:optionsCollection name="cir120Form" property="cir120YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>

          <logic:notEmpty name="cir120Form" property="cir120MonthLabelList">
            <html:select property="cir120DMonth" styleClass="cir120DMonth ml5 mr5">
              <html:optionsCollection name="cir120Form" property="cir120MonthLabelList" value="value" label="label" />
            </html:select>
          <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
      </td>
    </tr>
  </table>
</div>

<div class="js_cirAccountSelPop display_n" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
  <input type="hidden" id="selAccountElm" value="cir120AccountSid" />
  <input type="hidden" id="selAccountSubmit" value="true" />
  <div class="hp400 w100">
  <table class="w100 h100">
    <tr>
      <td id="cirAccountListArea" class="txt_t"></td>
    </tr>
  </table>
  </div>
</div>
</html:form>