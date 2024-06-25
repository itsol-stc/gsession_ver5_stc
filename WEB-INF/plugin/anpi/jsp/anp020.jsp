<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.anp.GSConstAnpi"%>
<!DOCTYPE html>
<% String maxLengthComment = String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.MAXLENGTH_REPLYCOMMENT); %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="anp.anp020.01" /></title>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp020.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../anpi/css/anpi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<bean:define id="dspChangeScript" value="" />
<logic:equal value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>" name="anp020Form" property="anp020usrJkbn">
  <logic:equal value="1" name="anp020Form" property="anp020usrUkoFlg">
    <bean:define id="dspChangeScript">dspChangeUsrUko();</bean:define>
  </logic:equal>
</logic:equal>
<logic:notEqual value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>" name="anp020Form" property="anp020usrJkbn">
  <bean:define id="dspChangeScript">dspChangeUsrUko();</bean:define>
</logic:notEqual>
<body onload="showLengthId($('#inputstr1')[0],<%=maxLengthComment %>,'inputlength1'); <%=dspChangeScript%>">
  <html:form action="/anpi/anp020">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="anpiSid" />
    <html:hidden property="userSid" />
    <html:hidden property="rmode" />
    <html:hidden property="anp010SelectGroupSid" />
    <html:hidden property="anp010NowPage" />
    <html:hidden property="anp010SortKeyIndex" />
    <html:hidden property="anp010OrderKey" />
    <html:hidden property="anp010KnrenFlg" />
    <html:hidden property="anp020EmployeeNo" />
    <html:hidden property="anp020Name" />
    <html:hidden property="anp020Kana" />
    <html:hidden property="anp020HaisinDate" />
    <html:hidden property="anp020ReplyDate" />
    <html:hidden property="anp020UrgentDspFlg" />
    <html:hidden property="anp020UrgentMail" />
    <html:hidden property="anp020UrgentTelNo" />
    <html:hidden property="anp020PhotoFileSid" />
    <html:hidden property="anp020PhotoDspFlg" />
    <html:hidden property="anp020usrUkoFlg" />
    <html:hidden property="anp010SearchKbn" />
    <html:hidden property="anp010SearchSndKbn" />
    <html:hidden property="anp010SearchAnsKbn" />
    <html:hidden property="anp010SearchAnpKbn" />
    <html:hidden property="anp010SearchPlcKbn" />
    <html:hidden property="anp010SearchSyuKbn" />
    <html:hidden property="anp010SvSearchSndKbn" />
    <html:hidden property="anp010SvSearchAnsKbn" />
    <html:hidden property="anp010SvSearchAnpKbn" />
    <html:hidden property="anp010SvSearchPlcKbn" />
    <html:hidden property="anp010SvSearchSyuKbn" />
    <!-- ヘッダー -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../anpi/images/classic/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
          <img class="header_pluginImg" src="../anpi/images/original/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
        </li>
        <li>
          <gsmsg:write key="anp.plugin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="anp.anp020.02" />
        </li>
        <!-- ボタン -->
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp020excute');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:notEqual name="anp020Form" property="rmode" value="<%=String.valueOf(GSConstAnpi.REMOTE_MODE)%>">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp020back');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:notEqual>
          </div>
        </li>
      </ul>
    </div>
    <!-- コンテンツ -->
    <div class="wrapper w80 mrl_auto">
      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>
      <!-- 訓練モード バー -->
      <logic:equal name="anp020Form" property="anp010KnrenFlg" value="1">
        <div class="anp_kunren">
          <gsmsg:write key="anp.knmode" />
        </div>
      </logic:equal>
      <table class="w100">
        <tr>
          <logic:notEqual name="anp020Form" property="rmode" value="<%=String.valueOf(GSConstAnpi.REMOTE_MODE)%>">
            <!-- ユーザ画像 -->
            <td class="wp150 txt_t">
              <div class="mt10">
                  <logic:equal name="anp020Form" property="anp020PhotoDspFlg" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE)%>">
                    <div class="txt_c hikokai_text hikokai_photo-l userImg-hikokai">
                      <span class="cl_fontWarn ">
                        <gsmsg:write key="cmn.private" />
                      </span>
                    </div>
                  </logic:equal>
                  <logic:equal name="anp020Form" property="anp020PhotoDspFlg" value="<%=String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN)%>">
                    <logic:equal name="anp020Form" property="anp020PhotoFileSid" value="0">
                      <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp130 hp150" alt="<gsmsg:write key="cmn.photo" />">
                      <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp130 hp150" alt="<gsmsg:write key="cmn.photo" />">
                    </logic:equal>
                    <logic:notEqual name="anp020Form" property="anp020PhotoFileSid" value="0">
                      <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="anp020Form" property="anp020PhotoFileSid" />" name="pictImage" alt="<gsmsg:write key="cmn.photo" />" class="wp130">
                    </logic:notEqual>
                  </logic:equal>
              </div>
            </td>
          </logic:notEqual>
          <td>
            <table id="js_usrInfoTable" class="table-left w100">
              <!-- 社員/職員番号 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="cmn.employee.staff.number" />
                </th>
                <td class="w75">
                  <bean:write name="anp020Form" property="anp020EmployeeNo" />
                </td>
              </tr>
              <!-- 氏名 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="cmn.name" />
                </th>
                <td class="w75 js_td_type">
                  <bean:write name="anp020Form" property="anp020Name" />
                </td>
              </tr>
              <!-- 氏名(カナ) -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="user.119" />
                </th>
                <td class="w75">
                  <bean:write name="anp020Form" property="anp020Kana" />
                </td>
              </tr>
              <!-- 送信日時 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="anp.date.send" />
                </th>
                <td class="w75">
                  <bean:write name="anp020Form" property="anp020HaisinDate" />
                </td>
              </tr>
              <!-- 回答日時 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="anp.date.ans" />
                </th>
                <td class="w75">
                  <bean:write name="anp020Form" property="anp020ReplyDate" />
                </td>
              </tr>
              <!-- 安否状況 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="anp.jokyo" />
                </th>
                <td class="w75">
                  <span class="verAlignMid">
                    <html:radio styleId="jokyoFlg1" name="anp020Form" property="anp020JokyoFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_UNSET)%>" />
                    <label for="jokyoFlg1">
                      <gsmsg:write key="cmn.notset" />
                    </label>
                    <html:radio styleClass="ml10" styleId="jokyoFlg2" name="anp020Form" property="anp020JokyoFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_GOOD)%>" />
                    <label for="jokyoFlg2">
                      <gsmsg:write key="anp.jokyo.good" />
                    </label>
                    <html:radio styleClass="ml10" styleId="jokyoFlg3" name="anp020Form" property="anp020JokyoFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_KEISYO)%>" />
                    <label for="jokyoFlg3">
                      <gsmsg:write key="anp.jokyo.keisyo" />
                    </label>
                    <html:radio styleClass="ml10" styleId="jokyoFlg4" name="anp020Form" property="anp020JokyoFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_JUSYO)%>" />
                    <label for="jokyoFlg4">
                      <gsmsg:write key="anp.jokyo.jusyo" />
                    </label>
                  </span>
                </td>
              </tr>
              <!-- 現在地 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="anp.place" />
                </th>
                <td class="w75">
                  <span class="verAlignMid">
                    <html:radio styleId="placeFlg1" name="anp020Form" property="anp020PlaceFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_UNSET)%>" />
                    <label for="placeFlg1">
                      <gsmsg:write key="cmn.notset" />
                    </label>
                    <html:radio styleClass="ml10" styleId="placeFlg2" name="anp020Form" property="anp020PlaceFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_HOUSE)%>" />
                    <label for="placeFlg2">
                      <gsmsg:write key="anp.place.house" />
                    </label>
                    <html:radio styleClass="ml10" styleId="placeFlg3" name="anp020Form" property="anp020PlaceFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_OFFICE)%>" />
                    <label for="placeFlg3">
                      <gsmsg:write key="anp.place.office" />
                    </label>
                    <html:radio styleClass="ml10" styleId="placeFlg4" name="anp020Form" property="anp020PlaceFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_OUT)%>" />
                    <label for="placeFlg4">
                      <gsmsg:write key="anp.place.out" />
                    </label>
                  </span>
                </td>
              </tr>
              <!-- 出社状況 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="anp.syusya.state" />
                </th>
                <td class="w75">
                  <span class="verAlignMid">
                    <html:radio styleId="syusyaFlg1" name="anp020Form" property="anp020SyusyaFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_UNSET)%>" />
                    <label for="syusyaFlg1">
                      <gsmsg:write key="cmn.notset" />
                    </label>
                    <html:radio styleClass="ml10" styleId="syusyaFlg2" name="anp020Form" property="anp020SyusyaFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_NO)%>" />
                    <label for="syusyaFlg2">
                      <gsmsg:write key="anp.syusya.no" />
                    </label>
                    <html:radio styleClass="ml10" styleId="syusyaFlg3" name="anp020Form" property="anp020SyusyaFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_OK)%>" />
                    <label for="syusyaFlg3">
                      <gsmsg:write key="anp.syusya.ok" />
                    </label>
                    <html:radio styleClass="ml10" styleId="syusyaFlg4" name="anp020Form" property="anp020SyusyaFlg" value="<%=String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_OKD)%>" />
                    <label for="syusyaFlg4">
                      <gsmsg:write key="anp.syusya.okd" />
                    </label>
                  </span>
                </td>
              </tr>
              <!-- 回答日時 -->
              <tr>
                <th class="w25">
                  <gsmsg:write key="anp.comment" />
                </th>
                <td class="w75">
                  <% String onkey1 = "showLengthStr(value," + maxLengthComment + ",'inputlength1');"; %>
                  <div>
                    <html:textarea styleId="inputstr1" name="anp020Form" property="anp020Comment" rows="3" styleClass="wp300" onkeyup="<%=onkey1%>" />
                  </div>
                  <span class="formCounter">
                    <gsmsg:write key="cmn.current.characters" />:
                  </span>
                  <span id="inputlength1" class="formCounter">0</span>
                  <span class="formCounter">/&nbsp;<%=maxLengthComment%>&nbsp;
                    <gsmsg:write key="cmn.character" />
                  </span>
                </td>
              </tr>
              <!-- 緊急連絡先 -->
              <logic:equal name="anp020Form" property="anp020UrgentDspFlg" value="1">
                <tr>
                  <th class="w25">
                    <gsmsg:write key="anp.kinkyu" />
                  </th>
                  <td class="w75">
                    <div>
                      <gsmsg:write key="cmn.mailaddress" />：<bean:write name="anp020Form" property="anp020UrgentMail" />
                    </div>
                    <div>
                      <gsmsg:write key="cmn.tel" />：<bean:write name="anp020Form" property="anp020UrgentTelNo" />
                    </div>
                    <div class="mt10">
                      <span class="cl_fontWarn">
                        <gsmsg:write key="anp.anp020.03" />
                      </span>
                    </div>
                  </td>
                </tr>
              </logic:equal>
            </table>
          </td>
        </tr>
      </table>

      <!-- ボタン -->
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('anp020excute');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:notEqual name="anp020Form" property="rmode" value="<%=String.valueOf(GSConstAnpi.REMOTE_MODE)%>">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('anp020back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:notEqual>
      </div>
    </div>
  </html:form>
  <!-- フッター -->
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>