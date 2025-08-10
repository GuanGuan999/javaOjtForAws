package jp.upward.javaOjt.controllers;

import jp.upward.javaOjt.beans.dtos.AuthorDTO;
import jp.upward.javaOjt.beans.entities.Author;
import jp.upward.javaOjt.beans.entities.AuthorPK;
import jp.upward.javaOjt.beans.responses.author.GetAuthorResponse;
import jp.upward.javaOjt.beans.responses.author.GetAuthorsResponse;
import jp.upward.javaOjt.exceptions.OjtNotFoundException;
import jp.upward.javaOjt.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Validated
public class AuthorController {

  private final AuthorService authorService;

  /**
   * Retrieves an author by his ID.
   *
   * @param id        the ID of the author to retrieve
   * @param withBooks whether to include the author's books in the response
   * @return a ResponseEntity containing the GetAuthorResponse
   * @throws OjtNotFoundException if the author does not exist
   */
  @GetMapping("/{id}")
  public ResponseEntity<GetAuthorResponse> getAuthor(
    @PathVariable(value = "id") Integer id,
    @RequestParam(value = "withBooks", required = false) boolean withBooks
  ) {
    GetAuthorResponse response = authorService.getAuthorById(id, withBooks);
    if (!response.isExists()) {
      throw new OjtNotFoundException("Author not found with ID: " + id);
    }
    return ResponseEntity.ok(response);
  }

  /**
   * Retrieves an author by his ID.
   *
   * @return a ResponseEntity containing the GetAuthorResponse
   * @throws OjtNotFoundException if the author does not exist
   */
  @GetMapping
  public ResponseEntity<GetAuthorsResponse> getAllAuthor() {
    GetAuthorsResponse response = authorService.getAllAuthor();
    if (!response.isExists()) {
      throw new OjtNotFoundException("Author not found" );
    }
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public Author createUser(@RequestBody Author authorRequest) {
    // 本来はDB登録やバリデーション処理を行う
    return authorService.createAuthor(authorRequest);
  }



}



