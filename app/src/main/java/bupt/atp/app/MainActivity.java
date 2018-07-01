package bupt.atp.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import bupt.atp.app.adapters.FileAttribute;
import bupt.atp.app.fragments.InfoFragment;
import bupt.atp.app.fragments.PersonalCenterFragment;

public class MainActivity extends AppCompatActivity implements MessageListener, Search, FragmentReflash {

    public CommService commService = null;
    InfoFragment infoFragment = null;
    private DrawerLayout mDrawerLayout;


    private ServiceConnection serviceConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            commService = ((CommService.CommBinder)iBinder).getService();
            commService.setListener(MainActivity.this);
            commService.startQueryFiles("/");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.wtf("Main", "[Thread" + thread.getId()  + "]", throwable);
            }
        };

        Thread.setDefaultUncaughtExceptionHandler(handler);

        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.left_drawer_item_info);

        PersonalCenterFragment fragment = new PersonalCenterFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.commit();

        bindService(new Intent(this, CommService.class), serviceConnection, BIND_AUTO_CREATE);

        textView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               infoFragment = new InfoFragment();
               infoFragment.setFragmentReflash(MainActivity.this);

               infoFragment.setListener(MainActivity.this);
               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction transaction = fragmentManager.beginTransaction();
               transaction.replace(R.id.main_layout, infoFragment);
               transaction.commit();
               Log.d("fragment replace", "click");


//               mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
//               mDrawerLayout.closeDrawer(main_left_drawer_layout);

               mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

               //startActivity(new Intent());
           }
        });
    }

    @Override
    public void onDictChanged(final List<FileAttribute> attrs) {
        if(infoFragment != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    infoFragment.updateList(attrs);
                }
            });
        }
    }

    @Override
    public void onSearchNewDict(String path) {
        commService.startQueryFiles(path);
        Log.d("MaintActivity", "New Dict");
    }

    @Override
    public void onReflashClick(final String path) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commService.startQueryFiles(path);
            }
        });
    }

    @Override
    public void onBackPressed() {
        commService.startQueryFiles(infoFragment.backToParentPath());
    }
}
