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

import dao.CategoriesDAO;
import dao.PresetTasksDAO;
import model.Categories;
import model.PresetTasks;

@WebServlet("/PresetTaskDetail")
public class PresetTaskDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int presetTaskId = Integer.parseInt(request.getParameter("presetTaskId"));
		
		PresetTasksDAO dao = new PresetTasksDAO();
		PresetTasks presetTask = dao.find(presetTaskId);
		
		CategoriesDAO CategoriesDAO = new CategoriesDAO();
        Categories categorys = CategoriesDAO.find(presetTask.getCategoryId());
   	    List<Categories> categoryList = CategoriesDAO.get();  // DAOからデータを取得
   	    request.setAttribute("categoryList", categoryList); 
        request.setAttribute("categorys", categorys);
		
		request.setAttribute("presetTask", presetTask);
		request.getRequestDispatcher("/WEB-INF/jsp/PresetTaskDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String time = request.getParameter("apptTime");
        String category = request.getParameter("categorySelect"); //int型にする
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");
        String taskId = request.getParameter("presetTaskId");
        
        int categoryId = Integer.parseInt(category);
        int presetTaskId = Integer.parseInt(taskId);
        int outin = request.getParameter("switch") != null ? 1 : 0;
        LocalTime taskTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
        
        PresetTasksDAO dao = new PresetTasksDAO();
        boolean result = dao.updatePresetTask(presetTaskId, categoryId, taskName, taskTime, memo, outin);
        
        if (result != false) {
            response.sendRedirect("PresetTaskDetail?presetTaskId=" + presetTaskId);  
        } else {
        	response.sendRedirect("PresetTaskDetail?presetTaskId=" + presetTaskId);
        }
	}

}
