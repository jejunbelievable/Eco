package sundosoft.co.eco;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.navigation.NavigationView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static androidx.core.content.FileProvider.getUriForFile;

public class MainMenuActivity extends Activity {
    ImageView photo_bt;
    ImageView register_bt;
    ImageView gallery_bt;
    ImageView menu_bt;
    File mFile;
    File mFile2;
    File mFolder;
    File mFolder2;
    ArrayList<String> mDatas= new ArrayList<String>();
    ListView listview;
    public static Uri imageUri;
    private static final int CAMERA_REQUEST_CODE = 0;
    LocationManager mLocationManager;
    MyLocationListener mLocationListener;
    Location mLocation;
    private DrawerLayout mDrawerLayout;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        photo_bt = (ImageView) findViewById(R.id.photo_bt);
        register_bt = (ImageView) findViewById(R.id.register_bt);
        gallery_bt = (ImageView) findViewById(R.id.gallery_bt);
        menu_bt = (ImageView) findViewById(R.id.menu_bt);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        mLocationListener = new MyLocationListener();
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        mDatas.add("홈으로");
        mDatas.add("사용안내");
        mDatas.add("환경설정");

        photo_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        });
        register_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainMenuActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        gallery_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(MainMenuActivity.this, GalleryActivity.class);
                startActivity(galleryIntent);

            }
        });

        menu_bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //메뉴바(네비게이션뷰) 실행
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.home){
                    //홈으로
                }
                else if(id == R.id.setting){
                    Toast.makeText(MainMenuActivity.this, title, Toast.LENGTH_SHORT).show();
                }
                else if(id == R.id.userguide){
                    Toast.makeText(MainMenuActivity.this, title, Toast.LENGTH_SHORT).show();
                }
                else if(id==R.id.statistic){
                    //등록현황으로 이동
                    Intent registerIntent = new Intent(MainMenuActivity.this, RegisterStatisticActivity.class);
                    startActivity(registerIntent);
                }
                else if(id==R.id.reservation){
                    //사진전송예약
                    Intent registerIntent = new Intent(MainMenuActivity.this, ReserveImageActivity.class);
                    startActivity(registerIntent);
                }

                return true;
            }
        });

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap takenImage = (Bitmap) extras.get("data");
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    saveImage(takenImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveImage(Bitmap finalBitmap) throws IOException {
        ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String filename = String.valueOf(System.currentTimeMillis());
        mFolder = new File(getFilesDir(), "photos");
        mFolder.mkdirs();
        mFile = new File(mFolder, filename + ".jpg");
        mFile.createNewFile();


        mFolder2 = new File(getFilesDir(), "metadata");
        mFolder2.mkdirs();
        mFile2 = new File(mFolder2, filename + ".txt");
        mFile2.createNewFile();

        imageUri = getUriForFile(MainMenuActivity.this, "sundosoft.co.eco.provider", mFolder);
        System.out.println(mLocation.getLatitude());
        System.out.println(mLocation.getLongitude());
        try {

            FileOutputStream fos = new FileOutputStream(mFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            BufferedWriter bw = new BufferedWriter(new FileWriter(mFile2));
            bw.write(String.valueOf(mLocation.getLatitude()));
            bw.newLine();
            bw.write(String.valueOf(mLocation.getLongitude()));
            bw.newLine();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bw.write(format.format(new Date(mLocation.getTime())));
            bw.newLine();
            bw.write(String.valueOf(mLocation.getAltitude()));
            bw.flush();

            fos.flush();
            fos.close();
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static class GenericFileProvider extends FileProvider{}

    private class MyLocationListener implements LocationListener {

        Location loc;
        public MyLocationListener(){}
        @Override
        public void onLocationChanged(Location loc) {
            this.loc = loc;
        }


        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}

