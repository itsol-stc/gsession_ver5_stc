<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../ipkanri/js/ipk040.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />

<title>GROUPSESSION <gsmsg:write key="ipk.15" /></title>
</head>

<body onload="showOrHide();">
  <html:form action="/ipkanri/ipk040">
    <html:hidden property="netSid" />
    <html:hidden property="iadSid" />
    <input type="hidden" name="CMD" value="search">
    <html:hidden property="iadPageNum" />
    <html:hidden property="iadCount" />
    <html:hidden property="iadCountUse" />
    <html:hidden property="iadCountNotUse" />
    <html:hidden property="maxPageNum" />
    <html:hidden property="sortKey" />
    <html:hidden property="orderKey" />
    <html:hidden property="binSid" />
    <html:hidden property="returnCmd" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w90 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../ipkanri/images/classic/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
          <img class="header_pluginImg" src="../ipkanri/images/original/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
        </li>
        <li>
          <gsmsg:write key="cmn.ipkanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="ipk.15" />
        </li>
        <li>
          <div>
            <logic:equal name="ipk040Form" property="iadNetAdmFlg" value="true">
              <button type="button" name="add" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="ipk040ButtonPush('ipAdd', '' , '0');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
                <gsmsg:write key="cmn.add" />
              </button>
            </logic:equal>
            <logic:equal name="ipk040Form" property="iadNetAdmFlg" value="true">
              <button type="button" name="export" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush2('import');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
                <gsmsg:write key="cmn.import" />
              </button>
            </logic:equal>
            <logic:notEqual name="ipk040Form" property="maxCount" value="0">
              <button type="button" name="export" value="<gsmsg:write key="cmn.export" />" class="baseBtn" onClick="buttonPush2('export');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
                <gsmsg:write key="cmn.export" />
              </button>
            </logic:notEqual>
            <logic:equal name="ipk040Form" property="checkBoxDspFlg" value="true">
              <button type="button" name="delete" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush2('selectDelete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
            </logic:equal>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('return', '');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper w90 mrl_auto">

      <div id="hide">
        <table class="table-left">
          <tr>
            <th class="bgC_header1 table_title-color w20">
              <img class="btn_classicImg-display bor1" src="../ipkanri/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.ipkanri" />">
              <img class="btn_originalImg-display" src="../ipkanri/images/original/icon_network.png" alt="<gsmsg:write key="cmn.ipkanri" />">
              <gsmsg:write key="ipk.4" />
              </span>
            </th>
            <td>
              <div class="component_bothEnd">
                <div class="w55">
                  <span>
                    <bean:write name="ipk040Form" property="netName" />
                  </span>
                </div>
                <div class="w45 txt_r">
                  <gsmsg:write key="ipk.5" />：<bean:write name="ipk040Form" property="iadCount" />
                  (<gsmsg:write key="cmn.in.use" />:<bean:write name="ipk040Form" property="iadCountUse" />
                  <gsmsg:write key="cmn.unused" />:<bean:write name="ipk040Form" property="iadCountNotUse" />)
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
          <div class="w60 txt_l">
            <img class="btn_classicImg-display bor1" src="../ipkanri/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.ipkanri" />">
            <img class="btn_originalImg-display" src="../ipkanri/images/original/icon_network.png" alt="<gsmsg:write key="cmn.ipkanri" />">
            <span class="table_title-color">
              <bean:write name="ipk040Form" property="netName" />
            </span>
          </div>
          <div class="txt_r w40">
            <span class="table_title-color fw_n fs_14">
            <gsmsg:write key="ipk.5" />：<bean:write name="ipk040Form" property="iadCount" />
            （<gsmsg:write key="cmn.in.use" />:<bean:write name="ipk040Form" property="iadCountUse" />
            <gsmsg:write key="cmn.unused" />:<bean:write name="ipk040Form" property="iadCountNotUse" />)
            </span>
            <button type="button" value="<gsmsg:write key="cmn.hide" />" class="baseBtn" onClick="hideText()">
              <gsmsg:write key="cmn.hide" />
            </button>
          </div>
        </div>

        <table class="table-left m0" cellpadding="0">
          <logic:equal name="ipk040Form" property="tempExist" value="true">
            <logic:equal name="ipk040Form" property="iadNetAdmFlg" value="true">
              <tr>
                <th class="w30 no_w">
                  <gsmsg:write key="ipk.2" />
                </th>
                <th class="w30 no_w">
                  <gsmsg:write key="ipk.3" />
                </th>
                <th class="w40 no_w" colspan="2">
                  <gsmsg:write key="cmn.comment" />
                </th>
              </tr>

              <logic:notEmpty name="ipk040Form" property="koukaiBinFileInfList">
                <logic:notEmpty name="ipk040Form" property="hikoukaiBinFileInfList">
                  <tr>
                    <td class="txt_c" rowspan="3">
                      <span>
                        <bean:write name="ipk040Form" property="netNetad" />
                      </span>
                    </td>
                    <td class="txt_c" rowspan="3">
                      <span>
                        <bean:write name="ipk040Form" property="netSabnet" />
                      </span>
                    </td>
                    <td class="txt_l txt_t" colspan="2">
                      <span>
                        <bean:write name="ipk040Form" property="netMsg" filter="false" />
                      </span>
                    </td>
                  </tr>
                  <tr>
                    <th>
                      <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                    </th>
                    <td class="no_w">
                      <logic:iterate id="koukaiFileMdl" name="ipk040Form" property="koukaiBinFileInfList">
                        <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                        <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                        <a href="javascript:fileLinkClick(<bean:write name="koukaiFileMdl" property="binSid" />);">
                          <span class="cl_linkDef">
                            <bean:write name="koukaiFileMdl" property="binFileName" />
                            <bean:write name="koukaiFileMdl" property="binFileSizeDsp" />
                        </a>
                        <br>
                      </logic:iterate>
                    </td>
                  </tr>
                  <tr>
                    <th class="no_w">
                      <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.private" />)
                    </th>
                    <td class="no_w">
                      <logic:iterate id="hikoukaiFileMdl" name="ipk040Form" property="hikoukaiBinFileInfList">
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


              <logic:empty name="ipk040Form" property="koukaiBinFileInfList">
                <tr>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netNetad" />
                    </span>
                  </td>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netSabnet" />
                    </span>
                  </td>
                  <td class="txt_l txt_t" colspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>
                <tr>
                  <th class="no_w w20">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.private" />)
                  </th>
                  <td class="no_w">
                    <logic:iterate id="hikoukaiFileMdl" name="ipk040Form" property="hikoukaiBinFileInfList">
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

              <logic:empty name="ipk040Form" property="hikoukaiBinFileInfList">
                <tr>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netNetad" />
                    </span>
                  </td>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netSabnet" />
                    </span>
                  </td>
                  <td class="txt_l txt_t" colspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>
                <tr>
                  <th class="no_w">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                  </th>
                  <td class="border_none no_w">
                    <logic:iterate id="koukaiFileMdl" name="ipk040Form" property="koukaiBinFileInfList">
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

            <logic:equal name="ipk040Form" property="iadNetAdmFlg" value="false">
              <logic:notEmpty name="ipk040Form" property="koukaiBinFileInfList">
                <tr>
                  <th class="w30 no_w">
                    <gsmsg:write key="ipk.2" />
                  </th>
                  <th class="w30 no_w">
                    <gsmsg:write key="ipk.3" />
                  </th>
                  <th class="w40 no_w" colspan="2">
                    <gsmsg:write key="cmn.comment" />
                  </th>
                </tr>
                <tr>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netNetad" />
                    </span>
                  </td>
                  <td class="txt_c" rowspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netSabnet" />
                    </span>
                  </td>
                  <td class="txt_l txt_t" colspan="2">
                    <span>
                      <bean:write name="ipk040Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>
                <tr>
                  <th class="no_w">
                    <gsmsg:write key="cmn.attach.file" />(<gsmsg:write key="cmn.public" />)
                  </th>
                  <td class="txt_c no_w">
                    <table>
                      <logic:iterate id="koukaiFileMdl" name="ipk040Form" property="koukaiBinFileInfList">
                        <tr class="border_none">
                          <td class="border_none">
                            <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.attached" />">
                            <img class="original-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.attached" />">
                          </td>
                          <td class="border_none">
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
              <logic:empty name="ipk040Form" property="koukaiBinFileInfList">
                <tr>
                  <th class="w30 no_w">
                    <gsmsg:write key="ipk.2" />
                  </th>
                  <th class="w30 no_w">
                    <gsmsg:write key="ipk.3" />
                  </th>
                  <th class="w40 no_w">
                    <gsmsg:write key="cmn.comment" />
                  </th>
                </tr>
                <tr>
                  <td class="txt_c">
                    <span>
                      <bean:write name="ipk040Form" property="netNetad" />
                    </span>
                  </td>
                  <td class="txt_c">
                    <span>
                      <bean:write name="ipk040Form" property="netSabnet" />
                    </span>
                  </td>
                  <td class="txt_l txt_t">
                    <span>
                      <bean:write name="ipk040Form" property="netMsg" filter="false" />
                    </span>
                  </td>
                </tr>
              </logic:empty>
            </logic:equal>
          </logic:equal>

          <logic:equal name="ipk040Form" property="tempExist" value="false">
            <tr>
              <th class="w30 no_w">
                <gsmsg:write key="ipk.2" />
              </th>
              <th class="w30 no_w">
                <gsmsg:write key="ipk.3" />
              </th>
              <th class="w40 no_w">
                <gsmsg:write key="cmn.comment" />
              </th>
            </tr>

            <tr>
              <td class="txt_c">
                <span>
                  <bean:write name="ipk040Form" property="netNetad" />
                </span>
              </td>
              <td class="txt_c">
                <span>
                  <bean:write name="ipk040Form" property="netSabnet" />
                </span>
              </td>
              <td class="txt_l txt_t">
                <span>
                  <bean:write name="ipk040Form" property="netMsg" filter="false" />
                </span>
              </td>
            </tr>
          </logic:equal>
        </table>
      </div>

      <table class="txt_l w100" border="0" cellpadding="0">
        <tr>
          <td class="no_w txt_m" colspan="2">
            <span class="verAlignMid">
              <span class="fs_14 fw_b">
                <gsmsg:write key="cmn.number.display" />:
              </span>
              <html:select name="ipk040Form" property="iadLimit" styleClass="ml5" onchange="pageSelect();">
                <html:option value="10">
                  <gsmsg:write key="ipk.ipk040.2" arg0="１０" />
                </html:option>
                <html:option value="20">
                  <gsmsg:write key="ipk.ipk040.2" arg0="２０" />
                </html:option>
                <html:option value="30">
                  <gsmsg:write key="ipk.ipk040.2" arg0="３０" />
                </html:option>
                <html:option value="50">
                  <gsmsg:write key="ipk.ipk040.2" arg0="５０" />
                </html:option>
                <html:option value="80">
                  <gsmsg:write key="ipk.ipk040.2" arg0="８０" />
                </html:option>
                <html:option value="100">
                  <gsmsg:write key="ipk.ipk040.2" arg0="１００" />
                </html:option>
                <html:option value="0">
                  <gsmsg:write key="cmn.showing.all" />
                </html:option>
              </html:select>
            </span>
            <span class="verAlignMid">
            <span class="fs_14 fw_b ml10 fw_b">
              <gsmsg:write key="ipk.ipk040.1" />:
            </span>
            <span class="verAlignMid mr5 ml5">
              <html:radio property="usedKbn" value="2" onclick="pageSelect();" styleId="sort_all" />
              <label class="fs_14" for="sort_all">
                <gsmsg:write key="cmn.all" />
              </label>
              </span>
              <span class="verAlignMid mr5">
              <html:radio property="usedKbn" value="1" onclick="pageSelect();" styleClass="ml5" styleId="sort_use" />
              <label class="fs_14" for="sort_use">
                <gsmsg:write key="cmn.in.use" />
              </label>
              </span>
              <span class="verAlignMid">
              <html:radio property="usedKbn" value="0" onclick="pageSelect();" styleClass="ml5" styleId="sort_notuse" />
              <label class="fs_14" for="sort_notuse">
                <gsmsg:write key="cmn.unused" />
              </label>
            </span>
            </span>
          </td>

          <td class="txt_r">
            <html:text property="ipk070KeyWord" styleClass="wp200" maxlength="50" />
            <button type="submit" name="btn_search" class="baseBtn" value="<gsmsg:write key="cmn.search" />" onClick="buttonPush2('search');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
              <gsmsg:write key="cmn.search" />
            </button>
          </td>
        </tr>
      </table>

      <logic:greaterThan name="ipk040Form" property="maxPageNum" value="1">
        <div class="paging txt_r mt5">
          <button type="button" class="webIconBtn" onClick="buttonPush2('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="iadPage1" onchange="changePage1();">
            <html:optionsCollection name="ipk040Form" property="iadPageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush2('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <!--- IPList --->
      <table class="table-top">

        <tr>
          <logic:equal name="ipk040Form" property="checkBoxDspFlg" value="true">
            <th class="txt_c cursor_p w3 js_tableTopCheck js_tableTopCheck-header" rowspan="2">
              <html:checkbox name="ipk040Form" property="deleteAllCheck" value="check" onclick="changeChk();" />
            </th>
          </logic:equal>
          <logic:equal name="ipk040Form" property="sortKey" value="0">
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="w15 txt_c no_w cursor_p" rowspan="2">
                <a href="#!" onclick="return sortOrderKey(0,1);">
                  <span>
                    <gsmsg:write key="ipk.6" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="w15 txt_c no_w cursor_p" rowspan="2">
                <a href="#!" onclick="return sortOrderKey(0,0);">
                  <span>
                    <gsmsg:write key="ipk.6" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="ipk040Form" property="sortKey" value="0">
            <th class="w15 txt_c no_w cursor_p" rowspan="2">
              <a href="#!" onclick="return sortOrderKey(0,0);">
                <span>
                  &nbsp;
                  <gsmsg:write key="ipk.6" />
                  &nbsp;
                </span>
              </a>
            </th>
          </logic:notEqual>
          <logic:equal name="ipk040Form" property="sortKey" value="1">
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="txt_c w20 cursor_p" rowspan="2">
                <a href="#!" onclick="return sortOrderKey(1,1);">
                  <span>
                    <gsmsg:write key="ipk.7" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="txt_c w20 cursor_p" rowspan="2">
                <a href="#!" onclick="return sortOrderKey(1,0);">
                  <span>
                    <gsmsg:write key="ipk.7" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="ipk040Form" property="sortKey" value="1">
            <th class="txt_c w20 cursor_p" rowspan="2">
              <a href="#!" onclick="return sortOrderKey(1,0);">
                <span>
                  &nbsp;
                  <gsmsg:write key="ipk.7" />
                  &nbsp;
                </span>
              </a>
            </th>
          </logic:notEqual>
          <logic:equal name="ipk040Form" property="sortKey" value="2">
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="w5 txt_c no_w cursor_p" rowspan="2">
                <a href="#!" onclick="return sortOrderKey(2,1);">
                  <span>
                    <gsmsg:write key="ipk.21" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="w5 txt_c no_w cursor_p" rowspan="2">
                <a href="#!" onclick="return sortOrderKey(2,0);">
                  <span>
                    <gsmsg:write key="ipk.21" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>

          <logic:notEqual name="ipk040Form" property="sortKey" value="2">
            <th class="w5 txt_c no_w cursor_p" rowspan="2">
              <a href="#!" onclick="return sortOrderKey(2,0);">
                <span>
                  <gsmsg:write key="ipk.21" />
                </span>
              </a>
            </th>
          </logic:notEqual>
          <th class="w10 txt_c no_w" rowspan="2">
            <span>
              <gsmsg:write key="cmn.employer" />
            </span>
          </th>

          <logic:equal name="ipk040Form" property="sortKey" value="6">
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="no_w w15 txt_c cursor_p">
                <a href="#!" onclick="return sortOrderKey(6,0);">
                  <span>CPU</span>
                  <span class="classic-display">▲</span>
                  <span class="original-display">
                    <i class="icon-sort_up"></i>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="no_w w15 txt_c cursor_p">
                <a href="#!" onclick="return sortOrderKey(6,1);">
                  <span>CPU</span>
                  <span class="classic-display">▼</span>
                  <span class="original-display">
                    <i class="icon-sort_down"></i>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="ipk040Form" property="sortKey" value="6">
            <th class="no_w w15 txt_c cursor_p">
              <a href="#!" onclick="return sortOrderKey(6,1);">
                <span>&nbsp; CPU &nbsp;</span>
              </a>
            </th>
          </logic:notEqual>

          <logic:equal name="ipk040Form" property="sortKey" value="7">
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="no_w w15 txt_c cursor_p">
                <a href="#!" onclick="return sortOrderKey(7,0);">
                  <span>
                    <gsmsg:write key="cmn.memory" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="no_w w15 txt_c cursor_p">
                <a href="#!" onclick="return sortOrderKey(7,1);">
                  <span>
                    <gsmsg:write key="cmn.memory" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="ipk040Form" property="sortKey" value="7">
            <th class="no_w w15 txt_c cursor_p">
              <a href="#!" onclick="return sortOrderKey(7,1);">
                <span>
                  &nbsp;
                  <gsmsg:write key="cmn.memory" />
                  &nbsp;
                </span>
              </a>
            </th>
          </logic:notEqual>

          <logic:equal name="ipk040Form" property="sortKey" value="8">
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="no_w w15 txt_c cursor_p">
                <a href="#!" onclick="return sortOrderKey(8,0);">
                  <span>HD</span>
                  <span class="classic-display">▲</span>
                  <span class="original-display">
                    <i class="icon-sort_up"></i>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="no_w w15 txt_c cursor_p">
                <a href="#!" onclick="return sortOrderKey(8,1);">
                  <span>HD</span>
                  <span class="classic-display">▼</span>
                  <span class="original-display">
                    <i class="icon-sort_down"></i>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="ipk040Form" property="sortKey" value="8">
            <th class="no_w w15 txt_c cursor_p">
              <a href="#!" onclick="return sortOrderKey(8,1);">
                <span>&nbsp; HD &nbsp;</span>
              </a>
            </th>
          </logic:notEqual>
          <th class="txt_c no_w" rowspan="2">
            <span>
              <gsmsg:write key="cmn.detail" />
            </span>
          </th>
        </tr>

        <tr>
          <logic:equal name="ipk040Form" property="sortKey" value="3">
            <logic:equal name="ipk040Form" property="orderKey" value="0">
              <th class="txt_c w100 no_w cursor_p" colspan="3">
                <a href="#!" onclick="return sortOrderKey(3,1);">
                  <span>
                    <gsmsg:write key="cmn.comment" />
                    <span class="classic-display">▲</span>
                    <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
            <logic:equal name="ipk040Form" property="orderKey" value="1">
              <th class="txt_c w100 no_w cursor_p" colspan="3">
                <a href="#!" onclick="return sortOrderKey(3,0);">
                  <span>
                    <gsmsg:write key="cmn.comment" />
                    <span class="classic-display">▼</span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </span>
                </a>
              </th>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="ipk040Form" property="sortKey" value="3">
            <th class="txt_c w100 no_w cursor_p" colspan="3">
              <a href="#!" onclick="return sortOrderKey(3,0);">
                <span>
                  &nbsp;
                  <gsmsg:write key="cmn.comment" />
                  &nbsp;
                </span>
              </a>
            </th>
          </logic:notEqual>
        </tr>

        <% String bgC; %>
        <logic:notEmpty name="ipk040Form" property="ipkAddList">
          <logic:iterate id="param" name="ipk040Form" property="ipkAddList" indexId="idx">
            <% if (idx % 2 == 0) {%>
            <% bgC = "bgC_tableCell"; %>
            <% } else {%>
            <% bgC = "bgC_tableCellEvn"; %>
            <% } %>
            <tr>
              <logic:equal name="ipk040Form" property="checkBoxDspFlg" value="true">
                <td class="<%= bgC %> txt_c js_tableTopCheck cursor_p" rowspan="2">
                  <logic:equal name="param" property="iadAdmFlg" value="true">
                    <bean:define id="delCheck" name="param" property="iadSid" type="java.lang.Integer" />
                    <html:multibox property="deleteCheck" value="<%= Integer.toString(delCheck.intValue()) %>" />
                  </logic:equal>
                </td>
              </logic:equal>

              <td class="<%= bgC %>" rowspan="2">
                <bean:write name="param" property="iadIpadDsp" />
              </td>
              <td class="<%= bgC %>" rowspan="2">
                <bean:write name="param" property="iadName" />
              </td>
              <td class="<%= bgC %> txt_c no_w" rowspan="2">
                <logic:equal name="param" property="iadUseKbn" value="0">
                  <span>
                    <gsmsg:write key="cmn.unused" />
                  </span>
                </logic:equal>
                <logic:equal name="param" property="iadUseKbn" value="1">
                  <gsmsg:write key="cmn.in.use" />
                </logic:equal>
              </td>
              <td class="<%= bgC %> no_w" rowspan="2">

                <logic:notEmpty name="param" property="userSeiMei">
                  <logic:iterate id="addAdm" name="param" property="userSeiMei">
                    <logic:equal name="addAdm" property="usrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU) %>">
                      <bean:define id="mukoUserClass" value="" />
                      <logic:equal name="addAdm" property="usrUkoFlg" value="1">
                        <bean:define id="mukoUserClass" value="mukoUser" />
                      </logic:equal>
                      <span class="<%=mukoUserClass%>">
                        <bean:write name="addAdm" property="usiSei" />
                        &nbsp;
                        <bean:write name="addAdm" property="usiMei" />
                      </span>
                      <br>
                    </logic:equal>
                    <logic:equal name="addAdm" property="usrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
                      <strike><bean:write name="addAdm" property="usiSei" />&nbsp;<bean:write name="addAdm" property="usiMei" /></strike>
                      <br>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>

              </td>
              <td class="<%= bgC %> no_w">
                <bean:write name="param" property="iadCpuName" filter="false" />
              </td>
              <td class="<%= bgC %> no_w">
                <bean:write name="param" property="iadMemoryName" filter="false" />
              </td>
              <td class="<%= bgC %> no_w">
                <bean:write name="param" property="iadHdName" filter="false" />
              </td>
              <td class="<%= bgC %> txt_c no_w w5" rowspan="2">
                <button type="button" class="baseBtn" name="edit" value="<gsmsg:write key="cmn.detail" />" onClick="ipk040ButtonPush('ipEdit', '<bean:write name="param" property="iadSid" />', '0');">
                  <gsmsg:write key="cmn.detail" />
                </button>
              </td>
            </tr>
            <tr>
              <td class="<%= bgC %> txt_t" colspan="3">
                <bean:write name="param" property="iadMsg" />
              </td>
            </tr>

          </logic:iterate>
        </logic:notEmpty>

      </table>
      <logic:greaterThan name="ipk040Form" property="maxPageNum" value="1">
        <div class="paging txt_r">
          <button type="button" class="webIconBtn" onClick="buttonPush2('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="iadPage2" onchange="changePage2();">
            <html:optionsCollection name="ipk040Form" property="iadPageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush2('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <div class="footerBtn_block mt5">
        <logic:equal name="ipk040Form" property="iadNetAdmFlg" value="true">
          <button type="button" name="add" value="<gsmsg:write key="cmn.add" />" class="baseBtn" onClick="ipk040ButtonPush('ipAdd', '' , '0');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </logic:equal>
        <logic:equal name="ipk040Form" property="iadNetAdmFlg" value="true">
          <button type="button" name="export" value="<gsmsg:write key="cmn.import" />" class="baseBtn" onClick="buttonPush2('import');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
            <gsmsg:write key="cmn.import" />
          </button>
        </logic:equal>
        <logic:notEqual name="ipk040Form" property="maxCount" value="0">
          <button type="button" name="export" value="<gsmsg:write key="cmn.export" />" class="baseBtn" onClick="buttonPush2('export');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.export" />">
            <gsmsg:write key="cmn.export" />
          </button>
        </logic:notEqual>
        <logic:equal name="ipk040Form" property="checkBoxDspFlg" value="true">
          <button type="button" name="delete" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush2('selectDelete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('return', '');">
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