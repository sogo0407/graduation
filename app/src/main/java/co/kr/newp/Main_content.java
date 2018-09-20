package co.kr.newp;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_content extends Activity {
TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7;
ImageView img1,img2,img3;
int ischoice;
		SharedPreferencesUtil spu;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_content);
		tx1=(TextView) findViewById(R.id.main_content_tx1);
		tx2=(TextView) findViewById(R.id.main_content_tx2);
		tx3=(TextView) findViewById(R.id.main_content_tx3);
		tx4=(TextView) findViewById(R.id.main_content_tx4);
		tx5=(TextView) findViewById(R.id.main_content_tx5);
		tx6=(TextView) findViewById(R.id.main_content_tx6);
		tx7=(TextView) findViewById(R.id.main_content_tx7);

		img1=(ImageView) findViewById(R.id.main_content_img1);
		img2=(ImageView) findViewById(R.id.main_content_img2);
		img3=(ImageView) findViewById(R.id.main_content_img3);
		spu=new SharedPreferencesUtil(getApplicationContext());
	
		
		
		
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		firstset();
		
	}
	private void firstset() {
		// TODO Auto-generated method stub
		
		Log.e("pic", G.arr.get(G.arr.size()-1).getPic());
		
		if(!spu.getValue("id", "").equals(G.arr.get(G.arr.size()-1).getWriter())){
			tx6.setVisibility(View.GONE);
			tx7.setVisibility(View.GONE);
		}
	//수정 삭제 안보이게
		((TextView)findViewById(R.id.top_text)).setText(G.arr.get(G.arr.size()-1).getWriter()+"의 여행기");
		
		tx1.setText(G.arr.get(G.arr.size()-1).getTitle());
		tx2.setText(G.arr.get(G.arr.size()-1).getDate());
		tx3.setText(G.arr.get(G.arr.size()-1).getWriter());
		tx4.setText(G.arr.get(G.arr.size()-1).getPlace());
		tx5.setText(G.arr.get(G.arr.size()-1).getText());
		
		tx7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Main_content.this)
				.setTitle("정말로 삭제 하시겠습니까")
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						 NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/content_del.php"	,getApplicationContext());
						 ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
						 
						 ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
						 Log.e("num",G.arr.get(G.arr.size()-1).getNum());
						 
						 Toast.makeText(getApplicationContext(), "글이 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
						 startActivity(new Intent(getApplicationContext(),Mystory.class));
							finish();
						 try {
							nw.getStringByPOST(ns);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
								
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
				
			}
		});
		tx6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 startActivity(new Intent(getApplicationContext(),Write_modify.class));
			}
		});
		
		img2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(G.ischoice==1){
				NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/ischoice_del.php",getApplicationContext());
				ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
				ns.add(new BasicNameValuePair("user_id", spu.getValue("id", "")));
				try {
					String s=nw.getStringByPOST(ns);
					if(s.equals("1")){
						onResume();
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				}
				else if(G.ischoice==0){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/ischoice_ins.php",getApplicationContext());
					ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
					ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
//					ns.add(new BasicNameValuePair("user_id", G.arr.get(G.arr.size()-1).getWriter()));
					ns.add(new BasicNameValuePair("user_id",spu.getValue("id", "")));
					try {
						String s=nw.getStringByPOST(ns);
						if(s.equals("0")){
							onResume();
							
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				
			}
		});
		img3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_replylist.php",getApplicationContext());
				ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
					
				
				 try{
					 JSONArray jsonarr=null;
					 G.arr_reply=new ArrayList<Data_reply>();
					 jsonarr=nw.getJSONArrayByPOST(ns);
					 
					 for(int i=0;i<jsonarr.length();i++){
						
						 JSONObject jsonob=jsonarr.getJSONObject(i);
						  G.arr_reply.add(new Data_reply(jsonob.getString("user_id").toString(), jsonob.getString("text").toString()));
								 
					 } 
					// 나의글 검색후 가져오기
					 
					 
					 // 붙이기
					 
					 
					 
				 }
				 catch(Exception e){
					 
				 }
				 
				 startActivity(new Intent(getApplicationContext(),Reply.class));
				
			}
		});
		
		
		
		
		
		
		
		
		
		
		try {
			WebGetImage wg=new WebGetImage();
			Bitmap s;
			s = wg.execute("http://sogo4070.dothome.co.kr/content/"+G.arr.get(G.arr.size()-1).getPic()).get();
			Matrix mt=new Matrix();
			s=Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mt, true);
			img1.setImageBitmap(s);
			if(s!=null)
			img1.setImageBitmap(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	  @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();

			
			
			NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/ischoice.php",getApplicationContext());
			ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
			ns.add(new BasicNameValuePair("user_id", spu.getValue("id", "")));
			try {
				String s=nw.getStringByPOST(ns);
				if(s.equals("1")){
					img2.setImageResource(R.drawable.choice2);
					G.ischoice=1;
				}
				else{
					img2.setImageResource(R.drawable.choice1);
					G.ischoice=0;
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.e("G.choice",G.ischoice+"");
		}
}
