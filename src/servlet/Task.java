package servlet;

import java.io.IOException;
import java.time.LocalDate;
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

@WebServlet("/Task")
public class Task extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        
        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        String selectedDate = request.getParameter("date");
        
        if (selectedDate == null || selectedDate.isEmpty()) {
            LocalDate today = LocalDate.now();
            selectedDate = today.toString();
        }

        request.setAttribute("selectedDate", selectedDate);
        
        TasksDAO taskDAO = new TasksDAO();
        List<Tasks> taskList  = taskDAO.findByCheckTask(loginUser.getId(), selectedDate);
        
        request.setAttribute("taskList", taskList);
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
