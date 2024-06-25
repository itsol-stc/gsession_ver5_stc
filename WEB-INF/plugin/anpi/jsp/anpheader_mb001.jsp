<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html:hidden property="anpiSid" />
<html:hidden property="userSid" />

<%
if (request != null) {
    if (BrowserUtil.isAndroid(request)
            || BrowserUtil.isIPhone(request)
            || BrowserUtil.isIPod(request)
            ) {
%>

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" />

<%

    }
}

%>