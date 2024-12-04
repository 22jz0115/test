package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TasksDAO;
import model.Accounts;
import model.Tasks;

/**
 * Servlet implementation class Result
 */
@WebServlet("/Result")
public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		  HttpSession session = request.getSession();
	        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

	        if (loginUser == null) {
	            // ログインユーザーがいない場合、ログイン画面へリダイレクト
	            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	            return;
	        }

	  
	        int outSum = 0;
	        int inSum = 0;
	        int outCheck = 0;
	        int inCheck = 0;
	        // 進捗データをリクエスト属性に設定
	        
	        
	        // タスクリストの取得
	        LocalDate today = LocalDate.now();
	        int month = today.getMonthValue();
	        
	        TasksDAO dao = new TasksDAO();
	        List<Tasks> taskList = dao.findByCurrentMonth(loginUser.getId(), month );
	        
	        for( Tasks task : taskList ) {
	        	if(task.getOutin() == 1) {
	        		outSum++;
	        		if(task.getCheck() == 1) {
	        			outCheck++;
	        		}
	 
	        	}else {
	        		inSum++;
	        		if(task.getCheck() == 1) {
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

	        int percentageFromDatabase2 = 0;
	        if (outSum != 0) { // outSumが0の場合の割り算エラーを防ぐ
	            percentageFromDatabase2 = (int) (((double) outCheck / outSum) * 100);
	        }

	        int percentageFromDatabase3 = 0;
	        if (inSum != 0) { // inSumが0の場合の割り算エラーを防ぐ
	            percentageFromDatabase3 =(int) (((double) inCheck / inSum) * 100);
	        }
	        
	        
	        request.setAttribute("percentageFromDatabase1", percentageFromDatabase1);
	        request.setAttribute("percentageFromDatabase2", percentageFromDatabase2);
	        request.setAttribute("percentageFromDatabase3", percentageFromDatabase3);
	        request.setAttribute("taskList", taskList);
	        
		request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
