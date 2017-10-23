import junit.framework.TestCase;

import java.security.KeyPair;
import java.util.Arrays;

public class TextTxHandler extends TestCase {
    public void testUTXOPool() {
        KeyPair scroogeKeys = CryptoHelper.generateKeys();
        Transaction txCreateNewCoins = new Transaction();
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins.finalize();

        UTXO utxo1 = new UTXO(txCreateNewCoins.getHash(), 0);
        UTXO utxo2 = new UTXO(txCreateNewCoins.getHash(), 1);
        UTXO utxo3 = new UTXO(txCreateNewCoins.getHash(), 2);

        UTXOPool utxoPool = new UTXOPool();
        utxoPool.addUTXO(utxo1, txCreateNewCoins.getOutput(0));
        utxoPool.addUTXO(utxo2, txCreateNewCoins.getOutput(1));
        utxoPool.addUTXO(utxo3, txCreateNewCoins.getOutput(2));

        TxHandler handler = new TxHandler(utxoPool);
        assertTrue(handler.utxoPool == utxoPool);
    }

    public void testIsValidTx() {
        KeyPair scroogeKeys = CryptoHelper.generateKeys();
        Transaction txCreateNewCoins = new Transaction();
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins.finalize();
        byte[] txCreateNewCoinsHash = txCreateNewCoins.getHash();


        Transaction txCreateNewCoins1 = new Transaction();
        txCreateNewCoins1.addOutput(1, scroogeKeys.getPublic());
        txCreateNewCoins1.finalize();
        byte[] txCreateNewCoinsHash1 = txCreateNewCoins1.getHash();


        UTXO utxo1 = new UTXO(txCreateNewCoinsHash, 0);
        UTXO utxo2 = new UTXO(txCreateNewCoinsHash, 1);
        UTXO utxo3 = new UTXO(txCreateNewCoinsHash1, 2);

        assertTrue(Arrays.equals(utxo1.getTxHash(), txCreateNewCoinsHash));
        assertTrue(Arrays.equals(utxo2.getTxHash(), txCreateNewCoinsHash));
        assertTrue(Arrays.equals(utxo3.getTxHash(), txCreateNewCoinsHash1));

        UTXOPool utxoPool = new UTXOPool();
        utxoPool.addUTXO(utxo1, txCreateNewCoins.getOutput(0));
        utxoPool.addUTXO(utxo2, txCreateNewCoins.getOutput(1));
        utxoPool.addUTXO(utxo3, txCreateNewCoins.getOutput(2));


        KeyPair goofyKeys = CryptoHelper.generateKeys();
        Transaction txPayOneCoinToGoofy = new Transaction();
        txPayOneCoinToGoofy.addInput(txCreateNewCoinsHash, 0); // first scrooge coin
        txPayOneCoinToGoofy.addOutput(1, goofyKeys.getPublic());
        txPayOneCoinToGoofy.addSignature(CryptoHelper.sign(scroogeKeys.getPrivate(), txPayOneCoinToGoofy.getRawDataToSign(0)), 0);

        TxHandler handler = new TxHandler(utxoPool);

        assertTrue(handler.isValidTx(txPayOneCoinToGoofy));
    }
}















