package com.example.tmh.calculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class CalFragment extends Fragment {
    public interface IMyCallBack {
        void setData(String data);
    }

    private IMyCallBack myCallBack;
    private View mView;

    public final static String PREF_KEY = "data";
    private final static String NUM0 = "0", NUM1 = "1", NUM2 = "2", NUM3 = "3";
    private final static String NUM4 = "4", NUM5 = "5", NUM6 = "6", NUM7 = "7", NUM8 = "8";
    private final static String NUM9 = "9", DOT = ".";
    private final static String ADD = "+", SUB = "-", MUL = "x", DIV = "/";

    private TextView mTextNum1, mTextNum2, mTextSign;
    private Button mButton0, mButton1, mButton2, mButton3, mButton4, mButton5, mButton6;
    private Button mButton7, mButton8, mButton9, mButtonDot;
    private Button mButtonAdd, mButtonSub, mButtonMul, mButtonDiv;
    private Button mButtonAc, mButtonOpposite, mButtonIntergerPart, mButtonResult, mButtonDel;

    private String mSign;
    private double mNum1, mNum2;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_cal, container, false);
        mapped();
        mTextNum2.setText(loadPre(PREF_KEY));
        setClick();
        return mView;
    }

    public void mapped() {
        mTextNum1 = (TextView) mView.findViewById(R.id.text_num1);
        mTextNum2 = (TextView) mView.findViewById(R.id.text_num2);
        mTextSign = (TextView) mView.findViewById(R.id.text_sign);
        mButtonDel = (Button) mView.findViewById(R.id.button_del);
        mButton0 = (Button) mView.findViewById(R.id.button_0);
        mButton1 = (Button) mView.findViewById(R.id.button_1);
        mButton2 = (Button) mView.findViewById(R.id.button_2);
        mButton3 = (Button) mView.findViewById(R.id.button_3);
        mButton4 = (Button) mView.findViewById(R.id.button_4);
        mButton5 = (Button) mView.findViewById(R.id.button_5);
        mButton6 = (Button) mView.findViewById(R.id.button_6);
        mButton7 = (Button) mView.findViewById(R.id.button_7);
        mButton8 = (Button) mView.findViewById(R.id.button_8);
        mButton9 = (Button) mView.findViewById(R.id.button_9);
        mButtonDot = (Button) mView.findViewById(R.id.button_dot);
        mButtonAdd = (Button) mView.findViewById(R.id.button_add);
        mButtonSub = (Button) mView.findViewById(R.id.button_sub);
        mButtonMul = (Button) mView.findViewById(R.id.button_mul);
        mButtonDiv = (Button) mView.findViewById(R.id.button_div);
        mButtonAc = (Button) mView.findViewById(R.id.button_ac);
        mButtonOpposite = (Button) mView.findViewById(R.id.button_opposite);
        mButtonIntergerPart = (Button) mView.findViewById(R.id.button_integerpart);
        mButtonResult = (Button) mView.findViewById(R.id.button_result);
    }

    public void setClick() {
        mButton0.setOnClickListener(clickNumber);
        mButton1.setOnClickListener(clickNumber);
        mButton2.setOnClickListener(clickNumber);
        mButton3.setOnClickListener(clickNumber);
        mButton4.setOnClickListener(clickNumber);
        mButton5.setOnClickListener(clickNumber);
        mButton6.setOnClickListener(clickNumber);
        mButton7.setOnClickListener(clickNumber);
        mButton8.setOnClickListener(clickNumber);
        mButton9.setOnClickListener(clickNumber);
        mButtonDot.setOnClickListener(clickNumber);

        mButtonAdd.setOnClickListener(clickDau);
        mButtonSub.setOnClickListener(clickDau);
        mButtonMul.setOnClickListener(clickDau);
        mButtonDiv.setOnClickListener(clickDau);

        mButtonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a = mTextNum2.getText().toString();
                if (TextUtils.isEmpty(a)) {
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                } else {
                    mTextNum2.setText(a.substring(0, a.length() - 1));
                }

            }
        });


        mButtonAc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextNum1.setText(null);
                mTextNum2.setText(null);
                mTextSign.setText(null);
            }
        });

        mButtonOpposite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTextNum2.getText().toString())) {
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                } else {
                    mNum2 = Float.parseFloat(mTextNum2.getText().toString());
                    mTextNum2.setText(String.valueOf(-mNum2));
                    myCallBack.setData(String.valueOf(-mNum2));
                }
            }

        });

        mButtonIntergerPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTextNum2.getText().toString())) {
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                } else {
                    mNum2 = Float.parseFloat(mTextNum2.getText().toString());
                    mTextNum2.setText(String.valueOf((int) mNum2));
                    myCallBack.setData(String.valueOf((int) mNum2));
                }

            }
        });

        mButtonResult.setOnClickListener(new View.OnClickListener() {
            double result;

            @Override
            public void onClick(View view) {
                mSign = mTextSign.getText().toString();
                String soA = mTextNum1.getText().toString();
                String soB = mTextNum2.getText().toString();
                if (TextUtils.isEmpty(soA) || TextUtils.isEmpty(soB) || TextUtils.isEmpty(mSign)) {
//                    Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
                } else {
                    mNum1 = Float.parseFloat(soA);
                    mNum2 = Float.parseFloat(soB);

                    switch (mSign) {
                        case ADD:
                            result = mNum1 + mNum2;
                            break;
                        case SUB:
                            result = mNum1 - mNum2;
                            break;
                        case MUL:
                            result = mNum1 * mNum2;
                            break;
                        case DIV:
                            result = mNum1 / mNum2;
                            break;

                    }
                    mTextNum1.setText(null);
                    mTextSign.setText(null);
                    mTextNum2.setText(String.valueOf(result));
                    myCallBack.setData(String.valueOf(result));
                }
            }
        });
    }


    View.OnClickListener clickNumber = new View.OnClickListener() {
        String number;

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_0:
                    number = NUM0;
                    break;
                case R.id.button_1:
                    number = NUM1;
                    break;
                case R.id.button_2:
                    number = NUM2;
                    break;
                case R.id.button_3:
                    number = NUM3;
                    break;
                case R.id.button_4:
                    number = NUM4;
                    break;
                case R.id.button_5:
                    number = NUM5;
                    break;
                case R.id.button_6:
                    number = NUM6;
                    break;
                case R.id.button_7:
                    number = NUM7;
                    break;
                case R.id.button_8:
                    number = NUM8;
                    break;
                case R.id.button_9:
                    number = NUM9;
                    break;
                case R.id.button_dot:
                    number = DOT;
                    break;
            }
            String old = mTextNum2.getText().toString();
            mTextNum2.setText(old + number);
        }
    };
    View.OnClickListener clickDau = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_add:
                    mSign = ADD;
                    break;
                case R.id.button_sub:
                    mSign = SUB;
                    break;
                case R.id.button_mul:
                    mSign = MUL;
                    break;
                case R.id.button_div:
                    mSign = DIV;
                    break;
            }
            mTextNum1.setText(mTextNum2.getText().toString());
            mTextSign.setText(mSign);
            mTextNum2.setText(null);
        }
    };

    private void savePre(String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String loadPre(String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String value = preferences.getString(key, null);
        return value;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_option, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                mTextNum1.setText(null);
                mTextNum2.setText(null);
                mTextSign.setText(null);
                savePre(PREF_KEY, null);
                break;
            case R.id.menu_save:
                savePre(PREF_KEY, mTextNum2.getText().toString());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            myCallBack = (IMyCallBack) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement setData");
        }
    }
}
