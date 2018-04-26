<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" isELIgnored="false"%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="${ctx}/js/tdvideo_lite.js"></script>
    <script src="${ctx}/js/common/common.js" type="text/javascript"></script>
    <title>网络视频服务器</title>
    <style type="text/css">
        body {
            font-size:9pt;
            line-height:120%;
            font-family: "Helvetica", "Arial", "sans-serif";
        }
        table {
            font-size: 9pt;

        }
        input {
            font-weight: normal;
            font-size: 9pt;
            font-family: "Helvetica", "Arial", "sans-serif";
        }
        select {
            font-weight: normal;
            font-size: 9pt;
            font-family: "Helvetica", "Arial", "sans-serif";
        }
        a {
            color: #2C3B74;
            text-transform: none;
            text-decoration: none;
        }
        a:hover {
            color: #FFFF00;
            cursor: hand;
            text-decoration: none;
        }
        a:active {
            color: #000000;
        }

    </style>
    <script language="JavaScript" type="text/JavaScript">
        <!--
        function MM_reloadPage(init) {  //reloads the window if Nav4 resized
            if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
                document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
            else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
        }
        MM_reloadPage(true);
        //-->
    </script>
    <script language="javascript" event="OnObsClicked(_ulObsID)" for="TiandyVideo">
        DHT_Ch_Change(_ulObsID);
    </script>
    <script language="javascript" event="OnPanelDblClick" for="TiandyVideo">
        DHT_Panel_DblClick();
    </script>
    <script language="javascript" event="OnRecErr(iObsID)" for="TiandyVideo">
        DHT_RecError(iObsID);
    </script>
    <script language="javascript" event="LogonMsg(lResult,iLogonID)" for="TiandyVideo">
        DHT_Install(lResult,iLogonID);
    </script>
    <script language="javascript" event="OnError(iStatus,iID)" for="TiandyVideo">
        OnError(iStatus,iID);
    </script>
    <script language="javascript" event="OnInit(iID)" for="TiandyVideo">
        LoadHtml();
        loadUser();
    </script>
    <script type="text/javascript">
        function loadUser() {
            var ip = "<%=request.getAttribute("ip")%>";
            var port = '<%=request.getAttribute("port")%>';
            var username = "<%=request.getAttribute("username")%>";
            var pwd = "<%=request.getAttribute("pwd")%>";

            DHT_LogOn(ip,port,username,pwd);
        }

    </script>
</head>

<body  BGCOLOR="#ffffff" LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0" onUnload="UnLoadHtml()">

<div id="lyMain" style="position:absolute; width:547px; height:469px;" align="center">
    <object classid="clsid:F4986929-536B-40A8-9398-7CEE72DBA480"
            codebase="Swallow.cab#version=3,3,0,5"
            name="TiandyVideo"
            width=547
            height=469
            id="TiandyVideo">
    </object>
</div>

</body>
</html>