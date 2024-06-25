<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.tcd.model.TcdTotalValueModel" %>
<%@ page import="jp.groupsession.v2.tcd.model.TcdHolidayInfModel" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<!DOCTYPE html>

<html:html>
<head>
  <LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/layout.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <link rel=stylesheet href='../common/css/all.css?<%= GSConst.VERSION_PARAM %>' type='text/css'>
  <theme:css filename="theme.css"/>

  <script src='../common/js/jquery-ui-1.8.16/jquery-1.6.2.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src='../common/js/jquery-ui-1.8.16/ui/jquery-ui-1.8.16.custom.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../timecard/js/tcd190.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src="../common/js/grouppopup.js?<%= GSConst.VERSION_PARAM %>"></script>
  <script src='../common/js/jquery-3.3.1.min.js?<%= GSConst.VERSION_PARAM %>'></script>
  <script src="../common/js/tableTop.js? <%= GSConst.VERSION_PARAM %>"></script>

  <title>GROUPSESSION <gsmsg:write key="tcd.51" /></title>

</head>

<body>
<html:form action="/timecard/tcd190">
<input type="hidden" name="CMD" value="">
<html:hidden property="backScreen" />
<html:hidden property="tcdBackScreen" />
<html:hidden property="year" />
<html:hidden property="month" />
<html:hidden property="tcdDspFrom" />
<html:hidden property="usrSid" />
<html:hidden property="sltGroupSid" />
<html:hidden property="tcdDspFrom" />
<input type="hidden" name="tcd190sortKey" value="<bean:write name="tcd190Form" property="tcd190sortKey" />">
<input type="hidden" name="tcd190order" value="<bean:write name="tcd190Form" property="tcd190order" />">
<input type="hidden" name="tcd200Name">
<input type="hidden" name="tcd200Group">
<input type="hidden" name="tcd200Nendo">

<%@ include file="/WEB-INF/plugin/common/jsp/header001.jsp" %>

<div class="kanriPageTitle mrl_auto w80">
  <ul>
    <li>
      <img src="../common/images/classic/icon_ktool_large.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg-classic">
      <img src="../common/images/original/icon_ktool_32.png" alt="<gsmsg:write key="cmn.admin.setting" />" class="header_pluginImg">
    </li>
    <li><gsmsg:write key="cmn.admin.setting" /></li>
    <li class="pageTitle_subFont"><span class="pageTitle_subFont-plugin"><gsmsg:write key="tcd.50" /></span><gsmsg:write key="tcd.212"/></li>
    <li>
      <div>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.import" />" onClick="buttonPush('import');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_csv.png" alt="<gsmsg:write key="cmn.import" />">
          <gsmsg:write key="cmn.import" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.add" />" onClick="addYukyu();">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_add.png" alt="<gsmsg:write key="cmn.add" />">
          <gsmsg:write key="cmn.add" />
        </button>
        <button type="button" class="baseBtn" value="<gsmsg:write key="cmn.back" />" onClick="buttonPush('tcd190back');">
          <img class="btn_classicImg-display" src="../common/images/classic/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <img class="btn_originalImg-display" src="../common/images/original/icon_back.png" alt="<gsmsg:write key="cmn.back" />">
          <gsmsg:write key="cmn.back" />
        </button>
      </div>
    </li>
  </ul>
</div>

<div class="wrapper mrl_auto w80">
  <logic:messagesPresent message="false">
    <html:errors/>
  </logic:messagesPresent>
  
  <div class="txt_l">
    <gsmsg:write key="tcd.tcd190.02" />
    <html:select property="tcd190nendo" styleClass="mr20" onchange="changeCombo();">
      <html:optionsCollection property="tcdNendoList" value="value" label="label" />
    </html:select>
    <gsmsg:write key="cmn.show.group" />
    <html:select property="tcd190group" onchange="changeCombo();">
      <html:optionsCollection property="tcdGroupList" value="value" label="label" />
    </html:select>
    <button type="button" class="iconBtn-border ml5" value="&nbsp;&nbsp;" onclick="openGroupWindow(this.form.tcd190group, 'tcd190group', '0');" id="tcd190GroupBtn">
      <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
      <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="<gsmsg:write key="cmn.member" />">
    </button>
  </div>
  
  <logic:notEmpty name="tcd190Form" property="tcd190pageList">
    <div class="paging js_paging ml_auto js_paging-mail">
      <button type="button" class="js_paging_prevBtn webIconBtn" onclick="buttonPush('page_left');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select property="tcd190page" styleClass="paging_combo js_paging_combo ml0 mr0">
        <html:optionsCollection property="tcd190pageList" value="value" label="label" />
      </html:select>
      <button type="button" class="js_paging_nextBtn webIconBtn" onclick="buttonPush('page_right');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>
  
  <logic:notEmpty name="tcd190Form" property="tcd190YukyuModelList">
    <table class="mt20 table-top">
      <tr>
        <logic:equal name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>">
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.name" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
          </logic:equal>
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.name" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>">
          <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SIMEI) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.name" /></a></th>
        </logic:notEqual>
        
        <logic:equal name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>">
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
          </logic:equal>
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.employee.staff.number" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>">
          <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_SYAINNO) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.employee.staff.number" /></a></th>
        </logic:notEqual>        
        
        <logic:equal name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYUDAYS) %>">
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYUDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="cmn.entry" /><gsmsg:write key="tcd.210" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
          </logic:equal>
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYUDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.entry" /><gsmsg:write key="tcd.210" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYUDAYS) %>">
          <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYUDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="cmn.entry" /><gsmsg:write key="tcd.210" /></a></th>
        </logic:notEqual>
        
        <logic:equal name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEDAYS) %>">
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="tcd.tcd190.04" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
          </logic:equal>
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.tcd190.04" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEDAYS) %>">
          <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEDAYS) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.tcd190.04" /></a></th>
        </logic:notEqual>
        
        <logic:equal name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEPERCENT) %>">
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEPERCENT) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>')"><gsmsg:write key="tcd.tcd190.05" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.22" /></span><span class="original-display txt_m"><i class="icon-sort_up"></i></span></a></th>
          </logic:equal>
          <logic:equal name="tcd190Form" property="tcd190order" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_DESC) %>">
            <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEPERCENT) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.tcd190.05" /><span class="classic-display"><gsmsg:write key="tcd.tcd040.23" /></span><span class="original-display txt_m"><i class="icon-sort_down"></i></span></a></th>
          </logic:equal>
        </logic:equal>
        <logic:notEqual name="tcd190Form" property="tcd190sortKey" value="<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEPERCENT) %>">
          <th class="table_title-color w20"><a href="#!" onclick="return onTitleLinkSubmit('<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.SORT_YUKYU_USEPERCENT) %>', '<%= String.valueOf(jp.groupsession.v2.cmn.GSConstTimecard.ORDER_KEY_ASC) %>')"><gsmsg:write key="tcd.tcd190.05" /></a></th>
        </logic:notEqual>
        
      </tr>
      
      <logic:notEmpty name="tcd190Form" property="tcd190YukyuModelList">
        <logic:iterate id="emp" name="tcd190Form" property="tcd190YukyuModelList">
          <tr class="js_listHover cursor_p js_yukyu-line">
            <input type="hidden" class="js_userSid" value="<bean:write name="emp" property="usrSid" />">
            <bean:define id="mukouserClass" value="" />
            <logic:equal name="emp" property="userUkoFlg" value="1"><bean:define id="mukouserClass" value="mukoUser" /></logic:equal>
            <td class="<%=mukouserClass %>"><bean:write name="emp" property="name"/></td>
            <td><bean:write name="emp" property="employeeNumber"/></td>
            <logic:equal name="emp" property="yukyuDays" value="0.0">
              <td class="txt_c"><gsmsg:write key="cmn.operand.minus" /></td>
            </logic:equal>
            <logic:notEqual name="emp" property="yukyuDays" value="0.0">
              <td class="txt_r"><bean:write name="emp" property="yukyuDays" /></td>
            </logic:notEqual>
            <td class="txt_r"><bean:write name="emp" property="yukyuUseDays" /></td>
            <logic:equal name="emp" property="yukyuUsePercent" value="0.0">
              <td class="txt_c"><gsmsg:write key="cmn.operand.minus" /></td>
            </logic:equal>
            <logic:notEqual name="emp" property="yukyuUsePercent" value="0.0">
              <td class="txt_r"><bean:write name="emp" property="yukyuUsePercent" />%</td>
            </logic:notEqual>
          </tr>
        </logic:iterate>
      </logic:notEmpty>
    </table>
  </logic:notEmpty>
  
  <logic:notEmpty name="tcd190Form" property="tcd190pageList">
    <div class="paging js_paging ml_auto js_paging-mail">
      <button type="button" class="js_paging_prevBtn webIconBtn" onclick="buttonPush('page_left');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_l.png" alt="<gsmsg:write key="cmn.previous" />">
        <i class="icon-paging_left"></i>
      </button>
      <html:select property="tcd190page" styleClass="paging_combo js_paging_combo ml0 mr0">
        <html:optionsCollection property="tcd190pageList" value="value" label="label" />
      </html:select>
      <button type="button" class="js_paging_nextBtn webIconBtn" onclick="buttonPush('page_right');">
        <img class="m0 btn_classicImg-display" src="../common/images/classic/icon_arrow_r.png" alt="<gsmsg:write key="cmn.next" />">
        <i class="icon-paging_right"></i>
      </button>
    </div>
  </logic:notEmpty>
</div>
</html:form>
<%@ include file="/WEB-INF/plugin/common/jsp/footer001.jsp" %>
</body>
</html:html>
