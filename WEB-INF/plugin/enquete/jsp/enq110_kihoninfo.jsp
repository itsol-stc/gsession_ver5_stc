<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.enq.GSConstEnquete" %>
<%@ page import="jp.groupsession.v2.enq.enq110.Enq110Const" %>
<%@ page import="jp.groupsession.v2.enq.enq210.Enq210Form" %>
<%@ taglib tagdir="/WEB-INF/tags/htmlframe" prefix="htmlframe" %>

      <table class="w100 table-left txt_c" cellpadding="5" cellspacing="0">

        <!-- 基本情報 重要度 -->
        <tr>
          <th><gsmsg:write key="enq.24" /></th>
          <td class="w30 no_w">
            <logic:equal name="enq110Form" property="enq110PriKbn" value="<%= String.valueOf(GSConstEnquete.JUUYOU_0) %>">
              <img class="classic-display" src="../common/images/classic/icon_star_blue.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
              <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
              <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.33" />">
            <span class="original-display">
              <i class="icon-star importance-blue fs_17"></i>
              <i class="icon-star_line fs_17"></i>
              <i class="icon-star_line fs_17"></i>
            </span>
            </logic:equal>
            <logic:equal name="enq110Form" property="enq110PriKbn" value="<%= String.valueOf(GSConstEnquete.JUUYOU_1) %>">
               <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
               <img class="classic-display" src="../common/images/classic/icon_star_gold.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
               <img class="classic-display" src="../common/images/classic/icon_star_white.png" class="star" border="0" alt="<gsmsg:write key="enq.34" />">
               <span class="original-display">
                 <i class="icon-star importance-yellow fs_17"></i>
                 <i class="icon-star importance-yellow fs_17"></i>
                 <i class="icon-star_line fs_17"></i>
               </span>
            </logic:equal>
            <logic:equal name="enq110Form" property="enq110PriKbn" value="<%= String.valueOf(GSConstEnquete.JUUYOU_2) %>">
               <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
               <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
               <img class="classic-display" src="../common/images/classic/icon_star_red.png" class="star" border="0" alt="<gsmsg:write key="enq.35" />">
               <span class="original-display">
                 <i class="icon-star importance-red fs_17"></i>
                 <i class="icon-star importance-red fs_17"></i>
                 <i class="icon-star importance-red fs_17"></i>
               </span>

            </logic:equal>
          </td>
          <!-- 基本情報 発信者 -->
          <th class="no_w"><gsmsg:write key="enq.25" /></th>
          <td class="w50 no_w">
            <bean:define id="sdFlg" name="enq110Form" property="enq110SendNameDelFlg" type="java.lang.Boolean" />
            <bean:define id="ukoFlg" name="enq110Form" property="enq110SendUkoFlg" type="java.lang.Integer"/>
            <span class="<% if (sdFlg) { %> text_deluser_enq<% } else if (ukoFlg == 1) { %> mukoUser<% } %>">
              <logic:notEqual name="enq110Form" property="enq210editMode" value="<%= String.valueOf(Enq210Form.EDITMODE_TEMPLATE) %>" >
                <bean:write name="enq110Form" property="enq110SendName" />
              </logic:notEqual>
            </span>
          </td>
        </tr>

        <!-- 基本情報 アンケート内容 -->
        <tr>
          <th class="no_w"><gsmsg:write key="enq.26" /></th>
          <td colspan="3">
          <span>
            <logic:equal name="enq110Form" property="enq110AttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>" >
              <logic:equal name="enq110Form" property="enq110AttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_TOP) %>" >
                <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                  <img src='../enquete/enq110.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110Form" property="ansEnqSid" />&enq110BinSid=<bean:write name="enq110Form" property="enq110AttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge"><br>
                </logic:equal>
                <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                  <img src='../enquete/enq110.do?CMD=getPreTempFile&enq110BinSid=<bean:write name="enq110Form" property="enq110AttachId" />&enq110PreTempDirKbn=0' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge"><br>
                </logic:notEqual>
                <table>
                  <tr>
                    <td class="txt_l txt_r border_none">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                    </td>

                    <td class="txt_l txt_r border_none">
                      <a href="#!" onclick="fileLinkClick(<bean:write name='enq110Form' property='enq110AttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 0);">
                        <span class="textLink"><bean:write name="enq110Form" property="enq110AttachName" /><bean:write name="enq110Form" property="enq110AttachSize" /></span>
                      </a><br>
                    </td>
                  </tr>
                </table>
              </logic:equal>
            </logic:equal>
            <logic:equal name="enq110Form" property="enq110AttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_FILE) %>" >
              <logic:equal name="enq110Form" property="enq110AttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_TOP) %>" >
                <table>
                  <tr>
                    <td class="txt_l txt_r border_none">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                    </td>
                    <td class="txt_l txt_m border_none">
                      <a href="#!" onclick="fileLinkClick(<bean:write name='enq110Form' property='enq110AttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 0);">
                        <span class="textLink"><bean:write name="enq110Form" property="enq110AttachName" /><bean:write name="enq110Form" property="enq110AttachSize" /></span>
                      </a><br>
                    </td>
                  </tr>
                </table>
              </logic:equal>
            </logic:equal>

            <htmlframe:write attrClass="w100">
              <bean:write name="enq110Form" property="enq110Desc" filter="false" />
            </htmlframe:write>

            <logic:equal name="enq110Form" property="enq110AttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_IMAGE) %>" >
              <logic:equal name="enq110Form" property="enq110AttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_BOTTOM) %>" >
                <logic:equal name="enq110Form" property="enq110DspMode" value="0">
                  <img src='../enquete/enq110.do?CMD=getImageFile&ansEnqSid=<bean:write name="enq110Form" property="ansEnqSid" />&enq110BinSid=<bean:write name="enq110Form" property="enq110AttachId" />' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge"><br>
                </logic:equal>
                <logic:notEqual name="enq110Form" property="enq110DspMode" value="0">
                  <img src='../enquete/enq110.do?CMD=getPreTempFile&enq110BinSid=<bean:write name="enq110Form" property="enq110AttachId" />&enq110PreTempDirKbn=0' name="enqImgName" alt="<gsmsg:write key='cmn.image' />" border="0" class="img_hoge"><br>
                </logic:notEqual>
                <table>
                  <tr>
                    <td class="txt_l txt_m border_none">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                    </td>
                    <td class="txt_l txt_m border_none">
                      <a href="#!" onclick="fileLinkClick(<bean:write name='enq110Form' property='enq110AttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 0);">
                        <span class="textLink"><bean:write name="enq110Form" property="enq110AttachName" /><bean:write name="enq110Form" property="enq110AttachSize" /></span>
                      </a><br>
                    </td>
                  </tr>
                </table>
              </logic:equal>
            </logic:equal>
            <logic:equal name="enq110Form" property="enq110AttachKbn" value="<%= String.valueOf(GSConstEnquete.TEMP_FILE) %>" >
              <logic:equal name="enq110Form" property="enq110AttachPos" value="<%= String.valueOf(GSConstEnquete.TEMP_POS_BOTTOM) %>" >
                <table>
                  <tr>
                    <td class="txt_l txt_m border_none">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key='cmn.file'/>">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key='cmn.file' />">
                    </td>
                    <td class="txt_l txt_m border_none">
                      <a href="#!" onclick="fileLinkClick(<bean:write name='enq110Form' property='enq110AttachId' />, <bean:write name="enq110Form" property="enq110DspMode" />, 0);">
                        <span class="textLink"><bean:write name="enq110Form" property="enq110AttachName" /><bean:write name="enq110Form" property="enq110AttachSize" /></span>
                      </a><br>
                    </td>
                  </tr>
                </table>
              </logic:equal>
            </logic:equal>
          </span>
          </td>
        </tr>

        <!-- 基本情報 回答期限 -->
        <logic:notEqual name="enq110Form" property="enq210editMode" value="<%= String.valueOf(Enq210Form.EDITMODE_TEMPLATE) %>" >
        <tr>
          <th class="no_w"><gsmsg:write key="enq.19" /></th>
          <td class="no_w">
            <span><bean:write name="enq110Form" property="enq110ResEnd" /></span>
          </td>
          <!-- 基本情報 結果公開期限 -->
          <th class="no_w"><gsmsg:write key="enq.enq210.11" /></th>
          <td class="no_w">
        <bean:define id="ansOpen" name="enq110Form" property="enq110AnsOpen" type="java.lang.Integer" />
        <% if (ansOpen == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {%>
            <span><bean:write name="enq110Form" property="enq110AnsPubStr" /></span>
            &nbsp;～&nbsp;
            <logic:empty name="enq110Form" property="enq110OpenEnd">
            <gsmsg:write key="main.man200.9" />
            </logic:empty>
            <logic:notEmpty name="enq110Form" property="enq110OpenEnd">
            <span><bean:write name="enq110Form" property="enq110OpenEnd" /></span>
            </logic:notEmpty>
          </td>
         <% } else { %>
          <gsmsg:write key="cmn.private" />
         <% } %>
        </tr>
        </logic:notEqual>

        <!-- 基本情報 注意事項 -->
        <tr>
          <th class="no_w"><gsmsg:write key="enq.27" /></th>
          <td colspan="3" class="no_w">
            <span>
              <bean:define id="anony" name="enq110Form" property="enq110Anony" type="java.lang.Integer" />
              <bean:define id="ansOpen" name="enq110Form" property="enq110AnsOpen" type="java.lang.Integer" />
              <% if (anony == GSConstEnquete.ANONYMUS_ON && ansOpen == GSConstEnquete.KOUKAI_ON) { %>
                <gsmsg:write key="enq.69" />
              <% } else if (anony == GSConstEnquete.ANONYMUS_ON) { %>
                <gsmsg:write key="enq.31" />
              <% } else if (ansOpen == GSConstEnquete.KOUKAI_ON) { %>
                <gsmsg:write key="enq.32" />
              <% } %>
            </span>
          </td>
        </tr>

      </table>
