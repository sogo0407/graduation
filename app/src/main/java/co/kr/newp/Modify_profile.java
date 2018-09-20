package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Modify_profile extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
	ImageView img1;
	RelativeLayout rel1,rel2;
	Button btn1;
	int sex=0;
	SharedPreferencesUtil spu;
	//0일떄 중립 1일떄 남자 2일때 여자

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_modify_profile);
			spu=new SharedPreferencesUtil(getApplicationContext());
			setbutton();
			setbuttonexe();
			
			Intent i=getIntent();	
			ed4.setText(i.getStringExtra("name").toString().trim());
			ed6.setText(i.getStringExtra("birth").toString().trim());
			ed7.setText(i.getStringExtra("profile").toString().trim());
			rel1.setBackgroundColor(Color.GRAY);
			rel2.setBackgroundColor(Color.parseColor("#50ffffff"));
			sex=1;
			if(i.getStringExtra("sex").equals("여")){
				rel2.setBackgroundColor(Color.GRAY);
				rel1.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=2;
			}
			
			
		}
		private void setbuttonexe() {
			// TODO Auto-generated method stub
			rel1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				rel1.setBackgroundColor(Color.GRAY);
				rel2.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=1;
				}
			});
			rel2.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				rel2.setBackgroundColor(Color.GRAY);
				rel1.setBackgroundColor(Color.parseColor("#50ffffff"));
				sex=2;
				}
			});
			
			btn1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				
					
					
					if(ed4.getText().toString().trim()!=null&&ed6.getText().toString().trim()!=null&&ed7.getText().toString().trim()!=null && !ed4.getText().toString().trim().equals("")&& !ed6.getText().toString().trim().equals("")&& !ed7.getText().toString().trim().equals("")
				        && sex!=0 			){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/member_modify.php",getApplicationContext());
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));
				//ns.add(new BasicNameValuePair("password", ed2.getText().toString().trim()));
				ns.add(new BasicNameValuePair("name", ed4.getText().toString().trim()));
				//ns.add(new BasicNameValuePair("email", ed5.getText().toString().trim()));
				ns.add(new BasicNameValuePair("birth", ed6.getText().toString().trim()));
				ns.add(new BasicNameValuePair("sex", sex+""));
				ns.add(new BasicNameValuePair("profile", ed7.getText().toString().trim()));
				
				
				try {
					String a=nw.getStringByPOST(ns);
					if(a.equals("1")){
						Toast.makeText(getApplicationContext(), "수정이 완료되었습니다.", 1000).show();
						startActivity(new Intent(getApplicationContext(),Main_profile.class));
						finish();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
					else{
						Toast.makeText(getApplicationContext(), "잘못 입력된 부분이있습니다", 1000).show();
					}
		
		}
		});
			
			
			findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
				 startActivity(new Intent(getApplicationContext(),Main_profile.class));
					finish();
				}
			});
			
			img1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					startActivityForResult(new Intent(getApplicationContext(),Timepick.class),1);
				}
			});
			
		}
		
		@Override
			protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				// TODO Auto-generated method stub
				super.onActivityResult(requestCode, resultCode, data);
				if(resultCode==1){
					String x=data.getStringExtra("content");
					ed6.setText(x);
				}
				
			}
		
		private void setbutton() {
			// TODO Auto-generated method stub
			ed1=(EditText) findViewById(R.id.member_join_ed1);
			ed2=(EditText) findViewById(R.id.member_join_ed2);
			ed3=(EditText) findViewById(R.id.member_join_ed3);
			ed4=(EditText) findViewById(R.id.member_join_ed4);
			ed5=(EditText) findViewById(R.id.member_join_ed5);
			ed6=(EditText) findViewById(R.id.member_join_ed6);
			ed7=(EditText) findViewById(R.id.member_join_ed7);
			img1=(ImageView) findViewById(R.id.member_join_date);
			rel1=(RelativeLayout) findViewById(R.id.member_join_rel1);
			rel2=(RelativeLayout) findViewById(R.id.member_join_rel2);
			btn1=(Button) findViewById(R.id.member_join_btn_confirm);
			
		}
	}

