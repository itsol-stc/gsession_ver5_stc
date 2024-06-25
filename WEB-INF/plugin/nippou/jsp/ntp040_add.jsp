<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="default" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<% String frhourval = request.getParameter("frhourval");%>
<% String frminval = request.getParameter("frminval");%>
<% String tohourval = request.getParameter("tohourval");%>
<% String tominval = request.getParameter("tominval");%>
<% String tdColor = request.getParameter("tdColor");%>
<% String valueFocusEvent = request.getParameter("valueFocusEvent"); %>
<% String maxLengthContent = request.getParameter("maxLengthContent"); %>
<% String maxLengthBiko = request.getParameter("maxLengthBiko"); %>
<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE); %>

<div class="w100 txt_r">
  <logic:equal name="ntp040Form" property="scheduleUseOk" value="0">
    <button type="button" class="baseBtn js_schDataGetBtn">
      <img class="btn_classicImg-display" src="../nippou/images/classic/icon_schedule.gif" alt="<gsmsg:write key="ntp.151" />">
      <img class="btn_originalImg-display" src="../nippou/images/original/icon_schedule.png" alt="<gsmsg:write key="ntp.151" />">
      <gsmsg:write key="ntp.151" />
    </button>
  </logic:equal>
  <logic:equal name="ntp040Form" property="projectUseOk" value="0">
    <button type="button" class="baseBtn js_prjDataGetBtn">
      <img class="btn_classicImg-display" src="../nippou/images/classic/icon_project.gif" alt="<gsmsg:write key="ntp.152" />">
      <img class="btn_originalImg-display" src="../nippou/images/original/icon_project.png" alt="<gsmsg:write key="ntp.152" />">
      <gsmsg:write key="ntp.152" />
    </button>
  </logic:equal>
  <logic:equal name="ntp040Form" property="addressUseOk" value="0">
    <button type="button" class="baseBtn js_contDataGetBtn">
      <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif" alt="コンタクト履歴データ取込">
      <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png" alt="コンタクト履歴データ取込">
      コンタクト履歴データ取込
    </button>
  </logic:equal>
</div>
<table id="nippou_data_1" class="table-left w100">
  <tr class="js_headNippou">
    <th colspan="4" class="bgC_header1 js_ntpInfoNum" id="pos1">
      <div class="w100 verAlignMid hp30">
        <img class="btn_classicImg-display" src="../nippou/images/classic/icon_menu_single.gif">
        <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png">
        <span class="js_nippouRowNumber cl_fontOutline">NO.1</span>
      </div>
    </th>
  </tr>
  <tr>
    <th class="w20">
      <gsmsg:write key="cmn.time" />
    </th>
    <td class="w80" colspan="3">
      <span class="display_flex">
      <span class="clockpicker_fr mr5 pos_rel display_flex input-group">
        <html:text property="ntp040FrTime" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
        <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
      </span>
      ～
      <span class="clockpicker_to ml5 pos_rel display_flex input-group">
        <html:text property="ntp040ToTime" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
        <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
      </span>
      <span id="betWeenDays"></span>
      </span>
    </td>
  </tr>


  <logic:equal name="ntp040Form" property="ntp040AnkenCompanyUse" value="0">
    <tr>
      <th class="w20">
         <gsmsg:write key="ntp.11" />
      </th>
      <td class="w30 txt_t">
        <div>
          <button type="button" class="baseBtn" onclick="return openAnkenWindow('ntp040','')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
          </button>
          <button type="button" class="baseBtn js_ankenHistoryPop">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
            <gsmsg:write key="ntp.17" />
          </button>
        </div>
        <div>
          <logic:equal name="ntp040Form" property="ntp040AnkenSid" value="-1">
            <div id="ntp040AnkenIdArea"></div>
            <div id="ntp040AnkenCodeArea"></div>
            <div id="ntp040AnkenNameArea"></div>
          </logic:equal>
          <logic:equal name="ntp040Form" property="ntp040AnkenSid" value="0">
            <div id="ntp040AnkenIdArea"></div>
            <div id="ntp040AnkenCodeArea"></div>
            <div id="ntp040AnkenNameArea"></div>
          </logic:equal>
          <logic:notEqual name="ntp040Form" property="ntp040AnkenSid" value="-1">
            <logic:notEqual name="ntp040Form" property="ntp040AnkenSid" value="0">
              <div id="ntp040AnkenIdArea">
                <input name="ntp040AnkenSid" value="<bean:write name="ntp040Form" property="ntp040AnkenSid" />" type="hidden">
              </div>
              <div id="ntp040AnkenCodeArea">
                案件コード：<bean:write name="ntp040Form" property="ntp040AnkenCode" />
              </div>
              <div id="ntp040AnkenNameArea">
                <a id="<bean:write name="ntp040Form" property="ntp040AnkenSid" />" class="cl_linkDef js_anken_click">
                  <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken_18.png">
                  <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                  <bean:write name="ntp040Form" property="ntp040AnkenName" />
                </a>
                <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif" onclick="delAnken('ntp040','');">
                <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png" onclick="delAnken('ntp040','');">
              </div>
            </logic:notEqual>
          </logic:notEqual>
        </div>
      </td>
      <th class="w20">
         企業・顧客
      </th>
      <td class="w30 txt_t">
        <div>
          <button type="button" class="baseBtn" onclick="return openCompanyWindow2('ntp040','')">
            <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif">
            <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png">
            <gsmsg:write key="addressbook" />
          </button>
          <button type="button" class="baseBtn js_adrHistoryPop">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
            <gsmsg:write key="ntp.17" />
          </button>
        </div>
        <logic:equal name="ntp040Form" property="ntp040CompanySid" value="-1">
          <div id="ntp040CompanyIdArea"></div>
          <div id="ntp040CompanyBaseIdArea"></div>
          <div id="ntp040CompanyCodeArea"></div>
          <div id="ntp040CompNameArea"></div>
        </logic:equal>
        <logic:equal name="ntp040Form" property="ntp040CompanySid" value="0">
          <div id="ntp040CompanyIdArea"></div>
          <div id="ntp040CompanyBaseIdArea"></div>
          <div id="ntp040CompanyCodeArea"></div>
          <div id="ntp040CompNameArea"></div>
        </logic:equal>
        <logic:notEqual name="ntp040Form" property="ntp040CompanySid" value="-1">
          <logic:notEqual name="ntp040Form" property="ntp040CompanySid" value="0">
            <div id="ntp040CompanyIdArea">
              <input name="ntp040CompanySid" value="<bean:write name="ntp040Form" property="ntp040CompanySid" />" type="hidden">
            </div>
            <div id="ntp040CompanyBaseIdArea">
              <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="-1">
                <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="0">
                  <input name="ntp040CompanyBaseSid" value="<bean:write name="ntp040Form" property="ntp040CompanyBaseSid" />" type="hidden">
                </logic:notEqual>
              </logic:notEqual>
            </div>
            <div id="ntp040CompanyCodeArea">
              企業コード：<bean:write name="ntp040Form" property="ntp040CompanyCode" />
            </div>
            <div id="ntp040CompNameArea">
              <a id="<bean:write name="ntp040Form" property="ntp040CompanySid" />" class="comp_click">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
                <bean:write name="ntp040Form" property="ntp040CompanyName" />
                <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="-1">
                  <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="0">
                    <span class="ml5"><bean:write name="ntp040Form" property="ntp040CompanyBaseName" /></span>
                  </logic:notEqual>
                </logic:notEqual>
              </a>
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif"  onclick="delCompany('ntp040','');">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png"  onclick="delCompany('ntp040','');">
            </div>
          </logic:notEqual>
        </logic:notEqual>
        <div id="ntp040AddressIdArea"></div>
        <div id="ntp040AddressNameArea"></div>
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="ntp040Form" property="ntp040AnkenCompanyUse" value="1">
    <tr>
      <th class="w20">
         <gsmsg:write key="ntp.11" />
      </th>
      <td class="w80 txt_t" colspan="3">
        <div>
          <button type="button" class="baseBtn" onclick="return openAnkenWindow('ntp040','')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
            <gsmsg:write key="ntp.11" /><gsmsg:write key="cmn.search" />
          </button>
          <button type="button" class="baseBtn js_ankenHistoryPop">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
            <gsmsg:write key="ntp.17" />
          </button>
        </div>
        <div>
          <logic:equal name="ntp040Form" property="ntp040AnkenSid" value="-1">
            <div id="ntp040AnkenIdArea"></div>
            <div id="ntp040AnkenCodeArea"></div>
            <div id="ntp040AnkenNameArea"></div>
          </logic:equal>
          <logic:equal name="ntp040Form" property="ntp040AnkenSid" value="0">
            <div id="ntp040AnkenIdArea"></div>
            <div id="ntp040AnkenCodeArea"></div>
            <div id="ntp040AnkenNameArea"></div>
          </logic:equal>
          <logic:notEqual name="ntp040Form" property="ntp040AnkenSid" value="-1">
            <logic:notEqual name="ntp040Form" property="ntp040AnkenSid" value="0">
              <div id="ntp040AnkenIdArea">
                <input name="ntp040AnkenSid" value="<bean:write name="ntp040Form" property="ntp040AnkenSid" />" type="hidden">
              </div>
              <div id="ntp040AnkenCodeArea">
                案件コード：<bean:write name="ntp040Form" property="ntp040AnkenCode" />
              </div>
              <div id="ntp040AnkenNameArea">
                <a id="<bean:write name="ntp040Form" property="ntp040AnkenSid" />" class="js_anken_click">
                  <img class="btn_classicImg-display" src="../nippou/images/classic/icon_anken_18.png">
                  <img class="btn_originalImg-display" src="../nippou/images/original/icon_anken.png">
                  <bean:write name="ntp040Form" property="ntp040AnkenName" />
                </a>
                <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif" onclick="delAnken('ntp040','');">
                <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png" onclick="delAnken('ntp040','');">
              </div>


            </logic:notEqual>
          </logic:notEqual>
        </div>
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="ntp040Form" property="ntp040AnkenCompanyUse" value="2">
    <tr>
      <th class="w20">
         企業・顧客
      </th>
      <td class="w80 txt_t" colspan="3">
        <button type="button" class="baseBtn" onclick="return openCompanyWindow2('ntp040','')">
          <img class="btn_classicImg-display" src="../nippou/images/classic/icon_address.gif" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../nippou/images/original/icon_address.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="addressbook" />
        </button>
        <button type="button" class="baseBtn js_adrHistoryPop">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_clock.png" alt="<gsmsg:write key="ntp.17" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_log_18.png" alt="<gsmsg:write key="ntp.17" />">
          <gsmsg:write key="ntp.17" />
        </button>
        <logic:equal name="ntp040Form" property="ntp040CompanySid" value="-1">
          <div id="ntp040CompanyIdArea"></div>
          <div id="ntp040CompanyBaseIdArea"></div>
          <div id="ntp040CompanyCodeArea"></div>
          <div id="ntp040CompNameArea"></div>
        </logic:equal>
        <logic:equal name="ntp040Form" property="ntp040CompanySid" value="0">
          <div id="ntp040CompanyIdArea"></div>
          <div id="ntp040CompanyBaseIdArea"></div>
          <div id="ntp040CompanyCodeArea"></div>
          <div id="ntp040CompNameArea"></div>
        </logic:equal>
        <logic:notEqual name="ntp040Form" property="ntp040CompanySid" value="-1">
          <logic:notEqual name="ntp040Form" property="ntp040CompanySid" value="0">
            <div id="ntp040CompanyIdArea">
              <input name="ntp040CompanySid" value="<bean:write name="ntp040Form" property="ntp040CompanySid" />" type="hidden">
            </div>
            <div id="ntp040CompanyBaseIdArea">
              <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="-1">
                <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="0">
                  <input name="ntp040CompanyBaseSid" value="<bean:write name="ntp040Form" property="ntp040CompanyBaseSid" />" type="hidden">
                </logic:notEqual>
              </logic:notEqual>
            </div>
            <div id="ntp040CompanyCodeArea">
              企業コード：<bean:write name="ntp040Form" property="ntp040CompanyCode" />
            </div>
            <div id="ntp040CompNameArea">
              <a id="<bean:write name="ntp040Form" property="ntp040CompanySid" />" class="js_compClick">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_company.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_company.png">
                <bean:write name="ntp040Form" property="ntp040CompanyName" />
                <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="-1">
                  <logic:notEqual name="ntp040Form" property="ntp040CompanyBaseSid" value="0">
                    <span class="ml5"><bean:write name="ntp040Form" property="ntp040CompanyBaseName" /></span>
                  </logic:notEqual>
                </logic:notEqual>
              </a>
              <img class="btn_classicImg-display cursor_p" src="../common/images/classic/icon_delete_2.gif"  onclick="delCompany('ntp040','');">
              <img class="btn_originalImg-display cursor_p" src="../common/images/original/icon_delete.png"  onclick="delCompany('ntp040','');">
            </div>
          </logic:notEqual>
        </logic:notEqual>
        <div id="ntp040AddressIdArea"></div>
        <div id="ntp040AddressNameArea"></div>
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="ntp040Form" property="ntp040KtBriHhuUse" value="0">
    <tr>
      <th class="w20">
        活動分類/方法
      </th>
      <td class="w80" colspan="3">
        <logic:notEmpty name="ntp040Form" property="ntp040KtbunruiLavel">
          <html:select property="ntp040Ktbunrui">
            <html:optionsCollection name="ntp040Form" property="ntp040KtbunruiLavel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
        <logic:notEmpty name="ntp040Form" property="ntp040KthouhouLavel">
          <html:select property="ntp040Kthouhou">
            <html:optionsCollection name="ntp040Form" property="ntp040KthouhouLavel" value="value" label="label" />
          </html:select>
        </logic:notEmpty>
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="ntp040Form" property="ntp040MikomidoUse" value="0">
    <tr>
      <th class="w20">
        見込み度
      </th>
      <td class="w80" colspan="3">
        <div class="verAlignMid">
          <html:radio name="ntp040Form" property="ntp040Mikomido" styleId="ntp040Mikomido0" value="0" /><label for="ntp040Mikomido0">10%</label>
          <html:radio name="ntp040Form" property="ntp040Mikomido" styleClass="ml10" styleId="ntp040Mikomido1" value="1" /><label for="ntp040Mikomido1">30%</label>
          <html:radio name="ntp040Form" property="ntp040Mikomido" styleClass="ml10" styleId="ntp040Mikomido2" value="2" /><label for="ntp040Mikomido2">50%</label>
          <html:radio name="ntp040Form" property="ntp040Mikomido" styleClass="ml10" styleId="ntp040Mikomido3" value="3" /><label for="ntp040Mikomido3">70%</label>
          <html:radio name="ntp040Form" property="ntp040Mikomido" styleClass="ml10" styleId="ntp040Mikomido4" value="4" /><label for="ntp040Mikomido4">100%</label>
        </div>
        <logic:notEqual name="ntp040Form" property="ntp040MikomidoFlg" value="0">
          <div>
            <button type="button" class="baseBtn js_mikomidoBtn">
              <gsmsg:write key="tcd.tcd010.15" />
            </button>
          </div>
        </logic:notEqual>
      </td>
    </tr>
  </logic:equal>
  <tr>
    <th class="w20">
      <gsmsg:write key="cmn.title" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
    </th>
    <td class="w80" colspan="3">
      <html:text name="ntp040Form" maxlength="100" property="ntp040Title" styleId="ntpTitleTextBox" styleClass="wp300" />
    </td>
  </tr>
  <tr>
    <th class="w20">
      <gsmsg:write key="schedule.10" />
    </th>
    <td class="w80" colspan="3">
      <bean:define id="colorMsg1" value=""/>
      <bean:define id="colorMsg2" value=""/>
      <bean:define id="colorMsg3" value=""/>
      <bean:define id="colorMsg4" value=""/>
      <bean:define id="colorMsg5" value=""/>
      <logic:iterate id="msgStr" name="ntp040Form" property="ntp040ColorMsgList" indexId="msgId" type="java.lang.String">
        <logic:equal name="msgId" value="0">
          <% colorMsg1 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="1">
          <% colorMsg2 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="2">
          <% colorMsg3 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="3">
          <% colorMsg4 = msgStr; %>
        </logic:equal>
        <logic:equal name="msgId" value="4">
          <% colorMsg5 = msgStr; %>
        </logic:equal>
      </logic:iterate>
      <input type="hidden" id="msgCol1" value="<%= colorMsg1 %>" />
      <input type="hidden" id="msgCol2" value="<%= colorMsg2 %>" />
      <input type="hidden" id="msgCol3" value="<%= colorMsg3 %>" />
      <input type="hidden" id="msgCol4" value="<%= colorMsg4 %>" />
      <input type="hidden" id="msgCol5" value="<%= colorMsg5 %>" />
      <div class="verAlignMid">
        <span class="ntp_titlecolor-block bgc_fontSchTitleBlue"><html:radio name="ntp040Form" property="ntp040Bgcolor" value="1" styleId="bg_color1"/></span>
        <label for="bg_color1" class="no_w ml5 mr10"><%= colorMsg1 %></label>
      </div>
      <wbr>
      <div class="verAlignMid">
        <span class="ntp_titlecolor-block bgc_fontSchTitleRed"><html:radio name="ntp040Form" property="ntp040Bgcolor" value="2" styleId="bg_color2" /></span>
        <label for="bg_color2" class="no_w ml5 mr10"><%= colorMsg2 %></label>
      </div>
      <wbr>
      <div class="verAlignMid">
        <span class="ntp_titlecolor-block bgc_fontSchTitleGreen"><html:radio name="ntp040Form" property="ntp040Bgcolor" value="3" styleId="bg_color3" /></span>
        <label for="bg_color3" class="no_w ml5 mr10"><%= colorMsg3 %></label>
      </div>
      <wbr>
      <div class="verAlignMid">
        <span class="ntp_titlecolor-block bgc_fontSchTitleYellow"><html:radio name="ntp040Form" property="ntp040Bgcolor" value="4" styleId="bg_color4" /></span>
        <label for="bg_color4" class="no_w ml5 mr10"><%= colorMsg4 %></label>
      </div>
      <wbr>
      <div class="verAlignMid">
        <span class="ntp_titlecolor-block bgc_fontSchTitleBlack"><html:radio name="ntp040Form" property="ntp040Bgcolor" value="5" styleId="bg_color5" /></span>
        <label for="bg_color5" class="no_w ml5"><%= colorMsg5 %></label>
      </div>
    </td>
  </tr>
  <tr>
    <th class="w20">
      <gsmsg:write key="cmn.content" /><a id="naiyou" name="naiyou"></a>
    </th>
    <td class="w80" colspan="3">
      <textarea name="ntp040Value" rows="5" class="w100" onkeyup="showLengthStr(value, <%= maxLengthContent %>, 'inputlength');" id="inputstr" <%= valueFocusEvent %>><bean:write name="ntp040Form" property="ntp040Value" /></textarea>
      <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
    </td>
  </tr>
  <logic:equal name="ntp040Form" property="ntp040TmpFileUse" value="0">
    <tr>
      <th class="w20">
        <gsmsg:write key="cmn.attached" /><a id="naiyou" name="naiyou"></a>
      </th>
      <td class="w80" colspan="3">
        <attachmentFile:filearea
        mode="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_FILE) %>"
        pluginId="<%= jp.groupsession.v2.ntp.GSConstNippou.PLUGIN_ID_NIPPOU %>"
        tempDirId="ntp040"
        tempDirPlus= "row1"
        formId="1" />
      </td>
    </tr>
  </logic:equal>
  <logic:equal name="ntp040Form" property="ntp040NextActionUse" value="0">
    <tr>
      <th class="w20">
        <gsmsg:write key="ntp.96" />
      </th>
      <td class="w80" colspan="3">
        <div>
          <div class="verAlignMid">
            <gsmsg:write key="ntp.34" />：
            <html:radio name="ntp040Form" property="ntp040ActDateKbn" value="1" styleClass="ml5" styleId="actDate1" onclick="toggleActionAreaShow('nxtActDateArea');"/>
            <label for="actDate1"><gsmsg:write key="reserve.show.ok" /></label>
            <html:radio name="ntp040Form" property="ntp040ActDateKbn" value="0" styleClass="ml10" styleId="actDate0" onclick="toggleActionAreaHide('nxtActDateArea');"/>
            <label for="actDate0"><gsmsg:write key="reserve.show.no" /></label>
          </div>
        </div>
        <logic:equal name="ntp040Form" property="ntp040ActDateKbn" value="0">
          <div id="nxtActDateArea" class="display_n">
        </logic:equal>
        <logic:equal name="ntp040Form" property="ntp040ActDateKbn" value="1">
          <div id="nxtActDateArea">
        </logic:equal>
          <div class="verAlignMid">
            <html:text name="ntp040Form" property="ntp040NxtActDate" maxlength="10" styleId="selActionDate" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
            <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
            <button type="button" class="webIconBtn ml5" onClick="return moveDay($('#selActionDate')[0], 1);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png">
              <i class="icon-paging_left "></i>
            </button>
            <button type="button" class="baseBtn classic-display" value="<gsmsg:write key="cmn.today" />" onClick="return moveDay($('#selActionDate')[0], 2);">
              <gsmsg:write key="cmn.today" />
            </button>
            <span>
              <a class="fw_b todayBtn original-display" onClick="return moveDay($('#selActionDate')[0], 2);">
                <gsmsg:write key="cmn.today" />
              </a>
            </span>
            <button type="button" class="webIconBtn" onClick="return moveDay($('#selActionDate')[0], 3);">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png">
              <i class="icon-paging_right "></i>
            </button>
          </div>
        </div>
        <div>
          <textarea name="ntp040NextAction" class="w100 mt10" rows="3" onkeyup="showLengthStr(value, <%= maxLengthContent %>, 'actionlength');" id="actionstr" <%= valueFocusEvent %>><bean:write name="ntp040Form" property="ntp040NextAction" /></textarea>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="actionlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;<%= maxLengthContent %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </td>
    </tr>
  </logic:equal>
</table>