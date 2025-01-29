package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriesDAO;
import model.Accounts;
import model.Categories;

@WebServlet("/Category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
        
   		CategoriesDAO categoriesDAO = new CategoriesDAO();
   		
   		List<Categories> categoryList = categoriesDAO.get();  // DAOからデータを取得
   		request.setAttribute("categoryList", categoryList); 
  
   		request.getRequestDispatcher("/WEB-INF/jsp/category.jsp").forward(request, response);
   	}
   	
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		doGet(request, response);
   	}
}
