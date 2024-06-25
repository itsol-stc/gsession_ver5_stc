<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>

<%@ page import="jp.groupsession.v2.cmn.GSConst"%>


<%
  String order_desc = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_DESC);
  String order_asc = String.valueOf(jp.groupsession.v2.cmn.GSConst.ORDER_KEY_ASC);
  String sortkey_id = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_ID);
  String sortkey_name = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SORT_PROJECT_NAME);
  String sortkey_start = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_START);
  String sortkey_end = String.valueOf(jp.groupsession.v2.prj.GSConstProject.SORT_PRJECT_END);
%>


<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GROUPSESSION <gsmsg:write key="cmn.project" /><gsmsg:write key="cmn.select" /></title>
<script src="../common/js/cmd.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>
<script src="../common/js/imageView.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../project/js/prj210.js?<%=GSConst.VERSION_PARAM%>"></script>
<script type="text/javascript" src="../common/js/tableTop.js? <%=GSConst.VERSION_PARAM%>"></script>

<link rel=stylesheet href="../project/css/project.css?<%=GSConst.VERSION_PARAM%>" type="text/css">
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body>

  <html:form action="/project/prj210">
    <input type="hidden" name="CMD" value="">

    <html:hidden property="prj210KoukaiKbn" />
    <html:hidden property="prj210Progress" />
    <html:hidden property="prj210page" />
    <html:hidden property="prj210parentPageId" />
    <html:hidden property="prj210ProjectSid" />
    <html:hidden property="prj210ProjectName" />
    <html:hidden property="prj210sort" />
    <html:hidden property="prj210order" />

    <div class="pageTitle w80 mrl_auto">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../project/images/classic/header_project.png" alt="<gsmsg:write key="project.29" />">
          <img class="header_pluginImg" src="../project/images/original/header_project.png" alt="<gsmsg:write key="project.29" />">
        </li>
        <li>
          <gsmsg:write key="cmn.project" />
        </li>
        <li></li>
        <li></li>
      </ul>
    </div>
    <div class="wrapper w80 mrl_auto">

      <div id="prj_select_index">

        <table class="w100 bgC_body">
          <tr>

            <logic:equal name="prj210Form" property="prj210KoukaiKbn" value="-1">
              <td class="bor1 txt_c bgC_gray w10">
                <span class="cl_fontOutline">
                  <gsmsg:write key="cmn.all" />
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210KoukaiKbn" value="-1">
              <td class="bor1 txt_c w10 td-hoverChange cl_linkDef cursor_p" onClick="return selectKoukaiKbn('-1');">
                  <gsmsg:write key="cmn.all" />
              </td>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210KoukaiKbn" value="0">
              <td class="bor1 txt_c bgC_gray w10">
                <span class="cl_fontOutline">
                  <gsmsg:write key="cmn.join.project" />
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210KoukaiKbn" value="0">
              <td class="bor1 txt_c w10 td-hoverChange cl_linkDef cursor_p" onclick="return selectKoukaiKbn('0');"">
                  <gsmsg:write key="cmn.join.project" />
              </td>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210KoukaiKbn" value="1">
              <td class="bor1 txt_c bgC_gray w10">
                <span class="cl_fontOutline">
                  <gsmsg:write key="project.prj210.4" />
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210KoukaiKbn" value="1">
              <td class="bor1 txt_c w10 td-hoverChange cl_linkDef cursor_p" onClick="return selectKoukaiKbn('1');">
                  <gsmsg:write key="project.prj210.4" />
              </td>
            </logic:notEqual>
          </tr>
        </table>

        <table class="w100 bgC_body mt10">
          <tr>

            <logic:equal name="prj210Form" property="prj210Progress" value="-1">
              <td class="bor1 txt_c bgC_gray w10">
                <span class="cl_fontOutline">
                  <gsmsg:write key="cmn.all" />
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210Progress" value="-1">
              <td class="bor1 txt_c w10 td-hoverChange cl_linkDef cursor_p" onClick="return selectProgress('-1');">
                  <gsmsg:write key="cmn.all" />
              </td>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210Progress" value="0">
              <td class="bor1 txt_c bgC_gray w10">
                <span class="cl_fontOutline">
                  <gsmsg:write key="project.prj210.5" />
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210Progress" value="0">
              <td class="bor1 txt_c w10 td-hoverChange cl_linkDef cursor_p" onClick="return selectProgress('0');">
                  <gsmsg:write key="project.prj210.5" />
                </a>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210Progress" value="1">
              <td class="bor1 txt_c bgC_gray w10">
                <span class="cl_fontOutline">
                  <gsmsg:write key="project.prj210.6" />
                </span>
              </td>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210Progress" value="1">
              <td class="bor1 txt_c w10 td-hoverChange cl_linkDef cursor_p" onClick="return selectProgress('1');">
                  <gsmsg:write key="project.prj210.6" />
              </td>
            </logic:notEqual>

          </tr>
        </table>
      </div>

      <logic:notEmpty name="prj210Form" property="prj210ProjectList">

        <logic:notEmpty name="prj210Form" property="pageCmbList">

          <div class="paging mt10">
            <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
              <i class="icon-paging_left"></i>
            </button>
            <html:select styleClass="paging_combo" property="prj210pageTop" onchange="buttonPush('changePageTop');">
              <html:optionsCollection name="prj210Form" property="pageCmbList" value="value" label="label" />
            </html:select>
            <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
              <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
              <i class="icon-paging_right"></i>
            </button>
          </div>
        </logic:notEmpty>

        <table class="table-top">
          <tr>
            <th class="no_w"></th>

            <logic:equal name="prj210Form" property="prj210sort" value="<%=sortkey_name%>">
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_desc%>">
                <th class="w80 no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_name%>, <%=order_asc%>);">
                    <span>
                      <gsmsg:write key="project.40" />
                      <span class="classic-display">▼</span>
                    </span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                     </span>
                  </a>
                </th>
              </logic:equal>
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_asc%>">
                <th class="w80 no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_name%>, <%=order_desc%>);">
                    <span>
                      <gsmsg:write key="project.40" />
                      <span class="classic-display">▲</span>
                    </span>
                       <span class="original-display">
                     <i class="icon-sort_up"></i>
                   </span>
                  </a>
                </th>
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210sort" value="<%=sortkey_name%>">
              <th class="w80 no_w">
                <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_name%>, <%=order_asc%>);">
                  <span>
                    <gsmsg:write key="project.40" />
                  </span>
                </a>
              </th>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210sort" value="<%=sortkey_id%>">
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_desc%>">
                <th class="w20 no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_id%>, <%=order_asc%>);">
                    <span>
                      <gsmsg:write key="cmn.id" />
                      <span class="classic-display">▼</span>
                    </span>
                    <span class="original-display">
                      <i class="icon-sort_down"></i>
                    </span>
                  </a>
                </th>
              </logic:equal>
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_asc%>">
                <th class="w20 no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_id%>, <%=order_desc%>);">
                    <span>
                      <gsmsg:write key="cmn.id" />
                      <span class="classic-display">▲</span>
                    </span>
                       <span class="original-display">
                       <i class="icon-sort_up"></i>
                    </span>
                  </a>
                </th>
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210sort" value="<%=sortkey_id%>">
              <th class="w20 no_w">
                <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_id%>, <%=order_asc%>);">
                  <span>
                    <gsmsg:write key="cmn.id" />
                  </span>
                </a>
              </th>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210sort" value="<%=sortkey_start%>">
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_desc%>">
                <th class="no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_start%>, <%=order_asc%>);">
                    <span>
                      <gsmsg:write key="cmn.start" />
                      <span class="classic-display">▼</span>
                    </span>
                    <span class="original-display">
                    <i class="icon-sort_down"></i>
                   </span>
                  </a>
                </th>
              </logic:equal>
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_asc%>">
                <th class="no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_start%>, <%=order_desc%>);">
                    <span>
                      <gsmsg:write key="cmn.start" />
                      <span class="classic-display">▲</span>
                    </span>
                       <span class="original-display">
                       <i class="icon-sort_up"></i>
                    </span>
                  </a>
                </th>
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210sort" value="<%=sortkey_start%>">
              <th class="no_w">
                <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_start%>, <%=order_asc%>);">
                  <span>
                    <gsmsg:write key="cmn.start" />
                  </span>
                </a>
              </th>
            </logic:notEqual>

            <logic:equal name="prj210Form" property="prj210sort" value="<%=sortkey_end%>">
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_desc%>">
                <th class="no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_end%>, <%=order_asc%>);">
                    <span>
                      <gsmsg:write key="cmn.end" />
                      <span class="classic-display">▼</span>
                    </span>
                    <span class="original-display">
                    <i class="icon-sort_down"></i>
                    </span>
                  </a>
                </th>
              </logic:equal>
              <logic:equal name="prj210Form" property="prj210order" value="<%=order_asc%>">
                <th class="no_w">
                  <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_end%>, <%=order_desc%>);">
                    <span>
                      <gsmsg:write key="cmn.end" />
                      <span class="classic-display">▲</span>
                    </span>
                       <span class="original-display">
                      <i class="icon-sort_up"></i>
                    </span>
                  </a>
                </th>
              </logic:equal>
            </logic:equal>
            <logic:notEqual name="prj210Form" property="prj210sort" value="<%=sortkey_end%>">
              <th class="no_w">
                <a href="#!" onClick="return onTitleLinkSubmit(<%=sortkey_end%>, <%=order_asc%>);">
                  <span>
                    <gsmsg:write key="cmn.end" />
                  </span>
                </a>
              </th>
            </logic:notEqual>
          </tr>

          <logic:iterate id="projectModel" name="prj210Form" property="prj210ProjectList" indexId="idx">
            <bean:define id="prjData" name="projectModel" type="jp.groupsession.v2.prj.model.ProjectItemModel" />
            <%
              String projectId = String.valueOf(prjData.getProjectSid());
            %>

            <tr class="js_listHover cursor_p">
              <td class="js_tableTopCheck">
                <html:multibox name="prj210Form" property="prj210selectProject" styleClass="check" styleId="<%=projectId%>"><%=projectId%></html:multibox>
              </td>
              <td class="js_radio cursor_p">
                <logic:equal name="prjData" property="prjBinSid" value="0">
                  <img class="classic-display" src="../project/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="cmn.icon" />">
                  <img class="original-display" src="../project/images/original/icon_project.png" alt="<gsmsg:write key="cmn.icon" />">
                </logic:equal>
                <logic:notEqual name="prjData" property="prjBinSid" value="0">
                  <img src="../project/prj210.do?CMD=getImageFile&prj010PrjSid=<bean:write name="prjData" property="projectSid" />&prj010PrjBinSid=<bean:write name="prjData" property="prjBinSid" />" name="pitctImage" alt="<gsmsg:write key="cmn.icon" />"
                    onload="initImageView('pitctImage');" class="prj_img_ico wp20hp20">
                </logic:notEqual>
                  <bean:write name="projectModel" property="projectName" />
              </td>
              <td class="js_radio cursor_p">
                <bean:write name="projectModel" property="projectId" />
              </td>
              <td class="js_radio cursor_p">
                <bean:write name="projectModel" property="strStartDate" />
              </td>
              <td class="js_radio cursor_p">
                <bean:write name="projectModel" property="strEndDate" />
              </td>
            </tr>
          </logic:iterate>

        </table>
      </logic:notEmpty>

      <logic:notEmpty name="prj210Form" property="pageCmbList">
        <div class="paging">
          <button type="button" class="webIconBtn" onClick="return buttonPush('prevPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
            <i class="icon-paging_left"></i>
          </button>
          <html:select styleClass="paging_combo" property="prj210pageBottom" onchange="buttonPush('changePageBottom');">
            <html:optionsCollection name="prj210Form" property="pageCmbList" value="value" label="label" />
          </html:select>
          <button type="button" class="webIconBtn" onClick="return buttonPush('nextPage');">
            <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
            <i class="icon-paging_right"></i>
          </button>
        </div>
      </logic:notEmpty>

      <div class="txt_c">

        <button type="button" value="<gsmsg:write key="cmn.select" />" class="baseBtn" onClick="return parentReload();">
           <img class="btn_classicImg-display" src="../common/images/classic/icon_ok.png" alt="<gsmsg:write key="cmn.select" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_check.png" alt="<gsmsg:write key="cmn.select" />">
          <gsmsg:write key="cmn.select" />
        </button>
        <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="return window.close();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
          <gsmsg:write key="cmn.close" />
        </button>
      </div>
    </div>

  </html:form>
</body>
</html:html>