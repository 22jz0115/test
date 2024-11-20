package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dao.PresetsDAO;
import model.Accounts;
import model.Presets;

/**
 * Servlet implementation class GetPresetData
 */
@WebServlet("/GetPresetData")
public class GetPresetData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String presetName = request.getParameter("name");
		
		HttpSession session = request.getSession();
        Accounts loginUser = (Accounts) session.getAttribute("loginUser");
        int accountId = loginUser.getId();
        
        PresetsDAO dao = new PresetsDAO();
        Presets presetData = dao.findByPreset(presetName, accountId);
        
     // JSON形式で返す準備
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ObjectMapperの作成とJavaTimeModuleの登録
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime対応
        
        System.out.println("come");
		System.out.println(presetData.getTasks());

        // プリセットをJSON形式で返す
        objectMapper.writeValue(response.getWriter(), presetData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
