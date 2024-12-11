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

@WebServlet("/TaskDetail")
public class TaskDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        String taskIdStr = request.getParameter("taskId");
        int taskId = Integer.parseInt(taskIdStr);

        TasksDAO tasksDAO = new TasksDAO();
        Tasks tasks = tasksDAO.findById(taskId, loginUser.getId());

        String selectedDate = tasks.getFormattedDate();
        request.setAttribute("task", tasks);

        int categoryId = tasks.getCategoryId();
        CategoriesDAO categoriesDAO = new CategoriesDAO();
        Categories category = categoriesDAO.find(categoryId);

        List<Categories> categoryList = categoriesDAO.get();
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("categorys", category);
        request.setAttribute("selectedDate", selectedDate);

        // 呼び出し元を取得してリクエストスコープに保存
        String from = request.getParameter("from");
        String taskHistoryId = request.getParameter("categoryId");
        System.out.println(from);
        if (from != null && taskHistoryId != null) {
            request.setAttribute("from", from);
            session.setAttribute("taskHistoryId", taskHistoryId);
            System.out.println(taskHistoryId);
        } else {
            session.removeAttribute("taskHistoryId"); // taskHistoryIdをセッションから削除
        }

        // タスク詳細画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/taskDetail.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String date = request.getParameter("dateInput");
        String time = request.getParameter("apptTime");
        String category = request.getParameter("categorySelect");
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");
        String taskid = request.getParameter("taskId");

        int taskId = Integer.parseInt(taskid);
        int categoryId = Integer.parseInt(category);

        String datetime = date + " " + time;
        LocalDateTime taskDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();

        int outin = request.getParameter("switch") != null ? 1 : 0;

        TasksDAO dao = new TasksDAO();
        boolean clear = dao.updateTask(taskId, categoryId, accountId, taskName, memo, outin, taskDateTime);

        // 呼び出し元の情報を取得
        String from = request.getParameter("from");
        String taskHistoryId = (String) session.getAttribute("taskHistoryId");
        System.out.println(taskHistoryId);

        if (clear) {
            // 成功時：呼び出し元にリダイレクト
            if ("TaskHistory".equals(from)) {
                session.setAttribute("taskHistoryId", taskHistoryId);  // セッションに保存
                response.sendRedirect("TaskHistory"); // `TaskHistory`へのリダイレクト
            } else {
                response.sendRedirect("Task?date=" + date);
            }
        } else {
            // 失敗時：同様に呼び出し元にリダイレクト
            if ("TaskHistory".equals(from)) {
                session.setAttribute("taskHistoryId", taskHistoryId);  // セッションに保存
                response.sendRedirect("TaskHistory");
            } else {
                response.sendRedirect("Task?date=" + date);
            }
        }
    }
}
