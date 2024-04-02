package com.digital.challenge.utils;

import com.digital.challenge.entities.News;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

public class ParseHelper {

    public static Optional<String> findItems(String response) {
        JSONObject objetoJson = new JSONObject(response);
        Optional<String> items = Optional.empty();
        if(objetoJson.has("hits")){
            items = Optional.of(objetoJson.get("hits").toString());
        }
        return items;
    }

    public static Specification<News> filterNews(String author, String storyTitle) {
        return (root, query, criteriaBuilder) -> {
            Predicate brandPredicate =
                    criteriaBuilder.like(root.get("author"), StringUtils.isBlank(author)
                            ? likePattern("") : author);
            Predicate namePredicate =
                    criteriaBuilder.like(root.get("storyTitle"), StringUtils.isBlank(storyTitle)
                            ? likePattern("") : storyTitle);
            return criteriaBuilder.and(namePredicate, brandPredicate);
        };
    }
    private static String likePattern(String value) {
        return "%" + value + "%";
    }
}
