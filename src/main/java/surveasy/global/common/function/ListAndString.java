package surveasy.global.common.function;

import java.util.List;

public class ListAndString {

    public static String listToString(List<Integer> intList) {
        String str = intList.toString();
        return str.substring(1, str.length()-1);
    }
}
