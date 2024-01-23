package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static Date StringToDate(String s) {
		Date result = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			result = dateFormat.parse(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

}
