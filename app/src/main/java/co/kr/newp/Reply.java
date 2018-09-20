package co.kr.newp;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Reply extends Activity {
ListView li1;
Button btn1;
SharedPreferencesUtil spu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		
		spu=new SharedPreferencesUtil(getApplicationContext());
		li1=(ListView) findViewById(R.id.reply_li1);
		btn1=(Button) findViewById(R.id.reply_btn1);
		Listadapter adp=new Listadapter(Reply.this, G.arr_reply);
		li1.setAdapter(adp);
		
		
		
findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
btn1.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		final EditText ed1=new EditText(getApplicationContext());
		ed1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ed1.setTextSize(16);
		
		new AlertDialog.Builder(Reply.this)
		.setTitle("답글을 입력하세요")
		.setView(ed1)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				 NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/insert_reply.php"	,getApplicationContext());
				 ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
				 ns.add(new BasicNameValuePair("user_id", spu.getValue("id", "")));
				 ns.add(new BasicNameValuePair("id", G.arr.get(G.arr.size()-1).getNum()));
				 ns.add(new BasicNameValuePair("text", ed1.getText().toString().trim()));
				 Toast.makeText(getApplicationContext(), "답글이 등록되었습니다", 1000).show();
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
	}
	
	
	
}
