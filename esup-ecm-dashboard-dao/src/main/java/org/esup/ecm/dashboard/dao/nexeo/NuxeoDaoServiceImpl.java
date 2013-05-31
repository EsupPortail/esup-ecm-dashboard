package org.esup.ecm.dashboard.dao.nexeo;

import org.esup.ecm.dashboard.dao.DaoService;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.springframework.stereotype.Service;
@Service
public class NuxeoDaoServiceImpl implements DaoService{
	private String defaultCondition = "AND (ecm:mixinType != 'HiddenInNavigation') AND (ecm:currentLifeCycleState != 'deleted')";
	
	public PaginableDocuments getListByPath(NuxeoResource nuxeoResource, String intranetPath, int page, int pageSize) throws Exception{
		Session session = nuxeoResource.getSession();
		Document root = (Document) session.newRequest(DocumentService.FetchDocument).set("value", intranetPath).execute();
		if(root != null){
			String query = "SELECT * FROM Document WHERE ecm:parentId = '"+root.getId()+"'" + defaultCondition + " ORDER BY dc:title";
			return getListByQuery(nuxeoResource, query, page, pageSize);
		}
		return null;
	}
	
	public PaginableDocuments getListByQuery(NuxeoResource nuxeoResource, String query, int page, int pageSize) throws Exception{
		Session session = nuxeoResource.getSession();
		return (PaginableDocuments)session
				.newRequest("Document.PageProvider")
				.setHeader(Constants.HEADER_NX_SCHEMAS, "*")
		        .set("page", page)
		        .set("pageSize",pageSize)
		        .set("query", query)
		        .execute();
	}
	
	public FileBlob getFile(NuxeoResource nuxeoResource, String fid) throws Exception{
		Session session = nuxeoResource.getSession();
		// Get the file document where blob was attached
		Document doc = (Document) session.newRequest(DocumentService.FetchDocument).setHeader(
		        Constants.HEADER_NX_SCHEMAS, "*").set("value", fid).execute();
		
		// get the file content property
		PropertyMap map = doc.getProperties().getMap("file:content");
		// get the data URL
		String path = map.getString("data");
		 
		// download the file from its remote location
		FileBlob blob = (FileBlob) session.getFile(path);
		return blob;
	}
	
}
