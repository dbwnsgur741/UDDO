package com.example.customlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

    /*
    public void getThum(){
        getget();
    }
    */

    public void setThums(){

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

        /**** If exist 0 byte img --> delete() *****/
        File storageDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File file = new File( String.valueOf( storageDir ) );
        File list[] = file.listFiles();
        for(int i =0; i < list.length; i++) {
            if (list[i].length() == 0) {
                list[i].delete();
            }
        }
        setThums();

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
    }
}
