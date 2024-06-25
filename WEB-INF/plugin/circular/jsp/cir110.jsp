<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<script src="../circular/js/cir110.js?<%=GSConst.VERSION_PARAM%>"></script>
<html:form action="/circular/cir110" styleClass="js_delForm">
<input type="hidden" name="plgId" value="cir">

<div class="wrapper w100 mrl_auto">
  <logic:messagesPresent message="false">
    <html:errors />
  </logic:messagesPresent>
  <div class="component_bothEnd">
    <div class="txt_l">※<gsmsg:write key="cir.59" /></div>
  </div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cir.25" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="cir110Form" property="cir110JdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO)%>" styleId="cir110JdelKbn_0" onclick="cirDispState(this.form.cir110JdelKbn, this.form.cir110JYear, this.form.cir110JMonth)" />
            <label for="cir110JdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="cir110Form" property="cir110JdelKbn" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT)%>" styleId="cir110JdelKbn_1" onclick="cirDispState(this.form.cir110JdelKbn, this.form.cir110JYear, this.form.cir110JMonth)" />
            <label for="cir110JdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
            <gsmsg:write key="cmn.after.data.head" />
          </div>
          <div class="ml20">
            <logic:notEmpty name="cir110Form" property="cir110YearLabelList">
              <html:select property="cir110JYear" styleClass="cir110JYear">
                <html:optionsCollection name="cir110Form" property="cir110YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="cir110Form" property="cir110MonthLabelList">
              <html:select property="cir110JMonth" styleClass="cir110JMonth ml5 mr5">
                <html:optionsCollection name="cir110Form" property="cir110MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cir.26" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="cir110Form" property="cir110SdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO)%>" styleId="cir110SdelKbn_0" onclick="cirDispState(this.form.cir110SdelKbn, this.form.cir110SYear, this.form.cir110SMonth)" />
            <label for="cir110SdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="cir110Form" property="cir110SdelKbn" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT)%>" styleId="cir110SdelKbn_1" onclick="cirDispState(this.form.cir110SdelKbn, this.form.cir110SYear, this.form.cir110SMonth)" />
            <label for="cir110SdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="cir110Form" property="cir110YearLabelList">
              <html:select property="cir110SYear" styleClass="cir110SYear">
                <html:optionsCollection name="cir110Form" property="cir110YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="cir110Form" property="cir110MonthLabelList">
              <html:select property="cir110SMonth" styleClass="cir110SMonth ml5 mr5">
                <html:optionsCollection name="cir110Form" property="cir110MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
        <gsmsg:write key="cir.27" />
      </th>
      <td class="w80">
        <div>
          <div class="verAlignMid">
            <html:radio name="cir110Form" property="cir110DdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO)%>" styleId="cir110DdelKbn_0" onclick="cirDispState(this.form.cir110DdelKbn, this.form.cir110DYear, this.form.cir110DMonth)" />
            <label for="cir110DdelKbn_0"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="cir110Form" property="cir110DdelKbn" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT)%>" styleId="cir110DdelKbn_1" onclick="cirDispState(this.form.cir110DdelKbn, this.form.cir110DYear, this.form.cir110DMonth)" />
            <label for="cir110DdelKbn_1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="cir110Form" property="cir110YearLabelList">
              <html:select property="cir110DYear" styleClass="cir110DYear">
                <html:optionsCollection name="cir110Form" property="cir110YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="cir110Form" property="cir110MonthLabelList">
              <html:select property="cir110DMonth" styleClass="cir110DMonth ml5 mr5">
                <html:optionsCollection name="cir110Form" property="cir110MonthLabelList" value="value" label="label" />
              </html:select>
              <gsmsg:write key="cmn.after.data" />
            </logic:notEmpty>
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>