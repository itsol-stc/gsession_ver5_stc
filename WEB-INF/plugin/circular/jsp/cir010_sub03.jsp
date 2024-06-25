<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<gsmsg:define id="title" msgkey="cmn.title" />
<gsmsg:define id="nitiji" msgkey="cmn.date" />
<gsmsg:define id="hassinsya" msgkey="cir.2" />
<bean:define id="cir010sortKey" name="cir010Form" property="cir010sortKey" type="java.lang.Integer" />
<bean:define id="cir010orderKey" name="cir010Form" property="cir010orderKey" type="java.lang.Integer" />

<%
String sortSign = "";
String sortSignOri = "";
String nextOrder = "";

  int[] sortKeyList = new int[] {
                       jp.groupsession.v2.cir.GSConstCircular.SORT_TITLE,
                       jp.groupsession.v2.cir.GSConstCircular.SORT_DATE,
                       jp.groupsession.v2.cir.GSConstCircular.SORT_USER
                       };
  String[] widthList = new String[] { "w70", "w15", "w15"};
  String[] titleList = new String[] {
          title,
          nitiji,
          hassinsya
                       };
%>
<table class="table-top">
  <tr>

    <!-- 表タイトル -->
    <!-- 全選択・全解除チェックボックス -->
    <th class="js_tableTopCheck cursor_p js_tableTopCheck-header">
      <input type="checkbox" name="allChk" onClick="changeChk();">
    </th>

    <%
      for (int titleIdx = 0; titleIdx < titleList.length; titleIdx++) {
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
      <a href="#" onClick="clickTitle(<%=String.valueOf(sortKeyList[titleIdx])%>, <%= nextOrder %>);">
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
    %>
  </tr>


  <!-- 表BODY -->
  <logic:notEmpty name="cir010Form" property="cir010CircularList" scope="request">
    <logic:iterate id="cirMdl" name="cir010Form" property="cir010CircularList" scope="request" indexId="idx">

      <% String sojuStr = ""; %>
      <% String titleFont = ""; %>

      <logic:equal name="cirMdl" property="jsFlg" value="<%= jusin %>">
        <gsmsg:define id="jusinStr" msgkey="cmn.receive2" />
        <% sojuStr = "[ " + jusinStr + " ]"; %>
      </logic:equal>
      <logic:equal name="cirMdl" property="jsFlg" value="<%= sosin %>">
        <gsmsg:define id="sosinStr" msgkey="cmn.sent2" />
        <% sojuStr = "[ " + sosinStr + " ]"; %>
      </logic:equal>

      <logic:equal name="cirMdl" property="cvwConf" value="<%= unopen %>">
      <% titleFont = "cl_linkDef fw_bold"; %>
      </logic:equal>

      <logic:notEqual name="cirMdl" property="cvwConf" value="<%= unopen %>">
      <% titleFont = "cl_linkVisit"; %>
      </logic:notEqual>

      <tr class="js_listHover cursor_p" id="<bean:write name="cirMdl" property="cifSid" />,<bean:write name="cirMdl" property="jsFlg" />">

        <!-- チェックボックス -->
        <td class="txt_c js_tableTopCheck">
          <html:multibox name="cir010Form" property="cir010delInfSid">
            <bean:write name="cirMdl" property="cifSid" />-<bean:write name="cirMdl" property="jsFlg" />
          </html:multibox>
        </td>

        <!-- タイトル -->
        <td class="js_trashlistClick">
          <logic:notEmpty name="cirMdl" property="labelName">
            <span class="baseLabel fw_n">
              <bean:write name="cirMdl" property="labelName" />
            </span>
          </logic:notEmpty>
          <span class="mr5 <%= titleFont %>"><%= String.valueOf(sojuStr) %>
            <bean:write name="cirMdl" property="cifTitle" />
          </span>
        </td>

        <!-- 日付 -->
        <td class="js_trashlistClick">
          <span class="no_w">
            <bean:write name="cirMdl" property="dspCifAdate" />
          </span>
        </td>

        <!-- 発信者 -->
        <td class="js_trashlistClick">
          <logic:equal name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
            <bean:define id="mukoUserClass" value="" />
            <logic:equal name="cirMdl" property="usrUkoFlg" value="1">
              <bean:define id="mukoUserClass" value="mukoUser" />
            </logic:equal>

            <span class="<%=mukoUserClass%>">
              <bean:write name="cirMdl" property="cacName" />
            </span>
          </logic:equal>
          <logic:notEqual name="cirMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
            <del>
              <span>
                <bean:write name="cirMdl" property="cacName" />
              </span>
            </del>
          </logic:notEqual>
        </td>
      </tr>

    </logic:iterate>
  </logic:notEmpty>
</table>