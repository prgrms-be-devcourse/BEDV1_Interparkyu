# 목차  
* [프로젝트 소개](#%EF%B8%8F-프로젝트-소개)
    * [인터파크 티켓팅은 어떤 서비스인가요?](#인터파크-티켓팅은-어떤-서비스인가요)
* [팀 소개](#%EF%B8%8F-팀-소개)
* [기술스택](#-기술스택)
  * [개발 환경](#개발-환경)
  * [협업 툴](#협업-툴)
  * [기타](#기타)
* [설계](#%EF%B8%8F-설계)
    * [ERD](#erd)
    * [API 문서](#api-문서)
* [개발 환경 설정](#-개발-환경-설정)
* [기타 규칙](#-기타-규칙)

# Interparkyu
    뀨팀의 인터파뀨 티켓팅 서비스! 포도 따러가자🍇

## ♨️ 프로젝트 소개
    인터파크 티켓팅의 예매기능을 클론 코딩한다.
    프로젝트 기간 : 2021/10/25 ~ 2021/11/05

### 인터파크 티켓팅은 어떤 서비스인가요?
<aside>
🗣 각종 공연 티켓을 구매할 수 있는 서비스 입니다. 

- 콘서트, 팬미팅, 뮤지컬 등 각종 공연
- 스포츠 경기의 입장권
- 행사 전시 및 스포츠 레저 티켓

공연 예매 시작 시간에 트래픽이 몰리는 시스템으로 예약처리가 까다로운 부분이 있습니다!
</aside>

---
## 🧚‍♀️ 팀 소개

| 김다희 - Scrum Master | 김휘년 - Product Owner | 이상민 - Tech Lead | 조규현 - Observer | 
| :-------------------: | :--------------------: | :----------------: | :----------------: | 
| ![트위티1 (1)](https://user-images.githubusercontent.com/68796085/140476834-ec30d9a4-3d0f-4b45-a99a-529fccd2dffd.png) |   ![Image from iOS](https://user-images.githubusercontent.com/68796085/140476756-1cf332fa-b10d-4d95-80c8-cf5c6cf61d3f.png) |         ![움짤2](https://user-images.githubusercontent.com/68796085/140476612-71d62f09-6ed7-4555-b70f-34127ec09ba1.gif)  | <img src="https://user-images.githubusercontent.com/68796085/140478941-fc431694-f5e8-468d-9e48-7e0dd8f274fc.jpg" style="width:300px"> |

---
## 🚀 기술스택
### 개발 환경
<p align="left">
  <img src="https://img.shields.io/badge/Java17-007396?style=flat-square&logo=Java&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/Spring Boot 2.5.6-6DB33F?style=flat-square&logo=Spring&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/-JPA-gray?logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/MySQL 8-4479A1?style=flat-square&logo=MySQL&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/Maven-4429A1?style=flat-square&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/Junit-25A162?style=flat-&logo=JUnit5&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/JavaScript-f7df1e?style=flat-square&logo=javascript&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/HTML5-e34f26?style=flat-square&logo=html5&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazon%20AWS&logoColor=white&style=flat"/></a>

### 협업 툴
  <img src="https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=Jira%20software&logoColor=white&style=flat"/></a>
[![Notion](https://img.shields.io/badge/Notion(click)-red.svg)](https://backend-devcourse.notion.site/0a3d26f3caaf4c55a2b451e795cbcb10)
<img src="https://img.shields.io/badge/slack-232F3E?style=flat-square&logo=slack&logoColor=white&style=flat"/></a>
<img src="https://img.shields.io/badge/Git Hook-007396?style=flat-square&logoColor=white&style=flat"/></a>

### 기타
  <img src="https://img.shields.io/badge/ERDCloud-4429A7?style=flat-square&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/Postman-0052CC?style=flat-square&logo=Postman&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/Swagger-85EA2D?style=flat-square&logo=Swagger&logoColor=white&style=flat"/></a>
  <img src="https://img.shields.io/badge/Husky-232F3E?style=flat-square&logoColor=white&style=flat"/></a>
</p>

## 🏗️ 설계

### ERD
![인터파뀨 (1)](https://user-images.githubusercontent.com/68796085/140474351-d76297bf-77b8-4012-9373-35e8525d2538.png)

### API 문서
[포스트맨 API 문서](https://documenter.getpostman.com/view/16516331/UVC2HUR7)

---
## 💻 개발 환경 설정 

- 프로젝트 설치

```bash
$ git clone https://github.com/prgrms-be-devcourse/BEDV1_Interparkyu

# 프로젝트 루트 디렉토리에서 실행 
# husky 설치로 git hook 관리 
$ npm install
```

- 필요 환경 변수

```bash
DB_HOST=userHost
DB_USERNAME=userName
DB_PASSWORD=userPassword
```

- 환경 변수가 설정 되어 있지 않다면 Intellij Plugin 설치
- [Intellij Plugin - EnvFile 설치](https://plugins.jetbrains.com/plugin/7861-envfile)

> Project root 경로에 `.env` 파일 생성 후 `필요 환경 변수` 작성

- EnvFile 플러그인 설정

<details>
<summary>설정 캡쳐 (펼치기)</summary>
<div markdown="1">
	<img src="https://user-images.githubusercontent.com/20269425/140634606-e092aad9-f273-49a0-a976-f432d74f3a36.png" alt="Run -> Edit configuration"  height="600px">
  <img src="https://user-images.githubusercontent.com/20269425/140716435-a7a96611-cf37-4483-ada2-313291af2371.png" alt="Spring boot EnvFile"  height="600px">
</div>
</details>


## 🔨 기타 규칙
- 깃 커밋 규칙
  - 브랜치명은 `feature/티켓이름`으로 한다
  - git hook에 의해 해당 브랜치에서 커밋시 타이틀에 자동으로 티켓이름이 들어간다 
