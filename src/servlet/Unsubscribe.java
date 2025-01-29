package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubscriptionsDAO;
import model.Accounts;

@WebServlet("/Unsubscribe")
public class Unsubscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
	    
	    int accountId = loginUser.getId();

	    try {
	        // サブスクリプション情報をデータベースから削除
	        SubscriptionsDAO dao = new SubscriptionsDAO();
	        boolean success = dao.deleteSubscribe(accountId); // accountIdとendpointで削除

	        if (success) {
	            // 成功レスポンス
	            response.setStatus(HttpServletResponse.SC_OK);
	            response.getWriter().write("Unsubscription successful");
	        } else {
	            // データベースエラー
	            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	            response.getWriter().write("Failed to unsubscribe");
	        }
	    } catch (Exception e) {
	        // エラーハンドリング
	        e.printStackTrace(); // ログにエラーを出力
	        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        response.getWriter().write("Error: " + e.getMessage());
	    }
	}
}