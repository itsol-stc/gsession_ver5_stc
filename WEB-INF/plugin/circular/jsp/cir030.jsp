<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<!DOCTYPE html>

<% String gomi = String.valueOf(jp.groupsession.v2.cir.GSConstCircular.MODE_GOMI); %>

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

  String[] title_width = new String[] { "w5", "w10", "w10", "w10", "w40", "w20", "w5"};
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

<logic:empty name="cir030Form" property="cir060dspId" scope="request">
  <bean:define id="cmdMode" name="cir030Form" property="cir010cmdMode" />
</logic:empty>
<logic:notEmpty name="cir030Form" property="cir060dspId" scope="request">
  <bean:define id="cmdMode" name="cir030Form" property="cir060svSyubetsu" />
</logic:notEmpty>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>GROUPSESSION <gsmsg:write key="cir.cir030.1" /></title>
<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../circular/js/cir030.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/imageView.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
<script src="../common/js/jquery.selection-min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/selectionSearch.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"> </script>

<link rel=stylesheet href='../circular/css/circular.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>

<logic:notEmpty name="cir030Form" property="cir010AccountTheme" scope="request">
  <bean:define id="selectThemePath" name="cir030Form" property="cir010AccountTheme" type="String"/>
  <theme:css filename="theme.css" selectthemepath="<%= selectThemePath %>" />
</logic:notEmpty>
<logic:empty name="cir030Form" property="cir010AccountTheme" scope="request">
  <theme:css filename="theme.css"/>
</logic:empty>

</head>

<body onload="">

  <html:form action="/circular/cir030">

    <bean:define id="dspMdl" name="cir030Form" property="cir020dspMdl" />
    <bean:define id="orderKey" name="cir030Form" property="cir030orderKey" />
    <bean:define id="sortKbn" name="cir030Form" property="cir030sortKey" />
    <% int iOrderKey = ((Integer) orderKey).intValue(); %>
    <% int iSortKbn = ((Integer) sortKbn).intValue(); %>

    <input type="hidden" name="helpPrm" value="<bean:write name="cmdMode" />">

    <input type="hidden" name="CMD" value="download">
    <input type="hidden" name="cmdMode" value="<bean:write name="cmdMode" />">
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
    <html:hidden property="cir030orderKey" />
    <html:hidden property="cir030sortKey" />
    <html:hidden property="cirEntryMode" />


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

    <logic:notEmpty name="cir030Form" property="cir060selUserSid" scope="request">
      <logic:iterate id="item" name="cir030Form" property="cir060selUserSid" scope="request">
        <input type="hidden" name="cir060selUserSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="cir030Form" property="cir060svSelUserSid" scope="request">
      <logic:iterate id="item" name="cir030Form" property="cir060svSelUserSid" scope="request">
        <input type="hidden" name="cir060svSelUserSid" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="cir030Form" property="cir060searchTarget" scope="request">
      <logic:iterate id="item" name="cir030Form" property="cir060searchTarget" scope="request">
        <input type="hidden" name="cir060searchTarget" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="cir030Form" property="cir060svSearchTarget" scope="request">
      <logic:iterate id="item" name="cir030Form" property="cir060svSearchTarget" scope="request">
        <input type="hidden" name="cir060svSearchTarget" value="<bean:write name="item"/>">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>



    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../circular/images/classic/header_circular.png" alt="<gsmsg:write key="cir.5" />"> <img class="header_pluginImg" src="../common/images/pluginImg/original/menu_icon_circular_50.png" alt="<gsmsg:write key="cir.5" />">
        </li>
        <li>
          <gsmsg:write key="cir.5" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="cir.cir030.2" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />"> <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
              <gsmsg:write key="cmn.pdf" />
            </button>
            <logic:equal name="cir030Form" property="cir020PrevBound" value="false">
              <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" disabled>
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l_3_off.png" alt="<gsmsg:write key="cmn.previous" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_left fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.previous" />
              </button>
            </logic:equal>
            <logic:equal name="cir030Form" property="cir020PrevBound" value="true">
              <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" onClick="buttonPush('prev030');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l_18.png" alt="<gsmsg:write key="cmn.previous" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_left fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.previous" />
              </button>
            </logic:equal>

            <logic:equal name="cir030Form" property="cir020NextBound" value="false">
              <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" disabled>
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r_3_off.png" alt="<gsmsg:write key="cmn.next" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_right fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.next" />
              </button>
            </logic:equal>
            <logic:equal name="cir030Form" property="cir020NextBound" value="true">
              <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" onClick="buttonPush('next030');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r_18.png" alt="<gsmsg:write key="cmn.next" />">
                <span class="btn_originalImg-display">
                  <i class="icon-paging_right fs_18 pb1"></i>
                </span>
                <gsmsg:write key="cmn.next" />
              </button>
            </logic:equal>

            <logic:notEqual name="cmdMode" value="<%= gomi %>">
              <logic:equal name="cir030Form" property="cirCreateFlg" value="true">
                <button type="button" value="<gsmsg:write key="cmn.register.copy.new2" />" class="baseBtn" onClick="cirCopy();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_syorui.gif" alt="<gsmsg:write key="cmn.register.copy.new2" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png" alt="<gsmsg:write key="cmn.register.copy.new2" />">
                  <gsmsg:write key="cmn.register.copy.new2" />
                </button>
                <button type="button" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="cirEdit();">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
                  <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
                  <gsmsg:write key="cmn.edit" />
                </button>
              </logic:equal>
            </logic:notEqual>

            <% String delBtnIcon = "icon_delete.png"; %>
            <logic:equal name="cmdMode" value="<%= gomi %>"><% delBtnIcon = "icon_delete.png"; %></logic:equal>

            <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
              <img class="btn_classicImg-display" src="../common/images/classic/<%= delBtnIcon %>" alt="<gsmsg:write key="cmn.delete" />">
              <img class="btn_originalImg-display" src="../common/images/original/<%= delBtnIcon %>" alt="<gsmsg:write key="cmn.delete" />">
              <gsmsg:write key="cmn.delete" />
            </button>

            <logic:equal name="cmdMode" value="<%= gomi %>">
              <button type="button" value="<gsmsg:write key="cmn.undo" />" class="baseBtn" onClick="buttonPush('comeback');">
                <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
                <img class="btn_originalImg-display" src="../common/images/original/icon_return.png" alt="<gsmsg:write key="cmn.undo" />">
                <gsmsg:write key="cmn.undo" />
              </button>
            </logic:equal>

            <!-- 戻る -->
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>

          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">
      <table class="table-left">

        <tr>
          <th>
            <span>
              <gsmsg:write key="cmn.title" />
            </span>
          </th>
          <td class="txt_l w100">
            <span>
              <bean:write name="dspMdl" property="cifTitle" />
            </span>
          </td>
        </tr>

        <tr>
          <th>
            <span>
              <gsmsg:write key="cir.2" />
            </span>
          </th>
          <bean:define id="mukoUserClass" value="" />
          <logic:equal name="cir030Form" property="cirViewAccountUko" value="1">
            <bean:define id="mukoUserClass">mukoUser</bean:define>
          </logic:equal>
          <td class="txt_l w100">
            <span class="<%=mukoUserClass%>">
              <bean:write name="cir030Form" property="cirViewAccountName" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cir.13" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="dspMdl" property="dspCifAdate" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.content" />
            </span>
          </th>
          <td class="txt_l">
            <logic:equal name="dspMdl" property="cifEkbn" value="1">
              <bean:define id="edate" name="dspMdl" property="dspCifEditDate" type="java.lang.String" />
              <div class="cl_fontWarn">
                ※<gsmsg:write key="bbs.bbs080.4" arg0="<%= edate %>" />
              </div>
            </logic:equal>
            <span>
              <bean:write name="dspMdl" property="cifValue" filter="false" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.attached" />
            </span>
          </th>
          <td class="txt_l">
            <logic:notEmpty name="cir030Form" property="cir020fileList" scope="request">
              <logic:iterate id="fileMdl" name="cir030Form" property="cir020fileList" scope="request">

                <logic:notEmpty name="fileMdl" property="binFileExtension">
                  <bean:define id="fext" name="fileMdl" property="binFileExtension" type="java.lang.String" />
                  <%
                    String dext = ((String)pageContext.getAttribute("fext",PageContext.PAGE_SCOPE));
                    if (dext != null) {
                        dext = dext.toLowerCase();
                        if (jp.groupsession.v2.cmn.biz.CommonBiz.isViewFile(dext)) {
                    %>
                          <div>
                            <img src="../circular/cir030.do?CMD=tempview&cir020binSid=<bean:write name="fileMdl" property="binSid" />&cir010selectInfSid=<bean:write name="cir030Form" property="cir010selectInfSid" />&cirViewAccount=<bean:write name="cir030Form" property="cirViewAccount" />"
                              name="pictImage<bean:write name="fileMdl" property="binSid" />" onload="initImageView('pictImage<bean:write name="fileMdl" property="binSid" />');">
                          </div>
                          <%
                        }
                    }
                  %>
                </logic:notEmpty>

                <a href="#!" onClick="return fileLinkClick('<bean:write name="fileMdl" property="binSid" />');">
                  <div>
                    <span class="textLink">
                      <bean:write name="fileMdl" property="binFileName" />
                      <bean:write name="fileMdl" property="binFileSizeDsp" />
                    </span>
                  </div>
                </a>
              </logic:iterate>
            </logic:notEmpty>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cir.cir030.3" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <logic:equal name="dspMdl" property="cifShow" value="0">
                <gsmsg:write key="cmn.public" />
              </logic:equal>
              <logic:notEqual name="dspMdl" property="cifShow" value="0">
                <gsmsg:write key="cmn.private" />
              </logic:notEqual>
          </td>
        </tr>
      </table>


      <div class="component_bothEnd">
        <div class="txt_l">
          <span class="mr10">
            <gsmsg:write key="cir.14" />
          </span>
          <div class="verAlignMid">

            <html:select styleClass="wp200" name="cir030Form" property="cirMemListGroup" onchange="buttonPush('changeGroup');">
              <logic:notEmpty name="cir030Form" property="cirMemListGroupCombo">
                <logic:iterate id="gpBean" name="cir030Form" property="cirMemListGroupCombo" scope="request">
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
            <button class="iconBtn-border ml5" type="button" id="cir030GroupBtn" value="" onClick="openGroupWindow(this.form.cirMemListGroup, 'cirMemListGroup', '0', 'changeGroup', 0)">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png"> <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
            </button>
          </div>
        </div>


        <div class="txt_r">
          <button type="button" class="baseBtn" value="<gsmsg:write key="cir.allTmep.download" />" onClick="buttonPush('allTmpExp');">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_folder.png"> <img class="btn_originalImg-display" src="../common/images/original/icon_zipfile.png">
            <gsmsg:write key="cir.allTmep.download" />
          </button>
        </div>
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
            <span class="classic-display"><%= titleList[i] %>▼
            </span>
            <span class="original-display txt_m"><%= titleList[i] %><i class="icon-sort_down"></i>
            </span>
          </a>
        </th>
        <%
          } else {
%>
        <th class="<%=title_width[i]%> no_w" colspan="<%=titlecol%>">
          <a href="#!" onClick="return onTitleLinkSubmit(<%= sortKeyList[i] %>, <%= order_desc %>);">
            <span class="classic-display"><%= titleList[i] %>▲
            </span>
            <span class="original-display txt_m"><%= titleList[i] %><i class="icon-sort_up"></i>
            </span>
          </a>
        </th>
        <%
          }
        } else if (sortKeyList[i] == -1) {
%>
        <th class="<%=title_width[i]%> no_w" colspan="<%=titlecol%>">
          <span><%= titleList[i] %></span>
        </th>
        <%
        } else {
%>
        <th class="<%=title_width[i]%> no_w" colspan="<%=titlecol%>">
          <a href="#!" onClick="return onTitleLinkSubmit(<%= sortKeyList[i] %>, <%= order_asc %>);">
            <span><%= titleList[i] %></span>
          </a>
        </th>
        <%
        }
      }
%>
      </tr>
      <logic:notEmpty name="cir030Form" property="cir030memList" scope="request">
        <logic:iterate id="memMdl" name="cir030Form" property="cir030memList" scope="request">
          <tr>

            <td>

              <span>
                <bean:write name="memMdl" property="syainNo" />
              </span>

            </td>

            <td>

              <span>
                <logic:equal name="memMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
                  <bean:define id="mukoUserClass" value="" />
                  <logic:equal name="memMdl" property="usrUkoFlg" value="1">
                    <bean:define id="mukoUserClass">mukoUser</bean:define>
                  </logic:equal>
                  <span class="<%=mukoUserClass%>">
                    <bean:write name="memMdl" property="cacName" />
                  </span>
                </logic:equal>
                <logic:notEqual name="memMdl" property="cacJkbn" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CAC_JKBN_NORMAL) %>">
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

            <td class="txt_c txt_m w3">

              <logic:equal name="memMdl" property="cvwConf" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_OPEN) %>">
                <img class="btn_classicImg-display" alt="<gsmsg:write key="cmn.confirmed" />" src="../common/images/classic/icon_check.png">
                <img class="btn_originalImg-display" alt="<gsmsg:write key="cmn.confirmed" />" src="../common/images/original/icon_checked.png">
              </logic:equal>

            </td>

            <td class="txt_c no_w txt_m">

              <logic:equal name="memMdl" property="cvwConf" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_OPEN) %>">
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
                  ※
                  <gsmsg:write key="cir.15" />
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
                    </span>
                  </a>
                  </div>
                </logic:iterate>
              </logic:notEmpty>
            </td>

            <td class="txt_c no_w" valign="middle">
              <logic:equal name="memMdl" property="cvwConf" value="<%= String.valueOf(jp.groupsession.v2.cir.GSConstCircular.CONF_OPEN) %>">
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

    <div class="footerBtn_block txt_r">
      <button type="button" value="<gsmsg:write key="cmn.pdf" />" class="baseBtn" onClick="buttonPush('pdf');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_pdf.png" alt="<gsmsg:write key="cmn.pdf" />">
        <gsmsg:write key="cmn.pdf" />
      </button>
      <logic:equal name="cir030Form" property="cir020PrevBound" value="false">
        <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" disabled>
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_l_3_off.png" alt="<gsmsg:write key="cmn.previous" />">
          <div class="btn_originalImg-display mb5">
            <i class="icon-paging_left fs_18 pb1"></i>
          </div>
          <gsmsg:write key="cmn.previous" />
        </button>
      </logic:equal>
      <logic:equal name="cir030Form" property="cir020PrevBound" value="true">
        <button type="button" value="<gsmsg:write key="cmn.previous" />" class="baseBtn" onClick="buttonPush('prev030');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_l_18.png" alt="<gsmsg:write key="cmn.previous" />">
          <div class="btn_originalImg-display mb5">
            <i class="icon-paging_left fs_18 pb1"></i>
          </div>
          <gsmsg:write key="cmn.previous" />
        </button>
      </logic:equal>

      <logic:equal name="cir030Form" property="cir020NextBound" value="false">
        <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" disabled>
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_r_3_off.png" alt="<gsmsg:write key="cmn.next" />">
          <div class="btn_originalImg-display mb5">
            <i class="icon-paging_right fs_18 pb1"></i>
          </div>
          <gsmsg:write key="cmn.next" />
        </button>
      </logic:equal>
      <logic:equal name="cir030Form" property="cir020NextBound" value="true">
        <button type="button" value="<gsmsg:write key="cmn.next" />" class="baseBtn" onClick="buttonPush('next030');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_arrow_3_r_18.png" alt="<gsmsg:write key="cmn.next" />">
          <div class="btn_originalImg-display mb5">
            <i class="icon-paging_right fs_18 pb1"></i>
          </div>
          <gsmsg:write key="cmn.next" />
        </button>
      </logic:equal>

      <logic:notEqual name="cmdMode" value="<%= gomi %>">
        <logic:equal name="cir030Form" property="cirCreateFlg" value="true">
          <button type="button" value="<gsmsg:write key="cmn.register.copy.new2" />" class="baseBtn" onClick="cirCopy();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_syorui.gif" alt="cmn.register.copy.new2">
            <img class="btn_originalImg-display" src="../common/images/original/icon_siryo.png" alt="cmn.register.copy.new2">
            <gsmsg:write key="cmn.register.copy.new2" />
          </button>
          <button type="button" value="<gsmsg:write key="cmn.edit" />" class="baseBtn" onClick="cirEdit();">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_edit_2.png" alt="<gsmsg:write key="cmn.edit" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_edit.png" alt="<gsmsg:write key="cmn.edit" />">
            <gsmsg:write key="cmn.edit" />
          </button>
        </logic:equal>
      </logic:notEqual>

      <button type="button" value="<gsmsg:write key="cmn.delete" />" class="baseBtn" onClick="buttonPush('delete');">
        <img class="btn_classicImg-display" src="../common/images/classic/<%= delBtnIcon %>" alt="<gsmsg:write key="cmn.delete" />">
        <img class="btn_originalImg-display" src="../common/images/original/<%= delBtnIcon %>" alt="<gsmsg:write key="cmn.delete" />">
        <gsmsg:write key="cmn.delete" />
      </button>

      <logic:equal name="cmdMode" value="<%= gomi %>">
        <button type="button" value="<gsmsg:write key="cmn.undo" />" class="baseBtn" onClick="buttonPush('comeback');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_return_2.png" alt="<gsmsg:write key="cmn.undo" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_return.png" alt="<gsmsg:write key="cmn.undo" />">
          <gsmsg:write key="cmn.undo" />
        </button>
      </logic:equal>

      <!-- 戻る -->
      <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('back');">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
        <gsmsg:write key="cmn.back" />
      </button>

    </div>


    <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>

  </html:form>

  <IFRAME type="hidden" src="../common/html/damy.html" style="display: none" name="navframe"></IFRAME>
  <logic:equal name="cir030Form" property="cir020searchUse" value="<%= String.valueOf(GSConst.PLUGIN_USE) %>">
    <span id="tooltip_search" class="tooltip_search"></span>
  </logic:equal>
</body>
</html:html>