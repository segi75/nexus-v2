NEXUS Framework 2.0 프로젝트의 일관된 관리를 위해, 중복된 섹션을 통합하고 백엔드와 프런트엔드의 구조를 명확히 구분하여 **`NEXUS_WORKLOG.md`** 최종본을 정리해 드립니다.

---

# 🏛️ NEXUS Framework 2.0 프로젝트 작업 일지

## 1. 프로젝트 아키텍처 (Blueprint)

* **Frontend**: Nuxt 4 (Atomic Design + Theme Engine)
* **Backend**: Spring Boot 4.0.1 (Multi-DataSource)
* **Infrastructure**: Docker (MySQL 8.0, MSSQL 2022 x 2)
* **DB 전략**:
* **Core (MySQL)**: 프레임워크 설정, 코드 관리, 디자인 토큰 (JSON)
* **Service (MSSQL 1)**: 실 서비스 데이터, 게시판, 캐시 데이터
* **Legacy (MSSQL 2)**: 시설 원장 데이터, 최종 수강신청 접수



---

## 2. 개발 환경 및 가이드라인

### 2-1. 시스템 환경

* **IDE**: Eclipse IDE for Enterprise Java and Web Developers
* **Language**: Java 21 / Build Tool: Gradle / GitHub: `nexus-v2`

### 2-2. Backend 구조 가이드 (`src/main/java`)

* **Domain**: `com.nexus.backend.domain.token` (토큰 VO)
* **Mapper Interface**: `com.nexus.backend.repository.core` (MySQL 전용)
* **Controller**: `com.nexus.backend.controller` (API 컨트롤러)
* **MyBatis XML**: `src/main/resources/mapper/core/` (SQL 파일)

### 2-3. Frontend 구조 가이드

* **Composables**: `composables/useNexusTheme.ts` (테마 로드 및 CSS 변수 주입)
* **Atomic Components**: `components/atoms/` (디자인 토큰 참조 UI)
* **Tailwind Integration**: `tailwind.config.ts` 내 `--nx-` 계열 변수 매핑

---

## 3. 작업 히스토리

### [Step 1] UI Skeleton & 디자인 시스템 (2026-01-03)

* ✅ Nuxt 4 + Tailwind CSS 기반 아토믹 컴포넌트(`NxButton`) 규격화 완료.

### [Step 2] 인프라 및 멀티 DB 연동 (2026-01-04)

* ✅ Docker 기반 Multi-DB 구축 및 GitHub 연동 완료.
* ✅ `application.yml` 환경 변수 기반 멀티 데이터소스 및 `log4jdbc` 구축 완료.
* ✅ **Troubleshooting**:
* WSL 2 Docker Integration 활성화 이슈 해결.
* Spring Boot 최신 버전 규격에 따른 `spring.servlet.encoding` 경로 수정.
* MySQL 드라이버 최신화(`com.mysql.cj.jdbc.Driver`).
* MSSQL 접속 시 '서버 인증서 신뢰' 옵션 설정 및 `nexus_service` DB 생성.



### [Step 3] 디자인 토큰 시스템 구축 (2026-01-04)

* ✅ MySQL 내 `NX_DESIGN_TOKEN` 테이블 설계 및 초기 JSON 데이터 적재 완료.
* ✅ `NxDesignToken` 도메인 및 `TokenMapper` (Core 전용) 구현 완료.
* ✅ `TokenController` 구현 및 `/api/v1/tokens/{tokenId}` 엔드포인트 확보.
* ✅ **Troubleshooting**: 이클립스 컴파일러 설정 내 `-parameters` 옵션 미활성화로 인한 리플렉션 오류 수정.

---

## 4. 현재 진행 단계 및 계획


### [Step 4] 프런트엔드 테마 엔진 및 백엔드 연동 (2026-01-04)
- **상태**: ✅ 완료 (Success)
- **성과**:
    - Nuxt 4 디렉토리 구조 최적화 (`srcDir: 'app'`) 및 TypeScript 설정 완비.
    - `useNexusTheme` 컴포저블을 통해 백엔드 API(`/api/v1/tokens`) 호출 성공.
    - JSON 데이터를 파싱하여 CSS Variable(`--nx-primary` 등)로 실시간 UI 반영 확인.
- **의의**: 프런트엔드와 백엔드 간의 첫 번째 완전한 통신 성공.
---
### [Step 5-1] Service DB(MSSQL) 데이터 조회 성공 (2026-01-04)
- **결과**: `[{"noticeId":2, "title":"정기 점검 공지", ...}]` JSON 응답 확인.
- **해결**: `NoticeMapper.xml` 파일 위치를 `resources/mapper/service/`로 이동하여 설정과 일치시킴.
- **상태**: ✅ Backend(Service DB) 연동 완료.

### [Step 5-2] 프런트엔드 리스트 화면 구현 (2026-01-04)
- **성과**: 
    - `app.vue`에 레이아웃(Header/Main) 구조 적용 및 `<NuxtPage />` 배치.
    - `pages/notice/index.vue`를 통해 공지사항 리스트 렌더링 성공.
    - CSS Variable Fallback 설정을 통해 테마 로딩 전 깜빡임(FOUC) 방지.
- **상태**: ✅ UI/UX 기본 구조 및 데이터 바인딩 완료.
### [Step 5-3] 공지사항 상세 조회(Detail) 구현 (2026-01-04)
- **Backend**:
    - `NoticeMapper`에 `getNoticeDetail` 쿼리(WHERE NOTICE_ID = #{id}) 추가.
    - `NoticeController`에 상세 조회 API(`GET /api/v1/notices/{id}`) 구현.
- **Frontend**:
    - 동적 라우팅 페이지 `pages/notice/[id].vue` 생성.
    - 리스트(`index.vue`) 클릭 시 `MapsTo`를 통한 라우팅 연결.
- **결과**: ✅ 공지사항 목록에서 카드를 클릭하여 상세 내용을 확인하고 복귀하는 흐름 완성.