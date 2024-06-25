<%@page import="jp.groupsession.v2.fil.model.FileDAccessUserModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>

<%
  String callOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CALL_OFF);
  String callOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CALL_ON);
  String shortcutOff = String.valueOf(jp.groupsession.v2.fil.GSConstFile.SHORTCUT_OFF);
  String shortcutOn = String.valueOf(jp.groupsession.v2.fil.GSConstFile.SHORTCUT_ON);
  String rekiKbnNew = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_NEW);
  String rekiKbnUpdate = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_UPDATE);
  String rekiKbnRepair = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_REPAIR);
  String rekiKbnDelete = String.valueOf(jp.groupsession.v2.fil.GSConstFile.REKI_KBN_DELETE);
  String DspModeHist = String.valueOf(jp.groupsession.v2.fil.GSConstFile.DSP_MODE_HIST);
  String cabinetKbnErrl = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_KBN_ERRL);
  String pgid = String.valueOf(0);
%>
<!DOCTYPE html>
<html:html>

<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.54" /></title>
<theme:css filename="theme.css" />
<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil050.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>
  <html:form action="/file/fil050">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="backDspLow" />
    <html:hidden property="filSearchWd" />

    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil010DspCabinetKbn" />

    <html:hidden property="fil040PersonalFlg" />
    <html:hidden property="fil040PersonalCabOwnerSid" />
    <html:hidden property="fil040PersonalCabOwnerName" />
    <html:hidden property="fil050DspMode" />
    <html:hidden property="fil050SortKey" />
    <html:hidden property="fil050OrderKey" />
    <html:hidden property="fil050ShortcutKbn" />
    <html:hidden property="fil050CallKbn" />
    <html:hidden property="fil050SltDirSid" />
    <html:hidden property="fil050SltDirVer" />
    <html:hidden property="fil050DirSid" />
    <html:hidden property="fil050ParentDirSid" />

    <html:hidden property="fileSid" />

    <html:hidden name="fil050Form" property="fil100SltCabinetSid" />
    <html:hidden name="fil050Form" property="fil100ChkTrgFolder" />
    <html:hidden name="fil050Form" property="fil100ChkTrgFile" />
    <html:hidden name="fil050Form" property="fil100SearchMode" />
    <html:hidden name="fil050Form" property="fil100ChkWdTrgName" />
    <html:hidden name="fil050Form" property="fil100ChkWdTrgBiko" />
    <html:hidden name="fil050Form" property="fil100ChkWdTrgText" />
    <html:hidden name="fil050Form" property="fileSearchfromYear" />
    <html:hidden name="fil050Form" property="fileSearchfromMonth" />
    <html:hidden name="fil050Form" property="fileSearchfromDay" />
    <html:hidden name="fil050Form" property="fileSearchtoYear" />
    <html:hidden name="fil050Form" property="fileSearchtoMonth" />
    <html:hidden name="fil050Form" property="fileSearchtoDay" />
    <html:hidden name="fil050Form" property="fil100ChkOnOff" />

    <html:hidden name="fil050Form" property="fil100SltCabinetKbn" />
    <html:hidden name="fil050Form" property="fil100ChkTrgDeleted" />
    <html:hidden name="fil050Form" property="fil100ChkTrgDeletedFolder" />
    <html:hidden name="fil050Form" property="fil100SearchTradeTarget" />
    <html:hidden name="fil050Form" property="fil100SearchTradeMoneyNoset" />
    <html:hidden name="fil050Form" property="fil100SearchTradeMoneyKbn" />
    <html:hidden name="fil050Form" property="fil100SearchTradeMoney" />
    <html:hidden name="fil050Form" property="fil100SearchTradeMoneyTo" />
    <html:hidden name="fil050Form" property="fil100SearchTradeMoneyType" />
    <html:hidden name="fil050Form" property="fil100SearchTradeMoneyJudge" />
    <html:hidden name="fil050Form" property="fil100SearchTradeDateKbn" />
    <html:hidden name="fil050Form" property="fil100SearchTradeDateFrom" />
    <html:hidden name="fil050Form" property="fil100SearchTradeDateTo" />

    <html:hidden name="fil050Form" property="fil100SvSltCabinetSid" />
    <html:hidden name="fil050Form" property="fil100SvChkTrgFolder" />
    <html:hidden name="fil050Form" property="fil100SvChkTrgFile" />
    <html:hidden name="fil050Form" property="fil100SvChkTrgDeleted" />
    <html:hidden name="fil050Form" property="fil100SvChkTrgDeletedFolder" />
    <html:hidden name="fil050Form" property="fil100SvSearchMode" />
    <html:hidden name="fil050Form" property="fil100SvChkWdTrgName" />
    <html:hidden name="fil050Form" property="fil100SvChkWdTrgBiko" />
    <html:hidden name="fil050Form" property="fil100SvChkWdTrgText" />
    <html:hidden name="fil050Form" property="fil100SvChkWdKeyWord" />
    <html:hidden name="fil050Form" property="fileSvSearchfromYear" />
    <html:hidden name="fil050Form" property="fileSvSearchfromMonth" />
    <html:hidden name="fil050Form" property="fileSvSearchfromDay" />
    <html:hidden name="fil050Form" property="fileSvSearchtoYear" />
    <html:hidden name="fil050Form" property="fileSvSearchtoMonth" />
    <html:hidden name="fil050Form" property="fileSvSearchtoDay" />
    <html:hidden name="fil050Form" property="fil100SvChkOnOff" />
    <html:hidden name="fil050Form" property="fil100sortKey" />
    <html:hidden name="fil050Form" property="fil100orderKey" />
    <html:hidden name="fil050Form" property="fil100pageNum1" />
    <html:hidden name="fil050Form" property="fil100pageNum2" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeTarget" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeMoney" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeMoneyTo" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeMoneyType" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeMoneyJudge" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeMoneyNoset" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeMoneyKbn" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeDateFrom" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeDateTo" />
    <html:hidden name="fil050Form" property="fil100SvSearchTradeDateKbn" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    
    <logic:equal name="fil050Form" property="fil050DspMode" value="<%= DspModeHist %>">
      <logic:equal name="fil050Form" property="fil010DspCabinetKbn" value="<%= cabinetKbnErrl %>">
        <% pgid = String.valueOf(1); %>
      </logic:equal>
    </logic:equal>
    <input type="hidden" name="helpPrm" value="<bean:write name="fil050Form" property="fil050DspMode" /><%= pgid %>">
    

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
          <gsmsg:write key="fil.54" />
        </li>
        <li>
          <div>
            <logic:equal name="fil050Form" property="fil050EditAuthKbn" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="MoveToFolderEdit();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil050back');">
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

      <logic:equal name="fil050Form" property="logicalDelKbn" value="true">
        <div class="cl_fontWarn txt_l"><gsmsg:write key="cmn.comments" /><gsmsg:write key="fil.fil050.5" /></div>
      </logic:equal>

      <table class="table-left" border="0" cellpadding="5">
        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.folder" />
            </span>
          </th>
          <td class="txt_l">
            <img class="classic-display" src="../common/images/classic/icon_folder.png" border="0" alt="">
            <img class="original-display" src="../common/images/original/icon_folder_box.png" border="0" alt="">
            <span>
              <bean:write name="fil050Form" property="fil050FolderPath" />
            </span>
            <logic:notEqual name="fil050Form" property="logicalDelKbn" value="true">
              &nbsp;
              <button type="button" value="<gsmsg:write key="cmn.show" />" class="baseBtn" onClick="MoveToFolderDsp();">
                <gsmsg:write key="cmn.show" />
              </button>
             </logic:notEqual>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="w80 txt_l">
            <span>
              <bean:write name="fil050Form" property="fil050Biko" filter="false" />
            </span>
          </td>
        </tr>
        <logic:notEqual name="fil050Form" property="logicalDelKbn" value="true">
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="fil.2" />
              </span>
            </th>

            <td class="w80 txt_l">
              <logic:equal name="fil050Form" property="fil050ShortcutKbn" value="<%=shortcutOff%>">
                <span>
                  <gsmsg:write key="fil.105" />
                </span>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('shortcutOn');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_scut.png" alt="<gsmsg:write key="cmn.add" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_shortcut.png" alt="<gsmsg:write key="cmn.add" />">
                  <gsmsg:write key="cmn.add" />
                </button>
              </logic:equal>

              <logic:equal name="fil050Form" property="fil050ShortcutKbn" value="<%=shortcutOn%>">
                <span>
                  <gsmsg:write key="fil.106" />
                </span>
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('shortcutOff');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:equal>
            </td>
          </tr>
        </logic:notEqual>
        <logic:notEqual name="fil050Form" property="logicalDelKbn" value="true">
          <logic:equal name="fil050Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
            <tr>
              <th class="w20 no_w">
                <span>
                  <gsmsg:write key="fil.1" />
                </span>
              </th>
              <td class="w80 txt_l">
                <div class="verAlignMid">
                <logic:equal name="fil050Form" property="fil050CallKbn" value="<%=callOff%>">
                  <span>
                    <gsmsg:write key="cmn.invalid" />
                  </span>
                  <button type="button" class="baseBtn" value="<gsmsg:write key="fil.fil050.2" />" onclick="buttonPush('callOn');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_beru.png" alt="<gsmsg:write key="fil.fil050.2" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png" alt="<gsmsg:write key="fil.fil050.2" />">
                    <gsmsg:write key="fil.fil050.2" />
                  </button>
                </logic:equal>

                <logic:equal name="fil050Form" property="fil050CallKbn" value="<%=callOn%>">
                  <span>
                    <gsmsg:write key="cmn.effective" />
                  </span>
                  <button type="button" class="baseBtn ml5" value="<gsmsg:write key="fil.20" />" onclick="buttonPush('callOff');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="fil.20" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="fil.20" />">
                    <gsmsg:write key="fil.20" />
                  </button>
                </logic:equal>
                <span class="verAlignMid">
                  <html:checkbox name="fil050Form" property="fil050CallLevelKbn" value="1" styleClass="ml5" styleId="ikkatuCheck" />
                  <label for="ikkatuCheck">
                    <gsmsg:write key="fil.7" />
                  </label>
                </span>
                </div>
              </td>
            </tr>
          </logic:equal>
        </logic:notEqual>
      </table>
      <logic:equal name="fil050Form" property="fil050DspMode" value="0">
        <!--更新履歴-->

        <ul class="tabHeader w100 hp30">
          <li class="tabHeader_tab-on mwp100 pl10 pr10 pt5 pb5 js_tab border_bottom_none bgI_none">
            <gsmsg:write key="fil.fil020.2" />
          </li>
          <li class="tabHeader_tab-off mwp100 pl10 pr10 pt5 pb5 js_tab border_bottom_none" onclick="fil050TabChange('fil050tabChange', '1');">
            <gsmsg:write key="fil.fil020.3" />
          </li>
          <li class="tabHeader_space border_bottom_none"></li>
          <li>
            <bean:size id="count1" name="fil050Form" property="fil050PageLabel" scope="request" />
            <logic:greaterThan name="count1" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="fil050PageNum1" onchange="fil050changePage(1);" styleClass="paging_combo">
                  <html:optionsCollection name="fil050Form" property="fil050PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
            </logic:greaterThan>
          </li>
        </ul>

        <logic:empty name="fil050Form" property="fil050RekiList">
          <table class="table-left mt0" border="0" cellpadding="5">
            <tr>
              <td>
                <span>
                  <gsmsg:write key="fil.fil020.4" />
                </span>
              </td>
          </table>
        </logic:empty>
        <logic:notEmpty name="fil050Form" property="fil050RekiList">
          <bean:define id="nameWidth" value="w40" type="String" />
          <logic:equal name="fil050Form" property="cabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
            <bean:define id="nameWidth" value="w20" type="String" />
          </logic:equal>

          <table class="table-top mt0" border="0" cellpadding="5">

            <tr>
              <th class="w10 no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="0">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(0, 1);">
                      <span>
                        <gsmsg:write key="cmn.update.day.hour" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(0, 0);">
                      <span>
                        <gsmsg:write key="cmn.update.day.hour" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="0">
                  <a href="#!" onClick="return fil050TitleClick(0, 1);">
                    <span>
                      <gsmsg:write key="cmn.update.day.hour" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w15 no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="1">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(1, 1);">
                      <span>
                        <gsmsg:write key="cmn.update.user" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(1, 0);">
                      <span>
                        <gsmsg:write key="cmn.update.user" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="1">
                  <a href="#!" onClick="return fil050TitleClick(1, 1);">
                    <span>
                      <gsmsg:write key="cmn.update.user" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="<%=nameWidth %> no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="2">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(2, 1);">
                      <span>
                        <gsmsg:write key="fil.9" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(2, 0);">
                      <span>
                        <gsmsg:write key="fil.9" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="2">
                  <a href="#!" onClick="return fil050TitleClick(2, 1);">
                    <span>
                      <gsmsg:write key="fil.9" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <logic:equal name="fil050Form" property="cabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">

              <th class="w5 no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="5">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(5, 1);">
                      <span>
                        <gsmsg:write key="fil.fil080.8" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(5, 0);">
                      <span>
                        <gsmsg:write key="fil.fil080.8" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="5">
                  <a href="#!" onClick="return fil050TitleClick(5, 1);">
                    <span>
                      <gsmsg:write key="fil.fil080.8" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w15 no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="6">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(6, 1);">
                      <span>
                        <gsmsg:write key="fil.fil030.18" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(6, 0);">
                      <span>
                        <gsmsg:write key="fil.fil030.18" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="6">
                  <a href="#!" onClick="return fil050TitleClick(6, 1);">
                    <span>
                      <gsmsg:write key="fil.fil030.18" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w10 no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="7">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(7, 1);">
                      <span>
                        <gsmsg:write key="fil.fil080.5" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(7, 0);">
                      <span>
                        <gsmsg:write key="fil.fil080.5" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="7">
                  <a href="#!" onClick="return fil050TitleClick(7, 1);">
                    <span>
                      <gsmsg:write key="fil.fil080.5" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              </logic:equal>

              <th class="w2 no_w cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="3">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(3, 1);">
                      <span>
                        <gsmsg:write key="cmn.operations" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(3, 0);">
                      <span>
                        <gsmsg:write key="cmn.operations" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="3">
                  <a href="#!" onClick="return fil050TitleClick(3, 1);">
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

              <logic:equal name="fil050Form" property="fil050EditAuthKbn" value="1">
                <th class="w2 no_w">
                  <span>
                    <gsmsg:write key="fil.12" />
                  </span>
                </th>
              </logic:equal>
              <logic:notEqual name="fil050Form" property="fil050EditAuthKbn" value="1">
                <logic:equal name="fil050Form" property="fil050RepairDspFlg" value="1">
                  <th class="w2 no_w">
                    <span>
                      <gsmsg:write key="fil.12" />
                    </span>
                  </th>
                </logic:equal>
              </logic:notEqual>
            </tr>

            <logic:iterate id="fil050Model" name="fil050Form" property="fil050RekiList" indexId="idx">

              <% String trElement = "", clickClass = ""; %>
              <bean:define id="fileName" name="fil050Model" property="ffrName" />
              <logic:notEqual name="fil050Model" property="binSid" value="0">
                <bean:define id="listBinSid" name="fil050Model" property="binSid" type="java.lang.Long" />
                <%
                  String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1));
                  trElement = " class=\"js_listHover cursor_p\" data-sid=\"" + listBinSid.longValue() + "\" data-extension=\"" + extension + "\"";
                  clickClass = " js_listClick";
                %>
              </logic:notEqual>

              <tr<%= trElement %>>
                <td class="txt_c no_w <%= clickClass %>">
                  <bean:write name="fil050Model" property="ffrEdate" />
                </td>
                <td class="txt_l no_w <%= clickClass %>">
                  <logic:equal name="fil050Model" property="usrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                    <span class="delete_border">
                      <bean:write name="fil050Model" property="usrSeiMei" />
                    </span>
                  </logic:equal>
                  <logic:notEqual name="fil050Model" property="usrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                    <bean:define id="ukoFlg" name="fil050Model" property="usrUkoFlg" type="Integer" />
                    <span class="<%=UserUtil.getCSSClassNameNormal(ukoFlg)%>">
                      <bean:write name="fil050Model" property="usrSeiMei" />
                    </span>
                  </logic:notEqual>
                </td>
                <td class="txt_l">

                  <logic:notEqual name="fil050Model" property="binSid" value="0">
                    <span class="cl_linkDef <%= clickClass %>">
                      <bean:write name="fil050Model" property="ffrName" />
                    </span>
                    <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                    <span class="ml5 cursor_p">
                      <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                      <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                    </span>
                    <% } %>
                  </logic:notEqual>

                  <logic:equal name="fil050Model" property="binSid" value="0">
                    <span class="cl_fontWeek <%= clickClass %>">
                      <bean:write name="fil050Model" property="ffrName" />
                    </span>
                  </logic:equal>

                </td>
                <logic:equal name="fil050Form" property="cabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
                  <td class="txt_c <%= clickClass %>">
                    <bean:write name="fil050Model" property="fdrTradeDate" />
                  </td>
                  <td class="txt_l <%= clickClass %>">
                    <bean:write name="fil050Model" property="fdrTradeTarget" />
                  </td>
                  <logic:equal name="fil050Model" property="fdrTradeMoneykbn" value="0">
                    <td class="txt_c">
                      -
                    </td>
                  </logic:equal>
                  <logic:notEqual name="fil050Model" property="fdrTradeMoneykbn" value="0">
                    <td class="txt_r no_w">
                      <bean:write name="fil050Model" property="fdrTradeMoney" />&nbsp;<bean:write name="fil050Model" property="fmmName" />
                    </td>
                  </logic:notEqual>
                </logic:equal>

                <td class="txt_c no_w<%= clickClass %>">
                  <span>
                    <logic:equal name="fil050Model" property="ffrKbn" value="<%=rekiKbnNew%>">
                      <gsmsg:write key="cmn.new" />
                    </logic:equal>
                    <logic:equal name="fil050Model" property="ffrKbn" value="<%=rekiKbnUpdate%>">
                      <gsmsg:write key="cmn.update" />
                    </logic:equal>
                    <logic:equal name="fil050Model" property="ffrKbn" value="<%=rekiKbnRepair%>">
                      <gsmsg:write key="fil.12" />
                    </logic:equal>
                    <logic:equal name="fil050Model" property="ffrKbn" value="<%=rekiKbnDelete%>">
                      <gsmsg:write key="cmn.delete" />
                    </logic:equal>
                  </span>
                </td>

                <td class="txt_l<%= clickClass %>">
                  <span>
                    <bean:write name="fil050Model" property="ffrUpCmt" filter="false" />
                  </span>
                </td>

                <logic:equal name="fil050Form" property="fil050EditAuthKbn" value="1">
                  <td class="txt_c no_w">
                    <logic:notEqual name="fil050Form" property="logicalDelKbn" value="true">

                      <logic:equal name="fil050Model" property="repairBtnDspFlg" value="true">
                        <button type="button" value="<gsmsg:write key="fil.fil070.2" />" class="baseBtn" onClick="fil050RepairClick(<bean:write name="fil050Model" property="fdrSid" />, <bean:write name="fil050Model" property="ffrVersion" />);">
                          <gsmsg:write key="fil.fil070.2" />
                        </button>
                      </logic:equal>
                    </logic:notEqual>
                  </td>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050EditAuthKbn" value="1">
                  <logic:equal name="fil050Form" property="fil050RepairDspFlg" value="1">
                    <td class="txt_c no_w">
                      <logic:notEqual name="fil050Form" property="logicalDelKbn" value="true">

                        <logic:equal name="fil050Model" property="repairBtnDspFlg" value="true">
                          <button type="button" value="<gsmsg:write key="fil.fil070.2" />" class="baseBtn" onClick="fil050RepairClick(<bean:write name="fil050Model" property="fdrSid" />, <bean:write name="fil050Model" property="ffrVersion" />);">
                            <gsmsg:write key="fil.fil070.2" />
                          </button>
                        </logic:equal>
                      </logic:notEqual>
                    </td>
                  </logic:equal>
                </logic:notEqual>
              </tr>
            </logic:iterate>
          </table>
          <bean:size id="count2" name="fil050Form" property="fil050PageLabel" scope="request" />
          <logic:greaterThan name="count2" value="1">
              <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="fil050PageNum2" onchange="fil050changePage(2);" styleClass="paging_combo">
                  <html:optionsCollection name="fil050Form" property="fil050PageLabel" value="value" label="label" />
                </html:select>
                <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                  <i class="icon-paging_right"></i>
                </button>
              </div>
          </logic:greaterThan>

        </logic:notEmpty>
      </logic:equal>

      <logic:equal name="fil050Form" property="fil050DspMode" value="1">
        <!--アクセス制限-->

          <ul class="tabHeader w100 hp30">
          <li class="tabHeader_tab-off mwp100 pl10 pr10 pt5 pb5 js_tab border_bottom_none" onclick="fil050TabChange('fil050tabChange', '0');">
            <gsmsg:write key="fil.fil020.2" />
          </li>
          <li class="tabHeader_tab-on mwp100 pl10 pr10 pt5 pb5 js_tab border_bottom_none bgI_none">
            <gsmsg:write key="fil.fil020.3" />
          </li>
          <li class="tabHeader_space border_bottom_none"></li>
          <li>
            <bean:size id="count1" name="fil050Form" property="fil050PageLabel" scope="request" />
              <logic:greaterThan name="count1" value="1">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td class="txt_r">
                    <div class="paging">
                      <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                        <i class="icon-paging_left"></i>
                      </button>
                      <html:select property="fil050PageNum1" onchange="fil050changePage(1);" styleClass="paging_combo">
                        <html:optionsCollection name="fil050Form" property="fil050PageLabel" value="value" label="label" />
                      </html:select>
                      <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                        <i class="icon-paging_right"></i>
                      </button>
                    </div>
                  </td>
                </tr>
              </table>
            </logic:greaterThan>
          </li>
        </ul>

        <logic:empty name="fil050Form" property="fil050AccessList">
          <table class="table-left mt0" border="0" cellpadding="5">
            <tr>
              <td>
                <span>
                  <gsmsg:write key="fil.fil020.5" />
                </span>
              </td>
          </table>
        </logic:empty>
        <logic:notEmpty name="fil050Form" property="fil050AccessList">
          <table class="table-top mt0" border="0" cellpadding="5">
            <tr>

              <th class="w20 cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="2">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(2, 1);">
                      <span>
                        <gsmsg:write key="cmn.employee.staff.number" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(2, 0);">
                      <span>
                        <gsmsg:write key="cmn.employee.staff.number" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="2">
                  <a href="#!" onClick="return fil050TitleClick(2, 1);">
                    <span>
                      <gsmsg:write key="cmn.employee.staff.number" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w35 cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="1">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(1, 1);">
                      <span>
                        <gsmsg:write key="cmn.name" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(1, 0);">
                      <span>
                        <gsmsg:write key="cmn.name" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="1">
                  <a href="#!" onClick="return fil050TitleClick(1, 1);">
                    <span>
                      <gsmsg:write key="cmn.name" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w25 cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="3">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(3, 1);">
                      <span>
                        <gsmsg:write key="cmn.post" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(3, 0);">
                      <span>
                        <gsmsg:write key="cmn.post" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="3">
                  <a href="#!" onClick="return fil050TitleClick(3, 1);">
                    <span>
                      <gsmsg:write key="cmn.post" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

              <th class="w20 cursor_p">
                <logic:equal name="fil050Form" property="fil050SortKey" value="5">
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="0">
                    <a href="#!" onClick="return fil050TitleClick(5, 1);">
                      <span>
                        <gsmsg:write key="cmn.edit.permissions" />
                        <span class="classic-display">▲</span>
                        <i class="original-display icon-sort_up"></i>
                      </span>
                    </a>
                  </logic:equal>
                  <logic:equal name="fil050Form" property="fil050OrderKey" value="1">
                    <a href="#!" onClick="return fil050TitleClick(5, 0);">
                      <span>
                        <gsmsg:write key="cmn.edit.permissions" />
                        <span class="classic-display">▼</span>
                        <i class="original-display icon-sort_down"></i>
                      </span>
                    </a>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="fil050Form" property="fil050SortKey" value="5">
                  <a href="#!" onClick="return fil050TitleClick(5, 1);">
                    <span>
                      <gsmsg:write key="cmn.edit.permissions" />
                    </span>
                  </a>
                </logic:notEqual>
              </th>

            </tr>

            <logic:iterate id="accessMdl" name="fil050Form" property="fil050AccessList" indexId="idx" type="FileDAccessUserModel">
              <tr>
                <td class="txt_l">
                  <span class=" <%=UserUtil.getCSSClassNameNormal(accessMdl.getUsrUkoFlg())%>">
                    <bean:write name="accessMdl" property="usiSyainNo" />
                  </span>
                </td>
                <td class="txt_l">
                  <span class="<%=UserUtil.getCSSClassNameNormal(accessMdl.getUsrUkoFlg())%>">
                    <bean:write name="accessMdl" property="usiSei" />
                    &nbsp;
                    <bean:write name="accessMdl" property="usiMei" />
                  </span>
                </td>
                <td class="txt_l">
                  <span class="<%=UserUtil.getCSSClassNameNormal(accessMdl.getUsrUkoFlg())%>">
                    <bean:write name="accessMdl" property="usiYakusyoku" />
                  </span>
                </td>
                <td class="txt_c">
                  <logic:equal name="accessMdl" property="accessKbn" value="0">
                    <img class="classic-display" src="../file/images/classic/18_icon_stop.png" alt="">
                    <img class="original-display" src="../file/images/original/icon_stop.png" alt="">
                  </logic:equal>
                  <logic:equal name="accessMdl" property="accessKbn" value="1">
                  </logic:equal>
                </td>
              </tr>
            </logic:iterate>

          </table>

        </logic:notEmpty>
        <table width="100%" cellpadding="0" cellspacing="0">
          <tr>
            <td align="left">
              <img class="classic-display" src="../file/images/classic/18_icon_stop.png" alt="">
              <img class="original-display" src="../file/images/original/icon_stop.png" alt="">
              <span>：<gsmsg:write key="fil.fil020.6" /></span>
            </td>
            <td>
              <bean:size id="count2" name="fil050Form" property="fil050PageLabel" scope="request" />
              <logic:greaterThan name="count2" value="1">
                  <div class="paging">
                <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                  <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                  <i class="icon-paging_left"></i>
                </button>
                <html:select property="fil050PageNum2" onchange="fil050changePage(2);" styleClass="paging_combo">
                  <html:optionsCollection name="fil050Form" property="fil050PageLabel" value="value" label="label" />
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
      <div class="txt_l">
        <span>URL:</span>
        <input type="text" value="<bean:write name='fil050Form' property='fil050FolderUrl' />" readOnly="true" class="wp500" />
      </div>

      <div class="footerBtn_block">
        <logic:equal name="fil050Form" property="fil050EditAuthKbn" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="MoveToFolderEdit();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil050back');">
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