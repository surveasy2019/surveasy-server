package surveasy.domain.survey.domain.target;

public enum TargetAge {

    ALL("전연령"),
    MALE("남"),
    FEMALE("여");

    final String str;

    TargetAge(String str) {
        this.str = str;
    }
}
