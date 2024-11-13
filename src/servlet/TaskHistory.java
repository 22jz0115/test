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

/**
 * Servlet implementation class TaskHistory
 */
@WebServlet("/TaskHistory")
public class TaskHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        int categoryId = Integer.parseInt(request.getParameter("categoryId")); 
        
       TasksDAO taskDAO = new TasksDAO();
       
       CategoriesDAO categoryDAO = new CategoriesDAO();
       Categories categoryName = categoryDAO.find(categoryId);
       
        List<Tasks> taskList = taskDAO.findByCategoryId(loginUser.getId(), categoryId);
        
        // 取得したデータをリクエストスコープに格納
        request.setAttribute("taskList", taskList);
        request.setAttribute("categoryName", categoryName);
        System.out.print(taskList.size());
		        
		
		request.getRequestDispatcher("/WEB-INF/jsp/taskHistory.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
