<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<script type="text/javascript" src="../smail/js/smlatesakisel.js?<%= GSConst.VERSION_PARAM %>"></script>

<script type="text/javascript">
<!--
var msglist_smlatesakisel = (function () {
  //使用するメッセージキーの配列を作成
   var ret = new Array();
   ret['cmn.add'] = '<gsmsg:write key="cmn.add"/>';
   ret['cmn.close'] = '<gsmsg:write key="cmn.close"/>';
   ret['cmn.delete'] = '<gsmsg:write key="cmn.delete"/>';
   ret['cmn.group'] = '<gsmsg:write key="cmn.group"/>';
   ret['cmn.mygroup'] = '<gsmsg:write key="cmn.mygroup"/>';
   ret['cmn.sel.all.group'] = '<gsmsg:write key="cmn.sel.all.group"/>';
   ret['cmn.select'] = '<gsmsg:write key="cmn.select"/>';
   ret['sml.189'] = '<gsmsg:write key="sml.189"/>';
   ret['sml.196'] = '<gsmsg:write key="sml.196"/>';
   ret['sml.218'] = '<gsmsg:write key="sml.218"/>';
   ret['sml.220'] = '<gsmsg:write key="sml.220"/>';
   ret['sml.221'] = '<gsmsg:write key="sml.221"/>';


  return ret;
})();

-->
</script>