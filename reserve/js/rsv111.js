$(function() {
    $('textarea').each(function() {
        setTextareaAutoResize($(this).get(0));
    });

});


function setDisabled(value) {
      $('.js_adv-everyday').hide();
      $('.js_adv-monthly').hide();
      $('.js_adv-weekday').hide();
      $('.js_adv-date').hide();

    if (document.forms[0].rsv111RsrKbn[1].checked == true) {
       $('.js_adv-weekday').show();
       $('.js_adv-everyday').show();
        document.forms[0].rsv111RsrDweek1.disabled=false;
        document.forms[0].rsv111RsrDweek2.disabled=false;
        document.forms[0].rsv111RsrDweek3.disabled=false;
        document.forms[0].rsv111RsrDweek4.disabled=false;
        document.forms[0].rsv111RsrDweek5.disabled=false;
        document.forms[0].rsv111RsrDweek6.disabled=false;
        document.forms[0].rsv111RsrDweek7.disabled=false;
        document.forms[0].rsv111RsrTranKbn[0].disabled=false;
        document.forms[0].rsv111RsrTranKbn[1].disabled=false;
        document.forms[0].rsv111RsrTranKbn[2].disabled=false;
        document.forms[0].rsv111RsrWeek.disabled=true;
        document.forms[0].rsv111RsrDay.disabled=true;
        document.forms[0].rsv111RsrWeek.value=0;
        document.forms[0].rsv111RsrDay.value=0;
        document.forms[0].rsv111RsrMonthOfYearly.disabled=true;
        document.forms[0].rsv111RsrDayOfYearly.disabled=true;

    } else if (document.forms[0].rsv111RsrKbn[2].checked == true) {
        $('.js_adv-monthly').show();
        $('.js_adv-weekday').show();
        $('.js_adv-everyday').show();
        document.forms[0].rsv111RsrDweek1.disabled=false;
        document.forms[0].rsv111RsrDweek2.disabled=false;
        document.forms[0].rsv111RsrDweek3.disabled=false;
        document.forms[0].rsv111RsrDweek4.disabled=false;
        document.forms[0].rsv111RsrDweek5.disabled=false;
        document.forms[0].rsv111RsrDweek6.disabled=false;
        document.forms[0].rsv111RsrDweek7.disabled=false;
        document.forms[0].rsv111RsrTranKbn[0].disabled=false;
        document.forms[0].rsv111RsrTranKbn[1].disabled=false;
        document.forms[0].rsv111RsrTranKbn[2].disabled=false;
        document.forms[0].rsv111RsrWeek.disabled=false;
        document.forms[0].rsv111RsrDay.disabled=false;
        document.forms[0].rsv111RsrMonthOfYearly.disabled=true;
        document.forms[0].rsv111RsrDayOfYearly.disabled=true;

        if (document.forms[0].rsv111RsrWeek.value==0) {
            document.forms[0].rsv111RsrDweek1.disabled=true;
            document.forms[0].rsv111RsrDweek2.disabled=true;
            document.forms[0].rsv111RsrDweek3.disabled=true;
            document.forms[0].rsv111RsrDweek4.disabled=true;
            document.forms[0].rsv111RsrDweek5.disabled=true;
            document.forms[0].rsv111RsrDweek6.disabled=true;
            document.forms[0].rsv111RsrDweek7.disabled=true;
        } else {
            document.forms[0].rsv111RsrDweek1.disabled=false;
            document.forms[0].rsv111RsrDweek2.disabled=false;
            document.forms[0].rsv111RsrDweek3.disabled=false;
            document.forms[0].rsv111RsrDweek4.disabled=false;
            document.forms[0].rsv111RsrDweek5.disabled=false;
            document.forms[0].rsv111RsrDweek6.disabled=false;
            document.forms[0].rsv111RsrDweek7.disabled=false;
        }
    } else if (document.forms[0].rsv111RsrKbn[3].checked == true) {
        $('.js_adv-date').show();
        $('.js_adv-everyday').show();
        document.forms[0].rsv111RsrDweek1.disabled=true;
        document.forms[0].rsv111RsrDweek2.disabled=true;
        document.forms[0].rsv111RsrDweek3.disabled=true;
        document.forms[0].rsv111RsrDweek4.disabled=true;
        document.forms[0].rsv111RsrDweek5.disabled=true;
        document.forms[0].rsv111RsrDweek6.disabled=true;
        document.forms[0].rsv111RsrDweek7.disabled=true;
        document.forms[0].rsv111RsrWeek.disabled=true;
        document.forms[0].rsv111RsrDay.disabled=true;
        document.forms[0].rsv111RsrTranKbn.disabled=true;
        document.forms[0].rsv111RsrDweek1.checked=false;
        document.forms[0].rsv111RsrDweek2.checked=false;
        document.forms[0].rsv111RsrDweek3.checked=false;
        document.forms[0].rsv111RsrDweek4.checked=false;
        document.forms[0].rsv111RsrDweek5.checked=false;
        document.forms[0].rsv111RsrDweek6.checked=false;
        document.forms[0].rsv111RsrDweek7.checked=false;
        document.forms[0].rsv111RsrWeek.value=0;
        document.forms[0].rsv111RsrDay.value=0;
        document.forms[0].rsv111RsrMonthOfYearly.disabled=false;
        document.forms[0].rsv111RsrDayOfYearly.disabled=false;
        document.forms[0].rsv111RsrTranKbn[0].disabled=false;
        document.forms[0].rsv111RsrTranKbn[1].disabled=false;
        document.forms[0].rsv111RsrTranKbn[2].disabled=false;
    } else {
        document.forms[0].rsv111RsrDweek1.disabled=true;
        document.forms[0].rsv111RsrDweek2.disabled=true;
        document.forms[0].rsv111RsrDweek3.disabled=true;
        document.forms[0].rsv111RsrDweek4.disabled=true;
        document.forms[0].rsv111RsrDweek5.disabled=true;
        document.forms[0].rsv111RsrDweek6.disabled=true;
        document.forms[0].rsv111RsrDweek7.disabled=true;
        document.forms[0].rsv111RsrTranKbn[0].disabled=true;
        document.forms[0].rsv111RsrTranKbn[1].disabled=true;
        document.forms[0].rsv111RsrTranKbn[2].disabled=true;
        document.forms[0].rsv111RsrWeek.disabled=true;
        document.forms[0].rsv111RsrDay.disabled=true;
        document.forms[0].rsv111RsrTranKbn.disabled=true;
        document.forms[0].rsv111RsrDweek1.checked=false;
        document.forms[0].rsv111RsrDweek2.checked=false;
        document.forms[0].rsv111RsrDweek3.checked=false;
        document.forms[0].rsv111RsrDweek4.checked=false;
        document.forms[0].rsv111RsrDweek5.checked=false;
        document.forms[0].rsv111RsrDweek6.checked=false;
        document.forms[0].rsv111RsrDweek7.checked=false;
        document.forms[0].rsv111RsrWeek.value=0;
        document.forms[0].rsv111RsrDay.value=0;
        document.forms[0].rsv111RsrMonthOfYearly.disabled=true;
        document.forms[0].rsv111RsrDayOfYearly.disabled=true;
        document.forms[0].rsv111RsrTranKbn[0].checked=true;
        document.forms[0].rsv111RsrTranKbn[1].checked=false;
        document.forms[0].rsv111RsrTranKbn[2].checked=false;
    }

}

function changeWeekCombo(){
    if (document.forms[0].rsv111RsrKbn[2].checked == true) {
        if (document.forms[0].rsv111RsrWeek.value==0) {
            document.forms[0].rsv111RsrDweek1.disabled=true;
            document.forms[0].rsv111RsrDweek2.disabled=true;
            document.forms[0].rsv111RsrDweek3.disabled=true;
            document.forms[0].rsv111RsrDweek4.disabled=true;
            document.forms[0].rsv111RsrDweek5.disabled=true;
            document.forms[0].rsv111RsrDweek6.disabled=true;
            document.forms[0].rsv111RsrDweek7.disabled=true;
            document.forms[0].rsv111RsrDweek1.checked=false;
            document.forms[0].rsv111RsrDweek2.checked=false;
            document.forms[0].rsv111RsrDweek3.checked=false;
            document.forms[0].rsv111RsrDweek4.checked=false;
            document.forms[0].rsv111RsrDweek5.checked=false;
            document.forms[0].rsv111RsrDweek6.checked=false;
            document.forms[0].rsv111RsrDweek7.checked=false;
        } else {
            document.forms[0].rsv111RsrDweek1.disabled=false;
            document.forms[0].rsv111RsrDweek2.disabled=false;
            document.forms[0].rsv111RsrDweek3.disabled=false;
            document.forms[0].rsv111RsrDweek4.disabled=false;
            document.forms[0].rsv111RsrDweek5.disabled=false;
            document.forms[0].rsv111RsrDweek6.disabled=false;
            document.forms[0].rsv111RsrDweek7.disabled=false;
        }
    }
}

function moveFromDay(elmDate, kbn) {

    systemDate = new Date();
    var year = convYear(systemDate.getFullYear());
    var month = ("0" + (systemDate.getMonth() + 1)).slice(-2);
    var day = ("0" + systemDate.getDate()).slice(-2);

    //「今日」ボタン押下
    if (kbn == 2) {
        $(elmDate).val(year + "/" + month + "/" + day);
        setToDay();
        return;
    }

    //「前日」or 「翌日」ボタン押下
    if (kbn == 1 || kbn == 3) {

        var ymdf = escape($(elmDate).val());
        re = new RegExp(/(\d{4})\/(\d{1,2})\/(\d{1,2})/);
        if (ymdf.match(re)) {

            newDate = new Date($(elmDate).val())

            if (kbn == 1) {
                newDate.setDate(newDate.getDate() - 1);
            } else if (kbn == 3) {
                newDate.setDate(newDate.getDate() + 1);
            }

            var newYear = convYear(newDate.getFullYear());
            var systemYear = convYear(systemDate.getFullYear());

            if (newYear < systemYear - 1 || newYear > systemYear + 3) {
                return;
            } else {
                year = newYear;
                month = ("0" + (newDate.getMonth() + 1)).slice(-2);
                day = ("0" + newDate.getDate()).slice(-2);
                $(elmDate).val(year + "/" + month + "/" + day);
            }

        } else {

            if ($(elmDate).val() == '') {
                $(elmDate).val(year + "/" + month + "/" + day);
            }
        }
    }

    setToDay();
}

function moveToDay(elmDate, kbn) {

    systemDate = new Date();
    var year = convYear(systemDate.getFullYear());
    var month = ("0" + (systemDate.getMonth() + 1)).slice(-2);
    var day = ("0" + systemDate.getDate()).slice(-2);

    //「今日」ボタン押下
    if (kbn == 2) {
        $(elmDate).val(year + "/" + month + "/" + day);
        setFromDay();
        return;
    }

    //「前日」or 「翌日」ボタン押下
    if (kbn == 1 || kbn == 3) {

        var ymdf = escape($(elmDate).val());
        re = new RegExp(/(\d{4})\/(\d{1,2})\/(\d{1,2})/);
        if (ymdf.match(re)) {

            newDate = new Date($(elmDate).val())

            if (kbn == 1) {
                newDate.setDate(newDate.getDate() - 1);
            } else if (kbn == 3) {
                newDate.setDate(newDate.getDate() + 1);
            }

            var newYear = convYear(newDate.getFullYear());
            var systemYear = convYear(systemDate.getFullYear());

            if (newYear < systemYear - 1 || newYear > systemYear + 3) {
                return;
            } else {
                year = newYear;
                month = ("0" + (newDate.getMonth() + 1)).slice(-2);
                day = ("0" + newDate.getDate()).slice(-2);
                $(elmDate).val(year + "/" + month + "/" + day);
            }

        } else {

            if ($(elmDate).val() == '') {
                $(elmDate).val(year + "/" + month + "/" + day);
            }
        }
    }

    setFromDay();
}

function convYear(yyyy) {
  if(yyyy<1900) {
    yyyy=1900+yyyy;
  }
  return yyyy;
}

function showOrHide(){
var rsv111HeaderDspFlg = document.forms[0].rsv111HeaderDspFlg.value;
  if (rsv111HeaderDspFlg == '0') {
    showText();
  } else {
    hideText();
  }
}

function showText(){
    $('.js_longHeader').show();
    $('.js_shortHeader').hide();
    document.forms[0].rsv111HeaderDspFlg.value='0';
}

function hideText(){
    $('.js_longHeader').hide();
    $('.js_shortHeader').show();
    document.forms[0].rsv111HeaderDspFlg.value='1';
}

function selectUsersList() {

    var flg = true;
   if (document.forms[0].rsv111SelectUsersKbn.checked) {
       flg = true;
   } else {
       flg = false;
   }
   oElements = document.getElementsByName("users_l");
   var defUserAry = document.forms[0].users_l.options;
   var defLength = defUserAry.length;
   for (i = defLength - 1; i >= 0; i--) {
       if (defUserAry[i].value != -1) {
           defUserAry[i].selected = flg;
       }
   }
}


//午前
function setAmTime() {
  var frHour = $(':hidden[name="rsv110AmFrHour"]').val();
  var frMinute = $(':hidden[name="rsv110AmFrMin"]').val();
  var toHour = $(':hidden[name="rsv110AmToHour"]').val();
  var toMinute = $(':hidden[name="rsv110AmToMin"]').val();
  var frTime = String(frHour).padStart(2, '0') + ":" + String(frMinute).padStart(2, '0');
  var toTime = String(toHour).padStart(2, '0') + ":" + String(toMinute).padStart(2, '0');

  $("input[name='rsv111RsrTimeFr']").val(frTime);
  $("input[name='rsv111RsrTimeTo']").val(toTime);
}

//午後
function setPmTime() {
  var frHour = $(':hidden[name="rsv110PmFrHour"]').val();
  var frMinute = $(':hidden[name="rsv110PmFrMin"]').val();
  var toHour = $(':hidden[name="rsv110PmToHour"]').val();
  var toMinute = $(':hidden[name="rsv110PmToMin"]').val();
  var frTime = String(frHour).padStart(2, '0') + ":" + String(frMinute).padStart(2, '0');
  var toTime = String(toHour).padStart(2, '0') + ":" + String(toMinute).padStart(2, '0');

  $("input[name='rsv111RsrTimeFr']").val(frTime);
  $("input[name='rsv111RsrTimeTo']").val(toTime);
}

//終日
function setAllTime() {
  var frHour = $(':hidden[name="rsv110AllDayFrHour"]').val();
  var frMinute = $(':hidden[name="rsv110AllDayFrMin"]').val();
  var toHour = $(':hidden[name="rsv110AllDayToHour"]').val();
  var toMinute = $(':hidden[name="rsv110AllDayToMin"]').val();
  var frTime = String(frHour).padStart(2, '0') + ":" + String(frMinute).padStart(2, '0');
  var toTime = String(toHour).padStart(2, '0') + ":" + String(toMinute).padStart(2, '0');

  $("input[name='rsv111RsrTimeFr']").val(frTime);
  $("input[name='rsv111RsrTimeTo']").val(toTime);
}

function setFromDay() {

    var frDate = document.forms[0].rsv111RsrDateFr.value;
    var toDate = document.forms[0].rsv111RsrDateTo.value;
    var frTime = document.forms[0].rsv111RsrTimeFr.value;
    var toTime = document.forms[0].rsv111RsrTimeTo.value;
    var daySameFlg = false;

    if (frDate > toDate) {
        document.forms[0].rsv111RsrDateFr.value = toDate;
    }
    if (frDate == frDate) {
        daySameFlg = true;
    }

    if (daySameFlg) {
        if (frTime > toTime) {
            document.forms[0].rsv111RsrTimeFr.value = toTime;
        }
    }
}

function setToDay() {

    var frDate = document.forms[0].rsv111RsrDateFr.value;
    var toDate = document.forms[0].rsv111RsrDateTo.value;
    var frTime = document.forms[0].rsv111RsrTimeFr.value;
    var toTime = document.forms[0].rsv111RsrTimeTo.value;
    var daySameFlg = false;

    if (frDate > toDate) {
        document.forms[0].rsv111RsrDateTo.value = frDate;
    }
    if (frDate == toDate) {
        daySameFlg = true;
    }

    if (daySameFlg) {
        if (frTime > toTime) {
            document.forms[0].rsv111RsrTimeTo.value = frTime;
        }
    }
}

function setFromTime() {
    var frTime = document.forms[0].rsv111RsrTimeFr.value;
    var toTime = document.forms[0].rsv111RsrTimeTo.value;

    if (frTime > toTime) {
        document.forms[0].rsv111RsrTimeFr.value = toTime;
    }
}

function setToTime() {
    var frTime = document.forms[0].rsv111RsrTimeFr.value;
    var toTime = document.forms[0].rsv111RsrTimeTo.value;

    if (frTime > toTime) {
        document.forms[0].rsv111RsrTimeTo.value = frTime;
     }
}

function selectPubUsersList() {
   var flg = true;
   if (document.forms[0].rsv111SelectPubUsersKbn.checked) {
       flg = true;
   } else {
       flg = false;
   }
   oElements = document.getElementById("users_r");
   var defUserAry = document.forms[0].rsv111RightUsrGrpSid.options;
   var defLength = defUserAry.length;
   for (i = defLength - 1; i >= 0; i--) {
       if (defUserAry[i].value != -1) {
           defUserAry[i].selected = flg;
       }
   }
}

function setDateParam() {
    setYmdParam($("#selDatefr"),
                $("input[name='rsv111RsrDateYearFr']"),
                $("input[name='rsv111RsrDateMonthFr']"),
                $("input[name='rsv111RsrDateDayFr']"));
    setYmdParam($("#selDateto"),
                $("input[name='rsv111RsrDateYearTo']"),
                $("input[name='rsv111RsrDateMonthTo']"),
                $("input[name='rsv111RsrDateDayTo']"));
    setHmParam($("#fr_clock"),
               $("input[name='rsv111RsrTimeHourFr']"),
               $("input[name='rsv111RsrTimeMinuteFr']"));
    setHmParam($("#to_clock"),
               $("input[name='rsv111RsrTimeHourTo']"),
               $("input[name='rsv111RsrTimeMinuteTo']"));
}

$(function() {
    $(document).on("click", ".js_public", function(){
      var val = $(this).val();
      if (val == 3) {
        $(".js_selectUsrArea").show();
      } else {
        $(".js_selectUsrArea").hide();
      }
    });

    //初期表示
    if ($('input[name="rsv111RsrPublic"]:checked').val()) {
      var value = $('input[name="rsv111RsrPublic"]:checked').val();
      if (value == 3) {
        $(".js_selectUsrArea").show();
      } else {
        $(".js_selectUsrArea").hide();
      }
    }
});


function rsv111SaveSvUser() {
  let hiddenSvUsr = '';
  if ($('input[name=rsv111ScdReflection]:checked').val() == 1) {
    let svUserList = document.getElementsByName('rsv111SvUsers');
    for (idx = 0; idx < svUserList.length; idx++) {
        hiddenSvUsr += '<input type="hidden" name="rsv111SvUsers" value="' + svUserList[idx].value + '" />';
    }
  }
  $('#rsv111SvUserArea').html(hiddenSvUsr);
}