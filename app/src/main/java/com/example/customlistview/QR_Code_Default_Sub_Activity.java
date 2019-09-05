package com.example.customlistview;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import java.util.Arrays;
import java.util.List;

public class QR_Code_Default_Sub_Activity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private Button scan_btn;
    private BarcodeView scanner_view;
    private final String[] QR_CODE= {"924512","936802","966509","999910"};
    private String[] point_array = {"985911","967903","910204","995605","971206","952607","974508","988501"};
    public boolean flag = true;
    private SharedPreferences sharedPreferences;
    private RelativeLayout relativeLayout;
    private TextView textView;
    private EditText editText;
    private Intent intent ;
    private String result;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_default_sub );

        /******* Layout setting *******/
        /// Toolbar setting ////
        Toolbar toolbar = (Toolbar) findViewById(R.id.qr_code_default_sub_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        /// End Of Toolbar setting ////
        textView = (TextView)findViewById( R.id.qr_set_scanner_textview );
        scan_btn = (Button)findViewById(R.id.scanner_btn);
        scanner_view = (BarcodeView) findViewById(R.id.qr_scanner);
        relativeLayout = (RelativeLayout)findViewById( R.id.qr_set_scanner_layout );
        editText = (EditText)findViewById( R.id.qr_code_default_sub_editText );

        /******* Layout setting *******/

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );

        /******* EVENT setting *******/

        scanner_view.decodeContinuous( callback );

        relativeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    textView.setVisibility( View.GONE );
                    scanner_view.setVisibility( View.VISIBLE );
                    flag = false;
                }else{
                    scanner_view.setVisibility( View.INVISIBLE );
                    textView.setVisibility( View.VISIBLE );
                    flag = true;
                }
            }
        } );

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = editText.getText().toString();
                if(checkArray(result)){
                    if(Arrays.asList(QR_CODE).contains(result)){
                        intent = new Intent(getApplicationContext(),QR_Code_Quiz_Activity.class);
                        intent.putExtra( "Quiz_Num",result );
                        scanner_view.setVisibility( View.INVISIBLE );
                        flag = true;
                        startActivity( intent );
                        finish();
                    }else{
                        Log.d("!!!!!!!!!!!!!!!!!!!!!",result);
                        Toast.makeText(getApplicationContext(),"잘못된 관리번호입니다.",Toast.LENGTH_LONG).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(),"이미 푼 문제입니다!\n다른 QR코드를 찾아주세요!",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
        /******* End Of EVENT setting *******/
    }
        //TODO : QR 코드 사진 기능 구현 완료 ... QR 코드 번호로 넘겨줄 경우 구현해야함

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            if(checkArray( result.getText() )){

                if(Arrays.asList(QR_CODE).contains( result.getText() )){
                    intent = new Intent(getApplicationContext(),QR_Code_Quiz_Activity.class);
                    intent.putExtra( "Quiz_Num",result.getText() );
                    scanner_view.setVisibility( View.INVISIBLE );
                    flag = true;
                    startActivity( intent );
                    finish();
                }
                else if(Arrays.asList(point_array).contains( result.getText() )){
                    Log.d("@@@@@@@@", String.valueOf( result.getText() ) );
                }
                else{
                    Log.d("##########", String.valueOf( result.getText() ) );
                }
            }

            else{
                Toast.makeText(getApplicationContext(),"이미 푼 문제입니다!\n다른 QR코드를 찾아주세요!",Toast.LENGTH_LONG).show();
                finish();
            }

        }
        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };

    @Override
    protected void onResume() {
        if(scanner_view == null){
            super.onResume();
        }
        else{
            scanner_view.resume();
            super.onResume();
        }
    }

    @Override
    protected void onPause() {
        if(scanner_view == null){
            super.onPause();
        }
        else{
            scanner_view.pause();
            super.onPause();
        }
    }

    protected boolean checkArray(String temp){
        int qr_array_index = Arrays.binarySearch( QR_CODE,temp );
        qr_array_index = (int) Math.pow( 2,qr_array_index );
        int code_check = sharedPreferences.getInt( "qr_code_manager",0 );
        byte a = (byte)(qr_array_index & code_check);
        if(a == 0 ){
            return true;
        }
        else{
            return false;
        }
    }
}
