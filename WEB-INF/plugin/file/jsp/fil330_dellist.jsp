<%@page import="jp.groupsession.v2.fil.fil330.Fil330DspFileModel"%>
<%@page import="jp.groupsession.v2.usr.UserUtil"%>
<%@page import="jp.groupsession.v2.fil.fil300.Fil300ParamModel"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.fil.GSConstFile"%>
<logic:empty name="fil330Form" property="fil330SelectDsp" >
  <div class="js_importArea-noSelect txt_c txt_m mt50">
    <span class="fs_20 fw_b cl_fontWeek"><gsmsg:write key="fil.fil300.5" /></span>
  </div>
  <div class="js_importArea">
  </div>
</logic:empty>
<logic:notEmpty name="fil330Form" property="fil330SelectDsp" >
  <div class="js_importArea-noSelect display_none txt_c txt_m mt50">
    <span class="fs_20 fw_b cl_fontWeek"><gsmsg:write key="fil.fil300.5" /></span>
  </div>
  <div class="js_importArea">
    <div class="mb5 txt_r">
        <button type="button" class="baseBtn js_fil330_deleteBtn" value="<gsmsg:write key="cmn.delete" />" >
          <img class="btn_classicImg-display" src="../common/images/classic/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_delete.png" alt="<gsmsg:write key="cmn.delete" />">
          <gsmsg:write key="cmn.delete" />
        </button>
    </div>

    <!-- ページング -->
    <logic:notEmpty name="fil330Form" property="fil330FileListPageLabel">
      <div class="txt_r w100">
        <span class="paging">
           <button type="button" class="webIconBtn js_pagingDec">
             <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
             <i class="icon-paging_left"></i>
           </button>
           <html:select name="fil330Form" property="fil330FileListPageNo" styleClass="paging_combo js_pagingSel">
             <html:optionsCollection name="fil330Form" property="fil330FileListPageLabel" value="value" label="label" />
           </html:select>
           <button type="button" class="webIconBtn js_pagingInc">
             <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
             <i class="icon-paging_right"></i>
           </button>
        </span>
      </div>
    </logic:notEmpty>
    <!-- 一覧 -->
    <table class="table-top mt0 w100" cellpadding="0" cellspacing="0">
      <tr>
        <th class="w2 js_tableTopCheck js_tableTopCheck-header cursor_p bgC_header2 no_w">
          <label class="w100 h100"><input type="checkbox" class="js_fil330selectDelAll" /></label>
        </th>
        <th class="w5 bgC_header2 no_w">
          <span class="cl_fontBody">
            <gsmsg:write key="fil.fil300.10" />
          </span>
        </th>
        <th class="w40 bgC_header2 no_w">
          <span class="cl_fontBody">
            <gsmsg:write key="cmn.name4" />
          </span>
        </th>
        <th class="w5 bgC_header2 no_w">
          <span class="cl_fontBody">
            <gsmsg:write key="cmn.size" />
          </span>
        </th>
        <th class="w5 bgC_header2 no_w">
          <span class="cl_fontBody">
            <gsmsg:write key="cmn.update.day.hour" />
          </span>
        </th>
        <th class="w15 bgC_header2 no_w">
          <span class="cl_fontBody">
            <gsmsg:write key="cmn.update.user" />
          </span>
        </th>
      </tr>

      <bean:define id="tdColor" value="" />
      <% String[] tdColors = new String[] {"bgC_tableCell", "bgC_tableCellEvn"};%>

      <logic:notEmpty name="fil330Form" property="fil330FileList" scope="request">
        <logic:iterate id="dirBean" name="fil330Form" property="fil330FileList" scope="request" indexId="idx" type="Fil330DspFileModel">
        <% tdColor = tdColors[(idx.intValue() % 2)]; %>
        <bean:define id="fileName" name="dirBean" property="base.fdrName" />
          <tr class="<%= tdColor %> " data-fdrsid="<bean:write name="dirBean" property="base.feaSid" />" data-binsid="<bean:write name="dirBean" property="base.binSid" />" data-parentSid="<bean:write name="dirBean" property="base.fdrParentSid" />" data-extension="<bean:write name="dirBean" property="base.fflExt" />">
            <td class="txt_c js_tableTopCheck cursor_p">
              <label class="w100 h100">
                <html:multibox name="fil330Form" property="fil330SelectDel">
                  <bean:write name="dirBean" property="base.feaSid" />
                </html:multibox>
              </label>
            </td>
            <td class="txt_l">
               <bean:write name="dirBean" property="base.feaSid" />
            </td>

            <td class="txt_l">
              <logic:notEmpty name="dirBean" property="base.fdrBiko">
                <bean:define id="biko" name="dirBean" property="base.fdrBiko" />
                <%
                  String tmpText = (String) pageContext.getAttribute("biko", PageContext.PAGE_SCOPE);
                              String tmpText2 = jp.co.sjts.util.StringUtilHtml.transToHTml(tmpText);
                %>
                <span class="js_nlTooltips"><gsmsg:write key="cmn.memo" />:<%=tmpText2%></span>
                <%--
                  filTipList.add("fdrsid" + String.valueOf(
                                  dirBean.getBase().getFeaSid());
                --%>
              </logic:notEmpty>
              <a href="#!" class="js_dlLink" data-feasid="<bean:write name="dirBean" property="base.feaSid"/>" data-fileextension="<bean:write name="dirBean" property="fileExt"/>">
                <bean:write name="dirBean" property="path" /><bean:write name="dirBean" property="base.fdrName" />
              </a>
              <% if (String.valueOf(fileName).toUpperCase().matches(".*\\.PNG$|.*\\.JPG$|.*\\.JPEG$|.*\\.PDF$")) { %>
              <span class="ml5 cursor_p js_preview"  data-feasid="<bean:write name="dirBean" property="base.feaSid"/>" data-fileextension="<bean:write name="dirBean" property="fileExt"/>">
                <img class="btn_classicImg-display " src="../common/images/classic/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
                <img class="btn_originalImg-display " src="../common/images/original/icon_preview.png" alt="<gsmsg:write key="cmn.preview" />">
              </span>
              <% } %>
            </td>
            <td class="txt_r no_w">
              <span>
                <bean:write name="dirBean" property="fileSize" />
              </span>
            </td>
            <td class="txt_c ">
              <span>
                <bean:write name="dirBean" property="edateString" />
              </span>
            </td>
            <td class="txt_l">
              <logic:equal name="dirBean" property="updateUser.usrJkbn" value="<%=String.valueOf(GSConst.JTKBN_DELETE)%>">
                <span>
                  <s><bean:write name="dirBean" property="updateUser.usiName" /><s>
                </span>
              </logic:equal>
              <logic:notEqual name="dirBean" property="updateUser.usrJkbn" value="<%=String.valueOf(GSConst.JTKBN_DELETE)%>">
                <span class="<%=UserUtil.getCSSClassNameNormal(dirBean.getUpdateUser().getUsrUkoFlg())%>">
                  <bean:write name="dirBean" property="updateUser.usiName" />
                </span>
              </logic:notEqual>
            </td>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>
    <!-- ページング -->
    <logic:notEmpty name="fil330Form" property="fil330FileListPageLabel">
      <div class="txt_r w100">
        <span class="paging">
           <button type="button" class="webIconBtn js_pagingDec">
             <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous.page" />">
             <i class="icon-paging_left"></i>
           </button>
            <html:select name="fil330Form" property="fil330FileListPageNo" styleClass="paging_combo js_pagingSel">
              <html:optionsCollection name="fil330Form" property="fil330FileListPageLabel" value="value" label="label" />
            </html:select>
           <button type="button" class="webIconBtn js_pagingInc">
             <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next.page" />">
             <i class="icon-paging_right"></i>
           </button>
        </span>
      </div>
    </logic:notEmpty>
  </div>
</logic:notEmpty>
