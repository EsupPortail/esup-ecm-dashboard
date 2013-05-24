/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esup.ecm.dashboard.domain;

import java.io.Serializable;

import org.esup.ecm.dashboard.domain.beans.User;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public interface DomainService extends Serializable {
	
	/**
	 * @param uid
	 * @return a user.
	 */
	User getUser(String uid);

}
