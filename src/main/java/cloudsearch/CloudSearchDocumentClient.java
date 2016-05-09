package cloudsearch;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudsearchdomain.AmazonCloudSearchDomainClient;
import com.amazonaws.services.cloudsearchdomain.model.*;
import com.amazonaws.services.cloudsearchv2.AmazonCloudSearchClient;
import com.amazonaws.services.cloudsearchv2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CloudSearchDocumentClient {

    private static final Logger logger = LoggerFactory.getLogger(CloudSearchDocumentClient.class);

//    @Value("${cloud.search.access.key}")
    String accessKey = "xxxxxxxxxxxxxxxxxxx";
//    @Value("${cloud.search.secret.key}")
    String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
//    @Value("${cloud.search.endpoint}")
    String endpoint = "doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com";

    String searchEndPoint = "search-tutorial-movie-xxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com";

    public void request(CloudSearchDocumentRequests requests) {
        AmazonCloudSearchDomainClient cloudSearch = createClient();

        System.out.println("hogehoge----");

        UploadDocumentsRequest uploadDocumentRequest = new UploadDocumentsRequest();
        uploadDocumentRequest
                .withContentType(ContentType.Applicationjson)
                .withContentLength(requests.bytesLength())
                .withDocuments(requests.inputStream());

        UploadDocumentsResult response = cloudSearch.uploadDocuments(uploadDocumentRequest);
        logger.debug(response.toString());

        cloudSearch.shutdown();
    }

    private AmazonCloudSearchDomainClient createClient() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonCloudSearchDomainClient cloudSearch = new AmazonCloudSearchDomainClient(credentials);
        cloudSearch.setEndpoint(endpoint);

        return cloudSearch;
    }

    public void list() {

        AmazonCloudSearchClient client = amazonCloudSearchClient();

        DescribeDomainsResult describeDomainsResult = client.describeDomains();
        List<DomainStatus> domainStatusList = describeDomainsResult.getDomainStatusList();
        for (DomainStatus status: domainStatusList) {
            System.out.println(status);
        }

        System.out.println("-------------------");

        DescribeIndexFieldsRequest describeIndexFieldsRequest = new DescribeIndexFieldsRequest();
        describeIndexFieldsRequest.withDomainName("tutorial-movie");
        DescribeIndexFieldsResult indexFieldsResult = client.describeIndexFields(describeIndexFieldsRequest);
        List<IndexFieldStatus> indexFieldStatusList = indexFieldsResult.getIndexFields();
        for (IndexFieldStatus idxFieldStatus : indexFieldStatusList) {
            System.out.println(idxFieldStatus);
        }
    }


    public void search(SearchRequest request) {

        AmazonCloudSearchDomainClient client = createClient();
//        AmazonCloudSearchClient client = amazonCloudSearchClient();
        client.setEndpoint(searchEndPoint);

        try{
            // 検索処理
            SearchResult result = client.search(request);


            SearchStatus status  = result.getStatus();
            Hits hits = result.getHits();

            // インデックス全体でのトータル件数(startとsizeで指定した範囲外も含む)
            Long total = hits.getFound();


            hits.getHit().forEach(hit -> {
                String id = hit.getId();
                String ranking =  hit.getFields().get("rank").get(0);

                logger.debug(" id:" + id + " rank:" + ranking);

            });

        } finally {
            client.shutdown();
        }

    }

    private AmazonCloudSearchClient amazonCloudSearchClient() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonCloudSearchClient client = new AmazonCloudSearchClient(credentials);
        client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1));
        return client;
    }

}
