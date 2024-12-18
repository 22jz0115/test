package servlet;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

@WebServlet("/TaskHistory")
public class TaskHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        int categoryId = 0;
        String taskHistoryId = (String) session.getAttribute("taskHistoryId");
        TasksDAO taskDAO = new TasksDAO();
        CategoriesDAO categoryDAO = new CategoriesDAO();
        Categories categoryName = null;

        if (taskHistoryId != null) {
            categoryId = Integer.parseInt(taskHistoryId);
            categoryName = categoryDAO.find(categoryId);
            session.removeAttribute("taskHistoryId"); // セッションから削除
        } else {
            categoryId = Integer.parseInt(request.getParameter("categoryId"));
            categoryName = categoryDAO.find(categoryId);
        }

     // タスクをカテゴリごとに取得
        List<Tasks> taskList = taskDAO.findByCategoryId(loginUser.getId(), categoryId);

        // 日付ごとにタスクをグループ化
        Map<String, List<Tasks>> groupedTasks = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");

        for (Tasks task : taskList) {
            // 修正: getCreatedDate() を getCreatedAt() に変更
            String formattedDate = task.getCreatedAt() != null ? task.getCreatedAt().format(formatter) : "不明な日付";

            // グループ化してリストに追加
            groupedTasks.computeIfAbsent(formattedDate, k -> new ArrayList<>()).add(task);
        }

        // グループ化されたタスクをリクエストにセット
        request.setAttribute("groupedTasks", groupedTasks);
        request.setAttribute("categoryName", categoryName);

        // JSPに転送
        request.getRequestDispatcher("/WEB-INF/jsp/taskHistory.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
