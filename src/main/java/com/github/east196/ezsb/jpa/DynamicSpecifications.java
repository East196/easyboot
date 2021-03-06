package com.github.east196.ezsb.jpa;

import cn.hutool.core.util.StrUtil;

import com.github.east196.core.boon.Boon;
import com.github.east196.core.util.Reflections;
import com.google.common.collect.Lists;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("all")
public class DynamicSpecifications {

    /**
     * 高级搜索必备
     *
     * @param filters
     * @param entityClazz
     * @return Specification
     */
    public static <T> Specification<T> bySearchFilter(final Collection<SearchFilter> filters,
                                                      final Class<T> entityClazz) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                if (Boon.notEmpty(filters)) {

                    List<Predicate> predicates = Lists.newArrayList();
                    for (SearchFilter filter : filters) {
                        // nested path translate, 如Task的名为"user.name"的filedName,
                        // 转换为Task.user.name属性
                        String[] names = StrUtil.split(filter.fieldName, ".");
                        Path expression = root.get(names[0]);
                        for (int i = 1; i < names.length; i++) {
                            expression = expression.get(names[i]);
                        }

                        // logic operator
                        switch (filter.operator) {
                            case EQ:
                                predicates.add(builder.equal(expression, filter.value));
                                break;
                            case LIKE:
                                predicates.add(builder.like(expression, "%" + filter.value + "%"));
                                break;
                            case GT:
                                predicates.add(builder.greaterThan(expression, (Comparable) filter.value));
                                break;
                            case LT:
                                predicates.add(builder.lessThan(expression, (Comparable) filter.value));
                                break;
                            case GTE:
                                predicates.add(builder.greaterThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                            case LTE:
                                predicates.add(builder.lessThanOrEqualTo(expression, (Comparable) filter.value));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return builder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return builder.conjunction();
            }
        };
    }

    /**
     * 搜索匹配搜索值的对象
     *
     * @param seach 搜索值
     * @param klass 搜索对象
     * @return Specification
     */
    public static <T> Specification<T> byStringFieldsLike(final String seach, final Class<T> klass) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                List<String> fieldNames = Reflections.fieldNameByKlass(klass, String.class);
                if (Boon.notEmpty(fieldNames)) {
                    List<Predicate> predicates = Lists.newArrayList();
                    for (String fieldName : fieldNames) {
                        Path expression = root.get(fieldName);
                        predicates.add(builder.like(expression, "%" + seach + "%"));
                    }
                    if (!predicates.isEmpty()) {
                        return builder.or(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }
                if (fieldNames.contains("enable")) {
                    builder.or(builder.equal(root.get("enable"), true));
                }
                return builder.conjunction();
            }
        };
    }

}
