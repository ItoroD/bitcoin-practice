package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.Utils;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;
import org.bitcoinj.script.ScriptOpCodes;

import java.util.HashMap;
import java.util.Map;

public class Main {

    private void decodeRawHexUsingBitcoinj(String rawTxHex){ //question 1

        // Network parameters
        NetworkParameters params = MainNetParams.get(); // use TestNet3Params.get() for testnet

        // Decode raw transaction hex to bytes
        byte[] rawTxBytes = Utils.HEX.decode(rawTxHex);

        // Parse transaction
        Transaction tx = new Transaction(params, rawTxBytes);

// Print transaction details
        System.out.println("Version: " + tx.getVersion());
        System.out.println("Inputs: " + tx.getInputs());
        System.out.println("Outputs: " + tx.getOutputs());
        System.out.println("Locktime: " + tx.getLockTime());
    }

    private void showRawHexTransactionParts(String rawTxHex){ //question 1
        String version = rawTxHex.substring(0, 8);
        String input = rawTxHex.substring(8, 72);
        String output = rawTxHex.substring(72, rawTxHex.length() - 16);
        String timelock = rawTxHex.substring(rawTxHex.length() - 16);

        System.out.println("Version: " + version);
        System.out.println("Input: " + input);
        System.out.println("Output: " + output);
        System.out.println("Timelock: " + timelock);
    }

    private Map<String, Object> showRawHexTransactionPartsFull(String rawTxHex){ //question 1
        Map<String, Object> transaction = new HashMap<>();

        transaction.put("version", rawTxHex.substring(0, 8));
        transaction.put("marker", Integer.parseInt(rawTxHex.substring(8, 10), 16));
        transaction.put("flag", Integer.parseInt(rawTxHex.substring(10, 12), 16));
        transaction.put("tx_in_count", Integer.parseInt(rawTxHex.substring(12, 14), 16));
        transaction.put("tx_in", rawTxHex.substring(14, 96));
        transaction.put("tx_out_count", Integer.parseInt(rawTxHex.substring(96, 98), 16));
        transaction.put("tx_out", rawTxHex.substring(98, 246));
        transaction.put("segwit", rawTxHex.substring(246, 378));
        //transaction.put("lock_time", Integer.parseInt(rawTxHex.substring(378, 386), 16));
        transaction.put("lock_time", rawTxHex.substring(378, 386));

//        String hexValue = (String) transaction.get("version");
//        String littleEndianHex = convertToLittleEndian(hexValue);
//        BigInteger version = new BigInteger(littleEndianHex, 16);
        String version = (String) transaction.get("version");
        System.out.println("Version : "+ version);
        String txIn = (String) transaction.get("tx_in");
        System.out.println("Tx input : "+ txIn);
        String txOut = (String) transaction.get("tx_out");
        System.out.println("Tx output : "+ txOut);
        String lockTime = (String) transaction.get("lock_time");
        System.out.println("Lock time : "+ lockTime);

        return transaction;
    }

    private String convertToLittleEndian(String hexValue){
        StringBuilder littleEndian = new StringBuilder();
        for (int i = hexValue.length() - 2; i >= 0; i -= 2) {
            littleEndian.append(hexValue, i, i + 2);
        }
        return littleEndian.toString();
    }

    private void decodeOpScriptHex(String hexString){// question 2
        byte[] bytes = Utils.HEX.decode(hexString);
        Script script = new Script(bytes);
        System.out.println(script);
    }

    private void buildRedeemScript(String preimageHex){// question 3
        byte[] preimage = Utils.HEX.decode(preimageHex);

        // Compute lock_hex
        Sha256Hash lockHex = Sha256Hash.of(preimage);

        // Build redeem script
        ScriptBuilder builder = new ScriptBuilder();
        builder.op(ScriptOpCodes.OP_SHA256);
        builder.data(lockHex.getBytes());
        builder.op(ScriptOpCodes.OP_EQUAL);
        Script redeemScript = builder.build();

        // Print redeem script in hex format
        System.out.println(Utils.HEX.encode(redeemScript.getProgram()));
    }


    public static void main(String[] args) {
        Main tx = new Main();
        //Question1
        // Raw transaction hex
        String rawTxHex = "020000000001010ccc140e766b5dbc884ea2d780c5e91e4eb77597ae64288a42575228b79e234900000000000000000002bd37060000000000225120245091249f4f29d30820e5f36e1e5d477dc3386144220bd6f35839e94de4b9cae81c00000000000016001416d31d7632aa17b3b316b813c0a3177f5b6150200140838a1f0f1ee607b54abf0a3f55792f6f8d09c3eb7a9fa46cd4976f2137ca2e3f4a901e314e1b827c3332d7e1865ffe1d7ff5f5d7576a9000f354487a09de44cd00000000"; // replace with your raw transaction hex
        System.out.println(tx.showRawHexTransactionPartsFull(rawTxHex));




        System.out.println();
        System.out.println();
        System.out.println("QUESTION 2");
        //QUESTION 2
        String hexString = "010101029301038801027693010487";
        tx.decodeOpScriptHex(hexString);

        System.out.println();
        System.out.println();
        System.out.println("QUESTION 3");
        //QUESTION 3
        // Preimage
        String preimageHex = "427472757374204275696c64657273";
        tx.buildRedeemScript(preimageHex);

    }
}