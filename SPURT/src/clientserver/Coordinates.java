package clientserver;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Coordinates {

	public Coordinates() {

	}

	public static String localizedFormat(double value) {
		Locale locale =  new Locale("en", "US");
		String pattern = "###,###.######";
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		return df.format(value);
	}

	public String getCoordinates() {

		double lat = (55 + Math.random());
		double lon = (12 + Math.random());
		return localizedFormat(lat)+","+localizedFormat(lon);
	}
}
