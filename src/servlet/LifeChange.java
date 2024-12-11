package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.ServletException;
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
	        Lifes life;
	        
	        life = dao.find(lifeId);
	        
	        request.setAttribute("life", life);
	        System.out.println("id = " + life.getId());
		    System.out.println("title = " + life.getTitle());
		    System.out.println("content = " + life.getContent());
	        
	        
	        request.getRequestDispatcher("/WEB-INF/jsp/lifeChange.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    // lifeId パラメータの取得と検証
	    String lifeIdParam = request.getParameter("lifeId");
	    String title = request.getParameter("title");
	    String content = request.getParameter("co");
	    System.out.println("id = " + lifeIdParam);
	    System.out.println("title = " + title);
	    System.out.println("content = " + content);
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
	    
	    System.out.println(lifeId);
	    // パラメータの取得
	    
	    content = content.replace("\n", "<br>");

	    // ファイルアップロードの処理
	    Part filePart = request.getPart("file");
	    String fileName = filePart != null && filePart.getSize() > 0 
	                      ? Paths.get(filePart.getSubmittedFileName()).getFileName().toString() 
	                      : null;

	    String uploadDir = "/opt/tomcat/webapps/test/assets/img";

	    // ファイルを保存
	    String relativePath = null;
	    if (fileName != null && !fileName.isEmpty()) {
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

	    // DAOを使ってデータ更新
	    LifesDAO lifesDAO = new LifesDAO();
	    HttpSession session = request.getSession();
	    Accounts loginUser = (Accounts) session.getAttribute("loginUser");

	    lifesDAO.lifeChange(lifeId, title, relativePath, content);

	    // リダイレクト処理
	    response.sendRedirect("LifeHackHistory");
	}

}
