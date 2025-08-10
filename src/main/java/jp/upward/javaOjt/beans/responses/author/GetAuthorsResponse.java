package jp.upward.javaOjt.beans.responses.author;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jp.upward.javaOjt.beans.dtos.AuthorDTO;
import jp.upward.javaOjt.beans.entities.Author;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class GetAuthorsResponse {

  private boolean isExists;

  @JsonProperty("authors")
  private List<AuthorDTO> authors;

  public GetAuthorsResponse(@NonNull List<AuthorDTO> authors) {
    this.isExists = true;
    this.authors = authors;

  }

  public static GetAuthorsResponse notFoundResponse() {
    GetAuthorsResponse response = new GetAuthorsResponse();
    response.isExists = false;
    return response;
  }

}
