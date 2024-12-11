package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountsDAO;
import dao.LifesDAO;
import model.Accounts;
import model.Lifes;

/**
 * Servlet implementation class LifeHackHisotry
 */
@WebServlet("/LifeHackHistory")
public class LifeHackHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   HttpSession session = request.getSession();
	        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

	        if (loginUser == null) {
	            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	            return;
	        }


	        LifesDAO dao = new LifesDAO();
	        List<Lifes> lifeList;

	        

	       lifeList = dao.getByAccountId(loginUser.getId());
	       
	       AccountsDAO accountDAO = new AccountsDAO();
	        for (Lifes life : lifeList) {
	            Accounts account = accountDAO.find(life.getAccountId());
	            life.setAccount(account);  
	        }

	        request.setAttribute("lifeList", lifeList);
	        request.getRequestDispatcher("/WEB-INF/jsp/lifeHackHistory.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
