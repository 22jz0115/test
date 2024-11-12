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
    // GETメソッド：タスク入力フォームの表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/taskInput.jsp").forward(request, response);
    }

    // POSTメソッド：タスクの追加処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // フォームデータを取得
        String date = request.getParameter("selectedDate");
        String time = request.getParameter("appt-time");
        String category = request.getParameter("category");
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");

        // DAOを使ってデータベースにタスクを挿入
        TaskeInputDAO dao = new TaskeInputDAO();
        TaskInput newTaskInput = dao.create(date, time, category, taskName, memo);

        if (newTaskInput != null) {
            // 挿入が成功した場合、タスク一覧画面にリダイレクト
            response.sendRedirect("Task?date=" + date);  // 日付をクエリパラメータとして渡してリダイレクト
        } else {
            // 挿入が失敗した場合、エラーメッセージを表示
            response.getWriter().println("タスクの追加に失敗しました。");
        }
    }
}