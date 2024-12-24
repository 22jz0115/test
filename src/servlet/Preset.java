package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PresetTasksDAO;
import dao.PresetsDAO;
import dao.TasksDAO;
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

	    // 入力されたプリセットIDと日付を取得
	    String presetName = request.getParameter("preset_name");
	    int presetId = Integer.parseInt(presetName);

	    String startDate = request.getParameter("dateInput1");
	    String endDate = request.getParameter("dateInput2");

	    // プリセットタスクを取得
	    PresetTasksDAO dao = new PresetTasksDAO();
	    List<PresetTasks> presetData = dao.findPresetTask(presetId);

	    // 日付範囲を処理
	    LocalDate start = LocalDate.parse(startDate);
	    LocalDate end = LocalDate.parse(endDate);

	    // 結果メッセージの初期化
	    boolean allInserted = true;

	    // 日付範囲内でタスクを挿入
	    TasksDAO taskDao = new TasksDAO();
	    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
	        boolean result = taskDao.insertPresetTasks(presetData, accountId, date.toString());
	        if (!result) {
	            allInserted = false;
	        }
	    }

	    // 挿入結果の処理
	    if (allInserted) {
	        request.setAttribute("msg", "すべてのタスクが正常に挿入されました");
	        response.sendRedirect("/test/Task?date=" + startDate);
	    } else {
	        request.setAttribute("msg", "一部またはすべてのタスク挿入に失敗しました");
	        response.sendRedirect("/test/Preset?date=" + startDate);
	    }
	}


}
