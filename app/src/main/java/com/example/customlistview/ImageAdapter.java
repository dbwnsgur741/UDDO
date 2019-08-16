package com.example.customlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.io.File;


public class ImageAdapter extends BaseAdapter  {
    private Context mContext;
    private Integer tt;
    private static Bitmap[] mThumbIds = new Bitmap[12];
    private ImageView imageView;
    private Matrix matrix;

    public void getThum(){
        getget();
        /*
        for(File tempFile : files){
            Log.d("MyTag",tempFile.getName());
            if(tempFile.getName() != null){
                String temp = tempFile.getName();
                temp = temp.substring( 0,1 );
                tt = Integer.parseInt( temp );
            }
        }
        */
        /*
        File file = new File(mContext.getCacheDir().toString());
        File[] files = file.listFiles();
        for(int i =0; i < files.length; i++){
            if(files[i].getName().toString() !=null){
                String temp = files[i].getName().toString();
                temp = temp.substring( 0,1 );
                tt = Integer.parseInt( temp );
                Bitmap bitmap1 = (Bitmap) BitmapFactory.decodeFile( String.valueOf( files[i] ) );
                mThumbIds[tt] = bitmap1;
            }
        }
        */
    }

    public void getget(){

        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File( String.valueOf( storageDir ) );
        File list[] = file.listFiles();
        for(int i =0; i < list.length; i++){
            if(list[i].getName() !=null){
                String temp = list[i].getName();
                String[] temp2 = temp.split( "_" );
                int aa = Integer.parseInt( temp2[0] );
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 16;
                matrix = new Matrix(  );
                matrix.postRotate( 90 );
                Bitmap bitmap1 = (Bitmap)BitmapFactory.decodeFile( String.valueOf( list[i] ),options );
                bitmap1 = Bitmap.createBitmap( bitmap1,0,0,bitmap1.getWidth(),bitmap1.getHeight(),matrix,true );
                mThumbIds[aa] = bitmap1;
            }
        }
    }


    public ImageAdapter(Context c){
        mContext = c;
        getget();
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        imageView = new ImageView( mContext );
        if(mThumbIds[position] != null){
            imageView.setImageBitmap( mThumbIds[position] );
        }
        imageView.setBackgroundColor( Color.DKGRAY );
        imageView.setScaleType( ImageView.ScaleType.CENTER_CROP );
        imageView.setLayoutParams( new GridView.LayoutParams(300,300) );
        return imageView;
    }
}
