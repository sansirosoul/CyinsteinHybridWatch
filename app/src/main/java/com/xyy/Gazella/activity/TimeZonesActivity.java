package com.xyy.Gazella.activity;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xyy.Gazella.adapter.TimeZonesListAdapter;
import com.xyy.model.TimeZonesData;
import com.ysp.hybridtwatch.R;
import com.ysp.newband.BaseActivity;
import com.ysp.newband.PreferenceData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeZonesActivity extends BaseActivity {
    private static final String TAG = TimeZonesActivity.class.getName();
    @BindView(R.id.btnExit)
    LinearLayout btnExit;
    @BindView(R.id.btnOpt)
    Button btnOpt;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.btnDate)
    Button btnDate;
    @BindView(R.id.TVTitle)
    TextView TVTitle;

    private ArrayList<TimeZonesData> dateList = new ArrayList<TimeZonesData>();
    private TimeZonesData data;
    private TimeZonesListAdapter adapter;
    private  int postion=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_zones);
        ButterKnife.bind(this);
        InitView();
    }

    private void InitView() {
        TVTitle.setText(getResources().getString(R.string.Time_Zones));
        btnOpt.setBackground(getResources().getDrawable(R.drawable.page35_baocun));
        getAssetsZones();
       String id= PreferenceData.getTimeZonesState(TimeZonesActivity.this);
        for (int i=0;i<dateList.size();i++){
         if(dateList.get(i).getGtm().equals(id))
             dateList.get(i).setClick(true);
        }
        adapter = new TimeZonesListAdapter(TimeZonesActivity.this, dateList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new ListviewItemClick());
    }
    
    @OnClick({R.id.btnExit, R.id.btnOpt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
                break;
            case R.id.btnOpt:
                if(postion!=-1) {
                    PreferenceData.setTimeZonesState(TimeZonesActivity.this, dateList.get(postion).getGtm());
                    showToatst(TimeZonesActivity.this, getResources().getString(R.string.Time_Zones_ok));
                }
                TimeZonesActivity.this.finish();
                overridePendingTransitionExit(TimeZonesActivity.this);
                break;
        }
    }

    public class ListviewItemClick implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            for (int n = 0; n < dateList.size(); n++) {
                dateList.get(n).setClick(false);
            }
            dateList.get(i).setClick(true);
            adapter.notifyDataSetChanged();
            postion=i;
        }
    }

    private void getAssetsZones() {
        Resources res = getResources();
        XmlResourceParser xrp = res.getXml(R.xml.timezones);
        try {
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    if (name.equals("timezone")) {
                        if (xrp.getAttributeValue(0).equals("Pacific/Majuro")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Majuro));
                            dateList.add(data);
                        }

                        if (xrp.getAttributeValue(0).equals("Pacific/Midway")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Midway));
                            dateList.add(data);
                        }

                        if (xrp.getAttributeValue(0).equals("Pacific/Honolulu")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Honolulu));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Anchorage")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Anchorage));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Los_Angeles")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Los_Angeles));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Tijuana")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Tijuana));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Phoenix")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Phoenix));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Chihuahua")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Chihuahua));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Denver")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Denver));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Costa_Rica")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Costa_Rica));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Chicago")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Chicago));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Mexico_City")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Mexico_City));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Regina")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Regina));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Bogota")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Bogota));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/New_York")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_New_York));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Caracas")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Caracas));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Barbados")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Barbados));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Manaus")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Manaus));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Santiago")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Santiago));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/St_Johns")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_St_Johns));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Sao_Paulo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Sao_Paulo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Argentina/Buenos_Aires")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Argentina_Buenos_Aires));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Godthab")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Godthab));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("America/Montevideo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.America_Montevideo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/South_Georgia")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_South_Georgia));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/Azores")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_Azores));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Atlantic/Cape_Verde")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Atlantic_Cape_Verde));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Casablanca")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Casablanca));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/London")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_London));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Amsterdam")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Amsterdam));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Belgrade")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Belgrade));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Brussels")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Brussels));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Sarajevo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Sarajevo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Windhoek")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Windhoek));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Brazzaville")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Brazzaville));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Amman")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Amman));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Beirut")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Beirut));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Cairo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Cairo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Helsinki")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Helsinki));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Jerusalem")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Jerusalem));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Minsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Minsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Harare")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Harare));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Baghdad")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Baghdad));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Europe/Moscow")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Europe_Moscow));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Kuwait")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Kuwait));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Africa/Nairobi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Africa_Nairobi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tehran")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tehran));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Baku")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Baku));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tbilisi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tbilisi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Yerevan")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Yerevan));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Dubai")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Dubai));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Kabul")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Kabul));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Karachi")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Karachi));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Oral")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Oral));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Yekaterinburg")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Yekaterinburg));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Calcutta")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Calcutta));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Colombo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Colombo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Katmandu")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Katmandu));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Almaty")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Almaty));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Rangoon")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Rangoon));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Krasnoyarsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Krasnoyarsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Bangkok")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Bangkok));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Shanghai")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Shanghai));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Hong_Kong")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Hong_Kong));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Irkutsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Irkutsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Kuala_Lumpur")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Kuala_Lumpur));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Perth")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Perth));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Taipei")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Taipei));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Seoul")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Seoul));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Tokyo")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Tokyo));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Yakutsk")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Yakutsk));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Adelaide")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Adelaide));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Darwin")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Darwin));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Brisbane")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Brisbane));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Hobart")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Hobart));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Australia/Sydney")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Australia_Sydney));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Vladivostok")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Vladivostok));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Guam")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Guam));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Asia/Magadan")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Asia_Magadan));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Auckland")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Auckland));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Fiji")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Fiji));
                            dateList.add(data);
                        }
                        if (xrp.getAttributeValue(0).equals("Pacific/Tongatapu")) {
                            data = new TimeZonesData();
                            data.setGtm(xrp.getAttributeValue(0));
                            data.setName(res.getString(R.string.Pacific_Tongatapu));
                            dateList.add(data);
                        }
                    }
                }
                xrp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
