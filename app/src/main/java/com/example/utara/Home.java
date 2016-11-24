package com.example.utara;

import com.example.utara.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Home extends Activity {
	private Button menu3, menu4;
	Dialog about, petunjuk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
//		menu1=(Button)findViewById(R.id.imageExit);
//		menu1.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Exit();
//				
//			}
//		});
//		
		//menu2=(Button)findViewById(R.id.imageAbout);
//		menu2.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View a) {
//				about();
//			}
//		});
		
		menu3=(Button)findViewById(R.id.imageCara);
		menu3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View a) {
				Music.playOnce(Home.this, R.raw.fb);
				tampilTutorial();
				
			}
		});
		
		menu4=(Button)findViewById(R.id.imagePlay);
		menu4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View a) {
				Music.playOnce(Home.this, R.raw.fb);
				Intent k = new Intent(Home.this,MainActivity.class);
				startActivity(k);
			}
		});	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
	}
	
//	public void Exit(){
//		Exit  = new Dialog(Form1.this,android.R.style.Theme_Black_NoTitleBar);
//		Exit.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		Exit.setCancelable(true);
//		Exit.setContentView(R.layout.exit);
//		Button btnkeluar = (Button)Exit.findViewById(R.id.imageExitYes);
//		btnkeluar.setOnClickListener(new View.OnClickListener() {
//		
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Exit.dismiss();
//				System.exit(0);
//			}
//		});
//		
//		Button btnback = (Button)Exit.findViewById(R.id.imageExitNo);
//		btnback.setOnClickListener(new View.OnClickListener() {
//		
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Exit.dismiss();
//			}
//		});
//		Exit.show();
//	}
//	
//	public void about(){
//		about  = new Dialog(Form1.this,android.R.style.Theme_Black_NoTitleBar);
//		about.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		about.setCancelable(true);
//		about.setContentView(R.layout.about);
//		Button btnkeluar = (Button)about.findViewById(R.id.exit);
//		btnkeluar.setOnClickListener(new View.OnClickListener() {
//		
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				about.dismiss();
//			}
//		});
//		about.show();
//	}
	
	public void tampilTutorial(){
		petunjuk = new Dialog(Home.this,android.R.style.Theme_Black_NoTitleBar);
		petunjuk.requestWindowFeature(Window.FEATURE_NO_TITLE);
		petunjuk.setCancelable(true);
		petunjuk.setContentView(R.layout.caramain);
//		Button btnkeluar = (Button)caraMain.findViewById(R.id.exit2);
//		btnkeluar.setOnClickListener(new View.OnClickListener() {
//		
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				caraMain.dismiss();
//			}
//		});
		petunjuk.show();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Music.play(this, R.raw.chocobo);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//Music.stop(this);
		Music.pauseSong(this);
	}
}
