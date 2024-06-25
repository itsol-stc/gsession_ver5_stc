<%@ page pageEncoding="Windows-31J" contentType="text/html; charset=UTF-8"%>
<input type="hidden" name="helpPrm" value="shukei">
<%-- タブ --%>
<table class="wp250 mb10 sideTop-font">
  <tr>
    <td class="w50 bor1 bgC_body txt_c searchMenu_title-select">
      <gsmsg:write key="ntp.130" />
    </td>
    <td class="w50 cursor_p bgC_lightGray bor1 txt_c searchMenu_top searchMenu_top" onclick="changeMode(1);">
      <gsmsg:write key="ntp.131" />
    </td>
  </tr>
</table>
<div class="w100 txt_l">
  <span class="verAlignMid">
  <gsmsg:write key="ntp.132" />:
  <html:text name="ntp220Form" property="ntp220DateFrStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_frDatePicker"/>
  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
  <label for="jquery-ui-datepicker-from" class="ml5">～</label>
  <html:text name="ntp220Form" property="ntp220DateToStr" readonly="true" maxlength="10" styleClass="txt_c ml5 wp95 datepicker js_toDatePicker"/>
  <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
  <span class="ml20"><gsmsg:write key="ntp.59" />:</span>
  <html:select styleClass="wp100" name="ntp220Form" property="ntp220CatSid" onchange="changeCategoryCombo();">
    <logic:notEmpty name="ntp220Form" property="ntp220CategoryList">
      <html:optionsCollection name="ntp220Form" property="ntp220CategoryList" value="value" label="label" />
    </logic:notEmpty>
  </html:select>
  </span>
</div>
<div class="w100 mt10 txt_l">
  <span class="verAlignMid">
  <gsmsg:write key="cmn.group" />:
  <html:select property="ntp220GroupSid" styleClass="ml5 wp120" onchange="changeKojinGroupCombo();">
    <logic:notEmpty name="ntp220Form" property="ntp220GroupLavel" scope="request">
      <logic:iterate id="gpBean" name="ntp220Form" property="ntp220GroupLavel" scope="request">
        <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
        <logic:equal name="gpBean" property="styleClass" value="0">
          <html:option value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
        </logic:equal>
        <logic:notEqual name="gpBean" property="styleClass" value="0">
          <html:option styleClass="select_mygroup-bgc" value="<%= gpValue %>"><bean:write name="gpBean" property="label" /></html:option>
        </logic:notEqual>
      </logic:iterate>
    </logic:notEmpty>
  </html:select>
  <input name="ntp220SelectUsrSid" type="hidden" value="" />
  </span>
</div>
<table class="w100 mt10">
  <tr>
    <td class="wp180 bor1 txt_t p0 toukei_pluginArea" id="sel_menu_wrapper">
      <table class="w100 h100 txt_l">
        <tr>
          <td>
            <div class="w100 toukei_option" id="kigyou">
              <gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" />
              <input id="kigyou_offset" type="hidden" value="1" />
              <input id="kigyou_already_sel" type="hidden" value="" />
            </div>
            <div class="js_selMenuContent pb5 pt5 pl10 bor_b1 bgC_body display_n" id="kigyou_sel_menu_content">
            </div>
          </td>
        </tr>
        <tr>
          <td class="cursor_p">
            <div class="w100 toukei_option js_selMenuTitleProcessArea" id="process">
              <gsmsg:write key="ntp.61" /> / <gsmsg:write key="ntp.62" />
              <input id="process_offset" type="hidden" value="1" />
              <input id="process_already_sel" type="hidden" value="" />
            </div>
            <div class="js_selMenuContent pb5 pt5 pl10 bgC_body Gyoushu display_n">
              <logic:notEmpty name="ntp220Form" property="ntp220GyoushuList">
                <html:select name="ntp220Form" property="ntp220GyoushuSid" onchange="changeGyoushuCombo();">
                  <html:optionsCollection name="ntp220Form" property="ntp220GyoushuList" value="value" label="label" />
                </html:select>
              </logic:notEmpty>
            </div>
            <div class="js_selMenuContent pb5 pt5 pl10 bor_b1 bgC_body display_n" id="process_sel_menu_content">
            </div>
          </td>
        </tr>
        <tr>
          <td class="cursor_p">
            <div class="w100 toukei_option" id="mikomido">
              <gsmsg:write key="ntp.32" />
              <input id="mikomido_offset" type="hidden" value="1" />
              <input id="mikomido_already_sel" type="hidden" value="" />
            </div>
            <div class="js_selMenuContent pb5 pt5 pl10 bor_b1 bgC_body display_n" id="mikomido_sel_menu_content">
              <span class="sel_menu_content_text">○×</span><br>
              <span class="sel_menu_content_text">△□</span><br>
              <span class="sel_menu_content_text">×○</span><br>
              <span class="sel_menu_content_text">□×</span><br>
            </div>
          </td>
        </tr>
        <tr>
          <td class="cursor_p">
            <div class="w100 toukei_option" id="kokyakugensen">
              <gsmsg:write key="ntp.65" />
              <input id="kokyakugensen_offset" type="hidden" value="1" />
              <input id="kokyakugensen_already_sel" type="hidden" value="" />
            </div>
            <div class="js_selMenuContent pb5 pt5 pl10 bor_b1 bgC_body display_n" id="kokyakugensen_sel_menu_content">
            </div>
          </td>
        </tr>
        <tr>
          <td class="cursor_p">
            <div class="w100 toukei_option" id="kadou">
              <gsmsg:write key="ntp.170" />
              <input id="kadou_offset" type="hidden" value="1" />
              <input id="kadou_already_sel" type="hidden" value="" />
            </div>
            <div class="js_selMenuContent pb5 pt5 pl10 bor_b1 bgC_body display_n" id="kadoou_sel_menu_content">
            </div>
          </td>
        </tr>
      </table>
    </td>
    <td class="wp5 pr5 toukei_optionClose borC_light" id="menu_length_area">
    </td>
    <td class="txt_t pl5">
      <table class="wp300 mb10 sideTop-font" id="anken_state_menu_area">
        <tr>
          <td id="-1" class="wp100 bor1 cursor_p bgC_body txt_c searchMenu_title-select anken_state_check">
            <gsmsg:write key="ntp.166" />
          </td>
          <td id="0" class="wp100 bor1 cursor_p bgC_lightGray txt_c searchMenu_top anken_state_check">
            <gsmsg:write key="ntp.74" />
          </td>
          <td id="1" class="wp100 bor1 cursor_p bgC_lightGray txt_c searchMenu_top anken_state_check">
            <gsmsg:write key="ntp.75" />
          </td>
        </tr>
      </table>
      <table class="w100 table-left">
        <tr class="def_graph">
          <td class="w100" colspan="2">
            <div class="verAlignMid">
              <span class="mr5 def_graph_title cursor_p js_bunsekiKingaku" id="0"><gsmsg:write key="ntp.167" /></span>
              |
              <span class="ml5 def_graph_title cursor_p js_bunsekiSyodanKekkka cl_fontWeek" id="1"><gsmsg:write key="ntp.64" /></span>
              <table class="wp200 ml10 fs_12 sideTop-font no_w" id="anken_state_menu_area">
                <tr>
                  <td id="-1" class="wp50 bor1 pt0 pb0 cursor_p bgC_body txt_c searchMenu_title-select sel_label_sel_all js_stateLabelSelText">
                    <gsmsg:write key="ntp.166" />
                  </td>
                  <td id="1" class="wp50 bor1 pt0 pb0 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_jutyu js_stateLabelSelText">
                    <gsmsg:write key="ntp.69" />
                  </td>
                  <td id="2" class="wp50 bor1 pt0 pb0 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_sityu js_stateLabelSelText">
                    <gsmsg:write key="ntp.7" />
                  </td>
                  <td id="0" class="wp50 bor1 pt0 pb0 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_shodan js_stateLabelSelText">
                    <gsmsg:write key="ntp.68" />
                  </td>
                </tr>
              </table>
            </div>
            <div id="ankenGraphArea">
              <div id="ankenGraph" class="ntp_defAnkenGraph"><img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" /><div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div></div>
              <div id="shodanStateGraph"></div>
            </div>
            <input type="hidden" value="0" id="def_graph_val" name="def_graph_val" />
          </td>
        </tr>
        <%-- 稼働時間グラフ --%>
        <tr id="kadouArea" class="display_n borC_light">
          <td colspan="2" class="anything_kadou_area kadou_graph w100">
            <div class="txt_c w100">
              <table class="w100"><tr><td class="w100 no_w kdou_times_back table-noBorder">
              <gsmsg:write key="ntp.175" />:<span id="sum_kadou_days" class="fw_b fs_16 td_u"></span><gsmsg:write key="cmn.day" /><span class="mr5"></span>
              <gsmsg:write key="ntp.171" />:<span id="sum_kadou_time" class="fw_b fs_16" td_u></span><gsmsg:write key="cmn.time" /><span class="mr5"></span>
              <gsmsg:write key="ntp.176" />:<span id="sum_kadou_time_average" class="fw_b fs_16 td_u"></span><gsmsg:write key="cmn.time" />/<gsmsg:write key="cmn.day" />
              </td></tr></table>
            </div>
            <div id="kadouGraphArea">
              <div id="kadouGraph" class="ntp_defAnkenGraph"><img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" /><div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div></div>
            </div>
          </td>
        </tr>
        <tr class="display_n borC_light">
          <td class="txt_t w50 anything_area">
            <div class="verAlignMid w100 no_w">
              <gsmsg:write key="ntp.167" />
              <table class="wp200 ml10 fs_12 sideTop-font no_w" id="anken_state_menu_area">
                <tr>
                  <td id="-1" class="wp50 pt0 pb0 bor1 cursor_p bgC_body txt_c searchMenu_title-select sel_label_sel_all js_stateLabelSelText">
                    <gsmsg:write key="ntp.166" />
                  </td>
                  <td id="1" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_jutyu js_stateLabelSelText">
                    <gsmsg:write key="ntp.69" />
                  </td>
                  <td id="2" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_sityu js_stateLabelSelText">
                    <gsmsg:write key="ntp.7" />
                  </td>
                  <td id="0" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_shodan js_stateLabelSelText">
                    <gsmsg:write key="ntp.68" />
                  </td>
                </tr>
              </table>
              <div class="w100 txt_l fs_12 ml5">
                <div class="bor1 bgC_body wp80 ml_auto pt5">
                <div class="txt_c lh_normal">
                  <div class="verAlignMid">
                    <span class="ntp_labelJutyu">&nbsp;</span>
                    <gsmsg:write key="ntp.54" />
                  </div>
                </div>
                <div class="txt_c lh_normal">
                  <div class="verAlignMid">
                    <div class="ntp_labelMitsumori">&nbsp;</div>
                    <gsmsg:write key="ntp.53" />
                  </div>
                </div>
                </div>
              </div>
            </div>
            <div>
              <input id="anythingGraph1Val" type="hidden" value="0" />
            </div>
            <div id="anythingGraph1Area" style="clear:both;">
              <div id="anythingGraph1" style=""></div>
              <div class="tooltip_back borC_light" id="tooltipAnything"></div>
            </div>
            <div class="w100 txt_c"><span id="anything1_more" class="sel_anything1_more display_n cl_linkDef fs_12 cursor_p">▼<gsmsg:write key="ntp.133" /></span></div>
            <input type="hidden" name="anything1page" value="1" />
            <input type="hidden" name="anything1NowCount" value="0" />
          </td>
          <td class="txt_t w50 anything_area">
            <div class="verAlignMid w100 no_w">
              <gsmsg:write key="ntp.11" /><gsmsg:write key="ntp.71" />
              <table class="wp200 ml10 fs_12 sideTop-font state_label_area display_n no_w">
                <tr>
                  <td id="-1" class="wp50 pt0 pb0 bor1 cursor_p bgC_body txt_c searchMenu_title-select sel_label_sel_all js_stateLabelSelText">
                    <gsmsg:write key="ntp.166" />
                  </td>
                  <td id="1" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_jutyu js_stateLabelSelText">
                    <gsmsg:write key="ntp.69" />
                  </td>
                  <td id="2" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_sityu js_stateLabelSelText">
                    <gsmsg:write key="ntp.7" />
                  </td>
                  <td id="0" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_shodan js_stateLabelSelText">
                    <gsmsg:write key="ntp.68" />
                  </td>
                </tr>
              </table>
              <div class="w100 txt_r">
                <input id="anythingGraph2Val" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingGraph2Btn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingGraph2Btn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingGraph2Area" style="clear:both;">
              <div id="anythingGraph2" class="hp200"></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingMoney"></div>
            </div>
          </td>
        </tr>

        <%-- 可変エリア(稼働時間)  親要素 --%>
        <tr class="display_n borC_light">
          <td class="txt_t w50 anything_kadou_area">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.11" />-<gsmsg:write key="ntp.170" />
              </div>
              <div class="w100 txt_r">
                <input id="anythingKadou1GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingKadou1GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingKadou1GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingKadouGraph1Area" style="clear:both;">
              <div id="anythingKadouGraph1" style=""></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingKadou1"></div>
            </div>
            <div class="w100 txt_c"><span id="anythingKadou1_more" class="sel_anythingKadou1_more display_n cl_linkDef fs_12 cursor_p">▼<gsmsg:write key="ntp.133" /></span></div>
            <input type="hidden" name="anythingKadou1page" value="1" />
            <input type="hidden" name="anythingKadou1NowCount" value="0" />
          </td>
          <td class="txt_t w50 anything_kadou_area">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.15" />-<gsmsg:write key="ntp.170" />
              </div>
              <div class="w100 txt_r">
                <input id="anythingKadou2GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingKadou2GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingKadou2GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingKadouGraph2Area" style="clear:both;">
              <div id="anythingKadouGraph2"></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingKadou2"></div>
            </div>
            <div class="w100 txt_c"><span id="anythingKadou2_more" class="sel_anythingKadou2_more display_n cl_linkDef fs_12 cursor_p">▼<gsmsg:write key="ntp.133" /></span></div>
            <input type="hidden" name="anythingKadou2page" value="1" />
            <input type="hidden" name="anythingKadou2NowCount" value="0" />
          </td>
        </tr>
        <tr class="display_n borC_light">
          <td class="txt_t w50 anything_kadou_area">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.3" />-<gsmsg:write key="ntp.170" />
              </div>
              <div class="w100 txt_r">
                <input id="anythingKadou3GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingKadou3GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingKadou3GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingKadouGraph3Area" style="clear:both;">
              <div id="anythingKadouGraph3" style=""></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingKadou3"></div>
            </div>
            <div class="w100 txt_c"><span id="anythingKadou3_more" class="sel_anythingKadou3_more display_n cl_linkDef fs_12 cursor_p">▼<gsmsg:write key="ntp.133" /></span></div>
            <input type="hidden" name="anythingKadou3page" value="1" />
            <input type="hidden" name="anythingKadou3NowCount" value="0" />
          </td>
          <td class="txt_t w50 anything_kadou_area">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.121" />-<gsmsg:write key="ntp.170" />
              </div>
              <div class="w100 txt_r">
                <input id="anythingKadou4GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingKadou4GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingKadou4GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingKadouGraph4Area" style="clear:both;">
              <div id="anythingKadouGraph4"></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingKadou4"></div>
            </div>
            <div class="w100 txt_c"><span id="anythingKadou4_more" class="sel_anythingKadou4_more display_n cl_linkDef fs_12 cursor_p">▼<gsmsg:write key="ntp.133" /></span></div>
            <input type="hidden" name="anythingKadou4page" value="1" />
            <input type="hidden" name="anythingKadou4NowCount" value="0" />
          </td>
        </tr>
        <tr class="display_n borC_light">
          <td class="txt_t w100 anything_child_area" colspan="2">
            <div class="w100 txt_c fw_b">
              <span class="child_sel_content_head_title"></span>
            </div>
            <div>
              <div class="verAlignMid w100 no_w">
                <gsmsg:write key="ntp.167" />
                <table class="wp200 ml10 fs_12 sideTop-font no_w">
                  <tr>
                    <td id="-1" class="wp50 pt0 pb0 bor1 cursor_p bgC_body txt_c searchMenu_title-select sel_label_sel_all js_stateLabelSelText">
                      <gsmsg:write key="ntp.166" />
                    </td>
                    <td id="1" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_jutyu js_stateLabelSelText">
                      <gsmsg:write key="ntp.69" />
                    </td>
                    <td id="2" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_sityu js_stateLabelSelText">
                      <gsmsg:write key="ntp.7" />
                    </td>
                    <td id="0" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_shodan js_stateLabelSelText">
                      <gsmsg:write key="ntp.68" />
                    </td>
                  </tr>
                </table>
              </div>
            </div>
            <div>
              <input id="anythingGraph1Val" type="hidden" value="0" />
            </div>
            <div id="anythingChild0GraphArea">
              <div id="anythingChild0Graph" style=""></div>
              <div class="tooltip_back borC_light" id="tooltipAnything"></div>
            </div>
          </td>
        </tr>
        <tr class="display_n borC_light">
          <td class="txt_t w50 anything_child_area">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.64" />
              </div>
              <div class="w100 txt_r">
                <input id="anythingChild1GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingChild1GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingChild1GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingChild1GraphArea" style="text-valign:top;clear:both;">
              <div id="anythingChild1Graph" class="hp200"></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingChildResult"></div>
            </div>
          </td>
          <td class="txt_t w50 anything_child_area">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.134" />
              </div>
              <table class="wp200 ml10 fs_12 sideTop-font no_w">
                <tr>
                  <td id="-1" class="wp50 pt0 pb0 bor1 cursor_p bgC_body txt_c searchMenu_title-select sel_label_sel_all js_stateLabelSelText">
                    <gsmsg:write key="ntp.166" />
                  </td>
                  <td id="1" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_jutyu js_stateLabelSelText">
                    <gsmsg:write key="ntp.69" />
                  </td>
                  <td id="2" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_sityu js_stateLabelSelText">
                    <gsmsg:write key="ntp.7" />
                  </td>
                  <td id="0" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_shodan js_stateLabelSelText">
                    <gsmsg:write key="ntp.68" />
                  </td>
                </tr>
              </table>
              <div class="w100 txt_r">
                <input id="anythingChild2GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingChild2GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingChild2GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingChild2GraphArea" style="clear:both;">
              <div id="anythingChild2Graph" style="height:200px;"></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingChild2"></div>
            </div>
          </td>
        </tr>
        <tr class="display_n borC_light">
          <td colspan="2" class="anything_kadou_child_area w100">
            <div class="w100 txt_c fw_b">
              <span class="child_sel_content_head_title"></span>
            </div>
            <div class="verAlignMid w100">
              <div class="txt_c w100">
                <table class="w100 table-noBorder"><tr><td class="w100 no_w">
                <gsmsg:write key="ntp.175" />:<span id="sum_kadou_days_child" class="fw_b fs_16 td_u"><span></span></span><gsmsg:write key="cmn.day" />
                <gsmsg:write key="ntp.170" /><gsmsg:write key="ntp.172" />:<span id="sum_kadou_time_child" class="fw_b fs_16 td_u"><span></span></span><gsmsg:write key="cmn.time" />
                <gsmsg:write key="ntp.176" />:<span id="sum_kadou_time_average_child" class="fw_b fs_16 td_u"><span></span></span><gsmsg:write key="cmn.time" />/<gsmsg:write key="cmn.day" />
                </td></tr></table>
              </div>
              <div class="w50 txt_r">
                <input id="anythingKadouChild0GraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="anythingKadouChild0GraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="anythingKadouChild0GraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="anythingKadouChild0GraphArea" style="clear:both;">
              <div id="anythingKadouChild0Graph" style=""></div>
              <div class="tooltip_back borC_light" id="tooltipAnythingKadouChild0"></div>
            </div>
            <div class="w100 txt_c"><span id="anythingKadou4_more" class="sel_anythingKadouChild0_more display_n cl_linkDef fs_12 cursor_p">▼<gsmsg:write key="ntp.133" /></span></div>
            <input type="hidden" name="anythingKadou4page" value="1" />
            <input type="hidden" name="anythingKadou4NowCount" value="0" />
          </td>
        </tr>
        <tr>
          <td class="txt_t w50 def_graph hp200">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.64" />
              </div>
              <div class="w100 txt_r">
                <input id="resultGraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="resultGraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="resultGraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="resultGraphArea" style="clear:both;">
              <div id="resultGraph" style="height:200px;"></div>
              <div class="tooltip_back borC_light" id="tooltipResult"></div>
            </div>
          </td>
          <td class="txt_t w50 def_graph">
            <div class="verAlignMid w100">
              <div class="fw_b no_w">
                <gsmsg:write key="ntp.58" />
              </div>
              <table class="wp200 ml10 fs_12 sideTop-font no_w">
                <tr>
                  <td id="-1" class="wp50 pt0 pb0 bor1 cursor_p bgC_body txt_c searchMenu_title-select sel_label_sel_all js_stateLabelSelText">
                    <gsmsg:write key="ntp.166" />
                  </td>
                  <td id="1" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_jutyu js_stateLabelSelText">
                    <gsmsg:write key="ntp.69" />
                  </td>
                  <td id="2" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_sityu js_stateLabelSelText">
                    <gsmsg:write key="ntp.7" />
                  </td>
                  <td id="0" class="wp50 pt0 pb0 bor1 cursor_p bgC_lightGray txt_c searchMenu_top sel_label_sel_shodan js_stateLabelSelText">
                    <gsmsg:write key="ntp.68" />
                  </td>
                </tr>
              </table>
              <div class="w100 txt_r">
                <input id="shohinGraphVal" type="hidden" value="0" />
                <img src="../nippou/images/classic/icon_graph_sel.gif" id="shohinGraphBtn" class="btn_classicImg-display cursor_p" />
                <img src="../nippou/images/original/icon_toggle_graph.png" id="shohinGraphBtn" class="btn_originalImg-display cursor_p" />
              </div>
            </div>
            <div id="shohinGraphArea" style="clear:both;">
              <div id="shohinGraph" class="hp200"></div>
              <div class="tooltip_back borC_light" id="tooltipShohin"></div>
            </div>
          </td>
        </tr>
      </table>
      <div class="w100" id="anken_detail_area">
        <table class="table-top w100" id="anken_val_table">
          <tr class="anken_val_title" id="anken_val_title">
            <th class="w5" class="anken_val_title">
            </th>
            <th class="w40">
              <gsmsg:write key="ntp.173" />
            </th>
            <th class="w15">
              <select class="fs12" name="ankenListState" id="ankenListState">
                <option value="-1" selected><gsmsg:write key="ntp.166" /></option>
                <option value="1"><gsmsg:write key="ntp.69" /></option>
                <option value="2"><gsmsg:write key="ntp.7" /></option>
                <option value="0"><gsmsg:write key="ntp.68" /></option>
              </select>
              <input name="ankenListState" type="hidden" value="-1" />
            </th>
            <th class="w20">
              <select class="fs12" id="ankenListMoney">
                <option value="1" selected><gsmsg:write key="ntp.54" /></option>
                <option value="0"><gsmsg:write key="ntp.53" /></option>
              </select>
              <input name="ankenListMoney" type="hidden" value="1" />
            </th>
            <th class="w20">
              <select class="fs12" id="ankenListOther">
                <option value="0"><gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /></option>
                <option value="1"><gsmsg:write key="ntp.58" /></option>
                <option value="2"><gsmsg:write key="ntp.61" /></option>
                <option value="3"><gsmsg:write key="ntp.62" /></option>
                <option value="4"><gsmsg:write key="ntp.32" /></option>
                <option value="5"><gsmsg:write key="ntp.65" /></option>
                <option value="6"><gsmsg:write key="cmn.staff" /></option>
              </select>
              <input name="ankenListOther" type="hidden" value="0" />
            </th>
          </tr>
        </table>
        <div class="w100 txt_c anken_list_more_area display_n cl_linkDef fs_12 cursor_p">
          <input name="ankenDataPageNum" type="hidden" value="1" />
          <span id="ankenDataMore" class="sel_menu_content_text_more2">▼<gsmsg:write key="ntp.133" /></span>
        </div>
      </div>
      <%-- 案件詳細 (稼働時間)--%>
      <div class="w100 display_n" id="anken_kadou_detail_area">
        <table class="table-top w100" id="anken_kadou_val_table">
          <tr class="anken_kadou_val_title" id="anken_kadou_val_title">
            <th class="w5" class="anken_kadou_val_title">
            </th>
            <th class="w20">
              <gsmsg:write key="ntp.173" />
            </th>
            <th class="w15">
              <gsmsg:write key="ntp.170" />(h)
            </th>
            <th class="w10">
              <gsmsg:write key="ntp.174" />
            </th>
            <th class="w10">
              <gsmsg:write key="ntp.71" />
              <input name="ankenKadouListState" type="hidden" value="-1" />
            </th>
            <th class="w20">
              <select class="fs12" id="ankenKadouListMoney">
                <option value="1" selected><gsmsg:write key="ntp.54" /></option>
                <option value="0"><gsmsg:write key="ntp.53" /></option>
              </select>
              <input name="ankenKadouListMoney" type="hidden" value="1" />
            </th>
            <th class="w20">
              <select class="fs12" id="ankenKadouListOther">
                <option value="0"><gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" /></option>
                <option value="1"><gsmsg:write key="ntp.58" /></option>
                <option value="2"><gsmsg:write key="ntp.61" /></option>
                <option value="3"><gsmsg:write key="ntp.62" /></option>
                <option value="4"><gsmsg:write key="ntp.32" /></option>
                <option value="5"><gsmsg:write key="ntp.65" /></option>
                <option value="6"><gsmsg:write key="cmn.staff" /></option>
              </select>
              <input name="ankenKadouListOther" type="hidden" value="0" />
            </th>
          </tr>
        </table>
        <div class="w100 txt_c anken_kadou_list_more_area display_n cl_linkDef fs_12 cursor_p">
          <input name="ankenKadouDataPageNum" type="hidden" value="1" />
          <span id="ankenKadouDataMore" class="sel_menu_content_text_more2">▼<gsmsg:write key="ntp.133" /></span>
        </div>
      </div>
      <div class="w100 display_n" id="kigyou_kadou_detail_area">
        <table class="table-top w100" id="kigyou_kadou_val_table">
          <tr class="kigyou_kadou_val_title" id="kigyou_kadou_val_title">
            <th class="w5" class="kigyou_kadou_val_title">
            </th>
            <th class="w50">
              <gsmsg:write key="ntp.15" />・<gsmsg:write key="ntp.16" />
            </th>
            <th class="w25">
              <gsmsg:write key="ntp.170" />(h)
            </th>
            <th class="w20">
              <gsmsg:write key="ntp.174" />
            </th>
          </tr>
        </table>
        <div class="w100 txt_c kigyou_kadou_list_more_area display_n cl_linkDef fs_12 cursor_p">
          <input name="kigyouKadouDataPageNum" type="hidden" value="1" />
          <span id="kigyouKadouDataMore" class="sel_menu_content_text_more2">▼<gsmsg:write key="ntp.133" /></span>
        </div>
      </div>
      <div class="w100 display_n" id="kbunrui_kadou_detail_area">
        <table class="table-top w100" id="kbunrui_kadou_val_table">
          <tr class="kbunrui_kadou_val_title" id="kbunrui_kadou_val_title">
            <th class="w5" class="kbunrui_kadou_val_title">
            </th>
            <th class="w50">
              <gsmsg:write key="ntp.3" />
            </th>
            <th class="w25">
              <gsmsg:write key="ntp.170" />(h)
            </th>
            <th class="w20">
              <gsmsg:write key="ntp.174" />
            </th>
          </tr>
        </table>
        <div class="w100 txt_c kbunrui_kadou_list_more_area display_n cl_linkDef fs_12 cursor_p">
          <input name="kbunruiKadouDataPageNum" type="hidden" value="1" />
          <span id="kbunruiKadouDataMore" class="sel_menu_content_text_more2">▼<gsmsg:write key="ntp.133" /></span>
        </div>
      </div>
      <div class="w100 display_n" id="khouhou_kadou_detail_area">
        <table class="table-top w100" id="khouhou_kadou_val_table">
          <tr class="khouhou_kadou_val_title" id="khouhou_kadou_val_title">
            <th class="w5" class="kbunrui_kadou_val_title">
            </th>
            <th class="w50">
              <gsmsg:write key="ntp.121" />
            </th>
            <th class="w25">
              <gsmsg:write key="ntp.170" />(h)
            </th>
            <th class="w20">
              <gsmsg:write key="ntp.174" />
            </th>
          </tr>
        </table>
        <div class="w100 txt_c khouhou_kadou_list_more_area display_n cl_linkDef fs_12 cursor_p">
          <input name="khouhouKadouDataPageNum" type="hidden" value="1" />
          <span id="khouhouKadouDataMore" class="sel_menu_content_text_more2">▼<gsmsg:write key="ntp.133" /></span>
        </div>
      </div>
    </td>
  </tr>
</table>