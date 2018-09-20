package co.kr.newp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

public class Login extends Activity {
EditText ed1,ed2;
Button btn1;
TextView tx1,tx2;
SharedPreferencesUtil spu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		spu=new SharedPreferencesUtil(getApplicationContext());
		setbutton();
		setbuttonexe();
		if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED&&ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_COARSE_LOCATION},0);

		}
		else{

		}

		
		
		
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		for(int i=0;i<permissions.length;i++){
			if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
				Toast.makeText(this, "해당 앱을 사용하려면 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	private void setbuttonexe() {
		// TODO Auto-generated method stub
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(ed1.getText().toString().trim()!=null &&ed2.getText().toString().trim()!=null && !ed1.getText().toString().trim().equals("") && !ed2.getText().toString().trim().equals("") 
							){
					NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/member_confirm.php",getApplicationContext());
				ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				ns.add(new BasicNameValuePair("id", ed1.getText().toString().trim()));
				ns.add(new BasicNameValuePair("pass", ed2.getText().toString().trim()));
				String a;
				try {
					a = nw.getStringByPOST(ns);
					if(a.equals("1")){
					
						Toast.makeText(getApplicationContext(), "로그인 성공", 1000).show();
						G.islogin=true;
						
					spu.put("id", ed1.getText().toString().trim());
					spu.put("password",ed2.getText().toString().trim());
							
						
						Intent j=new Intent(getApplicationContext(), Mystory.class);
						
						startActivity(j);
						finish();
						
						
					}
					else if(a.equals("2")){
						Toast.makeText(getApplicationContext(), "패쓰워드가 틀립니다", 1000).show();
						
					}
					else if(a.equals("3")){
						new AlertDialog.Builder(Login.this)
						   .setTitle("id가 가입되어있지 않습니다.")
						   .setMessage("가입하시겠습니까?")
						   .setPositiveButton("예", new DialogInterface.OnClickListener() {
						    
						    public void onClick(DialogInterface dialog, int which) {
						    	startActivity(new Intent(Login.this,Member_join.class));
						    	
						    	
						    }
						   })
						   
						   .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
						    
						    public void onClick(DialogInterface dialog, int which) {
						
						    	 dialog.dismiss();
						    	
						    }
						   })
						   .show();
						
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
					
				}
				else{
				Toast.makeText(getApplicationContext(), "입력하지않은항목이있거나 잘못된 입력이있습니다", 1000).show();
				}
				
			}
		});
		tx1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(),Lost_pass.class));
			}
		});
		tx2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Member_join.class));	
			}
		});
		
	}
	private void setbutton() {
		// TODO Auto-generated method stub
		ed1=(EditText) findViewById(R.id.login_ed1);
		ed2=(EditText) findViewById(R.id.login_ed2);
		btn1=(Button) findViewById(R.id.login_btn1);
		tx1=(TextView) findViewById(R.id.login_tx1);
		tx2=(TextView) findViewById(R.id.login_tx2);

		ed1.setText("a");
		ed2.setText("1");
		
	}
}
