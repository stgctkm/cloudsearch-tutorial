
## ドメインの作成
```
aws cloudsearch create-domain --domain-name tutorial-movie
```

## インスタンスタイプとレプリケーション数の設定
```
aws cloudsearch update-scaling-parameters --domain-name tutorial-movie \
--scaling-parameters DesiredInstanceType=search.m1.small,DesiredReplicationCount=1
```

## アクセスポリシーの設定

### Search and Suggester service: Allow all. Document Service: Account owner only.
```
aws cloudsearch update-service-access-policies --domain-name tutorial-movie --access-policies \
"{
  \"Version\":\"2012-10-17\",
  \"Statement\":[
    {
      \"Effect\":\"Allow\",
      \"Principal\":{
        \"AWS\":[
          \"*\"
        ]
      },
      \"Action\": [
        \"cloudsearch:search\",
        \"cloudsearch:suggest\"
      ]
    }
  ]
}"
```

### Allow access to all services from specific IP(s)
```
aws cloudsearch update-service-access-policies --domain-name tutorial-movie --access-policies \
"{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "",
      "Effect": "Allow",
      "Principal": {
        "AWS": "*"
      },
      "Action": "cloudsearch:*",
      "Condition": {
        "IpAddress": {
          "aws:SourceIp": [
            "xx.xx.xx.xx/32"
          ]
        }
      }
    }
  ]
}"
```



## インデックスフィールドの設定
```
# aws cloudsearch define-index-field --domain-name tutorial-movie --name id --type literal --return-enabled true --highlight-enabled ReturnEnabled --analysis-scheme _en_default_ --generate-cli-skeleton
aws cloudsearch define-index-field --domain-name tutorial-movie --name actors --type text-array --return-enabled true --highlight-enabled true --analysis-scheme _en_default_
aws cloudsearch define-index-field --domain-name tutorial-movie --name directors --type text-array --return-enabled true
aws cloudsearch define-index-field --domain-name tutorial-movie --name genres --type literal-array --search-enabled true --facet-enabled true --return-enabled true
aws cloudsearch define-index-field --domain-name tutorial-movie --name image_url --type text --return-enabled true --analysis-scheme _en_default_
aws cloudsearch define-index-field --domain-name tutorial-movie --name plot --type text --return-enabled true --highlight-enabled true --analysis-scheme _en_default_
aws cloudsearch define-index-field --domain-name tutorial-movie --name rank --type int --sort-enabled true --facet-enabled true --return-enabled true --sort-enabled true
aws cloudsearch define-index-field --domain-name tutorial-movie --name rating --type double --sort-enabled true --facet-enabled true  --return-enabled true --sort-enabled true
aws cloudsearch define-index-field --domain-name tutorial-movie --name release_date --type date --sort-enabled true --facet-enabled true  --return-enabled true --sort-enabled true
aws cloudsearch define-index-field --domain-name tutorial-movie --name running_time_secs --type int --sort-enabled true --facet-enabled true  --return-enabled true --sort-enabled true
aws cloudsearch define-index-field --domain-name tutorial-movie --name title --type text --return-enabled true --sort-enabled true --highlight-enabled true --analysis-scheme _en_default_
aws cloudsearch define-index-field --domain-name tutorial-movie --name year --type int --sort-enabled true --facet-enabled true  --return-enabled true --sort-enabled true


```

### analysis-scheme の設定
```
http://docs.aws.amazon.com/cloudsearch/latest/developerguide/text-processing.html#text-processing-settings
```

### インデックスフィールドの削除
```
aws cloudsearch delete-index-field --domain-name tutorial-movie --index-field-name id
```

## Indexing
```
aws cloudsearch index-documents --domain-name tutorial-movie
```


## データ投入

## endpoint 確認
```
aws cloudsearch describe-domains
{
    "DomainStatusList": [
        {
            "SearchInstanceType": "search.m1.small",
            "DomainId": "xxxxxxxxxx/tutorial-movie",
            "Limits": {
                "MaximumReplicationCount": 5,
                "MaximumPartitionCount": 10
            },
            "Created": true,
            "Deleted": false,
            "SearchInstanceCount": 1,
            "DomainName": "tutorial-movie",
            "SearchService": {
                "Endpoint": "search-tutorial-movie-xxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com"
            },
            "RequiresIndexDocuments": false,
            "Processing": false,
            "DocService": {
                "Endpoint": "doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com"
            },
            "ARN": "arn:aws:cloudsearch:ap-northeast-1:xxxxxxxxxxxxxx:domain/tutorial-movie",
            "SearchPartitionCount": 1
        }
    ]
}

```
### サンプルデータ
```
https://aws.amazon.com/developertools/9131774809784850
```
```

aws cloudsearchdomain --endpoint-url http://doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com upload-documents --content-type application/json --documents ~/Downloads/movies-v2/moviedata2.json
{
    "status": "success",
    "adds": 5000,
    "deletes": 0
}


```


##

## 検索
```
https://docs.aws.amazon.com/cloudsearch/latest/developerguide/searching-text.html
```
### ブラウザで。
```
https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com/2013-01-01/search?q=katniss&return=title
https://doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com/2013-01-01/search?q=star wars&q.options={fields: ['title^5','plot']}
https://doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com/2013-01-01/search?q=(and 'star' 'wars')
https://doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com/2013-01-01/search?q=(and 'star wars' 'luke')
https://doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com/2013-01-01/search?q="with love"~3
```

### facetの取得
```
https://doc-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com/2013-01-01/search?q=star&return=title&facet.genres={}
```

### 検索
```
aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'"
aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "(and 'star' 'wars')"
aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'" --facet {year:"{sort:'bucket', size:3}}"
aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'" --facet '{year:{buckets:["[1970,1979]","[1980,1989]", "[1990,1999]","[2000,2009]", "[2010,}"]}}'
#aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'" --facet '{size:{sort:["[1001,9999]","[0,1000]"]}}'
aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'" --facet '{genres:{sort:"bucket", size:5}}'


aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'" --return title
aws cloudsearchdomain search --endpoint-url https://search-tutorial-movie-xxxxxxxxxxxxxxxxxxxxxxxxxx.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "title:'star'" --return _no_fields
```
#### facet
```
http://docs.aws.amazon.com/cloudsearch/latest/developerguide/faceting.html
```

### reference
```
https://docs.aws.amazon.com/cloudsearch/latest/developerguide/searching-compound-queries.html
https://docs.aws.amazon.com/cloudsearch/latest/developerguide/search-api.html#structured-search-syntax
```

## ドメインの削除
```
aws cloudsearch delete-domain --domain-name tutorial-movie
```

### manual
```
aws cloudsearchdomain search help
```
