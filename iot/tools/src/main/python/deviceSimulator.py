# -*- coding:utf-8 -*-
# author:maozhihui
# date:2017-07-04
# 模拟HTTP协议的设备
import urllib2
import json
import time
import datetime
from time import sleep
import threading
import sys
import ConfigParser

DEVICE_ID = ''
BASE_URL = ''
PING_PERIOD = 3
UPLOAD_PERIOD = 5
TOKEN = 'ea8f1eb2-4fa6-4aad-986f-7b2fb354ba14'
RE_USERNAME = ''
RE_PWD = ''
DERE_GWID = ''
DERE_DEVICES = ''

def register():
    url=BASE_URL+DEVICE_ID+'/register'
    values = {'username':RE_USERNAME,'pwd':RE_PWD}
    jdata = json.dumps(values)                  # 对数据进行JSON格式化编码
    request = urllib2.Request(url, jdata)
    request.get_method = lambda:'POST'  
    request.add_header('Content-Type','application/json')        
    response = urllib2.urlopen(request)
    bodyJson = json.loads(response.read())
    global TOKEN
    TOKEN = bodyJson['data']['token']
    if(bodyJson['errno'] == 200):
        print 'device %s register success,token is %s' % (DEVICE_ID,TOKEN)
        timer = threading.Timer(1,fun_timer)    #注册成功，启动ping
        timer.start()
    else:
        print 'device %s register failed,because %s' % (DEVICE_ID,bodyJson['error'])
        #sys.exit(1)

def deregister():
    url=BASE_URL+DEVICE_ID+'/deregister'
    #此处需要将网关设备ID等补充完整
    #values={"gatewayid":"00000000271d010204","devices":["00000000271d010201","00000000271d010203"]}
    values={"gatewayid":DERE_GWID,"devices":DERE_DEVICES.split(',')}
    jdata = json.dumps(values)                  # 对数据进行JSON格式化编码
    request = urllib2.Request(url, jdata)
    request.get_method = lambda:'POST'  
    request.add_header('Content-Type','application/json')        
    request.add_header('X-Authorization',TOKEN)
    response = urllib2.urlopen(request)
    print response.read()
    timer.cancel()

def ping():
    url=BASE_URL+DEVICE_ID+'/ping'
    #values={"time":"1499332328199"}
    values={"time":str(int(round(time.time()*1000)))}
    jdata = json.dumps(values)                  # 对数据进行JSON格式化编码
    request = urllib2.Request(url, jdata)
    request.get_method = lambda:'PUT'  
    request.add_header('Content-Type','application/json')        
    request.add_header('X-Authorization',TOKEN)
    response = urllib2.urlopen(request)
    print response.read()

def telemetry():
    url=BASE_URL+DEVICE_ID+'/telemetry'
    values = {'temperature':12, 'humidity':45.0, 'active': 'true'}
    jdata = json.dumps(values)                  # 对数据进行JSON格式化编码
    request = urllib2.Request(url, jdata)
    request.get_method = lambda:'POST'  
    request.add_header('Content-Type','application/json')   
    request.add_header('X-Authorization',TOKEN)
    response = urllib2.urlopen(request)
    print response.read()
    #bodyJson = json.loads(response.read())
    #if(bodyJson['errno'] == 200):
    #    print 'device %s telemetry success.' % (DEVICE_ID)
    #else:
    #    print 'device %s telemetry failed,because %s' % (DEVICE_ID,bodyJson['error'])

def uploadAttributes():
    url=BASE_URL+DEVICE_ID+'/attributes'
    #values = {'firmware_version':'1.1.1'}
    #values = {'internet.type':'4G'}
    values= {"internet.type":"4G",#// 有三种模式：4G、wifi、eth
			"internet.ip":"192.168.1.2",
			"internet.mask":"255.255.255.0",
			"internet.gw":"192.168.1.1",
			"internet.dns1":"10.10.0.13",
			"internet.dns2":"10.10.0.12",
			"wifi.power":"on",
			"wifi.txpower":"20",
			"wifi.ssid":"lora",
			"wifi.mode":"NONE",
			"wifi.pwd":"123456",
			"4g.status":"on",
			"4g.mode":"LTE TDD ",
			"4g.careioperater":"china moblie",
			"eth.mode":"static",
			"eth.ip":"192.168.1.2",
			"eth.mask":"255.255.255.0",
			"eth.gw":"192.168.1.1",
			"eth.dns1":"10.10.0.13",
			"eth.dns2":"10.10.0.12",
			"LoRa.power":"on",
			"zigbee.power":"on",
			"zigbee.channel":"11",
			"zigbee.txpower":"20",
			"zigbee.panid":"ffff",
			"zigbee.netid":"ffff",
			"zigbee.mode":"route",
			"zigbee.txmode":"route",
			"dhcp.enable":"on",
			"dhcp.startip":"10.10.1.1",
			"dhcp.endip":"10.10.1.253",
			"dhcp.mask":"255.255.255.0",
			"dhcp.release":"86600",
}
    jdata = json.dumps(values)                  # 对数据进行JSON格式化编码
    request = urllib2.Request(url, jdata)
    request.get_method = lambda:'POST'  
    request.add_header('Content-Type','application/json')        
    request.add_header('X-Authorization',TOKEN)
    response = urllib2.urlopen(request)
    print response.read()
 
def getAttributes():
    url=BASE_URL+DEVICE_ID+'/attributes?needTotal=true'
    request = urllib2.Request(url)
    request.get_method = lambda:'GET'  
    request.add_header('Content-Type','application/json')        
    request.add_header('X-Authorization',TOKEN)
    response = urllib2.urlopen(request)
    print response.read()
	
def loadConfig():
    try:
        config = ConfigParser.ConfigParser()
        config.read('config.ini')
        #读取设备配置信息
        global DEVICE_ID
        DEVICE_ID = str(config.get('DEVICE','deviceId'))
        global PING_PERIOD
        PING_PERIOD = int(config.get('DEVICE','pingPeriod'))
        global UPLOAD_PERIOD
        UPLOAD_PERIOD = int(config.get('DEVICE','uploadPeriod'))
        global BASE_URL
        BASE_URL = 'http://'+str(config.get('SERVER','host'))+':'+str(config.get('SERVER','port'))+'/iot/api/v1/'
        #读取注册业务配置信息
        global RE_USERNAME;
        RE_USERNAME = str(config.get('REGISTER_CONFIG','username'))
        global RE_PWD
        RE_PWD = str(config.get('REGISTER_CONFIG','pwd'))
        #读取去注册业务配置
        global DERE_GWID
        DERE_GWID = config.get('DEREGISTER_CONFIG','gatewayid')
        global DERE_DEVICES
        DERE_DEVICES = config.get('DEREGISTER_CONFIG','devices')
    except:
        sys.exit(1)

def fun_timer():
    print('ping ......')
    ping()
    global timer
    timer = threading.Timer(15,fun_timer)
    timer.start()


if __name__ == "__main__":
    loadConfig()
    '''if len(sys.argv) > 1:
        for i in range(1,len(sys.argv)):
            print 'argv i = ' + sys.argv[i]
            if sys.argv[i] == 'register':
                register()
            elif sys.argv[i] == 'deregister':
                deregister()
			elif sys.argv[i] == 'telemetry':
                telemetry()
            elif sys.argv[i] == 'ping':
                ping()
    else:
        #t = threading.Thread(target=ping,name='test')
        #t.start()
        #t.join()
        print 'Usage:python deviceSimulator.py <register|deregister|telemetry|ping>'
    print 'thread is end ' + threading.current_thread().name
    '''
    print 'Usage:python deviceSimulator.py'
    print 'input options number:'
    print ''
    print '1) register'
    print '2) deregister'
    print '3) uploadAttributes'
    print '4) getAttributes'
    print '5) telemetry'
    print '6) quit'
    print ''
    register()
    while(1):
        try:
            name = raw_input()
            print 'your input command is %s.' % (name)
            if (name == '1'):
                register()
            elif (name == '2'):
                deregister()
            elif (name == '3'):
                uploadAttributes()
            elif (name == '4'):
                getAttributes()
            elif (name == '5'):
                telemetry()
            elif (name == '6'):
                deregister()
                sys.exit(1) 
            else:
                print 'this command is not support...'
        except KeyboardInterrupt:
            deregister()
            sys.exit(1)   