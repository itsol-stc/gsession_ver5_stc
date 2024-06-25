<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<bean:define id="prj150MovedDspId" name="prj150Form" property="movedDspId" />
<% boolean movePrjFlg = prj150MovedDspId != null && prj150MovedDspId.equals(jp.groupsession.v2.prj.GSConstProject.SCR_ID_PRJ030); %>

<logic:equal name="prj150Form" property="addressPluginKbn" value="<%=String.valueOf(jp.groupsession.v2.prj.GSConstProject.PLUGIN_USE)%>">
  <%
    if (movePrjFlg) {
  %>
  <html:hidden property="prj150gaibuInitFlg" />

  <div class="component_bothEnd mb10">
  <div>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.up" />" name="btn_upper" onClick="return buttonPush('prj150gaibuUp');">
      <gsmsg:write key="cmn.up" />
    </button>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.down" />" name="btn_downer" onClick="return buttonPush('prj150gaibuDown');">
      <gsmsg:write key="cmn.down" />
    </button>
  </div>
  <div>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onclick="return openCompanyWindow('prj150')" />
        <img class="btn_classicImg-display" src="../project/images/classic/icon_address.gif" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../project/images/original/icon_address.png" alt="<gsmsg:write key="cmn.add" />">
      <gsmsg:write key="cmn.add" />
    </button>

    <logic:notEmpty name="prj150Form" property="prj150DspList">
      <button type="button" name="btn_tododel" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('<%=jp.groupsession.v2.prj.prj150.Prj150Action.CMD_DEL_OUTER_CLICK%>');">
         <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:notEmpty>
  </div>
  </div>
  <%
    }
  %>
</logic:equal>

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
      <% if (movePrjFlg) { %>
      <th id="gaibu" class="txt_c w5 js_tableTopCheck js_tableTopCheck-header cursor_p">
        <input type="checkbox" name="prj150gaibuAllCheck" value="1" onClick="chgCheckAll('prj150gaibuAllCheck', 'prj150gaibuSelectMemberSid');">
      </th>
      <th class="txt_c w5"></th>
      <% } %>

      <th class="txt_r w5 no_w">
        <span>Ｎｏ</span>
      </th>
      <th class="txt_c w30 no_w">
        <span>
          <gsmsg:write key="cmn.company.name" />
        </span>
      </th>
      <th class="txt_c w30 no_w">
        <span>
          <gsmsg:write key="cmn.name" />
        </span>
      </th>
      <th class="txt_c w25 no_w">
        <span>
          <gsmsg:write key="project.prj150.7" />
        </span>
      </th>
      <% if (movePrjFlg) { %>
      <th class="txt_c w10 no_w"></th>
      <% } %>
    </tr>
 <logic:notEmpty name="prj150Form" property="prj150DspList">
    <bean:define id="mod" value="0" />
    <logic:iterate name="prj150Form" property="prj150DspList" scope="request" id="prj150DspList" indexId="index">

      <%
          String idx = index.toString();
          String adr = "adr" + idx;
        %>

      <html:hidden name="prj150DspList" property="adrSid" indexed="true" styleId="<%= adr %>" />
      <html:hidden name="prj150DspList" property="gaibuRowNumber" indexed="true" />
      <html:hidden name="prj150DspList" property="gaibuSort" indexed="true" />
      <html:hidden name="prj150DspList" property="companySid" indexed="true" />
      <html:hidden name="prj150DspList" property="companyName" indexed="true" />
      <html:hidden name="prj150DspList" property="adrName" indexed="true" />
      <html:hidden name="prj150DspList" property="adrMail" indexed="true" />
      <html:hidden name="prj150DspList" property="adrTel" indexed="true" />

      <tr>
        <% if (movePrjFlg) { %>
        <td class="txt_c js_tableTopCheck cursor_p">
          <html:multibox name="prj150Form" property="prj150gaibuSelectMemberSid">
            <bean:write name="prj150DspList" property="companySid" />:<bean:write name="prj150DspList" property="companyBaseSid" />:<bean:write name="prj150DspList" property="adrSid" />
          </html:multibox>
        </td>
        <bean:define id="atiValue" name="prj150DspList" property="gaibuSort" />
        <td class="txt_c js_radioCheck cursor_p">
          <html:radio property="prj150SortGaibuRadio" value="<%= String.valueOf(atiValue) %>" />
        </td>
        <% } %>
        <td class="txt_r">
          <bean:write name="prj150DspList" property="gaibuRowNumber" />
        </td>
        <td class="txt_l">
          <bean:write name="prj150DspList" property="companyName" />
        </td>
        <td class="txt_l">
          <div class="mr5">
            <bean:write name="prj150DspList" property="adrName" />
          </div>
        </td>

        <td class="txt_l">
          <a href="mailto:<bean:write name="prj150DspList" property="adrMail" />">
            <bean:write name="prj150DspList" property="adrMail" />
          </a>
          <br>
          <bean:write name="prj150DspList" property="adrTel" />
          <br>
        </td>
        <%
              String keyId      = "key" + idx;
              String keyIdSv    = "keySv" + idx;
              String keyId2     = "key2" + idx;
              String keyId2Sv   = "key2Sv" + idx;
          %>

        <html:hidden name="prj150DspList" property="companySid" indexed="true" styleId="<%= keyId %>" />
        <html:hidden name="prj150DspList" property="companySid" indexed="true" styleId="<%= keyIdSv %>" />

        <html:hidden name="prj150DspList" property="companyBaseSid" indexed="true" styleId="<%= keyId2 %>" />
        <html:hidden name="prj150DspList" property="companyBaseSid" indexed="true" styleId="<%= keyId2Sv %>" />

        <% if (movePrjFlg) { %>
        <td class="txt_c no_w">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="deleteCompany(<bean:write name="prj150DspList" property="companySid" />, <bean:write name="prj150DspList" property="companyBaseSid" />, <bean:write name="prj150DspList" property="adrSid" />);">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
           <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </td>
        <% } %>
      </tr>
    </logic:iterate>
  </logic:notEmpty>
</table>

