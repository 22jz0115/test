package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TasksDAO;
import model.Accounts;
import model.Tasks;

/**
 * Servlet implementation class TaskDetail
 */
@WebServlet("/TaskDetail")
public class TaskDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  HttpSession session = request.getSession();
          Accounts loginUser = (Accounts) session.getAttribute("loginUser");
    	// タスクIDをリクエストから取得
        String taskId = request.getParameter("taskId");
        
        // データベースからタスクの詳細を取得する処理（仮のメソッド）
       
        
        // 取得したタスク詳細をリクエストにセット
        TasksDAO dao = new TasksDAO();
        List<Tasks> taskList = dao.findByTaskList(loginUser.getId());
        request.setAttribute("taskList", taskList);
        
        // JSPへフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/taskDetail.jsp").forward(request, response);
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	request.setCharacterEncoding("UTF-8");
		
	  String action = request.getParameter("action");
      String taskIdStr = request.getParameter("id");

      if (taskIdStr == null || taskIdStr.isEmpty()) {
          response.sendError(HttpServletResponse.SC_BAD_REQUEST, "タスクIDが指定されていません");
          return;
      }

      int taskId = Integer.parseInt(taskIdStr);
      TasksDAO tasksDao = new TasksDAO();

      if ("delete".equals(action)) {
          boolean isDeleted = tasksDao.delete(taskId);
		  if (isDeleted) {
		      // 削除成功後、タスク一覧にリダイレクト
		      response.sendRedirect("TaskList");
		  } else {
		      // 削除失敗
		      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "タスク削除に失敗しました");
		  }
      }
    }
	}

