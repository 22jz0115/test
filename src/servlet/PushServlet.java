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
	private static final String PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlhI3eNMWKAmqQDM7tGOHjthyRD1qtGDaQyndE_45WIWKhsjoU_uaIJLptetAwSaT8APe277ZK7dAA3psGjRkb76_tcg1bTR-H3_2wTN1uGHuQNaXagjMGafC12wAayhdFkQ4HP6TsBFgycGrscvi1udZ3AC78Ot3NW3nOba1P9cdKfXyGkXEJLfp5CRy9QdBOqELLdv-McxLQFX4K_ic4MaJGVPu9xu1zkDhK4pkXlwrxYS9DHuWAB20Jf72LAQ06JqIR0Bi0WMQuYaIfaSGL-4tSJzIBorKM6buodhX4kjFB_hYNGrgiEyCziz_ZgGZPgBTbkxsWIuW1RUsMUAEwQIDAQAB";
    private static final String PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCWEjd40xYoCapAMzu0Y4eO2HJEPWq0YNpDKd0T_jlYhYqGyOhT-5ogkum160DBJpPwA97bvtkrt0ADemwaNGRvvr-1yDVtNH4ff_bBM3W4Ye5A1pdqCMwZp8LXbABrKF0WRDgc_pOwEWDJwauxy-LW51ncALvw63c1bec5trU_1x0p9fIaRcQkt-nkJHL1B0E6oQst2_4xzEtAVfgr-JzgxokZU-73G7XOQOErimReXCvFhL0Me5YAHbQl_vYsBDTomohHQGLRYxC5hoh9pIYv7i1InMgGisozpu6h2FfiSMUH-Fg0auCITILOLP9mAZk-AFNuTGxYi5bVFSwxQATBAgMBAAECggEAF2VFop9938YJ9bJHB4tQsdNKlRriDLbgsAHRxnEmrYc8HmEp3xXlZFii-MpxMOt7urpPvwBUyPzrjN9EBR3P3p_lMUjCE4FQfEatyFbmblVOGGub9Vrjvsgplv3uXsZPJM6D1PlXOuaqqSl5Q-GePpSYrLSsmBWNYxX8K0IxpX79nrAZI-e7QK-ug1ogstWq-kIj9pMvA-i0sjIWJda2O1iaDnaZYfFTRW21M1us3I8BHcx63UsFk97oqCYXgq2pjuMxu_ja7DdQTAAdNEGk9jz4FNs_1NK9smgreIcuYChIfIxk4UX8ubrtL_wLNWqol16kT_M1WTXclshgNOBSdQKBgQDEWyzbijGiSXgaipFgXGN3fD-GyhakvQeV_Qpm_nuzdPSrJArYbINu6srUbiSSKPAiT5iatB79wdON5FxP14ZypQYDbiHHdOMbTHkKU5giaYN32vOwYa0sPF59M5n7Nvz-Mu2N1DoGFHjNxQPdAz_QZQUAJGXQsHGfNutcJDKKEwKBgQDDp-FSZaldDdCBRhCNn58OoA9Ttmcff6ZZiMgc_lxP0yGJMO2WHUgXabz-Tka4hSeKjjZIvLCclKvn485Oac9PpI3dCsQucJ02Kd3UpjS__DB4S6A0iDZHN6fpG2QmNp4SB8JRKWvPj19feIexHq6xBLL4hR7tVGIVkak_y7FQWwKBgQCTKPYEj5zFUSS3V5S-0F8npLIDth-kVZuC2MNcD2vsQkJApWGPdDxI-6UJ8oZsGExompj3kPkNG6AO3tAkcpXsBCvulDKyxrKSugyZJa-GUufaAvnR_lKILbJvSSYlNcAET2zkd-MIvX0QUpCjqpS__YXy5lR8RFDTj5hzVRmfiwKBgBQCIQq6Fr3LYd02ew7gmfV27NpUtusDtNFHKZ-9a1dGchGDjWhiijLKv2cKPjU0eLy0mlyJ303D7miCZWCjdW3hmBhYt4NARPN7CJJshMx1T8edhD6bB2H1ERjHklBOGZAidv6xy7KoKMqsmsPELX0wy9SaWHVQwc8O3E4H2JQtAoGAazlOO-qCMiG4OyhjeQyIvq6nlIoPZE8Ku8foWAikogL3ciBYFgCrInBSV7r6B3Sf88_OUgCnHGUNpW-PiIayAjj3BwRIZQQjLrtYvS9Dn_i-yl6T_J7XqadTISvPqVna-jrHPEEXmpMoapvp0S4SAJNcncFjh_8srMnAbP7SZPs";
	
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
            String endpoint = jsonObject.getString("endpoint");
            JSONObject keys = jsonObject.getJSONObject("keys");
            String p256dh = keys.getString("p256dh");
            String auth = keys.getString("auth");

            SubscriptionsDAO dao = new SubscriptionsDAO();
            dao.create(accountId, endpoint, p256dh, auth);
            

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
