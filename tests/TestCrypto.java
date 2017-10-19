import junit.framework.TestCase;

import java.security.*;

public class TestCrypto extends TestCase {
    public void testCryptoTest() {
        KeyPairGenerator gen = null;
        try {
            gen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        assert gen != null;

        gen.initialize(1024,new SecureRandom());
        KeyPair keys=gen.generateKeyPair();
        PublicKey publicKey=keys.getPublic();
        PrivateKey privateKey=keys.getPrivate();

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

        byte[] bytes = "Message".getBytes();
        try {
            signature.update(bytes);
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        byte[] digitalSignature =  null;
        try {
            digitalSignature = signature.sign();
        } catch (SignatureException e) {
            e.printStackTrace();
        }

        assertTrue(Crypto.verifySignature(publicKey, "Message".getBytes(), digitalSignature));
        assertFalse(Crypto.verifySignature(publicKey, "Not That Message".getBytes(), digitalSignature));
    }
}
