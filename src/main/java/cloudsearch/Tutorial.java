package cloudsearch;

import com.amazonaws.services.cloudsearchdomain.model.QueryParser;
import com.amazonaws.services.cloudsearchdomain.model.SearchRequest;

public class Tutorial {

    public static void main(String[] args) {
        Movie movie = getMovie();

        AddIndexRequest request = new AddIndexRequest(movie);
        AddIndexRequests requests = new AddIndexRequests(request);

        new Tutorial().addIndex(requests);
        new Tutorial().list();
        System.out.println("***************************************");
        new Tutorial().simpleSearch();
        System.out.println("***************************************");
        new Tutorial().structuredQuerySearch();
    }



    CloudSearchDocumentClient client = new CloudSearchDocumentClient();

    void addIndex(CloudSearchDocumentRequests requests) {
        client.request(requests);
    }

    void deleteIndex(CloudSearchDocumentRequests requests) {
        client.request(requests);
    }

    void list() {
        client.list();
    }

    void simpleSearch() {
        SearchRequest request = (new SearchRequest())
                .withQueryParser(QueryParser.Simple)
                .withQuery("star")
                // titleとsubtitleに対して"AND"検索を行う
                .withQueryOptions("{\"defaultOperator\":\"and\",\"fields\":[\"title^2\",\"plot\"]}")
                // 先頭から1000件まで取得
                .withStart(0L)
                .withSize(1000L)
                // ソート条件を設定
                .withSort("rank asc,title desc")
                // 結果として取得する項目
                .withReturn("_score,title,rank");

        client.search(request);
    }

    void structuredQuerySearch() {
        SearchRequest request = (new SearchRequest())
                .withQueryParser(QueryParser.Structured)
                .withQuery("(and title:'star' year:{,2000])")
                // titleとsubtitleに対して"AND"検索を行う
                .withQueryOptions("{\"defaultOperator\":\"and\",\"fields\":[\"title\",\"plot\"]}")
                // 検索結果のフィルタリング（scoreには無影響）
                .withFilterQuery("rating:[3.1,}")
                // 先頭から1000件まで取得
                .withStart(0L)
                .withSize(1000L)
                // ソート条件を設定
                .withSort("rank asc,title desc")
                // 結果として取得する項目
                .withReturn("_score,title,rank");

        client.search(request);
    }

    private static Movie getMovie() {
        Movie movie = new Movie();
        movie.setActors(new String[] {"james, ronald"});
        movie.setDirectors(new String[] {"john smith", "jane smith"});
        movie.setGenres(new String[] {"Sci Fi", "Comedy"});
        movie.setImageUrl("image_url_dayo");
        movie.setPlot("Star Platinum vs The World");
        movie.setRank(7);
        movie.setRating(6.8);
        movie.setReleaseDate("1999-12-10T00:00:00Z");//	2011-05-27T00:00:00Z
//        movie.setReleaseDate(LocalDate.of(1999, 12, 10));
        movie.setTitle("Stardust Clusaders");
        movie.setYear("1999");
        return movie;
    }

}
