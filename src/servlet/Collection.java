package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoxsDAO;
import model.Accounts;
import model.MyBoxs;

/**
 * Servlet implementation class collection
 */
@WebServlet("/Collection")
public class Collection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 HttpSession session = request.getSession();
	     Accounts loginUser = (Accounts) session.getAttribute("loginUser");

	        if (loginUser == null) {
	            // ログインユーザーがいない場合、ログイン画面へリダイレクト
	            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	            return;
	        }
		
		MyBoxsDAO boxDao = new MyBoxsDAO();
		MyBoxs box = boxDao.create(loginUser.getId(), 1);
		
		request.getRequestDispatcher("/WEB-INF/jsp/collection.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);

	}
	
	

}
