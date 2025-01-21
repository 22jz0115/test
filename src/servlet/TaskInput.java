package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CategoriesDAO;
import dao.TasksDAO;
import model.Accounts;
import model.Categories;
import model.Tasks;

/**
 * Servlet implementation class TaskInput
 */
@WebServlet("/TaskInput")
public class TaskInput extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // GETメソッド：タスク入力フォームの表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // パラメータから日付を取得
        String selectedDate = request.getParameter("date");
        
        if (selectedDate == null || selectedDate.isEmpty()) {
            LocalDate today = LocalDate.now();
            selectedDate = today.toString();
        }
        
        CategoriesDAO categoriesDAO = new CategoriesDAO();
        List<Categories> categoryList = categoriesDAO.get();  // DAOからデータを取得
        request.setAttribute("categoryList", categoryList); 
        request.setAttribute("selectedDate", selectedDate); 
        
        request.getRequestDispatcher("/WEB-INF/jsp/taskInput.jsp").forward(request, response);
    }

    // POSTメソッド：タスクの追加処理
 // POSTメソッド：タスクの追加処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // フォームデータを取得
        String date1 = request.getParameter("dateInput1");
        String date2 = request.getParameter("dateInput2");
        String time = request.getParameter("appt-time");
        String category = request.getParameter("categorySelect");
        String taskName = request.getParameter("taskName");
        String memo = request.getParameter("story");
        String repeatOption = request.getParameter("repeatSelect");
        String dateOption = request.getParameter("dateOption"); // dateOptionの取得
        String selectedDay = request.getParameter("daySelect"); // 曜日の取得 (毎週の場合)

        int categoryId = Integer.parseInt(category);

        // 日付フォーマットの準備
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // 開始日と終了日
        LocalDateTime currentDate = null;
        LocalDateTime endDate = null;

        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();

        int outin = request.getParameter("switch") != null ? 1 : 0;

        TasksDAO dao = new TasksDAO();

        if("todayonly".equals(dateOption)) {
        	currentDate = LocalDateTime.parse(date1 + " " + time, dateTimeFormatter);
        	Tasks createdTask = dao.create(categoryId, accountId, taskName, memo, outin, currentDate);
            if (createdTask == null) {
                System.err.println("タスク作成失敗");
            }
        } else {
        	// 日付指定がない場合（今月選択）
            if ("currentMonth".equals(dateOption)) {
                currentDate = LocalDateTime.now().withDayOfMonth(1).withHour(Integer.parseInt(time.split(":")[0]))
                    .withMinute(Integer.parseInt(time.split(":")[1]));
                endDate = currentDate.withDayOfMonth(currentDate.toLocalDate().lengthOfMonth());
                endDate = endDate.withHour(Integer.parseInt(time.split(":")[0])).withMinute(Integer.parseInt(time.split(":")[1]));
            } else {
                if (date1 != null && !date1.isEmpty()) {
                    currentDate = LocalDateTime.parse(date1 + " " + time, dateTimeFormatter);
                }

                if (date2 != null && !date2.isEmpty()) {
                    endDate = LocalDateTime.parse(date2 + " " + time, dateTimeFormatter);
                }
            }
            
            ExecutorService executorService = Executors.newFixedThreadPool(1); // スレッドプールを適切に設定

	         // 繰り返し設定によるタスクの追加
	         switch (repeatOption) {
	             case "daily":  // 毎日
	                 if (currentDate != null) {
	                     LocalDateTime end = endDate != null ? endDate : currentDate.withDayOfMonth(currentDate.toLocalDate().lengthOfMonth());
	                     while (!currentDate.isAfter(end)) {
	                         final LocalDateTime taskDate = currentDate;  // finalまたはeffectively finalにする
	                         executorService.submit(() -> {
	                             try {
	                                 // 非同期でタスク作成処理
	                                 Tasks createdTask = dao.create(categoryId, accountId, taskName, memo, outin, taskDate);
	                                 if (createdTask == null) {
	                                     System.err.println("タスク作成失敗: " + taskDate.format(dateTimeFormatter));
	                                 }
	                                 Thread.sleep(500);  // 遅延（非同期タスク内）
	                             } catch (InterruptedException e) {
	                                 e.printStackTrace();
	                             }
	                         });
	                         currentDate = currentDate.plusDays(1); // 次の日
	                     }
	                 }
	                 break;
	
	             case "weekly": // 毎週
	                 if (currentDate != null && selectedDay != null) {
	                     // 曜日を設定
	                     LocalDateTime startDate = getNextWeekday(currentDate, selectedDay);
	
	                     // 終了日が指定されていない場合、1年後を終了日と設定
	                     LocalDateTime end = endDate != null ? endDate : currentDate.plusYears(1);
	                     while (!startDate.isAfter(end)) {
	                         final LocalDateTime taskDate = startDate;  // finalまたはeffectively finalにする
	                         executorService.submit(() -> {
	                             try {
	                                 Tasks createdTask = dao.create(categoryId, accountId, taskName, memo, outin, taskDate);
	                                 if (createdTask == null) {
	                                     System.err.println("タスク作成失敗: " + taskDate.format(dateTimeFormatter));
	                                 }
	                                 Thread.sleep(500);  // 遅延（非同期タスク内）
	                             } catch (InterruptedException e) {
	                                 e.printStackTrace();
	                             }
	                         });
	                         startDate = startDate.plusWeeks(1); // 次の週
	                     }
	                 }
	                 break;
	
	             	 default:  // 繰り返しなし
	            	    if (currentDate != null) {
	            	        final LocalDateTime taskDate = currentDate;  // finalにして非同期タスク内で使用
	            	        executorService.submit(() -> {
	            	            try {
	            	                Tasks createdTask = dao.create(categoryId, accountId, taskName, memo, outin, taskDate);
	            	                if (createdTask == null) {
	            	                    System.err.println("タスク作成失敗: " + taskDate.format(dateTimeFormatter));
	            	                }
	            	                Thread.sleep(500);  // 遅延（非同期タスク内）
	            	            } catch (InterruptedException e) {
	            	                e.printStackTrace();
	            	            }
	            	        });
	            	    }
	            	    break;
	         	 }
		         // ExecutorServiceのシャットダウン
		         executorService.shutdown();
         }
        
        // タスク一覧画面にリダイレクト
        response.sendRedirect("Task?date=" + date1);
    }

    // 次に指定された曜日に一致する日付を返すメソッド
    private LocalDateTime getNextWeekday(LocalDateTime currentDate, String weekday) {
        // 曜日を取得（例: "monday" → 1, "tuesday" → 2, ...）
        int targetDay = getDayOfWeek(weekday);
        int currentDay = currentDate.getDayOfWeek().getValue();

        // 現在日が目標曜日と一致する場合、当日を返す
        if (targetDay == currentDay) {
            return currentDate;
        }

        // 目標の曜日が現在の日付より後なら、その週の目標曜日を設定
        if (targetDay > currentDay) {
            return currentDate.plusDays(targetDay - currentDay);
        } else { 
            // 目標の曜日が今週より前なら、次の週のその曜日に設定
            return currentDate.plusDays(7 - (currentDay - targetDay));
        }
    }

    // 曜日文字列を数字に変換（1: Monday, 7: Sunday）
    private int getDayOfWeek(String weekday) {
        switch (weekday) {
            case "monday": return 1;
            case "tuesday": return 2;
            case "wednesday": return 3;
            case "thursday": return 4;
            case "friday": return 5;
            case "saturday": return 6;
            case "sunday": return 7;
            default: throw new IllegalArgumentException("Invalid weekday: " + weekday);
        }
    }

}
