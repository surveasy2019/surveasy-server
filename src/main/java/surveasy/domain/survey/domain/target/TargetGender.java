package surveasy.domain.survey.domain.target;

public enum TargetGender {

    ALL("모두"),
    MALE("남"),
    FEMALE("여");

    final String str;

    TargetGender(String str) {
        this.str = str;
    }
}
