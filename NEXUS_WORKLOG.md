

---

# 🏛️ NEXUS Framework 2.0 프로젝트 작업 일지

## 1. 프로젝트 아키텍처 (Blueprint)

* **Frontend**: Nuxt 4 (Atomic Design + Theme Engine)
* **Backend**: Spring Boot 3.2+ (Multi-DataSource, JPA/MyBatis)
* **Infrastructure**: Docker (MySQL 8.0, MSSQL 2022 x 2)
* **DB 전략**:
* **Core (MySQL)**: 프레임워크 설정, 코드 관리, 디자인 토큰 (JSON)
* **Service (MSSQL 1)**: 실 서비스 데이터, 게시판, 캐시 데이터
* **Legacy (MSSQL 2)**: 시설 원장 데이터, 최종 수강신청 접수



---

## 2. 개발 환경 및 가이드라인

### 2-1. 시스템 환경

* **IDE**: Eclipse IDE for Enterprise Java and Web Developers
* **Language**: Java 21 / **Build Tool**: Gradle / **Version Control**: GitHub (`nexus-v2`)

### 2-2. Backend 구조 가이드 (`src/main/java`)

* **Domain**: `com.nexus.backend.domain.*` (VO/DTO)
* `common`: 공통 요청/응답 객체 (`NxSearchRequest`, `NxPageResult`)


* **Mapper**:
* `repository.core`: MySQL 전용 (설정/토큰)
* `repository.service`: MSSQL 전용 (비즈니스 로직)


* **Controller**: `com.nexus.backend.controller` (API 진입점)
* **XML Location**:
* Core: `src/main/resources/mapper/core/`
* Service: `src/main/resources/mapper/service/`



### 2-3. Frontend 구조 가이드 (`frontend/app`)

* **Composables**: `composables/useNexusTheme.ts` (테마 로드 및 CSS 변수 주입)
* **Pages**:
* `pages/notice/index.vue`: 리스트 및 검색
* `pages/notice/[id].vue`: 상세 조회
* `pages/notice/create.vue`: 등록
* `pages/notice/edit/[id].vue`: 수정


* **Types**: `types/NxNotice.ts` (데이터 인터페이스)

---

## 3. 작업 히스토리 (Completed)

### [Step 1] UI Skeleton & 디자인 시스템 (2026-01-03)

* ✅ Nuxt 4 + Tailwind CSS 기반 아토믹 컴포넌트(`NxButton`) 규격화 완료.

### [Step 2] 인프라 및 멀티 DB 연동 (2026-01-04)

* ✅ Docker 기반 Multi-DB (MySQL + MSSQL) 구축 및 `log4jdbc` 설정 완료.
* ✅ **Troubleshooting**: WSL 2 Docker 연동, Spring Boot 인코딩, 드라이버 호환성 문제 해결.

### [Step 3] 디자인 토큰 시스템 구축 (2026-01-04)

* ✅ MySQL `NX_DESIGN_TOKEN` 테이블 설계 및 `NxDesignToken` 도메인 구현.
* ✅ **Troubleshooting**: Eclipse 컴파일러 파라미터 옵션 이슈 해결.

### [Step 4] 프런트엔드 테마 엔진 및 백엔드 연동 (2026-01-04)

* ✅ Nuxt 4 디렉토리 구조 최적화 (`srcDir: 'app'`) 및 TypeScript 설정.
* ✅ `useNexusTheme`을 통해 백엔드 토큰 데이터(JSON)를 CSS Variable로 실시간 반영 성공.

### [Step 5] Service DB(MSSQL) 조회 기능 구현 (Read) (2026-01-04)

* ✅ **Backend**: `NoticeMapper` 및 XML 위치(`mapper/service`) 매핑 수정으로 MSSQL 연동 성공.
* ✅ **Frontend**:
* `app.vue` 레이아웃 구조화 및 `<NuxtPage />` 적용.
* `notice/index.vue` 리스트 구현 및 CSS Variable Fallback(깜빡임 방지) 처리.
* `notice/[id].vue` 동적 라우팅 및 상세 조회 구현 (`MapsTo` 활용).



### [Step 6] 대용량 처리 및 검색 (Pagination & Search) (2026-01-04)

* ✅ **Pagination**: `NxSearchRequest`, `NxPageResult` 표준 객체 정의 및 MSSQL `OFFSET-FETCH` 쿼리 적용.
* ✅ **Dynamic Search**: MyBatis `<sql>` 태그를 활용하여 제목/내용 동적 검색 쿼리 구현.
* ✅ **Troubleshooting**: Spring Boot 3.2 파라미터 매핑 이슈 (`@PathVariable("name")`) 해결.

### [Step 7] 게시글 등록 (Create) (2026-01-04)

* ✅ **Backend**: `INSERT` 쿼리 및 `POST` API 구현 (`@RequestBody` 매핑).
* ✅ **Frontend**: `create.vue` 입력 폼 구현 및 저장 프로세스 완성.

### [Step 8] 게시글 수정 및 삭제 (Update & Delete) (2026-01-04)

* ✅ **Backend**: `UPDATE`, `DELETE` 쿼리 및 `PUT`, `DELETE` API 컨트롤러 매핑.
* ✅ **Frontend**:
* `edit/[id].vue` 생성 및 기존 데이터 바인딩 로직 구현.
* 상세 화면에 수정/삭제 버튼 연결 및 이벤트 핸들링.


* **결과**: NEXUS Framework의 **CRUD Core Engine** 완성.

---

## 4. 다음 진행 계획 (Roadmap)

### [Step 9] 유효성 검사 (Validation) & 예외 처리

* **목표**: 데이터 무결성 보장 및 사용자 경험(UX) 강화.
* **Backend**: `@Valid` 어노테이션 및 `GlobalExceptionHandler` 적용.
* **Frontend**: 입력 값 검증 로직 및 에러 메시지 UI 표시.

### [Step 10] 표준 응답 객체 (Standard Response)

* **목표**: API 응답 규격 통일 (`code`, `message`, `data`).
* **Action**: `NxResponse<T>` 래퍼 클래스 설계 및 적용.

---
### [Step 9] 유효성 검사 및 예외 처리 (2026-01-04)
- **Backend**:
    - `spring-boot-starter-validation` 의존성 추가.
    - `NxNotice` 도메인에 `@NotBlank`, `@Size` 등 제약 조건 정의.
    - `NoticeController`에 `@Valid` 적용하여 입력값 검증 수행.
    - `GlobalExceptionHandler`를 구현하여 `MethodArgumentNotValidException`을 `400 Bad Request`로 변환.
- **Frontend**:
    - `create.vue`의 저장 로직을 `$fetch`로 변경(Nuxt 권장사항 준수).
    - `try-catch` 블록을 통해 백엔드의 에러 메시지(`response._data.message`)를 사용자에게 알림 표시.
- **결과**: ✅ 데이터 무결성 보장 및 안정적인 에러 핸들링 체계 구축.
[Step 10] 표준 응답 객체 단계까지 완벽하게 마무리하셨습니다. 고생 많으셨습니다! 이제 마음 편히 다음 단계로 넘어가도 좋습니다.

📝 2026-01-04 작업 요약 (저장됨)
1. 백엔드 (Backend)

표준 응답 객체 (NxResponse) 적용: NoticeController의 모든 API가 통일된 규격(code, message, data)으로 응답하도록 수정했습니다.

2. 프런트엔드 (Frontend)

데이터 연동 구조 개선: 백엔드의 표준 응답(NxResponse)을 처리하도록 notice 관련 페이지(목록, 상세, 수정)의 로직을 전면 수정했습니다.

Atomic Design 시스템 구축:

기본 부품(Atoms) 생성: NxButton (확장형), NxInput, NxSelect 컴포넌트를 만들었습니다.

설정 최적화: nuxt.config.ts를 수정하여 컴포넌트 이름에 폴더명이 붙지 않도록 설정했습니다. (<AtomsNxInput> → <NxInput>)

UI/UX 개선:

공지사항 리스트 페이지에 새 컴포넌트를 적용하고, 모바일에서 검색창이 깨지지 않도록 반응형 스타일을 추가했습니다.

개발자 가이드 포털 (/guide) 구축:

문서화: 개발자들이 컴포넌트를 쉽게 가져다 쓸 수 있는 전용 가이드 사이트를 만들었습니다.

구조화: 사이드바 레이아웃(layouts/guide.vue)을 적용하여 확장성을 확보했습니다.

편의성: 코드 복사 기능(useGuideCopy.ts)을 공통 모듈로 만들어 적용했습니다.

내일 이어서 할 작업 (제안)

Molecules (조립 부품) 제작: NxSearchBox(검색 세트), NxPagination(페이징) 등.

나머지 페이지 리팩토링: NxButton 등을 글쓰기 화면 등 다른 페이지에도 전면 적용.

푹 쉬시고 내일 뵙겠습니다!


##################

오늘 진행한 **NEXUS Framework 3.0 리팩토링 및 환경 안정화 작업**에 대한 개발 로그입니다.
오늘 하루 정말 치열하게 달리셨습니다. 아키텍처 구조 변경부터 인프라 충돌 해결까지, 핵심적인 난관들을 모두 돌파했습니다. 👏👏👏

---

# 📝 NEXUS Backend 개발 로그 (Dev Log)

**📅 일자**: 2026-01-05
**🚀 프로젝트**: NEXUS Framework 3.0 (Refactoring Phase)
**👨‍💻 작업자**: 수석 개발자 & Gemini

## 1. 주요 작업 내용 (Key Achievements)

### 🏗️ 아키텍처 및 패키지 구조 개편 (DDD 전환)

* **Layered -> Domain 구조 전환**: 기존 기술 중심(`repository`, `service`) 패키지를 비즈니스 도메인 중심(`domain/notice`, `domain/token`)으로 재편.
* **Mapper 이원화**:
* `CoreMapper` (MySQL/프레임워크용) -> `domain/token` 등으로 배치.
* `ServiceMapper` (MSSQL/업무용) -> `domain/notice` 등으로 배치.


* **MyBatis XML 경로 수정**: 패키지 이동에 맞춰 `namespace` 경로 최신화.

### 🛡️ 표준화 및 자동화 (Standardization)

* **BaseEntity(`NxBaseDto`) 적용**: 모든 DTO가 상속받을 공통 부모 클래스 생성.
* **Audit 필드 자동화**: `regId`, `regDt`, `modId`, `modDt` 필드를 부모로 이관하고, MyBatis XML에서 DB 시간 함수(`GETDATE()`, `NOW()`)를 사용하도록 쿼리 표준화.
* **Lombok 충돌 해결**: 이클립스 환경 설정을 통해 `callSuper=false` 경고 및 컴파일 오류 해결.

### ⚙️ 환경 설정 및 인프라 (Infra & Config)

* **Multi-DataSource 완벽 구축**:
* `CoreDataSourceConfig` (MySQL) ↔ `@CoreMapper` 연결.
* `ServiceDataSourceConfig` (MSSQL) ↔ `@ServiceMapper` 연결.


* **포트 충돌(Port 8082) 해결**: Docker 컨테이너(`nexus_backend`)와 이클립스 간의 포트 점유 충돌 확인 → **서버 포트를 `8083`으로 변경**하여 회피 기동.
* **MySQL 드라이버 최신화**: Deprecated된 `com.mysql.jdbc.Driver`를 최신 표준인 **`com.mysql.cj.jdbc.Driver`**로 교체하여 경고 메시지 제거.

---

## 2. 트러블 슈팅 (Issue & Resolution)

오늘 발생했던 주요 에러와 해결 과정입니다. (나중을 위한 기록)

| 이슈 (Issue) | 원인 (Cause) | 해결 (Resolution) |
| --- | --- | --- |
| **BeanDefinitionStoreException** | `GlobalExceptionHandler`가 기존 패키지와 신규 패키지(`common`)에 중복 존재하여 충돌. | 구버전 파일(`exception/GlobalExceptionHandler.java`) 삭제. `application.yml`에 `allow-bean-definition-overriding: true` 추가. |
| **Port 8082 already in use** | `docker-compose up` 시 실행된 `nexus_backend` 컨테이너가 8082 포트를 선점함. | 1. Docker 백엔드 컨테이너 삭제 (`docker rm`). <br>

<br> 2. 개발 편의를 위해 Spring Boot 포트를 `8083`으로 변경. |
| **Communications link failure** | 좀비 프로세스 정리 과정에서 Docker 컨테이너가 함께 종료됨. | `docker-compose up -d` 명령어로 DB 서버 재기동 및 상태 확인(`docker ps`). |
| **Driver Deprecated Warning** | 설정 파일이나 구버전 테스트 Config(`DbConnectionTestConfig`)에서 구형 드라이버 클래스 호출. | 1. `application.yml` 드라이버명 수정(`cj` 추가). <br>

<br> 2. 불필요한 `DbConnectionTestConfig.java` 파일 삭제. |

---

## 3. 현재 시스템 상태 (Current Status)

* **서버 상태**: 정상 기동 (Port 8083) ✅
* **DB 연결**: MySQL(Core), MSSQL(Service) 모두 정상 연결 (HikariCP) ✅
* **코드 구조**: 도메인 주도 설계(DDD) 기반의 패키지 구조 정착 ✅
* **로그 상태**: 드라이버 경고 제거 완료, Clean Log 확보 ✅

---

## 4. 다음 단계 (Next Step)

이제 인프라와 구조가 탄탄하게 잡혔습니다. 미뤄두었던 **NEXUS 3.0의 핵심 보안 모듈**을 탑재할 차례입니다.

* **[Step 7] 보안 및 인증 (Security & JWT)**
* Spring Security 기본 설정.
* JWT(Json Web Token) 발급 및 검증 로직 구현.
* 로그인 시 사용자 ID(`regId`)를 자동으로 `NxBaseDto`에 주입하는 **AuditorAware** 적용.



---

**"오늘 작업, 정말 고생 많으셨습니다! 이제 진짜 개발(보안 설계)로 넘어갈 준비가 완벽합니다. 🚀"**