package com.example.compsci702;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.*;

import java.io.FileNotFoundException;
//import java.util.Base64;
import java.util.Random;

//import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    public String textTargetURI;
    public int height;
    public int pixel;
    public int pixel2;
    public int temp;
    public int width;
    public Bitmap bitmap;
    public Bitmap bitmapCopy;
    public int seed = 0;
    public int bound = 100;
    public int randomHeight;
    public int randomWidth;
    TextView Info;
    TextView saveURI;
    ImageView targetImage;
    EditText Seed;
    EditText Complexity;
    //add

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button LoadImage = (Button) findViewById(R.id.ImageSelect);
        Button Encrypt = (Button) findViewById(R.id.Encypt);
        Button SaveImage = (Button) findViewById(R.id.Save);
        Button Decrypt = (Button) findViewById(R.id.Decrypt);
        Info = (TextView) findViewById(R.id.Info);
        saveURI = (TextView) findViewById(R.id.SaveURI);
        targetImage = (ImageView) findViewById(R.id.ImageView);
        Seed = (EditText) findViewById(R.id.Seed);
        Complexity = (EditText) findViewById(R.id.Complexity);




            LoadImage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

                if (!hasRuntimePermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestRuntimePermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE, PERMISSION_REQUEST_CODE);
                } else {
                }
            }
        });


        Encrypt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                if (targetImage.getDrawable() != null) {
                    if (Seed.getText().toString().matches("")) {
                        seed = 0;
                        //Toast.makeText(MainActivity.this, "Seed is set to 0", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("YiLXzSxm2NBiPPNq/tMqzpHe6Erg/d6uLvrQkpXXWvQ="), Toast.LENGTH_SHORT).show();
                    } else {
                        seed = Integer.parseInt(Seed.getText().toString());
                    }
                    if (Complexity.getText().toString().matches("")) {
                        bound = 1;
                        //Toast.makeText(MainActivity.this, "Please enter scattering coefficient!! No encryption occurred.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("Jp/+gF3/gLAL+q9Q3mN3LhxAktG6YNo296+2VPxoWADMNdsvutLdOBVFMYd+G+ieWQaDUpLPU7d1NyAsn6+bhQ=="), Toast.LENGTH_SHORT).show();

                    } else if (Complexity.getText().toString().matches("0")) {
                        bound = 1;
                       // Toast.makeText(MainActivity.this, "0 scattering is not allowed! No encryption occurred.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("BYzAAXHcod8GwO17v+Cb+qkM11fa/fC9EGcPINnmz7MtGnnmFvSat28UODrcS4XoTEtVFX2KTKfcBiG6QiWS9w=="), Toast.LENGTH_SHORT).show();
                    } else {
                        bound = Integer.parseInt(Complexity.getText().toString());
                    }
                    Random r = new Random(seed);
                    for (int h = 1; h < height; h++) {
                        for (int w = 1; w < width; w++) {
                            randomHeight = (height * r.nextInt(bound)) / bound;
                            randomWidth = (width * r.nextInt(bound)) / bound;
                            pixel = bitmapCopy.getPixel(w, h);
                            pixel2 = bitmapCopy.getPixel(randomWidth, randomHeight);
                            temp = pixel;
                            pixel = pixel2;
                            pixel2 = temp;
                            bitmapCopy.setPixel(w, h, pixel);
                            bitmapCopy.setPixel(randomWidth, randomHeight, pixel2);
                        }
                    }
                    targetImage.setImageBitmap(bitmapCopy);
                    if (bound == 1 || Complexity.getText().toString().matches("")) {
                    } else {
                       // Toast.makeText(MainActivity.this, "Encrypted!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("hvvwEla9ExdcnYfBkIcEhw=="), Toast.LENGTH_SHORT).show();
                    }
                    //String info = "Height = " + height + " pixels\nWidth = " + width + " pixels\nLast Operation: Encrypt\nImage URI: " + textTargetURI;
                    String info = dLblMsg("ZyXcGGPmCy/5cY++HFlUYw==" )+ height + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg("xBxJC206npkuZB/DduzFVA==") + width + dLblMsg("+S40x5nEOY075bfTiyovPA==")+ dLblMsg("9JHa/Xbpi9ywNDQc3BgJyT5ovKMdJDOXLFqNRDp01HI=")+dLblMsg("KyK+ncvkcVsxzDoSsQ9HaQ==")+textTargetURI;
                    Info.setText(info);
                } else {
                    // Toast.makeText(MainActivity.this, "Please select an Image first!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this,dLblMsg("HVYtTlqNVYbuJE+75wtEcoevVTGPG1dsdDxKmrTdnYg="), Toast.LENGTH_SHORT).show();
                }
            }
        });


        SaveImage.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                FileOutputStream outStream = null;
                if (targetImage.getDrawable() != null) {

                    try {
                        File sdCard = Environment.getExternalStorageDirectory();
                        File dir = new File(sdCard.getAbsolutePath() + "/DCIM");
                        dir.mkdirs();

                        Random gen = new Random();
                        int n = 1000;
                        n = gen.nextInt(n);
                        //String photoname = "photo-" + n + ".jpg";
                        String photoname = dLblMsg("K4dOUfbSeDMfY2dce+pzIg==" )+ n + dLblMsg("Cl17NqDFHaUL9hgiidXvHw==");
                        String fileName = String.format(photoname);
                        //saveURI.setText("Image saved in: " + dir + "/DCIM \nImage name:" + fileName);
                        saveURI.setText(dLblMsg("tHwt+f1RMqj728WO3GrxlQ==") + dir + dLblMsg("TNJG007Aqi1416L1grVbBQ==")+ dLblMsg( "RhkPEBCUPL26K3od9wQc/A==") + fileName);
                        File outFile = new File(dir, fileName);
                        outStream = new FileOutputStream(outFile);
                        bitmapCopy.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                        outStream.flush();
                        outStream.close();
                        //Toast.makeText(MainActivity.this, "Image saved!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this,dLblMsg("tHwt+f1RMqj728WO3GrxlQ=="), Toast.LENGTH_SHORT).show();
                        //String info = "Height = " + height + " pixels\nWidth = " + width + " pixels\nLast Operation: Save Image\nImage URI: " + textTargetURI;
                        String info = dLblMsg("ZyXcGGPmCy/5cY++HFlUYw==") + height + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg( "xBxJC206npkuZB/DduzFVA==")+ width + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg("9JHa/Xbpi9ywNDQc3BgJyfcaml6vNC1Q6JNG8Mrz1LE=")+dLblMsg("KyK+ncvkcVsxzDoSsQ9HaQ==") + textTargetURI;
                        Info.setText(info);
                        addImageGallery(outFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //Toast.makeText(MainActivity.this, "FileNotFoundException", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("zZemGhXNeGx/5aXeCi/LfEQ56QcSxN6UQDTmn1mRBwA="), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        //Toast.makeText(MainActivity.this, "IOException", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("Mn2X6RaGT4AnQJ3+/ZY1AQ=="), Toast.LENGTH_SHORT).show();
                    } finally {
                    }
                } else {
                   // Toast.makeText(MainActivity.this, "Please select an Image first!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, dLblMsg("HVYtTlqNVYbuJE+75wtEcoevVTGPG1dsdDxKmrTdnYg="), Toast.LENGTH_SHORT).show();
                }
            }
        });


        Decrypt.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (targetImage.getDrawable() != null) {
                    if (Seed.getText().toString().matches("")) {
                        seed = 0;
                       // Toast.makeText(MainActivity.this, "Seed is set to 0", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("YiLXzSxm2NBiPPNq/tMqzpHe6Erg/d6uLvrQkpXXWvQ="), Toast.LENGTH_SHORT).show();
                    } else {
                        seed = Integer.parseInt(Seed.getText().toString());
                    }
                    if (Complexity.getText().toString().matches("")) {
                        bound = 1;
                        //Toast.makeText(MainActivity.this, "Please enter scattering coefficient! No decryption occurred.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("Jp/+gF3/gLAL+q9Q3mN3LhxAktG6YNo296+2VPxoWADwhi8R096sdOjDBMbHldQEMJ5NKkso9F5JWBp8wVXkVQ=="), Toast.LENGTH_SHORT).show();
                    } else if (Complexity.getText().toString().matches("0")) {
                        bound = 1;
                       // Toast.makeText(MainActivity.this, "0 scattering is not allowed! No decryption occurred.", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("BYzAAXHcod8GwO17v+Cb+qkM11fa/fC9EGcPINnmz7NtcOeaovlbBjTw1cYx+8PpTEtVFX2KTKfcBiG6QiWS9w=="), Toast.LENGTH_SHORT).show();
                    } else {
                        bound = Integer.parseInt(Complexity.getText().toString());
                    }
                    Random r = new Random(seed);


                    int[][] DecryptRandomHeight = new int[width][height];
                    int[][] DecryptRandomWidth = new int[width][height];

                    for (int h = 1; h < height; h++) {
                        for (int w = 1; w < width; w++) {
                            randomHeight = (height * r.nextInt(bound)) / bound;
                            randomWidth = (width * r.nextInt(bound)) / bound;
                            DecryptRandomHeight[w][h] = randomHeight;
                            DecryptRandomWidth[w][h] = randomWidth;

                        }
                    }
                    for (int h = height - 1; h >= 1; h--) {
                        for (int w = width - 1; w >= 1; w--) {
                            randomWidth = DecryptRandomWidth[w][h];
                            randomHeight = DecryptRandomHeight[w][h];
                            pixel = bitmapCopy.getPixel(w, h);
                            pixel2 = bitmapCopy.getPixel(randomWidth, randomHeight);
                            temp = pixel;
                            pixel = pixel2;
                            pixel2 = temp;
                            bitmapCopy.setPixel(w, h, pixel);
                            bitmapCopy.setPixel(randomWidth, randomHeight, pixel2);
                        }
                    }
                    targetImage.setImageBitmap(bitmapCopy);
                    if (bound == 1 || Complexity.getText().toString().matches("")) {
                    } else {
                        //Toast.makeText(MainActivity.this, "Decrypted!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, dLblMsg("kQhjqSSxsw8gIH0qKGFUTw=="), Toast.LENGTH_SHORT).show();
                    }
                    //String info = "Height = " + height + " pixels\nWidth = " + width + " pixels\nLast Operation: Decrypt\nImage URI: " + textTargetURI;
                    String info = dLblMsg("ZyXcGGPmCy/5cY++HFlUYw==") + height + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg( "xBxJC206npkuZB/DduzFVA==")+ width + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg("9JHa/Xbpi9ywNDQc3BgJyXNtdktpjiheONfUys+wdrU=")+dLblMsg("KyK+ncvkcVsxzDoSsQ9HaQ==") + textTargetURI;
                    Info.setText(info);
                } else {
                    //Toast.makeText(MainActivity.this, "Please select an Image first!", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, dLblMsg("HVYtTlqNVYbuJE+75wtEcoevVTGPG1dsdDxKmrTdnYg="), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean hasRuntimePermission(Context context, String runtimePermission) {
        boolean ret = false;

        int currentAndroidVersion = Build.VERSION.SDK_INT;

        if (currentAndroidVersion > Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(context, runtimePermission) == PackageManager.PERMISSION_GRANTED) {
                ret = true;
            }
        } else {
            ret = true;
        }
        return ret;
    }

    private void requestRuntimePermission(Activity activity, String runtimePermission, int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{runtimePermission}, requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                StringBuffer msgBuf = new StringBuffer();
                int grantResult = grantResults[0];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {

                } else {
                    //msgBuf.append("Permission denied : ");
                    msgBuf.append(dLblMsg("9ykufUOgCRXL/bftN5rU+vOm9XOa6ZBGchJ/0h88eCQ="));
                }

                if (permissions != null) {
                    int length = permissions.length;
                    for (int i = 0; i < length; i++) {
                        String permission = permissions[i];
                        msgBuf.append(permission);

                        if (i < length - 1) {
                            msgBuf.append(",");
                        }
                    }
                }

                Toast.makeText(getApplicationContext(), msgBuf.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri targetURI = data.getData();
            textTargetURI = targetURI.toString();
            try {

                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetURI));
                bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                targetImage.setImageBitmap(bitmap);
                height = bitmap.getHeight();
                width = bitmap.getWidth();
                //String info = "Height = " + height + " pixels\nWidth = " + width + " pixels\nLast Operation: Load Image\nImage URI: " + textTargetURI;
                String info = dLblMsg("ZyXcGGPmCy/5cY++HFlUYw==") + height + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg( "xBxJC206npkuZB/DduzFVA==")+ width + dLblMsg("+S40x5nEOY075bfTiyovPA==")+dLblMsg("9JHa/Xbpi9ywNDQc3BgJyYjwarqGc5c4UidJDZiNWpk=")+dLblMsg("KyK+ncvkcVsxzDoSsQ9HaQ==") + textTargetURI;
                Info.setText(info);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
           // Toast.makeText(MainActivity.this, "Image loaded Successfully", Toast.LENGTH_SHORT).show();
            Toast.makeText(MainActivity.this, dLblMsg("RYt4TQPhjAqf3AJ7ET1Q7OHN6z32+UPafC1mPSKg2U4="), Toast.LENGTH_SHORT).show();
        }
    }

    private void addImageGallery(File file) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg"); // setar isso
        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    //added by vikram
    public String dLblMsg(String str1) {
        Cipher dcipher= null;
        String strAESKey0 = "APIProjectGroup4";
        String strAESKeyO = "";
        //dummy code
        Key key;

        KeyGenerator keyGen= null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        key=keyGen.generateKey();
        keyGen.init(128);

        // Convert the encrypted key from AESkey
        try{
            SecretKeySpec skeySpec = new SecretKeySpec(strAESKey0.getBytes("UTF-8"), "AES");
            dcipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            dcipher.init(Cipher.DECRYPT_MODE,skeySpec);
            byte[] b = dcipher.doFinal(android.util.Base64.decode("ntGlDxPaBqyi14AENfA8BceWuPpEhcvuNSqJAvk8/gs=",0));
            strAESKeyO = new String(b);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        dcipher=null;

        //Decrypt the label from the secretkey (CompSci702012019)
        try {
            SecretKeySpec skeySpec0 = new SecretKeySpec(strAESKeyO.getBytes("UTF-8"),"AES");
            dcipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            dcipher.init(Cipher.DECRYPT_MODE,skeySpec0);

           byte[] b2=dcipher.doFinal(android.util.Base64.decode(str1,0));

           String str3 = new String(b2);
           return str3;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}