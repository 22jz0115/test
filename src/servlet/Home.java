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
import logic.AuthLogic;
import model.Accounts;
import model.Tasks;

@WebServlet("/Home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            // ログインユーザーがいない場合、ログイン画面へリダイレクト
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
        
        
        LocalDate today = LocalDate.now();
        String selectedDate = today.toString(); // YYYY-MM-DD形式

        // タスクリストの取得
        TasksDAO dao = new TasksDAO();
        List<Tasks> taskList = dao.findByCheckTask(loginUser.getId(), selectedDate);
        request.setAttribute("taskList", taskList);
        
        System.out.println("HomeServletに渡された日付データ" + selectedDate);
        

        
    
        
   

        request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
        
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        AuthLogic logic = new AuthLogic();
        Accounts account = logic.login(email, pass);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", account);
           
            response.sendRedirect("/test/Home");
        } else {
            request.setAttribute("msg", "ログインに失敗しました");
            doGet(request, response);
            
        }
    }
}
