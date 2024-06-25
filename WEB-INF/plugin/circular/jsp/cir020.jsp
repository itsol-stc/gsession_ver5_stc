<%@page import="org.apache.commons.codec.language.Nysiis"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib uri="/WEB-INF/ctag-attachmentFile.tld" prefix="attachmentFile" %>

<%@page import="jp.groupsession.v2.cir.GSConstCircular"%>
<%@page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstCommon" %>
<!DOCTYPE html>

<%
  String maxLengthMemo = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MAX_LENGTH_MEMO);
%>
<%
  String jusin = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_JUSIN);
%>
<%
  String main = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_JUSIN_MAIN);
%>
<%
  String gomi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_GOMI);
%>

<gsmsg:define id="syainno" msgkey="cmn.employee.staff.number" />
<gsmsg:define id="simei" msgkey="cmn.name" />
<gsmsg:define id="post" msgkey="cmn.post" />
<gsmsg:define id="kakunin" msgkey="cmn.check" />
<gsmsg:define id="memo" msgkey="cir.11" />
<gsmsg:define id="temp" msgkey="cmn.attach.file" />
<gsmsg:define id="koushin" msgkey="cmn.last.modified" />

<%
  int[] sortKeyList = new int[] {
      jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_SNO,
      jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_NAME,
      jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_POST,
      jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_KAKU,
      jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_MEMO,
      -1,
      jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_SAISYU
  };
  String[] title_width = new String[] { "w5", "w10", "w10", "w10", "w40", "w20", "w5" };
  String[] titleList = new String[] {
      syainno,
      simei,
      post,
      kakunin,
      memo,
      temp,
      koushin
  };
  int order_desc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC;
  int order_asc = jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC;
%>

<logic:empty name="cir020Form" property="cir060dspId" scope="request">
  <bean:define id="cmdMode" name="cir020Form" property="cir010cmdMode" />
</logic:empty>
<logic:notEmpty name="cir020Form" property="cir060dspId" scope="request">
  <bean:define id="cmdMode" name="cir020Form" property="cir060svSyubetsu" />
</logic:notEmpty>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>GROUPSESSION <gsmsg:write key="cir.cir020.1" /></title>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../circular/js/cir020.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/count.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/jquery.selection-min.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/selectionSearch.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>
<script src="../common/js/attachmentFile.js?<%= GSConst.VERSION_PARAM %>"></script>

<link rel=stylesheet href='../circular/css/circular.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<logic:notEmpty name="cir020Form" property="cir010AccountTheme" scope="request">
  <bean:define id="selectThemePath" name="cir020Form" property="cir010AccountTheme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="cir020Form" property="cir010AccountTheme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>
</head>

<body onload="">

  <html:form action="/circular/cir020">

    <bean:define id="dspMdl" name="cir020Form" property="cir020dspMdl" />
    <bean:define id="orderKey" name="cir020Form" property="cir030orderKey" />
    <bean:define id="sortKbn" name="cir020Form" property="cir030sortKey" />
    <%
      int iOrderKey = ((Integer) orderKey).intValue();
    %>
    <%
      int iSortKbn = ((Integer) sortKbn).intValue();
    %>

    <logic:equal name="cmdMode" value="<%=main%>">
      <input type="hidden" name="helpPrm" value="<%=jusin%>">
    </logic:equal>
    <logic:notEqual name="cmdMode" value="<%=main%>">
      <input type="hidden" name="helpPrm" value="<bean:write name="cmdMode" />">
    </logic:notEqual>

    <input type="hidden" name="CMD" value="conf">
    <input type="hidden" name="cmdMode" value="<bean:write name="cmdMode" />">
    <input type="hidden" name="cir020cvwConf" value="<bean:write name="dspMdl" property="cvwConf" />">
    <input type="hidden" name="cir020downLoadFlg" value="">
    <input type="hidden" name="cir020memoEdit" value="<bean:write name="cir020Form" property="memoFlg" />">
    <html:hidden property="cirViewAccount" />
    <html:hidden property="cirAccountMode" />
    <html:hidden property="cirAccountSid" />
    <html:hidden property="cir020binSid" />
    <html:hidden property="cir020cacSid" />
    <html:hidden property="cir010cmdMode" />
    <html:hidden property="cir010orderKey" />
    <html:hidden property="cir010sortKey" />
    <html:hidden property="cir010pageNum1" />
    <html:hidden property="cir010pageNum2" />
    <html:hidden property="cir010selectInfSid" />
    <html:hidden property="cir010SelectLabelSid" />
    <html:hidden property="mikakkuCount" />
    <html:hidden property="schWeekDate" />
    <html:hidden property="schDailyDate" />
    <html:hidden property="cir030orderKey" />
    <html:hidden property="cir030sortKey" />
    <html:hidden property="cirDelFlg" />

    <html:hidden property="cir060searchFlg" />
    <html:hidden property="cir010svSearchWord" />
    <html:hidden property="cir060svSyubetsu" />
    <html:hidden property="cir060svGroupSid" />
    <html:hidden property="cir060svUserSid" />
    <html:hidden property="cir060svWordKbn" />
    <html:hidden property="cir060svSort1" />
    <html:hidden property="cir060svOrder1" />
    <html:hidden property="cir060svSort2" />
    <html:hidden property="cir060svOrder2" />
    <html:hidden property="cir010searchWord" />
    <html:hidden property="cir060syubetsu" />
    <html:hidden property="cir060groupSid" />
    <html:hidden property="cir060userSid" />
    <html:hidden property="cir060wordKbn" />
    <html:hidden property="cir060sort1" />
    <html:hidden property="cir060order1" />
    <html:hidden property="cir060sort2" />
    <html:hidden property="cir060order2" />
    <html:hidden property="cir060pageNum1" />
    <html:hidden property="cir060pageNum2" />
    <html:hidden property="cir060dspId" />

    <logic:notEmpty name="cir020Form" property="cir060selUserSid" scope="request">
      <logic:iterate id="item" name="cir020Form" property="cir060selUserSid" scope="request">
        <input type="hidden" name="cir060selUserSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="cir020Form" property="cir060svSelUserSid" scope="request">
      <logic:iterate id="item" name="cir020Form" property="cir060svSelUserSid" scope="request">
        <input type="hidden" name="cir060svSelUserSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="cir020Form" property="cir060searchTarget" scope="request">
      <logic:iterate id="item" name="cir020Form" property="cir060searchTarget" scope="request">
        <input type="hidden" name="cir060searchTarget" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="cir020Form" property="cir060svSearchTarget" scope="request">
      <logic:iterate id="item" name="cir020Form" property="cir060svSearchTarget" scope="request">
        <input type="hidden" name="cir060svSearchTarget" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../circular/images/classic/header_circular.png" alt="<gsmsg:write key="cir.5" />">
          <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_circular_50.png" alt="<gsmsg:write key="cir.5" />">
        </li>
        <li>
          <gsmsg:write key="cir.5" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cmn.check" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <gsmsg:write key="cmn.pdf" />
            </button>
            <logic:equal name="cir020Form" property="cir020PrevBound" value="false">
              <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" disabled>
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l_3_off.png" alt="<gsmsg:write key="cmn.previous" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_left fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.previous" />
              </button>
            </logic:equal>
            <logic:equal name="cir020Form" property="cir020PrevBound" value="true">
              <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" onClick="buttonPush('prev020');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l_18.png" alt="<gsmsg:write key="cmn.previous" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_left fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.previous" />
              </button>
            </logic:equal>

            <logic:equal name="cir020Form" property="cir020NextBound" value="false">
              <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" disabled>
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r_3_off.png" alt="<gsmsg:write key="cmn.next" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_right fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.next" />
              </button>
            </logic:equal>
            <logic:equal name="cir020Form" property="cir020NextBound" value="true">
              <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" onClick="buttonPush('next020');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r_18.png" alt="<gsmsg:write key="cmn.next" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_right fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.next" />
              </button>
            </logic:equal>

            <logic:equal name="cmdMode" value="<%=gomi%>">
              <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                <gsmsg:write key="cmn.delete" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.undo" />" class="baseBtn" onClick="buttonPush('comeback');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_return.png" alt="<gsmsg:write key="cmn.undo" />">
                <gsmsg:write key="cmn.undo" />
              </button>
              <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                <gsmsg:write key="cmn.back" />
              </button>
            </logic:equal>

            <logic:notEqual name="cmdMode" value="<%=gomi%>">
              <logic:equal name="dspMdl" property="cvwConf" value="<%=String.valueOf(GSConstCircular.CONF_UNOPEN)%>">
                <button value="<gsmsg:write key="cmn.check" />" class="baseBtn" onClick="confClick();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.check" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.check" />">
                  <gsmsg:write key="cmn.check" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:equal>

              <logic:notEqual name="dspMdl" property="cvwConf" value="<%=String.valueOf(GSConstCircular.CONF_UNOPEN)%>">
                <logic:equal name="cir020Form" property="memoFlg" value="0">
                  <button value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="memoEdit();">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                    <gsmsg:write key="cmn.edit" />
                  </button>
                </logic:equal>
                <logic:equal name="cir020Form" property="memoFlg" value="1">
                </logic:equal>
                <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
                  <gsmsg:write key="cmn.delete" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
                  <gsmsg:write key="cmn.back" />
                </button>
              </logic:notEqual>
            </logic:notEqual>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper">

      <!-- エラーメッセージ -->
      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <logic:equal name="cmdMode" value="<%=gomi%>">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="cir.cir020.3" />
              </span>
            </th>
            <td class="txt_l w100">
              <span>
                <bean:write name="cir020Form" property="kakuninStr" />
              </span>
            </td>
          </tr>
        </logic:equal>

        <tr>
          <th class="no_w">
            <gsmsg:write key="cmn.title" />
          </th>
          <td class="txt_l w100">
            <span>
              <bean:write name="dspMdl" property="cifTitle" />
            </span>
          </td>
        </tr>
        <!-- 受信アカウント -->
        <tr>
          <th class="no_w">
            <gsmsg:write key="cmn.receive" /><gsmsg:write key="wml.102" />
          </th>
          <bean:define id="mukoUserClass" value="" />
          <logic:equal name="cir020Form" property="cirViewAccountUko" value="1">
            <bean:define id="mukoUserClass">mukoUser</bean:define>
          </logic:equal>

          <td class="txt_l w100">
            <span class="<%=mukoUserClass%>">
              <bean:write name="cir020Form" property="cirViewAccountName" />
            </span>
          </td>
        </tr>
        <!-- 発信者 -->
        <tr>
          <th>
            <gsmsg:write key="cir.2" />
          </th>
          <td class="txt_l">
            <logic:equal name="dspMdl" property="cacJkbn" value="<%=String.valueOf(GSConstCircular.CAC_JKBN_NORMAL)%>">
              <bean:define id="mukoUserClass" value="" />
              <logic:equal name="dspMdl" property="usrUkoFlg" value="1">
                <bean:define id="mukoUserClass">mukoUser</bean:define>
              </logic:equal>
              <span class="<%=mukoUserClass%>">
                <bean:write name="dspMdl" property="cacName" />
              </span>
            </logic:equal>
            <logic:notEqual name="dspMdl" property="cacJkbn" value="<%=String.valueOf(GSConstCircular.CAC_JKBN_NORMAL)%>">
              <del>
                <bean:write name="dspMdl" property="cacName" />
              </del>
            </logic:notEqual>
          </td>
        </tr>
        <!-- 日時 -->
        <tr>
          <th>
            <gsmsg:write key="cir.13" />
          </th>
          <td class="txt_l">
            <bean:write name="dspMdl" property="dspCifAdate" />
          </td>
        </tr>
        <!-- 内容 -->
        <tr>
          <th>
            <gsmsg:write key="cmn.content" />
          </th>
          <td class="txt_l">
            <logic:equal name="dspMdl" property="cifEkbn" value="1">
              <bean:define id="edate" name="dspMdl" property="dspCifEditDate" type="java.lang.String" />
              <div class="cl_fontWarn">
                ※<gsmsg:write key="bbs.bbs080.4" arg0="<%=edate%>" />
              </div>
            </logic:equal>
            <span>
              <bean:write name="dspMdl" property="cifValue" filter="false" />
            </span>
          </td>
        </tr>

        <tr>
          <!-- 発信時の添付 -->
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.attached" />
            </span>
          </th>
          <td class="txt_l">
            <logic:notEmpty name="cir020Form" property="cir020fileList" scope="request">
              <logic:iterate id="fileMdl" name="cir020Form" property="cir020fileList" scope="request">

                <logic:notEmpty name="fileMdl" property="binFileExtension">
                  <bean:define id="fext" name="fileMdl" property="binFileExtension" type="java.lang.String" />
                  <%
                    String dext = ((String) pageContext.getAttribute("fext", PageContext.PAGE_SCOPE));
                              if (dext != null) {
                                dext = dext.toLowerCase();
                                if (jp.groupsession.v2.cmn.biz.CommonBiz.isViewFile(dext)) {
                  %>
                  <div><img class="wp500" src="../circular/cir020.do?CMD=tempview&cir020binSid=<bean:write name="fileMdl" property="binSid" />&cir010selectInfSid=<bean:write name="cir020Form" property="cir010selectInfSid" />&cirViewAccount=<bean:write name="cir020Form" property="cirViewAccount" />" name="pictImage<bean:write name="fileMdl" property="binSid" />" onload="initImageView('pictImage<bean:write name="fileMdl" property="binSid" />');"></div>
                  <%
                    }
                      }
                  %>
                </logic:notEmpty>
                <div>
                  <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="binSid" />');" class="mb10">
                    <span class="textLink">
                      <bean:write name="fileMdl" property="binFileName" />
                      <bean:write name="fileMdl" property="binFileSizeDsp" />
                    </span>
                  </a>
                </div>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>

        <!-- メモ -->
        <tr>
          <th>
            <span>
              <gsmsg:write key="cir.11" />
            </span>
          </th>
          <td class="txt_l">

            <logic:equal name="dspMdl" property="cifMemoFlg" value="1">
              <div class="formCounter flo_l m0">
                <span class="mr10"><gsmsg:write key="cir.53" /></span>
                <bean:write name="dspMdl" property="dspCifMemoDate" />
              </div>
            </logic:equal>

            <textarea class="formContent textarea wp600" name="cir020memo" rows="10" onkeyup="showLengthStr(value, <%=maxLengthMemo%>, 'inputlength');" id="inputstr"><bean:write name="cir020Form" property="cir020memo" /></textarea>
            <div>
              <span class="formCounter"><gsmsg:write key="cmn.current.characters" />:</span><span id="inputlength" class="formCounter">0</span>
              <span class="formCounter_max"> / <%=maxLengthMemo%>
                <gsmsg:write key="cmn.character" />
              </span>
            </div>
          </td>
        </tr>

        <!-- 添付ファイル -->
        <tr>
          <th>
            <span>
              <gsmsg:write key="cir.cir020.4" /><gsmsg:write key="cmn.attached" />
            </span>
          </th>

          <td class="w75">
            <attachmentFile:filearea
            mode="<%= String.valueOf(GSConstCommon.CMN110MODE_FILE) %>"
            pluginId="<%= GSConstCircular.PLUGIN_ID_CIRCULAR %>"
            tempDirId="cir020"
            tempDirPlus="<%= GSConstCircular.TEMP_DIR_KN %>" />
          </td>
        </tr>
      </table>
      <logic:equal name="dspMdl" property="cifShow" value="0">
        <div class="component_bothEnd">
          <div class="txt_l mr10">
            <span>
              <gsmsg:write key="cir.14" />
            </span>
            <div class="verAlignMid">

              <html:select styleClass="wp200" name="cir020Form" property="cirMemListGroup" onchange="buttonPush('changeGroup');">
                <logic:notEmpty name="cir020Form" property="cirMemListGroupCombo">
                  <logic:iterate id="gpBean" name="cir020Form" property="cirMemListGroupCombo" scope="request">
                    <bean:define id="gpValue" name="gpBean" property="value" type="java.lang.String" />
                    <logic:equal name="gpBean" property="styleClass" value="0">
                      <html:option value="<%=gpValue%>">
                        <bean:write name="gpBean" property="label" />
                      </html:option>
                    </logic:equal>
                    <logic:equal name="gpBean" property="styleClass" value="1">
                      <html:option styleClass="select_mygroup-bgc" value="<%=gpValue%>">
                        <bean:write name="gpBean" property="label" />
                      </html:option>
                    </logic:equal>
                    <logic:equal name="gpBean" property="styleClass" value="2">
                      <html:option styleClass="select_daihyo-bgc" value="<%=gpValue%>">
                        <bean:write name="gpBean" property="label" />
                      </html:option>
                    </logic:equal>
                  </logic:iterate>
                </logic:notEmpty>
              </html:select>
              <button class="iconBtn-border ml5" type="button" id="cir020GroupBtn" value="" onClick="openGroupWindow(this.form.cirMemListGroup, 'cirMemListGroup', '0', 'changeGroup', 0);">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
              </button>
            </div>
          </div>

          <div class="txt_r">
            <button type="button" class="baseBtn" value="<gsmsg:write key="cir.allTmep.download" />" onClick="buttonPush('allTmpExp');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_folder.png">
              <img class="btn_originalImg-display" src="../common/images/original/icon_zipfile.png">
              <gsmsg:write key="cir.allTmep.download" />
            </button>
          </div>
        </div>

        <table class="table-top">
          <tr>
            <%
              int titlecol = 1;
                  for (int i = 0; i < sortKeyList.length; i++) {
                    if (jp.groupsession.v2.cir.GSConstCircular.SAKI_SORT_KAKU == sortKeyList[i]) {
                      titlecol = 2;
                    } else {
                      titlecol = 1;
                    }
                    if (iSortKbn == sortKeyList[i] && sortKeyList[i] != -1) {
                      if (iOrderKey == order_desc) {
            %>
            <th class="<%=title_width[i]%> no_w" colspan="<%=titlecol%>">
              <a href="#!" onClick="return onTitleLinkSubmit(<%= sortKeyList[i] %>, <%= order_asc %>);">
              <span class="classic-display"><%= titleList[i] %>▼</span>
              <span class="original-display txt_m"><%= titleList[i] %><i class="icon-sort_down"></i></span>
              </a>
            </th>
            <%
              } else {
            %>
            <th class="<%=title_width[i]%> no_w" colspan="<%=titlecol%>">
              <a href="#!" onClick="return onTitleLinkSubmit(<%= sortKeyList[i] %>, <%= order_desc %>);">
              <span class="classic-display"><%= titleList[i] %>▲</span>
              <span class="original-display txt_m"><%= titleList[i] %><i class="icon-sort_up"></i></span>
              </a>
            </th>
            <%
              }
                    } else if (sortKeyList[i] == -1) {
            %>
            <th class="<%=title_width[i]%> no_w">
              <span><%=titleList[i]%></span>
            </th>
            <%
              } else {
            %>
            <th class="<%=title_width[i]%> no_w" colspan="<%=titlecol%>">
              <a href="#!" onClick="return onTitleLinkSubmit(<%= sortKeyList[i] %>, <%= order_asc %>);"><span><%= titleList[i] %></span></a>
            </th>
            <%
              }
                  }
            %>
          </tr>

          <logic:notEmpty name="cir020Form" property="cir030memList" scope="request">
            <logic:iterate id="memMdl" name="cir020Form" property="cir030memList" scope="request">
              <tr>
                <td>
                  <span>
                    <bean:write name="memMdl" property="syainNo" />
                  </span>
                </td>

                <td>
                  <span>
                    <logic:equal name="memMdl" property="cacJkbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL)%>">
                      <bean:define id="mukoUserClass" value="" />
                      <logic:equal name="memMdl" property="usrUkoFlg" value="1">
                        <bean:define id="mukoUserClass">mukoUser</bean:define>
                      </logic:equal>
                      <span class="<%=mukoUserClass%>">
                        <bean:write name="memMdl" property="cacName" />
                      </span>
                    </logic:equal>
                    <logic:notEqual name="memMdl" property="cacJkbn" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL)%>">
                      <del>
                        <bean:write name="memMdl" property="cacName" />
                      </del>
                    </logic:notEqual>
                  </span>
                </td>
                <td>
                  <span>
                    <bean:write name="memMdl" property="posName" />
                  </span>
                </td>

                <td class="txt_c w3">
                  <logic:equal name="memMdl" property="cvwConf" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_OPEN)%>">
                    <img class="btn_classicImg-display" alt="<gsmsg:write key="cmn.confirmed" />" src="../common/images/classic/icon_check.png">
                    <img class="btn_originalImg-display" alt="<gsmsg:write key="cmn.confirmed" />" src="../common/images/original/icon_checked.png">
                  </logic:equal>
                </td>

                <td class="txt_c no_w">
                  <logic:equal name="memMdl" property="cvwConf" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_OPEN)%>">
                    <div>
                      <bean:write name="memMdl" property="openTimeDate" />
                    </div>
                      <bean:write name="memMdl" property="openTimeTime" />
                  </logic:equal>
                </td>

                <td class="txt_t">
                  <span>
                    <bean:write name="memMdl" property="cvwMemo" filter="false" />
                  </span>
                  <logic:equal name="memMdl" property="circularUse" value="false">
                    <span class="cl_fontWarn">
                      ※<gsmsg:write key="cir.15" />
                    </span>
                  </logic:equal>
                </td>

                <!-- 添付 -->
                <td class="txt_l">
                  <logic:notEmpty name="memMdl" property="dspUserTmpFileList">
                    <logic:iterate id="usrFileMdl" name="memMdl" property="dspUserTmpFileList">
                      <div>
                        <a href="#!" onClick="return usrFileLinkClick('<bean:write name="memMdl" property="cacSid" />','<bean:write name="usrFileMdl" property="binSid" />');">
                          <span class="textLink">
                            <bean:write name="usrFileMdl" property="binFileName" />
                            <bean:write name="usrFileMdl" property="binFileSizeDsp" />
                          </span>
                        </a>
                      </div>
                    </logic:iterate>
                  </logic:notEmpty>
                </td>

                <td class="txt_c">
                  <logic:equal name="memMdl" property="cvwConf" value="<%=String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_OPEN)%>">
                    <div>
                      <bean:write name="memMdl" property="dspCvwEdateDate" />
                      </div>
                      <bean:write name="memMdl" property="dspCvwEdateTime" />
                  </logic:equal>
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </logic:equal>
      <div class="footerBtn_block">
        <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
          <gsmsg:write key="cmn.pdf" />
        </button>
        <logic:equal name="cir020Form" property="cir020PrevBound" value="false">
          <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" disabled>
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l_3_off.png" alt="<gsmsg:write key="cmn.previous" />">
            <div class="btn_originalImg-display mb5">
              <i class="icon-paging_left fs_18 pb1"></i>
            </div>
            <gsmsg:write key="cmn.previous" />
          </button>
        </logic:equal>
        <logic:equal name="cir020Form" property="cir020PrevBound" value="true">
          <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" onClick="buttonPush('prev020');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l_18.png" alt="<gsmsg:write key="cmn.previous" />">
            <div class="btn_originalImg-display mb5">
              <i class="icon-paging_left fs_18 pb1"></i>
            </div>
            <gsmsg:write key="cmn.previous" />
          </button>
        </logic:equal>

        <logic:equal name="cir020Form" property="cir020NextBound" value="false">
          <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" disabled>
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r_3_off.png" alt="<gsmsg:write key="cmn.next" />">
            <div class="btn_originalImg-display mb5">
              <i class="icon-paging_right fs_18 pb1"></i>
            </div>
            <gsmsg:write key="cmn.next" />
          </button>
        </logic:equal>
        <logic:equal name="cir020Form" property="cir020NextBound" value="true">
          <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" onClick="buttonPush('next020');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r_18.png" alt="<gsmsg:write key="cmn.next" />">
            <div class="btn_originalImg-display mb5">
              <i class="icon-paging_right fs_18 pb1"></i>
            </div>
            <gsmsg:write key="cmn.next" />
          </button>
        </logic:equal>

        <logic:equal name="cmdMode" value="<%=gomi%>">
          <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
            <gsmsg:write key="cmn.delete" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.undo" />" class="baseBtn" onClick="buttonPush('comeback');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_return.png" alt="<gsmsg:write key="cmn.undo" />">
            <gsmsg:write key="cmn.undo" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </logic:equal>

        <logic:notEqual name="cmdMode" value="<%=gomi%>">
          <logic:equal name="dspMdl" property="cvwConf" value="<%=String.valueOf(GSConstCircular.CONF_UNOPEN)%>">
            <button value="<gsmsg:write key="cmn.check" />" class="baseBtn" onClick="confClick();">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.check" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.check" />">
              <gsmsg:write key="cmn.check" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:equal>

          <logic:notEqual name="dspMdl" property="cvwConf" value="<%=String.valueOf(GSConstCircular.CONF_UNOPEN)%>">
            <logic:equal name="cir020Form" property="memoFlg" value="0">
              <button value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="memoEdit();">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                <gsmsg:write key="cmn.edit" />
              </button>
            </logic:equal>
            <logic:equal name="cir020Form" property="memoFlg" value="1">
            </logic:equal>
            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </logic:notEqual>
        </logic:notEqual>
      </div>
    </div>

    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>

  <IFRAME type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></IFRAME>
  <logic:equal name="cir020Form" property="cir020searchUse" value="<%= String.valueOf(GSConst.PLUGIN_USE) %>">
    <span id="tooltip_search" class="tooltip_search"></span>
  </logic:equal>
</body>
</html:html>