# Final-Project 

```
##1. GIT COMMIT 작성규칙 :예시)2020-09-18 <추가> 네이버 로그인 기능
      ( 날짜 - <추가 , 수정, 삭제> -추가기능명) 순으로 작성 
```
 ```
 2. GIT PUSH 순서 
  (작업이 끝나고 커밋 하기 이전 상태를 가정)
 1. origin remote에서 fetch를 먼저 받는다 (마스터에 먼저 커밋된 것들과의 충돌을 막기위해)
 2. fetch 받은 origin remote와   자신의 마스터 브랜치를 merge 시킨다.
 3. 본인의 브랜치를 마스터 브랜치와 머지시킨다
 4. 3에서 충돌이 날 경우 해당 충돌난 곳에 대해 카카오톡 단톡방에 물어보고 상의 한 후 수정하도록한다.
 5. 충돌이 없다면 자신의 브랜치를 마스터 브랜치에 merge 시킨다. 
 6. 마스터 브랜치를 github에 push 한다 
 ```