package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TaskeInputDAO;

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
	
	@WebServlet("/TaskInput")
	public class TaskInputServlet extends HttpServlet {
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // フォームからのデータを取得
	        String date = request.getParameter("selectedDate");
	        String time = request.getParameter("appt-time");
	        String category = request.getParameter("category");
	        String taskName = request.getParameter("taskName");
	        String memo = request.getParameter("story");

	        // DAOを使ってデータベースにタスクを挿入
	        TaskeInputDAO dao = new TaskeInputDAO();
	        boolean isInserted = dao.insertTask(date, time, category, taskName, memo);

	        if (isInserted) {
	            // 挿入が成功した場合、確認画面へリダイレクト
	            response.sendRedirect("TaskSuccess.jsp");
	        } else {
	            // 挿入が失敗した場合、エラーメッセージを表示
	            response.getWriter().println("タスクの追加に失敗しました。");
	        }
	    }
	}
	
	
	
	
}
