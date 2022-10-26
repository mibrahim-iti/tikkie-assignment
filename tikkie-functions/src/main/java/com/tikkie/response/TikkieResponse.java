package com.tikkie.response;

import com.tikkie.error.TikkieError;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Mohamed Ibrahim
 * @created Tuesday 25 Oct 2022
 */
@Data
@Builder
public final class TikkieResponse<T> {

    private T responseBody;

    private List<TikkieError> errors;

}
