package servlet;

import java.io.IOException;

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

		    if (loginUser == null) {
		        response.sendRedirect("Login"); // ログイン画面へリダイレクト
		        return;
		    }

		    // タスクIDをリクエストから取得
		    String taskIdStr = request.getParameter("taskId");

		    if (taskIdStr == null || taskIdStr.isEmpty()) {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "タスクIDが指定されていません");
		        return;
		    }

		    int taskId;
		    try {
		        taskId = Integer.parseInt(taskIdStr);
		    } catch (NumberFormatException e) {
		        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "無効なタスクIDです");
		        return;
		    }

		    // データベースからタスクの詳細を取得
		    TasksDAO dao = new TasksDAO();
		    Tasks task = dao.findById(taskId, loginUser.getId());

		    if (task == null) {
		        response.sendError(HttpServletResponse.SC_NOT_FOUND, "指定されたタスクが見つかりません");
		        return;
		    }

		    // 取得したタスク詳細をリクエスト属性にセット
		    request.setAttribute("task", task);

		    // タスク詳細画面へフォワード
		    request.getRequestDispatcher("/WEB-INF/jsp/taskDetail.jsp").forward(request, response);
		}
	    

	    // POSTメソッド：削除または更新処理
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        request.setCharacterEncoding("UTF-8");

	

	        String taskIdStr1 = request.getParameter("taskId");
	        if (taskIdStr1 == null || taskIdStr1.isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "タスクIDが指定されていません");
	            return;
	        }


	        
	        
	    }
	}

