package org.esup.ecm.dashboard.dao.nuxeo;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoDaoServiceImpl;
import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.junit.Before;
import org.junit.Test;
import org.nuxeo.ecm.automation.client.model.Document;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;

public class NuxeoDaoServiceImplTest {
	NuxeoResource resource = null;
	
	@Before
	public void init() throws Exception{
		resource = new NuxeoResource();
		resource.makeSession("Administrator", "http://enor.univ-rennes1.fr:8080/nuxeo", "secret");
	}

	@Test
	public void getListByQuery() throws Exception {
		
		NuxeoDaoServiceImpl daoSerivce = new NuxeoDaoServiceImpl();
		String query = "SELECT * FROM Document WHERE ecm:path STARTSWITH '/default-domain/workspaces/devq2' ORDER BY dc:modified DESC";
		PaginableDocuments docs = daoSerivce.getListByQuery(resource, query, 0, 10);
		printDocs("queryList", docs);
	}
	
	@Test
	public void getListByPath() throws Exception {
		NuxeoDaoServiceImpl daoSerivce = new NuxeoDaoServiceImpl();
		PaginableDocuments docs = daoSerivce.getListByPath(resource, "/default-domain/workspaces/devq2/Jiyoung", 0, 10);
		printDocs("queryList", docs);
	}
	
	protected void printDocs(String queryTitle, PaginableDocuments docs){
		System.out.println("**** "+queryTitle+" ****");
		System.out.println("");
		
		int i = 0;
		if(docs != null)
		for (Document document : docs) {
			System.out.println(++i + " Title : " +document.getTitle());
		}
		System.out.println("-----------------------------------------");
		System.out.println("Total size : " + docs.getTotalSize());
		System.out.println("Page Index : " + docs.getPageIndex());
		System.out.println("Input Ref : " + docs.getInputRef());
		System.out.println("Page Count : " + docs.getPageCount());
		System.out.println("Page Size : " + docs.getPageSize());
		System.out.println("-----------------------------------------");
	}

}
