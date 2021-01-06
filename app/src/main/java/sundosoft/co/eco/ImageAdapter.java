package sundosoft.co.eco;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImageAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private List data = new ArrayList();

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
        String path = context.getFilesDir().toString() + "/photos";
        File directory = new File(path);
        File[] files = directory.listFiles();


        Bitmap item = (Bitmap) data.get(position);
        holder.image.setImageBitmap(item);




        holder.image.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(GalleryActivity.ORIGIN_IS_REGISTER){
                    Intent intent = new Intent(getContext(), RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                    RegisterActivity.isClicked = true;
                }else{
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
