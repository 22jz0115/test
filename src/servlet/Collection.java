package servlet;

import java.io.IOException;
import java.time.LocalDate;
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

/**
 * Servlet implementation class Collection
 */
@WebServlet("/Collection")
public class Collection extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            // ログイン画面にリダイレクト
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        MyBoxesDAO boxDao = new MyBoxesDAO();

        // 現在の日付を取得
        LocalDate now = LocalDate.now();

        // セッションから最後にチェックした日付を取得
        LocalDate lastCheckedDate = (LocalDate) session.getAttribute("lastCheckedDate");
        System.out.print(lastCheckedDate.getMonthValue());

        // 最後にチェックした日付がnullか、月が異なる場合
        if (lastCheckedDate == null || now.getMonthValue() != lastCheckedDate.getMonthValue()) {
            int outSum = 0;
            int inSum = 0;
            int outCheck = 0;
            int inCheck = 0;

            int comperTask = 10;
            int comperParsent = 90;

            // タスクリストの取得
            TasksDAO dao = new TasksDAO();
            List<Tasks> taskList = dao.findByCurrentMonth(loginUser.getId());

            for (Tasks task : taskList) {
                if (task.getOutin() == 1) {
                    outSum++;
                    if (task.getCheck() == 1) {
                        outCheck++;
                    }
                } else {
                    inSum++;
                    if (task.getCheck() == 1) {
                        inCheck++;
                    }
                }
            }

            // 分母が0でないか確認してから割り算を行う
            int percentageFromDatabase1 = 0;
            if (inCheck + outCheck != 0) {
                // 割り算をする際、整数同士の割り算を避けるためにキャスト
                percentageFromDatabase1 = (int) (((double)(inCheck + outCheck) / taskList.size()) * 100);
            }

            if (taskList.size() > comperTask) {
                if (percentageFromDatabase1 < comperParsent) {
                    // 月が変わった場合、createを呼び出す
                    boxDao.create(loginUser.getId(), now.getMonthValue());

                    // セッションに現在の日付を保存
                    session.setAttribute("lastCheckedDate", now);
                }
            }
        }

        // MyBoxsデータを取得
        List<MyBoxs> box = boxDao.findByAccountId(loginUser.getId());

        // MyBoxsリストからcollectionIdを抽出
        List<Integer> collectionIds = new ArrayList<>();
        for (MyBoxs myBox : box) {
            collectionIds.add(myBox.getCollectionId());
        }

        // CollectionsDAOでコレクションリストを取得
        CollectionsDAO collectDao = new CollectionsDAO();
        List<Collections> collects = collectDao.findByCollectionList(collectionIds);

        if (collects == null || collects.isEmpty()) {
            request.setAttribute("errorMessage", "コレクションが見つかりませんでした。");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
            return;
        }

        request.setAttribute("collection", collects);
        request.getRequestDispatcher("/WEB-INF/jsp/collection.jsp").forward(request, response);
    }
}
