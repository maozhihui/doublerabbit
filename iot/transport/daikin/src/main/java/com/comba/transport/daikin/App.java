package com.comba.transport.daikin;

import com.comba.transport.daikin.service.ModbusMasterService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        ModbusMasterService service = new ModbusMasterService();
        service.init();
        Thread.sleep(2*1000L);

        //读取内机状态
        byte slaveId = 1;
        short startAddr = 0x07d0;//起始地址
        short num = 6;//寄存器数量
        //service.checkSlaveStatus(slaveAddr,startAddr,num);
        
        //1-00 ,1组0号
        int groupId = 1;
        int No = 0;
        byte state = 0x00;
        long onlineTimeout = 600000;
        ModbusMasterService.onDeviceRegister("1-1-00",onlineTimeout);
       // ModbusMasterService.setAirConditioningState(slaveId,groupId,No,state);
        
    }
}
