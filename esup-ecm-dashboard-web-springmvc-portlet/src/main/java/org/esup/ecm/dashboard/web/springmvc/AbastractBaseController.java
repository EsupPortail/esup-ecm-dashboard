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
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.portlet.ModelAndView;

/**
 * @author jpark
 */
public abstract class AbastractBaseController implements MessageSourceAware{
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
	
	
	protected static final String PREFERNCES_UPDATED = "isPreferencesUpdated";
	
	/**
	 * Password to used by Nuxeo's portal-sso
	 */
	protected static final String NUXEO_SECRET = "nuxeoPortalAuthSecret";
	
	/** SessionRegistry used by this controller */
	@Autowired protected SessionRegistry sessionRegistry;
	/** Authenticator used by this controller */
	@Autowired protected Authenticator authenticator;
	@Autowired protected ViewSelector viewSelector;
	protected MessageSource messageSource;
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@ExceptionHandler({Exception.class})
	public ModelAndView handleException(Exception ex) {
		
		logger.error("Exception catching in spring mvc controller ... ", ex);
		
		ModelMap model = new ModelMap();
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
    	PrintStream print = new PrintStream(output);
    	ex.printStackTrace(print);
    	//String exceptionStackTrace = new String(output.toByteArray());
    	//model.put("exceptionStackTrace", exceptionStackTrace);
    	model.put("exceptionMessage", ex.getMessage());
    	
        return new ModelAndView("exception", model);
	}
	
	protected NuxeoResource getNuxeoResourceFromPortletSession(PortletRequest request){
		//request.getPortletSession().setAttribute("nuxeoResource", null);
		NuxeoResource nuxeoResource = (NuxeoResource) request.getPortletSession().getAttribute("nuxeoResource");
		if(nuxeoResource == null){
			nuxeoResource = new NuxeoResource();
			request.getPortletSession().setAttribute("nuxeoResource", nuxeoResource);
			sessionRegistry.registerNewSession(request.getPortletSession().getId(), nuxeoResource);
		}
		return nuxeoResource;
	}
	
	
    protected void makeColumns(PortletRequest request,NuxeoResource nuxeoResource) throws Exception{
    	PortletPreferences prefs = request.getPreferences();
    	if(prefs.getValue(NUXEO_COLUMNS, "").contains("[") && prefs.getValue(NUXEO_COLUMNS, "").contains("]")){
			nuxeoResource.setColumns(prefs.getValue(NUXEO_COLUMNS, ""));
		}
    }

	
	protected void makeNuxeoSession(PortletRequest request,NuxeoResource nuxeoResource) throws Exception{
		PortletPreferences prefs = request.getPreferences();
		if(prefs.getValue(NUXEO_HOST, "").contains("http://")){
			String nuxeoHost = prefs.getValue(NUXEO_HOST, "");
			String nuxeoSecret = prefs.getValue(NUXEO_SECRET, "");
			String uid;
			try {
				uid = authenticator.getUser().getLogin();
			} catch (Exception e) {
				// local test mode with pluto plugin.
				uid = "Administrator";
			}
			nuxeoResource.makeSession(nuxeoHost, uid, nuxeoSecret);
		}
	}
	
}
