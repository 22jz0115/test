package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import dao.TasksDAO;
import logic.AuthLogic;
import model.Accounts;
import model.Tasks;

@WebServlet("/Home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String BASE_API_URL = "https://www.jma.go.jp/bosai/forecast/data/forecast/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションからログインユーザー情報を取得
        HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");

        if (loginUser == null) {
            // ログインユーザーがいない場合、ログイン画面へリダイレクト
            request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
            return;
        }

        // ユーザーのエリアコードを取得またはデフォルト設定
        String areaCode = loginUser.getLocation() != null ? loginUser.getLocation() : "016000";
        System.out.println("エリアコード: " + areaCode);

        // 気象庁APIのURL
        String apiUrl = BASE_API_URL + areaCode + ".json";
        String weatherDescription = "";

        try {
            // APIからデータを取得
            String weatherData = getWeatherData(apiUrl);
            // 今日の天気情報を抽出
            weatherDescription = parseWeatherData(weatherData);
        } catch (IOException e) {
            weatherDescription = "天気情報の取得に失敗しました。";
            e.printStackTrace();
        }

        // タスクリストの取得
        TasksDAO dao = new TasksDAO();
        List<Tasks> taskList = dao.findByTaskList(46);
        request.setAttribute("taskList", taskList);

        // JSPに天気情報を渡す
        request.setAttribute("weatherDescription", weatherDescription);
        request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
    }

    // 気象庁APIからデータを取得するメソッド
    private String getWeatherData(String apiUrl) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }

    // JSONデータから今日の天気情報を抽出し、地域名を付加して自然な文章にするメソッド
    private String parseWeatherData(String jsonData) {
        JSONArray forecastArray = new JSONArray(jsonData);
        JSONObject forecast = forecastArray.getJSONObject(0);
        JSONArray timeSeries = forecast.getJSONArray("timeSeries");
        JSONObject todayWeather = timeSeries.getJSONObject(0);
        JSONArray areas = todayWeather.getJSONArray("areas");
        JSONObject areaWeather = areas.getJSONObject(0);
        JSONArray weathers = areaWeather.getJSONArray("weathers");
        String weather = weathers.getString(0).replaceAll("　", "");
        String areaName = areaWeather.getJSONObject("area").getString("name");

        return areaName + "の天気は、" + weather;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        AuthLogic logic = new AuthLogic();
        Accounts account = logic.login(email, pass);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", account);
            response.sendRedirect("/test/Home");
        } else {
            request.setAttribute("msg", "ログインに失敗しました");
            doGet(request, response);
        }
    }
}
