package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountsDAO;
import dao.OptionsDAO;
import model.Accounts;
/**
 * Servlet implementation class CreateUser
 */
@WebServlet("/CreateUser")
public class CreateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/jsp/shinki.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountsDAO dao = new AccountsDAO();
		
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("nickname");
		String email = request.getParameter("email");
		String pass = request.getParameter("password");
		String location = request.getParameter("prefecture");
		
		Accounts accounts = dao.create(name, email, pass, location);
		
		if (accounts != null) {
			// ログインしてトップページ（今回はVoD一覧）へリダイレクト
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", accounts);
			
			OptionsDAO optionDao = new OptionsDAO();
			optionDao.create(accounts.getId(), 0, "FFFFFF", "000000");
			
			response.sendRedirect("/test/Home");
		} else {
			// エラー時はエラーメッセージを追加し自分へ戻る
			request.setAttribute("msg", "ユーザの追加に失敗しました");
			doGet(request, response);
		}
	}

	
}
