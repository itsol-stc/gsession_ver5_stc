<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.ip.IpkConst" %>
<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="ipk.16" /></title>
</head>

<body onload="showOrHide();">
  <html:form action="/ipkanri/ipk060">
    <html:hidden property="CMD" />
    <html:hidden property="netSid" />
    <html:hidden property="binSid" />
    <html:hidden property="sortKey" />
    <html:hidden property="orderKey" />
    <html:hidden property="usedKbn" />
    <html:hidden property="iadLimit" />
    <html:hidden property="deleteAllCheck" />
    <logic:notEmpty name="ipk060Form" property="deleteCheck">
      <logic:iterate id="param" name="ipk060Form" property="deleteCheck">
        <input type="hidden" name="deleteCheck" value="<bean:write name="param" />">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>


    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../ipkanri/images/classic/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
          <img class="header_pluginImg" src="../ipkanri/images/original/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
        <li>
          <gsmsg:write key="cmn.ipkanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="ipk.16" />
        </li>
        <li>
          <div>
            <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush2('iadImp');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
              <gsmsg:write key="cmn.import" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk060Return');;">
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
                <div class="w50 txt_l">
                  <span>
                    <bean:write name="ipk060Form" property="netName" />
                  </span>
                </div>
                <div class="w50 txt_r">
                  <gsmsg:write key="ipk.5" />：<bean:write name="ipk060Form" property="iadCount" />
                  <span class="mr5">
                  （<gsmsg:write key="cmn.in.use" />:<bean:write name="ipk060Form" property="iadCountUse" />
                  </span>
                  <gsmsg:write key="cmn.unused" />：<bean:write name="ipk060Form" property="iadCountNotUse" />)
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
              <bean:write name="ipk060Form" property="netName" />
            </span>
          </div>
          <div class="txt_r w45">
            <span class="table_title-color fw_n fs_14">
                <gsmsg:write key="ipk.5" />：<bean:write name="ipk060Form" property="iadCount" />
                <span class="mr5">
                （<gsmsg:write key="cmn.in.use" />：<bean:write name="ipk060Form" property="iadCountUse" />
                </span>
                <gsmsg:write key="cmn.unused" />：<bean:write name="ipk060Form" property="iadCountNotUse" />)
              </span>
              <button type="button" value="<gsmsg:write key="cmn.hide" />" class="baseBtn" onClick="hideText()">
                <gsmsg:write key="cmn.hide" />
              </button>
            </div>
          </div>

        <table class="table-left m0" cellpadding="0" cellspacing="0">
          <logic:equal name="ipk060Form" property="tempExist" value="true">
            <tr>
              <th class="w30 no_w">
                <gsmsg:write key="ipk.2" />
              </th>
              <th class="w30 no_w">
                <gsmsg:write key="ipk.3" />
              </th>
              <th class="w40 no_w" colspan="3">
                <gsmsg:write key="cmn.comment" />
              </th>
            </tr>

            <logic:notEmpty name="ipk060Form" property="koukaiBinFileInfList">
              <logic:notEmpty name="ipk060Form" property="hikoukaiBinFileInfList">
                <tr>
                  <td class="txt_c" rowspan="3">
                    <span>
                      <bean:write name="ipk060Form" property="netNetad" />
                    </span>
                  </td>
                  <td class="txt_c" rowspan="3">
                    <span>
                      <bean:write name="ipk060Form" property="netSabnet" />
                    </span>
                  </td>
                  <td class="txt_t" colspan="2">
                    <span>
                      <bean:write name="ipk060Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>

                <tr>
                  <th class="w20 no_w">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                  </th>
                  <td class="w80 no_w">
                    <logic:iterate id="koukaiFileMdl" name="ipk060Form" property="koukaiBinFileInfList">
                      <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                      <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                      <a href="javascript:fileLinkClick(<bean:write name="koukaiFileMdl" property="binSid" />);">
                        <span class="textLink">
                          <bean:write name="koukaiFileMdl" property="binFileName" />
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
                    <logic:iterate id="hikoukaiFileMdl" name="ipk060Form" property="hikoukaiBinFileInfList">
                      <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                      <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                      <a href="javascript:fileLinkClick(<bean:write name="hikoukaiFileMdl" property="binSid" />);">
                        <span class="textLink">
                          <bean:write name="hikoukaiFileMdl" property="binFileName" />
                        </span>
                      </a>
                      </br>
                    </logic:iterate>
                  </td>
                </tr>
              </logic:notEmpty>
            </logic:notEmpty>

            <logic:empty name="ipk060Form" property="koukaiBinFileInfList">
              <tr>
                <td class="txt_c" rowspan="2">
                  <span>
                    <bean:write name="ipk060Form" property="netNetad" />
                  </span>
                </td>
                <td class="txt_c" rowspan="2">
                  <span>
                    <bean:write name="ipk060Form" property="netSabnet" />
                  </span>
                </td>
                <td class="txt_t" colspan="2">
                  <span>
                    <bean:write name="ipk060Form" property="netMsg" filter="false" />
                  </span>
                </td>
              </tr>

              <tr>
                <th class="w20 no_w">
                  <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.private" />)
                </th>
                <td class="w80 no_w">
                  <logic:iterate id="hikoukaiFileMdl" name="ipk060Form" property="hikoukaiBinFileInfList">
                    <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                    <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                    <a href="javascript:fileLinkClick(<bean:write name="hikoukaiFileMdl" property="binSid" />);">
                      <span class="textLink">
                        <bean:write name="hikoukaiFileMdl" property="binFileName" />
                      </span>
                    </a>
                    </br>
                  </logic:iterate>
                </td>
              </tr>
            </logic:empty>

            <logic:empty name="ipk060Form" property="hikoukaiBinFileInfList">
              <tr>
                <td class="txt_c" rowspan="2">
                  <span>
                    <bean:write name="ipk060Form" property="netNetad" />
                  </span>
                </td>
                <td class="txt_c" rowspan="2">
                  <span>
                    <bean:write name="ipk060Form" property="netSabnet" />
                  </span>
                </td>
                <td class="txt_t" colspan="2">
                  <span>
                    <bean:write name="ipk060Form" property="netMsg" filter="false" />
                  </span>
                </td>
              </tr>

              <tr>
                <th class="w20 no_w">
                  <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                </th>
                <td class="w80 no_w">
                  <logic:iterate id="koukaiFileMdl" name="ipk060Form" property="koukaiBinFileInfList">
                    <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                    <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                    <a href="javascript:fileLinkClick(<bean:write name="koukaiFileMdl" property="binSid" />);">
                      <span class="textLink">
                        <bean:write name="koukaiFileMdl" property="binFileName" />
                      </span>
                    </a>
                    </br>
                  </logic:iterate>
                </td>
              </tr>
            </logic:empty>
          </logic:equal>

          <logic:equal name="ipk060Form" property="tempExist" value="false">
            <tr>
              <th class="w30 no_w">
                <gsmsg:write key="ipk.2" />
              </td>
              <th class="w30 no_w">
                <gsmsg:write key="ipk.3" />
              </td>
              <th class="w40 no_w">
                <gsmsg:write key="cmn.comment" />
              </td>
            </tr>
            <tr>
              <td class="txt_c">
                <span>
                  <bean:write name="ipk060Form" property="netNetad" />
                </span>
              </td>
              <td class="txt_c">
                <span>
                  <bean:write name="ipk060Form" property="netSabnet" />
                </span>
              </td>
              <td class="txt_t">
                <span>
                  <bean:write name="ipk060Form" property="netMsg" filter="false" />
                </span>
              </td>
            </tr>
          </logic:equal>
        </table>
      </div>

      <logic:messagesPresent message="false">
        <html:errors/>
      </logic:messagesPresent>

      <!-- 取込みファイル -->
      <table class="table-left" cellpadding="5" cellspacing="0" border="0">

        <th class="w20 txt_l">
          <gsmsg:write key="cmn.capture.file" />
          </span>
          <span class="cl_fontWarn">※
        </th>
        <td>
          <span class="fs_13">
            <%
              jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
            %>
            <%
              String csvFileMsg = "<a href=\"../ipkanri/ipk060.do?CMD=ipk060_sample&sample=1\">"
                      + gsMsg.getMessage(request, "cmn.capture.csvfile") + "</a>";
            %>
            *<gsmsg:write key="cmn.plz.specify2" arg0="<%= csvFileMsg %>" />
          </span>
          <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU) %>"
            pluginId="<%= IpkConst.PLUGIN_ID_IPKANRI %>"
            tempDirId="ipk060" />
        </td>
        <tr>
          <th class="txt_m txt_l w20 no_w">
            <span>
              <gsmsg:write key="ipk.ipk060.2" />
            </span>
          </th>
          <td class="txt_m txt_l w80 no_w" colspan="1">
            <span class="verAlignMid">
              <html:radio property="importMode" value="0" styleId="import_tuika" />
              <label for="import_tuika">
                <gsmsg:write key="cmn.add" />
              </label>
              <html:radio property="importMode" value="1" styleClass="ml10" styleId="import_uwagaki" />
              <label for="import_uwagaki">
                <gsmsg:write key="cmn.overwrite" />
              </label>
            </span>
          </td>
        </tr>
      </table>
      <div class="footerBtn_block mt5">
        <button type="button" name="btn_add" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush2('iadImp');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk060Return');">
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