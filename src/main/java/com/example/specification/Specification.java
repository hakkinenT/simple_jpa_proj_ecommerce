package com.example.specification;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

@FunctionalInterface
public interface Specification<T> {
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);

    // Combina múltiplas especificações com AND
    /**
     * A anotação @SafeVarargs é usada para indicar ao compilador 
     * que o uso de varargs neste método é seguro, mesmo que envolva tipos genéricos. 
     * Ela pode ser aplicada apenas a métodos estáticos ou finais, 
     * pois esses métodos não podem ser sobrescritos, 
     * garantindo a segurança em relação ao heap pollution.
     */
    @SafeVarargs
    static <T> Specification<T> allOf(Specification<T>... specifications) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Specification<T> specification : specifications) {
                if (specification != null) {
                    predicates.add(specification.toPredicate(root, query, criteriaBuilder));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // Combina múltiplas especificações com OR (opcional)
    @SafeVarargs
    static <T> Specification<T> anyOf(Specification<T>... specifications) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (Specification<T> specification : specifications) {
                if (specification != null) {
                    predicates.add(specification.toPredicate(root, query, criteriaBuilder));
                }
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
