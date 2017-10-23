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
        KeyPair scroogeKeys = CryptoHelper.generateKeys();
        Transaction txCreateNewCoins = new Transaction();
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());

        KeyPair goofyKeys = CryptoHelper.generateKeys();
        Transaction txPayOneCoinToGoofy = new Transaction();
        txPayOneCoinToGoofy.addInput(txCreateNewCoins.getHash(), 0);
        txPayOneCoinToGoofy.addOutput(1, goofyKeys.getPublic());
        txPayOneCoinToGoofy.addSignature(CryptoHelper.sign(scroogeKeys.getPrivate(), txPayOneCoinToGoofy.getRawDataToSign(0)), 0);

        assertTrue(Crypto.verifySignature(scroogeKeys.getPublic(), txPayOneCoinToGoofy.getRawDataToSign(0), txPayOneCoinToGoofy.getInput(0).signature));
    }
}