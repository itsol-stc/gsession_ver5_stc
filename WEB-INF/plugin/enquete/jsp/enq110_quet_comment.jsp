<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete"%>
<%@ page import="jp.groupsession.v2.enq.enq110.Enq110Const"%>
<%@ page import="jp.groupsession.v2.enq.enq210.Enq210Form"%>
<%@ page import="jp.groupsession.v2.enq.enq110.Enq110Form"%>
<%@ page import="jp.groupsession.v2.enq.model.EnqMainListModel"%>

<%
   Enq110Form thisForm = (Enq110Form) request.getAttribute("enq110Form");
   EnqMainListModel mainList = thisForm.getEnq110QueList(Integer.parseInt(request.getParameter("mIdx")));
   pageContext.setAttribute("mainList", mainList);
%>

<table class="w100 mt10 mb10">
  <tbody>
    <tr>
      <td>
        <logic:equal name="mainList" property="eqmLineKbn" value="<%= String.valueOf(GSConstEnquete.COMMENT_LINE_TOP) %>">
          <div class="w100 borderLine"></div>
        </logic:equal>
        <logic:equal name="mainList" property="eqmLineKbn" value="<%= String.valueOf(GSConstEnquete.COMMENT_LINE_UPDOWN) %>">
          <div class="w100 borderLine"></div>
        </logic:equal>
        <div>
          <table class="w100" cellpadding="5" cellspacing="0" border="0">
            <tr>
              <td class="txt_l">
                <span>
                  <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>">
                    <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_TOP) %>">
                      <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                        <img src='../enquete/enq110.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110Form" property="ansEnqSid" />&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                        <br>
                      </logic:equal>
                      <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                        <img src='../enquete/enq110.do?CMD=getPreTempFile&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />&enq110TempDir=<bean:write name="mainList" property="eqmAttachDir" />&enq110PreTempDirKbn=1' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                        <br>
                      </logic:notEqual>
                      <table>
                        <tr>
                          <td class="txt_l txt_c">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                          </td>
                          <td class="txt_l txt_c">
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
                          <td class="txt_l txt_c">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                          </td>
                          <td class="txt_l txt_c">
                            <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                              <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                            </logic:equal>
                            <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                              <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                            </logic:notEqual>
                            <span class="ml10">
                              <bean:write name="mainList" property="eqmAttachName" />
                              <bean:write name="mainList" property="eqmAttachSize" />
                            </span>
                            </a>
                          </td>
                        </tr>
                      </table>
                    </logic:equal>
                  </logic:equal>

                  <bean:write name="mainList" property="eqmDesc" filter="false" />

                  <logic:equal name="mainList" property="eqmAttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>">
                    <logic:equal name="mainList" property="eqmAttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_BOTTOM) %>">
                      <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                        <img src='../enquete/enq110.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110Form" property="ansEnqSid" />&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge">
                        <br>
                      </logic:equal>
                      <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                        <img src='../enquete/enq110.do?CMD=getPreTempFile&enq110BinSid=<bean:write name="mainList" property="eqmAttachId" />&enq110TempDir=<bean:write name="mainList" property="eqmAttachDir" />&enq110PreTempDirKbn=1' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0"
                          class="img_hoge">
                        <br>
                      </logic:notEqual>
                      <table>
                        <tr>
                          <td class="txt_l txt_c">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                          </td>
                          <td class="txt_l txt_c">
                            <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                              <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                            </logic:equal>
                            <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                              <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                            </logic:notEqual>
                            <span class="ml10">
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
                          <td class="txt_l txt_c">
                            <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                            <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                          </td>
                          <td class="txt_l txt_c">
                            <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                              <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 1);">
                            </logic:equal>
                            <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                              <a href="#!" onclick="fileLinkClick(<bean:write name='mainList' property='eqmAttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, <bean:write name="mainList" property="eqmAttachDir" />, 1);">
                            </logic:notEqual>
                            <span class="ml10">
                              <bean:write name="mainList" property="eqmAttachName" />
                              <bean:write name="mainList" property="eqmAttachSize" />
                            </span>
                            </a>
                          </td>
                        </tr>
                      </table>
                    </logic:equal>
                  </logic:equal>
                </span>
              </td>
            </tr>
          </table>
        </div>
        <logic:equal name="mainList" property="eqmLineKbn" value="<%= String.valueOf(GSConstEnquete.COMMENT_LINE_BOTTOM) %>">
          <div class="w100 borderLine"></div>
        </logic:equal>
        <logic:equal name="mainList" property="eqmLineKbn" value="<%= String.valueOf(GSConstEnquete.COMMENT_LINE_UPDOWN) %>">
          <div class="w100 borderLine"></div>
        </logic:equal>
      </td>
    </tr>
  </tbody>
</table>
