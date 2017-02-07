package org.cayman.filter;


import lombok.AllArgsConstructor;
import org.cayman.model.Book;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


@AllArgsConstructor
public class BookFilter implements Specification<Book> {

    private SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (searchCriteria.getOperation()) {
            case IN: return buildLikePredicate(root, criteriaBuilder);
            case GT: return buildGreaterThanOrEqualsToPredicate(root, criteriaBuilder);
            case LT: return buildLessThanIrEqualsToPredicate(root, criteriaBuilder);
            default: return buildEqualsPredicate(root, criteriaBuilder);
        }
    }

    private Predicate buildEqualsPredicate(Root<Book> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
    }

    private Predicate buildLikePredicate(Root<Book> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
    }

    private Predicate buildGreaterThanOrEqualsToPredicate(Root<Book> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
    }

    private Predicate buildLessThanIrEqualsToPredicate(Root<Book> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), searchCriteria.getValue().toString());
    }
}
