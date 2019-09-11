package com.example.customlistview;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopupActivity extends AppCompatActivity implements View.OnTouchListener {

    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;

    private TextView txtText;
    private AppCompatImageView appCompatImageView;
    private boolean isImageFitToScreen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //타이틀바 없애기
        supportRequestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_popup );

        View view = getLayoutInflater().inflate( R.layout.popup_fullscreen, null );
        final Dialog dialog = new Dialog( this, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen );
        dialog.setContentView( view );
        view.setOnTouchListener( this );
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    dialog.show();
                } else {
                    isImageFitToScreen = true;
                    dialog.dismiss();
                }
            }
        };
        appCompatImageView = (AppCompatImageView) findViewById( R.id.txtText );
        appCompatImageView.setOnClickListener( clickListener );
        TextView popupTitle = (TextView)findViewById(R.id.mapTitle);
        popupTitle.setOnClickListener(clickListener);

        /*
        appCompatImageView = (AppCompatImageView)findViewById( R.id.txtText );
        appCompatImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    appCompatImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    appCompatImageView.setAdjustViewBounds(true);
                }else{
                    isImageFitToScreen=true;
                    appCompatImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    appCompatImageView.setScaleType( ImageView.ScaleType.FIT_XY);
                }
            }
            });
       */
    }

    //확인 버튼 클릭
    public void mOnClose(View v) {
        //데이터 전달하기
        Intent intent = new Intent();
        intent.putExtra( "result", "Close Popup" );
        setResult( RESULT_OK, intent );

        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        view.setScaleType( ImageView.ScaleType.MATRIX );
        float scale;

        dumpEvent( event );
        // Handle touch events here...

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set( matrix );
                start.set( event.getX(), event.getY() );
                Log.d( TAG, "mode=DRAG" ); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d( TAG, "mode=NONE" );
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing( event );
                Log.d( TAG, "oldDist=" + oldDist );
                if (oldDist > 5f) {
                    savedMatrix.set( matrix );
                    midPoint( mid, event );
                    mode = ZOOM;
                    Log.d( TAG, "mode=ZOOM" );
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {
                    matrix.set( savedMatrix );
                    matrix.postTranslate( event.getX() - start.x, event.getY() - start.y ); // create the transformation in the matrix  of points
                } else if (mode == ZOOM) {
                    // pinch zooming
                    float newDist = spacing( event );
                    Log.d( TAG, "newDist=" + newDist );
                    if (newDist > 5f) {
                        matrix.set( savedMatrix );
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale( scale, scale, mid.x, mid.y );
                    }
                }
                break;
        }

        view.setImageMatrix( matrix ); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event) {
        float x = event.getX( 0 ) - event.getX( 1 );
        float y = event.getY( 0 ) - event.getY( 1 );
        return (float) Math.sqrt( x * x + y * y );
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX( 0 ) + event.getX( 1 );
        float y = event.getY( 0 ) + event.getY( 1 );
        point.set( x / 2, y / 2 );
    }

    /**
     * Show an event in the LogCat view, for debugging
     */
    private void dumpEvent(MotionEvent event) {
        String names[] = {"DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?"};
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append( "event ACTION_" ).append( names[actionCode] );

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
            sb.append( "(pid " ).append( action >> MotionEvent.ACTION_POINTER_ID_SHIFT );
            sb.append( ")" );
        }

        sb.append( "[" );
        for (int i = 0; i < event.getPointerCount(); i++) {
            sb.append( "#" ).append( i );
            sb.append( "(pid " ).append( event.getPointerId( i ) );
            sb.append( ")=" ).append( (int) event.getX( i ) );
            sb.append( "," ).append( (int) event.getY( i ) );
            if (i + 1 < event.getPointerCount())
                sb.append( ";" );
        }

        sb.append( "]" );
        Log.d( "Touch Events ---------", sb.toString() );
    }
}