package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PresetTasksDAO;

/**
 * Servlet implementation class PresetTaskDelete
 */
@WebServlet("/PresetTaskDelete")
public class PresetTaskDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int presetTaskId = Integer.parseInt(request.getParameter("taskId"));
		int presetId = Integer.parseInt(request.getParameter("presetId"));
		
		PresetTasksDAO dao = new PresetTasksDAO();
		boolean result = dao.deletePresetTask(presetTaskId);
		
		if (result != false) {
            System.out.println("okokokok");
            response.sendRedirect("PresetDetail?presetId=" + presetId);  

        } else {
        	System.out.println("ngngngng");
        	response.sendRedirect("PresetDetail?presetId=" + presetId);
        }
	}

}
