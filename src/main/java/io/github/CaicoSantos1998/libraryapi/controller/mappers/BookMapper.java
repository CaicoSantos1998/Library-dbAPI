package io.github.CaicoSantos1998.libraryapi.controller.mappers;

import io.github.CaicoSantos1998.libraryapi.controller.dto.RegistrationBookDTO;
import io.github.CaicoSantos1998.libraryapi.controller.dto.ResultSearchBookDTO;
import io.github.CaicoSantos1998.libraryapi.model.Book;
import io.github.CaicoSantos1998.libraryapi.repository.RepositoryAuthor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public abstract class BookMapper {

    @Autowired
    RepositoryAuthor repositoryAuthor;

    @Mapping(source = "datePublication", target = "datePublication")
    @Mapping(target = "author", expression = "java( repositoryAuthor.findById(dto.idAuthor()).orElse(null) )")
    public abstract Book toEntity(RegistrationBookDTO dto);


    public abstract ResultSearchBookDTO toDTO(Book book);
}
