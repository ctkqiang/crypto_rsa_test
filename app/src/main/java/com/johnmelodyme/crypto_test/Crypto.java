package com.johnmelodyme.crypto_test;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
/**
 * @Author: John Melody Melissa
 * @Project: Cryptography
 * @Inpired : By GF TAN SIN DEE <3
 */
public class Crypto extends AppCompatActivity {
    private static final String TAG = "ASYMMETRIC_ALGORITHM_RSA";
    private TextView Original, Encoded;
    private String Decoded;
    Key publicKey, privateKey;

    @SuppressLint({"SetTextI18n", "LongLogTag"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Declaration:
        Original = findViewById(R.id.OriginalText);
        Encoded = findViewById(R.id.Encoded);
        Decoded = getResources().getString(R.string.decoded);
        publicKey = null;
        privateKey = null;

        // Original Text
        Original.setText("Original:" + "\n" + Decoded);

        // Generate key pair for 1024-bit RSA Encryption and Decryption:
        String algorithm;
        algorithm = getResources().getString(R.string.RSA);

        try {
            KeyPair keyPair;
            KeyPairGenerator keyPairGenerator;

            keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(0x400);
            keyPair = keyPairGenerator.genKeyPair();
            publicKey = keyPair.getPublic();
            privateKey = keyPair.getPrivate();
            Log.w(TAG, "CRYPTO:" + "RSA key status" + "========> {1}");
        } catch (NoSuchAlgorithmException exception) {
            Log.w(TAG, "CRYPTO:" + "RSA key pair error" + exception);
        }

        // Encode the original data with RSA private key:
        byte [] ENCODED_BYTES = null;
        try {
            Cipher cipher;
            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            ENCODED_BYTES = cipher.doFinal(Decoded.getBytes());
        }  catch (Exception exception) {
            Log.w(TAG, "CRYPTO:" + "RSA key pair error {Cipher}" + exception);
        }

        Encoded.setText("Encoded:" + "\n" + Base64.encodeToString(ENCODED_BYTES, Base64.DEFAULT));
    }
}