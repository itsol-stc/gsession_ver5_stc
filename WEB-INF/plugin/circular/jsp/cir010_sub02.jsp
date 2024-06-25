<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<gsmsg:define id="title" msgkey="cmn.title" />
<gsmsg:define id="kakunin" msgkey="cmn.check" />
<gsmsg:define id="nitiji" msgkey="cmn.date" />
<gsmsg:define id="hassinsya" msgkey="cir.2" />


<table class="table-top">
 <tr>
  <bean:define id="cir010sortKey" name="cir010Form" property="cir010sortKey" type="java.lang.Integer" />
  <bean:define id="cir010orderKey" name="cir010Form" property="cir010orderKey" type="java.lang.Integer" />
  <%
              String cif_show_open = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CIR_INIT_SAKI_PUBLIC);
              String sortSign = "";
              String sortSignOri = "";
              String nextOrder = "";
              jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
              String[] widthList = {
                      "",
                      "w65",
                      "w5",
                      "w15",
                      "w15",
                      };
              String[] titleList = {
                      "<input type=\"checkbox\" name=\"allChk\" onClick=\"changeChk();\">",
                      gsMsg.getMessage(request, "cmn.title"),
                      gsMsg.getMessage(request, "cmn.check"),
                      gsMsg.getMessage(request, "cmn.date"),
                      gsMsg.getMessage(request, "cir.2")
                      };
              int[] sortKeyList = {
                      -1,
                      jp.groupsession.v2.cir.GSConstCircular.SORT_TITLE,
                      -1,
                      jp.groupsession.v2.cir.GSConstCircular.SORT_DATE,
                      jp.groupsession.v2.cir.GSConstCircular.SORT_USER
                      };
              for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
                  if(sortKeyList[titleIdx]  == -1) {
                      %>
                      <% if(titleIdx  == 0) { %>
                      <th class="<%=widthList[titleIdx]%> cursor_p js_tableTopCheck js_tableTopCheck-header">
                      <% } else { %>
                      <th class="<%=widthList[titleIdx]%> no_w">
                      <% } %>
                        <%=titleList[titleIdx]%>
                      </th>
                      <%

                  } else {
                  if (cir010sortKey.intValue() == sortKeyList[titleIdx]) {
                      if (cir010orderKey.intValue() == 1) {
                          sortSign = "▼";
                          sortSignOri="<i class=\"icon-sort_down\"></i>";
                          nextOrder = "0";
                      } else {
                          sortSign = "▲";
                          sortSignOri="<i class=\"icon-sort_up\"></i>";
                          nextOrder = "1";
                      }
                  } else {
                      nextOrder = "0";
                      sortSignOri="";
                      sortSign = "";
                  }
  %>
  <th class="<%=widthList[titleIdx]%> no_w cursor_p">
    <a href="#" onClick="clickTitle(<%=String.valueOf(sortKeyList[titleIdx])%>, <%= nextOrder %>);" >
     <span class="classic-display"><%=titleList[titleIdx]%><%=sortSign%></span>
     <% if (cir010orderKey.intValue() == 1) { %>
     <span class="original-display txt_m"><%=titleList[titleIdx]%><%= sortSignOri %></span>
     <% } else { %>
     <span class="original-display txt_m"><%=titleList[titleIdx]%><%= sortSignOri %></span>
     <% } %>
    </a>
  </th>
  <%
                    }
                }
  %>
    </tr>


  <!-- 表BODY -->
  <logic:notEmpty name="cir010Form" property="cir010CircularList" scope="request">
  <logic:iterate id="cirMdl" name="cir010Form" property="cir010CircularList" scope="request" indexId="idx">

  <tr class ="js_listHover cursor_p" id="<bean:write name="cirMdl" property="cifSid" />">
  <!-- チェックボックス -->
  <td class="txt_c js_tableTopCheck">
    <html:multibox name="cir010Form" property="cir010delInfSid">
       <bean:write name="cirMdl" property="cifSid" />-<bean:write name="cirMdl" property="jsFlg" />
    </html:multibox>
  </td>

  <!-- タイトル -->
  <td class="js_listClick">
    <logic:notEmpty name="cirMdl" property="labelName">
        <span class="baseLabel fw_n"><bean:write name="cirMdl" property="labelName" /></span>
    </logic:notEmpty>
      <span class="cl_linkVisit"><bean:write name="cirMdl" property="cifTitle" /></span>
  </td>

  <td class="js_listClick txt_c">
  <span>
  <logic:equal name="cirMdl" property="cifShow" value="<%= cif_show_open %>"><bean:write name="cirMdl" property="openCount" />/<bean:write name="cirMdl" property="allCount" /></logic:equal>
  <logic:notEqual name="cirMdl" property="cifShow" value="<%= cif_show_open %>">-</logic:notEqual>
  </span>
  </td>

  <!-- 日付 -->
  <td class="js_listClick"><span class="no_w"><bean:write name="cirMdl" property="dspCifAdate" /></span></td>

  <!-- 発信者 -->
  <td class="js_listClick">
    <logic:equal name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
    <bean:define id="mukoUserClass" value=""/>
    <logic:equal name="cirMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass" value="mukoUser" /></logic:equal>

    <span class="<%=mukoUserClass%>"><bean:write name="cirMdl" property="cacName" /></span>
    </logic:equal>
    <logic:notEqual name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
    <del><span><bean:write name="cirMdl" property="cacName" /></span></del>
    </logic:notEqual>
  </td>
  </tr>

  </logic:iterate>
  </logic:notEmpty>

  </table>

