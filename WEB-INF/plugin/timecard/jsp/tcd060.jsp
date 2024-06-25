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
  <link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

  <link rel=stylesheet href=../timecard/css/timecard.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../schedule/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
  <script src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd060.js?<%= GSConst.VERSION_PARAM %>1"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.47" /></title>
</head>

<body>
<html:form action="/timecard/tcd060" styleId="tcd060Form" >
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />

<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<input type="hidden" name="addTimezoneKbn" value="">
<input type="hidden" name="editTimezoneSid" value="">

<html:hidden property="tcd060initFlg" />
<html:hidden property="tcd060ZoneKbn" />
<html:hidden property="tcd060FrH" />
<html:hidden property="tcd060FrM" />
<html:hidden property="tcd060ToH" />
<html:hidden property="tcd060ToM" />

<html:hidden property="timezoneCmdMode" />
<html:hidden property="timezoneSid" />
<html:hidden property="ttiUseSetFlg" />

<logic:notEmpty name="tcd060Form" property="selectDay" scope="request">
<logic:iterate id="select" name="tcd060Form" property="selectDay" scope="request">
  <input type="hidden" name="selectDay" value="<bean:write name="select" />">
</logic:iterate>
</logic:notEmpty>

<%-- ダイアログ認識用 --%>
  <input type="hidden" name="dialogMode" value="0">
  <input type="hidden" name="tcd060ZoneNo" value="-1">
  <%-- サブフォーム用 --%>
  <span class="js_hidden">
    <logic:notEmpty name="tcd060Form" property="tcd060TimezoneMeiList" scope="request">
      <logic:iterate id="meiList" name="tcd060Form" property="tcd060TimezoneMeiList" scope="request" indexId="idx">
        <html:hidden name="meiList" property="frTime"  indexed="true"/>
        <html:hidden name="meiList" property="toTime"  indexed="true"/>
        <html:hidden name="meiList" property="timeZoneKbn" indexed="true" />
        <html:hidden name="meiList" property="timeZoneNo" indexed="true" />
        <html:hidden name="meiList" property="timeZoneStr" indexed="true" />
        <html:hidden name="meiList" property="timeZoneSID" indexed="true" />
      </logic:iterate>
    </logic:notEmpty>
  </span>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.tcd030.01" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="OK" onClick="buttonPush('tcd060_ok');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd060_back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="kanriContent mrl_auto">
  <div class="txt_c">
  <logic:messagesPresent message="false">
  <html:errors/>
</logic:messagesPresent>
    <table class="table-left">
      <tr>
        <th>
          <gsmsg:write key="tcd.tcd120.01" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td>
          <html:text name="tcd060Form" property="tcd060Name" styleClass="wp600" maxlength="100" />
        </td>
      </tr>
      <tr>
        <th>
          <gsmsg:write key="tcd.tcd120.02" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td>
          <html:text name="tcd060Form" property="tcd060Ryaku" styleClass="wp200" maxlength="5" />
        </td>
      </tr>
      <tr>
        <th>
          <gsmsg:write key="tcd.tcd060.06" />
        </th>
        <td>
          <logic:equal value="0" name="tcd060Form" property="ttiUseSetFlg">
            <div class="fs_13 mb5">
              <gsmsg:write key="tcd.tcd060.08" />
            </div>
            <span  class="verAlignMid">
              <html:radio styleId="tcd060UseFlg_0" name="tcd060Form" property="tcd060UseFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.TIMEZONE_USE_KBN_OK) %>" /><label for="tcd060UseFlg_0"><gsmsg:write key="tcd.tcd060.09" /></label>
              <html:radio styleId="tcd060UseFlg_1" styleClass="ml10" name="tcd060Form" property="tcd060UseFlg" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.TIMEZONE_USE_KBN_NG) %>" /><label for="tcd060UseFlg_1"><gsmsg:write key="tcd.tcd060.10" /></label>
            </span>
          </logic:equal>
          <logic:notEqual value="0" name="tcd060Form" property="ttiUseSetFlg">
            <div class="fs_13">
              <logic:equal value="1" name="tcd060Form" property="ttiUseSetFlg">
                <gsmsg:write key="tcd.tcd060.14" />
              </logic:equal>
              <logic:notEqual value="1" name="tcd060Form" property="ttiUseSetFlg">
                <gsmsg:write key="tcd.tcd060.15" /><br>
                <gsmsg:write key="tcd.tcd060.16" /><a href="#!" onClick="buttonPush('tcd130');"><gsmsg:write key="tcd.tcd060.17" /></a><gsmsg:write key="tcd.tcd060.18" />
              </logic:notEqual>
            </div>
          </logic:notEqual>
        </td>
      </tr>

      <tr>
        <th>
          <gsmsg:write key="tcd.tcd060.07" />
        </th>
        <td>
          <div class="fs_13 mb5"><gsmsg:write key="tcd.tcd060.11" /></div>
          <span  class="verAlignMid">
            <html:radio styleId="tcd060Holiday_0" name="tcd060Form" property="tcd060Holiday" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOLKBN_WEEKDAY) %>" /><label for="tcd060Holiday_0"><gsmsg:write key="tcd.tcd060.12" /></label>
            <html:radio styleId="tcd060Holiday_1" styleClass="ml10" name="tcd060Form" property="tcd060Holiday" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.HOLKBN_HOLDAY) %>" /><label for="tcd060Holiday_1"><gsmsg:write key="tcd.tcd060.13" /></label>
          </span>
        </td>
      </tr>
    </table>

    <table class="w100" >
    <tr>

    <td class="w50">
      <table class="tl0 w99 table-top">
 <!--通常時間帯-->
      <tr>
        <td class="bgc_timecard-normal fw_b">
          <div class="component_bothEnd">
            <gsmsg:write key="tcd.tcd060.01" />
            <button type="button" class="baseBtn" name="btn_normal" value="<gsmsg:write key="rng.rng010.02" />" onClick="TimeAdd('1');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
          </div>
        </td>
      </tr>

      <logic:notEmpty name="tcd060Form" property="tcd060TimezoneMeiList" scope="request">
        <logic:iterate id="tzMdl" name="tcd060Form" property="tcd060TimezoneMeiList" scope="request" indexId="cnt1">
          <bean:define id="zoneNo" name="tzMdl" property="timeZoneNo"></bean:define>
          <bean:define id="zoneKbn" name="tzMdl" property="timeZoneKbn"></bean:define>
          <bean:define id="zoneTime" name="tzMdl" property="timeZoneStr"></bean:define>
          <logic:equal name="tzMdl" property="timeZoneKbn" value="1">
            <tr>
              <td class="td_type1" align="center">
                <a href="#!" onClick="TimeEdit(<%= zoneNo %>,'<%= zoneKbn%>','<%= zoneTime%>');">
                  <span class="text_link"><bean:write name="tzMdl" property="timeZoneStr" /></span>
                </a>

              </td>
            </tr>
          </logic:equal>
        </logic:iterate>
      </logic:notEmpty>

 <!--残業時間帯-->
      <tr>
      <td class="bgc_timecard-zangyo cl_fontOutline fw_b">
        <div class="component_bothEnd">
          <gsmsg:write key="tcd.tcd060.04" />
          <button type="button" class="baseBtn" name="btn_zangyo" value="<gsmsg:write key="rng.rng010.02" />" onClick="TimeAdd('2');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </div>
      </td>
      </tr>

<logic:notEmpty name="tcd060Form" property="tcd060TimezoneMeiList" scope="request">
          <logic:iterate id="tzMdl" name="tcd060Form" property="tcd060TimezoneMeiList" scope="request" indexId="cnt2">
            <bean:define id="zoneNo" name="tzMdl" property="timeZoneNo"></bean:define>
            <bean:define id="zoneKbn" name="tzMdl" property="timeZoneKbn"></bean:define>
            <bean:define id="zoneTime" name="tzMdl" property="timeZoneStr"></bean:define>
            <logic:equal name="tzMdl" property="timeZoneKbn" value="2">
              <tr>
              <td class="td_type1" align="center">
                <a href="#!" onClick="TimeEdit(<%= zoneNo %>,'<%= zoneKbn%>','<%= zoneTime%>');">
                  <span class="text_link"><bean:write name="tzMdl" property="timeZoneStr" /></span>
                </a>
              </td>
              </tr>
            </logic:equal>
          </logic:iterate>
        </logic:notEmpty>


 <!--休憩時間帯-->
      <tr>
      <td class="bgc_timecard-kyuukei fw_b">
        <div class="component_bothEnd">
          <gsmsg:write key="tcd.tcd060.05" />
          <button type="button" class="baseBtn" name="btn_kyuukei" value="<gsmsg:write key="rng.rng010.02" />" onClick="TimeAdd('4');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </div>
      </td>
      </tr>

<logic:notEmpty name="tcd060Form" property="tcd060TimezoneMeiList" scope="request">
          <logic:iterate id="tzMdl" name="tcd060Form" property="tcd060TimezoneMeiList" scope="request" indexId="cnt4">
            <bean:define id="zoneNo" name="tzMdl" property="timeZoneNo"></bean:define>
            <bean:define id="zoneKbn" name="tzMdl" property="timeZoneKbn"></bean:define>
            <bean:define id="zoneTime" name="tzMdl" property="timeZoneStr"></bean:define>
            <logic:equal name="tzMdl" property="timeZoneKbn" value="4">
              <tr>
                <td class="td_type1" align="center">
                  <a href="#!" onClick="TimeEdit(<%= zoneNo %>,'<%= zoneKbn%>','<%= zoneTime%>');">
                    <span class="text_link"><bean:write name="tzMdl" property="timeZoneStr" /></span>
                  </a>
                </td>
              </tr>
            </logic:equal>
          </logic:iterate>
        </logic:notEmpty>

</table>
</td>
<td class="w50 txt_t">
 <!--深夜残業-->
      <table class="tl0 w99 table-top ">
      <tr>
      <td class="bgc_timecard-sinya cl_fontOutline fw_b ">
        <div class="component_bothEnd"><gsmsg:write key="tcd.tcd060.03" />
          <button type="button" class="baseBtn" name="btn_shinya" value="<gsmsg:write key="rng.rng010.02" />" onClick="TimeAdd('3');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
            <gsmsg:write key="cmn.add" />
          </button>
        </div>
      </td>
      </tr>

      <logic:notEmpty name="tcd060Form" property="tcd060TimezoneMeiList" scope="request">
          <logic:iterate id="tzMdl" name="tcd060Form" property="tcd060TimezoneMeiList" scope="request" indexId="cnt3">
            <bean:define id="zoneNo" name="tzMdl" property="timeZoneNo"></bean:define>
            <bean:define id="zoneKbn" name="tzMdl" property="timeZoneKbn"></bean:define>
            <bean:define id="zoneTime" name="tzMdl" property="timeZoneStr"></bean:define>
            <logic:equal name="tzMdl" property="timeZoneKbn" value="3">
            <tr>
              <td class="td_type1" align="center">
                <a href="#!" onClick="TimeEdit(<%= zoneNo %>,'<%= zoneKbn%>','<%= zoneTime%>');">
                  <span class="text_link"><bean:write name="tzMdl" property="timeZoneStr" /></span>
                </a>
              </td>
            </tr>
            </logic:equal>
          </logic:iterate>
        </logic:notEmpty>
    </table>
    </td>
    </tr>
    </table>

    </table>
  </div>
</div>

<div class="footerBtn_block txt_r w80 mrl_auto">
  <button type="button" class="baseBtn" value="OK" onClick="buttonPush('tcd060_ok');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
    <gsmsg:write key="cmn.ok" />
  </button>
  <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd060_back');">
    <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
    <gsmsg:write key="cmn.back" />
  </button>
</div>

<div id="timezone_edit" class="" title="" style="display:none;">
  <div class="js_errMsg cl_fontWarn" style="font-size:14px;"></div>
  <table class="table-left w100 h80">
    <tr id="kbnDsp">
      <th class="txt_l w20">
        <gsmsg:write key="tcd.tcd070.03" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
        <td class="td_wt txt_l w80 p5">
          <input type="radio" name="tcd060TimeKbn" value="1" checked="checked" id="timeFlg_1">
          <label for="timeFlg_1"><span class="text_base6_2"><gsmsg:write key="tcd.tcd060.01" /></span></label>&nbsp;
          <input type="radio" name="tcd060TimeKbn" value="2"  id="timeFlg_2">
          <label for="timeFlg_2"><span class="text_base6_2"><gsmsg:write key="tcd.tcd060.04" /></span></label>
          <input type="radio" name="tcd060TimeKbn" value="4"  id="timeFlg_4">
          <label for="timeFlg_4"><span class="text_base6_2"><gsmsg:write key="tcd.tcd060.05" /></span></label>
        </td>
      </tr>
      <tr>
        <th class="table_bg_A5B4E1 txt_l w20">
          <gsmsg:write key="tcd.184" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
        </th>
        <td class="td_wt txt_l w80 p5">
          <span class="text_bb1 ml10"><gsmsg:write key="cmn.start" /><gsmsg:write key="wml.215" /></span>
          <!-- 時 -->
          <logic:notEmpty name="tcd060Form" property="tcd060HourLabel" scope="request">
            <html:select property="tcd060FrH" styleId="Fr_h">
              <html:optionsCollection name="tcd060Form" property="tcd060HourLabel" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.hour.input" />
          <!-- 分 -->
          <logic:notEmpty name="tcd060Form" property="tcd060MinuteLabel" scope="request">
          <html:select property="tcd060FrM" styleId="Fr_m">
            <html:optionsCollection name="tcd060Form" property="tcd060MinuteLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.minute.input" />
          <span class="text_bb1 ml10"><gsmsg:write key="cmn.end" /><gsmsg:write key="wml.215" /></span>
          <!-- 時 -->
          <logic:notEmpty name="tcd060Form" property="tcd060HourLabel" scope="request">
          <html:select property="tcd060ToH"  styleId="To_h">
            <html:optionsCollection name="tcd060Form" property="tcd060HourLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.hour.input" />
          <!-- 分 -->
          <logic:notEmpty name="tcd060Form" property="tcd060MinuteLabel" scope="request">
          <html:select property="tcd060ToM"  styleId="To_m">
            <html:optionsCollection name="tcd060Form" property="tcd060MinuteLabel" value="value" label="label" />
          </html:select>
          </logic:notEmpty>
          <gsmsg:write key="cmn.minute.input" />
        </td>
      </tr>
  </table>

</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>