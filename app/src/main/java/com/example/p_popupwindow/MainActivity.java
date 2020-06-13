package com.example.p_popupwindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    private PopupWindow popupWindow;
    private boolean noShow;
    private ConstraintLayout contanier;
    private String TAG ="hank";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contanier = findViewById(R.id.contanier);
    }

    public void popNavigation(View view) {

        //設定全螢幕陰影透明
        final Window window = getWindow();
        final WindowManager.LayoutParams params = window.getAttributes();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        params.dimAmount = 0.7f;
        params.alpha = 0.5f;
        window.setAttributes(params);

        //取得popupWindow物件
        View viewNavigation = LayoutInflater.from(MainActivity.this).inflate(R.layout.top_navigation,null);
        popupWindow = new PopupWindow(viewNavigation, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,true);

        //設定外部可觸摸關掉
        popupWindow.setFocusable(true);//設定可為焦點
        popupWindow.setOutsideTouchable(true); //設定外部可觸摸
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //設定特效
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);

        int animationStyle = popupWindow.getAnimationStyle();
        Log.v(TAG,"animationStyle:" + animationStyle);

        //當觸popWindow狀態時摸螢幕
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE && !popupWindow.isFocusable()) {
                    Log.v("hank","ACTION_OUTSIDE");
                    return true;
                }else{
                    popupWindow.dismiss();
                    params.dimAmount = 1.0f;
                    params.alpha = 1.0f;
                    window.setAttributes(params);
                    if(noShow){

                        noShow = false;
                    }
                    Log.v("hank","onTouch()");
                }
                //否则default，往下dispatch事件:关掉popupWindow，

                return false;

            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.v("hank","onDismiss()");


            }
        });

        //設定popupWindow彈出的位置
        popupWindow.showAtLocation(contanier, Gravity.TOP,0,0);
    }
}
