package net.codecrafting.fonticon.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;

import net.codecrafting.fonticon.TypefaceHandler;

import br.ufba.dcc.meetly.R;

import static android.content.ContentValues.TAG;

public class FontIconButton extends Button
{

    protected String mIconFont = "";

    /**
     * FontIconButton Constructor
     * @param context Context caller
     */
    public FontIconButton(Context context)
    {
        this(context,null,0);
    }

    /**
     * FontIconButton Construtor
     * @param context Context caller
     * @param attrs Attribute set
     */
    public FontIconButton(Context context, AttributeSet attrs)
    {
        this(context, attrs,0);
    }

    /**
     * FontIconButton Full Constructor
     * @param context Context caller
     * @param attrs Attribute set
     * @param defStyleAttr Style Attribute Definition
     */
    public FontIconButton(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    /**
     * Initialize FontIconButton Element
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
