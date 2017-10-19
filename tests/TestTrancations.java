import junit.framework.TestCase;

public class TestTrancations extends TestCase {
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
}