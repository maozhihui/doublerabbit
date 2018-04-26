package com.comba.server.transport.feibit;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UdpClient {
	//网关列表 <sn,ip>
	public static ConcurrentHashMap<String,String> gatewaysMap = new ConcurrentHashMap<String,String>();
	private static DatagramSocket detectSocket;
	
	public UdpClient(){
	}
	
	static {
		 try {
			 detectSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void send(String broadcastAdd, int packetPort,String msg,long interval) {
       
        // Send packet thread
        new Thread(new Runnable() {

            @Override
            public void run() {

                log.info("Send thread started.");
                
                while (true) {
                    try {
                        byte[] buf = new byte[1024];
                      
                        // Broadcast address
                        InetAddress hostAddress = InetAddress.getByName(broadcastAdd);
                       
                        buf = msg.getBytes();
                        //log.info("Send " + msg + " to " + hostAddress);

                        // Send packet to hostAddress:9090, server that listen
                        // 9090 would reply this packet
                        DatagramPacket out = new DatagramPacket(buf,
                                buf.length, hostAddress, packetPort);
                       if(detectSocket == null){
                    	   detectSocket = new DatagramSocket();
                       }
                       //gatewaysMap.clear();
                       detectSocket.send(out);
                    
                       Thread.sleep(interval);
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        }).start();
	}
	
	public static void receive() {
        // Receive packet thread.
        new Thread(new Runnable() {
        	
            @Override
            public void run() {
            	
            	log.info("Receive thread started.");
                
                while(true) {
                	
                    byte[] buf = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    
                    try {
                        detectSocket.receive(packet);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

/*                    String rcvd = "Received from " + packet.getSocketAddress() + ", Data="
                           + new String(packet.getData(), 0, packet.getLength());
                    log.info(rcvd);*/
                    log.info(new String(packet.getData()));
                    
                    String receivedata = new String(packet.getData(), 0, packet.getLength());
                    String keyValues[] = receivedata.split("\r\n");
                    String IP = null;
                    String SN = null;
                    
                    for(String keyValue:keyValues)
    				{
                    	if(keyValue != null && keyValue != "")
    					{
    						String data[] = keyValue.split(":");
    						
    						if(data[0].contains("IP"))
    						{
    							IP = data[1];
    						}
    						else if(data[0].contains("SN"))
    						{
    							SN = data[1];
    						}
    					}
    				}
                    
                    gatewaysMap.put(SN, IP);
                }
            }
        }).start();
	}
	
	public void closeSocket() {
		detectSocket.close();
	}
}