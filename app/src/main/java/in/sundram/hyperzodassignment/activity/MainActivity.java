package in.sundram.hyperzodassignment.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import in.sundram.hyperzodassignment.R;
import in.sundram.hyperzodassignment.adapter.MerchantItemAdapter;
import in.sundram.hyperzodassignment.datamodel.MerchantDataModel;
import in.sundram.hyperzodassignment.utils.JSONUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    private static final String TAG = MainActivity.class.getName();
    private LocationManager locationManager = null;
    private Context context = MainActivity.this;
    private String addressLine, city, county;
    private TextView city_county_tv, address_tv;
    private RecyclerView merchant_rv;
    private ProgressBar pb, toolbar_pb;
    private MerchantItemAdapter adapter;
    private List<MerchantDataModel> dataModelList;
    private static final int REQUEST_CHECK_SETTINGS = 214;
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    String service_provider="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
            checkWhetherGPSIsEnabledOrNotAndHandleFunctionAccordingToResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //binding views
    private void init() {
        city_county_tv = findViewById(R.id.city_county_tv);
        address_tv = findViewById(R.id.address_tv);
        merchant_rv = findViewById(R.id.merchant_rv);
        pb = findViewById(R.id.pb);
        toolbar_pb = findViewById(R.id.toolbar_pb);
    }

    private void checkWhetherGPSIsEnabledOrNotAndHandleFunctionAccordingToResult() {
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            service_provider=LocationManager.GPS_PROVIDER;
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace(

            );
        }
        try {
            service_provider=LocationManager.NETWORK_PROVIDER;
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setMessage("GPS not enabled");
            dialog.setPositiveButton("Ok", (dialog1, which) -> {
                //this will navigate user to the device location settings screen
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent, REQUEST_CHECK_SETTINGS);
            });
            AlertDialog alert = dialog.create();
            alert.show();
        } else {
            getLocationPermissionAndBindDataToView();
        }
    }

    private void manageRecyclerView() {
        try {
            pb.setVisibility(View.GONE);
            merchant_rv.setHasFixedSize(true);
            merchant_rv.setLayoutManager(new LinearLayoutManager(context));
            dataModelList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(JSONUtils.merchant_res);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                dataModelList.add(new GsonBuilder().create().fromJson(jsonArray.get(i).toString(), MerchantDataModel.class));
            }
            adapter = new MerchantItemAdapter(context, dataModelList);
            merchant_rv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterPermissionGranted(1)
    private void getLocationPermissionAndBindDataToView() {
        try {
            if (EasyPermissions.hasPermissions(this, perms)) {
                handleToGetCurrentLocation();
            } else {
                EasyPermissions.requestPermissions(this, "We need permissions because of the location..",
                        1, perms);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleToGetCurrentLocation() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                Log.w(TAG, "hasPermissions: API version < M, returning true by default");
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Location Permission is required for this app to run", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            //when permission enabled then get the user current location and bind to view and call recyclerview manage method
            locationManager.requestLocationUpdates(
                    service_provider, 5000, 10, loc -> {
                        Geocoder gcd = new Geocoder(context, Locale.getDefault());
                        List<Address> addresses;
                        try {
                            addresses = gcd.getFromLocation(loc.getLatitude(),
                                    loc.getLongitude(), 1);
                            if (addresses.size() > 0) {
                                toolbar_pb.setVisibility(View.GONE);
                                addressLine = addresses.get(0).getAddressLine(0);
                                county = addresses.get(0).getCountryName();
                                city = addresses.get(0).getLocality();
                                //calling recyclerview manage method so that merchant details can bind into the recyclerview
                                manageRecyclerView();
                            }
                            city_county_tv.setText(city + ", " + county);
                            address_tv.setText(addressLine);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);

    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull @org.jetbrains.annotations.NotNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull @org.jetbrains.annotations.NotNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
            // This will display a dialog directing them to enable the permission in app settings.
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {

            getLocationPermissionAndBindDataToView();
        }
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                getLocationPermissionAndBindDataToView();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                checkWhetherGPSIsEnabledOrNotAndHandleFunctionAccordingToResult();
            }
        }
    }

    @Override
    public void onRationaleAccepted(int requestCode) {
        //do something when rational ok button clicked
    }

    @Override
    public void onRationaleDenied(int requestCode) {
        Toast.makeText(context, "We need permissions because of the location.. Please Start the application", Toast.LENGTH_LONG).show();
    }
}