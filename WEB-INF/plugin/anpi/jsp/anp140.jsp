<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp140.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<link rel=stylesheet href='../anpi/css/anpi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body onload="setfocus();" onunload="windowClose()">
  <html:form action="/anpi/anp140">
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anp130SelectAphSid" />
    <html:hidden property="anp130allCheck" />
    <html:hidden property="anp130NowPage" />
    <html:hidden property="anp130DspPage1" />
    <html:hidden property="anp130DspPage2" />
    <html:hidden property="anp140NowPage" />
    <html:hidden property="anp140DspPage1" />
    <html:hidden property="anp140DspPage2" />
    <html:hidden property="anp140SortKeyIndex" />
    <html:hidden property="anp140OrderKey" />
    <html:hidden property="anp140ScrollFlg" />
    <logic:notEmpty name="anp140Form" property="anp130DelSidList" scope="request">
      <logic:iterate id="del" name="anp140Form" property="anp130DelSidList" scope="request">
        <input type="hidden" name="anp130DelSidList" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="kanriPageTitle w80 mrl_auto">
      <ul>
        <li>
          <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
          <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
        </li>
        <li>
          <gsmsg:write key="cmn.admin.setting" />
        </li>
        <li class="pageTitle_subFont">
          <span class="pageTitle_subFont-plugin"><gsmsg:write key="anp.plugin" /></span><gsmsg:write key="anp.anp140.01" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonPush('anp140copyNew');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy.new2" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy.new2" />">
              <gsmsg:write key="cmn.register.copy2" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('anp140delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp140back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <!-- 訓練モード バー -->
      <logic:equal name="anp140Form" property="anp010KnrenFlg" value="1">
        <div class="anp_kunren">
          <gsmsg:write key="anp.knmode" />
        </div>
      </logic:equal>
      <table class="table-left w100">
        <!-- 配信日時 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.date.send" />
          </th>
          <td class="w75">
            <bean:write name="anp140Form" property="anp140HaisinDate" />
          </td>
        </tr>
        <!-- 配信者 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.sender" />
          </th>
          <td class="w75">
            <logic:equal name="anp140Form" property="anp140usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
              <bean:define id="mukoUserClass" value="" />
              <logic:equal value="1" name="anp140Form" property="anp140usrUkoFlg">
                <bean:define id="mukoUserClass" value="mukoUser" />
              </logic:equal>
              <span class="<%= mukoUserClass%>">
                <bean:write name="anp140Form" property="anp140Name" />
              </span>
            </logic:equal>
            <logic:notEqual name="anp140Form" property="anp140usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
              <del>
                <bean:write name="anp140Form" property="anp140Name" />
              </del>
            </logic:notEqual>
          </td>
        </tr>
        <!-- 件名 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.subject" />
          </th>
          <td class="w75">
            <bean:write name="anp140Form" property="anp140Subject" />
          </td>
        </tr>
        <!-- 本文 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.body" />
          </th>
          <td class="w75">
            <bean:write name="anp140Form" property="anp140Body" filter="false" />
          </td>
        </tr>
        <!-- 結果状況 -->
        <tr>
          <th class="w25">
            <gsmsg:write key="anp.anp140.02" />
          </th>
          <td class="w75">
            <a id="js_label_sender" name="label_sender"></a>
            <table class="wp500">
              <tr>
                <td class="w60 border_none plr0">
                  <table class="table-left m0">
                    <tr>
                      <th class="txt_c fs_base">
                        <gsmsg:write key="anp.date.send" />
                      </th>
                      <td colspan="2">
                        <bean:write name="anp140Form" property="anp140HaisinDate" />
                      </td>
                    </tr>
                    <tr>
                      <th class="txt_c fs_base">
                        <gsmsg:write key="anp.date.end" />
                      </th>
                      <td colspan="2">
                        <bean:write name="anp140Form" property="anp140EndDate" />
                      </td>
                    </tr>
                    <tr>
                      <th class="txt_c fs_base">
                        <gsmsg:write key="anp.anp140.03" />
                      </th>
                      <td colspan="2">
                        <bean:write name="anp140Form" property="anp140ReplyState" />
                      </td>
                    </tr>
                    <tr>
                      <th class="txt_c fs_base" rowspan="3">
                        <gsmsg:write key="anp.state" />
                      </th>
                      <th class="fw_n fs_base">
                        <gsmsg:write key="anp.jokyo.good" />
                      </td>
                      <td class="w20 txt_r fs_base">
                        <bean:write name="anp140Form" property="anp140Buji" />
                      </td>
                    </tr>
                    <tr>
                      <th class="fw_n fs_base">
                        <gsmsg:write key="anp.jokyo.keisyo" />
                      </td>
                      <td class="txt_r">
                        <bean:write name="anp140Form" property="anp140Keisyo" />
                      </td>
                    </tr>
                    <tr>
                      <th class="fw_n fs_base">
                        <gsmsg:write key="anp.jokyo.jusyo" />
                      </td>
                      <td class="txt_r">
                        <bean:write name="anp140Form" property="anp140Jyusyo" />
                      </td>
                    </tr>
                    <tr>
                      <th class="txt_c fs_base" rowspan="2">
                        <gsmsg:write key="anp.syusya" />
                      </th>
                      <th class="fw_n fs_base">
                        <gsmsg:write key="anp.syusya.ok2" />
                      </td>
                      <td class="txt_r ">
                        <bean:write name="anp140Form" property="anp140SyusyaOk" />
                      </td>
                    </tr>
                    <tr>
                      <th class="fw_n fs_base">
                        <gsmsg:write key="anp.syusya.no" />
                      </td>
                      <td class="txt_r">
                        <bean:write name="anp140Form" property="anp140SyusyaNo" />
                      </td>
                    </tr>
                  </table>
                </td>
                <td class="border_none txt_b wp500 plr0"></td>
              </tr>
            </table>

            <!-- ページコンボ -->
            <bean:size id="pageCount" name="anp140Form" property="anp140PageLabel" scope="request" />
            <logic:greaterThan name="pageCount" value="1">
              <div class="paging txt_r ml5">
                <button type="button" class="webIconBtn" onClick="buttonPush('anp140pageLast');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select styleClass="paging_combo" property="anp140DspPage1" onchange="changePage(this);">
                  <html:optionsCollection name="anp140Form" property="anp140PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('anp140pageNext');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>

            <table class="table-top">
              <!-- ヘッダ部分 -->
              <tr>
                <bean:define id="sortKeyIndex" name="anp140Form" property="anp140SortKeyIndex" />
                <bean:define id="orderKey" name="anp140Form" property="anp140OrderKey" />
                <gsmsg:define id="h_send" msgkey="anp.send.dest" />
                <gsmsg:define id="h_mail" msgkey="cmn.mailaddress" />
                <gsmsg:define id="h_ans" msgkey="anp.date.ans" />
                <gsmsg:define id="h_state" msgkey="anp.state" />
                <% int iSortKeyIndex = ((Integer) sortKeyIndex).intValue();   %>
                <% int iOrderKey = ((Integer) orderKey).intValue();     %>
                <% String[] colTitle = new String[] {h_send, h_mail, h_ans, h_state}; %>
                <% String[] colWidth = new String[] {"w25", "w45", "w20", "w10"}; %>
                <% Integer[] colOrder = new Integer[] {1, 1, 1, 1}; %>
                <% for (int i = 0; i < colTitle.length; i++) { %>
                <%   String title = colTitle[i];                    %>
                <%   Integer order = -1;                            %>
                <%   String sortCla = "";                           %>
                <%   String sortOri = "";                           %>
                <%   if (iSortKeyIndex == i) {                      %>
                <%     if (iOrderKey == GSConst.ORDER_KEY_ASC) {    %>
                <%       title = title;                             %>
                <%       sortCla = "▲";                            %>
                <%       sortOri = "<i class=\"icon-sort_up\"></i>";%>
                <%     } else {                                     %>
                <%       title = title;                             %>
                <%       sortCla = "▼";                            %>
                <%       sortOri = "<i class=\"icon-sort_down\"></i>";%>
                <%     }                                            %>
                <%     order = iOrderKey;                           %>
                <%   }                                              %>
                <th class="<%= colWidth[i] %> table_title-color txt_c no_w cursor_p">
                  <% if (colOrder[i] == 1) { %>
                  <a href="#" class="cl_fontOutline" onClick="return sortList(<%= i %>, <%= order %>);">
                    <% } %>
                      <span class="classic-display"><%= title %><%= sortCla %></span>
                    <% if (iOrderKey == GSConst.ORDER_KEY_ASC) { %>
                      <span class="original-display txt_m"><%= title %><%= sortOri %></span>
                      <% } else { %>
                      <span class="original-display txt_m"><%= title %><%= sortOri %></span>
                      <% } %>
                    <% if (colOrder[i] == 1) { %>
                  </a>
                  <% } %>
                </th>
                <% } %>
              </tr>
              <logic:notEmpty name="anp140Form" property="anp140JyokyoList">
                <logic:iterate id="Jyookyo" name="anp140Form" property="anp140JyokyoList" indexId="idx">

                  <logic:equal name="Jyookyo" property="apsType" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEND_TYPE_GROUP)%>">
                    <tr class="js_listHover cursor_p" onClick="openGrpUsrRirekiWindow(<bean:write name="anp140Form" property="anp130SelectAphSid"/>,<bean:write name="Jyookyo" property="grpSid"/>);">
                  </logic:equal>
                  <logic:notEqual name="Jyookyo" property="apsType" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEND_TYPE_GROUP)%>">
                    <tr>
                  </logic:notEqual>

                  <!-- 送信者 -->
                    <td>
                      <logic:equal name="Jyookyo" property="apsType" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEND_TYPE_GROUP) %>">
                      <span class="cl_linkDef">
                        <bean:write name="Jyookyo" property="grpNameTo" />
                      </span>
                    </logic:equal>
                      <logic:notEqual name="Jyookyo" property="apsType" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEND_TYPE_GROUP) %>">
                        <logic:equal name="Jyookyo" property="jyotaiKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                          <bean:define id="mukoUserClass" value="" />
                          <logic:equal value="1" name="Jyookyo" property="usrUkoFlg">
                            <bean:define id="mukoUserClass" value="mukoUser" />
                          </logic:equal>
                          <span class="<%=mukoUserClass%>">
                            <bean:write name="Jyookyo" property="nameTo" />
                          </span>
                        </logic:equal>
                        <logic:notEqual name="Jyookyo" property="jyotaiKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                          <del>
                            <bean:write name="Jyookyo" property="nameTo" />
                          </del>
                        </logic:notEqual>
                      </logic:notEqual>
                    </td>
                    <!-- 連絡先 -->
                    <logic:equal name="Jyookyo" property="apsType" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEND_TYPE_GROUP) %>">
                      <td class="txt_c">
                        -
                      </td>
                    </logic:equal>
                    <logic:notEqual name="Jyookyo" property="apsType" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEND_TYPE_GROUP) %>">
                      <td class="txt_l">
                        <bean:write name="Jyookyo" property="mailAddress" />
                      </td>
                    </logic:notEqual>
                    <!-- 返信日時 -->
                    <td class="txt_c">
                      <bean:write name="Jyookyo" property="hensinDate" />
                    </td>
                    <!-- 状態 -->
                    <td class="txt_c">
                      <logic:equal name="Jyookyo" property="anpJyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_UNSET) %>">
                        -
                      </logic:equal>
                      <logic:equal name="Jyookyo" property="anpJyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_GOOD) %>">
                        <span class="cl_fontSafe">
                          <gsmsg:write key="anp.jokyo.good" />
                        </span>
                      </logic:equal>
                      <logic:equal name="Jyookyo" property="anpJyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_KEISYO) %>">
                        <span class="cl_fontWeekWarn">
                          <gsmsg:write key="anp.jokyo.keisyo" />
                        </span>
                      </logic:equal>
                      <logic:equal name="Jyookyo" property="anpJyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_JUSYO) %>">
                        <span class="cl_fontWarn">
                          <gsmsg:write key="anp.jokyo.jusyo" />
                        </span>
                      </logic:equal>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </table>
            <!-- ページコンボ -->
            <bean:size id="pageCount" name="anp140Form" property="anp140PageLabel" scope="request" />
            <logic:greaterThan name="pageCount" value="1">
              <div class="paging txt_r ml5">
                <button type="button" class="webIconBtn" onClick="buttonPush('anp140pageLast');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select styleClass="paging_combo" property="anp140DspPage2" onchange="changePage(this);">
                  <html:optionsCollection name="anp140Form" property="anp140PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('anp140pageNext');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.register.copy2" />" onClick="buttonPush('anp140copyNew');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy.new2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy.new2" />">
          <gsmsg:write key="cmn.register.copy2" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('anp140delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
         <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp140back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>