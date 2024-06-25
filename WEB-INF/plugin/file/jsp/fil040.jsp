<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.fil040.FileDirectoryDspModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>

<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="cmn.filelist" /></title>

<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil040.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?550'></script>
<script src="../file/js/fileDeleteDialog.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<theme:css filename="theme.css" />

<script type="text/javascript">  <!--
  var msglist_fil040 = (function () {
    //使用するメッセージキーの配列を作成
      var ret = new Array();
      ret['cmn.select.4'] = '<gsmsg:write key="cmn.select.4" arg0="elName" />';
      ret['cmn.cmn310.06'] = '<gsmsg:write key="cmn.cmn310.06" />';
      ret['fil.fil040.9'] = '<gsmsg:write key="fil.fil040.9" />';
      ret['fil.fil040.10'] = '<gsmsg:write key="fil.fil040.10" />';
      ret['fil.fil040.12'] = '<gsmsg:write key="fil.fil040.12" />';
    return ret;
  })();
-->

</script>
  
</head>

<%
  java.util.List filTipList = new java.util.ArrayList();
%>

<body>
  <html:form action="/file/fil040">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <% int helpParameter = 0; %>
    <logic:equal name="fil040Form" property="fil010DspCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
      <% helpParameter = 1; %>
    </logic:equal>
    <input type="hidden" name="helpPrm" value="<%= helpParameter %>">
    
    <input type="hidden" name="CMD" value="fil040search">
    <input type="hidden" name="moveToDir" value="">
    <input type="hidden" name="fil050DirSid" value="">
    <input type="hidden" name="fil050ParentDirSid" value="">
    <input type="hidden" name="fil070DirSid" value="">
    <input type="hidden" name="fil070ParentDirSid" value="">
    <input type="hidden" name="fil090DirSid" value="">
    <input type="hidden" name="fil040SelectUnlock" value="">
    <input type="hidden" name="fil040SelectUnlockVer" value="">
    <input type="hidden" name="fil040OpeComment" value="">

    <input type="hidden" name="fileSid" value="">


    <html:hidden property="selectDir" />
    <html:hidden property="sepKey" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil010DspCabinetKbn" />
    <html:hidden property="backDsp" />
    <html:hidden property="backDspLow" />

    <html:hidden property="fil040SortKey" />
    <html:hidden property="fil040OrderKey" />
    <html:hidden property="fil040PersonalFlg" />
    <html:hidden property="fil040PersonalCabOwnerSid" />
    <html:hidden property="fil040PersonalCabOwnerName" />
    <html:hidden property="fil040SideMenuFlg" />
    <html:hidden property="fil090SelectPluralKbn" />
    <bean:parameter id="fileTree" name="fileTree" value=""/>
    <input type="hidden" name="fileTree" value="<bean:write name="fileTree" />">

    <logic:notEmpty name="fil040Form" property="fil010SelectDelLink">
      <logic:iterate id="delSid" name="fil040Form" property="fil010SelectDelLink">
        <input type="hidden" name="fil010SelectDelLink" value="<bean:write name="delSid" />">
      </logic:iterate>
    </logic:notEmpty>
    <bean:define id="cabDirId" value=""/>
    <bean:define id="cabDirName" value=""/>
    <bean:define id="sepKey" name="fil040Form" property="sepKey" type="String"/>

    <logic:notEmpty name="fil040Form" property="treeFormLv0">
      <logic:iterate id="lv0" name="fil040Form" property="treeFormLv0" type="String">
        <logic:empty name="cabDirId">
         <% String[] sp = lv0.split(sepKey);
         %>
         <bean:define id="cabDirId" value="<%=sp[0] %>"/>
         <bean:define id="cabDirName" value="<%=sp[2] %>"/>
        </logic:empty>
        <input type="hidden" name="treeFormLv0" value="<bean:write name="lv0" />">
      </logic:iterate>
    </logic:notEmpty>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li>
          <gsmsg:write key="cmn.filekanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="fil.53" />：
          <bean:write name="fil040Form" property="fil040CabinetName" />
        </li>
        <li>
          <button type="button" value="<gsmsg:write key="fil.49" />" class="baseBtn wp100 btn_originalImg-display" onClick="buttonPush('cabinetMain')">
            <img src="../file/images/original/icon_cabinet.png" alt="<gsmsg:write key="fil.49" />">
            <gsmsg:write key="fil.49" />
          </button>
          <button type="button" value="<gsmsg:write key="fil.49" />" class="baseBtn btn_classicImg-display" onClick="buttonPush('cabinetMain')">
            <img src="../file/images/classic/btn_cabinet.png" alt="<gsmsg:write key="fil.49" />">
            <gsmsg:write key="fil.49" />
          </button>
          <logic:equal name="fil040Form" property="canEditNotEntryFile" value="<%=String.valueOf(GSConstFile.NOT_ENTRY_FILE_EXIST)%>">
            <button type="button" class="baseBtn btn_classicImg-display js_tradeTouroku" value="<gsmsg:write key="fil.fil010.8" />" >
              <img src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="fil.fil010.8" />
            </button>
            <button type="button" class="baseBtn btn_originalImg-display wp100 js_tradeTouroku" value="<gsmsg:write key="fil.fil010.8" />" >
              <img src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="fil.fil010.8" />
            </button>
          </logic:equal>

          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('fil040back')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </li>
      </ul>
    </div>

    <div class="mb5 txt_r">
      <logic:equal name="fil040Form" property="fil040DspAddBtn" value="1">
        <logic:equal name="fil040Form" property="admLockKbn" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="fil.121" />" onclick="FileRockOn();">
            <img class="classic-display" src="../common/images/classic/icon_lock.png" alt="<gsmsg:write key="fil.121" />">
            <img class="original-display" src="../common/images/original/icon_lock.png" alt="<gsmsg:write key="fil.121" />">
            <gsmsg:write key="fil.121" />
          </button>
          <button type="button" class="baseBtn" value="<gsmsg:write key="fil.122" />" onclick="FileRockOff();">
            <img class="classic-display" src="../common/images/classic/icon_unlock_key.gif" alt="<gsmsg:write key="fil.122" />">
            <img class="original-display" src="../common/images/original/icon_unlock.png" alt="<gsmsg:write key="fil.122" />">
            <gsmsg:write key="fil.122" />
          </button>
        </logic:equal>

        <logic:equal name="fil040Form" property="fil040DspFolderAddBtn" value="1">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.create.folder" />" onClick="CreateFolder();">
            <img class="classic-display" src="../common/images/classic/icon_add_folder.png" alt="<gsmsg:write key="cmn.create.folder" />">
            <img class="original-display" src="../common/images/original/icon_add_folder.png" alt="<gsmsg:write key="cmn.create.folder" />">
            <gsmsg:write key="cmn.create.folder" />
          </button>
        </logic:equal>

        <button type="button" class="baseBtn" value="<gsmsg:write key="fil.16" />" onClick="CreateFile();">
          <img class="classic-display" src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="fil.16" />">
          <img class="original-display" src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="fil.16" />">
          <gsmsg:write key="fil.16" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.move" />" onclick="MovePlural();">
          <img class="classic-display" src="../file/images/classic/icon_move_file.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="original-display" src="../file/images/original/icon_move_folder.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="DeleteDirectory();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
      </logic:equal>
      <logic:notEqual name="fil040Form" property="fil040DspAddBtn" value="1">
        <logic:equal name="fil040Form" property="fil040DspSelectDelAll" value="1">
          <logic:equal name="fil040Form" property="admLockKbn" value="1">
            <button type="button" class="baseBtn" value="<gsmsg:write key="fil.121" />" onclick="FileRockOn();">
              <img class="classic-display" src="../common/images/classic/icon_lock.png" alt="<gsmsg:write key="fil.121" />">
              <img class="original-display" src="../common/images/original/icon_lock.png" alt="<gsmsg:write key="fil.121" />">
              <gsmsg:write key="fil.121" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="fil.122" />" onclick="FileRockOff();">
              <img class="classic-display" src="../common/images/classic/icon_unlock_key.gif" alt="<gsmsg:write key="fil.122" />">
              <img class="original-display" src="../common/images/original/icon_unlock.png" alt="<gsmsg:write key="fil.122" />">
              <gsmsg:write key="fil.122" />
            </button>
          </logic:equal>
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.move" />" onclick="MovePlural();">
            <img class="classic-display" src="../file/images/classic/icon_move_file.png" alt="<gsmsg:write key="cmn.move" />">
            <img class="original-display" src="../file/images/original/icon_move_folder.png" alt="<gsmsg:write key="cmn.move" />">
            <gsmsg:write key="cmn.move" />
          <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="DeleteDirectory();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
      </logic:notEqual>

    </div>

    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>

    <% String cabinetClass="";
       String closeAreaClass="display_none";
       String fileListClass="";
    %>
    <logic:equal name="fil040Form" property="fil040SideMenuFlg" value="<%= String.valueOf(GSConst.DSP_NOT) %>">
      <% cabinetClass="display_none";
         closeAreaClass="";
         fileListClass="w100";
      %>
    </logic:equal>


    <div class="wrapper_2column">
      <div class="side-left wp200 <%= cabinetClass %>" id="cabinetList">
        <div class="">
          <div class="table_title-color fs_14 hp40 pl5 pt10">
            <span class="w50">
              <gsmsg:write key="cmn.filekanri" />
            </span>
            <span class="js_cabineList">
             <span class="flo_r mr10 cursor_p"><span class="icon-arrow_left cl_linkHoverChange"></span></span>
           </span>
          </div>
          <logic:notEmpty name="fil040Form" property="fil040CabinetList">
            <div class="p5">
              <html:select name="fil040Form" property="fil040SelectCabinet" onchange="buttonPush('fil040changeCabinet');" styleClass="w98">
                <html:optionsCollection name="fil040Form" property="fil040CabinetList" value="value" label="label" />
              </html:select>
            </div>
          </logic:notEmpty>

          <div id="sidetreecontrol">
            <a href="#!">
              <gsmsg:write key="cmn.all.close" />
            </a>
            |
            <a href="#!">
              <gsmsg:write key="cmn.all.open" />
            </a>
          </div>
          <div  class="hp500 ofx_s ofy_s no_w pl5">
            <div>
              <a href="#!" onClick="fil040FileTreeClick('detailDir', '<bean:write name="cabDirId" />')"><img class="classic-display mr5" src="../common/images/classic/icon_folder.png" alt=""><img class="original-display mr5" src="../common/images/original/icon_folder_box.png"  alt=""><bean:write name="cabDirName"  filter="false"/></a>
            </div>
            <ul id="tree" class="w100">
            </ul>
            <div class="js_tree_loader">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
              <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
              <gsmsg:write key="cmn.loading"/>
            </div>
          </div>
        </div>
      </div>
      <div class="js_listCloseArea js_cabineList listCloseArea bgC_lightGray cl_linkDef cl_linkHoverChange mr5 ml0 <%= closeAreaClass %>">
        <span class="icon-arrow_right txt_c"></span>
      </div>

      <% String colspan = "8";
         String nameWidth = "w40";
      %>

      <logic:equal name="fil040Form" property="fil010DspCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
        <% colspan = "11";
           nameWidth = "w20";
        %>
      </logic:equal>
      <div class="main js_main <%= fileListClass %>">
        <!-- 一覧 -->

        <table class="table-top mt0" cellpadding="0" cellspacing="0">

        <tr>
          <td class="table_title-color" colspan="<%= colspan %>">
          <div>
            <logic:equal name="fil040Form" property="fil040DspAddBtn" value="1">
              <logic:equal name="fil040Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                <img class="btn_classicImg-display" src="../file/images/classic/icon_cabinet.gif">
                <img class="btn_originalImg-display wp30hp30" src="../file/images/original/icon_cabinet_32.png">
              </logic:equal>
              <logic:equal name="fil040Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
                <img class="btn_classicImg-display" src="../file/images/classic/30classic_icon_personal_cabinet.png">
                <img class="btn_originalImg-display" src="../file/images/original/30original_icon_personal_cabinet.png">
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="fil040Form" property="fil040DspAddBtn" value="1">
              <img class="btn_classicImg-display" src="../file/images/classic/icon_stop_cabinet.gif">
              <img class="btn_originalImg-display" src="../file/images/original/icon_cabinet_stop_32.png">
            </logic:notEqual>

            <logic:notEmpty name="fil040Form" property="fil040DirectoryPathList" scope="request">
              <logic:iterate id="pathBean" name="fil040Form" property="fil040DirectoryPathList" scope="request" indexId="idx">
                <a href="#!" onClick="fil040FileLinkClick('detailDir', <bean:write name="pathBean" property="fdrSid"/>);">
                  <span class="cl_fontOutlineLink fw_b js_directoryHover">
                    <bean:write name="pathBean" property="fdrName" />/
                  </span>
                </a>
              </logic:iterate>
            </logic:notEmpty>

            <div class="flo_r verAlignMid">
              <html:text name="fil040Form" maxlength="50" property="filSearchWd" styleClass="wp150 ml5" />
              <button type="submit" value="<gsmsg:write key="cmn.search" />" class="baseBtn ml5" onClick="MoveToSearch();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                <gsmsg:write key="cmn.search" />
              </button>
            </div>
        </div>
          </td>
        </tr>

          <tr>
            <logic:equal name="fil040Form" property="fil040DspSelectDelAll" value="1">
              <th class="w2 js_tableTopCheck js_tableTopCheck-header cursor_p bgC_header2 no_w">
                <html:checkbox name="fil040Form" property="fil040SelectDelAll" value="1" onclick="return changeChk();" />
              </th>
            </logic:equal>
            <th class="<%= nameWidth %> cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="1">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(1, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.name4" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(1, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.name4" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="1">
                <a href="#!" onClick="return fil040TitleClick(1, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.name4" />
                  </span>
                </a>
              </logic:notEqual>
            </th>
            <logic:equal name="fil040Form" property="fil010DspCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
            <th class="w5 cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="<%= String.valueOf(GSConstFile.SORT_TRADE_DATE) %>">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(6, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.fil080.8" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(6, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.fil080.8" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="<%= String.valueOf(GSConstFile.SORT_TRADE_DATE) %>">
                <a href="#!" onClick="return fil040TitleClick(6, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="fil.fil080.8" />
                  </span>
                </a>
              </logic:notEqual>
            </th>

            <th class="w15 cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="<%= String.valueOf(GSConstFile.SORT_TRADE_TARGET) %>">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(7, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.fil030.18" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(7, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.fil030.18" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="<%= String.valueOf(GSConstFile.SORT_TRADE_TARGET) %>">
                <a href="#!" onClick="return fil040TitleClick(7, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="fil.fil030.18" />
                  </span>
                </a>
              </logic:notEqual>
            </th>

            <th class="w10 cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="<%= String.valueOf(GSConstFile.SORT_TRADE_MONEY) %>">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(8, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.fil080.5" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(8, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.fil080.5" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="<%= String.valueOf(GSConstFile.SORT_TRADE_MONEY) %>">
                <a href="#!" onClick="return fil040TitleClick(8, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="fil.fil080.5" />
                  </span>
                </a>
              </logic:notEqual>
            </th>
            </logic:equal>
            <th class="w5 cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="2">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(2, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.size" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(2, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.size" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="2">
                <a href="#!" onClick="return fil040TitleClick(2, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.size" />
                  </span>
                </a>
              </logic:notEqual>
            </th>
            <th class="w5 no_w cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="3">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(3, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.1" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(3, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="fil.1" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="3">
                <a href="#!" onClick="return fil040TitleClick(3, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="fil.1" />
                  </span>
                </a>
              </logic:notEqual>
            </th>
            <th class="w10 cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="4">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(4, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.update.day.hour" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(4, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.update.day.hour" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="4">
                <a href="#!" onClick="return fil040TitleClick(4, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.update.day.hour" />
                  </span>
                </a>
              </logic:notEqual>
            </th>
            <th class="w15 cursor_p bgC_header2 no_w">
              <logic:equal name="fil040Form" property="fil040SortKey" value="5">
                <logic:equal name="fil040Form" property="fil040OrderKey" value="0">
                  <a href="#!" onClick="return fil040TitleClick(5, 1);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.update.user" />
                      <span class="classic-display">▲</span>
                      <i class="original-display icon-sort_up"></i>
                    </span>
                  </a>
                </logic:equal>
                <logic:equal name="fil040Form" property="fil040OrderKey" value="1">
                  <a href="#!" onClick="return fil040TitleClick(5, 0);">
                    <span class="cl_fontBody">
                      <gsmsg:write key="cmn.update.user" />
                      <span class="classic-display">▼</span>
                      <i class="original-display icon-sort_down"></i>
                    </span>
                  </a>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="fil040Form" property="fil040SortKey" value="5">
                <a href="#!" onClick="return fil040TitleClick(5, 1);">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.update.user" />
                  </span>
                </a>
              </logic:notEqual>
            </th>

            <th class="w2 bgC_header2 no_w">
            </th>
          </tr>

          <bean:define id="tdColor" value="" />
          <% String[] tdColors = new String[] {"bgC_tableCell", "bgC_tableCellEvn"}; %>

          <logic:notEmpty name="fil040Form" property="fil040DirectoryList" scope="request">
            <logic:iterate id="dirBean" name="fil040Form" property="fil040DirectoryList" scope="request" indexId="idx" type="FileDirectoryDspModel">
            <% tdColor = tdColors[(idx.intValue() % 2)]; %>
            <bean:define id="fileName" name="dirBean" property="fdrName" />
            <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>

            <bean:define id="fileAccessKbn" name="dirBean" property="accessKbn" type="java.lang.Integer" />
            <logic:notEqual name="fil040Form" property="fil040DspSelectDelAll" value="1">
              <% fileAccessKbn = Integer.valueOf(0); %>
            </logic:notEqual>

              <tr class="<%= tdColor %> js_listHover" data-dirkbn="<bean:write name="dirBean" property="fdrKbn" />" data-fdrsid="<bean:write name="dirBean" property="fdrSid" />" data-binsid="<bean:write name="dirBean" property="fileBinSid" />" data-parentSid="<bean:write name="dirBean" property="fdrParentSid" />" data-extension="<%= extension %>" data-accessKbn="<%= String.valueOf(fileAccessKbn) %>">
                <logic:equal name="fil040Form" property="fil040DspSelectDelAll" value="1">
                  <td class="txt_c js_tableTopCheck cursor_p">
                    <logic:equal name="dirBean" property="accessKbn" value="1">
                      <html:multibox name="fil040Form" property="fil040SelectDel">
                        <bean:write name="dirBean" property="fdrSid" />
                      </html:multibox>
                    </logic:equal>
                    <input type="hidden" name="fil040List_dirName<bean:write name="dirBean" property="fdrSid" />" value="<bean:write name="dirBean" property="fdrName" />" />
                    <input type="hidden" name="fil040List_dirKbn<bean:write name="dirBean" property="fdrSid" />" value="<bean:write name="dirBean" property="fdrKbn" />" />
                  </td>
                </logic:equal>
                <logic:equal name="dirBean" property="fdrKbn" value="0">
                  <logic:notEmpty name="dirBean" property="fdrBiko">
                    <bean:define id="biko" name="dirBean" property="fdrBiko" />
                    <%
                      String tmpText = (String) pageContext.getAttribute("biko", PageContext.PAGE_SCOPE);
                                  String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                    %>
                    <td class="txt_l js_listClick cursor_p">
                      <a href="#!" onClick="fil040FileLinkClick('detailDir', <bean:write name="dirBean" property="fdrSid" />);" id="fdrsid<bean:write name="dirBean" property="fdrSid" />">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.memo" />:<%=tmpText2%></span>
                        <img class="classic-display" src="../common/images/classic/icon_folder_box.png" alt="">
                        <img class="original-display" src="../common/images/original/icon_folder_box.png" alt="">
                        <span class="cl_linkDef">
                          <bean:write name="dirBean" property="fdrName" />
                        </span>
                      </a>
                      <%
                        filTipList.add("fdrsid" + String.valueOf(
                                        ((jp.groupsession.v2.fil.model.FileDirectoryModel) dirBean).getFdrSid()));
                      %>
                    </td>
                  </logic:notEmpty>
                  <logic:empty name="dirBean" property="fdrBiko">
                    <td class="txt_l js_listClick cursor_p">
                      <a href="#!" onClick="fil040FileLinkClick('detailDir', <bean:write name="dirBean" property="fdrSid" />);" id="fdrsid<bean:write name="dirBean" property="fdrSid" />">
                        <img class="classic-display" src="../common/images/classic/icon_folder_box.png" alt="">
                        <img class="original-display" src="../common/images/original/icon_folder_box.png" alt="">
                        <span class="cl_linkDef">
                          <bean:write name="dirBean" property="fdrName" />
                        </span>
                      </a>
                    </td>
                  </logic:empty>
                  <logic:equal name="fil040Form" property="fil010DspCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
                    <td class="txt_r js_listClick cursor_p">
                      <span>&nbsp;</span>
                    </td>
                    <td class="txt_r js_listClick cursor_p">
                      <span>&nbsp;</span>
                    </td>
                    <td class="txt_r js_listClick cursor_p">
                      <span>&nbsp;</span>
                    </td>
                  </logic:equal>

                  <td class="txt_r js_listClick cursor_p">
                    <span>&nbsp;</span>
                  </td>
                  <td class="txt_c js_listClick cursor_p">
                    <logic:equal name="dirBean" property="callKbn" value="1">
                   <img class="btn_classicImg-display" src="../file/images/classic/icon_call.gif" alt="">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_bell.png" alt="">
                  </logic:equal>
                  </td>
                  <td class="txt_c js_listClick cursor_p">
                    <span>
                      <bean:write name="dirBean" property="edateString" />
                    </span>
                  </td>
                  <td class="txt_l js_listClick cursor_p">
                    <logic:equal name="dirBean" property="upUsrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                     <s><bean:write name="dirBean" property="upUsrName" /></s>
                    </logic:equal>
                    <logic:notEqual name="dirBean" property="upUsrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                      <span class="<%=UserUtil.getCSSClassNameNormal(dirBean.getUpUsrUkoFlg())%>">
                        <bean:write name="dirBean" property="upUsrName" />
                      </span>
                    </logic:notEqual>
                  </td>
                  <td class="txt_c no_w cursor_p js_fileMenu">
                    <i class="icon-ellipsis txt_m fs_18"></i>
                  </td>
                </logic:equal>

                <logic:notEqual name="dirBean" property="fdrKbn" value="0">
                  <logic:notEmpty name="dirBean" property="fdrBiko">
                    <bean:define id="biko" name="dirBean" property="fdrBiko" />
                    <%
                      String tmpText = (String) pageContext.getAttribute("biko", PageContext.PAGE_SCOPE);
                                  String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                    %>
                    <td class="txt_l">
                      <a href="#!" onClick="return fileDl('fileDownload', <bean:write name="dirBean" property="fileBinSid" />);" id="fdrsid<bean:write name="dirBean" property="fdrSid" />">
                        <span class="tooltips">
                          <gsmsg:write key="cmn.memo" />:<%=tmpText2%></span>
                        <img class="classic-display" src="../file/images/classic/icon_file.gif" border="0" alt="">
                        <img class="original-display" src="../common/images/original/icon_siryo.png" border="0" alt="">
                        <span class="cl_linkDef">
                          <bean:write name="dirBean" property="fdrName" />
                        </span>
                      </a>
                      <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                      <span class="ml5 cursor_p">
                        <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                        <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                      </span>
                      <% } %>
                      <logic:equal name="dirBean" property="lockKbn" value="1">
                        <logic:equal name="fil040Form" property="admLockKbn" value="1">
                          <br>
                          <img class="classic-display" src="../common/images/classic/icon_lock.png" alt="">
                          <img class="original-display" src="../common/images/original/icon_lock.png" alt="">
                          <span class="cl_fontWarn">
                            <gsmsg:write key="fil.fil040.5" />
                            (<bean:write name="dirBean" property="lockUsrName" />)
                          </span>
                          <logic:equal name="fil040Form" property="fil040UnLockAuth" value="1">
                            <logic:notEmpty name="dirBean" property="fdrBiko">
                              <a href="#!" onClick="UnLock(<bean:write name="dirBean" property="fdrSid" />,<bean:write name="dirBean" property="fdrVersion" />)">
                                <span>
                                  <img class="classic-display" src="../common/images/classic/icon_unlock_key.gif" title="<gsmsg:write key="cmn.memo" />:<bean:write name="dirBean" property="fdrBiko" />" alt="">
                                  <img class="original-display" src="../common/images/original/icon_unlock.png" title="<gsmsg:write key="cmn.memo" />:<bean:write name="dirBean" property="fdrBiko" />" alt="">
                                  <gsmsg:write key="fil.fil040.6" />
                                </span>
                              </a>
                            </logic:notEmpty>
                            <logic:empty name="dirBean" property="fdrBiko">
                              <a href="#!" onClick="UnLock(<bean:write name="dirBean" property="fdrSid" />,<bean:write name="dirBean" property="fdrVersion" />)">
                                <span>
                                  <img class="classic-display" src="../common/images/classic/icon_unlock_key.gif" alt="">
                                  <img class="original-display" src="../common/images/original/icon_unlock.png" alt="">
                                  <gsmsg:write key="fil.fil040.6" />
                                </span>
                              </a>
                            </logic:empty>
                          </logic:equal>
                          <logic:notEmpty name="dirBean" property="lockDate">
                            <br>
                            <span>
                              <bean:write name="dirBean" property="lockDate" />～
                            </span>
                          </logic:notEmpty>
                        </logic:equal>
                      </logic:equal>

                    </td>
                    <%
                      filTipList.add("fdrsid" + String.valueOf(
                                      ((jp.groupsession.v2.fil.model.FileDirectoryModel) dirBean).getFdrSid()));
                    %>
                  </logic:notEmpty>
                  <logic:empty name="dirBean" property="fdrBiko">
                    <td class="txt_l">
                      <a href="#!" onClick="return fileDl('fileDownload', <bean:write name="dirBean" property="fileBinSid" />);" id="fdrsid<bean:write name="dirBean" property="fdrSid" />">
                        <img class="classic-display" src="../file/images/classic/icon_file.gif" border="0" alt="">
                        <img class="original-display" src="../common/images/original/icon_siryo.png" border="0" alt="">
                        <span class="cl_linkDef">
                          <bean:write name="dirBean" property="fdrName" />
                        </span>
                      </a>
                      <bean:define id="fileName" name="dirBean" property="fdrName" />
                      <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                      <span class="ml5 cursor_p">
                        <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                        <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                      </span>
                      <% } %>
                      <logic:equal name="dirBean" property="lockKbn" value="1">
                        <logic:equal name="fil040Form" property="admLockKbn" value="1">
                          <br>
                          <img class="classic-display" src="../common/images/classic/icon_edit_2.png" alt="">
                          <img class="original-display" src="../common/images/original/icon_edit.png" alt="">
                          <span class="cl_fontWarn">
                            <gsmsg:write key="fil.fil040.5" />
                            (<bean:write name="dirBean" property="lockUsrName" />)
                          </span>
                          <logic:equal name="fil040Form" property="fil040UnLockAuth" value="1">
                            <logic:notEmpty name="dirBean" property="fdrBiko">
                              <a href="#!" onClick="UnLock(<bean:write name="dirBean" property="fdrSid" />,<bean:write name="dirBean" property="fdrVersion" />)">
                                <span>
                                  <img class="classic-display" src="../common/images/classic/icon_unlock_key.gif" title="<gsmsg:write key="cmn.memo" />:<bean:write name="dirBean" property="fdrBiko" />" alt="">
                                  <img class="original-display" src="../common/images/original/icon_unlock.png" title="<gsmsg:write key="cmn.memo" />:<bean:write name="dirBean" property="fdrBiko" />" alt="">
                                  <gsmsg:write key="fil.fil040.6" />
                                </span>
                              </a>
                            </logic:notEmpty>
                            <logic:empty name="dirBean" property="fdrBiko">
                              <a href="#!" onClick="UnLock(<bean:write name="dirBean" property="fdrSid" />,<bean:write name="dirBean" property="fdrVersion" />)">
                                <span>
                                  <img class="classic-display" src="../common/images/classic/icon_unlock_key.gif" alt="">
                                  <img class="original-display" src="../common/images/original/icon_unlock.png" alt="">
                                  <gsmsg:write key="fil.fil040.6" />
                                </span>
                              </a>
                            </logic:empty>
                          </logic:equal>
                          <logic:notEmpty name="dirBean" property="lockDate">
                            <br>
                            <span>
                              <bean:write name="dirBean" property="lockDate" />～
                            </span>
                          </logic:notEmpty>
                        </logic:equal>
                      </logic:equal>

                    </td>
                  </logic:empty>
                  <logic:equal name="fil040Form" property="fil010DspCabinetKbn" value="<%= String.valueOf(GSConstFile.CABINET_KBN_ERRL) %>">
                    <td class="txt_c js_listClick cursor_p">
                      <span>
                        <bean:write name="dirBean" property="tradeDate" />
                      </span>
                    </td>
                    <td class="txt_l js_listClick cursor_p">
                      <span>
                        <bean:write name="dirBean" property="fdrTradeTarget" />
                      </span>
                    </td>
                    <% String moneyClass = "txt_r"; %>
                    <logic:equal name="dirBean" property="tradeMoney" value="-" >
                      <% moneyClass = "txt_c"; %>
                    </logic:equal>
                    <td class="<%= moneyClass %> js_listClick cursor_p no_w">
                      <span>
                        <bean:write name="dirBean" property="tradeMoney" />
                      </span>
                    </td>
                  </logic:equal>

                  <td class="txt_r js_listClick cursor_p no_w">
                    <span>
                      <bean:write name="dirBean" property="fileSize" />
                    </span>
                  </td>
                  <td class="txt_c js_listClick cursor_p">&nbsp;</td>
                  <td class="txt_c js_listClick cursor_p">
                    <span>
                      <bean:write name="dirBean" property="edateString" />
                    </span>
                  </td>
                  <td class="txt_l js_listClick cursor_p">
                    <logic:equal name="dirBean" property="upUsrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                      <span>
                        <s><bean:write name="dirBean" property="upUsrName" /><s>
                      </span>
                    </logic:equal>
                    <logic:notEqual name="dirBean" property="upUsrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                      <span class="<%=UserUtil.getCSSClassNameNormal(dirBean.getUpUsrUkoFlg())%>">
                        <bean:write name="dirBean" property="upUsrName" />
                      </span>
                    </logic:notEqual>
                  </td>
                  <td class="txt_c no_w cursor_p js_fileMenu">
                    <i class="icon-ellipsis txt_m fs_18"></i>
                  </td>
                </logic:notEqual>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
        <div class="pos_abs display_n cursor_p wp50" id="tools">
          <div class="txt_c file_tools js_syosaiClick">
            <gsmsg:write key="cmn.detail" />
          </div>
          <div class="txt_c file_tools border_top_none js_moveClick">
            <gsmsg:write key="cmn.move" />
          </div>
      </div>

        <table class="w100" cellpadding="0" cellspacing="0">
          <tr class="txt_l">
            <td>
              <span>URL:</span>
              <input type="text" value="<bean:write name="fil040Form" property="fil040UrlString" />" class="wp550" readOnly="true" />
            </td>
          </tr>
        </table>
      </div>
    </div>
    </div>

    <filekanri:deleteFileDialog commentName="fil040OpeComment" />

  </html:form>

  <filekanri:fileTreeParams screenId="fil040" />

<!--
<script type="text/javascript">
<% for (int filCnt = 0; filCnt < filTipList.size(); filCnt++) { %>
$(function() {
  $('#<%= (java.lang.String) filTipList.get(filCnt) %>').tooltip();
});
<% } %>
</script>
 -->
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>