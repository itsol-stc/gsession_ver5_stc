<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>

<% String tdColor = request.getParameter("tdColor");%>
<logic:notEqual name="sch040Form" property="sch010SelectUsrKbn" value="1">
  <logic:notEmpty name="sch040Form" property="sch040AddedUsrLabel" scope="request">
    <logic:equal name="sch040Form" property="cmd" value="edit">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <!-- 同時修正 -->
        <tr id="addedUsrArea">
          <th class="w20 txt_l">
            <gsmsg:write key="schedule.32" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
          </th>
          <td class="w80 <%= tdColor %>">
            <span class="cl_fontWarn"><gsmsg:write key="schedule.12" /></span>
            <logic:iterate id="aurBean" name="sch040Form" property="sch040AddedUsrLabel" scope="request">
              <br>
              <logic:equal name="aurBean" property="usrUkoFlg" value="1">
                <span class="mukoUserOption">
                  <bean:write name="aurBean" property="usiSei" scope="page"/>　<bean:write name="aurBean" property="usiMei" scope="page"/>
                </span>
              </logic:equal>
              <logic:notEqual name="aurBean" property="usrUkoFlg" value="1">
                <bean:write name="aurBean" property="usiSei" scope="page"/>　<bean:write name="aurBean" property="usiMei" scope="page"/>
              </logic:notEqual>
            </logic:iterate>
            <br>
            <div class="verAlignMid">
              <html:radio name="sch040Form" property="sch040BatchRef" styleId="sch040BatchRef0" value="1" onclick="setDisabled();"/>
              <label for="sch040BatchRef0"><gsmsg:write key="schedule.34" /></label>
              <html:radio name="sch040Form" property="sch040BatchRef" styleId="sch040BatchRef1" value="0" onclick="setDisabled();" styleClass="ml10"/>
              <label for="sch040BatchRef1"><gsmsg:write key="schedule.33" /></label>
            </div>
          </td>
        </tr>
      </logic:notEqual>
    </logic:equal>
  </logic:notEmpty>
  <logic:empty name="sch040Form" property="sch040AddedUsrLabel" scope="request">
    <html:hidden property="sch040BatchRef" value="1"/>
  </logic:empty>

  <logic:notEqual name="sch040Form" property="schIkkatsuFlg" value="1">
  <tr>
    <th class="w20">
      <gsmsg:write key="schedule.117" />
    </th>
    <td class="w80 <%= tdColor %> pos_rel">
      <logic:notEqual name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <div class="pos_abs">
          <span class="cl_fontWarn fs_13">[<gsmsg:write key="schedule.29" />]</span>
        </div>
        <ui:multiselector name="sch040Form" property="sch040SameUserUI" styleClass="hp200" />
      </logic:notEqual>
      <logic:equal name="sch040Form" property="sch040EditDspMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstSchedule.EDIT_DSP_MODE_ANSWER) %>">
        <logic:notEmpty name="sch040Form" property="sch040SelectUsrLabel" scope="request">
          <logic:iterate id="urBean" name="sch040Form" property="sch040SelectUsrLabel" scope="request">
            <div>
              <logic:equal name="urBean" property="usrUkoFlg" value="1">
                <span class="mukoUserOption">
                  <bean:write name="urBean" property="usiSei" scope="page"/>&nbsp;<bean:write name="urBean" property="usiMei" scope="page"/>
                </span>
              </logic:equal>
              <logic:notEqual name="urBean" property="usrUkoFlg" value="1">
                <bean:write name="urBean" property="usiSei" scope="page"/>&nbsp;<bean:write name="urBean" property="usiMei" scope="page"/>
              </logic:notEqual>
            </div>
          </logic:iterate>
        </logic:notEmpty>
      </logic:equal>
    </td>
  </tr>
  </logic:notEqual>
</logic:notEqual>