
// モード 0:インサート 1:アップデート
var mode = 0;
// メッセージSID
var messageSid = "";
//チャットエリア縦幅
var chat_area_height = 0;
// リサイズフラグ
var resize_first = true;
// チャットエリア最小縦幅
var chat_area_min_height = 180;
// テキストエリア最大縦幅
var chat_textarea_max_height = 200;
// テキストエリア最小縦幅
var chat_textarea_min_height = 60;
var errorMsg = "編集中はファイルを送信できません。";

var allDispFlg = 0;
//スクロール時自動読み込みフラグ 0:読み込む 1:読み込まない
var scrollAutoReadFlg = 0;

//スクロールバー判定
jQuery.fn.hasScrollBar = function() {
  return this.get(0) ? this.get(0).scrollHeight > this.innerHeight() : false;
}

// 一定時間待機する関数
var kidoku = null;

function buttonPush(cmd){

    document.forms[1].CMD.value=cmd;
    document.forms[1].submit();
    return false;
}

function group_info() {

  var sid = document.forms[1].cht010SelectPartner.value;
  var kbn = document.forms[1].cht010SelectKbn.value;
  if (kbn == 1) {
    openUserInfoWindow(sid);
  } else {
    var h = window.innerHeight / 2;
    $('#groupInfoPop').dialog({
      position: {
          of : 'body',
          at: 'top+' + h,
          my: 'center'
      },
      modal: true,
        title:$(".js_chtGroupTitle").text(),
        dialogClass:'dialog_button',
        autoOpen: true,  // hide dialog
        bgiframe: true,   // for IE6
        resizable: false,
        width: 800,
        maxHeight: 400,
        overlay: {
          backgroundColor: '#000000',
            opacity: 0.5
        },
          buttons: {
            閉じる: function() {
              $(this).dialog('close');
            }
          }
      });
  }
}

function check_selected(id) {
    if ($("." + id).hasClass("js_media_select cursor_p media_selected")) {
        $("." + id).removeClass("media_selected");
    } else if ($("." + id).hasClass("js_media_select")) {
        $("." + id).addClass("media_selected");
    }
}

var dlCheck = 0;
function check_change() {
  if (dlCheck==1) {
    $(".js_mediaArea").removeClass("js_media_select");
    $(".js_mediaArea").removeClass("cursor_p");
    $(".js_mediaArea").removeClass("media_selected");
    $(".js_media_mine").addClass("media_mine");
    dlCheck = 0;
    document.getElementById("checkImg").src="../chat/images/classic/icon_check.png";
    $("#checkImgI").removeClass("icon-dl_on");
    $("#checkImgI").addClass("icon-dl");
    $(".js_check").prop("checked",false);
  } else {
    $(".js_media_mine").removeClass("media_mine");
    $(".js_mediaArea").removeClass("media_selected");
    $(".js_chtTextArea").removeClass("media_selectedColor");
    $(".js_chtTextArea").addClass("chattextArea_color");
    $(".js_mediaArea").addClass("js_media_select");
    $(".js_mediaArea").addClass("cursor_p");
    $(".js_chtConfirm").addClass("display_n");
    $(".js_chtCansel").addClass("display_n");
    $(".js_chtSend").removeClass("display_n");
    $(".js_chtAttach").removeClass("display_n");
    document.getElementById("checkImg").src="../chat/images/classic/icon_check_s.png";
    $("#checkImgI").removeClass("icon-dl");
    $("#checkImgI").addClass("icon-dl_on");
    mode = 0;
    dlCheck = 1;
    textClear();
    if ($("#js_errorMsg").text() == errorMsg) {
      $("#js_errorMsg").text("");
    }
  }
  ikkatuReset();
}

function ikkatuReset() {
  $(".js_fileNameSpace").children().remove();
  if (dlCheck==1) {
      $(".js_chtCheckbox").removeClass("display_n");
      $(".js_ikkatuArea").removeClass("display_n");
      $(".js_rightSpace").removeClass("p0");
      $(".js_rightSpace").addClass("wp300");
  } else {
      $(".js_chtCheckbox").addClass("display_n");
      $(".js_ikkatuArea").addClass("display_n");
      $(".js_rightSpace").removeClass("wp300");
      $(".js_rightSpace").addClass("p0");
  }
}

function changeTab(tabname) {
    var oldTabCode = chkTabCode();
     // タブメニュー実装
    $("#tabAll").addClass("display_n");
    $("#tabTimeline").addClass("display_n");
     // タブメニュー実装
    $("#" + tabname).removeClass("display_n");

    var tabCode = chkTabCode();

    if (oldTabCode != tabCode) {
        var param = createParamCht010();
        param['CMD'] = 'changeTab';
        param['cht010SelectTab'] = tabCode;
        paramStr = $.param(param, true);
        paramStr = setToken(paramStr);
        $.ajax({
              async: true,
              url:  "../chat/cht010.do",
              type: "post",
              data: paramStr
        }).done(function( data ) {
          if (data["tokenError"]){
            tokenError(data);
          }
        });
    }
}

function chkTabCode() {
    if ($('#tabAll').is(':visible')) {
        return 0;
    }
    if ($('#tabTimeline').is(':visible')) {
        return 1;
    }
    return 0;
}

// 待機する関数
$.extend({
    wait: function(duration){
        var dfd = $.Deferred();
        setTimeout(dfd.resolve, duration);
        return dfd;
    }
});

function initDisp(area) {
  if(!($(".js_lineMidoku").length)){
    $(area).scrollTop($(area).get(0).scrollHeight);
  } else {
    var aoH = document.getElementsByClassName("js_content_area")[0].offsetTop;
    $(area).scrollTop(aoH);
    var loH = document.getElementsByClassName("js_lineMidoku")[0].offsetTop - aoH;
    $(area).stop().animate({scrollTop: loH},'fast');
    // スクロールできない画面の場合、未読のメッセージを既読にする
    if ($("#js_chatMessageArea").get(0).scrollHeight - $("#js_chatMessageArea").get(0).clientHeight <= 0) {
      changeToKidoku(document.forms[1].cht010SelectKbn.value, document.forms[1].cht010SelectPartner.value);
    }
  }
}


// スクロールしない画面における既読処理
function changeToKidoku(selectKbn, selectSid) {
  var dspMesSid = 0;
  var dspMesCnt = 0;
  var readMsg = [];
    //未読→既読処理
    var windowH = $(".js_content_area").offset().top;
    var footerH = $(".js_sendMessageArea").offset().top;
    $('.js_mediaArea').each(function(idx) {
      // 未読メッセージに対する処理
      if($(this).find(".js_kidoku").text() == "1") {
        $(this).find(".js_kidoku").text("0");
        var tes = $(this).offset().top;
        // 画面内に表示されている場合
        if (tes > windowH && tes < footerH) {
          if (dspMesSid < $(this).find(".js_check").attr('id')) {
            dspMesSid = $(this).find(".js_check").attr('id');
            dspMesCnt = dspMesCnt + 1;
            readMsg.push(dspMesSid);
          }
          // 未読数書き換えのキャンセル
          if (kidoku != null) {
            clearTimeout(kidoku);
          }
          // 未読数書き換え
          kidoku = setTimeout(function(){
            paramStr = 'CMD=updateKidoku';
              paramStr = paramStr + '&cht010MessageSid=' + dspMesSid;
              paramStr = paramStr + '&cht010SelectPartner=' + selectSid;
              paramStr = paramStr + '&cht010SelectKbn= ' + selectKbn;
              paramStr = setToken(paramStr);
              $.ajax({
                    async: true,
                    url:  "../chat/cht010.do",
                    type: "post",
                    data: paramStr
              }).done(function( data ) {
                if (data["success"]) {
                // ユーザ間チャットの場合、相手の画面に「既読」を表示
                if (selectKbn == 1) {
                  sendKidoku(selectSid, readMsg);
                }
                  var cnt = 0;
                  if (selectKbn == 2) {
                    $(".js_chtGroup").each(function(i) {
                      if ($(this).attr('value') == selectSid) {
                        cnt = Number($(this).find('.js_midokuCount').text());
                        cnt = cnt - dspMesCnt;
                        if (cnt != 0) {
                              $(this).find('.js_midokuCount').text(cnt);
                        } else {
                          $(this).find('.js_midokuCount').text('');
                        }
                      }
                    });
                  } else {
                    $(".js_chtUser").each(function(i) {
                      if ($(this).attr('value') == selectSid) {
                        cnt = Number($(this).find('.js_midokuCount').text());
                        cnt = cnt - dspMesCnt;
                        if (cnt != 0) {
                            $(this).find('.js_midokuCount').text(cnt);
                        } else {
                            $(this).find('.js_midokuCount').text('');
                        }
                      }
                    });
                  }
                  updateTimeline();

                  // 未読タブ
                  var allMidoku = Number($(".js_allMidoku").text());
                  allMidoku -= dspMesCnt;
                  updateAllMidokuCnt(allMidoku);

                  dspMesCnt = 0;
                } else if (data["tokenError"]){
                  tokenError(data);
                } else {
                  alert(msglist_cht010['cht.cht010.23']);
                }
              }).fail(function(data){
                alert(msglist_cht010['cht.cht010.24']);
              });
          }, 2000);
        }
      }
    });
}

function changePartnerInit() {
  $("html,body").scrollTop( 0 );
  initDisp("#js_chatMessageArea");
  drag();
    ikkatuReset();
    dateHeader();
}

function chat_area_resize(initFlg) {
  var windowHeight = 0;
  var os, ua = navigator.userAgent;
  if (ua.match(/iPhone|iPad/)) {
    windowHeight = document.documentElement.clientWidth;
  } else {
    var windowHeight = window.innerHeight;
  }
    var chat_area_height = windowHeight - $("#js_chatHeader").height()
    - $(".js_chatHeader").height() - $("#hiduke_header").height() - $(".js_sendMessageArea").height()
    - $("footer").height() - 102; // GSメニューヘッダ分(90) + form margin分
    if (chat_area_height < chat_area_min_height) {
      $("#js_chatMessageArea").height(chat_area_min_height);
      $('#js_chatMessageArea').css('max-height', chat_area_min_height + 'px');
    } else {
      $("#js_chatMessageArea").height(chat_area_height);
      $('#js_chatMessageArea').css('max-height', chat_area_height + 'px');
    }
    if ($('#js_chatMessageArea').hasScrollBar()) {
      var margin_width = getScrollbarWidth();
      $("#hiduke_header").css({"margin-right":margin_width + 6 + "px"});
    } else {
      $("#hiduke_header").css({"margin-right":"6px"});
    }
    if (initFlg) {
      initDisp("#js_chatMessageArea");
    }
    chat_textarea_resize();
}

function group_combo_change() {
    paramStr = 'CMD=changeGrp';
    paramStr = paramStr + '&cht010GroupSid=' + $("#cht010ChangeGrp").val();
      $.ajax({
          async: true,
          url:  "../chat/cht010.do",
          type: "post",
          data: paramStr
      }).done(function( data ) {
          if (data["success"]) {
            var detail = "";
            if (data["size"] > 0) {
              for (var i = 0; i < data["size"]; i++) {

                detail += "<div class=\"m5\">";

                if (data["usiPictKf_"+i] != 0) {
                  detail += "  <div class=\"js_chtUser js_user_name display_b cursor_p display_inline w100 verAlignMid\" value=\""+data["usrSid_"+i]+"\">";
                  detail += "<div class=\"mr5\">";
                  detail += "<span class=\"hikokai_photo-s hikokai_text cl_fontWarn\">"+msglist_cht010['cmn.private.photo']+"</span>";
                } else {
                  detail += "  <div class=\"js_chtUser js_user_name display_b cursor_p display_inline w100 verAlignMid\" value=\""+data["usrSid_"+i]+"\">";
                  detail += "<div class=\"mr5\">";
                  if (data["binSid_"+i] == 0) {
                      detail += "    <img src=\"../common/images/classic/icon_photo.gif\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\""+msglist_cht010['cmn.photo']+" />\"  class=\"wp25 btn_classicImg-display\"/>";
                      detail += "    <img src=\"../common/images/original/photo.png\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\""+msglist_cht010['cmn.photo']+" />\"  class=\"wp25 btn_originalImg-display\"/>";
                  } else {
                    if (data["usrJkbn_"+i] == 9) {
                        detail += "    <img src=\"../common/images/classic/icon_photo.gif\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\""+msglist_cht010['cmn.photo']+" />\"  class=\"wp25 btn_classicImg-display\"/>";
                        detail += "    <img src=\"../common/images/original/photo.png\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\""+msglist_cht010['cmn.photo']+" />\"  class=\"wp25 btn_originalImg-display\"/>";
                    } else {
                 detail += "<img src=\"../common/cmn100.do?CMD=getImageFile&cmn100binSid="+data["binSid_"+i]+"\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\""+msglist_cht010['cmn.photo']+"\" class=\"wp25\"/>";
                    }
                  }
                }
                detail += "</div>";
                detail += "<div class=\"verAlignMid\">";
                if (data["usrJkbn_"+i] != 0) {
                  detail += "    <del class=\"cl_linkDef\">"+data["usiSei_"+i]+"&nbsp;"+data["usiMei_"+i];
                  detail += "</del>"
                  detail += "    <span class=\"midokuCount js_midokuCount cl_linkDef\">";
                  if (data["chtUserCount_"+i] != 0) {
                    detail += data["chtUserCount_"+i];
                  }
                  detail += "</span>";
                } else {
                  var ukoFlg = "cl_linkDef";
                  var hideFlg = "";
                  if (data["usrUkoFlg_"+i] != 0) {
                    ukoFlg = "mukoUser"
                  }
                  detail += "    <span class=\"" + ukoFlg + hideFlg + "\">"+data["usiSei_"+i]+"&nbsp;"+data["usiMei_"+i]+"</span>";
                  detail += "    <span class=\"midokuCount js_midokuCount " + ukoFlg + " \">";
                  if (data["chtUserCount_"+i] != 0) {
                    detail += data["chtUserCount_"+i];
                  }
                  detail += "</span>";
                }
                detail += "</div></div>"
                   + "</div>";
              }
            }
            $("#selGrpUsrArea").children().remove();
            $("#selGrpUsrArea").append(detail);
          } else {
            alert(msglist_cht010['cht.cht010.23']);
          }
      }).fail(function(data){
        alert(msglist_cht010['cht.cht010.24']);
      });
}

function chat_textarea_resize() {
  var textarea = document.getElementById("inText");
  if( textarea.scrollHeight > textarea.offsetHeight
      && textarea.offsetHeight < chat_textarea_max_height ){
    if (textarea.scrollHeight > chat_textarea_max_height) {
      textarea.style.height = chat_textarea_max_height + 'px';
    } else {
      textarea.style.height = textarea.scrollHeight+'px';
    }
    chat_area_resize(false);
  } else if ( textarea.scrollHeight < textarea.offsetHeight
      && textarea.offsetHeight > chat_textarea_min_height ){
    textarea.style.height = chat_textarea_min_height + 'px';
    textarea.style.height = textarea.scrollHeight+'px';
    chat_area_resize(false);
  }
}

$(function(){

  $(document).on("input", "#inText",function(){
    chat_textarea_resize();
  });

  $(window).on('resize', function(){
      chat_area_resize(false);
  });

  initDisp("#js_chatMessageArea");
  // 選択SID
  var selectSid = $(".js_selectSid").text();
  // 選択区分
  var selectKbn = $(".js_selectKbn").text();
  // 送信元SID
  var senderSid = $("#js_senderSid").text();
  // お気に入りフラグ
  var cntCheck  =  $(".js_favorite_flg").text();
  // 編集時用メッセージテキスト要素
  var editParent = "";
  // 表示したメッセージのSID
  var mesSid = 0;
  // 表示した未読メッセージ数
  var mesCnt = 0;

  allDispFlg = document.forms[1].cht010AllDispFlg.value;

  drag();

  //アーカイブ表示
  $(document).on("change", ".js_archive",function(){
    var check = $("[name=archive]:checked").val();
    var parent = $(".js_archiveGroup").parent();
    if (check == 1) {
      parent.show();
    } else {
      parent.hide();
    }
  });

  //メッセージ削除
  $(document).on("click", ".js_message_delete",function(){
    messageSid = $(this).attr("value");
    var h = window.innerHeight / 2;
      $('#delKakuninChtPop').dialog({
           position: {
             of : 'body',
             at: 'top+' + h,
             my: 'center'
            },
            autoOpen: true,  // hide dialog
            bgiframe: true,   // for IE6
            dialogClass:'dialog_button',
            resizable: false,
            height:160,
            width: 400,
            modal: true,
            overlay: {
              backgroundColor: '#000000',
              opacity: 0.5
            },
            buttons: {
              はい: function() {
                $(this).dialog('close');
                confirmDeleteMessage(selectSid, selectKbn, messageSid);
                if (mode == 1) {
                    $('.js_chtTextArea').val("");
                    var textarea = document.getElementById("inText");
                    textarea.style.height = chat_textarea_min_height + 'px';
                    chat_area_resize(false);
                    mode = 0;
                  }
              },
              いいえ: function() {
                $(this).dialog('close');
              }
            }
      });
  });

  //メッセージ編集
  $(document).on("click", ".js_message_edit",function(){
    messageSid = $(this).attr("value");
    var messageText = $(this).parent().parent().parent().find(".js_message").html();
    message_edit(messageText);
  });

  //メッセージ確定押下
  $(document).on("click", ".js_chtConfirm",function(){
    var msgContent = $(".js_chtTextArea").val();
    if (msgContent.length > 3000) {
      $("#js_errorMsg").text(msglist_cht010['cht.cht010.34']);
    } else {
      $("#js_errorMsg").text("");
      confirmEditChatMessage(msgContent, selectSid, selectKbn, messageSid);
    }
  });

  //メッセージキャンセル押下
  $(document).on("click", ".js_chtCansel",function(){
    $(".js_mediaArea").removeClass("media_selected");
    $(".js_media_mine").addClass("media_mine");
    $(".js_chtTextArea").removeClass("media_selectedColor");
    $(".js_chtTextArea").addClass("chattextArea_color");
    $(".js_chtConfirm").addClass("display_n");
    $(".js_chtCansel").addClass("display_n");
    $(".js_chtSend").removeClass("display_n");
    $(".js_chtAttach").removeClass("display_n");
    $('.js_chtTextArea').val("");
    var textarea = document.getElementById("inText");
    textarea.style.height = chat_textarea_min_height + 'px';
    chat_area_resize(false);
    if ($("#js_errorMsg").text() == errorMsg) {
      $("#js_errorMsg").text("");
    }
    mode = 0;
  });

  //もっとみるボタン
  $(document).on("click", ".js_moreView ",function(){
      loadTimeline(false);
  });

  //未読のみチェック
  $(document).on("change", ".js_checkOnlyNoRead", function() {
      loadTimeline(true);
  });


  //お気に入り
  $(document).on("click", ".js_chtStar",function(){
  if (cntCheck==1) {
        cntCheck = 0;
    } else {
        cntCheck = 1;
    }
    paramStr = 'CMD=favoriteChage';
    paramStr = paramStr + '&cht010FavoriteFlg=' + cntCheck;
    paramStr = paramStr + '&cht010SelectPartner=' + selectSid;
    paramStr = paramStr + '&cht010SelectKbn=' + selectKbn;
    paramStr = setToken(paramStr);
    $.ajax({
          async: true,
          url:  "../chat/cht010.do",
          type: "post",
          data: paramStr
      }).done(function( data ) {
          if (data["success"]) {
            var detail = "";
            if (selectKbn == 1) {
              if (data["size"] != 0) {
                detail += "<div class=\"fw_b lh130 mt5 ml5\">"+msglist_cht010['cmn.user']+"</div>";
              }
              for (var idx = 0; idx < data["size"]; idx++) {
                detail += "<div class=\"pl20 w100 mt5 lh130\">";
                if (data["favUsrUko"+idx] == 0) {
                  detail += "<a href=\"#\" class=\"js_chtUser cl_linkDef display_b\" value=\""+ data["favUsrSid"+idx] + "\">";
                } else {
                  detail += "<a href=\"#\" class=\"js_chtUser cl_linkDef display_b mukoUser\" value=\""+ data["favUsrSid"+idx] + "\">";
                }
                if (data["favUsrJkbn"+idx] != 0) {
                  detail += "<del>"
                       + data["favUsrName"+idx]
                         +  "</del>" ;
                } else {
                  detail += data["favUsrName"+idx];
                }
                detail += " <span class=\"midokuCount js_midokuCount\">";
                if (data["favUsrCnt"+idx] != 0) {
                    detail += data["favUsrCnt"+idx];
                }
                detail += "</span>";
                detail += "</a>"
                     + "</div>";
              }
              $(".js_favUser").children().remove();
              $(".js_favUser").append(detail);
            } else if (selectKbn == 2) {
              if (data["size"] != 0) {
                detail += "<div class=\"fw_b lh130 mt5 ml5\">"+msglist_cht010['cmn.group']+"</div>";
              }
              for (var idx = 0; idx < data["size"]; idx++) {
                detail += "<div class=\"pl20 w100 mt5 lh130\">"
                     +  "  <a href=\"#\" class=\"js_chtGroup cl_linkDef display_b\" value=\""+ data["favGrpSid"+idx] +"\">"
                     + data["favGrpName"+idx]
                detail += " <span class=\"midokuCount js_midokuCount\">";
                if (data["favGrpCnt"+idx] != 0) {
                  detail += data["favGrpCnt"+idx];
                }
                detail += "</span>";
                detail += "</a>"
                     + "</div>"
              }
              $(".js_favGroup").children().remove();
                $(".js_favGroup").append(detail);
            }
            if (cntCheck==0) {
                document.getElementById("js_starImg").src="../chat/images/classic/icon_star_mi.png";
                $(".js_starImgI").removeClass("icon-star");
                $(".js_starImgI").addClass("icon-star_line");
            } else {
                document.getElementById("js_starImg").src="../chat/images/classic/icon_star.png";
                $(".js_starImgI").removeClass("icon-star_line");
                $(".js_starImgI").addClass("icon-star");
            }
          } else if (data["tokenError"]){
            tokenError(data);
          } else {
            sendErrorAlert(data);
          }
      }).fail(function(data){
        alert(msglist_cht010['cht.cht010.26']);
      });

  });

  //グループ切り替え時
  $(document).on("click", ".js_chtGroup",function(){
    // 未読数書き換えのキャンセル
    if (kidoku != null) {
      clearTimeout(kidoku);
    }
    selectSid = $(this).attr("value");
    selectKbn = 2;
    document.forms[1].cht010SelectPartner.value = selectSid;
    document.forms[1].cht010SelectKbn.value = selectKbn;
    paramStr = 'CMD=changePartner';
    paramStr = paramStr + '&cht010SelectPartner=' + selectSid;
    paramStr = paramStr + '&cht010SelectKbn=' + selectKbn;

      $.ajax({
          async: true,
          url:  "../chat/cht010.do",
          type: "post",
          data: paramStr
      }).done(function( data ) {
          if (data["success"]) {
               document.forms[1].cht010FirstEntryDay.value = data["firstDate"];
              $("#js_chatMessageArea").children().remove();
              var detail = messageAreaCreate(data);
              $("#js_chatMessageArea").append(detail);
              $(".js_chatSpaceArea").remove()
              $("#js_chatMessageArea").append("<div class=\"js_chatSpaceArea\">&nbsp;</div>");
              if (dlCheck == 1) {
                  ikkatuReset();
              }
              if ($('#js_chatMessageArea').hasScrollBar()) {
                  var margin_width = getScrollbarWidth();
                  $("#hiduke_header").css({"margin-right":margin_width + 5 + "px"});
              } else {
                  $("#hiduke_header").css({"margin-right":"5px"});
              }
              messageSendArea(data);
              cntCheck = data["favoriteFlg"];
              if (cntCheck==1) {
                  document.getElementById("js_starImg").src="../chat/images/classic/icon_star.png";
                  $(".js_starImgI").removeClass("icon-star_line");
                  $(".js_starImgI").addClass("icon-star");
              } else {
                  document.getElementById("js_starImg").src="../chat/images/classic/icon_star_mi.png";
                  $(".js_starImgI").removeClass("icon-star");
                  $(".js_starImgI").addClass("icon-star_line");
              }
              $(".js_chatName").empty();
              $(".js_chatName").append(data["chatName"]);

              $(".js_chtGroupTitle").empty();
              $(".js_chtGroupTitle").append(data["chatName"]);

              $(".js_chtGroupId").empty();
              $(".js_chtGroupId").append(data["chatId"]);

              $(".js_bikoArea").empty();
              var biko = "";
              if (data["chatBiko"].length != 0) {
                biko += "<div class=\"fw_b mt15 fs_14\">"
                      + msglist_cht010["cmn.memo"]
                      + "</div>"
                      + "<div class=\"js_chtGroupBiko ml10 word_b-all\">"
                      + data["chatBiko"]
                      + "</div>";
              }
              $(".js_bikoArea").append(biko);

              $(".js_chtAdminMemList").remove();
              var adminMember = "";
              adminMember += "<div class=\"js_chtAdminMemList\">"
                           + "<div>";
              for (var idx = 0; idx < data["adminGroupSize"]; idx++) {
                adminMember += "<span class=\"ml10 display_inline-block\">"
                             + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_group.png\" alt=\"" + msglist_cht010["cmn.group"] + "\">"
                             + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_group.png\" alt=\"" + msglist_cht010["cmn.group"] + "\">"
                             + data["adminGroup_"+idx]
                             + "</span>";
              }
              adminMember += "</div>"
                           + "<div class=\"mt5\">";
              for (var idx = 0; idx < data["adminMemberSize"]; idx++) {
                adminMember += "<span class=\"ml10 display_inline-block\">"
                             + "<img class=\"btn_classicImg-display btnIcon-size\" src=\"../common/images/classic/icon_user.png\" alt=\"" + msglist_cht010["cmn.user"] + "\">"
                             + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_user.png\" alt=\"" + msglist_cht010["cmn.user"] + "\">"
                             + data["adminMemberName_"+idx]
                             + "</span>";
              }
              adminMember += "</div>"
                           + "</div>";
              $(".js_chtAdminList").append(adminMember);

              $(".js_chtGeneralMemList").remove();
              var generalMember = "";
              generalMember += "<div class=\"js_chtGeneralMemList\">"
                             + "<div>";
              if (data["generalGroupSize"] == 0 && data["generalMemberSize"] == 0) {
                generalMember += "<span class=\"ml10\">" + msglist_cht010["cmn.no"] + "</span>";
              }
              for (var idx = 0; idx < data["generalGroupSize"]; idx++) {
                generalMember += "<span class=\"ml10 display_inline-block\">"
                               + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_group.png\" alt=\"" + msglist_cht010["cmn.group"] + "\">"
                               + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_group.png\" alt=\"" + msglist_cht010["cmn.group"] + "\">"
                               +  data["generalGroup_"+idx]
                               + "</span>";
              }
              generalMember += "</div>"
                             + "<div class=\"mt5\">";
              for (var idx = 0; idx < data["generalMemberSize"]; idx++) {
                generalMember += "<span class=\"ml10 display_inline-block\">"
                               + "<img class=\"btn_classicImg-display btnIcon-size\" src=\"../common/images/classic/icon_user.png\" alt=\"" + msglist_cht010["cmn.user"] + "\">"
                               + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_user.png\" alt=\"" + msglist_cht010["cmn.user"] + "\">"
                               +  data["generalMemberName_"+idx]
                               + "</span>";
              }
              generalMember += "</div>"
                             + "</div>";
              $(".js_chtGeneralList").append(generalMember);

              changePartnerInit();
              mesSid = 0;
              mesCnt = 0;
              scrollAutoReadFlg = 0;
              allDispFlg = data["allDispFlg"];
              readMsg = [];
          } else if(data["error"]){
            alert(msglist_cht010['cht.cht010.33']);
          } else {
            alert(msglist_cht010['cht.cht010.25']);
          }
      }).fail(function(data){
        alert(msglist_cht010['cht.cht010.26']);
      });
  });

  //ポップアップによりグループ切り替え時
  $(document).on("click", "#fakeSearchGrpButton",function(){
    group_combo_change();
  });


  //グループコンボ切り替え
  $(document).on("change", "#cht010ChangeGrp",function(){
    group_combo_change();
  });

  //ユーザ切り替え時
  $(document).on("click", ".js_chtUser",function(){
    // 未読数書き換えのキャンセル
    if (kidoku != null) {
      clearTimeout(kidoku);
    }
    selectSid = $(this).attr("value");
    selectKbn = 1;
    document.forms[1].cht010SelectPartner.value = selectSid;
    document.forms[1].cht010SelectKbn.value = selectKbn;
    paramStr = 'CMD=changePartner';
    paramStr = paramStr + '&cht010SelectPartner=' + selectSid;
    paramStr = paramStr + '&cht010SelectKbn= ' + selectKbn;

      $.ajax({
          async: true,
          url:  "../chat/cht010.do",
          type: "post",
          data: paramStr
      }).done(function( data ) {
          if (data["success"]) {
               document.forms[1].cht010FirstEntryDay.value = data["firstDate"];
              $("#js_chatMessageArea").children().remove();
              var detail = messageAreaCreate(data);
              $("#js_chatMessageArea").append(detail);
              $(".js_chatSpaceArea").remove()
              $("#js_chatMessageArea").append("<div class=\"js_chatSpaceArea\">&nbsp;</div>");
              if (dlCheck == 1) {
                  ikkatuReset();
              }
              if ($('#js_chatMessageArea').hasScrollBar()) {
                  var margin_width = getScrollbarWidth();
                  $("#hiduke_header").css({"margin-right":margin_width + 5 + "px"});
                } else {
                    $("#hiduke_header").css({"margin-right":"5px"});
                }
              messageSendArea(data);
              cntCheck = data["favoriteFlg"];
              if (cntCheck==1) {
                  document.getElementById("js_starImg").src="../chat/images/classic/icon_star.png";
                  $(".js_starImgI").removeClass("icon-star_line");
                  $(".js_starImgI").addClass("icon-star");
              } else {
                  document.getElementById("js_starImg").src="../chat/images/classic/icon_star_mi.png";
                  $(".js_starImgI").removeClass("icon-star");
                  $(".js_starImgI").addClass("icon-star_line");
              }
              $(".js_chatName").empty();
              $(".js_chatName").append(data["chatName"]);
              changePartnerInit();
              mesSid = 0;
              mesCnt = 0;
              scrollAutoReadFlg = 0;
              allDispFlg = data["allDispFlg"];
              readMsg = [];
          } else {
            alert(msglist_cht010['cht.cht010.27']);
          }
      }).fail(function(data){
        alert(msglist_cht010['cht.cht010.27']);
      });
  });

  //Enter送信切り替え
  $(document).on("click", ".js_enterSend",function(){
    var val = $(this).prop("checked");
    var flg = 0;
    if (val) {
      flg = 1;
    }
    paramStr = 'CMD=enterSend';
    paramStr = paramStr + '&cht010EnterSendFlg=' + flg;
    paramStr = setToken(paramStr);
      $.ajax({
          async: true,
          url:  "../chat/cht010.do",
          type: "post",
          data: paramStr
      }).done(function( data ) {
          if (data["success"]) {
            if (val) {
              $(".js_chtTextArea").attr("placeholder","Shift+Enterで改行");
            }  else {
              $(".js_chtTextArea").attr("placeholder","Shift+Enterで送信");
            }
          } else if (data["tokenError"]){
            tokenError(data);
          } else {
            alert(msglist_cht010['cht.cht010.23']);
          }
      }).fail(function(data){
        alert(msglist_cht010['cht.cht010.24']);
      });
  });

  //メッセージエリア キー押下
  $(document).on("keydown", ".js_chtTextArea", function(e){
   if(e.shiftKey) {
      if (e.which == 13) {
        if (!$(".js_enterSend").prop("checked")) {
          var message = $('.js_chtTextArea').val();
          if (message.replace(/\s+/g,'').length != 0) {
            if (mode == 0) {
              sendMessage(selectSid, selectKbn);
              return false;
            } else {
              $(".js_mediaArea").removeClass("media_selected");
              $(".js_media_mine").addClass("media_mine");
              $(".js_chtTextArea").removeClass("media_selectedColor");
              $(".js_chtTextArea").addClass("chattextArea_color");
              var msgContent = $('.js_chtTextArea').val();
              confirmEditChatMessage(msgContent, selectSid, selectKbn, messageSid);
              mode = 0;
              return false;
            }
          }
        }
      } else if (e.which == 38 && mode == 0) {
        if ($("#inText").val().length == 0) {
          var message = $("#js_chatMessageArea").find('.js_media_mine').find(".js_message").not(".js_temp");
          if (message.length > 0) {
            message = $("#js_chatMessageArea").find('.js_media_mine').find(".js_message");
            var messageText = "";
            for (var i = (message.length - 1); i > -1; i--) {
              if (!$("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").find(".js_message").hasClass("js_temp")) {
                messageSid = $("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").find(".js_message_edit").attr("value");
                messageText = $("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").find(".js_message").html();
                $("#js_chatMessageArea").scrollTop(($("#js_chatMessageArea").scrollTop() + $("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").offset().top) - 140);
                break;
              }
            }
            message_edit(messageText);
          }
        }
      }
    } else {
      if (e.which == 13) {
        if ($(".js_enterSend").prop("checked")) {
          var message = $('.js_chtTextArea').val();
          if (message.replace(/\s+/g,'').length != 0) {
            if (mode == 0) {
              sendMessage(selectSid, selectKbn);
              return false;
            } else {
              $(".js_mediaArea").removeClass("media_selected");
              $(".js_media_mine").addClass("media_mine");
              $(".js_chtTextArea").removeClass("media_selectedColor");
              $(".js_chtTextArea").addClass("chattextArea_color");
              var msgContent = $('.js_chtTextArea').val();
              confirmEditChatMessage(msgContent, selectSid, selectKbn, messageSid);
              mode = 0;
              return false;
            }
          }
        }
      } else if (e.which == 38 && mode == 0) {
        if ($("#inText").val().length == 0) {
          var message = $("#js_chatMessageArea").find('.js_media_mine').find(".js_message").not(".js_temp");
          if (message.length > 0) {
            message = $("#js_chatMessageArea").find('.js_media_mine').find(".js_message");
            var messageText = "";
            for (var i = (message.length - 1); i > -1; i--) {
              if (!$("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").find(".js_message").hasClass("js_temp")) {
                messageSid = $("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").find(".js_message_edit").attr("value");
                messageText = $("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").find(".js_message").html();
                $("#js_chatMessageArea").scrollTop(($("#js_chatMessageArea").scrollTop() + $("#js_chatMessageArea").find(".js_media_mine:eq("+i+")").offset().top) - 140);
                break;
              }
            }
            message_edit(messageText);
          }
        }
      }
    }
  });

  //送信押下時
  $(document).on("click", ".js_chtSend", function(){
    var message = $('.js_chtTextArea').val();
    sendMessage(selectSid, selectKbn);
    $("#attachmentFileErrorArea").html("");
  });

  //チェックボックス押下
  $(document).on("click", ".js_check", function(e){
    var value =$(this).val();
    if ($(this).prop('checked')) {
      var meisyou = $(this).parent().parent().find(".js_temp").text();
      if (meisyou != "") {
        var detail = "";
            detail += "<tr class=\"js_message_sid_"+value+"\">"
                  + "  <td class=\"chat_width_90 ikkatu_font\">" + meisyou + "</td>"
                  + "  <td><img src=\"../chat/images/classic/icon_batu.png\" class=\"cursor_p btn_classicImg-display js_ikkatu_batu wp15\"/>"
                  + "  <img src=\"../common/images/original/icon_delete.png\" class=\"cursor_p btn_originalImg-display js_ikkatu_batu\"/></td>"
                  + "</tr>";
            $(".js_fileNameSpace").append(detail);
      }
    } else {
      $(".js_message_sid_"+value).remove();
    }
    e.stopPropagation();
  });

  //一括×ボタン押下
  $(document).on("click", ".js_ikkatu_batu", function(){
    var className = $(this).parent().parent().attr("class");
    $(this).parent().parent().remove();
    var res = className.split("_");
    $("#"+res[3]).prop("checked",false);
    $(".js_media_"+res[3]).removeClass("media_selected");
  });

  //添付ダウンロード
  $(document).on("click", ".js_tempDonwload", function(){
    document.forms[1].CMD.value="fileDownload";
    tempSid = $(this).attr("value");
    document.forms[1].cht010MessageSid.value=tempSid;
    document.forms[1].cht010SelectPartner.value=selectSid;
    document.forms[1].cht010SelectKbn.value=selectKbn;
    document.forms[1].submit();
  });

  //ダウンロードボタン押下
  $(document).on("click", ".js_ikkatuDlBbutton", function(){
    var check = $(".js_check:checked");
    var setSid = "";
    for (var i=0; i< check.length; i++) {
      if (i != 0) {
        setSid += ","
      }
      setSid += check[i].value;
    }
    if ($(".js_ikkatu_batu").length && setSid != "") {
        $(".js_mediaArea").removeClass("media_selected");
      document.forms[1].CMD.value="allTmpExp";
        document.forms[1].cht010SelectPartner.value=selectSid;
        document.forms[1].cht010SelectKbn.value=selectKbn;
      document.forms[1].cht010AllTempSid.value=setSid
        document.forms[1].submit();
        ikkatuReset()
        $(".js_check").prop("checked",false);
    } else {

    }
  });

  $(document).on("click", ".js_mediaArea", function(){
    if (dlCheck == 1) {
      var value =$(this).find(".js_check").val();
      if ($(this).find(".js_check").prop('checked')) {
        $(".js_message_sid_"+value).remove();
        $("#"+value).prop("checked",false);
      } else {
        var meisyou = $(this).find(".js_temp").text();
        if (meisyou != "") {
          var detail = "";
              detail += "<tr class=\"js_message_sid_"+value+"\">"
                    + "  <td class=\"w90\">" + meisyou + "</td>"
                    + "  <td class=\"w10\">"
                    + "  <img src=\"../chat/images/classic/icon_batu.png\" class=\"cursor_p btn_classicImg-display js_ikkatu_batu wp15\"/>"
                    + "  <img src=\"../common/images/original/icon_delete.png\" class=\"cursor_p btn_originalImg-display js_ikkatu_batu\"/></td>"
                    + "</tr>";
              $(".js_fileNameSpace").append(detail);
        }
        if (value != "") {
          $("#"+value).prop("checked",true);
        }

      }
    }
  });

  //メッセージエリアスクロール処理
  var readMsg = [];
  $("#js_chatMessageArea").on("scroll", function() {
  if (wordOverFlg) {
      return;
  }
  //未読→既読処理
  var windowH = $(".js_content_area").offset().top;
  var footerH = $(".js_sendMessageArea").offset().top;
  $('.js_mediaArea').each(function(idx) {
    // 未読メッセージに対する処理
    if($(this).find(".js_kidoku").text() == "1") {
      var tes = $(this).offset().top;
      // 画面内に表示されている場合
      if (tes > windowH && tes < footerH) {
        if (mesSid < $(this).find(".js_check").attr('id')) {
          mesSid = $(this).find(".js_check").attr('id');
          readMsg.push(mesSid);
        }
        // 未読数書き換えのキャンセル
        if (kidoku != null) {
          clearTimeout(kidoku);
        }
        // 未読数書き換え
        kidoku = setTimeout(function() {
          paramStr = 'CMD=updateKidoku';
          paramStr = paramStr + '&cht010MessageSid=' + mesSid;
          paramStr = paramStr + '&cht010SelectPartner=' + selectSid;
          paramStr = paramStr + '&cht010SelectKbn= ' + selectKbn;
          paramStr = setToken(paramStr);
            $.ajax({
                  async: true,
                  url:  "../chat/cht010.do",
                  type: "post",
                  data: paramStr
            }).done(function( data ) {
              if (data["success"]) {
                // 個人間チャットの場合、相手の画面に「既読」表示
                if (selectKbn == 1) {
                  sendKidoku(selectSid, readMsg);
                  readMsg = [];
                }
                var cnt = data["count"];
                var minusCnt = 0;
                if (selectKbn == 2) {
                  $(".js_chtGroup").each(function(i) {
                    if ($(this).attr('value') == selectSid) {
                      minusCnt = $(this).find('.js_midokuCount').text();
                      if (cnt != 0) {
                            $(this).find('.js_midokuCount').text(cnt);
                      } else {
                          $(this).find('.js_midokuCount').text('');
                      }
                    }
                  });
                } else {
                  $(".js_chtUser").each(function(i) {
                    if ($(this).attr('value') == selectSid) {
                      minusCnt = $(this).find('.js_midokuCount').text();
                      if (cnt != 0) {
                        $(this).find('.js_midokuCount').text(cnt);
                      } else {
                            $(this).find('.js_midokuCount').text('');
                      }
                    }
                  });
                }
                updateTimeline();

                $(this).find(".js_kidoku").text("0");
                // 未読タブ
                var allMidoku = Number($(".js_allMidoku").text());

                allMidoku = allMidoku - (minusCnt - cnt);
                updateAllMidokuCnt(allMidoku);

                mesCnt = 0;
              } else if (data["tokenError"]){
                tokenError(data);
              } else {
                sendErrorAlert(data)
              }
            }).fail(function(data){
              alert(msglist_cht010['cht.cht010.24']);
            });
        }, 2000);
      }
    }
  });

    //自動読み込み処理
  　if (scrollAutoReadFlg == 0) {
    var nowTop = $(this).scrollTop();
    var sH = $(this).get(0).scrollHeight;
    var oH = $(this).get(0).offsetHeight;
      if (sH != oH) {
        var scrollHeight = sH - oH;
        var readMode = 0;
        var messageSid = 0;
        var addBackH;
        if (nowTop == 0) {
          readMode = 1;
          $('.js_check').each(function(idx) {
            var sid = Number($(this).attr('id'));
            if (idx == 0) {
                messageSid = sid;
                addBackH = $(this).parent().parent();
            } else if (messageSid > sid) {
                messageSid = sid;
                addBackH = $(this).parent().parent();
            }
          });
        } else if (nowTop == scrollHeight) {
          readMode = 2;
          $(".js_check").each(function(idx) {
            var sid = Number($(this).attr('id'));
            if (idx == 0) {
              messageSid = sid;
            } else if (messageSid < sid) {
              messageSid = sid;
            }
          });
        }

        if (readMode != 0) {
          paramStr = 'CMD=scrollRead';
          paramStr = paramStr + '&cht010MessageMaxMinSid=' + messageSid;
          paramStr = paramStr + '&cht010ReadFlg=' + readMode;
          paramStr = paramStr + '&cht010SelectPartner=' + selectSid;
          paramStr = paramStr + '&cht010SelectKbn= ' + selectKbn;
            $.ajax({
                async: true,
                url:  "../chat/cht010.do",
                type: "post",
                data: paramStr
            }).done(function( data ) {
                if (data["success"]) {
                  if (data["size"] > 0) {
                    if (readMode == 1) {
                      var size = data["size"] - 1;
                          var date = escapeSelectorString(data["entryDay_"+size]);
                          if($('#'+date+'').length){
                            var parent = $('#'+date+'').parent();
                            $('#'+date+'').remove();
                            parent.append("<div class=\"chat_boder\"></div>");
                          }
                    }
                    var detail = messageAreaCreate(data);
                    if (readMode == 1) {
                      var curOffset = addBackH.offset().top - $(document).scrollTop();
                      detail += "<div class=\"chat_lrSpace mt5 mb5\"><div class=\"bor_t1 w100\"></div></div>";
                      $("#js_chatMessageArea").prepend(detail);
                      $("#js_chatMessageArea").scrollTop(addBackH.offset().top - curOffset );
                      $(".js_chatSpaceArea").remove()
                      $("#js_chatMessageArea").append("<div class=\"js_chatSpaceArea\">&nbsp;</div>");
                      //編集中
                      if (mode == 1) {
                        $(".js_media_mine").removeClass("media_mine");
                      }
                      //一括出力中
                      if (dlCheck == 1) {
                        $(".js_media_mine").removeClass("media_mine");
                        $(".js_mediaArea").addClass("js_media_select");
                        $(".js_mediaArea").addClass("cursor_p");
                        $(".js_chtCheckbox").removeClass("display_n");
                      }
                    } else if (readMode == 2) {
                      $("#js_chatMessageArea").append(detail);
                      $(".js_chatSpaceArea").remove()
                      $("#js_chatMessageArea").append("<div class=\"js_chatSpaceArea\">&nbsp;</div>");
                      allDispFlg = data["allDispFlg"];
                      if (dlCheck == 1) {
                          ikkatuReset();
                      }
                    }
                    if ($('#js_chatMessageArea').hasScrollBar()) {
                        var margin_width = getScrollbarWidth();
                        $("#hiduke_header").css({"margin-right":margin_width + 5 + "px"});
                      } else {
                          $("#hiduke_header").css({"margin-right":"5px"});
                      }
                  }
                } else {
                  alert(msglist_cht010['cht.cht010.28']);
                }
            }).fail(function(data){
              alert(msglist_cht010['cht.cht010.24']);
            });
        }
      }
  }

      var hidukefixTop = $("#hiduke_header").offset().top;
      var hidukeTop;
      var downValue = -1;
      var dateValue;
      //スクロール日付保持処理
      if($('.js_hiduke').length){
        $("#hiduke_header").show();
      }

      $(".js_hiduke").each(function(i) {
        hidukeTop = $(this).offset().top;
        if (downValue == -1) {
          $("#hiduke_header").text($(this).attr('value'));
          downValue = hidukeTop;
          var firstDate = document.forms[1].cht010FirstEntryDay.value;
          if ($(this).attr('value') == firstDate) {
            var date = escapeSelectorString($(this).attr('value'));
            $("#"+date).hide()
          }
        } else {
          if (hidukeTop <= hidukefixTop) {
              if (downValue < hidukeTop) {
                  $("#hiduke_header").text($(this).attr('value'));
                  downValue = hidukeTop;
                  var firstDate = document.forms[1].cht010FirstEntryDay.value;
                  if ($(this).attr('value') == firstDate) {
                      var date = escapeSelectorString($(this).attr('value'));
                      $("#"+date).hide()
                  }
              }
          }
        }
        if (resize_first) {
          resize_first = false;
          chat_area_resize(true);
        }
      });
  });
  if ($('#js_chatMessageArea').hasScrollBar()) {
      var margin_width = getScrollbarWidth();
      $("#hiduke_header").css({"margin-right":margin_width + 6 + "px"});
  } else {
      $("#hiduke_header").css({"margin-right":"6px"});
  }
  dateHeader();
  chat_area_resize(true);
});

//メッセージ編集
function message_edit(messageText) {
    var messageList = messageText.split(/(<br>)/);
    var message = messageText.replace(/(<br>)/g, '\n');
    message = $('<a>' + message + '</a>').text();


  $("#js_errorMsg").text("");
  $("#cmn110fileDataArea").children().remove();
  mode = 1;
  $(".js_media_mine").removeClass("media_mine");
  $(".js_media_" + messageSid).addClass("media_selected");
  $(".js_chtTextArea").removeClass("chattextArea_color");
  $(".js_chtTextArea").addClass("media_selectedColor");
  $(".js_chtTextArea").val(message);
  $(".js_chtConfirm").removeClass("display_n");
  $(".js_chtCansel").removeClass("display_n");
  $(".js_chtSend").addClass("display_n");
  $(".js_chtAttach").addClass("display_n");
  chat_textarea_resize();
}

function dateHeader() {
  var sH = $("#js_chatMessageArea").get(0).scrollHeight;
  var oH = $("#js_chatMessageArea").get(0).offsetHeight;
    if (sH == oH) {
      if(!$('.js_hiduke').length){
        $("#hiduke_header").hide();
      } else {
         $("#hiduke_header").show();
      }

      var firstDate = document.forms[1].cht010FirstEntryDay.value;
      var date = escapeSelectorString(firstDate);
      $("#"+date).hide();
      $("#hiduke_header").text(firstDate);
    }
}

function dragEnterCht(e) {
  if (mode == 0) {
    $(".js_chtTextArea").removeClass("chattextArea_color");
      $(".js_chtTextArea").addClass("media_selectedColor");
      e.stopPropagation();
      e.preventDefault();
  }
}

function dragLeaveCht(e) {
  if (mode == 0) {
    $(".js_chtTextArea").removeClass("media_selectedColor");
      $(".js_chtTextArea").addClass("chattextArea_color");
      e.stopPropagation();
      e.preventDefault();
  }
}


function drag() {
  dropbox = document.getElementById("inText");
  dropbox.addEventListener("dragenter", dragEnterCht, false);
  dropbox.addEventListener("dragleave", dragLeaveCht, false);
  dropbox.addEventListener("dragover", dragover, false);
  dropbox.addEventListener("drop", dropCht, false);
}

function dropCht(e) {
  e.stopPropagation();
  e.preventDefault();
  if ($("#inText").attr('readonly') != 'readonly') {
    if (mode == 0) {
        $("#js_errorMsg").text("");
        var files = e.dataTransfer.files;
        uploadFiles(files);
        $(".js_chtTextArea").removeClass("media_selectedColor");
        $(".js_chtTextArea").addClass("chattextArea_color");
      } else {
        $("#js_errorMsg").text(errorMsg);
      }
  }
}
/*
function checkStatus() {
  var text = document.getElementById('cmn110fileDataArea').innerHTML;
  document.getElementById('cmn110fileDataArea').innerHTML = text.replace("undefined","");
  var decision = document.forms[0].cmn110Decision.value;
    if (decision == 1) {
        var tempCloseFlg = false;
        if (document.forms[1].cmn110tempName) {
          var tempNameList = $("*[name=cmn110tempName]");
          var tempSaveNameList = $("*[name=cmn110tempSaveName]");
          for (tempIdx = 0; tempIdx < tempNameList.length; tempIdx++) {
            var tempName = tempNameList[tempIdx].value;
            var tempSaveName = tempSaveNameList[tempIdx].value;
            if (document.forms[0].cmn110pluginId.value.indexOf('chatDrag') >= 0) {
                var sid = document.forms[1].cht010SelectPartner.value;
                var kbn = document.forms[1].cht010SelectKbn.value;
                var sender = document.forms[1].cht010EditUsrSid.value;
                paramStr = 'CMD=tempAddDrag';
                paramStr = paramStr + '&cht010SelectPartner=' + sid;
                paramStr = paramStr + '&cht010SelectKbn= ' + kbn;
                var deferred = new $.Deferred();
                  $.ajax({
                      async: false,
                      url:  "../chat/cht010.do",
                      type: "post",
                      data: paramStr
                  }).done(function( data ) {
                    if (data["success"]) {
                      // リアルタイム通信
                            data["msgContent"] = document.forms[1].cmn110tempName.value;
                            data["selectSid"] = sid;
                            data["selectKbn"] = kbn;
                            data["senderSid"] = sender;
                    } else {
                      sendErrorAlert(data);
                    }

                  }).fail(function(data){
                    alert(msglist_cht010['cht.cht010.24']);
                  });
              tempCloseFlg = true;
            } else if (document.forms[0].cmn110pluginId.value.indexOf('chat') >= 0) {
              var sid = window.opener.document.cht010Form.cht010SelectPartner.value;
                var kbn = window.opener.document.cht010Form.cht010SelectKbn.value;
                var sender = window.opener.document.cht010Form.cht010EditUsrSid.value;
              paramStr = 'CMD=tempAdd';
              paramStr = paramStr + '&cht010SelectPartner=' + sid;
                paramStr = paramStr + '&cht010SelectKbn= ' + kbn;
                var deferred = new $.Deferred();
                  $.ajax({
                      async: false,
                      url:  "../chat/cht010.do",
                      type: "post",
                      data: paramStr
                  }).done(function( data ) {

                  }).fail(function(data){
                    alert(msglist_cht010['cht.cht010.24']);
                  });
              tempCloseFlg = true;
            }
          }
        }
        if (cmn110CloseFlg) {
            tempCloseFlg = true;
        }
        if (tempCloseFlg) {
            tempClose();
        }
    }
}
*/


// 閉じるボタン
function selectFileClose() {
  $(".js_media_mine").addClass("media_mine");
  $(".js_mediaArea").removeClass("js_media_select");
  $(".js_mediaArea").removeClass("cursor_p");
  $(".js_mediaArea").removeClass("media_selected");
  dlCheck = 0;
  document.getElementById("checkImg").src="../chat/images/classic/icon_check.png";
  $("#checkImgI").removeClass("icon-dl_on");
  $("#checkImgI").addClass("icon-dl");
  ikkatuReset();
  $(".js_check").prop("checked",false);
}

function escapeSelectorString(val){
  var word;
    word = val.replace(/[ !"#$%&'()*+,.\/:;<=>?@\[\\\]^`{|}~]/g, "\\$&")
  return word;
}

function messageAreaCreate(data) {
  var roop = data["size"];
  var detail = "";
    var dateFlg;
    var kidokuFlg = 0;
    for(var i = 0; i < roop; i++) {
      if (data["messageSid_"+i] == null) {
          continue;
      }
      var border = 0;
      if (dateFlg != data["entryDay_"+i]) {
        dateFlg = data["entryDay_"+i];
        var date = data["entryDay_"+i];
        date = escapeSelectorString(date);
        if(!($("#"+date)[0])){
          detail += "<p id=\""+data["entryDay_"+i]+"\" class=\"cht_dayLine cl_fontWeek fw_b js_hiduke_fixed\">" + data["entryDay_"+i] + "</p>";
          border = 1;
        }
      }
      if (kidokuFlg == 0) {
        if (data["ownKidoku_"+i] == 1) {
          var kidokuFlg = 1;
          if(!($(".js_lineMidoku").length)){
            detail += "<p class=\"js_lineMidoku cht_dayLine cht_newLine cl_linkDef fw_b js_hiduke_fixed\">"+msglist_cht010['cht.cht010.11']+"</p>";
            border = 1;
          }
        }
      }
      if (border == 0) {
        detail += "<div class=\"chat_lrSpace mt5 mb5\"><div class=\"bor_t1 w100\"></div></div>";
      }
      detail += "<span value=\""+data["entryDay_"+i]+"\" class=\"js_hiduke\"></span>";
      if (data["messageKbn_"+i] == 9) {
        detail += "<div class=\"js_mediaArea chat_lrSpace pt10 pb10\">"
              + "  <span class=\"display_n js_kidoku\">"+data["ownKidoku_"+i]+"</span>"
                + "  <span id="+data["messageSid_"+i]+" class=\"js_check\">"
                + "    <img src=\"../common/images/classic/icon_trash.png\" class=\"btn_classicImg-display\">"
                + "    <img src=\"../common/images/original/icon_trash.png\" class=\"btn_originalImg-display\">&nbsp;"
                + msglist_cht010['cht.cht010.03']
                + "  </span>"
                + "</div>";
       }  else {
         var usrSid = data["usrSid_"+i];
         var sendId = data["senderSid"];
         if ((data["sessionSid"] == usrSid && sendId == null) || sendId == document.forms[1].cht010EditUsrSid.value) {
           var divClass = "media_border js_media_mine js_mediaArea js_media_" + data["messageSid_" + i];
           if (dlCheck == 1) {
               divClass += " js_media_select cursor_p";
           } else {
               divClass += " media_mine";
           }
           detail += "<div class=\"" + divClass + "\" onclick=\"check_selected('js_media_" + data["messageSid_" + i] + "')\">"
                   + "  <span class=\"display_n js_kidoku\">"+data["ownKidoku_"+i]+"</span>"
                   + "  <span class=\"js_chtCheckbox display_n selToukou ml10\">"
                   + "    <input type=\"checkbox\" id="+data["messageSid_"+i]+" class=\"check_size js_check\" value="+data["messageSid_"+i]+">"
                   + "  </span>"
                   + "  <div class=\"chat_lrSpace pt5 pb5 pos_rel\">"
                   + "    <div class=\"flo_l mr10 wp25\">";
           if (data["usrPictKf_"+i] != 0) {
           detail += "<span class=\"hikokai_photo-s hikokai_text cl_fontWarn\">非公</span>";
           } else {
             if (data["usrBinSid_"+i] == 0) {
               detail += "      <img src=\"../common/images/classic/icon_photo.gif\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_classicImg-display\"/>";
               detail += "      <img src=\"../common/images/original/photo.png\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_originalImg-display\"/>";
             } else {
               if (data["usrJkbn_"+i] == 9) {
                 detail += "      <img src=\"../common/images/classic/icon_photo.gif\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_classicImg-display\"/>";
                 detail += "      <img src=\"../common/images/original/photo.png\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_originalImg-display\"/>";
               } else {
                 detail += "      <div class=\"txt_c\"><img src=\"../common/cmn100.do?CMD=getImageFile&cmn100binSid="+data["usrBinSid_"+i]+"\"name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"userIcon_size-w25\"/></div>";
               }
             }
           }
           detail += "    </div>"
                   + "    <div class=\"of_h\">"
                   + "      <div class=\"js_media_heading\">"
                   + "      <span class=\"cl_linkDef linkHover_line cursor_p fw_b\" onclick=\"openUserInfoWindow("+data["usrSid_"+i]+");\">"+data["usrName_"+i]+"</span>"
                   + "        <span class=\"cl_fontWeek ml10\">"+data["entryTime_"+i]+"</span>";
           if (data["partnerKidoku_"+i] ==1 || (data["selectSid"] == data["senderSid"] && data["selectKbn"] == 1)) {
           detail +="         <span class=\"cl_fontWeek ml5\">"+msglist_cht010['cht.cht010.04']+"</span>";
           }
           detail += "      </div>"
                   + "      <div class=\"js_media_text_" + data["messageSid_" + i] + "\">";
           if (data["binSid_"+i] == -1) {
               detail += "        <span class=\"js_message word_b-all\">"+data["messageText_"+i]+"</span>";
               if (data["messageKbn_"+i] == 1) {
           detail +="         <span class=\"cl_fontWeek edit_chat fs_12\">"
                   + "          <span class=\"edit_time ml5 bgC_body bor1\">"
                   +              data["updateDay_"+i] + "&nbsp;"
                   +              data["updateTime_"+i]
                   + "          </span>"
                   + msglist_cht010['cht.cht010.02']
                   + "        </span>";
               }
           } else {
           detail += "        <a href=\"#!\" class=\"js_tempDonwload\" value=\""+data["messageSid_" + i]+"\">"
                   + "          <img src=\"../common/images/classic/icon_temp_file.gif\" class=\"btn_classicImg-display\">"
                   + "          <img src=\"../common/images/original/icon_attach.png\" class=\"btn_originalImg-display\">"
                   + "          <span class=\"js_message word_b-all js_temp temp_color\">"+data["binFileName_"+i]+data["binFileSizeDsp_"+i]+"</span>"
                   + "        </a>"
           }
           detail += "      </div>"
                   + "    </div>";
           if ($("#inText").attr('readonly') != 'readonly') {
              detail += "    <div class=\"edit_deleteArea js_editDeleteArea\"><div class=\"verAlignMid\">";
                   if (data["binSid_"+i] == -1) {
                   detail += "<span class=\"message_edit js_message_edit mr10 cl_linkDef cursor_p\" value="+ data["messageSid_"+i] + " >"
                           + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_edit_3.png\">"
                           + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_edit.png\">"
                           + "<span class=\"ml5\">" + msglist_cht010['cmn.edit'] + "</span>"
                           + "</span>";
                   }
                   detail += "<span class=\"message_delete js_message_delete cl_linkDef cursor_p\" value=" + data["messageSid_"+i] + ">"
                           + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_trash.png\">"
                           + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_delete.png\">"
                           + "<span class=\"ml5\">" + msglist_cht010['cmn.delete'] + "</span>"
                           + "</span>"
                           + "</div></div>";
            }
           detail += "  </div>"
                   + "</div>";
         } else {
           var midokuFlg = 0;
           if (data["type"] == "message"
               && data["command"] == "add"
               && usrSid != document.forms[1].cht010EditUsrSid.value) {
             midokuFlg = 1;
           } else {
             midokuFlg = data["ownKidoku_"+i];
           }
            var divClass = "media_border js_mediaArea js_media_" + data["messageSid_" + i];
            if (dlCheck == 1) {
                divClass += " js_media_select cursor_p";
            }
            detail += "<div class=\"" + divClass + "\" onclick=\"check_selected('js_media_" + data["messageSid_" + i] + "')\">"
                    + "  <span class=\"display_n js_kidoku\">"+midokuFlg+"</span>"
                    + "  <span class=\"js_chtCheckbox display_n selToukou ml10\">"
                    + "    <input type=\"checkbox\" id="+data["messageSid_"+i]+" class=\"check_size js_check\" value="+data["messageSid_"+i]+">"
                    + "  </span>"
                    + "  <div class=\"chat_lrSpace pt5 pb5 pos_rel\">"
                    + "    <div class=\"flo_l mr10 wp25\">";
            if (data["usrPictKf_"+i] != 0) {
             detail += "<span class=\"hikokai_photo-s hikokai_text cl_fontWarn\">非公</span>";
            } else {
              if (data["usrBinSid_"+i] == 0) {
                  detail += "      <img src=\"../common/images/classic/icon_photo.gif\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_classicImg-display\"/>";
                  detail += "      <img src=\"../common/images/original/photo.png\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_originalImg-display\"/>";
                } else {
                  if (data["usrJkbn_"+i] == 9) {
                      detail += "      <img src=\"../common/images/classic/icon_photo.gif\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_classicImg-display\"/>";
                      detail += "      <img src=\"../common/images/original/photo.png\" name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"wp25 btn_originalImg-display\"/>";
                  } else {
                      detail += "      <div class=\"txt_c\"><img src=\"../common/cmn100.do?CMD=getImageFile&cmn100binSid="+data["usrBinSid_"+i]+"\"name=\"userImage\" onload=\"initImageView50('userImage"+data["usrSid_"+i]+"')\" alt=\"写真\" class=\"userIcon_size-w25\"/></div>";
                  }
                }
            }
           detail += "    </div>"
                    + "    <div class=\"of_h\">"
                    + "      <div class=\"js_media_heading\">";
           if (data["usrUkoFlg_"+i] == 0) {
            detail += "        <span onclick=\"openUserInfoWindow(" + data["usrSid_"+i] + ");\" class=\"cl_linkDef linkHover_line cursor_p fw_b\">";
           } else {
            detail +="         <span onclick=\"openUserInfoWindow(" + data["usrSid_"+i] + ");\" class=\"mukoUser linkHover_line cursor_p fw_b\">";
           }
           if (data["usrJkbn_"+i] != 0) {
            detail +="           <del>"
                    +              data["usrName_"+i]
                    + "          </del>";
           }
           if (data["usrJkbn_"+i] == 0) {
            detail +=            data["usrName_"+i];
           }
            detail +="</span> ";
            detail +="<span class=\"cl_fontWeek ml10\">"
                    + data["entryTime_"+i]
                    + "</span>"
                    + "</div>"
                    + "<div class=\"word_b-all js_media_text_" + data["messageSid_" + i] + "\">";
           if (data["binSid_"+i] == -1) {
               detail +=          data["messageText_"+i];

               if (data["messageKbn_"+i] == 1) {
            detail +="         <span class=\"cl_fontWeek edit_chat fs_12\">"
                    + "          <span class=\"edit_time ml5 bgC_body bor1\">"
                    +              data["updateDay_"+i] + "&nbsp;"
                    +              data["updateTime_"+i]
                    + "          </span>"
                    + msglist_cht010['cht.cht010.02']
                    + "        </span>";
               }
           } else {
            detail += "        <a href=\"#\" class=\"js_tempDonwload\" value=\""+data["messageSid_" + i]+"\">"
                    + "          <img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_temp_file.gif\">"
                    + "          <img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_attach.png\">"
                    + "          <span class=\"js_message word_b-all js_temp temp_color\">"+data["binFileName_"+i]+data["binFileSizeDsp_"+i]+"</span>"
                    + "        </a>";
           }
            detail +="       </div>"
                    + "    </div>"
                    + "  </div>"
                    + "</div>";
         }
       }
    }
    return detail;
}

function messageSendArea(data) {

  var detail = "";
  var kbn =  data["messageAreaDisp"];

  if (kbn == 0) {
    detail += "<div class=\"w100\">"
    + "<span  class=\"verAlignMid mt5\">";
    if (data["enterFlg"] == 1) {
      detail +="<input type=\"checkbox\" name=\"enter\" value=\"1\" id=\"enter\" class=\"js_enterSend cursor_p\" checked><label for=\"enter\" class=\"pl5\">"+msglist_cht010['cht.cht010.09']+"</label>";
    } else {
      detail +="<input type=\"checkbox\" name=\"enter\" value=\"0\" id=\"enter\" class=\"js_enterSend cursor_p\"><label for=\"enter\" class=\"pl5\">"+msglist_cht010['cht.cht010.09']+"</label>";
    }
    detail += "</span>"
    + "<span id=\"js_errorMsg\" class=\"ml10 cl_fontWarn\"></span>"
    + "<span id=\"cmn110fileDataArea\" class=\"ml10\"></span>"
    + "</span>"
    + "<span class=\"flo_r\">"
    + "<button type=\"button\" class=\"baseBtn js_chtAttach\" onClick=\"attachmentLoadFile('');\">"
    + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_temp_file_2.png\">"
    + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_attach.png\"> "
    + msglist_cht010['cmn.attached']
    + "</button>"
    + "<input type=\"file\" id=\"attachmentAreaBtn\" class=\"display_none\" onchange=\"attachFileSelect(this, '');\" multiple=\"\">"
    + "<button type=\"button\" class=\"baseBtn display_n js_chtCansel\">"
    + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_close.png\">"
    + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_close.png\"> "
    + msglist_cht010['cmn.cancel']
    + "</button> "
    + "<button type=\"button\" class=\"baseBtn js_chtSend\">"
    + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_edit_1.png\">"
    + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_edit.png\"> "
    + msglist_cht010['cmn.sent']
    + "</button>"
    + "<button type=\"button\" class=\"baseBtn display_n js_chtConfirm\">"
    + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_kakutei.png\">"
    + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_kakutei.png\"> "
    + msglist_cht010['cmn.final']
    + "</button>"
    + "</div>";
    if (data["enterFlg"] == 1) {
      detail += "<textarea class=\"chattextArea js_chtTextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.10']+"\" ></textarea>"
    } else {
      detail += "<textarea class=\"chattextArea js_chtTextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.16']+"\" ></textarea>"
    }

  } else {
    detail += "  <div class=\"w100\">"
            +"     <span class=\"verAlignMid mt5\">"
            +"       <input type=\"checkbox\" name=\"enter\" class=\"mr5\" value=\"0\" disabled checked>"+msglist_cht010['cht.cht010.09']
            +"     </span>"
            + "  </div>";
    if (data["messageAreaDisp"] == 1) {
        $(".js_editDeleteArea").remove();
        detail += "  <textarea class=\"cursor_d chattextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.05']+"\" readonly ></textarea>";
    } else if (data["messageAreaDisp"] == 2) {
        $(".js_editDeleteArea").remove();
      detail += "  <textarea class=\"cursor_d chattextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.06']+"\" readonly ></textarea>";
    } else if (data["messageAreaDisp"] == 3) {
        $(".js_editDeleteArea").remove();
      detail += "  <textarea class=\"cursor_d chattextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.07']+"\" readonly ></textarea>";
    } else if (data["messageAreaDisp"] == 4) {
        $(".js_editDeleteArea").remove();
      detail += "  <textarea class=\"cursor_d chattextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.08']+"\" readonly ></textarea>";
    } else if (data["messageAreaDisp"] == 5) {
      $(".js_editDeleteArea").remove();
      detail += "  <textarea class=\"cursor_d chattextArea\" id=\"inText\" placeholder=\""+msglist_cht010['cht.cht010.39']+"\" readonly ></textarea>";
    }
  }
  $(".js_sendMessageArea").children().remove();
  $(".js_sendMessageArea").append(detail);
}

var wordOverFlg = false;
// メッセージを送信
function sendMessage(selectSid, selectKbn) {
  // 送信するメッセージ
  $("#js_errorMsg").text("");
  var message = $('.js_chtTextArea').val();
  $("#cmn110fileDataArea").children().remove();
  wordOverFlg = true;

    var param = createParamCht010();
    param['CMD'] = 'sendMessage';
    param['cht010Message'] = message.replace(/\r/g, '').replace(/\n/g, '\r\n');
    param['cht010SelectPartner'] = selectSid;
    param['cht010SelectKbn'] = selectKbn;
    //ファイルダウンロードにより残った選択情報を除去
    delete param.cht010MessageSid;
    var paramStr = $.param(param, true);
    paramStr = setToken(paramStr);
    $.ajax({
        async: false,
        url:  "../chat/cht010.do",
        type: "post",
        data: paramStr
    }).done(function(data) {
        if (data["success"]) {
            textClear();
            if (!parent.webSocket) {
                if (typeof dspError == 'function') {
                    var errorMsg = "";
                    errorMsg = "メッセージの表示に失敗しました。<br>"
                        + "再接続を行うと送信したメッセージが表示されます。";
                    dspError(errorMsg);

                }
            }
            midokuData = $.extend(true, {}, data);
            if (data["selectKbn"] == 1) {
                midokuData['senderSid'] = data['selectSid'];
                midokuData["usrName_0"] = data['chatName'];
                midokuData["usrJkbn_0"] = data['usrJkbn'];
                midokuData["usrUkoFlg_0"] = data['usrUkoFlg'];
            }
            midokuData["entryTime_0"] = '';
            midokuData["insertDate_0"] = '';

        } else if (data["error"]) {
            sendError(data);
        } else if (data["errorAlert"]) {
            sendErrorAlert(data);
        } else if (data["tokenError"]){
            tokenError(data);
        } else {
            if (typeof dspError == 'function') {
                var errorMsg = msglist_cht010['cht.cht010.50'];

                dspError(errorMsg);
            }
        }

    }).fail(function(data){
        if (typeof dspError == 'function') {
            var errorMsg = msglist_cht010['cht.cht010.50'];

            dspError(errorMsg);
        }
    });


  wordOverFlg = false;
}

// メッセージの削除処理
function confirmDeleteMessage(selectSid, selectKbn, messageSid) {
    var param = createParamCht010();
    param['CMD'] = 'messageDelete';
    param['cht010MessageSid'] = messageSid
    param['cht010SelectPartner'] = selectSid;
    param['cht010SelectKbn'] = selectKbn;
    var paramStr = $.param(param, true);
    paramStr = setToken(paramStr);
    $.ajax({
        async: true,
        url:  "../chat/cht010.do",
        type: "post",
        data: paramStr
    }).done(function( data ) {
        if (data["success"]) {
            if (!parent.webSocket) {
                if (typeof dspError == 'function') {
                    var errorMsg = "";
                    errorMsg = "メッセージ削除結果の表示に失敗しました。<br>"
                        + "再接続を行うと削除結果が画面に反映されます。";
                    dspError(errorMsg);
                }
            }
        } else if (data["errorAlert"]) {
            sendErrorAlert(data);
        } else if (data["tokenError"]){
            tokenError(data);
        } else {
            if (typeof dspError == 'function') {
                var errorMsg = msglist_cht010['cht.cht010.50'];

                dspError(errorMsg);
            }
        }
    }).fail(function(data){
        if (typeof dspError == 'function') {
            var errorMsg = msglist_cht010['cht.cht010.50'];

            dspError(errorMsg);
        }
    });
}

// メッセージの更新処理
function confirmEditChatMessage(msgContent, selectSid, selectKbn, messageSid) {
    var message = msgContent;

    $("#cmn110fileDataArea").children().remove();


    var param = createParamCht010();
    param['CMD'] = 'messageEdit';
    param['cht010MessageSid'] = messageSid
    param['cht010SelectPartner'] = selectSid;
    param['cht010SelectKbn'] = selectKbn;
    param['cht010Message'] = message.replace(/\r/g, '').replace(/\n/g, '\r\n');

    var paramStr = $.param(param, true);
    paramStr = setToken(paramStr);
    $.ajax({
        async: true,
        url:  "../chat/cht010.do",
        type: "post",
        data: paramStr
    }).done(function( data ) {
        if (data["success"]) {
            textClear();
            if (!parent.webSocket) {
                if (typeof dspError == 'function') {
                    var errorMsg = "";
                    errorMsg = "メッセージの表示に失敗しました。<br>"
                        + "再接続を行うと送信したメッセージが表示されます。";
                    dspError(errorMsg);
                }
            }
        } else if (data["error"]) {
            sendError(data);
        } else if (data["errorAlert"]) {
            sendErrorAlert(data);
        } else if (data["tokenError"]){
            tokenError(data);
        } else {
            if (typeof dspError == 'function') {
                var errorMsg = msglist_cht010['cht.cht010.50'];

                dspError(errorMsg);
            }
        }
    }).fail(function(data){
        if (typeof dspError == 'function') {
            var errorMsg = msglist_cht010['cht.cht010.50'];

            dspError(errorMsg);
        }
    });
}

/**
 * 開いたメッセージの既読通知を送る
 * @param selectSid
 * @param readMsgSids
 * @returns
 */
function sendKidoku(selectSid, readMsgSids) {
    var param = createParamCht010();
    param['CMD'] = 'getAdmConf';
    param['cht010SelectPartner'] = selectSid;
    param['cht010KidokuMessageSids'] = String(readMsgSids);

    var paramStr = $.param(param, true);

    // 管理者設定からデータ取得
    $.ajax({
        async: true,
        url:  "../chat/cht010.do",
        type: "post",
        data: paramStr
    }).done(function(data) {});
}

// トークンエラー
function tokenError(data) {
  var detail = data["errorMessage_0"];
  $("#js_error").text(detail);
}

// メッセージ送信エラー
function sendError(data) {
  var detail = "";
  for (var i = 0; i < data["errorSize"]; i++) {
    var denger = "<br>";
    var word = data["errorMessage_"+i].replace(denger,"");
    detail += word;
  }
    $("#js_errorMsg").text(detail);
}
// メッセージ送信エラーalert
function sendErrorAlert(data) {
    alert(data["errorMsg"]);
}

// メッセージを編集
function editMessage(data) {
    var messageText = data["msgContent"];
          if (data["success"]) {
            if (data["selectKbn"] == document.forms[1].cht010SelectKbn.value) {
              var detail = "";

                detail += "     <div class=\"js_media_text_" + data["messageSid"] + "\">"
                        + "       <span class=\"js_message word_b-all\">"+messageText+"</span>"
                        + "       <span class=\"cl_fontWeek edit_chat fs_12\">"
                        + "         <span class=\"edit_time ml5 bgC_body bor1 cl_fontBody\">"
                        +             data["updateDay"] + "&nbsp;"
                        +             data["updateTime"]
                        + "         </span>"
                        + msglist_cht010['cht.cht010.02']
                        + "       </span>"
                        + "     </div>";
                editParent = $(".js_media_text_" + data["messageSid"]);
                editParent.empty();
                editParent.append(detail);

                if (data["senderSid"] == document.forms[1].cht010EditUsrSid.value) {
                  $('.js_chtTextArea').val("");
                  var textarea = document.getElementById("inText");
                  textarea.style.height = chat_textarea_min_height + 'px';
                  chat_area_resize(false);
                  $(".js_mediaArea").removeClass("media_selected");
                  $(".js_media_mine").addClass("media_mine");
                  $(".js_chtTextArea").removeClass("media_selectedColor");
                  $(".js_chtTextArea").addClass("chattextArea_color");
                  $(".js_chtConfirm").addClass("display_n");
                  $(".js_chtCansel").addClass("display_n");
                  $(".js_chtSend").removeClass("display_n");
                  $(".js_chtAttach").removeClass("display_n");
                  mode = 0;
                }
            }
          } else {
            alert(msglist_cht010['cht.cht010.29']);
            $(this).dialog('close');
          }
}

// メッセージ削除の実行
function deleteMessage(data) {
    if (data["success"]) {
      if (data["selectKbn"] == document.forms[1].cht010SelectKbn.value) {
        var detail = "";
        detail +="<div class=\"js_mediaArea chat_lrSpace pt10 pb10\">"
              + "        <span id=\""+data["messageSid"]+"\" class=\"js_check\">"
              + "          <img src=\"../common/images/classic/icon_trash.png\" class=\"btn_classicImg-display\">"
              + "          <img src=\"../common/images/original/icon_trash.png\" class=\"btn_originalImg-display\">&nbsp;"
              +            msglist_cht010['cht.cht010.03']
              + "        </span>"
              + "       </div>";
        var message = $(".js_media_" + data["messageSid"]);
        message.empty();
        message.removeClass("media_border js_mediaArea media_mine js_media_mine");
        message.append(detail);
      }
      } else {
        alert(msglist_cht010['cht.cht010.29']);
        $(this).dialog('close');
      }
}

function appendMessage(data) {
  // 送信者または送信先グループの画面を表示している場合のみ画面に描画

  var senderFlg = data["senderSid"] == document.forms[1].cht010EditUsrSid.value;
  var selectUserFlg = data["senderSid"] == document.forms[1].cht010SelectPartner.value && data["selectKbn"] == 1;
  var selectGroupFlg = data["selectSid"] == document.forms[1].cht010SelectPartner.value && data["selectKbn"] == 2;
  if (senderFlg || selectUserFlg || selectGroupFlg) {
  if (allDispFlg == 1) {
      var area = document.getElementById("js_chatMessageArea");
      var scrollFromBottom = area.scrollHeight - area.clientHeight - area.scrollTop;
      var detail = "";
      detail = messageAreaCreate(data);

      $("#js_chatMessageArea").append(detail);
      $(".js_chatSpaceArea").remove()
      $("#js_chatMessageArea").append("<div class=\"js_chatSpaceArea\">&nbsp;</div>");
      if (dlCheck == 1) {
          ikkatuReset();
      }
      if (data["senderSid"] == document.forms[1].cht010EditUsrSid.value || scrollFromBottom == 0) {
        scrollAutoReadFlg = 1;
        $("#js_chatMessageArea").animate({scrollTop: $("#js_chatMessageArea").get(0).scrollHeight},'fast',function(){
          scrollAutoReadFlg = 0;
        });

      }
      // スクロールできない画面の場合、受信したメッセージを既読にする
      if ($("#js_chatMessageArea").get(0).scrollHeight - $("#js_chatMessageArea").get(0).clientHeight <= 0) {
        changeToKidoku(document.forms[1].cht010SelectKbn.value, document.forms[1].cht010SelectPartner.value);
      }
    }
    chat_area_resize(false);
    // 受信したユーザが送信者と同じ場合、未読件数は増やさない
    if (data["senderSid"] == document.forms[1].cht010EditUsrSid.value) {
      var textarea = document.getElementById("inText");
      textarea.style.height = chat_textarea_min_height + 'px';
      chat_area_resize(false);
      var cnt = $(".js_mediaArea ");
      updateMidokuTab(data, 0, true);
      return;
    }
  }
    // 未読数更新
    var jsCht;
    var jsName;
    var jsDspName;
    var targetSid;
    if (data["selectKbn"] == 1) {
      jsCht = "js_chtUser";
      targetSid = data["senderSid"];
    } else if (data["selectKbn"] == 2) {
      jsCht = "js_chtGroup";
      targetSid = data["selectSid"];
    }
    // 未読件数
    var midokuCnt;

    // ユーザ情報・グループ情報に表示されている未読件数
    if ($("." + jsCht + "[value=" + targetSid + "] .js_midokuCount").length > 0) {
        midokuCnt = Number($($("." + jsCht + "[value=" + targetSid + "] .js_midokuCount")[0]).text());
        midokuCnt++;
    // 画面に表示されないユーザの未読件数を取得
    } else if (data["selectKbn"] == 1) {
      paramStr = "CMD=getMidokuCnt";
      paramStr += "&cht010SelectPartner=" + data["senderSid"];
      paramStr += "&cht010EditUsrSid=" + data["selectSid"];
      paramStr += "&cht010SelectKbn=" + data["selectKbn"];

        $.ajax({
          async:false,
          url: "../chat/cht010.do",
          type: "post",
          data: paramStr
        }).done(function(data) {
          midokuCnt = data["midokuCnt"];
        });
    } else if (data["selectKbn"] == 2) {
      paramStr = "CMD=getMidokuCnt";
        paramStr += "&cht010SelectPartner=" + data["selectSid"];
        paramStr += "&cht010EditUsrSid=" + document.forms[1].cht010EditUsrSid.value;
        paramStr += "&cht010SelectKbn=" + data["selectKbn"];
        $.ajax({
            async:false,
            url: "../chat/cht010.do",
            type: "post",
            data: paramStr
        }).done(function(data) {
            midokuCnt = data["midokuCnt"];
        });
    }
    var midokuAddCnt = midokuCnt;

    // 未読が既にある
    if (midokuCnt > 0) {
        if ($("." + jsCht + "[value=" + targetSid + "] .js_midokuCount").length > 0) {
            midokuAddCnt -= Number($($("." + jsCht + "[value=" + targetSid + "] .js_midokuCount")[0]).text());
        }
        $("." + jsCht + "[value=" + targetSid + "] .js_midokuCount").text(midokuCnt);
    }
    // 未読タブ
    updateMidokuTab(data, midokuCnt, false, midokuAddCnt);
}

function dspError(msg) {
  $("#js_error").empty();
  $("#js_error").append(msg);
}

function appendGroup(group) {
    $(".js_archive").before(group);
}

//チャットグループの表示を追加
function addNewGroup(groupSid, groupName) {
  if ($("a[class*=js_chtGroup][value=" + groupSid +"]").length == 0) {
      var group = "";
      group += "<div class=\"pl5 w100 mt5\">"
      group += "<a class=\"js_chtGroup cl_linkDef js_archiveGroup js_group_name display_b lh130\" href=\"#!\" value=\"" + groupSid + "\">"
            + "<span class=\"js_dsp_group_name\">" + groupName + "</span>"
            + "<span class=\"midokuCount js_midokuCount\"></span>"
            + "</a>";
      group += "</div>"
               $(".js_archive").before(group);
  }
}

// チャットグループの表示を更新
function updateGroup(groupSid, groupName, archiveFlg, messageCount, messageLastDate) {

  var midokuCnt = 0;
  var midokuCntStr = '';
  // グループ情報
  if ($("a[class*=js_chtGroup][value=" + groupSid +"]").length > 0) {
    // 未読件数
    if ($("a[class*=js_group_name][value=" + groupSid +"]").children(".js_midokuCount").length > 0) {
      midokuCnt = Number($("a[class*=js_group_name][value=" + groupSid +"]").children(".js_midokuCount").text());
    }
    if (midokuCnt > 0) {
        midokuCntStr = midokuCnt;
    }
    $("a[class*=js_chtGroup][value=" + groupSid +"]").empty();
    var groupDiv = "<span class=\"js_dsp_group_name\">" + groupName + "</span>";
    $("a[class*=js_chtGroup][value=" + groupSid +"]").append(groupDiv);
    $("a[class*=js_chtGroup][value=" + groupSid +"]").append("<span class=\"midokuCount js_midokuCount\">" + midokuCntStr + "</span>");

    // アーカイブモード切替
    if (archiveFlg == 1) {
      $("a[class*=js_chtGroup][value=" + groupSid +"]").parent().addClass("display_n");
      $("a[class*=js_chtGroup][value=" + groupSid +"]").removeClass("cl_linkDef");
      $("a[class*=js_chtGroup][value=" + groupSid +"]").addClass("cl_fontWeek");
      $("a[class*=js_chtGroup][value=" + groupSid +"]").addClass("js_archiveGroup");
    } else {
      $("a[class*=js_chtGroup][value=" + groupSid +"]").parent().removeClass("display_n");
      $("a[class*=js_chtGroup][value=" + groupSid +"]").removeClass("js_archiveGroup");
      $("a[class*=js_chtGroup][value=" + groupSid +"]").removeClass("cl_fontWeek");
      $("a[class*=js_chtGroup][value=" + groupSid +"]").addClass("cl_linkDef");
    }

    var check = $("[name=archive]:checked").val();
    var parent = $(".js_archiveGroup").parent();
    if (check == 1) {
      parent.show();
    } else {
      parent.hide();
    }

    //タイムラインタブ
    var groupNameTag = "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_group.png\">"
    + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_group.png\"> "
    + groupName
    + "<span class=\"midokuCount js_midokuCount\">" + midokuCntStr + "</span>";
    $("div.timeline_div.js_chtGroup[value=" + groupSid +"]").find(".js_dspName").empty();
    $("div.timeline_div.js_chtGroup[value=" + groupSid +"]").find(".js_dspName").append(groupNameTag);
    if (archiveFlg == 1) {
        $("div.timeline_div.js_chtGroup[value=" + groupSid +"]").addClass("js_archiveGroup");
    } else {
      $("div.timeline_div.js_chtGroup[value=" + groupSid +"]").removeClass("js_archiveGroup");
    }
    updateTimeline();

    //更新したグループを開いている場合
    if (document.forms[1].cht010SelectPartner.value == groupSid && document.forms[1].cht010SelectKbn.value == 2) {
      $("a[class*=js_chtGroup][value=" + groupSid +"]").click();
      return;
    }
  // 元々ない場合は新たに追加
  } else {
      var group = "";
      group += "<div>"
               + "<div class=\"left_menu_group_txt chat_float_left chat_favorite_margin\">";
               if (archiveFlg == 1) {
                 group += "<a class=\"cl_fontWeek js_chtGroup js_archiveGroup js_group_name\" href=\"#\" value=\"" + groupSid + "\">"
                       + "<span class=\"js_dsp_group_name\">" + groupName + "</span>"
                       + "</a>";
               } else {
                 group += "<a class=\"chat_group js_chtGroup js_group_name\" href=\"#\" value=\"" + groupSid + "\">"
                       + "<span class=\"js_dsp_group_name\">" + groupName + "</span>"
                       + "</a>";
               }
               group += "</div>"
                     +  "<br>"
                     +  "</div>";
      for (i = 0; i <= $(".js_group_name").length; i++ ) {
        var biggerSid = Number(groupSid) + i;
        if ($("a[class*=js_group_name][value=" + biggerSid +"]").length > 0) {
          $("a[class*=js_group_name][value=" + biggerSid +"]").parent().parent().before(group);
          break;
      } else {
          $(".js_archive").before(group);
          break;
        }
      }
    // 未読件数を取得
    if (messageCount > 0) {
        midokuCnt = messageCount
      }
      if (midokuCnt > 0) {
          midokuCntStr = midokuCnt;
      }
      $("a[class*=js_chtGroup][value=" + groupSid +"]").append("<span class=\"midokuCount js_midokuCount\">" + midokuCntStr + "</span>");
    // 未読タブ
    if (messageLastDate.length > 0 && midokuCnt > 0) {
      var month = messageLastDate.substring(4, 6);
      var day = messageLastDate.substring(6, 8);
      var hour = messageLastDate.substring(8, 10);
      var min = messageLastDate.substring(10, 12);
      var now = new Date();
      var archiveCls = '';
      if (archiveFlg == 1) {
          archiveCls = 'js_archiveGroup';
      }

      var midokuDiv = "<div class=\"bor_t1 p5 cursor_p timeline_div  js_chtGroup "+ archiveCls +"\" value=\"" + groupSid + "\">"
      + "<div>"
      + "<span class=\"js_dsp_group_name js_dspName\">"
      + "<img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_group.png\">"
      + "<img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_group.png\">"
      + groupName
      + "<span class=\"midokuCount js_midokuCount\">" + midokuCntStr + "</span>"
      + "</div>"
      + "<div class=\"lh_normal fs_12 js_lastTime txt_r\">";
      if (month != now.getMonth() + 1 || day != now.getDate()) {
        midokuDiv += month + "/" + day + " ";
      }
      midokuDiv += hour + ":" + min;
      + "</div>"
      + "<div class=\"display_n js_dateNone\">"
      + messageLastDate
      + "</div>"
      + "</div>";
      // 最新の最終投稿日時を取得
      var dateArrayUser = $("div[class*=js_midoku_div_usr]");
      var dateArrayGroup = $("div[class*=js_midoku_div_group]");
      // 最終投稿日時
      var lastDateArray = dateArrayUser;
      for (i = 0; i < dateArrayGroup.length; i++) {
        lastDateArray.push(dateArrayGroup[i]);
      }
      lastDateArray.sort(function(a,b){
              if( a > b ) return -1;
              if( a < b ) return 1;
              return 0;
      });

      var prevDate = Number(messageLastDate);
      var prevTagClass;
      for (i = 0; i < lastDateArray.length; i++) {
        var date = Number(lastDateArray[i].lastElementChild.innerHTML);
        // 表示されている未読メッセージのどれよりも最終投稿日時が古い場合は追加しない
        if (i + 1  == lastDateArray.length && prevDate == Number(messageLastDate)) {
          prevTagClass = "";
          break;
        } else if (Number(messageLastDate) <= date) {
          prevDate = date;
          prevTagClass = lastDateArray[i].className.split(" ")[2];
        }
      }
      if (prevDate == Number(messageLastDate)) {
        $("#timeline_body_area").children(".js_timelineListArea").prepend(midokuDiv);
      } else if (prevTagClass.length > 0) {
        $("div[class*=" + prevTagClass + "]").after(midokuDiv);
      }
      updateTimeline();
      // 未読タブの件数更新
      var allMidoku = Number($(".js_allMidoku").text());
      allMidoku += Number(messageCount);
      updateAllMidokuCnt(allMidoku);

      // 未読がない時に表示されているメッセージを除去
      if ($(".js_no_new_message").length > 0) {
        $(".js_no_new_message").remove();
      }
    }
  }
}

// 未読タブの表示更新
function updateMidokuTab(data, midokuCnt, own, midokuAddCnt) {
  var jsCht;
  var jsName;
  var jsDspName;
  var jsMidokuCol;
  var icon;
  var targetSid;
  var name = "";
  var usrMukoCls = '';
  if (data["selectKbn"] == 1) {
    jsCht = "js_chtUser";
        jsName = "js_user_name";
        jsDspName = "js_dsp_user_name js_dspName";
    jsMidokuCol = "cl_linkDef";
    icon = "user_icon_s.gif";
    targetSid = data["senderSid"];
    name = data["usrName_0"]
    if (data['usrUkoFlg_0'] != 0) {
        usrMukoCls = 'mukoUser';
    }
    if (own) {
        name = $('.js_chatName').text();
        targetSid = data["selectSid"];
    }
  } else if (data["selectKbn"] == 2) {
    jsCht = "js_chtGroup";
    jsName = "js_group_name";
    jsDspName = "js_dsp_group_name js_dspName";
    jsMidokuCol = "cl_linkDef";
    icon = "groupicon.gif";
    targetSid = data["selectSid"];
    name = $("a[class*=" + jsName + "][value=" + targetSid +"]").find("." + jsDspName).text()
    if (name == "") {
          paramStr = 'CMD=getGroupName';
        paramStr = paramStr + '&cht010SelectPartner=' + targetSid;
        $.ajax({
            async: false,
            url:  "../chat/cht010.do",
            type: "post",
            data: paramStr
        }).done(function( data ) {
            if (data["success"]) {
              name = data["groupName"];
            } else {
              alert(msglist_cht010['cht.cht010.25']);
            }
        }).fail(function(data){
          alert(msglist_cht010['cht.cht010.24']);
        });
      }
  }
    // 新着
    var timeLineRow = $('.timeline_div.' + jsCht + '[value=' + targetSid + ']');

    if (timeLineRow.length == 0) {
        var midokuCntStr ='';
        if (midokuCnt > 0) {
            jsMidokuCol = 'cl_linkDef';
            midokuCntStr =midokuCnt;
        } else {
            jsMidokuCol = 'cl_fontBody';
        }

        var midokuDiv = "<div class=\"bor_t1 p5 cursor_p timeline_div " + jsCht + "\" value=\"" + targetSid + "\">"
                    + "<div>"
                    + "<span class=\"" + jsDspName + " " + jsMidokuCol + " " + usrMukoCls + "\">";
        if (data["selectKbn"] == 1) {
          midokuDiv += "<img class=\"btn_classicImg-display wp18hp20\" src=\"../common/images/classic/icon_user.png\">"
                    + "<img class=\"btn_originalImg-display wp18hp20\" src=\"../common/images/original/icon_user.png\"> "

        } else if (data["selectKbn"] == 2) {
          midokuDiv += "<img class=\"btn_classicImg-display wp18hp20\" src=\"../common/images/classic/icon_group.png\">"
                    + "<img class=\"btn_originalImg-display wp18hp20\" src=\"../common/images/original/icon_group.png\"> "
        }
        midokuDiv += name
                    + "<span class=\"midokuCount js_midokuCount\">" + midokuCntStr + "</span>"
                    + "</span>"
                    + "</div>"
                    + "<div class=\"lh_normal fs_12 js_lastTime txt_r\">"
                    + data["entryTime_0"]
                    + "</div>"
                    + "<div class=\"display_n js_dateNone\">"
                    + data["insertDate_0"]
                    + "</div>";
                    + "</div>"
        var midoku = $(midokuDiv);
        $("#timelineBodyArea").find(".js_timelineListArea").prepend(midoku);
    // 更新
    } else if (timeLineRow.length > 0) {
        timeLineRow.find(".js_lastTime").empty();
        timeLineRow.find(".js_lastTime").text(data["entryTime_0"]);
        timeLineRow.find(".js_dateNone").empty();
        timeLineRow.find(".js_dateNone").text(data["insertDate_0"]);
        timeLineRow.remove();
        $("#timelineBodyArea").find(".js_timelineListArea").prepend(timeLineRow);
    }
    // 未読がない時に表示されているメッセージを除去
    if ($(".js_no_new_message").length > 0) {
        $(".js_no_new_message").remove();
    }

    updateTimeline();
    // 未読タブの件数更新
    var allMidoku = Number($(".js_allMidoku").text());
    if (midokuAddCnt != null) {
      allMidoku += midokuAddCnt;
    }
    updateAllMidokuCnt(allMidoku);

}

function removeGroup(groupSid) {
  var midokuCnt = 0;
    if ($("a[class*=js_group_name][value=" + groupSid +"]").children(".js_midokuCount").length > 0) {
      midokuCnt = Number($("a[class*=js_group_name][value=" + groupSid +"]").children(".js_midokuCount").text());
    }
    $("a[class*=js_chtGroup][value=" + groupSid +"]").parent().remove();
    $("div[class*=js_midoku_div_group_" + groupSid).remove();
    $("div.timeline_div.js_chtGroup[value=" + groupSid +"]").remove();
    // 未読タブ件数
    var allMidoku = Number($(".js_allMidoku").text());
    allMidoku -= midokuCnt;
    updateAllMidokuCnt(allMidoku);
}

// 指定したメッセージに「既読」を表示
function dspKidoku(data) {
  var msgArray = data["readMsgSids"].split(",");
  for (i = 0; i < msgArray.length; i++) {
    var kidoku = "<span class=\"cl_fontWeek ml5\">既読</span>";
    $(".js_media_" + msgArray[i]).find(".js_media_heading").append(kidoku);
  }
}


//スクロールバーサイズ取得
function getScrollbarWidth() {
  var scrollbarWidth;
  var userAgent = window.navigator.userAgent.toLowerCase();
  if (userAgent.indexOf('chrome') != -1 || userAgent.indexOf('safari') != -1) {
    scrollbarWidth = 10;
  } else {
    const scrollbarElem = document.createElement('div');
    scrollbarElem.setAttribute('style', 'visibility: hidden; position: absolute; top: 0; left: 0; width: 100vw;');
    document.body.appendChild(scrollbarElem);
    const vw = parseInt(window.getComputedStyle(scrollbarElem).width);
    scrollbarElem.style.width = '100%';
    const pc = parseInt(window.getComputedStyle(scrollbarElem).width);
    document.body.removeChild(scrollbarElem);
    scrollbarWidth = vw - pc;
  }
  return scrollbarWidth;
}

// メイン画面から遷移してきた場合はWebSocket再接続
function fromMain(clientSid, pluginUrl) {
    if (document.forms[1].cht010FromMain.value == 1) {
        parent.reConnect(clientSid, pluginUrl);
    }
}

// 再読み込みボタン
function pushReload() {
  $(".js_reload").addClass("pointer_none");
    var connection = parent.checkConnection();
    if (connection) {
      parent.closeConnect();
    }
    document.forms[1].CMD.value="reload";
    document.forms[1].submit();
    return false;
}

// WebSocket再接続 onloadで使用すること
function wsReload() {
  if (document.forms[1].cht010InitFlg.value== 2) {
    parent.webSocketCommunicate("../chat", true, true);
  }
  else if (document.forms[1].cht010InitFlg.value== 1) {
    var connection = parent.checkConnection();
      if (!connection) {
        dspError(msglist_cht010['cht.cht010.40']);
      }
  }
}

function textClear() {
  $('.js_chtTextArea').val("");
  var textarea = document.getElementById("inText");
    textarea.style.height = chat_textarea_min_height + 'px';
    chat_area_resize(false);
    mode = 0;
}

function createParamCht010() {
    var serialArr = $('#js_chtForm').serializeArray();
    var ret = {};
    $.each(serialArr, function () {
        ret[this.name]= this.value;
    });
    return ret;
}

/**
 * タイムライン全件の表示を切り替える
 * 既読未読の色変更、既読による非表示（未読のみ時）
 * 0件時のメッセージ表示
 *
 * @param timeline 変更対象タイムラインjqueryオブジェクト（.timeline_div [value={ターゲットSID}]）
 * @param midokuCnt 未読件数
 *
 */
function updateTimeline() {

    var onlyNoRead = ($('.js_checkOnlyNoRead:checked').val() == 1);
    var noBrank = false;
    //各行の表示更新
    $.each(
            $('.js_timelineListArea .js_chtGroup ,' +
                '.js_timelineListArea .js_chtUser'),
            function () {
                noBrank = true;

                var timeline = $(this).find('.js_dspName');
                var midokuCnt = Number(timeline.find(".js_midokuCount").text());

                if (onlyNoRead && midokuCnt == 0) {
                    //未読のみ表示時は行を削除
                    $(this).remove();
                    return;
                }

                timeline = timeline.removeClass('cl_linkDef');
                timeline = timeline.removeClass('cl_fontBody');
                timeline = timeline.removeClass('cl_fontWeek');

                if (midokuCnt > 0) {
                    timeline.addClass('cl_linkDef');
                    return;
                }
                if ($(this).is('.js_archiveGroup')) {
                    timeline.addClass('cl_fontWeek');
                    return;
                }
                timeline.addClass('cl_fontBody');
            });
    //タイムラインがない場合
    if ($('.js_timelineListArea .js_chtGroup ,' +
                '.js_timelineListArea .js_chtUser').length <= 0
            && $('.js_moreView ').is(':hidden')) {
          var noNewMessage = "";
          noNewMessage = "<div class=\"p5 bor_t1 js_no_new_message\">"+msglist_cht010['cht.cht010.13']+"</div>";
          $(".js_timelineListArea").empty();
          $(".js_timelineListArea").prepend(noNewMessage);

    }

}
/**
 * タイムライン再読み込み/追加読み込み
 * @param reset 再読み込みならtrue 追加読み込みならfalse
 * @returns
 */
function loadTimeline(reset) {
    var param = createParamCht010();
    param['CMD'] = 'moreView';
    if (!reset) {
        var lastDate = $("#js_lastdate").text();
        param['cht010MidokuLastDate'] = lastDate
    }
    paramStr = $.param(param, true);
    $.ajax({
          async: true,
          url:  "../chat/cht010.do",
          type: "post",
          data: paramStr
      }).done(function( data ) {
          if (data["success"]) {
              if (reset) {
                  $(".js_timelineListArea").empty();
              }
              var last ="";
              for (var i=0; i < data["size"]; i++) {
                  var detail = "";
                  var archiveCls = '';
                  if (data["archive_"+i] == 1) {
                      archiveCls = 'js_archiveGroup';
                  }
                  var usrUkoFlg = '';
                  if (data["usrUkoFlg_" + i] != 0) {
                      var usrUkoFlg = 'mukoUser';
                  }

                  if (data["kbn_"+i] == 1) {
                      detail +=" <div class=\"bor_t1 p5 cursor_p js_chtUser\" value=" + data["sid_" + i] + ">"
                      detail +="     <div>"
                          +"       <span class=\"js_dspName " + usrUkoFlg + "\">"
                          +"         <img class=\"btn_classicImg-display wp18hp20\" src=\"../common/images/classic/icon_user.png\"> "
                          +"         <img class=\"btn_originalImg-display wp18hp20\" src=\"../common/images/original/icon_user.png\"> "
                  } else if (data["kbn_"+i] == 2) {
                      detail +=" <div class=\"bor_t1 p5 cursor_p js_chtGroup\" value=" + data["sid_" + i] + ">"
                          +"   <div class=\"chat_forum_link chat_favorite_margin\">"
                          +"     <div>"
                          +"       <span class=\"js_dspName " + archiveCls + "\">"
                          +"         <img class=\"btn_classicImg-display\" src=\"../common/images/classic/icon_group.png\"> "
                          +"         <img class=\"btn_originalImg-display\" src=\"../common/images/original/icon_group.png\"> "
                  }

                  if (data["usrJkbn_" + i] != 0) {
                      detail +="     <del>"
                  }
                  detail  += data["name_"+i];
                  if (data["usrJkbn_" + i] != 0) {
                      detail +="</del>"
                  }
                  detail  += "<span class=\"midokuCount js_midokuCount\">";
                  if (data["count_"+i] > 0) {
                      detail  += data["count_"+i];
                  }
                  detail  += "</span>";
                  detail  += "</span>";
                  detail  += "</div>";
                  detail  += "<div class=\"lh_normal fs_12 js_lastTime txt_r\">";
                  detail  += data["dispDate_"+i];
                  detail  += "</div>";
                  detail  += "<div class=\"display_n js_dateNone\">";
                  detail  += data["date_" + i];
                  detail  += "</div>";
                  detail  += "</div>";

                  var obj = $(detail);
                  last=data["date_"+i]
                  $(".js_timelineListArea").append(obj);
              }
              $("#js_lastdate").text(last);
              if (data["buttonDisp"] == 0) {
                  $(".js_moreView ").addClass("display_n");
              } else if (data["buttonDisp"] == 1) {
                  $(".js_moreView ").removeClass("display_n");
              }
              updateTimeline();
          } else {
                alert(msglist_cht010['cht.cht010.25']);
          }
      }).fail(function(data){
            alert(msglist_cht010['man.error']);
      });
}

function updateAllMidokuCnt(cnt) {
    $("#timelineHeadArea").removeClass('menuHead-midoku');
    $('.js_timelineBach').removeClass('midokuBach-on');

    if (cnt > 0) {
        $(".js_allMidoku").text(cnt);
        $("#timelineHeadArea").addClass('menuHead-midoku');
        $('.js_timelineBach').addClass('midokuBach-on');
    } else {
        $(".js_allMidoku").text('');
    }

}

function cmn110Updated(tempWindow) {

    var sid = document.cht010Form.cht010SelectPartner.value;
    var kbn = document.cht010Form.cht010SelectKbn.value;
    var sender = document.cht010Form.cht010EditUsrSid.value;

    var paramStr = 'CMD=tempAdd';
    paramStr = paramStr + '&cht010SelectPartner=' + sid;
    paramStr = paramStr + '&cht010SelectKbn= ' + kbn;
    var deferred = new $.Deferred();
    $.ajax({
        async: false,
        url:  "../chat/cht010.do",
        type: "post",
        data: paramStr
    }).done(function( data ) {
      // リアルタイム通信
      if (!data["success"]) {
        try {
            if ($(data).find('[name=errCause]')
                && $(data).find('[name=errCause]').val() == 'GSAttachFileNotExistException') {
                 $('html').html('');
                 $('body').append($(data));
                 return;
            }
        } catch (ee) {
            //正常登録時のjsonをjqueryObjとして扱うとエクセプションになる
        }

        window.opener.document.cht010Form.sendErrorAlert(data);
      }
    }).fail(function(data){
      console.log("接続に失敗しました。");
    });

    return true;
}

function setToken(paramStr) {
    paramStr += '&org.apache.struts.taglib.html.TOKEN='
              + $('input:hidden[name="org.apache.struts.taglib.html.TOKEN"]').val();
              
    return paramStr;
}

function cmn110DropBan() {
    return $('body').find('div').hasClass('ui-widget-overlay');
}