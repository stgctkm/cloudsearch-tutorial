package cloudsearch;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddIndexRequest {

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

    Movie movie;

    @JsonProperty("fields")
    public Movie getMovie() {
        return movie;
    }

    public AddIndexRequest(Movie movie) {
        type = DocumentFieldRequestType.ADD;
//        id = new DocumentId(ticketType.getTicketTypeCode());
        id = UUID.randomUUID().toString();
        this.movie = movie;
    }
}
