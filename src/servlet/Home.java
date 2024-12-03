package servlet;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountsDAO;
import dao.MyBoxesDAO;
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
        
        
        MyBoxesDAO boxDao = new MyBoxesDAO();
        AccountsDAO Accountdao = new AccountsDAO();
        LocalDate now = LocalDate.now();  // 現在の日付を取得

        int getCollectionMonth = 0;  // 初期値を設定
        
        LocalDateTime updateDate = account.getUpdateDate();  // updateDate を取得

        if (updateDate != null) {
            // updateDate の月を取得
            getCollectionMonth = updateDate.getMonthValue();
            System.out.println("Login user's updateDate month: " + getCollectionMonth);
        } else {
            System.err.println("loginUser's updateDate is null.");
        }
        
        if (getCollectionMonth == 0 || now.getMonthValue() != getCollectionMonth) {
       	 // 最後にチェックした月が異なる場合、または getCollectionMonth が 0 の場合
       	
       
           int outCheck = 0;
           int inCheck = 0;

           int comperTask = 10;  // 比較基準となるタスク数
           int comperParsent = 10;  // 比較基準となる達成率

           // タスクリストを取得
           TasksDAO dao = new TasksDAO();
           List<Tasks> taskList = dao.findByCurrentMonth(account.getId(), getCollectionMonth);

           // タスクリストを反復処理
           for (Tasks task : taskList) {
               if (task.getOutin() == 1) {  // 出て行くタスク
           
                   if (task.getCheck() == 1) {
                       outCheck++;
                   }
               } else {  // 入ってくるタスク
            
                   if (task.getCheck() == 1) {
                       inCheck++;
                   }
               }
           }

           // タスクチェック率の計算
           int percentageFromDatabase1 = 0;
           if (inCheck + outCheck != 0) {
               percentageFromDatabase1 = (int) (((double) (inCheck + outCheck) / taskList.size()) * 100);
           }

           // タスク数と達成率に基づいて新しいコレクションを作成
           if (taskList.size() >= comperTask && percentageFromDatabase1 >= comperParsent) {
           	System.out.println("タスク数と達成率に基づいての所まで");
               boxDao.create(account.getId(), getCollectionMonth);  // 新しいコレクションを作成
            
           }
       }
        
        boolean isUpdated = Accountdao.updateLoginTime(account.getId());
        
        if (isUpdated) {
		    System.out.println("ログイン時刻が更新されました。");
		} else {
		    System.out.println("ログイン時刻の更新に失敗しました。");
		}
        

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
