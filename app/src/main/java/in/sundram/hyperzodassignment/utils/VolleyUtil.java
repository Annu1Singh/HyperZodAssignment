package in.sundram.hyperzodassignment.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.sundram.hyperzodassignment.datamodel.MerchantDataModel;
import in.sundram.hyperzodassignment.interfaces.GetObjectInterface;

public class VolleyUtil {
    public static void volleyGetRequest(Context context, String web_url, GetObjectInterface getObjectInterface) {

        StringRequest request = new StringRequest(
                Request.Method.GET,
               web_url,
                response -> {
                    try {
                        List<MerchantDataModel> dataModelList = new ArrayList<>();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray dataArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < dataArray.length(); i++) {
                            MerchantDataModel dataModel = new GsonBuilder().create().fromJson(dataArray.getJSONObject(i).toString(), MerchantDataModel.class);
                            dataModelList.add(dataModel);
                        }
                        getObjectInterface.getObject(dataModelList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, error -> Log.w("ERROR", error));
        Volley.newRequestQueue(context).add(request);
    }
}
