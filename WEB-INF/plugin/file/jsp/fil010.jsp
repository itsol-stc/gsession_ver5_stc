<%@page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.49" /></title>

<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmnPic.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/dtree.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil010.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../file/css/file.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css" />

</head>
<body onload="initPicture('iconImage', 30);selectCabinet();">
  <html:form action="/file/fil010">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->
    <input type="hidden" name="CMD" value="fil010search">
    <input type="hidden" name="cmnMode" value="">
    <input type="hidden" name="backDsp" value="">
    <input type="hidden" name="backDspLow" value="">
    <input type="hidden" name="backDspCall" value="">
    <input type="hidden" name="fil010SelectCabinet" value="">
    <input type="hidden" name="fil010SelectDirSid" value="">
    <html:hidden name="fil010Form" property="fil010binSid" />
    <input type="hidden" name="fil070DirSid" value="">
    <html:hidden property="fileSid" />
    <html:hidden property="fil010DspCabinetKbn" />
    <html:hidden property="detailCabinet" />

    <div class="pageTitle">
      <%-- タイトル --%>
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li>
          <gsmsg:write key="cmn.filekanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="fil.49" />
        </li>
        <li>
          <%-- 取引情報登録ボタン --%>
          <logic:equal name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
          <logic:equal name="fil010Form" property="canEditNotEntryFile" value="<%=String.valueOf(GSConstFile.NOT_ENTRY_FILE_EXIST)%>">
            <button type="button" class="baseBtn btn_classicImg-display" value="<gsmsg:write key="fil.fil010.8" />" onClick="buttonPush('tradeTouroku');">
                <img src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="fil.fil010.8" />
              </button>
              <button type="button" class="baseBtn btn_originalImg-display wp100" value="<gsmsg:write key="fil.fil010.8" />" onClick="buttonPush('tradeTouroku');">
                <img src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="fil.fil010.8" />
              </button>
          </logic:equal>
          </logic:equal>
          <%-- キャビネット作成ボタン --%>
          <logic:equal name="fil010Form" property="fil010DspCabinetAddBtn" value="<%=GSConstFile.DSP_KBN_ON%>">
            <logic:notEqual name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
              <button type="button" class="baseBtn btn_classicImg-display" value="<gsmsg:write key="fil.64" />" onClick="CreateCabinet();">
                <img src="../common/images/classic/icon_add_folder.png" alt="<gsmsg:write key="cmn.list" />">
                <gsmsg:write key="fil.64" />
              </button>
              <button type="button" class="baseBtn btn_originalImg-display wp100" value="<gsmsg:write key="fil.64" />" onClick="CreateCabinet();">
                <img src="../file/images/original/icon_cabinet_add_18.png" alt="<gsmsg:write key="cmn.list" />">
                <gsmsg:write key="fil.64" />
              </button>
            </logic:notEqual>
          </logic:equal>
        </li>
      </ul>
    </div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>
    <div class="wrapper_2column">
      <div class="side_multi-left bgC_none">
        <table class="table-top mt0">
          <tr>
            <th class="w100 txt_l">
              <gsmsg:write key="fil.fil010.6" />
            </th>
          </tr>
          <logic:equal name="fil010Form" property="personalCabAuthFlg" value="<%=String.valueOf(GSConstFile.CABINET_PRIVATE_USE)%>">
            <logic:equal name="fil010Form" property="dspPersonalCabFlg" value="<%=String.valueOf(GSConstFile.DSP_PERSONAL_CAB)%>">
              <tr>
                <td class="p0">
                  <div class="js_search_menu_title searchMenu_title w100" id="1">
                    <gsmsg:write key="fil.142" />
                  </div>
                </td>
              </tr>
            </logic:equal>
          </logic:equal>
          <tr>
            <td class="p0">
              <div class="js_search_menu_title searchMenu_title w100" id="0">
                <gsmsg:write key="fil.141" />
              </div>
            </td>
          </tr>
          <tr>
            <td class="p0">
              <div class="js_search_menu_title searchMenu_title w100" id="2">
                <gsmsg:write key="fil.147" />
                <logic:equal name="fil010Form" property="canEditNotEntryFile" value="<%=String.valueOf(GSConstFile.NOT_ENTRY_FILE_EXIST)%>">
                  <div class="flo_r">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_tempreg.png">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_tempreg.png">
                  </div>
                </logic:equal>
              </div>
            </td>
          </tr>
        </table>
        <logic:equal name="fil010Form" property="shortcutCallListFlg" value="0">
          <%-- ショートカット --%>
          <logic:notEmpty name="fil010Form" property="shortcutList" scope="request">
          <table class="table-top mt10">
            <tr>
              <th colspan="2">
                <div class="component_bothEnd">
                  <div>
                    <gsmsg:write key="fil.2" />
                  </div>
                  <div>
                    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('fil010scDelete');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                      <gsmsg:write key="cmn.delete" />
                    </button>
                  </div>
                </div>
              </th>
            </tr>
            <logic:iterate id="shortBean" name="fil010Form" property="shortcutList" scope="request" indexId="idx">
            <tr class="js_listHover">
              <td class="txt_c js_tableTopCheck cursor_p">
                <bean:define id="day" name="shortBean" property="directorySid" type="java.lang.Integer" />
                <html:multibox property="fil010SelectDelLink" value="<%=Integer.toString(day.intValue())%>" />
              </td>
              <logic:equal name="shortBean" property="directoryKbn" value="0">
              <td class="fs_13 w100">
                <img class="btn_classicImg-display cursor_p" src="../file/images/classic/icon_shortcut_folder.gif" alt="<gsmsg:write key="cmn.folder" />" onclick="MoveToFolderList(<bean:write name="shortBean" property="cabinetSid" />, <bean:write name="shortBean" property="directorySid" />);">
                <img class="btn_originalImg-display cursor_p" src="../file/images/original/icon_folder_shortcut.png" alt="<gsmsg:write key="cmn.folder" />" onclick="MoveToFolderList(<bean:write name="shortBean" property="cabinetSid" />, <bean:write name="shortBean" property="directorySid" />);">
                <span class="cl_linkDef cursor_p" onclick="MoveToFolderList(<bean:write name="shortBean" property="cabinetSid" />, <bean:write name="shortBean" property="directorySid" />);">
              </logic:equal>
              <logic:equal name="shortBean" property="directoryKbn" value="1">
              <td class="fs_13 w100">
                <img class="btn_classicImg-display" src="../file/images/classic/icon_shortcut_file.png" alt="<gsmsg:write key="cmn.file" />" onclick="fileDl('fileDownload', '<bean:write name="shortBean" property="binSid" />');">
                <img class="btn_originalImg-display" src="../file/images/original/icon_file_shortcut.png" alt="<gsmsg:write key="cmn.file" />" onclick="fileDl('fileDownload', '<bean:write name="shortBean" property="binSid" />');">
                <span class="cl_linkDef cursor_p" onclick="fileDl('fileDownload', '<bean:write name="shortBean" property="binSid" />');">
              </logic:equal>
                  <bean:write name="shortBean" property="directoryFullPathName" />
                </span>
                <bean:define id="fileName" name="shortBean" property="directoryFullPathName" />
                <bean:define id="fileSid" name="shortBean" property="binSid" />
                <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
                <span class="ml5 cursor_p" onclick="openPreviewWindow('../file/fil010.do?CMD=fileDownloadInline&fileSid=<%= fileSid %>', '<%= extension %>');">
                  <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                  <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                </span>
                <% } %>
              </td>
            </tr>
            </logic:iterate>
          </table>
          </logic:notEmpty>
          <%-- 更新通知 --%>
          <logic:notEmpty name="fil010Form" property="callList" scope="request">
            <logic:empty name="fil010Form" property="shortcutList" scope="request">
              <table class="table-top mt0">
            </logic:empty>
            <logic:notEmpty name="fil010Form" property="shortcutList" scope="request">
              <table class="table-top">
            </logic:notEmpty>
                <tr>
                  <th colspan="2">
                    <div class="component_bothEnd">
                      <div>
                        <gsmsg:write key="fil.1" />
                      </div>
                      <div>
                        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.list" />" onClick="MoveToCallList();">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_list.png" alt="<gsmsg:write key="cmn.list" />">
                          <gsmsg:write key="cmn.list" />
                        </button>
                      </div>
                    </div>
                  </th>
                </tr>
              <logic:iterate id="callBean" name="fil010Form" property="callList" scope="request" indexId="idx">
                <% String callScript = "MoveToFileDetail"; %>
                <logic:notEqual name="callBean" property="directoryKbn" value="0">
                  <tr class="fs_13 js_listHover">
                </logic:notEqual>
                <logic:equal name="callBean" property="directoryKbn" value="0">
                  <% callScript = "MoveToFolderDetail"; %>
                  <tr class="fs_13 js_listHover">
                </logic:equal>
                    <td class="txt_c cursor_p" onclick="<%= callScript %>(<bean:write name="callBean" property="cabinetSid" />,<bean:write name="callBean" property="directorySid" />);">
                      <logic:notEqual name="callBean" property="fcbMark" value="0">
                        <img name="iconImage" class="wp30" src="../file/fil010.do?CMD=tempview&fil010SelectCabinet=<bean:write name="callBean" property="cabinetSid" />&fil010binSid=<bean:write name="callBean" property="fcbMark" />" name="pctImage<bean:write name="callBean" property="fcbMark" />">
                      </logic:notEqual>
                      <logic:equal name="callBean" property="fcbMark" value="0">
                        <logic:equal name="callBean" property="directoryKbn" value="0">
                          <img class="btn_classicImg-display" src="../file/images/classic/icon_new_folder.gif">
                          <span class="labelNew original-display txt_t no_w">
                            <gsmsg:write key="bbs.bbsMain.6" />
                          </span>
                        </logic:equal>
                        <logic:notEqual name="callBean" property="directoryKbn" value="0">
                          <img class="btn_classicImg-display" src="../file/images/classic/icon_new_file.gif">
                          <span class="labelNew original-display txt_t no_w">
                            <gsmsg:write key="bbs.bbsMain.6" />
                          </span>
                        </logic:notEqual>
                      </logic:equal>
                    </td>
                    <td class="fs_13">
                      <bean:define id="dirFullPath" name="callBean" property="directoryFullPathName" />
                      <span class="cl_linkDef cursor_p"  onclick="<%= callScript %>(<bean:write name="callBean" property="cabinetSid" />,<bean:write name="callBean" property="directorySid" />);">
                        <bean:write name="callBean" property="directoryFullPathName" />
                      </span>
                      <bean:define id="fileSid" name="callBean" property="binSid" />
                      <% if (String.valueOf(dirFullPath).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                      <% String extension = String.valueOf(dirFullPath).substring((String.valueOf(dirFullPath).lastIndexOf(".") + 1)); %>
                        <span class="ml5 cursor_p" onclick="openPreviewWindow('../file/fil010.do?CMD=fileDownloadInline&fileSid=<%= fileSid %>', '<%= extension %>');">
                        <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                        <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                        </span>
                      <% } %>

                      <br>
                      <span class="cl_fontMiddle fs_11">
                        <gsmsg:write key="cmn.update" />
                        ：
                        <bean:write name="callBean" property="directoryUpdateStr" />
                      </span>
                    </td>
                  </tr>
              </logic:iterate>
              </table>
          </logic:notEmpty>
        </logic:equal>
      </div>
      <% String rightAreaClass = "main"; %>
      <logic:equal name="fil010Form" property="shortcutCallListFlg" value="1">
        <% rightAreaClass +=" w100"; %>
      </logic:equal>
      <div class="<%= rightAreaClass %>">

        <%-- ボタンなど --%>
        <div class="txt_r">
          <%-- 検索 --%>
          <html:text name="fil010Form" maxlength="50" property="filSearchWd" styleClass="wp150 hp22" />
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="MoveToSearch();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="cmn.search" />
          </button>
        </div>
        <div>
          <%-- マイキャビネット --%>
          <logic:equal name="fil010Form" property="personalCabAuthFlg" value="<%=String.valueOf(GSConstFile.CABINET_PRIVATE_USE)%>">
          <logic:equal name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
            <table class="table-top">
              <%-- 表示できるマイキャビネットがない場合 --%>
              <logic:empty name="fil010Form" property="myCabinet">
                <tr>
                  <th class="w75 cl_fontOutline">
                    <gsmsg:write key="fil.140" />
                  </th>
                </tr>
                <tr>
                  <td class="txt_c">
                    <button type="button" class="baseBtn" value="<gsmsg:write key="fil.152" />" onClick="CreateMyCabinet();">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_add_folder.png" alt="<gsmsg:write key="fil.152" />">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_add_folder.png" alt="<gsmsg:write key="fil.141" />">
                      <gsmsg:write key="fil.152" />
                    </button>
                  </td>
                </tr>
              </logic:empty>
              <%-- 表示できるマイキャビネットが存在する場合 --%>
              <logic:notEmpty name="fil010Form" property="myCabinet">
                <bean:define id="myCabinet" name="fil010Form" property="myCabinet" />
                <tr>
                  <th class="w5">&nbsp;</th>
                  <th class="w70 cl_fontOutline">
                    <gsmsg:write key="fil.140" />
                  </th>
                  <th class="w20 cl_fontOutline">
                    <gsmsg:write key="fil.fil010.1" />/<gsmsg:write key="fil.fil010.2" />
                  </th>
                  <th class="w5 cl_fontOutline">
                    <gsmsg:write key="cmn.detail" />
                  </th>
                </tr>
                <tr class="js_listHover" data-fcbsid="<bean:write name="myCabinet" property="fcbSid" />" data-rootsid="<bean:write name="myCabinet" property="rootDirSid" />">
                  <td class="txt_c js_listClick cursor_p">
                    <logic:equal name="myCabinet" property="fcbMark" value="0">
                      <img class="btn_classicImg-display" src="../file/images/classic/30classic_icon_personal_cabinet.png">
                      <img class="btn_originalImg-display" src="../file/images/original/30original_icon_personal_cabinet.png">
                    </logic:equal>
                    <logic:notEqual name="myCabinet" property="fcbMark" value="0">
                      <img name="iconImage" class="wp30" src="../file/fil010.do?CMD=tempview&fil010SelectCabinet=<bean:write name="myCabinet" property="fcbSid" />&fil010binSid=<bean:write name="myCabinet" property="fcbMark" />" name="pictImage<bean:write name="myCabinet" property="fcbMark" />">
                    </logic:notEqual>
                  </td>
                  <td class="js_listClick cursor_p word_b-all">
                    <span class="cl_linkDef"><bean:write name="myCabinet" property="dspfcbName" filter="false" /></span>
                    <logic:notEqual name="myCabinet" property="accessIconKbn" value="1">
                      <img class="btn_classicImg-display" src="../file/images/classic/18_icon_stop.png">
                      <img class="btn_originalImg-display" src="../file/images/original/icon_stop.png">
                    </logic:notEqual>
                    <logic:equal name="myCabinet" property="callIconKbn" value="1">
                      <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png">
                    </logic:equal>
                    <logic:notEmpty name="myCabinet" property="dspBikoString">
                      <div class="fs_11 mt5 cl_fontMiddle">
                        <bean:write name="myCabinet" property="dspBikoString" filter="false" />
                      </div>
                    </logic:notEmpty>
                  </td>
                  <td class="js_listClick cursor_p">
                    <logic:notEqual name="myCabinet" property="diskUsedWarning" value="1">
                      <bean:write name="myCabinet" property="diskUsedString" filter="false" />
                    </logic:notEqual>
                    <logic:equal name="myCabinet" property="diskUsedWarning" value="1">
                      <span class="verAlignMid">
                        <img class="mr5 btn_classicImg-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                        <img class="mr5 btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                        <div class="cl_fontWarn"><bean:write name="myCabinet" property="diskUsedString" filter="false" /></div>
                      </span>
                    </logic:equal>
                  </td>
                  <td class="txt_c">
                    <button type="button" value="<gsmsg:write key="fil.18" />" class="baseBtn no_w" onclick="personalCabinetDetail(<bean:write name="myCabinet" property="fcbSid" />);">
                      <gsmsg:write key="fil.18" />
                    </button>
                  </td>
                </tr>
              </logic:notEmpty>
            </table>
          </logic:equal>
          </logic:equal>
          <%-- 共有キャビネット or 電帳法キャビネット --%>
          <logic:notEmpty name="fil010Form" property="cabinetList" scope="request">
            <logic:notEqual name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
              <table class="table-top">
                <tr>
                  <th class="w5">&nbsp;</th>
                  <th class="w70 cl_fontOutline">
                    <logic:equal name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                      <gsmsg:write key="fil.141" />
                    </logic:equal>
                    <logic:equal name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
                      <gsmsg:write key="fil.147" />
                    </logic:equal>
                  </th>
                  <th class="w20 cl_fontOutline">
                    <gsmsg:write key="fil.fil010.1" />
                    /
                    <gsmsg:write key="fil.fil010.2" />
                  </th>
                  <th class="w5 cl_fontOutline">
                    <gsmsg:write key="cmn.detail" />
                  </th>
                </tr>
                <logic:iterate id="cabBean" name="fil010Form" property="cabinetList" scope="request" indexId="idx">
                  <tr class="js_listHover" data-fcbsid="<bean:write name="cabBean" property="fcbSid" />" data-rootsid="<bean:write name="cabBean" property="rootDirSid" />">
                    <td class="txt_c js_listClick cursor_p">
                      <logic:equal name="cabBean" property="fcbMark" value="0">
                        <img class="btn_classicImg-display" src="../file/images/classic/icon_cabinet.gif">
                        <img class="btn_originalImg-display" src="../file/images/original/icon_cabinet_32.png">
                      </logic:equal>
                      <logic:notEqual name="cabBean" property="fcbMark" value="0">
                        <img name="iconImage" class="wp30" src="../file/fil010.do?CMD=tempview&fil010SelectCabinet=<bean:write name="cabBean" property="fcbSid" />&fil010binSid=<bean:write name="cabBean" property="fcbMark" />" name="pictImage<bean:write name="cabBean" property="fcbMark" />">
                      </logic:notEqual>
                    </td>
                    <td class="js_listClick cursor_p word_b-all">
                      <span class="cl_linkDef"><bean:write name="cabBean" property="dspfcbName" filter="false" /></span>
                      <logic:equal name="cabBean" property="notEntryIconKbn" value="<%= String.valueOf(GSConstFile.NOT_ENTRY_FILE_EXIST) %>">
                      <logic:equal name="cabBean" property="accessIconKbn" value="1">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_tempreg.png">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_tempreg.png">
                      </logic:equal>
                      </logic:equal>
                      <logic:notEqual name="cabBean" property="accessIconKbn" value="1">
                        <img class="btn_classicImg-display" src="../file/images/classic/18_icon_stop.png">
                        <img class="btn_originalImg-display" src="../file/images/original/icon_stop.png">
                      </logic:notEqual>
                      <logic:equal name="cabBean" property="callIconKbn" value="1">
                        <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png">
                      </logic:equal>
                      <logic:notEmpty name="cabBean" property="dspBikoString">
                        <div class="fs_11 mt5 cl_fontMiddle">
                          <bean:write name="cabBean" property="dspBikoString" filter="false" />
                        </div>
                      </logic:notEmpty>
                    </td>
                    <td class="js_listClick cursor_p">
                      <logic:notEqual name="cabBean" property="diskUsedWarning" value="1">
                        <bean:write name="cabBean" property="diskUsedString" filter="false" />
                      </logic:notEqual>
                      <logic:equal name="cabBean" property="diskUsedWarning" value="1">
                        <span class="verAlignMid">
                          <img class="mr5 btn_classicImg-display" src="../common/images/classic/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                          <img class="mr5 btn_originalImg-display" src="../common/images/original/icon_warn.png" alt="<gsmsg:write key="cmn.warning" />">
                          <div class="cl_fontWarn"><bean:write name="cabBean" property="diskUsedString" filter="false" /></div>
                        </span>
                      </logic:equal>
                    </td>
                    <td class="txt_c">
                      <button type="button" value="<gsmsg:write key="fil.18" />" class="baseBtn" onclick="CabinetDetail(<bean:write name="cabBean" property="fcbSid" />);">
                        <span class="no_w">
                          <gsmsg:write key="fil.18" />
                        </span>
                      </button>
                    </td>
                  </tr>
                </logic:iterate>
              </table>
              <%-- 補足 --%>
              <div class="mt10 mb10">
                <span class="verAlignMid hp30">
                  <img class="btn_classicImg-display" src="../file/images/classic/18_icon_stop.png">
                  <img class="btn_originalImg-display" src="../file/images/original/icon_stop.png">
                  <div class="ml5"><gsmsg:write key="fil.fil010.3" /></div>
                </span>
                <span class="ml10 verAlignMid hp30">
                  <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png">
                  <div class="ml5"><gsmsg:write key="fil.fil010.4" /></div>
                </span>
                <logic:equal name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
                <logic:equal name="fil010Form" property="canEditNotEntryFile" value="<%=String.valueOf(GSConstFile.NOT_ENTRY_FILE_EXIST)%>">
                <span class="ml10 verAlignMid hp30">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_tempreg.png">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_tempreg.png">
                  <div class="ml5"><gsmsg:write key="fil.fil010.7" /></div>
                </span>
                </logic:equal>
                </logic:equal>
              </div>
            </logic:notEqual>
            <%-- 他ユーザの個人キャビネット --%>
            <logic:equal name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
              <table class="table-top">
                <tr>
                  <th class="w5">&nbsp;</th>
                  <th class="w70 cl_fontOutline">
                    <gsmsg:write key="fil.142" />
                  </th>
                  <th class="w20 cl_fontOutline">
                    <gsmsg:write key="fil.fil010.1" />/<gsmsg:write key="fil.fil010.2" />
                  </th>
                  <th class="w5 cl_fontOutline">
                    <gsmsg:write key="cmn.detail" />
                  </th>
                </tr>
                <logic:iterate id="cabBean" name="fil010Form" property="cabinetList" scope="request" indexId="idx">
                  <tr class="js_listHover" data-fcbsid="<bean:write name="cabBean" property="fcbSid" />" data-rootsid="<bean:write name="cabBean" property="rootDirSid" />">
                    <td class="txt_c js_listClick cursor_p">
                      <logic:equal name="cabBean" property="fcbMark" value="0">
                        <img class="btn_classicImg-display wp30" src="../file/images/classic/30classic_icon_personal_cabinet.png">
                        <img class="btn_originalImg-display wp30" src="../file/images/original/30original_icon_personal_cabinet.png">
                      </logic:equal>
                      <logic:notEqual name="cabBean" property="fcbMark" value="0">
                        <img name="iconImage" class="wp30" src="../file/fil010.do?CMD=tempview&fil010SelectCabinet=<bean:write name="cabBean" property="fcbSid" />&fil010binSid=<bean:write name="cabBean" property="fcbMark" />" name="pictImage<bean:write name="cabBean" property="fcbMark" />">
                      </logic:notEqual>
                    </td>
                    <td class="js_listClick cursor_p word_b-all">
                      <span class="cl_linkDef"><bean:write name="cabBean" property="dspfcbName" filter="false" /></span>
                      <logic:notEqual name="cabBean" property="accessIconKbn" value="1">
                        <img class="btn_classicImg-display" src="../file/images/classic/18_icon_stop.png">
                        <img class="btn_originalImg-display" src="../file/images/original/icon_stop.png">
                      </logic:notEqual>
                      <logic:equal name="cabBean" property="callIconKbn" value="1">
                        <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png">
                      </logic:equal>
                      <logic:notEmpty name="cabBean" property="dspBikoString">
                        <div class="fs_11 mt5 cl_fontMiddle">
                          <bean:write name="cabBean" property="dspBikoString" filter="false" />
                        </div>
                      </logic:notEmpty>
                    </td>
                    <td class="js_listClick cursor_p">
                      <logic:notEqual name="cabBean" property="diskUsedWarning" value="1">
                        <bean:write name="cabBean" property="diskUsedString" filter="false" />
                      </logic:notEqual>
                      <logic:equal name="cabBean" property="diskUsedWarning" value="1">
                        <img class="flo_l mt5 mr5 btn_classicImg-display" src="../common/images/keikoku.gif" alt="<gsmsg:write key="cmn.warning" />">
                        <img class="flo_l mt5 mr5 btn_originalImg-display" src="../common/images/demoImg_18x18.png" alt="<gsmsg:write key="cmn.warning" />">
                        <span class="cl_fontWarn bgC_warn">
                          <bean:write name="cabBean" property="diskUsedString" filter="false" />
                        </span>
                      </logic:equal>
                    </td>
                    <td class="txt_c">
                      <button type="button" value="<gsmsg:write key="fil.18" />" class="baseBtn no_w" onclick="personalCabinetDetail(<bean:write name="cabBean" property="fcbSid" />);">
                        <gsmsg:write key="fil.18" />
                      </button>
                    </td>
                  </tr>
                </logic:iterate>
              </table>
              <%-- 補足 --%>
              <div class="mt10 mb10">
                <span class="verAlignMid hp30">
                <img class="btn_classicImg-display" src="../file/images/classic/18_icon_stop.png">
                <img class="btn_originalImg-display" src="../file/images/original/icon_stop.png">
                <div class="ml5"><gsmsg:write key="fil.fil010.3" /></div>
                </span>
                <span class="ml10 verAlignMid hp30">
                  <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png">
                  <div class="ml5"><gsmsg:write key="fil.fil010.4" /></div>
                </span>
              </div>
            </logic:equal>
          </logic:notEmpty>

          <logic:empty name="fil010Form" property="cabinetList" scope="request">
            <logic:notEqual name="fil010Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
              <gsmsg:write key="fil.fil010.5" />
            </logic:notEqual>
          </logic:empty>
        </div>
      </div>
    </div>

  </html:form>

  <jsp:include page="/WEB-INF/plugin/common/jsp/footer001.jsp" />

</body>
</html:html>