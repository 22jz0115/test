package servlet;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriesDAO;
import dao.TasksDAO;
import model.Accounts;
import model.Categories;
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
		    
		    String taskIdStr = request.getParameter("taskId");
		    int taskId = Integer.parseInt(taskIdStr);
		    
		 // パラメータから日付を取得
	        String selectedDate = request.getParameter("date");
	        
	        if (selectedDate == null || selectedDate.isEmpty()) {
	            LocalDate today = LocalDate.now();
	            selectedDate = today.toString(); // YYYY-MM-DD形式
	        }
	        
	        System.out.print(selectedDate);
	        TasksDAO tasksDAO = new TasksDAO();
	        
	          // アカウントIDに関連するタスク一覧を取得
	       Tasks task = tasksDAO.findById(taskId, loginUser.getId());
	        request.setAttribute("task", task);
		  
	        
	        CategoriesDAO categoriesDAO = new CategoriesDAO();

	        // カテゴリ一覧を取得してリクエストスコープにセット
	        Categories categoryList = categoriesDAO.find(task.getCategoryId());
	        request.setAttribute("categoryList", categoryList);

	      
		    // タスク詳細画面へフォワード
		    request.getRequestDispatcher("/WEB-INF/jsp/taskDetail.jsp").forward(request, response);
		}
	    

	    // POSTメソッド：削除または更新処理
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	doGet(request, response);
	

	        
	    }
	}

