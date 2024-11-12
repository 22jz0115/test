package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.LifesDAO;
import model.Accounts;
import model.Lifes;

@WebServlet("/LifeHack")
@MultipartConfig
public class LifeHack extends HttpServlet {
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

        LifesDAO dao = new LifesDAO();
        List<Lifes> lifeList = dao.get();
        request.setAttribute("lifeList", lifeList);
        System.out.print(lifeList.size());
        

        request.getRequestDispatcher("/WEB-INF/jsp/lifeHack.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("name");
        String content = request.getParameter("comment");
        
        content = content.replace("\n", "<br>");

        // ファイルパートを取得
        Part filePart = request.getPart("file"); // フォームのinput type="file"のname属性が"file"である場合
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // ファイル名を取得

        // 保存先のディレクトリを指定 (絶対パスを使用)
        String uploadDir = "C:\\pleiades_Servlet\\workspace\\test\\WebContent\\assets\\img"; // 直接絶対パスを指定
        
        System.out.println("Upload directory: " + uploadDir);

        // 保存先のディレクトリが存在しない場合は作成
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // ファイルを指定したパスに保存
        File file = new File(uploadDir, fileName);
        try (InputStream inputStream = filePart.getInputStream()) {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING); // ファイルを保存
        }

        // 成功メッセージ
        response.getWriter().write("File uploaded successfully: " + fileName);
        
        // データベースに保存する相対パス
        String relativePath = "assets/img/" + fileName;

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
