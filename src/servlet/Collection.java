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
import model.Accounts;
import model.Collections;
import model.MyBoxs;

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

        // 現在の年月を取得
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        Integer lastCheckedMonth = (Integer) session.getAttribute("lastCheckedMonth");

        // 月が変わったかを確認
        if (lastCheckedMonth == null || lastCheckedMonth != currentMonth) {
            // 月が変わった場合、createを呼び出す
            boxDao.create(loginUser.getId(), currentMonth);

            // セッションに現在の月を保存
            session.setAttribute("lastCheckedMonth", currentMonth);
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
