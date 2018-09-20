package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Lost_pass extends Activity {
EditText ed1,ed2,ed3,ed4,ed5;
Button btn1,btn2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lost_pass);
		setbutton();
		setbuttonexe();
		
	
	}

	private void setbuttonexe() {
		// TODO Auto-generated method stub
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(ed1.getText()!=null&&ed2.getText()!=null &&!ed1.getText().toString().trim().equals("")&&!ed1.getText().toString().trim().equals("")){
			NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/lost_id.php"	, getApplicationContext());
			ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("name", ed1.getText().toString().trim()));
			ns.add(new BasicNameValuePair("email", ed2.getText().toString().trim()));
			try {
				String a=nw.getStringByPOST(ns);
				
				new AlertDialog.Builder(Lost_pass.this)
				   .setTitle("확인")
				   .setMessage("분실한 ID는   "+a+ "  입니다.")
				   
				   .setNegativeButton("확인", new DialogInterface.OnClickListener() {
				    
				    public void onClick(DialogInterface dialog, int which) {
				
				    	 dialog.dismiss();
				    	
				    }
				   })
				   .show();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
				
				
			}
		});
		
		
btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			if(ed3.getText()!=null&&ed4.getText()!=null&&ed5.getText()!=null  &&!ed3.getText().toString().trim().equals("")&&!ed4.getText().toString().trim().equals("")&&!ed5.getText().toString().trim().equals("")){
			NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/lost_pass.php"	, getApplicationContext());
			ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", ed3.getText().toString().trim()));
			ns.add(new BasicNameValuePair("name", ed4.getText().toString().trim()));
			ns.add(new BasicNameValuePair("email", ed5.getText().toString().trim()));
			try {
				String a=nw.getStringByPOST(ns);
				new AlertDialog.Builder(Lost_pass.this)
				   .setTitle("확인")
				   .setMessage("분실한 비밀번호는   "+a+ "  입니다.")
				   
				   .setNegativeButton("확인", new DialogInterface.OnClickListener() {
				    
				    public void onClick(DialogInterface dialog, int which) {
				
				    	 dialog.dismiss();
				    	
				    }
				   })
				   .show();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
				
				
			}
		});
		
	}

	private void setbutton() {
		// TODO Auto-generated method stub
		 ed1=(EditText) findViewById(R.id.lost_pass_ed1);
		 ed2=(EditText) findViewById(R.id.lost_pass_ed2);
		 ed3=(EditText) findViewById(R.id.lost_pass_ed3);
		 ed4=(EditText) findViewById(R.id.lost_pass_ed4);
		 ed5=(EditText) findViewById(R.id.lost_pass_ed5);
		 btn1=(Button) findViewById(R.id.lost_pass_btn1);
		 btn2=(Button) findViewById(R.id.lost_pass_btn2);
		
	}
	

}
