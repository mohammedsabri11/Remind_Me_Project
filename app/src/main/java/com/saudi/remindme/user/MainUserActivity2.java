package com.saudi.remindme.user;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.saudi.remindme.AlarmReceiver;
import com.saudi.remindme.R;
import com.saudi.remindme.account.LogInActivity;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.databinding.ActivityMainUser2Binding;
import com.saudi.remindme.statedialog.ConfirmationDialog;
import com.saudi.remindme.user.ui.fragment.InfoFragment;
import com.saudi.remindme.user.ui.fragment.OngoingQueryFragment;
import com.saudi.remindme.user.ui.fragment.PatientGameListFragment;
import com.saudi.remindme.user.ui.fragment.ReminderListFragment;

import java.util.Calendar;

public class MainUserActivity2 extends AppCompatActivity {


    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainUser2Binding binding = ActivityMainUser2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = MainUserActivity2.this;
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Remind Me");

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            final int[] TAB_TITLES = new int[]{R.string.user_tab_text_1, R.string.user_tab_text_2, R.string.user_tab_text_3, R.string.user_tab_text_4};

            tab.setText(getResources().getString(TAB_TITLES[position]));

        }).attach();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if (item.getItemId() == R.id.log_out_from_app) {
            // createNotification2 ();
            String title = getResources().getString(R.string.confirm_title);
            String mess = getResources().getString(R.string.log_out_message_confirm);


            ConfirmationDialog.showConfirmationDialog(this, title, mess, this::logout);
            return true;
        } else

            return super.onOptionsItemSelected(item);

    }

    private void logout() {

        SessionManager.getInstance(mContext).logout();
        Intent intent = new Intent(mContext, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    public void createNotification2() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("details", "drug");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        long triggerTime = calendar.getTimeInMillis(); // Set the reminder to trigger immediately

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime + 5000, pendingIntent);
    }

    public static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return PatientGameListFragment.newInstance();
                case 1:

                    return OngoingQueryFragment.newInstance();
                case 2:
                    return ReminderListFragment.newInstance();
                case 3:
                    return InfoFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 4; // Number of fragments
        }
    }
}