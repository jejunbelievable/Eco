package sundosoft.co.eco;

import android.app.DatePickerDialog;
<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> d3752d0696facf58fa8462a80210ca18094787b3
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
public class ReserveI
        mageDateActivity extends AppCompatActivity {
=======
public class ReserveImageDateActivity extends AppCompatActivity {
>>>>>>> d3752d0696facf58fa8462a80210ca18094787b3
    private Button send_btn;
    private ImageButton calender1;
    private ImageButton calender2;

    private TextView date1;
    private TextView date2;

    private DatePicker datePicker;
    private String datestring;

    private int mYear =0, mMonth=0, mDay=0;
    private int check=0;

    private Spinner spinner1; // 오전, 오후
    private Spinner spinner2; // 시간대

    //최종 데이터
    private  String day;
    private String time;
    private String startDate;
    private String endDate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_image_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        date1=findViewById(R.id.start_date_tv);
        date2=findViewById(R.id.finish_date_tv);
        datePicker = (DatePicker)findViewById(R.id.dataPicker);
        datePicker.setVisibility(View.INVISIBLE);

        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        datePicker.init(2021, 00, 05, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + monthOfYear + "/" + dayOfMonth;
                Toast.makeText(ReserveImageDateActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        send_btn=findViewById(R.id.booking_button);
        send_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //예약 전송 버튼
                day=spinner1.getSelectedItem().toString();
                time=spinner2.getSelectedItem().toString();
<<<<<<< HEAD
                Toast.makeText(ReserveImageDateActivity.this,"전송 예약 "+startDate+" "+endDate+" "+day+" "+time, Toast.LENGTH_SHORT).show();
=======
                Toast.makeText(ReserveImageDateActivity.this,"전송예약 "+startDate+" "+endDate+" "+day+" "+time, Toast.LENGTH_SHORT).show();
>>>>>>> d3752d0696facf58fa8462a80210ca18094787b3
                finish();
            }
        });

        //예약 시작 날짜 버튼
        calender1=findViewById(R.id.start_date_button);
        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ReserveImageDateActivity.this, listener, 2021, 00, 05);
                dialog.show();
                check=0;

            }
        });

        //예약 종료 날짜 버튼
        calender2=findViewById(R.id.finish_date_button);
        calender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(ReserveImageDateActivity.this, listener, 2021, 00, 06);
                dialog.show();
                check=1;
            }
        });
    }

    @Override //액션바 메뉴
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{//뒤로가기
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //캘린더에서 날짜 설정
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
            monthOfYear=monthOfYear+1;
            datestring=year+"-"+monthOfYear+"-"+dayOfMonth;
            datePicker.setVisibility(View.INVISIBLE);
            if(check==0){ //시작날짜
                date1.setText(datestring);
                startDate=datestring;
            }
            else{ //종료날짜
                date2.setText(datestring);
                endDate=datestring;
            }
        }
    };
}
