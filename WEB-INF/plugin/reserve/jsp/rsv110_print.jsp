<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>

<logic:equal name="rsv110Form" property="rsvPrintUseKbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.RSV_PRINT_USE_YES)%>">
  <tr>
    <th class="w20 no_w" colspan="2">
      <span>
        <gsmsg:write key="reserve.print" />
      </span>
    </th>
    <td class="w80 txt_l">
      <logic:notEqual name="rsv110Form" property="rsv110EditAuth" value="true">
        <logic:equal name="rsv110Form" property="rsv110PrintKbn" value="1">
          <span>
            <gsmsg:write key="reserve.print.yes" />
          </span>
        </logic:equal>
        <logic:notEqual name="rsv110Form" property="rsv110PrintKbn" value="1">
          <span>
            <gsmsg:write key="reserve.print.no" />
          </span>
        </logic:notEqual>
      </logic:notEqual>

      <logic:equal name="rsv110Form" property="rsv110EditAuth" value="true">
        <logic:notEqual name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
          <span class="verAlignMid">
            <html:checkbox name="rsv110Form" property="rsv110PrintKbn" value="1" styleId="print" />
            <label for="print">
              <gsmsg:write key="reserve.print.yes" />
            </label>
          </span>
        </logic:notEqual>
        <logic:equal name="rsv110Form" property="rsv110ProcMode" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConstReserve.PROC_MODE_POPUP)%>">
          <logic:equal name="rsv110Form" property="rsv110PrintKbn" value="1">
            <span>
              <gsmsg:write key="reserve.print.yes" />
            </span>
          </logic:equal>
          <logic:notEqual name="rsv110Form" property="rsv110PrintKbn" value="1">
            <span>
              <gsmsg:write key="reserve.print.no" />
            </span>
          </logic:notEqual>
        </logic:equal>
      </logic:equal>
    </td>
  </tr>
</logic:equal>
