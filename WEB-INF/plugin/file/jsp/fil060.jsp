<%@page import="jp.groupsession.v2.usr.model.UsrLabelValueBean"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.model.FileParentAccessDspModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>

<%
  String maxLengthBiko = String.valueOf(jp.groupsession.v2.fil.GSConstFile.MAX_LENGTH_FOLDER_BIKO);
%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="cmn.edit.folder" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil060.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../project/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onunload="windowClose();" onload="showOrHide();parentAccessShowOrHide(<bean:write name="fil060Form" property="fil060ParentAccessAll" />);showLengthId($('#inputstr')[0], <%=maxLengthBiko%>, 'inputlength');">

  <html:form action="/file/fil060">
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

    <html:hidden property="fil050DirSid" />
    <html:hidden property="fil050ParentDirSid" />
    <html:hidden property="fil060CmdMode" />
    <html:hidden property="fil060PluginId" />
    <html:hidden property="fil060ParentAccessAll" />
    <html:hidden property="fil060ParentZeroUser" />

    <html:hidden name="fil060Form" property="fil100SltCabinetSid" />
    <html:hidden name="fil060Form" property="fil100ChkTrgFolder" />
    <html:hidden name="fil060Form" property="fil100ChkTrgFile" />
    <html:hidden name="fil060Form" property="fil100SearchMode" />
    <html:hidden name="fil060Form" property="fil100ChkWdTrgName" />
    <html:hidden name="fil060Form" property="fil100ChkWdTrgBiko" />
    <html:hidden name="fil060Form" property="fil100ChkWdTrgText" />
    <html:hidden name="fil060Form" property="fileSearchfromYear" />
    <html:hidden name="fil060Form" property="fileSearchfromMonth" />
    <html:hidden name="fil060Form" property="fileSearchfromDay" />
    <html:hidden name="fil060Form" property="fileSearchtoYear" />
    <html:hidden name="fil060Form" property="fileSearchtoMonth" />
    <html:hidden name="fil060Form" property="fileSearchtoDay" />
    <html:hidden name="fil060Form" property="fil100ChkOnOff" />

    <html:hidden name="fil060Form" property="fil100SltCabinetKbn" />
    <html:hidden name="fil060Form" property="fil100ChkTrgDeleted" />
    <html:hidden name="fil060Form" property="fil100ChkTrgDeletedFolder" />
    <html:hidden name="fil060Form" property="fil100SearchTradeTarget" />
    <html:hidden name="fil060Form" property="fil100SearchTradeMoneyNoset" />
    <html:hidden name="fil060Form" property="fil100SearchTradeMoneyKbn" />
    <html:hidden name="fil060Form" property="fil100SearchTradeMoney" />
    <html:hidden name="fil060Form" property="fil100SearchTradeMoneyTo" />
    <html:hidden name="fil060Form" property="fil100SearchTradeMoneyType" />
    <html:hidden name="fil060Form" property="fil100SearchTradeMoneyJudge" />
    <html:hidden name="fil060Form" property="fil100SearchTradeDateKbn" />
    <html:hidden name="fil060Form" property="fil100SearchTradeDateFrom" />
    <html:hidden name="fil060Form" property="fil100SearchTradeDateTo" />

    <html:hidden name="fil060Form" property="fil100SvSltCabinetSid" />
    <html:hidden name="fil060Form" property="fil100SvChkTrgFolder" />
    <html:hidden name="fil060Form" property="fil100SvChkTrgFile" />
    <html:hidden name="fil060Form" property="fil100SvChkTrgDeleted" />
    <html:hidden name="fil060Form" property="fil100SvChkTrgDeletedFolder" />
    <html:hidden name="fil060Form" property="fil100SvSearchMode" />
    <html:hidden name="fil060Form" property="fil100SvChkWdTrgName" />
    <html:hidden name="fil060Form" property="fil100SvChkWdTrgBiko" />
    <html:hidden name="fil060Form" property="fil100SvChkWdTrgText" />
    <html:hidden name="fil060Form" property="fil100SvChkWdKeyWord" />
    <html:hidden name="fil060Form" property="fileSvSearchfromYear" />
    <html:hidden name="fil060Form" property="fileSvSearchfromMonth" />
    <html:hidden name="fil060Form" property="fileSvSearchfromDay" />
    <html:hidden name="fil060Form" property="fileSvSearchtoYear" />
    <html:hidden name="fil060Form" property="fileSvSearchtoMonth" />
    <html:hidden name="fil060Form" property="fileSvSearchtoDay" />
    <html:hidden name="fil060Form" property="fil100SvChkOnOff" />
    <html:hidden name="fil060Form" property="fil100sortKey" />
    <html:hidden name="fil060Form" property="fil100orderKey" />
    <html:hidden name="fil060Form" property="fil100pageNum1" />
    <html:hidden name="fil060Form" property="fil100pageNum2" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeTarget" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeMoney" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeMoneyTo" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeMoneyType" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeMoneyJudge" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeMoneyNoset" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeMoneyKbn" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeDateFrom" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeDateTo" />
    <html:hidden name="fil060Form" property="fil100SvSearchTradeDateKbn" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <logic:equal name="fil060Form" property="fil040PersonalFlg" value="0">
      <input type="hidden" name="helpPrm" value="<bean:write name="fil060Form" property="fil060CmdMode" />">
    </logic:equal>

    <logic:equal name="fil060Form" property="fil040PersonalFlg" value="1">
      <input type="hidden" name="helpPrm" value="<bean:write name="fil060Form" property="fil060CmdMode" /><bean:write name="fil060Form" property="fil040PersonalFlg" />">
    </logic:equal>

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
          <logic:equal name="fil060Form" property="fil060CmdMode" value="0">
            <gsmsg:write key="fil.39" />
          </logic:equal>

          <logic:equal name="fil060Form" property="fil060CmdMode" value="1">
            <gsmsg:write key="cmn.edit.folder" />
          </logic:equal>
        </li>
        <li>
          <div>
            <logic:equal name="fil060Form" property="fil060CmdMode" value="1">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil060add');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil060delete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>

            <logic:equal name="fil060Form" property="fil060CmdMode" value="0">
              <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('fil060add');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>

            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil060back');">
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

        <logic:equal name="fil060Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="fil.146" />
              </span>
            </th>
            <td class="txt_l">
              <bean:write name="fil060Form" property="fil040PersonalCabOwnerName" />
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
            <logic:notEmpty name="fil060Form" property="fil060groupList">
              <html:select property="fil060EditId" styleClass="select01">
                <html:optionsCollection name="fil060Form" property="fil060groupList" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="fil.21" />
              <span class="cl_fontWarn">※</span>
            </span>
          </th>
          <td class="txt_l">
            <html:text property="fil060DirName" maxlength="70" style="width:599px;" />
          </td>
        </tr>

        <%-- ユーザ制限 --%>
        <logic:equal name="fil060Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="fil.126" />
              </span>
            </th>
            <td class="txt_m txt_l w100">
              <table class="w100" border="0">

                <logic:equal name="fil060Form" property="fil060ParentAccessAllDspFlg" value="1">
                  <span class="hide0">
                    <button type="button" value="<gsmsg:write key="sml.218" />" class="baseBtn" onClick="return parentAccessShowOrHide(1);">
                      <gsmsg:write key="sml.218" />
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
                      <logic:notEmpty name="fil060Form" property="fil060ParentEditList">
                        <logic:iterate id="editMdl" name="fil060Form" property="fil060ParentEditList" indexId="idx" length="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
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
                        <logic:equal name="fil060Form" property="fil060ParentAccessAllDspFlg" value="1">
                          <bean:size name="fil060Form" property="fil060ParentEditList" id="editSize" />
                          <logic:greaterThan name="editSize" value="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>">
                            <span>…</span>
                          </logic:greaterThan>
                        </logic:equal>
                      </div>
                      <div class="show0">
                        <logic:notEmpty name="fil060Form" property="fil060ParentEditList">
                          <logic:iterate id="editMdl" name="fil060Form" property="fil060ParentEditList" indexId="idx" offset="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
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
                      </div>
                    </td>
                    <td class="selectForm_moveArea userSelect_center">
                      <div></div>
                    </td>
                    <td class="userSelect_fromTo txt_t">
                      <logic:notEmpty name="fil060Form" property="fil060ParentReadList">
                        <logic:iterate id="readMdl" name="fil060Form" property="fil060ParentReadList" indexId="idx" length="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
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
                        <logic:equal name="fil060Form" property="fil060ParentAccessAllDspFlg" value="1">
                          <bean:size name="fil060Form" property="fil060ParentReadList" id="readSize" />
                          <logic:greaterThan name="readSize" value="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>">
                            <span>…</span>
                          </logic:greaterThan>
                        </logic:equal>
                      </div>
                      <div class="show0">

                        <logic:notEmpty name="fil060Form" property="fil060ParentReadList">
                          <logic:iterate id="readMdl" name="fil060Form" property="fil060ParentReadList" indexId="idx" offset="<%=GSConstFile.MAXCOUNT_PARENT_ACCESS%>" type="FileParentAccessDspModel">
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
                </logic:equal>

                <tr>
                  <th class="no_w">
                    <gsmsg:write key="fil.130" />
                  </th>
                  <td class="txt_l ">
                    <logic:equal name="fil060Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                      <span class="verAlignMid">
                        <html:radio name="fil060Form" property="fil060AccessKbn" styleId="okini0" value="0" onclick="showOrHide();" />
                        <label for="okini0" class="mr10"><gsmsg:write key="cmn.not.limit" /></label>
                        <html:radio name="fil060Form" property="fil060AccessKbn" styleId="okini1" value="1" onclick="showOrHide();" />
                        <label for="okini1"><gsmsg:write key="cmn.do.limit" /></label>

                        <logic:notEqual name="fil060Form" property="fil060CmdMode" value="<%=GSConstFile.CMN_MODE_ADD%>">
                          <html:checkbox name="fil060Form" property="file060AdaptIncFile" value="1" styleId="adaptIncFile" styleClass="ml10" />
                          <label for="adaptIncFile"><gsmsg:write key="fil.127" /></label>
                        </logic:notEqual>
                      </span>
                    </logic:equal>
                    <logic:equal name="fil060Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PRIVATE%>">
                      <span class="verAlignMid">
                        <html:radio name="fil060Form" property="fil060AccessKbn" styleId="okini0" value="0" onclick="showOrHide();" />
                        <label for="okini0" class="mr10"><gsmsg:write key="cmn.not.permit" /></label>
                        <html:radio name="fil060Form" property="fil060AccessKbn" styleId="okini1" value="1" onclick="showOrHide();" />
                        <label for="okini1"><gsmsg:write key="cmn.permit" /></label>
                      </span>
                    </logic:equal>
                  </td>
                </tr>

                <tr id="hide0"></tr>
                <tr id="show0">
                  <th class="txt_m txt_l no_w">
                    <span>
                      <gsmsg:write key="fil.102" />
                    </span>
                    <span class="cl_fontWarn">※</span>
                  </th>
                  <td class="txt_m txt_l w100">
                    <logic:equal name="fil060Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                      <ui:usrgrpselector name="fil060Form" property="fil060PublicAcUserUI" styleClass="hp300" />
                    </logic:equal>
                    <logic:notEqual name="fil060Form" property="fil040PersonalFlg" value="<%=GSConstFile.DSP_CABINET_PUBLIC%>">
                      <ui:usrgrpselector name="fil060Form" property="fil060PrivateAcUserUI" styleClass="hp215" />
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
                    <textarea name="fil060Biko" class="w100" rows="5" onkeyup="showLengthStr(value, <%=maxLengthBiko%>, 'inputlength');" id="inputstr"><bean:write name="fil060Form" property="fil060Biko" /></textarea>
                    <span class="formCounter">
                      <gsmsg:write key="cmn.current.characters" />:
                    </span>
                    <span id="inputlength" class="formCounter">0</span><span class="ml5">/</span>
                    <span class="formCounter_max"><%=maxLengthBiko%><gsmsg:write key="cmn.character" />
                    </span>
                  </td>
                </tr>
              </table>
              <div class="footerBtn_block">
                <logic:equal name="fil060Form" property="fil060CmdMode" value="1">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onclick="buttonPush('fil060add');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                    <gsmsg:write key="cmn.edit" />
                  </button>
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil060delete');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                </logic:equal>

                <logic:equal name="fil060Form" property="fil060CmdMode" value="0">
                  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('fil060add');">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                    <gsmsg:write key="cmn.add" />
                  </button>
                </logic:equal>

                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil060back');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </div>
  </html:form>

  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>