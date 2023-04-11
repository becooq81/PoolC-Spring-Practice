package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.book.Book;
import com.poolc.springproject.poolcreborn.payload.request.book.BookRequest;
import com.poolc.springproject.poolcreborn.payload.response.book.BookDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildBookFromRequest(BookRequest bookRequest, @MappingTarget Book book);
    BookDto buildBookDtoFromBook(Book book);
    Book buildBookFromBookDto(BookDto bookDto);
}

