package com.comba.mqtt.service.util;

import java.io.File;
import java.io.IOException;

import org.dtools.ini.BasicIniFile;
import org.dtools.ini.IniFile;
import org.dtools.ini.IniFileReader;
import org.dtools.ini.IniItem;
import org.dtools.ini.IniSection;
import org.springframework.util.ResourceUtils;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		IniFile iniFile = new BasicIniFile(); 
		File file = ResourceUtils.getFile("classpath:mqtt.ini");
        //IniFileReader reader = new IniFileReader(iniFile, new java.io.File("D:\\IOT_STP\\trunk\\Source\\demonstrate\\src\\main\\resources\\mqtt.ini"));
				IniFileReader reader = new IniFileReader(iniFile, file);
        reader.read();  
        System.out.println("sectionNum=" + iniFile.getNumberOfSections());
          
        for(int i=0;i<iniFile.getNumberOfSections();i++){  
            IniSection sec = iniFile.getSection(i);  
              
            System.out.println("---- " + sec.getName() + " ----");  
            for(IniItem item : sec.getItems()){  
                System.out.println(item.getName() + " = " + item.getValue());  
            }  
        }  
	}

}
