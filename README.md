# Android-Anki-Blank
코딩에 입문하계된 계기이자 처음으로 완성한 프로젝트이다.
이것을 만드는 중에는 깃허브 같은 것은 모르는 상태였기에, 그저 배우고 코드를 짜는데만 몰두했었다.
그러나 기록의 중요성에 대해 느낀 점이 있어 프로젝트 과정을 되돌아 보면서 뒤늦게나마 나름의 깨달은 점을 써두고자 한다.

## 1. 계기
대학 재학 중, 산업기사 시험을 공부하던 중, 암기를 좀 더 편하게 했으면 했다. 그 전부터 Anki라는 카드형 암기 보조 프로그램을 알고는 있었다.  
  
Anki는 앞면에 문제, 뒷면에 정답이 있는 카드를 추가하여 많이 틀릴 수록 더 자주 보여준다. 인간의 암기 특성을 연구한 내용을 참고하여 만들어 졌다 한다.  
  
그러나 나에게는 수동으로 카드를 추가하는 과정이 배보다 배꼽이 크다 느꼈다. 더군다나 카드는 시험 준비서와는 달리, 주위내용과 분리되어 있어 맥락을 통한 공부는 불가능에 가깝다. 
  
결국 '직접 만들어 볼수 없을까?' 라는 생각이 들었고 Anki와 비슷하지만, 카드대신, PDF 파일의 어느 페이지의 특정 부분을 가리는 빈칸의 형태로 만들어 보기로 마음먹었다. 그렇게 산업기사 시험을 마친 직후 프로그래밍 입문공부 및 앱 제작 과정에 착수하였다.

## 2. 안드로이드를 선택한 이유
1에서의 계기 이후, 나는 내 아이디어가 꽤 괜찮아 보였고, 나 혼자 잘 사용하는 것을 넘어 용돈이라도 벌어 볼수는 없을까 생각하였다. 그렇게 마음을 먹자 고민해야 할 것이 많아졌다.  
  
우선 형태를 정해야 했다. 당장 처음 떠오르는 생각은 Anki 처럼 윈도우에서 쓸 수 있는 프로그램을 다운받을 수 있도록 하는 것이었다. 그러나 당시 소프트웨어 분야에 대해 아무것도 모르는 나라도 그러려면 서버가 필요하고, 서버를 운영하려면 서버비가 들어간다는 사실 정도는 알고 있었다. 무일푼인 내가 그만한 위험을 감수할 수는 없었기에 포기했다. 더 큰 문제는 결제였다. 코딩 경험과 사업 경험 둘다 없는 나에게 결제 기능을 만드는 것은 기술적인 부분을 넘어서 높은 벽으로 느껴졌다.
  
두번째로 떠올린 방식은 웹 브라우저 상에서 기능을 구현하는 것이다. 결과를 먼저 말하자면 이것도 기각했다. 우선 이것 또한 서버와 결제의 문제에서 자유로울 수는 없다. 첫번째보다 더 심각한데, 알아본 결과 웹에는 프론트엔드와 백엔드가 있는데 뭔가 빨리 결과를 내고 싶은 입장에서 2배의 지식을 습득해야 하는 필요성에 의문이 있었다. 그리고 오리지날 Anki에 생각이 묶여서 그런지는 몰라도 굳이 웹 브라우저를 통해 암기 공부를 하는 것이 감정적으로도 조잡하다고 느껴졌다.
  
또다른 선택지는 모바일이었다. 우선 모바일은 서버가 필요없었다. 또한 결제, 배포및 홍보의 어려움이 앞의 두 가지 것들에 비해 매우 낮다고 느꼈다. 구글과 애플의 앱스토어가 서버와 배포, 결제를 맡아서 해주고 있는 형태이기 때문이다. 이때는 고려하지 못했지만, 유저의 암기 데이터도 기기 내에 저장 할 수 있다는 이득도 누릴 수 있었다.
