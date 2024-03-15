# f1-Yigil
 
##  이길 로그

## 프로젝트 기획 및 목적

"이길 로그" 서비스는 지도 기반의 일정 및 장소 공유 플랫폼으로, 사용자들은 여행이나 다양한 활동
중에 방문한 장소들을 지도 상에 마킹하고 그에 대한 일정을 작성할 수 있습니다. 각 일정에는 사용자가
촬영한 사진과 함께 제목, 평점, 그리고 간단한 내용을 추가하여 기록할 수 있습니다. 이를 통해 
사용자들은 자신의 경험을 다양한 면에서 기록하고 공유할 수 있습니다.

## 배포 주소
  
[이길 로그](https://yigil.co.kr) 

## 주요 기능 

### 별점 기능
redis 캐싱을 활용하여 사용자들이 빠르게 볼 수 있도록 구현되어있다. 추후에 batch를 통해 실시간이 아닌 
주기적인 갱신을 진행하려고 합니다. 혹시 여기서 비효율적이거나 더 나은 방법이 있는지 궁금합니다.
추가적으로 초기 batch 기간을 어느정도로 정해야 하는지 궁금합니다.

### 팔로잉 팔로워 기능
followerCount와 followingCount를 redis를 활용하여 접근할 수 있도록 캐싱이 적용됩니다.

### 어드민 페이지 기능
어드민 페이지는 OAuth + 세션방식이 아니라 jwt 토큰 인증방식을 사용합니다.

### 알림 기능
webflux를 활용하여 SSE 알림을 구현하였습니다.

### 소셜 로그인 기능
로그인은 카카오 소셜로그인만 있습니다.
OAuth를 통해 카카오나 구글에서 토큰을 받아서 인증을 하고 추가정보를 받아서 회원가입을 진행하고
연결 유지는 세션을 통해서 진행합니다.

## 집중적으로 코드리뷰 필요한 부분

### 이벤트 기반 파일 업로드 과정
파일을 비동기식으로 업로드를 진행하는데 이 부분을 용도에 맞게 적용이 되었는지 궁금합니다.
고쳐야 하거나 개선을 할 부분이 있는지 궁금합니다.

event -> yigil-api/src/main/java/kr/co/yigil에 file 도메인에 있습니다.
attachFile -> support안에 있다.
파일 업로드가 필요하면 파일 업로드 이벤트를 만들고 그 안에 파일 콜백함수를 만듭니다.
그러면 이벤트가 publisher를 통해 이벤트를 발행하면 event listener에서 s3에 파일
을 업로드 한 후 파일의 주소를 담은 attachfile을 만들어 callback 함수를 실행해줍니다.
그 이후에 attachfile을 받아 파일에 저장하거나 spot을 save하거나 update를 수행합니다.

### 게시글 관련 기능
구조를 먼저 설명 드리면 travel을 상속받는 spot과 course가 있고 travel에는 공통적으로 
게시글의 타이틀이나 내용, 고유 ID, 멤버 정보 등이 있습니다. 그리고 course는 spot들이 모여서 구성되어 있습니다.

Spot에는 좌표값을 Point 객체로 얻고 좌표값에 위치한 장소에 대한 각종 정보를 place 객체로 포함하고 있습니다.
file의 주소나 정보를 담은 attachfile도 포함하고 있습니다.

나중에는 거리 순으로 근처 spot들을 조회할 예정인데 거리를 구하는 것이 너무 비효율적인 것 같아 
어떻게 처리하면 효율적일지 고민입니다.

### 좋아요 기능
Redis에 캐싱을 하는 과정에 있어서 동시에 캐싱 요청을 처리했을 때 데이터 정합성이 보장되지 않은 채로
캐싱될 수 있지 않을까 하는 걱정이 들었습니다.
ㅇ
