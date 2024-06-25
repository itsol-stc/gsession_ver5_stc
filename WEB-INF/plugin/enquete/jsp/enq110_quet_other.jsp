<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@ page import="jp.groupsession.v2.enq.enq110.Enq110Const"%>
<%@ page import="jp.groupsession.v2.enq.enq210.Enq210Form"%>
<%@ page import="jp.groupsession.v2.enq.model.EnqMainListModel"%>
<%@ page import="jp.groupsession.v2.enq.enq110.Enq110Form"%>

<%
   Enq110Form thisForm = (Enq110Form) request.getAttribute("enq110Form");
   int mIdx = Integer.parseInt(request.getParameter("mIdx"));
   EnqMainListModel mainList = thisForm.getEnq110QueList(mIdx);
   pageContext.setAttribute("mainList", mainList);
%>

<% String[] quePrmName = {"emnSid", "eqmSeq", "eqmDspSec", "eqmQueKbn", "eqmRequire",
                                  "eqmRngStrNum", "eqmRngEndNum", "eqmRngStrDat", "eqmRngEndDat", "eqmQueSec",
                                  "eqsSeq", "eqsDspName", "eqmOther", "eqmUnitNum"}; %>
<% String[] ansPrmName = {"eqmAnsText", "eqmAnsTextarea", "eqmAnsNum", "eqmSelectAnsYear", "eqmSelectAnsMonth", "eqmSelectAnsDay", "eqmSelOther", "eqmSelRbn", "eqmSelChk", "eqmSelectAnsDateInput"}; %>

<% String mIndex = String.valueOf(mIdx); %>
<% String formName = "enq110QueList[" + mIndex + "]."; %>


<table class="w100 table-top">
  <tbody>
    <tr>
      <td class="p0">
        <div class="pl0 pr0">
          <table class="w100 bgC_header2">
            <tr>
              <td class="w3 border_none fw_b txt_l no_w">
                <gsmsg:write key="enq.37" />
              </td>
              <td class="w5 border_none fw_b">
                <logic:equal name="enq110Form" property="enq210queSeqType" value="0">
                  <bean:write name="mainList" property="eqmQueSec" />
                </logic:equal>
              </td>
              <td class="border_none fw_b txt_l">
                <bean:write name="mainList" property="eqmQuestion" />
              </td>
            </tr>
            <tr>
              <td rowspan="2" colspan="2" class="border_none fw_b txt_l txt_t ">
                <logic:equal name="mainList" property="<%= quePrmName[4] %>" value="<%= String.valueOf(GSConstEnquete.REQUIRE_ON) %>">
                  <span class="cl_fontWarn">
                    <gsmsg:write key="enq.28" />
                  </span>
                </logic:equal>
              </td>
              <td class="txt_l border_none fw_b">
                <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>">
                  <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_TOP) %>">
                    <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                      <img src='../enquete/enq110.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110Form" property="ansEnqSid" />&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                    </logic:equal>
                    <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                      <img src='../enquete/enq110.do?CMD=getPreTempFile&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />&enq110TempDir=<bean:write name="mainList" property="eqmAttachDir" />&enq110PreTempDirKbn=1' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0"
                        class="img_hoge">
                    </logic:notEqual>
                    <table>
                      <tr>
                        <td class="txt_c txt_l p0">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                        </td>
                        <td class="txt_l txt_m p0 pl5">
                          <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                          </logic:equal>
                          <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                          </logic:notEqual>
                          <span>
                            <bean:write name="mainList" property="eqmAttachName" />
                            <bean:write name="mainList" property="eqmAttachSize" />
                          </span>
                          </a>
                        </td>
                      </tr>
                    </table>
                  </logic:equal>
                </logic:equal>
                <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_FILE) %>">
                  <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_TOP) %>">
                    <table>
                      <tr>
                        <td class="txt_c txt_m border_none p0">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                        </td>
                        <td class="txt_l txt_m border_none p0 pl5">
                          <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                          </logic:equal>
                          <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                          </logic:notEqual>
                          <span class="textLink">
                            <bean:write name="mainList" property="eqmAttachName" />
                            <bean:write name="mainList" property="eqmAttachSize" />
                          </span>
                          </a>
                        </td>
                      </tr>
                    </table>
                  </logic:equal>
                </logic:equal>

                <htmlframe:write attrClass="w100">
                  <bean:write name="mainList" property="eqmDesc" filter="false" />
                </htmlframe:write>

                <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>">
                  <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_BOTTOM) %>">
                    <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                      <img src='../enquete/enq110.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110Form" property="ansEnqSid" />&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                    </logic:equal>
                    <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                      <img src='../enquete/enq110.do?CMD=getPreTempFile&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />&enq110TempDir=<bean:write name="mainList" property="eqmAttachDir" />&enq110PreTempDirKbn=1' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0"
                        class="img_hoge">
                    </logic:notEqual>
                    <table>
                      <tr>
                        <td class="txt_c txt_m border_none p0">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                        </td>
                        <td class="txt_l border_none txt_m p0 pl5">
                          <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                          </logic:equal>
                          <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                          </logic:notEqual>
                          <span class="textLink">
                            <bean:write name="mainList" property="eqmAttachName" />
                            <bean:write name="mainList" property="eqmAttachSize" />
                          </span>
                          </a>
                        </td>
                      </tr>
                    </table>
                  </logic:equal>
                </logic:equal>
                <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_FILE) %>">
                  <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_BOTTOM) %>">
                    <table>
                      <tr>
                        <td class="txt_m txt_c border_none p0">
                          <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                          <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                        </td>
                        <td class="border_none txt_l txt_m p0 pl5">
                          <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                          </logic:equal>
                          <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                            <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                          </logic:notEqual>
                          <span class="textLink">
                            <bean:write name="mainList" property="eqmAttachName" />
                            <bean:write name="mainList" property="eqmAttachSize" />
                          </span>
                          </a>
                        </td>
                      </tr>
                    </table>
                  </logic:equal>
                </logic:equal>
              </td>
            </tr>

            <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_INTEGER) %>">
              <!-- 数値の最小値、最大値 -->
              <tr>
                <td class="border_none txt_l" colspan="2">
                  <logic:notEmpty name="mainList" property="eqmRngStrNum">
                    <logic:notEmpty name="mainList" property="eqmRngEndNum">
                      <gsmsg:write key="cmn.asterisk" />
                      <bean:write name="mainList" property="<%= quePrmName[5] %>" />&nbsp;～&nbsp;<bean:write name="mainList" property="<%= quePrmName[6] %>" />&nbsp;<gsmsg:write key="enq.65" />
                    </logic:notEmpty>
                  </logic:notEmpty>
                </td>
              </tr>
            </logic:equal>


            <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_DAY) %>">
              <!-- 日付の最小値、最大値 -->
              <tr>
                <td class="border_none txt_l" colspan="2">
                  <logic:notEmpty name="mainList" property="eqmRngStrDat">
                    <logic:notEmpty name="mainList" property="eqmRngEndDat">
                      <gsmsg:write key="cmn.asterisk" />
                      <bean:write name="mainList" property="<%= quePrmName[7] %>" />&nbsp;～&nbsp;<bean:write name="mainList" property="<%= quePrmName[8] %>" />&nbsp;<gsmsg:write key="enq.65" />
                    </logic:notEmpty>
                  </logic:notEmpty>
                </td>
              </tr>
            </logic:equal>

          </table>
        </div>
        <div class="bgC_body">
          <table class="w90">
            <tr>
              <td class="w10 pt0 pt10 no_w txt_t txt_c border_none fw_b">
                <gsmsg:write key="enq.52" />
              </td>
              <td class="w80 txt_l border_none fw_b p10">

                <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_SINGLE) %>">
                  <!-- 単一 -->
                  <logic:notEmpty name="mainList" property="eqmSubList">
                    <logic:iterate id="subList" name="mainList" property="eqmSubList" indexId="sIdx">
                      <% String sIndex = String.valueOf(sIdx); %>
                      <% String sFormName = "eqmSubList[" + sIndex + "]."; %>
                      <% String radio = "radio_" + mIndex + "_" + sIndex; %>
                      <% String eqsSeq = "eqsSeq"; %>

                      <html:hidden property="<%= formName + sFormName + quePrmName[10] %>" />
                      <html:hidden property="<%= formName + sFormName + quePrmName[11] %>" />

                      <logic:notEqual name="subList" property="eqsSeq" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                        <bean:define id="rbnVal" name="subList" property="eqsSeq" />
                        <% String val = String.valueOf(rbnVal); %>
                        <div class="verAlignMid">
                            <html:radio property="<%=formName + ansPrmName[7]%>" value="<%=val%>" styleId="<%=radio%>" styleClass="enqSelLabel" />
                            <label class="ml1 mr10" for="<%=radio%>">
                              <bean:write name="subList" property="eqsDspName" />
                            </label>
                        </div>
                      </logic:notEqual>
                      <logic:equal name="subList" property="eqsSeq" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                        <br>
                        <div class="enqSelLabel txt_t">
                            <logic:equal name="mainList" property="eqmOther" value="<%= String.valueOf(GSConstEnquete.OTHER_TEXT) %>">
                              <span class="verAlignMid">
                              <html:radio property="<%= formName + ansPrmName[7] %>" value="-1" styleId="<%= radio %>" styleClass="enqSelLabel" />
                              <label class="mr10" for="<%= radio %>">
                                <gsmsg:write key="cmn.other" />
                              </label>
                              <html:text property="<%= formName + ansPrmName[6] %>" styleClass="ans_text_other" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_TEXT) %>" />
                             </span>
                            </logic:equal>
                            <logic:equal name="mainList" property="eqmOther" value="<%= String.valueOf(GSConstEnquete.OTHER_TEXTAREA) %>">
                              <span class="txt_t">
                              <span class="verAlignMid">
                              <html:radio property="<%= formName + ansPrmName[7] %>" value="-1" styleId="<%= radio %>" styleClass="enqSelLabel" />
                              <label for="<%= radio %>">
                                <gsmsg:write key="cmn.other" />
                              </label>
                              </span>
                              </span>
                              <html:textarea property="<%= formName + ansPrmName[6] %>" styleClass="ans_textarea_other" />
                            </logic:equal>
                        </div>
                      </logic:equal>

                    </logic:iterate>
                  </logic:notEmpty>
                </logic:equal>

                <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_MULTIPLE) %>">
                  <!-- 複数 -->
                  <logic:notEmpty name="mainList" property="eqmSubList">
                    <logic:iterate id="subList" name="mainList" property="eqmSubList" indexId="sIdx">
                      <% String sIndex = String.valueOf(sIdx); %>
                      <% String sFormName = "eqmSubList[" + sIndex + "]."; %>
                      <% String check = "check_" + mIndex + "_" + sIndex; %>
                      <% String eqsSeq = "eqsSeq"; %>

                      <html:hidden property="<%= formName + sFormName + quePrmName[10] %>" />
                      <html:hidden property="<%= formName + sFormName + quePrmName[11] %>" />

                      <logic:notEqual name="subList" property="eqsSeq" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                        <bean:define id="chkVal" name="subList" property="eqsSeq" />
                        <% String val = String.valueOf(chkVal); %>
                        <div class="verAlignMid">
                          <html:multibox property="<%= formName + ansPrmName[8] %>" value="<%= val %>" styleId="<%= check %>" />
                          <label class="mr10" for="<%= check %>">
                              <bean:write name="subList" property="eqsDspName" />
                          </label>
                        </div>
                      </logic:notEqual>
                      <logic:equal name="subList" property="eqsSeq" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                        <br>
                        <div class="enqSelLabel">
                            <logic:equal name="mainList" property="eqmOther" value="<%= String.valueOf(GSConstEnquete.OTHER_TEXT) %>">
                              <span class="verAlignMid">
                              <html:multibox property="<%= formName + ansPrmName[8] %>" value="-1" styleId="<%= check %>" styleClass="enqSelLabel" />
                              <label class="mr10" for="<%= check %>">
                                <gsmsg:write key="cmn.other" />
                              </label>
                              <html:text property="<%= formName + ansPrmName[6] %>" styleClass="ans_text_other ml5" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_TEXT) %>" />
                            </span>
                            </logic:equal>
                            <logic:equal name="mainList" property="eqmOther" value="<%= String.valueOf(GSConstEnquete.OTHER_TEXTAREA) %>">
                              <span class="txt_t verAlignMid">
                                <html:multibox property="<%= formName + ansPrmName[8] %>" value="-1" styleId="<%= check %>" styleClass="enqSelLabel" />
                                <label for="<%= check %>">
                              <gsmsg:write key="cmn.other" />
                              </span>
                              </label>
                              <html:textarea property="<%= formName + ansPrmName[6] %>" styleClass="ans_textarea_other ml5" />

                            </logic:equal>
                        </div>
                      </logic:equal>

                    </logic:iterate>
                  </logic:notEmpty>
                </logic:equal>

                <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXT) %>">
                  <!-- テキスト -->
                  <html:text property="<%= formName + ansPrmName[0] %>" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_TEXT) %>" styleClass="ans_text" />
                </logic:equal>

                <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXTAREA) %>">
                  <!-- 複数行 -->
                  <html:textarea property="<%= formName + ansPrmName[1] %>" styleClass="txt_m text_base ans_textarea" />
                </logic:equal>

                <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_INTEGER) %>">
                  <!-- 数値 -->
                  <html:text property="<%= formName + ansPrmName[2] %>" maxlength="<%= String.valueOf(GSConstEnquete.MAX_LEN_EAS_ANS_NUM) %>" styleClass="ans_text_num wp140" />
                  <bean:write name="mainList" property="eqmUnitNum" />
                </logic:equal>
                <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_DAY) %>">
                  <!-- 日付 -->
                  <% String selYear  = "selYear_"  + String.valueOf(mIdx); %>
                  <% String selMonth = "selMonth_" + String.valueOf(mIdx); %>
                  <% String selDay   = "selDay_"   + String.valueOf(mIdx); %>
                  <% String selDate = "selDate_"   + String.valueOf(mIdx); %>
                  <input type="hidden" name="<%= formName + ansPrmName[3] %>" id='<%= selYear %>' value="-1">
                  <input type="hidden" name="<%= formName + ansPrmName[4] %>" id='<%= selMonth %>' value="-1">
                  <input type="hidden" name="<%= formName + ansPrmName[5] %>" id='<%= selDay %>' value="-1">
                  <span class="display_flex">
                    <html:text name="enq110Form" property="<%= formName + ansPrmName[9] %>" styleId="<%= selDate %>" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker js_enqDate"/>
                    <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
                  </span>
                </logic:equal>
              </td>
            </tr>
          </table>
        </div>
      </td>
    </tr>
  </tbody>
</table>
