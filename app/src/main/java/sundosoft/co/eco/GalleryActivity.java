package sundosoft.co.eco;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends FragmentActivity {
    private GridView gridView;
    private ImageAdapter gridAdapter;
    public static boolean ORIGIN_IS_REGISTER = false;
    File mFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        /*Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GalleryActivity.this,MainMenuActivity.class));
                finish();
            }
        });*/

        /*ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼*/

        Intent intent = getIntent();
        ORIGIN_IS_REGISTER = intent.getBooleanExtra("fromRegister",false);

        gridView = (GridView) findViewById(R.id.gv);
        try {
            gridAdapter = new ImageAdapter(this, R.layout.grid_item_layout, getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        gridView.setAdapter(gridAdapter);

    }


    private List<Bitmap> getData() throws FileNotFoundException {
        List<Bitmap> imageItems = new ArrayList<Bitmap>();
        String path = getFilesDir().toString()+"/photos";
        Log.d("Files", "Path: "+ path);
        mFolder = new File(path);
        File[] files = mFolder.listFiles();
        Log.d("Files","Size: "+files.length);
        for (int i = 0; i < files.length; i++) {
            Log.d("Files","FileName: "+files[i].getName());
            Log.d("Files","File path: "+files[i].getAbsolutePath());
            Bitmap myBitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
            imageItems.add(myBitmap);
        }
        return imageItems;
    }

    /*@Override //뒤로가기 메뉴
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{//뒤로가기
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }*/



}

