<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.ip.IpkConst" %>
<!DOCTYPE html>

<gsmsg:define id="siyou" msgkey="cmn.in.use" />
<gsmsg:define id="misiyou" msgkey="cmn.unused" />

<%
  String kbn_siyou = String.valueOf(jp.groupsession.v2.ip.IpkConst.USEDKBN_SIYOU);
  String kbn_misiyou = String.valueOf(jp.groupsession.v2.ip.IpkConst.USEDKBN_MISIYOU);
  String kbn_siyou_str = siyou;
  String kbn_misiyou_str = misiyou;
  String maxLengthComment = String.valueOf(jp.groupsession.v2.ip.IpkConst.MAX_LENGTH_MSG);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<title>GROUPSESSION <gsmsg:write key="ipk.ipk050.1" /></title>
</head>

<body onload="showOrHide();showLengthId($('#inputstr')[0], <%=maxLengthComment%>, 'inputlength');scroll2();">
  <html:form action="/ipkanri/ipk050">
    <html:hidden property="netSid" />
    <html:hidden property="iadSid" />
    <html:hidden property="binSid" />
    <html:hidden property="ipk050NetSid" />
    <html:hidden property="CMD" />
    <html:hidden property="iadCount" />
    <html:hidden property="iadCountUse" />
    <html:hidden property="iadCountNotUse" />
    <html:hidden property="textNum" />
    <html:hidden property="iadPageNum" />
    <html:hidden property="maxPageNum" />
    <html:hidden property="sortKey" />
    <html:hidden property="orderKey" />
    <html:hidden property="returnCmd" />
    <html:hidden property="usedKbn" />
    <html:hidden property="ipk050AdminFlg" />
    <html:hidden property="ipk050NetAdminFlg" />
    <html:hidden property="iadLimit" />
    <html:hidden property="deleteAllCheck" />
    <html:hidden property="ipk070PageNow" />
    <html:hidden property="ipk070MaxPageNum" />
    <html:hidden property="ipk070SltNet" />
    <html:hidden property="ipk070SltUser" />
    <html:hidden property="ipk070SltGroup" />
    <html:hidden property="ipk070SearchSortKey1" />
    <html:hidden property="ipk070SearchOrderKey1" />
    <html:hidden property="ipk070SearchSortKey2" />
    <html:hidden property="ipk070SearchOrderKey2" />
    <html:hidden property="ipk070KeyWord" />
    <html:hidden property="ipk070KeyWordkbn" />
    <html:hidden property="ipk070SvNetSid" />
    <html:hidden property="ipk070SvSltNet" />
    <html:hidden property="ipk070SvGrpSid" />
    <html:hidden property="ipk070SvUsrSid" />
    <html:hidden property="ipk070SvSearchSortKey1" />
    <html:hidden property="ipk070SvSearchOrderKey1" />
    <html:hidden property="ipk070SvSearchSortKey2" />
    <html:hidden property="ipk070SvSearchOrderKey2" />
    <html:hidden property="ipk070SvKeyWord" />
    <html:hidden property="ipk070SvKeyWordkbn" />

    <logic:notEmpty name="ipk050Form" property="ipk070SearchTarget">
      <logic:iterate id="sTarget" name="ipk050Form" property="ipk070SearchTarget">
        <input type="hidden" name="ipk070SearchTarget" value="<bean:write name="sTarget" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="ipk050Form" property="ipk070SvSearchTarget">
      <logic:iterate id="svSTarget" name="ipk050Form" property="ipk070SvSearchTarget">
        <input type="hidden" name="ipk070SvSearchTarget" value="<bean:write name="svSTarget" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="ipk050Form" property="deleteCheck">
      <logic:iterate id="param" name="ipk050Form" property="deleteCheck">
        <input type="hidden" name="deleteCheck" value="<bean:write name="param" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:equal name="ipk050Form" property="textNum" value="2">
      <html:hidden property="iadAddText1" />
    </logic:equal>

    <logic:equal name="ipk050Form" property="textNum" value="3">
      <html:hidden property="iadAddText1" />
      <html:hidden property="iadAddText2" />
    </logic:equal>

    <logic:equal name="ipk050Form" property="textNum" value="4">
      <html:hidden property="iadAddText1" />
      <html:hidden property="iadAddText2" />
      <html:hidden property="iadAddText3" />
    </logic:equal>

    <html:hidden name="ipk050Form" property="ipk050DspKbn" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="helpPrm" value="<bean:write name='ipk050Form' property='ipk050DspKbn' />">

    <html:hidden property="ipk050ScrollFlg" />

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../ipkanri/images/classic/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
          <img class="header_pluginImg" src="../ipkanri/images/original/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
        </li>
        <li>
          <gsmsg:write key="cmn.ipkanri" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
            <logic:empty name="ipk050Form" property="iadSid">
              <gsmsg:write key="ipk.ipk050.2" />
            </logic:empty>
            <logic:notEmpty name="ipk050Form" property="iadSid">
              <gsmsg:write key="ipk.ipk050.1" />
            </logic:notEmpty>
          </logic:equal>
          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
            <gsmsg:write key="ipk.ipk050.3" />
          </logic:equal>
        </li>
        <li>
          <div>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <logic:empty name="ipk050Form" property="iadSid">
                <button type="button" name="add" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onClick="buttonPush2('iadAdd');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                  <gsmsg:write key="cmn.entry" />
                </button>
              </logic:empty>
              <logic:notEmpty name="ipk050Form" property="iadSid">
                <button type="button" name="edit" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="buttonPush2('iadEdit');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.edit" />
                </button>
              <logic:notEmpty name="ipk050Form" property="iadSid">
                <button type="button" name="" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush2('ipadDelete');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:notEmpty>
              </logic:notEmpty>
            </logic:equal>
            <button type="button" name="cancel" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipReturn');" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">


      <div id="hide">
        <table class="table-left">
          <tr>
            <th class="bgC_header1 table_title-color w20">
              <img class="btn_classicImg-display bor1" src="../ipkanri/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.ipkanri" />">
              <img class="btn_originalImg-display" src="../ipkanri/images/original/icon_network.png" alt="<gsmsg:write key="cmn.ipkanri" />">
              <gsmsg:write key="ipk.4" />
            </th>
            <td class="w80">
              <div class="component_bothEnd">
                <div class="w50">
                  <span>
                    <bean:write name="ipk050Form" property="netName" />
                  </span>
                </div>
                <div class="w50 txt_r">
                  <gsmsg:write key="ipk.5" />：<bean:write name="ipk050Form" property="iadCount" />
                  <span class="mr5">
                  (<gsmsg:write key="cmn.in.use" />：<bean:write name="ipk050Form" property="iadCountUse" />
                  </span>
                  <gsmsg:write key="cmn.unused" />：<bean:write name="ipk050Form" property="iadCountNotUse" />)
                  <button type="button" value="<gsmsg:write key="cmn.show" />" class="baseBtn" onClick="showText()">
                    <gsmsg:write key="cmn.show" />
                  </button>
                </div>
              </div>
            </td>
          </tr>
        </table>
      </div>

      <div id="show">

        <div class="component_bothEnd bgC_header1 bor1 border_bottom_none p5">
          <div class="w55 txt_l">
            <img class="btn_classicImg-display bor1" src="../ipkanri/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.ipkanri" />">
            <img class="btn_originalImg-display" src="../ipkanri/images/original/icon_network.png" alt="<gsmsg:write key="cmn.ipkanri" />">
            <span class="table_title-color">
              <bean:write name="ipk050Form" property="netName" />
            </span>
          </div>
          <div class="txt_r w45">
            <span class="table_title-color fw_n fs_14">
               <gsmsg:write key="ipk.5" />：<bean:write name="ipk050Form" property="iadCount" />
                <span class="mr5">
               (<gsmsg:write key="cmn.in.use" />：<bean:write name="ipk050Form" property="iadCountUse" />
               </span>
               <gsmsg:write key="cmn.unused" />：<bean:write name="ipk050Form" property="iadCountNotUse" />)
             </span>
             <button type="button" value="<gsmsg:write key="cmn.hide" />" class="baseBtn" onClick="hideText()">
               <gsmsg:write key="cmn.hide" />
             </button>
           </div>
          </div>

        <table class="table-left m0" cellpadding="0" cellspacing="0">
          <logic:equal name="ipk050Form" property="ipk050TempExist" value="true">
            <logic:equal name="ipk050Form" property="ipk050NetAdminFlg" value="true">
              <tr>
                <th class="w20 no_w">
                  <gsmsg:write key="ipk.2" />
                </th>
                <th class="w20 no_w">
                  <gsmsg:write key="ipk.3" />
                </th>
                <th class="no_w" colspan="2">
                  <gsmsg:write key="cmn.comment" />
                </th>
              </tr>

              <logic:notEmpty name="ipk050Form" property="koukaiBinFileInfList">
                <logic:notEmpty name="ipk050Form" property="hikoukaiBinFileInfList">
                  <tr>
                    <td class="txt_c" rowspan="3">
                      <span>
                        <bean:write name="ipk050Form" property="netNetad" />
                      </span>

                    </td>
                    <td class="txt_c" rowspan="3">
                      <span>
                        <bean:write name="ipk050Form" property="netSabnet" />
                      </span>

                    </td>
                    <td class="txt_l txt_t" colspan="2">
                      <span>
                        <bean:write name="ipk050Form" property="netMsg" filter="false" />
                      </span>
                    </td>
                  </tr>

                  <tr>
                    <th class="w20 no_w">
                      <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                    </th>
                    <td class="w80 no_w">
                      <logic:iterate id="koukaiFileMdl" name="ipk050Form" property="koukaiBinFileInfList">
                        <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                        <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                        <a href="javascript:fileLinkClick(<bean:write name="koukaiFileMdl" property="binSid" />);">
                          <span class="cl_linkDef">
                            <bean:write name="koukaiFileMdl" property="binFileName" />
                            <bean:write name="koukaiFileMdl" property="binFileSizeDsp" />
                          </span>
                        </a>
                        <br>
                      </logic:iterate>
                    </td>
                  </tr>

                  <tr>
                    <th class="w20 no_w">
                      <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.private" />)
                    </th>
                    <td class="w80 no_w">
                      <logic:iterate id="hikoukaiFileMdl" name="ipk050Form" property="hikoukaiBinFileInfList">
                        <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                        <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                        <a href="javascript:fileLinkClick(<bean:write name="hikoukaiFileMdl" property="binSid" />);">
                          <span class="cl_linkDef">
                            <bean:write name="hikoukaiFileMdl" property="binFileName" />
                            <bean:write name="hikoukaiFileMdl" property="binFileSizeDsp" />
                          </span>
                        </a>
                        </br>
                      </logic:iterate>
                    </td>
                  </tr>
                </logic:notEmpty>
              </logic:notEmpty>

              <logic:empty name="ipk050Form" property="koukaiBinFileInfList">
                <tr>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netNetad" />
                    </span>
                  </td>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netSabnet" />
                    </span>
                  </td>
                  <td class="txt_l txt_t" colspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netMsg" filter="false" />
                    </span>

                  </td>
                </tr>

                <tr>
                  <th class="w20 no_w">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.private" />)
                  </th>
                  <td class="w80 no_w">
                    <logic:iterate id="hikoukaiFileMdl" name="ipk050Form" property="hikoukaiBinFileInfList">
                      <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                      <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                      <a href="javascript:fileLinkClick(<bean:write name="hikoukaiFileMdl" property="binSid" />);">
                        <span class="cl_linkDef">
                          <bean:write name="hikoukaiFileMdl" property="binFileName" />
                          <bean:write name="hikoukaiFileMdl" property="binFileSizeDsp" />
                        </span>
                      </a>
                      </br>
                    </logic:iterate>
                  </td>
                </tr>
              </logic:empty>

              <logic:empty name="ipk050Form" property="hikoukaiBinFileInfList">
                <tr>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netNetad" />
                    </span>

                  </td>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netSabnet" />
                    </span>

                  </td>
                  <td class="txt_l txt_t" colspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>

                <tr>
                  <th class="w20 no_w">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                  </th>
                  <td class="w80 no_w">
                    <logic:iterate id="koukaiFileMdl" name="ipk050Form" property="koukaiBinFileInfList">
                      <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                      <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                      <a href="javascript:fileLinkClick(<bean:write name="koukaiFileMdl" property="binSid" />);">
                        <span class="cl_linkDef">
                          <bean:write name="koukaiFileMdl" property="binFileName" />
                          <bean:write name="koukaiFileMdl" property="binFileSizeDsp" />
                        </span>
                      </a>
                      </br>
                    </logic:iterate>
                  </td>
                </tr>
              </logic:empty>
            </logic:equal>

            <logic:equal name="ipk050Form" property="ipk050NetAdminFlg" value="false">
              <logic:notEmpty name="ipk050Form" property="koukaiBinFileInfList">
                <th class="w20 no_w">
                  <gsmsg:write key="ipk.2" />
                </th>
                <th class="w20 no_w">
                  <gsmsg:write key="ipk.3" />
                </th>
                <th class="no_w" colspan="2">
                  <gsmsg:write key="cmn.comment" />
                </th>
                </tr>

                <tr>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netNetad" />
                    </span>
                  </td>

                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netSabnet" />
                    </span>
                  </td>

                  <td class="txt_l" colspan="2">
                    <span>
                      <bean:write name="ipk050Form" property="netMsg" filter="false" />
                    </span>
                  </td>

                </tr>

                <tr>
                  <th class="no_w">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                  </th>
                  <td class="txt_c">
                    <table cellpadding="0" cellpadding="0" border="0">
                      <logic:iterate id="koukaiFileMdl" name="ipk050Form" property="koukaiBinFileInfList">
                        <tr class="border_none">
                          <td class="border_none">
                            <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                            <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                          </td>
                          <td class="menu_bun border_none">
                            <a href="javascript:fileLinkClick(<bean:write name="koukaiFileMdl" property="binSid" />);">
                              <span class="cl_linkDef">
                                <bean:write name="koukaiFileMdl" property="binFileName" />
                                <bean:write name="koukaiFileMdl" property="binFileSizeDsp" />
                              </span>
                            </a>
                          </td>
                        </tr>
                      </logic:iterate>
                    </table>
                  </td>
                </tr>
              </logic:notEmpty>

              <logic:empty name="ipk050Form" property="koukaiBinFileInfList">
                <th class="w20 no_w">
                  <gsmsg:write key="ipk.2" />
                </th>
                <th class="w20 no_w">
                  <gsmsg:write key="ipk.3" />
                </th>
                <th class="no_w">
                  <gsmsg:write key="cmn.comment" />
                </th>
                </tr>

                <tr>
                  <td class="txt_c">
                    <span>
                      <bean:write name="ipk050Form" property="netNetad" />
                    </span>
                  </td>

                  <td class="txt_c">
                    <span>
                      <bean:write name="ipk050Form" property="netSabnet" />
                    </span>
                  </td>

                  <td class="txt_l">
                    <span>
                      <bean:write name="ipk050Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>
              </logic:empty>
            </logic:equal>
          </logic:equal>

          <logic:equal name="ipk050Form" property="ipk050TempExist" value="false">
            <th class="w20 no_w">
              <gsmsg:write key="ipk.2" />
            </th>
            <th class="w20 no_w">
              <gsmsg:write key="ipk.3" />
            </th>
            <th class="no_w">
              <gsmsg:write key="cmn.comment" />
            </th>
            </tr>
            <tr>
              <td class="txt_c">
                <span>
                  <bean:write name="ipk050Form" property="netNetad" />
                </span>
              </td>
              <td class="txt_c">
                <span>
                  <bean:write name="ipk050Form" property="netSabnet" />
                </span>
              </td>
              <td class="txt_l">
                <span>
                  <bean:write name="ipk050Form" property="netMsg" filter="false" />
                </span>
              </td>
            </tr>
          </logic:equal>
        </table>
      </div>

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left" cellpadding="0">


      <tr class="bgC_header1 table_title-color border txt_l">
        <td colspan="2">
        <span>
          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
            <logic:notEqual name="ipk050Form" property="CMD" value="ipEdit">
              <gsmsg:write key="ipk.ipk050.4" />
            </logic:notEqual>
            <logic:equal name="ipk050Form" property="CMD" value="ipEdit">
              <gsmsg:write key="ipk.ipk050.5" />
            </logic:equal>
          </logic:equal>
          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
            <gsmsg:write key="cmn.detail" />
          </logic:equal>
        </span>
        </td>
      </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="ipk.6" />
            </span>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <span class="cl_fontWarn">※</span>
            </logic:equal>
          </th>
          <td class="w80">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <logic:equal name="ipk050Form" property="textNum" value="1">
                <input type="text" name="iadAddText1" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText1" />" class="text_number">.
                <input type="text" name="iadAddText2" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText2" />" class="wp50 txt_r">.
                <input type="text" name="iadAddText3" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText3" />" class="wp50 txt_r">.
                <input type="text" name="iadAddText4" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText4" />" class="wp50 txt_r">
              </logic:equal>

              <logic:equal name="ipk050Form" property="textNum" value="2">
                <bean:write name="ipk050Form" property="iadAddText1" />.
                <input type="text" name="iadAddText2" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText2" />" class="wp50 txt_r">.
                <input type="text" name="iadAddText3" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText3" />" class="wp50 txt_r">.
                <input type="text" name="iadAddText4" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText4" />" class="wp50 txt_r">
              </logic:equal>

              <logic:equal name="ipk050Form" property="textNum" value="3">
                <bean:write name="ipk050Form" property="iadAddText1" />.
                <bean:write name="ipk050Form" property="iadAddText2" />.
                <input type="text" name="iadAddText3" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText3" />" class="wp50 txt_r">.
                <input type="text" name="iadAddText4" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText4" />" class="wp50 txt_r">
              </logic:equal>

              <logic:equal name="ipk050Form" property="textNum" value="4">
                <bean:write name="ipk050Form" property="iadAddText1" />.
                <bean:write name="ipk050Form" property="iadAddText2" />.
                <bean:write name="ipk050Form" property="iadAddText3" />.
                <input type="text" name="iadAddText4" maxlength="3" value="<bean:write name="ipk050Form" property="iadAddText4" />" class="wp50 txt_r">
              </logic:equal>
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="iadAddText1" />.
              <bean:write name="ipk050Form" property="iadAddText2" />.
              <bean:write name="ipk050Form" property="iadAddText3" />.
              <bean:write name="ipk050Form" property="iadAddText4" />
            </logic:equal>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="ipk.7" />
            </span>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <span class="cl_fontWarn">※</span>
            </logic:equal>
          </th>
          <td class="w80">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <input type="text" name="iadMachineName" class="wp200" maxlength="50" value="<bean:write name="ipk050Form" property="iadMachineName" />">
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="iadMachineName" />
            </logic:equal>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="ipk.11" />
            </span>
          </th>
          <td class="w80" cellspacing="0" cellpadding="0">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <span class="verAlignMid mr10">
              <html:radio property="iadUse" styleId="kbn_siyou" value="<%=kbn_siyou%>" />
                <label for="kbn_siyou"><%=kbn_siyou_str%></label>
              </span>
              <span class="verAlignMid">
             <html:radio property="iadUse" styleId="kbn_misiyou" value="<%=kbn_misiyou%>" />
                <label for="kbn_misiyou"><%=kbn_misiyou_str%></label>
              </span>
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <logic:equal name="ipk050Form" property="iadUse" value="1">
                <gsmsg:write key="cmn.in.use" />
              </logic:equal>
              <logic:equal name="ipk050Form" property="iadUse" value="0">
                <gsmsg:write key="cmn.unused" />
              </logic:equal>
            </logic:equal>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.comment" />
            </span>
          </th>
          <td class="w80">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <textarea name="iadMsg" rows="7" class="wp500" onkeyup="showLengthStr(value, <%=maxLengthComment%>, 'inputlength');" id="inputstr"><bean:write name="ipk050Form" property="iadMsg" /></textarea>
              <br>
              <span class="formCounter">
                <gsmsg:write key="cmn.current.characters" />:
              </span>
              <span id="inputlength" class="formCounter">0</span>
              <span class="formCounter_max">/<%=maxLengthComment%>
                <gsmsg:write key="cmn.character" />
              </span>
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="iadMsgHtml" filter="false" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w20">
            <span class="no_w">
              <gsmsg:write key="cmn.asset.register.num" />
            </span>
          </th>
          <td class="w80">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <input type="text" name="iadPrtMngNum" class="wp200" maxlength="50" value="<bean:write name="ipk050Form" property="iadPrtMngNum" />">
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="iadPrtMngNum" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <span>CPU</span>
          </th>
          <td class="w80" cellspacing="0" cellpadding="0">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <html:select name="ipk050Form" property="cpuSelect" styleClass="select01">
                <logic:notEmpty name="ipk050Form" property="ipk050cpuLabelList">
                  <logic:iterate id="cpu" name="ipk050Form" property="ipk050cpuLabelList" indexId="idx">
                    <bean:define id="backclass" value="bg_color" />
                    <bean:define id="backpat" value="<%=String.valueOf((idx.intValue() % 2) + 1)%>" />
                    <bean:define id="back" value="<%=String.valueOf(backclass) + String.valueOf(backpat)%>" />
                    <bean:define id="cpuValue" name="cpu" property="value" />
                    <html:option value="<%=String.valueOf(cpuValue)%>" styleClass="<%=String.valueOf(back)%>">
                      <bean:write name="cpu" property="label" filter="false" />
                    </html:option>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="cpuName" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.memory" />
            </span>
          </th>
          <td class="w80" cellspacing="0" cellpadding="0">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <html:select name="ipk050Form" property="memorySelect" styleClass="select01">
                <logic:notEmpty name="ipk050Form" property="ipk050memoryLabelList">
                  <logic:iterate id="memory" name="ipk050Form" property="ipk050memoryLabelList" indexId="idxmemory">
                    <bean:define id="memoryBackclass" value="bg_color" />
                    <bean:define id="memoryBackpat" value="<%=String.valueOf((idxmemory.intValue() % 2) + 1)%>" />
                    <bean:define id="memoryBack" value="<%=String.valueOf(memoryBackclass) + String.valueOf(memoryBackpat)%>" />
                    <bean:define id="memoryValue" name="memory" property="value" />
                    <html:option value="<%=String.valueOf(memoryValue)%>" styleClass="<%=String.valueOf(memoryBack)%>">
                      <bean:write name="memory" property="label" filter="false" />
                    </html:option>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="memoryName" />
            </logic:equal>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>HD</span>
          </th>
          <td class="w80" cellspacing="0" cellpadding="0">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <html:select name="ipk050Form" property="hdSelect" styleClass="select01">
                <logic:notEmpty name="ipk050Form" property="ipk050hdLabelList">
                  <logic:iterate id="hd" name="ipk050Form" property="ipk050hdLabelList" indexId="idxhd">
                    <bean:define id="hdBackclass" value="bg_color" />
                    <bean:define id="hdBackpat" value="<%=String.valueOf((idxhd.intValue() % 2) + 1)%>" />
                    <bean:define id="hdBack" value="<%=String.valueOf(hdBackclass) + String.valueOf(hdBackpat)%>" />
                    <bean:define id="hdValue" name="hd" property="value" />
                    <html:option value="<%=String.valueOf(hdValue)%>" styleClass="<%=String.valueOf(hdBack)%>">
                      <bean:write name="hd" property="label" filter="false" />
                    </html:option>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
            </logic:equal>
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
              <bean:write name="ipk050Form" property="hdName" />
            </logic:equal>
            <a id="add_user" name="add_user"></a>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.attached" />
              (<gsmsg:write key="cmn.public" />)
            </span>
          </th>

          <td class="w80">
            <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
                pluginId="<%= IpkConst.PLUGIN_ID_IPKANRI %>"
                tempDirId="ipk050"
                tempDirPlus="koukai"
                formId="1" />
            </logic:equal>


          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
            <logic:notEmpty name="ipk050Form" property="ipk050KoukaiFileLabelList">
              <table>
                <tr class="border_none">
                  <td class="no_w border_none">
                    <logic:iterate id="ipadKoukaiFileMdl" name="ipk050Form" property="ipadKoukaiBinFileInfList">
                      <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                      <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                      <a href="javascript:ipAdrFileLinkClick(<bean:write name='ipadKoukaiFileMdl' property='binSid' />);">
                        <span class="cl_linkDef">
                          <bean:write name="ipadKoukaiFileMdl" property="binFileName" />
                          <bean:write name="ipadKoukaiFileMdl" property="binFileSizeDsp" />
                        </span>
                      </a>
                      <br>
                    </logic:iterate>
                  </td>
                </tr>
              </table>
            </logic:notEmpty>
          </logic:equal>

          </td>
        </tr>

        <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
          <tr>
            <th class="w20 no_w">
              <span>
                <gsmsg:write key="cmn.attached" />(<gsmsg:write key="cmn.private" />)
              </span>
            </th>

            <td class="w80">
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
                pluginId="<%= IpkConst.PLUGIN_ID_IPKANRI %>"
                tempDirId="ipk050"
                tempDirPlus="hikoukai"
                formId="2" />
            </td>
          </tr>
        </logic:equal>
        <tr>

          <th class="w20 no_w">
            <span>
              <gsmsg:write key="cmn.employer" />
            </span>
          </th>
          <td class="w80">
          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
            <ui:usrgrpselector name="ipk050Form" property="adminSidListUI" styleClass="hp215" />
          </logic:equal>

          <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="false">
            <logic:notEmpty name="ipk050Form" property="employerUserList">
              <logic:iterate id="labelValueBean" name="ipk050Form" property="employerUserList">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal name="labelValueBean" property="usrUkoFlg" value="1">
                  <bean:define id="mukoUserClass" value="mukoUser" />
                </logic:equal>
                <span class="<%=mukoUserClass%>">
                  <bean:write name="labelValueBean" property="label" />
                </span>
                <br>
              </logic:iterate>
            </logic:notEmpty>
          </logic:equal>
          </td>
        </tr>
      </table>

      <div class="footerBtn_block mt5">
        <logic:equal name="ipk050Form" property="ipk050AdminFlg" value="true">
          <logic:empty name="ipk050Form" property="iadSid">
            <button type="button" name="add" value="<gsmsg:write key="cmn.entry" />" class="baseBtn" onClick="buttonPush2('iadAdd');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              <gsmsg:write key="cmn.entry" />
            </button>
          </logic:empty>
          <logic:notEmpty name="ipk050Form" property="iadSid">
            <button type="button" name="edit" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="buttonPush2('iadEdit');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.edit" />
            </button>
          </logic:notEmpty>
          <logic:notEmpty name="ipk050Form" property="iadSid">
            <button type="button" name="" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush2('ipadDelete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:notEmpty>
        </logic:equal>
        <button type="button" name="cancel" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipReturn');" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>
</html:html>