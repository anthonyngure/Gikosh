package ke.co.toshngure.gikosh.utils;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Anthony Ngure on 31/12/2017.
 * Email : anthonyngure25@gmail.com.
 */

public class Utils {

    public static String[] arrayFromHashSeparatedString(String hashSeparatedString){
        if (TextUtils.isEmpty(hashSeparatedString)){
            return new String[]{};
        }
        return hashSeparatedString.split("#");
    }

    public static String hashSeparatedString(List<String> stringList){
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        for (String string : stringList){
            if (count == (stringList.size() - 1)){
                stringBuilder.append(string);
            }else {
                stringBuilder.append(string).append("#");
            }
            count++;
        }
        return stringBuilder.toString();
    }
}
