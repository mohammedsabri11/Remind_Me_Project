package com.saudi.remindme.consultant;

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
import com.saudi.remindme.R;
import com.saudi.remindme.account.LogInActivity;
import com.saudi.remindme.account.SessionManager;
import com.saudi.remindme.consultant.ui.fragment.EvaluationFragment;
import com.saudi.remindme.consultant.ui.fragment.GameFragment;
import com.saudi.remindme.consultant.ui.fragment.OngoingConsultantQueryFragment;
import com.saudi.remindme.databinding.ActivityMainConsultantBinding;
import com.saudi.remindme.statedialog.ConfirmationDialog;

public class MainConsultantActivity extends AppCompatActivity {

    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.saudi.remindme.databinding.ActivityMainConsultantBinding binding = ActivityMainConsultantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mContext = MainConsultantActivity.this;

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Remind Me");

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            final int[] TAB_TITLES = new int[]{R.string.consultant_tab_text_1, R.string.consultant_tab_text_2, R.string.consultant_tab_text_3,};

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

    public static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return GameFragment.newInstance();
                case 1:

                    return OngoingConsultantQueryFragment.newInstance();
                case 2:

                    return EvaluationFragment.newInstance();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 3; // Number of fragments
        }
    }
    // intent.putExtra("BookingInfo", bookingInfo);
}

