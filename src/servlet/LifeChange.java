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

	    // lifeId パラメータの取得と検証
	    String lifeIdParam = request.getParameter("lifeId");
	    String title = request.getParameter("title");
	    String content = request.getParameter("comment");
	    String deleteImage = request.getParameter("deleteImage"); // 画像削除フラグ

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

	    // DAOのインスタンス作成
	    LifesDAO lifesDAO = new LifesDAO();

	    // 画像削除フラグがセットされている場合、画像を空白で更新
	    if ("true".equals(deleteImage)) {
	        // 現在の画像パスを取得
	        Lifes life = lifesDAO.find(lifeId);
	        String currentImagePath = life.getImg(); // 現在の画像のパスを取得
	        
	        // ファイルシステムから画像を削除
	        if (currentImagePath != null && !currentImagePath.isEmpty()) {
	            String uploadDir = "/opt/tomcat/webapps/test/assets/img";
	            File fileToDelete = new File(uploadDir, currentImagePath);
	            if (fileToDelete.exists()) {
	                fileToDelete.delete(); // 画像ファイルを削除
	            }
	        }
	        
	        // 画像のパスを空白にしてデータベースを更新
	        lifesDAO.lifeChange(lifeId, title, "", content); 
	        response.sendRedirect("LifeHackHistory");
	        return;
	    } else {
	        // ファイルアップロードの処理
	        Part filePart = request.getPart("file");
	        String fileName = filePart != null && filePart.getSize() > 0 
	                          ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() 
	                          : null;

	        if (fileName == null) {
	            // 画像がない場合はテキスト情報のみを更新
	            lifesDAO.lifeChangeText(lifeId, title, content);
	        } else {
	            String uploadDir = "/opt/tomcat/webapps/test/assets/img"; // サーバ上のアップロード先ディレクトリ
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
	                relativePath = "assets/img/" + fileName; // 相対パス
	            }

	            // データベースの情報を更新
	            lifesDAO.lifeChange(lifeId, title, relativePath, content);
	        }
	    }

	    // 成功したらリダイレクト
	    response.sendRedirect("LifeHackHistory");
	}


}
