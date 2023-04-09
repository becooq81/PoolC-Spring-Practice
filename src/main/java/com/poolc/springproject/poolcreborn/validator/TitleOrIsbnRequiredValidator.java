package com.poolc.springproject.poolcreborn.validator;

import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleOrIsbnRequiredValidator implements ConstraintValidator<TitleOrIsbnRequired, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BookRequest bookRequest = (BookRequest) value;
        return bookRequest.getIsbn() != null && bookRequest.getTitle() != null;
    }
}
