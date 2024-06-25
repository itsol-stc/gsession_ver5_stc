<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script src="../smail/js/sml150.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/smail/sml150" styleClass="js_delForm">
<input type="hidden" name="plgId" value="sml">

  <div class="wrapper w100 mrl_auto">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
    <div class="component_bothEnd">
      <div class="txt_l">※<gsmsg:write key="wml.wml020.02" /></div>
    </div>
    <table class="table-left">
      <tr>
        <th class="w20">
          <gsmsg:write key="cmn.autodelete" />
          <gsmsg:write key="cmn.receive" />
        </th>
        <td class="w80">
          <div class="verAlignMid">
            <html:radio name="sml150Form" property="sml150JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml150JdelKbn_0" onclick="smlDispState(this.form.sml150JdelKbn, this.form.sml150JYear, this.form.sml150JMonth)" />
            <label for="sml150JdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="sml150Form" property="sml150JdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml150JdelKbn_1" onclick="smlDispState(this.form.sml150JdelKbn, this.form.sml150JYear, this.form.sml150JMonth)" />
            <label for="sml150JdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="sml150Form" property="sml150YearLabelList">
              <html:select property="sml150JYear" styleClass="sml150JYear">
                <html:optionsCollection name="sml150Form" property="sml150YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="sml150Form" property="sml150MonthLabelList">
              <html:select property="sml150JMonth" styleClass="sml150JMonth ml5 mr5">
                <html:optionsCollection name="sml150Form" property="sml150MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w20">
          <gsmsg:write key="cmn.autodelete" />
          <gsmsg:write key="cmn.sent" />
        </th>
        <td class="w80">
          <div class="verAlignMid">
            <html:radio name="sml150Form" property="sml150SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml150SdelKbn_0" onclick="smlDispState(this.form.sml150SdelKbn, this.form.sml150SYear, this.form.sml150SMonth)" />
            <label for="sml150SdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="sml150Form" property="sml150SdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml150SdelKbn_1" onclick="smlDispState(this.form.sml150SdelKbn, this.form.sml150SYear, this.form.sml150SMonth)" />
            <label for="sml150SdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="sml150Form" property="sml150YearLabelList">
              <html:select property="sml150SYear" styleClass="sml150SYear">
                <html:optionsCollection name="sml150Form" property="sml150YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="sml150Form" property="sml150MonthLabelList">
              <html:select property="sml150SMonth" styleClass="sml150SMonth ml5 mr5">
                <html:optionsCollection name="sml150Form" property="sml150MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w20">
          <gsmsg:write key="cmn.autodelete" />
          <gsmsg:write key="cmn.draft" />
        </th>
        <td class="w80">
          <div class="verAlignMid">
            <html:radio name="sml150Form" property="sml150WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml150WdelKbn_0" onclick="smlDispState(this.form.sml150WdelKbn, this.form.sml150WYear, this.form.sml150WMonth)" />
            <label for="sml150WdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="sml150Form" property="sml150WdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml150WdelKbn_1" onclick="smlDispState(this.form.sml150WdelKbn, this.form.sml150WYear, this.form.sml150WMonth)" />
            <label for="sml150WdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="sml150Form" property="sml150YearLabelList">
              <html:select property="sml150WYear" styleClass="sml150WYear">
                <html:optionsCollection name="sml150Form" property="sml150YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="sml150Form" property="sml150MonthLabelList">
              <html:select property="sml150WMonth" styleClass="sml150WMonth ml5 mr5">
                <html:optionsCollection name="sml150Form" property="sml150MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </td>
      </tr>
      <tr>
        <th class="w20">
          <gsmsg:write key="cmn.autodelete" />
          <gsmsg:write key="cmn.trash" />
        </th>
        <td class="w80">
          <div class="verAlignMid">
            <html:radio name="sml150Form" property="sml150DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_NO) %>" styleId="sml150DdelKbn_0" onclick="smlDispState(this.form.sml150DdelKbn, this.form.sml150DYear, this.form.sml150DMonth)" />
            <label for="sml150DdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio styleClass="ml10" name="sml150Form" property="sml150DdelKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.SML_AUTO_DEL_LIMIT) %>" styleId="sml150DdelKbn_1" onclick="smlDispState(this.form.sml150DdelKbn, this.form.sml150DYear, this.form.sml150DMonth)" />
            <label for="sml150DdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="sml150Form" property="sml150YearLabelList">
              <html:select property="sml150DYear" styleClass="sml150DYear">
                <html:optionsCollection name="sml150Form" property="sml150YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="sml150Form" property="sml150MonthLabelList">
              <html:select property="sml150DMonth" styleClass="sml150DMonth ml5 mr5">
                <html:optionsCollection name="sml150Form" property="sml150MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </td>
      </tr>
    </table>
  </div>
</html:form>