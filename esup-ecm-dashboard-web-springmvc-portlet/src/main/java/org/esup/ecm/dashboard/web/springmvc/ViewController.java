package org.esup.ecm.dashboard.web.springmvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.services.auth.Authenticator;
import org.esup.ecm.dashboard.services.nuxeo.NuxeoService;
import org.nuxeo.ecm.automation.client.jaxrs.model.FileBlob;
import org.nuxeo.ecm.automation.client.jaxrs.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Scope("session")
@Controller
@RequestMapping(value = "VIEW")
public class ViewController extends AbastractExceptionController {
	
	private NuxeoResource nuxeoResource = new NuxeoResource();
	
	@Autowired private NuxeoService nuxeoService;
	@Autowired private Authenticator authenticator;
	@Autowired private ViewSelector viewSelector;
	@Autowired SessionRegistry sessionRegistry;
	
	private static final String NUXEO_SECRET = "nuxeoPortalAuthSecret"; 
	private static final String NUXEO_HOST = "nuxeoHost";
    private static final String NXQL = "NXQL";
    private static final String NUXEO_MAX_PAGE_SIZE = "maxPageSize";
    private static final String NUXEO_COLUMNS = "columns";
    
    
    @RenderMapping
    public ModelAndView welcome(RenderRequest request, RenderResponse response) throws Exception {
    	final PortletPreferences prefs = request.getPreferences();
    	String initPreferces = prefs.getValue("initPreferces", null);
    	
    	if(initPreferces != null && initPreferces.equals("yes")){
    		ModelMap model = new ModelMap();
    		setNuxeoResource(request.getPortletSession().getId(), prefs);
        	model.put("docs", nuxeoService.getListByQuery(nuxeoResource, prefs.getValue(NXQL, "")));
        	model.put("isuPortal", request.getPortalContext().getPortalInfo().contains("uPortal"));
        	model.put("columns", nuxeoResource.getColumns());
            return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    	}else{
    		return new ModelAndView(viewSelector.getViewName(request, "init"), null);
    	}
    }
    
    @RenderMapping(params="action=list")
    public ModelAndView getListByPath(@RequestParam(required=false) String intranetPath, RenderRequest request, RenderResponse response) throws Exception {
    	final PortletPreferences prefs = request.getPreferences();
    	ModelMap model = new ModelMap();
		setNuxeoResource(request.getPortletSession().getId(), prefs);
    	model.put("docs", nuxeoService.getListByPath(nuxeoResource, intranetPath));
    	model.put("isuPortal", request.getPortalContext().getPortalInfo().contains("uPortal"));
    	model.put("columns", nuxeoResource.getColumns());
        return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    }
    
    @ResourceMapping
    public void fileDown(ResourceRequest request, ResourceResponse response) throws Exception {
    	setNuxeoResource(request.getPortletSession().getId(), request.getPreferences());
    	String uid = request.getParameter("uid");
    	FileBlob f = nuxeoService.getFile(nuxeoResource, uid);
    	File file = f.getFile();
    	String fileName = f.getFileName();
    	
    	OutputStream outStream = response.getPortletOutputStream();
		if (!file.exists() || !file.canRead()) {
			outStream.write("<i>Unable to find the specified file</i>".getBytes());
		} else {
			FileInputStream inStream = new FileInputStream(file);
			String mimetype = f.getMimeType();
			response.setContentType(mimetype);
			response.setProperty("Content-disposition", "attachment; filename=\"" + fileName + "\"");
			response.setContentLength((int) file.length());
			
			IOUtils.copy(inStream, response.getPortletOutputStream());
		    response.flushBuffer();		
		}
		outStream.flush();
		outStream.close();
    }
    
	@SuppressWarnings("unchecked")
	private void setNuxeoResource(String portletSessionId, final PortletPreferences prefs) throws Exception {
		// ["dc:title", "dc:modified", "dc:creator", "dc:description"]
		String columns = "{\"columns\":" + prefs.getValue(NUXEO_COLUMNS, "") + "}";

		ObjectMapper mapper = new ObjectMapper();
		// read JSON from a file
		Map<String, Object> userInMap = mapper.readValue(columns, new TypeReference<Map<String, Object>>() {});
		ArrayList<String> cols = (ArrayList<String>) userInMap.get("columns");
		nuxeoResource.setColumns(cols);

		if (nuxeoResource.hasSession())
			return;
		
		String uid;
		try {
			uid = authenticator.getUser().getLogin();
		} catch (Exception e) {
			// local test mode with pluto.
			uid = "Administrator";
		}
		nuxeoResource.makeSession(uid, prefs.getValue(NUXEO_HOST, ""), prefs.getValue(NUXEO_SECRET, ""));
		sessionRegistry.registerNewSession(portletSessionId, nuxeoResource);
		//String maxPageSize = prefs.getValue(NUXEO_MAX_PAGE_SIZE, "");
	}
}
