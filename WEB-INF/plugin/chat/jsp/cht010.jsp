<%@page import="jp.groupsession.v2.cmn.GSConstCommon"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cht.GSConstChat" %>
<!DOCTYPE html>

<% int notSendSeigen = jp.groupsession.v2.cht.GSConstChat.NOT_SEND_USER_SEIGEN; %>
<% int notSendArchive = jp.groupsession.v2.cht.GSConstChat.NOT_SEND_ARCHIVE; %>
<% int notSendDelUsr = jp.groupsession.v2.cht.GSConstChat.NOT_SEND_DELETE_USER; %>
<% int notSendSpaccess = jp.groupsession.v2.cht.GSConstChat.NOT_SEND_SPACCESS; %>
<% int notSendAccess = jp.groupsession.v2.cht.GSConstChat.NOT_SEND_ACCESS; %>
<% int jkbn = jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE; %>
<% String key = jp.groupsession.v2.cmn.GSConst.SESSION_KEY; %>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.admin.setting.menu" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/freeze.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110_upload.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../chat/js/cht010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../chat/js/cht010_create.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../chat/css/chat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../chat/css/cht010.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<jsp:include page="/WEB-INF/plugin/chat/jsp/cht010_message.jsp" />
</head>

<body onload="wsReload(); fromMain('<bean:write name="<%= key %>" scope="session" property="usrsid" />', '../chat');">

<div class="display_n">
  <jsp:include page="/WEB-INF/plugin/chat/jsp/cht010_dragdrop.jsp" />
</div>


<html:form styleId="js_chtForm" action="/chat/cht010">

<input type="hidden" name="CMD" value="">
<html:hidden property="cht010SelectPartner" />
<html:hidden property="cht010SelectKbn" />
<html:hidden property="cht010EditUsrSid" />
<html:hidden property="cht010MessageSid" />
<html:hidden property="cht010AllTempSid" />
<html:hidden property="cht010FirstEntryDay" />
<html:hidden property="cht010AllDispFlg" />
<html:hidden property="cht010FromMain" />
<html:hidden property="cht010InitFlg" />

<span class="js_help_area">
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>
</span>

<%-- BODY --%>
<div class="pageTitle" id="js_chatHeader">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../chat/images/classic/header_chat.png" alt="<gsmsg:write key="cht.01" />">
      <img class="header_pluginImg" src="../chat/images/original/header_chat.png" alt="<gsmsg:write key="cht.01" />">
    </li>
    <li>
      <gsmsg:write key="cht.01" />
    </li>
    <li>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cht.cht010.41" />" onclick="pushReload();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cht.cht010.41" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cht.cht010.41" />">
        <gsmsg:write key="cht.cht010.41" />
      </button>
    </li>
  </ul>
</div>
<div>
  <span class="fw_b cl_fontWarn" id="js_error">
  </span>
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
</div>

<attachmentFile:filearea
 mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
 pluginId="<%= GSConstChat.PLUGIN_ID_CHAT %>"
 tempDirId="<%= GSConstChat.DIRID_CHT010 %>"
 tempBtn="false"
 fileList="false" />

<div class="wrapper_2column">
  <!-- 左 -->
  <div class="side-left border_left_none bgC_none">
    <div class="w100 bor_l1 verAlignMid border_bottom_none border_right_none">
      <bean:define id="clsName" value="cht_sideHeader cl_fontOutline chtTabHead_all fw_b" />
      <logic:notEqual name="cht010Form" property="cht010SelectTab" value="0">
        <bean:define id="clsName" value="bgC_lightGray cursor_p bor_r1 bor_b1" />
      </logic:notEqual>
      <div class="js_tabHead_all <%=clsName %> w50 no_w txt_c">
        <gsmsg:write key="cmn.all" />
      </div>
      <bean:define id="clsName" value="cht_sideHeader cl_fontOutline chtTabHead_timeline fw_b" />
      <logic:notEqual name="cht010Form" property="cht010SelectTab" value="1">
        <bean:define id="clsName" value="bgC_lightGray cursor_p bor_l1 bor_b1" />
      </logic:notEqual>
      <div class="js_tabHead_timeline bor_r1 pos_rel <%=clsName %> w50 no_w txt_c">
        <bean:define id="clsBach" value="" />
        <logic:notEqual name="cht010Form" property="cht010MidokuCount" value="0">
          <bean:define id="clsBach" value="midokuBach-on" />
        </logic:notEqual>
        <div class="js_timelineBach timelineBach <%=clsBach%>"></div>
        <gsmsg:write key="cmn.timeline" />
      </div>
    </div>

    <bean:define id="clsName" value="" />
    <logic:notEqual name="cht010Form" property="cht010SelectTab" value="0">
       <bean:define id="clsName" value="display_n" />
    </logic:notEqual>
    <div id="tabAll" class="<%=clsName%> bor_l1 bgC_body">

      <!-- お気に入り情報 -->
      <div id="favoriteHeadArea" class="p5 fw_b cursor_p side_header-folding cht_sideHeader">
        <span class="side_headerTitle side_header-open fs_13">
        <span class="side_headerArrow"></span><gsmsg:write key="cht.cht010.01" /></span>
      </div>
      <div id="favoriteBodyArea" class="bor_t1 mb5">
        <span class="js_favGroup">
          <bean:size id="groupFavoriteSize" name="cht010Form" property="cht010FavoriteGroup"/>
          <logic:notEqual name="groupFavoriteSize" value="0">
            <div class="fw_b lh130 mt5 ml5"><gsmsg:write key="cmn.group" /></div>
          </logic:notEqual>
          <logic:iterate id="favoriteGroup" name="cht010Form" property="cht010FavoriteGroup" indexId="Idx">
            <div class="pl20 w100 mt5 lh130">
              <a href="#" class="js_chtGroup cl_linkDef display_b" value="<bean:write name="favoriteGroup" property="cgiSid"/>">
                <bean:write name="favoriteGroup" property="cgiName"/>
                <span class="midokuCount js_midokuCount"><%--
              --%><logic:notEqual name="favoriteGroup" property="chtGroupCount" value="0"><%--
                --%><bean:write name="favoriteGroup" property="chtGroupCount"/><%--
              --%></logic:notEqual><%--
            --%></span>
              </a>
            </div>
          </logic:iterate>
        </span>
        <span class="js_favUser">
          <bean:size id="userFavoriteSize" name="cht010Form" property="cht010FavoriteUser"/>
          <logic:notEqual name="userFavoriteSize" value="0">
            <div class="fw_b lh130 mt5 ml5"><gsmsg:write key="cmn.user" /></div>
          </logic:notEqual>
          <logic:iterate id="favoriteUser" name="cht010Form" property="cht010FavoriteUser" indexId="Idx">
            <% String mukoClass = ""; %>
            <logic:notEqual name="favoriteUser" property="usrUkoFlg" value="0">
              <% mukoClass = "mukoUser"; %>
            </logic:notEqual>
            <div class="pl20 w100 mt5 lh130">
              <a href="#" class="js_chtUser cl_linkDef display_b <%= mukoClass %>" value="<bean:write name="favoriteUser" property="usrSid"/>">
                <logic:notEqual name="favoriteUser" property="usrJkbn" value="0">
                  <del>
                    <bean:write name="favoriteUser" property="usiSei"/>
                    <bean:write name="favoriteUser" property="usiMei"/>
                  </del>
                </logic:notEqual>
                <logic:equal name="favoriteUser" property="usrJkbn" value="0">
                  <bean:write name="favoriteUser" property="usiSei"/>
                  <bean:write name="favoriteUser" property="usiMei"/>
                </logic:equal>
                <span class="midokuCount js_midokuCount"><%--
               --%><logic:notEqual name="favoriteUser" property="chtUserCount" value="0"><%--
                  --%><bean:write name="favoriteUser" property="chtUserCount"/><%--
               --%></logic:notEqual><%--
            --%></span>
              </a>
            </div>
          </logic:iterate>
        </span>
      </div>

      <!-- グループ情報 -->
      <div class="bor_t1 display_flex cht_sideHeader side_header-folding">
        <div class="w80 p5 cursor_p hp40" id="groupHeadArea">
            <span class="side_headerTitle side_header-open fs_13"><%--
          --%><span class="side_headerArrow"></span><%--
          --%><gsmsg:write key="user.src.59" />
            </span>
        </div>
        <div class="js_grpconf_initDsp side_confGear cht_sideConfGear">
          <img class="btn_classicImg-display wp20" src="../chat/images/classic/icon_config.png">
          <i class="icon-setting"></i>
        </div>
      </div>
      <div id="groupBodyArea" class="bor_t1">
        <bean:size id="groupSize" name="cht010Form" property="cht010GroupList" />
        <logic:iterate id="group" name="cht010Form" property="cht010GroupList" indexId="idx">
          <% String lineGroupNoPlus = String.valueOf(idx.intValue()+1); %>
          <logic:equal name="group" property="cgiCompFlg" value="0">
            <div class="pl5 w100 mt5">
                <logic:equal name="group" property="cgiCompFlg" value="1">
                  <a class="cl_fontWeek js_chtGroup js_archiveGroup js_group_name display_b lh130" href="#!" value="<bean:write name="group" property="cgiSid"/>">
                    <bean:write name="group" property="cgiName"/>
                    <span class="midokuCount js_midokuCount"><%--
                  --%><logic:notEqual name="group" property="chtGroupCount" value="0"><%--
                    --%><bean:write name="group" property="chtGroupCount"/><%--
                  --%></logic:notEqual><%--
                --%></span>
                  </a>
                </logic:equal>
                <logic:notEqual name="group" property="cgiCompFlg" value="1">
                  <a class="cl_linkDef js_chtGroup js_group_name display_b lh130" href="#!" value="<bean:write name="group" property="cgiSid"/>">
                    <bean:write name="group" property="cgiName"/>
                    <span class="midokuCount js_midokuCount"><%--
                  --%><logic:notEqual name="group" property="chtGroupCount" value="0"><%--
                    --%><bean:write name="group" property="chtGroupCount"/><%--
                  --%></logic:notEqual><%--
                --%></span>
                  </a>
                </logic:notEqual>
            </div>
          </logic:equal>
          <logic:equal name="group" property="cgiCompFlg" value="1">
            <div class="pl5 w100 mt5 display_n">
              <logic:equal name="group" property="cgiCompFlg" value="1">
                <a class="cl_fontWeek js_chtGroup js_archiveGroup js_group_name display_b lh130" href="#!" value="<bean:write name="group" property="cgiSid"/>">
                  <span class="js_dsp_group_name"><bean:write name="group" property="cgiName"/></span>
                  <span class="midokuCount js_midokuCount"><%--
                --%><logic:notEqual name="group" property="chtGroupCount" value="0"><%--
                  --%><bean:write name="group" property="chtGroupCount"/><%--
                --%></logic:notEqual><%--
              --%></span>
                </a>
              </logic:equal>
              <logic:notEqual name="group" property="cgiCompFlg" value="1">
                <a class="cl_linkDef js_chtGroup js_group_name display_b lh130" href="#!" value="<bean:write name="group" property="cgiSid"/>">
                  <bean:write name="group" property="cgiName"/>
                  <span class="midokuCount js_midokuCount"><%--
                --%><logic:notEqual name="group" property="chtGroupCount" value="0"><%--
                  --%><bean:write name="group" property="chtGroupCount"/><%--
                --%></logic:notEqual><%--
              --%></span>
                </a>
              </logic:notEqual>
            </div>
          </logic:equal>
        </logic:iterate>
        <div class="js_archive cht_archive">
          <span class="verAlignMid">
            <input type="checkbox" name="archive" value="1" id="archive">
            <label for="archive"><gsmsg:write key="cht.cht010.15" /></label>
          </span>
        </div>
      </div>

      <!-- ユーザ情報 -->
      <div id="userHeadArea" class="bor_t1 p5 fw_b cursor_p side_header-folding cht_sideHeader">
        <span class="side_headerTitle side_header-open fs_13">
        <span class="side_headerArrow"></span><gsmsg:write key="cmn.shain.info" /></span>
      </div>
      <div id="userBodyArea" class="bor_t1">
        <div class="p5">
          <html:select name="cht010Form" property="cht010GroupSid" styleId="cht010ChangeGrp" styleClass="w80" >
            <logic:notEmpty name="cht010Form" property="cht010ComboGroupList">
              <logic:iterate id="gpBean" name="cht010Form" property="cht010ComboGroupList" scope="request">
                <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                <logic:equal name="gpBean" property="styleClass" value="0">
                  <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                </logic:equal>
                <logic:equal name="gpBean" property="styleClass" value="1">
                  <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
                </logic:equal>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
          <button type="button" onclick="openGroupWindow(this.form.cht010GroupSid, 'cht010GroupSid', '0', 'changeGrp', '1', 'fakeSearchGrpButton')" class="iconBtn-border ml5"  id="cht010GroupBtn">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
            <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
          </button>
          <button type="button" class="display_n" id="fakeSearchGrpButton" name="fakeSearchGrpButton" />
            &nbsp;
          </button>
        </div>
        <div id="selGrpUsrArea">
          <logic:notEmpty name="cht010Form" property="cht010UserList" >
            <logic:iterate id="usrList" name="cht010Form" property="cht010UserList" >
              <div class="m5">
                <logic:notEqual name="usrList" property="usiPictKf" value="0">
                  <div class="js_chtUser js_user_name display_b cursor_p display_inline w100 verAlignMid" value="<bean:write name="usrList" property="usrSid"/>">
                  <div class="mr5">
                  <span class="hikokai_photo-s hikokai_text cl_fontWarn"><gsmsg:write key="cmn.private.photo" /></span>
                </logic:notEqual>
                <logic:equal name="usrList" property="usiPictKf" value="0">
                  <div class="js_chtUser js_user_name display_b cursor_p display_inline w100 verAlignMid" value="<bean:write name="usrList" property="usrSid"/>">
                  <div class="mr5">
                  <logic:equal name="usrList" property="binSid" value="0">
                    <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_classicImg-display"/>
                    <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_originalImg-display"/>
                  </logic:equal>
                  <logic:notEqual name="usrList" property="binSid" value="0">
                    <logic:equal name="usrList" property="usrJkbn" value="9">
                      <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_classicImg-display"/>
                      <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_originalImg-display"/>
                    </logic:equal>
                    <logic:notEqual name="usrList" property="usrJkbn" value="9">
                      <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="usrList" property="binSid"/>" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25"/>
                    </logic:notEqual>
                  </logic:notEqual>
                </logic:equal>
                </div>
                <div class="verAlignMid">
                <logic:notEqual name="usrList" property="usrJkbn" value="0">
                  <del class="cl_linkDef">
                    <bean:write name="usrList" property="usiSei" />&nbsp;<bean:write name="usrList" property="usiMei" />
                    <span class="midokuCount js_midokuCount"><%--
                  --%><logic:notEqual name="usrList" property="chtUserCount" value="0"><%--
                    --%><bean:write name="usrList" property="chtUserCount" /><%--
                  --%></logic:notEqual><%--
                --%></span>
                  </del>
                </logic:notEqual>
                <logic:equal name="usrList" property="usrJkbn" value="0">
                  <span class="<logic:notEqual name="usrList" property="usrUkoFlg" value="1">cl_linkDef</logic:notEqual><logic:equal name="usrList" property="usrUkoFlg" value="1">mukoUser</logic:equal>"><bean:write name="usrList" property="usiSei" />&nbsp;<bean:write name="usrList" property="usiMei" /></span>
                  <span class="midokuCount js_midokuCount <logic:notEqual name="usrList" property="usrUkoFlg" value="1">cl_linkDef</logic:notEqual><logic:equal name="usrList" property="usrUkoFlg" value="1">mukoUser</logic:equal>"><%--
                --%><logic:notEqual name="usrList" property="chtUserCount" value="0"><%--
                  --%><bean:write name="usrList" property="chtUserCount" /><%--
                --%></logic:notEqual><%--
              --%></span>
                </logic:equal>
                </div>
              </div>
              </div>
            </logic:iterate>
          </logic:notEmpty>
        </div>
      </div>
    </div>

    <bean:define id="clsName" value="" />
    <logic:notEqual name="cht010Form" property="cht010SelectTab" value="1">
       <bean:define id="clsName" value="display_n" />
    </logic:notEqual>
    <div id="tabTimeline" class="<%=clsName%> bor_l1 bgC_body">

      <div class="display_flex cht_sideHeader side_header-folding">
        <bean:define id="clsMidokuCount" value="" />
        <logic:notEqual name="cht010Form" property="cht010MidokuCount" value="0">
          <bean:define id="clsMidokuCount" value="menuHead-midoku" />
        </logic:notEqual>
        <div class="w65 p5 cursor_p hp40 <%=clsMidokuCount %>" id="timelineHeadArea">
          <span class="side_headerTitle side_header-open fs_13">
            <span class="side_headerArrow"></span>
            <span class="menuHead_txt-midoku">
              <gsmsg:write key="cmn.read.yet" />
              <span class="midokuCount js_allMidoku"><%--
            --%><bean:write name="cht010Form" property="cht010MidokuCount"/><%--
          --%></span>
            </span>
            <span class="menuHead_txt-close">
              <gsmsg:write key="cmn.close" />
            </span>
            <span class="menuHead_txt-open">
              <gsmsg:write key="cmn.open" />
            </span>
          </span>
        </div>
        <div class="verAlignMid pr5">
          <logic:equal name="cht010Form" property="cht010GroupEditFlg" value="1">
            <span class="menu_head_txt menuHead_noread no_w verAlignMid cl_fontOutline">
              <html:checkbox name="cht010Form" property="cht010TimelineDspOnlyNoRead" value="1" styleId="cht010TimelineDspOnlyNoRead_1" styleClass="js_checkOnlyNoRead" ></html:checkbox>
              <label for="cht010TimelineDspOnlyNoRead_1"><gsmsg:write key="cht.cht010.49" /></label>
            </span>
          </logic:equal>
        </div>
      </div>
      <bean:size id="midokuSize" name="cht010Form" property="cht010MidokuList" />
      <div id="timelineBodyArea">
        <div class="js_timelineListArea">
          <logic:equal name="midokuSize" value="0">
            <div class="p5 js_no_new_message bor_t1">
              <gsmsg:write key="cht.cht010.13" />
            </div>
          </logic:equal>
          <logic:notEqual name="midokuSize" value="0">
            <logic:iterate id="midokuList" name="cht010Form" property="cht010MidokuList" indexId="Idx">
              <bean:define id="clLink" value="cl_fontBody"/>
              <bean:define id="midokuCntStr" value=""/>
              <logic:notEqual name="midokuList" property="midokuCount" value="0">
                <bean:define id="clLink" value="cl_linkDef"/>
                <bean:define id="midokuCntStr" ><bean:write name="midokuList" property="midokuCount"/></bean:define>
              </logic:notEqual>
              <logic:equal name="midokuList" property="midokuKbn" value="1">
                <div class="bor_t1 p5 cursor_p js_chtUser timeline_div"  value="<bean:write name="midokuList" property="midokuSid"/>">
              </logic:equal>
              <logic:equal name="midokuList" property="midokuKbn" value="2">
                <bean:define id="archive_group" value=""/>
                <logic:notEqual name="midokuList" property="archiveFlg" value="0">
                  <bean:define id="archive_group" value="js_archive_group"/>
                </logic:notEqual>
                <div class="bor_t1 p5 cursor_p js_chtGroup timeline_div <bean:write name="archive_group"/>"  value="<bean:write name="midokuList" property="midokuSid"/>">
              </logic:equal>
                <div>
                  <logic:equal name="midokuList" property="midokuKbn" value="1">
                    <span class="js_dspName <bean:write name="clLink"/> <logic:equal name="midokuList" property="usrUkoFlg" value="1">mukoUser</logic:equal>">
                      <img class="btn_classicImg-display wp18hp20" src="../common/images/classic/icon_user.png">
                      <img class="btn_originalImg-display wp18hp20" src="../common/images/original/icon_user.png">
                      <logic:equal name="midokuList" property="midokuJkbn" value="0">
                        <bean:write name="midokuList" property="midokuName"/>
                      </logic:equal>
                      <logic:notEqual name="midokuList" property="midokuJkbn" value="0">
                        <del>
                          <bean:write name="midokuList" property="midokuName"/>
                        </del>
                      </logic:notEqual>
                      <span class="midokuCount js_midokuCount"><%--
                      --%><bean:write name="midokuCntStr"/><%--
                  --%></span>
                    </span>
                  </logic:equal>
                  <logic:equal name="midokuList" property="midokuKbn" value="2">
                    <logic:equal name="midokuList" property="midokuCount" value="0">
                      <logic:notEqual name="midokuList" property="archiveFlg" value="0">
                        <bean:define id="clLink" value="cl_fontWeek"/>
                      </logic:notEqual>
                    </logic:equal>
                    <span class="js_dspName <bean:write name="clLink"/>">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                      <bean:write name="midokuList" property="midokuName"/>
                      <span class="midokuCount js_midokuCount"><%--
                      --%><bean:write name="midokuCntStr"/><%--
                    --%></span>
                    </span>
                  </logic:equal>
                </div>
                <div class="lh_normal fs_12 js_lastTime txt_r">
                  <bean:write name="midokuList" property="midokuDispDate"/>
                  <bean:define id="lastDate" name="midokuList" property="midokuDate"/>
                </div>
                <div class="display_n js_dateNone">
                  <bean:write name="midokuList" property="midokuDate"/>
                </div>
              </div>
            </logic:iterate>
          </logic:notEqual>
        </div>
      </div>
    </div>
    <logic:equal name="cht010Form" property="cht010MoreView" value="1">
      <div id="js_lastdate" class="display_n"><bean:write name="lastDate"/></div>
      <div class="w100 m0 border_none js_moreButton <%=clsName%>">
        <button type="button" class="baseBtn w100 mt5 mr0 ml0 js_moreView">
          <gsmsg:write key="cht.cht010.14" />
        </button>
      </div>
    </logic:equal>
    <logic:notEqual name="cht010Form" property="cht010MoreView" value="1">
      <div id="js_lastdate" class="display_n"></div>
      <div class="w100 mt5 m0 border_none js_moreButton <%=clsName%>">
        <button type="button" class="baseBtn w100 js_moreView display_n">
          <gsmsg:write key="cht.cht010.14" />
        </button>
      </div>
    </logic:notEqual>
  </div>
  <!-- 右 -->
  <div class="main">
    <div class="display_n js_selectSid"><bean:write name="cht010Form" property="cht010SelectPartner"/></div>
    <div class="display_n js_selectKbn"><bean:write name="cht010Form" property="cht010SelectKbn"/></div>
    <bean:define id="info" name="cht010Form" property="cht010ChtInfMdl"/>
    <table class="w100 mt0">
      <tr>
        <td>
          <table class="table-top w100 mt0">
            <tr class="js_chatHeader">
              <th class="txt_l w95 pl20 mr0 border_right_none">
                <div class="verAlignMid">
                <span class="js_chatName fs_18 lh150">
                  <bean:write name="info" property="chatName"/>
                </span>
                <div class="display_n js_favorite_flg"><bean:write name="cht010Form" property="cht010FavoriteFlg"/></div>
                <div class="verAlignMid ml20 mr5 chtFavoInfo_space">
                <logic:equal name="cht010Form" property="cht010FavoriteFlg" value="1">
                  <img class="btn_classicImg-display wp25 cursor_p js_chtStar" id="js_starImg" src="../chat/images/classic/icon_star.png" />
                  <i class="icon-star js_starImgI cursor_p js_chtStar fs_18"></i>
                </logic:equal>
                <logic:notEqual name="cht010Form" property="cht010FavoriteFlg" value="1">
                  <img class="btn_classicImg-display wp25 cursor_p js_chtStar" id="js_starImg" src="../chat/images/classic/icon_star_mi.png" />
                  <i class="icon-star_line js_starImgI cursor_p js_chtStar fs_18"></i>
                </logic:notEqual>
                  <img class="btn_classicImg-display wp25 cursor_p ml10" src="../chat/images/classic/icon_info.png" onclick="group_info();" />
                  <i class="icon-infomation cursor_p fs_18 ml10" onclick="group_info();"></i>
                </div>
                </div>
              </th>
              <th class="no_w w5 border_left_none cht_changeIcon">
                <img class="btn_classicImg-display wp25 cursor_p" id="checkImg" src="../chat/images/classic/icon_check.png" onclick="check_change();" />
                <i class="icon-dl cursor_p fs_18" id="checkImgI" onclick="check_change();"></i>
              </th>
            </tr>
            <tr class="js_content_area">
              <td colspan="3" class="p0 w100">
                <p id="hiduke_header" class="ml5 cht_dayLine cl_fontWeek fw_b js_hiduke_fixed"></p>
                <div id="js_chatMessageArea" class="chatList customScrollBar">
                  <bean:define id="dateFlg" value="20000101"/>
                  <bean:define id="kidokuFlg" value="0"/>
                  <bean:define id="dateCnt" value="0"/>
                  <logic:iterate id="message" name="cht010Form" property="cht010MessageList" indexId="Idx">
                    <bean:define id="border" value="0"/>
                    <!-- 日付入力 -->
                    <logic:notEqual name="message" property="entryDay" value="<%=dateFlg.toString() %>">
                      <bean:define id="dateflg" name="message" property="entryDay"/>
                      <bean:define id="dateFlg" value="<%=dateflg.toString() %>"/>
                      <p id="<bean:write name="message" property="entryDay"/>" class="cht_dayLine cl_fontWeek fw_b js_hiduke_fixed"><bean:write name="message" property="entryDay"/></p>
                      <bean:define id="border" value="1"/>
                    </logic:notEqual>
                    <!-- 新着メッセージフラグ -->
                    <logic:equal name="kidokuFlg" value="0">
                      <logic:equal name="message" property="ownKidoku" value="1">
                        <bean:define id="kidokuFlg" value="1"/>
                        <p class="js_lineMidoku cht_dayLine cht_newLine cl_linkDef fw_b js_hiduke_fixed" ><gsmsg:write key="cht.cht010.11"/></p>
                        <bean:define id="border" value="1"/>
                      </logic:equal>
                    </logic:equal>
                    <!-- 区切り線 -->
                    <logic:equal name="border" value="0">
                      <div class="chat_lrSpace mt5 mb5">
                        <div class="bor_t1 w100"></div>
                      </div>
                    </logic:equal>
                    <span class="js_hiduke" value="<bean:write name="message" property="entryDay"/>"></span>
                    <!-- 削除済みか -->
                    <logic:equal name="message" property="messageKbn" value="<%= String.valueOf(jkbn) %>">
                      <div class="js_mediaArea chat_lrSpace pt10 pb10">
                        <span class="display_n js_kidoku"><bean:write name="message" property="ownKidoku"/></span>
                        <span id="<bean:write name="message" property="messageSid"/>" class="js_check">
                          <img src="../common/images/classic/icon_trash.png" class="btn_classicImg-display">
                          <img src="../common/images/original/icon_trash.png" class="btn_originalImg-display">
                          <gsmsg:write key="cht.cht010.03" />
                        </span>
                       </div>
                    </logic:equal>
                    <!-- 自分のメッセージか他人のメッセージか -->
                    <logic:notEqual name="message" property="messageKbn" value="<%= String.valueOf(jkbn) %>">
                      <bean:define id="usrSid" name="message" property="usrSid"/>
                      <!-- 自分 -->
                      <logic:equal name="cht010Form" property="cht010EditUsrSid" value="<%=usrSid.toString() %>">
                      <div class="media_border media_mine js_media_mine js_mediaArea js_media_<bean:write name="message" property="messageSid" />" onclick="check_selected('js_media_<bean:write name="message" property="messageSid" />')">
                        <span class="display_n js_kidoku"><bean:write name="message" property="ownKidoku"/></span>
                        <span class="js_chtCheckbox display_n selToukou ml10">
                          <input type="checkbox" id="<bean:write name="message" property="messageSid"/>" class="check_size js_check" value="<bean:write name="message" property="messageSid"/>">
                        </span>
                        <div class="chat_lrSpace pt5 pb5 pos_rel">
                          <div class="flo_l mr10 wp25">
                            <logic:notEqual name="message" property="usrPictKf" value="0">
                              <span class="hikokai_photo-s hikokai_text cl_fontWarn"><gsmsg:write key="cmn.private.photo" /></span>
                            </logic:notEqual>
                            <logic:equal name="message" property="usrPictKf" value="0">
                              <logic:equal name="message" property="usrBinSid" value="0">
                                <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_classicImg-display"/>
                                <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_originalImg-display"/>
                              </logic:equal>
                              <logic:notEqual name="message" property="usrBinSid" value="0">
                                <logic:equal name="message" property="usrJkbn" value="9">
                                  <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_classicImg-display"/>
                                  <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_originalImg-display"/>
                                </logic:equal>
                                <logic:notEqual name="message" property="usrJkbn" value="9">
                                  <div class="txt_c">
                                    <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="message" property="usrBinSid"/>" name="userImage" onload="initImageView50('userImage<bean:write name="message" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="userIcon_size-w25"/>
                                  </div>
                                </logic:notEqual>
                              </logic:notEqual>
                            </logic:equal>
                          </div>
                          <div class="of_h">
                            <div class="js_media_heading">
                              <span class="cl_linkDef linkHover_line cursor_p fw_b" onclick="openUserInfoWindow(<bean:write name="message" property="usrSid"/>);">
                                <bean:write name="message" property="usrName"/>
                              </span>
                              <span class="cl_fontWeek ml10"><bean:write name="message" property="entryTime"/></span>
                              <logic:equal name="message" property="partnerKidoku" value="1">
                                <span class="cl_fontWeek ml5"><gsmsg:write key="cht.cht010.04" /></span>
                              </logic:equal>
                            </div>
                            <div class="js_media_text_<bean:write name="message" property="messageSid"/>">
                              <logic:equal name="message" property="binSid" value="-1">
                                <span class="js_message word_b-all"><bean:write name="message" property="messageText" filter="false"/></span>
                                <logic:equal name="message" property="messageKbn" value="1">
                                  <span class="cl_fontWeek edit_chat fs_12">
                                    <span class="edit_time ml5 bgC_body bor1 cl_fontBody">
                                      <bean:write name="message" property="updateDay"/>&nbsp;
                                      <bean:write name="message" property="updateTime"/>
                                    </span>
                                    <gsmsg:write key="cht.cht010.02" />
                                  </span>
                                </logic:equal>
                              </logic:equal>
                              <logic:notEqual name="message" property="binSid" value="-1">
                                <bean:define id="temp" name="message" property="tmpFile"/>
                                <a href="#!" class="js_tempDonwload" value="<bean:write name="message" property="messageSid"/>">
                                  <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif">
                                  <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png">
                                  <span class="js_message word_b-all js_temp"><bean:write name="temp" property="binFileName"/><bean:write name="temp" property="binFileSizeDsp"/></span>
                                </a>
                              </logic:notEqual>
                            </div>
                          </div>

                          <logic:notEqual name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendSeigen) %>">
                            <logic:notEqual name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendArchive) %>">
                              <logic:notEqual name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendDelUsr) %>">
                                <logic:notEqual name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendSpaccess) %>">
                                  <logic:notEqual name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendAccess) %>">
                                    <div class="edit_deleteArea js_editDeleteArea">
                                      <div class="verAlignMid">
                                      <logic:equal name="message" property="binSid" value="-1">
                                        <span class="message_edit js_message_edit mr10 cl_linkDef cursor_p" value="<bean:write name="message" property="messageSid"/>"><%--
                                      --%><img class="btn_classicImg-display" src="../common/images/classic/icon_edit_3.png"><%--
                                      --%><img class="btn_originalImg-display" src="../common/images/original/icon_edit.png"><%--
                                      --%><span class="ml5"><gsmsg:write key="cmn.edit"/></span><%--
                                    --%></span>
                                      </logic:equal>
                                      <span class="message_delete js_message_delete cl_linkDef cursor_p" value="<bean:write name="message" property="messageSid"/>"><%--
                                    --%><img class="btn_classicImg-display" src="../common/images/classic/icon_trash.png"><%--
                                    --%><img class="btn_originalImg-display" src="../common/images/original/icon_delete.png"><%--
                                    --%><span class="ml5"><gsmsg:write key="cmn.delete"/></span><%--
                                  --%></span>
                                      </div>
                                    </div>
                                  </logic:notEqual>
                                </logic:notEqual>
                              </logic:notEqual>
                            </logic:notEqual>
                          </logic:notEqual>
                        </div>
                      </div>
                      </logic:equal>
                      <!-- 他人 -->
                      <logic:notEqual name="cht010Form" property="cht010EditUsrSid" value="<%=usrSid.toString() %>">
                        <div class="media_border js_mediaArea js_media_<bean:write name="message" property="messageSid" />" onclick="check_selected('js_media_<bean:write name="message" property="messageSid" />')">
                          <span class="display_n js_kidoku"><bean:write name="message" property="ownKidoku"/></span>
                          <span class="js_chtCheckbox display_n selToukou ml10">
                            <input type="checkbox" id="<bean:write name="message" property="messageSid"/>" class="check_size js_check" value="<bean:write name="message" property="messageSid"/>">
                          </span>
                          <div class="chat_lrSpace pt5 pb5 pos_rel">
                            <div class="flo_l mr10 wp25">
                              <logic:notEqual name="message" property="usrPictKf" value="0">
                                <span class="hikokai_photo-s hikokai_text cl_fontWarn"><gsmsg:write key="cmn.private.photo" /></span>
                              </logic:notEqual>
                              <logic:equal name="message" property="usrPictKf" value="0">
                                <logic:equal name="message" property="usrBinSid" value="0">
                                  <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_classicImg-display"/>
                                  <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_originalImg-display"/>
                                </logic:equal>
                                <logic:notEqual name="message" property="usrBinSid" value="0">
                                  <logic:equal name="message" property="usrJkbn" value="9">
                                    <img src="../common/images/classic/icon_photo.gif" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_classicImg-display"/>
                                    <img src="../common/images/original/photo.png" name="userImage" onload="initImageView50('userImage<bean:write name="usrList" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="wp25 btn_originalImg-display"/>
                                  </logic:equal>
                                  <logic:notEqual name="message" property="usrJkbn" value="9">
                                    <div class="txt_c">
                                      <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="message" property="usrBinSid"/>" name="userImage" onload="initImageView50('userImage<bean:write name="message" property="usrSid"/>')" alt="<gsmsg:write key="cmn.photo" />" class="userIcon_size-w25"/>
                                    </div>
                                  </logic:notEqual>
                                </logic:notEqual>
                              </logic:equal>
                            </div>
                            <div class="of_h">
                              <div class="js_media_heading">
                                <logic:equal name="message" property="usrUkoFlg" value="0">
                                  <span class="cl_linkDef linkHover_line cursor_p fw_b" onclick="openUserInfoWindow(<bean:write name="message" property="usrSid"/>);">
                                </logic:equal>
                                <logic:notEqual name="message" property="usrUkoFlg" value="0">
                                  <span class="mukoUser linkHover_line cursor_p fw_b" onclick="openUserInfoWindow(<bean:write name="message" property="usrSid"/>);">
                                </logic:notEqual>
                                <logic:notEqual name="message" property="usrJkbn" value="0">
                                  <del><bean:write name="message" property="usrName"/></del></span>
                                </logic:notEqual>
                                <logic:equal name="message" property="usrJkbn" value="0">
                                  <bean:write name="message" property="usrName"/></span>
                                </logic:equal>
                                <span class="cl_fontWeek ml10"><bean:write name="message" property="entryTime"/></span>
                              </div>
                              <div class="word_b-all js_media_text_<bean:write name="message" property="messageSid"/>">
                                <logic:equal name="message" property="binSid" value="-1">
                                  <bean:write name="message" property="messageText" filter="false"/>
                                  <logic:equal name="message" property="messageKbn" value="1">
                                    <span class="cl_fontWeek edit_chat fs_12">
                                      <span class="edit_time ml5 bgC_body bor1 cl_fontBody">
                                        <bean:write name="message" property="updateDay"/>&nbsp;
                                        <bean:write name="message" property="updateTime"/>
                                      </span>
                                     <gsmsg:write key="cht.cht010.02" />
                                    </span>
                                  </logic:equal>
                                </logic:equal>
                                <logic:notEqual name="message" property="binSid" value="-1">
                                  <bean:define id="temp" name="message" property="tmpFile"/>
                                  <a href="#!" class="js_tempDonwload" value="<bean:write name="message" property="messageSid"/>">
                                    <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif">
                                    <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png">
                                    <span class="js_message word_b-all js_temp"><bean:write name="temp" property="binFileName"/><bean:write name="temp" property="binFileSizeDsp"/></span>
                                 </a>
                                </logic:notEqual>
                              </div>
                            </div>
                          </div>
                        </div>
                      </logic:notEqual>
                    </logic:notEqual>
                  </logic:iterate>
                  <div class="js_chatSpaceArea">&nbsp;</div>
                </div>
                <!-- 投稿要素 -->
                <div  class="chat_sendMessageArea js_sendMessageArea">
                  <div id="js_senderSid" class="display_n"><bean:write name="<%= key %>" scope="session" property="usrsid" /></div>
                  <logic:equal name="cht010Form" property="cht010MessageAreaDisp" value="0">
                    <div class="w100">
                      <span class="verAlignMid mt5">
                        <logic:equal name="cht010Form" property="cht010EnterSendFlg" value="1">
                          <input type="checkbox" name="enter" class="js_enterSend cursor_p" value="1" id="enter" checked>
                          <label for="enter" class="cursor_p pl5"><gsmsg:write key="cht.cht010.09" /></label>
                        </logic:equal>
                        <logic:notEqual name="cht010Form" property="cht010EnterSendFlg" value="1">
                          <input type="checkbox" name="enter" class="js_enterSend cursor_p" value="0" id="enter">
                          <label for="enter" class="cursor_p pl5"><gsmsg:write key="cht.cht010.09" /></label>
                        </logic:notEqual>
                        <span id="js_errorMsg" class="ml10 cl_fontWarn"></span>
                        <span id="cmn110fileDataArea" class="ml10"></span>
                      </span>
                      <span class="flo_r">
                        <button type="button" class="baseBtn js_chtAttach" value="<gsmsg:write key="cmn.attached" />" onClick="attachmentLoadFile('');">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png">
                          <gsmsg:write key="cmn.attached" />
                        </button>
                        <input type="file" id="attachmentAreaBtn" class="display_none" onchange="attachFileSelect(this, '');" multiple="">
                        <button type="button" class="baseBtn display_n js_chtCansel" value="<gsmsg:write key="cmn.cancel" />">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png">
                          <gsmsg:write key="cmn.cancel" />
                        </button>
                        <button type="button" class="baseBtn js_chtSend" value="<gsmsg:write key="cmn.sent" />">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_1.png">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png">
                          <gsmsg:write key="cmn.sent" />
                        </button>
                        <button type="button" class="baseBtn display_n js_chtConfirm" value="<gsmsg:write key="cmn.final" />">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png">
                          <gsmsg:write key="cmn.final" />
                        </button>
                      </span>
                    </div>
                    <logic:equal name="cht010Form" property="cht010EnterSendFlg" value="1">
                      <textarea class="chattextArea js_chtTextArea" id="inText" placeholder="<gsmsg:write key="cht.cht010.10" />" ></textarea>
                    </logic:equal>
                    <logic:notEqual name="cht010Form" property="cht010EnterSendFlg" value="1">
                      <textarea  class="chattextArea js_chtTextArea" id="inText" placeholder="<gsmsg:write key="cht.cht010.16" />" ></textarea>
                    </logic:notEqual>
                  </logic:equal>
                  <logic:notEqual name="cht010Form" property="cht010MessageAreaDisp" value="0">
                    <div class="w100">
                      <span class="verAlignMid mt5">
                        <input type="checkbox" name="enter" class="mr5" value="1" id="enter" disabled checked>
                        <gsmsg:write key="cht.cht010.09" />
                      </span>
                    </div>
                    <logic:equal name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendSeigen) %>">
                      <textarea class="chattextArea cursor_d" id="inText" placeholder="<gsmsg:write key="cht.cht010.05" />"  readonly></textarea>
                    </logic:equal>
                    <logic:equal name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendArchive) %>">
                      <textarea class="chattextArea cursor_d" id="inText" placeholder="<gsmsg:write key="cht.cht010.06" />"  readonly></textarea>
                    </logic:equal>
                    <logic:equal name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendDelUsr) %>">
                      <textarea class="chattextArea cursor_d" id="inText" placeholder="<gsmsg:write key="cht.cht010.07" />"  readonly></textarea>
                    </logic:equal>
                    <logic:equal name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendSpaccess) %>">
                      <textarea class="chattextArea cursor_d" id="inText" placeholder="<gsmsg:write key="cht.cht010.08" />"  readonly></textarea>
                    </logic:equal>
                    <logic:equal name="cht010Form" property="cht010MessageAreaDisp" value="<%= String.valueOf(notSendAccess) %>">
                      <textarea class="chattextArea cursor_d" id="inText" placeholder="<gsmsg:write key="cht.cht010.39" />"  readonly></textarea>
                    </logic:equal>
                  </logic:notEqual>
                </div>
              </td>
            </tr>
          </table>
        </td>
        <td class="js_rightSpace txt_t p0">
          <div class="display_n ml5 js_ikkatuArea">
            <table class="table-top w100 mt0">
              <tr>
                <th class="w100 txt_l">
                  <gsmsg:write key="cht.cht010.18" />
                </th>
              </tr>
              <tr>
                <td class="w100">
                  <div>
                    <gsmsg:write key="cht.cht010.19" />
                  </div>
                  <table class="js_fileNameSpace w100 table-noBorder"></table>
                  <div class="mt10 txt_c">
                    <button type="button" class="baseBtn js_ikkatuDlBbutton">
                      <gsmsg:write key="cmn.download" />
                    </button>
                    <button type="button" class="baseBtn" onclick="selectFileClose();">
                      <gsmsg:write key="cmn.close" />
                    </button>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </td>
      </tr>
    </table>
  </div>
</div>

<jsp:include page="/WEB-INF/plugin/chat/jsp/cht010_info.jsp" />
<jsp:include page="/WEB-INF/plugin/chat/jsp/cht010_create.jsp" />

<!--メッセージ削除 -->
<div id="delKakuninChtPop" class="display_n txt_c">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="cht.cht010.17" />
      </td>
    </tr>
  </table>
</div>

<span class="js_footerArea">
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</span>

</html:form>

</body>
</html:html>
