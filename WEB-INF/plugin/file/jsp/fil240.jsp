<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.fil010.FileLinkSimpleModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.fil240.1" /></title>

<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/dtree.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/file.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil240.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../file/css/file.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href="../file/css/dtree.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>
<body>
  <html:form action="/file/fil240">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <!-- BODY -->
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="cmnMode" value="">
    <input type="hidden" name="backDsp" value="">
    <input type="hidden" name="backDspLow" value="">
    <input type="hidden" name="fil010SelectCabinet" value="">
    <input type="hidden" name="fil010SelectDirSid" value="">
    <input type="hidden" name="fil070DirSid" value="">
    <html:hidden name="fil240Form" property="fil010DspCabinetKbn" />
    <html:hidden name="fil240Form" property="fil240PageNum" />
    <html:hidden name="fil240Form" property="backDspCall" />

    <html:hidden property="fileSid" />


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
          <gsmsg:write key="fil.fil240.1" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil240back');">
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

      <bean:size id="count1" name="fil240Form" property="fil240PageLabel" scope="request" />
      <logic:greaterThan name="count1" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select property="fil240Slt_page1" onchange="changePage1();" styleClass="paging_combo">
            <html:optionsCollection name="fil240Form" property="fil240PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <logic:notEmpty name="fil240Form" property="callList" scope="request">
        <table class="table-top">
          <tr class="row-heading">
            <th class="w5"></th>
            <th class="w50">
              <gsmsg:write key="fil.98" />
              </span>
            </th>
            <th class="w30">
              <gsmsg:write key="fil.11" />
              </span>
            </th>
            <th class="w10">
              <gsmsg:write key="cmn.update.user" />
              </span>
            </th>
            <th class="w5"></th>
          </tr>

          <logic:iterate id="callBean" name="fil240Form" property="callList" scope="request" indexId="idx" type="FileLinkSimpleModel">
            <tr class="js_listHover" data-sid="<bean:write name="callBean" property="directorySid" />" data-binsid="<bean:write name="callBean" property="binSid" />" data-kbn="<bean:write name="callBean" property="directoryKbn" />">
              <td class="txt_c js_listClick cursor_p">
                <logic:notEqual name="callBean" property="fcbMark" value="0">
                  <img name="iconImage" class="wp30" src="../file/fil010.do?CMD=tempview&fil010SelectCabinet=<bean:write name="callBean" property="cabinetSid" />&fil010binSid=<bean:write name="callBean" property="fcbMark" />" name="pctImage<bean:write name="callBean" property="fcbMark" />">
                </logic:notEqual>

                <logic:notEqual name="callBean" property="directoryKbn" value="0">
                  <logic:equal name="callBean" property="fcbMark" value="0">
                    <img class="btn_classicImg-display" src="../file/images/classic/icon_new_file.gif">
                    <span class="labelNew original-display txt_t">
                      <gsmsg:write key="bbs.bbsMain.6" />
                    </span>
                  </logic:equal>
              </td>
              <td class="txt_l js_listClick cursor_p">
                  <span class="cl_linkDef"><bean:write name="callBean" property="directoryFullPathName" /></span>
                </logic:notEqual>
                <br>
                <gsmsg:write key="cmn.update.day.hour" />:<bean:write name="callBean" property="directoryUpdateStr" />
              </td>

              <td class="txt_l js_listClick cursor_p">
                <bean:write name="callBean" property="ffrUpCmt" filter="false" />
              </td>

              <td class="txt_l no_w js_listClick cursor_p">
                <logic:equal name="callBean" property="editUsrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <bean:write name="callBean" property="editUsrName" />
                </logic:equal>
                <logic:notEqual name="callBean" property="editUsrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                  <span class="<%=UserUtil.getCSSClassNameNormal(callBean.getEditUsrUkoFlg())%>">
                    <bean:write name="callBean" property="editUsrName" />
                  </span>
                </logic:notEqual>
              </td>

              <td class="no_w">
                <button type="button" value="<gsmsg:write key="fil.18" />" class="baseBtn" onClick="MoveToFileDetail(<bean:write name="callBean" property="directorySid" />);">
                  <gsmsg:write key="fil.18" />
                </button>
              </td>

            </tr>
          </logic:iterate>
        </table>
      </logic:notEmpty>

      <bean:size id="count2" name="fil240Form" property="fil240PageLabel" scope="request" />
      <logic:greaterThan name="count2" value="1">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select property="fil240Slt_page2" onchange="changePage2();" styleClass="paging_combo">
            <html:optionsCollection name="fil240Form" property="fil240PageLabel" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:greaterThan>

      <div class="footerBtn_block txt_r mt10">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('fil240back');">
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