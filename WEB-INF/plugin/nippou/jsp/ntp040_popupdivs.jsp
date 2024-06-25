<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<div id="footdiv"></div>

<div id="dialog" title="削除確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        削除してもよろしいですか？
      </td>
    </tr>
  </table>
</div>

<div id="dialogNtpDel" title="削除確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        削除してもよろしいですか？
      </td>
    </tr>
  </table>
</div>

<div id="dialogCommentDel" title="削除確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        削除してもよろしいですか？
      </td>
    </tr>
  </table>
</div>

<div id="dialogEditOk" title="編集確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        編集してもよろしいですか？
      </td>
    </tr>
  </table>
</div>

<div id="dspMoveOk" title="移動確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        報告日付が変更されました。<br>変更した日付へ移動しますか？
      </td>
    </tr>
  </table>
</div>

<div id="commentError" title="入力エラー" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        コメントを入力してください。
      </td>
    </tr>
  </table>
</div>

<div id="commentLengthError" title="入力エラー" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        コメントは1000文字以内で入力してください。
      </td>
    </tr>
  </table>
</div>

<div id="goodError" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        この日報には「いいね!」しています。
      </td>
    </tr>
  </table>
</div>

<div id="goodDialog" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        この日報に「いいね!」しますか？
      </td>
    </tr>
  </table>
</div>

<div id="goodDialogComp" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        「いいね!」しました。
      </td>
    </tr>
  </table>
</div>

<div id="goodZero" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        この日報に「いいね!」しているユーザはいません。
      </td>
    </tr>
  </table>
</div>

<div id="dialog_error" class="error_dialog" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14" id="error_msg">
      </td>
    </tr>
  </table>
</div>

<div id="goodUsrInfArea" class="goodAddUsrArea bgC_body borC_light">
  <table class="w100 h100">
    <tr>
      <td class="w100 txt_r bgC_header2 hp25">
        <button id="goodAddUsrClose" type="button" class="baseBtn" name="btn_ktool">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png">
          閉じる
        </button>
      </td>
    </tr>
    <tr>
      <td class="w100 h100">
        <div id="goodUsrInfArea2" class="ofy_s hp220">
        </div>
      </td>
    </tr>
  </table>
</div>

<div id="dialogEditOk" title="変更確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        変更してもよろしいですか？
      </td>
    </tr>
  </table>
</div>
<div id="dialogAddOk" title="登録確認" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        登録してもよろしいですか？
      </td>
    </tr>
  </table>
</div>
<div id="notAddOk" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        未登録の日報を破棄してもよろしいですか？
      </td>
    </tr>
  </table>
</div>
<div id="notEditOk" title="" style="display:none">
  <table class="w100 hp75">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl10 fs_14">
        編集中のデータを破棄してもよろしいですか？
      </td>
    </tr>
  </table>
</div>
<div id="adrHistoryPop" title="選択アドレス履歴" style="display:none;">
  <div id="adrHistoryArea">
  </div>
</div>
<div id="ankenHistoryPop" title="選択案件履歴" style="display:none;">
  <div id="ankenHistoryArea">
  </div>
</div>


