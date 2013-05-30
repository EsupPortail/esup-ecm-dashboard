package org.esup.ecm.dashboard.web.springmvc;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.services.auth.Authenticator;
import org.esup.ecm.dashboard.services.nuxeo.NuxeoService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.portlet.ModelAndView;

/**
 * @author jpark
 */
public abstract class AbastractBaseController {
	
	private final Logger logger = new LoggerImpl(this.getClass());
	
	/**
	 * Nuxeo server's address to use when {@link NuxeoResource}'s makeSession method is called.
	 */
	protected static final String NUXEO_HOST = "nuxeoHost";
	/**
	 * NXQL to use when {@link NuxeoService}'s getListByQuery method is called.
	 */
	protected static final String NXQL = "NXQL";
	protected static final String NUXEO_MAX_PAGE_SIZE = "maxPageSize";
	/**
	 * The list of Nuxeo Nocumnet's columns which will be stored in the Preferences of portlet.<br>
	 */
	protected static final String NUXEO_COLUMNS = "columns";
	/**
	 * Password to used by Nuxeo's portal-sso
	 */
	protected static final String NUXEO_SECRET = "nuxeoPortalAuthSecret";
	
	/** SessionRegistry used by this controller */
	@Autowired protected SessionRegistry sessionRegistry;
	/** Authenticator used by this controller */
	@Autowired protected Authenticator authenticator;
	@Autowired protected ViewSelector viewSelector;
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		
		logger.error("Exception catching in spring mvc controller ... ", ex);
		
		ModelMap model = new ModelMap();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	PrintStream print = new PrintStream(output);
    	ex.printStackTrace(print);
    	String exceptionStackTrace = new String(output.toByteArray());
    	model.put("exceptionStackTrace", exceptionStackTrace);
    	model.put("exceptionMessage", ex.getMessage());
        return new ModelAndView("exception", model);
	}
	
	
    /**
     * Creates the NuxeoResource in the PortletSession
     * 
     * @see org.esup.ecm.dashboard.web.listener.SessionTimeoutListener#sessionDestroyed() 
     * 
     * @param request PortletRequest
     */
	protected NuxeoResource buildNuxeoSession(PortletRequest request) throws Exception{
		NuxeoResource nuxeoResource = (NuxeoResource) request.getPortletSession().getAttribute("nuxeoResource");
		if(nuxeoResource == null){
			nuxeoResource = new NuxeoResource();
			request.getPortletSession().setAttribute("nuxeoResource", nuxeoResource);
		}
		if(nuxeoResource.hasSession())
			return nuxeoResource;
		
		PortletPreferences prefs = request.getPreferences();
		if(prefs.getValue("initPreferences", "").equals("yes") && !nuxeoResource.hasSession()){
			String uid;
			try {
				uid = authenticator.getUser().getLogin();
			} catch (Exception e) {
				// local test mode with pluto plugin.
				uid = "Administrator";
			}
			nuxeoResource.makeSession(uid, prefs.getValue(NUXEO_HOST, ""), prefs.getValue(NUXEO_SECRET, ""));
			sessionRegistry.registerNewSession(request.getPortletSession().getId(), nuxeoResource);
		}
		return nuxeoResource;
	}
}
