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
 * Servlet implementation class Task
 */
@WebServlet("/Task")
public class Task extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	  // セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
    	
    	// パラメータから日付を取得
        String selectedDate = request.getParameter("date");
        
        System.out.print(selectedDate);
        
        

        // 日付をリクエスト属性に設定
        request.setAttribute("selectedDate", selectedDate);
        
        TasksDAO taskDAO = new TasksDAO();
        
        List<Tasks> taskList  = taskDAO.findByCheckTask(loginUser.getId() , selectedDate);
        
        request.setAttribute("taskList", taskList);
        
        System.out.print("taskの個数" + taskList.size());

        // 次のページに遷移
        request.getRequestDispatcher("/WEB-INF/jsp/task.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POSTリクエスト処理（必要に応じて実装）
    	 try {
             // リクエストからパラメータを取得
             int taskId = Integer.parseInt(request.getParameter("taskId"));
             int check = Integer.parseInt(request.getParameter("check"));

             // タスク状態を更新
             TasksDAO taskDAO = new TasksDAO();
             boolean isUpdated = taskDAO.updateCheckTask(taskId, check);

             if (isUpdated) {
                 response.setStatus(HttpServletResponse.SC_OK);
                 response.getWriter().write("タスクが更新されました");
             } else {
                 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                 response.getWriter().write("タスクの更新に失敗しました");
             }
         } catch (Exception e) {
             e.printStackTrace();
             response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             response.getWriter().write("不正なリクエストです");
         }
    	
    }
}
