package org.esup.ecm.dashboard.services.nuxeo;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;


public interface NuxeoService {
	
	public PaginableDocuments getListByPath(NuxeoResource nuxeoResource, String intranetPath, int page, int pageSize) throws Exception;
	public PaginableDocuments getListByQuery(NuxeoResource nuxeoResource, String query, int page, int pageSize) throws Exception;
	public FileBlob getFile(NuxeoResource nuxeoResource, String uid) throws Exception;
}
