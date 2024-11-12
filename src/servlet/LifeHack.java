package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

/**
 * Servlet implementation class LifeHack
 */
@WebServlet("/LifeHack")
@MultipartConfig
public class LifeHack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		 // タスクリストの取得
        LifesDAO dao = new LifesDAO();
        List<Lifes> lifeList = dao.get();
        request.setAttribute("taskList", lifeList);
        
        request.getRequestDispatcher("/WEB-INF/jsp/lifeHack.jsp").forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String title = request.getParameter("name");
	        String content = request.getParameter("comment");

	        // 画像ファイルの処理
	        Part filePart = request.getPart("example");
	        String fileName = filePart.getSubmittedFileName();
	        String imgPath = "assets/img/" + fileName;

	        // 画像をサーバーに保存
	        File file = new File(getServletContext().getRealPath("/") + imgPath);
	        try (InputStream fileContent = filePart.getInputStream();
	             FileOutputStream outputStream = new FileOutputStream(file)) {
	            byte[] buffer = new byte[1024];
	            int bytesRead;
	            while ((bytesRead = fileContent.read(buffer)) != -1) {
	                outputStream.write(buffer, 0, bytesRead);
	            }
	        }

	        // DAOを使ってデータベースに保存
	        LifesDAO lifesDAO = new LifesDAO();
	        HttpSession session = request.getSession();
	        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
	        int accountId = loginUser.getId();
	        
	        lifesDAO.create(accountId, title, imgPath, content);
	        
	        // 成功したらリダイレクトまたは画面遷移
	        response.sendRedirect("LifeHack"); 
	}

}
