package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class Intro extends Activity{
	
	Handler handle;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_intro);
		handle = new Handler();
		handle.postDelayed(rIntent, 3000);
		
	}
	Runnable rIntent = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent Main = new Intent(Intro.this,Login.class);
			startActivity(Main);
			finish();
			
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	};
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		handle.removeCallbacks(rIntent);
	}
	
}
