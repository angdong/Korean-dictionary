# Korean-dictionary
—--
This is Korean dictionary using '표준국어대사전' API\
You have to specify your api_key in [code](https://github.com/angdong/Korean-dictionary/blob/82d4e636bd21530005a71f7d1ce3c5bca8247b8d/app/src/main/java/edu/skku/cs/afinal/SearchActivity.kt#L25)\
You can get your own API Key in [link](https://stdict.korean.go.kr/openapi/openApiRegister.do)\
Requirement: SDK 33

<img src="/img/search_empty.png" width="200" height="400"/>
<img src="/img/search.png" width="200" height="400"/>
<img src="/img/word.png" width="200" height="400"/>
<img src="/img/word_star.png" width="200" height="400"/>
<img src="/img/history_empty.png" width="200" height="400"/>
<img src="/img/history.png" width="200" height="400"/>
<img src="/img/bookmark_empty.png" width="200" height="400"/>
<img src="/img/bookmark.png" width="200" height="400"/>
<img src="/img/quiz.png" width="200" height="400"/>

Each word in '포준국어대사전' has own code.\
To use words randomly quiz activity I access code randomly.\
But, there's some __empty code__. So, I replace that empty code with hard-coded word.

<img src="/img/quiz_correct.png" width="200" height="400"/>
<img src="/img/quiz_wrong.png" width="200" height="400"/>
