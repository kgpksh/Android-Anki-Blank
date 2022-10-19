# Android-Anki-Blank 프로젝트 과정
###### 코딩에 입문하계된 계기이자 처음으로 완성한 프로젝트이다.
###### 이 프로젝트중에는 깃허브 같은 것은 모르는 상태였기에, 그저 배우고 코드를 짜는데만 몰두했었다.
###### 그러나 기록의 중요성에 대해 느낀 점이 있어 프로젝트 과정을 되돌아 보면서 뒤늦게나마 나름의 깨달은 점을 써두고자 한다.
### [Anki-Blank 구글플레이스토어 링크](https://play.google.com/store/apps/details?id=com.first.Anki_blank)

## 1. 계기
대학 재학 중, 산림산업기사 시험을 공부하던 중, 암기를 좀 더 편하게 했으면 했다. 그 전부터 Anki라는 카드형 암기 보조 프로그램을 알고는 있었다.  
  
Anki는 앞면에 문제, 뒷면에 정답이 있는 카드를 추가하여 많이 틀릴 수록 더 자주 보여준다. 인간의 암기 특성을 연구한 내용을 참고하여 만들어 졌다 한다.  
  
그러나 나에게는 수동으로 카드를 추가하는 과정이 배보다 배꼽이 크다 느꼈다. 더군다나 카드는 시험 준비서와는 달리, 주위내용과 분리되어 있어 맥락을 통한 공부는 불가능에 가깝다. 
  
결국 '직접 만들어 볼수 없을까?' 라는 생각이 들었고 Anki와 비슷하지만, 카드대신, 학습서 파일의 어느 페이지의 특정 부분을 가리는 빈칸의 형태로 만들어 보기로 마음먹었다. 그렇게 산업기사 시험을 마친 직후 독학으로 프로그래밍 입문공부 및 앱 제작 과정에 착수하였다.
  
나는 내 아이디어가 꽤 괜찮아 보였고, 나중의 일이지만, 참고하기 위해 알아보다가 다른 비슷한 앱들이 있다는 것을 알았을 때도 내가 계획한 것이 더 뛰어나다고 느꼈다. 내가 원하는 결과물에 대한 자신이 있었기에 목표치가 더 높아졌음에도 중간에 동력을 잃지 않고 작업을 진행할 수 있었다.
## 2. 안드로이드를 선택한 이유
1에서의 계기 이후, 나 혼자 잘 사용하는 것을 넘어 용돈이라도 벌어 볼수는 없을까 생각하였다. 그렇게 마음을 먹자 고민해야 할 것이 많아졌다.  
  
우선 형태를 정해야 했다. 당장 처음 떠오르는 생각은 Anki 처럼 윈도우에서 쓸 수 있는 프로그램을 다운받을 수 있도록 하는 것이었다. 그러나 당시 소프트웨어 분야에 대해 아무것도 모르는 나라도 그러려면 서버가 필요하고, 서버를 운영하려면 서버비가 들어간다는 사실 정도는 알고 있었다. 무일푼인 내가 그만한 위험을 감수할 수는 없었기에 포기했다. 더 큰 문제는 결제였다. 코딩 경험과 사업 경험 둘다 없는 나에게 결제 기능을 만드는 것은 기술적인 부분을 넘어서 높은 벽으로 느껴졌다.
  
  두번째로 떠올린 방식은 웹 브라우저 상에서 기능을 구현하는 것이다. 결과를 먼저 말하자면 이것도 기각했다. 우선 이것 또한 서버와 결제의 문제에서 자유로울 수는 없다. 첫번째보다 더 심각한데, 알아본 결과 웹에는 프론트엔드와 백엔드가 있는데 뭔가 빨리 결과를 내고 싶은 입장에서 2배의 지식을 습득해야 하는 필요성에 의문이 있었다. 그리고 오리지날 Anki에 생각이 묶여서 그런지는 몰라도 굳이 웹 브라우저를 통해 암기 공부를 하는 것이 조잡하다고 느껴졌다.
  
  또다른 선택지는 모바일이었다. 우선 모바일은 서버가 필요없었다. 또한 결제, 배포및 홍보의 어려움이 앞의 두 가지 것들에 비해 매우 낮다고 느꼈다. 구글과 애플의 앱스토어가 서버와 배포, 결제를 맡아서 해주고 있는 형태이기 때문이다. 모바일은 이동성도 있어 어디서나 외울 수 있다. 이때는 고려하지 못했지만, 유저의 암기 데이터도 기기 내에 저장 할 수 있다는 이득도 누릴 수 있었다. 거기에 막연히 모바일 앱은 세련된 것이라는 생각도 동기가 되었다. IOS 대신 안드로이드를 선택한 이유는 안드로이드의 접근성과 개방성 때문이다. 앱스토어 계정등록비 10만원이 부담되기도 했고 나 스스로가 갤럭시를 쓰고 있기 때문에 마음이 기울 수 밖에 없었다. 당시 아무것도 모르는 상태였기 때문에 한가지에 집중해서 결과를 내는것이 목표였다.

## 3. 자바 및 안드로이드 학습
  안드로이드 앱 제작에 대해 알아보면서 자바 혹은 코틀린 언어를 배워야 한다는 것을 알았다. 내가 안 것은 자바가 나온지 오래 됐기 때문에 모를 때 참고 할 것이 많다는 정도였다. 독학으로, 혼자 만드는 것이기 때문에 자바를 배우는 것이 합리적이라 생각했다. 더군다나 자바는 옛날에 소프트웨어와 관련 없을 때도 몇번쯤 들어본 단어였다. 그렇게 언어를 결정하고 일단 자바 강의를 찾았다. 다행히도 유투브에 남궁성 강사님의 자바의 정석 강의가 공개되어 있는것을 발견했고 그것으로 학습했다. 다음으로 마찬가지, 유투브에서 Do it 안드로이드 강의를 보고 학습했다.

## 4. 기획, 설계, 디자인
학습중 관심있게 본 것은 화면 화면좌표구하기, 도형그리기 그리고 Sqlite이다. 이것을 보고 PDF파일을 연다음, 빈칸 사각형들의 꼭짓점의 좌표를 Sqlite에 저장한 후 그것을 바탕으로 화면에 사각형을 그리면 된다는 기본적인 아이디어를 구상하였다. 또한 구글플레이를 통해 배포하여 수익을 얻고자 하기 때문에 두 루트 폴더를 나누어 기본폴더는 PDF페이지 제한을 두기로 했다.
  
그러나 코드 작성에 착수하자 막상 고민할 것이 많았다. 이런 문제가 생기면 그때그때 해결했다. 예를 들어 메인화면의 경우 Anki 뿐만 아니라 구글플레이의 다른 비슷한 앱들을 참고해서 나의 방식으로 만드는 것이다. 이런 방식은 제작이 거의 후반에 접어 들 때까지 유지되었는데 후회하는 부분이다. 허술한 설계는 제작 과정 내내 부메랑이 되어 돌아왔다. 만약 다시 만든다면 앱 내의 전체적인 기능과 액티비티 관계, 각 화면 내의 모든 기능과 아이콘 모양까지 디테일하게 정하고 갈 것이다. 
  
그래도 잘 한 부분이 있다면, 나 뿐만 아니라 다른 사람이 사용하고, 돈을 지불하는 것도 염두에 뒀기 때문에 사용자가 화면 요소를 봤을 때 가능한 아름다움과 간결함을 느끼며, 버튼의 기능과 내 의도를 어렵지 않게 이해할 수 있도록 고민하면서 했다는 것이다. 그렇다 해서 전문적인 디자이너 보다 잘 한 것은 아니겠지만, 화면 요소 하나에도 여러번의 기능 수정이 있어왔던 것을 생각할때 별 생각없이 만들었다면 상당히 조악한 결과물이 나왔을게 틀림없다 생각한다.

## 5. 메인 화면1
다른 사례들을 참고한 결과 메인화면은 폴더 디렉토리와 PDF파일 목록을 보고 관리 할 수 있는 뷰가 중심이 되어야 한다. 우선, PDF 파일의 페이지 수 제한이 있는 체험판 폴더를 만들기로 했기 때문에 외부 파일을 앱 내 폴더에 복사해오기로 했다. 여기서 문제가 생긴다. 이때가 안드로이드 API 30이 막 나왔을 때이다. API 29부터 저장소 정책에 큰 변화가 있었다. 그렇기에 기존에 구글에서 찾을수 있는 저장소 접근 및 폴더,파일 관리 방법의 대부분의 Deprecated되었다. 결국 많은 시간을 소요해 안드로이드 ACTION_OPEN_DOCUMENT 인텐트를 이용해 파일 경로를 찾는 코드를 참고할 수 있었다. 그 후 추가적인 구글 검색을 통해 내가 원하는 파일 Input/Outputstream을 통해 루트 디렉토리 밑의 절대 경로로 파일을 복사해 올 수 있게 되었다. 그러나 내가 원하는 위치로 파일 이동이 되지 않고 무조건 앱 폴더에 복사가 되었다. 이를 해결한것은 일반적인 Outputstream이 아닌 FileOutputStream이란것이었다. 추가적으로 외부 라이브러리를 통해 선택한 PDF파일의 페이지 수를 확인 할 수 있는 메서드도 만들어 놨다.
  
다음은 앱 폴더 내의 PDF 목록을 리사이클러 뷰로 보여줘야 한다. 우선 한개 폴더만 구현했다. 이것을 만들면서 lisfFiles를 처음 알았고 리사이클러 뷰 사용을 많이 연습하게 되었다. 폴더안의 파일과 파일명 배열을 넘겨준다. 그걸로 목록을 만드는 것이다. 전 단계에서 언급했듯이 과정에서 리스트의 아이템 커스텀이 기획, 디자인 되자 않아 정하고 넘어가야 했는데 시간이 많이 걸렸다. 너무 작은 크기는 아닌지, 한눈에 PDF파일인 걸 알아볼 수 있는지 고민할 게 많아 코드 작성 외적으로 어려운 작업이었다. 
  
## 6. PDF 뷰어와 드로잉
우선 PDF 파일을 외부에서 가져와 앱 내에서 존재하는 것을 확인하는 것 까진 성공했다. 학습을 하기 위해서는 우선 일반적인 PDF뷰어부터 구현해야 했다. 구글링으로 좋은 라이브러리가 깃허브에 올려져 있는 것을 보았다. 뷰어를 띄우는데 조금 애를 먹긴 했지만 다른 작업에 비해서는 비교적 쉽게 마무리 했다. 다음은 PDF를 가리는 사각형을 만드는 것이다. 라이브러리에 드로잉 기능이 내재되어 있었다. Readme로는 이해하는 데 부족해서 라이브러리 사용자들 질문을 뒤져가면서 사용법을 이해했다. 문제는 사용자가 원하는 대로 그리는 것이다.
    
그러기 위해서는 몇가지 고민이 필요했다. 첫번째로 라이브러리가 탭 같은 동작을 감지할 수는 있어서 그 위치에 도형을 그릴 수는 있었다. 하지만 자유자재로 사각형을 그리려면 드래그를 해야 했다. 그런데 그렇게 되면 PDF 페이지가 넘어가게 된다. 처음 만들때 페이지를 고정시킬 수는 있었지만 문서를 읽는 중에 다시 넘길 수 있도록 바꿀수는 없었다. 메인 화면을 구성하는 이후로 가장 어려운 상황이었다. 시행착오를 거쳐 해결책을 찾았다. 비어있는 투명한 뷰를 PDF뷰 위에다 겹치는 것이다. 입력을 투명한 뷰가 받을때는 문서 스크롤이 되지 않는다. 입력 뷰를 터치하는 순간의 좌표와 땔때의 좌표를 꼭지점으로 전달하여 사각형을 그리면 되었다. 
  
두번째로 좌표 획득의 문제이다. 이 앱은 다양한 모양과 크기의 화면을 가진 안드로이드 기기에 설치될 것이다. 뿐만 아니라 화면안에는 다른 버튼들도 자리를 차지할 것이다. PDF 뷰어는 그에 따라 자동으로 버튼을 제외한 화면을 채울수 있으니 상관없다. 그러나 사각형의 좌표는 다르다. 다양한 기기에서의 사용을 고려 했기에 물리적인 하드웨어 화면의 좌표를 가져오는 방식은 적절하지 않았다. 아니면 화면 내 다른 요소들과 뒤섞일 수도 있다. 결국 화면 전체가 아닌, PDF 뷰어의 한 꼭지점을 기준으로 한 좌표를 구해야 했다. 가로, 세로 각각의 수를 PDF 뷰 전체의 폭과 길이로 나누어 0에서 1사이의 소수, 즉 상대좌표로 나누어 Sqlite에 저장하면 버튼을 침범하지 않고 어느 기기에서나 같은 위치, 비율의 사각형을 보장 받을 수 있다. 뷰의 좌표는 물리적인 화면의 좌표를 가져오는 것은 매우 달랐고 라이브러리 해석 능력이 떨어져 상당히 고생했다. 결국 깃허브의 라이브러리 저장소에서 질문을 뒤지던 중 내가 원하던 코드를 얻을 수 있었다. 
  
이것이 세번째 어려움인데 그것을 적절히 개조하면 됐었는데 문제가 생겼다. 그 코드는 라이브러리 내 어떤 변수의 접근제어자가 Public일때 기준으로 작성되었는데 무슨 일에선지 내가 쓸때는 그것이 Private으로 바뀌어있었다. 라이브러리 일부를 수정해야 했고 결국 build.gradle에 간단히 한줄 복사붙여넣기를 하는 것이 아닌, 다른 방법으로 라이브러리를 이용 하는 방법을 찾아야 했다. 그결과 JAR 파일을 직접 넣는 방법이 있다는 것을 배웠다. 복잡한 방법이라 앞으로 가능한 쓰고 싶지는 않지만, 쓸 수있는 수단이 하나 더 생긴 것은 나쁘지 않다 생각한다.

## 7. 메인 화면2
메인 뷰의 중심에서 다음으로 중요한 것은 폴더이다. 다른 앱들을 참고한 결과 루트 디렉토리부터 말단 파일까지 한눈에 들어오는 폴더 트리 구조가 있었다. 내눈에도 그것이 사용자가 쓰기에 좋아보여 따라하기로 했다. 그러나 그걸 구현하는것은 생각보다 어려웠다. 사용자의 폴더 밑 파일의 추가, 삭제에 반응해야 했으며 한 폴더 안의 표시를 어떻게 하는지, 언제 그것이 끝나는지 고민해야 했다. 사용자가 누를때 접었다 폈다도 할수 있어야 했다. 당시 나에게 너무 어려웠기 때문에 대신 한 화면에 한 폴더만 보여주는 식으로 이동해 가면 어떨까 생각했다. 기능성을 희생하더라도 쉬운 길을 가자는 유혹이 있었다. 그러나 아무리 봐도 원안과 두번째 안의 수준은 너무 차이가 느껴졌다. 결국 구글과 유투브에 많은 시간을 써서 비슷한 해결법을 찾았다. 핵심은 재귀였다. 폴더 뷰 안에 다른 폴더뷰와 PDF목록을 보여주는 리사이클러 뷰를 넣는 것이다. 지금 보면 별거 아니지만 나는 이때 처음으로 재귀함수라는 것을 알았다. 사실 이 문제를 해결하고 나서도 한동안 '재귀'라는 용어 자체는 몰랐을 정도다. 이 방식을 쓰면 기본 폴더와 프리미엄 폴더를 구분하는 것도 쉬워지는 효과도 있었다. 그저 몇몇 매개변수만 바꿔서 각각의 재귀함수를 두번 시행하면 되기 때문이다. 추가적으로 PDF 가져오기 코드에도 수정을 가해 중복검사를 할수 있도록 했다. 다만 중복검사는 같은 폴더 내에 있는지만 확인한다. 다시 말하면 다른 폴더의 PDF는 따로 취급하기로 했다. 사용자에 더 유연한 사용을 가능하게 하고 싶었기 때문이다.
  
폴더를 추가, 삭제하는것, PDF목록 리사이클러 뷰를 다루는 동안 AlertDialog,애니메이션과도 많이 친숙해졌다.이것은 기타 디자인과도 연결되는데 4번의 내용처럼 사용자에게 어떤 화면을 보여주고 기능을 제시할지 고민을 많이 하고 시행착오를 겪었다. 폴더와 PDF 아이콘을 선정해야 했고 저작권 문제도 없어야 했다. 글자 크기와 폰트도 중요했다. 예를 들어 자식 폴더는 부모 폴더보다 들여쓰기가 되어있는 것처럼 되어있다. 어느정도 길이만큼 밀어넣어야 사용자가 보기에 편안하고 화면을 절약 할수 있는가 고민을 했다. 평소에는 신경 안쓰던 윈도우나 안드로이드 스튜디오 좌측의 탐색기 디자인에 관심을 갖고 분석해봤다. 탐색기는 아이콘과 폴더 혹은 파일 이름이 좌에서 우로 가로로 배치되어 있다. 거기서 하위의 아이콘의 왼쪽 끝부분은 자기 바로 상위의 이름의 왼쪽 끝부분과 선을 맞춰야 한다는 것을 알았다. 이런식으로 폴더 트리 뷰 구성의 중요한 부분이 해결되었다.
  
## 8. 핵심 학습간격 알고리즘 구현 및 사용자 옵션 설정
Anki는 각각의 카드 질문 답변 여하에 따라 다른 재학습 주기를 제공한다. 이 알고리즘은 널리 알려져 있는 것으로 Anki도 Supermemo라는 곳에서 가져온 것이다. 알고리즘 자체는 어렵지 않다. 그러나 사전 조사 동안 Anki를 포함한 여러 암기 앱 참고를 통해 알아낸 결과, Anki는 사용자가 자신에게 맞는 좀 더 복잡한 커스텀 학습주기를 설정할수 있다는 것을 알아냈다. 그리고 Anki 커뮤니티를 확인해 본 결과 그것을 적극적으로 사용하는 사람이 꽤 많다는 것을 느꼈다. 그렇기 때문에 나도 비슷한 기능을 만들어야겠다고 생각했다. 결론부터 말하자면 좋지 않은 생각이었다고 본다. 가장 큰 이유는 일반 사용자가 볼때의 알고리즘의 난해함 때문이다. 아마 내가 본것은 Anki에 깊게 빠져있는 사용자층일 것이다. 나 자신도 이 앱을 제작한다고 알고리즘에 대해 신경을 써서 알아보았으니 이정도의 복잡함은 당연하게 느껴졌다. 그러나 대다수의 사용자 층이 머리아프게 학습주기 알고리즘을 알고싶지는 않을 것이다. 이 기능을 만든다고 들인 시간과 노력도 무시할 수 없다. 꼭 필요하다면 추후 업데이트로 추가하는게 좋다. 지금은 나중에라도 설명을 첨부할까 생각중이지만 그러기에는 너무 난잡해질것 같아 그 부분이 걱정이다.
  
옵션은 두 종류로 나뉘었다. 앱 전체에 대한 일반 설정, PDF 파일 별로 적용되는 학습주기에 직접 관여되는 커스텀 학습주기 설정이다. 둘다 SharedPreference를 많이 이용해서 만들었다. 일반 설정 같은 경우 학습 중의 빈칸 제시나 여러 학습 시간이 된 빈칸들을 얼마나 모아서 공부 할수 있는지 설정하였다. 어쨋거나 이 앱은 원본 Anki하고는 다르기 때문에 일반 설정 같은 경우 참고한 Anki 일반설정에서 많은 부분이 필요 없었다. 허전한 설정 화면은 나중에 빈칸 색 설정용 기능으로 채우게 된다. 일반 설정은 SharedPreference xml 파일 한개로 완성 가능했다.
  
커스텀 학습 스케줄은 우선 삭제 할수 없는 기본 설정이 주어진다. 그리고 그 외에 다른 수정 가능한 설정을 만들 수도 있다. 기본 설정은 사용자가 학습시 기억하기 좋음 버튼을 누를 시 10분 후 다시 보여주지만 30분 후에 보여 달라고 할 수도 있다. 설정 하나당 SharedPreference xml 파일이 새로 생긴다. PDF 파일 들은 이 옵션들 중에서 하나씩에 매칭된다. 한 스케줄 옵션은 여러 PDF 파일에 적용될 수 있지만 PDF 파일은 하나의 옵션만 사용할 수 있다. 어떤 PDF 파일이 어떤 옵션을 사용하는지는 따로 SharedPreference xml파일을 만들어 관리하고 있다. 키값은 PDF 파일의 이름이 아니라 경로로 관리된다. PDF를 누르면 옵션을 추가, 수정, 삭제, 선택 할 수 있게 되어있다. 폴더를 통해서도 들어갈 수 있는데 그렇게 되면 하위 모든 파일의 설정을 한꺼번에 바꿀 수 있다.

## 9. Sqlite, PDF 뷰어 및 학습
Sqlite는 안드로이드에 대해배우긴 했지만 구글링을 좀더 하다가 SqliteHelper클래스로 다루는게 관리에 좋다고 하여 따로 Helper와 Control목적의 자바파일을 만들었다. 그런데 이 클래스를 사용하기도 했지만 Sqlite를 사용하는 자바파일 안에 직접 Raw Query를 사용하는 등 일관되지 않은 통제방법을 썼다는 것은 아쉬운 부분이다.
  
### 9.1 초기 설계
Column 구조는 우선 새로운 row가 추가될 때마다 id가 자동으로 1씩 올라가도록 했다. 다음으로 6에서 받아온 좌표를 기억해 지속적으로 쓸 수 있어야 했다. Sqlite를 이용했는데 우선 사각형의 대각선을 사이에 두고 마주보는 두 점의 PDF 뷰에 상대적인 x,y좌표 4개는 0과 1사이의 실수형의 열로 필수적으로 들어가야 한다. PDF 페이지를 넘길때마다 읽어오는 것도 달라져야 하기 때문에 PDF 제목과 페이지 수를 각각 문자열과 정수형 열들이 들어가게 했다. 거기에 재학습 알고리즘을 계산하는데 필요한 인자 3개와 다음 학습 날짜 1개를 만들었다. PDF 뷰어에 터치로 사각형을 그릴때 손을 땔때마다 row가 한줄씩 생성되고, 최초의 다음 학습시간은 0년 1월1일 0시 0분으로 바로 학습시간이 되게 했다.
  
### 9.2 DB Select 쿼리
PDF 파일을 선택해 열때 파일이름을 Intent에 실어서 보낼 수 있다. 그리고 라이브러리 내에서 현재 페이지 번호를 알려줄 수 있다. 그 두개 조건으로 쿼리를 날려 뷰어에서 데이터를 불러와 그에 맞는 사각형들을 그리는데 성공하였다.
  
### 9.3 Transaction과 Index
처음에는 아무리 많은 양을 외우는 사용자라도 row가 일만개를 넘지는 않을꺼라 생각했다. 이정도는 요즘 하드웨어 성능이면 쾌적하게 이용 가능할 것이라 생각했다. 하지만 혹시나 해서 알아보니 Anki 사용자들 중에서는 10만개 가까이 외우는 사람들도 있었다. 그렇다면 속도에 문제가 되지 않을까 걱정이 되었다. 그렇기 때문에 10만개를 목표로 했다가 마음을 바꿔 100만개정도 저장하는 것은 고려해야겠다 생각하여 속도 테스트를 해보기로 했다. 
  
70페이지 정도의 파일을 이름만 바꿔서 10개 만든다음 앱 내에 임시 버튼을 클릭할시 반복문을 통해 무작위 페이지, 무작위 좌표4개를 입력하기로 했다. 그런데 문제는 테스트를 위한 데이터를 입력하는 속도 조차 느리고 그동안 앱이 멈춘다는 것이었다. 좀더 빠르게 하는 방법이 없나 구글링을 하다가 Transaction이란 것이 있다는 것을 알게 되었다. Transaction을 쓰는 이유에는 여러 이유가 있지만 내 경우에는 데이터를 하나씩 저장할때마다 DB를 여는 것 보다 우선 메모리에 띄어 두고 마지막에 한번에 저장해 둔다는 것이 의미 있었다. 코드를 적용해보니 체감이 될정도로 빨라졌다. 입력의 문제가 끝나자 읽기 속도를 시험해 볼수 있었다. 100만개의 데이터를 넣고 뷰어를 열자 화면은 사각형들로 뒤덮여 있었고 페이지를 넘기는 것이 느렸다. 그렇기 때문에 여기서도 속도 향상 방법을 알아보았다. 
  
Index라는 것이 있다는 것을 알았다. 일반적인 읽기는 어떤 PDF의 어떤 페이지를 읽어오는지가 중요하기 때문에 이름과 페이지 컬럼으로 Index를 만들었다. 나중에 학습을 할때는 날짜를 기준으로 Select해야 하기 때문에 추가했다. Index는 단점도 있다고 들었다. DB의 용량을 더 많이 필요로 하고 Select를 제외한 다른 활동에서 속도에 불이익을 받는다. 그러나 나는 이 앱에 Index를 적용함으로써 얻는 손해는 없다고 판단했다. 학습같이 읽어오는 경우를 제외하면 사람이 하나씩 데이터를 추가하거나 변경하는 경우밖에 없다. Select를 빠르게 할 수만 있다면 다른 것은 느려도 된다. 용량 또한 100만 줄을 넣어봤을때 140MB정도로 모바일 기기 입장에서 충분히 감당할 수 있는 수준이라 봤다.
  
결과를 확인해보니 페이지를 넘길때 여전히 느리긴 하지만 분명히 좀더 쾌적해졌다는 것을 알 수 있었다. 느린 것도 한 페이지에 현실적으로 쓰이지 않을 수준의 많은 양의 직사각형이 드로잉 되었기 때문이라는 결론을 내렸는데 근거는 의외로 아무 빈칸도 그려지지 않은 PDF에서 찾을 수 있었다. Index를 적용하기 전에는 아무것도 없는 PDF일지라도 버벅거렸지만 Index를 적용하자 완전히 쾌적해졌다. Index에 대해 알고나니 이것은 무조건 DB의 처음부터 끝까지 풀스캔 하지 않고 Select 할 데이터가 없는 경우 빠르게 검색을 마치기 때문이라는 결론을 내렸다. 상당히 성취감 있는 깨달음이다.
  
추가적으로 DB의 PDF 제목은 단순 제목이 아니라 그 파일의 경로를 저장했다. 그러나 앱 내의 PDF 저장 폴더의 루트 디렉토리 위치까지의 경로는 긴편이다. 한개만 있다면 괜찮지만 많은 수의 데이터가 있다면 상당한 저장공간 차지가 있다고 판단하였다. 그런데 이 저장경로의 경우의 수는 기본 디렉토리, 프리미엄 디렉토리 2개로 나눌 수 있다. 자장할 때 루트 디렉토리까지의 경로 문자열을 기본 폴더는 'a'로, 프리미엄 폴더는 'b'로 바꾸어 넣고 읽어 올때 다시 복원 하게 하였다. 이걸로 한 row당 대략 40~50자정도의 문자열의 크기 대부분을 아낄 수 있었다. 나중에 이것이 원시적인 압축의 원리와 비슷하다는 것을 알았다.

### 9.4 학습 화면
학습화면은 입력/읽기 화면처럼 하나의 PDF파일을 가지고만 열 수도 있지만 한 폴더내의 모든 파일, 혹은 앱 내의 모든 파일을 대상으로 학습 할 수 있게 하였다. DB에서 데이터를 선택하는 기준은 현재 시간이 Anki 알고리즘에 의해 계산된 다음 학습시간보다 미래일 경우이다. 여기에 일반 설정에서의 최소 학습시간 단위 기준인 분, 시간, 일을 반영하였다. 우선 읽어 오는것은 전체 데이터가 아니라 id이다. 그것으로 공부해야 할 id배열을 만든다. 그리고 일반 설정을 토대로 그중에서 어떤 순서대로 공부할 것인지를 선택한다. 가령 배열이 빌 때까지 무작위로 id를 선택한다. id를 선택하면 그 id에서의 PDF 이름(경로)와 페이지 숫자를 가지고 해당 페이지의 다른 빈칸 데이터들도 선택해 가져 오고 PDF뷰어에도 제목과 페이지를 전달해 새로고침하게 한다. 그렇게 되면 화면에는 다른 빈칸들이 있고 지금 학습하는 빈칸이 다른색으로 깜빡이게 된다. 정답을 확인 후 사용자가 느끼는 난이도에 따라 커스텀 학습주기 설정을 반영하여 Anki 알고리즘대로 다음 분단위 학습시간 및 다른 계산에 필요한 변수들을 저장한다. 이것을 id 배열이 빌때까지 계속한다. 배열이 비고나면 즉시 다시 학습해야 하거나 학습시간동안 다음 학습시간이 된 경우가 있을 수 있다. 그렇기 때문에 다시한번 선택을 한다. 두번 연속으로 빈 배열을 만나면 학습은 끝이다. 현재 시간을 기준으로 학습시간이 지난 경우에 선택하기 때문에 중간에 학습을 그만둬도 된다. 나중에 다시 시작하면 정상적으로 할 수 있기 때문이다.

## 10. 학습 알람
학습시간이 되면 앱에서 알람을 통해 알려주고 알람을 누르면 즉시 모든 PDF를 학습하는 화면이 켜지게 만들었다. PendingIntent와 AlarmManager를 이용했고 DB에서 다음 학습 날짜중 가장 이른 날짜를 골라 알람 예약 설정을 해두는 것이다. 기능은 알람을 예약하는 것, 취소하는 것, 이미 켜진 알람을 없애는 것 3개를 만들었다. 입력용이나 학습용 PDF뷰를 열때 알람이 취소되고 있던 알람도 사라진다. 창을 닫을때는 입력용일 경우 변동이 있을 경우 알람을 다시 예약한다. 학습용도 엑티비티가 꺼질 때 알람을 예약하도록 하였다. 일반설정을 통해 SharedPreference를 조작할 수 있고 알람 설정이 꺼져있으면 예약만 되고 울리도록 하지는 않는다.

## 11. 메인 화면 3
이제까지는 설정 버튼이나 전체학습 버튼등이 화면의 제일 상단에 있었다. 임시로 기능만 만들어 둔 것이다. 그러나 정리를 하기로 하고 햄버거 메뉴를 통해 옮겼다. 햄버거 메뉴에 일반설정, 커스텀 학습간격을 옮겼고 PDF를 옮기거나 삭제시 Sqlite에 잔류해 있는 데이터를 제거 할수 있도록 기능을 만들었다. DB에 테이블을 하나 만들어 가져온 PDF목록을 만들어 그 목록에서 디렉토리들에 들어있는 목록을 제거후 남아있는 데이터들에 해당하는 본 테이블의 데이터를 삭제하는 것이다. 프리미엄 결제 버튼또한 여기에 만들었다. 프리미엄 혜택을 적어놓고 결제 여부에 따라 시각적 효과가 달라지게 하였다. 햄버거 메뉴의 결제 버튼은 프리미엄결제 기능을 활성화 시키는 두가지 방법중에 하나이다.

## 12. 광고와 구독
### 12.1 광고
광고는 구글링과 ADMOB의 설명대로 manifest에 광고ID를 설정하고 따로 광고 클래스를 만들어 통제하도록 했다. 광고를 넣는 위치와 시점이 문제인데 ADMOB 정책과 앱의 특성, 유저의 불쾌감을 고려한 결과 학습이 시작할 때 광고 불러오기를 하고 끝났을때 띄우기로 하였다.

### 12.2 구독 결제
결제를 하는 방법은 두가지이다. 햄버거 메뉴에서 결제 버튼을 누르거나 구독 하지 않은 상태에서 프리미엄 폴더를 누른다. 그러면 Alertdialog가 뜨고 거기서 결제 버튼을 다시 누르면 진행이 된다. Alertdialog는 바깥 화면을 터치해도 사라지지 않게 하고 확실히 결제나 돌아가기 버튼을 누르거나 둘 중 하나만 하도록 했다. 프리미엄 폴더는 미구독시 닫혀있다. 결제는 깃허브에서 찾은 구글플레이 결제라이브러리의 도움을 받았다. 결제 정보를 Sharedpreference에 저장한다. 결제가 성공 시 메인화면을 새로고침하여 프리미엄 폴더를 사용 할 수 있도록 하고 학습시에도 결제 여부에 따라 프리미엄 폴더내의 PDF를 포함하여 학습 데이터를 불러오거나 불러 오지 않게 만들었다. 5분 단위의 테스트 결제 결과 구독시에는 바로 적용이 되었으나 구독 종료시에 실제 적용에 차이가 있어 한동안 헤맸으나 종료는 동기화에 시간이 걸린다는 것을 알았다.

## 13. 마무리
계기에서 언급했듯이, 개발에 입문한 것은 취업 준비가 아닌, 내가 생각한 앱을 만들어 보고 싶어서였다. 어떻게든지 내가 원하는 결과물을 만들어 내는 것이 목적이었지, 일반적으로 말하는 공부 그 자체가 목적은 아니었다. 이러한 태도로 접근한 것은 득도 있고 실도 있었지만 득이 매우 컸다고 생각한다. 우선, 단순히 프로그래밍 언어 문법을 배우는 거나 클론 코딩을 하는 것보다는 더 많은 부분을 생각해야 했으며, 이 앱을 만들때만 할 수 있을법한 고민도 많이 했다. 우선 위에서 언급했듯, 원본 Anki와 구글 플레이스토어의 다른 유사 앱들을 관찰하면서 약간의 시장조사와 디자인에 대한 고민을 했다. 개발 쪽으로는 손에 기초적인 코드작성 머슬메모리를 익혔다. 뿐만 아니라 자바와 안드로이드 강좌에서 배웠던 기능들을 의미있게 사용하는 경험을 얻었고 라이브러를 사용해도 했다. 이밖에도 막히는 부분을 직접 찾아 해결한 경험도 소중했다. 이 과정에서 기초 강좌에서 가르쳐 주지 않는 것도 많이 배울 수 있었다. 하지만 무엇보다 중요한 것은 해결방법이 막혔을 때의 마음가짐이다. 문제가 생겼을 때는 물어볼 데도 없고 너무 막막해서 혼자 고민하거나 구글링을 많이 했다. 기능 추가를 포기하거나 더 쉽지만 질이 떨어지는 방향으로 가는 유혹도 느꼈지만 내가 구상한 목표를 포기하고 싶지 않았다. 이런 어려움을 겪고 해결하는 것을 몇번 반복하다 보니 낙관적인 마음가짐이 생겼다. '내 수준에서 막히는 것은 내가 모르는 것일 뿐 해결 불가능 한 것이 아니다. 이 세상 어디에는 분명히 해결 방법이 있다.' 라는 생각이 자리 잡으니 어려움에 부딪혀도 침착하게 접근 가능해졋던 것이다.
  
아쉬웠던 점도 있다. 처음부터 구글 플레이 배포라는 결과물을 내보자라는 식으로 시작했기에 나타난 아쉬움이다. 자바의 정석과 Do it 안드로이드를 한번 빠르게 훑고 시작했기 때문에 기본기가 아쉬웠던 점이 있다. 그리고 알고리즘 공부에 대한 인식이 없었던 점도 아쉬웠다. 알고리즘 공부는 복잡한 알고리즘 자체뿐만 아니라 어떤 언어를 익히는걸 넘어 자료구조를 능숙히 사용하게 하고 속도를 고려하게 하는 마인드를 갖추게 하는데 도움이 된다고 본다. 또한 4번의 기획, 설계, 디자인에서 언급했듯, 초기에 계획을 디테일하게 세워서 들어갔으면 개발기간이나 어려움이 많이 줄어들었을 걸로 생각한다. 물론 자바나 안드로이드에서 쓸 수 있는 기능에 대해 아는 것이 별로 없었기 때문에 사전 계획을 세우는 데 한계는 있었을 것이다. 하지만 최소한 디자인만이라도 생각하고 갔으면 훨씬 수월했을 것이라 본다. 또한 기능을 구현하기 위해 RxJava와 같이 잘 모르는 기술을 쓰려한 흔적도 부끄러운 일이다. PDF 파일을 앱 내에 넣을 때 애니메이션 동시작업을 하기 위해 asyncTask를 쓰려다 deprecated가 되었다는 것을 알고 찝찝해서 도입한 것이다. 쓰레드를 생각못했던 것인데 바로 썻으면 좋지 않았을까 한다. 마지막으로 코드가 지저분했다. 이 앱을 완성하기 위해 구글검색을 많이 이용했었다. 그러면서 클린코드라는 단어를 지나치면서 간간히 보았다. 그때는 일단 완성된 앱을 만들어 보는 것이 목표였으므로 집중해서 보지 않았다. 하지만 스파게티 코드는 좋지 않다거나, 클래스를 분리해야 한다는 것을 어느정도 의식하여 나름대로 신경 써보기도 했다. 그러나 아무래도 클린코드하고는 거리가 멀다고 보인다 기본 프로그래밍 공부를 할때는 이런것을 집중적으로 배운적은 없었기 때문이다. 첫 프로젝트고, 완성 자체에 의의를 두었으니 후회는 하지 않는다. 하지만 다음번에는 좀더 깔끔한 코드작성을 위해 신경 쓸 것이다.
  
구글 플레이스토어에 게시 후 시간이 지났으나 사실상 돈은 못 벌었다. 그러나 전세계에서 다운로드 수도, 삭제 수도 꾸준히 올라가고 있다. 누군가는 내 결과를 이용해 준다는 것이다. 도움이 됐다는 리뷰도 받았다. 그것들을 보면서 지금까지 위에 쓴 글과 경험은 의미 기술적으로나 보람으로나 의미 있는 경험일 수 밖에 없다는 사실을  재확인 한다.
