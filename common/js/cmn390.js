$(function() {
  showLengthId($('#inputstr')[0], $('#inputstr').next().text(), 'inputlength');
  $('#inputstr').on('keyup', function() {
    showLengthId($('#inputstr')[0], $('#inputstr').next().text(), 'inputlength');
  });
  function changeDspAddrSeigenUseFlg() {
    var selVal = $('.js_ipAddrSeigenUseFlg:checked').val();
    if (selVal == 0) {
      $('.js_seigenSubParamRow').addClass('display_none');
    } else {
      $('.js_seigenSubParamRow').removeClass('display_none');
    }

  }
  changeDspAddrSeigenUseFlg();
  //IPアドレス制限設定変更
  $('.js_ipAddrSeigenUseFlg').on('change', function() {
    changeDspAddrSeigenUseFlg();
  });
});
