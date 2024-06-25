<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.mem.GSConstMemo"%>

<script src="../memo/js/mem030.js?<%= GSConst.VERSION_PARAM %>"></script>
<html:form action="/memo/mem030" styleClass="js_delForm">
<input type="hidden" name="plgId" value="mem">

<div class="wrapper w100 mrl_auto">
  <div class="txt_l js_memErrorArea"></div>
  <table class="table-left">
    <!-- 自動削除 発信 -->
    <tr>
      <th class="w20"><gsmsg:write key="cmn.autodelete" /></th>
      <td class="w80">
        <div><gsmsg:write key="memo.mem030.2" /></div>
        <div class="mt5">
          <div class="verAlignMid">
            <!-- 設定しない -->
            <html:radio styleId="mem030Kbn0" name="mem030Form" property="mem030Kbn" value="<%= String.valueOf(GSConstMemo.AUTO_DELETE_KBN_OFF) %>" onclick="changeDisable();" />
            <label for="mem030Kbn0"><gsmsg:write key="cmn.noset"/></label>
            <!-- 自動で削除する -->
            <html:radio styleId="mem030Kbn1" name="mem030Form" property="mem030Kbn" value="<%= String.valueOf(GSConstMemo.AUTO_DELETE_KBN_ON) %>" styleClass="ml10" onclick="changeEnable();"/>
            <label for="mem030Kbn1"><gsmsg:write key="cmn.automatically.delete" /></label>
          </div>
          <div class="ml20">
            <!-- 年 -->
            <logic:notEmpty name="mem030Form" property="mem030AtdelYearLabel" scope="request">
              <html:select property="mem030Year" styleId="atdelYear" styleClass="mem030Year">
                <html:optionsCollection name="mem030Form" property="mem030AtdelYearLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <!-- 月 -->
            <logic:notEmpty name="mem030Form" property="mem030AtdelMonthLabel" scope="request">
              <html:select property="mem030Month" styleId="atdelMonth" styleClass="mem030Month ml5 mr5">
                <html:optionsCollection name="mem030Form" property="mem030AtdelMonthLabel" value="value" label="label" />
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