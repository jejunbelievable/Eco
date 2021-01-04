package sundosoft.co.eco;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterStatisticActivity extends AppCompatActivity {
    private ImageView image1; //최근 등록 사진1
    private ImageView image2; //최근 등록 사진2
    private ImageView image3; //최근 등록 사진3
    
    //서버에서 값 받아와 저장하기
    private int prepare=0; //준비중 건수
    private int process=0; //처리중 건수
    private int success=0; //등록완료 건수 

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_statistic);
        
        image1=findViewById(R.id.recent_image1);
        image2=findViewById(R.id.recent_image2);
        image3=findViewById(R.id.recent_image3);
        //최근 등록된 사진으로 image1, image2, imgage3 변경하기

        

    }


}
