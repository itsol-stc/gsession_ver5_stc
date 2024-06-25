<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
  String maxLengthCmt = String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.MAX_LENGTH_CMT);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="bmk.bmk030.06" /></title>

<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>

<link rel=stylesheet href="../bookmark/css/bookmark.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/freeze.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../bookmark/js/bmk030.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body onload="showLengthId($('#inputstr')[0], <%=maxLengthCmt%>, 'inputlength');">

  <div id="FreezePane">

    <html:form action="/bookmark/bmk030">

      <input type="hidden" name="CMD" value="">

      <%@ include file="/WEB-INF/plugin/bookmark/jsp/bmk010_hiddenParams.jsp"%>

      <html:hidden property="bmk020url" />
      <html:hidden property="bmk030modeName" />
      <html:hidden property="bmk030InitFlg" />

      <html:hidden property="bmk070ReturnPage" />
      <html:hidden property="bmk070Page" />
      <html:hidden property="bmk070PageTop" />
      <html:hidden property="bmk070PageBottom" />
      <html:hidden property="bmk070OrderKey" />
      <html:hidden property="bmk070SortKey" />

      <html:hidden property="bmk080Page" />
      <html:hidden property="bmk080PageTop" />
      <html:hidden property="bmk080PageBottom" />
      <input type="hidden" name="helpPrm" value="<bean:write name="bmk030Form" property="procMode" />">

      <bean:define id="pMode" name="bmk030Form" property="procMode" type="java.lang.Integer" />
      <%
        if (pMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_MODE_EDIT) {
      %>
      <html:hidden property="bmk030mode" />
      <html:hidden property="bmk030groupSid" />
      <%
        }
      %>

      <bean:define id="bMode" name="bmk030Form" property="bmk030mode" type="java.lang.Integer" />
      <%
        if (bMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU) {
      %>
      <html:hidden property="bmk030public" />
      <html:hidden property="bmk030main" />
      <html:hidden property="bmk030groupSid" />
      <%
        } else if (bMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP) {
      %>
      <html:hidden property="bmk030main" />
      <%
        } else if (bMode.intValue() == jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN) {
      %>
      <html:hidden property="bmk030groupSid" />
      <%
        }
      %>

      <logic:notEmpty name="bmk030Form" property="bmk010delInfSid" scope="request">
        <logic:iterate id="item" name="bmk030Form" property="bmk010delInfSid" scope="request">
          <input type="hidden" name="bmk010delInfSid" value="<bean:write name="item"/>">
        </logic:iterate>
      </logic:notEmpty>

      <html:hidden property="bmk150PageNum" />
      <html:hidden property="bmk070ToBmk150DspFlg" />

      <span id="bmk040labelArea"> </span>

      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

      <div class="pageTitle w80 mrl_auto">
        <ul>
          <li>
            <img class="header_pluginImg-classic" src="../bookmark/images/classic/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />">
            <img class="header_pluginImg" src="../bookmark/images/original/header_bookmark.png" alt="<gsmsg:write key="bmk.43" />"
          </li>
          </li>
          <li>
            <gsmsg:write key="bmk.43" />
          </li>
          <li class="pageTitle_subFont">
            <gsmsg:write key="cmn.entry" />
          </li>
          <li>
            <div>
              <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('bmk030pushOk');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
                <gsmsg:write key="cmn.ok" />
              </button>
              <logic:equal name="bmk030Form" property="procMode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_MODE_EDIT)%>'>
                <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="return buttonPush('bmk030pushDelete');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:equal>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk030pushBack');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </div>
          </li>
        </ul>
      </div>
      <div class="wrapper w80 mrl_auto">

        <!-- メッセージ -->
        <logic:messagesPresent message="false">
          <html:errors />
        </logic:messagesPresent>

        <table class="table-left" border="0" cellspacing="0" cellpadding="5">

          <!-- 登録先 -->
          <tr>
            <th class="txt_l no_w w25" scope="row">
              <gsmsg:write key="bmk.16" />
            </th>

            <!-- 登録モードのとき -->
            <logic:equal name="bmk030Form" property="procMode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_MODE_TOUROKU)%>'>
              <td class="w75">
                <!-- 個人ブックマークラジオ -->
                <span class="verAlignMid">
                  <html:radio name="bmk030Form" styleId="bmk030mode_00" property="bmk030mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>" onclick="buttonPush('init');" />
                  <label for="bmk030mode_00">
                    <gsmsg:write key="bmk.30" />
                  </label>
                </span>
                <br>
                <!-- グループブックマークラジオ -->
                <span class="verAlignMid">
                  <html:radio name="bmk030Form" styleId="bmk030mode_01" property="bmk030mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>" onclick="buttonPush('init');" />
                  <label for="bmk030mode_01">
                    <gsmsg:write key="bmk.51" />
                  </label>
                <!-- グループコンボ -->
                <%
                    boolean bmkFlg = true;
                  %>
                <logic:equal name="bmk030Form" property="bmk030mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>'>
                  <%
                      bmkFlg = false;
                    %>
                </logic:equal>
                <logic:notEmpty name="bmk030Form" property="bmk030groupCmbList">
                  <html:select name="bmk030Form" property="bmk030groupSid" disabled="<%=bmkFlg%>" styleClass="ml10" onchange="buttonPush('init');">
                    <html:optionsCollection name="bmk030Form" property="bmk030groupCmbList" value="value" label="label" />
                  </html:select>
                </logic:notEmpty>
                </span>
                <br>
                <!-- 共有ブックマークラジオ -->
                <span class="verAlignMid">
                  <html:radio name="bmk030Form" styleId="bmk030mode_02" property="bmk030mode" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>" onclick="buttonPush('init');" />
                  <label for="bmk030mode_02">
                    <gsmsg:write key="bmk.34" />
                  </label>
                </span>
              </td>
            </logic:equal>

            <!-- 編集モードのとき -->
            <logic:equal name="bmk030Form" property="procMode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_MODE_EDIT)%>'>
              <td>
                <bean:write name="bmk030Form" property="bmk030modeName" />
              </td>
            </logic:equal>

          </tr>

          <!-- ＵＲＬ -->
          <tr>
            <th class="txt_l w25" scope="row">URL</th>
            <td class="w75">
              <logic:notEmpty name="bmk030Form" property="bmk030UrlDsp">
                <logic:iterate id="urlDsp" name="bmk030Form" property="bmk030UrlDsp" indexId="idx2">
                  <%
                      if (idx2 > 0) {
                    %>
                  <br>
                  <%
                      }
                    %>
                  <bean:write name="urlDsp" />
                </logic:iterate>
              </logic:notEmpty>
            </td>
          </tr>

          <!-- タイトル -->
          <tr>
            <th class="txt_l w25" scope="row">
              <gsmsg:write key="cmn.title" />
              <span class="cl_fontWarn">
                <gsmsg:write key="cmn.comments" />
              </span>
            </th>
            <td class="w75">
              <html:text maxlength="150" property="bmk030title" styleClass="wp500" />
            </td>
          </tr>

          <!-- ラベル -->
          <tr>
            <th class="txt_l w25" scope="row">
              <gsmsg:write key="cmn.label" />
            </th>
            <td class="w75">
              <div>
                <html:text maxlength="1000" property="bmk030label" styleClass="wp500" />
                <!-- ラベル選択ボタン -->
                <button type="button" value="<gsmsg:write key="cmn.select" />" class="baseBtn no_w" onClick="openlabel(<bean:write name="bmk030Form" property="procMode" />);" tabindex="47">
                  <gsmsg:write key="cmn.select" />
                </button>
              </div>
              <!-- ラベルの説明 -->
              <div>
                <span>
                  <gsmsg:write key="bmk.bmk030.02" />
                  <br>
                  <span>
                    <gsmsg:write key="bmk.bmk030.04" />
                  </span>
                </span>
              </div>
            </td>
          </tr>

          <!-- コメント -->
          <tr>
            <th class="txt_l w25" scope="row">
              <gsmsg:write key="cmn.comment" />
            </th>
            <td class="w75">
              <textarea wrap="soft" name="bmk030cmt" class="wp500" rows="3" onkeyup="showLengthStr(value, <%=maxLengthCmt%>, 'inputlength');" id="inputstr"><bean:write name="bmk030Form" property="bmk030cmt" /></textarea>
              <br>
              <span class="formCounter">
                <gsmsg:write key="cmn.current.characters" />
                :
              </span>
              <span id="inputlength" class="formCounter">0</span>
              <span class="formCounter_max">
                /<%=maxLengthCmt%>
                <gsmsg:write key="cmn.character" />
              </span>
            </td>
          </tr>

          <!-- 評価 -->
          <tr>
            <th class="txt_l w25" scope="row">
              <gsmsg:write key="bmk.bmk030kn.01" />
            </th>
            <td class="w75">
              <logic:notEmpty name="bmk030Form" property="bmk030scoreCmbList">
                <html:select name="bmk030Form" property="bmk030score">
                  <html:optionsCollection name="bmk030Form" property="bmk030scoreCmbList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </td>
          </tr>

          <!-- 公開区分 -->
          <logic:notEqual name="bmk030Form" property="bmk030mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KYOYU)%>'>
            <tr>
              <th class="txt_l w25" scope="row">
                <gsmsg:write key="cmn.public" />
              </th>
              <td class="w75">
                <span class="verAlignMid">
                  <html:radio name="bmk030Form" styleId="bmk030public_01" property="bmk030public" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_YES)%>" />
                  <label for="bmk030public_01">
                    <gsmsg:write key="cmn.public" />
                  </label>
                  <html:radio name="bmk030Form" styleId="bmk030public_00" styleClass="ml10" property="bmk030public" value="<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.KOKAI_NO)%>" />
                  <logic:equal name="bmk030Form" property="bmk030mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>'>
                    <label for="bmk030public_00">
                      <gsmsg:write key="cmn.private" />
                    </label>
                  </logic:equal>
                  <logic:equal name="bmk030Form" property="bmk030mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_GROUP)%>'>
                    <label for="bmk030public_00">
                      <gsmsg:write key="bmk.25" />
                    </label>
                  </logic:equal>
              </td>
            </tr>
          </logic:notEqual>

          <!-- メイン表示区分 -->
          <logic:equal name="bmk030Form" property="bmk030mode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_KBN_KOJIN)%>'>
            <tr>
              <th class="txt_l w25" scope="row">
                <gsmsg:write key="cmn.main.view" />
              </th>
              <td class="w75">
                <span class="verAlignMid">
                  <html:radio name="bmk030Form" styleId="bmk030main_01" property="bmk030main" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.DSP_YES) %>" />
                  <label for="bmk030main_01">
                    <gsmsg:write key="cmn.show" />
                  </label>
                  <html:radio name="bmk030Form" styleId="bmk030main_00" styleClass="ml10" property="bmk030main" value="<%= String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.DSP_NO) %>" />
                  <label for="bmk030main_00">
                    <gsmsg:write key="cmn.hide" />
                  </label>
                </span>
              </td>
            </tr>
          </logic:equal>
        </table>
        <div class="footerBtn_block">
          <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="return buttonPush('bmk030pushOk');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
            <gsmsg:write key="cmn.ok" />
          </button>
          <logic:equal name="bmk030Form" property="procMode" value='<%=String.valueOf(jp.groupsession.v2.bmk.GSConstBookmark.BMK_MODE_EDIT)%>'>
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="return buttonPush('bmk030pushDelete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="return buttonPush('bmk030pushBack');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </div>
      </div>

    </html:form>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </div>

  <div id="labelPanel" class="display_n txt_c p0">
    <div class="bd">
      <iframe src="../common/html/damy.html" name="lab" class="m0 p0 w100 h100" frameborder="no"></iframe>
    </div>
  </div>

</body>
</html:html>