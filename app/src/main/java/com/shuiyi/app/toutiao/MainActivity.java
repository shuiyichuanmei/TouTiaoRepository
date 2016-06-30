package com.shuiyi.app.toutiao;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.shuiyi.app.toutiao.common.Common;
import com.shuiyi.app.toutiao.common.DoubleClickExitHelper;
import com.shuiyi.app.toutiao.common.UpdateAppManager;


public class MainActivity extends AppCompatActivity {
    private Fragment[] mFragments;
    private RadioGroup bottomRg;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbOne, rbTwo, rbThree, rbFour;
    private UpdateAppManager updateManager;
    private DoubleClickExitHelper doubleClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        updateManager = new UpdateAppManager(this);
        updateManager.checkUpdateInfo();

        mFragments = new Fragment[4];
        fragmentManager = getSupportFragmentManager();
        mFragments[0] = fragmentManager.findFragmentById(R.id.fragement_one);
        mFragments[1] = fragmentManager.findFragmentById(R.id.fragement_two);
        mFragments[2] = fragmentManager.findFragmentById(R.id.fragement_three);
        mFragments[3] = fragmentManager.findFragmentById(R.id.fragement_four);

        fragmentTransaction = fragmentManager.beginTransaction()
                .hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]).hide(mFragments[3]);
        fragmentTransaction.show(mFragments[0]).commit();
        setFragmentIndicator();
        doubleClick = new DoubleClickExitHelper(this);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return doubleClick.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
    private void setFragmentIndicator() {

        bottomRg = (RadioGroup) findViewById(R.id.bottomRg);
        rbOne = (RadioButton) findViewById(R.id.rbOne);
        rbTwo = (RadioButton) findViewById(R.id.rbTwo);
        rbThree = (RadioButton) findViewById(R.id.rbThree);
        rbFour = (RadioButton) findViewById(R.id.rbFour);

        for (int i = 0; i < bottomRg.getChildCount(); i++) {
            RadioButton rbtn = (RadioButton) bottomRg.getChildAt(i);
            Drawable[] drawables = rbtn.getCompoundDrawables();
            drawables[1].setBounds(0, 0, 50, 50);
            rbtn.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);

        }

        bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                fragmentTransaction = fragmentManager.beginTransaction()
                        .hide(mFragments[0]).hide(mFragments[1])
                        .hide(mFragments[2]).hide(mFragments[3]);
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

                    case R.id.rbFour:
                        fragmentTransaction.show(mFragments[3]).commit();
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
