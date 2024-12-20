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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        // 現在の月を取得
        int month = LocalDate.now().getMonthValue();

        // リクエストパラメータで月が渡されている場合、その月に変更
        String monthParam = request.getParameter("month");
        if (monthParam != null) {
            try {
                month = Integer.parseInt(monthParam);
            } catch (NumberFormatException e) {
                // パラメータが無効な場合、現在の月を使用
            }
        }

        // 月の調整（1月と12月の境界を調整）
        if (month < 1) {
            month = 12;  // 1月より前の月は12月に設定
        } else if (month == 13) {
            month = 1;   // 12月より後の月は1月に設定
        }

        // タスクリストの取得
        TasksDAO dao = new TasksDAO();
        List<Tasks> taskList = dao.findByCurrentMonth(loginUser.getId(), month);

        // 統計の計算
        int outSum = 0;
        int inSum = 0;
        int outCheck = 0;
        int inCheck = 0;

        for (Tasks task : taskList) {
            if (task.getOutin() == 1) {
                outSum++;
                if (task.getCheck() == 1) {
                    outCheck++;
                }
            } else {
                inSum++;
                if (task.getCheck() == 1) {
                    inCheck++;
                }
            }
        }

        // 進捗率の計算
        int percentageFromDatabase1 = (inCheck + outCheck == 0) ? 0 : (int) (((double) (inCheck + outCheck) / taskList.size()) * 100);
        int percentageFromDatabase2 = (outSum == 0) ? 0 : (int) (((double) outCheck / outSum) * 100);
        int percentageFromDatabase3 = (inSum == 0) ? 0 : (int) (((double) inCheck / inSum) * 100);

        // 統計データをリクエストにセット
        request.setAttribute("percentageFromDatabase1", percentageFromDatabase1);
        request.setAttribute("percentageFromDatabase2", percentageFromDatabase2);
        request.setAttribute("percentageFromDatabase3", percentageFromDatabase3);
        request.setAttribute("taskList", taskList);
        request.setAttribute("month", month);  // 現在表示している月をセット

        // 結果を表示するJSPへ転送
        request.getRequestDispatcher("/WEB-INF/jsp/result.jsp").forward(request, response);
    }
}

