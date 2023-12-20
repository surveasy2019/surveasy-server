package surveasy.domain.survey.domain.target;

import lombok.Getter;

import java.util.Calendar;
import java.util.Date;

@Getter
public enum TargetAge {

    ALL(1, "전연령"),
    AGE_20_24(2, "20-24세"),
    AGE_25_29(3, "25-29세"),
    AGE_30_34(4, "30-34세"),
    AGE_35_39(5, "35-39세"),
    AGE_40_44(6, "40-44세"),
    AGE_45_49(7, "45-49세"),
    AGE_50_59(8, "50대"),
    AGE_60_69(9, "60대");

    private final int idx;
    private final String value;

    TargetAge(int idx, String value) {
        this.idx = idx;
        this.value = value;
    }

    public static TargetAge from(Date birth) {
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(new Date());

        Calendar birthCal = Calendar.getInstance();
        birthCal.setTime(birth);

        int age = nowCal.get(Calendar.YEAR) - birthCal.get(Calendar.YEAR);

        if(age >= 20 && age <= 24) return AGE_20_24;
        else if(age >= 25 && age <= 29) return AGE_25_29;
        else if(age >= 30 && age <= 34) return AGE_30_34;
        else if(age >= 35 && age <= 39) return AGE_35_39;
        else if(age >= 40 && age <= 44) return AGE_40_44;
        else if(age >= 45 && age <= 49) return AGE_45_49;
        else if(age >= 50 && age <= 59) return AGE_50_59;
        else if(age >= 60 && age <= 69) return AGE_60_69;
        else return ALL;
    }
}
