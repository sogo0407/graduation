package co.kr.newp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import java.util.Calendar;

public class Timepick extends Activity {
DatePicker dp;
String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timepick);
		dp=(DatePicker) findViewById(R.id.datePicker1);

		Calendar cal=Calendar.getInstance();

		content=cal.get(Calendar.YEAR)+"/"+(cal.get(Calendar.MONTH)+1)+"/"+cal.get(Calendar.DAY_OF_MONTH);

		dp.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new OnDateChangedListener() {
			
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				content=year+"/"+(1+monthOfYear)+"/"+dayOfMonth;
				
				
				
			}
		});
		
		findViewById(R.id.timepick_btn1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent();
				i.putExtra("content", content);
				setResult(1,i);
				finish();
				
			}
		});
		
	}
	
	
	
}


