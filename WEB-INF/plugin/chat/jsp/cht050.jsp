<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>

<script src='../chat/js/cht050.js?<%=GSConst.VERSION_PARAM%>'></script>
<html:form action="/chat/cht050" styleClass="js_delForm">
<input type="hidden" name="plgId" value="cht">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_chtErrorArea"></div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.autodelete" />
      </th>
      <td class="w80">
        <div><gsmsg:write key="cht.cht050.01" /></div>
        <div class="display_n js_check"><bean:write name="cht050Form" property="cht050DoAutoDel"/></div>
        <div class="mt5">
          <div class="verAlignMid">
            <html:radio name="cht050Form" property="cht050DoAutoDel" styleId="notConf" styleClass="cht_auto_del_flg_not" value="0"/>
            <label for="notConf"><gsmsg:write key="cmn.noset" /></label>
            <html:radio name="cht050Form" property="cht050DoAutoDel" styleId="autoConf" styleClass="cht_auto_del_flg ml10" value="1"/>
            <label for="autoConf"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <logic:notEmpty name="cht050Form" property="cht050YearLabelList" scope="request">
              <html:select property="cht050ReferenceYear" styleId="cht050DelYear">
                <html:optionsCollection name="cht050Form" property="cht050YearLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <logic:notEmpty name="cht050Form" property="cht050MonthLabelList" scope="request">
              <html:select styleClass="ml5 mr5" property="cht050ReferenceMonth" styleId="cht050DelMonth">
                <html:optionsCollection name="cht050Form" property="cht050MonthLabelList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <gsmsg:write key="cmn.after.data" />
          </div>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>