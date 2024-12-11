package servlet;

import java.io.IOException;
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

@WebServlet("/TaskHistory")
public class TaskHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        int categoryId = 0;

        String taskHistoryId = (String) session.getAttribute("taskHistoryId");
        System.out.println(taskHistoryId);

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

        List<Tasks> taskList = taskDAO.findByCategoryId(loginUser.getId(), categoryId);

        // 取得したデータをリクエストスコープに格納
        request.setAttribute("taskList", taskList);
        request.setAttribute("categoryName", categoryName);
        System.out.print(taskList.size());

        request.getRequestDispatcher("/WEB-INF/jsp/taskHistory.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
