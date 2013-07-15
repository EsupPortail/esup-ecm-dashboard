package org.esup.ecm.dashboard.web.springmvc;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.services.nuxeo.NuxeoService;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 * The <CODE>ViewController</CODE> represents the view mode session scoped controller.<br>
 * It extends the AbastractBaseController to provide nuxeo session information to portlets.<br>
 * 
 * @author Jiyoung Park
 */
@Scope("session")
@Controller
@RequestMapping(value = "VIEW")
public class ViewController extends AbastractBaseController {
	
	@Autowired private NuxeoService nuxeoService;
	@Autowired private ViewSelector viewSelector;
	/**
	 * First access point<br>
	 * If no Preferences information are found, send the response to init.jsp.<br>
	 * Otherwise, NuxeoService's getListByQuery method will be invoked.
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    @RenderMapping
    public ModelAndView viewList(@RequestParam(required=false, defaultValue="0") int pageIndex, RenderRequest request, RenderResponse response) throws Exception{
    	
    	PortletPreferences prefs = request.getPreferences();
    	boolean preferencesFlag = checkPerferences(prefs);
    	
    	if(!preferencesFlag){
    		return new ModelAndView(viewSelector.getViewName(request, "init"), null);
    	}else{
    		NuxeoResource nuxeoResource = getNuxeoResourceFromPortletSession(request);
    		if(!nuxeoResource.hasSession()){
    			makeNuxeoSession(request,nuxeoResource);
    			makeColumns(request, nuxeoResource);
    		}
    		
    		ModelMap model = new ModelMap();
        	int pageSize = new Integer(prefs.getValue(NUXEO_MAX_PAGE_SIZE, null));
        	
        	PaginableDocuments docs = null;
        	docs = nuxeoService.getListByQuery(nuxeoResource, request.getPreferences().getValue(NXQL, ""), pageIndex, pageSize);
        	
        	if(docs.getPageCount() == 0){
        		String message = messageSource.getMessage("nuxeo.fail.execution.query", null, "", request.getLocale());
        		throw new Exception(message);
        	}
        	
        	model.put("docs", docs.list());
        	model.put("isuPortal", request.getPortalContext().getPortalInfo().contains("uPortal"));
        	model.put("columns", nuxeoResource.getColumns());
        	
        	model.put("totalSize", docs.getTotalSize());
        	model.put("pageIndex", docs.getPageIndex());
        	model.put("inputRef", docs.getInputRef());
        	model.put("pageCount", docs.getPageCount());
        	model.put("pageSize", docs.getPageSize());
        	model.put(NUXEO_HOST, prefs.getValue(NUXEO_HOST, ""));
        	
            return new ModelAndView(viewSelector.getViewName(request, "view"), model);
    	}
    }
    
	private boolean checkPerferences(PortletPreferences prefs){
		String nuxeoHost = prefs.getValue(NUXEO_HOST, "");
		String nxql = prefs.getValue(NXQL, "");
		String[] columns = prefs.getValues(NUXEO_COLUMNS, null);
		if(!(nuxeoHost.startsWith("http://") || nuxeoHost.startsWith("https://")))
			return false;
		if(columns == null)
			return false;
		if(!(nxql.toLowerCase().startsWith("select")))
			return false;
		return true;
	}
}
