<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.UserSearchModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<!DOCTYPE html>
<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../schedule/jquery_ui/css/jquery.ui.dialog.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/jquery/jquery-theme.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<theme:css filename="theme.css"/>
<link rel=stylesheet href='../zaiseki/css/zaiseki.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>

<gsjsmsg:js filename="gsjsmsg.js"/>
<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../schedule/jquery_ui/js/jquery-ui-1.8.14.custom.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.draggable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.droppable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/sisetuPopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zsk010.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript">
<!--
  //自動リロード
  <logic:notEqual name="zsk010Form" property="zsk010Reload" value="0">
    var reloadinterval = <bean:write name="zsk010Form" property="zsk010Reload" />;
    setTimeout("buttonPush('reload')",reloadinterval);
  </logic:notEqual>
-->
</script>

<script type="text/javascript">
<!--
var zaseki;
var eleX = 0;
var eleY = 0;
var htmlStr = "";
var elementKey = '';
var imgX = 0;
var imgY = 0;

function init() {
  var pars = '?CMD=getElmInfo&selectZifSid=<bean:write name="zsk010Form" property="selectZifSid" />';
  imgX = $('#imgDiv').offset().left;
  imgY = $('#imgDiv').offset().top;
  var url = "../zaiseki/zsk010.do";
  url= url + pars;
  var a = $.ajax({
    async: false,
    url: url,
    type: "POST",
    data: pars,
    success: function(res){
      readjson(res);
    },
    complete: function(res){
      createHtml();
    }
  });
}

function readjson(httpObject) {
  data = eval("(" + httpObject + ")");
  var json = data.zasekielement;
  zaseki = json
}

function createHtml() {
  dsplinkKey = "";
  dsplinkKbn = "";
  dsplinkSid = "";
  dsplinkUio = "";
  dspname = "";
  dspeleX = 0;
  dspeleY = 0;
  for (j = 0; j < zaseki.length; j++) {
    dsplinkKey = zaseki[j].linkKey;
    dsplinkKbn = zaseki[j].linkKbn;
    dsplinkSid = zaseki[j].linkSid;
    dsplinkUio = zaseki[j].linkUio;
    dspname = zaseki[j].linkName;
    dspclass = zaseki[j].linkNameClass;
    dspeleX = zaseki[j].linkX;
    dspeleY = zaseki[j].linkY;
    dspeleX = parseInt(imgX) + parseInt(dspeleX);
    dspeleY = parseInt(imgY) + parseInt(dspeleY);
    htmlStr = "";
    if (dsplinkKbn == 0) {
      htmlStr = '<div class="wp5 pos_abs" id="' +
      dsplinkKey +
      '" style="left: ' + dspeleX + 'px; top: ' + dspeleY + 'px;">' +
      '<span id="' + dsplinkSid + '" class="js_zskUio_etc zsk_label-sonota fs_10 borC_deep">' +
      '<a href="#!" class="zsk_labelTextColor" onClick="return openUserIoWindow(' +
      dsplinkSid +
      ',' +
      dsplinkUio +
      ')"><span class=" '+ dspclass +'">' +
      $('<span>').text(dspname).html() +
      '</span></a>' +
      '</span>' +

      '</div>';
      if (dsplinkUio == 1) {
        htmlStr = '<div class="wp5 pos_abs" id="' +
        dsplinkKey +
        '" style="left: ' + dspeleX + 'px; top: ' + dspeleY + 'px;">' +
        '<span id="' + dsplinkSid + '" class="js_zskUio_in zsk_label-zaiseki fs_10 borC_deep">' +
        '<a href="#!" class="zsk_labelTextColor" onClick="return openUserIoWindow(' +
        dsplinkSid +
        ',' +
        dsplinkUio +
        ')"><span class=" '+ dspclass +'">' +
        $('<span>').text(dspname).html() +
        '</span></a>' +
        '</span>' +
        '</div>';
      } else if (dsplinkUio == 2) {
        month="month";
        htmlStr = '<div class="wp5 pos_abs" id="' +
        dsplinkKey +
        '" style="left: ' + dspeleX + 'px; top: ' + dspeleY + 'px;">' +
        '<span id="' + dsplinkSid + '" class="js_zskUio_leave zsk_label-huzai fs_10 borC_deep">' +
        '<a href="#!" class="zsk_labelTextColor" onClick="return openUserIoWindow(' +
        dsplinkSid +
        ',' +
        dsplinkUio +
        ')"><span class=" '+ dspclass +'">' +
        $('<span>').text(dspname).html() +
        '</span></a>' +
        '</span>' +
        '</div>';
      }
    } else if (dsplinkKbn == 1) {
      if (dsplinkUio == 0) {
        htmlStr = '<div class="wp5 pos_abs" id="' +
        dsplinkKey +
        '" style="left: ' + dspeleX + 'px; top: ' + dspeleY + 'px;">' +
        '<a href="#!" class="zsk_labelTextColor" onClick="return openSisetuSyosai(' + dsplinkSid + ', 1)">' +
        '<span class="zsk_rsvLabel-unused fs_10">' +
        $('<span>').text(dspname).html() +
        '</span></a></div>';
      } else {
        htmlStr = '<div class="wp5 pos_abs" id="' +
        dsplinkKey +
        '" style="left: ' + dspeleX + 'px; top: ' + dspeleY + 'px;">' +
        '<a href="#!" class="zsk_labelTextColor" onClick="return openSisetuSyosai(' + dsplinkSid + ', 1)">' +
        '<span class="zsk_rsvLabel-used fs_10">' +
        $('<span>').text(dspname).html() +
        '</span></a></div>';
      }
    } else if (dsplinkKbn == 2) {
      htmlStr = '<div class="wp5 pos_abs" id="' +
      dsplinkKey +
      '" style="left: ' + dspeleX + 'px; top: ' + dspeleY + 'px;">' +
      '<span class="zskMsg_text fs_10 zsk_labelTextColor">' +
      $('<span>').text(dspname).html() +
      '</span></div>';
    }
    document.getElementById("key").innerHTML += htmlStr;
  }
}
-->
</script>

<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
</head>

<body onunload="windowClose();" onload="init();">
<div id="key"></div>
<html:form action="/zaiseki/zsk010">

<input type="hidden" name="CMD">
<html:hidden property="cmd" />
<html:hidden name="zsk010Form" property="sortKey" />
<html:hidden name="zsk010Form" property="orderKey" />
<html:hidden name="zsk010Form" property="smailUseOk" />
<input type="hidden" name="uioUpdateUsrSid">
<input type="hidden" name="uioUpdateStatus">
<input type="hidden" name="sch010SelectUsrSid">
<input type="hidden" name="sch010SelectUsrKbn">

<logic:notEmpty name="zsk010Form" property="elementKeyList" scope="request">
  <logic:iterate id="key" name="zsk010Form" property="elementKeyList" scope="request">
    <input type="hidden" name="elementKey" value="<bean:write name="key"/>">
  </logic:iterate>
</logic:notEmpty>

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../zaiseki/images/classic/header_zaiseki.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
      <img class="header_pluginImg" src="../zaiseki/images/original/header_zaiseki.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
    </li>
    <li>
      <gsmsg:write key="cmn.zaiseki.management" />
    </li>
    <li class="pageTitle_subFont">
      <gsmsg:write key="zsk.20" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.reload" />" onClick="buttonPush('reload');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_reload.png" alt="<gsmsg:write key="cmn.preferences2" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_reload.png" alt="<gsmsg:write key="cmn.preferences2" />">
          <gsmsg:write key="cmn.reload" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="wrapperContent-top">
    <div class="txt_l">
      <span class="verAlignMid">
        <gsmsg:write key="cmn.user" /><gsmsg:write key="wml.215" />
        <span class="zai_header-zaiseki status_frame-base borC_light" >
          <gsmsg:write key="cmn.zaiseki" />
        </span>
        <span class="zai_header-huzai status_frame-base borC_light" >
          <gsmsg:write key="cmn.absence" />
        </span>
        <span class="zai_header-sonota status_frame-base borC_light" >
          <gsmsg:write key="cmn.other" />
        </span>
      </span>
      <span class="verAlignMid ml10">
        <gsmsg:write key="cmn.facility" /><gsmsg:write key="wml.215" />
        <span class="zsk_headerLabel-unused status_frame-base borC_light" >
          <gsmsg:write key="cmn.unused" />
        </span>
        <span class="zsk_headerLabel-used status_frame-base borC_light" >
          <gsmsg:write key="cmn.in.use" />
        </span>
      </span>
      <span class="verAlignMid ml10">
        <gsmsg:write key="zsk.29" />
        <html:select property="selectZifSid" styleClass="ml5" onchange="changeZasekiCombo();">
          <html:optionsCollection name="zsk010Form" property="zifLabelList" value="value" label="label" />
        </html:select>
      </span>
    </div>
  </div>
  <div class="wrapper_2column bor_t1">
    <div class="no_w mt10 mr10">
      <div id="imgDiv" class="pos_sta">
        <logic:notEqual name="zsk010Form" property="zsk010binSid" value="-1">
          <logic:notEqual name="zsk010Form" property="selectZifSid" value="-1">
            <img src="../zaiseki/zsk010.do?CMD=imageDownLord&zsk010binSid=<bean:write name='zsk010Form' property='zsk010binSid' />" name="zasekiImage">
          </logic:notEqual>
        </logic:notEqual>
      </div>
    </div>
    <div>
      <logic:notEmpty name="zsk010Form" property="userList" scope="request">
        <table class="table-top wp400">
          <tr>
            <!-- 氏名 -->
            <th colspan="2" class="w45"><gsmsg:write key="cmn.name" /></th>
            <!-- 在席状況 -->
            <th class="w20"><gsmsg:write key="zsk.20" /></th>
            <!-- 在席コメント -->
            <th class="w35"><gsmsg:write key="zsk.23" /></th>
          </tr>

          <logic:iterate id="userMdl" name="zsk010Form" property="userList" scope="request" indexId="idx" type="UserSearchModel">
            <tr>
              <input type="hidden" name="smlAble[<bean:write name="userMdl" property="usrSid" />]" value="<bean:write name="userMdl" property="smlAble"/>"/>
              <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
                <bean:define id="zskColor" value="zsk_listColor-zaiseki" />
              </logic:equal>
              <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
                <bean:define id="zskColor" value="zsk_listColor-huzai" />
              </logic:equal>
              <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
                <bean:define id="zskColor" value="zsk_listColor-sonota" />
              </logic:equal>
              <td class="txt_l <bean:write name="zskColor" />">
                <a href="#!" onClick="return openUserInfoWindow(<bean:write name="userMdl" property="usrSid" />)">
                  <span class="<%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>">
                    <bean:write name="userMdl" property="usiSei" />&nbsp;&nbsp;<bean:write name="userMdl" property="usiMei" />
                  </span>
                </a>
              </td>
              <td class="txt_c no_w <bean:write name="zskColor" />">
                <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.change" />" onClick="openUserIoWindow(<bean:write name="userMdl" property="usrSid"/>, <bean:write name="userMdl" property="uioStatus"/>);">
                  <gsmsg:write key="cmn.change" />
                </button>
              </td>
              <td class="txt_c <bean:write name="zskColor" />">
                <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_IN) %>">
                  <span class="classic-display">
                    <gsmsg:write key="cmn.zaiseki" />
                  </span>
                  <span class="original-display zsk_listStatus-zaiseki">
                    <gsmsg:write key="cmn.zaiseki" />
                  </span>
                </logic:equal>
                <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_LEAVE) %>">
                  <span class="classic-display">
                    <gsmsg:write key="cmn.absence" />
                  </span>
                  <span class="original-display zsk_listStatus-huzai">
                    <gsmsg:write key="cmn.absence" />
                  </span>
                </logic:equal>
                <logic:equal name="userMdl" property="uioStatus" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConst.UIOSTS_ETC) %>">
                  <span class="classic-display">
                    <gsmsg:write key="cmn.other" />
                  </span>
                  <span class="original-display zsk_listStatus-sonota">
                    <gsmsg:write key="cmn.other" />
                  </span>
                </logic:equal>
              </td>
              <td class="txt_l <bean:write name="zskColor" />">
                <bean:write name="userMdl" property="uioComment" />
              </td>
            </tr>
          </logic:iterate>
        </table>
      </logic:notEmpty>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>

<div id="smlPop" title="" class="display_n">
  <div id="smlCreateArea" class="w100"></div>
</div>

</body>
</html:html>