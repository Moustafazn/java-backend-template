package io.task.template.api.v1;

import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1")
public abstract class AbstractController {

    private static final int DEFAULT_PAGE_SIZE = 20;

    protected Pageable pageOf(final Integer offset, final Integer limit, final Sort sort) {
        final var size = limit == null ? DEFAULT_PAGE_SIZE : limit;
        final var page = (offset == null ? 0 : offset) / size;
        return PageRequest.of(page, size, sort);
    }

    protected Pageable pageOf(final Integer offset, final Integer limit) {
        return pageOf(offset, limit, Sort.by("id"));
    }

    protected Sort sortBy(final String expr) {
        if (expr == null || expr.isBlank()) {
            return Sort.by("id");
        }

        final var direction = expr.startsWith(">") ? Sort.Direction.DESC : Sort.Direction.ASC;
        final var property = expr.replaceAll("^>|^<", "");

        return Sort.by(direction, property);
    }

    protected <T> Specification<T> specificationFrom(final String query) {
        return RSQLJPASupport.toSpecification(query, true);
    }
}
