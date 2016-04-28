package cloudsearch;

import java.io.ByteArrayInputStream;

public interface CloudSearchDocumentRequests {
    ByteArrayInputStream inputStream();
    long bytesLength();
}
