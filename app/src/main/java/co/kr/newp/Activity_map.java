package co.kr.newp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Activity_map extends FragmentActivity implements OnMapReadyCallback {
    // 맵관련 화면
    private GoogleMap mMap;
    ArrayList<Data_Map> arr;
    SharedPreferencesUtil spu;
    EditText ed1;
    Button btn1;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        arr = new ArrayList<>();
        spu=new SharedPreferencesUtil(getApplication());
        ed1= (EditText) findViewById(R.id.ed1);
        btn1= (Button) findViewById(R.id.btn1);




    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        //자기위치 버튼 표시
        LatLng seoul = new LatLng(37.52487, 126.92723);
        //카메라를 여의도 위치로 옮긴다.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,12));
        // 첫 맵이 로드 되면 화면은 기본으로 여의도로 이동한다.


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //맵 클릭시 해당 위치에 마커를 넣는부분
                MarkerOptions makerOptions = new MarkerOptions();
                String markerTitle=getAddress(getApplicationContext(),latLng.latitude,latLng.longitude);
                //마커 제목에 쓸 타이틀을 가져온다.
                makerOptions.position(latLng).title(markerTitle);

                // 마커를 생성한다.
                mMap.addMarker(makerOptions);


                NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/add_marker.php"	,getApplicationContext());
                ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
                ns.add(new BasicNameValuePair("_id",spu.getValue("id","")));
                ns.add(new BasicNameValuePair("lat",latLng.latitude+""));
                ns.add(new BasicNameValuePair("lng",latLng.longitude+""));
                ns.add(new BasicNameValuePair("title",markerTitle+""));
                try {
                    nw.getStringByPOST(ns);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    onResume();
                    Toast.makeText(Activity_map.this, "입력되었습니다.", Toast.LENGTH_SHORT).show();
                }
                // 마커를 넣고 서버에 자신의 아이디와 위치와 주소를 넣는다.


                /*mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        marker.get
                        return false;
                    }
                });*/

            }
        });
        // 맵 클릭시 마커를 넣어준다.

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LatLng findLatlng=getLocationFromAddress(ed1.getText().toString());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(findLatlng,12));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //이동 버튼 눌렀을때 입력한 제목을 가지고  위도 경도로 이동한다.


        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mMap!=null) {
            arr = new ArrayList<>();
            mMap.clear();

            NetWork nw = new NetWork("http://sogo4070.dothome.co.kr/get_marker.php", getApplicationContext());
            ArrayList<NameValuePair> ns = new ArrayList<NameValuePair>();
            ns.add(new BasicNameValuePair("_id", spu.getValue("id", "")));

            //로그인한 아이디를 가지고 기존에 저장된 마커들을 가져온다.


            try {
                JSONArray jsonarr = null;

                jsonarr = nw.getJSONArrayByPOST(ns);
                for (int i = 0; i < jsonarr.length(); i++) {

                    JSONObject jsonob = jsonarr.getJSONObject(i);

                    arr.add(new Data_Map(jsonob.getString("_num").toString(), jsonob.getString("lat").toString(), jsonob.getString("lng").toString(), jsonob.getString("title").toString()));

                    MarkerOptions makerOptions = new MarkerOptions();
                    makerOptions.position(new LatLng(Double.parseDouble(jsonob.getString("lat")), Double.parseDouble(jsonob.getString("lng")))).title(jsonob.getString("title"));
                    // 마커를 생성한다.

                    mMap.addMarker(makerOptions);

                }

            } catch (Exception e) {

            }

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    getDialog(marker).show();
                    return true;
                }
            });
            // 마커를 찍었을때 지우는 다이얼로그를 보여준다.

        }


    }

    private AlertDialog getDialog(final Marker marker) {
        AlertDialog.Builder alg=new AlertDialog.Builder(Activity_map.this);
        alg.setTitle("확인");
        alg.setMessage("삭제하시겠습니까?");
        alg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for(int j=0;j<arr.size();j++){
                    if(arr.get(j).getLat().equals(marker.getPosition().latitude+"") && arr.get(j).getLng().equals(marker.getPosition().longitude+"")){
                        NetWork nw=new NetWork("http://sogo4070.dothome.co.kr/del_marker.php"	,getApplicationContext());
                        ArrayList<NameValuePair> ns=new ArrayList<NameValuePair>();
                        ns.add(new BasicNameValuePair("_num",arr.get(j).getNum()));
                        // 삭제를 누를 경우 해당 마커를 지운다.

                        try {
                            nw.getStringByPOST(ns);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            onResume();
                            Toast.makeText(Activity_map.this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            }
        });
        alg.setNegativeButton("취소",null);
        alg.setCancelable(false);
        return alg.create();
    }

    public class Data_Map{
        String num,lat,lng,title;

        public Data_Map(String num, String lat, String lng, String title) {
            this.num = num;
            this.lat = lat;
            this.lng = lng;
            this.title = title;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static String getAddress(Context mContext, double lat, double lng) {
        // 위도 경도로 주소 가져오는 부분
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);

                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    String currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;

                }
            }

        } catch (IOException e) {


            e.printStackTrace();
        }
        return nowAddress;
    }




    public LatLng getLocationFromAddress(String strAddress) throws IOException {
        // 제목가지고 위도 경도 받아오는 메소드

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude() ,
                     (location.getLongitude()));


        } catch (Exception e) {

        }

        return p1;
    }

}
