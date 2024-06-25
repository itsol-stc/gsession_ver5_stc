<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>
<%-- 表示時 --%>
<tr class="js_longHeader">
  <th class="w20 no_w " colspan="2">
    <span>
      <gsmsg:write key="cmn.facility.group" />
    </span>
  </th>
  <td class="w80 txt_l">
    <div class="component_bothEnd">
      <div>
        <span>
          <bean:write name="rsv110Form" property="rsv110GrpName" />
        </span>
      </div>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.hide" />" class="baseBtn flo_r" onClick="hideText();">
          <gsmsg:write key="cmn.hide" />
        </button>
      </div>
    </div>
  </td>
</tr>
<tr class="js_longHeader">
  <th class="no_w " colspan="2">
    <span>
      <gsmsg:write key="reserve.47" />
    </span>
  </th>
  <td class="txt_l">
    <span>
      <bean:write name="rsv110Form" property="rsv110SisetuKbnName" />
    </span>
  </td>
</tr>
<tr class="js_longHeader">
  <th class="no_w " colspan="2">
    <span>
      <gsmsg:write key="cmn.facility.name" />
    </span>
  </th>
  <td class="txt_l">
    <span>
      <bean:write name="rsv110Form" property="rsv110SisetuName" />
    </span>
  </td>
</tr>
<tr class="js_longHeader">
  <th class="no_w " colspan="2">
    <span>
      <gsmsg:write key="cmn.asset.register.num" />
    </span>
  </th>
  <td class="txt_l">
    <span>
      <bean:write name="rsv110Form" property="rsv110SisanKanri" />
    </span>
  </td>
</tr>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName4">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName4" />
      </span>
    </th>
    <td class="txt_l">
      <span>
        <bean:write name="rsv110Form" property="rsv110Prop4Value" />
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName5">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName5" />
      </span>
    </th>
    <td class="txt_l">
      <span>
        <bean:write name="rsv110Form" property="rsv110Prop5Value" />
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName1">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName1" />
      </span>
    </th>
    <td class="txt_l">
      <span>
        <bean:write name="rsv110Form" property="rsv110Prop1Value" />
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName2">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName2" />
      </span>
    </th>
    <td class="txt_l">
      <span>
        <logic:equal name="rsv110Form" property="rsv110Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>">
          <gsmsg:write key="cmn.accepted" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Prop2Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>">
          <gsmsg:write key="cmn.not" />
        </logic:equal>
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName3">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName3" />
      </span>
    </th>
    <td class="txt_l">
      <span>
        <logic:equal name="rsv110Form" property="rsv110Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>">
          <gsmsg:write key="cmn.accepted" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Prop3Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>">
          <gsmsg:write key="cmn.not" />
        </logic:equal>
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName7">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName7" />
      </span>
    </th>
    <td class="txt_l">
      <span>
        <logic:equal name="rsv110Form" property="rsv110Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_KA)%>">
          <gsmsg:write key="cmn.accepted" />
        </logic:equal>
        <logic:equal name="rsv110Form" property="rsv110Prop7Value" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROP_KBN_HUKA)%>">
          <gsmsg:write key="cmn.not" />
        </logic:equal>
      </span>
    </td>
  </tr>
</logic:notEmpty>
<logic:notEmpty name="rsv110Form" property="rsv110PropHeaderName6">
  <tr class="js_longHeader">
    <th class="no_w " colspan="2">
      <span>
        <bean:write name="rsv110Form" property="rsv110PropHeaderName6" />
      </span>
    </th>
    <td class="txt_l">
      <logic:notEmpty name="rsv110Form" property="rsv110Prop6Value">
        <span>
          <bean:write name="rsv110Form" property="rsv110Prop6Value" />
          <gsmsg:write key="cmn.days.after" />
      </logic:notEmpty>
      </span>
    </td>
  </tr>
</logic:notEmpty>
<tr class="js_longHeader">
  <th class="no_w " colspan="2">
    <span>
      <gsmsg:write key="cmn.memo" />
    </span>
  </th>
  <td class="txt_l">
    <span>
      <bean:write name="rsv110Form" property="rsv110Biko" filter="false" />
    </span>
  </td>
</tr>
<%-- 非表示時 --%>

<tr class="js_shortHeader">
  <th class="w20 no_w " colspan="2">
    <span>
      <gsmsg:write key="cmn.facility.name" />
    </span>
  </th>
  <td class="w80 txt_l">
    <div class="component_bothEnd">
      <div>
        <span>
          <bean:write name="rsv110Form" property="rsv110SisetuName" />
        </span>
      </div>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.show" />" class="baseBtn" onClick="showText();">
          <gsmsg:write key="cmn.show" />
        </button>
      </div>
    </div>
  </td>
</tr>
<tr class="">
  <td colspan="3" class="border_left_none border_right_none bgC_none"></td>
</tr>
