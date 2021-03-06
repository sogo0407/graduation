package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class Member_join extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8;
	ImageView img1;
	RelativeLayout rel1,rel2;
	Button btn1;
	int sex=0;
//0일떄 중립 1일떄 남자 2일때 여자

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_join);
		setbutton();
		setbuttonexe();
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

				if(ed2.getText().toString().trim().equals(ed3.getText().toString().trim())==false){
					Log.e("ed2", ed2.getText().toString());
					Log.e("ed3", ed3.getText().toString());

					Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show();
					return;
				}


				if(ed1.getText().toString().trim()!=null&&ed2.getText().toString().trim()!=null &&ed3.getText().toString().trim()!=null &&ed4.getText().toString().trim()!=null&&ed5.getText().toString().trim()!=null && ed8.getText().toString().trim()!=null &&ed6.getText().toString().trim()!=null&&ed7.getText().toString().trim()!=null  && !ed1.getText().toString().trim().equals("") && !ed2.getText().toString().trim().equals("")
						&& !ed3.getText().toString().trim().equals("")&& !ed4.getText().toString().trim().equals("")&& !ed5.getText().toString().trim().equals("") &&!ed8.getText().toString().trim().equals("") && !ed6.getText().toString().trim().equals("")&& !ed7.getText().toString().trim().equals("")
						&& sex!=0       ){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/member_insert.php",getApplicationContext());
					ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
					ns.add(new BasicNameValuePair("id", ed1.getText().toString().trim()));
					ns.add(new BasicNameValuePair("password", ed2.getText().toString().trim()));
					ns.add(new BasicNameValuePair("name", ed4.getText().toString().trim()));
					ns.add(new BasicNameValuePair("email", ed5.getText().toString().trim()));
					ns.add(new BasicNameValuePair("phone_num", ed8.getText().toString().trim()));  //새롬추가

					ns.add(new BasicNameValuePair("birth", ed6.getText().toString().trim()));
					ns.add(new BasicNameValuePair("sex", sex+""));
					ns.add(new BasicNameValuePair("profile", ed7.getText().toString().trim()));

					try {
						String a=nw.getStringByPOST(ns);
						if("1".equals(a)){
							Toast.makeText(getApplicationContext(), "가입 완료 되었습니다.", Toast.LENGTH_SHORT).show();
							finish();
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				else{
					Toast.makeText(getApplicationContext(), "잘못 입력 되었습니다.", Toast.LENGTH_SHORT).show();
				}

			}
		});


		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

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
		ed8=(EditText) findViewById(R.id.member_join_ed8);
		ed6=(EditText) findViewById(R.id.member_join_ed6);
		ed7=(EditText) findViewById(R.id.member_join_ed7);
		img1=(ImageView) findViewById(R.id.member_join_date);
		rel1=(RelativeLayout) findViewById(R.id.member_join_rel1);
		rel2=(RelativeLayout) findViewById(R.id.member_join_rel2);
		btn1=(Button) findViewById(R.id.member_join_btn_confirm);

	}
}