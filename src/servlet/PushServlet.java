package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.SubscriptionsDAO;
import model.Accounts;

@WebServlet("/PushServlet")
public class PushServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();

        try {
            // リクエストからJSONデータを取得
            BufferedReader reader = request.getReader();
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }

            // JSONの解析
            String jsonData = jsonBuffer.toString();
            
            JSONObject jsonObject = new JSONObject(jsonData);
            String endpoint = jsonObject.getString("token");

            SubscriptionsDAO dao = new SubscriptionsDAO();
            dao.create(accountId, endpoint);
            

            // 成功レスポンス
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Success");
        } catch (Exception e) {
            e.printStackTrace(); // ログにエラーを出力
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error: " + e.getMessage());
        }
    }
}
