package bupt.atp.app.activities;

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

import bupt.atp.app.R;
import bupt.atp.app.activities.fragments.DataFragment;
import bupt.atp.app.activities.fragments.DownloadRequestCommitter;
import bupt.atp.app.activities.fragments.FTPRequestCommitter;
import bupt.atp.app.activities.fragments.PersonalCenterFragment;
import bupt.atp.app.services.FTPService;

public class MainActivity extends AppCompatActivity {

    private FTPService   ftpService   = null;
    private DrawerLayout drawerLayout = null;
    private DataFragment dataFragment = new DataFragment();

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            ftpService = ((bupt.atp.app.services.FTPService.InnerBinder) iBinder).getService();
            ftpService.setQueryResultHandler(dataFragment);
            ftpService.setStateChangedHandler(dataFragment);

            dataFragment.setQueryCommitter(new FTPRequestCommitter() {
                @Override
                public void commit(String path) {
                    ftpService.startQueryFiles(path);
                }
            });

            dataFragment.setDownloadCommitter(new DownloadRequestCommitter(ftpService));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                Log.wtf("MainActivity", "[Thread" + thread.getId()  + "]", throwable);
            }
        });

        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.left_drawer_item_info);

        PersonalCenterFragment fragment = new PersonalCenterFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_layout, fragment);
        transaction.commit();

        bindService(new Intent(this, FTPService.class), serviceConnection, BIND_AUTO_CREATE);

        textView.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction transaction = fragmentManager.beginTransaction();
               transaction.replace(R.id.main_layout, dataFragment);
               transaction.commit();

               Log.d("fragment replace", "click");


//               drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
//               drawerLayout.closeDrawer(main_left_drawer_layout);

               drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

               //startActivity(new Intent());
           }
        });
    }

    @Override
    public void onBackPressed() {
        ftpService.startQueryFiles(
                dataFragment.getAdapter().getFileSystem().getParentPath()
        );
    }
}
