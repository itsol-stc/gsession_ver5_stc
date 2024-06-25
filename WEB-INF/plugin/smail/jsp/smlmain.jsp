<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<% String jtop = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.TAB_DSP_MODE_JUSIN_FROM_TOP); %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<%-- マーク --%>
<%
  String markAll       = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ALL);
  String markNone      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_NONE);
  String markTel       = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_TEL);
  String markImp       = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_INP);
  String markSmaily    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SMAILY);
  String markWorry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_WORRY);
  String markAngry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ANGRY);
  String markSadly     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SADRY);
  String markBeer      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_BEER);
  String markHart      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_HART);
  String markZasetsu   = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ZASETSU);
%>

<%-- マーク画像定義 --%>
<%
  jp.groupsession.v2.struts.msg.GsMessage gsMsg = new jp.groupsession.v2.struts.msg.GsMessage();
  String phone = gsMsg.getMessage(request, "cmn.phone");
  String important = gsMsg.getMessage(request, "sml.61");
  String smile = gsMsg.getMessage(request, "sml.11");
  String worry = gsMsg.getMessage(request, "sml.86");
  String angry = gsMsg.getMessage(request, "sml.83");
  String sad = gsMsg.getMessage(request, "sml.87");
  String beer = gsMsg.getMessage(request, "sml.15");
  String hart = gsMsg.getMessage(request, "sml.13");
  String tired = gsMsg.getMessage(request, "sml.88");
  String temp = gsMsg.getMessage(request, "cmn.attached");

  java.util.HashMap imgMap = new java.util.HashMap();
  imgMap.put(markTel, "<img src=\"../common/images/classic/icon_call.png\" alt=\"" + phone + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_call.png\">");
  imgMap.put(markImp, "<img src=\"../common/images/classic/icon_zyuu.png\" alt=\"" + important + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_zyuu.png\">");
  imgMap.put(markSmaily, "<img src=\"../smail/images/classic/icon_face01.png\" alt=\"" + smile + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_face_smil.png\">");
  imgMap.put(markWorry, "<img src=\"../smail/images/classic/icon_face02.png\" alt=\"" + worry + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_face_confu.png\">");
  imgMap.put(markAngry, "<img src=\"../smail/images/classic/icon_face03.png\" alt=\"" + angry + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_face_angry.png\">");
  imgMap.put(markSadly, "<img src=\"../smail/images/classic/icon_face04.png\" alt=\"" + sad + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_face_sad.png\">");
  imgMap.put(markBeer, "<img src=\"../smail/images/classic/icon_beer.png\" alt=\"" + beer + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_beer.png\">");
  imgMap.put(markHart, "<img src=\"../smail/images/classic/icon_hart.png\" alt=\"" + hart + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_hart.png\">");
  imgMap.put(markZasetsu, "<img src=\"../smail/images/classic/icon_zasetsu.png\" alt=\"" + tired + "\" class=\"img_bottom btn_classicImg-display\"><img class=\"btn_originalImg-display\" src=\"../smail/images/original/icon_zasetu.png\">");
  imgMap.put("none", "&nbsp;");
%>

<html:html>
<head>
<title>GROUPSESSION <gsmsg:write key="sml.smlmain.02" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../smail/js/smlmain.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>

<body>
<html:form action="/smail/smlmain">
<input type="hidden" name="CMD" value="">
<logic:notEmpty name="smlmainForm" property="sml010SmlList" scope="request">
<table class="table-top table_col-even w100 mwp300 mb0">
  <tr>
    <th class="txt_l table_title-color" colspan="4">
      <img class="mainPlugin_icon" src="../smail/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.shortmail" />">
      <a class="main_pluginTitle" href="<bean:write name="smlmainForm" property="smlTopUrl" />">
        <gsmsg:write key="cmn.shortmail" />
      </a>
    </th>
  </tr>
  <tr>
    <th class="w20 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.sendfrom" />
    </th>
    <th class="w45 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.subject" />
    </th>
    <th class="w15 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.from" />
    </th>
    <th class="w20 p0 bgC_header2 cl_fontBody no_w pl5 pr5" scope="col">
      <gsmsg:write key="cmn.date" />
    </th>
  </tr>
  <logic:iterate id="msg" name="smlmainForm" property="sml010SmlList" indexId="idx" scope="request">
    <bean:define id="sacMdl" name="msg" property="smlAccountData" />
    <tr class="js_listHover" data-url="../smail/sml010.do?sml010scriptFlg=1&sml010scriptKbn=1&sml010SelectedSid=<bean:write name="msg" property="smlSid" />&smlViewAccount=<bean:write name="sacMdl" property="accountSid" />">
      <bean:define id="imgMark"><bean:write name="msg" property="smsMark" /></bean:define>
      <logic:equal name="msg" property="smjOpkbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_UNOPENED) %>">
        <bean:define id="titleColor" value="cl_linkDef" />
      </logic:equal>
      <logic:equal name="msg" property="smjOpkbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.OPKBN_OPENED) %>">
        <bean:define id="titleColor" value="cl_linkVisit" />
      </logic:equal>
      <td class="js_listSmlClick  cursor_p">
        <logic:equal name="msg" property="usrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU) %>">
          <bean:define id="mukoUserClass">&nbsp;</bean:define>
          <logic:equal name="msg" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass">mukoUser</bean:define></logic:equal>
          <span class="<%=mukoUserClass%>"><bean:write name="msg" property="usiSei" />&nbsp;<bean:write name="msg" property="usiMei" /></span>
        </logic:equal>
        <logic:equal name="msg" property="usrJkbn" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE) %>">
          <del><bean:write name="msg" property="usiSei" />&nbsp;<bean:write name="msg" property="usiMei" /></del>
        </logic:equal>
        <logic:equal name="msg" property="usrJkbn" value="1">
          <del><bean:write name="msg" property="usiSei" />&nbsp;<bean:write name="msg" property="usiMei" /></del>
        </logic:equal>
      </td>
      <td class="js_listSmlClick  cursor_p">
        <%-- 絵文字 --%>
        <% java.lang.String key = "none";  if (imgMap.containsKey(imgMark)) { key = imgMark; } %> <%= (java.lang.String) imgMap.get(key) %>
        <%-- 添付マーク --%>
        <logic:notEqual name="msg" property="binCnt" value="0">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file.gif" alt="<%= temp %>">
          <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<%= temp %>">
        </logic:notEqual>
        <!-- タイトル -->
        <a href="../smail/sml010.do?sml010scriptFlg=1&sml010scriptKbn=1&sml010SelectedSid=<bean:write name="msg" property="smlSid" />&smlViewAccount=<bean:write name="sacMdl" property="accountSid" />"><span class="<bean:write name="titleColor" />"><bean:write name="msg" property="smsTitle" /></span></a>
      </td>
      <td class="js_listSmlClick  cursor_p">
        <bean:define id="mukoUserClass">&nbsp;</bean:define>
        <logic:equal name="sacMdl" property="usrUkoFlg" value="1"><bean:define id="mukoUserClass">mukoUser</bean:define></logic:equal>
        <span class="<%=mukoUserClass%>"><bean:write name="sacMdl" property="accountName" /></span>
      </td>
      <td class="txt_c js_listSmlClick  cursor_p"><bean:write name="msg" property="strSdate" /></td>
    </tr>
  </logic:iterate>
</table>
</logic:notEmpty>
</html:form>
</body>
</html:html>