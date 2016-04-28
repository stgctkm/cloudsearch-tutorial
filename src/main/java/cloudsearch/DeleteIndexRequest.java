package cloudsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeleteIndexRequest {

    DocumentFieldRequestType type;
    @JsonProperty("type")
    public String getIndexFieldRequestType() {
        return type.asText();
    }

    String id;
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    public DeleteIndexRequest(Movie movie) {
        type = DocumentFieldRequestType.DELETE;
        id = String.valueOf(movie.hashCode());
    }

}
