package com.csu.asms.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HibernateSession {
	
	@Autowired
	private SessionFactory sessionFactory;

	public synchronized Session getHibernateSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public synchronized void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public synchronized SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
