<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" />[<gsmsg:write key="cmn.weeks" />]</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../reserve/js/rsv010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/sisetuPopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/calendar.js?<%= GSConst.VERSION_PARAM %>"></script>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../common/css/default.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../reserve/css/reserve.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
</head>

<body class="body_03" onunload="windowClose();calWindowClose();">
<html:form action="/reserve/rsv190">
<input type="hidden" name="CMD" value="">
<html:hidden property="rsvDspFrom" />
<html:hidden property="rsvSelectedSisetuSid" />
<html:hidden property="rsvSelectedYoyakuSid" />
<html:hidden property="rsvSelectedDate" />

<table width="100%">
<tr>
<td width="100%" align="center">
  <table width="100%" class="tl0">
  <tr>
  <td colspan="8">
    <table class="w100">
    <tr>
    <td width="0%">
      <img src="../reserve/images/header_reserve_01.gif" border="0" alt="<gsmsg:write key="cmn.reserve" />"></td>
    <td width="0%" class="header_white_bg_text txt_r txt_m no_w"><span class="plugin_ttl"><gsmsg:write key="cmn.reserve" /></span></td>
    <td width="0%" class="header_white_bg_text no_w">[ <gsmsg:write key="cmn.weekly" /> ]</td>
    <td width="100%" class="header_white_bg">
    <input type="button" value="<gsmsg:write key="cmn.close" />" class="btn_base1" onClick="window.close();">
    </td>
    <td width="0%">
      <img src="../common/images/header_white_end.gif" border="0" alt=""></td>
    </tr>
    </table>
  </td>
  </tr>
  <tr>
  <td colspan="8" class="td_type0">
    <table width="100%" class="tl0" border="0">
    <tr>
    <td width="20%" align="left">
      <table class="border_none">
      <tr>
      <td class="no_w">
        <span class="text_bb1"><gsmsg:write key="cmn.group" />：</span>
        <html:select property="rsvSelectedGrpSid" styleClass="select01" onchange="changeGroupCombo();">
          <html:optionsCollection name="rsv190Form" property="rsvGrpLabelList" value="value" label="label" />
        </html:select>
      </td>
      </tr>
      </table>
    </td>
    <td class="w40 txt_r">&nbsp;</td>
    <td class="w40 txt_c no_w">&nbsp;</td>
    <td class="w0 txt_r no_w">
      <input type="button" class="btn_arrow1_l" value="&nbsp;" onclick="buttonPush('zensyu_ido');">
      <input type="button" class="btn_arrow_l" value="&nbsp;" onclick="buttonPush('zenzitu_ido');">
      <input type="button" class="btn_today" value="<gsmsg:write key="cmn.today" />" onClick="buttonPush('kyo');">
      <input type="button" class="btn_arrow_r" value="&nbsp;" onclick="buttonPush('yokuzitu_ido')">
      <input type="button" class="btn_arrow1_r" value="&nbsp;" onclick="buttonPush('yokusyu_ido');">

    </td>
    <td class="w0 txt_r no_w>
      <input type="button" value="Cal" onclick="wrtCalendar(this.form.rsvDspFrom)" class="calendar_btn2">
    </td>
    </tr>
    </table>
  </td>
  </tr>
  <tr>
  <th class="txt_r" colspan="8" >
    <table class="w100">
    <tr>
    <th class="w100 txt_l no_w>
      <span class="text_tl1"><font color="ffffff"><bean:write name="rsv190Form" property="rsvDispYm" /></font></span>
    </th>
    </tr>
    </table>
  </th>
  </tr>

  <tr>
  <th width="16%" class="td_type3" style="padding:5px 0px 5px 0px;"><span class="sc_ttl_def"><gsmsg:write key="cmn.facility.name" /></span></th>

  <logic:empty name="rsv190Form" property="rsv010SisetuList" scope="request">
    <bean:define id="daylyMoveFlg" value="0" />
  </logic:empty>
  <logic:notEmpty name="rsv190Form" property="rsv010SisetuList" scope="request">
    <bean:define id="daylyMoveFlg" value="1" />
  </logic:notEmpty>

    <logic:notEmpty name="rsv190Form" property="rsv010CalendarList" scope="request">
      <logic:iterate id="calBean" name="rsv190Form" property="rsv010CalendarList" scope="request">

        <logic:equal name="calBean" property="holidayKbn" value="1">
          <th width="12%" class="td_type3 no_w">
              <span class="sc_ttl_sun"><bean:write name="calBean" property="dspDayString" /></span>
          </th>
        </logic:equal>

       <logic:notEqual name="calBean" property="holidayKbn" value="1">
         <logic:equal name="calBean" property="weekKbn" value="1">
           <th width="12%" class="td_type3 no_w">
               <span class="sc_ttl_sun"><bean:write name="calBean" property="dspDayString" /></span>
           </th>
         </logic:equal>

         <logic:equal name="calBean" property="weekKbn" value="7">
           <th width="12%" class="td_type3 no_w">
               <span class="sc_ttl_sat"><bean:write name="calBean" property="dspDayString" /></span>
           </th>
         </logic:equal>

         <logic:notEqual name="calBean" property="weekKbn" value="1">
           <logic:notEqual name="calBean" property="weekKbn" value="7">
             <th width="12%" class="td_type3 no_w">
                 <span class="sc_ttl_def"><bean:write name="calBean" property="dspDayString" /></span>
             </th>
           </logic:notEqual>
         </logic:notEqual>

       </logic:notEqual>

     </logic:iterate>
   </logic:notEmpty>

  </tr>

  <logic:notEmpty name="rsv190Form" property="rsv010SisetuList" scope="request">
    <logic:iterate id="sisetu" name="rsv190Form" property="rsv010SisetuList" scope="request">

      <tr align="left" valign="top">
      <td class="borderBlock-white">
        <span><bean:write name="sisetu" property="rsdName" /></span><br>
        <span id="rt">
        </span>
      </td>

      <logic:notEmpty name="sisetu" property="rsvWeekList">
        <logic:iterate id="week" name="sisetu" property="rsvWeekList">

          <logic:notEmpty name="week" property="yoyakuDayList">
            <logic:iterate id="day" name="week" property="yoyakuDayList">

              <logic:equal name="day" property="yrkYobi" value="1">
                <td align="left" valign="top" class="borderBlock-pink">
              </logic:equal>
              <logic:equal name="day" property="yrkYobi" value="7">
                <td align="left" valign="top" class="td_type8">
              </logic:equal>

              <logic:notEqual name="day" property="yrkYobi" value="1">
                <logic:notEqual name="day" property="yrkYobi" value="7">
                  <td align="left" valign="top" class="borderBlock-white">
                </logic:notEqual>
              </logic:notEqual>

              <logic:notEmpty name="day" property="holName"><span id="rt"><font size="-2" color="#ff0000"><bean:write name="day" property="holName" /></font></span></logic:notEmpty>
              <br>

              <logic:notEmpty name="day" property="yoyakuList">
                <logic:iterate id="yrk" name="day" property="yoyakuList" indexId="idx">
                  <bean:define id="index" value="<%= String.valueOf(((Integer) idx).intValue()) %>" />
                  <logic:notEqual name="index" value="0"><br><img src="../common/images/spacer.gif" width="1px" height="10px" border="0"><br></logic:notEqual>

                    <logic:notEmpty name="yrk" property="yrkRiyoDateStr"><span class="rsv_sc_link_s"><bean:write name="yrk" property="yrkRiyoDateStr" /></span><br></logic:notEmpty>
                    <span class="rsv_sc_link_1">
                      <logic:notEmpty name="yrk" property="rsyMok"><bean:write name="yrk" property="rsyMok" /></logic:notEmpty>
                      <logic:notEmpty name="yrk" property="rsyMok">
                        <logic:notEmpty name="yrk" property="yrkName">&nbsp;/&nbsp;</logic:notEmpty>
                      </logic:notEmpty>
                      <logic:notEmpty name="yrk" property="yrkName"><bean:write name="yrk" property="yrkName" /></logic:notEmpty>
                    </span>

                </logic:iterate>
              </logic:notEmpty>
            </td>

            </logic:iterate>
          </logic:notEmpty>

         </tr>

        </logic:iterate>
      </logic:notEmpty>

    </logic:iterate>
  </logic:notEmpty>

  </table>

</td>
</tr>
</table>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
</body>
</html:html>