package logic;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import dao.AccountsDAO;
import model.Accounts;

public class AuthLogic {
	/**
	 * ログイン処理を行う
	 * @param email
	 * @param password
	 * @return 成功時はログインしたユーザ、失敗時はnull
	 */
	public Accounts login(String email, String password) {
		AccountsDAO dao = new AccountsDAO();
		Accounts account = dao.findByEmail(email);
		
		boolean isUpdated = dao.updateLoginTime(account.getId());

		if (isUpdated) {
		    System.out.println("ログイン時刻が更新されました。");
		} else {
		    System.out.println("ログイン時刻の更新に失敗しました。");
		}
		
		if ((account != null) && (BCrypt.checkpw(password, account.getPass()))) {
			return account;
		}
		
		return null;
	}
	
	/**
	 * ログアウト処理を行う
	 * @return なし
	 */
	public void logout(HttpSession session) {
		if (isLoggedIn(session)) {
			session.removeAttribute("loginUser");
		}
	}
	
	/**
	 * ログイン状態を確認する
	 * @param session
	 * @return ログインしていれば true、していなければ false
	 */
	public boolean isLoggedIn(HttpSession session) {
		return session.getAttribute("loginUser") != null;
	}
}
