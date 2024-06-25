<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="jp.groupsession.v2.cmn.ConfigBundle" %>
<%@ page import="jp.groupsession.v2.cmn.GSConst" %>


<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ctag-css.tld" prefix="theme" %>
<%@ taglib uri="/WEB-INF/ctag-message.tld" prefix="gsmsg" %>
<%@ taglib uri="/WEB-INF/ctag-jsmsg.tld" prefix="gsjsmsg" %>
<% String gsurl = jp.groupsession.v2.cmn.GSConst.GS_HOMEPAGE_URL; %>

<html:html>
<head>
<title>スマートフォン専用画面は廃止となりました</title>
<LINK REL="SHORTCUT ICON" href="../common/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" Content="no-cache">
<META HTTP-EQUIV="Cache-Control" Content="no-cache">
<META HTTP-EQUIV="Expires" Content="-1">

<style>
    
  body {
  margin: 0;
  padding: 0;
  background-color: #fff;
  color: #383839;
  font-size: 16px;
  line-height: 160%;
  text-align: center;
  font-feature-settings : "palt";
  letter-spacing: 0.1em;
  }
  a, a:link, a:visited {
    color: #0866BF;
  }
  a:hover {
    color: #0093bd;
    transition: 0.5s;
  }
  .line {
  width: 100%;
  background:repeating-linear-gradient(-45deg, #bde8f4 0, #bde8f4 15px, white 15px, white 30px);
  /*background:repeating-linear-gradient(-45deg, #14a2f2 0, #14a2f2 1px, white 1px, white 4px);*/
  height: 20px;
   }
  .bottom0 {
      position: fixed;
    bottom: 0;
   }
  
  img{
       margin-top:50px;
      }
  
      li{
          list-style: none;
      }
  h1{
  margin: 30px auto;
  color: #14a2f2;
  line-height: 140%;
  font-size: 1.5rem;
      }
      
  .box{
      padding: 0 10px;
      }
  
  .login_box{
  width: clamp( 320px, 450px, 80%);
  margin: 20px auto;
  padding: 20px 30px;
  text-align:center;
  clear:both;
  background-color:rgba(225,225,220,0.72);
  box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
  z-index: 100;
  border-radius: 10px;
  }
  
  .login_box p{
    text-align: left;
    margin: 10px auto 0 auto;
    padding: 0;
    font-weight: bold;
  }
  
  .app_box{
  width: clamp( 320px, 450px, 80%);
  margin: 20px auto;
  padding: 20px 30px;
  text-align:center;
  background-color:rgba(225,225,220,0.72);
  box-shadow: 0px 3px 5px 0px rgba(0, 0, 0, 0.2);
  z-index: 100;
  border-radius: 10px;
  }
  
  .store_icon{
      display: flex;
      justify-content: center;
      padding: 0;
      margin: 0;
      }
  
  .store_icon li {
    border: 3px #d7d7cf solid;
    border-radius: 5px;
    padding:10px;
    margin: 5px 10px;
      background: #fff;
  }
  .store_icon img {
    width: 120px;
    height: auto;
      padding: 0;
      margin: 0;
  }
      
      }
  </style>
</head>


<body>
  <div class="line"></div>
      
  <div class="box">
  <img src="../mobile/images/logo.png" alt="GroupSession">
  <h1>スマートフォン専用画面は廃止となりました</h1>
      
  <p>
  ご利用者さまにはご不便をおかけしますが、
  PC用表示でご利用いただくか、<br>
  GSモバイルアプリのインストールをお願いいたします。 <br>
  </p>
      
  <div class="login_box">
  PC用表示でのログインは<a href="../common/cmn001.do">こちら</a>から
   </div>
  
  <div class="app_box">
  GSモバイルアプリのインストールはこちらから
       <ul class="store_icon">
          <li>
       <a href="https://apps.apple.com/jp/app/gs%E3%83%A2%E3%83%90%E3%82%A4%E3%83%AB/id1636232885" target="_blank">
       <img src="../mobile/images/app_store.png" alt="appstore GSモバイル"></a>
       <br>
  <img src="../mobile/images/iOS_QR540.png" alt="appstore GSモバイル">
      </li>
  
      <li>
        <a href="https://play.google.com/store/apps/details?id=jp.co.sjts.mobile.gsapp" target="_blank"><img src="../mobile/images/google-play-badge.png" alt="googleplay GSモバイル"></a>
       <br>
  <img src="../mobile/images/Android_QR540.png" alt="googleplay GSモバイル">
    </li>
             </ul>
  </div>
      
  </div>
  <div class="line bottom0"></div>
</body>
</html:html>