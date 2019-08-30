package com.example.customlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.io.File;

import static android.content.Context.MODE_PRIVATE;


public class ImageAdapter extends BaseAdapter  {
    private Context mContext;
    private Integer tt;
    private static Bitmap[] mThumbIds = new Bitmap[10];
    private ImageView imageView;
    private Matrix matrix;
    private SharedPreferences sharedPreferences;

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
                int size = bitmap1.getWidth()>bitmap1.getHeight() ? bitmap1.getHeight() : bitmap1.getWidth();
                bitmap1 = Bitmap.createBitmap( bitmap1,0,0,size,size,matrix,true );
                mThumbIds[aa] = bitmap1;
            }
        }
    }


    public ImageAdapter(Context c){
        mContext = c;
        sharedPreferences = mContext.getSharedPreferences("NamSan",MODE_PRIVATE);
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
        View gridViewAndroid = null;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        if(convertView == null){
            gridViewAndroid = new View(mContext);
            gridViewAndroid = layoutInflater.inflate( R.layout.gridview_below_imageview,null );
            int temp = (int) Math.pow( 2,position );
            int temp2 = sharedPreferences.getInt( "picture_mission_manager",0 );
            byte a = (byte)(temp & temp2);
            if(mThumbIds[position] != null){
                ImageView imageView1 = (ImageView)gridViewAndroid.findViewById( R.id.android_gridview_image );
                ImageView imageView2 = (ImageView)gridViewAndroid.findViewById( R.id.gridview_image2 );
                imageView1.setImageBitmap( mThumbIds[position] );
                if(a!=0){
                    imageView2.setImageResource( R.drawable.clear );
                }else{
                    imageView2.setImageResource( R.drawable.unclear );
                }
            }else{
                ImageView imageView1 = (ImageView)gridViewAndroid.findViewById( R.id.android_gridview_image );
                ImageView imageView2 = (ImageView)gridViewAndroid.findViewById( R.id.gridview_image2 );
                imageView1.setImageResource( R.drawable.noimage );
                imageView2.setImageResource( R.drawable.unclear );
            }
        }else{
            gridViewAndroid = (View)convertView;
        }

        return gridViewAndroid;
        /*
        imageView = new ImageView( mContext );
        if(mThumbIds[position] != null){
            imageView.setImageBitmap( mThumbIds[position] );
        } else {
            imageView.setImageResource(R.drawable.noimage);

        }
        imageView.setScaleType( ImageView.ScaleType.FIT_XY );
        imageView.setAdjustViewBounds(true);
        imageView.setLayoutParams( new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT) );
        return imageView;
        */
    }
}
