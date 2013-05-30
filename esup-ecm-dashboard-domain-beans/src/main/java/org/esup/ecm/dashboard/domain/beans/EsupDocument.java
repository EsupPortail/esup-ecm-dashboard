package org.esup.ecm.dashboard.domain.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.nuxeo.ecm.core.schema.utils.DateParser;

/**
 * 
 * @author jiyoung
 *
 */
public class EsupDocument {
	
	private final Logger logger = new LoggerImpl(this.getClass());
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
	private Map<String, String> docMap = new HashMap<String, String>();
	
	public Map<String, String> getDocMap() {
		return docMap;
	}
	
	public void setDescription(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("description", doc.getProperties().getString("dc:description"));
	}
	public void setSubjects(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("subjects", doc.getProperties().getString("dc:subjects"));
	}
	public void setRights(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("rights", doc.getProperties().getString("dc:rights"));
	}
	public void setSource(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("source", doc.getProperties().getString("dc:source"));
	}
	public void setCoverage(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("coverage", doc.getProperties().getString("dc:coverage"));
	}
	public void setCreated(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("created", sdf.format(DateParser.parseDate(doc.getProperties().getString("dc:created"))));
	}
	public void setModified(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("modified", sdf.format(DateParser.parseDate(doc.getProperties().getString("dc:modified"))));
	}
	public void setIssued(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("issued", sdf.format(DateParser.parseDate(doc.getProperties().getString("dc:issued"))));
	}
	public void setValid(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("valid", sdf.format(DateParser.parseDate(doc.getProperties().getString("dc:valid"))));
	}
	public void setExpired(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("expired", sdf.format(DateParser.parseDate(doc.getProperties().getString("dc:expired"))));
	}
	public void setFormat(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("format", doc.getProperties().getString("dc:format"));
	}
	public void setLanguage(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("language", doc.getProperties().getString("dc:language"));
	}
	public void setCreator(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("creator", doc.getProperties().getString("dc:creator"));
	}
	public void setContributors(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("contributors", doc.getProperties().getString("dc:contributors"));
	}
	public void setLastContributor(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("lastContributor", doc.getProperties().getString("dc:lastContributor"));
	}
	public void setNature(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("nature", doc.getProperties().getString("dc:nature"));
	}
	public void setPublisher(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc) {
		this.docMap.put("publisher", doc.getProperties().getString("dc:publisher"));
	}
	
	public void setData(org.nuxeo.ecm.automation.client.jaxrs.model.Document doc, ArrayList<String> columns){
		this.docMap.put("title", doc.getTitle());
		this.docMap.put("path", doc.getPath());
		this.docMap.put("type", doc.getType());
		this.docMap.put("id", doc.getId());
		this.docMap.put("icon", doc.getProperties().getString("common:icon"));
		if(doc.getType().equals("File") || doc.getType().equals("File")){
			this.docMap.put("fileName", doc.getProperties().getString("file:filename"));
		}
		
		for(String column : columns){
			String mathodName = "set" + WordUtils.capitalize(column);
			if(!mathodName.equals("setTitle"))
			try {
				getClass().getMethod(mathodName, org.nuxeo.ecm.automation.client.jaxrs.model.Document.class).invoke(this, doc);
			} catch (Exception e) {
				logger.error(e.getMessage());
			} 
		}
	}

}
