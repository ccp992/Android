package com.cuny.qc.chenc.wordguess;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LetterTile extends TextView {

    public static final int TITLE_SIZE = 150;
    private Character letter;
    private boolean touchable;
    private Character secret;

    public LetterTile(Context context, Character letter, final Boolean touchable) {
        super(context);
        this.touchable = touchable;
        this.letter = letter;
        if (!touchable){
            setText(letter.toString());
        }else {
            setText("?");
            secret = letter;
        }
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setHeight(TITLE_SIZE);
        setWidth(TITLE_SIZE);
        setTextSize(30);
        if (touchable) {
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    setText(secret.toString());
                    return true;
                }
            });
        }
    }
    public void addtoView(LinearLayout viewGroup){
        viewGroup.addView(this);
        setVisibility(View.VISIBLE);
    }

    public void showAnswer(){
        if (touchable){
            setText(secret.toString());
        }
    }
}
