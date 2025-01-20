package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

/**
 * Servlet implementation class LifeChange
 */
@WebServlet("/LifeChange")
@MultipartConfig
public class LifeChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   HttpSession session = request.getSession();
	        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

	        if (loginUser == null) {
	            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	            return;
	        }
	        
	        String lifeIdParam = request.getParameter("lifeId");
	        if (lifeIdParam == null || lifeIdParam.isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lifeId パラメータがありません");
	            return;
	        }

	        int lifeId;
	        try {
	            lifeId = Integer.parseInt(lifeIdParam);
	        } catch (NumberFormatException e) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lifeId が数値ではありません");
	            return;
	        }

	        LifesDAO dao = new LifesDAO();
	        Lifes life = dao.find(lifeId);
	        
	        request.setAttribute("life", life);
	        request.getRequestDispatcher("/WEB-INF/jsp/lifeChange.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    
	    // パラメータの取得
	    String lifeIdParam = request.getParameter("lifeId");
	    String title = request.getParameter("title");
	    String content = request.getParameter("comment");
	    String deleteImage = request.getParameter("deleteImage"); // 画像削除フラグ

	    // デバッグ用
	    System.out.println("deleteImage : " + deleteImage); // 削除フラグが正しく渡されているか確認

	    if (lifeIdParam == null || lifeIdParam.isEmpty()) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lifeId パラメータがありません");
	        return;
	    }

	    int lifeId;
	    try {
	        lifeId = Integer.parseInt(lifeIdParam);
	    } catch (NumberFormatException e) {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "lifeId が数値ではありません");
	        return;
	    }

	    // DAOインスタンス作成
	    LifesDAO lifesDAO = new LifesDAO();

	    // 画像削除フラグが 'true' なら画像削除処理
	    if ("true".equals(deleteImage)) {
	        // 現在の画像パスを取得
	        Lifes life = lifesDAO.find(lifeId);
	        String currentImagePath = life.getImg();

	        if (currentImagePath != null && !currentImagePath.isEmpty()) {
	            String uploadDir = "/opt/tomcat/webapps/test/assets/img";
	            File fileToDelete = new File(uploadDir, currentImagePath);
	            if (fileToDelete.exists()) {
	                fileToDelete.delete(); // 画像削除
	            }
	        }

	        // データベースで画像のパスを空に更新
	        lifesDAO.lifeChange(lifeId, title, "", content);
	        response.sendRedirect("LifeHackHistory");
	        return;
	    } else {
	        // 画像が送信されていればアップロード処理
	        Part filePart = request.getPart("file");
	        String fileName = filePart != null && filePart.getSize() > 0 
	                          ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() 
	                          : null;

	        if (fileName == null) {
	            // 画像がない場合はテキストのみ更新
	            lifesDAO.lifeChangeText(lifeId, title, content);
	        } else {
	            String uploadDir = "/opt/tomcat/webapps/test/assets/img";
	            String relativePath = null;

	            if (!fileName.isEmpty()) {
	                File uploadDirFile = new File(uploadDir);
	                if (!uploadDirFile.exists()) {
	                    uploadDirFile.mkdirs();
	                }

	                File file = new File(uploadDir, fileName);
	                try (InputStream inputStream = filePart.getInputStream()) {
	                    Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	                }
	                relativePath = "assets/img/" + fileName;
	            }

	            // 新しい画像のパスでデータベース更新
	            lifesDAO.lifeChange(lifeId, title, relativePath, content);
	        }
	    }

	    // 処理後にリダイレクト
	    response.sendRedirect("LifeHackHistory");
	}



}
