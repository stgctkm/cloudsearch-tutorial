# AWS CloudSearch Client

---

## Java 

https://docs.aws.amazon.com/cloudsearch/latest/developerguide/getting-started.html

で使用されている `Movie` index を Java クライアントから操作する

```
http://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/overview-summary.html
```

```
http://docs.aws.amazon.com/cloudsearch/latest/developerguide/search-api.html
```


## CLI
```
aws cloudsearchdomain search --endpoint-url http://search-tutorial-movie-2pdrimt7emx32bf4e262fwwuh4.ap-northeast-1.cloudsearch.amazonaws.com --query-parser structured --search-query "(and title:'star' year:[2008,2016})"
```