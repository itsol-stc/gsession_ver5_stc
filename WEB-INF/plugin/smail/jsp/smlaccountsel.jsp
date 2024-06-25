<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script type="text/javascript" src="../common/js/tableTop.js?<%= GSConst.VERSION_PARAM %>"></script>
<script type="text/javascript" src="../smail/js/smlaccountsel.js?<%= GSConst.VERSION_PARAM %>"></script>

<script type="text/javascript">
<!--
var msglist_smlacountsel = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['cmn.search'] = '<gsmsg:write key="cmn.search"/>';
   ret['cmn.close'] = '<gsmsg:write key="cmn.close"/>';
   ret['cmn.cancel'] = '<gsmsg:write key="cmn.cancel"/>';
   ret['cmn.delete'] = '<gsmsg:write key="cmn.delete"/>';
   ret['cmn.next'] = '<gsmsg:write key="cmn.next"/>';
   ret['cmn.next.page'] = '<gsmsg:write key="cmn.next.page"/>';
   ret['cmn.ok'] = '<gsmsg:write key="cmn.ok"/>';
   ret['cmn.previous'] = '<gsmsg:write key="cmn.previous"/>';
   ret['cmn.previous.page'] = '<gsmsg:write key="cmn.previous.page"/>';
   ret['sml.sml240.01'] = '<gsmsg:write key="sml.sml240.01" />';
   ret['cmn.mailaddress'] = '<gsmsg:write key="cmn.mailaddress" />';
   ret['cmn.employer'] = '<gsmsg:write key="cmn.employer" />';
   ret['sml.217'] = '<gsmsg:write key="sml.217" />';
   ret['cmn.received.date'] = '<gsmsg:write key="cmn.received.date" />';
   ret['cmn.memo'] = '<gsmsg:write key="cmn.memo" />';
   ret['sml.230'] = '<gsmsg:write key="sml.230" />';


  return ret;
})();

-->
</script>