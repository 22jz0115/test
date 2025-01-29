package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.OptionsDAO;
import model.Accounts;
import model.Options;

@WebServlet("/Setting")
public class Setting extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Accounts loginUser = (Accounts) session.getAttribute("loginUser");
	    
        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
	    
	    int accountId = loginUser.getId();
	    
	    OptionsDAO dao = new OptionsDAO();
	    Options option = dao.find(accountId);
	    request.setAttribute("option", option);
		request.getRequestDispatcher("/WEB-INF/jsp/setting.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ("logout".equals(request.getParameter("action"))) {
            HttpSession session = request.getSession(false); // 既存のセッションを取得
            if (session != null) {
                session.invalidate(); // セッションを無効化
            }
            response.sendRedirect("Login"); // ログインページにリダイレクト
            return;
        }

        // 設定の保存処理
        String bgColor = request.getParameter("bgColor");
        String textColor = request.getParameter("textColor");
        String headerColor = request.getParameter("headerColor");

        // セッションに設定を保存
        HttpSession session = request.getSession();
        session.setAttribute("bgColor", bgColor);
        session.setAttribute("textColor", textColor);
        session.setAttribute("headerColor", headerColor);

        // 設定ページにリダイレクト
        response.sendRedirect("Setting");
	        
	 }
}