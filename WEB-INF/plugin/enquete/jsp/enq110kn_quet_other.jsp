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
<%@page import="jp.groupsession.v2.enq.model.EnqMainListModel"%>
<%@page import="jp.groupsession.v2.enq.enq110kn.Enq110knForm"%>

<%
   Enq110knForm thisForm = (Enq110knForm) request.getAttribute("enq110knForm");
   int mIdx = Integer.parseInt(request.getParameter("mIdx"));
   EnqMainListModel mainList = thisForm.getEnq110knQueList(mIdx);
   pageContext.setAttribute("mainList", mainList);
%>

<% String[] quePrmName = {"emnSid", "eqmSeq", "eqmDspSec", "eqmQueKbn", "eqmRequire",
                                  "eqmRngStrNum", "eqmRngEndNum", "eqmRngStrDat", "eqmRngEndDat", "eqmQueSec"}; %>
<% String[] ansPrmName = {"eqmAnsText", "eqmAnsTextarea", "eqmAnsNum", "eqmSelectAnsYear", "eqmSelectAnsMonth", "eqmSelectAnsDay", "eqmSelOther", "eqmSelRbn", "eqmSelChk"}; %>
<% String mIndex = String.valueOf(mIdx); %>
<% String knformName = "enq110knQueList[" + mIndex + "]."; %>


<table class="w100 table-top">
  <tr>
    <td class="p0" colspan="4">
      <div class="pl0 pr0">
        <table class="w100 bgC_header2">
          <tr>
            <td class="w3 border_none fw_b txt_l no_w">
              <gsmsg:write key="enq.37" />
            </td>
            <td class="w5 border_none fw_b">
              <bean:write name="mainList" property="eqmQueSec" />
            </td>
            <td class="border_none fw_b txt_l">
              <bean:write name="mainList" property="eqmQuestion" />
            </td>
          </tr>
          <tr>
            <td class="border_none fw_b txt_l txt_t" rowspan="2" colspan="2">
              <logic:equal name="mainList" property="<%= quePrmName[4] %>" value="<%= String.valueOf(GSConstEnquete.REQUIRE_ON) %>">
                <span class="cl_fontWarn">
                  <gsmsg:write key="enq.28" />
                </span>
              </logic:equal>
            </td>
            <td class="txt_l border_none fw_b">
              <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>">
                <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_TOP) %>">
                  <img src='../enquete/enq110kn.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110knForm" property="ansEnqSid" />&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                  <table>
                    <tr>
                      <td class="txt_l txt_m border_none">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                      </td>
                      <td class="txt_l txt_m border_none">
                        <a href="#!" onclick="fileLinkClickBin(<bean:write name='mainList' property='eqmAttachId' />);">
                          <span class="cl_linkDef">
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
                      <td class="txt_l txt_m border_none">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                      </td>
                      <td class="txt_l txt_m border_none">
                        <a href="#!" onclick="fileLinkClickBin(<bean:write name='mainList' property='eqmAttachId' />);">
                          <span class="cl_linkDef">
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
                  <img src='../enquete/enq110kn.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110knForm" property="ansEnqSid" />&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                  <table>
                    <tr>
                      <td class="txt_l border_none txt_m">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                      </td>
                      <td class="txt_l border_none txt_m">
                        <a href="#!" onclick="fileLinkClickBin(<bean:write name='mainList' property='eqmAttachId' />);">
                          <span class="cl_linkDef">
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
                      <td class="txt_l txt_m border_none">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                      </td>
                      <td class="txt_l txt_m border_none">
                        <a href="#!" onclick="fileLinkClickBin(<bean:write name='mainList' property='eqmAttachId' />);">
                          <span class="cl_linkDef">
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
        <table class="w80">
          <tr>
            <td class="w10 txt_c border_none">
              <gsmsg:write key="enq.52" />
            </td>
            <td class="txt_l border_none">

              <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_SINGLE) %>">
                <!-- 単一 -->
                <logic:notEqual name="mainList" property="eqmSelRbn" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                  <bean:write name="mainList" property="eqmSelRbnName" />
                </logic:notEqual>
                <logic:equal name="mainList" property="eqmSelRbn" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                  <gsmsg:write key="cmn.other" />[<bean:write name="mainList" property="eqmSelOther" filter="false" />]
                      </logic:equal>
                <logic:empty name="mainList" property="eqmSelRbn">
                  <gsmsg:write key="enq.38" />
                </logic:empty>

              </logic:equal>

              <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_MULTIPLE) %>">
                <!-- 複数 -->
                <logic:notEmpty name="mainList" property="eqmSelChkName">
                  <logic:iterate id="subList" name="mainList" property="eqmSelChkName">
                    <logic:notEqual name="subList" property="value" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                      <bean:write name="subList" property="label" />
                      <br>
                    </logic:notEqual>
                    <logic:equal name="subList" property="value" value="<%= String.valueOf(GSConstEnquete.CHOICE_KBN_OTHER) %>">
                      <gsmsg:write key="cmn.other" />[<bean:write name="mainList" property="eqmSelOther" filter="false" />]
                          </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
                <logic:empty name="mainList" property="eqmSelChkName">
                  <logic:empty name="mainList" property="eqmSelOther">
                    <gsmsg:write key="enq.38" />
                  </logic:empty>
                </logic:empty>
              </logic:equal>

              <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXT) %>">
                <!-- テキスト -->
                <bean:write name="mainList" property="<%= ansPrmName[0] %>" />
                <logic:empty name="mainList" property="<%= ansPrmName[0] %>">
                  <gsmsg:write key="enq.38" />
                </logic:empty>
              </logic:equal>

              <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_TEXTAREA) %>">
                <!-- 複数行 -->
                <bean:write name="mainList" property="<%= ansPrmName[1] %>" filter="false" />
                <logic:empty name="mainList" property="<%= ansPrmName[1] %>">
                  <gsmsg:write key="enq.38" />
                </logic:empty>
              </logic:equal>

              <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_INTEGER) %>">
                <!-- 数値 -->
                <logic:notEmpty name="mainList" property="<%= ansPrmName[2] %>">
                  <bean:write name="mainList" property="<%= ansPrmName[2] %>" />&nbsp;<bean:write name="mainList" property="eqmUnitNum" />
                </logic:notEmpty>
                <logic:empty name="mainList" property="<%= ansPrmName[2] %>">
                  <gsmsg:write key="enq.38" />
                </logic:empty>
              </logic:equal>

              <logic:equal name="mainList" property="eqmQueKbn" value="<%= String.valueOf(GSConstEnquete.SYURUI_DAY) %>">
                <!-- 日付 -->
                <logic:notEqual name="mainList" property="<%= ansPrmName[3] %>" value="-1">
                  <!--<bean:write name="mainList" property="<%= ansPrmName[3] %>" /><gsmsg:write key="cmn.year2" /><bean:write name="mainList" property="<%= ansPrmName[4] %>" /><gsmsg:write key="cmn.month" /><bean:write name="mainList" property="<%= ansPrmName[5] %>" /><gsmsg:write key="cmn.day" />-->
                  <bean:write name="mainList" property="eqmSelectAnsDate" />
                </logic:notEqual>
                <logic:equal name="mainList" property="<%= ansPrmName[3] %>" value="-1">
                  <gsmsg:write key="enq.38" />
                </logic:equal>
              </logic:equal>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
</table>
