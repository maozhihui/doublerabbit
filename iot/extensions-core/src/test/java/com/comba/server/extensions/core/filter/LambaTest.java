package com.comba.server.extensions.core.filter;

/**
 * @author maozhihui
 * @Description:
 * @create 2017/12/7 20:45
 **/
public class LambaTest {
    /*@FunctionalInterface
    public interface Runnable{
        void run();
    }*/

    public static void main(String[] args){
        new Thread(()->{
                        System.out.println("hello=====");
                        System.out.println("hello==xxxxx=");}).start();
    }
}
