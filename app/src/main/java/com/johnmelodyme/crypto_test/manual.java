package com.johnmelodyme.crypto_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
public class manual extends AppCompatActivity {
    private static final String TAG = "ASYMMETRIC_ALGORITHM_RSA";
    private EditText Origianl_text;
    private Button Encode;
    private TextView Encoded_Message;
    Key publicKey, privateKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        Origianl_text = findViewById(R.id.original_text);
        Encode = findViewById(R.id.encode);
        Encoded_Message = findViewById(R.id.encoded_msg);

        Encode.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                // Generate key pair for 1024-bit RSA Encryption and Decryption:
                String algorithm, Decoded;
                algorithm = getResources().getString(R.string.RSA);
                Decoded = Origianl_text.getText().toString();
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
                Encoded_Message.setText(Base64.encodeToString(ENCODED_BYTES, Base64.DEFAULT));
            }
        });

        Encode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Encoded_Message.setText("");
                Origianl_text.setText("");
                disp("Clear");
                return false;
            }
        });
    }

    public void disp(String msg){
        Toast.makeText(manual.this, msg,
                Toast.LENGTH_SHORT)
                .show();
    }
}
