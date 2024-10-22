package tuongVan.daoImp;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import tuongVan.dao.UserDAO;
import tuongVan.entities.User;

public class UserDAOImp implements UserDAO {
	private EntityManager entityManager;

	public UserDAOImp(EntityManager entityManager) {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	@Override
	public User save(User user) {
		EntityTransaction entityTransaction = null;

		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.persist(user);
			entityTransaction.commit();
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}

		return null;
	}

	@Override
	public User update(User user) {
		EntityTransaction entityTransaction = null;

		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			entityManager.merge(user);
			entityTransaction.commit();
			return user;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		EntityTransaction entityTransaction = null;

		try {
			entityTransaction = entityManager.getTransaction();
			entityTransaction.begin();
			User user = entityManager.find(User.class, id);
			if(user==null) {
				return false;
			}
			entityManager.remove(entityManager.contains(user)? user : entityManager.merge(user));
			entityTransaction.commit();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (entityTransaction != null && entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
		return false;
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return entityManager.find(User.class, id);
		
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		try {
			return entityManager.createQuery("from User").getResultList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
