package org.esup.ecm.dashboard.web.springmvc;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esup.ecm.dashboard.services.auth.Authenticator;
import org.esup.ecm.dashboard.web.resource.NuxeoResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Scope("session")
@Controller
@RequestMapping(value = "EDIT")
public class EditController extends AbastractExceptionController{
	
	@Autowired private NuxeoResource userSession;
	@Autowired private Authenticator authenticator;
    @Autowired private ViewSelector viewSelector;
    
	private static final String NUXEO_HOST = "nuxeoHost";
    private static final String NXQL = "NXQL";
    private static final String NUXEO_MAX_PAGE_SIZE = "maxPageSize";
    private static final String NUXEO_COLUMNS = "columns";
    private static final boolean INIT = false;
    
    @RenderMapping
    public ModelAndView editPreferences(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	PortletPreferences prefs = request.getPreferences();
    	if(!prefs.isReadOnly(NUXEO_HOST)){
    		model.put(NUXEO_HOST,prefs.getValue(NUXEO_HOST, null));
    	}
    	model.put(NXQL,prefs.getValue(NXQL, null));
    	model.put(NUXEO_MAX_PAGE_SIZE,prefs.getValue(NUXEO_MAX_PAGE_SIZE, null));
    	model.put(NUXEO_COLUMNS,prefs.getValue(NUXEO_COLUMNS, null));
    	
    	return new ModelAndView(viewSelector.getViewName(request, "edit"), model);
    }
    
    @ActionMapping(params="action=edit")
	public void editPreferences(ActionRequest request, ActionResponse response) throws Exception {
		PortletPreferences prefs = request.getPreferences();
		if(!prefs.isReadOnly(NUXEO_HOST)){
			prefs.setValue(NUXEO_HOST, request.getParameter(NUXEO_HOST));
    	}
		
		
		
		prefs.setValue(NXQL, request.getParameter(NXQL));
		prefs.setValue(NUXEO_MAX_PAGE_SIZE, request.getParameter(NUXEO_MAX_PAGE_SIZE));
		prefs.setValue(NUXEO_COLUMNS, request.getParameter(NUXEO_COLUMNS));
		
		
		
		response.setRenderParameter("initPreferces", "yes");
		
		prefs.store();
		response.setPortletMode(PortletMode.VIEW);
	}
}
