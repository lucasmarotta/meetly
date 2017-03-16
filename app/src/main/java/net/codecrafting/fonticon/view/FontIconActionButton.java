package net.codecrafting.fonticon.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import net.codecrafting.fonticon.TypefaceHandler;

public class FontIconActionButton extends FontIconButton {

    private static final String TAG = FontIconActionButton.class.getSimpleName();
    protected static final int DEFAULT_LAYOUT_SIZE = 56;
    protected static final int DEFAULT_TEXT_SIZE = 24;
    protected static final int DEFAULT_BACKGROUND_COLOR = Color.rgb(0,0,0);

    protected String mIconFont = "";

    /**
     * FontIconActionButton Constructor
     * @param context Context caller
     */
    public FontIconActionButton(Context context)
    {
        this(context,null,0);
    }

    /**
     * FontIconActionButton Construtor
     * @param context Context caller
     * @param attrs Attribute set
     */
    public FontIconActionButton(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }

    /**
     * FontIconActionButton Construtor
     * @param context Context caller
     * @param attrs Attribute set
     */
    public FontIconActionButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context,attrs,defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    /**
     * Initialize FontIcon Element
     * @param context Context caller
     * @param attrs Attribute set
     * @param defStyleAttr Style Attribute Definition
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr)
    {
        setDefaultStyles();
        setIconBackground();
    }

    /**
     * Set circle default icon background. Only colored ones are applicable
     */
    protected void setIconBackground()
    {

        Drawable background = this.getBackground();
        GradientDrawable buttonDrawable =  new GradientDrawable();
        buttonDrawable.setColor(DEFAULT_BACKGROUND_COLOR);

        if(background != null) {
            if(background instanceof ColorDrawable) {
                buttonDrawable.setColor(((ColorDrawable) background).getColor());
            } else if(background instanceof GradientDrawable) {
                buttonDrawable = (GradientDrawable) background;
            }
        }

        buttonDrawable.setShape(GradientDrawable.OVAL);
        setBackground(buttonDrawable);
    }

    /**
     * Set default button style
     */
    protected void setDefaultStyles()
    {

        //Set Layout Size
        setWidth(TypefaceHandler.getPixels(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LAYOUT_SIZE));
        setHeight(TypefaceHandler.getPixels(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_LAYOUT_SIZE));

        //Set Font Size
        setTextSize(TypedValue.COMPLEX_UNIT_SP,DEFAULT_TEXT_SIZE);

        //Align text to center
        setGravity(Gravity.CENTER);

    }
}
