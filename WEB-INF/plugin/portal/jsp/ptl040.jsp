<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.sortable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl040.js?<%= GSConst.VERSION_PARAM %>"></script>

<title>GROUPSESSION <gsmsg:write key="ptl.ptl040.1" /></title>
</head>

<!-- body -->
<body onload="initChgArea();" onunload="closePortalPreview();portalPopupClose();">
<html:form action="/portal/ptl040">

<input type="hidden" name="CMD" value="">
<html:hidden property="ptlPortalSid" />
<html:hidden property="ptl030sortPortal" />
<html:hidden property="ptl040PortalItemId" />
<html:hidden property="ptlCmdMode" />

<html:hidden property="ptl040areaTop" />
<html:hidden property="ptl040areaBottom" />
<html:hidden property="ptl040areaLeft" />
<html:hidden property="ptl040areaCenter" />
<html:hidden property="ptl040areaRight" />

<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div  class="kanriPageTitle">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.ptl040.1" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="buttonPush2('ptl040editPortal');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
          <gsmsg:write key="cmn.edit" />
        </button>
        <button type="button" value="<gsmsg:write key="ptl.5" />" class="baseBtn" onClick="buttonPush2('ptl040editLayout');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_layout.png" alt="<gsmsg:write key="ptl.5" />">
          <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.5" />">
          <gsmsg:write key="ptl.5" />
        </button>
        <button type="button" value="<gsmsg:write key="ptl.6" />" class="baseBtn" onClick="openPortalPreview('<bean:write name="ptl040Form" property="ptlPortalSid" />');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="ptl.6" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="ptl.6" />">
          <gsmsg:write key="ptl.6" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush2('ptl040back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <table class="table-left w100">
    <tr>
      <th class="w20">
        <gsmsg:write key="ptl.4" />
      </th>
      <td class="w80">
        <bean:write name="ptl040Form" property="ptl040portalName" />
      </td>
    </tr>
  </table>
  <div class="txt_r mb10">
    <button type="button" value="<gsmsg:write key="ptl.3" />" class="baseBtn" onClick="openPortalPopup(1);">
      <img class="btn_classicImg-display" src="../portal/images/classic/icon_portlet.png" alt="<gsmsg:write key="ptl.3" />">
      <img class="btn_originalImg-display" src="../portal/images/original/icon_portlet.png" alt="<gsmsg:write key="ptl.3" />">
      <gsmsg:write key="ptl.3" />
    </button>
    <button type="button" value="<gsmsg:write key="cmn.plugin" />" class="baseBtn" onClick="openPortalPopup(0);">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_gs_2.png" alt="<gsmsg:write key="cmn.plugin" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_gs_18.png" alt="<gsmsg:write key="cmn.plugin" />">
      <gsmsg:write key="cmn.plugin" />
    </button>
  </div>
  <table class="w100">

    <!-- 上 -->
    <logic:equal name="ptl040Form" property="ptl040areaTop" value="0">
      <tr>
        <td id="mainScreenListTop" class="w100 hp90 p0 bor3 portal_area portal_area_top">
          <logic:notEmpty name="ptl040Form" property="ptl040topList">
            <logic:iterate id="topModel" name="ptl040Form" property="ptl040topList" indexId="idxtop">
              <div class="portlet cursor_p ml10 mr10" id="<bean:write name='topModel' property='ptpItemid' />">
                <table class="table-top w100">
                  <tr>
                    <th class="txt_l w100 border_right_none">
                      <span class="fw_b"><bean:write name="topModel" property="dspName" /></span>
                    </th>
                    <th class="border_left_none">
                      <logic:notEqual name="topModel" property="ptpType" value="3">
                        <a href="#!" class="fs_14" onClick="return delPortlet('<bean:write name='topModel' property='ptpItemid' />', '<bean:write name='topModel' property='dspName' />');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                        </a>
                      </logic:notEqual>
                    </th>
                  </tr>
                  <tr>
                    <logic:equal name="topModel" property="ptpView" value="0">
                      <td id="portlet_area<bean:write name='topModel' property='ptpItemid' />" colspan=2>
                    </logic:equal>
                    <logic:equal name="topModel" property="ptpView" value="1">
                      <td class="bgC_other1" id="portlet_area<bean:write name='topModel' property='ptpItemid' />" colspan=2>
                    </logic:equal>
                      <logic:equal name="topModel" property="ptpView" value="0">
                        <div class="verAlignMid">
                          <input type="radio" class="mr5" name="openKbn<bean:write name='topModel' property='num' />" value="0" id="openKbn0_<bean:write name='topModel' property='num' />" onClick="changeOpenKbn(<bean:write name='topModel' property='ptpItemid'/>, 0);" checked="checked">
                          <label for="openKbn0_<bean:write name='topModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                          <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='topModel' property='num' />" value="1" id="openKbn1_<bean:write name='topModel' property='num' />" onClick="changeOpenKbn(<bean:write name='topModel' property='ptpItemid'/>, 1);">
                          <label for="openKbn1_<bean:write name='topModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                        </div>
                      </logic:equal>
                      <logic:equal name="topModel" property="ptpView" value="1">
                        <div class="verAlignMid">
                          <input type="radio" class="mr5" name="openKbn<bean:write name='topModel' property='num' />" value="0" id="openKbn0_<bean:write name='topModel' property='num' />" onClick="changeOpenKbn(<bean:write name='topModel' property='ptpItemid'/>, 0);">
                          <label for="openKbn0_<bean:write name='topModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                          <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='topModel' property='num' />" value="1" id="openKbn1_<bean:write name='topModel' property='num' />" onClick="changeOpenKbn(<bean:write name='topModel' property='ptpItemid'/>, 1);" checked="checked">
                          <label for="openKbn1_<bean:write name='topModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                        </div>
                      </logic:equal>
                    </td>
                  </tr>
                </table>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:equal>

    <tr>
      <td class="pt10" id="portal_space_Top">
      </td>
    </tr>

    <tr>
      <td class="p0">
        <table class="w100">
          <tr>

            <logic:equal name="ptl040Form" property="ptl040areaLeft" value="0">
              <td id="mainScreenListLeft" class="hp90 p0 bor3 txt_t portal_area portal_area_left">
                <logic:notEmpty name="ptl040Form" property="ptl040leftList">
                  <logic:iterate id="leftModel" name="ptl040Form" property="ptl040leftList" indexId="idxleft">
                    <div class="portlet cursor_p ml10 mr10" id="<bean:write name='leftModel' property='ptpItemid' />">
                      <table class="table-top w100">
                        <tr>
                          <th class="txt_l w100 border_right_none">
                            <span class="fw_b"><bean:write name="leftModel" property="dspName" /></span>
                          </th>
                          <th class="border_left_none">
                            <logic:notEqual name="leftModel" property="ptpType" value="3">
                              <a href="#!" class="fs_14" onClick="return delPortlet('<bean:write name='leftModel' property='ptpItemid' />', '<bean:write name='leftModel' property='dspName' />');">
                                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                              </a>
                            </logic:notEqual>
                          </th>
                        </tr>
                        <tr>
                          <logic:equal name="leftModel" property="ptpView" value="0">
                            <td id="portlet_area<bean:write name='leftModel' property='ptpItemid' />" colspan=2>
                          </logic:equal>
                          <logic:equal name="leftModel" property="ptpView" value="1">
                            <td class="bgC_other1" id="portlet_area<bean:write name='leftModel' property='ptpItemid' />" colspan=2>
                          </logic:equal>
                            <logic:equal name="leftModel" property="ptpView" value="0">
                              <div class="verAlignMid">
                                <input type="radio" class="mr5" name="openKbn<bean:write name='leftModel' property='num' />" value="0" id="openKbn0_<bean:write name='leftModel' property='num' />" onClick="changeOpenKbn(<bean:write name='leftModel' property='ptpItemid'/>, 0);" checked="checked">
                                <label for="openKbn0_<bean:write name='leftModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                                <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='leftModel' property='num' />" value="1" id="openKbn1_<bean:write name='leftModel' property='num' />" onClick="changeOpenKbn(<bean:write name='leftModel' property='ptpItemid'/>, 1);">
                                <label for="openKbn1_<bean:write name='leftModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                              </div>
                            </logic:equal>
                            <logic:equal name="leftModel" property="ptpView" value="1">
                              <div class="verAlignMid">
                                <input type="radio" class="mr5" name="openKbn<bean:write name='leftModel' property='num' />" value="0" id="openKbn0_<bean:write name='leftModel' property='num' />" onClick="changeOpenKbn(<bean:write name='leftModel' property='ptpItemid'/>, 0);">
                                <label for="openKbn0_<bean:write name='leftModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                                <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='leftModel' property='num' />" value="1" id="openKbn1_<bean:write name='leftModel' property='num' />" onClick="changeOpenKbn(<bean:write name='leftModel' property='ptpItemid'/>, 1);" checked="checked">
                                <label for="openKbn1_<bean:write name='leftModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                              </div>
                            </logic:equal>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </logic:equal>

            <td class="pl10" id="left_space">
            </td>

            <logic:equal name="ptl040Form" property="ptl040areaCenter" value="0">
              <td id="mainScreenListCenter" class="hp90 p0 bor3 txt_t portal_area portal_area_center">
                <logic:notEmpty name="ptl040Form" property="ptl040centerList">
                  <logic:iterate id="centerModel" name="ptl040Form" property="ptl040centerList" indexId="idxcenter">
                    <div class="portlet cursor_p ml10 mr10" id="<bean:write name='centerModel' property='ptpItemid' />">
                      <table class="table-top w100">
                        <tr>
                          <th class="txt_l w100 border_right_none">
                            <span class="fw_b"><bean:write name="centerModel" property="dspName" /></span>
                          </th>
                          <th class="border_left_none">
                            <logic:notEqual name="centerModel" property="ptpType" value="3">
                              <a href="#!" class="fs_14" onClick="return delPortlet('<bean:write name='centerModel' property='ptpItemid' />', '<bean:write name='centerModel' property='dspName' />');">
                                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                              </a>
                            </logic:notEqual>
                          </th>
                        </tr>
                        <tr>
                          <logic:equal name="centerModel" property="ptpView" value="0">
                            <td id="portlet_area<bean:write name='centerModel' property='ptpItemid' />" colspan=2>
                          </logic:equal>
                          <logic:equal name="centerModel" property="ptpView" value="1">
                            <td class="bgC_other1" id="portlet_area<bean:write name='centerModel' property='ptpItemid' />" colspan=2>
                          </logic:equal>
                            <logic:equal name="centerModel" property="ptpView" value="0">
                              <div class="verAlignMid">
                                <input type="radio" class="mr5" name="openKbn<bean:write name='centerModel' property='num' />" value="0" id="openKbn0_<bean:write name='centerModel' property='num' />" onClick="changeOpenKbn(<bean:write name='centerModel' property='ptpItemid'/>, 0);" checked="checked">
                                <label for="openKbn0_<bean:write name='centerModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                                <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='centerModel' property='num' />" value="1" id="openKbn1_<bean:write name='centerModel' property='num' />" onClick="changeOpenKbn(<bean:write name='centerModel' property='ptpItemid'/>, 1);">
                                <label for="openKbn1_<bean:write name='centerModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                              </div>
                            </logic:equal>
                            <logic:equal name="centerModel" property="ptpView" value="1">
                              <div class="verAlignMid">
                                <input type="radio" class="mr5" name="openKbn<bean:write name='centerModel' property='num' />" value="0" id="openKbn0_<bean:write name='centerModel' property='num' />" onClick="changeOpenKbn(<bean:write name='centerModel' property='ptpItemid'/>, 0);">
                                <label for="openKbn0_<bean:write name='centerModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                                <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='centerModel' property='num' />" value="1" id="openKbn1_<bean:write name='centerModel' property='num' />" onClick="changeOpenKbn(<bean:write name='centerModel' property='ptpItemid'/>, 1);" checked="checked">
                                <label for="openKbn1_<bean:write name='centerModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                              </div>
                            </logic:equal>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </logic:equal>

            <td class="pr10" id="right_space">
            </td>

            <logic:equal name="ptl040Form" property="ptl040areaRight" value="0">
              <td id="mainScreenListRight" class="hp90 p0 bor3 txt_t portal_area portal_area_right">
                <logic:notEmpty name="ptl040Form" property="ptl040rightList">
                  <logic:iterate id="rightModel" name="ptl040Form" property="ptl040rightList" indexId="idxright">
                    <div class="portlet cursor_p ml10 mr10" id="<bean:write name='rightModel' property='ptpItemid' />">
                      <table class="table-top w100">
                        <tr>
                          <th class="txt_l w100 border_right_none">
                            <span class="fw_b"><bean:write name="rightModel" property="dspName" /></span>
                          </th>
                          <th class="border_left_none">
                            <logic:notEqual name="rightModel" property="ptpType" value="3">
                              <a href="#!" class="fs_14" onClick="return delPortlet('<bean:write name='rightModel' property='ptpItemid' />', '<bean:write name='rightModel' property='dspName' />');">
                                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                              </a>
                            </logic:notEqual>
                          </th>
                        </tr>
                        <tr>
                          <logic:equal name="rightModel" property="ptpView" value="0">
                            <td id="portlet_area<bean:write name='rightModel' property='ptpItemid' />" colspan=2>
                          </logic:equal>
                          <logic:equal name="rightModel" property="ptpView" value="1">
                            <td class="bgC_other1" id="portlet_area<bean:write name='rightModel' property='ptpItemid' />" colspan=2>
                          </logic:equal>
                            <logic:equal name="rightModel" property="ptpView" value="0">
                              <div class="verAlignMid">
                                <input type="radio" class="mr5" name="openKbn<bean:write name='rightModel' property='num' />" value="0" id="openKbn0_<bean:write name='rightModel' property='num' />" onClick="changeOpenKbn(<bean:write name='rightModel' property='ptpItemid'/>, 0);" checked="checked">
                                <label for="openKbn0_<bean:write name='rightModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                                <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='rightModel' property='num' />" value="1" id="openKbn1_<bean:write name='rightModel' property='num' />" onClick="changeOpenKbn(<bean:write name='rightModel' property='ptpItemid'/>, 1);">
                                <label for="openKbn1_<bean:write name='rightModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                              </div>
                            </logic:equal>
                            <logic:equal name="rightModel" property="ptpView" value="1">
                              <div class="verAlignMid">
                                <input type="radio" class="mr5" name="openKbn<bean:write name='rightModel' property='num' />" value="0" id="openKbn0_<bean:write name='rightModel' property='num' />" onClick="changeOpenKbn(<bean:write name='rightModel' property='ptpItemid'/>, 0);">
                                <label for="openKbn0_<bean:write name='rightModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                                <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='rightModel' property='num' />" value="1" id="openKbn1_<bean:write name='rightModel' property='num' />" onClick="changeOpenKbn(<bean:write name='rightModel' property='ptpItemid'/>, 1);" checked="checked">
                                <label for="openKbn1_<bean:write name='rightModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                              </div>
                            </logic:equal>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </logic:iterate>
                </logic:notEmpty>
              </td>
            </logic:equal>
          </tr>
        </table>
      </td>
    </tr>

    <tr>
      <td class="pt10" id="portal_space_Bottom">
      </td>
    </tr>

    <!-- 下 -->
    <logic:equal name="ptl040Form" property="ptl040areaBottom" value="0">
      <tr>
        <td id="mainScreenListBottom" class="w100 hp90 p0 bor3 portal_area portal_area_bottom">
          <logic:notEmpty name="ptl040Form" property="ptl040bottomList">
            <logic:iterate id="bottomModel" name="ptl040Form" property="ptl040bottomList" indexId="idxbottom">
              <div class="portlet cursor_p ml10 mr10" id="<bean:write name='bottomModel' property='ptpItemid' />">
                <table class="table-top w100">
                  <tr>
                    <th class="txt_l w100 border_right_none">
                      <span class="fw_b"><bean:write name="bottomModel" property="dspName" /></span>
                    </th>
                    <th class="border_left_none">
                      <logic:notEqual name="bottomModel" property="ptpType" value="3">
                        <a href="#!" class="fs_14" onClick="return delPortlet('<bean:write name='bottomModel' property='ptpItemid' />', '<bean:write name='bottomModel' property='dspName' />');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                        </a>
                      </logic:notEqual>
                    </th>
                  </tr>
                  <tr>
                    <logic:equal name="bottomModel" property="ptpView" value="0">
                      <td id="portlet_area<bean:write name='bottomModel' property='ptpItemid' />" colspan=2>
                    </logic:equal>
                    <logic:equal name="bottomModel" property="ptpView" value="1">
                      <td class="bgC_other1" id="portlet_area<bean:write name='bottomModel' property='ptpItemid' />" colspan=2>
                    </logic:equal>
                      <logic:equal name="bottomModel" property="ptpView" value="0">
                        <div class="verAlignMid">
                          <input type="radio" class="mr5" name="openKbn<bean:write name='bottomModel' property='num' />" value="0" id="openKbn0_<bean:write name='bottomModel' property='num' />" onClick="changeOpenKbn(<bean:write name='bottomModel' property='ptpItemid'/>, 0);" checked="checked">
                          <label for="openKbn0_<bean:write name='bottomModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                          <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='bottomModel' property='num' />" value="1" id="openKbn1_<bean:write name='bottomModel' property='num' />" onClick="changeOpenKbn(<bean:write name='bottomModel' property='ptpItemid'/>, 1);">
                          <label for="openKbn1_<bean:write name='bottomModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                        </div>
                      </logic:equal>
                      <logic:equal name="bottomModel" property="ptpView" value="1">
                        <div class="verAlignMid">
                          <input type="radio" class="mr5" name="openKbn<bean:write name='bottomModel' property='num' />" value="0" id="openKbn0_<bean:write name='bottomModel' property='num' />" onClick="changeOpenKbn(<bean:write name='bottomModel' property='ptpItemid'/>, 0);">
                          <label for="openKbn0_<bean:write name='bottomModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.show" /></label>
                          <input type="radio" class="ml10 mr5" name="openKbn<bean:write name='bottomModel' property='num' />" value="1" id="openKbn1_<bean:write name='bottomModel' property='num' />" onClick="changeOpenKbn(<bean:write name='bottomModel' property='ptpItemid'/>, 1);" checked="checked">
                          <label for="openKbn1_<bean:write name='bottomModel' property='num' />" class="fs_14"><gsmsg:write key="cmn.hide" /></label>
                        </div>
                      </logic:equal>
                    </td>
                  </tr>
                </table>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </td>
      </tr>
    </logic:equal>

  </table>
</div>

</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</body>

</html:html>
