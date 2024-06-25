<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>

<bean:define id="info" name="cht010Form" property="cht010ChtInfMdl"/>
<div id="groupInfoPop"  class="display_n">
  <div class="p5">
    <div class="js_chtGroupTitle display_n">
      <bean:write name="info" property="chatName"/>
    </div>
    <div>
      <div class="fw_b fs_14">
        <gsmsg:write key="cht.02" /><gsmsg:write key="cmn.colon" />
        <span class="js_chtGroupId ml10">
          <bean:write name="info" property="chatId"/>
        </span>
      </div>

      <div class="display_flex mt15">
        <div class="w50 pl5 pr5">
          <div class="fw_b fs_14">
            <gsmsg:write key="cht.03" />
          </div>
          <div class="js_chtAdminList mt10">
            <div class="js_chtAdminMemList">
              <div>
                <logic:iterate id="adminGroupList" name="info" property="adminGroup"><!--
               --><span class="ml10 display_inline-block">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="cmn.group">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="cmn.group"><!--
                 --><bean:write name="adminGroupList" property="grpName"/>
                 </span><!--
            --></logic:iterate>
              </div>
              <div class="mt5">
                <logic:iterate id="adminList" name="info" property="adminMember"><!--
               --><span class="ml10 display_inline-block">
                    <img class="btn_classicImg-display btnIcon-size" src="../common/images/classic/icon_user.png" alt="cmn.user">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_user.png" alt="cmn.user"><!--
                 --><bean:write name="adminList" property="usiSei"/>
                    <bean:write name="adminList" property="usiMei"/>
                  </span><!--
             --></logic:iterate>
              </div>
            </div>
          </div>
        </div>

        <div class="w50 pl5 pr5">
          <div class="fw_b fs_14">
            <gsmsg:write key="cht.04" />
          </div>
          <bean:size name="info" id="generalSize" property="generalMember" />
          <bean:size name="info" id="generalGroupSize" property="generalGroup" />
          <div class="js_chtGeneralList mt10">
            <div class="js_chtGeneralMemList">
              <logic:equal name="generalGroupSize" value="0">
                <logic:equal name="generalSize" value="0">
                  <span class="ml10"><gsmsg:write key="cmn.no" /></span>
                </logic:equal>
              </logic:equal>
              <div>
                <logic:iterate id="generalGroupList" name="info" property="generalGroup"><!--
               --><span class="ml10 display_inline-block">
                    <img class="btn_classicImg-display" src="../common/images/classic/icon_group.png" alt="cmn.group">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_group.png" alt="cmn.group"><!--
                 --><bean:write name="generalGroupList" property="grpName"/>
                  </span><!--
             --></logic:iterate>
              </div>
              <div class="mt5">
                <logic:iterate id="generalList" name="info" property="generalMember"><!--
               --><span class="ml10 display_inline-block">
                    <img class="btn_classicImg-display btnIcon-size" src="../common/images/classic/icon_user.png" alt="cmn.user">
                    <img class="btn_originalImg-display" src="../common/images/original/icon_user.png" alt="cmn.user"><!--
                 --><bean:write name="generalList" property="usiSei"/>
                   <bean:write name="generalList" property="usiMei"/>
                  </span><!--
                --></logic:iterate>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="js_bikoArea">
        <logic:notEqual name="info" property="chatBiko" value="">
          <div class="fw_b mt15 fs_14">
            <gsmsg:write key="cmn.memo" />
          </div>
          <div class="js_chtGroupBiko ml10 word_b-all">
            <bean:write name="info" property="chatBiko" filter="false"/>
          </div>
        </logic:notEqual>
      </div>
    </div>
  </div>
</div>