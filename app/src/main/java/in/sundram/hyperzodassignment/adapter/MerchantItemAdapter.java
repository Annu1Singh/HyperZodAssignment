package in.sundram.hyperzodassignment.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.sundram.hyperzodassignment.R;
import in.sundram.hyperzodassignment.activity.DetailsActivity;
import in.sundram.hyperzodassignment.datamodel.MerchantDataModel;

public class MerchantItemAdapter extends RecyclerView.Adapter<MerchantItemAdapter.ViewHolder> {

    private Context context;
    private List<MerchantDataModel> dataModelList;

    public MerchantItemAdapter(Context context, List<MerchantDataModel> dataModelList) {
        this.context = context;
        this.dataModelList = dataModelList;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.single_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MerchantItemAdapter.ViewHolder holder, int position) {
        try {
            Glide.with(context)
                    .load(dataModelList.get(position)
                            .getMerchant_img())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.merchant_iv);
            Glide.with(context)
                    .load(dataModelList.get(position)
                            .getMerchant_img())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.merchant_thumbnail);
            holder.merchant_rating_tv.setText(dataModelList.get(position).getMerchant_rating());
            holder.merchant_offer_tv.setText(dataModelList.get(position).getMerchant_display_offer());
            holder.merchant_special_dish_tv.setText(dataModelList.get(position).getMerchant_special_dish());
            holder.merchant_title.setText(dataModelList.get(position).getMerchant_name());
            holder.merchant_special_info_tv.setText(dataModelList.get(position).getMerchant_description());


            //calling details activity on click of view btn click
            holder.view_more_btn.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", dataModelList.get(position).getMerchant_id());
                intent.putExtra("name", dataModelList.get(position).getMerchant_name());
                intent.putExtra("img", dataModelList.get(position).getMerchant_img());
                intent.putExtra("location", dataModelList.get(position).getMerchant_location());
                intent.putExtra("desc", dataModelList.get(position).getMerchant_description());
                intent.putExtra("special", dataModelList.get(position).getMerchant_special_dish());
                intent.putExtra("rating", dataModelList.get(position).getMerchant_rating());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView merchant_iv;
        private CircleImageView merchant_thumbnail;
        private TextView merchant_offer_tv, merchant_title, merchant_special_dish_tv, merchant_special_info_tv, merchant_rating_tv;
        private AppCompatButton view_more_btn;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            try {
                merchant_iv = itemView.findViewById(R.id.merchant_iv);
                merchant_thumbnail = itemView.findViewById(R.id.merchant_thumbnail);
                merchant_offer_tv = itemView.findViewById(R.id.offer_tv);
                merchant_title = itemView.findViewById(R.id.merchant_title);
                merchant_special_dish_tv = itemView.findViewById(R.id.merchant_special_dish_tv);
                merchant_special_info_tv = itemView.findViewById(R.id.special_info_tv);
                merchant_rating_tv = itemView.findViewById(R.id.rating_tv);
                view_more_btn = itemView.findViewById(R.id.view_more_btn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
