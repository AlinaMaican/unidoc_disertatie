package ro.alina.unidoc.utils;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;
import ro.alina.unidoc.model.type.DocumentStatusType;
import ro.alina.unidoc.model.type.DocumentType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class GenericSpecification <T>{
    private static final int NESTED_PROPERTY_SIZE = 2;
    private static final int PARENT = 0;
    private static final int CHILD = 1;
    private static final int SECOND_CHILD = 2;
    private static final int DAY_MARGIN = 1;
    private static final String NESTED_PROPERTY_DELIMITER = "\\.";
    private static final String LIKE_WILDCARD = "%";
    private static final String OUTSIDE = "OUTSIDE";
    private static final String ALL = "ALL";
    private static final String RANGE_TYPE = "rangeType";

    public Specification<T> where(Specification<T> specification) {
        return Specification.where(specification);
    }

    public Specification<T> isPropertyLike(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value)) {
            return (root, query, builder) -> builder.like(builder.lower(root.get(property)),
                    LIKE_WILDCARD + value.toLowerCase() + LIKE_WILDCARD);
        }
        return null;
    }

    public Specification<T> isPropertyEqual(final String property, final String value) {
        if (StringUtils.isEmptyOrWhitespace(value) || "ALL".equals(value)) {
            return null;
        } else {
            return (root, query, builder) -> builder.equal(root.get(property), value);
        }
    }

    public Specification<T> isStatusEqual(final String property, final String value) {
        if (value == null) {
            return null;
        } else {
            if(value.equals("ALL")){
                return null;
            }
            return (root, query, builder) -> builder.equal(root.get(property), DocumentStatusType.valueOf(value));
        }
    }

    public Specification<T> isDocumentTypeEqual(final String property, final String value) {
        if (value == null) {
            return null;
        }
        return (root, query, builder) -> builder.equal(root.get(property), DocumentType.valueOf(value));

    }

    public Specification<T> isPropertyEqualNumber(final String property, final Long value) {
        if (!StringUtils.isEmptyOrWhitespace(property)) {
            return (root, query, builder) -> builder.equal(root.get(property), value);
        }
        return null;
    }

    public Specification<T> isPropertyIn(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(property) && !StringUtils.isEmptyOrWhitespace(value)) {
            return (root, query, builder) -> root.get(property).in(value);
        }
        return null;
    }

    public Specification<T> isNestedPropertyIn(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder) -> root.join(properties[PARENT]).get(properties[CHILD]).in(value);
        }
        return null;
    }

    public Specification<T> isNestedPropertyIn(final String property, final List<Boolean> value) {
        if (!CollectionUtils.isEmpty(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder) -> root.join(properties[PARENT]).get(properties[CHILD]).in(value);
        }
        return null;
    }

    public Specification<T> isPropertyInString(final String property, final List<String> value) {
        if (!StringUtils.isEmptyOrWhitespace(property) && !CollectionUtils.isEmpty(value)) {
            return (root, query, builder) -> root.get(property).in(value);
        }
        return null;
    }

    public Specification<T> isNestedPropertyInString(final String property, final List<String> value) {
        if (!CollectionUtils.isEmpty(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder) -> root.join(properties[PARENT]).get(properties[CHILD]).in(value);
        }
        return null;
    }

    public Specification<T> isNestedPropertyForSourcesIn(final String property, final List<Long> value) {
        if (value == null) {
            return null;
        }
        if (!CollectionUtils.isEmpty(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder) -> root.join(properties[PARENT]).get(properties[CHILD]).in(value);
        }
        if (CollectionUtils.isEmpty(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder) -> builder.isEmpty(root.get(properties[PARENT]));
        }
        return null;
    }

    public Specification<T> distinct() {
        return (root, query, cb) -> {
            query.distinct(true);
            return null;
        };
    }

    public Specification<T> isNestedNestedPropertyIn(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder) -> root.join(properties[PARENT]).join(properties[CHILD]).get(properties[SECOND_CHILD]).in(value);
        }
        return null;
    }

    public Specification<T> isNestedNestedPropertyLike(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder)
                    -> builder.like(builder.lower(root.join(properties[PARENT]).join(properties[CHILD]).get(properties[SECOND_CHILD])),
                    LIKE_WILDCARD + value.toLowerCase() + LIKE_WILDCARD);
        }
        return null;
    }

    public Specification<T> isNestedPropertyLike(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value) && checkForNestedProperty(property) && value!= null) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder)
                    -> builder.like(builder.lower(root.get(properties[PARENT]).get(properties[CHILD])),
                    LIKE_WILDCARD + value.toLowerCase() + LIKE_WILDCARD);
        }
        return null;
    }

    public Specification<T> isSearchDateBetween(final LocalDateTime startDate, final LocalDateTime endDate,
                                                final String dateColumnName) {
        if (Objects.nonNull(startDate) && Objects.nonNull(endDate)) {
            return (root, query, builder) -> builder
                    .between(root.get(dateColumnName), startDate, endDate.plusDays(DAY_MARGIN));
        } else if (Objects.nonNull(startDate)) {
            return (root, query, builder) -> builder
                    .between(root.get(dateColumnName), startDate, startDate.plusDays(DAY_MARGIN));
        }
        return null;
    }

    public Specification<T> isNestedPropertyEqualTo(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder)
                    -> builder.equal(root.get(properties[PARENT]).get(properties[CHILD]), value);
        }
        return null;
    }

    public Specification<T> isNestedDocumentTypeEqualTo(final String property, final String value) {
        if (!StringUtils.isEmptyOrWhitespace(value) && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder)
                    -> builder.equal(root.get(properties[PARENT]).get(properties[CHILD]), DocumentType.valueOf(value));
        }
        return null;
    }

    public Specification<T> isNestedPropertyEqualNumber(final String property, final Long value) {
        if (value != null && checkForNestedProperty(property)) {
            if(value == -1){
                return null;
            }
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder)
                    -> builder.equal(root.get(properties[PARENT]).get(properties[CHILD]), value);
        }
        return null;
    }

    public Specification<T> isNestedNestedPropertyEqualNumber(final String property, final Long value) {
        if (value != null && checkForNestedProperty(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            return (root, query, builder)
                    -> builder.equal(root.join(properties[PARENT]).join(properties[CHILD]).get(properties[SECOND_CHILD]), value);
        }
        return null;
    }


    private Boolean checkForNestedProperty(final String property) {
        if (!StringUtils.isEmptyOrWhitespace(property)) {
            String[] properties = property.split(NESTED_PROPERTY_DELIMITER);
            if (properties.length == NESTED_PROPERTY_SIZE || properties.length == 3) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
