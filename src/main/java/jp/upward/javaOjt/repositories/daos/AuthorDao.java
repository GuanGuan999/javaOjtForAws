package jp.upward.javaOjt.repositories.daos;

import java.util.List;

import jp.upward.javaOjt.beans.dtos.AuthorDTO;
import jp.upward.javaOjt.beans.dtos.AuthorWithBookDTO;
import jp.upward.javaOjt.beans.entities.Author;

public interface AuthorDao {

  List<AuthorWithBookDTO> getAuthorWithBookDTOsById(Integer id);

  List<AuthorDTO> getAllAuthor();
}
