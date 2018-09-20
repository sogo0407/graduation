package co.kr.newp;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import co.kr.newp.Mystory.WebGetImage;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Story2 extends Activity {
ImageView img1;
SharedPreferencesUtil spu;
RelativeLayout rel1,rel2;
TextView tx1,tx2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story2);
		spu=new SharedPreferencesUtil(getApplicationContext());
		img1=(ImageView) findViewById(R.id.img1);
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		tx1.setText("이름 "+spu.getValue("name", ""));
		tx2.setText("ID  "+spu.getValue("id", ""));

		try {
			WebGetImage wg=new WebGetImage();
			Bitmap s=wg.execute("http://sogo4070.dothome.co.kr/profile/"+spu.getValue("id", "")+".jpg").get();
			Matrix mat=new Matrix();
			s=Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mat, true);
			if(s!=null)
			img1.setImageBitmap(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		findViewById(R.id.rel2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Login.class));
			spu.put("id", "");
			spu.put("name", "");
			spu.put("password", "");
			finish();
			}
		});
		
	findViewById(R.id.rel3).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Bye.class));
			
			}
		});
	}
	
	  class WebGetImage extends AsyncTask<String, Void, Bitmap> {
		  
		    @Override
		    protected Bitmap doInBackground(String... params) {
		      // 네트워크에 접속해서 데이터를 가져옴
		      
		      try {
		        //웹사이트에 접속 (사진이 있는 주소로 접근)
		        URL Url = new URL(params[0]);
		        // 웹사이트에 접속 설정
		        URLConnection urlcon = Url.openConnection();
		        // 연결하시오
		        urlcon.connect();
		        // 이미지 길이 불러옴
		        int imagelength = urlcon.getContentLength();
		        // 스트림 클래스를 이용하여 이미지를 불러옴
		        BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);
		        // 스트림을 통하여 저장된 이미지를 이미지 객체에 넣어줌
		        Bitmap bit = BitmapFactory.decodeStream(bis);
		        return bit;
		      } catch (Exception e) {
		        e.printStackTrace();
		        return null;
		      }
		     
		    }

		    @Override
		    protected void onPostExecute(Bitmap result) {
		    // TODO Auto-generated method stub
		    super.onPostExecute(result);
		    }
	  }
}
