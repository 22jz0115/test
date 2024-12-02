package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CollectionsDAO;
import dao.MyBoxesDAO;
import dao.TasksDAO;
import model.Accounts;
import model.Collections;
import model.MyBoxs;
import model.Tasks;

@WebServlet("/Collection")
public class Collection extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        // ログインユーザーが null の場合、ログイン画面にリダイレクト
        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        MyBoxesDAO boxDao = new MyBoxesDAO();
        LocalDate now = LocalDate.now();  // 現在の日付を取得

        int getCollectionMonth = 0;  // 初期値を設定


            LocalDateTime updateDate = loginUser.getUpdateDate();  // updateDate を取得

        if (updateDate != null) {
            // updateDate の月を取得
            getCollectionMonth = updateDate.getMonthValue();
            System.out.println("Login user's updateDate month: " + getCollectionMonth);
        } else {
            System.err.println("loginUser's updateDate is null.");
        }
        

        // 最後にチェックした月が異なる場合、または getCollectionMonth が 0 の場合
        
            int outCheck = 0;
            int inCheck = 0;

            int comperTask = 10;  // 比較基準となるタスク数
            int comperParsent = 10;  // 比較基準となる達成率

            // タスクリストを取得
            TasksDAO dao = new TasksDAO();
            List<Tasks> taskList = dao.findByCurrentMonth(loginUser.getId(), getCollectionMonth);

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
            if (taskList.size() > comperTask && percentageFromDatabase1 > comperParsent) {
                boxDao.create(loginUser.getId(), now.getMonthValue() - 1);  // 新しいコレクションを作成
            }
        
        // MyBoxsデータを取得
        List<MyBoxs> box = boxDao.findByAccountId(loginUser.getId());
        List<Integer> collectionIds = new ArrayList<>();

        // MyBoxsリストから collectionId を抽出
        for (MyBoxs myBox : box) {
            collectionIds.add(myBox.getCollectionId());
        }

        // CollectionsDAO を使ってコレクションリストを取得
        CollectionsDAO collectDao = new CollectionsDAO();
        List<Collections> collects = collectDao.findByCollectionList(collectionIds);



        // コレクションリストをリクエストスコープに設定
        request.setAttribute("collection", collects);
        request.getRequestDispatcher("/WEB-INF/jsp/collection.jsp").forward(request, response);
    }
}
