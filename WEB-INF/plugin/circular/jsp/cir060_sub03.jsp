<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


      <tr class="table_bg_7D91BD_search">
        <th class="w60"><a href="#!" onClick="clickSortTitle(<%= sortTitle %>);"><span><gsmsg:write key="cmn.title" /></span></a></th>
        <th class="w10"><a href="#!" onClick="clickSortTitle(<%= sortDate %>);"><span><gsmsg:write key="cmn.date" /></span></a></th>
        <th class="w30"><a href="#!" onClick="clickSortTitle(<%= sortUser %>);"><span><gsmsg:write key="cir.2" /></span></a></th>
      </tr>

      <logic:iterate id="cirMdl" name="cir060Form" property="circularList" scope="request" indexId="idx">

      <%
      String font = "";
      String titleFont = "cl_linkDef fw_bold";
        String sojuStr = "";
      %>
      <logic:notEqual name="cirMdl" property="cvwConf" value="<%= unopen %>">
      <%
        font = "";
        titleFont = "cl_linkVisit";
      %>
      </logic:notEqual>

      <% jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage(); %>
      <logic:equal name="cirMdl" property="jsFlg" value="<%= jusin %>">
      <%
        sojuStr = gsMsg.getMessage(request, "sml.100");
      %>
      </logic:equal>
      <logic:equal name="cirMdl" property="jsFlg" value="<%= sosin %>">
      <%
        sojuStr = gsMsg.getMessage(request, "sml.102");
      %>
      </logic:equal>

      <tr class="js_listHover cursor_p" id="<bean:write name="cirMdl" property="cifSid" />,<bean:write name="cirMdl" property="jsFlg" />">
        <td class="js_listClick txt_l <%= titleFont %>"><%= String.valueOf(sojuStr) %><span class="ml5"><bean:write name="cirMdl" property="cifTitle" /></span></td>

        <td class="js_listClick txt_c no_w"><span class="<%= String.valueOf(font) %>"><bean:write name="cirMdl" property="dspCifAdate" /></span></td>

        <td class="js_listClick txt_l">
          <logic:equal name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU) %>">
          <bean:define id="mukoUserClass" value=""/>
          <logic:equal name="cirMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" >mukoUser</bean:define></logic:equal>
          <span class="<%= String.valueOf(font)%> <%=mukoUserClass%>"><bean:write name="cirMdl" property="cacName" /></span>
          </logic:equal>
          <logic:notEqual name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU) %>">
          <del><span class="<%= String.valueOf(font) %>"><bean:write name="cirMdl" property="cacName" /></span></del>
          </logic:notEqual>
        </td>
      </tr>

      </logic:iterate>
