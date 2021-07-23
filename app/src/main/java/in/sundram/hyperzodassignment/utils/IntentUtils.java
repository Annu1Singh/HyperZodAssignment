package in.sundram.hyperzodassignment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class IntentUtils {
    public static void CommonIntentWithFadeInAndFadeOutTransitionAndFinishAffinity(Context context, Class<?> cls){
        Intent intent =new Intent(context,cls);
        context.startActivity(intent);
        ((Activity)context).finishAffinity();
        ((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    public static void CommonIntentWithFadeInAndFadeOutTransition(Context context, Class<?> cls){
        Intent intent =new Intent(context,cls);
        context.startActivity(intent);
        ((Activity)context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
