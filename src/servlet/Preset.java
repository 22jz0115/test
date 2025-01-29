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

import dao.PresetTasksDAO;
import dao.PresetsDAO;
import dao.TasksDAO;
import model.Accounts;
import model.PresetTasks;
import model.Presets;

@WebServlet("/Preset")
public class Preset extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }
    	
    	// パラメータから日付を取得
        String selectedDate = request.getParameter("date");
        request.setAttribute("selectedDate", selectedDate);
        
        int accountId = loginUser.getId();

        PresetsDAO dao = new PresetsDAO();
        List<Presets> presetList = dao.get(accountId);
        request.setAttribute("presetList", presetList);

        request.getRequestDispatcher("/WEB-INF/jsp/priset.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();

        // フォームデータの取得
        String presetName = request.getParameter("preset_name");
        int presetId = Integer.parseInt(presetName);

        String startDate = request.getParameter("dateInput1");
       
        String endDate = request.getParameter("dateInput2");

        // プリセットタスクを取得
        PresetTasksDAO presetTasksDAO = new PresetTasksDAO();
        List<PresetTasks> presetData = presetTasksDAO.findPresetTask(presetId);

        // 繰り返し設定を取得
        String repeatOption = request.getParameter("repeatSelect");

        // 繰り返し設定がnullの場合、デフォルト値を設定
        if (repeatOption == null) {
            repeatOption = "none";  // "none" は繰り返しなしのデフォルト値と仮定
        }

        String selectedDay = request.getParameter("daySelect"); // 曜日の取得（毎週の場合）

        // dateOption の取得
        String dateOption = request.getParameter("dateOption");
        
        // 結果メッセージの初期化
        boolean allInserted = true;
        TasksDAO tasksDAO = new TasksDAO();

        // 日付の処理
        LocalDate start = null;
        LocalDate end = null;
        
        if("todayonly".equals(dateOption)) {
        	boolean result = tasksDAO.insertPresetTasks(presetData, accountId, startDate.toString());
        	if (!result) {
                allInserted = false;
            }
        } else {
        	if ("currentMonth".equals(dateOption)) {
                // 今月の場合
                start = LocalDate.now().withDayOfMonth(1); // 今月の1日
                end = start.withDayOfMonth(start.lengthOfMonth()); // 今月の最後の日
            } else if ("specified".equals(dateOption)) {
                // 日付指定の場合
                start = LocalDate.parse(startDate);
                end = LocalDate.parse(endDate);
            } else {
                // デフォルトとして指定された日付を使用
                start = LocalDate.parse(startDate);
                end = LocalDate.parse(endDate);
            }

            // 繰り返し設定によるタスク挿入処理
            switch (repeatOption) {
                case "daily": // 毎日
                    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                        boolean result = tasksDAO.insertPresetTasks(presetData, accountId, date.toString());
                        if (!result) {
                            allInserted = false;
                        }
                    }
                    break;

                case "weekly": // 毎週
                    // 曜日を取得
                    int targetDay = getDayOfWeek(selectedDay);
                    LocalDate currentDate = start;

                    // 指定された曜日が来るまで日付を進める
                    while (currentDate.getDayOfWeek().getValue() != targetDay) {
                        currentDate = currentDate.plusDays(1); // 次の日に進める
                    }

                    // 現在の曜日が指定した曜日に一致した場合からタスクを挿入
                    while (!currentDate.isAfter(end)) {
                        boolean result = tasksDAO.insertPresetTasks(presetData, accountId, currentDate.toString());
                        if (!result) {
                            allInserted = false;
                        }
                        currentDate = currentDate.plusWeeks(1); // 次の週の同じ曜日に進む
                    }
                    break;

                default: // 繰り返しなし
                    for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                        boolean result = tasksDAO.insertPresetTasks(presetData, accountId, date.toString());
                        if (!result) {
                            allInserted = false;
                        }
                    }
                    break;
             }
        }

        // 結果メッセージの処理
        if (allInserted) {
            request.setAttribute("msg", "すべてのタスクが正常に挿入されました");
            response.sendRedirect("/test/Task?date=" + startDate);
        } else {
            request.setAttribute("msg", "一部またはすべてのタスク挿入に失敗しました");
            response.sendRedirect("/test/Preset?date=" + startDate);
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
