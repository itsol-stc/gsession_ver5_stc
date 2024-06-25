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
<title>GROUPSESSION <gsmsg:write key="anp.anp010.01" /></title>
<script type="text/javascript" src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"> </script>
<script type="text/javascript" src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../anpi/js/anp010.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../anpi/css/anpi.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/anpi/anp010">
    <!-- BODY -->
    <input type="hidden" name="CMD">
    <html:hidden property="backScreen" />
    <html:hidden property="anpiSid" />
    <html:hidden property="userSid" />
    <html:hidden property="anp010NowPage" />
    <html:hidden property="anp010SortKeyIndex" />
    <html:hidden property="anp010OrderKey" />
    <html:hidden property="anp010SearchKbn" />
    <html:hidden property="anp010SvSearchSndKbn" />
    <html:hidden property="anp010SvSearchAnsKbn" />
    <html:hidden property="anp010SvSearchAnpKbn" />
    <html:hidden property="anp010SvSearchPlcKbn" />
    <html:hidden property="anp010SvSearchSyuKbn" />
    <!-- ヘッダー -->
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../anpi/images/classic/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
          <img class="header_pluginImg" src="../anpi/images/original/header_anpi.png" alt="<gsmsg:write key="anp.plugin" />">
        </li>
        <li>
          <gsmsg:write key="anp.plugin" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="anp.anp010.01" />
        </li>
        <!-- ボタン -->
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('reload');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
              <gsmsg:write key="cmn.reload" />
            </button>
            <logic:equal name="anp010Form" property="anp010KanriFlg" value="1">
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('anp010haisin');">
                <img class="btn_classicImg-display" src="../anpi/images/classic/icon_okmail.gif" alt="<gsmsg:write key="anp.send.new" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_mail2.png" alt="<gsmsg:write key="anp.send.new" />">
                <gsmsg:write key="anp.send.new" />
              </button>
            </logic:equal>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">
      <!-- 進行中データなし -->
      <logic:empty name="anp010Form" property="anpiSid" scope="request">
        <div class="txt_l fs_15">
          <gsmsg:write key="anp.anp010.02" />
        </div>
      </logic:empty>
      <!-- 訓練モード バー -->
      <logic:equal name="anp010Form" property="anp010KnrenFlg" value="1">
        <div class="anp_kunren">
          <gsmsg:write key="anp.knmode" />
        </div>
      </logic:equal>
      <logic:notEmpty name="anp010Form" property="anpiSid" scope="request">
        <!-- ヘッダー下のボタン -->
        <div class="wrapperContent-top txt_r mt10">
          <button type="button" value="<gsmsg:write key="cmn.advanced.search" />" class="baseBtn" onClick="dispSearch();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_advanced_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
            <gsmsg:write key="cmn.advanced.search" />
          </button>
          <logic:equal name="anp010Form" property="anp010KanriFlg" value="1">
            <logic:equal name="anp010Form" property="anp010AllDeleteFlg" value="0">
              <button type="button" value="<gsmsg:write key="anp.send.re" />" class="baseBtn" onClick="buttonPush('anp010saiso');">
                <img class="btn_classicImg-display" src="../anpi/images/classic/icon_remail.png" alt="<gsmsg:write key="anp.send.re" />">
                <img class="btn_originalImg-display" src="../anpi/images/original/icon_resend.png" alt="<gsmsg:write key="anp.send.re" />">
                <gsmsg:write key="anp.send.re" />
              </button>
            </logic:equal>
            <button type="button" value="<gsmsg:write key="cmn.advanced.search" />" class="baseBtn" onClick="buttonPush('anp010finish');">
              <gsmsg:write key="anp.end" />
            </button>
          </logic:equal>
        </div>
        <table id="haisin" class="w100">
          <tr>
            <!-- 左側コンテンツ -->
            <td class="wp250 pr5 txt_t">
              <!-- 配信内容 -->
              <div class="txt_r">
                <button type="button" class="baseBtn m0" value="<gsmsg:write key="anp.anp010.10"/>" onClick="buttonPush('anp010InfoConf');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_syorui.gif" alt="<gsmsg:write key="anp.anp010.10" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png" alt="<gsmsg:write key="anp.anp010.10" />">
                  <gsmsg:write key="anp.anp010.10" />
                </button>
              </div>
              <!-- あなたの回答状況 -->
              <logic:equal name="anp010Form" property="anp010SendFlg" value="true">
                <table class="table-left">
                  <tr>
                    <th colspan="3" class="bgC_header1 table_title-color txt_c">
                      <gsmsg:write key="anp.anp010.03" />
                    </th>
                  </tr>
                  <!-- まだ回答していません -->
                  <logic:equal name="anp010Form" property="anp010AnsKbn" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.ANP_ANS_NO) %>">
                    <tr class="bgC_tableCell">
                      <td colspan="3">
                        <div class="txt_c">
                          <gsmsg:write key="anp.anp010.04" />
                        </div>
                        <div class="txt_c">
                          <button type="button" class="baseBtn" value="<gsmsg:write key="anp.answer"/>" onclick="selectSyosai(<bean:write name="anp010Form" property="anp010SessionUserInfo.usrSid" />);">
                            <gsmsg:write key="anp.answer" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  </logic:equal>
                  <!-- 回答済みです -->
                  <logic:equal name="anp010Form" property="anp010AnsKbn" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.ANP_ANS_YES) %>">
                    <tr>
                      <td colspan="3">
                        <div class="txt_c">
                          <gsmsg:write key="anp.anp010.05" />
                        </div>
                        <div class="txt_c">
                          <bean:write name="anp010Form" property="anp010SessionUserInfo.replyDate" />
                        </div>
                        <div class="txt_c">
                          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit"/>" onclick="selectSyosai(<bean:write name="anp010Form" property="anp010SessionUserInfo.usrSid" />);">
                            <gsmsg:write key="cmn.edit" />
                          </button>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <th class="w30">
                        <gsmsg:write key="anp.jokyo" />
                      </th>
                      <td colspan="2" class="w70">
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_UNSET) %>">
                          <gsmsg:write key="cmn.notset" />
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_GOOD) %>">
                          <span class="cl_fontSafe">
                            <gsmsg:write key="anp.jokyo.good" />
                          </span>
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_KEISYO) %>">
                          <span class="cl_fontWeekWarn">
                            <gsmsg:write key="anp.jokyo.keisyo" />
                          </span>
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_JUSYO) %>">
                          <span class="cl_fontWarn">
                            <gsmsg:write key="anp.jokyo.jusyo" />
                          </span>
                        </logic:equal>
                      </td>
                    </tr>
                    <tr>
                      <th class="w30">
                        <gsmsg:write key="anp.place" />
                      </th>
                      <td colspan="2" class="w70">
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_UNSET) %>">
                          <gsmsg:write key="cmn.notset" />
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_HOUSE) %>">
                          <gsmsg:write key="anp.place.house" />
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_OFFICE) %>">
                          <gsmsg:write key="anp.place.office" />
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_OUT) %>">
                          <gsmsg:write key="anp.place.out" />
                        </logic:equal>
                      </td>
                    </tr>
                    <tr>
                      <th class="w30">
                        <gsmsg:write key="anp.syusya.state" />
                      </th>
                      <td colspan="2" class="w70">
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_UNSET) %>">
                          <gsmsg:write key="cmn.notset" />
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_NO) %>">
                          <span class="cl_fontWarn">
                            <gsmsg:write key="anp.syusya.no" />
                          </span>
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_OK) %>">
                          <gsmsg:write key="anp.syusya.ok" />
                        </logic:equal>
                        <logic:equal name="anp010Form" property="anp010SessionUserInfo.syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_OKD) %>">
                          <gsmsg:write key="anp.syusya.okd" />
                        </logic:equal>
                      </td>
                    </tr>
                    <tr>
                      <th class="w30">
                        <gsmsg:write key="anp.comment" />
                      </th>
                      <td colspan="2" class="w70">
                        <bean:write name="anp010Form" property="anp010SessionUserInfo.comment" filter="false" />
                      </td>
                    </tr>
                  </logic:equal>
                </table>
              </logic:equal>
              <!-- 現在の状況 -->
              <logic:notEmpty name="anp010Form" property="anp010State">
                <bean:define id="anpState" name="anp010Form" property="anp010State" />
                <table class="table-left no_w">
                  <tr>
                    <th colspan="3" class="bgC_header1 table_title-color txt_c">
                      <gsmsg:write key="anp.anp010.06" />
                    </th>
                  </tr>
                  <tr>
                    <th class="w30">
                      <gsmsg:write key="anp.date.send" />
                    </th>
                    <td colspan="2" class="w70">
                      <bean:write name="anpState" property="haisinDate" />
                    </td>
                  </tr>
                  <tr>
                    <th class="w30">
                      <gsmsg:write key="anp.date.resend" />
                    </th>
                    <td colspan="2" class="w70">
                      <bean:write name="anpState" property="resendDate" />
                    </td>
                  </tr>
                  <tr>
                    <th class="w30">
                      <gsmsg:write key="anp.date.end" />
                    </th>
                    <td colspan="2" class="w70">
                      <bean:write name="anpState" property="lastDate" />
                    </td>
                  </tr>
                  <tr>
                    <th class="w30">
                      <gsmsg:write key="anp.ans.state" />
                    </th>
                    <td colspan="2" class="w70">
                      <bean:write name="anpState" property="replyState" />
                    </td>
                  </tr>
                  <tr>
                    <th rowspan="3" class="w30 txt_c">
                      <gsmsg:write key="anp.state" />
                    </th>
                    <th class="fw_n">
                      <gsmsg:write key="anp.jokyo.good" />
                    </th>
                    <td class="w20 txt_r">
                      <bean:write name="anpState" property="jokyoGood" />
                    </td>
                  </tr>
                  <tr>
                    <th class="w30 fw_n">
                      <gsmsg:write key="anp.jokyo.keisyo" />
                    </th>
                    <td class="w20 txt_r">
                      <bean:write name="anpState" property="jokyoKeisyo" />
                    </td>
                  </tr>
                  <tr>
                    <th class="w30 fw_n">
                      <gsmsg:write key="anp.jokyo.jusyo" />
                    </th>
                    <td class="w20 txt_r">
                      <bean:write name="anpState" property="jokyoJusyo" />
                    </td>
                  </tr>
                  <tr>
                    <th rowspan="2" class="w30 txt_c">
                      <gsmsg:write key="anp.syusya" />
                    </th>
                    <th class="fw_n">
                      <gsmsg:write key="anp.syusya.ok2" />
                    </td>
                    <td class="w20 txt_r">
                      <bean:write name="anpState" property="syusyaOk" />
                    </td>
                  </tr>
                  <tr>
                    <th class="w30 fw_n">
                      <gsmsg:write key="anp.syusya.no" />
                    </th>
                    <td class="w20 txt_r">
                      <bean:write name="anpState" property="syusyaNo" />
                    </td>
                  </tr>
                </table>
              </logic:notEmpty>
              <!-- 安否確認管理者一覧 -->
              <logic:notEmpty name="anp010Form" property="anp010AdmUsrList">
                <table class="table-left">
                  <tr>
                    <th colspan="3" class="bgC_header1 table_title-color txt_c">
                      <gsmsg:write key="anp.anp080.13" />
                    </th>
                  </tr>
                  <tr>
                    <td class="txt_l">
                      <logic:iterate id="adminUsr" name="anp010Form" property="anp010AdmUsrList">
                        <div>
                          <bean:define id="mukoUserClass" value="" />
                          <logic:equal value="1" name="adminUsr" property="usrUkoFlg">
                            <bean:define id="mukoUserClass" value="mukoUser" />
                          </logic:equal>
                          <span class="<%= mukoUserClass %>">
                            <bean:write name="adminUsr" property="label" />
                          </span>
                        </div>
                      </logic:iterate>
                    </td>
                  </tr>
                </table>
              </logic:notEmpty>
              <div>
                <!-- アイコンの説明 -->
                <div class="txt_l btn_classicImg-display">
                  <img src="../anpi/images/classic/icon_okmail.gif" alt="<gsmsg:write key="anp.icon.ok"/>">：
                  <gsmsg:write key="anp.icon.ok" />
                </div>
                <div class="txt_l btn_originalImg-display">
                  <img src="../common/images/original/icon_mail2.png" alt="<gsmsg:write key="anp.icon.ok"/>">：
                  <gsmsg:write key="anp.icon.ok" />
                </div>
                <div class="txt_l btn_classicImg-display">
                  <img src="../anpi/images/classic/icon_waitmail.gif" alt="<gsmsg:write key="anp.icon.wait"/>">：
                  <gsmsg:write key="anp.icon.wait" />
                </div>
                <div class="txt_l btn_originalImg-display">
                  <img src="../anpi/images/original/icon_sending.png" alt="<gsmsg:write key="anp.icon.wait"/>">：
                  <gsmsg:write key="anp.icon.wait" />
                </div>
                <div class="txt_l btn_classicImg-display">
                  <img src="../anpi/images/classic/icon_nomail.gif" alt="<gsmsg:write key="anp.icon.error"/>">：
                  <gsmsg:write key="anp.icon.error" />
                </div>
                <div class="txt_l btn_originalImg-display">
                  <img src="../anpi/images/original/icon_senderr.png" alt="<gsmsg:write key="anp.icon.error"/>">：
                  <gsmsg:write key="anp.icon.error" />
                </div>
                <div class="txt_l btn_classicImg-display">
                  <img src="../anpi/images/classic/16_icon_edit_1.png" alt="<gsmsg:write key="anp.icon.answer"/>">：
                  <gsmsg:write key="anp.icon.answer" />
                </div>
                <div class="txt_l btn_originalImg-display">
                  <img src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="anp.icon.answer"/>">：
                  <gsmsg:write key="anp.icon.answer" />
                </div>
              </div>
            </td>

            <!-- 右側コンテンツ -->
            <td class="txt_t">
              <!-- 検索条件 -->
              <div id="js_top_Search" class="bgC_lightGray mt0 borC_light mb10">
                <div class="bor1 p5">
                  <table class="table-left">
                    <tr>
                      <th class="w15">
                        <gsmsg:write key="anp.send.state" />
                      </th>
                      <td class="w90">
                        <span class="verAlignMid">
                          <html:radio name="anp010Form" property="anp010SearchSndKbn" styleId="searchSndKbn0" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SENDKBN_ALL) %>" />
                          <label for="searchSndKbn0">
                            <gsmsg:write key="cmn.all" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchSndKbn" styleId="searchSndKbn1" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SENDKBN_NO) %>" />
                          <label for="searchSndKbn1">
                            <gsmsg:write key="anp.send.notyet" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchSndKbn" styleId="searchSndKbn2" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SENDKBN_OK) %>" />
                          <label for="searchSndKbn2">
                            <gsmsg:write key="anp.send.ok" />
                          </label>
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <th class="w10">
                        <gsmsg:write key="anp.ans.state" />
                      </th>
                      <td class="w90">
                        <span class="verAlignMid">
                          <html:radio name="anp010Form" property="anp010SearchAnsKbn" styleId="searchAnsKbn0" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANSKBN_ALL) %>" onclick="changeDispRadio();" />
                          <label for="searchAnsKbn0">
                            <gsmsg:write key="cmn.all" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchAnsKbn" styleId="searchAnsKbn1" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANSKBN_NO) %>" onclick="changeDispRadio();" />
                          <label for="searchAnsKbn1">
                            <gsmsg:write key="anp.ans.notyet" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchAnsKbn" styleId="searchAnsKbn2" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANSKBN_OK) %>" onclick="changeDispRadio();" />
                          <label for="searchAnsKbn2">
                            <gsmsg:write key="anp.ans.ok" />
                          </label>
                        </span>
                      </td>
                    </tr>
                    <tr class="js_top_Search_anp">
                      <th class="w10">
                        <gsmsg:write key="anp.jokyo" />
                      </th>
                      <td class="w90">
                        <span class="verAlignMid">
                          <html:radio name="anp010Form" property="anp010SearchAnpKbn" styleId="searchAnpKbn0" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANPKBN_ALL) %>" />
                          <label for="searchAnpKbn0">
                            <gsmsg:write key="cmn.all" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchAnpKbn" styleId="searchAnpKbn1" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANPKBN_GOOD) %>" />
                          <label for="searchAnpKbn1">
                            <gsmsg:write key="anp.jokyo.good" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchAnpKbn" styleId="searchAnpKbn2" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANPKBN_KEISYO) %>" />
                          <label for="searchAnpKbn2">
                            <gsmsg:write key="anp.jokyo.keisyo" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchAnpKbn" styleId="searchAnpKbn3" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_ANPKBN_JUSYO) %>" />
                          <label for="searchAnpKbn3">
                            <gsmsg:write key="anp.jokyo.jusyo" />
                          </label>
                        </span>
                      </td>
                    </tr>
                    <tr class="js_top_Search_plc">
                      <th class="w10">
                        <gsmsg:write key="anp.place" />
                      </th>
                      <td class="w90">
                        <span class="verAlignMid">
                          <html:radio name="anp010Form" property="anp010SearchPlcKbn" styleId="searchPlcKbn0" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_PLACEKBN_ALL) %>" />
                          <label for="searchPlcKbn0">
                            <gsmsg:write key="cmn.all" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchPlcKbn" styleId="searchPlcKbn1" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_PLACEKBN_HOUSE) %>" />
                          <label for="searchPlcKbn1">
                            <gsmsg:write key="anp.place.house" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchPlcKbn" styleId="searchPlcKbn2" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_PLACEKBN_OFFICE) %>" />
                          <label for="searchPlcKbn2">
                            <gsmsg:write key="anp.place.office" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchPlcKbn" styleId="searchPlcKbn3" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_PLACEKBN_OUT) %>" />
                          <label for="searchPlcKbn3">
                            <gsmsg:write key="anp.place.out" />
                          </label>
                        </span>
                      </td>
                    </tr>
                    <tr class="js_top_Search_syu">
                      <th class="w10">
                        <gsmsg:write key="anp.syusya.state" />
                      </th>
                      <td class="w90">
                        <span class="verAlignMid">
                          <html:radio name="anp010Form" property="anp010SearchSyuKbn" styleId="searchSyuKbn0" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SYUSYAKBN_ALL) %>" />
                          <label for="searchSyuKbn0">
                            <gsmsg:write key="cmn.all" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchSyuKbn" styleId="searchSyuKbn1" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SYUSYAKBN_NO) %>" />
                          <label for="searchSyuKbn1">
                            <gsmsg:write key="anp.syusya.no" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchSyuKbn" styleId="searchSyuKbn2" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SYUSYAKBN_OK) %>" />
                          <label for="searchSyuKbn2">
                            <gsmsg:write key="anp.syusya.ok" />
                          </label>
                          <html:radio name="anp010Form" styleClass="ml10" property="anp010SearchSyuKbn" styleId="searchSyuKbn3" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SEARCH_SYUSYAKBN_OKD) %>" />
                          <label for="searchSyuKbn3">
                            <gsmsg:write key="anp.syusya.okd" />
                          </label>
                        </span>
                      </td>
                    </tr>
                  </table>
                  <div class="txt_c mb10">
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search"/>" onclick="buttonPush('detailSearch');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                      <gsmsg:write key="cmn.search" />
                    </button>
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.cancel"/>" onclick="dispSearch();">
                      <gsmsg:write key="cmn.cancel" />
                    </button>
                  </div>
                </div>
              </div>
              <!-- グループコンボ -->
              <table class="w100">
                <tr>
                  <td class="txt_l">
                    <logic:notEmpty name="anp010Form" property="anp010GroupLabel" scope="request">
                      <gsmsg:write key="cmn.show.group" />
                      <html:select property="anp010SelectGroupSid" onchange="buttonPush('anp010group');">
                        <logic:notEmpty name="anp010Form" property="anp010GroupLabel" scope="request">
                          <logic:iterate id="gpBean" name="anp010Form" property="anp010GroupLabel" scope="request">
                            <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                            <logic:equal name="gpBean" property="styleClass" value="0">
                              <html:option value="<%= gpValue %>">
                                <bean:write name="gpBean" property="label" />
                              </html:option>
                            </logic:equal>
                            <logic:notEqual name="gpBean" property="styleClass" value="0">
                              <html:option value="<%= gpValue %>">
                                <bean:write name="gpBean" property="label" />
                              </html:option>
                            </logic:notEqual>
                          </logic:iterate>
                        </logic:notEmpty>
                      </html:select>
                      <button class="iconBtn-border" type="button" id="groupBtn" value="  " onClick="openGroupWindow(this.form.anp010SelectGroupSid, 'anp010SelectGroupSid', '1', 'anp010group')">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                      </button>
                    </logic:notEmpty>
                  </td>
                  <td class="txt_r">
                    <!-- ページング -->
                    <bean:size id="pageCount" name="anp010Form" property="anp010PageLabel" scope="request" />
                    <logic:greaterThan name="pageCount" value="1">
                      <div class="paging">
                        <button type="button" class="webIconBtn" onClick="buttonPush('anp010pageLast');">
                          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                          <i class="icon-paging_left"></i>
                        </button>
                        <html:select styleClass="paging_combo" property="anp010DspPage1" onchange="changePage(this);">
                          <html:optionsCollection name="anp010Form" property="anp010PageLabel" value="value" label="label" />
                        </html:select>
                        <button type="button" class="webIconBtn" onClick="buttonPush('anp010pageNext');">
                          <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                          <i class="icon-paging_right"></i>
                        </button>
                      </div>
                    </logic:greaterThan>
                  </td>
                </tr>
              </table>
              <table class="table-top no_w">
                <!-- ヘッダー -->
                <tr>
                  <bean:define id="sortKeyIndex" name="anp010Form" property="anp010SortKeyIndex" />
                  <bean:define id="orderKey" name="anp010Form" property="anp010OrderKey" />
                  <gsmsg:define id="h_send" msgkey="anp.send" />
                  <gsmsg:define id="h_answer" msgkey="anp.answer" />
                  <gsmsg:define id="h_name" msgkey="cmn.name" />
                  <gsmsg:define id="h_state" msgkey="anp.state" />
                  <gsmsg:define id="h_place" msgkey="anp.place" />
                  <gsmsg:define id="h_syusya" msgkey="anp.syusya" />
                  <gsmsg:define id="h_comment" msgkey="anp.comment" />
                  <% int iSortKeyIndex = ((Integer) sortKeyIndex).intValue();   %>
                  <% int iOrderKey = ((Integer) orderKey).intValue();     %>
                  <% String[] colTitle = new String[] {h_send, h_answer, h_name, h_state, h_place, h_syusya, h_comment}; %>
                  <% String[] colWidth = new String[] {"w5", "w10", "w20", "", "", "", "w50"}; %>
                  <% Integer[] colOrder = new Integer[] {1, 1, 1, 1, 1, 1, 0}; %>
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
                  <% if (i != colTitle.length - 1) { %>
                  <th class="<%= colWidth[i] %> table_title-color txt_c cursor_p table_header-evt js_table_header-evt">
                    <% } else { %>

                  <th class="<%= colWidth[i] %> table_title-color txt_c">
                    <% } %>
                    <% if (colOrder[i] == 1) { %>
                    <a href="#" onClick="return sortList(<%= i %>, <%= order %>);">
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

                <logic:notEmpty name="anp010Form" property="anp010List">
                  <logic:iterate id="detailModel" name="anp010Form" property="anp010List" indexId="idx">

                    <% String list_hover = "js_listHover cursor_p"; %>
                    <% String list_click = "js_listClick"; %>
                    <% String link_color = "cl_linkDef";%>

                    <logic:notEqual name="detailModel" property="usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                      <% list_hover = ""; %>
                      <% list_click = ""; %>
                      <% link_color = "";%>
                    </logic:notEqual>

                    <tr class="<%= list_hover %>" id=<bean:write name="detailModel" property="usrSid" />>
                      <td class="txt_c no_w js_listClick">
                        <logic:equal name="detailModel" property="haisinflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.HAISIN_FLG_OK) %>">
                          <img class="btn_classicImg-display" src="../anpi/images/classic/icon_okmail.gif" alt="<gsmsg:write key="anp.icon.ok"/>">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_mail2.png" alt="<gsmsg:write key="anp.icon.ok"/>">
                        </logic:equal>
                        <logic:equal name="detailModel" property="haisinflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.HAISIN_FLG_UNSET) %>">
                          <img class="btn_classicImg-display" src="../anpi/images/classic/icon_waitmail.gif" alt="<gsmsg:write key="anp.icon.wait"/>">
                          <img class="btn_originalImg-display" src="../anpi/images/original/icon_sending.png" alt="<gsmsg:write key="anp.icon.wait"/>">
                        </logic:equal>
                        <logic:equal name="detailModel" property="haisinflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.HAISIN_FLG_ERROR) %>">
                          <img class="btn_classicImg-display" src="../anpi/images/classic/icon_nomail.gif" alt="<gsmsg:write key="anp.icon.error"/>">
                          <img class="btn_originalImg-display" src="../anpi/images/original/icon_senderr.png" alt="<gsmsg:write key="anp.icon.error"/>">
                        </logic:equal>
                      </td>
                      <td class="txt_c <%= list_click %>">
                        <logic:notEmpty name="detailModel" property="replyDate">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png" alt="<gsmsg:write key="anp.icon.answer"/>">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="anp.icon.answer"/>">
                          <span class="ml10">
                            <bean:write name="detailModel" property="replyDate" />
                          </span>
                        </logic:notEmpty>
                      </td>
                      <td class="txt_l <%= list_click %> <%= link_color %>">
                        <logic:equal name="detailModel" property="usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                          <bean:define id="mukoUserClass" value="" />
                          <logic:equal value="1" name="detailModel" property="usrUkoFlg">
                            <bean:define id="mukoUserClass" value="mukoUser" />
                          </logic:equal>
                          <span class="<%= mukoUserClass %>">
                            <bean:write name="detailModel" property="name" />
                          </span>
                        </logic:equal>
                        <logic:notEqual name="detailModel" property="usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_TOROKU) %>">
                          <del>
                            <bean:write name="detailModel" property="name" />
                          </del>
                        </logic:notEqual>
                      </td>
                      <td class="txt_c <%= list_click %>">
                        <logic:equal name="detailModel" property="jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_UNSET) %>">
                          -
                        </logic:equal>
                        <logic:equal name="detailModel" property="jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_GOOD) %>">
                          <span class="cl_fontSafe">
                            <gsmsg:write key="anp.jokyo.good" />
                          </span>
                        </logic:equal>
                        <logic:equal name="detailModel" property="jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_KEISYO) %>">
                          <span class="cl_fontWeekWarn">
                            <gsmsg:write key="anp.jokyo.keisyo" />
                          </span>
                        </logic:equal>
                        <logic:equal name="detailModel" property="jokyoflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.JOKYO_FLG_JUSYO) %>">
                          <span class="cl_fontWarn">
                            <gsmsg:write key="anp.jokyo.jusyo" />
                          </span>
                        </logic:equal>
                      </td>
                      <td class="txt_c <%= list_click %>">
                        <logic:equal name="detailModel" property="placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_UNSET) %>">
                          -
                        </logic:equal>
                        <logic:equal name="detailModel" property="placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_HOUSE) %>">
                          <gsmsg:write key="anp.place.house" />
                        </logic:equal>
                        <logic:equal name="detailModel" property="placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_OFFICE) %>">
                          <gsmsg:write key="anp.place.office" />
                        </logic:equal>
                        <logic:equal name="detailModel" property="placeflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.PLACE_FLG_OUT) %>">
                          <gsmsg:write key="anp.place.out" />
                        </logic:equal>
                      </td>
                      <td class="txt_c <%= list_click %>">
                        <logic:equal name="detailModel" property="syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_UNSET) %>">
                          -
                        </logic:equal>
                        <logic:equal name="detailModel" property="syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_NO) %>">
                          <span class="cl_fontWarn">
                            <gsmsg:write key="anp.syusya.no" />
                          </span>
                        </logic:equal>
                        <logic:equal name="detailModel" property="syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_OK) %>">
                          <gsmsg:write key="anp.syusya.ok" />
                        </logic:equal>
                        <logic:equal name="detailModel" property="syusyaflg" value="<%= String.valueOf(jp.groupsession.v2.anp.GSConstAnpi.SYUSYA_FLG_OKD) %>">
                          <gsmsg:write key="anp.syusya.okd" />
                        </logic:equal>
                      </td>
                      <td class="txt_l js_listClick">
                        <bean:write name="detailModel" property="comment" filter="false" />
                      </td>
                    </tr>
                  </logic:iterate>
                </logic:notEmpty>
              </table>
              <!-- ページング -->
              <bean:size id="pageCount" name="anp010Form" property="anp010PageLabel" scope="request" />
              <logic:greaterThan name="pageCount" value="1">
                <div class="paging">
                  <button type="button" class="webIconBtn" onClick="buttonPush('anp010pageLast');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                    <i class="icon-paging_left"></i>
                  </button>
                  <html:select styleClass="paging_combo" property="anp010DspPage1" onchange="changePage(this);">
                    <html:optionsCollection name="anp010Form" property="anp010PageLabel" value="value" label="label" />
                  </html:select>
                  <button type="button" class="webIconBtn" onClick="buttonPush('anp010pageNext');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                    <i class="icon-paging_right"></i>
                  </button>
                </div>
              </logic:greaterThan>
            </td>
          </tr>
        </table>
      </logic:notEmpty>
    </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>