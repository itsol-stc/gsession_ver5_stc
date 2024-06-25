<%@page import="jp.groupsession.v2.fil.GSConstFile"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.46" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../file/js/dtree.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil090.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

</head>

<body>

  <html:form action="/file/fil090">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="helpPrm" value="<bean:write name="fil090Form" property="fil090Mode" />">

    <input type="hidden" name="CMD" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="selectDir" />
    <html:hidden property="sepKey" />
    <html:hidden property="moveToDir" />
    <html:hidden property="fileSid" />

    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil010DspCabinetKbn" />

    <html:hidden property="fil090Mode" />
    <html:hidden property="fil090DirSid" />
    <html:hidden property="fil090DirName" />
    <html:hidden property="fil090Biko" />
    <html:hidden property="fil090BinSid" />
    <html:hidden property="fil090SltDirPath" />
    <html:hidden property="fil090VerKbn" />
    <html:hidden property="fil090SelectPluralKbn" />

    <bean:define id="cabDirId" value=""/>
    <bean:define id="cabDirName" value=""/>
    <bean:define id="cabDirNoLink" value="0"/>
    <bean:define id="sepKey" name="fil090Form" property="sepKey" type="String"/>
    <logic:notEmpty name="fil090Form" property="treeFormLv0" scope="request">
      <logic:iterate id="lv0" name="fil090Form" property="treeFormLv0" scope="request" type="String">
        <logic:empty name="cabDirId">
         <% String[] sp = lv0.split(sepKey);
         %>
         <bean:define id="cabDirId" value="<%=sp[0] %>"/>
         <bean:define id="cabDirName" value="<%=sp[2] %>"/>
         <% if (sp.length >= 4) {%>
             <bean:define id="cabDirNoLink" value="<%=sp[3] %>"/>
         <% } %>
        </logic:empty>

        <input type="hidden" name="treeFormLv0" value="<bean:write name="lv0"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="fil090Form" property="fil040SelectDel" scope="request">
      <logic:iterate id="del" name="fil090Form" property="fil040SelectDel" scope="request">
        <input type="hidden" name="fil040SelectDel" value="<bean:write name="del"/>">
      </logic:iterate>
    </logic:notEmpty>

    <bean:parameter id="file090tree" name="file090tree" value=""/>
    <input type="hidden" name="file090tree" value="<bean:write name="file090tree" />">

    <script type="text/javascript">
      if (document.getElementsByName('file090tree')[0].value.length <= 0) {

        var sepKey = document.forms[0].sepKey.value;
        var selectDir = document.forms[0].selectDir.value;
        var openDir = '';
        var levelNum, arrayLv;

        var levelNum, arrayLv;
        for (levelNum = 10; levelNum >= 2; levelNum--) {
          arrayLv = document.getElementsByName("treeFormLv" + levelNum);
          for (i = 0; i < arrayLv.length; i++) {
            var sp = arrayLv[i].value.split(sepKey);
            if (typeof selectDir != 'undefined') {
              if (selectDir != '-1' && selectDir == sp[0]) {
//                openDir = "&quot;" + sp[1] + "&quot;:&quot;block&quot;" + openDir;
                openDir = "\"" + sp[1] + "\":\"block\"" + openDir;
                if (levelNum > 2) {
                    openDir = ',' + openDir;
                }
                selectDir = sp[1];
              }
            }
          }
        }

        openDir = '{' + openDir + '}';
        document.getElementsByName('file090tree')[0].value = openDir;
      }
    </script>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li>
          <gsmsg:write key="cmn.filekanri" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="fil090Form" property="fil090Mode" value="0">
            <gsmsg:write key="cmn.move.folder" />
          </logic:equal>
          <logic:equal name="fil090Form" property="fil090Mode" value="1">
            <gsmsg:write key="fil.move.file" />
          </logic:equal>
          <logic:equal name="fil090Form" property="fil090Mode" value="2">
            <gsmsg:write key="fil.116" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.move" />" onclick="buttonPush('fil090move');">
              <img class="classic-display" src="../common/images/classic/icon_move.png" alt="<gsmsg:write key="cmn.move" />">
              <img class="original-display" src="../common/images/original/icon_move.png" alt="<gsmsg:write key="cmn.move" />">
              <gsmsg:write key="cmn.move" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('fil090back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left" border="0" cellpadding="5">

        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.update.user" />
            </span>
          </th>
          <td class="w75 txt_l">
            <logic:notEmpty name="fil090Form" property="fil090groupList">
              <html:select property="fil090EditId">
                <html:optionsCollection name="fil090Form" property="fil090groupList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>

        <logic:equal name="fil090Form" property="fil090Mode" value="0">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="fil.21" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="fil090Form" property="fil090DirName" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.memo" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="fil090Form" property="fil090Biko" filter="false" />
              </span>
            </td>
          </tr>

        </logic:equal>

        <logic:equal name="fil090Form" property="fil090Mode" value="1">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="fil.9" />
              </span>
            </th>
            <td class="txt_l">
              <logic:notEmpty name="fil090Form" property="fil090FileLabelList">
                <logic:iterate id="file" name="fil090Form" property="fil090FileLabelList">
                  <a href="#" onclick="fileDl('fileDownload', '<bean:write name="file" property="value" />');">
                      <bean:write name="file" property="label" />
                  </a>
                  <bean:define id="fileName" name="file" property="label" />
                  <bean:define id="fileSid" name="file" property="value" />
                  <bean:define id="cabSid" name="fil090Form" property="fil010SelectCabinet" />
                  <bean:define id="dirSid" name="fil090Form" property="fil010SelectDirSid" />
                  <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) {%>
                  <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
                  <span class="ml5 cursor_p" onclick="openPreviewWindow('../file/fil010.do?CMD=fileDownloadInline&fileSid=<%= fileSid %>&fil010SelectCabinet=<%= cabSid %>&il010SelectDirSid=<%= dirSid %>', '<%= extension %>');">
                    <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                    <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                  </span>
                  <% } %>
                  <br>
                </logic:iterate>
              </logic:notEmpty>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.version" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="fil090Form" property="fil090VerKbn" value="0">
                  <gsmsg:write key="fil.fil090kn.2" />
                </logic:equal>
                <logic:notEqual name="fil090Form" property="fil090VerKbn" value="0">
                  <bean:define id="ver" name="fil090Form" property="fil090VerKbn" type="java.lang.String" />
                  <gsmsg:write key="fil.generations" arg0="<%=ver%>" />
                </logic:notEqual>
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cmn.memo" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="fil090Form" property="fil090Biko" filter="false" />
              </span>
            </td>
          </tr>

        </logic:equal>


        <logic:equal name="fil090Form" property="fil090Mode" value="2">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="fil.119" />
              </span>
            </th>
            <td class="txt_l">
              <logic:notEmpty name="fil090Form" property="fil090FolderNameList">
                <table class="table-left w60">
                  <tr>
                    <th class="txt_c"><gsmsg:write key="cmn.folder" /></th>
                  </tr>
                  <tr>
                    <td>
                      <logic:iterate id="folderName" name="fil090Form" property="fil090FolderNameList">
                        <bean:write name="folderName" />
                        <br>
                      </logic:iterate>
                    </td>
                  </tr>
                </table>
              </logic:notEmpty>

              <logic:notEmpty name="fil090Form" property="fil090FileLabelList">
                <table class="table-left w60">
                  <tr>
                    <th class="txt_c"><gsmsg:write key="cmn.file" /></th>
                  </tr>
                  <tr>
                    <td>
                      <logic:iterate id="file" name="fil090Form" property="fil090FileLabelList">
                        <a href="#" onclick="fileDl('fileDownload', '<bean:write name="file" property="value" />');">
                          <bean:write name="file" property="label" />
                        </a>
                        <bean:define id="fileName" name="file" property="label" />
                        <bean:define id="fileSid" name="file" property="value" />
                        <bean:define id="cabSid" name="fil090Form" property="fil010SelectCabinet" />
                        <bean:define id="dirSid" name="fil090Form" property="fil010SelectDirSid" />
                        <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                        <% String extension = String.valueOf(fileName).substring((String.valueOf(fileName).lastIndexOf(".") + 1)); %>
                        <span class="ml5 cursor_p" onclick="openPreviewWindow('../file/fil010.do?CMD=fileDownloadInline&fileSid=<%= fileSid %>&fil010SelectCabinet=<%= cabSid %>&il010SelectDirSid=<%= dirSid %>', '<%= extension %>');">
                          <img class="btn_classicImg-display js_preview" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                          <img class="btn_originalImg-display js_preview" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                        </span>
                        <% } %>
                        <br>
                      </logic:iterate>
                    </td>
                  </tr>
                </table>
              </logic:notEmpty>
            </td>
          </tr>
        </logic:equal>


        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="fil.75" />
            </span>
          </th>
          <td class="w80 txt_l">
            <div id="moveDirName" class="word_b-all">
              <img class="classic-display" src="../common/images/classic/icon_folder.png" border="0" alt="">
              <img class="original-display" src="../common/images/original/icon_folder_box.png" border="0" alt="">
              <bean:write name="fil090Form" property="fil090SltDirPath" />
            </div>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="fil.23" />
            </span>
          </th>
          <td class="w80 txt_l">
            <logic:notEmpty name="fil090Form" property="fil090CabinetCombo">
              <html:select name="fil090Form" property="fil090SelectCabinetSid" styleClass="wp300" onchange="buttonPush('selectCabinet');">
                <logic:iterate id="cbBean" name="fil090Form" property="fil090CabinetCombo" scope="request">
                  <bean:define id="cbSid" name="cbBean" property="fcbSid" type="java.lang.Integer" />
                  <logic:equal name="cbBean" property="fcbPersonalFlg" value="0">
                    <html:option value="<%=String.valueOf(cbSid)%>">
                      <bean:write name="cbBean" property="fcbName" />
                    </html:option>
                  </logic:equal>
                  <logic:notEqual name="cbBean" property="fcbPersonalFlg" value="0">
                    <html:option styleClass="select_mygroup-bgc" value="<%=String.valueOf(cbSid)%>">
                      <bean:write name="cbBean" property="fcbName" />
                    </html:option>
                  </logic:notEqual>
                </logic:iterate>
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>
        <tr>
          <th class="w25 no_w">
            <span>
              <gsmsg:write key="cmn.select.destination" />
            </span>
          </th>
          <td class="w80 txt_l">


            <div  class="hp300 ofx_s ofy_s no_w">
              <div>
                <logic:equal name="cabDirNoLink" value="<%=GSConstFile.ACCESS_KBN_WRITE%>">
                  <bean:define id="hrefVal">javascript:(fileTreeClick('detailDir', '<bean:write name="cabDirId" />'))</bean:define>
                  <a href="<bean:write name="hrefVal"/>"><img class="classic-display mr5" src="../common/images/classic/icon_folder.png" alt=""><img class="original-display mr5" src="../common/images/original/icon_folder_box.png"  alt=""><bean:write name="cabDirName" filter="false"/></a>
                </logic:equal>
                <logic:notEqual name="cabDirNoLink" value="<%=GSConstFile.ACCESS_KBN_WRITE%>">
                  <span class="cl_fontWeek"><img class="classic-display mr5" src="../common/images/classic/icon_folder.png" alt=""><img class="original-display mr5" src="../common/images/original/icon_folder_box.png"  alt=""><bean:write name="cabDirName"  filter="false"/></span>
                </logic:notEqual>

              </div>
              <ul id="tree" class="w100">
              </ul>
              <div class="js_tree_loader">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
                <gsmsg:write key="cmn.loading"/>
              </div>
            </div>

          </td>
        </tr>
      </table>

      <div class="footerBtn_block txt_r">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.move" />" onclick="buttonPush('fil090move');">
          <img class="classic-display" src="../common/images/classic/icon_move.png" alt="<gsmsg:write key="cmn.move" />">
          <img class="original-display" src="../common/images/original/icon_move.png" alt="<gsmsg:write key="cmn.move" />">
          <gsmsg:write key="cmn.move" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('fil090back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

  </html:form>

  <filekanri:fileTreeParams screenId="fil090" />

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>