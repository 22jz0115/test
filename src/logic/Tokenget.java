package logic;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


public class Tokenget {
	public static void generateVapidKeyPair() throws Exception {
        // RSA鍵ペアの生成
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);  // 2048ビットの鍵を生成
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 秘密鍵と公開鍵を取得
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        // 公開鍵をBase64エンコード
        String publicKeyBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getEncoded());

        // 秘密鍵をBase64エンコード
        String privateKeyBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString(privateKey.getEncoded());

        // 結果を表示
        System.out.println("Public Key (Base64): " + publicKeyBase64);
        System.out.println("Private Key (Base64): " + privateKeyBase64);
    }

    public void main() {
        try {
            generateVapidKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
