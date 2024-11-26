package servlet;

import java.io.IOException;
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
        
        String presetName = request.getParameter("preset_name");
		int presetId = Integer.parseInt(presetName);
		
		String date = request.getParameter("dateInput");
		
		PresetTasksDAO dao = new PresetTasksDAO();
        List<PresetTasks> presetData = dao.findPresetTask(presetId);
        
        TasksDAO taskDao = new TasksDAO();
        boolean result = taskDao.insertPresetTasks(presetData, accountId, date);
        
        if (result) {
        	request.setAttribute("msg", "タスクが挿入されました");
        	response.sendRedirect("/test/Task?date=" + date);
        } else {
        	request.setAttribute("msg", "タスクの挿入に失敗しました");
        	response.sendRedirect("/test/Preset?date=" + date);
        }
	}

}
