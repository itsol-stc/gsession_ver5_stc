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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../ipkanri/js/ipkanri.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/check.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/cmn110.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../common/js/grouppopup.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>">

</script>

<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
<link rel=stylesheet href='../ipkanri/css/ip.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<title>GROUPSESSION <gsmsg:write key="ipk.ipk070.1" /></title>
</head>

<body>
  <html:form action="/ipkanri/ipk070">
    <html:hidden property="CMD" />
    <html:hidden property="netSid" />
    <html:hidden property="iadSid" />
    <html:hidden property="binSid" />
    <html:hidden property="usedKbn" />
    <html:hidden property="iadLimit" />
    <html:hidden property="deleteAllCheck" />
    <html:hidden property="sortKey" />
    <html:hidden property="orderKey" />
    <html:hidden property="ipk070PageNow" />
    <html:hidden property="ipk070MaxPageNum" />
    <html:hidden property="ipk070SvNetSid" />
    <html:hidden property="ipk070SvSltNet" />
    <html:hidden property="ipk070SvGrpSid" />
    <html:hidden property="ipk070SvUsrSid" />
    <html:hidden property="ipk070SvSearchSortKey1" />
    <html:hidden property="ipk070SvSearchOrderKey1" />
    <html:hidden property="ipk070SvSearchSortKey2" />
    <html:hidden property="ipk070SvSearchOrderKey2" />
    <html:hidden property="ipk070SvKeyWord" />
    <html:hidden property="ipk070SvKeyWordkbn" />
    <html:hidden property="ipk050NetSid" />
    <html:hidden property="returnCmd" />


    <logic:notEmpty name="ipk070Form" property="ipk070SvSearchTarget">
      <logic:iterate id="prm" name="ipk070Form" property="ipk070SvSearchTarget">
        <input type="hidden" name="ipk070SvSearchTarget" value="<bean:write name="prm" />">
      </logic:iterate>
    </logic:notEmpty>

    <logic:notEmpty name="ipk070Form" property="deleteCheck">
      <logic:iterate id="param" name="ipk070Form" property="deleteCheck">
        <input type="hidden" name="deleteCheck" value="<bean:write name="param" />">
      </logic:iterate>
    </logic:notEmpty>

    <%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp"%>

    <div class="pageTitle w85 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../ipkanri/images/classic/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
          <img class="header_pluginImg" src="../ipkanri/images/original/header_ipkanri.png" alt="<gsmsg:write key="ipk.12" />">
        </li>
        <li>
          <gsmsg:write key="cmn.ipkanri" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="ipk.ipk070.1" />
        </li>
        <li>
          <div>
            <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk070Return');">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
              <gsmsg:write key="cmn.back" />
            </button>
          </div>
        </li>
      </ul>
    </div>
    <div class="wrapper w85 mrl_auto">

      <logic:messagesPresent message="false">
        <html:errors />
      </logic:messagesPresent>

      <table class="table-left">
        <tr>
          <th class="w100 hp30 bgC_header1" colspan="4">
          <span class="verAlignMid">
            <img class="btn_classicImg-display table_header_icon_search" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.advanced.search" />">
              <span class="table_title-color ml5">
                <gsmsg:write key="cmn.advanced.search" />
              </span>
            </span>
          </th>
        </tr>

        <tr class="w100">
          <th class="w20 no_w txt_c">
            <gsmsg:write key="ipk.1" />
          </th>
          <td class="w40 no_w" colspan="3">
            <html:select property="ipk070SltNet" styleClass="wp200">
              <logic:notEmpty name="ipk070Form" property="ipk070NetNameLabel">
                <html:optionsCollection name="ipk070Form" property="ipk070NetNameLabel" value="value" label="label" />
              </logic:notEmpty>
            </html:select>
          </td>
        </tr>

        <tr class="w100">
          <th class="w20 no_w txt_c">
            <gsmsg:write key="cmn.employer" />
          </th>
          <td class="w100" class="w40 no_w" colspan="3">
            <table class="w100">

              <tr>
                <td class="border_none p0">
                  <div class="verAlignMid">
                    <html:select styleClass="wp200" name="ipk070Form" property="ipk070SltGroup" onchange="buttonPush2('changeGroup');">
                      <logic:notEmpty name="ipk070Form" property="ipk070GroupLabel">
                        <html:optionsCollection name="ipk070Form" property="ipk070GroupLabel" value="value" label="label" />
                      </logic:notEmpty>
                    </html:select>
                    <button class="iconBtn-border ml5" type="button" id="ipk070GroupBtn" value="" onclick="openGroupWindowForIpkanri(this.form.ipk070SltGroup, 'ipk070SltGroup', '0', 'changeGrp');">
                      <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png">
                      <img class="btn_originalImg-display" src="../common/images/original/icon_group.png">
                    </button>
                  </div>

                  <html:select property="ipk070SltUser" styleClass="wp200">
                    <logic:notEmpty name="ipk070Form" property="ipk070UserLabel">
                      <logic:iterate id="user" name="ipk070Form" property="ipk070UserLabel">
                        <bean:define id="userValue" name="user" property="value" />
                        <bean:define id="mukoUserClass" value="" />
                        <logic:equal name="user" property="usrUkoFlg" value="1">
                          <bean:define id="mukoUserClass" value="mukoUserOption" />
                        </logic:equal>
                        <html:option styleClass="<%=mukoUserClass%>" value="<%=String.valueOf(userValue)%>">
                          <bean:write name="user" property="label" />
                        </html:option>
                      </logic:iterate>
                    </logic:notEmpty>
                  </html:select>
                </td>
              </tr>
            </table>
          </td>
        </tr>

        <tr>
          <th class="w20 no_w txt_c">
            <gsmsg:write key="cmn.keyword" />
          </th>
          <td class="no_w">
            <html:text name="ipk070Form" property="ipk070KeyWord" maxlength="100" styleClass="wp200" />
            <div class="w40 no_w">
              <span class="verAlignMid">
                <html:radio name="ipk070Form" property="ipk070KeyWordkbn" value="0" styleId="keyKbn_01" />
                <label for="keyKbn_01">
                  <gsmsg:write key="cmn.contains.all" />
                  (AND)
                </label>
                <html:radio name="ipk070Form" property="ipk070KeyWordkbn" value="1" styleClass="ml10" styleId="keyKbn_02" />
                <label for="keyKbn_02">
                  <gsmsg:write key="cmn.containing.either" />
                  (OR)
                </label>
              </span>
            </div>
          </td>
          <th class="w20 no_w txt_c">
            <gsmsg:write key="cmn.search2" />
          </th>
          <td class="w40 no_w">
            <div>
              <span class="verAlignMid">
                <html:multibox styleId="search_scope_01" name="ipk070Form" property="ipk070SearchTarget" value="1" />
                <label for="search_scope_01">
                  <gsmsg:write key="ipk.6" />
                </label>
              </span>
            </div>
            <div>
              <span class="verAlignMid">
                <html:multibox styleId="search_scope_02" name="ipk070Form" property="ipk070SearchTarget" value="2" />
                <label for="search_scope_02">
                  <gsmsg:write key="ipk.7" />
                </label>
              </span>
            </div>
            <div>
              <span class="verAlignMid">
                <html:multibox styleId="search_scope_03" name="ipk070Form" property="ipk070SearchTarget" value="3" />
                <label for="search_scope_03">
                  <gsmsg:write key="cmn.comment" />
                </label>
              </span>
            </div>
          </td>
        </tr>

        <tr>
          <th class="w10 no_w txt_c">
            <gsmsg:write key="cmn.sort.order" />
          </th>
          <td class="w40 no_w" colspan="3">
            <span>
              <gsmsg:write key="cmn.first.key" />
            </span>

            <span class="verAlignMid">
              <html:select property="ipk070SearchSortKey1">
                <logic:notEmpty name="ipk070Form" property="ipk070SortKeyLabelList">
                  <html:optionsCollection name="ipk070Form" property="ipk070SortKeyLabelList" value="value" label="label" />
                </logic:notEmpty>
              </html:select>

              <html:radio name="ipk070Form" property="ipk070SearchOrderKey1" value="0" styleId="sort1_up" styleClass="ml10" />
              <label for="sort1_up">
                <gsmsg:write key="cmn.order.asc" />
              </label>
              <html:radio name="ipk070Form" property="ipk070SearchOrderKey1" value="1" styleId="sort1_dw" styleClass="ml10" />
              <label for="sort1_dw">
                <gsmsg:write key="cmn.order.desc" />
              </label>
            </span>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span>
              <gsmsg:write key="cmn.second.key" />
            </span>
            <span class="verAlignMid">
              <html:select property="ipk070SearchSortKey2">
                <logic:notEmpty name="ipk070Form" property="ipk070SortKeyLabelList">
                  <html:optionsCollection name="ipk070Form" property="ipk070SortKeyLabelList" value="value" label="label" />
                </logic:notEmpty>
              </html:select>
              <html:radio name="ipk070Form" property="ipk070SearchOrderKey2" value="0" styleId="sort2_up" styleClass="ml10" />
              <label for="sort2_up">
                <gsmsg:write key="cmn.order.asc" />
              </label>
              <html:radio name="ipk070Form" property="ipk070SearchOrderKey2" value="1" styleId="sort2_dw" styleClass="ml10" />
              <label for="sort2_dw">
                <gsmsg:write key="cmn.order.desc" />
              </label>
            </span>
          </td>
        </tr>
      </table>

      <div class="txt_c">
        <button type="button" value="<gsmsg:write key="cmn.search" />" class="baseBtn" onclick="buttonPush2('doSearch');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_search.png" alt="<gsmsg:write key="cmn.search" />">
          <gsmsg:write key="cmn.search" />
        </button>
      </div>

      <logic:notEmpty name="ipk070Form" property="searchModelList">
        <logic:greaterThan name="ipk070Form" property="ipk070MaxPageNum" value="1">
          <div class="paging">
            <button type="button" class="webIconBtn" onClick="buttonPush2('arrow_left');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <logic:notEmpty name="ipk070Form" property="ipk070PageLabel">
              <html:select styleClass="paging_combo" property="ipk070Page1" onchange="ipk070ChangePage1();">
                <html:optionsCollection name="ipk070Form" property="ipk070PageLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <button type="button" class="webIconBtn" onClick="buttonPush2('arrow_right');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>
      <logic:notEmpty name="ipk070Form" property="searchModelList">
        <table class="table-top" cellspacing="0" cellpadding="0">
          <tr>
            <th class="txt_c no_w w10 cursor_p" rowspan="2">
              <a href="#!" onclick="return clickSortTitle(4);">
                <gsmsg:write key="ipk.19" />
              </a>
            </th>

            <th class="txt_c no_w w10 cursor_p" rowspan="2">
              <a href="#!" onclick="return clickSortTitle(5);">
                <gsmsg:write key="ipk.20" />
              </a>
            </th>

            <th class="txt_c no_w w15 cursor_p" rowspan="2">
              <a href="#!" onclick="return clickSortTitle(0);">
                <gsmsg:write key="ipk.6" />
              </a>
            </th>

            <th class="txt_c no_w w20 cursor_p" rowspan="2">
              <a href="#!" onclick="return clickSortTitle(1);">
                <gsmsg:write key="ipk.7" />
              </a>
            </th>

            <th class="txt_c no_w cursor_p" rowspan="2">
              <a href="#!" onclick="return clickSortTitle(2);">
                <gsmsg:write key="ipk.21" />
              </a>
            </th>

            <th class="txt_c no_w" rowspan="2">
              <gsmsg:write key="cmn.employer" />
            </th>

            <th class="txt_c no_w w10 cursor_p">
              <a href="#!" onclick="return clickSortTitle(6);">
                CPU
              </a>
            </th>

            <th class="txt_c no_w w10 cursor_p">
              <a href="#!" onclick="return clickSortTitle(7);">
                  <gsmsg:write key="cmn.memory" />
              </a>
            </th>

            <th class="txt_c no_w w10 cursor_p">
              <a href="#!" onclick="return clickSortTitle(8);">
                HD
              </a>
            </td>
            <th class="txt_c no_w" rowspan="2">
              <gsmsg:write key="cmn.detail" />
            </th>
          </tr>

          <tr>
            <th class="w100 txt_c no_w cursor_p" colspan="3">
              <a href="#!" onclick="return clickSortTitle(3);">
                <gsmsg:write key="cmn.comment" />
              </a>
            </th>
          </tr>

          <% String bgC; %>
          <logic:notEmpty name="ipk070Form" property="searchModelList">
            <logic:iterate id="searchModel" name="ipk070Form" property="searchModelList" indexId="idx">
              <%if (idx % 2 == 0) { %>
              <% bgC = "bgC_tableCell"; %>
              <% } else { %>
              <% bgC = "bgC_tableCellEvn"; %>
              <% } %>

              <tr>
                <td class="<%=bgC%>" rowspan="2">
                  <bean:write name="searchModel" property="netad" />
                </td>
                <td class="<%=bgC%>" rowspan="2">
                  <bean:write name="searchModel" property="subnet" />
                </td>
                <td class="<%=bgC%>" rowspan="2">
                  <bean:write name="searchModel" property="ipad" />
                </td>
                <td class="<%=bgC%>" rowspan="2">
                  <bean:write name="searchModel" property="iadName" />
                </td>
                <td class="<%=bgC%> no_w" rowspan="2">
                  <logic:equal name="searchModel" property="useKbn" value="0">
                    <span class="cl_fontWeekWarn">
                      <gsmsg:write key="cmn.unused" />
                    </span>
                  </logic:equal>
                  <logic:equal name="searchModel" property="useKbn" value="1">
                    <gsmsg:write key="cmn.in.use" />
                  </logic:equal>
                </td>
                <td class="<%=bgC%> no_w" rowspan="2">

                  <logic:notEmpty name="searchModel" property="iadAdmList">
                    <logic:iterate id="addAdm" name="searchModel" property="iadAdmList">
                      <logic:equal name="addAdm" property="usrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_TOROKU)%>">
                        <bean:define id="mukoUserClass" value="" />
                        <logic:equal name="addAdm" property="usrUkoFlg" value="1">
                          <bean:define id="mukoUserClass" value="mukoUser" />
                        </logic:equal>
                        <span class="<%=mukoUserClass%>">
                          <bean:write name="addAdm" property="usiSei" />
                          &nbsp;
                          <bean:write name="addAdm" property="usiMei" />
                        </span>
                        <br>
                      </logic:equal>
                      <logic:equal name="addAdm" property="usrJkbn" value="<%=String.valueOf(jp.groupsession.v2.cmn.GSConst.JTKBN_DELETE)%>">
                        <strike><bean:write name="addAdm" property="usiSei" />&nbsp;<bean:write name="addAdm" property="usiMei" /></strike>
                        <br>
                      </logic:equal>
                    </logic:iterate>
                  </logic:notEmpty>

                </td>
                <td class="<%=bgC%> no_w">
                  <bean:write name="searchModel" property="cpuName" />
                </td>
                <td class="<%=bgC%> no_w">
                  <bean:write name="searchModel" property="memoryName" />
                </td>
                <td class="<%=bgC%> no_w">
                  <bean:write name="searchModel" property="hdName" />
                </td>
                <td class="<%=bgC%> no_w txt_c" rowspan="2">
                  <button type="button" class="baseBtn" name="detail" value="<gsmsg:write key="cmn.detail" />" onClick="ipk070ButtonPush('ipk070detail', '<bean:write name="searchModel" property="netSid" />', '<bean:write name="searchModel" property="iadSid" />', '1');">
                    <gsmsg:write key="cmn.detail" />
                  </button>
                </td>
              </tr>
              <tr>
                <td class="<%=bgC%> no_w" colspan="3">
                  <bean:write name="searchModel" property="iadMsg" />
                </td>
              </tr>
            </logic:iterate>
          </logic:notEmpty>
        </table>
      </logic:notEmpty>

      <logic:notEmpty name="ipk070Form" property="searchModelList">
        <logic:greaterThan name="ipk070Form" property="ipk070MaxPageNum" value="1">
          <div class="paging mb10">
            <button type="button" class="webIconBtn" onClick="buttonPush2('arrow_left');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <logic:notEmpty name="ipk070Form" property="ipk070PageLabel">
              <html:select styleClass="paging_combo" property="ipk070Page2" onchange="ipk070ChangePage2();">
                <html:optionsCollection name="ipk070Form" property="ipk070PageLabel" value="value" label="label" />
              </html:select>
            </logic:notEmpty>
            <button type="button" class="webIconBtn" onClick="buttonPush2('arrow_right');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:greaterThan>
      </logic:notEmpty>

      <div class="footerBtn_block">
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush2('ipk070Return');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>

    </div>

  </html:form>
  <%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp"%>
</body>
</html:html>