package servlet;

import java.io.IOException;
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
  
        
        // MyBoxsデータを取得
        List<MyBoxs> box = boxDao.findByAccountId(loginUser.getId());
        List<Integer> collectionIds = new ArrayList<>();
        
        System.out.println(box.size());

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
