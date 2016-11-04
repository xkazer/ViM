package tencent.vim.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.TIMUserProfile;

import tencent.vim.R;
import tencent.vim.present.ProfilePresenter;
import tencent.vim.present.view.IProfileView;

/**
 * Created by xkazerzhang on 2016/11/3.
 */
public class ProfileFragment extends Fragment implements IProfileView, View.OnClickListener, View.OnFocusChangeListener{
    private final static String TAG = "ViM-ProfileFragment";

    private ProfilePresenter presenter;
    private LinearLayout llBackGround;
    private ImageView ivIcon, ivEditNickName, ivEditSign;
    private EditText etNickName, etId, etSign;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        llBackGround = (LinearLayout)getView().findViewById(R.id.ll_background);
        ivIcon = (ImageView)getView().findViewById(R.id.iv_icon);
        etNickName = (EditText)getView().findViewById(R.id.et_nick_name);
        etId = (EditText)getView().findViewById(R.id.et_id);
        etSign = (EditText)getView().findViewById(R.id.et_sign);

        ivEditNickName = (ImageView)getView().findViewById(R.id.iv_edit_nick_name);
        ivEditSign = (ImageView)getView().findViewById(R.id.iv_edit_sign);

        ivEditNickName.setOnClickListener(this);
        ivEditSign.setOnClickListener(this);

        etNickName.setOnFocusChangeListener(this);
        etSign.setOnFocusChangeListener(this);

        presenter = new ProfilePresenter(this);
        presenter.pullMyProfile();

        llBackGround.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                llBackGround.setFocusable(true);
                llBackGround.setFocusableInTouchMode(true);
                llBackGround.requestFocus();
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
        case R.id.iv_edit_nick_name:
            etNickName.setEnabled(true);
            etNickName.setFocusableInTouchMode(true);
            etNickName.requestFocus();
            break;
        case R.id.iv_edit_sign:
            etSign.setEnabled(true);
            etSign.setFocusableInTouchMode(true);
            etSign.requestFocus();
            break;
        }
    }

    @Override
    public void onFocusChange(View view, boolean bFocus) {
        if (!bFocus){
            switch (view.getId()){
            case R.id.et_nick_name:
                presenter.setNickName(etNickName.getText().toString());
                etNickName.setEnabled(false);
                break;
            case R.id.et_sign:
                presenter.setSign(etSign.getText().toString());
                etSign.setEnabled(false);
                break;
            }
        }
    }

    @Override
    public void onDataUpdate(TIMUserProfile profile) {
        etNickName.setText(profile.getNickName());
        etId.setText(profile.getIdentifier());
        etSign.setText(profile.getSelfSignature());
    }

    @Override
    public void onModifySuccess() {
        Toast.makeText(getContext(), R.string.modify_success, Toast.LENGTH_SHORT).show();
    }
}
