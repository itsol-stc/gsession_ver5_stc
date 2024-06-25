<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.model.FileParentAccessDspModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>
<%
    String maxLengthBiko        = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAX_LENGTH_FILE_BIKO);
    String maxLengthUpCmt       = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAX_LENGTH_FILE_UP_CMT);
    String VERSION_ALL_KBN_OFF  = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_ALL_KBN_OFF);
    String VERSION_ALL_KBN_ON   = String.valueOf(jp.groupsession.v2.fil.GSConstFile.VERSION_ALL_KBN_ON);
    String cabinetKbnPrvt = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_KBN_PRIVATE);
    String cabinetKbnErrl = String.valueOf(jp.groupsession.v2.fil.GSConstFile.CABINET_KBN_ERRL);
%>

<html:html>

<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.16" />
</title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/ui/i18n/jquery.ui.datepicker-ja.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/datepicker.js?<%= GSConst.VERSION_PARAM %>" ></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/fil080.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/components/currency-input/index.js?<%=GSConst.VERSION_PARAM%>"></script>

<script src="../file/js/fileDeleteDialog.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel="stylesheet" href="../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href="../file/css/file.css?<%= GSConst.VERSION_PARAM %>" type="text/css">
<link rel="stylesheet" type="text/css" href="../common/css/picker.css?<%= GSConst.VERSION_PARAM %>">
<link rel=stylesheet href='../common/css/gscalender.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

</head>

<% boolean callWebmail = false; String screenWidthClass = "w80"; %>
<logic:equal name="fil080Form" property="fil080webmail" value="1">
  <script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>
  <% callWebmail = true; screenWidthClass = "w100"; %>
</logic:equal>

<body onload="fil080ShowOrHide();parentAccessShowOrHide(<bean:write name="fil080Form" property="fil080ParentAccessAll" />);showLengthId($('#inputstr')[0], <%= maxLengthBiko %>, 'inputlength');showLengthId($('#inputstr2')[0], <%= maxLengthUpCmt %>, 'inputlength2');" onunload="windowClose();closePreviewWindow();">

  <html:form action="/file/fil080">
    <input type="hidden" name="CMD" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="backDspLow" />
    <html:hidden property="admVerKbn" />
    <html:hidden property="filSearchWd" />

    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil010DspCabinetKbn" />

    <html:hidden property="fil040PersonalFlg" />
    <html:hidden property="fil040PersonalCabOwnerSid" />
    <html:hidden property="fil040PersonalCabOwnerName" />
    <html:hidden property="fil070DirSid" />
    <html:hidden property="fil070ParentDirSid" />
    <html:hidden property="fil080Mode" />
    <html:hidden property="fil080PluginId" />
    <html:hidden property="fil080SvPluralKbn" />
    <html:hidden property="fil080ParentAccessAll" />
    <html:hidden property="fil080ParentZeroUser" />
    <html:hidden property="fil080InitFlg" />

    <logic:equal name="fil080Form" property="fil080VerallKbn" value="<%= VERSION_ALL_KBN_ON %>">
      <html:hidden property="fil080VerKbn" />
    </logic:equal>

    <html:hidden name="fil080Form" property="fil100SltCabinetSid" />
    <html:hidden name="fil080Form" property="fil100ChkTrgFolder" />
    <html:hidden name="fil080Form" property="fil100ChkTrgFile" />
    <html:hidden name="fil080Form" property="fil100SearchMode" />
    <html:hidden name="fil080Form" property="fil100ChkWdTrgName" />
    <html:hidden name="fil080Form" property="fil100ChkWdTrgBiko" />
    <html:hidden name="fil080Form" property="fil100ChkWdTrgText" />
    <html:hidden name="fil080Form" property="fileSearchfromYear" />
    <html:hidden name="fil080Form" property="fileSearchfromMonth" />
    <html:hidden name="fil080Form" property="fileSearchfromDay" />
    <html:hidden name="fil080Form" property="fileSearchtoYear" />
    <html:hidden name="fil080Form" property="fileSearchtoMonth" />
    <html:hidden name="fil080Form" property="fileSearchtoDay" />
    <html:hidden name="fil080Form" property="fil100ChkOnOff" />

    <html:hidden name="fil080Form" property="fil100SltCabinetKbn" />
    <html:hidden name="fil080Form" property="fil100ChkTrgDeleted" />
    <html:hidden name="fil080Form" property="fil100ChkTrgDeletedFolder" />
    <html:hidden name="fil080Form" property="fil100SearchTradeTarget" />
    <html:hidden name="fil080Form" property="fil100SearchTradeMoneyNoset" />
    <html:hidden name="fil080Form" property="fil100SearchTradeMoneyKbn" />
    <html:hidden name="fil080Form" property="fil100SearchTradeMoney" />
    <html:hidden name="fil080Form" property="fil100SearchTradeMoneyTo" />
    <html:hidden name="fil080Form" property="fil100SearchTradeMoneyType" />
    <html:hidden name="fil080Form" property="fil100SearchTradeMoneyJudge" />
    <html:hidden name="fil080Form" property="fil100SearchTradeDateKbn" />
    <html:hidden name="fil080Form" property="fil100SearchTradeDateFrom" />
    <html:hidden name="fil080Form" property="fil100SearchTradeDateTo" />

    <html:hidden name="fil080Form" property="fil100SvSltCabinetSid" />
    <html:hidden name="fil080Form" property="fil100SvChkTrgFolder" />
    <html:hidden name="fil080Form" property="fil100SvChkTrgFile" />
    <html:hidden name="fil080Form" property="fil100SvChkTrgDeleted" />
    <html:hidden name="fil080Form" property="fil100SvChkTrgDeletedFolder" />
    <html:hidden name="fil080Form" property="fil100SvSearchMode" />
    <html:hidden name="fil080Form" property="fil100SvChkWdTrgName" />
    <html:hidden name="fil080Form" property="fil100SvChkWdTrgBiko" />
    <html:hidden name="fil080Form" property="fil100SvChkWdTrgText" />
    <html:hidden name="fil080Form" property="fil100SvChkWdKeyWord" />
    <html:hidden name="fil080Form" property="fileSvSearchfromYear" />
    <html:hidden name="fil080Form" property="fileSvSearchfromMonth" />
    <html:hidden name="fil080Form" property="fileSvSearchfromDay" />
    <html:hidden name="fil080Form" property="fileSvSearchtoYear" />
    <html:hidden name="fil080Form" property="fileSvSearchtoMonth" />
    <html:hidden name="fil080Form" property="fileSvSearchtoDay" />
    <html:hidden name="fil080Form" property="fil100SvChkOnOff" />
    <html:hidden name="fil080Form" property="fil100sortKey" />
    <html:hidden name="fil080Form" property="fil100orderKey" />
    <html:hidden name="fil080Form" property="fil100pageNum1" />
    <html:hidden name="fil080Form" property="fil100pageNum2" />
    <html:hidden name="fil080Form" property="fil240PageNum" />
    <html:hidden name="fil080Form" property="backDspCall" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeTarget" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeMoney" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeMoneyTo" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeMoneyType" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeMoneyJudge" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeMoneyNoset" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeMoneyKbn" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeDateFrom" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeDateTo" />
    <html:hidden name="fil080Form" property="fil100SvSearchTradeDateKbn" />
    <input type="hidden" name="fil080OpeComment" value="">

    <html:hidden property="fil080webmail" />

    <logic:notEqual name="fil080Form" property="fil080webmail" value="1">
      <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    </logic:notEqual>

    <% if (callWebmail) { %>
    <bean:define id="cabDirId" value=""/>
    <bean:define id="cabDirName" value=""/>
    <bean:define id="sepKey" name="fil080Form" property="sepKey" type="String"/>
    <bean:define id="cabDirNoLink" value="0"/>

    <html:hidden property="selectDir" />
    <html:hidden property="sepKey" />
    <input type="hidden" name="moveToDir" value="">

    <logic:notEmpty name="fil080Form" property="treeFormLv0">
      <logic:iterate id="lv0" name="fil080Form" property="treeFormLv0" type="String">
        <logic:empty name="cabDirId">
         <% String[] sp = lv0.split(sepKey);
         %>
         <bean:define id="cabDirId" value="<%=sp[0] %>"/>
         <bean:define id="cabDirName" value="<%=sp[2] %>"/>
         <% if (sp.length >= 4) {%>
             <bean:define id="cabDirNoLink" value="<%=sp[3] %>"/>
         <% } %>

        </logic:empty>
        <input type="hidden" name="treeFormLv0" value="<bean:write name="lv0" />">
      </logic:iterate>
    </logic:notEmpty>
    <% } %>
    
    <% String pgid = ""; %>
    <logic:equal name="fil080Form" property="fil010DspCabinetKbn" value="<%= cabinetKbnPrvt %>">
      <% pgid = String.valueOf(1); %>
    </logic:equal>
    <logic:equal name="fil080Form" property="fil010DspCabinetKbn" value="<%= cabinetKbnErrl %>">
      <% pgid = String.valueOf(2); %>
    </logic:equal>
    <input type="hidden" name="helpPrm" value="<bean:write name="fil080Form" property="fil080Mode" /><%= pgid %>">

    <% int errlMode = 0; %>
    <logic:equal name="fil080Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_ERRL%>">
      <logic:equal name="fil080Form" property="fil040PersonalFlg" value="0">
        <% errlMode = 1; %>
      </logic:equal>
    </logic:equal>

    <div class="pageTitle <%= screenWidthClass %> mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li>
          <gsmsg:write key="cmn.filekanri" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="fil080Form" property="fil080Mode" value="0">
            <gsmsg:write key="fil.16" />
          </logic:equal>
          <logic:equal name="fil080Form" property="fil080Mode" value="1">
            <gsmsg:write key="cmn.edit.file" />
          </logic:equal>
        </li>
        <li>
          <div>
            <logic:equal name="fil080Form" property="fil080Mode" value="0">
              <% if (errlMode == 1) { %>
                <button type="button" class="baseBtn wp90 btn_originalImg-display" value="<gsmsg:write key="fil.fil070.10" />" onclick="buttonPush('fil080add');">
                  <img src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="fil.fil070.10" />">
                  <gsmsg:write key="fil.fil070.10" />
                </button>
                <button type="button" class="baseBtn btn_classicImg-display" value="<gsmsg:write key="fil.fil070.10" />" onclick="buttonPush('fil080add');">
                  <img src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="fil.fil070.10" />">
                  <gsmsg:write key="fil.fil070.10" />
                </button>
              <% } else { %>
                <button type="button" class="baseBtn" value="<gsmsg:write key="fil.16" />" onclick="buttonPush('fil080add');">
                  <img class="btn_classicImg-display" src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="fil.16" />
                </button>
              <% } %>
            </logic:equal>
            <logic:equal name="fil080Form" property="fil080Mode" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil080add');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="fil080Delete('<bean:write name="fil080Form" property="fil080EditFileName" />', <%= String.valueOf(errlMode) %>);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
            <%
              if (callWebmail) {
            %>
            <button type="button" name="btn_close" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.parent.webmailEntrySubWindowClose();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.close" />
            </button>
            <%
              } else {
            %>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil080back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
            <% } %>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper <%= screenWidthClass %> mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <% if (callWebmail) { %>
      <%-- ページコンテンツ start --%>
      <table cellpadding="5" cellspacing="0" class="w100">
        <tr>
          <td class="w20 txt_t">

            <table class="table-top" cellpadding="0" cellspacing="0">
              <tr>
                <th class="no_w">
                  <gsmsg:write key="cmn.filekanri" />
                </th>
              </tr>

              <logic:notEmpty name="fil080Form" property="fil040CabinetList">
                <tr>
                  <td class="txt_c hp30 p5">
                    <div>
                      <span class="verAlignMid no_w">
                        <html:radio name="fil080Form" property="fil080SelectCabinetKbn" styleId="cabinetPublic" value="0" onclick="changeSelectCabinet('fil080changeCabinet');" />
                        <label for="cabinetPublic">
                          <gsmsg:write key="fil.141" />
                        </label>
                        <html:radio name="fil080Form" property="fil080SelectCabinetKbn" styleId="cabinetErrl" styleClass="ml5" value="1" onclick="changeSelectCabinet('fil080changeCabinet');" />
                        <label for="cabinetErrl">
                          <gsmsg:write key="fil.147" />
                        </label>
                      </span>
                    </div>
                    <div>
                      <html:select name="fil080Form" styleClass="wp250 js_selectCabinetCombo" property="fil040SelectCabinet" onchange="buttonPush('fil080changeCabinet');">
                        <html:optionsCollection name="fil080Form" property="fil040CabinetList" value="value" label="label" />
                      </html:select>
                    </div>
                  </td>
                </tr>
              </logic:notEmpty>
              <tr>
                <td>

                  <div  class="wp250 hp400 ofx_a txt_l">

                    <div id="sidetreecontrol">
                      <a href="#!">
                        <gsmsg:write key="cmn.all.close" />
                      </a>|
                      <a href="#!">
                        <gsmsg:write key="cmn.all.open" />
                      </a>
                    </div>

                    <div>

                      <logic:equal name="cabDirNoLink" value="<%=GSConstFile.ACCESS_KBN_WRITE%>">
                        <a href="#!" onClick="fileTreeClick('changeDir', '<bean:write name="cabDirId" />')"><bean:write name="cabDirName"  filter="false"/></a>
                      </logic:equal>
                      <logic:notEqual name="cabDirNoLink" value="<%=GSConstFile.ACCESS_KBN_WRITE%>">
                        <span class="cl_fontWeek"><bean:write name="cabDirName"  filter="false"/></span>
                      </logic:notEqual>
                    </div>
                    <ul id="tree" class="w100">
                    </ul>
                    <div class="js_tree_loader">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                      <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
                      <gsmsg:write key="cmn.loading"/>
                    </div>
                    <script>
$(function(){
  // ツリー表示初期化

    $("#tree").treeview({
        name:'fil080tree',
        allOpen:$('#sidetreecontrol a').eq(1),
        allClose:$('#sidetreecontrol a').eq(0),
        duration:'fast'
    });
    function __makeTree() {
      return createTreesValueArray();
    }

    $('#tree').treeworker_ctrl().run({
        tree:__makeTree(),
        sepKey:document.getElementsByName('sepKey')[0].value,
        selectDir:document.getElementsByName('selectDir')[0].value,
        treeClass:function (sp, selectDir) {
            this.name = sp[0];
            this.paths = new Array();
            this.open = false;
            this.label =  '';
            this.class = '';
            this.open = (selectDir != '-1' && selectDir == sp[0]);


            if (sp.length == 4) {
                    if (parseInt(sp[3]) > 0) {
                          this.label =
                               "<a href=\"#\" onclick=\"fileTreeClick(\'changeDir\', '" + sp[0] + "');\">" + sp[2] + "</a>";
                    } else {
                        this.label =
                            '<span class="cl_fontWeek">' +  sp[2] + '</span>';
                    }
              return this;
            }
            return false;
        }.toString()
    });
});

                    </script>


                  </div>
                </td>
              </tr>
            </table>

          </td>

          <td class="w80 txt_t">
            <% } %>


            <table class="table-left" border="0" cellpadding="5">

              <logic:equal name="fil080Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
                <tr>
                  <th class="no_w">
                    <span>
                      <gsmsg:write key="fil.146" />
                    </span>
                  </th>
                  <td class="txt_l">
                    <bean:write name="fil080Form" property="fil040PersonalCabOwnerName" />
                  </td>
                </tr>
              </logic:equal>

              <tr>
                <th class="no_w">
                  <span>
                    <gsmsg:write key="cmn.update.user" />
                  </span>
                </th>
                <td class="txt_l">
                  <logic:notEmpty name="fil080Form" property="fil080groupList">
                    <html:select property="fil080EditId" styleClass="wp250">
                      <html:optionsCollection name="fil080Form" property="fil080groupList" value="value" label="label" />
                    </html:select>
                  </logic:notEmpty>
                </td>
              </tr>

              <tr>
                <th class="no_w">
                  <span>
                    <gsmsg:write key="fil.21" />
                  </span>
                </th>
                <td class="txt_l">
                  <% boolean sortFolderMode = false; %>
                  <% if (errlMode == 1) { %>
                    <logic:equal name="fil080Form" property="fil080FcbSortFolder" value="<%= String.valueOf(GSConstFile.SORT_FOLDER_USE) %>">
                      <logic:equal name="fil080Form" property="fil080Mode" value="0">
                        <% sortFolderMode = true; %>
                      </logic:equal>
                    </logic:equal>
                  <% } %>

                  <% if (sortFolderMode) { %>
                    <gsmsg:write key="fil.fil080.12" /><br>
                    <bean:write name="fil080Form" property="fil080FcbSortFolderName" />
                  <% } else { %>
                    <logic:equal name="fil080Form" property="fil070ParentDirSid" value="-1">
                      <span>
                        <gsmsg:write key="wml.146" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="fil080Form" property="fil070ParentDirSid" value="-1">
                      <img class="classic-display" src="../common/images/classic/icon_folder.png" border="0" alt="">
                      <img class="original-display" src="../common/images/original/icon_folder_box.png" border="0" alt="">
                      <span>
                        <bean:write name="fil080Form" property="fil080DirPath" />
                      </span>
                    </logic:notEqual>
                  <% } %>
                </td>
              </tr>

              <tr>
                <th class="w20 no_w">
                  <span>
                    <gsmsg:write key="cmn.file" />
                  </span>
                </th>

                <td class="w80 txt_l">

                  <logic:equal name="fil080Form" property="fil080PluralKbn" value="0">
                    <div>
                      <attachmentFile:filearea
                        mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILEKANRI_TANITU) %>"
                        pluginId="<%= GSConstFile.PLUGIN_ID_FILE %>"
                        tempDirId="fil080" />
                    </div>
                  </logic:equal>

                  <logic:equal name="fil080Form" property="fil080PluralKbn" value="1">
                    <div>
                      <attachmentFile:filearea
                        mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILEKANRI) %>"
                        pluginId="<%= GSConstFile.PLUGIN_ID_FILE %>"
                        tempDirId="fil080" />
                    </div>
                  </logic:equal>

                </td>
              </tr>

              <% if (errlMode == 1) { %>
                <logic:equal name="fil080Form" property="fil080Mode" value="1">
                  <tr>
                    <th>
                      <gsmsg:write key="fil.fil080.8" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
                    </th>
                    <td>
                      <div class="verAlignMid">
                      <span class="pos_rel display_flex mr5">
                        <html:text name="fil080Form" property="fil080TradeDate" maxlength="10" styleClass="wp90 easyRegister-text datepicker js_frDatePicker" styleId="easyFrDate"/>
                        <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart" id="iconKikanStart"></span>
                      </span>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <th>
                      <gsmsg:write key="fil.fil030.18" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
                    </th>
                    <td>
                      <html:text name="fil080Form" maxlength="50" property="fil080TradeTarget" styleClass="wp400" />
                    </td>
                  </tr>
                  <tr>
                    <th>
                      <gsmsg:write key="fil.fil080.5" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
                    </th>
                    <td>
                      <div class="verAlignMid">
                      <html:checkbox name="fil080Form" property="fil080TradeMoneyKbn" styleClass="js_tradeMoneyKbn" value="1" styleId="moneyCheck" onclick="return changeTradeMoneyKbn();" />
                      <label for="moneyCheck"><gsmsg:write key="fil.157" /></label>
                      <bean:define id="tradeMoney" name="fil080Form" property="fil080TradeMoney"/>
                      <currency-input name="fil080TradeMoney" value="<%=tradeMoney %>" class="js_tradeMoney ml20 wp180" >
                      </currency-input>
                      <html:select name="fil080Form" property="fil080TradeMoneyType" styleClass="js_tradeMoneyMaster ml10">
                        <html:optionsCollection name="fil080Form" property="fil080TradeMoneyMasterList" value="value" label="label" />
                      </html:select>
                      </div>
                    </td>
                  </tr>
                </logic:equal>
              <% } %>


              <%-- ユーザ制限 --%>
              <% int dspMode = 0; %>
              <logic:equal name="fil080Form" property="fil040PersonalFlg" value="1">
                <% dspMode = 1; %>
              </logic:equal>
              <% if (errlMode == 1) { %>
                  <% dspMode = 1; %>
                <logic:equal name="fil080Form" property="fil080Mode" value="1">
                  <% dspMode = 0; %>
                </logic:equal>
              <% } %>
              <% if (dspMode != 1) { %>
                <tr>
                  <th class="no_w">
                    <span>
                      <gsmsg:write key="fil.126" />
                    </span>
                  </th>
                  <td class="txt_m txt_l w100">
                    <table class="w100" border="0">
                      <logic:equal name="fil080Form" property="fil080ParentAccessAllDspFlg" value="1">
                        <span class="hide0">
                          <button type="button" value="<gsmsg:write key="cmn.all" /><gsmsg:write key="api.cmn.view" />" class="baseBtn" onClick="return parentAccessShowOrHide(1);">
                            <gsmsg:write key="cmn.all" /><gsmsg:write key="api.cmn.view" />
                          </button>
                        </span>
                        <span class="show0">
                          <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="return parentAccessShowOrHide(0);">
                            <gsmsg:write key="cmn.close" />
                          </button>
                        </span>
                        </td>
                        </tr>
                      </logic:equal>

                      <table class="userSelectBlock">
                        <tr>
                          <td class="userSelect_fromTo">
                            <span class="userGroup_title">
                              <gsmsg:write key="cmn.add.edit.delete" />
                            </span>
                          </td>
                          <td class="userSelect_center"></td>
                          <td class="userSelect_fromTo">
                            <span class="userGroup_title">
                              <gsmsg:write key="cmn.reading" />
                            </span>
                          </td>
                        </tr>
                        <tr class="border_none">
                          <td class="userSelect_fromTo txt_t">
                            <logic:notEmpty name="fil080Form" property="fil080ParentEditList">
                              <logic:iterate id="editMdl" name="fil080Form" property="fil080ParentEditList" indexId="idx" length="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
                                <div>
                                  <logic:equal name="editMdl" property="grpFlg" value="1">
                                    <span class="cal_label-g classic-display">G</span>
                                    <span class="cal_label-g original-display"></span>
                                  </logic:equal>
                                  <span class="<%=UserUtil.getCSSClassNameNormal(editMdl.getUsrUkoFlg())%>">
                                    <bean:write name="editMdl" property="userName" />
                                  </span>
                                </div>
                              </logic:iterate>
                            </logic:notEmpty>
                            <div class="hide0">
                              <logic:equal name="fil080Form" property="fil080ParentAccessAllDspFlg" value="1">
                                <bean:size name="fil080Form" property="fil080ParentEditList" id="editSize" />
                                <logic:greaterThan name="editSize" value="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>">
                                  <span>…</span>
                                </logic:greaterThan>
                              </logic:equal>
                            </div>
                            <div class="show0">
                              <logic:notEmpty name="fil080Form" property="fil080ParentEditList">
                                <logic:iterate id="editMdl" name="fil080Form" property="fil080ParentEditList" indexId="idx" offset="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
                                  <div>
                                    <logic:equal name="editMdl" property="grpFlg" value="1">
                                      <span class="cal_label-g classic-display">G</span>
                                      <span class="cal_label-g original-display"></span>
                                    </logic:equal>
                                    <span class="<%=UserUtil.getCSSClassNameNormal(editMdl.getUsrUkoFlg())%>">
                                      <bean:write name="editMdl" property="userName" />
                                    </span>
                                  </div>
                                </logic:iterate>
                              </logic:notEmpty>
                          </td>
                          <td class="selectForm_moveArea userSelect_center">
                            <div></div>
                          </td>
                          <td class="userSelect_fromTo txt_t">
                            <logic:notEmpty name="fil080Form" property="fil080ParentReadList">
                              <logic:iterate id="readMdl" name="fil080Form" property="fil080ParentReadList" indexId="idx" length="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
                                <div>
                                  <logic:equal name="readMdl" property="grpFlg" value="1">
                                    <span class="cal_label-g classic-display">G</span>
                                    <span class="cal_label-g original-display"></span>
                                  </logic:equal>
                                  <span class="<%=UserUtil.getCSSClassNameNormal(readMdl.getUsrUkoFlg())%>">
                                    <bean:write name="readMdl" property="userName" />
                                  </span>
                                </div>
                              </logic:iterate>
                            </logic:notEmpty>
                            <div class="hide0">
                              <logic:equal name="fil080Form" property="fil080ParentAccessAllDspFlg" value="1">
                                <bean:size name="fil080Form" property="fil080ParentReadList" id="readSize" />
                                <logic:greaterThan name="readSize" value="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>">
                                  <span>…</span>
                                </logic:greaterThan>
                              </logic:equal>
                            </div>
                            <div class="show0">
                              <logic:notEmpty name="fil080Form" property="fil080ParentReadList">
                                <logic:iterate id="readMdl" name="fil080Form" property="fil080ParentReadList" indexId="idx" offset="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
                                  <div>
                                    <logic:equal name="readMdl" property="grpFlg" value="1">
                                      <span class="cal_label-g classic-display">G</span>
                                      <span class="cal_label-g original-display"></span>
                                    </logic:equal>
                                    <span class="<%=UserUtil.getCSSClassNameNormal(readMdl.getUsrUkoFlg())%>">
                                      <bean:write name="readMdl" property="userName" />
                                    </span>
                                  </div>
                                </logic:iterate>
                              </logic:notEmpty>
                            </div>
                          </td>
                        </tr>
                      </table>
                    <% } %>

                      <tr>
                        <th class="no_w">
                          <span>
                            <gsmsg:write key="fil.130" />
                          </span>
                        </th>
                        <td class="txt_l">
                          <logic:equal name="fil080Form" property="fil040PersonalFlg" value="0">
                            <logic:equal name="fil080Form" property="fil010DspCabinetKbn" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                              <span class="verAlignMid">
                                <html:radio name="fil080Form" property="fil080AccessKbn" styleId="okini0" value="0" onclick="fil080ShowOrHide();" />
                                <label for="okini0">
                                  <gsmsg:write key="cmn.not.limit" />
                                </label>
                              </span>&nbsp;
                               <span class="verAlignMid">
                                <html:radio name="fil080Form" property="fil080AccessKbn" styleId="okini1" value="1" onclick="fil080ShowOrHide();" />
                                <label for="okini1">
                                  <gsmsg:write key="cmn.do.limit" />
                                </label>
                                &nbsp;
                              </span>
                            </logic:equal>
                            <% if (errlMode == 1) { %>
                              <logic:equal name="fil080Form" property="fil080Mode" value="0"><html:radio name="fil080Form" property="fil080AccessKbn" styleClass="display_n" styleId="okini0" value="0" /><gsmsg:write key="fil.fil080.7" /></logic:equal>
                              <logic:notEqual name="fil080Form" property="fil080Mode" value="0">
                                <span class="verAlignMid">
                                  <html:radio name="fil080Form" property="fil080AccessKbn" styleId="okini0" value="0" onclick="fil080ShowOrHide();" />
                                  <label for="okini0">
                                    <gsmsg:write key="cmn.not.limit" />
                                  </label>
                                </span>&nbsp;
                                <span class="verAlignMid">
                                  <html:radio name="fil080Form" property="fil080AccessKbn" styleId="okini1" value="1" onclick="fil080ShowOrHide();" />
                                  <label for="okini1">
                                    <gsmsg:write key="cmn.do.limit" />
                                  </label>
                                  &nbsp;
                                </span>
                              </logic:notEqual>
                            <% } %>
                          </logic:equal>
                          <logic:equal name="fil080Form" property="fil040PersonalFlg" value="1">
                            <span class="verAlignMid">
                              <html:radio name="fil080Form" property="fil080AccessKbn" styleId="okini0" value="0" onclick="fil080ShowOrHide();" />
                              <label for="okini0">
                                <gsmsg:write key="cmn.not.permit" />
                              </label>
                            </span>&nbsp;
                            <span class="verAlignMid">
                              <html:radio name="fil080Form" property="fil080AccessKbn" styleId="okini1" value="1" onclick="fil080ShowOrHide();" />
                              <label for="okini1">
                                <gsmsg:write key="cmn.permit" />
                              </label>
                              &nbsp;
                            </span>
                          </logic:equal>
                        </td>
                      </tr>
                      <% String accessSettingClass = ""; %>
                      <% if (errlMode == 1) { %>
                        <logic:equal name="fil080Form" property="fil080Mode" value="0">
                          <% accessSettingClass = "display_n"; %>
                        </logic:equal>
                      <% } %>
                      <tr id="show0" class="<%= accessSettingClass %>">
                        <th class="txt_m txt_l no_w">
                          <span>
                            <gsmsg:write key="fil.102" />
                          </span>
                          <span class="cl_fontWarn">※</span>
                        </th>
                        <td class="txt_m txt_l w100">
                          <%-- 共有キャビネット内の場合のみ追加・変更・削除の権限を設定可能 --%>
                          <logic:equal name="fil080Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                            <ui:usrgrpselector name="fil080Form" property="fil080PublicAcUserUI" styleClass="hp300" />
                          </logic:equal>

                          <logic:notEqual name="fil080Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                            <ui:usrgrpselector name="fil080Form" property="fil080PrivateAcUserUI" styleClass="hp215" />
                          </logic:notEqual>

                        </td>
                      </tr>
                      <logic:equal name="fil080Form" property="admVerKbn" value="1">
                        <tr>
                          <th class="no_w">
                            <span>
                              <gsmsg:write key="fil.5" />
                            </span>
                          </th>
                          <td class="txt_l">

                            <logic:equal name="fil080Form" property="fil080VerallKbn" value="<%= VERSION_ALL_KBN_OFF %>">

                              <logic:notEmpty name="fil080Form" property="fil080VerKbnLabelList">
                                <html:select property="fil080VerKbn">
                                  <html:optionsCollection name="fil080Form" property="fil080VerKbnLabelList" value="value" label="label" />
                                </html:select>
                              </logic:notEmpty>

                            </logic:equal>

                            <logic:equal name="fil080Form" property="fil080VerallKbn" value="<%= VERSION_ALL_KBN_ON %>">

                              <logic:notEqual name="fil080Form" property="fil080VerKbn" value="0">
                                <logic:equal name="fil080Form" property="fil080VerKbn" value="11">
                                  <gsmsg:write key="fil.fil030.2" />
                                </logic:equal>
                                <logic:notEqual name="fil080Form" property="fil080VerKbn" value="11">
                                  <bean:define id="ver" name="fil080Form" property="fil080VerKbn" type="java.lang.String" />
                                  <span>
                                    <gsmsg:write key="fil.generations" arg0="<%= ver %>" />
                                  </span>
                                </logic:notEqual>
                              </logic:notEqual>

                              <logic:equal name="fil080Form" property="fil080VerKbn" value="0">
                                <span>
                                  <gsmsg:write key="fil.22" />
                                </span>
                              </logic:equal>

                            </logic:equal>

                          </td>
                        </tr>
                      </logic:equal>

                      <tr>
                        <th class="w20 no_w">
                          <span>
                            <gsmsg:write key="cmn.memo" />
                          </span>
                        </th>
                        <td class="w80 txt_l">
                          <textarea name="fil080Biko" class="wp550" rows="5" onkeyup="showLengthStr(value, <%= maxLengthBiko %>, 'inputlength');" id="inputstr"><bean:write name="fil080Form" property="fil080Biko" /></textarea>
                          <br>
                          <span class="formCounter">
                            <gsmsg:write key="cmn.current.characters" />
                            :
                          </span>
                          <span id="inputlength" class="formCounter">0</span>
                          /
                          <span class="formCounter_max"><%= maxLengthBiko %>&nbsp;
                            <gsmsg:write key="cmn.character" />
                          </span>
                        </td>
                      </tr>

                      <tr>
                        <th class="w20 no_w">
                          <span>
                            <gsmsg:write key="fil.11" />
                          </span>
                        </th>
                        <td class="w80 txt_l">
                          <textarea name="fil080UpCmt" class="wp550" rows="3" onkeyup="showLengthStr(value, <%= maxLengthUpCmt %>, 'inputlength2');" id="inputstr2"><bean:write name="fil080Form" property="fil080UpCmt" /></textarea>
                          <br>
                          <span class="formCounter">
                            <gsmsg:write key="cmn.current.characters" />
                            :
                          </span>
                          <span id="inputlength2" class="formCounter">0</span>
                          /
                          <span class="formCounter_max"><%= maxLengthUpCmt %>&nbsp;
                            <gsmsg:write key="cmn.character" />
                          </span>
                        </td>
                      </tr>

                    </table>

                    <% if (callWebmail) { %>
                  </td>
                </tr>
            </table>
            <% } %>

            <div class="footerBtn_block">
            <logic:equal name="fil080Form" property="fil080Mode" value="0">
              <% if (errlMode == 1) { %>
                <button type="button" class="baseBtn wp90 btn_originalImg-display" value="<gsmsg:write key="fil.fil070.10" />" onclick="buttonPush('fil080add');">
                  <img src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="fil.fil070.10" />">
                  <gsmsg:write key="fil.fil070.10" />
                </button>
                <button type="button" class="baseBtn btn_classicImg-display" value="<gsmsg:write key="fil.fil070.10" />" onclick="buttonPush('fil080add');">
                  <img src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="fil.fil070.10" />">
                  <gsmsg:write key="fil.fil070.10" />
                </button>
              <% } else { %>
                <button type="button" class="baseBtn" value="<gsmsg:write key="fil.16" />" onclick="buttonPush('fil080add');">
                  <img class="btn_classicImg-display" src="../file/images/classic/icon_add_file.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../file/images/original/icon_addfile.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="fil.16" />
                </button>
              <% } %>
            </logic:equal>
            <logic:equal name="fil080Form" property="fil080Mode" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil080add');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="fil080Delete('<bean:write name="fil080Form" property="fil080EditFileName" />', <%= String.valueOf(errlMode) %>);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
            <%
              if (callWebmail) {
            %>
            <button type="button" name="btn_close" class="baseBtn" value="<gsmsg:write key="cmn.close" />" onClick="window.parent.webmailEntrySubWindowClose();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.close" />
            </button>
            <%
              } else {
            %>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil080back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
            <% } %>
            </div>
        </div>

        <filekanri:deleteFileDialog commentName="fil080OpeComment" />

    </html:form>
    <% if (callWebmail) { %>
      <filekanri:fileTreeParams screenId="fil080" />
    <% } %>

  <logic:notEqual name="fil080Form" property="fil080webmail" value="1">
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </logic:notEqual>
</body>
</html:html>