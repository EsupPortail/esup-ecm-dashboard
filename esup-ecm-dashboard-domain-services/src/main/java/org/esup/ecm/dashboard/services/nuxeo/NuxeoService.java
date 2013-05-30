package org.esup.ecm.dashboard.services.nuxeo;

import java.util.List;

import org.esup.ecm.dashboard.dao.nexeo.NuxeoResource;
import org.esup.ecm.dashboard.domain.beans.EsupDocument;
import org.nuxeo.ecm.automation.client.model.FileBlob;


public interface NuxeoService {
	
	public List<EsupDocument> getListByPath(NuxeoResource nuxeoResource, String intranetPath) throws Exception;
	public List<EsupDocument> getListByQuery(NuxeoResource nuxeoResource, String query) throws Exception;
	public FileBlob getFile(NuxeoResource nuxeoResource, String uid) throws Exception;

}
