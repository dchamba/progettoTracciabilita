package com.vaadin.demo.dashboard.component.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonUtils {

	public static String formatoDataEu = "dd/MM/yyyy HH:mm:ss";
	public static String formatoDataJpn = "yyyy-MM-dd HH:mm:ss";
	
    public static final DateFormat DATETIMEFORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final DateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static final DateFormat YEARTWODIGITFORMAT = new SimpleDateFormat("yy");
    public static final DateFormat JULIANDAYFORMAT = new SimpleDateFormat("DDD");
    public static final DateFormat DATEREVERSEFORMAT = new SimpleDateFormat("YYYYMMDD");
    public static final DateFormat HOUMMINUTEFIVEDIGITFORMAT = new SimpleDateFormat("0HHmm");
    
    public static final DecimalFormat TWODECIMALFORMAT = new DecimalFormat("0.00");
    public static final DecimalFormat NODECIMALFORMAT = new DecimalFormat("0");
    
    public static Double toDouble(String valore) {
    	Double valoreDouble = null;
    	try {
    		if(valore != null && !valore.isEmpty()) {
        		valoreDouble = Double.parseDouble(valore);
    		}
    	} catch (NumberFormatException e) {
    	    e.printStackTrace();
    	}
    	return valoreDouble;
    }

	public static String formatDouble(Double value, DecimalFormat formato) {
		return value == null ? "" : formato.format(value);
	}
	
	public static String formatDate(Date value, DateFormat formato) {
		return value == null ? "" : formato.format(value);
	}
	
	public static List<String> stringToList(String stringsList, String separator) {
//		if(stringsList == null || stringsList.isEmpty()) {
//			return Collection.;
//		}
		List<String> list = Stream.of(stringsList.split(";")).collect(Collectors.toList());
		return list;
	}

	public static Date toDate(LocalDate value) {
		if (value == null) {
			return null;
		}
		return  Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static Date toDate(String dataStringa) {
		Date dataResult = null;
		
		try {
			dataResult =  new SimpleDateFormat(formatoDataEu, Locale.ITALIAN).parse(dataStringa);
		} catch (Exception ex) { dataResult = null; }

		if(dataResult == null) {
			try {
				dataResult =  new SimpleDateFormat(formatoDataJpn, Locale.ITALIAN).parse(dataStringa);
			} catch (Exception ex) { dataResult = null; }
		}
		
		return dataResult;
	}

	public static Date toDate(String formato, String dataStringa) throws ParseException {
		return  new SimpleDateFormat(formato, Locale.ITALIAN).parse(dataStringa);
	}

	public static String fromIntConZeroIniziali(Integer numero, Integer lunghezzaString) {
		if (numero == null) {
			return "";
		}
		return  String.format("%0" + lunghezzaString.toString() + "d", numero);
	}

	public static <T> Predicate<T> distinctByKey (
	    Function<? super T, ?> keyExtractor) {
		  
	    Map<Object, Boolean> seen = new ConcurrentHashMap<>(); 
	    return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null; 
	}
	
}
