package com.learnings.HibernateInitials;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.learnings.HibernateInitials.Entity.Laptop;
import com.learnings.HibernateInitials.Entity.Student;

/**
 * 
 *
 */
public class App {
	public static void main(String[] args) {
		Student student = new Student();
		Laptop laptop1 = new Laptop();
		Laptop laptop2 = new Laptop();

		laptop1.setModel("Dell");
		laptop2.setModel("HP");

		student.setName("Ram");
		student.setMarks(150);

		// Set the relationship on both sides
		student.getLaptop().add(laptop1);
		student.getLaptop().add(laptop2);

		laptop1.getStudent().add(student);
		laptop2.getStudent().add(student);

		Configuration con = new Configuration().configure().addAnnotatedClass(Student.class)
		        .addAnnotatedClass(Laptop.class);

		SessionFactory sessionFac = con.buildSessionFactory();
		Session session = sessionFac.openSession();

		Transaction txn = null;
		try {
		    txn = session.beginTransaction();
		    session.save(student); // This will also save associated laptops due to cascade
		    txn.commit();
		} catch (Exception e) {
		    if (txn != null) {
		        txn.rollback();
		    }
		    e.printStackTrace();
		} finally {
		    session.close();
		}

	}
}
