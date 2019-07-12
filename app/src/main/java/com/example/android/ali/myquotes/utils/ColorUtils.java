package com.example.android.ali.myquotes.utils;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;

import com.example.android.ali.myquotes.R;

public class ColorUtils {
    private Context context;

    public ColorUtils(Context context){
        this.context = context;
    }

    public int getColorFromString(String colorString){
        int color = ResourcesCompat.getColor(context.getResources(), R.color.cardview_white, null);;
        if(colorString == null){
            return color;
        }

        if(colorString.equals(AppConstants.CARDVIEW_RED)){
            color = ResourcesCompat.getColor(context.getResources(), R.color.cardview_red, null);
        }
        else if(colorString.equals(AppConstants.CARDVIEW_BLUE)){
            color =  ResourcesCompat.getColor(context.getResources(), R.color.cardview_blue, null);
        }
        else if(colorString.equals(AppConstants.CARDVIEW_GREEN)){
            color = ResourcesCompat.getColor(context.getResources(), R.color.cardview_green, null);
        }
        else{
            color = ResourcesCompat.getColor(context.getResources(), R.color.cardview_white, null);
        }

        return color;
    }
}
