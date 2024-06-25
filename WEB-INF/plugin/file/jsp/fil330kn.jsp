<%@page import="jp.groupsession.v2.fil.fil330.Fil330DspFileModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.fil300.Fil300ParamModel"%>
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
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /> <gsmsg:write key="fil.fil330kn.1" /></title>
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/cmn380.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/toastDisplay.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/jtooltip.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/fil330.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.8.16/ui/dialog/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../file/css/file.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/></head>

<%
  java.util.List filTipList = new java.util.ArrayList();
%>

<body>
  <html:form action="/file/fil330kn">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="escapeCmd" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil330SelectDsp"  />
    <html:hidden property="fil330SelectCabinet"  />
    <html:hidden name="fil330knForm" property="fil300insertMode" />
    <html:hidden property="fil010DspCabinetKbn" />
    <html:hidden name="fil330knForm" property="fil330FileListPageNo" />

    <html:hidden property="fil300BeforeDspFlg" />
    <logic:notEmpty name="fil330knForm" property="fil300BeforeInsertFile">
      <logic:iterate id="sid" name="fil330knForm" property="fil300BeforeInsertFile">
        <input type="hidden" name="fil300BeforeInsertFile" value="<bean:write name="sid" />">
      </logic:iterate>
    </logic:notEmpty>

    <span class="js_treeText">
      <bean:parameter id="fileTree" name="fileTree" value=""/>
      <html:hidden name="fil330knForm" property="fil330Tree" />

      <html:hidden property="sepKey" />
      <bean:define id="sepKey" name="fil330knForm" property="sepKey" type="String"/>
      <logic:notEmpty name="fil330knForm" property="treeFormLv0">
        <logic:iterate id="lv0" name="fil330knForm" property="treeFormLv0" type="String">
          <logic:empty name="cabDirId">
           <% String[] sp = lv0.split(sepKey);
           %>
           <bean:define id="cabDirId" value="<%=sp[0] %>"/>
           <bean:define id="cabDirName" value="<%=sp[2] %>"/>
          </logic:empty>
          <input type="hidden" name="treeFormLv0" value="<bean:write name="lv0" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv1">
        <logic:iterate id="lv1" name="fil330knForm" property="treeFormLv1">
          <input type="hidden" name="treeFormLv1" value="<bean:write name="lv1" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv2">
        <logic:iterate id="lv2" name="fil330knForm" property="treeFormLv2">
          <input type="hidden" name="treeFormLv2" value="<bean:write name="lv2" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv3">
        <logic:iterate id="lv3" name="fil330knForm" property="treeFormLv3">
          <input type="hidden" name="treeFormLv3" value="<bean:write name="lv3" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv4">
        <logic:iterate id="lv4" name="fil330knForm" property="treeFormLv4">
          <input type="hidden" name="treeFormLv4" value="<bean:write name="lv4" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv5">
        <logic:iterate id="lv5" name="fil330knForm" property="treeFormLv5">
          <input type="hidden" name="treeFormLv5" value="<bean:write name="lv5" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv6">
        <logic:iterate id="lv6" name="fil330knForm" property="treeFormLv6">
          <input type="hidden" name="treeFormLv6" value="<bean:write name="lv6" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv7">
        <logic:iterate id="lv7" name="fil330knForm" property="treeFormLv7">
          <input type="hidden" name="treeFormLv7" value="<bean:write name="lv7" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv8">
        <logic:iterate id="lv8" name="fil330knForm" property="treeFormLv8">
          <input type="hidden" name="treeFormLv8" value="<bean:write name="lv8" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv9">
        <logic:iterate id="lv9" name="fil330knForm" property="treeFormLv9">
          <input type="hidden" name="treeFormLv9" value="<bean:write name="lv9" />">
        </logic:iterate>
      </logic:notEmpty>
      <logic:notEmpty name="fil330knForm" property="treeFormLv10">
        <logic:iterate id="lv10" name="fil330knForm" property="treeFormLv10">
          <input type="hidden" name="treeFormLv10" value="<bean:write name="lv10" />">
        </logic:iterate>
      </logic:notEmpty>
    </span>



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
          <gsmsg:write key="fil.fil330kn.1" />
        </li>
        <li>
          <button type="button" class="baseBtn " value="<gsmsg:write key="cmn.delete" />" onclick="buttonPush('fil330knDelComp');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>

          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('fil330knCancel')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </li>
      </ul>
    </div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <div class="wrapper">
      <div class="w100">
        <logic:empty name="fil330knForm" property="fil330SelectDsp" >
          <div class="js_importArea-noSelect txt_c txt_m mt50">
            <span class="fs_20 fw_b cl_fontWeek"><gsmsg:write key="fil.fil300.5" /></span>
          </div>
          <div class="js_importArea">
          </div>
        </logic:empty>
        <logic:notEmpty name="fil330knForm" property="fil330SelectDsp" >
          <div class="js_importArea-noSelect display_none txt_c txt_m mt50">
            <span class="fs_20 fw_b cl_fontWeek"><gsmsg:write key="fil.fil300.5" /></span>
          </div>
          <div class="js_importArea">

            <!-- 一覧 -->
            <table class="table-top mt0 w100" cellpadding="0" cellspacing="0">
              <tr>
                <th class="w5 bgC_header2 no_w">
                  <span class="cl_fontBody">
                    <gsmsg:write key="fil.fil300.10" />
                  </span>
                </th>
                <th class="w40 bgC_header2 no_w">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.name4" />
                  </span>
                </th>
                <th class="w5 bgC_header2 no_w">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.size" />
                  </span>
                </th>
                <th class="w5 bgC_header2 no_w">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.update.day.hour" />
                  </span>
                </th>
                <th class="w15 bgC_header2 no_w">
                  <span class="cl_fontBody">
                    <gsmsg:write key="cmn.update.user" />
                  </span>
                </th>
              </tr>

              <bean:define id="tdColor" value="" />
              <% String[] tdColors = new String[] {"bgC_tableCell", "bgC_tableCellEvn"};%>

              <logic:notEmpty name="fil330knForm" property="fil330FileList" scope="request">
                <logic:iterate id="dirBean" name="fil330knForm" property="fil330FileList" scope="request" indexId="idx" type="Fil330DspFileModel">
                <% tdColor = tdColors[(idx.intValue() % 2)]; %>
                <bean:define id="fileName" name="dirBean" property="base.fdrName" />
                  <tr class="<%= tdColor %> " data-fdrsid="<bean:write name="dirBean" property="base.feaSid" />" data-binsid="<bean:write name="dirBean" property="base.binSid" />" data-parentSid="<bean:write name="dirBean" property="base.fdrParentSid" />" data-extension="<bean:write name="dirBean" property="base.fflExt" />">
                    <td class="txt_l">
                       <bean:write name="dirBean" property="base.feaSid" />
                    </td>
                    <td class="txt_l">
                      <span class="display_none">
                      <html:multibox name="fil330knForm" property="fil330SelectDel" >
                        <bean:write name="dirBean" property="base.feaSid" />
                      </html:multibox>
                      </span>
                      <logic:notEmpty name="dirBean" property="base.fdrBiko">
                        <bean:define id="biko" name="dirBean" property="base.fdrBiko" />
                        <%
                          String tmpText = (String) pageContext.getAttribute("biko", PageContext.PAGE_SCOPE);
                                      String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                        %>
                        <span class="js_nlTooltips"><gsmsg:write key="cmn.memo" />:<%=tmpText2%></span>
                        <%--
                          filTipList.add("fdrsid" + String.valueOf(
                                          dirBean.getBase().getFeaSid());
                        --%>
                      </logic:notEmpty>
                      <a href="#!" class="js_dlLink" data-feasid="<bean:write name="dirBean" property="base.feaSid"/>" data-fileextension="<bean:write name="dirBean" property="fileExt"/>">
                        <img class="classic-display" src="../file/images/classic/icon_file.gif" border="0" alt="">
                        <img class="original-display" src="../common/images/original/icon_siryo.png" border="0" alt="">
                        <bean:write name="dirBean" property="path" /><bean:write name="dirBean" property="base.fdrName" />
                      </a>

                      <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
                      <span class="ml5 cursor_p js_preview"  data-feasid="<bean:write name="dirBean" property="base.feaSid"/>" data-fileextension="<bean:write name="dirBean" property="fileExt"/>">
                        <img class="btn_classicImg-display " src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                        <img class="btn_originalImg-display " src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                      </span>
                      <% } %>
                    </td>
                    <td class="txt_r no_w">
                      <span>
                        <bean:write name="dirBean" property="fileSize" />
                      </span>
                    </td>
                    <td class="txt_c ">
                      <span>
                        <bean:write name="dirBean" property="edateString" />
                      </span>
                    </td>
                    <td class="txt_l">
                      <logic:equal name="dirBean" property="updateUser.usrJkbn" value="<%=String.valueOf(GSConst.JTKBN_DELETE)%>">
                        <span>
                          <s><bean:write name="dirBean" property="updateUser.usiName" /><s>
                        </span>
                      </logic:equal>
                      <logic:notEqual name="dirBean" property="updateUser.usrJkbn" value="<%=String.valueOf(GSConst.JTKBN_DELETE)%>">
                        <span class="<%=UserUtil.getCSSClassNameNormal(dirBean.getUpdateUser().getUsrUkoFlg())%>">
                          <bean:write name="dirBean" property="updateUser.usiName" />
                        </span>
                      </logic:notEqual>
                    </td>
                  </tr>
                </logic:iterate>
              </logic:notEmpty>
            </table>
          </div>
        </logic:notEmpty>
      </div>
   </div>
  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>
