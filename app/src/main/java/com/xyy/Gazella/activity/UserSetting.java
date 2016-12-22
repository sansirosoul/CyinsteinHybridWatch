package com.xyy.Gazella.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.kevin.crop.UCrop;
import com.xyy.Gazella.utils.CalendarDialog;
import com.xyy.Gazella.utils.HeightDialog;
import com.xyy.Gazella.utils.SharedPreferencesUtils;
import com.xyy.Gazella.utils.WeightDialog;
import com.xyy.Gazella.view.RoundImageView;
import com.xyy.model.User;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by Administrator on 2016/10/25.
 */

public class UserSetting extends BaseActivity {

    @BindView(R.id.head)
    RoundImageView head;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.ll_birth)
    LinearLayout llBirth;

    @BindView(R.id.tv_height)
    TextView tvHeight;
    @BindView(R.id.ll_height)
    LinearLayout llHeight;
    @BindView(R.id.tv_weight)
    TextView tvWeight;
    @BindView(R.id.ll_weight)
    LinearLayout llWeight;
    @BindView(R.id.back)
    RelativeLayout back;
    @BindView(R.id.save)
    RelativeLayout save;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    private Context context;
    private WeightDialog.OnSelectedListener wOnSelectedListener;
    private HeightDialog.OnSelectedListener hSelectedListener;
    private CalendarDialog.OnSelectedListener cSelectedListener;
    private SharedPreferencesUtils preferencesUtils;
    private int sex = -1;
    private TimePickerView pvTime;
    private Date date;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.user_setting_activity);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        User user = PreferenceData.getUserInfo(context);
        if (user.getName() != null && !user.getName().equals("")) {
            edName.setText(user.getName());
            edName.setSelection(user.getName().length());
        }
        if (user.getBirthday() != null && !user.getBirthday().equals("")) {
            tvBirth.setText(user.getBirthday());
            tvBirth.setTextColor(context.getResources().getColor(R.color.white));
        }
        if (user.getHeight() != null && !user.getHeight().equals("")) {
            tvHeight.setText(user.getHeight());
            tvHeight.setTextColor(context.getResources().getColor(R.color.white));
        }
        if (user.getWeight() != null && !user.getWeight().equals("")) {
            tvWeight.setText(user.getWeight());
            tvWeight.setTextColor(context.getResources().getColor(R.color.white));
        }

        File f = new File(Environment.getExternalStorageDirectory() + "/" + "userImage.png");
        if (!f.exists())
            head.setBackground(getResources().getDrawable(R.drawable.page5_head_portrait));
        else
            head.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + "userImage.png"));

        if (user.getSex() != -1) {
            sex = user.getSex();
            if (sex == 0) {
                rbMale.setChecked(true);
            } else if(sex == 1){
                rbFemale.setChecked(true);
            }
        }

        rbMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sex=0;
                }
            }
        });

        rbFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sex=1;
                }
            }
        });

        wOnSelectedListener = new WeightDialog.OnSelectedListener() {
            @Override
            public void onSelected(String text) {
                tvWeight.setText(text);
                tvWeight.setTextColor(context.getResources().getColor(R.color.white));
            }
        };

        hSelectedListener = new HeightDialog.OnSelectedListener() {
            @Override
            public void onSelected(String text) {
                tvHeight.setText(text);
                tvHeight.setTextColor(context.getResources().getColor(R.color.white));
            }
        };

        cSelectedListener = new CalendarDialog.OnSelectedListener() {
            @Override
            public void onSelected(String text) {
                tvBirth.setText(text);
                tvBirth.setTextColor(context.getResources().getColor(R.color.white));
            }
        };

        try {
            date = sdf.parse("1990.1.1");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 100, calendar.get(Calendar.YEAR) + 100);//要在setTime 之前才有效果
        pvTime.setTime(date);
        pvTime.setCancelable(true);
        pvTime.setCyclic(true);

        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                tvBirth.setText(sdf.format(date));
                tvBirth.setTextColor(context.getResources().getColor(R.color.white));
            }
        });
    }

    @OnClick({R.id.back, R.id.head, R.id.ll_birth, R.id.ll_height, R.id.ll_weight, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                overridePendingTransitionExit(UserSetting.this);
                break;
            case R.id.save:
                if (edName.getText() == null || edName.getText().toString().equals("")) {
                    Toast.makeText(context, R.string.input_name, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (edName.getText().toString().length() > 20) {
                    Toast.makeText(context, R.string.name_too_long, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvBirth.getText().equals(getResources().getString(R.string.choose_birth))) {
                    Toast.makeText(context, R.string.choose_birth, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sex == -1) {
                    Toast.makeText(context, R.string.choose_sex, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvHeight.getText().equals(getResources().getString(R.string.choose_height))) {
                    Toast.makeText(context, R.string.choose_height, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (tvWeight.getText().equals(getResources().getString(R.string.choose_weight))) {
                    Toast.makeText(context, R.string.choose_weight, Toast.LENGTH_SHORT).show();
                    return;
                }
                PreferenceData.setUserInfo(context, edName.getText().toString(), tvBirth.getText().toString(),
                        sex, tvHeight.getText().toString(), tvWeight.getText().toString());
                finish();
                overridePendingTransitionExit(UserSetting.this);
                break;
            case R.id.head:
                PhotoPicker.builder()
                        .setShowCamera(true)
                        .setPhotoCount(1)
                        .start(this);
                break;
            case R.id.ll_birth:
                //隐藏软健盘
                View v = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                pvTime.show();
                break;
            case R.id.ll_height:
                HeightDialog heightDialog = new HeightDialog(context);
                heightDialog.setOnSelectedListener(hSelectedListener);
                heightDialog.show();
                break;
            case R.id.ll_weight:
                WeightDialog weightDialog = new WeightDialog(context);
                weightDialog.setOnSelectedListener(wOnSelectedListener);
                weightDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1000 && data != null) {
            Uri resultUri = UCrop.getOutput(data);
            if (resultUri != null)
                head.setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + "userImage.png"));
        }
    }
}
