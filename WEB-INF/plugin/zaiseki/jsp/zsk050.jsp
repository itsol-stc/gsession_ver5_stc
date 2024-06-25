<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.cmn.model.UserSearchModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>
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

<script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src='../common/js/jquery-ui-1.8.16/jquery-ui-1.8.16.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.core.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.widget.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.mouse.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.draggable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/jquery-ui-1.8.16/ui/jquery.ui.droppable.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmn110.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../zaiseki/js/zsk050.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/attachmentFile.js?<%=GSConst.VERSION_PARAM%>"></script>

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
  var pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />';
  imgX = $('#imgDiv').offset().left;
  imgY = $('#imgDiv').offset().top;
  var url = "../zaiseki/zsk050.do?CMD=getElmInfo";
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
    dspeleX = zaseki[j].linkX;
    dspeleY = zaseki[j].linkY;
    dspeleX = parseInt(imgX) + parseInt(dspeleX);
    dspeleY = parseInt(imgY) + parseInt(dspeleY);
    htmlStr = "";
    if (dsplinkKbn == 0) {
      htmlStr = '<div id="' +
      dsplinkKey +
      '" class="can_drop z_index-20 wp5 pos_abs" style="left:' + dspeleX + '; top:' + dspeleY + ';">' +
      '<span class="zsk_label-zaiseki fs_10 borC_deep zsk_labelTextColor">' +
      '<span class="' + zaseki[j].linkNameClass + '">' +
      $('<span>').text(dspname).html() +
      '</span></span></div>';
    } else if (dsplinkKbn == 1) {
      htmlStr = '<div id="' +
      dsplinkKey +
      '" class="can_drop z_index-20 wp5 pos_abs" style="left:' + dspeleX + '; top:' + dspeleY + ';">' +
      '<span class="zsk_rsvLabel-unused fs_10 zsk_labelTextColor">' +
      $('<span>').text(dspname).html() +
      '</span></div>';
    } else if (dsplinkKbn == 2) {
      htmlStr = '<div id="' +
      dsplinkKey +
      '" class="can_drop z_index-20 wp5 pos_abs" style="left:' + dspeleX + '; top:' + dspeleY + ';">' +
      '<span class="zskMsg_text fs_10 zsk_labelTextColor cursor_p">' +
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
       scroll: false
    });
    //Edge対応でわざと動かすイベントを挿入（同じ座標に移動しているため位置は変わらない）
    dspeleX = zaseki[j].linkX;
    dspeleY = zaseki[j].linkY;
    dspeleX = parseInt(imgX) + parseInt(dspeleX);
    dspeleY = parseInt(imgY) + parseInt(dspeleY);
    $('#' + elementKey).animate({'top':dspeleY,'left':dspeleX},0);
  }

  $("#imgDiv").droppable({
    accept: ".can_drop",
    drop: function(event, ui) {
      x = $(ui.draggable).offset().left;
      y = $(ui.draggable).offset().top;
      xIndex = parseInt($(ui.draggable).offset().left) - parseInt($('#imgDiv').offset().left)
      yIndex = parseInt($(ui.draggable).offset().top) - parseInt($('#imgDiv').offset().top)
      doMoveElement(ui.draggable, xIndex, yIndex);
    }
  });
  $("#garbage_box").droppable({
    accept: ".can_drop",
    hoverClass: "bgC_select",
    drop: function(event, ui) {
      delElement($(ui.draggable).attr("id"));
    }
  });

  $("#allDiv").droppable({
    accept: ".can_drop",
    drop: function(event, ui) {
      x = parseInt($(ui.draggable).offset().left);
      y = parseInt($(ui.draggable).offset().top);
      setUndoIndex($(ui.draggable).attr("id"))
    }
  });
}

function doMoveElement(element, xIndex, yIndex) {
  pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />' +
         '&elKey=' +
          $(element).attr("id") +
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

function delElement(eid) {
  pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />' +
         '&elKey=' +
          eid
          ;
  url = "../zaiseki/zsk050.do?CMD=delElmInfo";
  var a = $.ajax({
    url: url,
    type: "POST",
    data: pars,
    success: function(res){

    },
    complete: function(res){
      $('#' + eid).remove();
    },
    error: function(res){
        alert(msg2);
    }
  });
}

function setUndoIndex(key) {
  name = "";
  linkKey = "";
  eleX = 0;
  eleY = 0;
  pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />' +
          '&elKey=' +
          key
          ;
  url = "../zaiseki/zsk050.do?CMD=getElmIndex";
  var a = $.ajax({
    url: url,
    type: "POST",
    data: pars,
    success: function(res){
      readjson(res);
    },
    complete: function(res){
      for (j = 0; j < zaseki.length; j++) {
        linkKey = zaseki[j].linkKey;
        eleX = zaseki[j].linkX;
        eleY = zaseki[j].linkY;
      }
      eleX = parseInt($('#imgDiv').offset().left) + parseInt(eleX);
      eleY = parseInt($('#imgDiv').offset().top) + parseInt(eleY);
      $('#' + linkKey)[0].style.left = eleX;
      $('#' + linkKey)[0].style.top = eleY;
    },
    error: function(res){
      alert(msg2);
    }
  });
}

function setElementNameHtml(kbn, sid, id) {
  var addname = "";
  pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />' +
          '&addElKbn=' +
          kbn +
          '&addElSid=' +
          sid
          ;
  url = "../zaiseki/zsk050.do?CMD=getElmName";
  var a = $.ajax({
    url: url,
    type: "POST",
    data: pars,
    success: function(res){
      readjson(res);
    },
    complete: function(res){
      addname = getNameFromJson();
      if (kbn == 0) {
        htmlStr = '<div id="' +
        id +
        '" class="can_drop">' +
        '<span class="zsk_uio_in">' +
        '<span class="' + zaseki[0].linkNameClass + '">' +
        name +
        '</span></span></div>';
        setElementHtml(0, sid, id, htmlStr, '');
      } else if(kbn == 1) {
        htmlStr = '<div id="' +
        id +
        '" class="can_drop">' +
        '<span class="zsk_rsv">' +
        addname +
        '</span></div>';
        setElementHtml(1, sid, id, htmlStr, '');
      }
    },
    error: function(res){
      alert(msg2);
    }
  });
}

function getNameFromJson() {
    name = "";
    if (zaseki.length > 0) {
      name = zaseki[0].linkName;
    }
    return name;
}

function setElementHtml(kbn, sid, id, htmlStr, msg) {
  x = $('#imgDiv').offset().left;
  y = $('#imgDiv').offset().top;
  setX = parseInt(x) + 10;
  setY = parseInt(y) + 10;
  document.getElementById("key").innerHTML += htmlStr;
  $('#' + id)[0].style.left = setX;
  $('#' + id)[0].style.top = setY;
  var keys = getElementsByClassName('can_drop', null, 'div');
  for (i = 0; i < keys.length; i++) {
    $('#' + keys[i].id).draggable({
      scroll: false
    });
  }
  addElement(id, 10, 10, kbn, sid, msg);
}

function getElementsByClassName(searchClass, domNode, tagName) {
    if (domNode == null) domNode = document;
    if (tagName == null) tagName = '*';
    var el = new Array();
    var tags = domNode.getElementsByTagName(tagName);
    var tcl = " "+searchClass+" ";
    for(i=0,j=0; i<tags.length; i++) {
        var test = " " + tags[i].className + " ";
        if (test.indexOf(tcl) != -1)
        el[j++] = tags[i];
    }
    return el;
}

function addElement(eid, xIndex, yIndex, kbn, sid, addElMsg) {
  pars = 'editZifSid=<bean:write name="zsk050Form" property="editZifSid" />' +
         '&elKey=' +
          eid +
          '&indexx=' +
          xIndex +
          '&indexy=' +
          yIndex +
          '&addElKbn=' +
          kbn +
          '&addElSid=' +
          sid +
          '&addElMsg=' +
          addElMsg
          ;
  url = "../zaiseki/zsk050.do?CMD=addElmInfo";
  var a = $.ajax({
    url: url,
    type: "POST",
    data: pars,
    success: function(res){
    },
    complete: function(res){
     changeCombo();
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
<html:form action="/zaiseki/zsk050">
<div id="allDiv">
<div id="key"></div>

<input type="hidden" name="CMD">
<html:hidden name="zsk050Form" property="backScreen" />
<html:hidden name="zsk050Form" property="initFlg" />
<html:hidden name="zsk050Form" property="editZifSid" />

<html:hidden name="zsk050Form" property="indexx" />
<html:hidden name="zsk050Form" property="indexy" />
<html:hidden name="zsk050Form" property="elKey" />
<html:hidden name="zsk050Form" property="addElKbn" />
<html:hidden name="zsk050Form" property="addElSid" />
<html:hidden name="zsk050Form" property="addElMsg" />


<html:hidden name="zsk050Form" property="selectZifSid" />
<html:hidden name="zsk050Form" property="uioStatus" />
<html:hidden name="zsk050Form" property="uioStatusBiko" />
<html:hidden name="zsk050Form" property="sortKey" />
<html:hidden name="zsk050Form" property="orderKey" />

<% String tempMode = String.valueOf(jp.groupsession.v2.cmn.GSConstCommon.CMN110MODE_GAZOU); %>
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
      <span class="pageTitle_subFont-plugin"><gsmsg:write key="cmn.zaiseki.management" /></span><gsmsg:write key="zsk.zsk050.04" />
    </li>
    <li>
      <div>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('zsk050kn');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
          <gsmsg:write key="cmn.ok" />
        </button>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('zsk050delete');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
        <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk030');">
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
        <gsmsg:write key="zsk.29" /><gsmsg:write key="wml.215" />
        <html:text name="zsk050Form" property="zasekiMapName" styleClass="wp150" maxlength="20"/>
        <span class="ml10">
          <gsmsg:write key="cmn.sort" /><gsmsg:write key="wml.215" />
          <html:text name="zsk050Form" property="zasekiSortNum" styleClass="wp50" maxlength="3"/>
        </span>
      </span>
    </div>
  </div>
  <div class="wrapper_2column bor_t1">
    <div>
      <table class="table-top wp350">
        <tr>
          <th class="txt_l">
            <span class="verAlignMid w100">
              <span class="w20 no_w">
                <gsmsg:write key="zsk.zsk050.06" />
              </span>
              <span class="w80 txt_r">
                <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="zsk.zsk050.07" />" onClick="buttonPush('changeImage');">
                  <gsmsg:write key="zsk.zsk050.07" />
                </button>
              </span>
            </span>
          </th>
        </tr>
        <tr>
          <td>
            <attachmentFile:filearea
            mode="<%= tempMode %>"
            pluginId="<%=GSConst.PLUGINID_ZSK %>"
            tempDirId="zsk050" />
            <br><gsmsg:write key="zsk.42" />
            <br><gsmsg:write key="zsk.41" />
            <br><gsmsg:write key="zsk.43" />
          </td>
        </tr>
      </table>

      <table class="table-top mt10 wp350">
        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.delete" />
          </th>
        </tr>
        <tr>
          <td>
            <gsmsg:write key="zsk.zsk050.10" />
            <div id="garbage_box" class="txt_c pt10 pb10">
              <img class="btn_classicImg-display" src="../zaiseki/images/classic/icon_dust_box.gif" alt="<gsmsg:write key="cmn.trash" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_trash.png" alt="<gsmsg:write key="cmn.trash" />">
            </div>
          </td>
        </tr>
      </table>

      <table class="table-top mt10 wp350">
        <tr>
          <th class="txt_l">
            <gsmsg:write key="zsk.zsk050.08" />
          </th>
        </tr>
        <tr>
          <td>
            <span class="fw_b">
              [<gsmsg:write key="zsk.zsk050.03" />]
            </span>
            <br>
            <gsmsg:write key="zsk.zsk050.18" />
            <br>
            <gsmsg:write key="zsk.zsk050.15" />
            <br>
            <gsmsg:write key="zsk.zsk050.13" /><gsmsg:write key="zsk.zsk050.14" />
          </td>
        </tr>

        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.group" /><gsmsg:write key="wml.215" />
            <html:select property="selectGroup" styleClass="wp200" onchange="changeCombo();">
              <html:optionsCollection name="zsk050Form" property="groupLabelList" value="value" label="label" />
            </html:select>
            <button type="button" id="zsk050GroupBtn" name="rng020keiro(0).keiro(0).usrgrpSel.group.select" class="iconBtn-border groupSelect_btn ml5" onclick="openGroupWindow(this.form.selectGroup, 'selectGroup', '0')" value="&nbsp;&nbsp;">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </th>
        </tr>
        <tr>
          <td>
            <logic:empty name="zsk050Form" property="belongUserList" scope="request">&nbsp;</logic:empty>
            <logic:notEmpty name="zsk050Form" property="belongUserList" scope="request">
              <logic:iterate id="userMdl" name="zsk050Form" property="belongUserList" scope="request" type="UserSearchModel">
                <a href="#!" ondblclick="createUserElement(<bean:write name="userMdl" property="usrSid" />);">
                  <span class="textLink <%=UserUtil.getCSSClassNameNormal(userMdl.getUsrUkoFlg())%>">
                    <bean:write name="userMdl" property="usiSei" />&nbsp;<bean:write name="userMdl" property="usiMei" />
                  </span>
                </a>
                <br>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>

        <tr>
          <th class="txt_l">
            <gsmsg:write key="cmn.facility.group" /><gsmsg:write key="wml.215" />
            <html:select property="selectRsvGroup" styleClass="wp200" onchange="changeCombo();">
              <html:optionsCollection name="zsk050Form" property="rsvGroupLabelList" value="value" label="label" />
            </html:select>
          </th>
        </tr>
        <tr>
          <td>
            <logic:empty name="zsk050Form" property="belongRsvList" scope="request">&nbsp;</logic:empty>
            <logic:notEmpty name="zsk050Form" property="belongRsvList" scope="request">
              <logic:iterate id="rsvMdl" name="zsk050Form" property="belongRsvList" scope="request">
                <a href="#!" ondblclick="createRsvElement(<bean:write name="rsvMdl" property="rsdSid" />)">
                  <span class="textLink">
                    <bean:write name="rsvMdl" property="rsdName" />
                  </span>
                </a>
                <br>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>
      </table>

      <table class="table-top mt10 wp350">
        <tr>
          <th class="txt_l">
            <gsmsg:write key="zsk.zsk050.09" />
          </th>
        </tr>
        <tr>
          <td>
            <span class="fw_b">
              [<gsmsg:write key="zsk.zsk050.03" />]
            </span>
            <br>
            <gsmsg:write key="zsk.zsk050.17" />
            <br>
            <gsmsg:write key="zsk.zsk050.16" />
            <br>
            <gsmsg:write key="zsk.zsk050.13" /><gsmsg:write key="zsk.zsk050.14" />
            <br>
            <html:text name="zsk050Form" property="commentValue" styleClass="wp150" maxlength="20"/>
            <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="zsk.zsk050.01" />" onClick="createTxtElement();">
              <gsmsg:write key="zsk.zsk050.01" />
            </button>
          </td>
        </tr>
      </table>
    </div>
    <div class="no_w mt10 ml10">
      <div id="imgDiv">
        <logic:notEmpty name="zsk050Form" property="imageFileName">
          <img src="../zaiseki/zsk050.do?CMD=imageDownLord&imageFileName=<bean:write name="zsk050Form" property="imageFileName" />&<bean:write name="zsk050Form" property="zsk050RndNum" />" name="userPhoto">
        </logic:notEmpty>
      </div>
    </div>
  </div>
  <div class="footerBtn_block mt20">
    <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.ok" />" onClick="buttonPush('zsk050kn');">
     <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.ok" />">
     <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.ok" />">
     <gsmsg:write key="cmn.ok" />
   </button>
   <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.delete" />" onClick="buttonPush('zsk050delete');">
     <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
     <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
     <gsmsg:write key="cmn.delete" />
   </button>
   <button type="button" class="baseBtn" name="btn_ktool" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('zsk030');">
     <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
     <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
     <gsmsg:write key="cmn.back" />
    </button>
  </div>
</div>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</html:form>

</body>
</div>
</html:html>