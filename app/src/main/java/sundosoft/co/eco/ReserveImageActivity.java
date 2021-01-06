package sundosoft.co.eco;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

public class ReserveImageActivity extends AppCompatActivity {
    private Button send_btn;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_image);

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
}
