package com.example.customlistview;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;


public class Admin_Login_Activity extends AppCompatActivity {

    private final String ADMIN = "1";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextInputEditText textInputEditText;
    private AppCompatButton button;
    private String admin_status;
    private String inputText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.admin_login );

        sharedPreferences = getSharedPreferences( "NamSan",MODE_PRIVATE );
        editor = sharedPreferences.edit();
        admin_status = sharedPreferences.getString( "Admin","" );

        checkLogin();

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = String.valueOf( textInputEditText.getText() );
                if(inputText.equals( ADMIN )){
                    adminLogin(inputText);
                    finish();
                }else{
                    adminLogout();
                    finish();
                }
            }
        });
    }

    private void adminLogout(){
        editor.putString( "Admin","" );
        editor.commit();
    }

    private void adminLogin(String text){
        editor.putString( "Admin",text );
        editor.commit();
    }

    private void checkLogin(){
        button = findViewById( R.id.admin_login_btn );
        textInputEditText = findViewById( R.id.admin_login_editText );

        if(admin_status ==""){
            textInputEditText.setVisibility( View.VISIBLE );
            button.setText( "로그인 하기" );
        }else{
            textInputEditText.setVisibility( View.GONE );
            button.setText( "로그아웃하기" );
        }
    }
}



/* If use fragment , extends fragment.
    public Admin_Login_Activity(){
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.admin_login,container,false );

    }
    */
