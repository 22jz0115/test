package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class Weather
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 気象庁APIのベースURL（例: 大阪の地域コード 270000）
    private static final String BASE_API_URL = "https://www.jma.go.jp/bosai/forecast/data/forecast/";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // リクエストからエリアコードを取得（例: 270000 が大阪府）
        String areaCode = request.getParameter("areaCode");
        if (areaCode == null || areaCode.isEmpty()) {
            areaCode = "270000"; // デフォルト値として大阪のエリアコード
        }

        // 気象庁APIのURL（都道府県の天気予報を取得）
        String apiUrl = BASE_API_URL + areaCode + ".json";

        // APIからデータを取得
        String weatherData = getWeatherData(apiUrl);

        // 今日の天気情報を抽出
        String weatherDescription = parseWeatherData(weatherData);

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
        // APIのJSONレスポンスをJSONObjectとして解析
        JSONArray forecastArray = new JSONArray(jsonData);
        JSONObject forecast = forecastArray.getJSONObject(0); // 先頭の天気予報データを取得

        // "timeSeries"フィールドから時間帯別の天気データを取得
        JSONArray timeSeries = forecast.getJSONArray("timeSeries");

        // 最初の "timeSeries" が今日の天気を示す
        JSONObject todayWeather = timeSeries.getJSONObject(0);

        // "areas" フィールドから地域の天気情報を取得
        JSONArray areas = todayWeather.getJSONArray("areas");
        JSONObject areaWeather = areas.getJSONObject(0);

        // "weathers" フィールドから今日の天気を取得
        JSONArray weathers = areaWeather.getJSONArray("weathers");
        String weather = weathers.getString(0).replaceAll("　", ""); // 空白をすべて削除

        // "area" フィールドから地域名を取得
        String areaName = areaWeather.getJSONObject("area").getString("name");

        // 自然な会話調の文章を作成
        return areaName + "の天気は、" + weather;
    }
}
