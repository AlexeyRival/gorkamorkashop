package com.rivalsoftware.gorkamorkashop;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class CardView extends LinearLayout {

    private TextView cardText;
    private ImageView cardImage;
    private Button cardButton;
    public CardView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CardView,0,0);
        cardText.setText(a.getString(R.styleable.CardView_text));
        cardButton.setText(a.getString(R.styleable.CardView_buttonText));
        cardImage.setImageDrawable(a.getDrawable(R.styleable.CardView_src));
        a.recycle();
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CardView,defStyleAttr,0);
        cardText.setText(a.getString(R.styleable.CardView_text));
        cardButton.setText(a.getString(R.styleable.CardView_buttonText));
        cardImage.setImageDrawable(a.getDrawable(R.styleable.CardView_src));
        a.recycle();
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CardView,defStyleAttr,defStyleRes);
        cardText.setText(a.getString(R.styleable.CardView_text));
        cardButton.setText(a.getString(R.styleable.CardView_buttonText));
        cardImage.setImageDrawable(a.getDrawable(R.styleable.CardView_src));
        a.recycle();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
    }


    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.vertical_shop_card, this);

        cardText = findViewById(R.id.card_text);
        cardButton = findViewById(R.id.card_button);
        cardImage = findViewById(R.id.card_pic);
    }
    public void SetText(String text)
    {
        cardText.setText(text);
    }
    public Button GetButton()
    {
        return cardButton;
    }
    public void SetImage(Bitmap bitmap)
    {
        cardImage.setImageBitmap(bitmap);
    }
}
