package sundosoft.co.eco;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static sundosoft.co.eco.GalleryActivity.deleted;
import static sundosoft.co.eco.GalleryActivity.fileList;


public class ImageAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private List data;

    private ArrayList<Integer> deleteChecked=new ArrayList<Integer>();

    public static HashMap<Integer, Integer> selectedPositions = new HashMap<>();
    public ImageAdapter(Context context, int layoutResourceId, List data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        if(!deleted||selectedPositions.get(position)==1){
            selectedPositions.put(position,1);
            Bitmap item = (Bitmap) data.get(position);
            holder.image.setImageBitmap(item);
        }else if(selectedPositions.get(position)==-1&&deleted){
            //삭제
            new File(fileList.get(position).getAbsolutePath()).delete();
            fileList.remove(position);
        }

        ViewHolder finalHolder = holder;


        holder.image.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    selectedPositions.replace(position, selectedPositions.get(position) * -1);
                }
                if(GalleryActivity.ORIGIN_IS_REGISTER){
                    Intent intent = new Intent(getContext(), RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                    RegisterActivity.isClicked = true;
                }if (selectedPositions.get(position) == -1&&GalleryActivity.ORIGIN_IS_DELETE) {
                    int border = 5;
                    finalHolder.image.setPadding(2 * border, 2 * border, 2 * border, 2 * border);
                    final ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(10, 10);
                    params.leftMargin = border;
                    params.topMargin = border;
                    finalHolder.image.setBackgroundColor(Color.RED);  //삭제하기 전 선택된 사진 테두리 RED로 변경
                }if (selectedPositions.get(position) == 1&&GalleryActivity.ORIGIN_IS_DELETE) {
                    finalHolder.image.setPadding(0, 0, 0, 0);
                }if(!GalleryActivity.ORIGIN_IS_DELETE) {
                    Intent viewIntent = new Intent(getContext(),ViewActivity.class);
                    viewIntent.putExtra("position", position);
                    context.startActivity(viewIntent);
                    RegisterActivity.isClicked = false;
                }
            }
        });
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}
