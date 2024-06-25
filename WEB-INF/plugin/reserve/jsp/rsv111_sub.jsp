<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<%@ page import="jp.groupsession.v2.rsv.rsv310.Rsv310Form"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%
String maxLengthNaiyo = String.valueOf(GSConstReserve.MAX_LENGTH_NAIYO);
%>

<%-- 時間マスタ --%>
&nbsp;&nbsp;
<span>
  <a href="#!" onclick="setAmTime();">
    <gsmsg:write key="cmn.am" />
    <span class="tooltips">
      <bean:write name="rsv111Form" property="rsv110AmFrHour" format="00" />
      :
      <bean:write name="rsv111Form" property="rsv110AmFrMin" format="00" />
      ～
      <bean:write name="rsv111Form" property="rsv110AmToHour" format="00" />
      :
      <bean:write name="rsv111Form" property="rsv110AmToMin" format="00" />
    </span>
  </a>
</span>
&nbsp;&nbsp;
<span>
  <a href="#!" onclick="setPmTime();">
    <gsmsg:write key="cmn.pm" />
    <span class="tooltips">
      <bean:write name="rsv111Form" property="rsv110PmFrHour" format="00" />
      :
      <bean:write name="rsv111Form" property="rsv110PmFrMin" format="00" />
      ～
      <bean:write name="rsv111Form" property="rsv110PmToHour" format="00" />
      :
      <bean:write name="rsv111Form" property="rsv110PmToMin" format="00" />
    </span>
  </a>
</span>
&nbsp;&nbsp;
<span>
  <a href="#!" onclick="setAllTime();">
    <gsmsg:write key="cmn.allday" />
    <span class="tooltips">
      <bean:write name="rsv111Form" property="rsv110AllDayFrHour" format="00" />
      :
      <bean:write name="rsv111Form" property="rsv110AllDayFrMin" format="00" />
      ～
      <bean:write name="rsv111Form" property="rsv110AllDayToHour" format="00" />
      :
      <bean:write name="rsv111Form" property="rsv110AllDayToMin" format="00" />
    </span>
  </a>
</span>
</td>
</tr>
<html:hidden styleId="rsv111AmFrHour" property="rsv110AmFrHour" />
<html:hidden styleId="rsv111AmFrMin" property="rsv110AmFrMin" />
<html:hidden styleId="rsv111AmToHour" property="rsv110AmToHour" />
<html:hidden styleId="rsv111AmToMin" property="rsv110AmToMin" />
<html:hidden styleId="rsv111PmFrHour" property="rsv110PmFrHour" />
<html:hidden styleId="rsv111PmFrMin" property="rsv110PmFrMin" />
<html:hidden styleId="rsv111PmToHour" property="rsv110PmToHour" />
<html:hidden styleId="rsv111PmToMin" property="rsv110PmToMin" />
<html:hidden styleId="rsv111AllDayFrHour" property="rsv110AllDayFrHour" />
<html:hidden styleId="rsv111AllDayFrMin" property="rsv110AllDayFrMin" />
<html:hidden styleId="rsv111AllDayToHour" property="rsv110AllDayToHour" />
<html:hidden styleId="rsv111AllDayToMin" property="rsv110AllDayToMin" />
</td>
</tr>

<tr>
  <th class="no_w" colspan="2">
    <span>
      <gsmsg:write key="cmn.content" />
    </span>
  </th>
  <td class="txt_l">
    <%-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 --%>
    <textarea class="wp400" name="rsv111RsrBiko" rows="6" onkeyup="showLengthStr(value, <%=maxLengthNaiyo%>, 'inputlength');" id="inputstr"><bean:write name="rsv111Form" property="rsv111RsrBiko" /></textarea>
    <div>
      <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span>
      <span id="inputlength" class="formCounter">0</span>
      <span class="formCounter_max">/<%=maxLengthNaiyo%><gsmsg:write key="cmn.character" /></span>
    </div>
  </td>
</tr>

<tr>
  <th class="no_w" colspan="2">
    <span>
      <gsmsg:write key="cmn.edit.permissions" />
    </span>
  </th>
  <td class="txt_l">
    <span>
      <gsmsg:write key="cmn.comments" />
      <gsmsg:write key="reserve.89" />
      <br> ※
      <gsmsg:write key="reserve.90" />
      <br> &nbsp;&nbsp;
      <gsmsg:write key="reserve.91" />
    </span>
    <br>
    <br>
    <span class="verAlignMid">
      <html:radio styleId="lvl1" name="rsv111Form" property="rsv111RsrEdit" value="<%=String.valueOf(GSConstReserve.EDIT_AUTH_NONE)%>" />
      <label for="lvl1" class="mr10">
        <gsmsg:write key="cmn.nolimit" />
      </label>
      <html:radio styleId="lvl2" name="rsv111Form" property="rsv111RsrEdit" value="<%=String.valueOf(GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>" />
      <label for="lvl2"  class="mr10">
        <gsmsg:write key="cmn.only.principal.or.registant" />
      </label>
      <html:radio styleId="lvl3" name="rsv111Form" property="rsv111RsrEdit" value="<%=String.valueOf(GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>" />
      <label for="lvl3">
        <gsmsg:write key="cmn.only.affiliation.group.membership" />
      </label>
    </span>
  </td>
</tr>

<%-- 公開区分 --%>
<tr>
  <th class="no_w" colspan="2">
    <span>
      <gsmsg:write key="cmn.public.kbn" />
    </span>
  </th>
  <td class="txt_l">
    <div class="verAlignMid">
      <html:radio name="rsv111Form" property="rsv111RsrPublic" styleClass="js_public" styleId="rsv111Public0" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL)%>" />
      <span  class="mr10">
        <label for="rsv111Public0">
          <gsmsg:write key="cmn.public" />
        </label>
      </span>
    </div><!--
 --><div class="verAlignMid">
      <html:radio name="rsv111Form" property="rsv111RsrPublic" styleClass="js_public" styleId="rsv111Public1" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS)%>" />
      <span  class="mr10">
        <label for="rsv111Public1">
          <gsmsg:write key="reserve.175" />
        </label>
      </span>
    </div><!--
 --><div class="verAlignMid">
      <html:radio name="rsv111Form" property="rsv111RsrPublic" styleClass="js_public" styleId="rsv111Public4" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE)%>" />
      <span class="mr10">
        <label for="rsv111Public4">
          <gsmsg:write key="reserve.189" />
        </label>
      </span>
    </div><!--
 --><div class="verAlignMid">
      <html:radio name="rsv111Form" property="rsv111RsrPublic" styleClass="js_public" styleId="rsv111Public2" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP)%>" />
      <span class="mr10">
        <label for="rsv111Public2">
          <gsmsg:write key="reserve.176" />
        </label>
      </span>
    </div><!--
 --><div class="verAlignMid">
      <html:radio name="rsv111Form" property="rsv111RsrPublic" styleClass="js_public" styleId="rsv111Public3" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP) %>" />
      <span>
        <label for="rsv111Public3">
          <gsmsg:write key="reserve.187" />
        </label>
      </span>
    </div>
  </td>
</tr>
<tr class="js_selectUsrArea">
  <th class="no_w" colspan="2">
    <span>
      <gsmsg:write key="reserve.190" />
    </span>
    <span class="cl_fontWarn">
      <gsmsg:write key="cmn.comments" />
    </span>
  </th>
  <td>
    <ui:usrgrpselector name="rsv111Form" property="rsv111PubUsrGrpUI" styleClass="hp215" />
  </td>
</tr>