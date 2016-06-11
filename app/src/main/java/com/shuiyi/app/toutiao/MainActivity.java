package com.shuiyi.app.toutiao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MainActivity extends AppCompatActivity {
    private Fragment[] mFragments;
    private RadioGroup bottomRg;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbOne, rbTwo, rbThree, rbFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragments = new Fragment[3];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_one);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_two);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragement_three);
        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();
        setFragmentIndicator();
    }



    private void setFragmentIndicator() {

        bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        rbThree = (RadioButton) findViewById(R.id.rbThree);
        rbOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mFragments[0].isVisible())
                {
                    fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]);
                    fragmentTransaction.show(mFragments[0]).commit();
                }
            }
        });
        bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1])
                        .hide(mFragments[2]);
                switch (checkedId) {
                    case R.id.rbOne:
                        fragmentTransaction.show(mFragments[0]).commit();
                        break;

                    case R.id.rbTwo:
                        fragmentTransaction.show(mFragments[1]).commit();
                        break;

                    case R.id.rbThree:
                        fragmentTransaction.show(mFragments[2]).commit();
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
