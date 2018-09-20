package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Bye extends Activity {
Button btn1,btn2;
EditText ed1,ed2;
SharedPreferencesUtil spu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bye);
		ed1=(EditText) findViewById(R.id.ed1);
		ed2=(EditText) findViewById(R.id.ed2);
		btn2=(Button) findViewById(R.id.btn1);
		
		spu=new SharedPreferencesUtil(getApplicationContext());
		
		
		findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			if(ed1.getText().toString().equals(ed2.getText().toString()) && !ed1.equals("") &&ed1.getText().toString().equals(spu.getValue("password", ""))){
				NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/user_del.php", getApplicationContext());
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));
				String s;
				try {
					s = nw.getStringByPOST(ns);
					
//					if(s.equals("1")){
						Toast.makeText(getApplicationContext(), "정상적으로 탈퇴되었습니다", 1000).show();
						spu.put("name", "");
						spu.put("id", "");
						spu.put("password", "");
						startActivity(new Intent(getApplicationContext(),Login.class));
						finish();
//					}
//					else{
//						Toast.makeText(getApplicationContext(), "문제가 발생하였습니다.", 1000).show();
//					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
					
				
				
				
				
				
			}
			else{
				Toast.makeText(getApplicationContext(), "비밀번호가 같지않습니다", 1000).show();
			}
			
		
			}
		});
		
		
		
		
	}
}
