<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.UserSearchModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cmn.GSConstSchedule" %>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="../zaiseki/js/zskmaingrp.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmnPic.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
</head>

<body onload="initPicture2('userImage', 130, 150);">
<div id="zaiseki_zskmain_div">
<html:form action="/zaiseki/zskmaingrp">
<div id="tooltips_zsk">
<input type="hidden" name="CMD" value="">
<html:hidden property="zskSelectUsrSid" />
<html:hidden property="zskSelectSchSid" />
<% boolean originalTheme =  jp.groupsession.v2.cmn.biz.JspBiz.getTheme(request);%>

<logic:equal name="zskmaingrpForm" property="zskGrpDspFlg" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAINGRP_DSP) %>">

<table class="table-top w100 mb0">
  <tr>
    <th class="txt_l table_title-color w100">
      <img class="mainPlugin_icon" src="../zaiseki/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="zsk.zskmaingrep.06" />">
      <a href="<bean:write name="zskmaingrpForm" property="zskTopUrl" />">
        <gsmsg:write key="zsk.zskmaingrep.06" />
      </a>
      <span class="flo_r">
        <%if (originalTheme) { %>
          <button class="mainConfigBtn" type="button" value="<gsmsg:write key="cmn.setting" />" onClick="zskPriConf('mainDspSetting');">
            <gsmsg:write key="cmn.setting" />
          </button>
        <% } else { %>
          <input type="button" onclick="zskPriConf('mainDspSetting');" class="mainConfigBtn cursor_p" value="<gsmsg:write key="cmn.setting" />">
        <% } %>
      </span>
    </th>
  </tr>
  <!-- グループコンボ -->
  <logic:notEmpty name="zskmaingrpForm" property="zaiGrpLabelList">
    <tr class="bgC_lightGray">
      <input type="button" class="display_none" Id="fakeButton" onClick="return updateZskGrp('changeGrpCmb');">
      <td>
        <gsmsg:write key="cmn.show.group" />
        <br>
        <div class="verAlignMid">
        <html:select name="zskmaingrpForm" styleClass="wp200" property="zaiGrpSid" onchange="updateZskGrp('changeGrpCmb');">
          <logic:iterate id="gpBean" name="zskmaingrpForm" property="zaiGrpLabelList" scope="request">
            <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
            <logic:equal name="gpBean" property="styleClass" value="0">
              <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
            </logic:equal>
            <logic:notEqual name="gpBean" property="styleClass" value="0">
              <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
            </logic:notEqual>
          </logic:iterate>
        </html:select>
        <button class="iconBtn-border ml5" type="button" id="zskmaingrpGroupBtn" value="&nbsp;&nbsp;" onClick="openGroupWindow(this.form.zaiGrpSid, 'zaiGrpSid', '1', 'changeGrpCmb', '1', 'fakeButton');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
        </button>
        </div>
      </td>
    </tr>
  </logic:notEmpty>
  <logic:notEmpty name="zskmaingrpForm" property="userList" scope="request">
    <% boolean space_flg = true; %>
    <logic:iterate id="userMdl" name="zskmaingrpForm" property="userList" scope="request" indexId="idx" type="UserSearchModel">
    <%if (originalTheme) { %>
      <tr>
        <td>
          <div>
            <table class="w100 table-noBorder">
              <tr>
                <td class="pl0 pr0" rowspan="2">
                  <logic:equal name="userMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                    <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />);"><div align="center" class="hikokai_photo-m wp35 hp40 lh_normal"><span class="hikokai_text mt10"><gsmsg:write key="cmn.private" /></span></div></a>
                  </logic:equal>
                  <logic:equal name="userMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>">
                    <logic:equal name="userMdl" property="binSid" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.USR_IMAGE_NOT_DSP) %>">
                      <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />);">
                        <img src="../common/images/original/photo.png" name="userImage" class="original-display wp35" alt="<gsmsg:write key="cmn.photo" />">
                      </a>
                    </logic:equal>
                    <logic:notEqual name="userMdl" property="binSid" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.USR_IMAGE_NOT_DSP) %>">
                      <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />);">
                        <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="userMdl" property="binSid" />" name="userImage" class="wp35" alt="<gsmsg:write key="cmn.photo" />">
                      </a>
                    </logic:notEqual>
                  </logic:equal>
                </td>
                <td class="main_zskProfArea txt_r w100">
                  <a href="#!" class="fw_b flo_l <%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />)">
                    <bean:write name="userMdl" property="usiSei" />&nbsp;<bean:write name="userMdl" property="usiMei" />
                  </a>
                  <!-- 在席 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
                    <span class="main_zskLadio-zaiseki"><gsmsg:write key="cmn.zaiseki2" /></span>
                  </logic:equal>
                  <!-- 不在 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
                    <span class="main_zskLadio-huzai"><gsmsg:write key="cmn.absence2" /></span>
                  </logic:equal>
                  <!-- その他 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
                    <span class="main_zskLadio-sonota"><gsmsg:write key="cmn.other" /></span>
                  </logic:equal>
                </td>
              </tr>
              <tr>
                <td class="main_zskProfArea">
                  <!-- 在席状況変更ボタン -->
                  <logic:equal name="zskmaingrpForm" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
                    <!-- 在席 -->
                    <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
                      <a href="#!" onClick="return openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>);">
                        <img class="btn_originalImg-display eventImg" src="../zaiseki/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
                      </a>
                    </logic:equal>
                    <!-- 不在 -->
                    <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
                      <a href="#!" onClick="return openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>);">
                        <img class="btn_originalImg-display eventImg" src="../zaiseki/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
                      </a>
                    </logic:equal>
                    <!-- その他 -->
                    <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
                      <a href="#!" onClick="return openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>);">
                        <img class="btn_originalImg-display eventImg" src="../zaiseki/images/original/menu_icon_single.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
                      </a>
                    </logic:equal>
                  </logic:equal>
                  <!-- ショートメールボタン -->
                  <logic:equal name="zskmaingrpForm" property="smailUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
                    <logic:equal name="userMdl" property="smlAble" value="1">
                      <a href="#" onClick="moveCreateMsg('msg', <bean:write name="userMdl" property="usrSid" />);">
                        <img class="btn_originalImg-display eventImg" src="../zaiseki/images/original/icon_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
                      </a>
                    </logic:equal>
                  </logic:equal>
                  <!-- スケジュール表示切替ボタン -->
                  <logic:equal name="zskmaingrpForm" property="schUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
                    <logic:equal name="userMdl" property="schAccessFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_ACCESS_YES) %>">
                      <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_DSP) %>">
                        <a href="#!" onClick="dispDescriptionZskOriginal('ds<bean:write name="idx" />');">
                          <img class="btn_originalImg-display eventImg" id="resesSwitchds<bean:write name='idx' />" src="../zaiseki/images/original/icon_sch_not_dsp.png" alt="<gsmsg:write key="cmn.hide" />">
                        </a>
                      </logic:equal>
                      <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_NOT_DSP) %>">
                        <a href="#!" onClick="dispDescriptionZskOriginal('ds<bean:write name="idx" />');">
                          <img class="btn_originalImg-display eventImg" id="resesSwitchds<bean:write name='idx' />" src="../zaiseki/images/original/icon_sch_dsp.png" alt="<gsmsg:write key="zsk.zskmaingrep.07" />">
                        </a>
                      </logic:equal>
                    </logic:equal>
                  </logic:equal>
                </td>
              </tr>
            </table>
            <!-- コメント -->
            <logic:notEmpty name="userMdl" property="uioComment">
              <div class="main_zskComment" colspan="2">
                <bean:write name="userMdl" property="uioComment" />
              </div>
            </logic:notEmpty>
            <logic:equal name="zskmaingrpForm" property="schUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
              <logic:equal name="userMdl" property="schAccessFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_ACCESS_YES) %>">
                <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_DSP) %>">
                  <div id="ds<bean:write name='idx' />" class="">
                </logic:equal>
                <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_NOT_DSP) %>">
                  <div id="ds<bean:write name='idx' />" class="display_n">
                </logic:equal>
                <div class="main_zskSchedule lh140">
                  <!-- スケジュール表示 -->
                  <logic:notEmpty name="zskmaingrpForm" property="scheduleList" scope="request">
                    <logic:iterate id="scdMdl" name="zskmaingrpForm" property="scheduleList" scope="request" indexId="idx2">
                      <bean:define id="dspUser" name="userMdl" property="usrSid" />
                      <bean:define id="selUser" name="scdMdl" property="scdUsrSid" />
                      <bean:define id="u_usrsid" name="scdMdl" property="scdUsrSid" type="java.lang.Integer" />
                      <bean:define id="u_schsid" name="scdMdl" property="scdSid" type="java.lang.Integer" />
                      <%
                        if (dspUser.equals(selUser)) {
                      %>
                        <logic:empty name="scdMdl" property="scdValue">
                          <logic:notEqual name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                            <logic:notEqual name="scdMdl" property="scdPublic" value="<%= String.valueOf(GSConstSchedule.DSP_YOTEIARI) %>">
                            <logic:notEqual name="scdMdl" property="scdPublic" value="<%= String.valueOf(GSConstSchedule.DSP_TITLE) %>">
                              <logic:notEqual name="scdMdl" property="notBelongGrpFlg" value="true">
                                <a href="#" title="" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);">
                                  <span class="tooltips"><bean:write name="scdMdl" property="scdTitle" /></span>
                              </logic:notEqual>
                            </logic:notEqual>
                            </logic:notEqual>
                          </logic:notEqual>
                          <logic:equal name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                            <a href="#" title="" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);">
                              <span class="tooltips"><bean:write name="scdMdl" property="scdTitle" /></span>
                          </logic:equal>
                        </logic:empty>
                        <logic:notEmpty name="scdMdl" property="scdValue">
                          <bean:define id="scnaiyou" name="scdMdl" property="scdValue" />
                          <%
                            String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                            String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                           %>
                          <logic:notEqual name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                            <logic:notEqual name="scdMdl" property="scdPublic" value="2">
                            <logic:notEqual name="scdMdl" property="scdPublic" value="<%= String.valueOf(GSConstSchedule.DSP_TITLE) %>">
                              <a href="#" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);" title="">
                                <span class="tooltips"><gsmsg:write key="cmn.content" />:<br><%= tmpText2 %></span>
                            </logic:notEqual>
                            </logic:notEqual>
                          </logic:notEqual>
                          <logic:equal name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                            <a href="#" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);" title="">
                              <span class="tooltips"><gsmsg:write key="cmn.content" />:<br><%= tmpText2 %></span>
                          </logic:equal>
                        </logic:notEmpty>
                        <!-- スケジュール　タイトルカラー設定 -->
                        <logic:equal name="scdMdl" property="scdBgcolor" value="1">
                          <span class="cl_fontSchTitleBlue fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="2">
                          <span class="cl_fontSchTitleRed fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="3">
                          <span class="cl_fontSchTitleGreen fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="4">
                          <span class="cl_fontSchTitleYellow fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="5">
                          <span class="cl_fontSchTitleBlack fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="6">
                          <span class="cl_fontSchTitleNavy fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="7">
                          <span class="cl_fontSchTitleWine fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="8">
                          <span class="cl_fontSchTitleCien fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="9">
                          <span class="cl_fontSchTitleGray fs_13 verAlignMid">
                        </logic:equal>
                        <logic:equal name="scdMdl" property="scdBgcolor" value="10">
                          <span class="cl_fontSchTitleMarine fs_13 verAlignMid">
                        </logic:equal>
                        <!-- スケジュール　期間表示 -->
                        <bean:write name="scdMdl" property="scdFrToDateDsp" />&nbsp;&nbsp;
                        <!-- スケジュール　タイトル表示 -->
                        <logic:equal name="scdMdl" property="keyFlg" value="true">
                          <span class="mr5">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_lock_13.png">
                        </span>
                        </logic:equal>
                        <bean:write name="scdMdl" property="scdTitle" />
                      </span><br>
                      <% } %>
                    </a>
                    </logic:iterate><!-- スケジュール繰返し終了 -->
                  </logic:notEmpty>
                  <logic:equal name="userMdl" property="schRegistFlg" value="true">
                  <!-- スケジュール追加ボタン -->
                    <div class="txt_r">
                    <a href="#!" class="txt_r" onClick="return zskaddSchedule('schw_add', <bean:write name="userMdl" property="usrSid" />);">
                      <img class="btn_originalImg-display eventImg" src="../common/images/original/icon_add.png">
                    </a>
                    </div>
                  </logic:equal>
                </div>
              </logic:equal>
            </logic:equal>
          </div>
        </td>
      </tr>
    <% } else { %>
      <!-- 在席 -->
      <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
        <bean:define id="zskColor" value="bgC_zaiseki" />
      </logic:equal>
      <!-- 不在 -->
      <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
        <bean:define id="zskColor" value="bgC_huzai" />
      </logic:equal>
      <!-- その他 -->
      <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
        <bean:define id="zskColor" value="bgC_zskOther" />
      </logic:equal>
      <tr>
        <td class="<bean:write name="zskColor" />">
          <table class="w100 table-noBorder">
            <tr>
              <td rowspan="2">
                <logic:equal name="userMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_CLOSE) %>">
                  <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />);"><div align="center" class="hikokai_photo-m wp35 hp40 lh_normal"><span class="hikokai_text mt10"><gsmsg:write key="cmn.private" /></span></div></a>
                </logic:equal>
                <logic:equal name="userMdl" property="usiPictKf" value="<%= String.valueOf(jp.groupsession.v2.usr.GSConstUser.INDIVIDUAL_INFO_OPEN) %>">
                  <logic:equal name="userMdl" property="binSid" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.USR_IMAGE_NOT_DSP) %>">
                    <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />);">
                      <img src="../common/images/classic/icon_photo.gif" name="userImage" class="classic-display wp35" alt="<gsmsg:write key="cmn.photo" />">
                    </a>
                  </logic:equal>
                  <logic:notEqual name="userMdl" property="binSid" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.USR_IMAGE_NOT_DSP) %>">
                    <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />);">
                      <img src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="userMdl" property="binSid" />" name="userImage" class="wp35" alt="<gsmsg:write key="cmn.photo" />">
                    </a>
                  </logic:notEqual>
                </logic:equal>
              </td>
              <td class="p0 w100" colspan="2">
                <a href="#!" class="<%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />)">
                  <bean:write name="userMdl" property="usiSei" />&nbsp;<bean:write name="userMdl" property="usiMei" /><br>
                </a>
              </td>
            </tr>
            <tr>
              <td class="p0">
                <span class="img_middle">
                  <!-- 在席状況表示 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
                    <gsmsg:write key="cmn.zaiseki2" />:
                  </logic:equal>
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
                    <gsmsg:write key="cmn.absence2" />:
                  </logic:equal>
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
                    <gsmsg:write key="cmn.other" />:
                  </logic:equal>
                  <!-- 在席コメント -->
                  <bean:write name="userMdl" property="uioComment" />
                </span>
              </td>
              <td class="txt_r p0">
                <!-- 在席状況変更ボタン -->
                <logic:equal name="zskmaingrpForm" property="zaisekiUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
                  <!-- 在席 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
                    <a href="#!" onClick="return openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>);">
                      <img class="btn_classicImg-display eventImg" src="../zaiseki/images/classic/icon_zsk.gif" alt="<gsmsg:write key="cmn.zaiseki.management" />">
                    </a>
                  </logic:equal>
                  <!-- 不在 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
                    <a href="#!" onClick="return openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>);">
                      <img class="btn_classicImg-display eventImg" src="../zaiseki/images/classic/icon_zsk.gif" alt="<gsmsg:write key="cmn.zaiseki.management" />">
                    </a>
                  </logic:equal>
                    <!-- その他 -->
                  <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
                    <a href="#!" onClick="return openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>);">
                      <img class="btn_classicImg-display eventImg" src="../zaiseki/images/classic/icon_zsk.gif" alt="<gsmsg:write key="cmn.zaiseki.management" />">
                    </a>
                  </logic:equal>
                </logic:equal>
                <!-- ショートメールボタン -->
                <logic:equal name="zskmaingrpForm" property="smailUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
                  <logic:equal name="userMdl" property="smlAble" value="1">
                  <a href="#" onClick="moveCreateMsg('msg', <bean:write name="userMdl" property="usrSid" />);">
                    <img class="btn_classicImg-display eventImg" src="../zaiseki/images/classic/icon_sml.gif" alt="<gsmsg:write key="cmn.shortmail" />">
                  </a>
                  </logic:equal>
                </logic:equal>
                <!-- スケジュール表示切替ボタン -->
                <logic:equal name="zskmaingrpForm" property="schUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
                  <logic:equal name="userMdl" property="schAccessFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_ACCESS_YES) %>">
                    <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_DSP) %>">
                      <a href="#!" onClick="dispDescriptionZsk('ds<bean:write name="idx" />');">
                        <img class="btn_classicImg-display eventImg" id="resesSwitchds<bean:write name='idx' />" src="../zaiseki/images/classic/icon_sch_not_dsp.gif" alt="<gsmsg:write key="cmn.hide" />">
                      </a>
                    </logic:equal>
                    <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_NOT_DSP) %>">
                      <a href="#!" onClick="dispDescriptionZsk('ds<bean:write name="idx" />');">
                        <img class="btn_classicImg-display eventImg" id="resesSwitchds<bean:write name='idx' />" src="../zaiseki/images/classic/icon_sch_dsp.gif" alt="<gsmsg:write key="zsk.zskmaingrep.07" />">
                      </a>
                    </logic:equal>
                  </logic:equal>
                </logic:equal>
              </td>
            </tr>
            <logic:equal name="zskmaingrpForm" property="schUseOk" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.PLUGIN_USE) %>">
              <logic:equal name="userMdl" property="schAccessFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.SCH_ACCESS_YES) %>">
                <tr>
                  <td class="p0" colspan="3">
                    <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_DSP) %>">
                      <div id="ds<bean:write name='idx' />" class="">
                    </logic:equal>
                    <logic:equal name="zskmaingrpForm" property="zaiSchViewDf" value="<%= String.valueOf(jp.groupsession.v2.zsk.GSConstZaiseki.MAIN_SCH_NOT_DSP) %>">
                      <div id="ds<bean:write name='idx' />" class="display_n">
                    </logic:equal>
                    <div class="main_zskSchedule lh140">
                      <!-- スケジュール表示 -->
                      <logic:notEmpty name="zskmaingrpForm" property="scheduleList" scope="request">
                        <logic:iterate id="scdMdl" name="zskmaingrpForm" property="scheduleList" scope="request" indexId="idx2">
                          <bean:define id="dspUser" name="userMdl" property="usrSid" />
                          <bean:define id="selUser" name="scdMdl" property="scdUsrSid" />
                          <bean:define id="u_usrsid" name="scdMdl" property="scdUsrSid" type="java.lang.Integer" />
                          <bean:define id="u_schsid" name="scdMdl" property="scdSid" type="java.lang.Integer" />
                          <%
                            if (dspUser.equals(selUser)) {
                          %>
                          <logic:empty name="scdMdl" property="scdValue">
                            <logic:notEqual name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                              <logic:notEqual name="scdMdl" property="scdPublic" value="<%= String.valueOf(GSConstSchedule.DSP_YOTEIARI) %>">
                                <logic:notEqual name="scdMdl" property="notBelongGrpFlg" value="true">
                                <logic:notEqual name="scdMdl" property="notSelectUsrGrpFlg" value="true">
                                <logic:notEqual name="scdMdl" property="scdPublic" value="<%= String.valueOf(GSConstSchedule.DSP_TITLE) %>">
                                  <a href="#" title="" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);">
                                    <span class="tooltips"><bean:write name="scdMdl" property="scdTitle" /></span>
                                </logic:notEqual>
                                </logic:notEqual>
                                </logic:notEqual>
                              </logic:notEqual>
                            </logic:notEqual>
                            <logic:equal name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                              <a href="#" title="" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);">
                                <span class="tooltips"><bean:write name="scdMdl" property="scdTitle" /></span>
                            </logic:equal>
                          </logic:empty>
                          <logic:notEmpty name="scdMdl" property="scdValue">
                            <bean:define id="scnaiyou" name="scdMdl" property="scdValue" />
                            <%
                              String tmpText = (String)pageContext.getAttribute("scnaiyou",PageContext.PAGE_SCOPE);
                              String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                             %>
                            <logic:notEqual name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                              <logic:notEqual name="scdMdl" property="scdPublic" value="2">
                              <logic:notEqual name="scdMdl" property="scdPublic" value="<%= String.valueOf(GSConstSchedule.DSP_TITLE) %>">
                                <a href="#" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);" title="">
                                  <span class="tooltips"><gsmsg:write key="cmn.content" />:<br><%= tmpText2 %></span>
                              </logic:notEqual>
                              </logic:notEqual>
                            </logic:notEqual>
                            <logic:equal name="zskmaingrpForm" property="dspUserSid" value="<%= selUser.toString() %>">
                              <a href="#" onClick="zskeditSchedule('schw_edit', <bean:write name="scdMdl" property="scdSid" />, <bean:write name="scdMdl" property="scdUsrSid" />);" title="">
                                <span class="tooltips"><gsmsg:write key="cmn.content" />:<br><%= tmpText2 %></span>
                            </logic:equal>
                          </logic:notEmpty>
                          <!-- スケジュール　タイトルカラー設定 -->
                          <logic:equal name="scdMdl" property="scdBgcolor" value="1">
                            <span class="cl_fontSchTitleBlue fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="2">
                            <span class="cl_fontSchTitleRed fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="3">
                            <span class="cl_fontSchTitleGreen fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="4">
                            <span class="cl_fontSchTitleYellow fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="5">
                            <span class="cl_fontSchTitleBlack fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="6">
                            <span class="cl_fontSchTitleNavy fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="7">
                            <span class="cl_fontSchTitleWine fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="8">
                            <span class="cl_fontSchTitleCien fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="9">
                            <span class="cl_fontSchTitleGray fs_13 verAlignMid">
                          </logic:equal>
                          <logic:equal name="scdMdl" property="scdBgcolor" value="10">
                            <span class="cl_fontSchTitleMarine fs_13 verAlignMid">
                          </logic:equal>
                          <!-- スケジュール　期間表示 -->
                          <bean:write name="scdMdl" property="scdFrToDateDsp" />&nbsp;&nbsp;
                          <!-- スケジュール　タイトル表示 -->
                          <logic:equal name="scdMdl" property="keyFlg" value="true">
                            <span class="mr5">
                              <img class="btn_classicImg-display" src="../common/images/classic/icon_lock_13.png">
                            </span>
                          </logic:equal>
                          <bean:write name="scdMdl" property="scdTitle" />
                          </span><br>
                          <% } %>
                        </a>
                        </logic:iterate><!-- スケジュール繰返し終了 -->
                      </logic:notEmpty>
                      <logic:equal name="userMdl" property="schRegistFlg" value="true">
                      <!-- スケジュール追加ボタン -->
                        <div class="txt_r">
                          <img class="btn_classImg-display eventImg cursor_p" src="../common/images/classic/icon_scadd.png" alt="<gsmsg:write key="cmn.add" />" onClick="return zskaddSchedule('schw_add', <bean:write name="userMdl" property="usrSid" />);" />
                        </div>
                      </logic:equal>
                    </div>
                  </td>
                </tr>
              </logic:equal>
            </logic:equal>
          </table>
        </td>
      </tr>
    <% } %>
    </logic:iterate>
  </logic:notEmpty>
</table>
</logic:equal>
</div>
</html:form>
</div>
</body>
</html:html>