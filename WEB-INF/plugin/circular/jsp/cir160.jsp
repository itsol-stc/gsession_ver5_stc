<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<%@ page import="jp.groupsession.v2.cir.cir160.Cir160Form"%>
<%-- 定数値 --%>
<%
  String acModeNormal = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.ACCOUNTMODE_NORMAL);
  String acModePsn = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.ACCOUNTMODE_PSNLSETTING);
  String acModeCommon = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.ACCOUNTMODE_COMMON);
  String cmdModeAdd = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CMDMODE_ADD);
  String cmdModeEdit = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CMDMODE_EDIT);
%>

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
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<script src="../circular/js/cir160.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="wml.wml040.05" /></title>
</head>

<body onunload="windowClose();">

  <html:form styleId="cir160Form" action="/circular/cir160">

    <logic:equal name="cir160Form" property="cirAccountMode" value="2">
      <input type="hidden" name="helpPrm" value="" />
    </logic:equal>

    <logic:notEqual name="cir160Form" property="cirAccountMode" value="2">
      <input type="hidden" name="helpPrm" value="5" />
    </logic:notEqual>

    <input type="hidden" name="CMD" value="">
    <html:hidden property="backScreen" />

    <html:hidden property="cirCmdMode" />
    <html:hidden property="cirViewAccount" />
    <html:hidden property="cirAccountMode" />
    <html:hidden property="cirAccountSid" />

    <html:hidden property="cir010cmdMode" />
    <html:hidden property="cir010orderKey" />
    <html:hidden property="cir010sortKey" />
    <html:hidden property="cir010pageNum1" />
    <html:hidden property="cir010pageNum2" />
    <html:hidden property="cir010SelectLabelSid" />

    <html:hidden property="cir010adminUser" />
    <html:hidden property="cir160initFlg" />
    <html:hidden property="cir160AccountKbn" />
    <html:hidden property="cir160DefActUsrSid" />
    <html:hidden property="cir160elementKbn" />
    <html:hidden property="cir150keyword" />
    <html:hidden property="cir150group" />
    <html:hidden property="cir150user" />
    <html:hidden property="cir150svKeyword" />
    <html:hidden property="cir150svGroup" />
    <html:hidden property="cir150svUser" />
    <html:hidden property="cir150sortKey" />
    <html:hidden property="cir150order" />
    <html:hidden property="cir150searchFlg" />
    <html:hidden property="cir160autoDelKbn" />
    <html:hidden property="cir160cirInitKbn" />
    <html:hidden property="cir160SelTab" />
    <html:hidden property="cir160SmlNtfKbn" />

    <logic:equal name="cir160Form" property="cir160cirInitKbn" value="0">
      <html:hidden property="cir160memoKbn" />
      <html:hidden property="cir160memoPeriod" />
      <html:hidden property="cir160show" />
    </logic:equal>


    <bean:define id="acctMode" name="cir160Form" property="cirAccountMode" type="java.lang.Integer" />
    <bean:define id="wCmdMode" name="cir160Form" property="cirCmdMode" type="java.lang.Integer" />
    <bean:define id="adminflg" name="cir160Form" property="cir010adminUser" type="java.lang.Boolean" />
    <%
      int accountMode = acctMode.intValue();
    %>
    <%
      int cmdMode = wCmdMode.intValue();
    %>
    <%
      boolean adminFlg = adminflg;
    %>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <%if (adminflg) { %>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          <gsmsg:write key="cmn.admin.setting" />
          <% } else { %>
          <img src="../common/images/classic/icon_ptool_large.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ptool_32.png" alt="<gsmsg:write key="cmn.preferences2" />" class="header_pluginImg">
          <gsmsg:write key="cmn.preferences2" />
          <% } %>
        </li>
        <li class="pageTitle_subFont">
          <%if (cmdMode == 0) { %>
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.wml040.05" />
          <% } else { %>
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="cir.5" /></span><gsmsg:write key="wml.98" />
          <% } %>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:equal name="cir160Form" property="cirCmdMode" value="1">
              <logic:equal name="cir160Form" property="cir160AccountKbn" value="1">
                <logic:equal name="cir160Form" property="cir160CanDelFlg" value="0">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccount');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                </logic:equal>
              </logic:equal>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper w80 mrl_auto">

      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <ul class="tabHeader w100">
        <li class="tabHeader_tab-on mwp100 pl10 pr10 js_tab border_bottom_none bgI_none" id="tab1">
          <gsmsg:write key="cmn.preferences" />
        </li>
        <logic:notEqual name="cir160Form" property="cir160autoDelKbn" value="0">
          <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab2">
            <gsmsg:write key="cmn.autodelete" />
          </li>
        </logic:notEqual>
        <logic:notEqual name="cir160Form" property="cir160cirInitKbn" value="0">
            <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab3">
              <gsmsg:write key="cir.23" />
            </li>
        </logic:notEqual>
        <logic:equal name="cir160Form" property="canSmlUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE)%>">
          <logic:equal name="cir160Form" property="cir160SmlNtfKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_USER)%>">
            <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab4">
              <gsmsg:write key="shortmail.notification" />
            </li>
          </logic:equal>
        </logic:equal>
        <li class="tabHeader_tab-off mwp100 pl10 pr10 js_tab border_bottom_none" id="tab5">
          <gsmsg:write key="cmn.other" />
        </li>
        <li class="tabHeader_space border_bottom_none"></li>
      </ul>

      <!-- 基本設定 -->
      <table id="tab1_table" class="table-left w100 mt0">
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="wml.96" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="txt_l">
            <logic:equal name="cir160Form" property="cir160AccountKbn" value="1">
              <html:text name="cir160Form" property="cir160name" maxlength="100" styleClass="wp400" />
            </logic:equal>
            <logic:equal name="cir160Form" property="cir160AccountKbn" value="0">
              <html:hidden property="cir160name" />
              <bean:write name="cir160Form" property="cir160name" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="webmail_td1 txt_l">
            <html:textarea name="cir160Form" property="cir160biko" styleClass="wp400" rows="10" />
          </td>
        </tr>
        <logic:equal name="cir160Form" property="cir160acntUserFlg" value="true">
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.employer" />
            </span>
            <span class="cl_fontWarn">
              <gsmsg:write key="cmn.comments" />
            </span>
          </th>
          <td class="w75">
            <ui:usrgrpselector name="cir160Form" property="userKbnUserSelector" styleClass="hp300" />
          </td>
        </tr>
      </logic:equal>
      <logic:notEqual name="cir160Form" property="cir160acntUserFlg" value="true">
        <logic:notEmpty name="cir160Form" property="cir160userKbnUser">
          <logic:iterate id="accountUser" name="cir160Form" property="cir160userKbnUser">
            <input type="hidden" name="cir160userKbnUser" value="<bean:write name="accountUser" />">
          </logic:iterate>
        </logic:notEmpty>
      </logic:notEqual>
      </table>

      <!-- 自動削除 -->
      <logic:notEqual name="cir160Form" property="cir160autoDelKbn" value="0">
        <table id="tab2_table" class="table-left display_none w100 mt0">
          <tr>
            <th class="w25">
              <span>
                <gsmsg:write key="cmn.autodelete" />
                <gsmsg:write key="cir.25" />
              </span>
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <span class="verAlignMid">
                  <html:radio name="cir160Form" property="cir160JdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO)%>" styleId="cir160JdelKbn_0" onclick="setDispState(this.form.cir160JdelKbn, this.form.cir160JYear, this.form.cir160JMonth)" />
                  <label for="cir160JdelKbn_0">
                    <gsmsg:write key="cmn.noset" />
                  </label>
                  <html:radio name="cir160Form" property="cir160JdelKbn" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT)%>" styleId="cir160JdelKbn_1" onclick="setDispState(this.form.cir160JdelKbn, this.form.cir160JYear, this.form.cir160JMonth)" />
                  <label for="cir160JdelKbn_1">
                    <gsmsg:write key="cmn.automatically.delete" />
                  </label>
                </span>
                <gsmsg:write key="cmn.after.data.head" />
                <logic:notEmpty name="cir160Form" property="cir160YearLabelList">
                  <html:select property="cir160JYear" styleClass="wp100 ml5">
                    <html:optionsCollection name="cir160Form" property="cir160YearLabelList" value="value" label="label" />
                  </html:select>
                </logic:notEmpty>
                <logic:notEmpty name="cir160Form" property="cir160MonthLabelList">
                  <html:select property="cir160JMonth" styleClass="wp100 ml10 mr5">
                    <html:optionsCollection name="cir160Form" property="cir160MonthLabelList" value="value" label="label" />
                  </html:select>
                  <gsmsg:write key="cmn.after.data" />
                </logic:notEmpty>
              </span>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <span>
                <gsmsg:write key="cmn.autodelete" />
                <gsmsg:write key="cir.26" />
              </span>
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <span class="verAlignMid">
                  <html:radio name="cir160Form" property="cir160SdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO)%>" styleId="cir160SdelKbn_0" onclick="setDispState(this.form.cir160SdelKbn, this.form.cir160SYear, this.form.cir160SMonth)" />
                  <label for="cir160SdelKbn_0">
                    <gsmsg:write key="cmn.noset" />
                  </label>
                  <html:radio name="cir160Form" property="cir160SdelKbn" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT)%>" styleId="cir160SdelKbn_1" onclick="setDispState(this.form.cir160SdelKbn, this.form.cir160SYear, this.form.cir160SMonth)" />
                  <label for="cir160SdelKbn_1">
                    <gsmsg:write key="cmn.automatically.delete" />
                  </label>
                </span>
                <gsmsg:write key="cmn.after.data.head" />
                <logic:notEmpty name="cir160Form" property="cir160YearLabelList">
                  <html:select property="cir160SYear" styleClass="wp100 ml5">
                    <html:optionsCollection name="cir160Form" property="cir160YearLabelList" value="value" label="label" />
                  </html:select>
                </logic:notEmpty>

                <logic:notEmpty name="cir160Form" property="cir160MonthLabelList">
                  <html:select property="cir160SMonth" styleClass="wp100 ml10 mr5">
                    <html:optionsCollection name="cir160Form" property="cir160MonthLabelList" value="value" label="label" />
                  </html:select>
                  <gsmsg:write key="cmn.after.data" />
                </logic:notEmpty>
              </span>
            </td>
          </tr>
          <tr>
            <th class="w25">
              <span>
                <gsmsg:write key="cmn.autodelete" />
                <gsmsg:write key="cir.27" />
              </span>
            </th>
            <td class="w75">
              <span class="verAlignMid">
                <span class="verAlignMid">
                  <html:radio name="cir160Form" property="cir160DdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_NO)%>" styleId="cir160DdelKbn_0" onclick="setDispState(this.form.cir160DdelKbn, this.form.cir160DYear, this.form.cir160DMonth)" />
                  <label for="cir160DdelKbn_0">
                    <gsmsg:write key="cmn.noset" />
                  </label>
                  <html:radio name="cir160Form" styleClass="ml10" property="cir160DdelKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_AUTO_DEL_LIMIT)%>" styleId="cir160DdelKbn_1" onclick="setDispState(this.form.cir160DdelKbn, this.form.cir160DYear, this.form.cir160DMonth)" />
                  <label for="cir160DdelKbn_1">
                    <gsmsg:write key="cmn.automatically.delete" />
                  </label>
                </span>
                <gsmsg:write key="cmn.after.data.head" />

                <logic:notEmpty name="cir160Form" property="cir160YearLabelList">
                  <html:select property="cir160DYear" styleClass="wp100 ml5">
                    <html:optionsCollection name="cir160Form" property="cir160YearLabelList" value="value" label="label" />
                  </html:select>
                </logic:notEmpty>

                <logic:notEmpty name="cir160Form" property="cir160MonthLabelList">
                  <html:select property="cir160DMonth" styleClass="wp100 ml10 mr5">
                    <html:optionsCollection name="cir160Form" property="cir160MonthLabelList" value="value" label="label" />
                  </html:select>
                  <gsmsg:write key="cmn.after.data" />
                </logic:notEmpty>
              </span>
            </td>
          </tr>
        </table>
      </logic:notEqual>

      <!-- 回覧板初期値設定 -->
      <logic:notEqual name="cir160Form" property="cir160cirInitKbn" value="0">
        <table id="tab3_table" class="table-left display_none w100 mt0">

          <tr>
            <th class="w25" id="cirEditArea1">
              <span>
                <gsmsg:write key="cir.cir040.2" />
              </span>
            </th>
            <logic:equal name="cir160Form" property="cir160memoKbn" value="0">
              <html:hidden property="cir160memoPeriod" />
              <!-- メモ欄修正区分 -->
                <td class="w75 no_w">
                  <span class="verAlignMid">
                  <span>
                    <gsmsg:write key="cir.cir040.3" />
                  </span>
                    <html:radio property="cir160memoKbn" styleClass="ml10" styleId="memoNg" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_NO)%>" onclick="buttonPush('memoKbnChange');" />
                    <label for="memoNg">
                      <span>
                        <gsmsg:write key="cmn.not" />
                      </span>
                    </label>
                    <html:radio property="cir160memoKbn" styleClass="ml10" styleId="memoOk" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_YES)%>" onclick="buttonPush('memoKbnChange');" />
                    <label for="memoOk">
                      <span>
                        <gsmsg:write key="cmn.accepted" />
                      </span>
                    </label>
                  </span>
                </td>
            </logic:equal>

            <logic:equal name="cir160Form" property="cir160memoKbn" value="1">
              <!-- メモ欄修正区分 -->
                <td class="w75">
                  <div class="verAlignMid">
                  <span>
                    <gsmsg:write key="cir.cir040.3" />
                  </span>
                    <html:radio property="cir160memoKbn" styleId="memoNg" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_NO)%>" onclick="buttonPush('memoKbnChange');" />
                    <label for="memoNg">
                      <span>
                        <gsmsg:write key="cmn.not" />
                      </span>
                    </label>
                    <html:radio property="cir160memoKbn" styleId="memoOk" styleClass="ml10" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_MEMO_CHANGE_YES)%>" onclick="buttonPush('memoKbnChange');" />
                    <label for="memoOk">
                      <span>
                        <gsmsg:write key="cmn.accepted" />
                      </span>
                    </label>
                  </div>

                  <!-- メモ欄修正期限設定 -->
                  <div>
                    <span>
                      <gsmsg:write key="cir.54" />
                    </span>
                    <span class="verAlignMid">
                      <html:radio property="cir160memoPeriod" styleId="today" value="1" />
                      <label for="today">
                        <span>
                          <gsmsg:write key="cmn.today" />
                        </span>
                      </label>
                      <html:radio property="cir160memoPeriod" styleClass="ml10" styleId="1weeks" value="0" />
                      <label for="1weeks">
                        <span>
                          1<gsmsg:write key="cmn.weeks" />
                        </span>
                      </label>
                      <html:radio property="cir160memoPeriod" styleClass="ml10" styleId="2weeks" value="2" />
                      <label for="2weeks">
                        <span>
                          2<gsmsg:write key="cmn.weeks" />
                        </span>
                      </label>
                      <html:radio property="cir160memoPeriod" styleClass="ml10" styleId="months" value="3" />
                      <label for="months">
                        <span>
                          <gsmsg:write key="cmn.months" arg0="1" />
                        </span>
                      </label>
                    </span>
                  </div>
                </td>
            </logic:equal>
          </tr>

          <!-- 回覧先確認編集権限区分 -->
          <tr>
            <th class="w25" id="cirEditArea1">
              <span>
                <gsmsg:write key="cir.cir030.3" />
              </span>
            </th>

            <!-- 回覧先確認状況区分 -->
            <td class="w75">
              <span class="verAlignMid">
                <html:radio name="cir160Form" property="cir160show" styleId="showPub" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC)%>" />
                <label for="showPub">
                  <span>
                    <gsmsg:write key="cmn.public" />
                  </span>
                </label>
                <html:radio name="cir160Form" property="cir160show" styleClass="ml10" styleId="showPri" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PRIVATE)%>" />
                <label for="showPri">
                  <span>
                    <gsmsg:write key="cmn.private" />
                  </span>
                </label>
              </span>
            </td>
          </tr>

        </table>
      </logic:notEqual>
      <!-- ショートメール通知タブボディ -->
      <logic:equal name="cir160Form" property="canSmlUse" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.PLUGIN_USE)%>">
        <logic:equal name="cir160Form" property="cir160SmlNtfKbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAF_SML_NTF_USER)%>">
          <table id="tab4_table" class="table-left display_none w100 mt0">

            <tr>
              <th class="w25">
                <span>
                  <gsmsg:write key="cir.cir160.2" />
                </span>
              </th>
              <td class="w75">
                <div>
                  <gsmsg:write key="cir.cir160.3" />
                </div>
                <div class="mt5">
                  <span class="verAlignMid">
                    <html:radio name="cir160Form" styleId="cir160smlNtf_01" property="cir160smlNtf" value="<%=tuuchi%>" />
                    <label for="cir160smlNtf_01">
                      <span>
                        <gsmsg:write key="cmn.notify" />
                      </span>
                    </label>
                    <html:radio name="cir160Form" styleClass="ml10" styleId="cir160smlNtf_02" property="cir160smlNtf" value="<%=notuuchi%>" />
                    <label for="cir160smlNtf_02">
                      <span>
                        <gsmsg:write key="cmn.dont.notify" />
                      </span>
                    </label>
                  </span>
                </div>
              </td>
            </tr>
            <tr>
              <th class="w25">
                <span>
                  <gsmsg:write key="cir.cir160.4" />
                </span>
              </th>
              <td class="w75">
                <div>
                  <gsmsg:write key="cir.cir160.5" />
                </div>
                <div class="mt5">
                  <span class="verAlignMid">
                    <html:radio name="cir160Form" styleId="cir160smlMemo_01" property="cir160smlMemo" value="<%=tuuchi%>" />
                    <label for="cir160smlMemo_01">
                      <span>
                        <gsmsg:write key="cmn.notify" />
                      </span>
                    </label>
                    <html:radio name="cir160Form" styleClass="ml10" styleId="cir160smlMemo_02" property="cir160smlMemo" value="<%=notuuchi%>" />
                    <label for="cir160smlMemo_02">
                      <span>
                        <gsmsg:write key="cmn.dont.notify" />
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
                    <html:radio name="cir160Form" styleId="cir160smlEdt_01" property="cir160smlEdt" value="<%=tuuchi%>" />
                    <label for="cir160smlEdt_01">
                      <span>
                        <gsmsg:write key="cmn.notify" />
                      </span>
                    </label>
                    <html:radio name="cir160Form" styleClass="ml10" styleId="cir160smlEdt_02" property="cir160smlEdt" value="<%=notuuchi%>" />
                    <label for="cir160smlEdt_02">
                      <span>
                        <gsmsg:write key="cmn.dont.notify" />
                      </span>
                    </label>
                  </span>
                </div>
              </td>
            </tr>
          </table>
        </logic:equal>
      </logic:equal>
      <!-- その他 -->
      <table id="tab5_table" class="table-left display_none w100 mt0">
        <tr>
          <th class="w25">
            <span>
              <gsmsg:write key="cmn.theme" />
            </span>
          </th>
          <td class="w75">
            <html:select name="cir160Form" property="cir160theme">
              <logic:notEmpty name="cir160Form" property="cir160themeList">
                <html:optionsCollection name="cir160Form" property="cir160themeList" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('confirm');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="cir160Form" property="cirCmdMode" value="1">
          <logic:equal name="cir160Form" property="cir160AccountKbn" value="1">
            <logic:equal name="cir160Form" property="cir160CanDelFlg" value="0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('deleteAccount');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
          </logic:equal>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('beforePage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    <div id="messagePop" title="" class="display_n">
      <table class="w100 h100">
        <tr>
          <td class="w15">
            <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
          </td>
          <td class="w85">
            <b id="messageArea" class="fs_13"></b>
          </td>
        </tr>
      </table>
    </div>

  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>