package servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriesDAO;
import dao.PresetTasksDAO;
import dao.PresetsDAO;
import model.Accounts;
import model.Categories;
import model.Presets;

/**
 * Servlet implementation class PresetInput
 */
@WebServlet("/PresetInput")
public class PresetInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
        
        int accountId = loginUser.getId();
		
        CategoriesDAO dao = new CategoriesDAO();
   		
   		List<Categories> categoryList = dao.get();  // DAOからデータを取得
   		request.setAttribute("categoryList", categoryList); 
   		
   		PresetsDAO presetDao = new PresetsDAO();
        List<String> presetList = presetDao.getName(accountId);
        String presetName = String.join(",", presetList);
        request.setAttribute("presetList", presetName);
		
		request.getRequestDispatcher("/WEB-INF/jsp/presetInput.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        
    	HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();
		
        // フォームデータを取得
		String preset_name = request.getParameter("presetTask_name");
		
		PresetsDAO dao = new PresetsDAO();
		Presets preset =	dao.findByPreset(preset_name, accountId);
		if(preset == null) {
			dao.create(preset_name, accountId);
		}
		
        String time = request.getParameter("appt-time");
        LocalTime taskTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        String category = request.getParameter("categorySelect"); //int型にする
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");

        int categoryId = Integer.parseInt(category);
        int outin = request.getParameter("switch") != null ? 1 : 0;
        
        int presetId = 	dao.findByPresetId(preset_name, accountId);  
        
        PresetTasksDAO taskDao = new PresetTasksDAO();
        taskDao.createPresetTask(presetId, categoryId, taskName, taskTime, memo, outin);
        
        response.sendRedirect("/test/Preset");
	}
}
