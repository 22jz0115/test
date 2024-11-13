package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Task
 */
@WebServlet("/Task")
public class Task extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // パラメータから日付を取得
        String selectedDate = request.getParameter("date");
        
        

        // 日付をリクエスト属性に設定
        request.setAttribute("selectedDate", selectedDate);

        // 次のページに遷移
        request.getRequestDispatcher("/WEB-INF/jsp/task.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POSTリクエスト処理（必要に応じて実装）
    }
}
