package ge.edu.freeuni.taxi.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFactory {

	public static EntityManagerFactory EMF = null;

	public synchronized static EntityManager createEM() {
		if (EMF == null) {
			EMF = Persistence.createEntityManagerFactory("freeuni_taxi");
		}
		return EMF.createEntityManager();
	}

	public synchronized static void close() {
		if (EMF != null) {
			EMF.close();
		}
	}
}
