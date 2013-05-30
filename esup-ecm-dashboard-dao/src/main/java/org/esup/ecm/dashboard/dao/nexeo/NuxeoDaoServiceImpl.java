package org.esup.ecm.dashboard.dao.nexeo;

import java.util.ArrayList;
import java.util.List;

import org.esup.ecm.dashboard.dao.DaoService;
import org.esup.ecm.dashboard.domain.beans.EsupDocument;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.adapters.DocumentService;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PropertyMap;
import org.springframework.stereotype.Service;
@Service
public class NuxeoDaoServiceImpl implements DaoService{

	private String defaultCondition = "AND (ecm:mixinType != 'HiddenInNavigation') AND (ecm:currentLifeCycleState != 'deleted')";
	
	public List<EsupDocument> getListByPath(NuxeoResource nuxeoResource, String intranetPath) throws Exception{
		Session session = nuxeoResource.getSession();
		Document root = (Document) session.newRequest(DocumentService.FetchDocument).set("value", intranetPath).execute();
		if(root != null){
			String query = "SELECT * FROM Document WHERE ecm:parentId = '"+root.getId()+"'" + defaultCondition + " ORDER BY dc:title";
			return getListByQuery(nuxeoResource, query);
		}
		return null;
	}
	
	public List<EsupDocument> getListByQuery(NuxeoResource nuxeoResource, String query) throws Exception{
		Session session = nuxeoResource.getSession();
		Documents docs = (Documents) session.newRequest(DocumentService.Query).setHeader(
		        Constants.HEADER_NX_SCHEMAS, "dublincore").set("query", query).execute();
		return convert(docs, nuxeoResource.getColumns());
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
	
	
	private List<EsupDocument> convert(Documents docs, ArrayList<String> columns){
		
		List<EsupDocument> docList = new ArrayList<EsupDocument>();
		for(Document doc : docs){
			EsupDocument esupDoc = new EsupDocument();
			esupDoc.setData(doc, columns);
			docList.add(esupDoc);
		}
		return docList;
	}

}
