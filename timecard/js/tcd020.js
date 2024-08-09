function clearValue(hour, min){
    document.getElementsByName(hour).item(0).value = '-1';
    document.getElementsByName(min).item(0).value = '-1';
}

function init() {
    holComboChange();
}

function holComboChange(){
    var holKbn = document.forms[0].tcd020HolKbn.value;
    //休日内容を登録できる休日のSIDをカンマ区切りで持つ
    var str = document.forms[0].tcd020HolidayContentKbn.value;
    var holArray = str.split(',');
    var holValueDisp = 0;
    var holDaysDisp = 0;
    if (holKbn != 0){
    	holDaysDisp = 1;
    	for (i = 0; i < holArray.length; i++) {
        	//休日内容表示
           if (holKbn == holArray[i]) {
        	   holValueDisp = 1;
               break;
           }
        }
    }
    $('#js_holValue').removeClass('display_none');
    if (holValueDisp == 0) {
    	$('#js_holValue').addClass('display_none');
    }

    //--- 追加 2024/08/08 システム開発Gr 塩見

    //休日区分を取得
    var selectElement = document.getElementsByName("tcd020HolKbn")[0];
    var selectedOption = selectElement.options[selectElement.selectedIndex];
    var selectedText = selectedOption.text;

    //休日日数を格納する変数を初期化
    var holdays = "0.0";

    //始業・終業時間をクリアする処理の制御用フラグ
    var timeclearFlg = false;
    
    //休日日数を格納
    switch (selectedText){
    
        case "午前半休":
            holdays = "0.5";
            break;
        case "午後半休":
            holdays = "0.5";
            break;
        case "振休":
            holdays = "";
            break;
        case "指定なし":
            holdays = "";
            break;    
        default:
            holdays = "1.0";
            timeclearFlg = true;
    }
    
    //休日日数を更新
    document.getElementsByName("tcd020HolDays").item(0).value = holdays;

    //始業・終業時間をクリア
    if (timeclearFlg){
        clearValue('tcd020InHour', 'tcd020InMinute'); //始業時間
        clearValue('tcd020OutHour', 'tcd020OutMinute'); //終業時間
    }

    //---

    setStyle('tcd020HolDays', holDaysDisp);
}

function setStyle(fname, flg){

    chkAry = document.getElementsByName(fname);

    if (flg == 1) {
        for(i = 0; i < chkAry.length; i++) {
            chkAry[i].disabled=false;
        }
    } else if (flg == 0) {
        for(i = 0; i < chkAry.length; i++) {
            chkAry[i].disabled=true;
            if (chkAry[i].type == 'text' || chkAry[i].type == 'textarea') {
                chkAry[i].value="";
            }
        }
    }
}

