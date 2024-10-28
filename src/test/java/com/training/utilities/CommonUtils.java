package com.training.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;





import net.bytebuddy.utility.RandomString;

public class CommonUtils {
	public static String getStringDateAndTimeStamp() {
		String value = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return value;
	}
	
	public static String generateRandomString() {
		return  RandomString.make();

	}
	
	
}