package com.example.customlistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Code_Detail_Activity extends AppCompatActivity {

    public int[] quiz_array= {996509,999910,924512,936802};
    public int[] point_array = {985911,967903,910204,995605,971206,952607,974508,988501};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.qr_code_detail );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        int insert_code = Integer.parseInt((result.getContents()));

        switch (insert_code){
            case 996509:
            case 999910:
            case 924512:
            case 936802:
                break;
            case 985911:
            case 967903:
            case 910204:
            case 995605:
            case 971206:
            case 952607:
            case 974508:
            case 988501:


        }


        super.onActivityResult(requestCode, resultCode, data);

    }
}
