package clientserver;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class Coordinates {

	public Coordinates() {

	}

	static public String localizedFormat(String pattern, double value, Locale loc) {
		NumberFormat nf = NumberFormat.getNumberInstance(loc);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		return df.format(value);
//		System.out.println(pattern + "  " + output + "  " + loc.toString());
	}

	public String sendCoordinates() {
		// String lat = "55.785955";
		// String lon = "12.524138";
		// This is somewhere in copenhagen
//		DecimalFormat df = new DecimalFormat("00.000000");
		Locale currentLocale = new Locale("en", "US");

		DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols(
				currentLocale);
		unusualSymbols.setDecimalSeparator('|');
		unusualSymbols.setGroupingSeparator('^');
		String strange = "#,##0.###";
		DecimalFormat df = new DecimalFormat(strange,
				unusualSymbols);
		// DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
		// symbols.setDecimalSeparator(';');

		double lat = (55 + Math.random());
		double lon = (12 + Math.random());
		Locale[] locales = { new Locale("en", "US"),new Locale("de", "DE"), new Locale("fr", "FR") };
		localizedFormat("###,###.###", lat, locales[0]);
		return localizedFormat("###,###.######", lat, locales[0])+","+localizedFormat("###,###.######", lon, locales[0]);
//		return df.format(lat) + "," + df.format(lon);
	}
}
