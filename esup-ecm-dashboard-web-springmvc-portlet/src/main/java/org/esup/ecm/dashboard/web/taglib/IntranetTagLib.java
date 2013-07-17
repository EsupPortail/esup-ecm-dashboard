package org.esup.ecm.dashboard.web.taglib;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.nuxeo.ecm.core.schema.utils.DateParser;

public class IntranetTagLib {
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
	private static final String dateColumns = "dc:modified,dc:created,dc:issued,dc:valid,dc:expired";
	
	public static String getValue(PropertyMap map, String key){
		String value = map.getString(key);
		return (value == null || value.equals("null")) ? "" : value;
	}
	
	public static String getLastModifiedDate(PropertyMap map){
		Date d = DateParser.parseDate(map.getString("dc:modified"));
		return sdf.format(d);
	}
	
	public static String getImgFileName(PropertyMap map){
		return map.getString("common:icon");
	}
	
	public static String getDublinCoreProperty(PropertyMap map, String name){
		if(dateColumns.contains(name)){
			Date d = DateParser.parseDate(map.getString("dc:"+name));
			return sdf.format(d);
		}
		String rtnValue = map.getString("dc:"+name);
		return (rtnValue == null || rtnValue.equals("null")) ? "" : rtnValue;
	}
	
	public static String getColumnValue(PropertyMap map, String name){
		if(dateColumns.contains(name)){
			Date d = DateParser.parseDate(map.getString(name));
			return sdf.format(d);
		}
		String rtnValue = map.getString(name);
		return (rtnValue == null || rtnValue.equals("null")) ? "" : rtnValue;
	}
}
