<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<jsp:include page="/WEB-INF/plugin/smail/jsp/sml010_message.jsp" />
<script src="../smail/js/sml160.js?<%= GSConst.VERSION_PARAM %>"></script>

<html:form action="/smail/sml160">
<html:hidden property="sml160AccountSid" />
<html:hidden property="sml160AccountName" />

<logic:notEmpty name="sml160Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml160Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<input type="hidden" name="plgId" value="sml">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_smlErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setSmlMessage('smail', this);">
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
          <html:radio name="sml160Form" property="sml160SelKbn" styleClass="accountSelKbn" styleId="sml160SelKbn_0" value="0"/>
          <label for="sml160SelKbn_0" class="mr10"><gsmsg:write key="wml.wml010.12" /></label>
            <html:radio name="sml160Form" property="sml160SelKbn" styleClass="accountSelKbn" styleId="sml160SelKbn_1" value="1"/>
            <label for="sml160SelKbn_1"><gsmsg:write key="cmn.all" /></label>
        </div>
        <div id="smlAccountSelArea">
          <logic:notEmpty name="sml160Form" property="sml160AccountName">
            <span id="selSmlAccountNameArea"><bean:write name="sml160Form" property="sml160AccountName" /></span>
          </logic:notEmpty>
          <button type="button" id="smlAccountSelBtn" class="baseBtn ml20" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" >
            <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
          </button>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.57" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml160Form" property="sml160JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml160JdelKbn_0" onclick="smlDispState(this.form.sml160JdelKbn, this.form.sml160JYear, this.form.sml160JMonth)" />
          <label for="sml160JdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
            <html:radio name="sml160Form" property="sml160JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml160JdelKbn_1" onclick="smlDispState(this.form.sml160JdelKbn, this.form.sml160JYear, this.form.sml160JMonth)" />
          <label for="sml160JdelKbn_1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="sml160Form" property="sml160YearLabelList">
            <html:select property="sml160JYear" styleClass="sml160JYear ml20">
              <html:optionsCollection name="sml160Form" property="sml160YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml160Form" property="sml160MonthLabelList">
            <html:select property="sml160JMonth" styleClass="sml160JMonth ml5 mr5">
              <html:optionsCollection name="sml160Form" property="sml160MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.59" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml160Form" property="sml160SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml160SdelKbn_0" onclick="smlDispState(this.form.sml160SdelKbn, this.form.sml160SYear, this.form.sml160SMonth)" />
          <label for="sml160SdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="sml160Form" property="sml160SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml160SdelKbn_1" onclick="smlDispState(this.form.sml160SdelKbn, this.form.sml160SYear, this.form.sml160SMonth)" />
          <label for="sml160SdelKbn_1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="sml160Form" property="sml160YearLabelList">
            <html:select property="sml160SYear" styleClass="sml160SYear ml20">
              <html:optionsCollection name="sml160Form" property="sml160YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml160Form" property="sml160MonthLabelList">
            <html:select property="sml160SMonth" styleClass="sml160SMonth ml5 mr5">
              <html:optionsCollection name="sml160Form" property="sml160MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.58" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml160Form" property="sml160WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml160WdelKbn_0" onclick="smlDispState(this.form.sml160WdelKbn, this.form.sml160WYear, this.form.sml160WMonth)" />
          <label for="sml160WdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="sml160Form" property="sml160WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml160WdelKbn_1" onclick="smlDispState(this.form.sml160WdelKbn, this.form.sml160WYear, this.form.sml160WMonth)" />
          <label for="sml160WdelKbn_1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="sml160Form" property="sml160YearLabelList">
            <html:select property="sml160WYear" styleClass="sml160WYear ml20">
              <html:optionsCollection name="sml160Form" property="sml160YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml160Form" property="sml160MonthLabelList">
            <html:select property="sml160WMonth" styleClass="sml160WMonth ml5 mr5">
              <html:optionsCollection name="sml160Form" property="sml160MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
    <tr>
      <th>
        <gsmsg:write key="sml.56" />
      </th>
      <td>
        <div class="verAlignMid">
          <html:radio name="sml160Form" property="sml160DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml160DdelKbn_0" onclick="smlDispState(this.form.sml160DdelKbn, this.form.sml160DYear, this.form.sml160DMonth)" />
          <label for="sml160DdelKbn_0" class="mr10"><gsmsg:write key="cmn.dont.delete" /></label>
          <html:radio name="sml160Form" property="sml160DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml160DdelKbn_1" onclick="smlDispState(this.form.sml160DdelKbn, this.form.sml160DYear, this.form.sml160DMonth)" />
            <label for="sml160DdelKbn_1"><gsmsg:write key="wml.60" /></label>
          <logic:notEmpty name="sml160Form" property="sml160YearLabelList">
            <html:select property="sml160DYear" styleClass="sml160DYear ml20">
              <html:optionsCollection name="sml160Form" property="sml160YearLabelList" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <logic:notEmpty name="sml160Form" property="sml160MonthLabelList">
            <html:select property="sml160DMonth" styleClass="sml160DMonth ml5 mr5">
              <html:optionsCollection name="sml160Form" property="sml160MonthLabelList" value="value" label="label" />
            </html:select>
            <gsmsg:write key="cmn.after.data" />
          </logic:notEmpty>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>
<div class="display_none">
  <div class="js_smlAccountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
    <input type="hidden" id="selAccountElm" value="sml160AccountSid" />
    <input type="hidden" id="selAccountSubmit" value="true" />
    <div class="hp400 w100">
      <table class="w100 h100">
        <tr>
          <td id="smlAccountListArea"  class="txt_t"></td>
        </tr>
      </table>
    </div>
  </div>
</div>