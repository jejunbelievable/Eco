package sundosoft.co.eco;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ViewActivity extends FragmentActivity implements OnMapReadyCallback{

    File mFolder;
    String lat;
    String lon;
    String datetime;
    String alt;
    File mFolder2;
    File mFile2;
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ImageView photoView = findViewById(R.id.photoView);
        TextView dataView = findViewById(R.id.dataView);
        Button back_bt2 = findViewById(R.id.back_bt2);
        back_bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this, GalleryActivity.class));
                finish();
            }
        });
        Intent intent = getIntent();
        int pos = intent.getIntExtra("position",0);

        System.out.println("position: "+ pos);
        String root = getFilesDir().getAbsolutePath();
        mFolder = new File(root + "/photos");
        File[] files = mFolder.listFiles();


        mFolder2 = new File(root+"/metadata");
        File[] files2 = mFolder2.listFiles();

        photoView.setImageBitmap(BitmapFactory.decodeFile(files[pos].getAbsolutePath()));

        mFile2 = files2[pos];
        try {
            FileReader fr = new FileReader(mFile2);
            BufferedReader br = new BufferedReader(fr);
            lat = br.readLine();
            lon = br.readLine();
            datetime = br.readLine();
            alt = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String info = "위도: " + lat + "\n" + "경도: " + lon + "\n" +
                "촬영시간: "+ datetime + "\n" + "고도: " +alt;
        dataView.setText(info);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lon);
        LatLng takenLoc = new LatLng(latitude,longitude);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(takenLoc).title("촬영위치"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(takenLoc,15));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}
