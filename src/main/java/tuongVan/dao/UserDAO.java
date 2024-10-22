package tuongVan.dao;

import java.util.List;

import tuongVan.entities.User;

public interface UserDAO {
	public User save(User user);
	public User update(User user);
	public boolean delete(int id);
	public User findById(int id);
	public List<User> findAll();
}
