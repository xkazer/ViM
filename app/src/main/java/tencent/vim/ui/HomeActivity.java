package tencent.vim.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;

import tencent.vim.R;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class HomeActivity extends FragmentActivity {
    private static final String TAG = "ViM-HomeActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTabHost tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabHost.addTab(tabHost.newTabSpec("Communication")
                        .setIndicator(getString(R.string.comunication)),
                ContactFragment.class,
                null);

        tabHost.addTab(tabHost.newTabSpec("Meeting")
                        .setIndicator(getString(R.string.meeting)),
                MeetFragment.class,
                null);

        tabHost.addTab(tabHost.newTabSpec("Conversation")
                        .setIndicator(getString(R.string.conversation)),
                ConversationFragment.class,
                null);

        tabHost.addTab(tabHost.newTabSpec("Profile")
                        .setIndicator(getString(R.string.profile)),
                ProfileFragment.class,
                null);
    }
}
