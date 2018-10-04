package co.kr.newp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import  android.content.pm.Signature;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

public class Login extends Activity {

	EditText ed1, ed2;
	Button btn1, mSendBtn;
	TextView tx1, tx2;
	SharedPreferencesUtil spu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		//희진추가 - 카카오링크호출
		mSendBtn = (Button) findViewById(R.id.btnSend);
		mSendBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendLink();
			}
		});

		getAppKeyHash(); //희진추가 - 키해시 로그찍기

		spu = new SharedPreferencesUtil(getApplicationContext());
		setbutton();
		setbuttonexe();
		if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);

		} else {
		}
	}

	//카카오링크 호출함수
	private void sendLink(){
		FeedTemplate params = FeedTemplate
				.newBuilder(ContentObject.newBuilder("Graduation으로 초대합니다",
						"https://i.imgur.com/3BNSSB2.png",
						LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
								.setMobileWebUrl("https://developers.kakao.com").build())
						.setDescrption("즐거운 여행기! 추억을 남겨보아요:)")
						.build())
				.addButton(new ButtonObject("앱에서 보기", LinkObject.newBuilder()
						.setWebUrl("'https://developers.kakao.com")
						.setMobileWebUrl("'https://developers.kakao.com")
						.setAndroidExecutionParams("key1=value1")
						.setIosExecutionParams("key1=value1")
						.build()))
				.build();

		Map<String, String> serverCallbackArgs = new HashMap<String, String>();
		serverCallbackArgs.put("user_id", "${current_user_id}");
		serverCallbackArgs.put("product_id", "${shared_product_id}");

		KakaoLinkService.getInstance().sendDefault(this, params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
			@Override
			public void onFailure(ErrorResult errorResult) {
				Logger.e(errorResult.toString());
			}

			@Override
			public void onSuccess(KakaoLinkResponse result) {
				// 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
			}
		});
	}

	//희진 추가함수 - 키해시 알아내기(카카오에 필요)
	private void getAppKeyHash() {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md;
				md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				String something = new String(Base64.encode(md.digest(), 0));
				Log.d("Hash key", something);
			}
		} catch (Exception e) {
// TODO Auto-generated catch block
			Log.e("name not found", e.toString());
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
