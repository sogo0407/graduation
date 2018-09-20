package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Main_profile extends Activity {
ImageView img1,img2,img3,img4,img5,imgtop;
TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,txtop;
SharedPreferencesUtil spu;
final int REQ_CODE_SELECT_IMAGE=100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_profile);
		tx1=(TextView) findViewById(R.id.main_profile_tx1);
		tx2=(TextView) findViewById(R.id.main_profile_tx2);
		tx3=(TextView) findViewById(R.id.main_profile_tx3);
		tx4=(TextView) findViewById(R.id.main_profile_tx4);
		tx5=(TextView) findViewById(R.id.main_profile_tx5);
		tx6=(TextView) findViewById(R.id.main_profile_tx6);
		tx7=(TextView) findViewById(R.id.main_profile_tx7);
		txtop=(TextView) findViewById(R.id.main_profile_txtop);
		img1=(ImageView) findViewById(R.id.main_profile_img1);
		img2=(ImageView) findViewById(R.id.main_profile_img2);
		img3=(ImageView) findViewById(R.id.main_profile_img3);
		img4=(ImageView) findViewById(R.id.main_profile_img4);
		img5=(ImageView) findViewById(R.id.main_profile_img5);
		imgtop=(ImageView) findViewById(R.id.main_profile_imgtop);
		spu=new SharedPreferencesUtil(getApplicationContext());
		
		
		NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_profile.php",getApplicationContext());
		ArrayList<NameValuePair>	ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("id", spu.getValue("id", "")));
		
			
			JSONArray jso;
			try {
				jso = nw.getJSONArrayByPOST(ns);
				for(int i=0;i<jso.length();i++){
					JSONObject jo=jso.getJSONObject(i);
					tx2.setText(jo.getString("name"));
					txtop.setText(jo.getString("name"));
					tx3.setText(jo.getString("id"));
					tx4.setText(jo.getString("birth"));
					tx5.setText("남");
					if(jo.getInt("sex")==2){
					tx5.setText("여");
					}
					tx6.setText(jo.getString("profile"));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		setbtnexe();
		

try {WebGetImage wg=new WebGetImage();
	Bitmap s=wg.execute("http://sogo4070.dothome.co.kr/profile/"+spu.getValue("id", "")+".jpg").get();
	if(s==null) return;
	Matrix mat=new Matrix();
	s=Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mat, true);
	if(s!=null)
	img1.setImageBitmap(s);
	imgtop.setImageBitmap(s);
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}
	//처음 화면에서 이미지가져오기(이미지가 존재할 경우)
	
	
	private void setbtnexe() {
		// TODO Auto-generated method stub
		tx1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Mystory.class));
			finish();
			
			}
		});
		tx7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			Intent i=new Intent(getApplicationContext(),Modify_profile.class);	
			i.putExtra("name", tx2.getText().toString().trim());
			i.putExtra("birth", tx4.getText().toString().trim());
			i.putExtra("sex", tx5.getText().toString().trim());
			i.putExtra("profile", tx6.getText().toString().trim());
			startActivity(i);
			finish();
			}
		});
		img1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK);                
				intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
				intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, REQ_CODE_SELECT_IMAGE); 
				
				
			}
		});
		
img3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Story.class));
			
				
			}
		});
img5.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	startActivity(new Intent(getApplicationContext(),Setting.class));
	
		
	}
});
		
		img4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Write.class));
			
				
			}
		});
		
	}
	@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			if(requestCode == REQ_CODE_SELECT_IMAGE) 
			{ 
				if(resultCode==Activity.RESULT_OK) 
				{     
					try { 
						//String name_Str = getImageNameToUri(data.getData()); 

						Bitmap image_bitmap 	= Images.Media.getBitmap(getContentResolver(), data.getData());
						Matrix mat=new Matrix();
						image_bitmap=Bitmap.createBitmap(image_bitmap, 0, 0, image_bitmap.getWidth(), image_bitmap.getHeight(), mat, true);
						img1.setImageBitmap(image_bitmap);  
						imgtop.setImageBitmap(image_bitmap);
						Log.e("a",getPath(data.getData())+"");
						Dojob upload_profile_pic=new Dojob();
						upload_profile_pic.execute(getPath(data.getData())+"");
						
					//Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();

					
					} catch (FileNotFoundException e) { 
						// TODO Auto-generated catch block 
						e.printStackTrace(); 
					} catch (IOException e) { 
						// TODO Auto-generated catch block 
						e.printStackTrace(); 
					} catch (Exception e)
					{
	            		e.printStackTrace();
					} 
				}     
			}   
		}

		
		
		
	public String getPath(Uri uri) {
	    String[] projection = {MediaStore.Images.Media.DATA};
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    startManagingCursor(cursor);
	    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(columnIndex);
	}
	
	
	 class Dojob extends AsyncTask<String,Void, Void>{
			@Override
			protected Void doInBackground(String... params) {
				 Log.e("bb", "1");
				 
				// TODO Auto-generated method stub
				try {
					   Log.e("bb", "1");
				     FTPClient mFTP = new FTPClient();

				     mFTP.connect("112.175.184.70", 21);  // ftp로 접속
				     mFTP.login("sogo4070", "sopwer12"); // ftp 로그인 계정/비번
				     mFTP.setFileType(FTP.BINARY_FILE_TYPE); // 바이너리 파일
				     Log.e("bb", "1");
				     mFTP.setBufferSize(1024 * 1024); // 버퍼 사이즈 
				     mFTP.enterLocalPassiveMode(); 
				     mFTP.setFileType(FTP.BINARY_FILE_TYPE);

				    // 업로드 경로 수정 (선택 사항 )
				     Log.e("bb", "2");
				     mFTP.cwd("html"); // ftp 상의 업로드 디렉토리
				     mFTP.mkd("profile"); // public아래로 files 디렉토리를 만든다 
				     mFTP.cwd("profile"); // public/files 로 이동 (이 디렉토리로 업로드가 진행)

				     Log.e("bb", params[0]);
				     File file = new File(params[0].trim()); // 업로드 할 파일이 있는 경로(예제는 sd카드 사진 폴더)
				    
				     Bitmap src = BitmapFactory.decodeFile(params[0]);
				     String z=SaveBitmapToFileCache(src, Environment.getExternalStorageDirectory().getAbsolutePath(),"/img_temp.jpg");
				     Log.e("path", Environment.getExternalStorageDirectory().getAbsolutePath());
				     File filezz = new File(z); 
				               if (filezz.isFile()) {
				                    FileInputStream ifile = new FileInputStream(filezz);
				                    mFTP.storeFile(spu.getValue("id", "")+".jpg", ifile);
				                    Log.e("aa","1");
				                 //   mFTP.rest(spu.getValue("id", ""));  // ftp에 해당 파일이있다면 이어쓰기
		//
				                    mFTP.appendFile(spu.getValue("id", "")+".jpg", ifile); // ftp 해당 파일이 없다면 새로쓰기
				                    
				                    
				               }
				          
				     
				 mFTP.disconnect();

				     } catch (Exception e) {
				          // TODO Auto-generated catch block
				          e.printStackTrace();
				          Log.e("aa","3");
				     } 
				 Log.e("aa","2");
				return null;
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
	  public String SaveBitmapToFileCache(Bitmap bitmap, String strFilePath,
	            String filename) {
	 
	        File file = new File(strFilePath);
	 
	        // If no folders
	        if (!file.exists()) {
	            file.mkdirs();
	            // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
	        }
	 
	        File fileCacheItem = new File(strFilePath + filename);
	        OutputStream out = null;
	 
	        try {
	            fileCacheItem.createNewFile();
	            out = new FileOutputStream(fileCacheItem);
	 
	            bitmap.compress(CompressFormat.JPEG, 10, out);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                out.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	         
	               }
	            return strFilePath + filename;
	        }
	    }


	@Override
	public void onBackPressed() {

	}
}
