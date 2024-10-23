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

import org.json.JSONObject;

/**
 * Servlet implementation class Weather
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 気象庁APIのURL（例: 大阪の地域コード 270000）
    private static final String API_URL = "https://www.jma.go.jp/bosai/forecast/data/overview_forecast/270000.json";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // APIからデータを取得
        String weatherData = getWeatherData(API_URL);
        
        // 天気情報を抽出
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

    // JSONデータから天気情報を抽出し、特定の単語が含まれている場合に指定のテキストを表示するメソッド
    private String parseWeatherData(String jsonData) {
        // APIのJSONレスポンスをJSONObjectとして解析して、天気情報（"text"フィールド）を取得
        JSONObject forecast = new JSONObject(jsonData);

        // "text"フィールドを取得
        String weatherText = forecast.getString("text");

        // 特定の単語を含んでいるかどうかを確認し、カスタムメッセージを設定
        if (weatherText.contains("雨")) {
            return "雨が予想されています。傘を忘れないでください。";
        } else if (weatherText.contains("雷")) {
            return "雷の可能性があります。安全に注意してください。";
        } else if (weatherText.contains("台風")) {
            return "台風が接近しています。外出は控えてください。";
        } else {
            // それ以外の場合は通常の天気予報テキストを表示
            return weatherText;
        }
    }
    
    
}
