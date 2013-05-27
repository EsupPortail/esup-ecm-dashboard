package org.esup.ecm.dashboard.web.listener;

import javax.servlet.http.HttpSessionEvent;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionEventPublisher;

public class SessionTimeoutListener extends HttpSessionEventPublisher{

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {

		SessionRegistry sessionRegistry = (SessionRegistry) ApplicationContextHolder.getContext().getBean("sessionRegistry");

	    SessionInformation sessionInfo = (sessionRegistry != null ? 
	    		sessionRegistry.getSessionInformation(event.getSession().getId()) : null);
	    
	    NuxeoResource nuxeoResource = null;
	    if (sessionInfo != null) {
	    	nuxeoResource = (NuxeoResource) sessionInfo.getPrincipal();
	    }
	    
	    if (nuxeoResource != null) {
	    	nuxeoResource.closeSession();
	    }
	    
	    super.sessionDestroyed(event);
	}

}
