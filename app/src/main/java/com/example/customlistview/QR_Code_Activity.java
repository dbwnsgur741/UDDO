package com.example.customlistview;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;

import java.util.Arrays;
import java.util.List;

public class QR_Code_Activity extends AppCompatActivity {

    private Button scan_btn;
    private BarcodeView scanner_view;
    private String[] quiz_array= {"999910","924512","936802","966509"};
    private String[] point_array = {"985911","967903","910204","995605","971206","952607","974508","988501"};
    public boolean flag = true;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_code_default);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        setSupportActionBar(toolbar);

        scan_btn = (Button)findViewById(R.id.scanner_btn);
        scanner_view = (BarcodeView) findViewById(R.id.qr_scanner);
        scanner_view.decodeContinuous( callback );


        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                    scanner_view.setVisibility( View.VISIBLE );
                    scan_btn.setHint( "취소하기" );
                    flag = false;
                }
                else{
                    scanner_view.setVisibility( View.INVISIBLE );
                    scan_btn.setHint( "해독하기" );
                    flag = true;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        Intent intent ;
        @Override
        public void barcodeResult(BarcodeResult result) {
            if(Arrays.asList(quiz_array).contains( result.getText() )){
                intent = new Intent(getApplicationContext(),QR_Code_Quiz_Activity.class);
                intent.putExtra( "Quiz_Num",result.getText() );
                scanner_view.setVisibility( View.INVISIBLE );
                flag = true;
                scan_btn.setHint( "해독하기" );
                startActivityForResult( intent , 5000 );
            }
            else if(Arrays.asList(point_array).contains( result.getText() )){
                Log.d("@@@@@@@@", String.valueOf( result.getText() ) );
            }
            else{
                Log.d("##########", String.valueOf( result.getText() ) );
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
}
