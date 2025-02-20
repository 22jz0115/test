package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.AuthLogic;
import model.Accounts;

@WebServlet("/Logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // ログアウト処理
    	HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            // ログインユーザーがいない場合、ログイン画面へリダイレクト
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }else {
        	AuthLogic logic = new AuthLogic();
            logic.logout(session);
        }
        
        LocalDate today = LocalDate.now();
        
        ServletContext sc = getServletContext();
        // アプリケーションスコープに保存
        sc.setAttribute("lastCheckdDate", today);
        // ログアウト後にログインページへリダイレクト
        response.sendRedirect(request.getContextPath() + "/Login");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
