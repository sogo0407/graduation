package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
//나의 이야기
public class Mystory extends Activity {
	ImageView img2,img3,img4,img5,imgtop;
	TextView tx1,txtop;
	SharedPreferencesUtil spu;
	ArrayList<Data_content> arr;
	GridView grid1;
	Gridadapter gridadt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mystory);
		tx1=(TextView) findViewById(R.id.main_profile_tx1);
		txtop=(TextView) findViewById(R.id.main_profile_txtop);
		img2=(ImageView) findViewById(R.id.main_profile_img2);
		img3=(ImageView) findViewById(R.id.main_profile_img3);
		img4=(ImageView) findViewById(R.id.main_profile_img4);
		img5=(ImageView) findViewById(R.id.main_profile_img5);
		imgtop=(ImageView) findViewById(R.id.main_profile_imgtop);
		spu=new SharedPreferencesUtil(getApplicationContext());
		grid1=(GridView) findViewById(R.id.mystory_grid1);
		

			 
		
		
				}
	
	//oncreate
					
				//처음 화면에서 이미지가져오기(이미지가 존재할 경우)

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

			  HttpURLConnection.setFollowRedirects(false);

			  /** HTTP 요청 메소드 SET
			   * 본 예제는 파일의 존재여부만 확인하려니 간단히 HEAD 요청을 보냄
			   * HEAD요청에 대해 웹서버는 수정된 시간이 포함된 리소스의 해더 정보를 간단히 리턴
			   *  GET,POST,HEAD,OPTIONS,PUT,DELETE,TRACE 값등이 올 수 있다.
			   * 디폴트는 GET
			   **/
			  HttpURLConnection con = (HttpURLConnection) Url.openConnection();
			  con.setRequestMethod("HEAD");
			  if(con.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND){
				  return null;
			  }



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
		NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_profile.php",getApplicationContext());
		ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));
		
			
			JSONArray jso;
			try {
				jso = nw.getJSONArrayByPOST(ns);
				for(int i=0;i<jso.length();i++){
					JSONObject jo=jso.getJSONObject(i);
					txtop.setText(jo.getString("name"));
					spu.put("name", jo.getString("name"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//프로필 이름 넣기
			
			setbtnexe();
			
			
			
			
		
		
			
			
			
			 arr=new ArrayList<Data_content>();
				try {
					WebGetImage wg=new WebGetImage();
					Bitmap s=wg.execute("http://sogo4070.dothome.co.kr/profile/"+spu.getValue("id", "")+".jpg").get();
					if(s==null) {


					}
					else {
						Matrix mat = new Matrix();
						s = Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mat, true);
						imgtop.setImageBitmap(s);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//프로필 사진 넣기
				  nw=new NetWork("http://sogo4070.dothome.co.kr/get_content.php"	,getApplicationContext());
				 ns=new ArrayList<NameValuePair>();
//				 ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));
				 ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));
				 
						
				 try{
					 JSONArray jsonarr=null;
					 
					 jsonarr=nw.getJSONArrayByPOST(ns);
					 for(int i=0;i<jsonarr.length();i++){
						
						 JSONObject jsonob=jsonarr.getJSONObject(i);
						  nw=new NetWork("http://sogo4070.dothome.co.kr/get_choice.php"	,getApplicationContext());
							 ns=new ArrayList<NameValuePair>();
							 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));
							 String z=nw.getStringByPOST(ns);
							 Log.e("z",z);
							 nw=new NetWork("http://sogo4070.dothome.co.kr/get_reply.php"	,getApplicationContext());
							 ns=new ArrayList<NameValuePair>();
							 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));
							 String q=nw.getStringByPOST(ns);
							 Log.e("q",z);
						arr.add(new Data_content(jsonob.getString("_num").toString(),jsonob.getString("_writer").toString(),jsonob.getString("_date").toString(), jsonob.getString("_title").toString(),jsonob.getString("_place").toString(), jsonob.getString("_pic").toString(), jsonob.getString("_text").toString(),z,q));
								 
					 } 
					// 나의글 검색후 가져오기
					 gridadt=new Gridadapter(getApplicationContext(), arr);
					 grid1.setAdapter(gridadt);
					 // 붙이기
					 
					 
					 }
				 
					 
					 
				 catch(Exception e){
					 
				 }
				 
				 
		
				 
				 
					grid1.setOnItemClickListener(new OnItemClickListener() {
						//그리드뷰 클릭시 나오는 화면
									@Override
									public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
											long arg3) {
										G.arr.add(arr.get(arg2));
										Log.e("aa", arg2+"");
										startActivity(new Intent(Mystory.this,Main_content.class));
										
										
									}
									
				 
				});
					
		
	}
	  private void setbtnexe() {
			// TODO Auto-generated method stub
			tx1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Main_profile.class));
				finish();
				
				}
			});
			img3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getApplicationContext(),Story.class));
					finish();
				}
			});
			
			img4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Write.class));

					
				}
			});
img5.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Setting.class));
					
				}
			});
			
		}
	  
	  
}
