package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.AccountsDAO;
import dao.LifesDAO;
import model.Accounts;
import model.Lifes;

@WebServlet("/LifeHack")
@MultipartConfig
public class LifeHack extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        // キーワード取得
        String keyword = request.getParameter("keyword");

        LifesDAO dao = new LifesDAO();
        List<Lifes> lifeList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            // 検索結果を取得
            lifeList = dao.searchByKeyword(keyword.trim());
        } else {
            // 全件取得
            lifeList = dao.get();
        }

        // 関連するアカウント情報の取得
        AccountsDAO accountDAO = new AccountsDAO();
        for (Lifes life : lifeList) {
            Accounts account = accountDAO.find(life.getAccountId());
            life.setAccount(account);  
        }

        request.setAttribute("lifeList", lifeList);
        request.getRequestDispatcher("/WEB-INF/jsp/lifeHack.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("name");
        String content = request.getParameter("comment");
        System.out.println(content);

        // ファイルパートを取得
        Part filePart = request.getPart("file");
        String relativePath = ""; // 初期値（写真がない場合）

        if (filePart != null && filePart.getSize() > 0) { // ファイルが存在する場合のみ処理
            String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            System.out.println("Original filename: " + originalFileName);

            // 拡張子を取得
            String fileExtension = "";
            int dotIndex = originalFileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileExtension = originalFileName.substring(dotIndex);
            }

            // 重複しないようにファイル名を変更（UUID + タイムスタンプ）
            String uniqueFileName = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + fileExtension;
            System.out.println("Renamed filename: " + uniqueFileName);

            // 保存先のディレクトリを指定
            String uploadDir = "/opt/tomcat/webapps/test/assets/img";

            // 保存先のディレクトリが存在しない場合は作成
            File uploadDirFile = new File(uploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }

            // 保存先ファイルパスを設定
            File file = new File(uploadDir, uniqueFileName);

            // ファイルを保存
            try (InputStream inputStream = filePart.getInputStream()) {
                Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // 相対パスを設定
            relativePath = "assets/img/" + uniqueFileName;
        } else {
            System.out.println("No file uploaded.");
            relativePath = ""; // デフォルト画像を指定（存在しない場合は空文字）
        }

        // 成功メッセージ
        response.getWriter().write("File uploaded successfully: " + relativePath);

        // DAOを使ってデータベースに保存
        LifesDAO lifesDAO = new LifesDAO();
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();

        lifesDAO.create(accountId, title, relativePath, content);

        // 成功したらリダイレクトまたは画面遷移
        response.sendRedirect("LifeHack");
    }
}
