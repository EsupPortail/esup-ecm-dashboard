package org.esup.ecm.dashboard.web.listener;

import javax.servlet.http.HttpSessionEvent;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * Declared in web.xml as
 * <pre>
 * &lt;listener&gt;
 *     &lt;listener-class&gt;org.esup.ecm.dashboard.web.listener.SessionTimeoutListener&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 *
 * Publishes <code>HttpSessionApplicationEvent</code>s to the Spring Root WebApplicationContext. Maps
 * javax.servlet.http.HttpSessionListener.sessionCreated() to {@link HttpSessionCreatedEvent}. Maps
 * javax.servlet.http.HttpSessionListener.sessionDestroyed() to {@link HttpSessionDestroyedEvent}.
 *
 * @author jpark
 */
public class SessionTimeoutListener extends HttpSessionEventPublisher{
	
	
    /**
     * Handles the HttpSessionEvent by publishing a {@link HttpSessionDestroyedEvent} to the application
     * appContext.
     * 
     * Retrieves the NuxeoResource from {@link SessionRegistry} to close nuxeo session.
     *
     * @param event The HttpSessionEvent pass in by the container
     */
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
