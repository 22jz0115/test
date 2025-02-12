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
import model.Accounts;
import model.PresetTasks;
import model.Presets;

@WebServlet("/PresetDetail")
public class PresetDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
		
		int presetId = Integer.parseInt(request.getParameter("presetId"));
		
		PresetsDAO dao = new PresetsDAO();
		Presets preset = dao.find(presetId);
		
		PresetTasksDAO presetTasksDao = new PresetTasksDAO();
		List<PresetTasks> presetTaskList = presetTasksDao.findPresetTask(presetId);
		
		request.setAttribute("presetTaskList", presetTaskList);
		request.setAttribute("preset", preset);
		request.setAttribute("presetId", presetId);
		request.getRequestDispatcher("/WEB-INF/jsp/presetDetail.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int presetId = Integer.parseInt(request.getParameter("presetId"));
		
		PresetsDAO dao = new PresetsDAO();
		boolean result = dao.deleteAllPreset(presetId);
		
		if (result != false) {
            response.sendRedirect("PresetList");
        } else {
        	response.sendRedirect("PresetDetail?presetId=" + presetId);
        }
	}
}
