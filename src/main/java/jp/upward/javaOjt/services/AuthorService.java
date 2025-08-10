package jp.upward.javaOjt.services;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.CriteriaBuilder;
import jp.upward.javaOjt.beans.dtos.AuthorDTO;
import jp.upward.javaOjt.beans.dtos.AuthorWithBookDTO;
import jp.upward.javaOjt.beans.entities.Author;
import jp.upward.javaOjt.beans.entities.AuthorPK;
import jp.upward.javaOjt.beans.responses.author.GetAuthorResponse;
import jp.upward.javaOjt.beans.responses.author.GetAuthorsResponse;
import jp.upward.javaOjt.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

  final private AuthorRepository authorRepository;

  /**
   * Fetches an author by his ID.
   *
   * @param id        author ID
   * @param withBooks if true, fetches the author along with their books; if false, fetches only the
   *                  author details
   * @return GetAuthorResponse containing author details and optionally their books
   */
  public GetAuthorResponse getAuthorById(Integer id, boolean withBooks) {
    GetAuthorResponse response;
    // If withBooks is true, fetch the author along with their books
    if (withBooks) {
      List<AuthorWithBookDTO> beans = authorRepository.getAuthorWithBookDTOsById(id);
      if (beans.isEmpty()) {
        return GetAuthorResponse.notFoundResponse();
      }
      response = new GetAuthorResponse();
      response.setExists(true);
      response.setId(beans.getFirst().author().getId());
      response.setName(beans.getFirst().author().getName());
      response.setBooks(beans.stream().map(
          bean -> new GetAuthorResponse.Book(
            bean.book().getId(),
            bean.book().getTitle(),
            bean.book().getPublishedAt() == null ? null : bean.book().getPublishedAt().toString()
          )
        ).toList()
      );
    }
    // If withBooks is false, fetch only the author details
    else {
      Optional<Author> author = authorRepository.findById(new AuthorPK(id));
      if (author.isEmpty()) {
        return GetAuthorResponse.notFoundResponse();
      } else {
        response = new GetAuthorResponse(author.get());
      }
    }
    return response;
  }

  /**
   * Fetches an author by his ID.
   *
   * @return GetAuthorResponse containing author details and optionally their books
   */
  public GetAuthorsResponse getAllAuthor() {
    GetAuthorsResponse response;
    List<AuthorDTO> authorList = authorRepository.getAllAuthor();

      if (authorList.isEmpty()) {
        return GetAuthorsResponse.notFoundResponse();
      }
      response = new GetAuthorsResponse();
      response.setExists(true);

    response.setAuthors(authorList);

    return response;
  }

  public Author createAuthor(Author authorRequest) {
    Author authorResponse = authorRepository.save(authorRequest);
    return authorResponse;
  }

  public void deleteAuthor(AuthorPK authorRequest) {
    authorRepository.deleteById(authorRequest);
  }
}
