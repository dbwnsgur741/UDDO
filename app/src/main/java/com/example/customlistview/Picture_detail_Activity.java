package com.example.customlistview;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class Picture_detail_Activity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();
    private AppCompatButton button;
    private final static int TAKE_PICTURE = 1;
    private int position;
    private Intent intent;
    private Context context;
    private ImageView imageView;
    private String imageFilePath;
    private String photoUri;
    private final int REQUEST_TAKE_PHOTO = 2;
    private Matrix matrix;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.picture_mission_detail);

        context = this;

        matrix = new Matrix(  );

        Toolbar toolbar = (Toolbar)findViewById( R.id.picture_mission_detail_toolbar );
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        intent = getIntent();
        position = intent.getExtras().getInt( "position" );
        TextView textView = (TextView) findViewById(R.id.picture_mission_detail_TextView);
        imageView = (ImageView) findViewById( R.id.picture_mission_detail_ImageView );

        switch (position) {
            case 0:
                textView.setText("팀원이 모두 나오게 단체사진을 찍으시오.");
                break;
            case 1:
                textView.setText("사진에 나오는 천우각을 찾아가 팀원들과 함께  만세를 하는 사진을 찍으시오.(천우각이 나오고 팀원들이 만세하는 사진이 나와야 성공 인정)");
                imageView.setImageResource(R.drawable.img31);
                break;
            case 2:
                textView.setText("아래에 나오는 사진은 옥인동 윤씨가옥의 내부 사진이다. 사진에 나오는 배경과 똑같은 곳에서 손가락으로 하트 만들어서 사진찍기\n");
                imageView.setImageResource(R.drawable.img32);
                break;
            case 3:
                textView.setText("아래의 사진에 나오는 사진의 가옥은 재기동 해풍부원군 윤택영 재실이다. 해당 안내판을 찾아가서 안내판 읽는 팀원의 사진을 찍으시오.\n");
                imageView.setImageResource(R.drawable.img33);
                break;
            case 4:
                textView.setText("보이는 사진은 관훈동 민씨 가옥이다. 관훈동 민씨 가옥 마당에서 팀원끼리 닭싸움 하는 사진을 찍으시오. ");
                imageView.setImageResource(R.drawable.img34);
                break;
            case 5:
                textView.setText("삼선동 오위장 김춘영의 가옥을 찾아가서 구경하던 관람객과 같이 기념사진을 찍어라.");
                imageView.setImageResource(R.drawable.img35);
                break;
            case 6:
                textView.setText("삼각동 도편수 이승엽 가옥을 찾아서 안채 내부가 나오도록 사진찍기 ");
                imageView.setImageResource(R.drawable.img36);
                break;
            case 7:
                textView.setText("타임캡슐 광장에서 팀원들과 함께 제일 자신있는 포즈로 사진찍기");
                imageView.setImageResource(R.drawable.img36);
                break;
            case 8:
                textView.setText("한복입은 외국인과 함께 사진 찍기");
                break;
            case 9:
                textView.setText("팀원들과 함께 점프샷 찍기(발이 땅에 닿으면 실패!)");
                break;
        }
        button = (AppCompatButton) findViewById( R.id.picture_mission_detail_btn );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent camera = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( camera,TAKE_PICTURE );
                */
                dispatchTakePictureIntent();
            }
        });

        // 6.0 마쉬멜로우 이상일 경우에는 권한 체크 후 권한 요청
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission( Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    // 권한 요청
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult( requestCode, resultCode, intent );

        /* 내부 캐시 디렉토리 저장소 이용할 때

        if(requestCode == TAKE_PICTURE){
            Bitmap bitmap = (Bitmap) intent.getExtras().get( "data" );
            if(bitmap ==null){
                return;
            }
            else{
                savePicture( bitmap );
                Intent intent1 = new Intent(getApplicationContext(),Picture_default_Activity.class);
                setResult( 2222,intent1 );
                finish();
            }

        }
        */

        if(requestCode == REQUEST_TAKE_PHOTO){
            Intent intent1 = new Intent( getApplicationContext(),Picture_default_Activity.class );
            setResult(2222,intent1);
            finish();

        }
        else{
            Intent intent1 = new Intent( getApplicationContext(),Picture_default_Activity.class );
            startActivity(intent1);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    /* : 내부 캐시 디렉토리 저장소 이용할 때
    protected void savePicture(Bitmap bm){
        String name = String.valueOf( position );
        File storage = getCacheDir();
        String fileName = name +".JPEG";
        File tempFile = new File( storage,fileName );

        try {
            // 자동으로 빈 파일을 생성합니다.
            tempFile.createNewFile();
            // 파일을 쓸 수 있는 스트림을 준비합니다.
            FileOutputStream out = new FileOutputStream(tempFile);
            // 스트림 사용후 닫아줍니다.
            bm.compress( Bitmap.CompressFormat.JPEG, 100, out );
            out.close();
        } catch (FileNotFoundException e) {
            Log.e("MyTag","FileNotFoundException : " + e.getMessage());
        } catch (IOException e) {
            Log.e("MyTag","IOException : " + e.getMessage());
        }
    }
    */

    protected void setThumbs() {

        File storageDir = context.getExternalFilesDir( Environment.DIRECTORY_PICTURES );
        File file = new File( String.valueOf( storageDir ) );
        File list[] = file.listFiles();
        for (int i = 0; i < list.length; i++) {
            if (list[i].getName() != null) {
                String temp = list[i].getName();
                String temp2[] = temp.split( "_" );
                int tt = Integer.parseInt( temp2[0] );
                matrix.postRotate( 180 );
                Bitmap bitmap1 = (Bitmap) BitmapFactory.decodeFile( String.valueOf( list[i] ) );
                bitmap1 = Bitmap.createBitmap( bitmap1,0,0,bitmap1.getWidth(),bitmap1.getHeight(),matrix,true );
                if (position == tt) {
                    imageView.setImageBitmap( bitmap1 );
                    imageView.setBackgroundResource( 0 );
                }
            }
        }
    }

    private File createImageFile() throws IOException{
        String timeStamp ="AAAA";
        String imageFileName = position + "_" + timeStamp;
        //TODO : 인덱스가 같은 파일 이름이 있는지 확인 후 있으면 temp file 에다가 이름 저장 없으면 null로 초기화
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile( imageFileName,".jpg",storageDir );
        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private void checkSameIndexImage() {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File( String.valueOf( storageDir ) );
        File list[] = file.listFiles();
        for(int i =0; i<list.length; i++){
            if(list[i].getName() !=null){
                String temp = list[i].getName();
                String[] temp2 = temp.split( "_" );
                if(Integer.parseInt( temp2[0] ) == position){
                    list[i].delete();
                }
                /*
                if(position < 10) {
                    temp = temp.substring( 0, 1 );
                    int tt = Integer.parseInt( temp );
                    if (position == tt) {
                        list[i].delete();
                    }
                }else{
                        temp = temp.substring( 0,2 );
                        int aa = Integer.parseInt( temp );
                        if(position == aa){
                            list[i].delete();
                        }
                    }
                */
                }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.d("@@@@@@@@@@@@",  photoFile.getName() );
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                checkSameIndexImage();
                Uri photoURI = FileProvider.getUriForFile(this,"com.example.customlistview.fileprovider",photoFile);
                Log.d("!!!!!!!!!!!!!!!", String.valueOf( photoURI ) );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setThumbs();
    }
}
