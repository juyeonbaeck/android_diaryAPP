package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    //private  mySQLiteOpenHelper  helper = null;   //db 사용

    // 날짜 선택 버튼
    Button mDatePickerBtn;

    // 내용 텍스트
    EditText minputText;

    // 저장 버튼
    Button msaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 날짜 텍스트_일단 오늘날짜 지정
        TextView tv = findViewById(R.id.DatetextView);
        Calendar cal = Calendar.getInstance();
        tv.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));

        //날짜 선택 버튼
        mDatePickerBtn = (Button) findViewById(R.id.DatePickerBtn);

        //내용 텍스트
        minputText = (EditText) findViewById(R.id.inputText);

        // 저장 버튼
        msaveBtn = (Button) findViewById(R.id.saveBtn);
        msaveBtn.setOnClickListener(listener);
    }

    // Date Picker에서 선택한 yy,mm,dd를 TextView에 설정
    DatePickerDialog.OnDateSetListener mDateSetListener = (datePicker, yy, mm, dd) -> {
        TextView tv = findViewById(R.id.DatetextView);
        tv.setText(String.format("%d-%d-%d", yy,mm+1,dd));
    };

    // DATE Picker에서 선택한 날짜를 날짜TextView에 지정_mDateSetListener 함수로
    public void showDatePicker(View view){
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(this, mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

    // 저장 버튼 누를때 listener
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.saveBtn) {
                Log.i("TAG", "save 진행");
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput("memo.txt", Context.MODE_PRIVATE);
                    String out = minputText.getText().toString();
                    fos.write(out.getBytes());
                    Toast.makeText(MainActivity.this, "save 완료", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fos != null) fos.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}