package surveasy.global.common.util;

import surveasy.domain.survey.domain.target.TargetAge;

import java.util.ArrayList;
import java.util.List;

public class ListAndStringUtils {

    public static String listToStr(List<?> intList) {
        String str = intList.toString();
        return str.substring(1, str.length()-1);
    }

    public static List<Integer> strToIntegerList(String strList) {
        String[] convertedArray = strList.split(", ");
        List<Integer> convertedList = new ArrayList<>();

        for(String strNum : convertedArray) {
            convertedList.add(Integer.parseInt(strNum));
        }

        return convertedList;
    }

    public static List<TargetAge> strToTargetAgeList(String strList) {
        String[] convertedArray = strList.split(", ");
        List<TargetAge> convertedList = new ArrayList<>();

        for(String strTargetAge : convertedArray) {
            convertedList.add(TargetAge.valueOf(strTargetAge));
        }

        return convertedList;
    }
}
