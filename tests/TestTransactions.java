import junit.framework.TestCase;

import java.security.*;

public class TestTransactions extends TestCase {
    public void testTransactionIDs() {
        Transaction tx1 = new Transaction();
        byte[] txID1 = tx1.getRawTx();
        System.out.println("txID: " + txID1.toString());

        Transaction tx2 = new Transaction();
        byte[] txID2 = tx2.getRawTx();
        System.out.println("txID: " + txID2.toString());
    }

    public void testTransactionHashes() {
        Transaction tx1 = new Transaction();
        tx1.finalize();
        assertNotNull(tx1.getHash());
    }

    public void testTransactionInputOutput() {
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


        Transaction txOutput = new Transaction();
        txOutput.addOutput(1, publicKey);

        assertNull(txOutput.getOutput(10));

        Transaction txInput = new Transaction();
        txInput.addInput(txOutput.getHash(), 0);
    }
}