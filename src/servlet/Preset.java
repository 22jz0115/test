package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.PresetsDAO;
import model.Accounts;
import model.PresetTasks;
import model.Presets;

/**
 * Servlet implementation class Priset
 */
@WebServlet("/Preset")
public class Preset extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
        
		request.getRequestDispatcher("/WEB-INF/jsp/priset.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();
        
        // リクエストからJSONデータを取得
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        // JSONデータをJavaオブジェクトに変換
        ObjectMapper objectMapper = new ObjectMapper();
        PresetTasks submissionData = objectMapper.readValue(sb.toString(), PresetTasks.class);

        // 受け取ったデータをログに出力
        System.out.println("プリセット名: " + submissionData.getName());
        System.out.println("日付: " + submissionData.getTaskTime());
	}

}
