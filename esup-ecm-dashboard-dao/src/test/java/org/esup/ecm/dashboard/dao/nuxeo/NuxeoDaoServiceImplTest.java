package org.esup.ecm.dashboard.dao.nuxeo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.esup.ecm.dashboard.dao.nexeo.NuxeoDaoServiceImpl;
import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.domain.beans.EsupDocument;
import org.junit.Before;
import org.junit.Test;
import org.nuxeo.ecm.automation.client.Constants;
import org.nuxeo.ecm.automation.client.Session;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.Documents;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;

public class NuxeoDaoServiceImplTest {
	NuxeoResource resource = null;
	private String defaultCondition = "AND (ecm:mixinType != 'HiddenInNavigation') AND (ecm:currentLifeCycleState != 'deleted')";
	@Before
	public void init() throws Exception{
		resource = new NuxeoResource();
		String columns = "{\"columns\": [\"dc:title\", \"dc:modified\", \"dc:creator\", \"dc:description\"] }";
		ObjectMapper mapper = new ObjectMapper();
		// read JSON from a file
		Map<String, Object> userInMap = mapper.readValue(columns, new TypeReference<Map<String, Object>>() {});
		resource.setColumns((ArrayList<String>) userInMap.get("columns"));
		resource.makeSession("Administrator", "http://enor.univ-rennes1.fr:8080/nuxeo", "secret");
	}
	

	@Test
	public void getListByQuery() throws Exception {
		
		NuxeoDaoServiceImpl daoSerivce = new NuxeoDaoServiceImpl();
		String query = "SELECT * FROM Document WHERE ecm:path STARTSWITH '/default-domain/workspaces' ORDER BY dc:modified DESC";
		List<org.esup.ecm.dashboard.domain.beans.EsupDocument> docs = daoSerivce.getListByQuery(resource, query);
		printDocs("queryList", docs);
	}
	
	@Test
	public void getListByPath() throws Exception {
		NuxeoDaoServiceImpl daoSerivce = new NuxeoDaoServiceImpl();
		String path = "/default-domain/workspaces/devq2/Jiyoung";
		List<org.esup.ecm.dashboard.domain.beans.EsupDocument> docs = daoSerivce.getListByPath(resource, path);
		printDocs("queryList", docs);
	}
	
	@Test
	public void testPageProvider() throws Exception {
		String query = "SELECT * FROM Document WHERE ecm:path STARTSWITH '/default-domain/workspaces/devq2' ORDER BY dc:modified DESC";
		pageProvider(resource, query, 1, 10);
	}
	
	private void pageProvider(NuxeoResource nuxeoResource, String query, int page, int pageSize) throws Exception {
		Session session = resource.getSession();
		PaginableDocuments docs = (PaginableDocuments) session.newRequest("Document.PageProvider").setHeader(
		        Constants.HEADER_NX_SCHEMAS, "dublincore").set("page", page).set("pageSize",pageSize).set("query", query).execute();
		
		System.out.println("size : " + docs.getTotalSize());
		printDocs("queryList", convert(docs, resource.getColumns()));
	}
	
	protected void printDocs(String queryTitle, List<EsupDocument> docs){
		System.out.println("**** "+queryTitle+" ****");
		System.out.println("");
		int i = 0;
		if(docs != null)
		for (org.esup.ecm.dashboard.domain.beans.EsupDocument document : docs) {
			//System.out.println("Type : " +document.getDocMap().get("type"));
			System.out.println(++i + " Title : " +document.getDocMap().get("title"));
			//System.out.println("Path : " +document.getDocMap().get("path"));
			//System.out.println("");
		}
		System.out.println("size : " + docs.size());
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
