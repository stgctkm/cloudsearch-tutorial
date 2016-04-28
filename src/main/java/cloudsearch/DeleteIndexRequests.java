package cloudsearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;

public class DeleteIndexRequests implements CloudSearchDocumentRequests {
    List<DeleteIndexRequest> values;

    public DeleteIndexRequests(DeleteIndexRequest request) {
        this.values = Arrays.asList(request);
    }

    @Override
    public ByteArrayInputStream inputStream() {
        return new ByteArrayInputStream(bytesOfJsonValue());
    }

    @Override
    public long bytesLength() {
        return (long) bytesOfJsonValue().length;
    }

    private byte[] bytesOfJsonValue() {
        return toJSON().getBytes();
    }

    public String toJSON(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(values);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }
}
