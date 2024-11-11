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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedDate = request.getParameter("date");

        // 日付をリクエスト属性に設定
        request.setAttribute("selectedDate", selectedDate);

        // タスク入力画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/taskInput.jsp").forward(request, response);
    }


    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // フォームからのデータを取得
        String date = request.getParameter("selectedDate");
        String time = request.getParameter("appt-time");
        String category = request.getParameter("category");
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");

        // DAOを使ってデータベースにタスクを挿入
        TaskeInputDAO dao = new TaskeInputDAO();
        TaskInput newTaskInput = dao.create(date, time, category, taskName, memo);

        if (newTaskInput != null) {
            // 挿入が成功した場合、成功画面へリダイレクト
            response.sendRedirect("TaskSuccess.jsp");
        } else {
            // 挿入が失敗した場合、エラーメッセージを表示
            response.getWriter().println("タスクの追加に失敗しました。");
        }
    }
}