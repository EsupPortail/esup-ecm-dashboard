package org.esup.ecm.dashboard.web.springmvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.services.nuxeo.NuxeoService;
import org.nuxeo.ecm.automation.client.jaxrs.util.IOUtils;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 * The <CODE>ViewController</CODE> represents the view mode session scoped controller.<br>
 * It extends the AbastractBaseController to provide nuxeo session information to portlets.<br>
 * 
 * @author jpark
 */
@Scope("session")
@Controller
@RequestMapping(value = "VIEW")
public class ViewController extends AbastractBaseController {
	
	@Autowired private NuxeoService nuxeoService;
	@Autowired private ViewSelector viewSelector;
    
	/**
	 * First access point<br>
	 * If no Preferences information is found, send the response to init.jsp.<br>
	 * Otherwise, NuxeoService's  getListByQuery method will be invoked.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RenderMapping
    public ModelAndView welcome(@RequestParam(required=false, defaultValue="0") int pageIndex, RenderRequest request, RenderResponse response) throws Exception {
    	NuxeoResource nuxeoResource = buildNuxeoSession(request);
    	
    	if(!nuxeoResource.hasSession()){
    		return new ModelAndView(viewSelector.getViewName(request, "init"), null);
    	}else{
    		ModelMap model = new ModelMap();
    		
    		PortletPreferences prefs = request.getPreferences();
        	int pageSize = new Integer(prefs.getValue(NUXEO_MAX_PAGE_SIZE, null));
        	
        	
    		PaginableDocuments docs = nuxeoService.getListByQuery(nuxeoResource, request.getPreferences().getValue(NXQL, ""), pageIndex, pageSize);
        	model.put("docs", docs.list());
        	model.put("isuPortal", request.getPortalContext().getPortalInfo().contains("uPortal"));
        	model.put("columns", nuxeoResource.getColumns());
        	
        	model.put("totalSize", docs.getTotalSize());
        	model.put("pageIndex", docs.getPageIndex());
        	model.put("inputRef", docs.getInputRef());
        	model.put("pageCount", docs.getPageCount());
        	model.put("pageSize", docs.getPageSize());
        	
            return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    	}
    }
    
    /**
     * 
     * Retrieve Nuxeo document list by folder path
     * 
     * @param intranetPath
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RenderMapping(params="action=list")
    public ModelAndView getListByPath(@RequestParam(required=false, defaultValue="0") int pageIndex, @RequestParam(required=false) String intranetPath, RenderRequest request, RenderResponse response) throws Exception {
    	ModelMap model = new ModelMap();
    	NuxeoResource nuxeoResource = buildNuxeoSession(request);
    	
    	PortletPreferences prefs = request.getPreferences();
    	int pageSize = new Integer(prefs.getValue(NUXEO_MAX_PAGE_SIZE, null));
    	PaginableDocuments docs = nuxeoService.getListByQuery(nuxeoResource, request.getPreferences().getValue(NXQL, ""), pageIndex, pageSize);
    	
    	model.put("docs", docs.list());
    	model.put("isuPortal", request.getPortalContext().getPortalInfo().contains("uPortal"));
    	model.put("columns", nuxeoResource.getColumns());
    	model.put("totalSize", docs.getTotalSize());
    	model.put("pageIndex", docs.getPageIndex());
    	model.put("inputRef", docs.getInputRef());
    	model.put("pageCount", docs.getPageCount());
    	model.put("pageSize", docs.getPageSize());
        return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    }
    
    /**
     * File Download
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ResourceMapping
    public void fileDown(ResourceRequest request, ResourceResponse response) throws Exception {
    	
    	NuxeoResource nuxeoResource = buildNuxeoSession(request);
    	
    	// this fid can be a id or a path of Nuxeo Documents
    	String fid = request.getParameter("fid");
    	FileBlob f = nuxeoService.getFile(nuxeoResource, fid);
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
    
}
