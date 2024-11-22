package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TasksDAO;
import model.Accounts;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		    Accounts loginUser = (Accounts) session.getAttribute("loginUser");
		    
		     
		    String taskIdStr = request.getParameter("taskId");
		    int taskId = Integer.parseInt(taskIdStr);
		    
		 // パラメータから日付を取得
	        String selectedDate = request.getParameter("selectedDate");
	        
	        if (selectedDate == null || selectedDate.isEmpty()) {
	            LocalDate today = LocalDate.now();
	            selectedDate = today.toString(); // YYYY-MM-DD形式
	        }
	        
	        TasksDAO taskdao = new TasksDAO();
	        taskdao.deleteTask(loginUser.getId(), taskId);
	        
	        request.getAttribute("selectedDate");
	   		request.getRequestDispatcher("/WEB-INF/jsp/Tasks.jsp").forward(request, response);
	   		
	}

}
