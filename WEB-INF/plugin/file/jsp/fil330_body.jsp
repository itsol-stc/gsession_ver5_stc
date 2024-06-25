<%@page import="jp.groupsession.v2.fil.fil330.Fil330DspFileModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.fil300.Fil300ParamModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ taglib tagdir="/WEB-INF/tags/file/" prefix="filekanri" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<body>

  <bean:define id="sepKey" name="fil330Form" property="sepKey" type="String"/>
  <html:form action="/file/fil330">
  <div class="pos_rel">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="escapeCmd" value="">
    <html:hidden property="backDsp" />
    <html:hidden property="fil010SelectCabinet" />
    <html:hidden property="fil010SelectDirSid" />
    <html:hidden property="fil330SelectDsp"  />
    <html:hidden property="fil330SelectCabinet"  />
    <html:hidden name="fil330Form" property="fil300insertMode" />
    <html:hidden property="fil010DspCabinetKbn" />
    <html:hidden property="fil300BeforeDspFlg" />
    <logic:notEmpty name="fil330Form" property="fil300BeforeInsertFile">
      <logic:iterate id="sid" name="fil330Form" property="fil300BeforeInsertFile">
        <input type="hidden" name="fil300BeforeInsertFile" value="<bean:write name="sid" />">
      </logic:iterate>
    </logic:notEmpty>
    <html:hidden property="sepKey" />

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../file/images/classic/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
          <img class="header_pluginImg" src="../file/images/original/header_file.png" border="0" alt="<gsmsg:write key="cmn.filekanri" />">
        </li>
        <li>
          <gsmsg:write key="cmn.filekanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="fil.fil330.1" />
        </li>
        <li>
          <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('fil330back')">
            <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
            <gsmsg:write key="cmn.back" />
          </button>
        </li>
      </ul>
    </div>
    <div id="upperArea" class="w100">
      <bean:define id="modeKbn" name="fil330Form" property="fil300insertMode" />
      <ul id="normal_tab" class="tabHeader mb10 w100">
        <li class="pr10 pl10 bor_b1 tabHeader_tab-off js_tabHeader" data-selectid="single">
          <gsmsg:write key="fil.fil300.2" />
        </li>
        <li class="pr10 pl10 bor_b1 tabHeader_tab-off js_tabHeader" data-selectid="multiple">
          <gsmsg:write key="fil.fil300.3" />
        </li>
        <li class="pr10 pl10 bor_b1 tabHeader_tab-on">
          <gsmsg:write key="man.purge" />
        </li>
      </ul>
      <logic:equal name="fil330Form" property="fil300BeforeDspFlg" value="<%= String.valueOf(GSConstFile.BEFORE_DSP_FIL080) %>">
        <gsmsg:write key="fil.182" /><br>
        <gsmsg:write key="fil.183" />
      </logic:equal>
    </div>
    <logic:messagesPresent message="false">
      <html:errors />
    </logic:messagesPresent>

    <div class="wrapper_2column">
      <div class="wp350">
        <table class="table-top js_dirTable">
          <tbody>
          <tr>
            <th class="w100 txt_l">
              <gsmsg:write key="fil.fil300.5" />
            </th>
          </tr>
          <tr>
            <td>
              <div id="sidetreecontrol">
                <a href="#!">
                  <gsmsg:write key="cmn.all.close" />
                </a>
                |
                <a href="#!">
                  <gsmsg:write key="cmn.all.open" />
                </a>
              </div>
              <div  class="hp250 ofx_a ofy_a no_w pl5 wp340 js_treeSideList">
                <ul id="tree" class="w100">
                </ul>
                <div class="js_tree_loader">
                  <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
                  <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
                  <gsmsg:write key="cmn.loading"/>
                </div>
              </div>
            </td>
          </tr>
          </tbody>
        </table>

      </div>

      <div class="file_tradeMain js_file_tradeMain ml5">
        <%@ include file="/WEB-INF/plugin/file/jsp/fil330_dellist.jsp"%>
      </div>
    </div>
  </div>
  </html:form>

  <span class="js_treeText">
    <bean:parameter id="fileTree" name="fileTree" value=""/>
    <html:hidden name="fil330Form" property="fil330Tree" />

    <logic:notEmpty name="fil330Form" property="treeFormLv0">
      <logic:iterate id="lv0" name="fil330Form" property="treeFormLv0" type="String">
        <logic:empty name="cabDirId">
         <% String[] sp = lv0.split(sepKey);
         %>
         <bean:define id="cabDirId" value="<%=sp[0] %>"/>
         <bean:define id="cabDirName" value="<%=sp[2] %>"/>
        </logic:empty>
        <span class="display_none" name="treeFormLv0" data-treevalue="<bean:write name="lv0" />"></span>
      </logic:iterate>
    </logic:notEmpty>

  </span>

  <filekanri:fileTreeParams screenId="fil330" maxLv="11" />

  <!-- ローディング -->
  <div class="js_loading bgC_body h100 w100 pos_abs top0 display_n">
  </div>

<%--
<!--
<script type="text/javascript">
<% for (int filCnt = 0; filCnt < filTipList.size(); filCnt++) { %>
$(function() {
  $('#<%= (String) filTipList.get(filCnt) %>').tooltip();
});
<% } %>
</script>
 -->
 --%>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
