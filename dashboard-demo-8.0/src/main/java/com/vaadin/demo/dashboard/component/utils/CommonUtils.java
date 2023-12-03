package com.vaadin.demo.dashboard.component.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommonUtils {

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
