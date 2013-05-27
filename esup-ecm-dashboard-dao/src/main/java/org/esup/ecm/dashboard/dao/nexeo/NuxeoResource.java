package org.esup.ecm.dashboard.dao.nexeo;

import java.util.ArrayList;

import org.nuxeo.ecm.automation.client.jaxrs.Session;
import org.nuxeo.ecm.automation.client.jaxrs.impl.HttpAutomationClient;
import org.nuxeo.ecm.automation.client.jaxrs.spi.auth.PortalSSOAuthInterceptor;

public class NuxeoResource {
	private Session session;
	private boolean hasSession = false;
	private ArrayList<String> columns;
	
	public Session getSession() {
		if(session == null)
			throw new IllegalAccessError("No session available.");
		return session;
	}
	
	public boolean hasSession(){
		return hasSession;
	}

	public void makeSession(String uid, String nuxeoHost, String nuxeoPortalAuthSecret) throws Exception{
		if(session == null){
			HttpAutomationClient client = new HttpAutomationClient(nuxeoHost + "/site/automation");
			client.setRequestInterceptor(new PortalSSOAuthInterceptor(nuxeoPortalAuthSecret, uid));
			this.session = client.getSession();
			hasSession = true;
		}
	}
	
	public void setColumns(ArrayList<String> cols){
		columns = new ArrayList<String>();
		for(String column : cols){
			columns.add(column.substring(column.indexOf(":")+1));
		}
	}

	public ArrayList<String> getColumns() {
		return columns;
	}

	public void closeSession(){
		if(session != null)
			session.getClient().shutdown();
		session = null;
		hasSession = false;
	}
}
