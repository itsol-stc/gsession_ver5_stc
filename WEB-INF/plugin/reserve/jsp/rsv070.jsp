<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme"%>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg"%>
<%@ page import="jp.groupsession.v2.cmn.GSConst"%>
<%@ page import="jp.groupsession.v2.cmn.GSConstReserve"%>

<html:html>
<head>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<title>GROUPSESSION <gsmsg:write key="cmn.reserve" /> [ <gsmsg:write key="reserve.rsv070.1" /> ]
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src='../common/js/jquery-1.5.2.min.js?<%=GSConst.VERSION_PARAM%>'></script>

<link rel=stylesheet href='../reserve/css/reserve.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/bootstrap-reboot.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/layout.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<link rel=stylesheet href='../common/css/all.css?<%=GSConst.VERSION_PARAM%>' type='text/css'>
<theme:css filename="theme.css" />
</head>

<body onload="">
  <html:form action="/reserve/rsv070">
    <input type="hidden" name="CMD" value="">

    <div class="pageTitle">
      <ul>
        <li>
          <img class="header_pluginImg-classic" src="../reserve/images/classic/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
          <img class="header_pluginImg" src="../reserve/images/original/header_reserve.png" alt="<gsmsg:write key="cmn.reserve" />">
        </li>
        <li>
          <gsmsg:write key="cmn.reserve" />
        </li>
        <li class="pageTitle_subFont">
          <gsmsg:write key="reserve.rsv070.1" />
        </li>
        <li>
          <div>
            <button type="button" value="<gsmsg:write key="cmn.close" />" class="baseBtn" onClick="window.close()">
              <img class="btn_classicImg-display" src="../common/images/classic/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
              <img class="btn_originalImg-display" src="../common/images/original/icon_close.png" alt="<gsmsg:write key="cmn.close" />">
              <gsmsg:write key="cmn.close" />
            </button>
          </div>
        </li>
      </ul>
    </div>

    <div class="wrapper">

      <table class="table-left" >
        <tr>
          <th class="w20 no_w" <span><gsmsg:write key="cmn.facility.group" /></span></th>
          <td class="txt_l w80">
            <span>
              <bean:write name="rsv070Form" property="rsv070GrpName" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.47" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv070Form" property="rsv070SisetuKbnName" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="reserve.55" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv070Form" property="rsv070SisetuId" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.facility.name" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv070Form" property="rsv070SisetuName" />
            </span>
          </td>
        </tr>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.asset.register.num" />
            </span>
          </td>
          <td class="txt_l">
            <span>
              <bean:write name="rsv070Form" property="rsv070SisanKanri" />
            </span>
          </td>
        </tr>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName4">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName4" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv070Form" property="rsv070Prop4Value" />
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName5">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName5" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv070Form" property="rsv070Prop5Value" />
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName1">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName1" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv070Form" property="rsv070Prop1Value" />
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName2">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName2" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv070Form" property="rsv070Prop2Value" value="<%=String.valueOf(GSConstReserve.PROP_KBN_KA)%>">
                  <gsmsg:write key="cmn.accepted" />
                </logic:equal>
                <logic:equal name="rsv070Form" property="rsv070Prop2Value" value="<%=String.valueOf(GSConstReserve.PROP_KBN_HUKA)%>">
                  <gsmsg:write key="cmn.not" />
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName3">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName3" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv070Form" property="rsv070Prop3Value" value="<%=String.valueOf(GSConstReserve.PROP_KBN_KA)%>">
                  <gsmsg:write key="cmn.accepted" />
                </logic:equal>
                <logic:equal name="rsv070Form" property="rsv070Prop3Value" value="<%=String.valueOf(GSConstReserve.PROP_KBN_HUKA)%>">
                  <gsmsg:write key="cmn.not" />
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName7">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName7" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv070Form" property="rsv070Prop7Value" value="<%=String.valueOf(GSConstReserve.PROP_KBN_KA)%>">
                  <gsmsg:write key="cmn.accepted" />
                </logic:equal>
                <logic:equal name="rsv070Form" property="rsv070Prop7Value" value="<%=String.valueOf(GSConstReserve.PROP_KBN_HUKA)%>">
                  <gsmsg:write key="cmn.not" />
                </logic:equal>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:equal name="rsv070Form" property="rsv070apprDspFlg" value="true">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="reserve.appr.set.title" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:equal name="rsv070Form" property="rsv070apprKbnFlg" value="true">
                  <gsmsg:write key="reserve.appr.set.kbn1" />
                </logic:equal>
                <logic:notEqual name="rsv070Form" property="rsv070apprKbnFlg" value="true">
                  <gsmsg:write key="reserve.appr.set.kbn2" />
                </logic:notEqual>
              </span>
            </td>
          </tr>
        </logic:equal>

        <logic:notEmpty name="rsv070Form" property="rsv070PropHeaderName6">
          <tr>
            <th class="no_w">
              <span>
                <bean:write name="rsv070Form" property="rsv070PropHeaderName6" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <logic:notEmpty name="rsv070Form" property="rsv070Prop6Value">
                  <bean:write name="rsv070Form" property="rsv070Prop6Value" />
                  <gsmsg:write key="cmn.days.after" />
                </logic:notEmpty>
              </span>
            </td>
          </tr>
        </logic:notEmpty>

        <tr>
          <th class="no_w">
            <span>
              <gsmsg:write key="cmn.memo" />
            </span>
          </th>
          <td class="txt_l">
            <span>
              <bean:write name="rsv070Form" property="rsv070Biko" filter="false" />
            </span>
          </td>
        </tr>

        <logic:notEmpty name="rsv070Form" property="rsv070SisetuBinDataList" scope="request">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="reserve.59" />
              </span>
            </th>
            <td class="txt_l pb0">
              <logic:iterate id="sisetuBinMdl" name="rsv070Form" property="rsv070SisetuBinDataList" scope="request">
                <div class="mb5 display_flex">
                  <img src="../reserve/rsv010.do?CMD=getImageFileSisetu&rsvSelectedSisetuSid=<bean:write name="rsv070Form" property="rsv070RsdSid" />&rsv010BinSid=<bean:write name="sisetuBinMdl" property="binSid" />" alt="<gsmsg:write key="reserve.17" />" class="wp130 mr5">
                  <logic:equal name="rsv070Form" property="rsv070AdmFlg" value="true">
                    <div class="display_flex">
                      <gsmsg:write key="fil.9" /><gsmsg:write key="cmn.colon" /><bean:write name="sisetuBinMdl" property="binFileName" />
                    </div>
                  </logic:equal>
                </div>
              </logic:iterate>
            </td>
          </tr>
        </logic:notEmpty>

        <logic:notEmpty name="rsv070Form" property="rsv070PlaceBinDataList" scope="request">
          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="reserve.location.comments" />
              </span>
            </th>
            <td class="txt_l">
              <span>
                <bean:write name="rsv070Form" property="rsv070PlaComment" />
              </span>
            </td>
          </tr>

          <tr>
            <th class="no_w">
              <span>
                <gsmsg:write key="reserve.60" />
              </span>
            </th>
            <td class="txt_l pb0">
              <logic:iterate id="placeBinMdl" name="rsv070Form" property="rsv070PlaceBinDataList" scope="request" indexId="idx">
                <div class="mb5 display_flex">
                  <a href="../reserve/rsv010.do?CMD=getImageFileSisetu&rsvSelectedSisetuSid=<bean:write name="rsv070Form" property="rsv070RsdSid" />&rsv010BinSid=<bean:write name="placeBinMdl" property="binSid" />" target="_blank">
                    <img src="../reserve/rsv010.do?CMD=getImageFileSisetu&rsvSelectedSisetuSid=<bean:write name="rsv070Form" property="rsv070RsdSid" />&rsv010BinSid=<bean:write name="placeBinMdl" property="binSid" />" alt="<gsmsg:write key="reserve.17" />" border="1" class="wp130 mr5">
                  </a>
                  <div>
                    <logic:equal name="rsv070Form" property="rsv070AdmFlg" value="true">
                      <div class="display_flex">
                        <gsmsg:write key="fil.9" /><gsmsg:write key="cmn.colon" />
                        <a href="../reserve/rsv010.do?CMD=getImageFileSisetu&rsvSelectedSisetuSid=<bean:write name="rsv070Form" property="rsv070RsdSid" />&rsv010BinSid=<bean:write name="placeBinMdl" property="binSid" />"  class="cl_linkDef" target="_blank">
                          <bean:write name="placeBinMdl" property="binFileName" />
                        </a>
                      </div>
                    </logic:equal>
                    <div class="display_flex">
                      <gsmsg:write key="cmn.form.label" /><gsmsg:write key="cmn.colon" /><bean:write name="rsv070Form" property="<%=\"rsv070PlaceFileComment\" + (idx+1) %>" />
                    </div>
                  </div>
                </div>
              </logic:iterate>
            </td>
          </tr>
        </logic:notEmpty>
      </table>
      </td>
      </tr>

      <logic:notEmpty name="rsv070Form" property="rsv070RsvList">
        <bean:size id="row" name="rsv070Form" property="rsv070RsvList" />
        <tr>
          <td>
            <table class="table-left" >
              <tr>
                <th class="w20 no_w" rowspan="<%= row %>">
                  <span>
                    <gsmsg:write key="reserve.rsv070.2" />
                  </span>
                </th>

                <logic:iterate id="rsvStr" name="rsv070Form" property="rsv070RsvList" indexId="cnt">
                  <logic:greaterThan name="cnt" value="0">
                    <tr>
                  </logic:greaterThan>
                  <td class="txt_l w80">
                    <span>
                      <bean:write name="rsvStr" />
                    </span>
                  </td>
                </logic:iterate>

              </tr>
            </table>
          </td>
        </tr>
      </logic:notEmpty>
      </table>
    </div>


  </html:form>
</body>
</html:html>