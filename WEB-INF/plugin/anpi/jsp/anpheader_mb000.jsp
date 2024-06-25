<%@ page import="jp.co.sjts.util.http.BrowserUtil" %>

<%
	boolean clientDoCoMo = false;
    boolean clientKDDI = false;
    boolean clientSoftBank = false;
    boolean clientAndroid = false;
    String ua = request.getHeader(BrowserUtil.USER_AGENT_KEYWORD);
    String thisForm = "";

    if (BrowserUtil.isDocomo(request)) {
    	clientDoCoMo = true;
    } else if ((BrowserUtil.isAuWap20(request) || ua.indexOf("BREW-Applet") > 0)) {
    	clientKDDI = true;
    } else if ((BrowserUtil.isVodafone(request) || BrowserUtil.isSoftBank(request))) {
    	clientSoftBank = true;
    } else if (BrowserUtil.isAndroid(request)) {
        clientAndroid = true;
    }
	
	String emojiNew     = "";
	String emojiKira    = "";
	String emojiNum1    = "";
	String emojiNum2    = "";
    String emojiNum3    = "";
	String emojiNum4    = "";
    String emojiNum5    = "";
	String emojiNum6    = "";
	String emojiNum7    = "";
	String emojiNum8    = "";
    String emojiNum9    = "";
	String emojiNum0    = "";
	String emojiHito    = "";
	String emojiHito2   = "";
    String emojiMoney   = "";
	String emojiSmile   = "";
	String emojiLight   = "";
	String emojiBook    = "";
	String emojiMemo    = "";
	String emojiMidashi = "";
    String emojiTime    = "";
    String emojiBack    = "";
    String emojiOk      = "";
    String emojiNg      = "";
    String emojiTokei   = "";
    String emojiPen     = "";
    String emojiPen2    = "";
    String emojiLook    = "";
    String emojiRsv     = "";
    String emojiMail    = "";
    String emojiPhone   = "";
    String emojiImp     = "";
    String emojiWorry   = "";
    String emojiAnger   = "";
    String emojiBeer    = "";
    String emojiSad     = "";
    String emojiHeart   = "";
    String emojiTired   = "";
    String emojiRingi   = "";
    String emojiCheck   = "";
    String textTagStart = "";
    String textTagEnd   = "";
    String agent        = "";
	
	if(clientDoCoMo){
		response.setContentType("text/html;charset=Shift_JIS");
		request.setCharacterEncoding("Shift_JIS");
		emojiNew     = "&#xE6DD;";
		emojiNum1    = "&#xE6E2;";
		emojiNum2    = "&#xE6E3;";
	    emojiNum3    = "&#xE6E4;";
		emojiNum4    = "&#xE6E5;";
        emojiNum5    = "&#xE6E6;";
		emojiNum6    = "&#xE6E7;";
	    emojiNum7    = "&#xE6E8;";
	    emojiNum8    = "&#xE6E9;";
	    emojiNum9    = "&#xE6EA;";
	    emojiNum0    = "&#xE6EB;";
		emojiHito    = "&#xE733;";
		emojiHito2   = "&#xE734;";
		emojiMoney   = "&#xE715;";
		emojiSmile   = "&#xE6F0;";
		emojiLight   = "&#xE6FB;";
		emojiBook    = "&#xE683;";
		emojiMemo    = "&#xE689;";
		emojiMidashi = "&#xE719;";
		emojiTime    = "&#xE71C;";
		emojiBack    = "&#xE6DA;";
		emojiOk      = "&#xE70B;";
		emojiNg      = "&#xE72F;";
		emojiTokei   = "&#xE6BA;";
		emojiPen     = "&#xE719;";
		emojiPen2    = "&#xE6AE;";
		emojiLook    = "&#xE691;";
		emojiRsv     = "&#xE714;";
		emojiMail    = "&#xE6D3;";
		emojiPhone   = "&#xE687;";
		emojiImp     = "&#xE702;";
		emojiWorry   = "&#xE6F3;";
		emojiAnger   = "&#xE724;";
		emojiSad     = "&#xE72E;";
		emojiBeer    = "&#xE672;";
		emojiHeart   = "&#xE6EC;";
		emojiTired   = "&#xE6F4;";
		emojiRingi   = "&#xE730;";
		emojiCheck   = "&#xE6DE;";
		textTagStart = "<span style=\"font-size:smaller\">";
		textTagEnd   = "</span>";
		agent        = "docomo";
		
	}else if(clientKDDI){
		response.setContentType("text/html;charset=Shift_JIS");
		request.setCharacterEncoding("Shift_JIS");
		
		emojiNew     = "&#xE5B5;";
		emojiNum1    = "&#xE522;";
		emojiNum2    = "&#xE523;";
	    emojiNum3    = "&#xE524;";
		emojiNum4    = "&#xE525;";
        emojiNum5    = "&#xE526;";
		emojiNum6    = "&#xE527;";
	    emojiNum7    = "&#xE528;";
	    emojiNum8    = "&#xE529;";
	    emojiNum9    = "&#xE52A;";
	    emojiNum0    = "&#xE5AC;";
		emojiHito    = "&#xEADA;";
		emojiHito2   = "&#xE471;";
		emojiMoney   = "&#xE4C7;";
		emojiSmile   = "&#xE471;";
		emojiLight   = "&#xE476;";
		emojiBook    = "&#xE49F;";
		emojiMemo    = "&#xE561;";
		emojiMidashi = "&#xE54A;";
		emojiTime    = "&#xE47C;";
		emojiBack    = "&#xE55D;";
		emojiOk      = "&#xE5AD;";
		emojiNg      = "&#xE551;";
		emojiTokei   = "&#xE594;";
		emojiPen     = "&#xE4A1;";
		emojiPen2    = "&#xEB03;";
		emojiLook    = "&#xE5A4;";
		emojiRsv     = "&#xE4AB;";
		emojiMail    = "&#xE521;";
		emojiPhone   = "&#xE596;";
		emojiImp     = "&#xE482;";
		emojiWorry   = "&#xEAC3;";
		emojiAnger   = "&#xEB5D;";
		emojiSad     = "&#xEB69;";
		emojiBeer    = "&#xE4C3;";
		emojiHeart   = "&#xE595;";
		emojiTired   = "&#xE5AE;";
		emojiRingi   = "&#xE4A0;";
		emojiCheck   = "&#xEB2C;";
		textTagStart = "<font size=\"1\">";
		textTagEnd   = "</font>";
		agent        = "au";
		ua = request.getHeader("X-Up-Subno");
		
		
		
	}else if(clientSoftBank){
		response.setContentType("text/html;charset=Shift_JIS");
		request.setCharacterEncoding("Shift_JIS");
		
		emojiNew     = "&#xE212;";
		emojiNum1    = "&#xE21C;";
		emojiNum2    = "&#xE21D;";
		emojiNum3    = "&#xE21E;";
	    emojiNum4    = "&#xE21F;";
        emojiNum5    = "&#xE220;";
		emojiNum6    = "&#xE221;";
	    emojiNum7    = "&#xE222;";
	    emojiNum8    = "&#xE223;";
	    emojiNum9    = "&#xE224;";
	    emojiNum0    = "&#xE225;";
		emojiHito    = "&#xE428;";
		emojiHito2   = "&#xE115;";
		emojiMoney   = "&#xE12F;";
		emojiSmile   = "&#xE057;";
		emojiLight   = "&#xE10F;";
		emojiBook    = "&#xE148;";
		emojiMemo    = "&#xE301;";
		emojiMidashi = "&#xE219;";
		emojiTime    = "&#xE026;";
		emojiBack    = "&#xE235;";
		emojiOk      = "&#xE24D;";
		emojiNg      = "&#xE333;";
		emojiTokei   = "&#xE02D;";
		emojiPen     = "&#xE301;";
		emojiPen2    = "&#xE301;";
		emojiLook    = "&#xE419;";
		emojiRsv     = "&#xE036;";
		emojiMail    = "&#xE103;";
		emojiPhone   = "&#xE009;";
		emojiImp     = "&#xE021;";
		emojiWorry   = "&#xE407;";
		emojiAnger   = "&#xE416;";
		emojiSad     = "&#xE413;";
		emojiBeer    = "&#xE047;";
		emojiHeart   = "&#xE022;";
		emojiTired   = "&#xE406;";
		emojiRingi   = "&#xE301;";
		emojiCheck   = "&#xE12B;";
		textTagStart = "<font style=\"font-size:medium\">";
		textTagEnd   = "</font>";
		agent        = "softbank";
		ua           = request.getHeader("x-jphone-uid");
	} else {
       response.setContentType("text/html;charset=UTF-8");
	}
%>
