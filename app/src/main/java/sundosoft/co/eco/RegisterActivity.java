package sundosoft.co.eco;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Scanner;

import static androidx.core.content.FileProvider.getUriForFile;
import static sundosoft.co.eco.R.id.default_activity_button;
import static sundosoft.co.eco.R.id.finish_date_button;
import static sundosoft.co.eco.R.id.inver_bt;
import static sundosoft.co.eco.R.id.src_atop;

public class RegisterActivity extends AppCompatActivity {
    String type = "";
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageButton imageBt;
    TextView location_info;
    File mFolder;
    File mFolder2;
    File mFile2;
    public static Boolean isClicked = false;//image button is clicked
    private static boolean FROM_REGISTER = true;
    String lat;
    String lon;
    @RequiresApi(api = Build.VERSION_CODES.N)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_2);

        //Button back_bt3 = (Button) findViewById(R.id.back_bt3);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        Button redo_bt = (Button) findViewById(R.id.redo_bt);

        Button plant_bt = (Button) findViewById(R.id.plant_bt);
        Button mammal_bt = (Button) findViewById(R.id.mammal_bt);
        Button bird_bt = (Button) findViewById(R.id.bird_bt);
        Button insect_bt = (Button) findViewById(R.id.insect_bt);
        Button fish_bt = (Button) findViewById(R.id.fish_bt);
        Button inver_bt = (Button) findViewById(R.id.inver_bt);
        Button topography_bt = (Button) findViewById(R.id.topography_bt);
        Button vegetation_bt = (Button) findViewById(R.id.vegetation_bt);
        Button herptile_bt = (Button) findViewById(R.id.herptile_bt);
        Button geology_bt = (Button) findViewById(R.id.geology_bt);
        Button seaweed_bt = (Button) findViewById(R.id.seaweed_bt);
        Button cinver_bt = (Button) findViewById(R.id.cinver_bt);
        Button frmtenvint = (Button) findViewById(R.id.frmtenvimt);
        Button fungus_bt = (Button) findViewById(R.id.fungus_bt);
        Button bat_bt = (Button) findViewById(R.id.bat_bt);
        Button vegirating_bt = (Button) findViewById(R.id.vegirating_bt);
        Button normal_stat_bt = (Button) findViewById(R.id.normal_stat_bt);
        Button bryo_bt = (Button) findViewById(R.id.bryo_bt);
        Button spider_bt = (Button) findViewById(R.id.spider_bt);



        imageBt = (ImageButton) findViewById(R.id.imageBt);

        EditText typeName1 = (EditText) findViewById(R.id.typeName1);
        EditText typeName2 = (EditText) findViewById(R.id.typeName2);

        String root = getFilesDir().getAbsolutePath();
        mFolder = new File(root + "/photos");
        File[] files = mFolder.listFiles();


        mFolder2 = new File(root+"/metadata");
        File[] files2 = mFolder2.listFiles();

        if(isClicked) {
            Intent intent = getIntent();
            int pos = intent.getIntExtra("position", 0);
            location_info = (TextView) findViewById(R.id.location_info);
            mFile2 = files2[pos];
            try {
                FileReader fr = new FileReader(mFile2);
                BufferedReader br = new BufferedReader(fr);
                lat = br.readLine();
                lon = br.readLine();
                System.out.println(lat);
                System.out.println(lon);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String info = "위도: " + lat + "\n" + "경도: " + lon;
            location_info.setText(info);

            if (intent != null) {
                imageBt.setImageBitmap(BitmapFactory.decodeFile(files[pos].getAbsolutePath()));
            }
        }


        Button.OnClickListener onClickListener = new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch(v.getId()){

                    case R.id.plant_bt: typeName1.setText(plant_bt.getText()); typeName2.setText(plant_bt.getText()); break;
                    case R.id.mammal_bt: typeName1.setText(mammal_bt.getText()); typeName2.setText(mammal_bt.getText()); break;
                    case R.id.bird_bt: typeName1.setText(bird_bt.getText()); typeName2.setText(bird_bt.getText()); break;
                    case R.id.insect_bt: typeName1.setText(insect_bt.getText()); typeName2.setText(insect_bt.getText()); break;
                    case R.id.fish_bt: typeName1.setText(fish_bt.getText()); typeName2.setText(fish_bt.getText()); break;
                    case R.id.inver_bt: typeName1.setText(inver_bt.getText()); typeName2.setText(inver_bt.getText()); break;
                    case R.id.topography_bt: typeName1.setText(topography_bt.getText()); typeName2.setText(topography_bt.getText()); break;
                    case R.id.vegetation_bt: typeName1.setText(vegetation_bt.getText()); typeName2.setText(vegetation_bt.getText()); break;
                    case R.id.herptile_bt: typeName1.setText(herptile_bt.getText()); typeName2.setText(herptile_bt.getText()); break;
                    case R.id.geology_bt: typeName1.setText(geology_bt.getText()); typeName2.setText(geology_bt.getText()); break;
                    case R.id.seaweed_bt: typeName1.setText(seaweed_bt.getText()); typeName2.setText(seaweed_bt.getText()); break;
                    case R.id.cinver_bt: typeName1.setText(cinver_bt.getText()); typeName2.setText(cinver_bt.getText()); break;
                    case R.id.frmtenvimt: typeName1.setText(frmtenvint.getText()); typeName2.setText(frmtenvint.getText()); break;
                    case R.id.fungus_bt: typeName1.setText(fungus_bt.getText()); typeName2.setText(fungus_bt.getText()); break;
                    case R.id.bat_bt: typeName1.setText(bat_bt.getText()); typeName2.setText(bat_bt.getText()); break;
                    case R.id.vegirating_bt: typeName1.setText(vegirating_bt.getText()); typeName2.setText(vegirating_bt.getText()); break;
                    case R.id.normal_stat_bt: typeName1.setText(normal_stat_bt.getText()); typeName2.setText(normal_stat_bt.getText()); break;
                    case R.id.bryo_bt: typeName1.setText(bryo_bt.getText()); typeName2.setText(bryo_bt.getText()); break;
                    case R.id.spider_bt: typeName1.setText(spider_bt.getText()); typeName2.setText(spider_bt.getText()); break;
                    case default_activity_button:break;

                }
                type = typeName1.getText().toString();
            }
        };

        redo_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imageBt.setImageBitmap(null);
                location_info.setText("");
                typeName1.setText("");
                typeName2.setText("");
            }
        });


        /*back_bt3.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainMenuActivity.class));
                finish();
            }
        });*/

        plant_bt.setOnClickListener(onClickListener); mammal_bt.setOnClickListener(onClickListener);
        bird_bt.setOnClickListener(onClickListener); insect_bt.setOnClickListener(onClickListener);
        fish_bt.setOnClickListener(onClickListener);inver_bt.setOnClickListener(onClickListener);
        topography_bt.setOnClickListener(onClickListener);vegetation_bt.setOnClickListener(onClickListener);
        herptile_bt.setOnClickListener(onClickListener);geology_bt.setOnClickListener(onClickListener);
        seaweed_bt.setOnClickListener(onClickListener);cinver_bt.setOnClickListener(onClickListener);
        frmtenvint.setOnClickListener(onClickListener);fungus_bt.setOnClickListener(onClickListener);
        bat_bt.setOnClickListener(onClickListener);vegirating_bt.setOnClickListener(onClickListener);
        normal_stat_bt.setOnClickListener(onClickListener);bryo_bt.setOnClickListener(onClickListener);
        spider_bt.setOnClickListener(onClickListener);



        imageBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(RegisterActivity.this, GalleryActivity.class);
                galleryIntent.putExtra("fromRegister",FROM_REGISTER);
                startActivity(galleryIntent);
                finish();
                isClicked=true;
            }

        });


        EditText other_Info = (EditText) findViewById(R.id.other_info);

        //특이사항


        ImageView send_bt = (ImageView) findViewById(R.id.send_bt);
        send_bt.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //서버에 데이터 전송
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
