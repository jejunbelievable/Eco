package sundosoft.co.eco;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GalleryActivity extends AppCompatActivity {
    private GridView gridView;
    private ImageAdapter gridAdapter;
    public static boolean ORIGIN_IS_REGISTER = false;
    public static boolean ORIGIN_IS_DELETE = false;
    public static boolean deleted = false;
    File mFolder;

    public static ArrayList<File> fileList = new ArrayList<File>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_2);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        Button delete_button = (Button) findViewById(R.id.delete_button);
        Button delete_data = (Button) findViewById(R.id.delete_data);
        delete_data.setVisibility(View.INVISIBLE);
        delete_data.setClickable(false);
        TextView empty_message_tv=findViewById(R.id.empty_tv);
        TextView album_tv=findViewById(R.id.album_tv);

        String path = getFilesDir().toString()+"/photos";
        mFolder = new File(path);
        if(mFolder.exists()){
            File[] files = mFolder.listFiles();
            if(files.length==0){ //갤러리가 비어있는 경우
                empty_message_tv.setVisibility(View.VISIBLE);
                album_tv.setVisibility(View.INVISIBLE);
            }
            else{ //갤러리에 사진이 있는 경우
                Log.d("Files","Size: "+files.length);
                for (int i = 0; i < files.length; i++) {
                    Log.d("Files","FileName: "+files[i].getName());
                    Log.d("Files","File path: "+files[i].getAbsolutePath());
                    if(fileList.size()<files.length){
                        fileList.add(new File(files[i].getAbsolutePath()));
                    }
                }
            }
        }

        Intent intent = getIntent();
        ORIGIN_IS_REGISTER = intent.getBooleanExtra("fromRegister",false);
        gridView = (GridView) findViewById(R.id.gv);
        try {
            gridAdapter = new ImageAdapter(GalleryActivity.this, R.layout.grid_item_layout, getData());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        gridView.setAdapter(gridAdapter);

        delete_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                delete_data.setVisibility(View.VISIBLE);
                delete_data.setClickable(true);
                ORIGIN_IS_DELETE = true;
            }
        });

        delete_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                deleted = true;
                delete_data.setVisibility(View.INVISIBLE);
                delete_data.setClickable(false);
                ORIGIN_IS_DELETE = false;
                try {
                    gridAdapter = new ImageAdapter(GalleryActivity.this, R.layout.grid_item_layout, getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                gridView.setAdapter(gridAdapter);
                gridAdapter.notifyDataSetChanged();
            }

        });
    }


    private List<Bitmap> getData() throws FileNotFoundException {
        List<Bitmap> imageItems = new ArrayList<Bitmap>();

        for(int i=0;i<fileList.size();i++){
            Bitmap myBitmap = BitmapFactory.decodeFile(fileList.get(i).getAbsolutePath());
            imageItems.add(myBitmap);
        }
        return imageItems;
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





