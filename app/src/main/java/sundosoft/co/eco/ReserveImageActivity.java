package sundosoft.co.eco;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ReserveImageActivity extends AppCompatActivity {
    private Button send_btn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_image);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        send_btn=findViewById(R.id.send_booking_button);
        send_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //날짜, 시간 정하는 페이지로 이동
                Intent intent = new Intent(ReserveImageActivity.this, ReserveImageDateActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override //뒤로가기 메뉴
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{//뒤로가기
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
