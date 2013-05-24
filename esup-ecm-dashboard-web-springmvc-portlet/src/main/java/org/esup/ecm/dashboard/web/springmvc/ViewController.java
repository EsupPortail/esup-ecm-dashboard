package org.esup.ecm.dashboard.web.springmvc;

import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esup.ecm.dashboard.services.auth.Authenticator;
import org.esup.ecm.dashboard.services.nuxeo.NuxeoService;
import org.esup.ecm.dashboard.web.resource.NuxeoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Scope("session")
@Controller
@RequestMapping(value = "VIEW")
public class ViewController extends AbastractExceptionController {
	
	
	@Autowired private NuxeoService nuxeoService;
	@Autowired private Authenticator authenticator;
	@Autowired private NuxeoResource nuxeoResource;
	@Autowired private ViewSelector viewSelector;
	

	private static final String NUXEO_HOST = "nuxeoHost";
    private static final String NXQL = "NXQL";
    private static final String NUXEO_MAX_PAGE_SIZE = "maxPageSize";
    private static final String NUXEO_COLUMNS = "columns";
    
    
    @RenderMapping
    public ModelAndView welcome(@RequestParam(required=false) String initPreferces, RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	
    	if(initPreferces != null && initPreferces.equals("yes")){
    		
    		final PortletPreferences prefs = request.getPreferences();
        	String nuxeoHost = prefs.getValue(NUXEO_HOST, "");
        	String nxql = prefs.getValue(NXQL, "");
        	String maxPageSize = prefs.getValue(NUXEO_MAX_PAGE_SIZE, "");
        	String columns = prefs.getValue(NUXEO_COLUMNS, "");
        	
        	model.put(NUXEO_HOST, nuxeoHost);
        	model.put(NXQL, nxql);
        	model.put(NUXEO_MAX_PAGE_SIZE, maxPageSize);
        	model.put(NUXEO_COLUMNS, columns);
        	return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    	}else{
    		return new ModelAndView(viewSelector.getViewName(request, "init"), model);
    	}
    }

}
