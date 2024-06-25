<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cht.GSConstChat"%>

<script src='../chat/js/cht060.js?<%=GSConst.VERSION_PARAM%>'></script>

<html:form action="/chat/cht060">

<input type="hidden" name="plgId" value="cht">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_chtErrorArea"></div>
  <div class="txt_r">
    <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="setChtMessage('chat', this);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
      <gsmsg:write key="cmn.delete" />
    </button>
  </div>
  <table class="table-left">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.manual.delete2" />
      </th>
      <td class="w80">
        <gsmsg:write key="cht.cht060.01" /><br>
        <div class="verAlignMid mt5">
        <logic:notEmpty name="cht060Form" property="cht060YearLabelList">
        <html:select property="cht060ReferenceYear" styleId="delYear" styleClass="cht060ReferenceYear">
        <html:optionsCollection name="cht060Form" property="cht060YearLabelList" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
        <logic:notEmpty name="cht060Form" property="cht060MonthLabelList">
        <html:select property="cht060ReferenceMonth" styleId="delMonth" styleClass="cht060ReferenceMonth ml5 mr5">
        <html:optionsCollection name="cht060Form" property="cht060MonthLabelList" value="value" label="label" />
        </html:select>
        </logic:notEmpty>
        <gsmsg:write key="cmn.after.data"/>
        </div>
      </td>
    </tr>
  </table>
</div>
</html:form>