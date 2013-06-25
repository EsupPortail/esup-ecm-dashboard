package org.esup.ecm.dashboard.web.springmvc;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
/**
 * The <CODE>EditController</CODE> represents the edit mode session scoped controller.<br>
 * It extends the AbastractBaseController to provide nuxeo session information to portlets.<br>
 * 
 * @author jpark
 */
@Scope("session")
@Controller
@RequestMapping(value = "EDIT")
public class EditController extends AbastractBaseController{
	
	/**
	 * 
	 * Initialize the Preferences for ᅟthe Form UI
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RenderMapping
    public ModelAndView editPreferences(RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	PortletPreferences prefs = request.getPreferences();
    	
    	model.put(NUXEO_HOST + "_readOnly", prefs.isReadOnly(NUXEO_HOST));
		model.put(NUXEO_HOST,prefs.getValue(NUXEO_HOST, null));
		
		model.put(NXQL + "_readOnly", prefs.isReadOnly(NXQL));
    	model.put(NXQL,prefs.getValue(NXQL, null));
    	
    	model.put(NUXEO_MAX_PAGE_SIZE + "_readOnly", prefs.isReadOnly(NUXEO_MAX_PAGE_SIZE));
    	model.put(NUXEO_MAX_PAGE_SIZE,prefs.getValue(NUXEO_MAX_PAGE_SIZE, null));
    	
    	model.put(NUXEO_COLUMNS + "_readOnly", prefs.isReadOnly(NUXEO_COLUMNS));
    	model.put(NUXEO_COLUMNS,prefs.getValue(NUXEO_COLUMNS, null));
    	
    	return new ModelAndView(viewSelector.getViewName(request, "edit"), model);
    }
    
    /**
     * Store preferences values.
     * If 'nuxeoHost' in the preferences is not readOnly then recreate the nuxeo session with the 'nuxeoHost' passed.
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ActionMapping(params="action=edit")
	public void editPreferences(ActionRequest request, ActionResponse response) throws Exception {
    	
		PortletPreferences prefs = request.getPreferences();
		
		NuxeoResource nuxeoResource = getNuxeoResourceFromPortletSession(request);
		if(!prefs.isReadOnly(NUXEO_HOST)){
			prefs.setValue(NUXEO_HOST, request.getParameter(NUXEO_HOST));
			prefs.store();
			makeNuxeoSession(request, nuxeoResource);
    	}
		if(!prefs.isReadOnly(NXQL)){
			prefs.setValue(NXQL, request.getParameter(NXQL));
		}
		if(!prefs.isReadOnly(NUXEO_MAX_PAGE_SIZE)){
			prefs.setValue(NUXEO_MAX_PAGE_SIZE, request.getParameter(NUXEO_MAX_PAGE_SIZE));
		}
		if(!prefs.isReadOnly(NUXEO_COLUMNS)){
			prefs.setValue(NUXEO_COLUMNS, request.getParameter(NUXEO_COLUMNS));
		}
		
		prefs.store();
		
		makeColumns(request, nuxeoResource);
		response.setPortletMode(PortletMode.VIEW);
	}
    
    
}
