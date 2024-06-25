<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.man.GSConstPortal" %>

<% int tCmdAdd = GSConstPortal.CMD_MODE_ADD; %>
<% int tCmdEdit = GSConstPortal.CMD_MODE_EDIT; %>

<!DOCTYPE html>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<gsjsmsg:js filename="gsjsmsg.js" />
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tinymce-5.10.3/tinymce.min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl100.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../portal/js/ptl160.js?<%= GSConst.VERSION_PARAM %>"></script>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../portal/css/portal.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>

<title>GROUPSESSION <gsmsg:write key="ptl.ptl100.3" /></title>
</head>

<!-- body -->
<body onload="showLengthId($('#inputstr')[0], 1000, 'inputlength');" onunload="closePortletImagePopup();">
<html:form action="/portal/ptl100">
<input type="hidden" name="CMD" value="init">
<input type="hidden" name="helpPrm" value="<bean:write name="ptl100Form" property="ptlCmdMode" /><bean:write name="ptl100Form" property="ptl100contentType" />">
<html:hidden property="ptl090category" />
<html:hidden property="ptl090svCategory" />
<html:hidden property="ptl090sortPortlet" />
<html:hidden property="ptlCmdMode" />
<html:hidden property="ptlPortletSid" />
<html:hidden property="ptl100init" />
<html:hidden property="ptl100contentPlusImage" />

<input type="hidden" name="pltPortletImageSid" value="0">

<logic:equal name="ptl100Form" property="ptl100contentType" value="0">
  <html:hidden property="ptl100contentUrl" />
  <html:hidden property="ptl100contentHtml" />
</logic:equal>
<logic:equal name="ptl100Form" property="ptl100contentType" value="2">
  <html:hidden property="ptl100contentUrl" />
  <html:hidden property="ptl100content" />
</logic:equal>
<logic:equal name="ptl100Form" property="ptl100contentType" value="1">
  <html:hidden property="ptl100contentHtml" />
  <html:hidden property="ptl100content" />
</logic:equal>

<%@ include file="/WEB-INF/plugin/portal/jsp/ptl_hiddenParams.jsp" %>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle w80 mrl_auto">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="ptl.ptl100.3" />
    </li>
    <li>
      <div>
        <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ptl100add');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <logic:equal name="ptl100Form" property="ptlCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
        </logic:equal>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ptl100back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper w80 mrl_auto">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <table class="table-left w100">
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.17" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <html:text property="ptl100name" maxlength="100" size="40" styleClass="w100" />
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.category" />
      </th>
      <td class="w80">
        <html:select property="ptl100category">
          <html:optionsCollection property="ptl100CategoryList" value="value" label="label" />
        </html:select>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.16" />
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio property="ptl100border" styleClass="mr5" value="0" styleId="borderKbn0" />
          <label for="borderKbn0"><gsmsg:write key="address.adr010.contact.5" /></label>
          <html:radio property="ptl100border" styleClass="mr5 ml10" value="1" styleId="borderKbn1" />
          <label for="borderKbn1" class="text_base"><gsmsg:write key="cmn.no" /></label>
        </div>
      </td>
    </tr>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="cmn.content" /><span class="cl_fontWarn"><gsmsg:write key="cmn.comments" /></span>
      </th>
      <td class="w80">
        <div class="verAlignMid">
          <html:radio property="ptl100contentType" styleClass="mr5" value="0" styleId="contentType0" onclick="return buttonPush('init');" />
          <label for="contentType0" class="text_base"><gsmsg:write key="ptl.10" /></label>
          <html:radio property="ptl100contentType" styleClass="mr5 ml10" value="2" styleId="contentType2" onclick="return buttonPush('init');" />
          <label for="contentType2" class="text_base"><gsmsg:write key="ptl.11" /></label>
          <html:radio property="ptl100contentType" styleClass="mr5 ml10" value="1" styleId="contentType1" onclick="return buttonPush('init');" />
          <label for="contentType1" class="text_base"><gsmsg:write key="ptl.12" /></label>
        </div>
        <span id="contentUrl">
          <logic:equal name="ptl100Form" property="ptl100contentType" value="0">
            <input type="hidden" name="attachment_ID_list" value="2">
            <input type="hidden" name="attachmentFileListFlg2" value="false" />
            <input type="hidden" name="attachmentMode2" value="7" />
            <input type="hidden" name="attachmentPluginId2" value="<%= GSConstPortal.PLUGIN_ID %>" />
            <input type="hidden" name="attachmentTempDirId2" value="ptl100" />
            <input type="hidden" name="attachmentTempDirPlus2" value="bodyFile" />
            <input type="hidden" id="attachmentFileMultiMode" value="multi" />

            <span id="attachmentFileErrorArea2"></span>
            <div id="attachment_FormArea2">
            <html:textarea styleClass="w100" styleId="ptletContentArea" property="ptl100content" cols="50" rows="40"/>
            </div>
            <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="attachFileSelect(this, '2');" multiple="">
          </logic:equal>
          <logic:equal name="ptl100Form" property="ptl100contentType" value="1">
            <div>
              URL: <html:text property="ptl100contentUrl" size="40" styleClass="wp400" />
            </div>
          </logic:equal>
          <logic:equal name="ptl100Form" property="ptl100contentType" value="2">
            <html:textarea property="ptl100contentHtml" styleClass="w100" rows="20" />
          </logic:equal>
        </span>
      </td>
    </tr>
    <!-- 「HTMLを入力」のみ表示 -->
    <logic:equal name="ptl100Form" property="ptl100contentType" value="2">
      <tr>
        <th class="w20 no_w">
          <gsmsg:write key="cmn.image" />
        </th>
        <td class="w80">

          <span id="attachmentFileErrorArea2"></span>
          <input type="file" id="attachmentAreaBtn2" class="display_none" onchange="ptl100UploadImgFile(this);" multiple="false">
          <input type="hidden" name="attachmentMode2" value="7" />

          <button type="button" value="<gsmsg:write key="ptl.ptl100.1" />" class="baseBtn" onClick="attachmentLoadFile('2');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="ptl.ptl100.1" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="ptl.ptl100.1" />">
            <gsmsg:write key="ptl.ptl100.1" />
          </button>

          <logic:notEmpty name="ptl100Form" property="ptl100ImageList">

            <logic:iterate id="imageModel" name="ptl100Form" property="ptl100ImageList" indexId="idx">

            <div class="mt5 display_flex">

              <div class="verAlignMid">
                <img class="classic-display" src="../common/images/classic/icon_temp_file_2.png" draggable="false">
                <img class="original-display" src="../common/images/original/icon_attach.png" draggable="false">
                <a href="#!" onclick="selectPortletImage(<bean:write name="imageModel" property="pliSid" />);">
                <span class="textLink ml5"><bean:write name="imageModel" property="pliName" /></span>
                </a>
                <img class="ml5 cursor_p btn_originalImg-display" src="../common/images/original/icon_delete.png" onclick="deleteImage('<bean:write name="imageModel" property="pliSid" />');" draggable="false">
                <img class="ml5 cursor_p btn_classicImg-display" src="../common/images/classic/icon_delete.png" onclick="deleteImage('<bean:write name="imageModel" property="pliSid" />');" draggable="false">
              </div>

            </div>

            </logic:iterate>

          </logic:notEmpty>

        </td>
      </tr>
    </logic:equal>
    <tr>
      <th class="w20 no_w">
        <gsmsg:write key="ptl.8" />
      </th>
      <td class="w80">
        <html:textarea property="ptl100description" styleClass="w100" onkeyup="showLengthStr(value, 1000, 'inputlength');" styleId="inputstr" rows="6" />
        <div>
          <span class="formCounter"><gsmsg:write key="wml.js.15" /></span><span id="inputlength" class="formCounter">0</span>&nbsp;<span class="formCounter_max">/&nbsp;1000&nbsp;<gsmsg:write key="cmn.character" /></span>
        </div>
      </td>
    </tr>
  </table>
  <div class="footerBtn_block">
    <button type="button" value="<gsmsg:write key="cmn.ok" />" class="baseBtn" onClick="buttonPush('ptl100add');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
      <gsmsg:write key="cmn.ok" />
    </button>
    <logic:equal name="ptl100Form" property="ptlCmdMode" value="<%= String.valueOf(tCmdEdit) %>">
      <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>
    </logic:equal>
    <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('ptl100back');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<!-- Footer -->

<script type="text/javascript">
var font  = "Andale Mono=Andale Mono, andale mono, monospace;Arial=Arial, arial, helvetica, sans-serif;Arial Black=Arial Black, arial black, sans-serif;";
    font += "Book Antiqua=Book Antiqua, book antiqua, palatino, serif;Comic Sans MS=Comic Sans MS, comic sans ms, sans-serif;";
    font += "Courier New=Courier New, courier new, courier, monospace;Georgia=Georgia, georgia, palatino, serif;Helvetica=Helvetica, helvetica, arial, sans-serif;";
    font += "Impact=Impact, impact, sans-serif;Σψµβολ=Σψµβολ, symbol;Tahoma=Tahoma, tahoma, arial, helvetica, sans-serif;Terminal=terminal, monaco, monospace;";
    font += "Times New Roman=Times New Roman, times new roman, times, serif;Trebuchet MS=Trebuchet MS, trebuchet ms, geneva, sans-serif;";
    font += "Verdana=Verdana, verdana, geneva, sans-serif;Webdings=Webdings, webdings;Wingdings=Wingdings,wingdings, zapf dingbats;";
    font += "メイリオ=メイリオ, Meiryo;明朝=ＭＳ Ｐ明朝,MS PMincho,ヒラギノ明朝 Pro W3,Hiragino Mincho Pro,serif;";
    font += "ゴシック=ＭＳ Ｐゴシック,MS PGothic,ヒラギノ角ゴ Pro W3,Hiragino Kaku Gothic Pro,sans-serif";
var fontSize = "8pt 10pt デフォルト=11pt 12pt 14pt 18pt 24pt 36pt";
tinymce.init({
    selector: '#ptletContentArea',
    plugins: [
      'advlist autolink link image lists charmap hr anchor pagebreak spellchecker',
      'searchreplace visualblocks visualchars code fullscreen insertdatetime media nonbreaking',
      'save table contextmenu directionality template paste textcolor preview colorpicker'
    ],
    menubar: false,
    toolbar1: 'undo redo | visualblocks | styleselect fontsizeselect fontselect bold italic strikethrough forecolor backcolor',
    toolbar2: 'alignleft aligncenter alignright alignjustify | bullist numlist outdent indent table | link image media preview code addbodyfile',
    content_style: 'p {font-size: 11pt; font-family: "メイリオ";}',
    font_formats: font,
    fontsize_formats: fontSize,
    width:"100%",
    height:"430px",
    resize: 'both',
    language: "ja",
    deprecation_warnings: false,
    dragDropUpload: false,
    paste_filter_drop: false,
    external_plugins: {
    },
    setup: function (editor) {
      editor.ui.registry.addButton('addbodyfile', {
        text: msglist["cmn.insert.content"],
        onAction: function () {
          attachmentLoadFile('2');
        }
      });
    },
    init_instance_callback: (editor) => {
      editor.contentDocument.addEventListener("dragover", function(e) {
        if ('attachmentOverlayShow' in window) {
          if (!isDisplayAttachmentOverlay()) {
            attachmentOverlayShow(e);
          }
        }
      })
    }
  });

</script>

</html:form>

<!-- Footer -->
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>


</body>

</html:html>