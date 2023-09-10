package surveasy.domain.survey.repository;

import org.springframework.data.jpa.domain.Specification;
import surveasy.domain.survey.domain.Survey;

public class SurveySpecification {

    public static Specification<Survey> equalEmail(String email) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"), email);
    }

    public static Specification<Survey> equalProgress2(String email) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("progress"), 2));
    }

    public static Specification<Survey> geProgress3() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("progress"), 3));
    }
}
