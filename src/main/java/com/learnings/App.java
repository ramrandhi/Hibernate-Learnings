package com.learnings;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


/**
 * 
 *
 */
public class App {
	public static void main(String[] args) {
		

	    Alien alien = null;
//	    alien.setName("ram3");
//	    alien.setColor("orange");
	    
		
	    Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);


		SessionFactory sessionFac = con.buildSessionFactory();
		
		// Second Level caching
		
//		Session session1 = sessionFac.openSession();
//		session1.beginTransaction();
//		alien = (Alien) session1.get(Alien.class, 1);
//		System.out.println(alien);
//		session1.getTransaction().commit();
//		session1.close();
//		
//		Session session2 = sessionFac.openSession();
//		session2.beginTransaction();
//		alien = (Alien) session2.get(Alien.class, 1);
//		System.out.println(alien);
//		session2.getTransaction().commit();
//		session2.close();
		
		// second level caching with eh caching
		
		Session session1 = sessionFac.openSession();
		session1.beginTransaction();
		
		Query q1 = session1.createQuery("from Alien where id = 1");
		q1.setCacheable(true);
		alien = (Alien)q1.uniqueResult();
		System.out.println(alien);
		session1.getTransaction().commit();
		session1.close();
		 
		
		Session session2 = sessionFac.openSession();
		session2.beginTransaction();
		
		Query q2 = session2.createQuery("from Alien where id = 1");
		q2 .setCacheable(true);
		alien = (Alien)q2.uniqueResult();
		System.out.println(alien);
		session2.getTransaction().commit();
		session2.close();

	}
}
