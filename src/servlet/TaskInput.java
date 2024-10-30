package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class taskinput
 */
@WebServlet("/TaskInput")
public class TaskInput extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String selectedDate = request.getParameter("date");
	        
	     

	        // 日付をリクエスト属性に設定
	     request.setAttribute("selectedDate", selectedDate);
		

		request.getRequestDispatcher("/WEB-INF/jsp/taskInput.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		

	}
}
