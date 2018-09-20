package co.kr.newp;

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

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import co.kr.newp.Main_content.WebGetImage;
import co.kr.newp.Write.Dojobs;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
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

public class Write_modify extends Activity {
	EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
	ImageView img1,	bottom_img2,	bottom_img3,	bottom_img4,	bottom_img5;
	RelativeLayout rel1,rel2;
	Button btn1;
	int sex=0;
	SharedPreferencesUtil spu;
	ImageView img2;
	//0일떄 중립 1일떄 남자 2일때 여자
	String picname="";
	String pic="";
	final int REQ_CODE_SELECT_IMAGE=200;
	String z;
	boolean ispicome;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_modify);
		bottom_img2=(ImageView) findViewById(R.id.main_profile_img2);
		bottom_img3=(ImageView) findViewById(R.id.main_profile_img3);
		bottom_img4=(ImageView) findViewById(R.id.main_profile_img4);
		bottom_img5=(ImageView) findViewById(R.id.main_profile_img5);

		spu=new SharedPreferencesUtil(getApplicationContext());
		setbutton();
		setbuttonexe();
		setfirst();
		 
//		Intent i=getIntent();	
//		
//		ed4.setText(i.getStringExtra("name").toString().trim());
//		ed6.setText(i.getStringExtra("birth").toString().trim());
//		ed7.setText(i.getStringExtra("profile").toString().trim());
//		rel1.setBackgroundColor(Color.GRAY);
//		rel2.setBackgroundColor(Color.parseColor("#50ffffff"));
//		sex=1;
//		if(i.getStringExtra("sex").equals("여")){
//			rel2.setBackgroundColor(Color.GRAY);
//			rel1.setBackgroundColor(Color.parseColor("#50ffffff"));
//			sex=2;
//		}
		
		
	}
	private void setfirst() {
		// TODO Auto-generated method stub
		ed3.setText(G.arr.get(G.arr.size()-1).getTitle());
		ed4.setText(G.arr.get(G.arr.size()-1).getPlace());
		ed6.setText(G.arr.get(G.arr.size()-1).getDate());
		ed7.setText(G.arr.get(G.arr.size()-1).getText());
		try {
			WebGetImage wg=new WebGetImage();
			Bitmap s;
			s = wg.execute("http://sogo4070.dothome.co.kr/content/"+G.arr.get(G.arr.size()-1).getPic()).get();
			Matrix mt=new Matrix();
			s=Bitmap.createBitmap(s, 0, 0, s.getWidth(), s.getHeight(), mt, true);
			img2.setImageBitmap(s);
			if(s!=null)
			img2.setImageBitmap(s);
			z=SaveBitmapToFileCache(s, Environment.getExternalStorageDirectory().getAbsolutePath(),"/img_temp.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		picname=G.arr.get(G.arr.size()-1).getPic();
		
		
	}
	private void setbuttonexe() {
		// TODO Auto-generated method stub
		
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				
				
				if(ed3.getText().toString().trim()!=null && ed4.getText().toString().trim()!=null&&ed6.getText().toString().trim()!=null&&ed7.getText().toString().trim()!=null && !ed3.getText().toString().trim().equals("") && !ed4.getText().toString().trim().equals("")&& !ed6.getText().toString().trim().equals("")&& !ed7.getText().toString().trim().equals("")
			        ){
				NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/write_modify_content.php",getApplicationContext());
			ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
			ns.add(new BasicNameValuePair("_num", G.arr.get(G.arr.size()-1).getNum()));
			ns.add(new BasicNameValuePair("_writer", spu.getValue("id", "")));
			ns.add(new BasicNameValuePair("_title", ed3.getText().toString().trim()));
			//ns.add(new BasicNameValuePair("password", ed2.getText().toString().trim()));
			ns.add(new BasicNameValuePair("_place", ed4.getText().toString().trim()));
			//ns.add(new BasicNameValuePair("email", ed5.getText().toString().trim()));
			ns.add(new BasicNameValuePair("_date", ed6.getText().toString().trim()));
			ns.add(new BasicNameValuePair("_text", ed7.getText().toString().trim()));
			ns.add(new BasicNameValuePair("_pic", picname));
			
			
			try {
				String a=nw.getStringByPOST(ns);
				if(a.equals("1")){
					Dojobs upload_profile_pic=new Dojobs();
					upload_profile_pic.execute(pic);
					Toast.makeText(getApplicationContext(), "글이 정상적으로 등록 되었습니다.", 1000).show();
					startActivity(new Intent(getApplicationContext(),Mystory.class));
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
		img2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated mehod stub
				ispicome=true;
				Intent intent = new Intent(Intent.ACTION_PICK);                
				intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
				intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, REQ_CODE_SELECT_IMAGE); 
			}
		});
		
		bottom_img2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Mystory.class));
			finish();
			}
		});
bottom_img3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			startActivity(new Intent(getApplicationContext(),Story.class));
			finish();
			}
		});
		
bottom_img4.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	startActivity(new Intent(getApplicationContext(),Setting.class));
	finish();
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
					mat.postRotate(-90);
					image_bitmap=Bitmap.createBitmap(image_bitmap, 0, 0, image_bitmap.getWidth(), image_bitmap.getHeight(), mat, true);
					img2.setImageBitmap(image_bitmap);     
				
					Dojobs upload_profile_pic=new Dojobs();
					upload_profile_pic.execute(getPath(data.getData())+"");
					pic=(getPath(data.getData())+"");
					picname=(getPath(data.getData())+"");
					picname=picname.substring(picname.length()-9);
					picname=spu.getValue("id", "")+picname;
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
			if(resultCode==1){
				String x=data.getStringExtra("content");
				ed6.setText(x);
			
				   
			}}
			
	
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
		img2=(ImageView) findViewById(R.id.write_img);
		rel1=(RelativeLayout) findViewById(R.id.member_join_rel1);
		rel2=(RelativeLayout) findViewById(R.id.member_join_rel2);
		btn1=(Button) findViewById(R.id.member_join_btn_confirm);
		
	}
	
	public String getPath(Uri uri) {
	    String[] projection = {MediaStore.Images.Media.DATA};
	    Cursor cursor = managedQuery(uri, projection, null, null, null);
	    startManagingCursor(cursor);
	    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(columnIndex);
	}
	
	
	class Dojobs extends AsyncTask<String,Void, Void>{
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
			     mFTP.mkd("content"); // public아래로 files 디렉토리를 만든다 
			     mFTP.cwd("content"); // public/files 로 이동 (이 디렉토리로 업로드가 진행)

			     Log.e("bb", params[0]);
			     File file = new File(params[0].trim()); // 업로드 할 파일이 있는 경로(예제는 sd카드 사진 폴더)
			     Bitmap src = BitmapFactory.decodeFile(params[0]);
			      z=SaveBitmapToFileCache(src, Environment.getExternalStorageDirectory().getAbsolutePath(),"/img_temp.jpg");
			     File filezz = new File(z); 
			               if (file.isFile()) {
			            	   
			                    FileInputStream ifile = new FileInputStream(filezz);
			                    mFTP.storeFile(picname, ifile);
			                    Log.e("aa","1");
			                 //   mFTP.rest(spu.getValue("id", ""));  // ftp에 해당 파일이있다면 이어쓰기
	//
			                    mFTP.appendFile(picname, ifile); // ftp 해당 파일이 없다면 새로쓰기
			                    
			                    
			               }
			          
			     
			 

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
	
}
