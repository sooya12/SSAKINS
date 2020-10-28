# SSAKINS




## Code Style


### Java



#### Naming

 + Class는 첫글자 대문자 + camel case
    + ex) MainController.java
 + Method 나 Variable은  첫글자 소문자 + camel case
    + ex) public void setUserName(); / private String userName;
 + 이름만으로 기능이나 특성 유추 할 수 있게 명명하기

#### Statement

+ if

  + 한줄 일 때, Block 처리하기

  + else if / else /중괄호는 조건문 바로 옆에 붙이기

    + ```java
      if(condition){
      	statement;
      } else if(condition2){
      	statement2;
      } else{
      	statement3;
      }
      ```

+ for 
  
  + 단순 반복문은 iterator를 i, j, k, ... ,z 순으로 명명 하기



+ Null check 하기
  + Optional 지향 + Null 상황 대응하기
+ Method Chaining
  + 체이닝 한 메소드 당 줄 바꾸기





#### Comment

+ 기본적으로 한글로 달기
+ 주석처리된 코드 사유 적고 작성자가 책임지고 지우기
+ /**/ 을 이용해서 위에다가 쓰기
+ 간단한 설명은 옆에 // 주석 쓰기





### Javascript

#### Naming

+ var 변수보다 let 변수 사용 지향하기

  

#### Statement

+ 들여쓰기 : 2칸
+ callback 함수 사용 시, arrow function 사용하기

#### Comment

+ html, css 에서는<!-- -->로 주석 처리하기



---



## Commit message

[BE/FE/FS] initial/update | 내용 | Jira 이슈 번호

> BE : 백  
> FE : 프론트  
> FS : 풀스택  


README(이름)



## Branch 규칙

feature/기능명 으로 develop에서 분기해서 사용하고, 기능 개발 완료 후 develop에 머지!





## Stack


![batge](https://img.shields.io/badge/JDK-11.0.8-blue) ![batge](https://img.shields.io/badge/Spring%20Boot-2.3.4-blue) ![batge](https://img.shields.io/badge/JPA----blue) ![batge](https://img.shields.io/badge/mongeDB----blue)  
![batge](https://img.shields.io/badge/Vue.js-2.6.11-blue) ![batge](https://img.shields.io/badge/Node.js-12.18.2-blue) ![batge](https://img.shields.io/badge/Maven-3.6.3-blue)

![batge](https://img.shields.io/badge/VSCode-1.49.0-green) ![batge](https://img.shields.io/badge/STS-3.9.12-green) ![batge](https://img.shields.io/badge/Intellij-2020.2.1-green)  

![batge](https://img.shields.io/badge/Jira-8.2-lightgreen) ![batge](https://img.shields.io/badge/Gitlab-11.6.2-lightgreen) ![batge](https://img.shields.io/badge/Notion----lightgreen) ![batge](https://img.shields.io/badge/MatterMost----lightgreen)


![batge](https://img.shields.io/badge/Ubuntu-18.04.1%20LTS-black) ![batge](https://img.shields.io/badge/Docker-19.03.12-black) ![batge](https://img.shields.io/badge/NginX----black) ![batge](https://img.shields.io/badge/Jenkins-2.176.1-black)





## Gantt Chart

```mermaid
gantt
    dateFormat  YYYY-MM-DD
    title       SSAKINS
    excludes    weekends
    %% (`excludes` accepts specific dates in YYYY-MM-DD format, days of the week ("sunday") or "weekends", but not the word "weekdays".)

    section 프로젝트 설계
    기능 설계	:done, 2020-10-12, 5d
    와이어프레임 설계	:done, 2020-10-12, 5d
    ERD 설계	:done, 2020-10-12, 5d
    
    section 프로젝트 준비
    기술 학습	:crit, active, 2020-10-17, 16d


    section 기능 구현
    설정 CRUD	:active, 2020-10-26, 7d
    회원	:active, 2020-10-28, 3d
    DB	:active, 2020-10-28, 3d
    레퍼런스	:2020-11-04, 4d
    설치형 패키지	:crit, active, 2020-10-19, 21d

    section TEST
    알파 TEST	:2020-11-09, 12d
    베타 TEST	:2020-11-17, 6d
   
    section 유지보수
    유지보수	:2020-11-09, 12d 
    
    section 최종발표
    UCC	:2020-11-23, 4d
    PPT	:2020-11-23, 4d
```



## ERD

```mermaid
erDiagram
    User ||--o{ Project : setting
```

