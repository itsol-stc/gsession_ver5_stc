<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="rss.rss040.1" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../rss/js/rss040.js?<%=GSConst.VERSION_PARAM%>"></script>
<link rel=stylesheet href='../rss/css/rss.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/rss/rss040">
    <input type="hidden" name="CMD" value="">
    <input type="hidden" name="rssSid" value="">
    <input type="hidden" name="rss040feedTitle" value="">

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../rss/images/classic/header_rss.png" alt=" <gsmsg:write key="rss.3" />">
          <img class="header_pluginImg" src="../rss/images/original/menu_icon_single.png" alt=" <gsmsg:write key="rss.3" />">
        </li>
        <li>
          <gsmsg:write key="rss.3" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="rss.3" />
          -
          <gsmsg:write key="cmn.entry.rankings" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backPage');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <logic:notEmpty name="rss040Form" property="resultList">
        <bean:size id="count1" name="rss040Form" property="pageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="rss040page1" onchange="changePage(0);">
              <html:optionsCollection name="rss040Form" property="pageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>

        </logic:greaterThan>
      </logic:notEmpty>


      <div class="w100 txt_c">

        <table class="table-top">
          <logic:notEmpty name="rss040Form" property="resultList">
            <tr>
              <th colspan="4">
                <span>
                  <gsmsg:write key="cmn.entry.rankings" />
                </span>
              </th>
              <logic:iterate id="resultMdl" name="rss040Form" property="resultList" indexId="idx">
                <tr>
                  <td class="txt_c fw_b w5 border_right_none">
                    <span>
                      <bean:write name="resultMdl" property="ranking" />
                    </span>
                  </td>
                  <td class="txt_m w85 border_left_none border_right_none">
                   <div>
                   <a href="<bean:write name="resultMdl" property="rsdUrl" />" target="_blank">
                     <span class="fs_16">
                       <bean:write name="resultMdl" property="rsdTitle" />
                     </span>
                   </a>
                   </div>
                   <div>
                     <span class="fs_13">
                       <bean:write name="resultMdl" property="description" />
                     </span>
                   </div>
                  </td>
                  <td class="w5 txt_c no_w border_left_none border_right_none">
                   <span class="rssCount"><bean:write name="resultMdl" property="userCount" />users</span>
                  </td>
                  <td class="w10 txt_c border_left_none">
                    <logic:equal name="resultMdl" property="koudokuCount" value="0">
                    <span class="no_w">
                      <button type="button" value="<gsmsg:write key="rss.6" />" onClick="rssAdd(<bean:write name="resultMdl" property="rssSid" />, '<bean:write name="resultMdl" property="rsdTitle" />');" class="baseBtn no_w">
                        <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="rss.6" />">
                        <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="rss.6" />">
                        <gsmsg:write key="rss.6" />
                      </button>
                      </span>
                    </logic:equal>
                    <logic:greaterThan name="resultMdl" property="koudokuCount" value="0">
                      <span class="cl_fontWarn fs_13">
                        <gsmsg:write key="rss.1" />
                        <br>
                        <gsmsg:write key="cmn.pre" />
                      </span>
                    </logic:greaterThan>
                  </td>
                </tr>
              </logic:iterate>
            </tr>
          </logic:notEmpty>
        </table>
        </div>

        <logic:notEmpty name="rss040Form" property="resultList">
        <bean:size id="count1" name="rss040Form" property="pageLabelList" scope="request" />
        <logic:greaterThan name="count1" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="rss040page2" onchange="changePage(1);">
              <html:optionsCollection name="rss040Form" property="pageLabelList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
          </logic:greaterThan>
          </logic:notEmpty>

        <div class="footerBtn_block mt5">
        <button type="button" value="<gsmsg:write key="cmn.back" />" class="baseBtn" onClick="buttonPush('backPage');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
</div>



</html:form>

<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>