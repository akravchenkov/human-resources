package ru.human.resources.user.service.utils.convertor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import ru.human.resources.common.data.page.PageData;

/**
 * @author Anton Kravchenkov
 * @since 12.08.2021
 */
public abstract class Convertor<R, T, D> {

    protected abstract Class<R> getClassResponse();
    protected abstract Class<D> getClassDto();

    private final ModelMapper modelMapper;

    public Convertor() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public PageData<R> convertToPageData(PageData<D> data) {
        List<R> list = Collections.emptyList();
        if (data.getData() != null && !data.getData().isEmpty()) {
            list = new ArrayList<>();
            for (D dto : data.getData()) {
                if (dto != null) {
                    list.add(modelMapper.map(dto, getClassResponse()));
                }
            }
        }
        return new PageData<>(list, data.getTotalPages(), data.getTotalElements(),
            data.hasNext());
    }

    public R convertToResponse(D dto) {
        return modelMapper.map(dto, getClassResponse());
    }

    public D convertToDto(T request) {
        return modelMapper.map(request, getClassDto());
    }
}
