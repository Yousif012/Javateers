package com.example.notes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import jp.wasabeef.richeditor.RichEditor;

public class RichEditorBackEvent extends RichEditor {
    // creates a custom RichEditor with a listener for the back button
    // so that we can switch between MathJaxView and RichEditor when we're done typing

    private RichEditorImeBackListener mOnImeBack;

    public RichEditorBackEvent(Context context) {
        super(context);
    }

    public RichEditorBackEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichEditorBackEvent(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && 
            event.getAction() == KeyEvent.ACTION_UP) {
            if (mOnImeBack != null) 
                mOnImeBack.onImeBack(this, this.getHtml().toString());
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnRichEditorImeBackListener(RichEditorImeBackListener listener) {
        mOnImeBack = listener;
    }

}

interface RichEditorImeBackListener {
    public abstract void onImeBack(RichEditorBackEvent ctrl, String text);
}