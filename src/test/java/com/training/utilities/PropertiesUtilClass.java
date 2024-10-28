package com.training.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.training.base.BaseTest;




public class PropertiesUtilClass extends BaseTest{
	//reading data from the property
	// writing the property
	// deleting the property
	//get all property name
	
	//reading data from the properties file
	public  static String readDataFromPropertyFile(String path, String key){
		File file = new File(path);
		FileInputStream fis = null;
		Properties property = new Properties();
		String data = null;
		
		try {
			fis = new FileInputStream(file);
			property.load(fis);
			data = property.getProperty(key);
			fis.close();
			
		}catch (FileNotFoundException e) {
			mylog.error("File not found in the path.");
		}catch (IOException e) {
			mylog.error("Error loading the file in properties object ");
		}

		return data;
	}
	
	
	// Writing the data to the property
	public static void writeDataToPropertyFile(String path, String key, String value){
		File file = new File(path);
		FileOutputStream fos = null;
		Properties property = new Properties();
		
		try {
			fos = new FileOutputStream(file);
			property.setProperty(key, value);
			property.store(fos,"Writing data");
			fos.close();
			
		}catch (FileNotFoundException e) {
			mylog.error("File not found in the path.");
		}catch (IOException e) {
			mylog.error("Error storing the file in properties file ");
		}

		
	}
	
	// deleting the data in the properties file
	public static void deleteDataInPropertyFile(String path, String key){
		File file = new File(path);
		FileOutputStream fos = null;
		Properties property = new Properties();

		try {
			fos = new FileOutputStream(file);
			property.remove(key);
			property.store(fos,"deleting data");
			fos.close();
			
		}catch (FileNotFoundException e) {
			mylog.error("File not found in the path.");
		}catch (IOException e) {
			mylog.error("Error deleting the data from properties file ");
		}

		
	}
	
	// get all property names
	public static Set<Object> getAllPropertyNames(String path){
		File file = new File(path);
		FileInputStream fis = null;
		Properties property = new Properties();
		Set<Object> keys = null;
		try {
			fis = new FileInputStream(file);
			property.load(fis);
			keys = property.keySet();
			fis.close();
			
		}catch (FileNotFoundException e) {
			mylog.error("File not found in the path.");
		}catch (IOException e) {
			mylog.error("Error getting the property names from properties file ");
		}
		
		return keys;
	}
	
//	public static void main(String[] args) {
//		String path = System.getProperty("user.dir")+"/resources/data.properties";
//		
//		Set<Object> keys = getAllPropertyNames(path);
//		Iterator<Object> it = keys.iterator();
//		mylog.error("Printing property names: ");
//		while(it.hasNext()) {
//			mylog.error(it.next());
//		}
//	}

}
