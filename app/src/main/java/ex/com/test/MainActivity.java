package ex.com.test;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import cn.ieclipse.af.util.AppUtils;
import cn.ieclipse.af.util.BitmapUtil;
import cn.ieclipse.af.util.FileUtil;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private Spinner spn1;
    private Spinner spn2;
    private ImageView image1;
    private ImageView image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null)
                    .show();
            }
        });

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);

        spn1 = (Spinner) findViewById(R.id.spn1);
        spn2 = (Spinner) findViewById(R.id.spn2);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        spn1.setOnItemSelectedListener(this);
        spn2.setOnItemSelectedListener(this);
    }

    private int[] images = {R.mipmap.png8, R.mipmap.png24, R.mipmap.png32, R.mipmap.jpeg_80};

    private Bitmap.Config[] configs
        = {Bitmap.Config.ALPHA_8, Bitmap.Config.ARGB_4444, Bitmap.Config.ARGB_8888, Bitmap.Config.RGB_565};

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int image = images[spn1.getSelectedItemPosition()];
        image1.setImageResource(image);
        image1.setDrawingCacheEnabled(true);
        Bitmap b = image1.getDrawingCache();
        if (b != null) {
            tv3.setText(FileUtil.formatFileSize(BitmapUtil.getSizeInBytes(b)));
        }
        else {
            tv3.setText("bitmap null");
        }
        image1.setDrawingCacheEnabled(false);

        Bitmap.Config config = configs[spn2.getSelectedItemPosition()];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), image, options);
        image2.setImageBitmap(bitmap);
        tv4.setText(FileUtil.formatFileSize(BitmapUtil.getSizeInBytes(bitmap)));

        showMemInfo();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void showMemInfo() {
        String sys = String.format("model:%s,sdk:%s", AppUtils.getModel(), AppUtils.getSDKVersion());

        String runtime = String.format("runtime/heap memory,max(app max memory)=%s,total(app use from os)=%s,free=%s",
            FileUtil.formatFileSize(Runtime.getRuntime().maxMemory()),
            FileUtil.formatFileSize(Runtime.getRuntime().totalMemory()),
            FileUtil.formatFileSize(Runtime.getRuntime().freeMemory()));
        ActivityManager.MemoryInfo memoryInfo = AppUtils.getMemory(this);
        String amMem = String.format("os memory, total=%s,available=%s, used=%s%%",
            FileUtil.formatFileSize(memoryInfo.totalMem), FileUtil.formatFileSize(memoryInfo.availMem),
            (memoryInfo.totalMem - memoryInfo.availMem) * 100 / memoryInfo.totalMem);
        tv1.setText(String.format("%s\n%s\n%s", sys, runtime, amMem));

        DisplayMetrics dm = AppUtils.getDisplayMetrics(this);
        String screen = String.format(
            "width=%d,height=%d,densityDpi=%d,status bar height=%d,softKey=%s, " + "softKeyHeight=%s", dm.widthPixels,
            dm.heightPixels, dm.densityDpi, AppUtils.getStatusBarHeight(this), AppUtils.hasVirtualSoftKey(this),
            AppUtils.getSoftKeyBarHeight(this));
        tv2.setText(screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
