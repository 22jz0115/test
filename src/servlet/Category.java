package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoriesDAO;
import model.Categories;

/**
 * Servlet implementation class category
 */
@WebServlet("/Category")
public class Category extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
   	/**
   	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   	 */
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		// TODO Auto-generated method stub
   		
   		
   		CategoriesDAO categoriesDAO = new CategoriesDAO();
   		
   		List<Categories> categoryList = categoriesDAO.get();  // DAOからデータを取得
   		request.setAttribute("categoryList", categoryList); 
  
		
   		
   		request.getRequestDispatcher("/WEB-INF/jsp/category.jsp").forward(request, response);
   	}

   	/**
   	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   	 */
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		doGet(request, response);

   	}
   	
}
