<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.plugin" /></title>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script type="text/javascript" src="../anpi/js/anp170.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp170">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="anp170AnpSid" />
    <html:hidden property="anp170GrpSid" />
    <html:hidden property="anp170NowPage" />
    <html:hidden property="anp170DspPage1" />
    <html:hidden property="anp170DspPage2" />
    <html:hidden property="anp170SortKeyIndex" />
    <html:hidden property="anp170OrderKey" />

    <div class="pageTitle">
      <img class="header_pluginImg-classic" src="../anpi/images/classic/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
      <img class="header_pluginImg" src="../anpi/images/original/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
      <gsmsg:write key="anp.plugin" />
      <span class="pageTitle_subFont">
        <gsmsg:write key="anp.anp140.01" />
        <gsmsg:write key="anp.anp140.02" />
      </span>
    </div>

    <div class="wrapper">

      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <!-- コンテンツ -->
      <table class="table-left">
        <tr>
          <th class="w25">
            <gsmsg:write key="cmn.group" />
          </th>
          <td class="w75">
            <bean:write name="anp170Form" property="anp170GrpName" />
          </td>
        </tr>
        <tr>
          <!-- 送信先 -->
          <th class="w25 no_w">
            <gsmsg:write key="anp.anp140.02" />
          </th>

          <td class="w75">
            <!-- ページング -->
            <bean:size id="pageCount" name="anp170Form" property="anp170PageLabel" scope="request" />
            <logic:greaterThan name="pageCount" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('anp170pageLast');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select styleClass="paging_combo" property="anp170DspPage1" onchange="changePage(this);">
                  <html:optionsCollection name="anp170Form" property="anp170PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('anp170pageNext');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>

            <table class="table-top">
              <!-- ヘッダ部分 -->
              <tr>
                <bean:define id="sortKeyIndex" name="anp170Form" property="anp170SortKeyIndex" />
                <bean:define id="orderKey" name="anp170Form" property="anp170OrderKey" />
                <gsmsg:define id="h_send" msgkey="anp.send.dest" />
                <gsmsg:define id="h_mail" msgkey="cmn.mailaddress" />
                <gsmsg:define id="h_ans" msgkey="anp.date.ans" />
                <gsmsg:define id="h_state" msgkey="anp.state" />
                <% int iSortKeyIndex = ((Integer) sortKeyIndex).intValue();   %>
                <% int iOrderKey = ((Integer) orderKey).intValue();     %>
                <% String[] colTitle = new String[] {h_send, h_mail, h_ans, h_state}; %>
                <% String[] colWidth = new String[] {"w20", "w35", "w20", "w15"}; %>
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
              <logic:notEmpty name="anp170Form" property="anp170JyokyoList">
                <logic:iterate id="Jyookyo" name="anp170Form" property="anp170JyokyoList" indexId="idx">
                  <tr>
                    <!-- 送信者 -->
                    <td class="w20">
                      <logic:equal name="Jyookyo" property="anp170JyotaiKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                        <bean:write name="Jyookyo" property="anp170NameTo" />
                      </logic:equal>
                      <logic:notEqual name="Jyookyo" property="anp170JyotaiKbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                        <del>
                          <bean:write name="Jyookyo" property="anp170NameTo" />
                        </del>
                      </logic:notEqual>
                    </td>
                    <!-- 連絡先 -->
                    <td class="txt_l w35">
                      <bean:write name="Jyookyo" property="anp170MailAddress" />
                    </td>
                    <!-- 返信日時 -->
                    <td class="txt_c w20">
                      <bean:write name="Jyookyo" property="anp170HensinDate" />
                    </td>
                    <!-- 状態 -->
                    <td class="txt_c w10">
                      <logic:equal name="Jyookyo" property="anp170Jyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_UNSET) %>">
                        -
                      </logic:equal>
                      <logic:equal name="Jyookyo" property="anp170Jyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_GOOD) %>">
                        <span class="cl_fontSafe">
                          <gsmsg:write key="anp.jokyo.good" />
                        </span>
                      </logic:equal>
                      <logic:equal name="Jyookyo" property="anp170Jyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_KEISYO) %>">
                        <span class="cl_fontWeekWarn">
                          <gsmsg:write key="anp.jokyo.keisyo" />
                        </span>
                      </logic:equal>
                      <logic:equal name="Jyookyo" property="anp170Jyokyo" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_JUSYO) %>">
                        <span class="cl_fontWarn">
                          <gsmsg:write key="anp.jokyo.jusyo" />
                        </span>
                      </logic:equal>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </table>

            <!-- ページング -->
            <bean:size id="pageCount" name="anp170Form" property="anp170PageLabel" scope="request" />
            <logic:greaterThan name="pageCount" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('anp170pageLast');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select styleClass="paging_combo" property="anp170DspPage2" onchange="changePage(this);">
                  <html:optionsCollection name="anp170Form" property="anp170PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('anp170pageNext');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </td>
        </tr>
      </table>
    </div>

    <!-- 閉じるボタン -->
    <div class="txt_c">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.close();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
        <gsmsg:write key="cmn.close" />
      </button>
    </div>

  </html:form>
</body>
</html:html>