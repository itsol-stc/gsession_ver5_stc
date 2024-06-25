<%@page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<%
  String callOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CALL_OFF);
  String callOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CALL_ON);
  String shortcutOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.SHORTCUT_OFF);
  String shortcutOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.SHORTCUT_ON);
  String rekiKbnNew = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_NEW);
  String rekiKbnUpdate = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_UPDATE);
  String rekiKbnRepair = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_REPAIR);
  String rekiKbnDelete = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_DELETE);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="fil.50" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmnPic.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil020.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<!-- BODY -->
<body
  <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
  <input type="hidden" name="helpPrm" value="<bean:write name="fil020Form" property="fil020DspMode" />">
  <html:form action="/file/fil020">
    <input type="hidden" name="CMD" value="">

    <html:hidden property="cmnMode" />
    <html:hidden property="backDsp" />
    <html:hidden property="fileSid" />
    <html:hidden property="filSearchWd" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010DspCabinetKbn" />
    <input type="hidden" name="fil030SelectCabinet" value="">

    <html:hidden property="fil020DspMode" />
    <html:hidden property="fil020SortKey" />
    <html:hidden property="fil020OrderKey" />
    <html:hidden property="fil020SltDirSid" />
    <html:hidden property="fil020SltDirVer" />
    <html:hidden property="fil020binSid" />

    <logic:notEmpty name="fil020Form" property="fil010SelectDelLink" scope="request">
      <logic:iterate id="item" name="fil020Form" property="fil010SelectDelLink" scope="request">
        <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <!-- HEADER -->

    <div class="pageTitle w100 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li>
          <gsmsg:write key="cmn.filekanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="fil.50" />
        </li>
        <li>
          <div>
            <logic:equal name="fil020Form" property="fil020WriteFlg" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="fil020CabinetEdit('fil020edit');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil020back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w100 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.23" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="fil020Form" property="fil020CabinetName" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.3" />
            </span>
          </td>
          <td class="txt_l w80">
            <logic:equal name="fil020Form" property="fil020DspCapaKbn" value="0">
              <span>
                <gsmsg:write key="cmn.noset" />
              </span>
            </logic:equal>
            <logic:notEqual name="fil020Form" property="fil020DspCapaKbn" value="0">
              <span>
                <gsmsg:write key="fil.4" />：
                <span class="fw_b">
                  <bean:write name="fil020Form" property="fil020DspCapaSize" />MB&nbsp;
                </span>
              </span>
              <logic:notEqual name="fil020Form" property="fil020DspCapaWarn" value="0">
                <br>
                <span>
                  <gsmsg:write key="fil.fil030kn.1" />：
                  <span class="fw_b">
                    <bean:write name="fil020Form" property="fil020DspCapaWarn" />%
                  </span>
                </span>
              </logic:notEqual>
            </logic:notEqual>
          </td>

        </tr>
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.icon" />
            </span>
          </td>
          <td class="txt_l w80">
            <logic:equal name="fil020Form" property="fil020binSid" value="0">
              <logic:equal name="fil020Form" property="detailCabinet" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                <img class="btn_classicImg-display" src="../file/images/classic/icon_cabinet.gif">
                <img class="btn_originalImg-display" src="../file/images/original/icon_cabinet_32.png">
              </logic:equal>
              <logic:equal name="fil020Form" property="detailCabinet" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
                <img class="btn_classicImg-display" src="../file/images/classic/30classic_icon_personal_cabinet.png">
                <img class="btn_originalImg-display" src="../file/images/original/30original_icon_personal_cabinet.png">
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="fil020Form" property="fil020binSid" value="0">
              <img name="iconImage" class="wp30" src="../file/fil020.do?CMD=tempview&fil010SelectCabinet=<bean:write name="fil020Form" property="fil010SelectCabinet" />&fil020binSid=<bean:write name="fil020Form" property="fil020binSid" />" name="pictImage">
            </logic:notEqual>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.17" />
            </span>
          </td>
          <td class="txt_l w80">
            <logic:equal name="fil020Form" property="fil020ErrlKbn" value="1">
              <gsmsg:write key="fil.128" />
            </logic:equal>
            <logic:notEqual name="fil020Form" property="fil020ErrlKbn" value="1">
              <gsmsg:write key="fil.129" />
            </logic:notEqual>
          </td>
        </tr>

        <logic:equal name="fil020Form" property="fil020ErrlKbn" value="1">
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="fil.19" />
              </span>
            </td>
            <td class="txt_l w80">
              <logic:equal name="fil020Form" property="fil020ErrlAutoKbn" value="0">
                <gsmsg:write key="fil.fil290.3" />
              </logic:equal>
              <logic:notEqual name="fil020Form" property="fil020ErrlAutoKbn" value="0">
                <gsmsg:write key="fil.fil290.2" /><div class="mt10">
                <div class="mt10">
                  <gsmsg:write key="fil.190" />
                  <div class="ml20">
                    <gsmsg:write key="fil.fil030.10" />
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder1" value="1">
                      <span class="ml5"><gsmsg:write key="fil.fil030.16" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder1" value="2">
                      <span class="ml5"><gsmsg:write key="fil.fil030.17" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder1" value="3">
                      <span class="ml5"><gsmsg:write key="fil.fil030.18" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder1" value="4">
                      <span class="ml5"><gsmsg:write key="user.35" /></span>
                    </logic:equal>
                  </div>
                  <div class="ml20">
                    <gsmsg:write key="fil.fil030.11" />
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder2" value="0">
                      <span class="ml5"><gsmsg:write key="cmn.no" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder2" value="1">
                      <span class="ml5"><gsmsg:write key="fil.fil030.16" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder2" value="2">
                      <span class="ml5"><gsmsg:write key="fil.fil030.17" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder2" value="3">
                      <span class="ml5"><gsmsg:write key="fil.fil030.18" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder2" value="4">
                      <span class="ml5"><gsmsg:write key="user.35" /></span>
                    </logic:equal>
                  </div>
                  <logic:notEqual name="fil020Form" property="fil020ErrlAutoFolder2" value="0">
                  <div class="ml20">
                    <gsmsg:write key="fil.fil030.24" />
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder3" value="0">
                      <span class="ml5"><gsmsg:write key="cmn.no" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder3" value="1">
                      <span class="ml5"><gsmsg:write key="fil.fil030.16" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder3" value="2">
                      <span class="ml5"><gsmsg:write key="fil.fil030.17" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder3" value="3">
                      <span class="ml5"><gsmsg:write key="fil.fil030.18" /></span>
                    </logic:equal>
                    <logic:equal name="fil020Form" property="fil020ErrlAutoFolder3" value="4">
                      <span class="ml5"><gsmsg:write key="user.35" /></span>
                    </logic:equal>
                  </div>
                  </logic:notEqual>
                </div>
              </logic:notEqual>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="fil020Form" property="admVerKbn" value="1">
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="fil.5" />
              </span>
            </td>
            <td class="txt_l w80">
              <logic:equal name="fil020Form" property="fil020ErrlKbn" value="1">
                <gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />:<gsmsg:write key="fil.fil030.2" /></span>
              </logic:equal>
              <logic:notEqual name="fil020Form" property="fil020ErrlKbn" value="1">
                <logic:equal name="fil020Form" property="fil020VerKbn" value="0">
                  <span>
                    <gsmsg:write key="fil.fil030kn.5" />
                  </span>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020VerKbn" value="0">
                  <bean:define id="ver" name="fil020Form" property="fil020VerKbn" type="java.lang.String" />
                  <gsmsg:write key="fil.15" /><span class="ml20"><gsmsg:write key="fil.fil030.3" />：<gsmsg:write key="fil.generations" arg0="<%=ver%>" /></span>
                </logic:notEqual>
              </logic:notEqual>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </td>
          <td class="txt_l w80">
            <span>
              <bean:write name="fil020Form" property="fil020DspBiko" filter="false" />
            </span>
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="fil.2" />
            </span>
          </td>
          <td class="txt_l w80">
            <logic:equal name="fil020Form" property="fil020ShortCutKbn" value="0">
              <span class="mr10">
                <gsmsg:write key="fil.105" />
              </span>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('fil020short');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_scut.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_shortcut.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
            <logic:notEqual name="fil020Form" property="fil020ShortCutKbn" value="0">
              <span class="mr10">
                <gsmsg:write key="fil.106" />
              </span>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil020short');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:notEqual>
          </td>
        </tr>

        <logic:equal name="fil020Form" property="detailCabinet" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="fil.1" />
              </span>
            </td>
            <td class="txt_l w80">
              <span class="verAlignMid">
                <logic:equal name="fil020Form" property="fil020CallKbn" value="0">
                  <span class="mr10">
                    <gsmsg:write key="cmn.invalid" />
                  </span>
                  <button type="button" class="baseBtn mr5" value="<gsmsg:write key="fil.1" />" onclick="buttonPush('fil020call');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_beru.png" alt="<gsmsg:write key="fil.1" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png" alt="<gsmsg:write key="fil.1" />">
                    <gsmsg:write key="fil.1" />
                  </button>
                </logic:equal>
                <logic:equal name="fil020Form" property="fil020CallKbn" value="1">
                  <span class="mr10">
                    <gsmsg:write key="cmn.effective" />
                  </span>
                  <button type="button" class="baseBtn mr5" value="<gsmsg:write key="fil.20" />" onclick="buttonPush('fil020call');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="fil.20" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="fil.20" />">
                    <gsmsg:write key="fil.20" />
                  </button>
                </logic:equal>
                <span class="verAlignMid ml20">
                  <html:checkbox name="fil020Form" property="fil020CallLevelKbn" value="1" styleId="ikkatuCheck" />
                  <label for="ikkatuCheck">
                    <gsmsg:write key="fil.7" />
                  </label>
                </span>
              </span>
            </td>
          </tr>
        </logic:equal>
      </table>

      <logic:equal name="fil020Form" property="fil020DspMode" value="0">
        <!--更新履歴-->

        <ul class="tabHeader w100 hp30">
          <li class="tabHeader_tab-on mwp100 pl10 pr10 pt5 pb5 js_tab border_bottom_none bgI_none">
            <gsmsg:write key="fil.fil020.2" />
          </li>
          <li class="tabHeader_tab-off mwp100 pl10 pr10 pt5 pb5 js_tab border_bottom_none" onclick="fil020TabChange('fil020tabChange', '1');">
            <gsmsg:write key="fil.fil020.3" />
          </li>
          <li class="tabHeader_space border_bottom_none"></li>
          <li>
            <bean:size id="count1" name="fil020Form" property="fil020PageLabel" scope="request" />
            <logic:greaterThan name="count1" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select styleClass="paging_combo" property="fil020Slt_page1" onchange="fil020changePage(1);">
                  <html:optionsCollection name="fil020Form" property="fil020PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </li>
        </ul>

        <logic:empty name="fil020Form" property="historyList">
          <table class="table-left mt0" cellpadding="5">
            <tr>
              <td>
                <span>
                  <gsmsg:write key="fil.fil020.4" />
                </span>
              </td>
          </table>
        </logic:empty>

        <logic:notEmpty name="fil020Form" property="historyList">
          <bean:define id="nameWidth" value="w40" type="String" />
          <logic:equal name="fil020Form" property="cabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
            <bean:define id="nameWidth" value="w20" type="String" />
          </logic:equal>

          <table class="table-top mt0 mb0">
            <tr>
              <th class="w10 no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="1">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(1, 1);">
                      <span>
                        <gsmsg:write key="cmn.update.day.hour" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(1, 0);">
                      <span>
                        <gsmsg:write key="cmn.update.day.hour" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="1">
                  <a href="#!" onClick="return fil020TitleClick(1, 1);">
                    <span>
                      <gsmsg:write key="cmn.update.day.hour" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w15 no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="2">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(2, 1);">
                      <span>
                        <gsmsg:write key="cmn.update.user" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(2, 0);">
                      <span>
                        <gsmsg:write key="cmn.update.user" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="2">
                  <a href="#!" onClick="return fil020TitleClick(2, 1);">
                    <span>
                      <gsmsg:write key="cmn.update.user" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="<%=nameWidth %> no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="3">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(3, 1);">
                      <span>
                        <gsmsg:write key="fil.9" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(3, 0);">
                      <span>
                        <gsmsg:write key="fil.9" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="3">
                  <a href="#!" onClick="return fil020TitleClick(3, 1);">
                    <span>
                      <gsmsg:write key="fil.9" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <logic:equal name="fil020Form" property="cabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">

              <th class="w5 no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="5">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(5, 1);">
                      <span>
                        <gsmsg:write key="fil.fil080.8" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(5, 0);">
                      <span>
                        <gsmsg:write key="fil.fil080.8" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="5">
                  <a href="#!" onClick="return fil020TitleClick(5, 1);">
                    <span>
                      <gsmsg:write key="fil.fil080.8" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w15 no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="6">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(6, 1);">
                      <span>
                        <gsmsg:write key="fil.fil030.18" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(6, 0);">
                      <span>
                        <gsmsg:write key="fil.fil030.18" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="6">
                  <a href="#!" onClick="return fil020TitleClick(6, 1);">
                    <span>
                      <gsmsg:write key="fil.fil030.18" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w10 no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="7">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(7, 1);">
                      <span>
                        <gsmsg:write key="fil.fil080.5" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(7, 0);">
                      <span>
                        <gsmsg:write key="fil.fil080.5" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="7">
                  <a href="#!" onClick="return fil020TitleClick(7, 1);">
                    <span>
                      <gsmsg:write key="fil.fil080.5" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              </logic:equal>

              <th class="w2 no_w cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="4">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(4, 1);">
                      <span>
                        <gsmsg:write key="cmn.operations" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(4, 0);">
                      <span>
                        <gsmsg:write key="cmn.operations" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="4">
                  <a href="#!" onClick="return fil020TitleClick(4, 1);">
                    <span>
                      <gsmsg:write key="cmn.operations" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w20 no_w">
                <span>
                  <gsmsg:write key="fil.11" />
                </span>
              </th>

              <logic:equal name="fil020Form" property="fil020FileWriteFlg" value="1">
                <th class="w2 no_w">
                  <span>
                    <gsmsg:write key="fil.12" />
                  </span>
                </th>
              </logic:equal>
              <logic:notEqual name="fil020Form" property="fil020FileWriteFlg" value="1">
                <logic:equal name="fil020Form" property="fil020RepairDspFlg" value="1">
                  <th class="w2 no_w">
                    <span>
                      <gsmsg:write key="fil.12" />
                    </span>
                  </th>
                </logic:equal>
              </logic:notEqual>

            </tr>
          <logic:iterate id="historyMdl" name="fil020Form" property="historyList" indexId="idx">

            <% String trElement = "", clickClass = ""; %>
            <bean:define id="fileName" name="historyMdl" property="ffrName" />
            <logic:notEqual name="historyMdl" property="binSid" value="0">
              <bean:define id="listBinSid" name="historyMdl" property="binSid" type="java.lang.Long" />
              <%
              String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1));
              trElement = " class=\"js_listHover cursor_p\" data-sid=\"" + listBinSid.longValue() + "\" data-extension=\"" + extension + "\"";
                clickClass = " js_listClick";
              %>
            </logic:notEqual>

            <tr<%= trElement %>>

              <td class="txt_c no_w <%= clickClass %>">
                <bean:write name="historyMdl" property="ffrEdate" />
              </td>
              <td class="txt_l no_w <%= clickClass %>">
                <logic:equal name="historyMdl" property="usrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <span>
                      <s><bean:write name="historyMdl" property="usrSeiMei" /></s>
                    </span>
                </logic:equal>
                <logic:notEqual name="historyMdl" property="usrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal value="1" name="historyMdl" property="usrUkoFlg">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>">
                    <bean:write name="historyMdl" property="usrSeiMei" />
                  </span>
                </logic:notEqual>
              </td>
              <td class="txt_l">
                <logic:notEqual name="historyMdl" property="binSid" value="0">
                  <span class="cl_linkDef <%= clickClass %>">
                    <bean:write name="historyMdl" property="ffrName" />
                  </span>
                  <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                  <span class="ml5 cursor_p">
                    <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                    <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                  </span>
                  <% } %>
                </logic:notEqual>

                <logic:equal name="historyMdl" property="binSid" value="0">
                  <span class="cl_fontWeek <%= clickClass %>">
                    <bean:write name="historyMdl" property="ffrName" />
                  </span>
                </logic:equal>
              </td>
              <logic:equal name="fil020Form" property="cabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
                <td class="txt_c">
                  <bean:write name="historyMdl" property="fdrTradeDate" />
                </td>
                <td class="txt_l">
                  <bean:write name="historyMdl" property="fdrTradeTarget" />
                </td>
                <logic:equal name="historyMdl" property="fdrTradeMoneykbn" value="0">
                  <td class="txt_c">
                    -
                  </td>
                </logic:equal>
                <logic:notEqual name="historyMdl" property="fdrTradeMoneykbn" value="0">
                  <td class="txt_r no_w">
                    <bean:write name="historyMdl" property="fdrTradeMoney" />&nbsp;<bean:write name="historyMdl" property="fmmName" />
                  </td>
                </logic:notEqual>
              </logic:equal>
              <td class="txt_c no_w<%= clickClass %>">
                <span>
                  <logic:equal name="historyMdl" property="ffrKbn" value="<%=rekiKbnNew%>">
                    <gsmsg:write key="cmn.new" />
                  </logic:equal>
                  <logic:equal name="historyMdl" property="ffrKbn" value="<%=rekiKbnUpdate%>">
                    <gsmsg:write key="cmn.update" />
                  </logic:equal>
                  <logic:equal name="historyMdl" property="ffrKbn" value="<%=rekiKbnRepair%>">
                    <gsmsg:write key="fil.12" />
                  </logic:equal>
                  <logic:equal name="historyMdl" property="ffrKbn" value="<%=rekiKbnDelete%>">
                    <gsmsg:write key="cmn.delete" />
                  </logic:equal>
                </span>
              </td>

              <td class="txt_l <%= clickClass %>">
                <span>
                  <bean:write name="historyMdl" property="ffrUpCmt" filter="false" />
                </span>
              </td>

              <logic:equal name="fil020Form" property="fil020FileWriteFlg" value="1">
                <td class="txt_c">
                  <logic:equal name="historyMdl" property="repairBtnDspFlg" value="true">
                    <button type="button" value="<gsmsg:write key="fil.fil070.2" />" class="baseBtn no_w" onClick="fil020RepairClick(<bean:write name="historyMdl" property="fdrSid" />, <bean:write name="historyMdl" property="ffrVersion" />);">
                      <gsmsg:write key="fil.fil070.2" />
                    </button>
                  </logic:equal>
                </td>
              </logic:equal>
              <logic:notEqual name="fil020Form" property="fil020FileWriteFlg" value="1">
                <logic:equal name="fil020Form" property="fil020RepairDspFlg" value="1">
                  <td class="txt_c">
                    <logic:equal name="historyMdl" property="repairBtnDspFlg" value="true">
                      <button type="button" value="<gsmsg:write key="fil.fil070.2" />" class="baseBtn no_w" onClick="fil020RepairClick(<bean:write name="historyMdl" property="fdrSid" />, <bean:write name="historyMdl" property="ffrVersion" />);">
                        <gsmsg:write key="fil.fil070.2" />
                      </button>
                    </logic:equal>
                  </td>
                </logic:equal>
              </logic:notEqual>
            </tr>
          </logic:iterate>

          </table>
          <bean:size id="count2" name="fil020Form" property="fil020PageLabel" scope="request" />
          <logic:greaterThan name="count2" value="1">
            <div class="paging">
              <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo" property="fil020Slt_page2" onchange="fil020changePage(2);">
                <html:optionsCollection name="fil020Form" property="fil020PageLabel" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:greaterThan>
        </logic:notEmpty>
      </logic:equal>
      <logic:equal name="fil020Form" property="fil020DspMode" value="1">
        <!--アクセス制限-->

        <ul class="tabHeader w100 hp30">
          <li class="tabHeader_tab-off mwp100 pl10 pr10 pb5 pt5 js_tab border_bottom_none" onclick="fil020TabChange('fil020tabChange', '0');">
            <gsmsg:write key="fil.fil020.2" />
          </li>
          <li class="tabHeader_tab-on mwp100 pl10 pr10 pb5 pt5 js_tab border_bottom_none bgI_none">
            <gsmsg:write key="fil.fil020.3" />
          </li>
          <li class="tabHeader_space border_bottom_none"></li>
          <li>
            <bean:size id="count1" name="fil020Form" property="fil020PageLabel" scope="request" />
            <logic:greaterThan name="count1" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select styleClass="paging_combo" property="fil020Slt_page1" onchange="fil020changePage(1);">
                  <html:optionsCollection name="fil020Form" property="fil020PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </li>
        </ul>


        <logic:empty name="fil020Form" property="accessList">
          <table class="table-left">
            <tr>
              <td>
                <span>
                  <gsmsg:write key="fil.fil020.5" />
                </span>
              </td>
          </table>
        </logic:empty>

        <logic:notEmpty name="fil020Form" property="accessList">
          <table class="table-top mt0 mb0">

            <tr>
              <th class="w20 cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="2">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(2, 1);">
                      <span>
                        <gsmsg:write key="cmn.employee.staff.number" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(2, 0);">
                      <span>
                        <gsmsg:write key="cmn.employee.staff.number" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="2">
                  <a href="#!" onClick="return fil020TitleClick(2, 1);">
                    <span>
                      <gsmsg:write key="cmn.employee.staff.number" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w30 cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="1">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(1, 1);">
                      <span>
                        <gsmsg:write key="cmn.name" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(1, 0);">
                      <span>
                        <gsmsg:write key="cmn.name" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="1">
                  <a href="#!" onClick="return fil020TitleClick(1, 1);">
                    <span>
                      <gsmsg:write key="cmn.name" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w20 cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="3">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(3, 1);">
                      <span>
                        <gsmsg:write key="cmn.post" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(3, 0);">
                      <span>
                        <gsmsg:write key="cmn.post" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="3">
                  <a href="#!" onClick="return fil020TitleClick(3, 1);">
                    <span>
                      <gsmsg:write key="cmn.post" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w15 cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="4">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(4, 1);">
                      <span>
                        <gsmsg:write key="cmn.admin" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(4, 0);">
                      <span>
                        <gsmsg:write key="cmn.admin" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="4">
                  <a href="#!" onClick="return fil020TitleClick(4, 1);">
                    <span>
                      <gsmsg:write key="cmn.admin" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w15 cursor_p">
                <logic:equal name="fil020Form" property="fil020SortKey" value="5">
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="0">
                    <a href="#!" onClick="return fil020TitleClick(5, 1);">
                      <span>
                        <gsmsg:write key="cmn.edit.permissions" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil020Form" property="fil020OrderKey" value="1">
                    <a href="#!" onClick="return fil020TitleClick(5, 0);">
                      <span>
                        <gsmsg:write key="cmn.edit.permissions" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil020Form" property="fil020SortKey" value="5">
                  <a href="#!" onClick="return fil020TitleClick(5, 1);">
                    <span>
                      <gsmsg:write key="cmn.edit.permissions" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

            </tr>

            <logic:iterate id="accessMdl" name="fil020Form" property="accessList" indexId="idx">
              <bean:define id="mukoUserClass" value="" />
              <logic:equal value="1" name="accessMdl" property="usrUkoFlg">
                <bean:define id="mukoUserClass" value="mukoUser" />
              </logic:equal>
              <tr>
                <td class="txt_l">
                  <span class="<%= mukoUserClass %>">
                    <bean:write name="accessMdl" property="usiSyainNo" />
                  </span>
                </td>
                <td class="txt_l">
                  <span class="<%= mukoUserClass %>">
                    <bean:write name="accessMdl" property="usiSei" />
                    &nbsp;
                    <bean:write name="accessMdl" property="usiMei" />
                  </span>
                </td>
                <td class="txt_l">
                  <span class="<%= mukoUserClass %>">
                    <bean:write name="accessMdl" property="usiYakusyoku" />
                  </span>
                </td>
                <td class="txt_c">
                  <logic:equal name="accessMdl" property="cabinetAdminKbn" value="1">
                    <img class="classic-display" src="../common/images/classic/icon_check.png" alt="">
                    <img class="original-display" src="../common/images/original/icon_checked.png" alt="">
                  </logic:equal>
                </td>
                <td class="txt_c">
                  <logic:equal name="accessMdl" property="cabinetAccessKbn" value="0">
                    <img class="classic-display" src="../file/images/classic/18_icon_stop.png" alt="">
                    <img class="original-display" src="../file/images/original/icon_stop.png" alt="">
                  </logic:equal>
                </td>
              </tr>
            </logic:iterate>

          </table>
        </logic:notEmpty>

        <table class="w100 mt0">
          <tr>
            <td class="txt_l">
              <span class="verAlignMid">
                <img class="classic-display" src="../common/images/classic/icon_check.png" alt="">
                <img class="original-display" src="../common/images/original/icon_checked.png" alt="">
                ：<gsmsg:write key="cmn.admin" />
                <img class="classic-display ml20" src="../file/images/classic/18_icon_stop.png" alt="">
                <img class="original-display ml20" src="../file/images/original/icon_stop.png" alt="">
                ：<gsmsg:write key="fil.fil020.6" />
              </span>
            </td>
            <td class="txt_r txt_t">
              <bean:size id="count2" name="fil020Form" property="fil020PageLabel" scope="request" />
              <logic:greaterThan name="count2" value="1">
                <div class="paging">
                  <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                    <i class="icon-paging_left"></i>
                  </button>
                  <html:select styleClass="paging_combo" property="fil020Slt_page2" onchange="fil020changePage(2);">
                    <html:optionsCollection name="fil020Form" property="fil020PageLabel" value="value" label="label" />
                  </html:select>
                  <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                    <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                    <i class="icon-paging_right"></i>
                  </button>
                </div>
              </logic:greaterThan>
            </td>
          </tr>
        </table>
      </logic:equal>

      <div class="footerBtn_block mt20">
        <logic:equal name="fil020Form" property="fil020WriteFlg" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="fil020CabinetEdit('fil020edit');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil020back');">
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