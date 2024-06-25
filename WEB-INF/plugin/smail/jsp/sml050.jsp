<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String markTel = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_TEL); %>
<% String markImp = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_INP); %>
<% String markSmaily    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SMAILY);  %>
<% String markWorry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_WORRY);   %>
<% String markAngry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ANGRY);   %>
<% String markSadly     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SADRY);   %>
<% String markBeer      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_BEER);    %>
<% String markHart      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_HART);    %>
<% String markZasetsu   = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ZASETSU); %>

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

  java.util.HashMap imgMap = new java.util.HashMap();
  imgMap.put(markTel, "<img class=\"btn_classicImg-display txt_t\" src=\"../common/images/classic/icon_call.png\" alt=\"" + phone + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../common/images/original/icon_call.png\" alt=\"" + phone +"\">");
  imgMap.put(markImp, "<img class=\"btn_classicImg-display txt_t\" src=\"../common/images/classic/icon_zyuu.png\" alt=\"" + important + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../common/images/original/icon_zyuu.png\" alt=\"" + important +"\">");
  imgMap.put(markSmaily, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_face01.png\" alt=\"" + smile + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_face_smil.png\" alt=\"" + smile +"\">");
  imgMap.put(markWorry, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_face02.png\" alt=\"" + worry + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_face_confu.png\" alt=\"" + worry +"\">");
  imgMap.put(markAngry, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_face03.png\" alt=\"" + angry + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_face_angry.png\" alt=\"" + angry +"\">");
  imgMap.put(markSadly, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_face04.png\" alt=\"" + sad + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_face_sad.png\" alt=\"" + sad +"\">");
  imgMap.put(markBeer, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_beer.png\" alt=\"" + beer + "\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_beer.png\" alt=\"" + beer +"\">");
  imgMap.put(markHart, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_hart.png\" alt=\"" + hart +"\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_hart.png\" alt=\"" + hart +"\">");
  imgMap.put(markZasetsu, "<img class=\"btn_classicImg-display txt_t\" src=\"../smail/images/classic/icon_zasetsu.png\" alt=\"" + tired +"\"><img class=\"btn_originalImg-display txt_t\" src=\"../smail/images/original/icon_zasetu.png\" alt=\"" + tired +"\">");
  imgMap.put("none", "");
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../smail/js/sml050.js?<%= GSConst.VERSION_PARAM %>"></script>
<jsp:include page="/WEB-INF/plugin/smail/jsp/smlaccountsel.jsp" />
<jsp:include page="/WEB-INF/plugin/smail/jsp/sml010_message.jsp" />

<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../smail/css/smail.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<title>GROUPSESSION
<logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>"><gsmsg:write key="sml.sml050.01" /></logic:equal>
<logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>"><gsmsg:write key="sml.sml050.02" /></logic:equal>
</title>
</head>

<body>
<html:form action="/smail/sml050">
<input type="hidden" name="CMD" value="">
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />
<html:hidden property="selectedHinaSid" />
<html:hidden property="backScreen" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml050HinaKbn" />
<html:hidden property="sml050InitFlg" />


<logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">
  <input type="hidden" name="helpPrm" value="1">
</logic:equal>
<logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
  <input type="hidden" name="helpPrm" value="0">
</logic:equal>

<logic:notEmpty name="sml050Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml050Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml050Sort_key" />
<html:hidden property="sml050Order_key" />
<html:hidden property="sml050PageNum" />


<input type="hidden" name="sml050HinaKbn" value="1" />

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="kanriContent">
    <logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">
    <div class="kanriPageTitle">
    </logic:equal>
    <logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
    <div class="pageTitle">
    </logic:equal>
      <ul>
        <logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li><gsmsg:write key="cmn.admin.setting" /></li>
          <li class="pageTitle_subFont">
            <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.shortmail" /></span><%--
        --%><gsmsg:write key="sml.sml050.01" />
          </li>
        </logic:equal>
        <logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
          <li>
            <img class="header_pluginImg-classic" src="../smail/images/classic/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
            <img class="header_pluginImg" src="../smail/images/original/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
          </li>
          <li><gsmsg:write key="cmn.shortmail" /></li>
          <li class="pageTitle_subFont">
           <gsmsg:write key="sml.sml050.02" />
          </li>
        </logic:equal>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('hina_add');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
              <gsmsg:write key="cmn.add" />
            </button>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backFromHina');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div>
    <logic:equal name="sml050Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
      <table class="table-left w100">
        <tr>
          <th class="w20">
            <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
          </th>
          <td class="w80">
            <div id="accountSelArea">
              <span class="mr20" id="selAccountNameArea">
              <bean:write name="sml050Form" property="sml050AccountName" />
              </span>
              <button type="button" id="accountSelBtn" class="baseBtn" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" >
                <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
              </button>
            </div>
          </td>
        </tr>
      </table>
    </div>
    </logic:equal>
    <div class="txt_r paging">
      <logic:notEmpty name="sml050Form" property="sml050PageLabel" scope="request">
        <bean:size id="count1" name="sml050Form" property="sml050PageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <logic:empty name="sml050Form" property="sml050PageLabel">
            <html:select property="sml050Slt_page1" styleClass="paging_combo">
              <option value="1">1 / 1</option>
            </html:select>
          </logic:empty>
          <logic:notEmpty name="sml050Form" property="sml050PageLabel">
            <html:select property="sml050Slt_page1" onchange="changePage1();" styleClass="paging_combo">
              <html:optionsCollection name="sml050Form" property="sml050PageLabel" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </logic:greaterThan>
      </logic:notEmpty>
    </div>
    <table class="table-top js_table-top mt0 mb0 w100">
      <tr class="">
        <th class="w40 table_title-color cursor_p txt_c">
        <logic:equal name="sml050Form" property="sml050Sort_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_NAME) %>">
          <logic:equal name="sml050Form" property="sml050Order_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC) %>')">
              <gsmsg:write key="sml.template.name" /><span class="classic-display">▲</span><i class="original-display icon-sort_up"></i>
            </a>
          </logic:equal>
          <logic:equal name="sml050Form" property="sml050Order_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC) %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="sml.template.name" /><span class="classic-display">▼</span><i class="original-display icon-sort_down"></i></a>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="sml050Form" property="sml050Sort_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_NAME) %>">
          <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_NAME) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>')"><gsmsg:write key="sml.template.name" /></a>
        </logic:notEqual>
        </th>
        <th class="w60 table_title-color cursor_p txt_c ">
          <logic:equal name="sml050Form" property="sml050Sort_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_TITLE) %>">
            <logic:equal name="sml050Form" property="sml050Order_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_TITLE) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC) %>')">
                              <gsmsg:write key="cmn.subject" /><span class="classic-display">▲</span><i class="original-display icon-sort_up"></i>
              </a>
            </logic:equal>
            <logic:equal name="sml050Form" property="sml050Order_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC) %>">
              <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_TITLE) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>')">
                              <gsmsg:write key="cmn.subject" /><span class="classic-display">▼</span><i class="original-display icon-sort_down"></i>
              </a>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="sml050Form" property="sml050Sort_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_TITLE) %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_TITLE) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.subject" />
            </a>
          </logic:notEqual>
        </th>
        <th class="w0 table_title-color cursor_p txt_c no_w">
          <logic:equal name="sml050Form" property="sml050Sort_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_MARK) %>">
            <logic:equal name="sml050Form" property="sml050Order_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>">
              <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_MARK) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC) %>')">
                              <gsmsg:write key="cmn.mark" /><span class="classic-display">▲</span><i class="original-display icon-sort_up"></i>
              </a>
            </logic:equal>
            <logic:equal name="sml050Form" property="sml050Order_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_DESC) %>">
              <a  href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_MARK) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>')">
                              <gsmsg:write key="cmn.mark" /><span class="classic-display">▼</span><i class="original-display icon-sort_down"></i>
              </a>
            </logic:equal>
          </logic:equal>
          <logic:notEqual name="sml050Form" property="sml050Sort_key" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_MARK) %>">
            <a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_SORT_KEY_MARK) %>', '<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.ORDER_KEY_ASC) %>')">
              <gsmsg:write key="cmn.mark" />
            </a>
          </logic:notEqual>
        </th>
        <th class="w0 table_title-color">
          &nbsp;
        </th>
      </tr>
      <logic:notEmpty name="sml050Form" property="sml050HinaList" scope="request">
        <bean:define id="mod" value="0" />
        <logic:iterate id="hina" name="sml050Form" property="sml050HinaList" indexId="idx" scope="request">
          <logic:equal name="mod" value="<%= String.valueOf(idx.intValue() % 2) %>">
            <bean:define id="tblColor" value="smail_td1" />
          </logic:equal>
          <logic:notEqual name="mod" value="<%= String.valueOf(idx.intValue() % 2) %>">
            <bean:define id="tblColor" value="smail_td2" />
          </logic:notEqual>

          <%-- マーク定義  --%>
          <bean:define id="imgMark"><bean:write name="hina" property="shnMark" /></bean:define>
          <tr>
          <td class="w40">
            <bean:write name="hina" property="shnHname" />
          </td>
          <td class="w60">
            <bean:write name="hina" property="shnTitle" />
          </td>
          <td class="w0 txt_c">
            <%-- マーク --%>
            <% java.lang.String key = "none";
            if (imgMap.containsKey(imgMark)) {
                key = imgMark;
            } %>
            <%= (java.lang.String) imgMap.get(key) %>
          </td>
          <td class="w0 txt_c  no_w">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.edit" />" onClick="moveEdit('<bean:write name="hina" property="shnSid" />');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.edit" />
            </button>
          </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>
    <div class="txt_r paging">
      <logic:notEmpty name="sml050Form" property="sml050PageLabel" scope="request">
        <bean:size id="count1" name="sml050Form" property="sml050PageLabel" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_left');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <logic:empty name="sml050Form" property="sml050PageLabel">
            <html:select property="sml050Slt_page1" styleClass="paging_combo">
              <option value="1">1 / 1</option>
            </html:select>
          </logic:empty>
          <logic:notEmpty name="sml050Form" property="sml050PageLabel">
            <html:select property="sml050Slt_page1" onchange="changePage1();" styleClass="paging_combo">
              <html:optionsCollection name="sml050Form" property="sml050PageLabel" value="value" label="label" />
            </html:select>
          </logic:notEmpty>
          <button type="button" class="webIconBtn" onClick="buttonPush('arrorw_right');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </logic:greaterThan>
      </logic:notEmpty>
    </div>
    <div class="footerBtn_block mt10">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="buttonPush('hina_add');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
        <gsmsg:write key="cmn.add" />
      </button>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('backFromHina');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>
    </div>

  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>

</html:form>
<div class="display_none">
  <div id="accountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />" >
    <input type="hidden" id="selAccountElm" value="smlAccountSid" />
    <input type="hidden" id="selAccountSubmit" value="true" />
    <input type="hidden" id="sml240user" value="<bean:write name="sml050Form" property="smlViewUser" />" />


    <div class="hp450 w100 ofy_a">
      <table class="w100 h100">
        <tr>
          <td id="accountListArea"  class="txt_t"></td>
        </tr>
      </table>
    </div>
  </div>


  <div id="setKakuninPop" title="">
    <ul class="mt20 p0">
      <li class="display_inline" >
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png" alt="cmn.warn">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png" alt="cmn.warn">
      </li>
      <li class="display_inline txt_t pt20 pl10">
         <gsmsg:write key="sml.170" />
      </li>
    </ul>
    <div id="accountKakuninListArea" class="sml_accountKakuninListArea pl15 pt10"></div>
  </div>
</div>

</body>
</html:html>