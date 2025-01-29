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

@WebServlet("/Delete")
public class Delete extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
    	
    	// 画面遷移だけ
        request.getRequestDispatcher("/WEB-INF/jsp/Task.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        // タスクIDを取得
        String taskIdStr = request.getParameter("taskId");
        int taskId = Integer.parseInt(taskIdStr);

        // パラメータから日付と遷移元を取得
        String selectedDate = request.getParameter("selectedDate");
        String from = request.getParameter("from");

        // タスクを削除
        TasksDAO taskdao = new TasksDAO();
        taskdao.deleteTask(loginUser.getId(), taskId);

        // 遷移元に応じたリダイレクト
        if ("TaskHistory".equals(from)) {
            String categoryId = request.getParameter("categoryId");
            response.sendRedirect(request.getContextPath() + "/TaskHistory?categoryId=" + categoryId);
        } else {
            response.sendRedirect(request.getContextPath() + "/Task?date=" + selectedDate);
        }
    }
}
