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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
        
     // lifeId パラメータの取得と検証
	    String lifeIdParam = request.getParameter("lifeId");
	    
	    if (lifeIdParam == null || lifeIdParam.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lifeId パラメータがありません");
	        return;
	    }

	    int lifeId;
	    try {
	        lifeId = Integer.parseInt(lifeIdParam);
	    } catch (NumberFormatException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lifeId が数値ではありません");
	        return;
	    }
        
        LifesDAO dao = new LifesDAO();
        
        dao.deleteLife(loginUser.getId(), lifeId);
        
        
   	 // 成功したらリダイレクトまたは画面遷移
           response.sendRedirect("LifeHackHistory");
	}
}
