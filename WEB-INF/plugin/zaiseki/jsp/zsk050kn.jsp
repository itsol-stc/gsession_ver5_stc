<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

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

<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.draggable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.droppable.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/userpopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../reserve/js/sisetuPopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zsk010.js?<%= GSConst.VERSION_PARAM %>"></script>
<title>GROUPSESSION <gsmsg:write key="cmn.zaiseki.management" /></title>
<script type="text/javascript">
<!--
var zaseki;
var eleX = 0;
var eleY = 0;
var htmlStr = "";
var elementKey = '';
var imgX = 0;
var imgY = 0;
var msg1 = '<gsmsg:write key="zsk.10" />';
var msg2 = '<gsmsg:write key="zsk.09" />';
function init() {
  var pars = 'editZifSid=<bean:write name="zsk050knForm" property="editZifSid" />';
  imgX = $('#imgDiv').offset().left;
  imgY = $('#imgDiv').offset().top;
  var url = "../zaiseki/zsk050kn.do?CMD=getElmInfo";
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
      doDraggable();
    },
    error: function(res){
      alert(msg2);
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
  dspname = "";
  dspeleX = 0;
  dspeleY = 0;
  for (j = 0; j < zaseki.length; j++) {
    dsplinkKey = zaseki[j].linkKey;
    dsplinkKbn = zaseki[j].linkKbn;
    dsplinkSid = zaseki[j].linkSid;
    dspname = zaseki[j].linkName;
    dspnameClass = zaseki[j].linkNameClass;
    dspeleX = zaseki[j].linkX;
    dspeleY = zaseki[j].linkY;
    dspeleX = parseInt(imgX) + parseInt(dspeleX);
    dspeleY = parseInt(imgY) + parseInt(dspeleY);
    if (dsplinkKbn == 0) {
      htmlStr = '<div class="pos_abs" id="' +
      dsplinkKey +
      '" style="left:' + dspeleX + '; top:' + dspeleY + ';">' +
      '<a href="#!" class="zsk_labelTextColor" onClick="return openUserInfoWindow(' +
      dsplinkSid +
      ')">' +
      '<span class="zsk_label-zaiseki fs_10 borC_deep">' +
      '<span class="' + dspnameClass + '">' +
      $('<span>').text(dspname).html() +
      '</span></span></a></div>';
    } else if (dsplinkKbn == 1) {
      htmlStr = '<div class="pos_abs" id="' +
      dsplinkKey +
      '" style="left:' + dspeleX + '; top:' + dspeleY + ';">' +
      '<a href="#!" class="zsk_labelTextColor" onClick="return openSisetuSyosai(' + dsplinkSid + ')">' +
      '<span class="zsk_rsvLabel-unused fs_10" >' +
      $('<span>').text(dspname).html() +
      '</span></a></div>';
    } else if (dsplinkKbn == 2) {
      htmlStr = '<div class="pos_abs" id="' +
      dsplinkKey +
      '" style="left:' + dspeleX + '; top:' + dspeleY + ';">' +
      '<span class="zskMsg_text fs_10 zsk_labelTextColor">' +
      $('<span>').text(dspname).html() +
      '</span></div>';
    }
    document.getElementById("key").innerHTML += htmlStr;
  }
}

function doDraggable() {
    for (j = 0; j < zaseki.length; j++) {
      elementKey = zaseki[j].linkKey;
      $('#' + elementKey).draggable({
        disabled: true
      });
      //Edge対応でわざと動かすイベントを挿入（同じ座標に移動しているため位置は変わらない）
      dspeleX = zaseki[j].linkX;
      dspeleY = zaseki[j].linkY;
      dspeleX = parseInt(imgX) + parseInt(dspeleX);
      dspeleY = parseInt(imgY) + parseInt(dspeleY);
      $('#' + elementKey).animate({'top':dspeleY,'left':dspeleX},0);
    }
}

function doMoveElement(element, xIndex, yIndex) {
  pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />' +
         '&elKey=' +
          element.id +
          '&indexx=' +
          xIndex +
          '&indexy=' +
          yIndex;
  url = "../zaiseki/zsk050.do?CMD=setElmInfo";
  var a = $.ajax({
    url: url,
    type: "POST",
    data: pars,
    success: function(res){
    },
    complete: function(res){
    },
    error: function(res){
        alert(msg2);
    }
  });
}
-->
</script>
</head>

<body onload="init();">
<html:form action="/zaiseki/zsk050kn">
<input type="hidden" name="CMD">
<html:hidden name="zsk050knForm" property="backScreen" />
<html:hidden name="zsk050knForm" property="initFlg" />
<html:hidden name="zsk050knForm" property="editZifSid" />
<html:hidden name="zsk050knForm" property="zasekiMapName" />
<html:hidden name="zsk050knForm" property="zasekiSortNum" />

<html:hidden name="zsk050knForm" property="selectZifSid" />
<html:hidden name="zsk050knForm" property="uioStatus" />
<html:hidden name="zsk050knForm" property="uioStatusBiko" />
<html:hidden name="zsk050knForm" property="sortKey" />
<html:hidden name="zsk050knForm" property="orderKey" />

<html:hidden name="zsk050knForm" property="selectGroup" />
<html:hidden name="zsk050knForm" property="selectRsvGroup" />
<html:hidden name="zsk050knForm" property="commentValue" />
<div id="key"></div>
<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<!-- BODY -->
<div class="pageTitle">
  <ul>
    <li>
      <img class="header_pluginImg-classic" src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
      <img class="header_pluginImg" src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.zaiseki.management" />">
    </li>
    <li>
      <gsmsg:write key="cmn.admin.setting" />
    </li>
    <li class="pageTitle_subFont">
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="zsk.zsk050.04" /><gsmsg:write key="cmn.check" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('zsk050knCommit');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
          <gsmsg:write key="cmn.final" />
        </button>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk050');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>
<div class="wrapper">
  <div class="txt_l">
    <logic:messagesPresent message="false">
      <html:errors/>
    </logic:messagesPresent>
  </div>
  <div class="wrapperContent-top">
    <div class="txt_l">
      <span class="verAlignMid">
        <gsmsg:write key="zsk.29" /><gsmsg:write key="wml.215" />&nbsp;<bean:write name="zsk050knForm" property="zasekiMapName" />
        <span class="ml10">
          <gsmsg:write key="cmn.sort" /><gsmsg:write key="wml.215" />&nbsp;<bean:write name="zsk050knForm" property="zasekiSortNum" />
        </span>
      </span>
    </div>
  </div>
  <div class="bor_t1 no_w txt_l pt10">
    <div id="imgDiv" class="pos_sta">
      <logic:notEmpty name="zsk050knForm" property="imageFileName">
        <img src="../zaiseki/zsk050kn.do?CMD=imageDownLord&imageFileName=<bean:write name="zsk050knForm" property="imageFileName" />" name="userPhoto">
      </logic:notEmpty>
    </div>
  </div>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.final" />" onClick="buttonPush('zsk050knCommit');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_kakutei.png" alt="<gsmsg:write key="cmn.final" />">
      <gsmsg:write key="cmn.final" />
    </button>
    <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk050');">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
      <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>


<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>
</body>
</html:html>