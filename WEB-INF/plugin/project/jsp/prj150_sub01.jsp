<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.prj.prj150.Prj150MemberForm"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<bean:define id="prj150MovedDspId" name="prj150Form" property="movedDspId" />
<% boolean movePrjFlg = prj150MovedDspId != null && prj150MovedDspId.equals(jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ030); %>

<% if (movePrjFlg) { %>
<html:hidden property="prj150naibuInitFlg" />
<div class="component_bothEnd mb10">
  <div>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onClick="return buttonPush('prj150up');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="return buttonPush('prj150down');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <div>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="buttonPush('<%= jp.groupsession.v2.prj.prj150.Prj150Action.CMD_ADD_CLICK %>');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>
    <logic:notEmpty name="prj150Form" property="member">
      <button type="button" name="btn_tododel" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('<%= jp.groupsession.v2.prj.prj150.Prj150Action.CMD_DEL_CLICK %>');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:notEmpty>
  </div>
</div>
<% } %>

    <logic:notEqual name="prj150Form" property="movedDspId" value="<%=back140%>">
        <logic:equal name="prj150Form" property="prj150cmdMode" value="<%=modeNaibu%>">
          <ul class="tabHeader w100">
            <li class="tabHeader_tab-on mwp100 pl10 pr10 bgI_none" onclick="changeTab('<%=tabNaibuClick%>');">
              <gsmsg:write key="project.prj150.9" />
            </li>
            <li class="tabHeader_tab-off mwp100 pl10 pr10 border_bottom_none" onclick="changeTab('<%=tabGaibuClick%>');">
              <gsmsg:write key="project.prj150.10" />
            </li>
            <li class="tabHeader_space border_bottom_none"></li>
          </ul>
        </logic:equal>

        <logic:equal name="prj150Form" property="prj150cmdMode" value="<%=modeGaibu%>">
          <ul class="tabHeader w100">
            <li class="tabHeader_tab-off mwp100 pl10 pr10 border_bottom_none" onclick="changeTab('<%=tabNaibuClick%>');">
              <gsmsg:write key="project.prj150.9" />
            </li>
            <li class="tabHeader_tab-on mwp100 pl10 pr10 bgI_none" onclick="changeTab('<%=tabGaibuClick%>');">
              <gsmsg:write key="project.prj150.10" />
            </li>
            <li class="tabHeader_space border_bottom_none"></li>
          </ul>
        </logic:equal>
      </logic:notEqual>

<table class="table-top mt0">

    <tr>

      <%
        String memIdWidth = "55";
        if (movePrjFlg) {
          memIdWidth = "50";
      %>
      <th id="naibu" class="txt_c js_tableTopCheck js_tableTopCheck-header cursor_p">
        <input type="checkbox" name="prj150naibuAllCheck" value="1" onClick="chgCheckAll('prj150naibuAllCheck', 'prj150naibuSelectMemberSid');">
      </th>
      <th class="txt_c"></th>
      <% } %>

      <th class="w5 txt_r no_w">
        <span>Ｎｏ</span>
      </th>
      <th class="w35 txt_c">
        <span>
          <gsmsg:write key="cmn.name" />
        </span>
      </th>
      <th class="w<%= memIdWidth %> txt_c no_w">
        <span>
          <gsmsg:write key="project.3" />
        </span>
      </th>
    </tr>
      <logic:notEmpty name="prj150Form" property="member">

    <logic:iterate name="prj150Form" property="member" scope="request" id="member" indexId="index" type="Prj150MemberForm ">

      <%
          String idx = index.toString();
          String usr = "usr" + idx;
        %>

      <html:hidden name="member" property="usrSid" indexed="true" styleId="<%= usr %>" />
      <html:hidden name="member" property="rowNumber" indexed="true" />

      <tr>
        <% if (movePrjFlg) { %>
        <td class="txt_c js_tableTopCheck cursor_p">
          <html:multibox name="prj150Form" property="prj150naibuSelectMemberSid">
            <bean:write name="member" property="usrSid" />
          </html:multibox>
        </td>

        <bean:define id="atiValue" name="member" property="sort" />
        <td class="txt_c js_radioCheck cursor_p">
          <html:radio property="prj150SortRadio" value="<%= String.valueOf(atiValue) %>" />
        </td>
        <% } %>
        </td>
        <td class="txt_r">
          <bean:write name="member" property="rowNumber" />
        </td>
        <td class="txt_l">
          <a class="<%=UserUtil.getCSSClassNameNormal(member.getUsrUkoFlg())%>" href="#!" onClick="return openUserInfoWindow('<bean:write name="member" property="usrSid" />');">
            <bean:write name="member" property="usrName" />
          </a>
        </td>
        <td class="txt_l">

          <%
              String keyId = "key" + idx;
              String keyIdSv = "keySv" + idx;
          %>

          <html:text name="member" property="projectMemberKey" maxlength="20" styleClass="wp200" indexed="true" styleId="<%= keyId %>" />
          <html:hidden name="member" property="projectMemberKeySv" indexed="true" styleId="<%= keyIdSv %>" />

        </td>
      </tr>

    </logic:iterate>
  </logic:notEmpty>
</table>

