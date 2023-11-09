package surveasy.domain.activepanel.dto;

import lombok.Builder;
import lombok.Getter;
import surveasy.global.common.function.ListAndString;

import java.util.List;

@Getter
public class ActivePanelResponse {

    private List<Integer> totalList;
    private List<Integer> maleList;
    private List<Integer> femaleList;

    @Builder
    private ActivePanelResponse(List<Integer> totalList, List<Integer> maleList, List<Integer> femaleList) {
        this.totalList = totalList;
        this.maleList = maleList;
        this.femaleList = femaleList;
    }

    public static ActivePanelResponse of(String totalStrList, String maleStrList, String femaleStrList) {
        return ActivePanelResponse.builder()
                .totalList(ListAndString.strToIntegerList(totalStrList))
                .maleList(ListAndString.strToIntegerList(maleStrList))
                .femaleList(ListAndString.strToIntegerList(femaleStrList))
                .build();
    }
}
