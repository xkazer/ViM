package tencent.vim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;
import android.widget.ListView;

import tencent.vim.R;
import tencent.vim.com.ViMConstants;

/**
 * Created by xkazerzhang on 2016/11/4.
 */
public class SearchActivity extends FragmentActivity {
    private final static String TAG = "ViM-SearchActivity";

    private boolean bGroupMode = false;

    private EditText etWords;
    private ListView lvResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String mode = intent.getStringExtra(ViMConstants.SEARCH_MODE);
        if (mode.equals(ViMConstants.GROUP_MODE)){
            bGroupMode = true;
        }

        etWords = (EditText)findViewById(R.id.et_words);
        lvResult = (ListView)findViewById(R.id.lv_result);

    }
}
