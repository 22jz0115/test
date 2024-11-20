package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PresetsDAO;
import model.Accounts;
import model.Presets;

/**
 * Servlet implementation class Priset
 */
@WebServlet("/Preset")
public class Preset extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータから日付を取得
        String selectedDate = request.getParameter("date");
        // 日付をリクエスト属性に設定
        request.setAttribute("selectedDate", selectedDate);
        
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();
        
        PresetsDAO dao = new PresetsDAO();
        List<Presets> presetList = dao.get(accountId);
        request.setAttribute("presetList", presetList);
        
        List<String> presetNameList = dao.getName(accountId);
        String optionsString = String.join(",", presetNameList);
        request.setAttribute("jsonString", optionsString);
        
		request.getRequestDispatcher("/WEB-INF/jsp/priset.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
