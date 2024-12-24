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
 * Servlet implementation class taskinput
 */
@WebServlet("/TaskInput")
public class TaskInput extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // GETメソッド：タスク入力フォームの表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
    	// パラメータから日付を取得
        String selectedDate = request.getParameter("date");
        
        CategoriesDAO categoriesDAO = new CategoriesDAO();
   		
   		List<Categories> categoryList = categoriesDAO.get();  // DAOからデータを取得
   		request.setAttribute("categoryList", categoryList); 
   		request.setAttribute("selectedDate", selectedDate); 
        
   		
   		request.getRequestDispatcher("/WEB-INF/jsp/taskInput.jsp").forward(request, response);
    }
    // POSTメソッド：タスクの追加処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // フォームデータを取得
        String date1 = request.getParameter("dateInput1");
        String date2 = request.getParameter("dateInput2");
        String time = request.getParameter("appt-time");
        String category = request.getParameter("categorySelect"); // int型にする
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");

        int categoryId = Integer.parseInt(category);

        // 日付フォーマットの準備
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime startDate = LocalDateTime.parse(date1 + " " + time, dateTimeFormatter);
        LocalDateTime endDate = LocalDateTime.parse(date2 + " " + time, dateTimeFormatter);

        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();

        int outin = request.getParameter("switch") != null ? 1 : 0;

        TasksDAO dao = new TasksDAO();

        // 開始日から終了日までの期間を計算してタスクを作成
        LocalDateTime currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Tasks createdTask = dao.create(categoryId, accountId, taskName, memo, outin, currentDate);
            if (createdTask == null) {
                System.err.println("タスク作成失敗: " + currentDate.format(dateTimeFormatter));
            }
            currentDate = currentDate.plusDays(1); // 次の日付へ進む
        }

        // タスク一覧画面にリダイレクト
        response.sendRedirect("Task?date=" + date1);
    }
}