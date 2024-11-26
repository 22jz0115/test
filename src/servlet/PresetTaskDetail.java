package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PresetTasksDAO;
import model.PresetTasks;

@WebServlet("/PresetTaskDetail")
public class PresetTaskDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int presetTaskId = Integer.parseInt(request.getParameter("presetTaskId"));
		
		PresetTasksDAO dao = new PresetTasksDAO();
		PresetTasks presetTask = dao.find(presetTaskId);
		
		// カテゴリー追加
		
		request.setAttribute("presetTask", presetTask);
		request.getRequestDispatcher("/WEB-INF/jsp/PresetTaskDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
