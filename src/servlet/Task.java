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
        try {
            // リクエストからtaskIdとcheckの値を取得
            String taskIdParam = request.getParameter("taskId");
            String checkParam = request.getParameter("check");
            
            // taskIdとcheckがnullまたは空でないことを確認
            if (taskIdParam != null && !taskIdParam.isEmpty() && checkParam != null && !checkParam.isEmpty()) {
                int taskId = Integer.parseInt(taskIdParam);
                int check = Integer.parseInt(checkParam);

                // デバッグ用にログ出力
                System.out.println("受信したtaskId: " + taskId);
                System.out.println("受信したcheck: " + check);

                // タスク状態を更新
                TasksDAO taskDAO = new TasksDAO();
                boolean isUpdated = taskDAO.updateCheckTask(taskId, check);

                if (isUpdated) {
                    // 更新成功の場合
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.getWriter().write("タスクが更新されました");
                } else {
                    // 更新失敗の場合
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("タスクの更新に失敗しました");
                }
            } else {
                // パラメータが不正の場合
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("不正なリクエストです");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // エラーハンドリング
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("不正なリクエストです");
        }
    }
}
