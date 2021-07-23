package in.sundram.hyperzodassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import in.sundram.hyperzodassignment.R;
import in.sundram.hyperzodassignment.utils.DBHelper;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = DetailsActivity.class.getName();
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    String merchant_id = "", img, des, location, special, rating, name;
    int count = 0;
    private ImageView merchant_iv;
    private CircleImageView merchant_thumbnail;
    private TextView merchant_offer_tv, merchant_title, merchant_special_dish_tv, merchant_special_info_tv, merchant_rating_tv;
    private AppCompatButton view_more_btn;
    private Context context = DetailsActivity.this;
    private Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        try {
            init();
            if (getIntent().getStringExtra("id") != null) {
                Intent intent = getIntent();
                merchant_id = intent.getStringExtra("id");
                dbHelper = new DBHelper(this);
                db = dbHelper.getReadableDatabase();
                cursor = dbHelper.getCountOfMerchantsById(merchant_id, db);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        //write update table value code here
                    }
                } else {
                    addCacheForMostVisitedRestaurant();
                }

                img = intent.getStringExtra("img");
                location = intent.getStringExtra("location");
                des = intent.getStringExtra("desc");
                special = intent.getStringExtra("special");
                rating = intent.getStringExtra("rating");
                name = intent.getStringExtra("name");
                onBindDataToView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
       try {
           merchant_thumbnail = findViewById(R.id.merchant_thumbnail);
           merchant_iv = findViewById(R.id.merchant_iv);
           merchant_offer_tv = findViewById(R.id.offer_tv);
           merchant_title = findViewById(R.id.merchant_title);
           merchant_special_dish_tv = findViewById(R.id.merchant_special_dish_tv);
           merchant_special_info_tv = findViewById(R.id.special_info_tv);
           merchant_rating_tv = findViewById(R.id.rating_tv);
           view_more_btn = findViewById(R.id.view_more_btn);
           hideUnwantedView();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void hideUnwantedView() {
        view_more_btn.setVisibility(View.GONE);
    }

    private void onBindDataToView() {
      try {
          Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(merchant_iv);
          Glide.with(context).load(img).placeholder(R.drawable.placeholder).into(merchant_thumbnail);
          merchant_rating_tv.setText(rating);
          merchant_offer_tv.setText(location);
          merchant_special_dish_tv.setText(special);
          merchant_title.setText(name);
          merchant_special_info_tv.setText(des);
      }catch (Exception e){
          e.printStackTrace();
      }
    }

    private void addCacheForMostVisitedRestaurant() {
      try {
          count++;
          //creating db helper object
          dbHelper = new DBHelper(this);
          //get SqlLiteDB object from dbHelper object using getWritableDatabase()
          db = dbHelper.getWritableDatabase();
          //here calling add cache method from DBHelper Class
          dbHelper.addCountOfMerchant(merchant_id, String.valueOf(count), db);
          db.close();
      }catch (Exception e){
          e.printStackTrace();
      }
    }
}