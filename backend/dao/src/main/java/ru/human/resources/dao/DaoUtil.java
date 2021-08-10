package ru.human.resources.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.human.resources.common.data.page.PageData;
import ru.human.resources.common.data.page.PageLink;
import ru.human.resources.common.data.page.SortOrder;
import ru.human.resources.dao.model.ToData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Anton Kravchenkov
 * @since 16.07.2021
 */
public abstract class DaoUtil {

    public static <T> PageData<T> toPageData(Page<? extends ToData<T>> page) {
        List<T> data = convertDataList(page.getContent());
        return new PageData<>(data, page.getTotalPages(), page.getTotalElements(), page.hasNext());
    }

    public static <T> T getData(ToData<T> data) {
        T object = null;
        if (data != null) {
            object = data.toData();
        }
        return object;
    }

    public static <T> T getData(Optional<? extends ToData<T>> data) {
        T object = null;
        if (data.isPresent()) {
            object = data.get().toData();
        }
        return object;
    }

    public static Pageable toPageable(PageLink pageLink) {
        return toPageable(pageLink, Collections.emptyMap());
    }

    public static Pageable toPageable(PageLink pageLink, Map<String, String> columnMap) {
        return PageRequest.of(pageLink.getPage(), pageLink.getPageSize(), toSort(pageLink.getSortOrder(), columnMap));
    }

    public static Sort toSort(SortOrder sortOrder) {
        return toSort(sortOrder, Collections.emptyMap());
    }

    public static Sort toSort(SortOrder sortOrder, Map<String, String> columnMap) {
        if (sortOrder == null) {
            return Sort.unsorted();
        } else {
            String property = sortOrder.getProperty();
            if (columnMap.containsKey(property)) {
                property = columnMap.get(property);
            }
            return Sort.by(Sort.Direction.fromString(sortOrder.getDirection().name()), property);
        }
    }

    public static <T> List<T> convertDataList(Collection<? extends ToData<T>> toDataList) {
        List<T> list = Collections.emptyList();
        if (toDataList != null && !toDataList.isEmpty()) {
            list = new ArrayList<>();
            for (ToData<T> obj : toDataList) {
                if (obj != null) {
                    list.add(obj.toData());
                }
            }
        }
        return list;
    }

}
