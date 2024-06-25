<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.ntp.ntp040.Ntp040Form"%>
<%@ page import="jp.groupsession.v2.ntp.ntp040.model.Ntp040DataModel"%>
<%@ page import="jp.groupsession.v2.usr.UserUtil" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<% String tdColor = request.getParameter("tdColor");%>
<% String maxLengthContent = request.getParameter("maxLengthContent"); %>
<% String maxLengthBiko = request.getParameter("maxLengthBiko"); %>
<% String selYearIdStr = ""; %>
<% String selMonthIdStr = ""; %>
<% String selDayIdStr = ""; %>
<% String selDateIdStr = ""; %>
<% String selActionYearIdStr = ""; %>
<% String selActionMonthIdStr = ""; %>
<% String selActionDayIdStr = ""; %>
<% String selActionDateIdStr = ""; %>
<% Integer lastRowNumber = 0; %>
<%
    Ntp040Form thisForm = (Ntp040Form) request.getAttribute("ntp040Form");
    int idx = Integer.parseInt(request.getParameter("idx"));
    Ntp040DataModel dataMdl = thisForm.getNtp040DataModelList().get(idx);
    pageContext.setAttribute("dataMdl", dataMdl);
%>
 <% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE); %>
<bean:define id="colormsg1" value=""/>
<bean:define id="colormsg2" value=""/>
<bean:define id="colormsg3" value=""/>
<bean:define id="colormsg4" value=""/>
<bean:define id="colormsg5" value=""/>
<bean:define id="datafrhourval" name="dataMdl" property="frHour" />
<bean:define id="datafrminval" name="dataMdl" property="frMin"/>
<bean:define id="datatohourval" name="dataMdl" property="toHour"/>
<bean:define id="datatominval" name="dataMdl" property="toMin"/>
<bean:define id="datafrtimeval" name="dataMdl" property="frTime"/>
<bean:define id="datatotimeval" name="dataMdl" property="toTime"/>
<tr class="js_titleArea<%= idx + 1 %> display_n">
  <th class="w20" >
    <gsmsg:write key="cmn.title" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
  </th>
  <td class="w80" colspan="3">
    <div class="verAlignMid">
      <input name="ntp040Title_<%= idx + 1 %>" maxlength="100" value="<bean:write name="dataMdl" property="title" />" id="ntpTitleTextBox" class="wp300" type="text">
      <logic:equal name="dataMdl" property="bgcolor" value="1">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleBlue ml10"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="1" checked="checked" id="bg_color1_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color1_<%= idx + 1 %>" class="no_w mr10"><%= colormsg1 %></label>
        </div>
        <wbr>
      </logic:equal>
      <logic:notEqual name="dataMdl" property="bgcolor" value="1">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleBlue ml10"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="1" id="bg_color1_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color1_<%= idx + 1 %>" class="no_w mr10"><%= colormsg1 %></label>
        </div>
        <wbr>
      </logic:notEqual>
      <logic:equal name="dataMdl" property="bgcolor" value="2">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleRed"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="2" checked="checked" id="bg_color2_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color2_<%= idx + 1 %>" class="no_w mr10"><%= colormsg2 %></label>
        </div>
        <wbr>
      </logic:equal>
      <logic:notEqual name="dataMdl" property="bgcolor" value="2">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleRed"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="2" id="bg_color2_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color2_<%= idx + 1 %>" class="no_w mr10"><%= colormsg2 %></label>
        </div>
        <wbr>
      </logic:notEqual>
      <logic:equal name="dataMdl" property="bgcolor" value="3">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleGreen"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="3" checked="checked" id="bg_color3_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color3_<%= idx + 1 %>" class="no_w mr10"><%= colormsg3 %></label>
        </div>
        <wbr>
      </logic:equal>
      <logic:notEqual name="dataMdl" property="bgcolor" value="3">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleGreen"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="3" id="bg_color3_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color3_<%= idx + 1 %>" class="no_w mr10"><%= colormsg3 %></label>
        </div>
        <wbr>
      </logic:notEqual>
      <logic:equal name="dataMdl" property="bgcolor" value="4">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleYellow"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="4" checked="checked" id="bg_color4_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color4_<%= idx + 1 %>" class="no_w mr10"><%= colormsg4 %></label>
        </div>
        <wbr>
      </logic:equal>
      <logic:notEqual name="dataMdl" property="bgcolor" value="4">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleYellow"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="4" id="bg_color4_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color4_<%= idx + 1 %>" class="no_w mr10"><%= colormsg4 %></label>
        </div>
        <wbr>
      </logic:notEqual>
      <logic:equal name="dataMdl" property="bgcolor" value="5">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleBlack"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="5" checked="checked" id="bg_color5_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color5_<%= idx + 1 %>" class="no_w mr10"><%= colormsg5 %></label>
        </div>
        <wbr>
      </logic:equal>
      <logic:notEqual name="dataMdl" property="bgcolor" value="5">
        <div class="verAlignMid">
          <span class="ntp_titlecolor-block bgc_fontSchTitleBlack"><input name="ntp040Bgcolor_<%= idx + 1 %>" value="5" id="bg_color5_<%= idx + 1 %>" type="radio"></span>
          <label for="bg_color5_<%= idx + 1 %>" class="no_w mr10"><%= colormsg5 %></label>
        </div>
        <wbr>
      </logic:notEqual>
    </div>
  </td>
</tr>
<tr class="js_ntpDateAreaTr<%= idx + 1 %> display_n">
  <th class="w20">
    <gsmsg:write key="ntp.35" />
  </th>
  <td class="w80" colspan="3">
    <% selYearIdStr  = "selYear"  + String.valueOf(idx + 1); %>
    <% selMonthIdStr = "selMonth" + String.valueOf(idx + 1); %>
    <% selDayIdStr   = "selDay"   + String.valueOf(idx + 1); %>
    <% selDateIdStr   = "selDate"   + String.valueOf(idx + 1); %>
    <bean:define id="dataYear" name="dataMdl" property="ntpYear" type="java.lang.Integer"/>
    <bean:define id="dataMonth" name="dataMdl" property="ntpMonth" type="java.lang.Integer"/>
    <bean:define id="dataDay" name="dataMdl" property="ntpDay" type="java.lang.Integer"/>
    <bean:define id="dataDate" name="dataMdl" property="ntpDate" type="java.lang.String"/>
    
    <div class="verAlignMid w100">
      <input type="text" name="<%= selDateIdStr %>" id="<%= selDateIdStr.toString() %>" maxlength="10" class="txt_c wp95 datepicker js_frDatePicker" value="<%= dataDate %>" />
      <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
      <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#<%= selDateIdStr %>')[0], 1);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
        <i class="icon-paging_left "></i>
      </button>
      <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#<%= selDateIdStr %>')[0], 2);">
        <gsmsg:write key="cmn.today" />
      </button>
      <span>
        <a class="fw_b todayBtn original-display" onClick="return moveDay($('#<%= selDateIdStr %>')[0], 2);">
          <gsmsg:write key="cmn.today" />
        </a>
      </span>
      <button type="button" class="webIconBtn" onClick="return moveDay($('#<%= selDateIdStr %>')[0], 3);">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
        <i class="icon-paging_right "></i>
      </button>
    </div>
  </td>
</tr>
<tr class="js_ntpTimeArea<%= idx + 1 %> display_n">
  <th class="w20">
    <gsmsg:write key="cmn.time" />
  </th>
  <td class="w80" colspan="3">
    <span class="display_flex">
      <% String ntp040FrTime = "ntp040FrTime_" + (idx + 1); %>
      <% String frTimeId = "fr_clock_" + (idx + 1); %>
      <span class="clockpicker_fr mr5 pos_rel display_flex input-group">
        <input type="text" name="<%= ntp040FrTime %>" id="<%= frTimeId %>" maxlength="5" class="clockpicker js_clockpicker txt_c wp60" value="<%= String.valueOf(datafrtimeval) %>"/>
        <label for="<%= frTimeId %>" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
      </span>
      ～
      <% String ntp040ToTime = "ntp040ToTime_" + (idx + 1); %>
      <% String toTimeId = "to_clock_" + (idx + 1); %>
      <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
        <input type="text" name="<%= ntp040ToTime %>" id="<%= toTimeId %>" maxlength="5" class="clockpicker js_clockpicker txt_c wp60" value="<%= datatotimeval %>"/>
        <label for="<%= toTimeId %>" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
      </span>
    </span>
    <span id="betWeenDays"></span>
  </td>
</tr>
<logic:equal name="ntp040Form" property="ntp040AnkenCompanyUse" value="0">
  <tr>
    <th class="w20" >
      <gsmsg:write key="ntp.11" />
    </th>
    <td class="w30 txt_t">
      <div class="js_ankenDataArea<%= idx + 1 %>">
        <logic:notEmpty name="dataMdl" property="ankenSid">
          <div class="ntp040AnkenCodeDspArea_<%= idx + 1 %>">案件コード：<bean:write name="dataMdl" property="ankenCode" /></div>
        </logic:notEmpty>
        <logic:notEmpty name="dataMdl" property="ankenSid">
          <logic:notEmpty name="dataMdl" property="ankenName">
            <div class="ntp040AnkenNameDspArea_<%= idx + 1 %>">
              <a id="<bean:write name="dataMdl" property="ankenSid" />" class="js_anken_click cl_linkDef mr5">
                <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken_18.png">
                <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                <span class="anken_name_<%= idx + 1 %> ml5">
                  <bean:write name="dataMdl" property="ankenName" />
                </span>
              </a>
            </div>
          </logic:notEmpty>
          <logic:empty name="dataMdl" property="ankenName">
            <div class="ntp040AnkenNameDspArea_<%= idx + 1 %>">
            </div>
          </logic:empty>
        </logic:notEmpty>
      </div>
      <div class="js_ankenDataArea<%= idx + 1 %> display_n">
        <button type="button" class="baseBtn" onclick="return openAnkenWindow('ntp040','<%= idx + 1 %>')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />">
          <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
        </button>
        <button type="button" class="baseBtn js_ankenHistoryPop" id="<%= idx + 1 %>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
          <gsmsg:write key="ntp.17" />
        </button>
        <div id="ntp040AnkenIdArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="ankenSid">
            <input name="ntp040AnkenSid_<%= idx + 1 %>" value="<bean:write name="dataMdl" property="ankenSid" />" type="hidden">
          </logic:notEmpty>
        </div>
        <div id="ntp040AnkenCodeArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="ankenCode">
            案件コード：<bean:write name="dataMdl" property="ankenCode" />
          </logic:notEmpty>
        </div>
        <logic:notEmpty name="dataMdl" property="ankenSid">
          <logic:notEmpty name="dataMdl" property="ankenName">
            <div id="ntp040AnkenNameArea_<%= idx + 1 %>">
              <a id="<bean:write name="dataMdl" property="ankenSid" />" class="js_anken_click cl_linkDef mr5">
                <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken_18.png">
                <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                <span class="anken_name_<%= idx + 1 %> ml5">
                  <bean:write name="dataMdl" property="ankenName" />
                </span>
              </a>
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif"  onclick="delAnken('ntp040','_<%= idx + 1 %>');">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png"  onclick="delAnken('ntp040','_<%= idx + 1 %>');">
            </div>
          </logic:notEmpty>
          <logic:empty name="dataMdl" property="ankenName">
            <div id="ntp040AnkenNameArea_<%= idx + 1 %>">
            </div>
          </logic:empty>
        </logic:notEmpty>
      </div>
    </td>
    <th class="w20">
      企業・顧客
    </th>
    <td class="w30 txt_t">
      <div class="js_kigyouDataArea<%= idx + 1 %>">
        <logic:notEmpty name="dataMdl" property="companySid">
          <div class="js_ntp040CompCodeDspArea_<%= idx + 1 %>">企業コード：<bean:write name="dataMdl" property="companyCode" /></div>
        </logic:notEmpty>
        <logic:notEmpty name="dataMdl" property="companyName">
          <div class="js_ntp040CompNameDspArea_<%= idx + 1 %>">
            <a id="<bean:write name="dataMdl" property="companySid" />" class="js_compClick mr5 cl_linkDef">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
              <bean:write name="dataMdl" property="companyName" />
              <logic:notEmpty name="dataMdl" property="companySid">
                <span class="ml5"><bean:write name="dataMdl" property="companyBaseName" /></span>
              </logic:notEmpty>
            </a>
          </div>
        </logic:notEmpty>
        <logic:empty name="dataMdl" property="companyName">
          <div class="js_ntp040CompNameDspArea_<%= idx + 1 %>">
          </div>
        </logic:empty>
      </div>
      <div class="js_kigyouDataArea<%= idx + 1 %> display_n">
        <button type="button" class="baseBtn" onclick="return openCompanyWindow2('ntp040',<%= idx + 1 %>)">
          <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="addressbook" />
        </button>
        <button type="button" class="baseBtn js_adrHistoryPop" id="<%= idx + 1 %>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
          <gsmsg:write key="ntp.17" />
        </button>
        <div id="ntp040CompanyIdArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companySid">
            <input name="ntp040CompanySid_<%= idx + 1 %>" value="<bean:write name="dataMdl" property="companySid" />" type="hidden">
          </logic:notEmpty>
        </div>
        <div id="ntp040CompanyBaseIdArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companyBaseSid">
            <input name="ntp040CompanyBaseSid_<%= idx + 1 %>" value="<bean:write name="dataMdl" property="companyBaseSid" />" type="hidden">
          </logic:notEmpty>
        </div>
        <div id="ntp040CompanyCodeArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companyCode">
            企業コード：<bean:write name="dataMdl" property="companyCode" />
          </logic:notEmpty>
        </div>
        <div id="ntp040CompNameArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companyName">
            <div>
              <a id="<bean:write name="dataMdl" property="companySid" />" class="js_compClick mr5 cl_linkDef">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
                <bean:write name="dataMdl" property="companyName" />
                <logic:notEmpty name="dataMdl" property="companySid">
                  <span class="ml5"><bean:write name="dataMdl" property="companyBaseName" /></span>
                </logic:notEmpty>
              </a>
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif"  onclick="delCompany('ntp040','_<%= idx + 1 %>');">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png"  onclick="delCompany('ntp040','_<%= idx + 1 %>');">
            </div>
          </logic:notEmpty>
        </div>
        <div id="ntp040AddressIdArea_<%= idx + 1 %>">
        </div>
        <div id="ntp040AddressNameArea_<%= idx + 1 %>">
        </div>
      </div>
    </td>
  </tr>
</logic:equal>
<logic:equal name="ntp040Form" property="ntp040AnkenCompanyUse" value="2">
  <tr>
    <th class="w20" >
      <gsmsg:write key="ntp.11" />
    </th>
    <td class="w80 txt_t" colspan="3">
      <div class="js_ankenDataArea<%= idx + 1 %>">
        <logic:notEmpty name="dataMdl" property="ankenSid">
          <div class="ntp040AnkenCodeDspArea_<%= idx + 1 %>">案件コード：<bean:write name="dataMdl" property="ankenCode" /></div>
        </logic:notEmpty>
        <logic:notEmpty name="dataMdl" property="ankenSid">
          <logic:notEmpty name="dataMdl" property="ankenName">
            <div class="ntp040AnkenNameDspArea_<%= idx + 1 %>">
              <a id="<bean:write name="dataMdl" property="ankenSid" />" class="js_anken_click cl_linkDef mr5">
                <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken_18.png">
                <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                <span class="anken_name_<%= idx + 1 %> ml5">
                  <bean:write name="dataMdl" property="ankenName" />
                </span>
              </a>
            </div>
          </logic:notEmpty>
          <logic:empty name="dataMdl" property="ankenName">
            <div class="ntp040AnkenNameDspArea_<%= idx + 1 %>">
            </div>
          </logic:empty>
        </logic:notEmpty>
      </div>
      <div class="js_ankenDataArea<%= idx + 1 %>" style="display:none;">
        <button type="button" class="baseBtn" onclick="return openAnkenWindow('ntp040','<%= idx + 1 %>')">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />">
          <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
        </button>
        <button type="button" class="baseBtn js_ankenHistoryPop" id="<%= idx + 1 %>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
          <gsmsg:write key="ntp.17" />
        </button>
        <div id="ntp040AnkenIdArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="ankenSid">
            <input name="ntp040AnkenSid_<%= idx + 1 %>" value="<bean:write name="dataMdl" property="ankenSid" />" type="hidden">
          </logic:notEmpty>
        </div>
        <div id="ntp040AnkenCodeArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="ankenCode">
            案件コード：<bean:write name="dataMdl" property="ankenCode" />
          </logic:notEmpty>
        </div>
        <logic:notEmpty name="dataMdl" property="ankenSid">
          <logic:notEmpty name="dataMdl" property="ankenName">
            <div id="ntp040AnkenNameArea_<%= idx + 1 %>">
              <a id="<bean:write name="dataMdl" property="ankenSid" />" class="js_anken_click cl_linkDef mr5">
                <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken_18.png">
                <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                <span class="anken_name_<%= idx + 1 %> ml5">
                  <bean:write name="dataMdl" property="ankenName" />
                </span>
              </a>
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif"  onclick="delAnken('ntp040','_<%= idx + 1 %>');">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png"  onclick="delAnken('ntp040','_<%= idx + 1 %>');">
            </div>
          </logic:notEmpty>
          <logic:empty name="dataMdl" property="ankenName">
            <div id="ntp040AnkenNameArea_<%= idx + 1 %>">
            </div>
          </logic:empty>
        </logic:notEmpty>
      </div>
    </td>
  </tr>
</logic:equal>
<logic:equal name="ntp040Form" property="ntp040AnkenCompanyUse" value="2">
  <tr>
    <th class="w20" >
      企業・顧客
    </th>
    <td class="w80 txt_t" colspan="3">
      <div class="js_kigyouDataArea<%= idx + 1 %>">
        <logic:notEmpty name="dataMdl" property="companySid">
          <div class="ntp040CompanyCodeDspArea_<%= idx + 1 %>">企業コード：<bean:write name="dataMdl" property="companyCode" /></div>
        </logic:notEmpty>
        <logic:notEmpty name="dataMdl" property="companyName">
          <div class="js_ntp040CompNameDspArea_<%= idx + 1 %>">
            <a id="<bean:write name="dataMdl" property="companySid" />" class="js_compClick mr5 cl_linkDef">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
              <bean:write name="dataMdl" property="ankenName" />
              <logic:notEmpty name="dataMdl" property="companySid">
                <span class="ml5"><bean:write name="dataMdl" property="companyBaseName" /></span>
              </logic:notEmpty>
            </a>
          </div>
        </logic:notEmpty>
        <logic:empty name="dataMdl" property="companyName">
          <div class="js_ntp040CompNameDspArea_<%= idx + 1 %>">
          </div>
        </logic:empty>
      </div>
      <div class="js_kigyouDataArea<%= idx + 1 %> display_n">
        <button type="button" class="baseBtn" onclick="return openCompanyWindow2('ntp040',<%= idx + 1 %>)">
          <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="addressbook" />
        </button>
        <button type="button" class="baseBtn js_adrHistoryPop" id="<%= idx + 1 %>">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
          <gsmsg:write key="ntp.17" />
        </button>
        <div id="ntp040CompanyIdArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companySid">
            <input name="ntp040CompanySid_<%= idx + 1 %>" value="<bean:write name="dataMdl" property="companySid" />" type="hidden">
          </logic:notEmpty>
        </div>
        <div id="ntp040CompanyBaseIdArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companyBaseSid">
            <input name="ntp040CompanyBaseSid_<%= idx + 1 %>" value="<bean:write name="dataMdl" property="companyBaseSid" />" type="hidden">
          </logic:notEmpty>
        </div>
        <div id="ntp040CompanyCodeArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companyCode">
            企業コード：<bean:write name="dataMdl" property="companyCode" />
          </logic:notEmpty>
        </div>
        <div id="ntp040CompNameArea_<%= idx + 1 %>">
          <logic:notEmpty name="dataMdl" property="companyName">
            <div>
              <a id="<bean:write name="dataMdl" property="companySid" />" class="js_compClick mr5 cl_linkDef">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
                <bean:write name="dataMdl" property="ankenName" />
                <logic:notEmpty name="dataMdl" property="companySid">
                  <span class="ml5"><bean:write name="dataMdl" property="companyBaseName" /></span>
                </logic:notEmpty>
              </a>
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif"  onclick="delCompany('ntp040','_<%= idx + 1 %>');">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png"  onclick="delCompany('ntp040','_<%= idx + 1 %>');">
            </div>
          </logic:notEmpty>
        </div>
        <div id="ntp040AddressIdArea_<%= idx + 1 %>">
        </div>
        <div id="ntp040AddressNameArea_<%= idx + 1 %>">
        </div>
      </div>
    </td>
  </tr>
</logic:equal>
<tr>
  <th class="w20">
    <gsmsg:write key="cmn.content" /><a id="naiyou" name="naiyou"></a>
  </th>
  <td class="w80" colspan="3">
    <div class="js_naiyouArea<%= idx + 1 %>">
      <span class="dsp_naiyou_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspValueStr" filter="false"/></span>
    </div>
    <div class="js_naiyouArea<%= idx + 1 %> display_n">
      <textarea id="inputstr_<%= idx + 1 %>" name="ntp040Value_<%= idx + 1 %>" class="w100" rows="5" onkeyup="showLengthStr(value, 1000, 'inputlength<%= idx + 1 %>');"><bean:write name="dataMdl" property="valueStr" /></textarea>
      <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength<%= idx + 1 %>" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </div>
  </td>
</tr>
<logic:equal name="ntp040Form" property="ntp040KtBriHhuUse" value="0">
  <tr>
    <th class="w20">
      活動分類/方法
    </th>
    <td class="w80" colspan="3">
      <bean:define id="ktbunruival" name="dataMdl" property="ktbunruiSid"/>
      <bean:define id="ktbouhouval" name="dataMdl" property="kthouhouSid"/>
      <div class="js_ktBunruiArea<%= idx + 1 %>">
        <span class="dsp_ktbunrui_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspKtbunrui"/></span>
        <span class="dsp_kthouhou_<%= idx + 1 %>" class="ml5"><bean:write name="dataMdl" property="ntp040DspKthouhou"/></span>
      </div>
      <div class="js_ktBunruiArea<%= idx + 1 %> display_n">
        <% String ntp040Ktbunrui = "ntp040Ktbunrui_" + (idx + 1); %>
        <logic:notEmpty name="ntp040Form" property="ntp040KtbunruiLavel">
          <html:select property="<%= ntp040Ktbunrui %>" value="<%= String.valueOf(ktbunruival) %>">
             <html:optionsCollection name="ntp040Form" property="ntp040KtbunruiLavel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        <logic:notEmpty name="ntp040Form" property="ntp040KthouhouLavel">
          <% String ntp040Kthouhou = "ntp040Kthouhou_" + (idx + 1); %>
          <html:select property="<%= ntp040Kthouhou %>" value="<%= String.valueOf(ktbouhouval) %>">
             <html:optionsCollection name="ntp040Form" property="ntp040KthouhouLavel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </div>
    </td>
  </tr>
</logic:equal>
<logic:equal name="ntp040Form" property="ntp040MikomidoUse" value="0">
  <tr>
    <th class="w20">
      見込み度
    </th>
    <td class="w80" colspan="3">
      <div class="js_mikomidoArea<%= idx + 1 %>">
        <span class="dsp_mikomido_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspMikomido"/></span>％
      </div>
      <div class="js_mikomidoArea<%= idx + 1 %> display_n">
        <div class="verAlignMid">
          <logic:equal name="dataMdl" property="mikomido" value="0">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="0" checked="checked" id="ntp040Mikomido0_<%= idx + 1 %>" type="radio"><label for="ntp040Mikomido0_<%= idx + 1 %>">10%</label>
          </logic:equal>
          <logic:notEqual name="dataMdl" property="mikomido" value="0">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="0" id="ntp040Mikomido0_<%= idx + 1 %>" type="radio"><label for="ntp040Mikomido0_<%= idx + 1 %>">10%</label>
          </logic:notEqual>
          <logic:equal name="dataMdl" property="mikomido" value="1">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="1" checked="checked" id="ntp040Mikomido1_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido1_<%= idx + 1 %>">30%</label>
          </logic:equal>
          <logic:notEqual name="dataMdl" property="mikomido" value="1">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="1" id="ntp040Mikomido1_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido1_<%= idx + 1 %>">30%</label>
          </logic:notEqual>
          <logic:equal name="dataMdl" property="mikomido" value="2">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="2" checked="checked" id="ntp040Mikomido2_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido2_<%= idx + 1 %>">50%</label>
          </logic:equal>
          <logic:notEqual name="dataMdl" property="mikomido" value="2">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="2" id="ntp040Mikomido2_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido2_<%= idx + 1 %>">50%</label>
          </logic:notEqual>
          <logic:equal name="dataMdl" property="mikomido" value="3">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="3" checked="checked" id="ntp040Mikomido3_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido3_<%= idx + 1 %>">70%</label>
          </logic:equal>
          <logic:notEqual name="dataMdl" property="mikomido" value="3">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="3" id="ntp040Mikomido3_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido3_<%= idx + 1 %>">70%</label>
          </logic:notEqual>
          <logic:equal name="dataMdl" property="mikomido" value="4">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="4" checked="checked" id="ntp040Mikomido4_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido4_<%= idx + 1 %>">100%</label>
          </logic:equal>
          <logic:notEqual name="dataMdl" property="mikomido" value="4">
            <input name="ntp040Mikomido_<%= idx + 1 %>" value="4" id="ntp040Mikomido4_<%= idx + 1 %>" type="radio" class="ml10"><label for="ntp040Mikomido4_<%= idx + 1 %>">100%</label>
          </logic:notEqual>
        </div>
        <logic:notEqual name="ntp040Form" property="ntp040MikomidoFlg" value="0">
          <div>
            <button type="button" class="baseBtn js_mikomidoBtn">
              <gsmsg:write key="tcd.tcd010.15" />
            </button>
          </div>
        </logic:notEqual>
      </div>
    </td>
  </tr>
</logic:equal>
<logic:equal name="ntp040Form" property="ntp040TmpFileUse" value="0">
  <tr>
    <th class="w20">
      <gsmsg:write key="cmn.attached" /><a id="naiyou" name="naiyou"></a>
    </th>
    <td class="w80" colspan="3">
      <div class="js_tempFileArea<%= idx + 1 %> dsp_tmp_file_area_<%= idx + 1 %>">
        <logic:notEmpty name="dataMdl" property="ntp040FileList">
          <logic:iterate id="tempMdl" name="dataMdl" property="ntp040FileList">
            <div>
            <a href="#!" onClick="return fileLinkClick(<bean:write name="dataMdl" property="ntp040NtpSid" />,<bean:write name="tempMdl" property="binSid"/>);"><bean:write name="tempMdl" property="binFileName"/><bean:write name="tempMdl" property="binFileSizeDsp" /></a>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </div>
      <div class="js_tempFileArea<%= idx + 1 %> display_n">
        <% String tmpDirPlus = "row" + String.valueOf(idx + 1); %>
        <attachmentFile:filearea
        mode="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE) %>"
        pluginId="<%= jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_ID_NIPPOU %>"
        tempDirId="ntp040"
        tempDirPlus="<%= tmpDirPlus %>"
        formId="<%= String.valueOf(idx + 1) %>" />
      </div>
    </td>
  </tr>
</logic:equal>
<logic:equal name="ntp040Form" property="ntp040NextActionUse" value="0">
  <tr>
    <th class="w20">
      <gsmsg:write key="ntp.96" /><a id="nextAction" name="nextAction"></a>
    </th>
    <td class="w80" colspan="3">
      <div class="js_nextActionArea<%= idx + 1 %>">
        <span id="actionSelDateArea_<%= idx + 1 %>">
          <logic:equal name="dataMdl" property="actDateKbn" value="1">
            <div>日付：
            <span class="dsp_actionyear_<%= idx + 1 %>"><bean:write name="dataMdl" property="actionYear"/></span>年
            <span class="dsp_actionmonth_<%= idx + 1 %>"><bean:write name="dataMdl" property="actionMonth"/></span>月
            <span class="dsp_actionday_<%= idx + 1 %>"><bean:write name="dataMdl" property="actionDay"/></span>日
            </div>
          </logic:equal>
        </span>
        <span class="dsp_nextaction_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040DspActionStr" filter="false"/></span>
      </div>
      <div class="js_nextActionArea<%= idx + 1 %> display_n">
        <div class="verAlignMid">
          日付指定：
          <logic:equal name="dataMdl" property="actDateKbn" value="1">
            <input name="ntp040ActDateKbn_<%= idx + 1 %>" value="1" class="ml5" checked="checked" onclick="toggleActionAreaShow('nxtActDateArea_<%= idx + 1 %>');" id="actDate1_<%= idx + 1 %>" type="radio">
          </logic:equal>
          <logic:notEqual name="dataMdl" property="actDateKbn" value="1">
            <input name="ntp040ActDateKbn_<%= idx + 1 %>" value="1" class="ml5" onclick="toggleActionAreaShow('nxtActDateArea_<%= idx + 1 %>');" id="actDate1_<%= idx + 1 %>" type="radio">
          </logic:notEqual>
          <label for="actDate1_<%= idx + 1 %>"><gsmsg:write key="reserve.show.ok" /></label>
          <logic:equal name="dataMdl" property="actDateKbn" value="0">
            <input name="ntp040ActDateKbn_<%= idx + 1 %>" value="0" class="ml10" checked="checked" onclick="toggleActionAreaHide('nxtActDateArea_<%= idx + 1 %>');" id="actDate0_<%= idx + 1 %>" type="radio">
          </logic:equal>
          <logic:notEqual name="dataMdl" property="actDateKbn" value="0">
            <input name="ntp040ActDateKbn_<%= idx + 1 %>" value="0" class="ml10" onclick="toggleActionAreaHide('nxtActDateArea_<%= idx + 1 %>');" id="actDate0_<%= idx + 1 %>" type="radio">
          </logic:notEqual>
          <label for="actDate0_<%= idx + 1 %>"><gsmsg:write key="reserve.show.no" /></label>
        </div>
        <logic:equal name="dataMdl" property="actDateKbn" value="0">
          <div id="nxtActDateArea_<%= idx + 1 %>" class="display_n">
        </logic:equal>
        <logic:notEqual name="dataMdl" property="actDateKbn" value="0">
          <div id="nxtActDateArea_<%= idx + 1 %>">
        </logic:notEqual>
        <% selActionYearIdStr  = "selActionYear"  + String.valueOf(idx + 1); %>
        <% selActionMonthIdStr = "selActionMonth" + String.valueOf(idx + 1); %>
        <% selActionDayIdStr   = "selActionDay"   + String.valueOf(idx + 1); %>
        <% selActionDateIdStr   = "selActionDate"   + String.valueOf(idx + 1); %>
        <bean:define id="dataActionYear" name="dataMdl" property="actionYear" type="java.lang.Integer"/>
        <bean:define id="dataActionMonth" name="dataMdl" property="actionMonth" type="java.lang.Integer"/>
        <bean:define id="dataActionDay" name="dataMdl" property="actionDay" type="java.lang.Integer"/>
        <logic:equal name="dataMdl" property="actDateKbn" value="0">
          <bean:define id="actionInitYear" name="ntp040Form" property="ntp040InitYear" type="java.lang.String"/>
          <bean:define id="actionInitMonth" name="ntp040Form" property="ntp040InitMonth" type="java.lang.String"/>
          <bean:define id="actionInitDay" name="ntp040Form" property="ntp040InitDay" type="java.lang.String"/>
          <% dataActionYear  =  Integer.parseInt(actionInitYear); %>
          <% dataActionMonth =  Integer.parseInt(actionInitMonth); %>
          <% dataActionDay   =  Integer.parseInt(actionInitDay); %>
        </logic:equal>
        <div class="verAlignMid">
        <logic:equal name="dataMdl" property="actDateKbn" value="0">
          <input type="text" name="<%= selActionDateIdStr %>" id="<%= selActionDateIdStr %>" maxlength="10" class="txt_c ml5 wp95 datepicker js_frDatePicker" value="<bean:write name="ntp040Form" property="ntp040InitDate" />" />
        </logic:equal>
        <logic:notEqual name="dataMdl" property="actDateKbn" value="0">
          <input type="text" name="<%= selActionDateIdStr %>" id="<%= selActionDateIdStr %>" maxlength="10" class="txt_c ml5 wp95 datepicker js_frDatePicker" value="<bean:write name="dataMdl" property="actionDate" />" />
        </logic:notEqual>
        <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
        <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#<%= selActionDateIdStr.toString() %>')[0], 1);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
          <i class="icon-paging_left "></i>
        </button>
        <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#<%= selActionDateIdStr.toString() %>')[0], 2);">
          <gsmsg:write key="cmn.today" />
        </button>
        <span>
          <a class="fw_b todayBtn original-display" onClick="return moveDay($('#<%= selActionDateIdStr.toString() %>')[0], 2);">
            <gsmsg:write key="cmn.today" />
          </a>
        </span>
        <button type="button" class="webIconBtn" onClick="return moveDay($('#<%= selActionDateIdStr.toString() %>')[0], 3);">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
          <i class="icon-paging_right "></i>
        </button>
        </div>
        </div>
        <div>
          <textarea id="actionstr_<%= idx + 1 %>" name="ntp040NextAction_<%= idx + 1 %>" class="w100 mt10" rows="3" onkeyup="showLengthStr(value, 1000, 'actionlength<%= idx + 1 %>');"><bean:write name="dataMdl" property="actionStr" /></textarea>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="actionlength<%= idx + 1 %>" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </div>
    </td>
  </tr>
</logic:equal>
<tr>
  <th class="w20">
    <gsmsg:write key="cmn.registant" />
  </th>
  <td class="w80" colspan="3">
    <div>
      <logic:notEqual name="ntp040Form" property="ntp040AddUsrJkbn" value="9">
        <span class="addUserName_<%= idx + 1 %> <%=UserUtil.getCSSClassNameNormal(dataMdl.getNtp040AddUsrUkoFlg())%>"><bean:write name="dataMdl" property="ntp040NtpAddUsrName" /></span>
      </logic:notEqual>
      <logic:equal name="ntp040Form" property="ntp040AddUsrJkbn" value="9">
        <del>
          <span class="addUserName_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040NtpAddUsrName" /></span>
        </del>
      </logic:equal>
      <span class="flo_r">
        <span class="addDate_<%= idx + 1 %>"><bean:write name="dataMdl" property="ntp040NtpDate" /></span>
      </span>
    </div>
  </td>
</tr>
<tr>
  <td class="txt_c" colspan="4">
    <span class="js_goodBtnArea_<bean:write name="dataMdl" property="ntp040NtpSid" />">
      <logic:equal name="dataMdl" property="ntp040GoodFlg" value="0">
        <button type="button" id="<bean:write name="dataMdl" property="ntp040NtpSid" />" class="baseBtn ntp_goodButton js_goodLink" value="<gsmsg:write key="ntp.22" />!">
          <gsmsg:write key="ntp.22" />!
        </button>
      </logic:equal>
      <logic:notEqual name="dataMdl" property="ntp040GoodFlg" value="0">
        <span class="js_textAlreadyGood fs_12 fw_b">いいね!しています</span>
      </logic:notEqual>
    </span>
    <span class="ml5 js_textGood cursor_p" id="<bean:write name="dataMdl" property="ntp040NtpSid" />" data-count="<bean:write name="dataMdl" property="ntp040GoodCnt" />">
      <img class="btn_classicImg-display" src="../nippou/images/classic/bg_good_2.gif">
      <img class="btn_originalImg-display" src="../nippou/images/original/icon_good.png">
      <span class="js_goodCount_<bean:write name="dataMdl" property="ntp040NtpSid" />"><bean:write name="dataMdl" property="ntp040GoodCnt" /></span>
    </span>
  </td>
</tr>
<logic:notEmpty name="dataMdl" property="ntp040CommentList">
  <tr class="js_ntp040DspComment_tr_<%= idx + 1 %>">
    <td class="js_ntp040DspComment_<%= idx + 1 %>" colspan="4">
      <span class="js_commentDspArea<%= idx + 1 %>">
      <logic:iterate id="npcMdl" name="dataMdl" property="ntp040CommentList">
        <bean:define id="usrInfMdl" name="npcMdl" property="ntp040UsrInfMdl" type="jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel"/>
        <bean:define id="ntpCmtMdl" name="npcMdl" property="ntp040CommentMdl"/>
        <logic:notEqual name="ntpCmtMdl" property="npcSid" value="1">
          <div class="settingForm_separator ml20 mr20"></div>
        </logic:notEqual>
        <table class="w100 table-noBorder commentDspAreaTable_<%= idx + 1 %>_<bean:write name="ntpCmtMdl" property="npcSid" />">
          <tr>
            <td>
              <logic:equal name="usrInfMdl" property="binSid" value="0">
                <logic:notEqual name="usrInfMdl" property="usiPictKf" value="1">
                  <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                  <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                </logic:notEqual>
                <logic:equal name="usrInfMdl" property="usiPictKf" value="1">
                  <div class="hikokai_photo-m txt_c">
                    <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                  </div>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="usrInfMdl" property="binSid" value="0">
                <logic:equal name="usrInfMdl" property="usiPictKf" value="1">
                  <div class="hikokai_photo-m txt_c">
                    <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                  </div>
                </logic:equal>
                <logic:notEqual name="usrInfMdl" property="usiPictKf" value="1">
                  <img class="wp50" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="usrInfMdl" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                </logic:notEqual>
              </logic:notEqual>
            </td>
            <td class="w100 txt_t" id="commentDspAreaTable_<%= idx + 1 %>_<bean:write name="ntpCmtMdl" property="npcSid" />">
              <logic:notEqual name="usrInfMdl" property="usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_DELETE) %>">
                <span class="<%=UserUtil.getCSSClassNameNormal(usrInfMdl.getUsrUkoFlg()) %> fw_b"><bean:write name="usrInfMdl" property="usiSei"/>&nbsp;<bean:write name="usrInfMdl" property="usiMei"/></span>
                <span class="ml5"><bean:write name="npcMdl" property="ntp040CommentDate" filter="false"/></span>
              </logic:notEqual>
              <logic:equal name="usrInfMdl" property="usrJkbn" value="<%= String.valueOf(GSConst.JTKBN_DELETE) %>">
                  <del><span class="fw_b"><bean:write name="usrInfMdl" property="usiSei"/>&nbsp;<bean:write name="usrInfMdl" property="usiMei"/></span></del>
                  <span class="ml5"><bean:write name="npcMdl" property="ntp040CommentDate" filter="false"/></span>
              </logic:equal>
              <logic:equal name="npcMdl" property="ntp040CommentDelFlg" value="1">
                <span class="commentDel cursor_p" id="<bean:write name="ntpCmtMdl" property="npcSid" />">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete_2.gif">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png">
                </span>
              </logic:equal>
              <div>
                <bean:write name="ntpCmtMdl" property="npcComment" filter="false" />
              </div>
            </td>
          </tr>
        </table>
      </logic:iterate>
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:empty name="dataMdl" property="ntp040CommentList">
  <tr class="js_ntp040DspComment_tr_<%= idx + 1 %> display_n">
    <td class="js_ntp040DspComment_<%= idx + 1 %>" colspan="5"></td>
  </tr>
</logic:empty>

<logic:equal name="dataMdl" property="ankenViewable" value="true">
  <tr class="js_commentArea<%= idx + 1 %>">
    <td class="w100" colspan="4">
      <table class="w100 table-noBorder">
        <tr>
          <td>
            <logic:notEmpty name="ntp040Form" property="ntp040UsrInfMdl">
              <bean:define id="usrInf" name="ntp040Form" property="ntp040UsrInfMdl"/>
              <logic:equal name="usrInf" property="binSid" value="0">
                <logic:notEqual name="usrInf" property="usiPictKf" value="1">
                  <img src="../common/images/classic/icon_photo.gif" name="pitctImage" class="classic-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                  <img src="../common/images/original/photo.png" name="pitctImage" class="original-display wp50" alt="<gsmsg:write key="cmn.photo" />" >
                </logic:notEqual>
                <logic:equal name="usrInf" property="usiPictKf" value="1">
                  <div align="center" class="hikokai_photo-m">
                    <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                  </div>
                </logic:equal>
              </logic:equal>
              <logic:notEqual name="usrInf" property="binSid" value="0">
                <logic:equal name="usrInf" property="usiPictKf" value="1">
                  <div align="center" class="hikokai_photo-m">
                    <span class="cl_fontWarn"><gsmsg:write key="cmn.private" /></span>
                  </div>
                </logic:equal>
                <logic:notEqual name="usrInf" property="usiPictKf" value="1">
                  <img class="wp50" src="../common/cmn100.do?CMD=getImageFile&cmn100binSid=<bean:write name="usrInf" property="binSid" />" alt="<gsmsg:write key="cmn.photo" />" />
                </logic:notEqual>
              </logic:notEqual>
            </logic:notEmpty>
          </td>
          <td class="w100">
            <div class="textfield verAlignMid w100">
              <label class="js_ntp_labelArea ntp_labelArea cl_fontWeek" for="field_id<%= idx + 1 %>"><gsmsg:write key="ntp.36" /></label>
              <textarea name="ntp040Comment_<%= idx + 1 %>" cols="45" rows="3" class="w100" id="field_id<%= idx + 1 %>"></textarea>
            </div>
          </td>
          <td id="<%= idx + 1 %>" class="no_w">
            <button type="button" class="baseBtn js_commentBtn" value="投稿" id="<bean:write name="dataMdl" property="ntp040NtpSid" />">
              投稿
            </button>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</logic:equal>
<tr class="commentArea<%= idx + 1 %>" style="display:none;"></tr>
