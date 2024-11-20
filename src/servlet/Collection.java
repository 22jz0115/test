package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CollectionsDAO;
import model.Accounts;
import model.Collections;

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
	    HttpSession session = request.getSession();
	    Accounts loginUser = (Accounts) session.getAttribute("loginUser");

	    if (loginUser == null) {
	        // ログイン画面にリダイレクト
	        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	        return;
	    }
	    
	    

//	    MyBoxesDAO boxDao = new MyBoxesDAO();
//	    
//	    
//	    MyBoxs box = boxDao.create(loginUser.getId(), 1);


	    CollectionsDAO collectDao = new CollectionsDAO();
	    Collections collect = collectDao.find(1);

	    if (collect == null) {
	        request.setAttribute("errorMessage", "コレクションが見つかりませんでした。");
	        request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
	        return;
	    }

	    request.setAttribute("collection", collect);
	    request.getRequestDispatcher("/WEB-INF/jsp/collection.jsp").forward(request, response);
	}

	
	

}
