package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriesDAO;
import dao.PresetInputDAO;
import model.Accounts;
import model.Categories;

/**
 * Servlet implementation class PresetInput
 */
@WebServlet("/PresetInput")
public class PresetInput extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String preset_name = request.getParameter("preset_name");
		
		request.setAttribute("presetName", preset_name);
		
        CategoriesDAO categoriesDAO = new CategoriesDAO();
   		
   		List<Categories> categoryList = categoriesDAO.get();  // DAOからデータを取得
   		request.setAttribute("categoryList", categoryList); 
		
		request.getRequestDispatcher("/WEB-INF/jsp/presetInput.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
        // フォームデータを取得
		String preset_name = request.getParameter("preset_name");
        String time = request.getParameter("appt-time");
        String category = request.getParameter("categorySelect"); //int型にする
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");
        
    	HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();
        
        int outin = request.getParameter("switch") != null ? 1 : 0;
        
     // タスク情報をJSON形式で構築
        Map<String, Object> taskData = new HashMap<>();
        taskData.put("name", taskName);
        taskData.put("memo", memo);
        taskData.put("outin", outin);
        taskData.put("category_id", category);
        taskData.put("task_time", time);
        
        PresetInputDAO dao = new PresetInputDAO();
        if(dao.findByPresetName(preset_name, accountId) == null) {
        	dao.createPreset(preset_name, taskData, accountId);
        } else {
        	int presetId = dao.findByPresetId(preset_name, accountId);
        	boolean isUpdated = dao.addTaskToPreset(presetId, taskData);
        	
        	// 処理結果を確認してリダイレクト
            if (isUpdated) {
            	request.setAttribute("msg", "タスクの追加に成功しました。");
            } else {
                request.setAttribute("msg", "タスクの追加に失敗しました。");
            }
        }
        
        response.sendRedirect("/test/Preset");
	}

}
