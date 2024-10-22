package tuongVan.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import tuongVan.dao.UserDAO;
import tuongVan.daoImp.UserDAOImp;
import tuongVan.entities.User;
import tuongVan.utils.EntityFactoryManagerUtil;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityFactoryManagerUtil entityFactoryManagerUtil;
	private UserDAO userDAO;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		this.entityFactoryManagerUtil = new EntityFactoryManagerUtil();
		userDAO = new UserDAOImp(entityFactoryManagerUtil.getEntityManager());

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		entityFactoryManagerUtil.close();
		super.destroy();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action") != null ? request.getParameter("action") : "";

		switch (action) {
		case "new":
			showRegisterForm(request, response);
			break;
		case "delete":
			deleteUser(request, response);
			break;
		case "edit":
			showEditForm(request, response);
			break;

		default:
			listUser(request, response);
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action") != null ? request.getParameter("action") : "";

		switch (action) {
		case "insert":
			insertUser(request, response);
			break;
		case "update":
			updatetUser(request, response);
			break;
		default:
			listUser(request, response);
			break;
		}
	}

	private void showRegisterForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/add.jsp");
		dispatcher.forward(request, response);
	}
	
	 private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        User existingUser = this.userDAO.findById(id);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/edit.jsp");
	        request.setAttribute("user", existingUser);
	        dispatcher.forward(request, response);
	     }

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.delete(id);
		response.sendRedirect("users");
	}


	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<User> list = userDAO.findAll();
		System.out.println(list);
		request.setAttribute("listUser", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("views/user/list.jsp");
		dispatcher.forward(request, response);
	}

	private void insertUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id= Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User user=new User(id, name, email, country);
		
		ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
		Validator validator= factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		if(violations.isEmpty()) {
			this.userDAO.save(user);
			response.sendRedirect("users");
		}else {
			RequestDispatcher dispatcher=request.getRequestDispatcher("views/user/add.jsp");
			
			StringBuilder stringBuilder= new StringBuilder();
			violations.forEach(t -> {
				stringBuilder.append(t.getPropertyPath()+": " + t.getMessage());
				stringBuilder.append("<br/>");
			});
			request.setAttribute("user", user);
			request.setAttribute("errors", stringBuilder);
			dispatcher.forward(request, response);
		}
	}
	
	private void updatetUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User user=new User(name, email, country);
		
		ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
		Validator validator= factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		
		if(violations.isEmpty()) {
			this.userDAO.update(user);
			response.sendRedirect("users");
		}else {
			RequestDispatcher dispatcher=request.getRequestDispatcher("views/common/add.jsp");
			
			StringBuilder stringBuilder= new StringBuilder();
			violations.forEach(t -> {
				stringBuilder.append(t.getPropertyPath()+": " + t.getMessage());
				stringBuilder.append("<br/>");
			});
			request.setAttribute("user", user);
			request.setAttribute("errors", stringBuilder);
			dispatcher.forward(request, response);
		}
	}

	
	

}
