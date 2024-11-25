package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dao.PresetTasksDAO;
import model.PresetTasks;

/**
 * Servlet implementation class GetPresetData
 */
@WebServlet("/GetPresetData")
public class GetPresetData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String presetName = request.getParameter("name");
		int presetId = Integer.parseInt(presetName);
        
        PresetTasksDAO dao = new PresetTasksDAO();
        List<PresetTasks> presetData = dao.findPresetTask(presetId);
        
     // JSON形式で返す準備
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // ObjectMapperの作成とJavaTimeModuleの登録
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // LocalDateTime対応
        

        // プリセットをJSON形式で返す
        objectMapper.writeValue(response.getWriter(), presetData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
