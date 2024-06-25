var cmn380SubWindow;

var previewWinWidth = 900,
       previewWinHeight = 900;

function openPreviewWindow(url, extension) {
    var winx = getCenterX(previewWinWidth);
    var winy = getCenterY(previewWinHeight);

    var encodeUrl = encodeURIComponent(url);
    var encodeExtension = encodeURIComponent(extension);

    var url = '../common/cmn380.do'
            + '?cmn380PreviewURL=' + encodeUrl
            + '&cmn380PreviewFileExtension=' + encodeExtension;
    var newWinOpt =
       'width=' + previewWinWidth + ','
     + 'height=' + previewWinHeight + ','
     + 'resizable=yes, toolbar=no, resizable=no,'
     + 'left=' + winx + ','
     + 'top=' + winy + ','
     + 'scrollbars=yes';
    cmn380SubWindow = window.open(url, 'thissite', newWinOpt);

}

function getCenterX(winWidth) {
  var x = (screen.width - winWidth) / 2;
  return x;
}

function getCenterY(winHeight) {
  var y = (screen.height - winHeight) / 2;
  return y;
}

function closePreviewWindow() {
    if(cmn380SubWindow != null){
        cmn380SubWindow.close();
    }
}

function cmn380LoadFile() {
  const cmn380ImageArea = document.getElementById('imgPreview');
  new Viewer(cmn380ImageArea, {
    navbar: false,
    inline: true,
    title: false,
    toolbar: {
      zoomIn: 1,
      zoomOut: 1,
      reset: 1,
      rotateLeft: 1,
      rotateRight: 1,
      flipHorizontal: 1,
      flipVertical: 1
    }
  });

}

function existPreviewWindow() {
  if (cmn380SubWindow != undefined) {
      return !cmn380SubWindow.closed;
  }
  return false;
}

$(function(){

  if (!$('body').attr('onunload')
  || $('body').attr('onunload').indexOf('closePreviewWindow()') < 0) {

    if ($(window).on) {
      $(window).on("beforeunload", function(){
        closePreviewWindow();
      });
    } else if ($(window).live) {
      $(window).unload(function(){
        closePreviewWindow();
      });
    }
  }
});