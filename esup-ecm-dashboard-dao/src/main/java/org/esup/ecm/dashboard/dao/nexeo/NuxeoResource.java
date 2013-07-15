package org.esup.ecm.dashboard.dao.nexeo;

import java.io.IOException;
import java.util.ArrayList;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.spi.auth.PortalSSOAuthInterceptor;

public class NuxeoResource {
	private Session session;

	private ArrayList<String> columns;
	public Session getSession() {
		if(session == null)
			throw new IllegalAccessError("No session available.");
		return session;
	}
	
	public boolean hasSession(){
		return (session != null);
	}

	public void makeSession(String nuxeoHost, String ssoUserId, String ssoUserPw) throws Exception{
		if(session != null){
			closeSession();
		}
		HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
		client.setRequestInterceptor(new PortalSSOAuthInterceptor(ssoUserPw, ssoUserId));
		this.session = client.getSession();
	}
	
	public void setColumns(String[] colsStr) throws JsonParseException, JsonMappingException, IOException{
		
		// set columns from preferences.
		// Format : JSON
//		String tmp = "{\"columns\":" + colsStr + "}";
//		ObjectMapper mapper = new ObjectMapper();
//		Map<String, Object> userInMap = null;
//		userInMap = mapper.readValue(tmp, new TypeReference<Map<String, Object>>() {});
//		@SuppressWarnings("unchecked")
//		ArrayList<String> cols = (ArrayList<String>) userInMap.get("columns");
//				
		columns = new ArrayList<String>();
		for(String column : colsStr){
			if(column.contains(":")){
				String col = column.substring(column.indexOf(":")+1);
				if(!hasSameColumn(col)){
					columns.add(col);
				}
			}
		}
	}
	
	private boolean hasSameColumn(String column){
		for(String col : columns){
			if(column.equals(col))
				return true;
		}
		return false;
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void closeSession(){
		if(session != null)
			session.getClient().shutdown();
		session = null;
	}
}
