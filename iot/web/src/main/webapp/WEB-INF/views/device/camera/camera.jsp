<%@ page language="java" contentType="text/html; charset=gb2312" %>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="${ctx}/js/tdvideo.js"></script>
    <script language="javascript" type="text/javascript">
        ChangeLanguage();
    </script>
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

        .menubtn {
            border-style:ridge;
            border-width:1px;
            background-color: #909CC6;
            padding-top:3px;
        }
        .menubtndown {
            border-style:ridge;
            border-width:1px;
            background-color: #F5F9F5;
            padding-top:3px;
        }
        #lyTalk {
            cursor: hand;
            position:absolute;
            width:49;
            height:31;
            z-index:11;
            left: 395px;
            top: 14px;
        }
        #lyTalkMD {
            cursor: hand;
            position:absolute;
            width:49;
            height:31;
            z-index:12;
            left: 395px;
            top: 14px;
            visibility: hidden;
        }
        #lySnap {
            cursor: hand;
            position:absolute;
            width:49;
            height:31;
            z-index:13;
            left: 444px;
            top: 14px;
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

    <script language="javascript" type="text/javascript">
        ChangeLanguage();
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
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

        .menubtn {
            border-style:ridge;
            border-width:1px;
            background-color: #909CC6;
            padding-top:3px;
        }
        .menubtndown {
            border-style:ridge;
            border-width:1px;
            background-color: #F5F9F5;
            padding-top:3px;
        }
        #lyTalk {
            cursor: hand;
            position:absolute;
            width:49;
            height:31;
            z-index:11;
            left: 395px;
            top: 14px;
        }
        #lyTalkMD {
            cursor: hand;
            position:absolute;
            width:49;
            height:31;
            z-index:12;
            left: 395px;
            top: 14px;
            visibility: hidden;
        }
        #lySnap {
            cursor: hand;
            position:absolute;
            width:49;
            height:31;
            z-index:13;
            left: 444px;
            top: 14px;
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
    <script language="JavaScript" type="text/JavaScript">
        function disableRightClick(e)
        {
//            var message = "Right click disabled";
//
//            if(!document.rightClickDisabled) // initialize
//            {
//                if(document.layers)
//                {
//                    document.captureEvents(Event.MOUSEDOWN);
//                    document.onmousedown = disableRightClick;
//                }
//                else document.oncontextmenu = disableRightClick;
//                return document.rightClickDisabled = true;
//            }
//            if(document.layers || (document.getElementById && !document.all))
//            {
//                if (e.which==2||e.which==3)
//                {
//                    return false;
//                }
//            }
//            else
//            {
//                //alert(message);
//                return false;
//            }
        }
        disableRightClick();
    </script>
    <script language="javascript" event="onkeydown" for="txPassword">
        if(event.keyCode == 13)
            DHT_LogOn();
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
    <script language="javascript" event="QueryResult(lLogonID,	strResult)" for="TiandyVideo">
        UpdateQueryResult(lLogonID,	strResult);
    </script>
    <script language="javascript" event="OnParaChg(lLogonID,lChann,lMsg)" for="TiandyVideo">
        DHT_OnParaChange(lLogonID,lChann,lMsg);
    </script>
    <script language="javascript" event="OnDownLoad(downloadID,loadMsg)" for="TiandyVideo">
        PB_DownloadMsg(downloadID,loadMsg);
    </script>
    <script language="javascript" event="SysInfo(iLogonID,iCPUResult,iMRMResult,iFLASHResult)" for="TiandyVideo">
        DHT_SysInfoMsg(iLogonID,iCPUResult,iMRMResult,iFLASHResult);
    </script>
    <script language="javascript" event="UpgradeProg(iLogonID,strResult)" for="TiandyVideo">
        DHT_UpgradeProg(iLogonID,strResult);
    </script>
    <script language="javascript" event="StartPlay(ihr,iObsID)" for="TiandyVideo">
        StartPlay(ihr,iObsID);
    </script>
    <script language="javascript" event="OnPlayBack(iObsID,iStatus)" for="TiandyVideo">
        OnPlayBack(iObsID,iStatus);
    </script>
    <script language="javascript" event="OnTalk(iLogonID,iStatus)" for="TiandyVideo">
        OnTalk(iLogonID,iStatus);
    </script>
    <script language="javascript" event="OnError(iStatus,iID)" for="TiandyVideo">
        OnError(iStatus,iID);
    </script>
    <script language="javascript" event="DiskFormatProg(iLogonID,iProgress)" for="TiandyVideo">
        OnDiskFormatProg(iLogonID,iProgress);
    </script>
    <script language="javascript" event="OnLocalStoreProg(iLogonID,iProgress)" for="TiandyVideo">
        OnLocalStoreProg(iLogonID,iProgress);
    </script>
    <script language="javascript" event="OnFormatErr(iLogonID,iStatus)" for="TiandyVideo">
        OnFormatErr(iLogonID,iStatus);
    </script>
    <script language="javascript" event="OnPartErr(iLogonID,iStatus)" for="TiandyVideo">
        OnPartErr(iLogonID,iStatus);
    </script>
    <script language="javascript" event="OnInit(iID)" for="TiandyVideo">
//        LoadHtml();
//        loadUser();
    </script>
    <script>
        //------时间日期输入框-------
        now = new Date();
        function writeYears(obj, date)
        {
            var years = date.getFullYear();
            for (var i=years-50; i<years+50; i++)
            {
                var opt = document.createElement("option")
                opt.text = opt.value = i
                obj.add(opt)
            }
        }
        function writeMonths(obj)
        {
            for (var i=1; i<13; i++)
            {
                var opt = document.createElement("option")
                opt.text = opt.value = Math.floor(i/10) ? i : "0"+i
                obj.add(opt)
            }
        }
        function writeDate(obj)
        {
            for (var i=1; i<32; i++)
            {
                var opt = document.createElement("option")
                opt.text = opt.value = Math.floor(i/10) ? i : "0"+i
                obj.add(opt)
            }
        }
        function writeHours(obj)
        {
            for (var i=0; i<24; i++)
            {
                var opt = document.createElement("option")
                opt.text = opt.value = Math.floor(i/10) ? i : "0"+i
                obj.add(opt)
            }
        }
        function writeMinutes(obj)
        {
            for (var i=0; i<60; i++)
            {
                var opt = document.createElement("option")
                opt.text = opt.value = Math.floor(i/10) ? i : "0"+i
                obj.add(opt)
            }
        }
        function writeSeconds(obj)
        {
            for (var i=0; i<60; i++)
            {
                var opt = document.createElement("option")
                opt.text = opt.value = Math.floor(i/10) ? i : "0"+i
                obj.add(opt)
            }
        }
        function changeDate()
        {
            var c_year = eval("document.forms[0].c_year")
            var c_month = eval("document.forms[0].c_month")
            var c_day = eval("document.forms[0].c_day")
            writeDate(c_day, c_year.options(c_year.selectedIndex).value, c_month.options(c_month.selectedIndex).value)
            //writeTime(c_Hour,c_Minute,c_Second)
        }

        function returnByUrl(){
            LogOffAll(0);
            window.location.href="${ctx}/device/list";
        }

    </script >
    <script type="text/javascript">
        function loadUser() {
            var ip = "<%=request.getAttribute("ip")%>";
            var port = '<%=request.getAttribute("port")%>';
            var username = "<%=request.getAttribute("username")%>";
            var pwd = "<%=request.getAttribute("pwd")%>";

            document.getElementById("txHostIp").setAttribute("value", ip);
            document.getElementById("txPort").setAttribute("value", port);
            document.getElementById("txUserName").setAttribute("value", username);
            document.getElementById("txPassword").setAttribute("value",  pwd);
            LoadHtml();
            DHT_LogOn();
        }

    </script>
</head>

<body  BGCOLOR="#ffffff" LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0" onload="loadUser()" onUnload="UnLoadHtml()">
<div style="padding:10px">
    <img onclick="javascript:returnByUrl();" alt="返回" src="${ctx}/images/icon/return.png" style="display: inline;width: 25px;margin-bottom: 10px;cursor: pointer;">
    <a href="javascript:returnByUrl();" style="font-size: 12px;color: rgb(165, 165, 165);">返回</a>
</div>
<div id="lyMain" style="position:absolute; width:978px; height:578px; top:1px; left:50%; margin:0 0 0 -489px;" align="center">
    <table border="0" width="978" id="table1" height="578" cellspacing="0" cellpadding="0" bgcolor="#2C3B74">
        <tr>
            <td width="3"></td>
            <td width="652">
                <table border="0" width="100%" id="table2" height="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <td height="35">
                            <img border="0" src="${ctx}/images/camera/v-001gif.gif" width="184" height="35"><img border="0" src="${ctx}/images/camera/v-002gif.gif" width="306" height="35"><img border="0" src="${ctx}/images/camera/v-003.gif" width="199" height="35"></td>
                    </tr>
                    <tr>
                        <td>
                            <div align="center" id="lyVideoFrom">
                                <table border="1" width="100%" id="table3" height="100%" cellspacing="0" bordercolor="#FFFFFF" bgcolor="#000000" cellpadding="0">
                                    <tr>
                                        <td>
                                            <p align="center">
                                                <object classid="clsid:F4986929-536B-40A8-9398-7CEE72DBA480"
                                                        codebase="Swallow.cab#version=3,2,0,2"
                                                        name="TiandyVideo"
                                                        width=680
                                                        height=510
                                                        id="TiandyVideo">
                                                </object>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td height="53" width="97%">
                            <div id="lyMenuParent" style="cursor: hand; position:absolute; width:20px; height:20px; z-index:2; left: 20px; top: 549px;">
                                <div id="ly1pic" style="position:absolute;z-index:1; left: 78px; top: 14px;padding-top: 26px" onClick="ChangeMonitorNum(1)"><img alt="单画面" name="imgPic1" src="${ctx}/images/camera/v-008.gif" width="49" height="31"></div>
                                <div id="lyTalk" onClick="Menu_TalkStart()" style="position: absolute; left: 420px; top: 14px;padding-top: 26px"><img alt="开始对讲" name="imgTalkStart" src="${ctx}/images/camera/talk.gif" width="49" height="31"></div>
                                <div id="lyPlayBack" onClick="Menu_PlayBack()" style="position: absolute; left: 518px; top: 14px;padding-top: 26px">
                                    <img name="imgPlayBack" alt="录像回放" border="0" src="${ctx}/images/camera/play.gif" width="49" height="31"></div>
                                <div id="lySnap" style="position: absolute; left: 469px; top: 14px;padding-top: 26px"><img name="imgSnap" alt="抓拍" id="imgSnap" src="${ctx}/images/camera/snap.gif" width="49" height="31" onMouseDown="Menu_Snap(true)" onMouseUp="Menu_Snap(false)"></div>
                                <div id="lySnapMD" style="position: absolute; left: 469px; top: 14px; visibility: hidden;;padding-top: 26px"><img src="${ctx}/images/camera/snap_s.gif" width="49" height="31"></div>
                                <div id="lyTalkMD" onClick="Menu_TalkStop()" style="position: absolute; left: 420px; top: 14px;padding-top: 26px"><img name="imgTalkStop" alt="停止对讲" src="${ctx}/images/camera/talk_s.gif" width="49" height="31"></div>
                                <div id="ly4pic" style="cursor: hand; position:absolute;z-index:2; left: 127px; top: 14px;padding-top: 26px" onClick="ChangeMonitorNum(4)"><img name="imgPic4" alt="四画面" src="${ctx}/images/camera/v-009.gif" width="49" height="31"></div>
                                <div id="lyFullSrc" style="cursor: hand; position:absolute;z-index:3; left: 273px; top: 14px;padding-top: 26px" onClick="DHT_FullSrc_Click()"><img name="imgPicFull" alt="全屏" src="${ctx}/images/camera/v-010.gif" width="48" height="31"></div>
                                <div id="lyAudio" style="cursor: hand; position:absolute; z-index:4; left: 322px; top: 14px;padding-top: 26px" onClick="DHT_AudioStart()"><img name="imgAudioStart" alt="打开音频" src="${ctx}/images/camera/v-011.gif" width="49" height="31"></div>
                                <div id="lyRecorder" style="cursor: hand; position:absolute; z-index:5; left: 371px; top: 14px;padding-top: 26px" onClick="DHT_RecStart()"><img name="imgRecStart" alt="开始录像" src="${ctx}/images/camera/v-012.gif" width="49" height="31"></div>
                                <div id="ly1picMD" style="position:absolute; z-index:6; left: 78px; top: 14px; visibility: hidden;padding-top: 26px"><img src="${ctx}/images/camera/v-008-a.gif" width="49" height="31"></div>
                                <div id="ly16pic" style="position:absolute; z-index:6; left: 225px; top: 14px;padding-top: 26px" onClick="ChangeMonitorNum(16)"><img name="imgPic16" alt="十六画面" src="${ctx}/images/camera/16.gif" width="49" height="32"></div>
                                <div id="ly9pic" style="position:absolute; z-index:6; left: 176px; top: 14px;padding-top: 26px" onClick="ChangeMonitorNum(9)"><img name="imgPic9" alt="九画面" src="${ctx}/images/camera/9.gif" width="49" height="32"></div>
                                <div id="ly4picMD" style="position:absolute;z-index:7; left: 127px; top: 14px; visibility: hidden;"><img src="${ctx}/images/camera/v-009-a.gif" width="49" height="31"></div>
                                <div id="lyFullSrcMD" style="position:absolute; z-index:8; left: 225px; top: 14px; visibility: hidden;"><img src="${ctx}/images/camera/v-010-a.gif" width="48" height="31"></div>
                                <div id="lyAudioMD" style="cursor: hand; position:absolute; z-index:9; left: 322px; top: 14px; visibility: hidden;" onClick="DHT_AudioStop()"><img name="imgAudioStop" alt="关闭音频" src="${ctx}/images/camera/v-011-a.gif" width="49" height="31"></div>
                                <div id="lyRecorderMD" style="cursor: hand; position:absolute;z-index:10; left: 371px; top: 14px; visibility: hidden;" onClick="DHT_RecStop()"><img name="imgRecStop" alt="停止录像" src="${ctx}/images/camera/v-012-a.gif" width="49" height="31"></div>
                            </div>
                            <img border="0" src="${ctx}/images/camera/toolbarbg.gif" width="690" height="52"></td>
                    </tr>
                </table>
            </td>
            <td width="7"></td>
            <td width="316">
                <table border="0" width="100%" id="table4" height="100%" cellspacing="0" cellpadding="0">
                    <tr>
                        <td height="41">
                            <p align="center">
                                <img border="0" src="${ctx}/images/camera/v-013.gif" width="143" height="32"></td>
                    </tr>
                    <tr>
                        <td height="527" align="left" valign="top">
                            <div align="center">
                                <table border="0" width="282" id="table5" height="550" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td width="282" height="18">
                                            <img border="0" src="${ctx}/images/camera/v2.9-m.gif" width="282" height="22"></td>
                                    </tr>
                                    <tr>
                                        <td id ="lgtd_1" valign="top" bgcolor="#5568AF">
                                            <div align="center">
                                                <table border="0" width="247" id="table6" height="461" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="19"><img src="${ctx}/images/camera/v2.9-2.gif" width="262" height="19"></td>
                                                    </tr>
                                                    <tr>
                                                        <td id ="lgtd_2" height="426" valign="top" bgcolor="#909CC6"><div id="lySetParent" style="position:absolute; width:20; height:20; z-index:3;">
                                                            <div style="position: absolute; width: 208px; height: 463px; z-index: 13; left: 10px; top: -10px; visibility: hidden;" id="lyNetWork">
                                                                <div style="position: absolute; width: 210px; height: 129px; z-index: 7; left: 5px; top: 140px" id="lyRegCenter">
                                                                    <table border="0" width="100%" id="table18" height="141" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td colspan="3" height="18">
                                                                                <script>OutPrint(JS_cShow15);</script></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td height="14"><script>OutPrint(JS_cShow251);</script></td>
                                                                            <td height="14"><script>OutPrint(JS_cShow217);</script></td>
                                                                            <td height="14"><script>OutPrint(JS_cShow218);</script></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td height="24">
                                                                                <input name="txRegUser" type="text" id="txRegUser" size="6" maxlength="15"></td>
                                                                            <td height="24">
                                                                                <input name="txRegPwd" type="password" id="txRegPwd" size="6" maxlength="15"></td>
                                                                            <td height="24">
                                                                                <input name="txRegNVSName" type="text" id="txRegNVSName" size="8" maxlength="15"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="3" valign="top">
                                                                                <table border="0" width="100%" id="table19" height="76" cellspacing="0" cellpadding="0">
                                                                                    <tr>
                                                                                        <td width="16%">
                                                                                            IP1:</td>
                                                                                        <td width="84%">
                                                                                            <input name="txRegIP1" type="text" id="txRegIP1" size="15" maxlength="31"><input name="txRegPort1" type="text" id="txRegPort1" size="4" maxlength="5"></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td width="16%">
                                                                                            IP2:</td>
                                                                                        <td width="84%">
                                                                                            <input name="txRegIP2" type="text" id="txRegIP2" size="15" maxlength="31"><input name="txRegPort2" type="text" id="txRegPort2" size="4" maxlength="5"></td>
                                                                                    </tr>
                                                                                    <tr>
                                                                                        <td height="20" colspan="3"><p align="right">
                                                                                            <b><a href="JavaScript:NetWork_SetRegCenter()"><script>OutPrint(JS_cShow20);</script></a></b></p></td>
                                                                                    </tr>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </div>
                                                                <div style="position: absolute; width: 200; height: 11px; z-index: 5; left: 5px; top: 271px" id="layer17">
                                                                    <hr>
                                                                </div><div style="position: absolute; width: 200; height: 11px; z-index: 5; left: 5px; top: 125px" id="layer16">
                                                                <hr>
                                                            </div>
                                                                <div style="position: absolute; width: 200; height: 18px; z-index: 5; left: 4px; top: 301px" id="layer15">
                                                                    <hr>
                                                                </div>
                                                                <div style="position: absolute; width: 210px; height: 140px; z-index: 1; left: 5px; top: -15px" id="lyIP">
                                                                    <table border="0" width="100%" id="table16" height="138" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td height="24" colspan="2" align="left"><input type="checkbox" name="chkbDHCP" value="ON" onClick="DHCP_SetEnabled()" tabindex=1>
                                                                                <script>OutPrint(JS_cShow185);</script> </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="33%" align="left" height="24"><script>OutPrint(JS_cShow215);</script></td>
                                                                            <td width="67%" height="24">
                                                                                <input name="txIPChange" type="text" id="txIPChange" size="15" maxlength="15"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="33%" align="left" height="25"><script>OutPrint(JS_cShow17);</script></td>
                                                                            <td width="67%" height="25">
                                                                                <input name="txMask" type="text" id="txMask" size="15" maxlength="15"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="33%" align="left" height="25"><script>OutPrint(JS_cShow18);</script></td>
                                                                            <td width="67%" height="25">
                                                                                <input name="txGateway" type="text" id="txGateway" size="15" maxlength="15"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="33%" align="left" height="24"><script>OutPrint(JS_cShow216);</script></td>
                                                                            <td width="67%" height="24">
                                                                                <input name="txDNS" type="text" id="txDNS" size="15" maxlength="15"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td  height="16" colspan="2">
                                                                                <div id="lyG3Show" style="position:absolute; left: 0px; top: 125px;"><b><a href="JavaScript:DHT_GMessage()">
                                                                                    <script>OutPrint(JS_cShow310);</script></a></b></div>
                                                                                <div id="lyWifi" style="position:absolute; left: 74px; top: 125px;"><b><a href="JavaScript:DHT_WifiInfo()">
                                                                                    <script>OutPrint(JS_cShow19);</script>
                                                                                </a></b></div>
                                                                                <div id="lyChangeIPset" style="position:absolute; left: 175px; top: 125px;"><b><a href="JavaScript:DHT_ChangeSvrIP()"><script>OutPrint(JS_cShow20);</script></a></b></div>
                                                                            </td>
                                                                        </tr>
                                                                    </table>
                                                                </div>
                                                                <div style="position: absolute; width: 209px; height: 100px; z-index: 6; left: 4px; top: 321px" id="lyPPPoE">
                                                                    <table border="0" width="210" id="table17" height="96" cellspacing="0" cellpadding="0">
                                                                        <tr>
                                                                            <td height="25" colspan="2">
                                                                                <script>OutPrint(JS_cShow21);</script><div style="position: absolute; width: 21px; height: 20px; z-index: 1; left: 161px; top: 2px" id="lyPPPEnabled">
                                                                                <input type="checkbox" name="chkbPPPEnabled" value="ON" onClick="PPPoE_SetEnabled()"></div>														</td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td height="24" width="33%" align="left">
                                                                                <script>OutPrint(JS_cShow22);</script></td>
                                                                            <td height="24" width="67%">
                                                                                <input name="txPPPUser" type="text" id="txPPPUser" size="18" maxlength="63" onChange="value=value.replace(/[^\x00-\xff]/g,'')"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="33%" align="left">
                                                                                <script>OutPrint(JS_cShow3);</script></td>
                                                                            <td width="67%">
                                                                                <input name="txPPPPwd" type="password" id="txPPPPwd" size="18" maxlength="63" onChange="value=value.replace(/[^\x00-\xff]/g,'')"></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td height="24" colspan="2">
                                                                                <p align="right"><b>
                                                                                    <a href="JavaScript:DHT_SetPPPoE()"><script>OutPrint(JS_cShow20);</script></a></b></td>
                                                                        </tr>
                                                                    </table>
                                                                </div>
                                                                <p><div id="lyNetWebPort" style="position: absolute; width: 210px; height: 28px; z-index: 24; top:424px; left: 4px;" class="border5">
                                                                    <table width="216" border="0">
                                                                        <tr>
                                                                            <td width="67"><script>OutPrint(JS_cShow23);</script></td>
                                                                            <td width="78"><input name="txWebPort" type="text" id="txWebPort" size="6" maxlength="6" onChange="value=value.replace(/[^\d]/g,'')" ></td>
                                                                            <td width="51"><p align="right"><b>
                                                                <a href="JavaScript:NetWork_SetWebPort()"><script>OutPrint(JS_cShow20);</script></a></b></td>
                                                    </tr>
                                                </table>
                                                <!--webbot bot="Validation" b-value-required="TRUE" i-maximum-length="32" -->
                                            </div>
                                            <div id="lyUpnp" style="position:absolute; width:194px; height:21px; z-index:25; left: 6px; top: 286px;">
                                                <input type="checkbox" name="chkbUPnP" value="ON" onClick="UPnP_SetEnable()" tabindex=1>
                                                UPnP &nbsp;&nbsp;&nbsp;
                                                <b><a href="JavaScript:DHT_setDDNS()">[DDNS]</a></b>&nbsp;&nbsp;&nbsp;
                                                <b><a href="JavaScript:DHT_setFTP()">[FTP]</a></b>&nbsp;&nbsp;&nbsp;
                                                <b><a href="JavaScript:DHT_setNTP()">[NTP]</a></b>
                                            </div>&nbsp;&nbsp;&nbsp;
                            </div>
                            <div style="position: absolute; width: 230px; height: 472px; z-index: 12; left: 9px; top: -14px; visibility: hidden;" id="lyAdvanced">
                                <div id="LyFirmwareUpDate" style="position:absolute; width:249px; height:96px; z-index:10; left: -1px; top: 360px;" class="border5">
                                    <div id="lyUpDataState1" style="position:absolute; width:230px; z-index:12; top: 116px; left: 0px;" class="styleUpState"></div>
                                    <input name="file1" type="file" id="file1" style="Z-INDEX: 9; left:  1px; width: 230px; position: absolute;  top:18px; height:22px" size="50" maxlength="250" contenteditable="false" runat="server">
                                    <script>OutPrint(JS_cShow24);</script>
                                    <div id="LyprogramUpdate" style="position:absolute; z-index:11; left: 182px; top: 42px;"><b><a  href="JavaScript:DHT_UpgradeProgram()" class="style12" ><script>OutPrint(JS_cShow25);</script></a></b></div>
                                    <div id="Lyprogram" style="position:absolute; z-index:14; left: 5px; top: 42px;"><b><a href="JavaScript:DHT_UpgradeProgramClear()" class="style12" ><script>OutPrint(JS_cShow26);</script></a></b></div>
                                    <div style="position: absolute; width: 244px; height: 33px; z-index: 3; left: 1px; top: 59px" id="lyUpDataState"></div>
                                </div>
                                <div style="position: absolute; width: 227px; height: 17px; z-index: 9; left: 0px; top: 225px" id="lySysBtn">
                                    <div style="position: absolute; width: 108px; height: 22px; z-index: 2; left: 19px; top: 0px" id="lyDefPara">
                                        <b><a href="JavaScript:Advanced_DefPara()">
                                            <script>OutPrint(JS_cShow27);</script></a></b></div>
                                    <div style="position: absolute; width: 68px; height: 22px; z-index: 2; left: 142px; top: 0px" id="lyReboot">
                                        <b><a href="JavaScript:Advanced_Reboot()">
                                            <script>OutPrint(JS_cShow28);</script></a></b></div>
                                </div>
                                <div style="position: absolute; width: 227px; height: 24px; z-index: 8; left: 1px; top: 208px;" id="lySetTime">
                                    <div style="position: absolute; width: 68px; height: 22px; z-index: 2; left: 142px; top: 0px" id="lybtnSetTime">
                                        <b><a href="JavaScript:Advanced_SetTime()">
                                            <script>OutPrint(JS_cShow29);</script></a></b></div>
                                    <table border="0" width="100%" id="table22" cellspacing="0" cellpadding="0" height="24">
                                        <tr>
                                            <td>
                                                <div style="position: absolute; width: 130px; height: 23px; z-index: 1; left: 2px; top: 0px" id="lyTimeNow">
                                                </div>
                                                <p></td>
                                            <td width="74">														</td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="position: absolute; width: 230px; height: 143px; z-index: 6; left: 0px; top: 0px" id="lyModifyPwd">
                                    <table border="0" width="100%" id="table20" cellspacing="0" cellpadding="0" height="139">
                                        <tr>
                                            <td height="15" colspan="2">
                                                <script>OutPrint(JS_cShow30);</script></td>
                                        </tr>
                                        <tr>
                                            <td height="18">
                                                <script>OutPrint(JS_cShow31);</script></td>
                                            <td height="18">
                                                <select size="1" name="selUserList" style="width: 110" onChange="Advanced_SelUserInfo()">
                                                    <option selected>Admin</option>
                                                </select></td>
                                        </tr>
                                        <tr>
                                            <td height="18">
                                                <script>OutPrint(JS_cShow2);</script></td>
                                            <td height="18">
                                                <input name="txSelUserName" type="text" id="txSelUserName" size="15" maxlength="15"></td>
                                        </tr>
                                        <tr>
                                            <td height="18" width="36%">
                                                <script>OutPrint(JS_cShow32);</script></td>
                                            <td height="18" width="64%">													  <input name="txOldPwd" type="password" id="txOldPwd" size="15" maxlength="15"></td>
                                        </tr>
                                        <tr>
                                            <td width="36%" height="18"><script>OutPrint(JS_cShow33);</script></td>
                                            <td width="64%" height="18">
                                                <input name="txNewPwd" type="password" id="txNewPwd" size="15" maxlength="15"></td>
                                        </tr>
                                        <tr>
                                            <td width="36%" height="18"><script>OutPrint(JS_cShow247);</script></td>
                                            <td width="64%" height="18">
                                                <input name="txNewPwdConfirm" type="password" id="txNewPwdConfirm" size="15" maxlength="15"></td>
                                        </tr>
                                        <tr>
                                            <td width="36%" height="18">
                                                <script>OutPrint(JS_cShow34);</script></td>
                                            <td width="64%" height="18">
                                                <select size="1" name="selUserAuth" style="width: 110">
                                                    <option selected value="1"><script>OutPrint(JS_cShow35);</script></option>
                                                    <option value="2"><script>OutPrint(JS_cShow36);</script></option>
                                                    <option value="3"><script>OutPrint(JS_cShow37);</script></option>
                                                    <option value="4"><script>OutPrint(JS_cShow38);</script></option>
                                                </select></td>
                                        </tr>
                                        <tr>
                                            <td height="18" colspan="2">
                                                <p align="center">
                                                    <b><a href="JavaScript:Advanced_AddUser()"><script>OutPrint(JS_cShow39);</script></a>
                                                        <a href="JavaScript:Advanced_DelUser()"><script>OutPrint(JS_cShow40);</script></a>
                                                        <a href="JavaScript:Advanced_ModifyPwd()"><script>OutPrint(JS_cShow41);</script></a></b></td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="position: absolute; width: 230; height: 4px; z-index: 5; left: -1px; top: 160px" id="layer12">
                                    <hr>
                                </div>
                                <div style="position: absolute; width: 230px; height: 35px; z-index: 8; left: 0px; top: 170px;" id="lySystemInfo">
                                    <table border="0" width="100%" id="table18" height="34" cellspacing="0" cellpadding="0">
                                        <tr><td height="16" colspan="3"><script>OutPrint(JS_cShow42);</script></td>
                                        </tr>
                                        <tr><td height="15" width="33%">CPU:
                                            <div style="position: absolute; width: 25px; height: 15px; z-index: 1; left: 57px; top: 19px" id="lyCPUInfo">
                                                0%</div>
                                        </td>
                                            <td height="15" width="33%">MEM:
                                                <div style="position: absolute; width: 28px; height: 15px; z-index: 8; left: 131px; top: 19px;" id="lyMRMInfo">0%
                                                </div>
                                            </td>
                                            <td height="15" width="33%">FLASH:
                                                <div style="position: absolute; width: 28px; height: 15px; z-index: 8; left: 215px; top: 19px;" id="lyFLASHInfo">0%
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                                <div id="lyDeviceMound" style="position:absolute; width:232px; height:20px; z-index:1; top: 240px; left: 0px; visibility: hidden;">
                                    <div id="lyNomanDeviceinfo" style="position:absolute; width:80px; left:20px; top:12px; ">
                                        <script>OutPrint(JS_cShow246);</script></div>
                                    <div id="NomanDevicestatus" style="position:absolute; left:1px; top:8px; ">
                                        <input type="checkbox" name="chkNoMan" disabled="true"></div>
                                    <div id="lyRTSPDeviceinfo" style="position:absolute; left:140px; top:10px; ">
                                        RTSP</div>
                                    <div id="RTSPDevicestatus" style="position:absolute; left:120px; top:8px; ">
                                        <input type="checkbox" name="chkRTSP" disabled="true"></div>
                                    <div id="G3Devicestatus" style="position:absolute; left:180px; top:8px; ">
                                        <input type="checkbox" name="chk3GStatus" disabled="true">
                                    </div>
                                    <div id="ly3GDeviceinfo" style="position:absolute; left:200px; top:10px; ">3G</div>
                                </div>
                                <div style="position: absolute; width: 230; height: 11px; z-index: 5; left: 0px; top: 265px; visibility: hidden;" id="layer12">
                                    <hr>
                                </div>
                                <div style="position: absolute; width: 230; height: 11px; z-index: 5; left: 0px; top: 235px" id="layer13">
                                    <hr>
                                </div>
                                <div style="position: absolute; width: 230; height: 76px; z-index: 3; left: 0px; top: 258px" id="lyVersion">
                                    <table border="0" width="100%" id="table12" height="76" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="78" align="left" height="20"><script>OutPrint(JS_cShow43);</script></td>
                                            <td height="20">
                                                <div style="position: absolute; width: 100px; height: 20px; z-index: 1; left: 79px; top: 0px" id="lySDKVer">
                                                    SDK</div>
                                                <p></td>
                                        </tr>
                                        <tr>
                                            <td width="78" height="20" align="left"><script>OutPrint(JS_cShow44);</script></td>
                                            <td height="20">
                                                <div style="position: absolute; width: 100px; height: 20px; z-index: 2; left: 78px; top: 22px" id="lySvrVer">
                                                    Svr</div>
                                                <p></td>
                                        </tr>
                                        <tr>
                                            <td width="78" align="left" height="20"><script>OutPrint(JS_cShow45);</script></td>
                                            <td height="23">
                                                <div style="position: absolute; width: 223px; height: 20px; z-index: 3; left: 0px; top: 64px" id="lyDeviceID">
                                                    ID</div>
                                                <p></td>
                                        </tr>
                                        <tr>
                                            <td height="20" colspan="2" align="left"><div style="position: absolute; width: 100px; height: 20px; z-index: 3; left: 78px; top: 43px" id="lyAxVer"> Ax</div></td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="position: absolute; width: 230px; height: 4px; z-index: 5; left: 0px; top: 348px" id="layer33">
                                    <hr>
                                </div>
                                <p></div>
                            <div id="lyLogon" style="position:absolute; width:223px; height:400px; z-index:1; left: 23px; top: 1px;">
                                <div align="center">
                                    <table border="0" width="94%" id="table7" height="370" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td>
                                                <table border="0" width="100%" id="table9" height="127" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <input name="txAgentIp" type="hidden" id="txAgentIp" size="15" maxlength="31"></td>
                                                    </tr>
                                                    <tr>
                                                        <td width="134" height="24">
                                                            <input name="txPort" type="hidden" id="txPort" size="10" maxlength="5" value="3000"></td>
                                                    </tr>
                                                    <tr>
                                                        <td height="25">
                                                            <p align="right">
                                                                <script>OutPrint(JS_cShow8);</script></td>
                                                        <td width="134" height="25">
                                                            <select name="selChannel" size="1" style="width: 75">
                                                                <option value="1">1</option>
                                                                <option value="2">2</option>
                                                                <option value="3">3</option>
                                                                <option value="4">4</option>
                                                                <option value="5">5</option>
                                                                <option value="6">6</option>
                                                                <option value="7">7</option>
                                                                <option value="8">8</option>
                                                                <option value="9">9</option>
                                                                <option value="10">10</option>
                                                                <option value="11">11</option>
                                                                <option value="12">12</option>
                                                                <option value="13">13</option>
                                                                <option value="14">14</option>
                                                                <option value="15">15</option>
                                                                <option value="16">16</option>
                                                            </select></td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <p align="right">
                                                                <script>OutPrint(JS_cShow9);</script></td>
                                                        <td width="134">
                                                            <select name="selStreamType" style="width: 75">
                                                                <option value="0"><script>OutPrint(JS_cShow10);</script></option>
                                                                <option value="1"><script>OutPrint(JS_cShow11);</script></option>
                                                            </select></td>
                                                    </tr>
                                                    <tr>
                                                        <td height="25">
                                                            <p align="right">
                                                                <script>OutPrint(JS_cShow12);</script></td>
                                                        <td width="134" height="25">
                                                            <select name="selNetMode" style="width: 75">
                                                                <option value="1">TCP</option>
                                                                <option value="2">UDP</option>
                                                            </select></td>
                                                    </tr>
                                                    <tr><td colspan="2" height="25">
                                                        <p align="center"><b>
                                                            <a href="JavaScript:DHT_StartObs()"><script>OutPrint(JS_cShow13);</script></a>&nbsp;&nbsp;
                                                            <a href="JavaScript:DHT_StopObs()"><script>OutPrint(JS_cShow14);</script></a>&nbsp;&nbsp;
                                                            <a href="JavaScript:ShowConfig()"><script>OutPrint(JS_cShow667);</script></a></b></p>
                                                    </tr>
                                                </table>
                                                <input name="txDecrypt" type="text" id="txDecrypt" size="15" maxlength="15"><a href="JavaScript:InputDecrypt()"><script>OutPrint(JS_cShow668);</script></a></td>													</td>
                                        </tr>
                                        <tr>
                                            <td height="161">
                                                <table border="0" width="100%" id="table8" height="130" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="24" width="63%">
                                                            <input name="txHostIp" type="hidden" id="txHostIp" size="15" maxlength="15" >																</td>
                                                    </tr>
                                                    <tr>
                                                        <td height="24" width="63%">
                                                            <input name="txUserName" type="hidden" id="txUserName" size="15" maxlength="15" ></td>
                                                    </tr>
                                                    <tr>
                                                        <td height="28" width="63%">
                                                            <input name="txPassword" type="hidden" id="txPassword" size="15" maxlength="15"></td>
                                                    </tr>
                                                </table>														</td>
                                        </tr>

                                    </table>
                                </div>
                                <div style="position: absolute; color:#2C3B74;z-index: 10; left: 15px; top: 400px;" id="lySaveConnectInfor">
                                    <input name="chkSaveConnectInfor" type="checkbox" id="chkSaveConnectInfor" tabindex=1 onClick="DHT_SaveAutoConnectInfor()" value="ON">
                                    <b><script>OutPrint(JS_cShow664);</script></b>
                                </div>
                            </div>
                            <div id="lyVideoParam" style="position: absolute; width: 257px; height: 475px; z-index: 5; left: 15px; top: -11px; overflow: hidden; visibility: hidden;">

                                <div id="layerBrightness" style="position:absolute; width:200px; height:26px; z-index:6; left: 0px; top: 10px;">
                                    <div id="trackbarTitle" style="position: absolute; width: 52px; height: 22; z-index: 7; left: 2px; top: 2px; " class="Rec">
                                        <script>OutPrint(JS_cShow156);</script></div>
                                    <div id="layerbar" style="position: absolute; width: 138px; height: 26px; z-index: 7; left: 60px; top: 0px; background-image:   url(movebar.jpg); layer-background-image:   url(movebar.jpg); border: 1px none #000000">
                                        <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url(pin.gif); layer-background-image:   url(pin.gif); border: 1px none #000000" id="lyBrightnessBut" onMouseDown="trackbar_mousedown(lyBrightnessBut)" onMouseUp="trackbar_mouseup(lyBrightnessBut)" onMouseMove="trackbar_mousemove(lyBrightnessBut)">
                                            <div align="center"></div>
                                        </div>
                                        <div align="left">
                                            <div id="labBrightnessValue" style="position:absolute; width:22px; height:10px; z-index:8; left: 114px; top: 2px;">50</div>
                                            <div id="lybar" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 5px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
                                        </div>
                                    </div>
                                </div>
                                <div id="layerContrast" style="position:absolute; width:200px; height:26px; z-index:6; left: 0px; top: 62px;">
                                    <div id="trackbarTitle" style="position:absolute; width:52px; height:23px; z-index:7; left: 4px; top: 2px;" class="Rec">
                                        <script>OutPrint(JS_cShow157);</script></div>
                                    <div id="layerbar" style="position: absolute; width: 138px; height: 26px; z-index: 7; left: 60px; top: 0px; background-image:   url(movebar.jpg); layer-background-image:   url(movebar.jpg); border: 1px none #000000">
                                        <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url(pin.gif); layer-background-image:   url(pin.gif); border: 1px none #000000" id="lyContrastBut" onMouseDown="trackbar_mousedown(lyContrastBut)" onMouseUp="trackbar_mouseup(lyContrastBut)" onMouseMove="trackbar_mousemove(lyContrastBut)">
                                            <div align="center"></div>
                                        </div>
                                        <div align="left">
                                            <div id="labContrastValue" style="position:absolute; width:22px; height:10px; z-index:8; left: 114px; top: 2px;">50</div>
                                            <div id="lybar" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 5px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
                                        </div>
                                    </div>
                                </div>
                                <div id="layerHue" style="position:absolute; width:200px; height:26px; z-index:6; left: 0px; top: 87px;">
                                    <div id="trackbarTitle" style="position:absolute; width:50px; height:22px; z-index:7; left: 4px; top: 2px;" class="Rec">
                                        <script>OutPrint(JS_cShow158);</script></div>
                                    <div id="layerbar" style="position: absolute; width: 138px; height: 26px; z-index: 7; left: 60px; top: 0px; background-image:   url(movebar.jpg); layer-background-image:   url(movebar.jpg); border: 1px none #000000">
                                        <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url(pin.gif); layer-background-image:   url(pin.gif); border: 1px none #000000" id="lyHueBut" onMouseDown="trackbar_mousedown(lyHueBut)" onMouseUp="trackbar_mouseup(lyHueBut)" onMouseMove="trackbar_mousemove(lyHueBut)">
                                            <div align="center"></div>
                                        </div>
                                        <div align="left">
                                            <div id="labHueValue" style="position:absolute; width:22px; height:10px; z-index:8; left: 114px; top: 2px;">50</div>
                                            <div id="lybar" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 5px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
                                        </div>
                                    </div>
                                </div>
                                <div id="layerSaturation" style="position:absolute; width:200px; height:26px; z-index:6; left: 0px; top: 36px;">
                                    <div id="trackbarTitle" style="position:absolute; width:53px; height:23px; z-index:7; left: 4px; top: 2px;" class="Rec">
                                        <script>OutPrint(JS_cShow159);</script></div>
                                    <div id="layerbar" style="position: absolute; width: 138px; height: 26px; z-index: 7; left: 60px; top: 0px; background-image:   url(movebar.jpg); layer-background-image:   url(movebar.jpg); border: 1px none #000000">
                                        <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url(pin.gif); layer-background-image:   url(pin.gif); border: 1px none #000000" id="lySaturationBut" onMouseDown="trackbar_mousedown(lySaturationBut)" onMouseUp="trackbar_mouseup(lySaturationBut)" onMouseMove="trackbar_mousemove(lySaturationBut)">
                                            <div align="center"></div>
                                        </div>
                                        <div align="left">
                                            <div id="labSaturationValue" style="position:absolute; width:22px; height:10px; z-index:8; left: 114px; top: 2px;">50</div>
                                            <div id="lybar" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 5px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
                                        </div>
                                    </div>
                                </div>
                                <div id="lyVideoQuality" style="position:absolute; width:200px; height:27px; z-index:6; left: 0px; top: 125px;">
                                    <select name="selVideoQuality" size="1" id="select3" style="position: absolute; float: right; left: 90px; top: 1px; width: 102px; height: 23px; z-index: 7" onChange="VQ_SetVideoQuality()">
                                        <option value="6"><script>OutPrint(JS_cShow160);</script></option>
                                        <option value="8"><script>OutPrint(JS_cShow161);</script></option>
                                        <option value="10"><script>OutPrint(JS_cShow162);</script></option>
                                        <option value="12"><script>OutPrint(JS_cShow163);</script></option>
                                        <option value="14"><script>OutPrint(JS_cShow164);</script></option>
                                    </select>
                                    <div id="lyVQTitle" style="position: absolute; width: 80px; height: 20px; z-index: 7; left: 3px; top: 5px" class="Rec">
                                        <script>OutPrint(JS_cShow165);</script></div>
                                </div>
                                <div id="lyMediaType" style="position: absolute; width: 200px; height: 27px; z-index: 6; left: 0px; top: 175px">
                                    <select name="selMediaType" id="selMediaType" onChange="VQ_SetMediaType()" style="position: absolute; float: right; left: 90px; top: 1px; width: 102px; height: 23px; z-index: 7;" size="1">
                                        <option selected value="0"><script>OutPrint(JS_cShow166);</script></option>
                                        <option value="1"><script>OutPrint(JS_cShow167);</script></option>
                                        &nbsp;
                                    </select>
                                    <div id="lyMTTitle" style="position: absolute; width: 74px; height: 22px; z-index: 7; left: 3px; top: 5px" class="Rec">
                                        <script>OutPrint(JS_cShow168);</script></div>
                                </div>
                                <div id="lyPreferType" style="position: absolute; width: 200px; height: 27px; z-index: 6; left: 0px; top: 200px">
                                    <select name="selPreferType" id="selPreferType" onChange="VQ_SetPreferMode()" style="position: absolute; float: right; left: 90px; top: 1px; width: 102px; height: 23px; z-index: 7;" size="1">
                                        <option value="0"><script>OutPrint(JS_cShow169);</script></option>
                                        <option value="1"><script>OutPrint(JS_cShow170);</script></option>
                                        &nbsp;
                                    </select>
                                    <div id="lyPreferTitle" style="position: absolute; width: 74px; height: 22px; z-index: 7; left: 3px; top: 5px" class="Rec">
                                        <script>OutPrint(JS_cShow171);</script></div>
                                </div>
                                <div id="lyFrameRate" style="position:absolute; width:200px; height:26px; z-index:6; left: 0px; top: 150px;">
                                    <select name="selFrameRate" id="select6" onChange="VQ_SetFrameRate()" style="position: absolute; float: right; left: 90px; top: 1px; width: 102px; height: 23px; z-index: 7" size="1">
                                        <option value="1">1</option>
                                        <option value="5">5</option>
                                        <option value="10">10</option>
                                        <option value="15">15</option>
                                        <option value="20">20</option>
                                        <option selected value="25">25</option>
                                        <option value="30">30</option>
                                    </select>
                                    <div id="lyFRTitle" style="position: absolute; width: 76px; height: 21px; z-index: 7; top: 5px; left: 3px" class="Rec">
                                        <script>OutPrint(JS_cShow172);</script></div>
                                </div>
                                <div id="Layer9" style="position:absolute; width:200px; height:13px; z-index:6; top: 275px; left: 0px">
                                    <hr>
                                </div>
                                <div style="position: absolute; width: 198px; height: 103px; z-index: 2; left: 2px; top: 291px" id="lyVideo">
                                    <table border="0" width="100%" id="table11" height="102" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td align="left" width="61" height="25"><script>OutPrint(JS_cShow173);</script></td>
                                            <td height="25">
                                                <input name="txBitRate" type="text" id="txBitRate" size="8" maxlength="4"></td>
                                            <td width="50" height="25">
                                                <p align="right"><b><a href="JavaScript:DHT_SetMaxKByteRate()"><script>OutPrint(JS_cShow20);</script></a></b></td>
                                        </tr>
                                        <tr>
                                            <td align="left" width="61" height="25"><script>OutPrint(JS_cShow174);</script></td>
                                            <td height="25">
                                                <select size="1" name="slVideoSize" style="width: 75px">
                                                    <option value="0">Quarter Cif</option>
                                                    <option value="1">Half Cif</option>
                                                    <option value="2">Full Cif</option>
                                                    <option value="3">Half D1</option>
                                                    <option value="4">Full D1</option>
                                                </select>
                                            </td>
                                            <td width="50" height="25">
                                                <p align="right"><b><a href="JavaScript:DHT_SetVideoSize()"><script>OutPrint(JS_cShow20);</script></a></b></td>
                                        </tr>
                                        <tr>
                                            <td width="80" align="left">
                                                <script>OutPrint(JS_cShow175);</script></td>
                                            <td>
                                                <select size="1" name="slEncodeMode"  onChange="DHT_SetEncodeMode()" style="width: 75px">
                                                    <option value="0">CBR</option>
                                                    <option value="1">VBR</option>
                                                </select></td>
                                            <td width="50"></td>
                                        </tr>
                                        <tr>
                                            <td align="left"><script>OutPrint(JS_cShow176);</script></td>
                                            <td><select size="1" name="slVidePNMode"  style="width: 75px">
                                                <option value="0">PAL</option>
                                                <option value="1">NTSC</option>
                                            </select></td>
                                            <td><p align="right"><b><a href="JavaScript:DHT_SetVideoPNMode()"><script>OutPrint(JS_cShow20);</script></a></b></td>
                                        </tr>
                                    </table>
                                </div>
                                <div style="position: absolute; width: 198px; height: 24px; z-index: 7; left: 2px; top: 390px" id="lyShowBitRate">
                                    <table border="0" width="100%" id="table21" cellspacing="0" cellpadding="0" height="20px">
                                        <tr>
                                        </tr>
                                    </table>
                                </div>
                                <div id="Layer6" style="position:absolute; width:200px; height:13px; z-index:6; top: 105px; left: 0px;">
                                    <hr>
                                </div>
                                <div id="bVideoQuaSetParent" style="position:absolute; z-index:6; left: 212px; top: 95px;">
                                    <b><a href="JavaScript:VQ_SetVideoParam()">
                                        <script>OutPrint(JS_cShow20);</script></a> </b>          </div>
                                <div id="lyHD" style="position:absolute; width:256px; height:59px; z-index:10; left: 0px; top: 405px;">
                                    <div style="position: absolute; z-index: 10; left: 10px; top: 15px;" id="">
                                        <b><a href="JavaScript:HDCameraSet()"><script>OutPrint(JS_cShow626)</script></a></b>
                                    </div>
                                </div>
                                <div name="lyhdAdvance" id="lyhdAdvance" style="position:absolute; width:256px; height:463px; z-index:2; left: 5px; top: 0px; visibility: hidden;">

                                    <div style="position: absolute; z-index: 10; left: 2px; top: 10px;" id="lyAutoApertureEnable">
                                        <input name="chkAutoApertureEnable" type="checkbox" id="chkAutoApertureEnable" tabindex=1 onClick="HD_CamerSet()" value="ON">
                                        <script>OutPrint(JS_cShow351);</script>
                                    </div>

                                    <div id="Layer6" style="position:absolute; width:230px; height:8px; z-index:6; top: 40px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div style="position: absolute; z-index: 10; left: 2px; top: 60px;" id="lychkApheliotropicEnable">
                                        <input name="chkchkApheliotropicEnable" type="checkbox" id="chkchkApheliotropicEnable" tabindex=1 onClick="HD_CamerSet()" value="ON">
                                        <script>OutPrint(JS_cShow352);</script>
                                    </div>
                                    <div id="lySetBeiGuangArea" style="position:absolute; width:232px; height:25px; z-index:56; top: 80px; left: 0px;">
                                        <div id="lySetBeiGuangAreaEnable1" style="position: absolute; width:150px; z-index: 10; left: 2px; top: 10px;" >
                                            <input name="chkBeiGuangAreaEnable" type="checkbox" id="chkBeiGuangAreaEnable" tabindex=1 onClick="HD_CamerSetBeiguangArea()" value="ON">
                                            <script>OutPrint(JS_cShow637);</script>
                                        </div>
                                        <div id="lySetBeiGuangAreaEnable2" style="position:absolute; width:71px; height:25px; z-index:1; top: 12px; left: 170px;">
                                            <b><a href="JavaScript:NVR_ClearBeiguangArea()"><script>OutPrint(JS_cShow69);</script></a></b>
                                        </div>
                                    </div>

                                    <div id="Layer6" style="position:absolute; width:230px; height:8px; z-index:6; top: 110px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lySetExposeArea" style="position:absolute; width:232px; height:25px; z-index:56; top: 120px; left: 0px;">
                                        <div id="lychkExposeAreaEnable1" style="position: absolute; width:120px; z-index: 10; left: 2px; top: 9px;" >
                                            <input name="chkExposeAreaEnable" type="checkbox" id="chkExposeAreaEnable" tabindex=1 onClick="HD_CamerSetExposeArea()" value="ON">
                                            <script>OutPrint(JS_cShow636);</script>
                                        </div>
                                        <div id="lychkExposeAreaEnable2" style="position:absolute; width:71px; height:25px; z-index:1; top: 12px; left: 170px;">
                                            <b><a href="JavaScript:NVR_ClearExposalArea()"><script>OutPrint(JS_cShow69);</script></a></b>
                                        </div>
                                    </div>

                                    <div id="Layer6" style="position:absolute; width:230px; height:8px; z-index:6; top: 150px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lyexposaltime" style="position:absolute; width:232px; height:25px; z-index:56; top: 160px; left: 0px;">
                                        <div id="lyexposaltime1" style="position:absolute; width:80px; height:25px; z-index:1; top: 12px; left: 2px;">
                                            <script>OutPrint(JS_cShow627);</script>
                                        </div>
                                        <div id="lyexposaltime2" style="position:absolute; width:125px; height:25px; z-index:1; top: 8px; left: 85px;">
                                            <select name="ExposalTime" type="text" id="ExposalTime" style="position: absolute; float: right; width: 100px; left: 0px; top: 0; height: 23; z-index: 7" size="1">
                                                <option value="0"><script>OutPrint(JS_cShow630);</script></option>
                                                <option value="1"><script>OutPrint(JS_cShow631);</script></option>
                                                <option value="2"><script>OutPrint(JS_cShow632);</script></option>
                                            </select>
                                        </div>
                                        <div id="lyExposalTime3" style="position:absolute; z-index:55; top: 10px; left: 190px; width: 38px;">
                                            <b><a href="JavaScript:NVR_SetExposalTime()"><script>OutPrint(JS_cShow20);</script></a></b>
                                        </div>
                                    </div>
                                    <div id="Layer6" style="position:absolute; width:230px; height:8px; z-index:6; top: 190px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lyShutter" style="position:absolute; width:232px; height:25px; z-index:56; top: 200px; left: 0px;">
                                        <div id="lyshutter1" style="position:absolute; width:80px; height:25px; z-index:1; top: 12px; left: 2px;">
                                            <script>OutPrint(JS_cShow628);</script>
                                        </div>
                                        <div id="lyshutter2" style="position:absolute; width:125px; height:25px; z-index:1; top: 8px; left: 85px;">
                                            <select name="selShutter" type="text" id="selShutter" style="position: absolute; float: right; width: 100px; left: 0px; top: 0; height: 23; z-index: 7" size="1">
                                                <option value="0">1/25</option>
                                                <option value="1">1/50</option>
                                                <option value="2">1/120</option>
                                                <option value="3">1/250</option>
                                                <option value="4">1/500</option>
                                                <option value="5">1/1K</option>
                                                <option value="6">1/2K</option>
                                                <option value="7">1/4K</option>
                                                <option value="8">1/10K</option>
                                                <option value="9">1/100K</option>
                                            </select>
                                        </div>
                                        <div id="setShutte" style="position:absolute; z-index:55; top: 10px; left: 190px; width: 38px;">
                                            <b><a href="JavaScript:NVR_SetShutter()"><script>OutPrint(JS_cShow20);</script></a></b>
                                        </div>
                                    </div>
                                    <div id="Layer6" style="position:absolute; width:230px; height:13px; z-index:6; top: 230px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lyDeviceFlip" style="position:absolute; width:232px; height:25px; z-index:56; top: 240px; left: 0px;" >
                                        <div id="lyDeviceFlip1" style="position:absolute; width:80px; height:25px; z-index:1; top: 12px; left: 2px;">
                                            <script>OutPrint(JS_cShow633);</script>
                                        </div>
                                        <div id="lyDeviceFlip2" style="position:absolute; width:125px; height:25px; z-index:1; top: 8px; left: 85px;">
                                            <select name="DeviceFlip" type="text" id="DeviceFlip" onChange="Advanced_DeviceFlip()" style="position: absolute; float: right; width: 100px; left: 0px; top: 0; height: 23; z-index: 7" size="1">
                                                <option value="0"><script>OutPrint(JS_cShow635);</script></option>
                                                <option value="1"><script>OutPrint(JS_cShow634);</script></option>
                                            </select>
                                        </div>
                                    </div>
                                    <div id="Layer6" style="position:absolute; width:230px; height:13px; z-index:6; top: 270px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lyColorToGray" style="position:absolute; width:232px; height:25px; z-index:56; top: 280px; left: 0px;" >
                                        <div style="position: absolute; z-index: 10; left: 2px; top: 13px;" id="lyColorToGray1">
                                            <script>OutPrint(JS_cShow629);</script>
                                        </div>
                                        <div id="lyColorToGray2" style="position:absolute; width:125px; height:25px; z-index:1; top: 8px; left: 85px;">
                                            <select name="ColorToGraySet" type="text" id="ColorToGraySet" style="position: absolute; float: right; width: 100px; left: 0px; top: 0; height: 23; z-index: 7" size="1">
                                                <option value="0"><script>OutPrint(JS_cShow656);</script></option>
                                                <option value="1"><script>OutPrint(JS_cShow657);</script></option>
                                                <option value="2"><script>OutPrint(JS_cShow658);</script></option>
                                            </select>
                                        </div>
                                        <div id="lyColorToGray3" style="position:absolute; z-index:55; top: 10px; left: 190px; width: 38px;">
                                            <b><a href="JavaScript:HD_SetColorToGray()"><script>OutPrint(JS_cShow20);</script></a></b>
                                        </div>
                                    </div>
                                    <div id="Layer6" style="position:absolute; width:230px; height:13px; z-index:6; top: 310px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lyAutoIncrease" style="position:absolute; width:232px; height:25px; z-index:56; top: 320px; left: 0px;" >
                                        <div style="position: absolute; z-index: 10; left: 2px; top: 13px;" id="lyAutoIncrease1">
                                            <script>OutPrint(JS_cShow660);</script>
                                        </div>
                                        <div id="lyAutoIncrease2" style="position:absolute; width:125px; height:25px; z-index:1; top: 8px; left: 85px;">
                                            <select name="AutoIncreaseSet" type="text" id="AutoIncreaseSet" style="position: absolute; float: right; width: 100px; left: 0px; top: 0; height: 23; z-index: 7" size="1">
                                                <option value="0"><script>OutPrint(JS_cShow630);</script></option>
                                                <option value="1"><script>OutPrint(JS_cShow661);</script></option>
                                                <option value="2"><script>OutPrint(JS_cShow662);</script></option>
                                                <option value="3"><script>OutPrint(JS_cShow663);</script></option>
                                            </select>
                                        </div>
                                        <div id="lyAutoIncrease3" style="position:absolute; z-index:55; top: 10px; left: 190px; width: 38px;">
                                            <b><a href="JavaScript:HD_SetAutoIncrease()"><script>OutPrint(JS_cShow20);</script></a></b>
                                        </div>
                                    </div>
                                    <div id="Layer6" style="position:absolute; width:230px; height:13px; z-index:6; top: 350px; left: 0px;">
                                        <hr>
                                    </div>
                                    <div id="lyback" style="position: absolute; z-index: 5; left: 187px; top: 420px;">
                                        <b><a href="javascript:DHT_VideoQuality()"><script>OutPrint(JS_cShow155);</script></a></b>
                                    </div>
                                </div>
                                <div id="lyMJpegType" style="position: absolute; width: 200; height: 27; z-index: 6; left: 0px; top: 225px">
                                    <select name="selVencType" id="selVencType" onChange="VQ_SetVencType()" style="position: absolute; float: right; left: 90px; top: 1px; width: 102; height: 23; z-index: 7;" size="1">
                                        <option value="0">H.264</option>
                                        <option value="1">Motion Jpeg</option> </select>
                                            <div id="lyMJpegTitle" style="position: absolute; width: 74px; height: 22; z-index: 7; left: 3; top: 5" class="Rec">
                                                <script>OutPrint(JS_cShow353);</script>
                                </div>
                            </div>
                            <div id="lyShowFrameMode" style="position: absolute; width: 200; height: 27; z-index: 6; left: 0px; top: 250px">
                                <select name="selShowFrameMode" id="selShowFrameMode" onChange="VQ_SetShowFrameMode()" style="position: absolute; float: right; left: 90px; top: 1px; width: 102; height: 23; z-index: 7;" size="1">
                                    <option value="0"><script>OutPrint(JS_cShow361);</script></option>
                                    <option value="1"><script>OutPrint(JS_cShow362);</script> </option>
                                    &nbsp;
                                </select>
                                <div id="lyShowFrameModeTitle" style="position: absolute; width: 74px; height: 22px; z-index: 7; left: 3px; top: 5px" class="Rec">
                                    <script>OutPrint(JS_cShow360);</script>
                                </div>
                            </div>
</div>
<div id="lyNetWifiSet" style="position:absolute; width:255px; height:401px; z-index:1; top: -6px; left: 8px; visibility: hidden;">
    <table border="0" width="100%" id="tablewifi" cellspacing="0" cellpadding="0" height="24">
        <tr><td height="20"><p align="left"><script>OutPrint(JS_cShow178);</script>
        </td></tr>
        <tr><td height="25">
            <select name="selNetWifiInf" size="1" id="selNetWifiInf" style="width: 130px" onchange="NetWifiLink_change()">
            </select>
            <b><a href="JavaScript:NetWifiSearch()"><script>OutPrint(JS_cShow142);</script></a><a href="JavaScript:NetWifiDisConnect()"><script>OutPrint(JS_cShow335);</script></a></b>
        </td></tr>
        <tr><td width="50%" height="25"><script>OutPrint(JS_cShow179);</script>
            <select name="selWifiCard" id="selWifiCard" style="width: 50px" disabled="true">
                <option value="0"><script>OutPrint(JS_cShow180);</script></option>
                <option value="1"><script>OutPrint(JS_cShow181);</script></option>
            </select>
            <script>OutPrint(JS_cShow182);</script>
            <select name="selWifiState" id="selWifiState" style="width: 75px " disabled="true">
                <option value="0"><script>OutPrint(JS_cShow183);</script></option>
                <option value="1"><script>OutPrint(JS_cShow184);</script></option>
            </select>
        </td></tr>
        <tr><td height="25"><p align="left"><script>OutPrint(JS_cShow185);</script>
            <input type="checkbox" name="chkbWifiDHCP" id="chkbWifiDHCP" value="ON" onClick="WifiDHCP_SetEnabled()" tabindex=1>
        </td></tr>
        <tr><td height="25">
            <div id="lyWifiIP" style="position: absolute; width: 240px; height:25px; z-index: 1; top:98px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow186);</script>
                <div id="LyWifiIP1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <input name="txWifiIP" type="text" id="txWifiIP2" size="17" maxlength="17">
                </div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lyWifiSubMask" style="position: absolute; width: 240px; height:20px; z-index: 1; top:123px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow187);</script>
                <div id="LyWifiSubMask1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <input name="txWifiSubMask" type="text" id="txWifiSubMask" size="17" maxlength="17"></div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lyWifiGateWay" style="position: absolute; width: 240px; height:20px; z-index: 1; top:148px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow188);</script>
                <div id="LyWifiGateWay1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <input name="txWifiGateWay" type="text" id="txWifiGateWay" size="17" maxlength="17"></div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lyWifiDNS" style="position: absolute; width: 240px; height:20px; z-index: 1; top:173px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow189);</script>
                <div id="LyWifiDNS1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <input name="txWifiDNS" type="text" id="txWifiDNS" size="17" maxlength="17"></div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lyWifiESSID" style="position: absolute; width: 240px; height:20px; z-index: 1; top:198px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow400);</script>
                <div id="LyWifiESSID1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <input name="txWifiESSID" type="text" id="txWifiESSID" disabled="true" size="4" maxlength="31">
                </div>
                <div id="LyWifiESSID2" style="position:absolute; width: 20px; z-index:8; left: 162px; top: 0px; height: 21px;" >
                    <input name="ManualChange" type="button" class="button" id="ManualChange" value="手动修改" border="0" width="12" height="21" onClick="Wifi_ManualChange()">
                </div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lySecurityType" style="position: absolute; width: 240px; height:20px; z-index: 1; top:223px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow190);</script>
                <div id="LySecurityType1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <select name="selSecurityType" style="width: 125" onchange="NetWifiSecurityType_change()">
                        <option value="0" selected>none</option>
                        <option value="1">WEP</option>
                        <option value="2">WPA-PSK</option>
                        <option value="3">WPA2-PSK</option>
                    </select>
                </div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lySecKeyFormat" style="position: absolute; width: 240px; height:20px; z-index: 1; top:248px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow191);</script>
                <div id="LySecKeyFormat1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <select name="selSecKeyFormat" style="width: 125">
                        <option value="0">hex</option>
                        <option value="1">ascii</option>
                    </select></div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lySecKeyType" style="position: absolute; width: 240px; height:20px; z-index: 1; top:271px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow192);</script>
                <div id="LySecKeyType1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <select name="dSecKeyType" style="width: 125" onchange="NetWifiSecKeyType_change()">
                        <option value="0"><script>OutPrint(JS_cShow193);</script></option>
                        <option value="1"><script>OutPrint(JS_cShow194);</script></option>
                        <option value="2"><script>OutPrint(JS_cShow195);</script></option>
                    </select>
                </div>
                <div id="lySecKey2" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <select name="selKeyMode" id="selKeyMode" style="width: 125">
                        <option value="1">TKIP</option>
                        <option value="2">AES</option>
                    </select>
                </div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lyPassWord" style="position: absolute; width: 240px; height:20px; z-index: 1; top:296px; left: 0px;">
                <p align="left"><script>OutPrint(JS_cShow3);</script>
                <div id="LyPassWord１" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <input name="txwifiPassWord" type="password" id="txwifiPassWord" size="16" maxlength="64">
                </div>
            </div></td></tr>
        <tr><td height="25">
            <div id="lySecKey" style="position: absolute; width: 240px; height:25px; z-index: 1; top:320px; left: 0px;">
                <div id="lySecKeyTop" style="position:absolute; width:104px; height:23px; z-index:1"><p align="left"><script>OutPrint(JS_cShow196);</script></div>


                <div id="lySecKey1" style="position:absolute; width: 20px; z-index:8; left: 104px; top: 0px;" >
                    <select name="selSecKey" style="width: 45px">
                        <option value="0">1</option>
                        <option value="1">2</option>
                        <option value="2">3</option>
                        <option value="3">4</option>
                    </select>
                </div>

                <div id="set" style="position:absolute; width:60px; height:22px; left: 177px; top: 4px;"><b><a href="JavaScript:NetWifiParaSet()"><script>OutPrint(JS_cShow20);</script></a></b></div>
            </div></td></tr>
        <tr><td height="100"><div id="lybackWifi" style="position: absolute; z-index: 5; left: 187px; top: 430px; width: 56px;"> <b><a href="javascript:DHT_NetWork()">
            <script>OutPrint(JS_cShow155);</script>
        </a></b></div>

            <div id="lyWifiInfo" style="position:absolute; width:250px; height:82px; z-index:1; left: 3px; top: 348px;"><script>OutPrint(JS_cShow197);</script></div>
        </td></tr>
    </table>
</div>
<div id="ly3GMessage" style="position:absolute; width:261px; height:462px; z-index:15; left: 1px; visibility: hidden;">
    <div id="ly3GDeviceInfo" style="position:absolute; width:100px; height:21px; z-index:2; top: -45px;">
        <div id="ly3GDeviceStatues" style="position:absolute; width:232px; z-index:6; top: 30px; left: 0px; height: 30px;">
            <div id="ly3GDeviceStatues1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"> <script>OutPrint(JS_cShow311);</script></div>
            <div id="ly3GDeviceStatues2" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
                <select name="sel3GDeviceStatues" size="1" id="sel3GDeviceStatues" disabled="true" style="width: 100px;">
                    <option value="0">DT</option>
                    <option value="1">EVDO</option>
                    <option value="2">WCDMA</option>
                    <option value="3">NONE</option>
                </select>
            </div>
        </div>
        <div id="ly3GStatues" style="position:absolute; width:232px; z-index:5; top: 55px; left: 0px; height: 30px;">
            <div id="ly3GStatues1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"> <script>OutPrint(JS_cShow312);</script></div>
            <div id="ly3GStatues2" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
                <select name="sel3Gstatues" size="1" id="sel3Gstatues" disabled="true" style="width: 100px;">
                    <option value="0"><script>OutPrint(JS_cShow313);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow314);</script></option>
                </select>
            </div>
        </div>
        <div id="ly3GIntensity" style="position:absolute; width:232px; z-index:4; top: 80px; left: 0px; height: 30px;">
            <div id="ly3GIntensity1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow315);</script></div>
            <div id="ly3GIntensity2" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
                <select name="selIntensity" size="1" id="selIntensity" disabled="true" style="width: 100px;">
                    <option value="0"><script>OutPrint(JS_cShow330);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow331);</script></option>
                    <option value="2"><script>OutPrint(JS_cShow332);</script></option>
                    <option value="3"><script>OutPrint(JS_cShow333);</script></option>
                </select>
            </div>
        </div>
        <div id="ly3GPPPOE" style="position:absolute; width:228px; height:25px; z-index:3; top: 105px; left: 0px;">
            <div id="ly3GPPPOE1" style="position:absolute; width:90px; height:18px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow316);</script>
            </div>
            <div id="ly3GPPPOE2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="tx3GPPPOE" type="text" id="tx3GPPPOE" style="width: 100;" disabled="true" >
            </div>
        </div>
        <div id="ly3GStarttime" style="position:absolute; width:228px; height:25px; z-index:2; top: 130px; left: 0px;">
            <div id="lyStarttime1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow317);</script></div>
            <div id="lyStarttime2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="tx3GStartTime" type="text" id="tx3GStartTime" style="width: 100;" disabled="true">
            </div>
        </div>
    </div>
    <div id="ly3GDIALOG" style="position:absolute; width:10px; height:10px; z-index:2; top: 100px;">
        <div id="ly3GDialogShutDownSet" style="position:absolute; z-index:3; top: -85px; left: 210px; width: 38px;"> <b><a href="JavaScript:G3_DialogShutDownSet()">
            <script>OutPrint(JS_cShow335);</script>
        </a></b> </div>
        <div id="ly3GDIALOGType" style="position:absolute; width:232px; z-index:6; top: 30px; left: 0px; height: 30px;">
            <div id="ly3GStartType" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow318);</script></div>
            <div id="ly3GStartType1" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
                <select name="sel3GStartType" size="1" id="sel3GStartType" style="width: 100;">
                    <option value="0"><script>OutPrint(JS_cShow319);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow320);</script></option>
                    <option value="2"><script>OutPrint(JS_cShow321);</script></option>
                    <option value="3"><script>OutPrint(JS_cShow322);</script></option>
                </select>
            </div>
        </div>
        <div id="ly3GStatues" style="position:absolute; width:232px; z-index:5; top: 55px; left: 0px; height: 30px;">
            <div id="ly3GStopTime" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow323);</script></div>
            <div id="ly3GStopTime1" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
                <select name="sel3GStopType" size="1" id="sel3GStopType" style="width: 100;">
                    <option value="0"><script>OutPrint(JS_cShow324);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow325);</script></option>
                </select>
            </div>
        </div>
        <div id="ly3GIntensity" style="position:absolute; width:232px; z-index:4; top: 80px; left: 0px; height: 30px;">
            <div id="ly3GIntensity1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow326);</script></div>
            <div id="ly3GIntensity2" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
                <input name="tx3GIntensity" type="text" id="tx3GIntensity" style="width: 100;" onChange="value=value.replace(/[^\d]/g,'')">
            </div>
        </div>
        <div id="ly3GDialogSet" style="position:absolute; z-index:1; top: 85px; left: 210px; width: 38px;"> <b><a href="JavaScript:G3_DialogSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
    </div>
    <div id="ly3GMessageInfo" style="position:absolute; width:250px; height:22px; z-index:2; top: 230px;">
        <script>OutPrint(JS_cShow327);</script>
        <div id="ly3GDeviceStatues" style="position:absolute; width:232px; z-index:6; top: 25px; left: 0px; height: 30px; visibility: hidden;">
            <div id="ly3GNOTIFY1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow328);</script></div>
            <div id="lyNOTIFY2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="tx3GNOTIFY" type="text" id="tx3GNOTIFY" style="width: 100;" >
            </div>
        </div>
        <div id="ly3GMessageSet" style="position:absolute; z-index:1; top: 60px; left: 210px; width: 38px; height: 17px;"> <b><a href="JavaScript:G3_MessageSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
        <div id="ly3GPhone1" style="position:absolute; width:264px; z-index:6; top: 30px; left: 0px; height: 30px;">
            <div id="ly3GNUM1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow329);</script></div>
            <div id="ly3GNUM11" style="position:absolute; width:125px; height:25px; z-index:2; top: 0px; left: 151px;">
                <input name="tx3GNUM1" type="text" id="tx3GNUM1" style="width: 100;"onChange="value=value.replace(/[^\d]/g,'')">
            </div>
            <div id="ly3GStartType1" style="position:absolute; width:125px; z-index:1; top: 1px; left: 100px;">
                <select name="sleNumber" size="1" id="sleNumber" style="width: 50;" onChange="JavaScript:G3_MessageGet()">
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">5</option>
                </select>
            </div>
        </div>
    </div>
    <div id="lyback3G" style="position: absolute; z-index: 5; left: 210px; top: 420px; width: 56px;"> <b><a href="javascript:DHT_NetWork()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b></div>
    <div id="lyG3ShowAdvance" style="position:absolute; left: 2px; top: 420px; visibility: hidden;"><b><a href="JavaScript:DHT_GAdvanceMessage()">
        <script>OutPrint(JS_cShow339);</script>
    </a></b></div>
    <div id="ly3GVPND" style="position:absolute; width:250px; height:22px; z-index:2; top: 305px; left: 0px;">
        <script>OutPrint(JS_cShow410);</script>
        <div id="ly3GMessageSet" style="position:absolute; z-index:1; top: 87px; left: 210px; width: 38px; height: 17px;"> <b><a href="JavaScript:G3_VPDNSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
        <div id="ly3GDialNumber" style="position:absolute; width:232px; z-index:6; top: 25px; left: 0px; height: 30px;">
            <div id="ly3GDialNumber1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow411);</script>
            </div>
            <div id="ly3GNUM11" style="position:absolute; width:125px; height:25px; z-index:2; top: 0px; left: 100px;">
                <input name="tx3GDialNumber" type="text" id="tx3GDialNumber" style="width: 100;"onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="23">
            </div>
        </div>
        <div id="ly3GPhone2" style="position:absolute; width:232px; z-index:6; top: 50px; left: 0px; height: 30px;">
            <div id="ly3GAccounter1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow412);</script>
            </div>
            <div id="ly3GNUM22" style="position:absolute; width:125px; height:25px; z-index:3; top: 0px; left: 100px;">
                <input name="tx3GAccounter" type="text" id="tx3GAccounter" style="width: 100;" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="23">
            </div>
        </div>
        <div id="ly3GPhone3" style="position:absolute; width:232px; z-index:6; top: 75px; left: 0px; height: 30px;">
            <div id="ly3GPassword1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow413);</script>
            </div>
            <div id="ly3GNUM33" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="tx3GPassword" type="password" id="tx3GPassword" style="width: 100;"onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="23">
            </div>
        </div>
    </div>
</div>
<div id="lyNTPAdvance" style="position:absolute; width:230px; height:427px; z-index:11; left: 5px; top: 0px; visibility: hidden;">
    <div id="LyNTP" style="position:absolute; width:223px; height:34px; z-index:6; top: 0px;">
        <div id="lylabTital" style="position:absolute; width:108px; height:17px; z-index:51; left: 0px; top: 12px;">
            <p>
                <script>OutPrint(JS_cShow614);</script>
            </p>
        </div>

        <div id="lyOSDA3" style="position:absolute; width:232px; height:25px; z-index:56; top: 30px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:70px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow615);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 75px;">
                <input name="txNTPIP" type="text" id="txNTPIP" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="16">
            </div>
        </div>

        <div id="lyOSDA2" style="position:absolute; width:232px; height:25px; z-index:53; top: 60px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:70px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow616);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 75px;">
                <input name="txNTPPort" type="text" id="txNTPPort" onChange="value=value.replace(/[^\d]/g,'')" maxlength="5">
            </div>
        </div>

        <div id="lyOSDA2" style="position:absolute; width:232px; height:25px; z-index:53; top: 90px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:70px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow617);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 75px;">
                <input name="txNTPIntelval" type="text" id="txNTPIntelval" onChange="value=value.replace(/[^\d]/g,'')" maxlength="5">
            </div>
        </div>
        <div id="lyNTPSet" style="position:absolute; z-index:55; top: 120px; left: 180px; width: 38px;">
            <b><a href="JavaScript:NVR_SetNTP()"><script>OutPrint(JS_cShow20);</script></a></b>
        </div>

    </div>
    <div id="lyback" style="position: absolute; z-index: 5; left: 187px; top: 400px;">
        <b><a href="javascript:DHT_NetWork()"><script>OutPrint(JS_cShow155);</script></a></b>
    </div>
</div>
<div id="lyFTPAdvance" style="position:absolute; width:230px; height:427px; z-index:11; left: 5px; top: 0px; visibility: hidden;">
    <div id="LyFTP" style="position:absolute; width:223px; height:34px; z-index:6; top: 0px;">
        <div id="lyOSDA3" style="position:absolute; width:232px; height:25px; z-index:56; top: 150px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow7);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="txFTPPort" type="text" id="txFTPPort" onChange="value=value.replace(/[^\d]/g,'')" maxlength="16">
            </div>
        </div>
        <div id="lyOSDASet" style="position:absolute; z-index:55; top: 180px; left: 200px; width: 38px;"> <b><a href="JavaScript:NVR_SetFTP()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
        <div id="Layer6" style="position:absolute; width:235px; height:8px; z-index:6; top: 190px; left: 0px;">
            <hr>
        </div>
        <div id="lyFTPSnapTimer" style="position:absolute; width:223px; height:34px; z-index:6; top: 210px;">
            <p><script>OutPrint(JS_cShow648);</script></p>
            <div id="lySnapEnable" style="position:absolute; width:221px; height:36; z-index:14; left: 0;top:10px;" >
                <div style="position: absolute; width: 21px; height: 20px; z-index: 14; top: 10; left: 1px;" id="lyPPPEnabled" tabindex=6>
                    <input name="chkSnapTimerEnable" type="checkbox" id="chkSnapTimerEnable"  value="ON">
                </div>
                <div align="left">
                    <div id="Layer31" style="position:absolute; z-index:12; left: 38px; height: 21px; top: 13px;">
                        <script>OutPrint(JS_cShow649);</script></div>
                </div>
            </div>
            <div id="lySnapQuality" style="position:absolute; width:200px; height:20px; z-index:1; top: 50px; left: 0;">
                <div id="IDEfullsize" style="position:absolute; width:80px; left:1px; top:4px; ">
                    <script>OutPrint(JS_cShow650);</script>
                </div>
                <div style="position:absolute; width:149px; left:81px; top:0px; ">
                    <select name="selFTPTimeType" id="selFTPTimeType" style="width: 80" >
                        <option value="0"><script>OutPrint(JS_cShow651);</script></option>
                        <option value="1"><script>OutPrint(JS_cShow652);</script></option>
                        <option value="2"><script>OutPrint(JS_cShow653);</script></option>
                    </select>
                </div>
            </div>
            <div id="lySnapInterval" style="position:absolute; width:200px; height:20px; z-index:1; top: 80px; left: 0;">
                <div id="IDEfreesize" style="position:absolute; width:80px; left:1px; top:4px; ">
                    <script>OutPrint(JS_cShow654);</script>
                </div>
                <div style="position:absolute; width:149px; left:81px; top:0px; ">
                    <select name="selFTPTime_H" id="selFTPTime_H" style="width: 40" >
                    </select>:		<select name="selFTPTime_M" id="selFTPTime_M" style="width: 40" >
                </select>:		<select name="selFTPTime_S" id="selFTPTime_S" style="width: 40" >
                </select>
                </div>
            </div>
            <div id="lyOSDASet" style="position:absolute; z-index:55; top: 110px; left: 200px; width: 38px;"> <b><a href="JavaScript:NVR_SetFTPConfig(2)">
                <script>OutPrint(JS_cShow20);</script>
            </a></b> </div>
            <div id="Layer6" style="position:absolute; width:235px; height:8px; z-index:6; top: 120px; left: 0px;">
                <hr>
            </div>
        </div>
        <div id="lyOSDA2" style="position:absolute; width:232px; height:25px; z-index:53; top: 30px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow609);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="txFTPAddress" type="text" id="txFTPAddress" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="63">
            </div>
        </div>
        <div id="lyOSDA2" style="position:absolute; width:232px; height:25px; z-index:53; top: 60px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow610);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="txFTPPath" type="text" id="txFTPPath" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="63">
            </div>
        </div>
        <div id="lyOSDA2" style="position:absolute; width:232px; height:25px; z-index:53; top: 90px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow2);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="txFTPUserName" type="text" id="txFTPUserName" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="16">
            </div>
        </div>
        <div id="lyOSDA2" style="position:absolute; width:232px; height:25px; z-index:53; top: 120px; left: 0px;">
            <div id="lyOSDAA22" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow3);</script>
            </div>
            <div id="lyOSDAA21" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="txFTPPassword" type="password" id="txFTPPassword" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="16">
            </div>
        </div>
        <div id="lylabTital" style="position:absolute; width:108px; height:17px; z-index:51; left: 0px; top: 12px;">
            <p>
                <script>OutPrint(JS_cShow613);</script>
            </p>
        </div>
    </div>
    <div id="lyback" style="position: absolute; z-index: 5;width:230; left: 200px; top: 420px;"> <b><a href="javascript:DHT_NetWork()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b>
    </div>
</div>
<div id="lyOSDParam" style="position: absolute; width: 224px; height: 459px; z-index: 5; left: 15px; top: 0px; overflow: hidden; visibility: hidden;">

    <div id="lyOSDType" style="position:absolute; width:224px; height:27px; z-index:6; left: 0px; top: 84px;">
        <select name="selOSDType" id="select" onChange="VQ_SetOSDType()" style="position: absolute; float: right; width: 120px; left: 105px; top: 0; height: 23px; z-index: 7" size="1">
            <option value="0"><script>OutPrint(JS_cShow78);</script></option>
            <option value="1"><script>OutPrint(JS_cShow79);</script></option>
            <option value="2"><script>OutPrint(JS_cShow83);</script></option>
            <option selected value="3"><script>OutPrint(JS_cShow81);</script></option>
        </select>
        <div id="lyOSDTitle" style="position: absolute; width: 100px; height: 20px; z-index: 7; left: 2px; top: 2px" class="Rec">
            <script>OutPrint(JS_cShow82);</script></div>
    </div>
    <div id="lyChannelTitle" style="position: absolute; width: 224px; height: 26px; z-index: 6; left: 0px; top: 4px">
       <!--webbot bot="Validation" b-value-required="TRUE" i-maximum-length="32" -->
        <input name="txChannelTitle" type="text" id="txChannelTitle" size="12" style="position: absolute; float: right; left: 96px; top: -1px; z-index: 7; width: 120px; height: 20px" maxlength="32">
        <div id="lyCTTitle" style="position: absolute; width: 96px; height: 22px; z-index: 7; left: 0px; top: 3px" class="Rec">
            <script>OutPrint(JS_cShow80);</script></div>
        <div id="bSetChTitleParent" style="position:absolute; z-index:7; left: 184px; top: 31px">
            <b><a href="JavaScript:VQ_SetChannelTitle()"><script>OutPrint(JS_cShow20);</script></a></b></div>
    </div>
    <div id="lyChTitlePos" style="position:absolute; width:224px; height:27px; z-index:6; left: 0px; top: 147px;">
        <select name="selChTitlePos" id="select7" onChange="VQ_SetCTP()" style="position: absolute; float: right; width: 120px; left: 105px; top: 0px; height: 23; z-index: 7" size="1">
            <option value="0">0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9 ">9</option>
            <option value="10">10</option>
            <option value="11 ">11 </option>
            <option value="12">12</option>
            <option value="13">13</option>
            <option value="14">14</option>
            <option selected value="15">15</option>
        </select>
        <div id="lyCTPTitle" style="position: absolute; width: 100px; height: 20px; z-index: 7; left: 0px; top: 2px" class="Rec">
            <script>OutPrint(JS_cShow84);</script></font></div>
    </div>
    <div id="lyDataTimePos" style="position:absolute; width:224px; height:25px; z-index:6; left: 0px; top: 116px;">
        <select name="selDataTimePos" id="select8" onChange="VQ_SetDTP()" style="position: absolute; float: right; width: 120px; left: 105px; top: 0px; height: 23; z-index: 7" size="1">
            <option value="0" selected>0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
            <option value="11">11</option>
            <option value="12">12</option>
            <option value="13">13</option>
            <option value="14">14</option>
            <option value="15">15</option>
        </select>
        <div id="lyDTPTitle" style="position: absolute; width: 100; height: 22; z-index: 7; left: 2px; top: 2px" class="Rec">
            <script>OutPrint(JS_cShow85);</script></div>
    </div>
    <div id="Layer7" style="position:absolute; width:222px; height:14px; z-index:6; left: 0px; top: 58px;">
        <hr>
    </div>

    <div id="bSetOsdTypeParent" style="position:absolute; z-index:7; left: 184px; top: 181px">
        <b><a href="JavaScript:VQ_SetOSDParam()"><script>OutPrint(JS_cShow20);</script></a></b></div>
    <div id="Layer17" style="position:absolute; width:224px; height:10px; z-index:6; left: 0px; top: 206px;">
        <hr>
    </div>
    <div id="lyMoreOSD" style="position:absolute; width:224px; height:159px; left:0px; top:235px">
        <script>OutPrint(JS_cShow86);</script>

        <textarea name="txMoreOSD" cols="50" rows="10" id="txMoreOSD" wrap="off" style="overflow:scroll; position: absolute; left: 2px; top: 19px; z-index: 7; width: 211px; height: 74px"></textarea>
        <div id="lyMoreOSDtext" style="position:absolute; width:224px; height:47px; left:0px; top:102px">
            <script>OutPrint(JS_cShow87);</script>
            <input name="txOSDx" type="text" id="txOSDx" onChange="value=value.replace(/[^\d]/g,'')"  onKeyDown="value=value.replace(/[^\d]/g,'')" value="0" size="3" maxlength="3">
            <script>OutPrint(JS_cShow88);</script><input name="txOSDy" type="text" id="txOSDy" onChange="value=value.replace(/[^\d]/g,'')"  onKeyDown="value=value.replace(/[^\d]/g,'')" value="0" size="3" maxlength="3">
            <div id="lyMoreOSDset" style="position:absolute; left:184px; top:26px"><b><a href="JavaScript:VQ_OsdTextEx_Set()">
                <script>OutPrint(JS_cShow20);</script></a></b></div>
        </div>
    </div><div style="position: absolute; width: 232px; height: 25px; z-index: 8; left: 0px; top: 400px;" id="lyVideoCover">
    <table border="0" width="100%" id="table15" height="30" cellspacing="0" cellpadding="0">
        <tr>
            <td width="20">
                <input type="checkbox" name="chkVideoCover" value="ON" onClick="DHT_DrawVC()"></td>
            <td colspan="2"><script>OutPrint(JS_cShow71);</script></td>
        </tr>
    </table>
</div>
</div>
<div id="lyDiskInfo" style="position:absolute; width:261px; height:462px; z-index:2; left: 1px; top: 0px; visibility: hidden;">
    <div id="lyUsbInfo" style="position:absolute; width:265px; height:110px; z-index:1; left: 0px; top: -15px;">
        <div id="lyDiskInfoShow" style="position:absolute; width:190px; height:18px; z-index:7; left: 0px; top: 116px;"></div>
        <div id="lyDiskFormat" style="position: absolute; z-index: 6; left: 150px; top: 94px; height: 12px;"> <b><a href="javascript:Store_FormatDisk()">
            <script>OutPrint(JS_cShow297);</script>
        </a></b></div>
        <div id="lyDiskFormatSTAT" style="position: absolute; z-index: 6; left: 150px; top: 140px; height: 12px;"> <b><a href="javascript:Store_FormatDiskSTAT()">
            <script>OutPrint(JS_cShow507);</script>
        </a></b></div>
        <div id="lyDeviceinfo" style="position:absolute; width:120px; height:10px; z-index:1; left: 0px; top: 8px;">
            <script>OutPrint(JS_cShow89);</script></div>
        <div id="lyUsbInfo" style="position:absolute; width:108px; height:20px; z-index:1; top: 35px; left: 140px;">
            <div id="usbinfo" style="position:absolute; width:48px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow90);</script></div>
            <div id="usbstatus" style="position:absolute; width:64px; left:46px; top:-1px; ">
                <select id="selUSBStatus" name="selUSBStatus" disabled="true" style="width:74px " >
                    <option value="0"><script>OutPrint(JS_cShow291);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow292);</script></option>
                    <option value="2"><script>OutPrint(JS_cShow293);</script></option>
                    <option value="3"><script>OutPrint(JS_cShow294);</script></option>
                    <option value="4"><script>OutPrint(JS_cShow295);</script></option>
                </select></div>
        </div>
        <div id="lyUsbInfoNO" style="position:absolute; width:111px; height:20px; z-index:1; top: 35px; left: 0px;">
            <div id="usbinfono" style="position:absolute; width:70px; left:0px; top:4px; ">
                <script>OutPrint(JS_cShow286);</script></div>
            <div id="usbNO" style="position:absolute; width:54px; left:75px; top:-1px; ">
                <select id="selUSBNO" name="selUSBNO" style="width:50 " onChange="Mem_GetUSBDiskInfo()">
                    <option value="0">1</option>
                    <option value="1">2</option>
                    <option value="2">3</option>
                    <option value="3">4</option>
                </select></div>
        </div>
        <div id="lyUsbInfoUsage" style="position:absolute; width:108px; height:20px; z-index:1; top: 60px; left: 140px;">
            <div id="usbinfoUsage" style="position:absolute; width:52px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow289);</script></div>
            <div id="usbUsage" style="position:absolute; width:64px; left:46px; top:0px; ">
                <select id="selUSBUsage" name="selUSBUsage" disabled="true" style="width:74px " >
                    <option value="0"><script>OutPrint(JS_cShow287);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow288);</script></option>
                </select></div>
        </div>
        <div id="lyUsbFullSize" style="position:absolute; width:131px; height:20px; z-index:1; top: 60px; left: 0px;">
            <div id="usbfullsize" style="position:absolute; width:68px; left:0px; top:4px; ">
                <script>OutPrint(JS_cShow93);</script></div>
            <div style="position:absolute; width:65px; left:75px; top:0px; ">
                <input name="txUsbFullSize" type="text" value="0" disabled="true" style="width:50 " >
            </div>
        </div>
        <div id="lyUsbFreeSize" style="position:absolute; width:130px; height:20px; z-index:1; top: 84px; left: 0px;">
            <div id="usbfreesize" style="position:absolute; width:67px; left:0px; top:4px; ">
                <script>OutPrint(JS_cShow94);</script></div>
            <div style="position:absolute; width:66px; left:75px; top:0px; ">
                <input name="txUsbFreeSize" type="text" disabled="true" value="0" style="width:50 ">
            </div>
        </div>
        <div id="lyUsbPart" style="position:absolute; width:108px; height:20px; z-index:1; top: 84px; left: 140px;">
            <div id="usbinfoPart" style="position:absolute; width:63px; left:-4px; top:4px; visibility: hidden;">
                <script>OutPrint(JS_cShow290);</script></div>
            <div id="usbPart" style="position:absolute; width:64px; left:46px; top:0px; visibility: hidden;">
                <select id="select12" name="selUSBPart" disabled="true" style="width:74px " >
                    <option value="0">1</option>
                    <option value="1">2</option>
                    <option value="2">3</option>
                    <option value="3">4</option>
                </select>
            </div>
        </div>
        <div id="lyDiskClear" style="position: absolute; z-index: 5; left: 72px; top: 117px; height: 12px; visibility: hidden;">
            <b><a href="javascript:Store_ClearDisk()">
                <script>OutPrint(JS_cShow296);</script></a></b></div>
    </div>
    <div id="lyIDEInfo" style="position:absolute; width:262px; height:114px; z-index:2; top: 115px; left: 0px;">
        <div id="SATAinfoNumTital" style="position:absolute; width:75px; left:1px; top:12px; ">
            <script>OutPrint(JS_cShow95);</script></div>
        <div id="lySATAInfoNO" style="position:absolute; width:132px; height:20px; z-index:1; top: 35px; left: 0px;">
            <div id="SATAinfono" style="position:absolute; width:75px; left:1px; top:4px; ">
                <script>OutPrint(JS_cShow286);</script></div>
            <div id="SATANO" style="position:absolute; width:55px; left:75px; top:0px; ">
                <select id="selSATANO" name="selSATANO" style="width:50px " onChange="Mem_GetSATADiskInfo()">
                    <option value="0">1</option>
                    <option value="1">2</option>
                    <option value="2">3</option>
                    <option value="3">4</option>
                    <option value="4">5</option>
                    <option value="5">6</option>
                    <option value="6">7</option>
                    <option value="7">8</option>
                </select></div>
        </div>
        <div id="lySATAInfo" style="position:absolute; width:104px; height:20px; z-index:1; top: 35px; left: 140px;">
            <div id="SATAinfo" style="position:absolute; width:48px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow90);</script></div>
            <div id="SATAstatus" style="position:absolute; width:64px; left:46px; top:0px; ">
                <select id="selSATAStatus" name="selSATAStatus" disabled="true" style="width:74px " >
                    <option value="0"><script>OutPrint(JS_cShow291);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow292);</script></option>
                    <option value="2"><script>OutPrint(JS_cShow293);</script></option>
                    <option value="3"><script>OutPrint(JS_cShow294);</script></option>
                    <option value="4"><script>OutPrint(JS_cShow295);</script></option>
                </select></div>
        </div>
        <div id="lySATAInfoUsage" style="position:absolute; width:106px; height:20px; z-index:1; top: 60px; left: 140px;">
            <div id="SATAinfoUsage" style="position:absolute; width:62px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow289);</script></div>
            <div id="SATAUsage" style="position:absolute; width:64px; left:46px; top:0px; ">
                <select id="selSATAUsage" name="selSATAUsage" disabled="true" style="width:74px " >
                    <option value="0"><script>OutPrint(JS_cShow287);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow288);</script></option>
                </select></div>
        </div>
        <div id="lyIDEFullSize" style="position:absolute; width:131px; height:20px; z-index:1; top: 60px; left: 0px;">
            <div id="IDEfullsize" style="position:absolute; width:74px; left:1px; top:4px; ">
                <script>OutPrint(JS_cShow96);</script></div>
            <div style="position:absolute; width:48px; left:75px; top:0px; ">
                <input name="txIDEFullSize" type="text" value="0" disabled="true" style="width:50 ">
            </div>
        </div>
        <div id="lyIDEFreeSize" style="position:absolute; width:123px; height:20px; z-index:1; top: 84px; left: 0px;">
            <div id="IDEfreesize" style="position:absolute; width:74px; left:0px; top:4px; ">
                <script>OutPrint(JS_cShow94);</script></div>
            <div style="position:absolute; width:48px; left:75px; top:0px; ">
                <input name="txIDEFreeSize" type="text" value="0" disabled="true" style="width:50 ">
            </div>
        </div>
        <div id="lySATAPart" style="position:absolute; width:106px; height:20px; z-index:1; top: 84px; left: 140px;">
            <div id="SATAinfoPart" style="position:absolute; width:67px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow290);</script></div>
            <div id="SATAPart" style="position:absolute; width:64px; left:46px; top:0px; ">
                <select id="selSATAPart" name="selSATAPart" disabled="true" style="width:74px " >
                    <option value="0">1</option>
                    <option value="1">2</option>
                    <option value="2">3</option>
                    <option value="3">4</option>
                </select></div>
        </div>
    </div>
    <div id="lyNetDev" style="position:absolute; width:262px; height:182px; z-index:2; top: 230px; left: 0px;">
        <hr>
        <script>OutPrint(JS_cShow97);</script>
        <div id="lyNFSInfoNO" style="position:absolute; width:129px; height:20px; z-index:1; top: 35px; left: 0px;">
            <div id="NFSinfono" style="position:absolute; width:74px; left:0px; top:4px; ">
                <script>OutPrint(JS_cShow286);</script></div>
            <div id="NFSNO" style="position:absolute; width:54px; left:75px; top:0px; ">
                <select id="selNFSNO" name="selNFSNO" style="width:50px " onChange="Mem_GetNFSDiskInfo()" >
                    <option value="0" selected>1</option>
                </select></div>
        </div>
        <div id="lyNFSInfoUsage" style="position:absolute; width:108px; height:20px; z-index:1; top: 60px; left: 140px;">
            <div id="NFSinfoUsage" style="position:absolute; width:54px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow289);</script></div>
            <div id="NFSUsage" style="position:absolute; width:64px; left:46px; top:0px; ">
                <select id="selNFSUsage" name="selNFSUsage" disabled="true" style="width:74px " >
                    <option value="0"><script>OutPrint(JS_cShow287);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow288);</script></option>
                </select></div>
        </div>
        <div id="lyNetFullSize" style="position:absolute; width:125px; height:20px; z-index:1; top: 60px; left: 0px;">
            <div id="Netfullsize" style="position:absolute; width:68px; left:1px; top:2px; ">
                <script>OutPrint(JS_cShow96);</script></div>
            <div style="position:absolute; width:47px; left:75px; top:0px; ">
                <input name="txNetFullSize" type="text" value="0" disabled="true" style="width:50 ">
            </div>
        </div>
        <div id="lyNetFreeSize" style="position:absolute; width:128px; height:20px; z-index:1; top: 84px; left: 0px;">
            <div id="Netfreesize" style="position:absolute; width:67px; left:0px; top:4px; ">
                <script>OutPrint(JS_cShow94);</script></div>
            <div style="position:absolute; width:46px; left:75px; top:0px; ">
                <input name="txNetFreeSize" type="text" value="0" disabled="true" style="width:50 ">
            </div>
        </div>
        <div id="lyNetStatus" style="position:absolute; width:108px; height:20px; z-index:1; top: 35px; left: 140px;">
            <div id="Netstatus" style="position:absolute; width:60px; left:-4px; top:4px; ">
                <script>OutPrint(JS_cShow90);</script></div>
            <div style="position:absolute; width:64px; left:46px; top:0px; " id="lyNetstatusEx">
                <select id="selNetStatus" name="selNetStatus" disabled="true" style="width:74px ">
                    <option value="0"><script>OutPrint(JS_cShow291);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow292);</script></option>
                    <option value="2"><script>OutPrint(JS_cShow293);</script></option>
                    <option value="3"><script>OutPrint(JS_cShow294);</script></option>
                    <option value="4"><script>OutPrint(JS_cShow295);</script></option>
                </select>
            </div>
        </div>
        <div id="lyNetIP" style="position:absolute; width:189px; height:20px; z-index:1; top: 110px; left: 0px;">
            <div id="Netip" style="position:absolute; width:68px; left:1px; top:4px; ">
                <script>OutPrint(JS_cShow101);</script></div>
            <div style="position:absolute; width:108px; left:75px; top:0px; " id="lyNetIPEx">
                <input name="txNetIP" type="text" value="0" style="width:140 "></div>
        </div>
        <div id="lyNetPath" style="position:absolute; width:183px; height:20px; z-index:1; top: 133px; left: 0px;">
            <div id="Netpath" style="position:absolute; width:80px; left:1px; top:4px; ">
                <script>OutPrint(JS_cShow102);</script></div>
            <div style="position:absolute; width:119px; left:75px; top:0px; " id="lyNetPathEx">
                <input name="txNetPath" type="text" value="0" style="width:140px "></div>
        </div>
        <div id="lyNetSet" style="position:absolute; width:69px; height:20px; z-index:1; top: 159px; left: 171px;">
            <div align="right">
                <b>
                    <a  href="JavaScript:Mem_SetNetDev()" class="style12">
                        <script>OutPrint(JS_cShow20);</script>
                    </a>
                </b>
            </div>
        </div>
    </div>
    <div id="lyback" style="position: absolute; z-index: 5; left: 206px; top: 419px;">
        <b><a href="javascript:DHT_MemorySet()">
            <script>OutPrint(JS_cShow155);</script></a></b></div>
</div>
<div id="lyPTZCruise" style="position:absolute; width:230px; height:474px; z-index:2; left: 5px; top: 0px; visibility: hidden;">
    <div id="lyDDNScheck" style="position:absolute; width:200px; z-index:1; top: 20px; left: 0px;">
        <input type="checkbox" name="chkPTZCruiseEnable" value="ON">
        <script>OutPrint(JS_cShow502);</script></div>
    <div id="lyPTZCruiseNum" style="position:absolute; width:230px; z-index:1; top: -10px; height: 0px;">
        <div id="lyDDNSnvsDomain1" style="position:absolute; width:90px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow501);</script></div>
        <div id="lyDDNSnvsDomain2" style="position:absolute; width:88px; z-index:1; top: 0px; left: 100px;">
            <select name="selPTZCruise" style="position:absolute; width:120px; left: 0px; top: 0px;" onChange="DEV_PTZCruiseGet()" onClick="DEV_PTZCruiseGet()">
                <option value="0">0</option>
                <option value="1">1</option>
            </select></div>
    </div>
    <div id="lyPTZCruiseNumCount" style="position:absolute; width:232px; z-index:1; top: 45px; left: 0px;">
        <div id="lyDDNSDomain1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow503);</script></div>
        <div id="lyDDNSDomain2" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px; height: 24px;">
            <select name="selPTZCruiseNum" style="position:absolute; width:120px; left: 0px; top: 0px;" onChange="DEV_PTZGetCruiseNum()" onClick="DEV_PTZGetCruiseNum()">
                <option value="0">0</option>
                <option value="1">1</option>
            </select>

        </div>
    </div>
    <div id="lyDDNSSet" style="position:absolute; z-index:1; top: 380px; left: 189px; width: 38px;">
        <b><a href="JavaScript:DEV_PTZCruiseSet()">
            <script>OutPrint(JS_cShow20);</script></a></b>	</div>
    <div id="lyCmdSet" style="position:absolute; z-index:1; top: 380px; left: 70px; width: 38px;">
        <b><a href="JavaScript:DEV_PTZCruiseAction(1)">
            <script>OutPrint(JS_cShow47);</script></a></b>	</div>
    <div id="lyCmdSet2" style="position:absolute; z-index:1; top: 380px; left: 130px; width: 38px;">
        <b><a href="JavaScript:DEV_PTZCruiseAction(0)">
            [
            <script>OutPrint(JS_cShow113);</script>]</a></b>	</div>
    <div id="lybackDDNS" style="position: absolute; z-index: 5; left: 189px; top: 415px; width: 56px;"> <b><a href="javascript:DHT_DeviceCtrl()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b></div>
    <div id="lyCruiseNum1" style="position:absolute; width:200px; height:92px; z-index:6; left: 0px; top: 120px;">
        <div id="lyCruiseSpeed" style="position:absolute; width:232px; height:25px; z-index:4; top: 55px; left: 2px; visibility: hidden;">
            <div id="lyDDNSPassWord1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow505);</script></div>
            <div id="lyDDNSPassWord2" style="position:absolute; width:120px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise03" id="select28" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyCruiseStayTime" style="position:absolute; width:232px; height:25px; z-index:3; top: -30px; left: 2px;">
            <div id="lyDDNSUserName1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow506);</script></div>
            <div id="lyDDNSUserName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise02" id="select27" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyPreset" style="position:absolute; width:228px; height:25px; z-index:2; top: 30px; left: 2px;">
            <div id="lyDDNSPort1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"> <script>OutPrint(JS_cShow274);</script></div>
            <div id="lyDDNSPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise01" id="select11" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyTital01" style="position:absolute; width:90px; z-index:1; top: 10px; left: 2px;">
            <script>OutPrint(JS_cShow504);</script>1</div>
    </div>
    <div id="lyCruiseNum2" style="position:absolute; width:200px; height:92px; z-index:6; left: 0px; top: 195px;">
        <div id="lyCruiseSpeed" style="position:absolute; width:232px; height:25px; z-index:4; top: 55px; left: 2px; visibility: hidden;">
            <div id="lyDDNSPassWord1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow505);</script></div>
            <div id="lyDDNSPassWord2" style="position:absolute; width:120px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise13" id="select29" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyCruiseStayTime" style="position:absolute; width:232px; height:25px; z-index:3; top: 35px; left: 2px; visibility: hidden;">
            <div id="lyDDNSUserName1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow506);</script></div>
            <div id="lyDDNSUserName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise12" id="select30" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyPreset" style="position:absolute; width:228px; height:25px; z-index:2; top: 15px; left: 2px;">
            <div id="lyDDNSPort1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow274);</script></div>
            <div id="lyDDNSPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise11" id="select31" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyTital01" style="position:absolute; width:90px; z-index:1; top: -5px; left: 2px;"><script>OutPrint(JS_cShow504);</script>2</div>
    </div>
    <div id="lyCruiseNum3" style="position:absolute; width:200px; height:92px; z-index:6; left: 0px; top: 260px;">
        <div id="lyCruiseSpeed" style="position:absolute; width:232px; height:25px; z-index:4; top: 55px; left: 2px; visibility: hidden;">
            <div id="lyDDNSPassWord1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow505);</script></div>
            <div id="lyDDNSPassWord2" style="position:absolute; width:120px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise23" id="select32" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyCruiseStayTime" style="position:absolute; width:232px; height:25px; z-index:3; top: 35px; left: 2px; visibility: hidden;">
            <div id="lyDDNSUserName1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow506);</script></div>
            <div id="lyDDNSUserName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise22" id="select33" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyPreset" style="position:absolute; width:228px; height:25px; z-index:2; top: 15px; left: 2px;">
            <div id="lyDDNSPort1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow274);</script></div>
            <div id="lyDDNSPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise21" id="select34" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyTital01" style="position:absolute; width:90px; z-index:1; top: -5px; left: 2px;"><script>OutPrint(JS_cShow504);</script>3</div>
    </div>
    <div id="lyCruiseNum4" style="position:absolute; width:200px; height:92px; z-index:6; left: 0px; top: 320px;">
        <div id="lyCruiseSpeed" style="position:absolute; width:232px; height:25px; z-index:4; top: 55px; left: 2px; visibility: hidden;">
            <div id="lyDDNSPassWord1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow505);</script></div>
            <div id="lyDDNSPassWord2" style="position:absolute; width:120px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise33" id="select35" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyCruiseStayTime" style="position:absolute; width:232px; height:25px; z-index:3; top: 35px; left: 2px; visibility: hidden;">
            <div id="lyDDNSUserName1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow506);</script></div>
            <div id="lyDDNSUserName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise32" id="select36" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyPreset" style="position:absolute; width:228px; height:25px; z-index:2; top: 15px; left: 2px;">
            <div id="lyDDNSPort1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;"><script>OutPrint(JS_cShow274);</script></div>
            <div id="lyDDNSPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <select name="selCruise31" id="select37" style="position:absolute; width:120px; left: 0px; top: 0px;">
                    <option value="0">0</option>
                    <option value="1">1</option>
                </select>
            </div>
        </div>
        <div id="lyTital01" style="position:absolute; width:90px; z-index:1; top: -5px; left: 2px;"><script>OutPrint(JS_cShow504);</script>4</div>
    </div>
</div>
<div id="lyDDNSset" style="position:absolute; width:230px; height:427px; z-index:2; left: 5px; top: 0px; visibility: hidden;">
    <div id="lyDDNScheck" style="position:absolute; width:200px; z-index:1; top: 20px; left: 10px;">
        <input type="checkbox" name="chkbDDNS" value="ON"><script>OutPrint(JS_cShow235);</script>
    </div>
    <div id="lyDDNSnvsDomain" style="position:absolute; width:230px; z-index:1; top: 50px; left: 0px;">
        <div id="lyDDNSnvsDomain1" style="position:absolute; width:90px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow236);</script></div>
        <div id="lyDDNSnvsDomain2" style="position:absolute; width:88px; z-index:1; top: 0px; left: 100px;">
            <input type="text" name="txDomainName" maxlength="31">
        </div>
    </div>
    <div id="lyDDNSDomain" style="position:absolute; width:232px; z-index:1; top: 80px; left: 0px;">
        <div id="lyDDNSDomain1" style="position:absolute; width:100px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow237);</script></div>
        <div id="lyDDNSDomain2" style="position:absolute; width:125px; z-index:1; top: 0px; left: 100px;">
            <select name="selDDNSDomain" size="1">
                <option value="0">www.3322.org</option>
                <option value="1">www.ChangeIP.com</option>
            </select>
        </div>
    </div>
    <div id="lyDDNSPort" style="position:absolute; width:228px; height:25px; z-index:1; top: 110px; left: 0px;">
        <div id="lyDDNSPort1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow7);</script></div>
        <div id="lyDDNSPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
            <input type="text" name="txDDNSPort" onChange="value=value.replace(/[^\d]/g,'')">
        </div>
    </div>
    <div id="lyDDNSUserName" style="position:absolute; width:232px; height:25px; z-index:1; top: 140px; left: 0px;">
        <div id="lyDDNSUserName1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow2);</script></div>
        <div id="lyDDNSUserName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
            <input type="text" name="txDDNSUserName" maxlength="31" onChange="value=value.replace(/[^\x00-\xff]/g,'')">
        </div>
    </div>
    <div id="lyDDNSPassWord" style="position:absolute; width:232px; height:25px; z-index:1; top: 170px; left: 0px;">
        <div id="lyDDNSPassWord1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow3);</script></div>
        <div id="lyDDNSPassWord2" style="position:absolute; width:120px; height:25px; z-index:1; top: 0px; left: 100px;">
            <input type="password" name="txDDNSPassWord" maxlength="31" onChange="value=value.replace(/[^\x00-\xff]/g,'')">
        </div>
    </div>
    <div id="lyDDNSPwdConfirm" style="position:absolute; width:232px; height:25px; z-index:1; top: 200px; left: 0px;">
        <div id="lyDDNSPwdConfirm1" style="position:absolute; width:90px; height:24px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow238);</script></div>
        <div id="lyDDNSPwdConfirm2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
            <input type="password" name="txDDNSPwdConfirm" maxlength="31">
        </div>
    </div>
    <div id="lyDDNSSet" style="position:absolute; z-index:1; top: 246px; left: 185px; width: 38px;">
        <b><a href="JavaScript:Network_SetDDNS()"><script>OutPrint(JS_cShow20);</script></a></b>
    </div>
    <div id="lybackDDNS" style="position: absolute; z-index: 5; left: 190px; top: 418px; width: 56px;"> <b><a href="javascript:DHT_NetWork()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b></div>
</div>

<div id="lyAlarmAdvance" style="position:absolute; width:252px; height:463px; z-index:2; left: 5px; top: 0px; visibility: hidden;">
    <div id="lyComServerInfo" style="position:absolute; width:250px; height:116px; z-index:2; left: 0px; top: 65px;">
        <div id="lyComServer" style="position:absolute; width:232px; height:25px; z-index:3; top: 0px; left: 5px;">
            <div id="lyComServerName1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow299);</script>
            </div>
            <div id="lyComServerName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txComServerIP" style="width:115px;" type="text" id="txComServerIP" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="15">
            </div>
        </div>
        <div id="lyComServerPort" style="position:absolute; width:228px; height:25px; z-index:2; top: 27px; left: 5px;">
            <div id="lyComServerPort2" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow7);</script>
            </div>
            <div id="lyComServerPort1" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txComServerPort" style="width:115px;" type="text" id="txComServerPort" onChange="value=value.replace(/[^\d]/g,'')" maxlength="5">
            </div>
        </div>
        <div id="lyComServerSet" style="position:absolute; z-index:1; top: 30px; left: 205px; width: 42px; height: 14px;"> <b><a href="JavaScript:COM_ServerSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
    </div>
    <div style="position: absolute; width: 240; height: 11px; z-index: 5; left: 5px; top: 115px" id="layer16">
        <hr>
    </div>
    <div id="lyAlarmServerInfo" style="position:absolute; width:238px; height:120px; z-index:3; top: 0px;">
        <div id="lyAlarmServerPort" style="position:absolute; width:228px; height:25px; z-index:4; top: 27px; left: 2px;">
            <div id="lyAlarmServerPort1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow7);</script>
            </div>
            <div id="lyAlarmServerPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txAlarmServerPort" style="width:115px;" type="text" id="txAlarmServerPort" onChange="value=value.replace(/[^\d]/g,'')" maxlength="5">
            </div>
        </div>
        <div id="lyAlarmServer" style="position:absolute; width:232px; height:25px; z-index:3; top: 0px; left: 2px;">
            <div id="lyAlarmServerName1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow298);</script>
            </div>
            <div id="lyAlarmServerName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txAlarmServerIP" style="width:115px;" type="text" id="txAlarmServerIP" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="15">
            </div>
        </div>
        <div id="lyAlarmServerSet" style="position:absolute; z-index:1; top: 30px; left: 205px; width: 42px; height: 14px;"> <b><a href="JavaScript:Alarm_ServerSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
    </div>
    <div style="position: absolute; width: 240; height: 11px; z-index: 5; left: 5px; top: 50px" id="layer16">
        <hr>
    </div>
    <div id="lyEmailSetting" style="position:absolute; width:250px; height:116px; z-index:2; left: 0px; top: 130px;">
        <div id="lyEmailServerCheck" style="position:absolute; width:232px; height:25px; z-index:3; top: 0px; left: 5px;">
            <div id="lyEmailServerCheck" style="position:absolute; width:150px; height:25px; z-index:1; top: 3px; left: 2px;">
                <input type="checkbox" name="EmailSettingCheck" id="EmailSettingCheck" onClick="DHT_SetEmailEnable()"><script>OutPrint(JS_cShow618);</script>
            </div>
        </div>
        <div id="lyEmailServer" style="position:absolute; width:232px; height:25px; z-index:3; top: 26px; left: 5px;">
            <div id="lyEmailServerName1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow619);</script>
            </div>
            <div id="lyEmailServerName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txEmailServerIP" style="width:115px;" type="text" id="txEmailServerIP" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
        </div>
        <div id="lyEmailServerPort" style="position:absolute; width:228px; height:25px; z-index:2; top: 52px; left: 5px;">
            <div id="lyEmailServerPort1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow620);</script>
            </div>
            <div id="lyEmilServerPort2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txEmailServerPort" style="width:115px;" type="text" id="txEmailServerPort" onChange="value=value.replace(/[^\d]/g,'')" maxlength="5">
            </div>
        </div>
        <div id="lyEmailServerName" style="position:absolute; width:228px; height:25px; z-index:2; top: 78px; left: 5px;">
            <div id="lyEmailServerName1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow621);</script>
            </div>
            <div id="lyEmilServerName2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txEmailServerName" style="width:115px;" type="text" id="txEmailServerName" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
        </div>
        <div id="lyEmailServerPswd" style="position:absolute; width:228px; height:25px; z-index:2; top: 104px; left: 5px;">
            <div id="lyEmilServerPswd1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow622);</script>
            </div>
            <div id="lyEmailServerPswd2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txEmailServerPswd" style="width:115px;" type="password" id="txEmailServerPswd" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
        </div>
        <div id="lyEmailLogonModel" style="position:absolute; width:228px; height:25px; z-index:2; top: 130px; left: 5px;">
            <div id="lyEmailLogonModel1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow623);</script>
            </div>
            <div id="lyEmailLogonModel2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 78px;">
                <select name="selectEmailModel" id="selectEmailModel">
                    <option value="1">off</option>
                    <option value="2">plain</option>
                    <option value="3">cram-md5</option>
                    <option value="4">digest-md5</option>
                    <option value="5">gssapi</option>
                    <option value="6">external</option>
                    <option value="7">login</option>
                    <option value="8">ntlm</option>
                </select>
            </div>
        </div>
        <div id="lyEmailSendAddr" style="position:absolute; width:228px; height:120px; z-index:2; top: 156px; left: 5px;">
            <div id="lyEmailSendAddr1" style="position:absolute; width:75px; height:25px; z-index:1; top: 3px; left: 2px;">
                <script>OutPrint(JS_cShow624);</script>
            </div>
            <div id="lyEmailSendAddr2" style="position:absolute; width:115px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txEmailSendAddr1" style="width:120px;" type="text" id="txEmailSendAddr1" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
            <div id="lyEmailSendAddr3" style="position:absolute; width:115px; height:25px; z-index:1; top: 26px; left: 78px;">
                <input name="txEmailSendAddr2" style="width:120px;" type="text" id="txEmailSendAddr2" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
            <div id="lyEmailSendAddr4" style="position:absolute; width:115px; height:25px; z-index:1; top: 53px; left: 78px;">
                <input name="txEmailSendAddr3" style="width:120px;" type="text" id="txEmailSendAddr3" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
            <div id="lyEmailSendAddr5" style="position:absolute; width:115px; height:25px; z-index:1; top: 79px; left: 78px;">
                <input name="txEmailSendAddr4" style="width:120px;" type="text" id="txEmailSendAddr4" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
        </div>
        <div id="lyEmailSendTitle" style="position:absolute; width:228px; height:25px; z-index:2; top: 260px; left: 5px;">
            <div id="lyEmailSendTitle1" style="position:absolute; width:75px; height:25px; z-index:1; top: 7px; left: 2px;">
                <script>OutPrint(JS_cShow625);</script>
            </div>
            <div id="lyEmilSendTitle2" style="position:absolute; width:115px; height:25px; z-index:1; top: 0px; left: 78px;">
                <input name="txEmailSendTitle" style="width:120px;" type="text" id="txEmailSendTitle" onChange="value=value.replace(/[^\x00-\xff]/g,'')" maxlength="32">
            </div>
        </div>
        <div id="lyEmailServerSet" style="position:absolute; z-index:1; top: 263px; left: 207px; width: 42px; height: 14px;"> <b><a href="JavaScript:Email_ServerSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
    </div>
    <div id="lyAlarmServerback" style="position: absolute; z-index: 5; left: 207px; top: 425px; width: 56px;"> <b><a href="javascript:DHT_AlarmInOutCtrl()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b></div>
</div>
<div id="ly3GAdvance" style="position:absolute; width:252px; height:463px; z-index:14; left: 5px; top: 0px; visibility: hidden;">
    <div id="ly3GNotify" style="position:absolute; width:250px; height:116px; z-index:2; left: 0px; top: 177px;">
        <div id="ly3GNotifyType" style="position:absolute; width:232px; height:25px; z-index:3; top: 8px; left: 5px;">
            <div id="ly3GNotifyType1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow337);</script>
            </div>
            <div id="ly3GNotifyType2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                      <span class="border5">
                      <select  id="sel3GNotifyType" style="width: 110">
                        <option value="0"><script>OutPrint(JS_cShow340);</script></option>
                        <option value="1"><script>OutPrint(JS_cShow341);</script></option>
                        <option value="2"><script>OutPrint(JS_cShow342);</script></option>
                                        </select>
                      </span>                    </div>
        </div>
        <div id="lyComServerPort" style="position:absolute; width:228px; height:25px; z-index:2; top: 44px; left: 5px; visibility: hidden;">
            <div id="ly3GNotifyMessage1" style="position:absolute; width:90px; height:25px; z-index:1; top: 5px; left: 2px;">
                <script>OutPrint(JS_cShow338);</script>
            </div>
            <div id="ly3GNotifyMessage2" style="position:absolute; width:125px; height:25px; z-index:1; top: 0px; left: 100px;">
                <input name="tx3GNotifyMessage" type="text" id="tx3GNotifyMessage"  maxlength="63" >
            </div>
        </div>
        <div id="ly3GNotifyMessageSet" style="position:absolute; z-index:1; top: 60px; left: 190px; width: 42px; height: 14px;"> <b><a href="JavaScript:G3_NotifySet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
    </div>
    <div id="ly3GSchedule" style="position:absolute; width:238px; height:120px; z-index:3; top: 43px;">
        <div id="ly3GScheduleSet" style="position:absolute; z-index:1; top: 92px; left: 190px; width: 42px; height: 14px;"> <b><a href="JavaScript:G3_TaskScheduleSet()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
        <div id="lyTimeSegment3G" style="position:absolute; z-index:26; top: 42px; left: 0px; width: 240px;" class="border5">
            <input name="chk3Gschule" type="checkbox" id="chkTimeSect12"value="ON" onClick="NetFile_Set3GTimeEnabled()">
            <select name=select_Hour_start_3G id="select_Hour_start_3G">
            </select>
            :
            <select name=select_Minute_start_3G id="select_Minute_start_3G">
            </select>
            ~
            <select name=select_Hour_End_3G id="select_Hour_End_3G">
            </select>
            :
            <select name=select_Minute_End_3G id="select_Minute_End_3G">
            </select>
        </div>
        <div id="ly3GScheduleTital" style="position:absolute; width:116px; height:25px; z-index:1; top: 5px; left: 2px;">
            <script>OutPrint(JS_cShow336);</script>
        </div>
    </div>
    <div id="ly3GAdvanceback" style="position: absolute; z-index: 5; left: 190px; top: 418px; width: 56px;"> <b><a href="javascript:DHT_GMessage()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b></div>
</div>
<div id="lyMemorySet" style="position:absolute; width:200px; height:434px; z-index:2; left: 5px; top:-6px; visibility: hidden;">

    <p><a href="JavaScript:DHT_DiskInfo()">
        <script>OutPrint(JS_cShow103);</script></a></p>
    <div id="lyMemorySetContent" style="position:absolute; width:279px; height:427px; z-index:14; left: -2px; top: 33px;" class="border4">
        <div id="bSetTimerREC" style="position:absolute; z-index:19; left: 136px; top: 191px; width: 90px; height: 24px;"> <b><a href="JavaScript:NetFile_SetTaskScheduleAll()" class="style12">
            <script>OutPrint(JS_cShow104);</script>  </a></b></div>
        <div id="bSetChTitleParent" style="position:absolute; width:39px; height:20px; z-index:20; left: 30px; top: 191px;"> <b><a href="JavaScript:NetFile_SetTaskSchedule()" class="style12">
            <script>OutPrint(JS_cShow20);</script> </a></b></div>
        <div id="LyMemPro" style="position:absolute; width:253px; height:90px; z-index:11; top: 210px; left: 2px;" class="border5">
            <div id="bSetChTitleParent" style="position:absolute; width:222px; height:24px; z-index:7; top: 3px;">
                <div id="lyMaxFileLen2" style="position: absolute; width: 244px; height: 22px; z-index: 7; top: 3px;" class="Rec">
                    <script>OutPrint(JS_cShow105);</script></div>
                <div id="lyMaxFileLen" style="position: absolute; z-index: 7; left: 210px; height: 24px; width: 36px;" class="Rec">
                    <input name="txMaxFileLen" type="text" id="txMaxFileLen2" value="60" style="width:30px " onChange="value=value.replace(/[^\d]/g,'')" onKeyDown="value=value.replace(/[^\d]/g,'')">
                </div>
                <b> </b></div>
            <div id="bSetChTitleParent" style="position:absolute; width:234px; height:28px; z-index:7; top: 30px;">
                <div id="lyFreeDisk2" style="position: absolute; width: 200px; height: 22px; z-index: 7; top: 5px;" class="Rec">  <script>OutPrint(JS_cShow106);</script> </div>
                <b> </b>
                <div id="lyFreeDisk" style="position: absolute; z-index: 7; top: 3px; left: 210px; width: 35px; height: 22px;" class="Rec">
                    <input name="txFreeDisk" type="text" id="txFreeDisk2" value="500" style="width:30px "onChange="value=value.replace(/[^\d]/g,'')"  onKeyDown="value=value.replace(/[^\d]/g,'')">
                </div>
                <b> </b></div>
            <div id="bSetChTitleParent" style="position:absolute; width:246px; height:22px; z-index:7; top: 60px; left: 0px;">
                <script>OutPrint(JS_cShow107);</script><b>
                <select name="selRecPolicy" size="1" id="select25" style="position: absolute; float: right; left: 80px; top: -1px; width: 126px; height: 23px; z-index: 7;">
                    <option value="0" selected> <script>OutPrint(JS_cShow108);</script> </option>
                    <option value="1"> <script>OutPrint(JS_cShow109);</script> </option>
                    <option value="2"> <script>OutPrint(JS_cShow110);</script> </option>
                </select>
            </b></div>
            <div id="bSetChTitleParent" style="position:absolute; z-index:7; left: 208px; top: 63px;"> <b><a href="JavaScript:NetFile_NetFileSetRecordRule()" class="style12">
                <script>OutPrint(JS_cShow20);</script></a></b></div>
        </div>

        <div id="lyTimeSegment1" style="position:absolute; z-index:26; top: 50px; left: 2px; width: 240px;" class="border5">
            <input name="chkTimeSect1" type="checkbox" id="chkTimeSect12"value="ON" onClick="NetFile_SetTimeEnabled(1)">
            <select name=select_Hour_start_1>
            </select>
            :
            <select name=select_Minute_start_1>
            </select>
            ~
            <select name=select_Hour_End_1>
            </select>
            :
            <select name=select_Minute_End_1>
            </select>
        </div>
        <div id="lyTimeSegment2" style="position:absolute; width:248px; z-index:25; top: 80px; left: 2px;" class="border5">
            <input name="chkTimeSect2" type="checkbox" id="chkTimeSect22"  value="ON" onClick="NetFile_SetTimeEnabled(2)">
            <select name=select_Hour_start_2 id="select21">
            </select>
            :
            <select name=select_Minute_start_2 id="select22">
            </select>
            ~
            <select name=select_Hour_End_2 id="select23"></select>
            :
            <select name=select_Minute_End_2 id="select24"></select>
        </div>
        <div id="lyTimeSegment3" style="position:absolute; width:248px; z-index:24; top: 110px; left: 2px;" class="border5">
            <input name="chkTimeSect3" type="checkbox" id="chkTimeSect32"  value="ON" onClick="NetFile_SetTimeEnabled(3)">
            <select name=select_Hour_start_3 id="select17">
            </select>
            :
            <select name=select_Minute_start_3 id="select18">
            </select>
            ~
            <select name=select_Hour_End_3 id="select2">
            </select>
            :
            <select name=select_Minute_End_3 id="select4">
            </select>
        </div>
        <div id="lyTimeSegment4" style="position:absolute; width:248px; z-index:23; top: 140px; left: 2px;" class="border5">
            <input name="chkTimeSect4" type="checkbox" id="chkTimeSect42"  value="ON" onClick="NetFile_SetTimeEnabled(4)">
            <select name=select_Hour_start_4 id="select13">
            </select>
            :
            <select name=select_Minute_start_4 id="select14">
            </select>
            ~
            <select name=select_Hour_End_4 id="select15">
            </select>
            :
            <select name=select_Minute_End_4 id="select16">
            </select>
        </div>
        <div style="position: absolute; width: 80px; height: 20px; z-index: 18; left: 80px; top: -5px;" id="lyTimerRECenabled" class="border5">
            <input type="checkbox" name="chkTimerREC" value="ON" onClick="NetFile_TimerREC()" tabindex=2>
            <script>OutPrint(JS_cShow111);</script> </div>
        <div style="position: absolute; width: 80px; height: 20px; z-index: 17; top: -5px; left: 0px;" id="lyAlertRecEnabled" class="border5">
            <input type="checkbox" name="chkAlertRec" value="ON" onClick="NetFile_AlertREC()"   tabindex=1>
            <script>OutPrint(JS_cShow112);</script> </div>
        <div id="LyRecState" style="position:absolute; width:224px; height:44px; z-index:15; top: 165px; left: 4px;" class="border5">
            <select name="selRecState" size="1" id="selRecState" disabled="true" style="position: absolute; float: right; left: 92px; width: 100px; height: 23px; z-index: 8; top: 1px;" contenteditable="false">
                <option value="0">
                    <script>OutPrint(JS_cShow113);</script>
                </option>
                <option value="1">
                    <script>OutPrint(JS_cShow114);</script>
                </option>
                <option value="2">
                    <script>OutPrint(JS_cShow111);</script>
                </option>
                <option value="3">
                    <script>OutPrint(JS_cShow112);</script>
                </option>
            </select>
            <div id="lyCTTitle" style="position: absolute; width: 87px; height: 22; z-index: 7; top: 3px;" class="Rec"> <script>OutPrint(JS_cShow115);</script></div>
        </div>
        <div id="Layer6" style="position:absolute; width:220px; height:13px; z-index:6;left: 15px; top: 40px;">
            <hr>
        </div>
        <div id="Layer7" style="position:absolute; width:220px; height:13px; z-index:6;left: 15px; top: 290px;">
            <hr>
        </div>
        <div id="Layer8" style="position:absolute; width:220px; height:13px; z-index:6;left: 15px; top: -10px;">
            <hr>
        </div>
        <div id="Layer23" style="position:absolute; width:183px; height:27px; z-index:13; top: 20px; left: 4px;" class="border5">
            <p>
                <select name="selRecDate" size="1" id="selRecDate" style="position: absolute; float: right; left: 98px; top: 0px; width: 80px; height: 23; z-index: 7;" onChange="NetFile_GetTaskSchedule()">
                    <option value="0"><script>OutPrint(JS_cShow116);</script></option>
                    <option value="1"><script>OutPrint(JS_cShow117);</script></option>
                    <option value="2"><script>OutPrint(JS_cShow118);</script></option>
                    <option value="3"><script>OutPrint(JS_cShow119);</script></option>
                    <option value="4"><script>OutPrint(JS_cShow120);</script></option>
                    <option value="5"><script>OutPrint(JS_cShow121);</script></option>
                    <option value="6"><script>OutPrint(JS_cShow122);</script></option>
                </select>
            </p>
            <div id="lyCTTitle" style="position: absolute; width: 80px; height: 22; z-index: 7; top: 0;" class="Rec"> <script>OutPrint(JS_cShow123);</script></div>
        </div>
        <div id="lyManualRecOnRmt" style="position:absolute; width:100px; height:20px; z-index:16; left: 160px; top: -5px;">
            <input type="checkbox" name="chkManualRecOnRmt" value="ON" onClick="NetFile_ManualRecOnRmt()" tabindex=2><script>OutPrint(JS_cShow114);</script>
        </div><div id="LyAlarmMem" style="position:absolute; width:220px; height:80px; z-index:12; top: 300px; left: 2px;" class="border5">
        <div id="bSetChTitleParent" style="position:absolute; width:220px; height:24px; z-index:7; top: 23px;"><script>OutPrint(JS_cShow124);</script> <b> </b>
            <div id="lyPreRecordTime" style="position: absolute; height: 23px; z-index: 7; left: 195px; top: -2px; width: 33px;" class="Rec">
                <input name="txPreRecordTime" style="width:30 " type="text" id="txPreRecordTime2" onChange="value=value.replace(/[^\d]/g,'')"  onKeyDown="value=value.replace(/[^\d]/g,'')" value="10" size="2" maxlength="2">
            </div>
            <b> </b></div>
        <div id="bSetChTitleParent" style="position:absolute; width:220px; height:24px; z-index:7; top: 50px;"> <script>OutPrint(JS_cShow125);</script> <b> </b>
            <div id="lyDelay" style="position: absolute; z-index: 7; left: 195px; top: -2px; width: 34px; height: 25px;" class="Rec">
                <input name="txDelay" type="text" style="width:30 " id="txDelay2"  onChange="value=value.replace(/[^\d]/g,'')"  onKeyDown="value=value.replace(/[^\d]/g,'')" value="20" size="2" maxlength="2">
            </div>
            <b> </b></div>
        <div id="lyCTPTitle" style="position: absolute; z-index: 39; left:20px; top: 80px;width: 105px;" class="Rec"  tabindex=11>
            <b><a href="JavaScript:Advanced_DeviceSnap()" class="style12">
                <script>OutPrint(JS_cShow126);</script>
            </a></b></div>
        <div id="lyCapLink" style="position: absolute; z-index: 39; left:110px; top: 80px;width: 90px;" class="Rec"  tabindex=11>
            <b><a href="JavaScript:Advanced_CapLinkSet()" class="style12">
                <script>OutPrint(JS_cShow639);</script>
            </a></b></div>
        <div id="bSetChTitleParent" style="position:absolute; z-index:7; left: 205; top: 80px; width: 44px;"> <b><a href="JavaScript:NetFile_NetFileSetAlarmRule()" class="style12"> <script>OutPrint(JS_cShow20);</script> </a></b></div>
        <div style="position: absolute; width: 150px; z-index: 13;" id="lyAlertRecEnabled">
            <input type="checkbox" name="chkAlarmRule" value="ON" >
            <script>OutPrint(JS_cShow127);</script> </div>
    </div>
    </div>
    <p>&nbsp;</p>
</div>
<div id="lyCapFTPSet" style="position:absolute; width:256px; height:463px; z-index:56; left: 5px; top: 0px; visibility: hidden;">
    <div id="lyCapTimeSet" style="position: absolute; width:256;z-index: 10; left: 2px; top: 0px;">
        <p><script>OutPrint(JS_cShow646);</script> </p>
        <div id="lySnapEnable" style="position:absolute; width:232px; height:25px; z-index:56; top: 20px; left: 0px;" >
            <div id="lySnapEnable2" style="position: absolute; width: 21px; height: 20px; z-index: 0; top: 0px; left: 10px;"  tabindex=6>
                <input name="chkSnapEnable" type="checkbox" id="chkSnapEnable"  value="ON">
            </div>
            <div id="lySnapEnable1" style="position:absolute; z-index:2; left: 38px; height: 21px; top: 3px;">
                <script>OutPrint(JS_cShow643);</script>
            </div>
        </div>
        <!--
        <div id="lySnapQuality" style="position:absolute; width:200px; height:20px; z-index:56; top: 50px; left: 0;">
            <div id="lySnapQuality1" style="position:absolute; width:80px; left:10px; top:4px; ">
                <script>OutPrint(JS_cShow644);</script>
            </div>
            <div id="lySnapQuality2" style="position:absolute; width:149px; left:91px; top:0px; ">
                <input name="txSnapQuality" type="text" id="txSnapQuality" size="10"  width="80" onChange="value=value.replace(/[^\d]/g,'')">
            </div>
        </div>
        -->
        <div id="lySnapInterval" style="position:absolute; width:200px; height:20px; z-index:1; top: 50px; left: 0;">
            <div id="lySnapInterval1" style="position:absolute; width:80px; left:10px; top:4px; ">
                <script>OutPrint(JS_cShow645);</script>
            </div>
            <div id="lySnapInterval2" style="position:absolute; width:149px; left:91px; top:0px; ">
                <input name="txSnapInterval" type="text"  id="txSnapInterval" size="10" width="80" onChange="value=value.replace(/[^\d]/g,'')">
            </div>
        </div>
        <div id="lyCapUpdateSet" style="position:absolute; z-index:55; top: 55px; left: 190px; width: 38px;">
            <b><a href="JavaScript:NVR_SetFTPConfig(0)"><script>OutPrint(JS_cShow20);</script></a></b>
        </div>
    </div>
    <div id="Layer6" style="position:absolute; width:230px; height:8px; z-index:6; top: 90px; left: 0px;">
        <hr>
    </div>
    <div id="lyCapLinkSet" style="position:absolute; width:221px; z-index:14; left: 1;top:110px;">
        <p><script>OutPrint(JS_cShow647);</script> </p>
        <div id="lySnapLinkEnable" style="position:absolute; width:221px; height:36; z-index:14; left: -1;top:10;" >
            <div style="position: absolute; width: 21px; height: 20px; z-index: 14; top: 10px; left: 10;" id="lyenabled" tabindex=6>
                <input name="chkSnapLinkEnable" type="checkbox" id="chkSnapLinkEnable" value="ON">
            </div>
            <div id="Layer31" style="position:absolute; z-index:12; left: 38px; height: 21px; top: 13px;">
                <script>OutPrint(JS_cShow640);</script>
            </div>
        </div>
        <div id="lySnapLinkEmail" style="position:absolute; width:221px; height:21px; z-index:14; left: 25; top: 40;" >
            <div style="position: absolute; width: 21px; height: 20px; z-index: 14; top: 10px; left: 10;" id="lyenabled" tabindex=6>
                <input name="chkSnapLinkEmail" type="checkbox" id="chkSnapLinkEmail"  value="ON">
            </div>
            <div id="Layer31" style="position:absolute; z-index:12; left: 38px; height: 21px; top: 12px;">
                <script>OutPrint(JS_cShow641);</script>
            </div>
        </div>
        <div id="lySnapLinkFTP" style="position:absolute; width:221px; height:36; z-index:14; left: 25; top: 70;" >
            <div style="position: absolute; width: 21px; height: 20px; z-index: 14; top: 10px; left: 10px;" id="lyenabled" tabindex=6>
                <input name="chkSnapLinkFTP" type="checkbox" id="chkSnapLinkFTP"  value="ON">
            </div>
            <div id="Layer31" style="position:absolute; z-index:12; left: 38px; height: 21px; top: 12px;">
                <script>OutPrint(JS_cShow642);</script>
            </div>
        </div>
        <div id="lyLinkSet" style="position:absolute; z-index:55; top: 110px; left: 190px; width: 38px;"> <b><a href="JavaScript:NVR_SetFTPConfig(1)">
            <script>OutPrint(JS_cShow20);</script>
        </a></b> </div>
    </div>
    <div id="Layer6" style="position:absolute; width:230px; height:8px; z-index:6; top: 240px; left: 0px;">
        <hr>
    </div>
    <div id="lyback" style="position: absolute; z-index: 5; left: 206px; top: 419px;">
        <b><a href="javascript:DHT_MemorySet()"><script>OutPrint(JS_cShow155);</script></a></b>
    </div>
</div>
<div id="lyPlayBack1" style="position:absolute; width:269px; height:482px; z-index:2; left: 1px; top: -11px; visibility: hidden;">
    <div id="lyUsbInfo" style="position:absolute; width:200px; height:25px; z-index:1; left: 0px; top:-7px;">
        <div  id="lyselNVS" style="position:absolute; width:200px; height:27px; z-index:1; left: 0px; top:-7px; visibility: hidden;">
            <select name="selNVS" id="selNVS" style="width: 200" ></select>
        </div>
        <div id="lyUsbInfo2" style="position:absolute; width:269px; height:487px; z-index:1; top: 0px; left: -3px;">
            <table width="259px" align="center">
                <tr><td><script>OutPrint(JS_cShow128);</script></td></tr>
                <tr><td><input type="text" name="txpath" id="txpath" size="30"><input type="button" id="btnbrowse" name="btnbrowse" size="3" value="设置" onClick="FilePath()"></td></tr>
                <tr><td height="18px"><script>OutPrint(JS_cShow129);</script></td></tr>
                <tr><td height="20px"><select name="selNVSIP" id="selNVSIP" style="width: 200px" onChange="DHT_SelNVS()"></select>
                </td></tr>
                <tr><td height="20px"><script>OutPrint(JS_cShow130);</script>
                    <input name="chkbRemote" type="checkbox" id="chkbRemote2" value="checkbox" onClick="PlayBack_ISRemote()">
                    <script>OutPrint(JS_cShow131);</script>
                    <select name="selFileType" id="select19"  style="width: 65px">
                        <option value="1"><script>OutPrint(JS_cShow132);</script></option>
                        <option value="2"><script>OutPrint(JS_cShow133);</script></option>
                    </select></td></tr>
                <tr>
                    <td height="18px"><script>OutPrint(JS_cShow134);</script></td>
                </tr>
                <tr><td height="18px">
                    <select name="selBeginTimeYYYY"   style="width: 52px "></select>
                    <select name="selBeginTimeSM"   style="width: 40px "></select>-
                    <select name="selBeginTimeDD"   style="width: 40px "></select>

                    <select name="selBeginTimeHH"   style="width: 40px "></select>:
                    <select name="selBeginTimeMM"   style="width: 40px "></select>
                </td>
                </tr>
                <tr>
                    <td height="18px"><script>OutPrint(JS_cShow135);</script></td>
                </tr>
                <tr><td height="18">
                    <select name="selEndTimeYYYY"   style="width: 52px"></select>-
                    <select name="selEndTimeSM"   style="width: 40px"></select>-
                    <select name="selEndTimeDD"   style="width: 40px"></select>
                    <select name="selEndTimeHH"   style="width: 40px"></select>:
                    <select name="selEndTimeMM"   style="width: 40px"></select>
                </td></tr>
                <tr><td height="25"><script>OutPrint(JS_cShow136);</script>
                    <select name="selRecType" id="select20" style="width: 35px">
                        <option value="255" selected><script>OutPrint(JS_cShow137);</script></option>
                        <option value="1"><script>OutPrint(JS_cShow138);</script></option>
                        <option value="2"><script>OutPrint(JS_cShow139);</script></option>
                        <option value="3"><script>OutPrint(JS_cShow140);</script></option>
                    </select>
                    <script>OutPrint(JS_cShow141);</script>
                    <select name="selChann" style="width: 35px" id="selChann">
                        <option value="0" selected>1</option>
                        <option value="1">2</option>
                        <option value="2">3</option>
                        <option value="3">4</option>
                        <option value="1">5</option>
                        <option value="2">6</option>
                        <option value="3">7</option>
                        <option value="1">8</option>
                        <option value="2">9</option>
                        <option value="3">10</option>
                        <option value="1">11</option>
                        <option value="2">12</option>
                        <option value="3">13</option>
                        <option value="1">14</option>
                        <option value="2">15</option>
                        <option value="3">16</option>
                    </select>
                    <b><a href="JavaScript:QF_Query()"><script>OutPrint(JS_cShow142);</script></a></b>
                </td></tr>
                <tr><td height="60">
                    <select name="selQFResult" size="10" id="select26" style="width: 238px;height: 100px" ondblclick="PlayBack_StartEx()">
                    </select></td>
                </tr>
                <tr><td height="18px">
                    <a href="javascript:PlayBack_Start()"><script>OutPrint(JS_cShow143);</script></a>
                    <a href="javascript:PlayBack_Pause()"><script>OutPrint(JS_cShow144);</script></a>
                    <a href="javascript:PlayBack_Stop()"><script>OutPrint(JS_cShow145);</script></a>
                    <a href="javascript:PlayBack_Fast()"><script>OutPrint(JS_cShow146);</script></a>
                    <a href="javascript:PlayBack_Slow()"><script>OutPrint(JS_cShow147);</script></a>
                </td></tr>
                <tr><td height="18px">
                    <a href="javascript:PlayBack_Snap()">[<script>OutPrint(JS_cShow225);</script>]</a>
                    <a href="javascript:PB_Down()"><script>OutPrint(JS_cShow148);</script></a>
                    <a href="javascript:PB_StopDown()"><script>OutPrint(JS_cShow149);</script></a>
                    <div id="divDownProgress" style="position: absolute; width: 34px; height: 20px; z-index: 5; left: 125px; top: 360px;"></div>
                </td></tr>
                <tr><td height="18px">
                    <a href="JavaScript:QF_FirstPage()"><script>OutPrint(JS_cShow150);</script></a>
                    <a href="JavaScript:QF_PrePage()"><script>OutPrint(JS_cShow151);</script></a>
                    <a href="JavaScript:QF_NextPage()"><script>OutPrint(JS_cShow152);</script></a>
                    <a href="JavaScript:QF_LastPage()"><script>OutPrint(JS_cShow153);</script></a>
                    <div id="divPageNum" style="position: absolute; height: 20px; z-index: 5; left: 172px; top: 398px; width: 35px;"></div>
                </td></tr>
                <tr><td height="20px"><input name="txDecryptPlayBack" type="text" id="txDecryptPlayBack" size="15" maxlength="15"><a href="JavaScript:InputDecryptPlayBack()"><script>OutPrint(JS_cShow668);</script></a><script>OutPrint(JS_cShow154);</script></td></tr>

                <tr><td height="20px"><input name="localfile" type="file" id="localfile" style="Z-INDEX: 9; left:14px; top:420px; position:absolute; width:180px; height:22px" size="50" maxlength="250" contenteditable="false" runat="server" onDblClick="FilePath_Open_Clear()" ></td></tr>
                <tr><td height="20px"></td></tr>
            </table>
            <div id="lyFTPUpLoad" style="position: absolute; z-index: 5; left: 10px; top: 443px;">
                <b><a href="javascript:FTP_Upload()"><script>OutPrint(JS_cShow638);</script></a></b>
            </div>
            <div id="lyback" style="position: absolute; z-index: 5; left: 216px; top: 443px;">
                <b><a href="javascript:Menu_Back()"><script>OutPrint(JS_cShow155);</script></a></b>
            </div>
            <div id="lybackOK" style="position: absolute; width: 37px; height: 13px; z-index: 5; left: 197px; top: 421px;">
                <input type="button" value="确认" name="btnPlayLocal" id="btnPlayLocal" size="4" onClick="FilePath_Open()"></div>
        </div>
    </div>
</div>
<div id="lyInOutPort" style="position: absolute; width: 262px; height: 453px; z-index: 5; left: 9px; top: -10px; overflow: hidden; visibility: hidden;">
    <div id="LyAlarmLinkSchedule" style="position:absolute; width:244px; height:167px; z-index:55; left: 0px; top: 150px;">
        <div id="labAlmInPort" style="position: absolute; width: 53px; height: 20px; z-index: 8; top: 0px; left: 0px;" class="Rec">
            <div id="labAlmInPort1" style="position: absolute; width: 96px; height: 20px; z-index: 8; top: -20px; left: 0px;" class="Rec">
                <script>OutPrint(JS_cShow272);</script></div>
        </div><div id="lyTimeSchedule3" style="position:absolute; z-index:26; top: 97px; left: 0px; width: 240px;" class="border5">
        <p>
            <input name="chkOutAlmTime3" type="checkbox" id="chkOutAlmTime32"value="ON" onClick="DHT_chkOutAlmTime3()"><select name=selectAOut_Hour_start_3>
        </select>
            :
            <select name=selectAOut_Minute_start_3>
            </select>
            ~
            <select name=selectAOut_Hour_End_3>
            </select>
            :
            <select name=selectAOut_Minute_End_3>
            </select>
        </p>
    </div>
        <div id="lyTimeSchedule2" style="position:absolute; z-index:26; top: 70px; left: 0px; width: 240px;" class="border5">
            <input name="chkOutAlmTime2" type="checkbox" id="chkOutAlmTime2"value="ON" onClick="DHT_chkOutAlmTime2()"><select name=selectAOut_Hour_start_2>
        </select>
            :
            <select name=selectAOut_Minute_start_2>
            </select>
            ~
            <select name=selectAOut_Hour_End_2>
            </select>
            :
            <select name=selectAOut_Minute_End_2>
            </select>
        </div>
        <div id="lyTimeSchedule1" style="position:absolute; z-index:26; top: 45px; left: 0px; width: 240px;" class="border5">
            <input name="chkOutAlmTime1" type="checkbox" id="chkOutAlmTime1"value="ON" onClick="DHT_chkOutAlmTime1()"><select name=selectAOut_Hour_start_1>
        </select>
            :
            <select name=selectAOut_Minute_start_1>
            </select>
            ~
            <select name=selectAOut_Hour_End_1>
            </select>
            :
            <select name=selectAOut_Minute_End_1>
            </select>
        </div>
        <div id="lyTimeSchedule4" style="position:absolute; z-index:26; top: 124px; left: 0px; width: 240px;" class="border5">
            <input name="chkOutAlmTime4" type="checkbox" id="chkOutAlmTime4"value="ON" onClick="DHT_chkOutAlmTime4()"><select name=selectAOut_Hour_start_4>
        </select>
            :
            <select name=selectAOut_Minute_start_4>
            </select>
            ~
            <select name=selectAOut_Hour_End_4>
            </select>
            :
            <select name=selectAOut_Minute_End_4>
            </select>
        </div>
        <div id="labAlarmIn" style="position: absolute; width: 34px; height: 15px; z-index: 6; left: 204px; top: 151px" class="Rec"><b><a href="JavaScript:NetFile_SetAlarmOutportSchedule()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b></div>
        <div id="LyDate" style="position:absolute; width:240px; height:20px; z-index:13; top: -15px; left: -2px;" class="border5">

            <select name="selOutWeekDay" size="1" id="selOutWeekDay" style="position: absolute; float: right; left: 90px; top: 19px; width: 80px; height: 20px; z-index: 7;" onChange="NetFile_GetAlarmOutportSchedule()">
                <option value="0">
                    <script>OutPrint(JS_cShow116);</script>
                </option>
                <option value="1">
                    <script>OutPrint(JS_cShow117);</script>
                </option>
                <option value="2">
                    <script>OutPrint(JS_cShow118);</script>
                </option>
                <option value="3">
                    <script>OutPrint(JS_cShow119);</script>
                </option>
                <option value="4">
                    <script>OutPrint(JS_cShow120);</script>
                </option>
                <option value="5">
                    <script>OutPrint(JS_cShow121);</script>
                </option>
                <option value="6">
                    <script>OutPrint(JS_cShow122);</script>
                </option>
            </select>
            <select name="selOutPortNO" id="selOutPortNO" style="position: absolute; width: 55px; left: 76px; top: -4px; height: 23; z-index: 6; visibility: hidden;" size="1" onChange="NetFile_GetAlarmOutportSchedule()">
                <option value="0">1</option>
                <option value="1">2</option>
                <option value="2">3</option>
                <option value="3">4</option>
                <option value="4">5</option>
                <option value="5">6</option>
                <option value="6">7</option>
                <option value="7">8</option>
                <option value="8">9</option>
                <option value="9">10</option>
                <option value="10">11</option>
                <option value="11">12</option>
                <option value="12">13</option>
                <option value="13">14</option>
                <option value="14">15</option>
                <option value="15">16</option>
            </select>
            <input name="chkOutAlarmEnable" type="checkbox" id="chkOutAlarmEnable" value="8" style="position: absolute; left: 176px; top: 20px; z-index: 8; visibility: hidden;" onClick="NetFile_chkOutAlarmEnable()">

            <div id="labAlarmIn" style="position: absolute; width: 33px; height: 16px; z-index: 6; left: 200px; top: 24px; visibility: hidden;" class="Rec">
                <script>OutPrint(JS_cShow271);</script>
            </div>
            <div id="lyCTTitle" style="position: absolute; width: 117px; height: 22; z-index: 7; top: 24px; left: 1px;" class="Rec">
                <script>OutPrint(JS_cShow123);</script>
            </div>
        </div>
    </div>
    </a></b>
    <div id="lyStopAlarmMode" style="position:absolute; width:258px; height:89px; z-index: 54; top:316px; left: 0px;">
        <select name="selOutPort" id="selOutPort" style="position: absolute; width: 40; left: 71px; top: 11px; height: 23; z-index: 46; visibility: hidden;" size="1" onChange="Alarm_GetStopMode()">
        </select>
        <div id="labAlarmOut" style="position: absolute; width: 65px; height: 23; z-index: 6; left: 1px; top: 11px; visibility: hidden;" class="Rec">
            <script>OutPrint(JS_cShow64);</script></div>
        <select name="selStopAlmMode" id="selStopAlmMode" style="position: absolute; width: 80px; left: 90px; top: 36px; height: 23; z-index: 47" size="1">
            <option value="1"><script>OutPrint(JS_cShow266);</script></option>
            <option value="2"><script>OutPrint(JS_cShow267);</script></option>
        </select>
        <div id="labStopAlarm" style="position: absolute; width: 62px; height: 23; z-index: 48; left: 1px; top: 40px" class="Rec"><script>OutPrint(JS_cShow268);</script></div>
        <div id="labDelayAlarm" style="position: absolute; width: 63px; height: 23; z-index: 49; left: 1px; top: 70px" class="Rec"><script>OutPrint(JS_cShow269);</script></div>
        <div id="lyAlarmModeSet" style="position:absolute; width:47px; height:23px; z-index:51; left: 204px; top: 70px"> <b><a href="JavaScript:Alarm_SetStopMode()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b></div>
        <select name="selAlmDelayTime" id="selAlmDelayTime" style="position: absolute; width: 80px; left: 90px; top: 64px; height: 23; z-index: 47" size="1">
            <option value="0">0</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="5" selected>5</option>
            <option value="10">10</option>
            <option value="30">30</option>
        </select>
    </div>
    <div id="lyback" style="position: absolute; z-index: 12; left: 204px; top: 430px; width: 59px;"> <b><a href="javascript:DHT_AlarmInOutCtrl()">
        <script>OutPrint(JS_cShow155);</script>
    </a></b></div>
    <div id="lyAlarmStat" style="position: absolute; width: 230px; height: 57; z-index: 6; left: 0px; top: 129px; visibility: hidden;">
        <div id="lyAlarmInport" style="position:absolute; width:30px; ">
            <select name="selInPort" id="selInPort" style="position: absolute; width: 40px; left: 98px; top: 6px; height: 23px; z-index: 12" size="1" onChange="Alarm_GetLinkEnabled()">
            </select></div>
        <div id="bAlarmLinkSetParent" style="position:absolute; width:47px; height:23px; z-index:11; left: 198px; top: 37px"> <b><a href="JavaScript:Alarm_SetLink()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b></div>
        <div id="labAlarmStat" style="position: absolute; width: 98px; height: 18px; z-index: 10; left: 1px; top: -14px; color: #800000">
            <script>OutPrint(JS_cShow67);</script>
        </div>
        <div style="position: absolute; width: 125px; height: 27px; z-index: 9; left: 4px; top: 60px" id="lyInPortEnable">
            <table border="0" width="100%" id="table14" cellspacing="0" cellpadding="0" height="25">
                <tr>
                    <td><script>OutPrint(JS_cShow62);</script></td>
                    <td width="35">
                        <input type="checkbox" name="chkbAlarmInPortEnabled" value="ON" onClick="AlarmInPort_SetEnabled()"></td>
                </tr>
            </table>
        </div>
        <p>
            &nbsp;&nbsp;&nbsp;            </p>
        <div id="labAlmInPort" style="position: absolute; width: 76px; height: 20px; z-index: 8; top: 12px; left: 4px;" class="Rec">
            <script>OutPrint(JS_cShow63);</script></font></div>
        <p>
            <input name="chkbOutLink1" type="checkbox" id="chkbOutLink1" value="1" style="position: absolute; left: 94; top: 37; z-index: 8">
            <input name="chkbOutLink2" type="checkbox" id="chkbOutLink2" value="2" style="position: absolute; left: 116; top: 37; z-index: 8">
            <input name="chkbOutLink3" type="checkbox" id="chkbOutLink3" value="4" style="position: absolute; left: 138; top: 37; z-index: 8">
            <input name="chkbOutLink4" type="checkbox" id="chkbOutLink4" value="8" style="position: absolute; left: 162px; top: 36px; z-index: 8">
        </p>
        <div id="labAlmOutPort" style="position: absolute; width: 77px; height: 19px; z-index: 8; top: 38px; left: 4px" class="Rec"><script>OutPrint(JS_cShow64);</script></font></div>
    </div>
    <div id="Layer13" style="position:absolute; width:235px; height:15px; z-index:6; left: 2px; top: 319px;">
        <hr>
    </div>
    <div id="Layer5" style="position:absolute; width:235px; height:15px; z-index:47; left: 0px; top: 111px;">
        <hr>
    </div>
    <div id="lyAlarm" style="position: absolute; width: 237px; height: 180px; z-index: 5; left: 0px; top: 150px; overflow: hidden;">
        <div id="lyAlarmSel" style="position: absolute; width: 231px; height: 43px; z-index: 6; left: 0px; top: 82px; visibility: hidden;">
            <input name="chkbAlarmMotionDetect" type="checkbox" id="chkbAlarmMotionDetect" value="checkbox" style="position: absolute; float: right; left: -2; top: -23; width: 17px; z-index: 7; visibility: hidden;" onClick="Alarm_SetAlarmMotionDetect()">
            <div id="lyAlarmMotionDetect" style="position: absolute; width: 220px; height: 16px; z-index: 7; left: 18px; top: -21px; visibility: hidden;" class="Rec">
                <script>OutPrint(JS_cShow73);</script></font></div>
            <p>
                <input name="chkbAlarmVideoLost" type="checkbox" id="chkbAlarmVideoLost" value="checkbox" style="position: absolute; left: 124; top: -24; z-index: 7; visibility: hidden;" onClick="Alarm_SetAlarmVideoLost()">
            </p>
            <div id="lyAlarmVideoCover" style="position: absolute; width: 145px; height: 20; z-index: 7; top: 58px; left: 51px; visibility:hidden" class="Rec">
                <script>OutPrint(JS_cShow74);</script></font></div>
            <div id="lyAlarmVideoLost" style="position: absolute; width: 89px; height: 17px; z-index: 7; left: 145px; top: -21px; visibility: hidden;" class="Rec">
                <script>OutPrint(JS_cShow75);</script></font></div>
            <p>
                <input name="chkbAlarmVideoCover" type="checkbox" id="chkbAlarmVideoCover" value="checkbox" style="position: absolute; left: 30px; top: 60px; visibility: hidden;" onClick="Alarm_SetAlarmVideoCover()">
            </p>
        </div>
    </div>
</div>
<div id="lyAlarmLink" style="position:absolute; width:244px; height:427px; z-index:2; left: 10px; top: -8px; visibility: hidden;">
    <div id="lyAlarmOutMode" style="position:absolute; width:108px; height:17px; z-index:54; left: 0px; top: 85px;">
        <div id="bAlarmModeSetParent" style="position:absolute; z-index:54; left: 204px; top: 78px; visibility: hidden;"> <b><a href="JavaScript:Alarm_SetActiveOutPortMode()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b></div>
        <select name="selAlarmOutMode" id="selAlarmOutMode" style="position: absolute; width: 80px; left: 80px; top: 47px; height: 23; z-index: 53" size="1" onChange="Alarm_SetActiveOutPortMode()">
            <option value="0">
                <script>OutPrint(JS_cShow76);</script>
            </option>
            <option value="1">
                <script>OutPrint(JS_cShow77);</script>
            </option>
        </select>
        <select name="selAlarmOutPort" id="selAlarmOutPort" style="position: absolute; width: 80px; left: 80px; top: 20px; height: 23px; z-index: 52" size="1"  onChange="Alarm_AlarmOutPortState()">
        </select>
        <div id="labAlarmOutMode" style="position: absolute; width: 90px; height: 23; z-index: 51; left: 0px; top: 50px" class="Rec">
            <script>OutPrint(JS_cShow68);</script>
        </div>
        <div id="labAlarmOut" style="position: absolute; width: 90px; height: 23; z-index: 50; left: 0px; top: 22px" class="Rec">
            <script>OutPrint(JS_cShow66);</script>
        </div>
    </div>
    <div id="lyAlarmAdvanceSet" style="position:absolute; width:69px; height:23px; z-index:53; left: 0px; top: -10px;"> <b><a href="JavaScript:DHT_setAlarmAdvance()">
        <script>OutPrint(JS_cShow265);</script>
    </a></b></div>

    <div id="lyMotionDetect" style="position:absolute; width:233px; height:44px; z-index:10; left: 0px; top: 70px;">
        <div style="position: absolute; width: 234px; height: 24px; z-index: 9; left: 0px; top: 53px" id="lyMDSet">
            <div style="position: absolute; width: 71px; height: 25px; z-index: 1; left: 178px; top: 0px" id="lyMDClear"> <b><a href="JavaScript:Alarm_MDClear()">
                <script>OutPrint(JS_cShow69);</script>
            </a></b></div>
            <div style="position: absolute; width: 121px; height: 24px; z-index: 1; left: 3px; top: -1px" id="lyMDOpen">
                <table border="0" width="100%" id="table23" cellspacing="0" cellpadding="0" height="24">
                    <tr>
                        <td width="20">
                            <input type="checkbox" name="chkbMDOpen" value="ON" onClick="Alarm_MDOpen()"></td>
                        <td><script>OutPrint(JS_cShow70);</script></td>
                    </tr>
                </table>
            </div>
            <p>
        </div>
        <div id="bMotionDetectSetParent" style="position:absolute; width:47px; height:23px; z-index:8; left: 204px; top: 28px"> <b> <a href="JavaScript:Alarm_SetMotionDetectTh()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b></div>
        <div id="trackbarTitle0" style="position:absolute; width:149px; height:18px; z-index:7; left: 0px; top: 9px;" class="Rec">
            <script>OutPrint(JS_cShow72);</script>
        </div>
        <div id="layerbar0" style="position: absolute; width: 138px; height: 18px; z-index: 7; left: 0px; top: 29px; background-image:   url('movebar.jpg'); layer-background-image:   url(movebar.jpg); border: 1px none #000000">
            <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url(pin.gif); layer-background-image:   url(pin.gif); border: 1px none #000000" id="lyMotionDetectBut" onMouseDown="trackbar_mousedown(lyMotionDetectBut)" onMouseUp="trackbar_mouseup(lyMotionDetectBut)" onMouseMove="trackbar_mousemove(lyMotionDetectBut)">
                <div align="center"></div>
            </div>
            <div align="left">
                <div id="labMotionDetectValue" style="position:absolute; width:23px; height:10px; z-index:8; left: 115px; top: 2px;">50</div>
                <div id="lybar0" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 1px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
            </div>
        </div>
    </div>
    <div id="layerCoverDetect" style="position:absolute; width:233px; height:52px; z-index:6; left: 0px; top: 70px;">

        <div id="lyAlarmVideoCover" style="position: absolute; width: 145px; height: 20; z-index: 9; top: 58px; left: 44px; visibility: hidden;" class="Rec">
            <script>OutPrint(JS_cShow74);</script>
        </div>
        <input name="chkbAlarmVideoCover" type="checkbox" id="chkbAlarmVideoCover" value="checkbox" style="position: absolute; left: 13px; top: 62px; z-index: 8; visibility: hidden;" onClick="Alarm_SetAlarmVideoCover()">
        <div id="trackbarTitle0" style="position:absolute; width:173px; height:23px; z-index:7; left: 0px; top: 9px;" class="Rec">
            <script>OutPrint(JS_cShow415);</script></font></div>
        <div id="layerbar0" style="position: absolute; width: 138px; height: 26px; z-index: 7; left: 0px; top: 29px; background-image:   url('movebar.jpg'); layer-background-image:   url(movebar.jpg); border: 1px none #000000">
            <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url(pin.gif); layer-background-image:   url(pin.gif); border: 1px none #000000" id="lyCoverDetectBut" onMouseDown="trackbar_mousedown(lyCoverDetectBut)" onMouseUp="trackbar_mouseup(lyCoverDetectBut)" onMouseMove="trackbar_mousemove(lyCoverDetectBut)">
                <div align="center"></div>
            </div>
            <div align="left">
                <div id="labCoverDetectValue" style="position:absolute; width:23px; height:10px; z-index:8; left: 115px; top: 2px;">50</div>
                <div id="lybar0" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 1px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
            </div>
        </div>
        <div id="bCoverDetectSetParent" style="position:absolute; width:47px; height:23px; z-index:7; left: 204px; top: 28px"> <b> <a href="JavaScript:Alarm_SetVideoCoverTh()">
            <script>OutPrint(JS_cShow20);</script>
        </a></b></div>
    </div>
    <select name="selAlarmType" id="selAlarmType" style="position: absolute; width: 82px; left: 80px; top: 20px; height: 18px; z-index: 7" size="1" onChange="NetFile_GetAlarmSchedule()">
        <option value="0"><script>OutPrint(JS_cShow256);</script></option>
        <option value="1"><script>OutPrint(JS_cShow257);</script></option>
        <option value="2"><script>OutPrint(JS_cShow255);</script></option>
        <option value="3"><script>OutPrint(JS_cShow417);</script></option>
    </select>
    <div id="labAlarmIn" style="position: absolute; width: 79px; height: 14px; z-index: 6; left: 0px; top: 22px" class="Rec">
        <script>OutPrint(JS_cShow270);</script></div>
    <div id="labAlarmInText" style="position: absolute; width: 78px; height: 17px; z-index: 6; left: 0px; top: 50px" class="Rec">
        <script>OutPrint(JS_cShow63);</script></div>
    <select name="selChannelNo" id="selChannelNo" style="position: absolute; width: 82px; left: 80px; top: 47px; height: 20px; z-index: 6" size="1" onChange="NetFile_GetAlarmSchedule()"><option value="1">1</option></select>
    <input name="chkAlarmEnable" type="checkbox" id="chkAlarmEnable" value="8" style="position: absolute; left: 173px; top: 48px; z-index: 8" onClick="NetFile_SetAlarmSchdEnable()">
    <div id="labAlarmIn" style="position: absolute; width: 46px; height: 16px; z-index: 6; left: 200px; top: 53px" class="Rec">
        <script>OutPrint(JS_cShow271);</script></div>
    <div id="LyAlarmLinkSchedule2" style="position:absolute; width:244px; height:140px; z-index:9; left: 0px; top: 172px;">
        <div id="labAlmInPort" style="position: absolute; width: 80px; height: 20px; z-index: 8; top: 0px; left: 2px;" class="Rec">
            <script>OutPrint(JS_cShow272);</script></div>

        <div id="lyTimeSchedule3" style="position:absolute; z-index:26; top: 74px; left: 0px; width: 240px;" class="border5">
            <input name="chkInAlmTime3" type="checkbox" id="chkInAlmTime3"value="ON" onClick="DHT_chkInAlmTime3()"><select name=selectAlm_Hour_start_3>
        </select>
            :
            <select name=selectAlm_Minute_start_3>
            </select>
            ~
            <select name=selectAlm_Hour_End_3>
            </select>
            :
            <select name=selectAlm_Minute_End_3>
            </select>
        </div>
        <div id="lyTimeSchedule4" style="position:absolute; z-index:26; top: 101px; left: 0px; width: 240px;" class="border5">
            <input name="chkInAlmTime4" type="checkbox" id="chkInAlmTime4"value="ON" onClick="DHT_chkInAlmTime4()"><select name=selectAlm_Hour_start_4>
        </select>
            :
            <select name=selectAlm_Minute_start_4>
            </select>
            ~
            <select name=selectAlm_Hour_End_4>
            </select>
            :
            <select name=selectAlm_Minute_End_4>
            </select>
        </div>
        <div id="lyTimeSchedule2" style="position:absolute; z-index:26; top: 48px; left: 0px; width: 240px;" class="border5">
            <input name="chkInAlmTime2" type="checkbox" id="chkInAlmTime2"value="ON" onClick="DHT_chkInAlmTime2()"><select name=selectAlm_Hour_start_2>
        </select>
            :
            <select name=selectAlm_Minute_start_2>
            </select>
            ~
            <select name=selectAlm_Hour_End_2>
            </select>
            :
            <select name=selectAlm_Minute_End_2>
            </select>
        </div>
        <div id="lyTimeSchedule1" style="position:absolute; z-index:26; top: 22px; left: 0px; width: 240px;" class="border5">
            <input name="chkInAlmTime1" type="checkbox" id="chkInAlmTime1"value="ON" onClick="DHT_chkInAlmTime1()"><select name=selectAlm_Hour_start_1>
        </select>
            :
            <select name=selectAlm_Minute_start_1>
            </select>
            ~
            <select name="selWeekDay" size="1" id="selWeekDay" style="position: absolute; float: right; left: 80px; top: -27px; width: 82px; height: 23; z-index: 7;" onChange="NetFile_GetAlarmSchedule()">
                <option value="0">
                    <script>OutPrint(JS_cShow116);</script>
                </option>
                <option value="1">
                    <script>OutPrint(JS_cShow117);</script>
                </option>
                <option value="2">
                    <script>OutPrint(JS_cShow118);</script>
                </option>
                <option value="3">
                    <script>OutPrint(JS_cShow119);</script>
                </option>
                <option value="4">
                    <script>OutPrint(JS_cShow120);</script>
                </option>
                <option value="5">
                    <script>OutPrint(JS_cShow121);</script>
                </option>
                <option value="6">
                    <script>OutPrint(JS_cShow122);</script>
                </option>
            </select>
            <select name=selectAlm_Hour_End_1>
            </select>
            :
            <select name=selectAlm_Minute_End_1>
            </select>
        </div>
        <div id="labAlarmIn" style="position: absolute; width: 35px; height: 15px; z-index: 6; left: 204px; top: 133px" class="Rec"><b><a href="JavaScript:NetFile_SetAlarmSchedule()">
            <script>OutPrint(JS_cShow20);</script></a></b></div>
        <div id="LyDate" style="position:absolute; width:91px; height:25px; z-index:13; top: -5px; left: 78px;" class="border5">
            <p>&nbsp;            </p>
        </div>
        <div id="lylayer" style="position:absolute; width:240px; height:14px; z-index:6; left: 0px; top: -22px;">
            <hr>
        </div>
    </div>
    <div id="lyLinkPTZCHNPort" style="position:absolute; width:238px; height:68px; z-index:11; top: 334px; left: 0px;">
        <div id="labAlarmIn1" style="position: absolute; width: 66px; height: 17px; z-index: 6; left: 0px; top: 12px" class="Rec">
            <script>OutPrint(JS_cShow277);</script> </div>
        <select name="selAlarmLinkType" id="selAlarmLinkType" style="position: absolute; width: 80px; left: 80px; top: 7px; height: 23; z-index: 6" size="1" onChange="Alarm_GetAlarmLinkType()">
            <option value="1"> <script>OutPrint(JS_cShow278);</script></option>
            <option value="2"><script>OutPrint(JS_cShow279);</script></option>
            <option value="3"><script>OutPrint(JS_cShow280);</script></option>
        </select>
        <div id="labAlarmIn2" style="position: absolute; width: 62px; height: 18px; z-index: 6; left: 132px; top: 0px; visibility: hidden;" class="Rec">
            <script>OutPrint(JS_cShow63);</script></div>
        <select name="selAlarmLinkInPort" id="selAlarmLinkInPort" style="position: absolute; width: 41px; left: 193px; top: -4px; height: 23; z-index: 6; visibility: hidden;" size="1" onChange="Alarm_GetAlarmLinkType()">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
            <option value="11">11</option>
            <option value="12">12</option>
            <option value="13">13</option>
            <option value="14">14</option>
            <option value="15">15</option>
            <option value="16">16</option>
        </select>
        <div id="lyAlarmChannel" style="position:absolute; width:241px; height:55px; z-index:9; top: 39px; visibility: hidden;">
            <input name="chkbOutChannelLink16" type="checkbox" id="chkbOutChannelLink16" value="8" style="position: absolute; left: 170px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink15" type="checkbox" id="chkbOutChannelLink15" value="8" style="position: absolute; left: 150px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink14" type="checkbox" id="chkbOutChannelLink14" value="8" style="position: absolute; left: 130px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink13" type="checkbox" id="chkbOutChannelLink13" value="8" style="position: absolute; left: 110px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink12" type="checkbox" id="chkbOutChannelLink12" value="8" style="position: absolute; left: 90px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink11" type="checkbox" id="chkbOutChannelLink11" value="8" style="position: absolute; left: 70px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink10" type="checkbox" id="chkbOutChannelLink10" value="8" style="position: absolute; left: 50px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink9" type="checkbox" id="chkbOutChannelLink9" value="8" style="position: absolute; left: 30px; top: 25px; z-index: 8">
            <input name="chkbOutChannelLink8" type="checkbox" id="chkbOutChannelLink8" value="8" style="position: absolute; left: 170px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink7" type="checkbox" id="chkbOutChannelLink7" value="8" style="position: absolute; left: 150px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink6" type="checkbox" id="chkbOutChannelLink6" value="8" style="position: absolute; left: 130px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink5" type="checkbox" id="chkbOutChannelLink5" value="8" style="position: absolute; left: 110px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink4" type="checkbox" id="chkbOutChannelLink4" value="8" style="position: absolute; left: 90px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink3" type="checkbox" id="chkbOutChannelLink3" value="8" style="position: absolute; left: 70px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink2" type="checkbox" id="chkbOutChannelLink2" value="8" style="position: absolute; left: 50px; top: 2px; z-index: 8">
            <input name="chkbOutChannelLink1" type="checkbox" id="chkbOutChannelLink1" value="8" style="position: absolute; left: 30px; top: 2px; z-index: 8">
        </div>
        <div id="LyAlarmLinkPTZ" style="position:absolute; width:240px; height:45px; z-index:9; top: 41px; left: 0px;">
            <select name="selLinkPTZ" id="selLinkPTZ" style="position: absolute; width: 46px; left: 0px; top: 18px; height: 18px; z-index: 6" size="1" onChange="Alarm_GetAlarmLinkType()">
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
            </select>
            <select name="selLinkPTZType" id="selLinkPTZType" style="position: absolute; width: 63px; left: 72px; top: 18px; height: 18px; z-index: 6" size="1" onChange="Alarm_GetAlarmLinkPTZ()">
                <option value="1"><script>OutPrint(JS_cShow273);</script></option>
                <option value="2"><script>OutPrint(JS_cShow274);</script></option>
                <option value="3"><script>OutPrint(JS_cShow276);</script></option>
                <option value="4"><script>OutPrint(JS_cShow275);</script></option>
            </select>
            <select name="selLinkPTZNo" id="selLinkPTZNo" style="position: absolute; width: 46px; left: 150px; top: 18px; height: 18px; z-index: 6" size="1" >
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
                <option value="11">11</option>
                <option value="12">12</option>
                <option value="13">13</option>
                <option value="14">14</option>
                <option value="15">15</option>
                <option value="16">16</option>
            </select>
            <div id="labAlarmIn" style="position: absolute; width: 49px; height: 18px; z-index: 6; left: 0px; top: 0px" class="Rec"><script>OutPrint(JS_cShow282);</script> </div>
            <div id="labAlarmIn" style="position: absolute; width: 58px; height: 18px; z-index: 6; left: 72px; top: 0px" class="Rec"> <script>OutPrint(JS_cShow283);</script></div>
            <div id="labAlarmIn" style="position: absolute; width: 51px; height: 15px; z-index: 6; left: 150px; top: 0px" class="Rec"> <script>OutPrint(JS_cShow284);</script></div>
        </div>
        <div id="labAlarmIn3" style="position: absolute; width: 35px; height: 15px; z-index: 6; left: 204px; top: 84px" class="Rec"><b><a href="JavaScript:Alarm_SetAlarmLinkType()">
            <script>OutPrint(JS_cShow20);</script></a></b></div>
    </div>
    <div id="lylayer2" style="position:absolute; width:242px; height:12px; z-index:6; left: 0px; top: 320px;">
        <hr>
    </div>
    <div id="lyAlarmInMode" style="position:absolute; width:234px; height:23px; z-index:13; left: 0px; top: 75px;">
        <select name="selAlarmInPort" id="selAlarmInPort" style="position: absolute; width: 40; left: 94; top: 26; height: 23; z-index: 15; visibility: hidden;" size="1" onChange="Alarm_SetAlarmInPort()">
        </select>
        <select name="selAlarmInMode" id="selAlarmInMode" style="position: absolute; width: 82px; left: 80px; height: 23; z-index: 14; top: 0;" size="1" onChange="Alarm_SetActiveInPortMode()">
            <option value="0">
                <script>OutPrint(JS_cShow76);</script>
            </option>
            <option value="1">
                <script>OutPrint(JS_cShow77);</script>
            </option>
        </select>
        <div id="labAlarmIn" style="position: absolute; width: 89px; height: 23; z-index: 13; top: 5px;" class="Rec">
            <script>OutPrint(JS_cShow68);</script>
        </div>
    </div>
</div>
<div id="lyDeviceCtrl" style="position: absolute; width: 220px; height: 471px; z-index: 5; left: 15px; top: -15px; overflow: hidden; visibility: hidden;">
    <div style="position: absolute; width: 200px; height: 33px; z-index: 10; left: 0px; top: 237px" id="lyPresent">
        <table border="0" width="100%" id="table28" cellspacing="0" cellpadding="0" height="26px">
            <tr>
                <td align="center"><b><a href="JavaScript:Device_SetView('${ctx}/images/camera/')"><script>OutPrint(JS_cShow46);</script></a></b></td>
                <td align="center">
                    <input name="txPresent" type="text" id="txPresent" size="8" maxlength="3" value="1" onChange="value=value.replace(/[^\d]/g,'')"  onKeyDown="value=value.replace(/[^\d]/g,'')"></td>
                <td align="center"><b>
                    <a href="JavaScript:Device_CallView('${ctx}/images/camera/')"><script>OutPrint(JS_cShow47);</script></a></b></td>
            </tr>
        </table>
    </div>
    <div style="position: absolute; width: 200px; height: 131px; z-index: 9; left: 0px; top: 90px" id="lyAction">
        <table border="0" width="100%" id="table25" height="111" cellspacing="0" cellpadding="0">
            <tr><td height="40"><div><div style="position: absolute; z-index: 9; left: -3px; top: 8px;" id="ly3D">
                <input type="checkbox" name="chkb3D" value="ON" onClick="Device_Set3DEnabled()" tabindex=1>
                <script>OutPrint(JS_cShow48);</script></div>
                <div style="position: absolute; z-index: 9; left: -3px; top: 185px;" id="lyEPTZ">
                    <input name="chkEPTZ" type="checkbox" id="chkEPTZ" tabindex=1  value="ON">
                    <script>OutPrint(JS_cShow350);</script>
                </div>
            </td>
            </tr>
            <tr>
                <td align="center">
                    <input type="button" style="width: 60px" class=menubtn value="变倍大" name="bZoomBig" id="bZoomBig" onMouseDown="Device_ZoomBig(true,'${ctx}/images/camera/')" onMouseUp="Device_ZoomBig(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')">
                    <input type="button" style="width: 60px" class=menubtn value="变倍小" name="bZoomSmall" id="bZoomSmall" onMouseDown="Device_ZoomSmall(true,'${ctx}/images/camera/')" onMouseUp="Device_ZoomSmall(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></td>
            </tr>
            <tr>
                <td align="center"><input type="button" style="width: 60px" class=menubtn value="聚焦远" name="bFocusFar" id="bFocusFar" onMouseDown="Device_FocusFar(true,'${ctx}/images/camera/')" onMouseUp="Device_FocusFar(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')">
                    <input type="button" style="width: 60px" class=menubtn value="聚焦近" name="bFocusNear" id="bFocusNear" onMouseDown="Device_FocusNear(true,'${ctx}/images/camera/')" onMouseUp="Device_FocusNear(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></td>
            </tr>
            <tr>
                <td align="center"><input type="button" style="width:60px" class=menubtn value="光圈开" name="bIrisOpen" id="bIrisOpen" onMouseDown="Device_IrisOpen(true,'${ctx}/images/camera/')" onMouseUp="Device_IrisOpen(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')">
                    <input type="button" style="width: 60px" class=menubtn value="光圈关" name="bIrisClose" id="bIrisClose" onMouseDown="Device_IrisClose(true,'${ctx}/images/camera/')" onMouseUp="Device_IrisClose(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></td>
            </tr>
            <tr>
                <td align="center"><input type="button" style="width: 39px" class=menubtn value="灯光" name="bLight" id="bLight" onClick="Device_Light('${ctx}/images/camera/')">
                    <input type="button" style="width: 39px" class=menubtn value="电源" name="bPower" id="bPower" onClick="Device_Power()">
                    <input type="button" style="width: 39px" class=menubtn value="雨刷" name="bRain" id="bRain" onMouseDown="Device_Rain(true)" onMouseUp="Device_Rain(false)" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></td>
            </tr>
        </table>
    </div>
    <div id="lySpeed" style="position:absolute; width:200px; height:26px; z-index:6; left: 1px; top: 390px;">
        <div id="labSpeedTitle" style="position:absolute; width:53px; height:23px; z-index:7; left: 7px; top: 2px" class="Rec">
            <script>OutPrint(JS_cShow58);</script></div>
        <div id="laybarSpeed" style="position: absolute; width: 138px; height: 26px; z-index: 7; left: 48px; top: 0px; background-image:   url('${ctx}/images/camera/movebar.jpg'); layer-background-image:   url('${ctx}/images/camera/movebar.jpg'); border: 1px none #000000">
            <div style="position: absolute; width: 9px; height: 9px; z-index: 10; left: 50px; top: 3px; visibility: inherit; background-image:   url('${ctx}/images/camera/pin.gif'); layer-background-image:   url('${ctx}/images/camera/pin.gif'); border: 1px none #000000" id="lySpeedBut" onMouseDown="trackbar_mousedown(lySpeedBut)" onMouseUp="trackbar_mouseup(lySpeedBut)" onMouseMove="trackbar_mousemove(lySpeedBut)">
                <div align="center"></div>
            </div>
            <div align="left">
                <div id="labSpeedValue" style="position:absolute; width:22px; height:10px; z-index:8; left: 114px; top: 2px;">50</div>
                <div id="lybarSpeed" style="position:absolute; width:100px; height:5px; z-index:8; top: 6px; left: 1px;"><img src="${ctx}/images/camera/bar.gif" width="100" height="5"></div>
            </div>
        </div>
    </div>
    <div style="position: absolute; width: 108px; height: 108; z-index: 8; left: 57px; top: 279px" id="lyDeviceMove">
        <div style="position: absolute; width: 33px; height: 31px; z-index: 9; left: 32px; top: 33px" id="lyAuto">
            <img id="imgAuto" border="0" src="${ctx}/images/camera/auto.gif" width="33px" height="31px" onClick="Device_Auto('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 32px; height: 33px; z-index: 8; left: 65px; top: 64px" id="lyDownRight">
            <img id="imgDownRight" border="0" src="${ctx}/images/camera/ctl_right_down.gif" width="32px" height="33px" onMouseDown="Device_DownRight(true,'${ctx}/images/camera/')" onMouseUp="Device_DownRight(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 32px; height: 33px; z-index: 7; left: 0px; top: 64px" id="lyDownLeft">
            <img id="imgDownLeft" border="0" src="${ctx}/images/camera/ctl_left_down.gif" width="32px" height="33px" onMouseDown="Device_DownLeft(true,'${ctx}/images/camera/')" onMouseUp="Device_DownLeft(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 32px; height: 33px; z-index: 6; left: 65px; top: 0px" id="lyUpRight">
            <img id="imgUpRight" border="0" src="${ctx}/images/camera/ctl_right_up.gif" width="32px" height="33px" onMouseDown="Device_UpRight(true,'${ctx}/images/camera/')" onMouseUp="Device_UpRight(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 32px; height: 33px; z-index: 5; left: 0px; top: 0px" id="lyUpLeft">
            <img id="imgUpLeft" border="0" src="${ctx}/images/camera/ctl_left_up.gif" width="32px" height="33px" onMouseDown="Device_UpLeft(true,'${ctx}/images/camera/')" onMouseUp="Device_UpLeft(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 32px; height: 31px; z-index: 4; left: 65px; top: 33px" id="lyRight">
            <img id="imgRight" border="0" src="${ctx}/images/camera/ctl_right.gif" width="32px" height="31px" onMouseDown="Device_Right(true,'${ctx}/images/camera/')" onMouseUp="Device_Right(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 33px; height: 31px; z-index: 3; left: 0px; top: 33px" id="lyLeft">
            <img id="imgLeft" border="0" src="${ctx}/images/camera/ctl_left.gif" width="32px" height="31px" onMouseDown="Device_Left(true,'${ctx}/images/camera/')" onMouseUp="Device_Left(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 33px; height: 33px; z-index: 2; left: 32px; top: 64px" id="lyDown">
            <img id="imgDown" border="0" src="${ctx}/images/camera/ctl_down.gif" width="33px" height="33px" onMouseDown="Device_Down(true,'${ctx}/images/camera/')" onMouseUp="Device_Down(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <div style="position: absolute; width: 33px; height: 33px; z-index: 1; left: 32px; top: 0px" id="lyUp"><img id="imgUp" border="0" src="${ctx}/images/camera/ctl_up.gif" width="33px" height="33px" onMouseDown="Device_Up(true,'${ctx}/images/camera/')" onMouseUp="Device_Up(false,'${ctx}/images/camera/')" onMouseOut="Device_MouseOut('${ctx}/images/camera/')"></div>
        <p></div>
    <div style="position: absolute; width: 197px; height: 78px; z-index: 7; left: 1px; top: 20px;" id="lyDeviceType">
        <table border="0" width="100%" id="table24" height="70px" cellspacing="0" cellpadding="0">
            <tr>
                <td width="32%" height="11px"><script>OutPrint(JS_cShow59);</script></td>
                <td width="68%" height="11px">
                    <select name="dDeviceType" style="width: 125px" onChange="DeviceCtrl_DeviceType_Change()">
                    </select></td>
            </tr>
            <tr>
                <td width="32%" height="24px"><script>OutPrint(JS_cShow60);</script></td>
                <td width="68%" height="24px"><select name="selComFormat" id="selComFormat" style="width: 125px" onChange="DeviceCtrl_DeviceType_Change_3520()">
                    <option value="1">300,n,8,1</option>
                    <option value="2">1200,n,8,1</option>
                    <option value="3">2400,n,8,1</option>
                    <option value="4">4800,n,8,1</option>
                    <option value="5">9600,n,8,1</option>
                    <option value="6">19200,n,8,1</option>
                    <option value="7">38400,n,8,1</option>
                    <option value="8">57600,n,8,1</option>
                    <option value="9">115200,n,8,1</option>
                </select></td>
            </tr>
            <tr>
                <td colspan="2">
                    <table border="0" width="100%" id="table27" cellspacing="0" cellpadding="0">
                        <tr>
                            <td width="64"><script>OutPrint(JS_cShow61);</script></td>
                            <td>
                                <input name="txDevAddress" type="text" id="txDevAddress" size="8" maxlength="3" value="1" onChange="value=value.replace(/[^\d]/g,'');Device_Set3DEnabled()"  onKeyDown="value=value.replace(/[^\d]/g,'')" onKeyUp="Device_Set3DEnabled()"></td>
                            <td></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <p>
        <label> &nbsp;&nbsp;&nbsp;</label>
    </p>
    <div id="labPTZCruise" style="position:absolute; width:115px; height:23px; z-index:7; left: 0px; top: 435px" class="Rec">
        <b><a href="JavaScript:DHT_SetPTZCruise()"><script>OutPrint(JS_cShow500);</script></a></b>
    </div>
    <p>&nbsp; </p>
</div></td>
</tr>
<tr>
    <td height="16px">
        <img border="0" src="${ctx}/images/camera/v2.9-3.gif" width="262px" height="16px"></td>
</tr>
</table>

</td>
</tr>
<tr>
    <td id="lgtd_3" bgcolor="#5568AF" height="24">
        <div style="position: absolute; width: 20px; height: 20px; z-index: 4;" id="lyMainMenu">
            <div style="position: absolute; width: 270px; height: 54px; z-index: 1; left: 1px; top: -17px" id="lyMenu">
                <table border="0" width="100%" id="table26" cellspacing="0" cellpadding="0" height="49">
                    <tr>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet1" size="25" id="bSet12" class=menubtndown onClick="DHT_Logon()"></td>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet2"  size="25"id="bSet22" class=menubtn onClick="DHT_DeviceCtrl()"></td>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet3" size="25" id="bSet32" class=menubtn onClick="DHT_VideoQuality()"></td>
                        <td align="center">
                            <input type="button" style="width: 67" value="" size="25" name="bSet7" id="bSet72" class=menubtn onClick="DHT_OSDParam()"></td>
                    </tr>
                    <tr>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet4" id="bSet42" class=menubtn onClick="DHT_AlarmInOutCtrl()"></td>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet5" id="bSet52" class=menubtn onClick="DHT_NetWork()"></td>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet8" id="bSet82" class=menubtn onClick="DHT_MemorySet()">
                        </td>
                        <td align="center">
                            <input type="button" style="width: 67" value="" name="bSet6" id="bSet62" class=menubtn onClick="DHT_Advanced()">
                        </td>
                    </tr>
                </table>
            </div>
            <p></div>
        <p></td>
</tr>
<tr>
    <td height="18">
        <img border="0" src="${ctx}/images/camera/v2.9-4-m.gif" width="282" height="18"></td>
</tr>
</table>
</div>
</td>
</tr>
</table>
</td>
</tr>
</table>
</div>

</body>
</html>