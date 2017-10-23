import java.security.*;

/**
 * Created by puzanov on 10/23/17.
 */
public class CryptoHelper {
    public static KeyPair generateKeys() {
        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert gen != null;

        gen.initialize(1024,new SecureRandom());
        return gen.generateKeyPair();
    }

    public static byte[] sign(PrivateKey privateKey, byte[] message) {
        Signature signature = null;
        try {
            signature = Signature.getInstance("SHA256withRSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert signature != null;

        try {
            signature.initSign(privateKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        try {
            signature.update(message);
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        byte[] digitalSignature =  null;
        try {
            digitalSignature = signature.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return digitalSignature;
    }
}

