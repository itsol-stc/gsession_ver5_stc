<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<% String jusin = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_JUSIN); %>
<% String sosin = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_SOUSIN); %>
<% String gomi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_GOMI); %>
<% String label = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_LABEL); %>
<% String unopen = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_UNOPEN); %>
<%
   int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
   int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title>GROUPSESSION <gsmsg:write key="cir.5" /></title>
<script language="JavaScript" src='../common/js/jquery-1.7.2.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script language="JavaScript" src='../common/css/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../circular/js/cir010.js?<%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href='../common/css/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../circular/css/circular.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<jsp:include page="/WEB-INF/plugin/circular/jsp/cir010_message.jsp" />

<logic:notEmpty name="cir010Form" property="cir010AccountTheme" scope="request">
  <bean:define id="selectThemePath" name="cir010Form" property="cir010AccountTheme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="cir010Form" property="cir010AccountTheme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="cir010Form" property="cir010Reload" value="0">
  var reloadinterval = <bean:write name="cir010Form" property="cir010Reload" />;
  setTimeout("buttonPush('reload')", reloadinterval);
  </logic:notEqual>
  -->
</script>
</head>
<body>
  <html:form styleId="cir010Form" action="/circular/cir010">
    <input type="hidden" name="helpPrm" value="<bean:write name="cir010Form" property="cir010cmdMode" />">
    <input type="hidden" name="CMD" value="search">
    <input type="hidden" name="cir010selectInfSid">
    <input type="hidden" name="cir010sojuKbn">
    <html:hidden property="cirAccountSid" />
    <html:hidden property="cir010cmdMode" />
    <html:hidden property="cir010orderKey" />
    <html:hidden property="cir010sortKey" />
    <html:hidden property="cir010SelectLabelSid" />
    <html:hidden property="cir010addLabelType" />
    <html:hidden property="cir010addLabel" />
    <html:hidden property="cir010addLabelName" />
    <html:hidden property="cir010delLabel" />

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <bean:define id="orderKey" name="cir010Form" property="cir010orderKey" />
    <bean:define id="sortKbn" name="cir010Form" property="cir010sortKey" />
    <%int iOrderKey = ((Integer) orderKey).intValue();%>
    <%int iSortKbn = ((Integer) sortKbn).intValue();%>
    <!-- BODY -->

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../circular/images/classic/header_circular.png" alt="<gsmsg:write key="cir.5" />">
          <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_circular_50.png" alt="<gsmsg:write key="cir.5" />">
        </li>
        <li>
          <span class="no_w"><gsmsg:write key="cir.5" /></span>
        </li>
        <li class="pageTitle_subFont">
          <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=jusin%>">
            <gsmsg:write key="cmn.receive2" />
          </logic:equal>
          <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=sosin%>">
            <gsmsg:write key="cmn.sent2" />
          </logic:equal>
          <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=gomi%>">
            <gsmsg:write key="cmn.trash2" />
          </logic:equal>
          <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=label%>">
            <gsmsg:write key="cmn.label" />
          </logic:equal>
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.reload" />" onClick="buttonPush('reload');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.reload" />">
              <gsmsg:write key="cmn.reload" />
            </button>
            <button class="baseBtn" value="<gsmsg:write key="cmn.account" />" onClick="return buttonPush('accountConf');">
              <img class="classic-display" src="../common/images/classic/icon_account_setting.png" alt="<gsmsg:write key="cmn.account" />">
              <img class="original-display" src="../common/images/original/icon_account_setting.png" alt="<gsmsg:write key="cmn.account" />">
              <gsmsg:write key="cmn.account" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper_2column">

      <div class="side-left fs_13">

        <div id="js_account_area" class="side_header cursor_p side_header-folding">
          <span class="side_headerTitle side_header-open fs_13">
            <span class="side_headerArrow"></span>
            <gsmsg:write key="wml.102" />
          </span>
        </div>
        <div id="js_account_child_area" class="side_content">
          <!-- コンボ -->
          <html:select property="cirViewAccount" styleId="account_comb_box" styleClass="account_select">
            <logic:notEmpty name="cir010Form" property="cir010AccountList">
              <logic:iterate id="accountMdl" name="cir010Form" property="cir010AccountList">
                <bean:define id="mukoUserClass" value="" />
                <logic:equal name="accountMdl" property="usrUkoFlg" value="1">
                  <bean:define id="mukoUserClass" value="mukoUserOption" />
                </logic:equal>
                <bean:define id="accoutVal" name="accountMdl" property="accountSid" type="java.lang.Integer" />
                <html:option styleClass="<%= mukoUserClass %>" value="<%= String.valueOf(accoutVal) %>">
                  <bean:write name="accountMdl" property="accountName" />
                </html:option>
              </logic:iterate>
            </logic:notEmpty>
          </html:select>
          <!-- セル -->
          <div class="lh130">
            <logic:notEmpty name="cir010Form" property="cir010AccountList">
              <bean:define id="viewAccoutVal" name="cir010Form" property="cirViewAccount" type="java.lang.Integer" />
              <logic:iterate id="accountMdl" name="cir010Form" property="cir010AccountList">
                <logic:notEqual name="accountMdl" property="accountSid" value="<%= String.valueOf(viewAccoutVal) %>">
                  <div class="mb5 js_account_sel js_side_accountName side_accountName verAlignMid w100 <logic:equal name="accountMdl" property="usrUkoFlg" value="1">mukoUser</logic:equal>" id="<bean:write name="accountMdl" property="accountSid" />">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_account_sel.png">
                    <i class="btn_originalImg-display icon-account_sel"></i>
                    <span><bean:write name="accountMdl" property="accountName" />
                    <!-- 受信件数表示 -->
                    <logic:notEqual name="accountMdl" property="accountMidokuCount" value="0">
                      (<bean:write name="accountMdl" property="accountMidokuCount" />)
                    </logic:notEqual>
                    </span>
                  </div>
                </logic:notEqual>
              </logic:iterate>
            </logic:notEmpty>
          </div>
        </div>
        <div id="js_folder_area" class="side_header cursor_p side_header-folding">
          <span class="side_headerTitle side_header-open fs_13">
            <span class="side_headerArrow"></span>
            <gsmsg:write key="cmn.folder" />
          </span>
        </div>
        <div id="js_folder_child_area" class="side_content p0">
          <div class="content_div">
            <div id="menu_jushin_txt" class="side_folder-focus cursor_p" onclick="changeMode('jusin');">
              <div class="side_folderImg side_folderImg-lineTop classic-display"></div>
              <div class="side_folderImg side_folderImg-jushin"></div>
              <gsmsg:write key="cmn.receive" />
              <!-- 受信件数表示 -->
              <logic:notEqual name="cir010Form" property="cir010JusinMidokuCnt" value="0">
                <span class="fs_11 fw_b">
                  (<bean:write name="cir010Form" property="cir010JusinMidokuCnt" />)
                </span>
              </logic:notEqual>
            </div>
            <!-- 送信 -->
            <div class="cursor_p">
              <div class="side_folder-focus" id="1" onclick="changeMode('sousin');">
                <div class="side_folderImg side_folderImg-line classic-display"></div>
                <div class="side_folderImg side_folderImg-soshin"></div>
                <gsmsg:write key="cmn.sent" />
              </div>
            </div>
            <!-- ゴミ -->
            <div class="cursor_p">
              <div class="cir_dust folder_float"></div>
              <div class="side_folder-focus" id="1" onclick="changeMode('gomi');">
                <logic:notEmpty name="cir010Form" property="cir010LabelList">
                  <div id="gomibako_bottom_div" class="side_folderImg classic-display side_folderImg-line"></div>
                </logic:notEmpty>
                <logic:empty name="cir010Form" property="cir010LabelList">
                  <div id="gomibako_bottom_div" class="side_folderImg classic-display side_folderImg-lineBottom"></div>
                </logic:empty>
                <div class="side_folderImg side_folderImg-dust"></div>
                <gsmsg:write key="cmn.trash" />
                <logic:notEqual name="cir010Form" property="cir010GomiMidokuCnt" value="0">
                  <span class="fs_11 fw_b">
                    (<bean:write name="cir010Form" property="cir010GomiMidokuCnt" />)
                  </span>
                </logic:notEqual>
                <span class="ml5">[</span><a href="#!" class="" id="head_menu_empty_trash_btn"><gsmsg:write key="wml.js.111" /></a>]
              </div>
            </div>

            <!-- ラベル -->
            <logic:notEmpty name="cir010Form" property="cir010LabelList">
              <div>
                <div class="verAlignMid w100 display_flex cursor_p side_folder-focus" id="label_oya">
                  <div class="original-display ml10"></div>
                  <div id="label_oyaIcon" class="side_folderImg cl_webIcon side_folderImg-lineMinusBottom js_lineToggle"></div>
                  <div class="side_folderImg side_folderImg-label ml0 classic-display"></div>
                  <div class="side_folderImg side_folderImg-label ml0 hp20 original-display"></div>
                  <span class="side-folderText">
                    <gsmsg:write key="cmn.label" />
                  </span>
                </div>

                <bean:size id="cir010LabelListSize" name="cir010Form" property="cir010LabelList" />
                <div id="label_ko">
                  <bean:size name="cir010Form" property="cir010LabelList" id="labelSize" />
                  <logic:iterate id="labelData" name="cir010Form" property="cir010LabelList" indexId="idx">
                    <div class="js_label_area">
                      <div class="cursor_p folder_float side_folder-focus" onclick="changeFolder('label', <bean:write name="labelData" property="labelSid" />);">
                        <div class="wp20 hp25 side_folderImg"></div>
                        <% if( idx < cir010LabelListSize - 1) { %>
                        <div class="side_folderImg side_folderImg-line"></div>
                        <% } else { %>
                        <div class="side_folderImg side_folderImg-lineBottom"></div>
                        <% } %>
                        <span onclick="changeFolder('label', <bean:write name="labelData" property="labelSid" />);"> </span>
                        <bean:write name="labelData" property="labelName" />
                        <!-- 未読件数 -->
                        <logic:greaterThan name="labelData" property="midokuCount" value="0">
                          <span class="fs_11 fw_b">
                            (<bean:write name="labelData" property="midokuCount" />)
                          </span>
                        </logic:greaterThan>
                      </div>
                    </div>
                  </logic:iterate>
                </div>
              </div>
            </logic:notEmpty>
          </div>
        </div>
      </div>

      <div class="main">
        <table class="pageTitle_sub">
          <tr class="txt_t">
            <td class="txt_r pb5">
              <html:text property="cir010searchWord" styleClass="wp150 cir_search_form" maxlength="100" />
              <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onClick="buttonPush('search');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
                <gsmsg:write key="cmn.search" />
              </button>
              <logic:notEqual name="cir010Form" property="cir010cmdMode" value="<%= gomi %>">
                <button type="button" name="btn_send" class="baseBtn js_add_label">
                  <img class="btn_classicImg-display pb2" src="../common/images/classic/icon_label.png" alt="<gsmsg:write key="cmn.add.label2" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_label_add.png" alt="<gsmsg:write key="cmn.add.label2" />"
                   >
                  <gsmsg:write key="cmn.add.label2" />
                </button>
                <button type="button" name="btn_send" class="baseBtn js_del_label">
                  <img class="btn_classicImg-display pb2" src="../common/images/classic/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_label_del.png" alt="<gsmsg:write key="wml.js.108" />"
                   >
                  <gsmsg:write key="wml.js.108" />
                </button>
              </logic:notEqual>
              <%boolean canCirCreate = false;%>
              <logic:equal name="cir010Form" property="cirCreateFlg" value="true">
                <button type="button" value="<gsmsg:write key="cir.cir010.3" />" class="baseBtn" onClick="buttonPush('add');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cir.cir010.3" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cir.cir010.3" />">
                  <gsmsg:write key="cir.cir010.3" />
                </button>
                <%canCirCreate = true;%>
              </logic:equal>
              <%boolean cirSendTab = false;%>
              <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= sosin %>">
                <% cirSendTab = true; %>
              </logic:equal>
              <% if (!cirSendTab || canCirCreate) { %>
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
                <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= gomi %>">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                </logic:equal>
                <logic:notEqual name="cir010Form" property="cir010cmdMode" value="<%= gomi %>">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                </logic:notEqual>
                <gsmsg:write key="cmn.delete" />
              </button>
              <% } %>
              <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= gomi %>">
                <button type="button" value="<gsmsg:write key="cmn.undo" />" class="baseBtn" onClick="buttonPush('comeback');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_return.png" alt="<gsmsg:write key="cmn.undo" />">

                  <gsmsg:write key="cmn.undo" />
                </button>
              </logic:equal>
            </td>
          </tr>

          <tr>
            <td>
              <span class="sub_title m0">
                <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=jusin%>">
                      <span class="ml5"><gsmsg:write key="cmn.receive" /></span>
                </logic:equal>
                <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=sosin%>">
                      <span class="ml5"><gsmsg:write key="cmn.sent" /></span>
                </logic:equal>
                <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=gomi%>">
                      <span class="ml5"><gsmsg:write key="cmn.trash" /></span>
                </logic:equal>
                <logic:equal name="cir010Form" property="cir010cmdMode" value="<%=label%>">
                      <span class="ml5"><bean:write name="cir010Form" property="cir010TitleLabelName" /></span>
                </logic:equal>
              </span>
            </td>
          </tr>
        </table>

        <!-- エラーメッセージ -->
        <logic:messagesPresent message="false">
        <div class="mt10 mb5">
          <html:errors />
        </div>
        </logic:messagesPresent>

        <div class="txt_r">
          <!-- ページング -->
          <bean:size id="count1" name="cir010Form" property="cir010PageLabel" scope="request" />
          <logic:greaterThan name="count1" value="1">
            <div class="paging">
              <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo" property="cir010pageNum1" onchange="changePage(1);">
                <html:optionsCollection name="cir010Form" property="cir010PageLabel" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:greaterThan>
        </div>

        <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= jusin %>">
          <%@ include file="/WEB-INF/plugin/circular/jsp/cir010_sub01.jsp"%>
        </logic:equal>
        <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= sosin %>">
          <%@ include file="/WEB-INF/plugin/circular/jsp/cir010_sub02.jsp"%>
        </logic:equal>
        <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= gomi %>">
          <%@ include file="/WEB-INF/plugin/circular/jsp/cir010_sub03.jsp"%>
        </logic:equal>
        <logic:equal name="cir010Form" property="cir010cmdMode" value="<%= label %>">
          <%@ include file="/WEB-INF/plugin/circular/jsp/cir010_sub04.jsp"%>
        </logic:equal>
        <div class="txt_r">
          <!-- ページング -->
          <bean:size id="count2" name="cir010Form" property="cir010PageLabel" scope="request" />
          <logic:greaterThan name="count2" value="1">
            <div class="paging">
              <button type="button" class="webIconBtn" onClick="buttonPush('prev');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
                <i class="icon-paging_left"></i>
              </button>
              <html:select styleClass="paging_combo" property="cir010pageNum2" onchange="changePage(2);">
                <html:optionsCollection name="cir010Form" property="cir010PageLabel" value="value" label="label" />
              </html:select>
              <button type="button" class="webIconBtn" onClick="buttonPush('next');">
                <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
                <i class="icon-paging_right"></i>
              </button>
            </div>
          </logic:greaterThan>
        </div>
      </div>
    </div>

    <!-- ダイアログ -->
    <div id="messagePop" title="" class="display_n">
      <table class="w100 h100">
        <tr>
          <td>
            <img class="classic-display" src="../main/images/classic/header_info.png" alt="cmn.info">
            <img class="original-display" src="../common/images/original/icon_info_32.png" alt="cmn.info">
          </td>
          <td class="pl10 w100">
            <span id="messageArea" class="fs_13"></span>
          </td>
        </tr>
      </table>
    </div>

    <!-- ラベル追加ダイアログ -->
    <div id="labelAddPop" title="<gsmsg:write key="wml.wml010.16" />">
      <table id="labelAddContentArea" class="w100 h100"></table>
    </div>

    <!-- ラベル削除ダイアログ -->
    <div id="labelDelPop" title="<gsmsg:write key="wml.js.108" />">
      <table id="labelDelContentArea" class="w100 h100"></table>
    </div>


    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>
</body>
</html:html>