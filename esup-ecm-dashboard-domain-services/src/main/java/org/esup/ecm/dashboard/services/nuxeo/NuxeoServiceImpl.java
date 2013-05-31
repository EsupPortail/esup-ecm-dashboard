package org.esup.ecm.dashboard.services.nuxeo;

import org.esup.ecm.dashboard.dao.DaoService;
import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.nuxeo.ecm.automation.client.model.PaginableDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NuxeoServiceImpl implements NuxeoService{
	
	@Autowired private DaoService daoService;
	
	public PaginableDocuments getListByPath(NuxeoResource nuxeoResource, String intranetPath, int page, int pageSize) throws Exception{
		return daoService.getListByPath(nuxeoResource, intranetPath, page, pageSize);
	}
	
	public PaginableDocuments getListByQuery(NuxeoResource nuxeoResource, String query, int page, int pageSize) throws Exception{
		return daoService.getListByQuery(nuxeoResource, query, page, pageSize);
	}
	
	public FileBlob getFile(NuxeoResource nuxeoResource, String uid) throws Exception{
		return daoService.getFile(nuxeoResource, uid);
	}

}
