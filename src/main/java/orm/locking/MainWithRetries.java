package orm.locking;

import java.util.function.Function;

import jakarta.persistence.*;

public class MainWithRetries {

	private EntityManagerFactory factory;

	public MainWithRetries() {
		this.factory = Persistence.createEntityManagerFactory("jpa-pgsql");
	}

	public static void main(String[] args) {
		MainWithRetries m = new MainWithRetries();

		m.retriesOnConflict((em) -> {
			int anioActual = 2024;
			TypedQuery<NextNumber> query = em.createQuery(
					"from NextNumber where anio = :anioActual",
					NextNumber.class);
			query.setParameter("anioActual", anioActual);

			NextNumber l = query.getSingleResult();
			// System.out.println(l.recuperarSiguiente());
			return l.recuperarSiguiente();
		}, 5);
	}

	public int retriesOnConflict(Function<EntityManager, Integer> toExecute,
			int cantidadRetries) {
		int retries = 0;

		while (retries < cantidadRetries) {
			try {
				return runInTx(toExecute);
			} catch (OptimisticLockException e) {
				retries++;
			}
		}

		throw new RuntimeException(
				"Tx no puede completarse debido a un conflicto de concurrencia");
	}

	public int runInTx(Function<EntityManager, Integer> toExecute) {

		EntityManager em = factory.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			int v = toExecute.apply(em);

			tx.commit();

			return v;
		} catch (OptimisticLockException e) {
			tx.rollback();
			throw e;
		} catch (Exception e) {
			tx.rollback();
			if (e.getCause() != null && e.getCause().getClass()
					.equals(OptimisticLockException.class)) {
				throw (OptimisticLockException) e.getCause();
			}
			throw new RuntimeException(e);
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}
}
