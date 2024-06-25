<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<%@ page import="jp.groupsession.v2.cmn.GSConst" %>
<%@ page import="jp.groupsession.v2.cht.GSConstChat" %>
<bean:define id="info" name="cht010Form" property="cht010ChtInfMdl"/>
<div id="groupConfigPop"  class="display_n  chat_dialog">
  <span class="js_generalSid_hidden"></span>
  <span class="js_adminSid_hidden"></span>
  <span class="js_cgiSid_hidden"></span>
  <table class="w100 p5">
    <tr>
      <td class="w35 txt_t">
        <table class="table-top w100 m0">
          <tr>
            <th class="fw_b txt_c">
              <gsmsg:write key="cmn.grouplist" />
            </th>
          </tr>
          <tr>
            <td class="p0 hp550 txt_t js_leftMenu">
              <div class="p5">
                <span class="js_grpconfAllDsp"></span>
                <div class="verAlignMid">
                  <html:checkbox name="cht010Form" property="cht010GrpConfDspArcKbn" styleId="archiveKbn"
                    styleClass="checkbox_position_2 mr5 js_grpconf_searchArchive" value="<%=String.valueOf(GSConstChat.CHAT_ARCHIVE_MODE)%>"/>
                  <label for="archiveKbn"><gsmsg:write key="cht.cht010.15" /></label>
                </div>
                <div class="pos_rel cursor_p">
                  <html:text name="cht010Form" property="cht010GrpConfSearchKeyword" maxlength="100" styleId="searchform"
                    styleClass="w100 js_grpconfSearchKeyword grpconfSearchKeyword" />
                  <span class="cursor_p js_grpconf_search searchIcon-submit"></span>
                </div>
              </div>
              <div class="js_grpconfGrouplist mt10 hp450 ofx_h">
              </div>
            </td>
          </tr>
          <tr>
            <td class="w100 js_grpconfAddGroup cht_grpConfAddGroup cl_fontWeek fw_b p0 js_leftMenu">
              ＋<gsmsg:write key="user.37" />
            </td>
          </tr>
        </table>
      </td>
      <td class="w65 js_rightPosition txt_c">
        <div class="js_groupconfRightarea">
        </div>
      </td>
    </tr>
  </table>
</div>
 <!--グループ作成 -->
<div id="addGroupKakuninChtPop" class="display_n txt_c">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="cht.cht010.20" />
      </td>
    </tr>
  </table>
</div>
 <!--グループ編集 -->
<div id="editGroupKakuninChtPop" class="display_n txt_c">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="cht.cht010.21" />
      </td>
    </tr>
  </table>
</div>
<!--グループ削除 -->
<div id="delGroupKakuninChtPop" class="display_n txt_c">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <gsmsg:write key="cht.cht010.22" />
      </td>
    </tr>
  </table>
</div>
<!--エラー -->
<div id="errorGroupChtPop" class="display_n txt_c">
  <table class="w100 h100">
    <tr>
      <td class="w10">
        <img class="header_pluginImg-classic" src="../main/images/classic/header_info.png">
        <img class="header_pluginImg" src="../common/images/original/icon_info_32.png">
      </td>
      <td class="txt_l w90 pl20 fs_14">
        <span class="js_grpconf_error errorMsgStr"></span>
      </td>
    </tr>
  </table>
</div>

