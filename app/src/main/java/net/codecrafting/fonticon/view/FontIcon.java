package net.codecrafting.fonticon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import net.codecrafting.fonticon.TypefaceHandler;

import br.ufba.dcc.meetly.R;

import static android.content.ContentValues.TAG;

public class FontIcon extends TextView {

    protected String mIconFont = "";

    /**
     * FontIcon Constructor
     * @param context Context caller
     */
    public FontIcon(Context context)
    {
        this(context,null,0);
    }

    /**
     * FontIcon Construtor
     * @param context Context caller
     * @param attrs Attribute set
     */
    public FontIcon(Context context, AttributeSet attrs)
    {
        this(context,attrs,0);
    }

    /**
     * FontIcon Full Constructor
     * @param context Context caller
     * @param attrs Attribute set
     * @param defStyleAttr Style Attribute Definition
     */
    public FontIcon(Context context, AttributeSet attrs, int defStyleAttr)
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
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontIcon, 0, 0);
        try {
            String iconFontAttr = ta.getString(R.styleable.FontIcon_iconFont);
            setIconFont(iconFontAttr);
        } finally {
            ta.recycle();
        }
    }

    /**
     * Get Icon Font name
     * @return Icon Font String name
     */
    public String getIconFont()
    {
        return mIconFont;
    }

    /**
     * Set Icon Font name and Typeface
     * @param iconFont Icon Font String name
     */
    public void setIconFont(String iconFont)
    {
        Context context = getContext();
        try {
            mIconFont = iconFont;
            setTypeface(TypefaceHandler.get(context, "fonts/"+ iconFont));
        } catch(Exception e) {
            Log.e(TAG,e.getMessage());
        }
    }

}
