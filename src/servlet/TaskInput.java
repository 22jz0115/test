package servlet;

import java.io.IOException;
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
 * Servlet implementation class taskinput
 */
@WebServlet("/TaskInput")
public class TaskInput extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    // GETメソッド：タスク入力フォームの表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
	CategoriesDAO categoriesDAO = new CategoriesDAO();
   		
   		List<Categories> categoryList = categoriesDAO.get();  // DAOからデータを取得
   		request.setAttribute("categoryList", categoryList); 
   		
   		 request.getRequestDispatcher("/WEB-INF/jsp/taskInput.jsp").forward(request, response);
    }

    // POSTメソッド：タスクの追加処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
        // フォームデータを取得
        String date = request.getParameter("selectedDate");
        String time = request.getParameter("appt-time");
        String category = request.getParameter("category");
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");
        
        
    	HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();
        

        // DAOを使ってデータベースにタスクを挿入
        TasksDAO dao = new TasksDAO();
        Tasks TaskInput = dao.create();

        if (newTaskInput != null) {
            // 挿入が成功した場合、タスク一覧画面にリダイレクト
            response.sendRedirect("Task?date=" + date);  // 日付をクエリパラメータとして渡してリダイレクト
        } else {
            // 挿入が失敗した場合、エラーメッセージを表示
            response.getWriter().println("タスクの追加に失敗しました。");
        }
    }
}