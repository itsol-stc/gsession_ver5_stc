<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.filekanri" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<bean:define id="cabId" name="filptl010Form" property="dspFcbSid" type="java.lang.Integer" />
<% String filFormId = "cabinet" + String.valueOf(cabId.intValue()); %>

<bean:define id="fcbSid" name="filptl010Form" property="dspFcbSid" type="java.lang.Integer" />
<% String cabSid = String.valueOf(fcbSid.intValue()); %>

<script src="../common/js/cmd.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../file/js/file.js?<%= GSConst.VERSION_PARAM %>"></script>
<script src="../common/js/treeview.js?<%=GSConst.VERSION_PARAM%>"></script>
<script src="../file/js/treeworker_ctrl.js?<%=GSConst.VERSION_PARAM%>"></script>
</head>

<body>
<logic:notEqual name="filptl010Form" property="dspFcbSid" value="0">
<html:form action="/file/filptl010" styleId="<%= filFormId %>">

<input type="hidden" name="CMD" value="">
<html:hidden property="dspFcbSid" />
<html:hidden property="selectDir" />
<html:hidden property="sepKey" />
<bean:define id="cabDirId" value=""/>
<bean:define id="cabDirName" value=""/>
<bean:define id="cabDirNoLink" value="0"/>
<bean:define id="sepKey" name="filptl010Form" property="sepKey" type="String"/>
<input type="hidden" name="backDsp" value="">
<input type="hidden" name="fil010SelectCabinet" value="">
<input type="hidden" name="fil010SelectDirSid" value="">

<logic:notEmpty name="filptl010Form" property="treeFormLv0">
<logic:iterate id="lv0" name="filptl010Form" property="treeFormLv0" type="String">
  <logic:empty name="cabDirId">
   <% String[] sp = lv0.split(sepKey);
   %>
   <bean:define id="cabDirId" value="<%=sp[0] %>"/>
   <bean:define id="cabDirName" value="<%=sp[2] %>"/>
  </logic:empty>
  <input type="hidden" name="tree<%= cabSid %>FormLv0" value="<bean:write name="lv0" />">
</logic:iterate>
</logic:notEmpty>

<table class="table-top main_oddcol_table w100 mb0">
  <tr>
    <th class="table_title-color txt_l" colspan=2>
      <img class="mainPlugin_icon" src="../file/images/classic/menu_icon_single.gif" alt="<gsmsg:write key="fil.1" />">
      <bean:write name="filptl010Form" property="filPtl010FcbName" />
    </th>
  </tr>
  <tr>
    <td>
      <div id="sidetreecontrol<%= cabSid %>">
        <a href="#!"><gsmsg:write key="cmn.all.close" /></a> | <a href="#!"><gsmsg:write key="cmn.all.open" /></a>
      </div>
      <div>
        <img class="classic-display mr5" src="../common/images/classic/icon_folder.png" alt=""><img class="original-display mr5" src="../common/images/original/icon_folder_box.png" alt=""><a href="javascript:(filptlTreeClick(<%=cabDirId %>, <%= filFormId %>))"><bean:write name="cabDirName"  filter="false"/></a>
      </div>
      <ul id="tree<%= cabSid %>" class="w100">
      </ul>
      <div class="js_tree_loader">
        <img class="btn_classicImg-display" src="../common/images/classic/icon_loader.gif" />
        <div class="loader-ball"><span class=""></span><span class=""></span><span class=""></span></div>
        <gsmsg:write key="cmn.loading"/>
      </div>

      <script type="text/javascript">
      $(function(){
        $("#tree<%= cabSid %>").treeview({
            allOpen:$('#sidetreecontrol<%= cabSid %> a').eq(1),
            allClose:$('#sidetreecontrol<%= cabSid %> a').eq(0),
            duration:'fast'
        });
        function __makeTree() {
          return createTreesValueArray('tree<%= cabSid %>FormLv', 1, 10);
        }
        var sepKey = document.getElementById("cabinet<%= cabSid %>").sepKey.value;
        var selectDir = document.getElementById("cabinet<%= cabSid %>").selectDir.value;

        $('#tree<%= cabSid %>').treeworker_ctrl().run({
            tree:__makeTree(),
            sepKey:sepKey,
            selectDir:0,
            treeClass:function (sp, selectDir) {
                this.name = sp[0];
                this.paths = new Array();
                this.open = false;
                this.label =  '';
                this.class = '';
                this.open = false;

                if (sp.length == 3) {
                    this.label =
                        "<img class=\"classic-display mr5\" src=\"../common/images/classic/icon_folder.png\"><img class=\"original-display mr5\" src=\"../common/images/original/icon_folder_box.png\"><a href=\"javascript:(filptlTreeClick(" + sp[0] + ", <%= filFormId %>))\">" + sp[2] + "</a>";

                  return this;
                }
                return false;
            }.toString()
        });
      });
      </script>
    </td>
  </tr>
</table>

</html:form>

<%
for (int idx = 1; idx <= 10; idx++) {
  String levelNum = String.valueOf(idx);
  String lvFormParamName = "treeFormLv" + levelNum ;
  String lvParamName = "tree" + cabSid + "FormLv" + levelNum ;
  String formName = "filptl010Form";
%>
  <logic:notEmpty name="<%= formName %>" property="<%= lvFormParamName %>">
    <logic:iterate id="lvID" name="<%= formName %>" property="<%= lvFormParamName %>">
      <span class="display_none" name="<%= lvParamName %>" data-treevalue="<bean:write name="lvID"/>"></span>
    </logic:iterate>
  </logic:notEmpty>
<% } %>

</logic:notEqual>

</body>
</html:html>
