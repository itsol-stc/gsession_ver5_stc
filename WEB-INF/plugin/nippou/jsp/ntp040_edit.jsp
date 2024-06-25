<%@page import="jp.groupsession.v2.ntp.ntp040.Ntp040Form"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.usr.UserUtil" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<gsjsmsg:js filename="gsjsmsg.js"/>
<% String tdColor = request.getParameter("tdColor");%>
<% String maxLengthContent = request.getParameter("maxLengthContent"); %>
<% String maxLengthBiko = request.getParameter("maxLengthBiko"); %>

<html:hidden property="ntp040FrYear" />
<html:hidden property="ntp040FrMonth" />
<html:hidden property="ntp040FrDay" />
<% String selYearIdStr = ""; %>
<% String selMonthIdStr = ""; %>
<% String selDayIdStr = ""; %>
<% String selActionYearIdStr = ""; %>
<% String selActionMonthIdStr = ""; %>
<% String selActionDayIdStr = ""; %>
<% Integer lastRowNumber = 0; %>
<bean:define id="colormsg1" value=""/>
<bean:define id="colormsg2" value=""/>
<bean:define id="colormsg3" value=""/>
<bean:define id="colormsg4" value=""/>
<bean:define id="colormsg5" value=""/>
<logic:iterate id="mstr" name="ntp040Form" property="ntp040ColorMsgList" indexId="mId" type="java.lang.String">
<logic:equal name="mId" value="0">
<% colormsg1 = mstr; %>
</logic:equal>
<logic:equal name="mId" value="1">
<% colormsg2 = mstr; %>
</logic:equal>
<logic:equal name="mId" value="2">
<% colormsg3 = mstr; %>
</logic:equal>
<logic:equal name="mId" value="3">
<% colormsg4 = mstr; %>
</logic:equal>
<logic:equal name="mId" value="4">
<% colormsg5 = mstr; %>
</logic:equal>
</logic:iterate>
<input type="hidden" id="msgCol1" value="<%= colormsg1 %>" />
<input type="hidden" id="msgCol2" value="<%= colormsg2 %>" />
<input type="hidden" id="msgCol3" value="<%= colormsg3 %>" />
<input type="hidden" id="msgCol4" value="<%= colormsg4 %>" />
<input type="hidden" id="msgCol5" value="<%= colormsg5 %>" />
<logic:notEmpty name="ntp040Form" property="ntp040DataModelList">
  <logic:iterate id="dataMdl" name="ntp040Form" property="ntp040DataModelList"  indexId="idx" type="jp.groupsession.v2.ntp.ntp040.model.Ntp040DataModel">
    <% lastRowNumber =  idx + 1; %>
    <bean:define id="datafrhourval" name="dataMdl" property="frHour" />
    <bean:define id="datafrminval" name="dataMdl" property="frMin"/>
    <bean:define id="datatohourval" name="dataMdl" property="toHour"/>
    <bean:define id="datatominval" name="dataMdl" property="toMin"/>
    <table id="nippou_data_<%= idx + 1 %>" class="table-left w100 js_nippouData_<%= idx + 1 %>">
      <tr id="<%= idx + 1 %>" class="js_headNippou">
        <th colspan="4" class="bgC_header1 js_ntpInfoNum w100" id="pos<%= idx + 1 %>">
          <logic:equal name="dataMdl" property="ntp040SelectFlg" value="1">
            <div id="initSelect"></div>
          </logic:equal>
          <div class="w100 verAlignMid hp30">
            <img class="btn_classicImg-display" src="../nippou/images/classic/icon_menu_single.gif">
            <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png">
            <span class="js_nippouRowNumber cl_fontOutline">NO.<%= idx + 1 %></span>
            <div class="js_nipHeader_<%= idx + 1 %> w100 txt_r" id="<bean:write name="dataMdl" property="ntp040NtpSid" />">
              <logic:equal name="dataMdl" property="ankenViewable" value="true">
                <button type="button" class="baseBtn js_ntpCopyBtn js_editButtonArea<%= idx + 1 %>" value="<gsmsg:write key="cmn.register.copy2" />" id="<bean:write name="dataMdl" property="ntp040NtpSid" />">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_copy_add.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_copy.png" alt="<gsmsg:write key="cmn.register.copy2" />">
                  <gsmsg:write key="cmn.register.copy2" />
                </button>
              </logic:equal>
              <logic:equal name="ntp040Form" property="authAddEditKbn" value="0">
                <logic:equal name="dataMdl" property="ankenViewable" value="true">
                  <button type="button" class="baseBtn js_ntpEditBtn js_editButtonArea<%= idx + 1 %>" id="<%= idx + 1 %>" value="<gsmsg:write key="cmn.edit" />">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit"/>">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                    <gsmsg:write key="cmn.edit" />
                  </button>
                  <button type="button" class="baseBtn js_ntpDellBtn js_editButtonArea<%= idx + 1 %>" value="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                    <gsmsg:write key="cmn.delete" />
                  </button>
                  <button type="button" class="baseBtn js_ntpEditKakuteiBtn js_editButtonArea<%= idx + 1 %> display_n" id="<%= idx + 1 %>" value="<gsmsg:write key="cmn.final" />">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.final"/>">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.final" />">
                    <gsmsg:write key="cmn.final" />
                  </button>
                  <button type="button" class="baseBtn js_ntpEditCancelBtn js_editButtonArea<%= idx + 1 %> display_n" id="<%= idx + 1 %>" value="<gsmsg:write key="cmn.cancel" />">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.cancel" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.cancel" />">
                    <gsmsg:write key="cmn.cancel" />
                  </button>
                </logic:equal>
              </logic:equal>
            </div>
          </div>
        </th>
      </tr>
      <tr class="js_usrInfArea<%= idx + 1 %>">
        <td class="txt_t w100" colspan="4">
          <table class="w100 table-noBorder">
            <tr>
              <td>
                <logic:equal name="ntp040Form" property="ntp040UsrBinSid" value="0">
                  <logic:notEqual name="ntp040Form" property="ntp040UsrPctKbn" value="1">
                    <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                    <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                  </logic:notEqual>
                  <logic:equal name="ntp040Form" property="ntp040UsrPctKbn" value="1">
                    <div align="center" class="hikokai_photo-m">
                      <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                    </div>
                  </logic:equal>
                </logic:equal>
                <logic:notEqual name="ntp040Form" property="ntp040UsrBinSid" value="0">
                  <logic:equal name="ntp040Form" property="ntp040UsrPctKbn" value="1">
                    <div align="center" class="hikokai_photo-m">
                      <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                    </div>
                  </logic:equal>
                  <logic:notEqual name="ntp040Form" property="ntp040UsrPctKbn" value="1">
                    <img class="wp50" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="ntp040Form" property="ntp040UsrBinSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                  </logic:notEqual>
                </logic:notEqual>
              </td>
              <td class="w100">
                <bean:define id="ukoFlg" name="ntp040Form" property="ntp040UsrUkoFlg" type="java.lang.Integer"/>
                <div class="lh100 fw_b <%= UserUtil.getCSSClassNameNormal(ukoFlg) %>">
                  <bean:write name="ntp040Form" property="ntp040UsrName" />
                </div>
                <div>
                  <span class="js_dspFrhour_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspFrHour" /></span>
                  <gsmsg:write key="cmn.hour.input" />
                  <span class="js_dspFrminute_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspFrMinute"/></span>
                  <gsmsg:write key="cmn.minute.input" />
                  ～
                  <span class="js_dspTohour_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspToHour"/></span>
                  <gsmsg:write key="cmn.hour.input" />
                  <span class="js_dspTominute_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspToMinute"/></span>
                  <gsmsg:write key="cmn.minute.input" />
                  <span id="betWeenDays"></span>
                </div>
                <div class="fw_b fs_16 mt5 js_dspTitle_<%= idx + 1 %>">
                  <span class="dsp_title_<%= idx + 1 %>"><bean:write name="dataMdl" property="title" /></span>
                </div>
              </td>
            </tr>
          </table>
        </td>
      </tr>
      <jsp:include page="/WEB-INF/plugin/nippou/jsp/ntp040_edit_contents.jsp">
        <jsp:param value="<%= idx %>" name="idx"/>
      </jsp:include>
    </table>
  </logic:iterate>
</logic:notEmpty>
<logic:empty name="ntp040Form" property="ntp040DataModelList">
  <div id="ntpEmptyArea" class="js_ntpEmptyArea txt_c">
    <div class="w100 p10 cl_fontWarn fw_b">該当する日報がありません。</div>
  </div>
</logic:empty>
<input type="hidden" id="editLastRowNum" value="<%= lastRowNumber %>" />