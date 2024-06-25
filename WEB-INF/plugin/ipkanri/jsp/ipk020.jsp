<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ taglib tagdir="/WEB-INF/tags/ui" prefix="ui"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<%@ page import="jp.groupsession.v2.ip.IpkConst" %>
<!DOCTYPE html>
<%
  String maxLengthComment = String.valueOf(jp.groupsession.v2.ip.IpkConst.MAX_LENGTH_MSG);
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.12.1/jquery-ui.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/js/jquery-ui-1.12.1/jquery-ui.css?<%= GSConst.VERSION_PARAM %>'>
<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery_ui/css/ui1.8to1.12compat.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<title>GROUPSESSION <gsmsg:write key="ipk.ipk020.1" /></title>
</head>

<body onload="showLengthId($('#inputstr')[0], <%=maxLengthComment%>, 'inputlength');scroll();">
  <html:form action="/ipkanri/ipk020">
    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="helpPrm" value="<bean:write name='ipk020Form' property='ipk020helpMode' />">
    <html:hidden property="netSid" />
    <html:hidden property="CMD" />
    <html:hidden property="binSid" />
    <html:hidden property="ipk020AdminFlg" />
    <html:hidden property="ipk020ScrollFlg" />

    <div class="pageTitle w85 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../ipkanri/images/classic/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
          <img class="header_pluginImg" src="../ipkanri/images/original/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
        </li>
        <li>
          <gsmsg:write key="cmn.ipkanri" />
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
            <logic:empty name="ipk020Form" property="netSid">
              <gsmsg:write key="ipk.ipk020.2" />
            </logic:empty>
            <logic:notEmpty name="ipk020Form" property="netSid">
              <gsmsg:write key="ipk.ipk020.1" />
            </logic:notEmpty>
          </logic:equal>
          <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
            <gsmsg:write key="ipk.ipk020.4" />
          </logic:equal>
        </li>
        <li>
          <div>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <logic:empty name="ipk020Form" property="netSid">
                <button type="button" name="edit" value="<gsmsg:write key="cmn.entry" />" onClick="buttonPush2('netAdd');" class="baseBtn">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
                  <gsmsg:write key="cmn.entry" />
                </button>
              </logic:empty>
              <logic:notEmpty name="ipk020Form" property="netSid">
                <button type="button" name="edit" value="<gsmsg:write key="cmn.edit" />" onClick="buttonPush2('netEdit');" class="baseBtn">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                  <gsmsg:write key="cmn.edit" />
                </button>
              </logic:notEmpty>
              <logic:notEmpty name="ipk020Form" property="netSid">
                <button type="button" name="edit" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush2('netDelete');" class="baseBtn">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
              </logic:notEmpty>
            </logic:equal>
            <button type="button" name="cancel" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('return', '');" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <tr>
          <th class="w15">
            <span>
              <gsmsg:write key="cmn.name4" />
            </span>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <span class="cl_fontWarn">※</span>
            </logic:equal>
          </th>
          <td>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <input type="text" name="netName" class="wp200" maxlength="50" value="<bean:write name="ipk020Form" property="netName" />">
          </td>
          </logic:equal>
          <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
            <bean:write name="ipk020Form" property="netName" />
          </logic:equal>
        </tr>

        <tr>
          <th class="no_w w15">
            <span>
              <gsmsg:write key="ipk.2" />
            </span>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <span class="cl_fontWarn">※</span>
            </logic:equal>
          </th>
          <td>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <input type="text" name="netNetad1" maxlength="3" value="<bean:write name="ipk020Form" property="netNetad1" />" class="wp50 txt_r">&nbsp; .
              <input type="text" name="netNetad2" maxlength="3" value="<bean:write name="ipk020Form" property="netNetad2" />" class="wp50 txt_r">&nbsp; .
              <input type="text" name="netNetad3" maxlength="3" value="<bean:write name="ipk020Form" property="netNetad3" />" class="wp50 txt_r">&nbsp; .
              <input type="text" name="netNetad4" maxlength="3" value="<bean:write name="ipk020Form" property="netNetad4" />" class="wp50 txt_r">
            </logic:equal>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
              <bean:write name="ipk020Form" property="netNetad1" />.
              <bean:write name="ipk020Form" property="netNetad2" />.
              <bean:write name="ipk020Form" property="netNetad3" />.
              <bean:write name="ipk020Form" property="netNetad4" />
            </logic:equal>
          </td>
        <tr>
          <th>
            <span class="w15">
              <gsmsg:write key="ipk.3" />
            </span>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <span class="cl_fontWarn">※</span>
            </logic:equal>
          </th>
          <td>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <input type="text" name="netSabnet1" maxlength="3" value="<bean:write name="ipk020Form" property="netSabnet1" />" class="wp50 txt_r">&nbsp; .
              <input type="text" name="netSabnet2" maxlength="3" value="<bean:write name="ipk020Form" property="netSabnet2" />" class="wp50 txt_r">&nbsp; .
              <input type="text" name="netSabnet3" maxlength="3" value="<bean:write name="ipk020Form" property="netSabnet3" />" class="wp50 txt_r">&nbsp; .
              <input type="text" name="netSabnet4" maxlength="3" value="<bean:write name="ipk020Form" property="netSabnet4" />" class="wp50 txt_r">
            </logic:equal>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
              <bean:write name="ipk020Form" property="netSabnet1" />.
              <bean:write name="ipk020Form" property="netSabnet2" />.
              <bean:write name="ipk020Form" property="netSabnet3" />.
              <bean:write name="ipk020Form" property="netSabnet4" />
            </logic:equal>
          </td>
        </tr>

        <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
          <tr>
            <th class="w15">
              <span>
                <gsmsg:write key="cmn.public" />/<gsmsg:write key="cmn.private" />
              </span>
            </th>
            <td>
            <span class="verAlignMid mr10">
              <html:radio property="netDsp" value="0" styleId="network_dsp" />
              <label for="network_dsp" />
              <gsmsg:write key="cmn.public" />
              </label>
              </span>
              <span class="verAlignMid">
              <html:radio property="netDsp" value="9" styleId="network_not_dsp" />
              <label for="network_not_dsp" />
              <gsmsg:write key="cmn.private" />
              </label>
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
          <tr>
            <th class="w15">
              <span>
                <gsmsg:write key="cmn.sort" />
              </span>
              <span class="cl_fontWarn">※</span>
            </th>
            <td>
              <input type="text" name="netSort" maxlength="3" value="<bean:write name="ipk020Form" property="netSort" />" class="wp50 txt_r">
            </td>
          </tr>
        </logic:equal>
        <tr>
          <th class="w15">
            <span>
              <gsmsg:write key="cmn.comment" />
            </span>
          </th>
          <td>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <textarea name="netMsg" class="wp500" rows="7" class="textarea" onkeyup="showLengthStr(value, <%=maxLengthComment%>, 'inputlength');" id="inputstr"><bean:write name="ipk020Form" property="netMsg" /></textarea>
              <br>
              <span class="formCounter">
                <gsmsg:write key="cmn.current.characters" />
                :
              </span>
              <span id="inputlength" class="formCounter">0</span>
              <span class="formCounter_max">/<%=maxLengthComment%>
                <gsmsg:write key="cmn.character" />
              </span>
            </logic:equal>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
              <bean:write name="ipk020Form" property="netMsgHtml" filter="false" />
            </logic:equal>
          </td>
        </tr>
        <tr>
          <th class="w15">
            <span>
              <gsmsg:write key="cmn.attached" />
              (<gsmsg:write key="cmn.public" />)
            </span>
          </th>
          <td>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
                pluginId="<%= IpkConst.PLUGIN_ID_IPKANRI %>"
                tempDirId="ipk020"
                tempDirPlus="koukai"
                formId="1" />
            </logic:equal>
            <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
              <logic:notEmpty name="ipk020Form" property="ipk020KoukaiFileLabelList">
                <logic:iterate id="koukaiFileMdl" name="ipk020Form" property="koukaiBinFileInfList">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_temp_file_2.png" alt="<gsmsg:write key="cmn.file" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_attach.png" alt="<gsmsg:write key="cmn.file" />">
                  <a href="javascript:fileLinkClick(<bean:write name='koukaiFileMdl' property='binSid' />);">
                    <span class="textLink">
                      <bean:write name="koukaiFileMdl" property="binFileName" />
                      <bean:write name="koukaiFileMdl" property="binFileSizeDsp" />
                    </span>
                  </a>
                  <br>
                </logic:iterate>
              </logic:notEmpty>
            </logic:equal>
          </td>
        </tr>

        <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
          <tr>
            <th class="w15">
              <span>
                <gsmsg:write key="cmn.attached" />(<gsmsg:write key="cmn.private" />)
              </span>
            </th>

            <td>
              <attachmentFile:filearea
                mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
                pluginId="<%= IpkConst.PLUGIN_ID_IPKANRI %>"
                tempDirId="ipk020"
                tempDirPlus="hikoukai"
                formId="2" />
            </td>
        </logic:equal>
        <tr>
          <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
            <th class="w15">
              <span>
                <gsmsg:write key="ipk.ipk020.5" />
              </span>
            </th>
            <td cellspacing="0" cellpadding="0">
              <ui:usrgrpselector name="ipk020Form" property="adminSidListUI" styleClass="hp215" />
            </td>
          </logic:equal>

          <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="false">
            <th>
              <span>
                <gsmsg:write key="ipk.ipk020.5" />
              </span>
            </th>
            <td cellspacing="0" cellpadding="0">
              <logic:notEmpty name="ipk020Form" property="adminUserList">
                <logic:iterate id="param" name="ipk020Form" property="adminUserList">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="param" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass" value="mukoUser" />
                  </logic:equal>
                  <span class="<%=mukoUserClass%>">
                    <bean:write name="param" property="label" />
                  </span>
                  <br />
                </logic:iterate>
              </logic:notEmpty>
            </td>
          </logic:equal>

        </tr>
      </table>

      <div class="footerBtn_block">
        <logic:equal name="ipk020Form" property="ipk020AdminFlg" value="true">
          <logic:empty name="ipk020Form" property="netSid">
            <button type="button" name="edit" value="<gsmsg:write key="cmn.entry" />" onClick="buttonPush2('netAdd');" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.entry" />">
              <gsmsg:write key="cmn.entry" />
            </button>
          </logic:empty>
          <logic:notEmpty name="ipk020Form" property="netSid">
            <button type="button" name="edit" value="<gsmsg:write key="cmn.edit" />" onClick="buttonPush2('netEdit');" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
              <gsmsg:write key="cmn.edit" />
            </button>
          </logic:notEmpty>
          <logic:notEmpty name="ipk020Form" property="netSid">
            <button type="button" name="edit" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush2('netDelete');" class="baseBtn">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
          </logic:notEmpty>
        </logic:equal>
        <button type="button" name="cancel" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('return', '');" class="baseBtn">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
  </html:form>
</body>
</html:html>