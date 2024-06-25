<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<% String cif_show_open = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC); %>

      <tr>
        <th class="w45"><a href="#!" onClick="clickSortTitle(<%= sortTitle %>);"><span><gsmsg:write key="cmn.title" /></span></a></th>
        <th class="w5"><span><gsmsg:write key="cmn.check" /></span></th>
        <th class="w20"><a href="#!" onClick="clickSortTitle(<%= sortDate %>);"><span><gsmsg:write key="cmn.date" /></span></a></th>
        <th class="w30"><a href="#!" onClick="clickSortTitle(<%= sortUser %>);"><span><gsmsg:write key="cir.2" /></span></a></th>
      </tr>

      <logic:iterate id="cirMdl" name="cir060Form" property="circularList" scope="request" indexId="idx">

      <%
        String font = "";
        String titleFont = "cl_linkDef fw_bold";
      %>

      <logic:notEqual name="cirMdl" property="cvwConf" value="<%= unopen %>">
      <%
        font = "";
        titleFont = "cl_linkVisit";
      %>
      </logic:notEqual>

      <tr class="js_listHover cursor_p" id="<bean:write name="cirMdl" property="cifSid" />,0">
        <td class="js_listClick txt_l <%= titleFont %>"><bean:write name="cirMdl" property="cifTitle" /></td>

        <td class="js_listClick txt_c">
        <span class="<%= String.valueOf(font) %>">
          <logic:equal name="cirMdl" property="cifShow" value="<%= cif_show_open %>"><bean:write name="cirMdl" property="openCount" />/<bean:write name="cirMdl" property="allCount" /></logic:equal>
          <logic:notEqual name="cirMdl" property="cifShow" value="<%= cif_show_open %>">-</logic:notEqual>
        </td>

        <td class="js_listClick txt_c"><span class="<%= String.valueOf(font) %>"><bean:write name="cirMdl" property="dspCifAdate" /></span></td>

        <td class="js_listClick txt_l">
          <logic:equal name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
          <bean:define id="mukoUserClass" value=""/>
          <logic:equal name="cirMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" >mukoUser</bean:define></logic:equal>
          <span class="<%= String.valueOf(font) %> <%=mukoUserClass%>"><bean:write name="cirMdl" property="cacName" /></span>
          </logic:equal>
          <logic:notEqual name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
          <del><span class="<%= String.valueOf(font) %>"><bean:write name="cirMdl" property="cacName" /></span></del>
          </logic:notEqual>
        </td>
      </tr>

      </logic:iterate>
