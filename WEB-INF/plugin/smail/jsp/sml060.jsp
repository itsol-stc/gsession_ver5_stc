<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<% String markNone      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_NONE); %>
<% String markTel = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_TEL); %>
<% String markImp = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_INP); %>
<% String markSmaily    = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SMAILY);  %>
<% String markWorry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_WORRY);   %>
<% String markAngry     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ANGRY);   %>
<% String markSadly     = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_SADRY);   %>
<% String markBeer      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_BEER);    %>
<% String markHart      = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_HART);    %>
<% String markZasetsu   = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MARK_KBN_ZASETSU); %>

<%-- <% String maxLengthText = String.valueOf(jp.groupsession.v2.sml.GSConstSmail.MAX_LENGTH_TEXT); %> --%>
<% String maxLengthText = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.MAX_LENGTH_SMLBODY); %>


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
        imgMap.put(markTel, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../common/images/classic/icon_call.png\" alt=\"" + phone + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../common/images/original/icon_call.png\" alt=\"" + phone +"\">");
        imgMap.put(markImp, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../common/images/classic/icon_zyuu.png\" alt=\"" + important + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../common/images/original/icon_zyuu.png\" alt=\"" + important +"\">");
        imgMap.put(markSmaily, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face01.png\" alt=\"" + smile + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_smil.png\" alt=\"" + smile +"\">");
        imgMap.put(markWorry, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face02.png\" alt=\"" + worry + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_confu.png\" alt=\"" + worry +"\">");
        imgMap.put(markAngry, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face03.png\" alt=\"" + angry + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_angry.png\" alt=\"" + angry +"\">");
        imgMap.put(markSadly, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_face04.png\" alt=\"" + sad + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_face_sad.png\" alt=\"" + sad +"\">");
        imgMap.put(markBeer, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_beer.png\" alt=\"" + beer + "\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_beer.png\" alt=\"" + beer +"\">");
        imgMap.put(markHart, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_hart.png\" alt=\"" + hart +"\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_hart.png\" alt=\"" + hart +"\">");
        imgMap.put(markZasetsu, "<img class=\"btn_classicImg-display ml5 mr5 txt_m\" src=\"../smail/images/classic/icon_zasetsu.png\" alt=\"" + tired +"\"><img class=\"btn_originalImg-display ml5 mr5 txt_m\" src=\"../smail/images/original/icon_zasetu.png\" alt=\"" + tired +"\">");
        imgMap.put("none", "");
        %>

<%
  java.util.HashMap imgTextMap = new java.util.HashMap();
  imgTextMap.put(markTel, phone);
  imgTextMap.put(markImp, important);
  imgTextMap.put(markSmaily, smile);
  imgTextMap.put(markWorry, worry);
  imgTextMap.put(markAngry, angry);
  imgTextMap.put(markSadly, sad);
  imgTextMap.put(markBeer, beer);
  imgTextMap.put(markHart, hart);
  imgTextMap.put(markZasetsu, tired);

  imgTextMap.put("none", "&nbsp;");
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js"/>
<script src="../smail/js/sml060.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/submit.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>

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
<logic:equal name="sml060Form" property="selectedHinaSid" value="0"><gsmsg:write key="sml.sml060.03" /></logic:equal>
<logic:notEqual name="sml060Form" property="selectedHinaSid" value="0"><gsmsg:write key="sml.sml060.04" /></logic:notEqual>

<logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>"><gsmsg:write key="sml.sml060.01" /></logic:equal>
<logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>"><gsmsg:write key="sml.sml060.02" /></logic:equal>
</title>
</head>

<body onload="showLengthId($('#inputstr')[0], <%= maxLengthText %>, 'inputlength');">
<html:form action="/smail/sml060">
<input type="hidden" name="CMD" value="add_kn">
<html:hidden property="backScreen" />
<html:hidden property="selectedHinaSid" />
<html:hidden property="sml010ProcMode" />
<html:hidden property="sml010Sort_key" />
<html:hidden property="sml010Order_key" />
<html:hidden property="sml010PageNum" />
<html:hidden property="sml010SelectedSid" />
<html:hidden property="sml050HinaKbn" />
<html:hidden property="sml050InitFlg" />
<html:hidden property="smlViewAccount" />
<html:hidden property="smlAccountSid" />

<logic:notEmpty name="sml060Form" property="sml010DelSid" scope="request">
  <logic:iterate id="del" name="sml060Form" property="sml010DelSid" scope="request">
    <input type="hidden" name="sml010DelSid" value="<bean:write name="del"/>">
  </logic:iterate>
</logic:notEmpty>

<html:hidden property="sml050ProcMode" />
<html:hidden property="sml050Sort_key" />
<html:hidden property="sml050Order_key" />
<html:hidden property="sml050PageNum" />

<logic:notEqual name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">

<logic:lessEqual name="sml060Form" property="selectedHinaSid" value="0">
  <input type="hidden" name="helpPrm" value="0">
</logic:lessEqual>
<logic:greaterThan name="sml060Form" property="selectedHinaSid" value="0">
  <input type="hidden" name="helpPrm" value="1">
</logic:greaterThan>

</logic:notEqual>

<logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">

<logic:lessEqual name="sml060Form" property="selectedHinaSid" value="0">
  <input type="hidden" name="helpPrm" value="2">
</logic:lessEqual>
<logic:greaterThan name="sml060Form" property="selectedHinaSid" value="0">
  <input type="hidden" name="helpPrm" value="3">
</logic:greaterThan>

</logic:equal>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="wrapper">
  <div class="formContent">
    <logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">
    <div class="kanriPageTitle">
    </logic:equal>
    <logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
    <div class="pageTitle">
    </logic:equal>
      <ul>
        <logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_CMN) %>">
          <li>
            <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
            <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
          </li>
          <li><gsmsg:write key="cmn.admin.setting" /></li>
        </logic:equal>
        <logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
          <li>
            <img class="header_pluginImg-classic" src="../smail/images/classic/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
            <img class="header_pluginImg" src="../smail/images/original/header_smail.png" alt="<gsmsg:write key="cmn.shortmail" />">
          </li>
          <li><gsmsg:write key="cmn.shortmail" /></li>
        </logic:equal>

        <li class="pageTitle_subFont">
          <logic:equal name="sml060Form" property="selectedHinaSid" value="0"><gsmsg:write key="sml.sml060.03" /></logic:equal>
          <logic:notEqual name="sml060Form" property="selectedHinaSid" value="0"><gsmsg:write key="sml.sml060.04" /></logic:notEqual>
        </li>
        <li>
          <div>
            <button class="baseBtn" value="OK" onClick="return onControlSubmit();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
              <gsmsg:write key="cmn.ok" />
            </button>
            <logic:greaterThan name="sml060Form" property="selectedHinaSid" value="0">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            </logic:greaterThan>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_from_hina_add');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="txt_l">
      <html:errors />
    </div>
    <table class="table-left w100">
      <logic:equal name="sml060Form" property="sml050HinaKbn" value="<%= String.valueOf(jp.groupsession.v2.sml.GSConstSmail.HINA_KBN_PRI) %>">
        <tr>
          <th class="w20 txt_l">
            <gsmsg:write key="cmn.target" /><gsmsg:write key="wml.102" />
          </th>
          <td class="w80 bgC_tableCell">
            <logic:equal name="sml060Form" property="selectedHinaSid" value="0">
              <div id="accountSelArea">
                <span id="selAccountNameArea" class="mr20">
                  <bean:write name="sml060Form" property="sml060AccountName" />
                </span>
                <button type="button" class="baseBtn" id="accountSelBtn" value="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
                  <gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />
                </button>

              </div>
            </logic:equal>
            <logic:notEqual name="sml060Form" property="selectedHinaSid" value="0">
              <span id="selAccountNameArea"><bean:write name="sml060Form" property="sml060AccountName" /></span>
            </logic:notEqual>
          </td>
        </tr>
      </logic:equal>
      <tr>
        <th class="w20 txt_l"><gsmsg:write key="sml.template.name" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
        <td class="w80 bgC_tableCell"><html:text styleClass="wp600" maxlength="50" name="sml060Form" property="sml060HinaName" /></td>
      </tr>
      <tr>
        <th class="w20 txt_l"><gsmsg:write key="cmn.subject" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
        <td class="w80 bgC_tableCell"><html:text styleClass="wp600" maxlength="50" name="sml060Form" property="sml060HinaTitle" /></td>
      </tr>
      <tr>
        <th class="w20 txt_l"><gsmsg:write key="cmn.mark" /></th>
        <td class="w80 bgC_tableCell">

          <span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="0" styleId="sml060HinaMark_0" />
            <label for="sml060HinaMark_0" class="ml5" ><gsmsg:write key="cmn.no3" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markTel %>" styleId="sml060HinaMark_1" />
            <label for="sml060HinaMark_1" class=" no_w verAlignMid"><%=imgMap.get(markTel) %>
            <gsmsg:write key="cmn.phone" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markImp %>" styleId="sml060HinaMark_2" />
            <label for="sml060HinaMark_2" class=" no_w verAlignMid"><%=imgMap.get(markImp) %>
            <gsmsg:write key="sml.61" /></label>
          </span>
          <!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markSmaily %>" styleId="sml060HinaMark_3" />
            <label for="sml060HinaMark_3" class=" no_w verAlignMid"><%=imgMap.get(markSmaily) %>
            <gsmsg:write key="sml.11" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markWorry %>" styleId="sml060HinaMark_4" />
            <label for="sml060HinaMark_4" class=" no_w verAlignMid"><%=imgMap.get(markWorry) %>
            <gsmsg:write key="sml.86" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markAngry %>" styleId="sml060HinaMark_5" />
            <label for="sml060HinaMark_5" class=" no_w verAlignMid"><%=imgMap.get(markAngry) %>
            <gsmsg:write key="sml.83" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markSadly %>" styleId="sml060HinaMark_6" />
            <label for="sml060HinaMark_6" class=" no_w verAlignMid"><%=imgMap.get(markSadly) %>
            <gsmsg:write key="sml.87" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markBeer %>" styleId="sml060HinaMark_7" />
            <label for="sml060HinaMark_7" class=" no_w verAlignMid"><%=imgMap.get(markBeer) %>
            <gsmsg:write key="sml.15" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markHart %>" styleId="sml060HinaMark_8" />
            <label for="sml060HinaMark_8" class=" no_w verAlignMid"><%=imgMap.get(markHart) %>
            <gsmsg:write key="sml.13" /></label>
          </span><!--
       --><span class="no_w mr10 pr5 verAlignMid">
            <html:radio property="sml060HinaMark" value="<%=markZasetsu %>" styleId="sml060HinaMark_9" />
            <label for="sml060HinaMark_9" class=" no_w verAlignMid"><%=imgMap.get(markZasetsu) %>
            <gsmsg:write key="sml.88" /></label>
          </span>
        </td>
      </tr>
      <tr>
        <th class="w20 txt_l"><gsmsg:write key="wml.210" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span></th>
        <td class="w80 bgC_tableCell">
          <textarea class="wp600" name="sml060HinaBody" rows="20" onkeyup="showLengthStr(value, <%= maxLengthText %>, 'inputlength');" id="inputstr"><bean:write name="sml060Form" property="sml060HinaBody" /></textarea>
          <br>
          <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:<span id="inputlength">0</span>&nbsp;/&nbsp;<%= maxLengthText %>&nbsp;<gsmsg:write key="cmn.character" /></span>
        </td>
      </tr>
    </table>
    <div class="footerBtn_block">
      <button class="baseBtn" value="OK" onClick="return onControlSubmit();">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
        <gsmsg:write key="cmn.ok" />
      </button>
      <logic:greaterThan name="sml060Form" property="selectedHinaSid" value="0">
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
      </logic:greaterThan>
      <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('back_from_hina_add');">
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
  <div id="accountSelPop" title="<gsmsg:write key="wml.102" /><gsmsg:write key="cmn.select" />">
    <input type="hidden" id="selAccountElm" value="smlAccountSid" />
    <input type="hidden" id="selAccountSubmit" value="false" />
    <input type="hidden" id="sml240user" value="<bean:write name="sml060Form" property="smlViewUser" />" />
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