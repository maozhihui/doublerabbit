package com.comba.license;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;


/**
 * 解析license的xml明文。license数据转换xml数据格式，并读取xml的key,value。
 * 
 * @author huangjinlong
 *
 */

@Slf4j
public class LicenseParser {
	private static final XStream stream = new XStream(new DomDriver("UTF-8"));
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	
	static {
		stream.alias("configuration", License.class);
		stream.registerConverter(new LicenseConverter());
	}
	
	public License parse(String licenseContent) {
		try{		
			License license = (License) stream.fromXML(licenseContent);
			
			if(license.getStartTime() == null || 
					license.getEndTime() == null ||
					license.getSerialNumber() == null ||
					license.getContractNumber() == null ){
					throw new LicenseException(LicenseStatus.INVALID_LICENSE,"License'format is error!");
				}
			return license;
		}
		catch(Exception e){
			throw new LicenseException(LicenseStatus.INVALID_LICENSE,e.getMessage());
		}
	}

	
	public static class LicenseConverter implements Converter {
		private ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String,String>();
		@SuppressWarnings("rawtypes")
		
		public boolean canConvert(Class clazz) {
			return clazz == License.class;
		}
		
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			License license = new License();
			map.clear();
			try {
				while(reader.hasMoreChildren()) {
					reader.moveDown();
					String name = reader.getAttribute("Name");
					if(name.equalsIgnoreCase("ValidTime")||
					   name.equalsIgnoreCase("ValidDevice")||
					   name.equalsIgnoreCase("ContractInfo")||
					   name.equalsIgnoreCase("ConfigData")){
						parseKeyValue(reader);
					}
					reader.moveUp();
				}

				for (Map.Entry<String, String> entry : map.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					
					if(key.equalsIgnoreCase("StartTime")){
						license.setStartTime(dateFormat.parse(value));
					}else if(key.equalsIgnoreCase("EndTime")){
						license.setEndTime(dateFormat.parse(value));
					}else if(key.equalsIgnoreCase("SerialNumber")){
						license.setSerialNumber(value);
					}else if(key.equalsIgnoreCase("ContractNumber")){
						license.setContractNumber(value);
					}else if(key.equalsIgnoreCase("UserNumber")){
						license.setUserNumber(Integer.parseInt(value));
					}else if(key.equalsIgnoreCase("NENumber")){
						license.setNENumber(Integer.parseInt(value));
					}
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				log.error("INVALID_LICENSE ",e);
				throw new LicenseException(e);
			}
			return license;
		}
		
		private void parseKeyValue(HierarchicalStreamReader reader) {
			while(reader.hasMoreChildren()) {
				reader.moveDown();
				String key = reader.getAttribute("key");
				String value = reader.getAttribute("value");
				map.put(key, value);
				reader.moveUp();
			}
		}

		public void marshal(Object arg0, HierarchicalStreamWriter arg1,	MarshallingContext arg2) {
			throw new UnsupportedOperationException();
		}
	}
	
}
