package com.gzucm.wsyy.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.gzucm.wsyy.R;

import java.util.Calendar;

public class MainActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDate();

    }

    public static final String DATEPICKER_TAG = "datepicker";
    private void initDate() {
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
        findViewById(R.id.dateButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(1903, 2028);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        int truemonth = month + 1;
        Toast.makeText(MainActivity.this, "new date:" + year + "-" + truemonth + "-" + day, Toast.LENGTH_LONG).show();
    }

}
