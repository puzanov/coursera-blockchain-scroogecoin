import java.util.Arrays;

public class TxHandler {

    /**
     * Creates a public ledger whose current UTXOPool (collection of unspent transaction outputs) is
     * {@code utxoPool}. This should make a copy of utxoPool by using the UTXOPool(UTXOPool uPool)
     * constructor.
     */

    public UTXOPool utxoPool;

    public TxHandler(UTXOPool utxoPool) {
        this.utxoPool = utxoPool;
    }

    /**
     * @return true if:
     * (1) all outputs claimed by {@code tx} are in the current UTXO pool, 50%
     * (2) the signatures on each input of {@code tx} are valid, 100%
     * (3) no UTXO is claimed multiple times by {@code tx},
     * (4) all of {@code tx}s output values are non-negative, and
     * (5) the sum of {@code tx}s input values is greater than or equal to the sum of its output
     *     values; and false otherwise.
     */
    public boolean isValidTx(Transaction tx) {

        boolean txOutputsInCurrentPool = false;
        boolean isSignaturesValid =  false;

        // TODO: should br replaced with all inputs method (now checks only 0 index)
        for (int i = 0; i < utxoPool.getAllUTXO().size(); i++) {
            if (Arrays.equals(utxoPool.getAllUTXO().get(i).getTxHash(), tx.getInput(0).prevTxHash)) {
                txOutputsInCurrentPool = true;
            }
        }

        for (int i = 0; i < tx.numInputs(); i++) {
            if (!Crypto.verifySignature(utxoPool.getTxOutput(new UTXO(tx.getInput(i).prevTxHash, tx.getInput(i).outputIndex)).address, tx.getRawDataToSign(i), tx.getInput(i).signature)) {
                break;
            }
            isSignaturesValid = true;
        }



        return txOutputsInCurrentPool && isSignaturesValid;
    }

    /**
     * Handles each epoch by receiving an unordered array of proposed transactions, checking each
     * transaction for correctness, returning a mutually valid array of accepted transactions, and
     * updating the current UTXO pool as appropriate.
     */
    public Transaction[] handleTxs(Transaction[] possibleTxs) {
        // IMPLEMENT THIS
        return null;
    }

}
