package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validation {

	public static Double isDouble(String str) {
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public static Date isDate(String dateStr, String dateFormat) {
		DateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
}
