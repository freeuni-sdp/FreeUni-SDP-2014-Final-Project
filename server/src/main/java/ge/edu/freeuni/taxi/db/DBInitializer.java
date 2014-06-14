package ge.edu.freeuni.taxi.db;

import ge.edu.freeuni.taxi.User;

import javax.persistence.EntityManager;

public class DBInitializer {

	public static void main(String [] args) {
		EntityManager em = EMFactory.createEM();

		User user = new User();
		user.setName("Admin");
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();

		EMFactory.close();
	}
}
