<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>


      <tr class="table_bg_7D91BD_search">
        <th class="w45"><a href="#!" onClick="clickSortTitle(<%= sortTitle %>);"><span><gsmsg:write key="cmn.title" /></span></a></th>
        <th class="w5"><span><gsmsg:write key="cmn.check" /></span></th>
        <th class="w20"><a href="#!" onClick="clickSortTitle(<%= sortDate %>);"><span><gsmsg:write key="cmn.date" /></span></a></th>
        <th class="w30"><a href="#!" onClick="clickSortTitle(<%= sortUser %>);"><span><gsmsg:write key="cir.2" /></span></a></th>
      </tr>

      <logic:iterate id="cirMdl" name="cir060Form" property="circularList" scope="request" indexId="idx">

      <tr class="js_listHover cursor_p" id="<bean:write name="cirMdl" property="cifSid" />,1">
        <td class="txt_l js_listClick cl_linkVisit"><span><bean:write name="cirMdl" property="cifTitle" /></span></td>

        <td class="txt_c js_listClick"><span><bean:write name="cirMdl" property="openCount" />/<bean:write name="cirMdl" property="allCount" /></span></td>

        <td class="txt_c js_listClick"><span><bean:write name="cirMdl" property="dspCifAdate" /></span></td>

        <td class="txt_l js_listClick">
          <logic:equal name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
          <bean:define id="mukoUserClass" value=""/>
          <logic:equal name="cirMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" >mukoUser</bean:define></logic:equal>
          <span class="<%=mukoUserClass%>"><bean:write name="cirMdl" property="cacName" /></span>
          </logic:equal>
          <logic:notEqual name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
          <del><span><bean:write name="cirMdl" property="cacName" /></span></del>
          </logic:notEqual>
        </td>
      </tr>

      </logic:iterate>
