package com.khallware.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Repo
{
	private static final Logger logger = LoggerFactory.getLogger(
		Repo.class);
	private static SessionFactory factory;

	public Repo() throws Exception
	{
		factory = new Configuration()
			.configure()
			.addAnnotatedClass(Item.class)
			.buildSessionFactory();
	}

	public void persist(Item item) throws Exception
	{
		Session session = factory.openSession();
		Transaction trx = session.beginTransaction();
		String id = ""+session.save(item);
		logger.info("new id: \"{}\"", id);
		trx.commit();
		session.close();
	}
}
