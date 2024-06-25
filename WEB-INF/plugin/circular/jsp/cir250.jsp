<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.cir.cir250.Cir250Form"%>
<%-- 定数値 --%>

<%
  String tuuchi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SMAIL_TSUUCHI);
%>
<%
  String notuuchi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.SMAIL_NOT_TSUUCHI);
%>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>GROUPSESSION <gsmsg:write key="wml.wml040.05" /></title>
</head>

<body>
  <html:form styleId="cir250Form" action="/circular/cir250" styleClass="js_smailSetting">
  <input type="hidden" name="plgId" value="cir">
    <div class="wrapper w100">
      <div class="txt_l">
      <gsmsg:write key="cmn.select" /><gsmsg:write key="cmn.account" /><gsmsg:write key="wml.215" />
      <html:select name="cir250Form" property="cir250selectAccount" onchange="accountChange('circular', this);">
        <html:optionsCollection name="cir250Form" property="cir250accountList" value="value" label="label" />
      </html:select>
      </div>
      <table class="table-left w100">
        <tr>
          <th class="w20">
            <span>
              <gsmsg:write key="cir.cir160.2" />
            </span>
          </th>
          <td class="w80">
            <div>
              <gsmsg:write key="cir.cir160.3" />
            </div>
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="cir250Form" styleId="cir250smlNtf_02" property="cir250smlNtf" value="<%=notuuchi%>" />
                <label for="cir250smlNtf_02">
                  <span>
                    <gsmsg:write key="cmn.dont.notify" />
                  </span>
                </label>
                <html:radio name="cir250Form" styleClass="ml10" styleId="cir250smlNtf_01" property="cir250smlNtf" value="<%=tuuchi%>" />
                <label for="cir250smlNtf_01">
                  <span>
                    <gsmsg:write key="cmn.notify" />
                  </span>
                </label>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <th class="w20">
            <span>
              <gsmsg:write key="cir.cir160.4" />
            </span>
          </th>
          <td class="w80">
            <div>
              <gsmsg:write key="cir.cir160.5" />
            </div>
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="cir250Form" styleId="cir250smlMemo_02" property="cir250smlMemo" value="<%=notuuchi%>" />
                <label for="cir250smlMemo_02">
                  <span>
                    <gsmsg:write key="cmn.dont.notify" />
                  </span>
                </label>
                <html:radio name="cir250Form" styleClass="ml10" styleId="cir250smlMemo_01" property="cir250smlMemo" value="<%=tuuchi%>" />
                <label for="cir250smlMemo_01">
                  <span>
                    <gsmsg:write key="cmn.notify" />
                  </span>
                </label>
              </span>
            </div>
          </td>
        </tr>
        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cir.cir160.6" />
            </span>
          </th>
          <td>
            <div>
              <gsmsg:write key="cir.cir160.7" />
            </div>
            <div class="mt5">
              <span class="verAlignMid">
                <html:radio name="cir250Form" styleId="cir250smlEdt_02" property="cir250smlEdt" value="<%=notuuchi%>" />
                <label for="cir250smlEdt_02">
                  <span>
                    <gsmsg:write key="cmn.dont.notify" />
                  </span>
                </label>
                <html:radio name="cir250Form" styleClass="ml10" styleId="cir250smlEdt_01" property="cir250smlEdt" value="<%=tuuchi%>" />
                <label for="cir250smlEdt_01">
                  <span>
                    <gsmsg:write key="cmn.notify" />
                  </span>
                </label>
              </span>
            </div>
          </td>
        </tr>
      </table>
    </div>
  </html:form>
</body>
</html:html>