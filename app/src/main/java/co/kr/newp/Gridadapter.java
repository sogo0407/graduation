package co.kr.newp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

@SuppressLint({ "ViewHolder", "InflateParams" }) public class Gridadapter extends BaseAdapter{ 
	ArrayList<Data_content> arr;
	Context con;
	
	
	public Gridadapter(Context con, ArrayList<Data_content> arr) {
		// TODO Auto-generated constructor stub
		this.arr=arr;
		this.con=con;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}

	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arr.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater lif=(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View vi=lif.inflate(R.layout.layout_grid, null);
		ImageView img1=(ImageView) vi.findViewById(R.id.layout_grid_img1);
		TextView tx1=(TextView) vi.findViewById(R.id.layout_grid_tx1);
		TextView tx2=(TextView) vi.findViewById(R.id.layout_grid_tx2);
		TextView tx3=(TextView) vi.findViewById(R.id.layout_grid_tx3);
		
		TextView tx4=(TextView) vi.findViewById(R.id.layout_grid_tx4);
		TextView tx5=(TextView) vi.findViewById(R.id.layout_grid_tx5);
		TextView tx6=(TextView) vi.findViewById(R.id.layout_grid_tx6);
		
		Data_content dt=arr.get(position);
		if(dt.getPic()!=null && !dt.getPic().equals("")){
			
			try {
				WebGetImage wg=new WebGetImage();
				Bitmap at;
				at = wg.execute("http://sogo4070.dothome.co.kr/content/"+dt.getPic()).get();
				if(at!=null)
				{
					Matrix mt = new Matrix();
					at = Bitmap.createBitmap(at, 0, 0, at.getWidth(), at.getHeight(), mt, true);
					img1.setImageBitmap(at);
					tx1.setText(dt.getText());
					tx2.setText(dt.getDate());
					tx3.setText(dt.getTitle());
					tx4.setText(dt.getChoice());
					tx5.setText(dt.getReply());
					tx6.setText(dt.getWriter());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		}
		
	
		
		return vi;
	}
	class WebGetImage extends AsyncTask<String, Void, Bitmap> {
		  
	    @Override
	    protected Bitmap doInBackground(String... params) {
	      // 네트워크에 접속해서 데이터를 가져옴
	      
	      try {
	        //웹사이트에 접속 (사진이 있는 주소로 접근)
			  URL Url = new URL(params[0]);
	        // 웹사이트에 접속 설정
	        HttpURLConnection urlcon = (HttpURLConnection) Url.openConnection();
	        // 연결하시오


			  HttpURLConnection.setFollowRedirects(false);

			  /** HTTP 요청 메소드 SET
			   * 본 예제는 파일의 존재여부만 확인하려니 간단히 HEAD 요청을 보냄
			   * HEAD요청에 대해 웹서버는 수정된 시간이 포함된 리소스의 해더 정보를 간단히 리턴
			   *  GET,POST,HEAD,OPTIONS,PUT,DELETE,TRACE 값등이 올 수 있다.
			   * 디폴트는 GET
			   **/
			  HttpURLConnection con = (HttpURLConnection) Url.openConnection();
			  con.setRequestMethod("HEAD");
			  if(urlcon.getResponseCode()==HttpURLConnection.HTTP_NOT_FOUND){
				  return null;
			  }

	        urlcon.connect();
	        // 이미지 길이 불러옴




	        int imagelength = urlcon.getContentLength();
	        // 스트림 클래스를 이용하여 이미지를 불러옴
	        BufferedInputStream bis = new BufferedInputStream(urlcon.getInputStream(), imagelength);
	        // 스트림을 통하여 저장된 이미지를 이미지 객체에 넣어줌
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        options.inSampleSize = 50;
			  Bitmap bit = BitmapFactory.decodeStream(bis,null,options);
			  //Bitmap bit = BitmapFactory.decodeStream(bis);

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
	

}
