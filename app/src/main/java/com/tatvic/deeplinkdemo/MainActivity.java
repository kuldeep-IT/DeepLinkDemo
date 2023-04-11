package com.tatvic.deeplinkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tvScreen1);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Screen_Two.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        Uri referrerUri = intent.getParcelableExtra(Intent.EXTRA_REFERRER);
        Log.d("MainActivity_URI: ", " packageName: "+referrerUri);

        Uri uri = intent.getData();
        String packageName = intent.getPackage();
        Log.d("MainActivity URI: ", String.valueOf(uri)+" packageName: "+packageName);


        Log.d("DEEP_URI: ", getReferrerCompatible(MainActivity.this) +"");

        Log.d("DEEP_URI_DATA: ", getReferrerData(MainActivity.this) +"");
        textView.setText(getReferrerData(MainActivity.this)+"");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d("HE_IS_HERE", "onCreate: "+this.getReferrer());

//            this.getReferrer();
            String response = this.getReferrer() != null ? this.getReferrer().toString() : "null";

//            textView.setText(response.toString());


//            getAppNameOfDynamicLink();
//            solution2();
//            solution3();
//            sol4();
//                sol5();
     /*       String deepLinkUrl = getIntent().getDataString();
            Log.d("DATA_IS_HERE", "onCreate: "+deepLinkUrl);*/

         /*   Intent i1 = getIntent();
            Uri data = i1.getData();
            if (data != null) {
                String deepLinkUrl = data.toString();
                String utmSource = data.getQueryParameter("utm_source");
                Log.d("DATA_IS_HERE", "onCreate: "+deepLinkUrl+"\n utmSource "+utmSource
                        +"\n data "+data);

                if (utmSource != null) {
                    // Do something with the utm_source value
                }
            }*/
        }

        if (uri != null) {
            // if the uri is not null then we are getting
            // the path segments and storing it in list.
            //List<String> parameters = uri.getPathSegments();

            // after that we are extracting string
            // from that parameters.
            //String param = parameters.get(parameters.size() - 1);

            // on below line we are setting that string
            // to our text view which we got as params.
            //textView.setText(param);
            Set<String> parameters = uri.getQueryParameterNames();
            String utm_source = uri.getQueryParameter("utm_source");
            String utm_medium = uri.getQueryParameter("utm_medium");

            Log.d("Parameters: ", String.valueOf(utm_source + "  " + utm_medium));
        }
    }

    public void getAppNameOfDynamicLink(){
        Intent intent = getIntent();
        String packageName = intent.getPackage();
        PackageManager packageManager = getPackageManager();
        String appName = "";

        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            appName = packageManager.getApplicationLabel(appInfo).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        String message = "The deep link was clicked from the app: " + appName;
        Log.d("deepLink_is_here", message);

    }

    @Override
    protected void onResume() {
        super.onResume();

//        sol6();

        Log.d("LKJVCFGHJ", "onResume: INNN");

        Intent intent = getIntent();
        Uri referrerUri = intent.getParcelableExtra(Intent.EXTRA_REFERRER);
        Log.d("MainActivity_URI: ", " packageName: "+referrerUri);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            Log.d("LKJVCFGHJ", "onResume: "+ this.getReferrer());
        }

    }

    public void solution2(){
        Intent intent = getIntent();
        String packageName = intent.getPackage();
        if (packageName == null) {
            // The Intent was not launched from another app, do something else here
            String message = "The deep link was not clicked from another app.";
            Log.d("DeepLink_name", message);
        } else {
            PackageManager packageManager = getPackageManager();
            String appName = "";

            try {
                ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                appName = packageManager.getApplicationLabel(appInfo).toString();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            String message = "The deep link was clicked from the app: " + appName;
            Log.d("DeepLink", message);
        }
    }

    public void solution3() {

        String callingPackage = getCallingPackage();
        if (callingPackage != null) {
            PackageManager packageManager = getPackageManager();
            String appName = null;
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(callingPackage, 0);
                appName = (String) packageManager.getApplicationLabel(applicationInfo);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Log.d("DeepLink_abc", "App name: " + appName);
        } else {
            Log.d("DeepLink_abc", "No calling package found.");
        }
    }

    public void sol4(){
        Intent intent = getIntent();
        Uri uri = intent.getData();

        PackageManager pm = getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (resolveInfos != null && !resolveInfos.isEmpty()) {
            ResolveInfo resolveInfo = resolveInfos.get(0);
            String packageName = resolveInfo.activityInfo.packageName;
            String appName = pm.getApplicationLabel(resolveInfo.activityInfo.applicationInfo).toString();
            Log.d("DeepLink_abc", "Package Name: " + packageName);
            Log.d("DeepLink_abc", "App Name: " + appName);
        }

    }

    public void sol5(){
        // Get the package manager
        PackageManager pm = getPackageManager();

// Get the intent that started this activity
        Intent intent = getIntent();

// Get the referring app's package name
        String referringPackageName = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            referringPackageName = this.getReferrer().getHost();
        }

// Get the ApplicationInfo object for the referring app
        ApplicationInfo referringAppInfo = null;
        try {
            referringAppInfo = pm.getApplicationInfo(referringPackageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            // Handle the exception if the package name is not found
        }

// Get the referring app's name
        String referringAppName = (String) (referringAppInfo != null ? pm.getApplicationLabel(referringAppInfo) : "Unknown");

        Log.d("DeepLink_abc", "App Name: " + referringAppName);


// Use the referring app's name in your app as needed
    }


    private void sol6(){
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo app : apps) {
            IntentFilter filter = new IntentFilter(Intent.ACTION_SEND);
            filter.addDataScheme("android-app");
//            filter.addDataAuthority("myapp.com", null);
            filter.addDataPath("/first_screen", PatternMatcher.PATTERN_LITERAL);
            filter.addCategory(Intent.CATEGORY_DEFAULT);

            List<ResolveInfo> matches = pm.queryIntentActivities(new Intent().setAction(Intent.ACTION_SEND).addCategory(Intent.CATEGORY_DEFAULT).setData(Uri.parse("https://www.deeplinkdemo.com/first_screen")), PackageManager.MATCH_DEFAULT_ONLY | PackageManager.GET_RESOLVED_FILTER);
            if (matches.size() > 0 && matches.get(0) != null) {
                String callingPackageName = matches.get(0).resolvePackageName;
                // Do something with the calling package name
                Log.d("FIND_FIND", "sol6: "+callingPackageName);

                break;
            }
        }
    }


    private static final String REFERRER_NAME = "android.intent.extra.REFERRER_NAME";


    private Uri getReferrerCompatible(Activity activity) {
        Intent intent = activity.getIntent();
        Uri referrerUri = intent.getParcelableExtra(Intent.EXTRA_REFERRER);
        if (referrerUri != null) {
            return referrerUri;
        }
        String referrer = intent.getStringExtra(REFERRER_NAME);
        if (referrer != null) {
            // Try parsing the referrer URL; if it's invalid, return null
            try {
                return Uri.parse(referrer);
            } catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static String getReferrerData(Activity activity) {
        if (activity == null)
            return null;
        try {
            Intent intent = activity.getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Set<String> keySet = bundle.keySet();
                for (String k : keySet) {
                    if (k.contains("application_id")) {
                        return bundle.getString(k);
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                Uri referrer = activity.getReferrer();
                if (referrer != null) {
                    return referrer.toString();
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }



}