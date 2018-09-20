package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Story extends Activity {
	ArrayList<Data_content> arr;
	GridView grid1;
	Gridadapter gridadt;
	ImageView img1,img2,img3,img4,img5;
	TextView tx1,tx2,tx3;
	EditText ed1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_story);
	
		ed1=(EditText) findViewById(R.id.ed1);
		img1=(ImageView) findViewById(R.id.img1);
		grid1=(GridView) findViewById(R.id.grid1);
		tx1=(TextView) findViewById(R.id.tx1);
		tx2=(TextView) findViewById(R.id.tx2);
		tx3=(TextView) findViewById(R.id.tx3);
		img2=(ImageView) findViewById(R.id.img2);
		img3=(ImageView) findViewById(R.id.img3);
		img4=(ImageView) findViewById(R.id.img4);
		img5=(ImageView) findViewById(R.id.img5);
		
		setbtnexe();
		
		
		
		
			 
	
			 
			 
				grid1.setOnItemClickListener(new OnItemClickListener() {
					//그리드뷰 클릭시 나오는 화면
								@Override
								public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
										long arg3) {
									G.arr.add(arr.get(arg2));
			Log.e("aa", arg2+"");
									startActivity(new Intent(Story.this,Main_content.class));
									
									
								}
								
			 
			});
				
				img1.performClick();
			}
	
	 private void setbtnexe() {
			// TODO Auto-generated method stub
		 tx1.setOnClickListener(new OnClickListener(
				 ) {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Story.class));
				finish();
				
			}
		});
		 tx2.setOnClickListener(new OnClickListener(
		 ) {

			 @Override
			 public void onClick(View v) {
				 // TODO Auto-generated method stub
				 startActivity(new Intent(getApplicationContext(),Activity_Calendar.class));


			 }
		 });
		 tx3.setOnClickListener(new OnClickListener(
		 ) {

			 @Override
			 public void onClick(View v) {
				 // TODO Auto-generated method stub
				 startActivity(new Intent(getApplicationContext(),Activity_map.class));


			 }
		 });
		 img3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getApplicationContext(),Story.class));
				}
			});
		 
			img4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
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
img2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Mystory.class));
					
				}
			});

img1.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
					// TODO Auto-generated method stub
		arr=new ArrayList<Data_content>();
		
		NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/get_content_search.php"	,getApplicationContext());
		ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
			 ns.add(new BasicNameValuePair("id", ed1.getText().toString().trim()));
					
			 try{
				 JSONArray jsonarr=null;
				 
				 jsonarr=nw.getJSONArrayByPOST(ns);
				 for(int i=0;i<jsonarr.length();i++){
					
					 JSONObject jsonob=jsonarr.getJSONObject(i);
					  nw=new NetWork("http://sogo4070.dothome.co.kr/get_choice.php"	,getApplicationContext());
						 ns=new ArrayList<NameValuePair>();
						 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));
						 String z=nw.getStringByPOST(ns);
						 Log.e("z",jsonob.getString("_num"));
						 nw=new NetWork("http://sogo4070.dothome.co.kr/get_reply.php"	,getApplicationContext());
						 ns=new ArrayList<NameValuePair>();
						 ns.add(new BasicNameValuePair("id", jsonob.getString("_num")));
						 String q=nw.getStringByPOST(ns);
						 Log.e("q",jsonob.getString("_num"));
					arr.add(new Data_content(jsonob.getString("_num").toString(),jsonob.getString("_writer").toString(),jsonob.getString("_date").toString(), jsonob.getString("_title").toString(),jsonob.getString("_place").toString(), jsonob.getString("_pic").toString(), jsonob.getString("_text").toString(),z,q));
					 Log.e("arr",arr.get(0).getWriter()); 
				 } 
				// 나의글 검색후 가져오기
				 
				 
				 gridadt=new Gridadapter(getApplicationContext(), arr);
				 grid1.setAdapter(gridadt);
				 // 붙이기
				 
				 
				 }
			 
				 
				 
			 catch(Exception e){
				 
			 }
			 
		
	}
});
			
		}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		img1.performClick();
	}
	  
}
