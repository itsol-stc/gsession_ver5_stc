<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<div id="FreezePane">
<div id="InnerFreezePane"> </div>

<html:form action="common/cmn110" enctype="multipart/form-data">

<input type="hidden" name="CMD">
<html:hidden property="cmn110parentListName" />
<html:hidden property="cmn110pluginId" />
<html:hidden property="cmn110Decision" />
<html:hidden property="cmn110Mode" />
<html:hidden property="cmn110TempDirPlus" />
<html:hidden property="tempDirId" />
<html:hidden property="splitStr" />

<input type="hidden" name="cmn110uploadType" value="0">
<input type="hidden" name="cmn110freezeText" value="<gsmsg:write key="cmn.cmn110.4" />">

<span id="cmn110fileDataArea">
<logic:notEmpty name="cmn110Form" property="cmn110tempName" scope="request">
  <logic:iterate id="fileName" name="cmn110Form" property="cmn110tempName" scope="request">
    <input type="hidden" name="cmn110tempName" value="<bean:write name="fileName"/>">
  </logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="cmn110Form" property="cmn110tempSaveName" scope="request">
  <logic:iterate id="fileName" name="cmn110Form" property="cmn110tempSaveName" scope="request">
    <input type="hidden" name="cmn110tempSaveName" value="<bean:write name="fileName"/>">
  </logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="cmn110Form" property="fileList" scope="request">
  <logic:iterate id="item" name="cmn110Form" property="fileList" scope="request">
    <input type="hidden" name="fileList" value="<bean:write name="item"/>">
  </logic:iterate>
</logic:notEmpty>
</span>

<html:hidden property="cmn110PrjUseFlg" />

<!-- BODY -->
<table width="545px" align="center">
  <logic:notEqual name="cmn110Form" property="strMaxSize" value="0">
  <tr id="cmn110DragArea">
  <td>
    <div id="uploadArea" draggable="true" class="fileDropArea" style="height: 120px;">
    </div>
  </td>
  </tr>
  </logic:notEqual>
  </tr>
</table>

</html:form>
</div>