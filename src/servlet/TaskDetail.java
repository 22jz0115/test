package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
	       
	        
	        
	       
	        TasksDAO tasksDAO = new TasksDAO();
	          // アカウントIDに関連するタスク一覧を取得
	       Tasks tasks = tasksDAO.findById(taskId, loginUser.getId());
	       System.out.print("タスク確認"+ tasks.getTaskName());
	       String selectedDate = tasks.getFormattedDate();
          request.setAttribute("task", tasks);
        
          int categoryId = tasks.getCategoryId();
       // DAOを使ってカテゴリーを取得
          CategoriesDAO CategoriesDAO = new CategoriesDAO();
          Categories categorys = CategoriesDAO.find(categoryId);
          
     		
     	  List<Categories> categoryList = CategoriesDAO.get();  // DAOからデータを取得
     	  request.setAttribute("categoryList", categoryList); 
       
          // リクエストスコープに格納
          request.setAttribute("categorys", categorys);
          System.out.println(categorys.getCategoryName());
          
         
          request.setAttribute("selectedDate", selectedDate);
          System.out.println("selectedDate: " + selectedDate);
          

         
		    // タスク詳細画面へフォワード
		    request.getRequestDispatcher("/WEB-INF/jsp/taskDetail.jsp").forward(request, response);
		}
	    

	    // POSTメソッド：削除または更新処理
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    	request.setCharacterEncoding("UTF-8");
	        // フォームデータを取得
	        String date = request.getParameter("dateInput");
	        
	        String time = request.getParameter("apptTime");
	        
	        String category = request.getParameter("categorySelect"); //int型にする
	        String taskName = request.getParameter("taskName");
	        String memo = request.getParameter("story");
	        String taskid = request.getParameter("taskId");
	        System.out.println(date);
	        System.out.println(time);
	        System.out.println(category);
	        System.out.println(taskName);
	        System.out.println(memo);
	        System.out.println(taskid);
	        
	        
	        int taskId = Integer.parseInt(taskid);
	        int categoryId = Integer.parseInt(category);
	        
	        String datetime = date + " " + time;
	        LocalDateTime taskDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
	        
	    	HttpSession session = request.getSession();
	        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
	        int accountId = loginUser.getId();
	        
	        int outin = request.getParameter("switch") != null ? 1 : 0;
	        

	        // DAOを使ってデータベースにタスクを挿入
	        TasksDAO dao = new TasksDAO();
	        boolean clear =  dao.updateTask(taskId, categoryId, accountId, taskName, memo, outin, taskDateTime);

	        if (clear != false) {
	            // 挿入が成功した場合、タスク一覧画面にリダイレクト
	            response.sendRedirect("Task?date=" + date);  // 日付をクエリパラメータとして渡してリダイレクト

	            System.out.println("okokokok");

	        } else {
	            // 挿入が失敗した場合、エラーメッセージを表示
	        	System.out.println("ngngngng");
	        	response.sendRedirect("Task?date=" + date);
	        }
	    }
	}

