<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<script src="../reserve/js/rsvschedule.js?<%=GSConst.VERSION_PARAM%>"></script>
<%
String maxLengthNaiyo = String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.MAX_LENGTH_NAIYO);
%>

<tr>
  <th class="no_w" rowspan="2">
    <span>
      <gsmsg:write key="cmn.period" />
    </span>
  </th>
  <th class="no_w">
    <span>
      <gsmsg:write key="cmn.start" />
    </span>
  </th>
  <td class="txt_l">

    <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
      <span>
        <bean:write name="rsv110Form" property="yoyakuFrString" />
      </span>
    </logic:equal>
    <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
      <span>
        <bean:write name="rsv110Form" property="yoyakuFrString" />
      </span>
    </logic:notEqual>

    <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
      <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
        <div class="verAlignMid">
          <html:text name="rsv110Form" property="rsv110SelectedDateFr" maxlength="10" styleClass="txt_c wp95 datepicker js_frDatePicker" styleId="selDatefr" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanStart"></span>
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="rsv110Form" property="rsv110SelectedTimeFr" styleId="fr_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="fr_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveFromDay($('#selDatefr')[0], 1)">
            <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left"></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onclick="return moveFromDay($('#selDatefr')[0], 2)">
            <gsmsg:write key='cmn.today' />
          </button>
          <span>
            <a class="fw_b todayBtn original-display" onclick="return moveFromDay($('#selDatefr')[0], 2)">
              <gsmsg:write key='cmn.today' />
            </a>
          </span>
          <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveFromDay($('#selDatefr')[0], 3)">
            <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right"></i>
          </button>
        </div>
        <br>
        <div class="verAlignMid">
      </logic:notEqual>
    </logic:equal>

    <%-- 時間マスタ --%>
    <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
      <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
      <span>
          <a href="#!" onclick="setAmTime();">
            <gsmsg:write key="cmn.am" />
            <span class="tooltips">
              <bean:write name="rsv110Form" property="rsv110AmFrHour" format="00" />
              :
              <bean:write name="rsv110Form" property="rsv110AmFrMin" format="00" />
              ～
              <bean:write name="rsv110Form" property="rsv110AmToHour" format="00" />
              :
              <bean:write name="rsv110Form" property="rsv110AmToMin" format="00" />
            </span>
          </a>
        </span>
      &nbsp;&nbsp;
      <span>
          <a href="#!" onclick="setPmTime();">
            <gsmsg:write key="cmn.pm" />
            <span class="tooltips">
              <bean:write name="rsv110Form" property="rsv110PmFrHour" format="00" />:
              <bean:write name="rsv110Form" property="rsv110PmFrMin" format="00" />
              ～
              <bean:write name="rsv110Form" property="rsv110PmToHour" format="00" />:
              <bean:write name="rsv110Form" property="rsv110PmToMin" format="00" />
            </span>
          </a>
        </span>
      &nbsp;&nbsp;
      <span>
          <a href="#!" onclick="setAllTime();">
            <gsmsg:write key="cmn.allday" />
            <span class="tooltips">
              <bean:write name="rsv110Form" property="rsv110AllDayFrHour" format="00" />:
              <bean:write name="rsv110Form" property="rsv110AllDayFrMin" format="00" />
              ～
              <bean:write name="rsv110Form" property="rsv110AllDayToHour" format="00" />:
              <bean:write name="rsv110Form" property="rsv110AllDayToMin" format="00" />
            </span>
          </a>
        </span>
      </logic:equal>
    </logic:notEqual>
    </div>
  </td>
</tr>

<tr>
  <th class="no_w">
    <span>
      <gsmsg:write key="cmn.end" />
    </span>
  </th>
  <td class="txt_l">

    <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
      <span>
        <bean:write name="rsv110Form" property="yoyakuToString" />
      </span>
    </logic:equal>

    <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
      <span>
        <bean:write name="rsv110Form" property="yoyakuToString" />
      </span>
    </logic:notEqual>

    <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
      <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
        <span class="verAlignMid">

          <%-- 期間 --%>
          <html:text name="rsv110Form" property="rsv110SelectedDateTo" maxlength="10" styleClass="txt_c wp95 datepicker js_toDatePicker" styleId="selDateto" />
          <span class="picker-acs icon-date display_flex cursor_pointer iconKikanFinish"></span>
          <span class="clockpicker_fr ml5 pos_rel display_flex input-group">
            <html:text name="rsv110Form" property="rsv110SelectedTimeTo" styleId="to_clock" maxlength="5" styleClass="clockpicker js_clockpicker txt_c wp60"/>
            <label for="to_clock" class="picker-acs cursor_pointer icon-clock input-group-addon"></label>
          </span>
          <button type="button" class="webIconBtn ml5" value="&nbsp;" onclick="return moveToDay($('#selDateto')[0], 1)">
            <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_l.png">
            <i class="icon-paging_left"></i>
          </button>
          <button type="button" class="baseBtn classic-display" value="<gsmsg:write key='cmn.today' />" onclick="return moveToDay($('#selDateto')[0], 2)">
            <gsmsg:write key='cmn.today' />
          </button>
          <span>
            <a class="fw_b todayBtn original-display" onclick="return moveToDay($('#selDateto')[0], 2)">
              <gsmsg:write key='cmn.today' />
            </a>
          </span>
          <button type="button" class="webIconBtn" value="&nbsp;" onclick="return moveToDay($('#selDateto')[0], 3)">
            <img class="btn_classicImg-display m0" src="../common/images/classic/icon_arrow_r.png">
            <i class="icon-paging_right"></i>
          </button>
        </span>
        <div>
          
        </div>
      </logic:notEqual>
    </logic:equal>
  </td>
</tr>

<tr>
  <th colspan="2" class="no_w">
    <span>
      <gsmsg:write key="cmn.content" />
    </span>
  </th>
  <td class="txt_l">
    <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
      <span>
        <bean:write name="rsv110Form" property="rsv110Naiyo" filter="false" />
      </span>
    </logic:notEqual>

    <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
      <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
        <%-- textareaの先頭行に入力した改行を反映させるため、開始タグの直後には必ず改行をいれること。 --%>
        <textarea class="wp400" name="rsv110Naiyo" rows="5" onkeyup="showLengthStr(value, <%=maxLengthNaiyo%>, 'inputlength');" id="inputstr"><bean:write name="rsv110Form" property="rsv110Naiyo" /></textarea>
        <div>
        <span class="formCounter">
          <gsmsg:write key="cmn.current.characters" />:
        </span>
        <span id="inputlength" class="formCounter">0</span>
        <span class="formCounter_max">&nbsp;/&nbsp;<%=maxLengthNaiyo%>
          <gsmsg:write key="cmn.character" />
        </span>
        </div>
      </logic:notEqual>
      <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
        <span>
          <bean:write name="rsv110Form" property="rsv110Naiyo" filter="false" />
        </span>
      </logic:equal>
    </logic:equal>
  </td>
</tr>

<%-- 編集権限 --%>
<tr>
  <th colspan="2" class="no_w">
    <span>
      <gsmsg:write key="cmn.edit.permissions" />
    </span>
  </th>
  <td class="txt_l">
    <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
      <span>
        <logic:equal name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_NONE)%>">
          <gsmsg:write key="cmn.nolimit" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>">
          <gsmsg:write key="cmn.only.principal.or.registant" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>">
          <gsmsg:write key="cmn.only.affiliation.group.membership" />
        </logic:equal>
      </span>
    </logic:notEqual>

    <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
      <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
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
          <html:radio styleId="lvl1" name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_NONE)%>" />
          <label for="lvl1" class="mr10">
            <gsmsg:write key="cmn.nolimit" />
          </label>
          <html:radio styleId="lvl2" name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>" />
          <label for="lvl2" class="mr10">
            <gsmsg:write key="cmn.only.principal.or.registant" />
          </label>
          <html:radio styleId="lvl3" name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>" />
          <label for="lvl3">
            <gsmsg:write key="cmn.only.affiliation.group.membership" />
          </label>
        </span>
      </logic:notEqual>
      <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
        <span>
          <logic:equal name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_NONE)%>">
            <gsmsg:write key="cmn.nolimit" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_PER_AND_ADU)%>">
            <gsmsg:write key="cmn.only.principal.or.registant" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110RsyEdit" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.EDIT_AUTH_GRP_AND_ADU)%>">
            <gsmsg:write key="cmn.only.affiliation.group.membership" />
          </logic:equal>
        </span>
      </logic:equal>
    </logic:equal>
  </td>
</tr>

<%-- 公開区分 --%>
<tr>
  <th class="no_w" colspan="2">
    <span>
      <gsmsg:write key="cmn.public.kbn" />
    </span>
  </th>
  <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
    <td>
      <span>
        <logic:equal name="rsv110Form" property="rsv110Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL) %>">
          <gsmsg:write key="cmn.public" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS) %>">
          <gsmsg:write key="reserve.175" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP) %>">
          <gsmsg:write key="reserve.176" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP) %>">
          <gsmsg:write key="reserve.187" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Public" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE) %>">
          <gsmsg:write key="reserve.189" />
        </logic:equal>
      </span>
    </td>
  </logic:notEqual>
  <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
    <td class="txt_l">
      <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP) %>">
      <div class="verAlignMid">
        <html:radio name="rsv110Form" property="rsv110Public" styleClass="js_public" styleId="rsv110Public0" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL) %>" />
        <span class="mr10">
          <label for="rsv110Public0">
            <gsmsg:write key="cmn.public" />
          </label>
        </span>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv110Form" property="rsv110Public" styleClass="js_public" styleId="rsv110Public1" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS) %>" />
        <span class="mr10">
          <label for="rsv110Public1">
            <gsmsg:write key="reserve.175" />
          </label>
        </span>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv110Form" property="rsv110Public" styleClass="js_public" styleId="rsv110Public4" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE) %>" />
        <span class="mr10">
          <label for="rsv110Public4">
            <gsmsg:write key="reserve.189" />
          </label>
        </span>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv110Form" property="rsv110Public" styleClass="js_public" styleId="rsv110Public2" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP) %>" />
        <span class="mr10">
          <label for="rsv110Public2">
            <gsmsg:write key="reserve.176" />
          </label>
        </span>
      </div><!--
   --><div class="verAlignMid">
        <html:radio name="rsv110Form" property="rsv110Public" styleClass="js_public" styleId="rsv110Public3" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP) %>" />
        <span>
          <label for="rsv110Public3">
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
      <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
        <span class="cl_fontWarn">
          <gsmsg:write key="cmn.comments" />
        </span>
      </logic:equal>
    </th>
    <td>
      <ui:usrgrpselector name="rsv110Form" property="rsv110PubUsrGrpUI" styleClass="hp215" />

      </logic:notEqual>
      <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
        <span>
          <logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_ALL)%>">
            <gsmsg:write key="cmn.public" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_PLANS)%>">
            <gsmsg:write key="reserve.175" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_TITLE)%>">
            <gsmsg:write key="reserve.189" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_GROUP)%>">
            <gsmsg:write key="reserve.176" />
          </logic:equal>
          <logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP)%>">
            <gsmsg:write key="reserve.187" />
          </logic:equal>
        </span>
        <logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP)%>">
        </td>
        </tr>
        <tr>
          <th class="no_w" colspan="2"><gsmsg:write key="reserve.190" /></th>
          <td>
            <logic:notEmpty name="rsv110Form" property="rsv110PubUsrGrpList">
              <logic:iterate id="usrMdl" name="rsv110Form" property="rsv110PubUsrGrpList">
                <div><bean:write name="usrMdl" property="label" /><div>
              </logic:iterate>
            </logic:notEmpty>
        </logic:equal>
      </logic:equal>
    </td>
  </logic:equal>
</tr>
<logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
<logic:equal name="rsv110Form" property="rsv110Public" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PUBLIC_KBN_USRGRP)%>">
<tr>
  <th class="no_w" colspan="2"><gsmsg:write key="reserve.190" /></th>
  <td>
    <logic:notEmpty name="rsv110Form" property="rsv110PubUsrGrpList">
      <logic:iterate id="usrMdl" name="rsv110Form" property="rsv110PubUsrGrpList">
        <div><bean:write name="usrMdl" property="label" /><div>
      </logic:iterate>
    </logic:notEmpty>
  </td>
</tr>
</logic:equal>
</logic:notEqual>
