package surveasy.global.common.dto;

import java.util.List;

public class ListAndString {

    public String listToString(List<String> intList) {
        return String.join(",", intList);
    }
}
