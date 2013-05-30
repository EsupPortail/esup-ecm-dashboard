package org.esup.ecm.dashboard.services.nuxeo;

import java.util.List;

import org.esup.ecm.dashboard.dao.DaoService;
import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.domain.beans.EsupDocument;
import org.nuxeo.ecm.automation.client.model.FileBlob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class NuxeoServiceImpl implements NuxeoService{
	
	@Autowired private DaoService daoService;
	
	public List<EsupDocument> getListByPath(NuxeoResource nuxeoResource, String intranetPath) throws Exception{
		return daoService.getListByPath(nuxeoResource, intranetPath);
		
	}
	
	public List<EsupDocument> getListByQuery(NuxeoResource nuxeoResource, String query) throws Exception{
		return daoService.getListByQuery(nuxeoResource, query);
	}
	
	public FileBlob getFile(NuxeoResource nuxeoResource, String uid) throws Exception{
		return daoService.getFile(nuxeoResource, uid);
	}

}
