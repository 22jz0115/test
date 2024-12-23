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

/**
 * Servlet implementation class ChangeButton
 */
@WebServlet("/ChangeButton")
public class ChangeButton extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
	    Accounts loginUser = (Accounts) session.getAttribute("loginUser");
	    int accountId = loginUser.getId();
	    
	    String message = request.getParameter("message");
	    int messageNumber = Integer.parseInt(message);
	    try {
	    	OptionsDAO dao = new OptionsDAO();
	    	boolean isUpdated = dao.updateMessage(accountId, messageNumber);
	    	
	    	if (isUpdated) {
                // 更新成功の場合
	    		System.out.println("更新されました");
            } else {
                // 更新失敗の場合
                System.out.println("更新に失敗しました");
            }
	    } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング
            System.out.println("不正なリクエストです");
        }
	}

}
