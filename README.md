# CanTuna-acon3d

## DB

- User

 - 작가 / 에디터 / 고객 / createTime / updateTime

 - id / pw  / Usertype

- 상품

 - 상품코드 / 제목(모델명,언어별) / 작가 / 승인에디터 / 가격 / 본문(상품 설명,언어별) / 승인여부 / 수수료 / createTime / updateTime

- 환율

 - 국가 / 환율 /  createTime / updateTime

- 구매 리스트

 - 구매ID(자동생성) / 고객ID / 상품코드 / 수량 /  createTime / updateTime

## Service

- 작가

  - 상품의 작성(한국어)

- 에디터

 - 상품의 등록/수정(언어 추가)

- 고객

 - 상품 조회

 - 상품 구매

## Controller

- 로그인

id,pw > 로그인 여부/유저 정보

*인터셉터 > 권한 체크

GET PUT POST DELETE

- 상품 작성

유저권한 > 상품코드 / 제목(모델명,언어별) / 작가 / 승인에디터 / 가격 / 본문(상품 설명,언어별) / 승인여부 / 수수료  > ok

POST /items

- 상품 등록(에디터 승인)

유저 권한, 상품코드 > [상품승인.is](http://상품승인.is)Registered(), isAuth()

PUT /items/{id}/approved

- 상품 수정

유저권한,유저 정보, 상품코드 > 상품코드 / 제목(모델명,**언어별**) / 작가 / **승인에디터** / 가격 / 본문(상품 설명,**언어별**) / **승인여부** / **수수료**  > ok

PUT /items /{id}

- 상품 조회

 - id 조회

GET /items/{id}

 - name 으로 조회

GET / name/{name}

 - 승인 여부로 조회

GET/name/approved

GET /items?priceOver=30000&priceUnder=5000&liked=400000

- 상품 구매

유저정보  상품ID, 수량

POST /items/purchased

## 상품 수정 시나리오

controller (상품 수정 리퀘스트) > Service (권한체크)

N : UnAuth

Y : Service(상품 정보 수정) > try{Repository(DB 수정)}catch{}>service(OK) > ctrller(OK) >response(OK)

- 추가 설정

 - 에디터가 승인되지 않은 상품을 확인하기 위한 findNonApprovedItem cnrk
