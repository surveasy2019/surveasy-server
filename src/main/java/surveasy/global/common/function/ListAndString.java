package surveasy.global.common.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAndString {

    public static String listToString(List<Integer> intList) {
        String str = intList.toString();
        return str.substring(1, str.length()-1);
    }

    public static List<Integer> strToIntegerList(String strList) {
        String[] convertedArray = strList.split(",");
        List<Integer> convertedList = new ArrayList<>();

        for(String strNum : convertedArray) {
            convertedList.add(Integer.parseInt(strNum));
        }

        return convertedList;
    }
}
