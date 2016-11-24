package com.example.utara;

import com.example.utara.R;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
//import android.graphics.PixelFormat;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {
	
	/** The OpenGL view */
	private GLSurfaceView glSurfaceView;
	Dialog popup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		// requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		

        // Initiate the Open GL view and
        // create an instance with this activity
        glSurfaceView = new ESSurfaceView(this);
        
        
      //glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        //glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //glSurfaceView.setBackgroundResource(R.drawable.splash);
       // glSurfaceView.setZOrderMediaOverlay(true);
        //glSurfaceView.setZOrderOnTop(true);
        
        //glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        //glSurfaceView.setBackgroundResource(R.drawable.splash);
       // glSurfaceView.setZOrderOnTop(true);
        

        
        // set our renderer to be the main renderer with
        // the current activity context
        //glSurfaceView.setRenderer(new ESRender());
        setContentView(glSurfaceView);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}
	
//	public void popup(){
//		popup  = new Dialog(MainActivity.this,android.R.style.Theme_Translucent);
//		popup.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		popup.setCancelable(true);
////		popup.setContentView(R.layout.exit);
////		Button btnkeluar = (Button)popup.findViewById(R.id.imageExitYes);
////		btnkeluar.setOnClickListener(new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			popup.dismiss();
//			System.exit(0);
//		}
//		});
//			
//		Button btnback = (Button)popup.findViewById(R.id.imageExitNo);
//		btnback.setOnClickListener(new View.OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			popup.dismiss();
//		}
//		});
//		popup.show();
//	}
	
	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		Music.play(this, R.raw.medley8bit);
		glSurfaceView.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		//Music.stop(this);
		Music.pauseSong(this);
		glSurfaceView.onPause();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}