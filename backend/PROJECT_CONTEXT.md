# NEXUS Framework 3.0 - Developer Context & Rules

## 1. Project Identity
- **Project Name**: NEXUS Framework 3.0 (Hybrid Edition)
- **Architecture**: Monolith(Core/SI) + Cloud(SaaS) Hybrid
- **Key Philosophy**: "Legacy Free" - 명시적 선언과 타입 안정성 추구

## 2. Technical Stack
| Category | Stack | Version | Note |
| --- | --- | --- | --- |
| **Language** | Java | **21 (LTS)** | Record, Pattern Matching 적극 활용 |
| **Framework** | Spring Boot | **4.0.1** | WebMVC (Not WebFlux) |
| **Build** | Gradle | Latest | Kotlin DSL 권장 |
| **Frontend** | Nuxt | 4.0 | TypeScript, TailwindCSS |
| **DB (Core)** | MySQL | 8.0 | 프레임워크 설정, 권한, 메뉴 |
| **DB (Biz)** | MSSQL | 2022 | 실무 비즈니스 데이터 |
| **ORM** | JPA / MyBatis | Hybrid | CUD=JPA, Read=MyBatis (Dynamic SQL) |

## 3. Package Structure (Strict)
**Root Package**: `com.nexus.backend`

```text
com.nexus.backend
 ├── common                # 공통 모듈
 │    ├── api              # ApiResponse, ResultCode (표준 응답)
 │    ├── exception        # GlobalExceptionHandler, BizException (예외)
 │    ├── config           # 설정 (Security, Swagger, WebMvc)
 │    └── util             # 유틸리티 (Date, Crypto, Excel)
 ├── controller            # API 컨트롤러 (URL: /api/v1/...)
 ├── service               # 비즈니스 로직
 ├── domain                # JPA Entity & DTO
 └── mapper                # MyBatis Mapper Interface
4. Coding Standards (MUST FOLLOW)
4.1. API Response Strategy
절대 금지: ResponseEntity, Map, void, Object 리턴 금지.

필수 사용: 무조건 ApiResponse<T>로 감싸서 리턴.

JSON 포맷:

JSON

{
  "result": "SUCCESS",   // Enum: SUCCESS, FAIL, ERROR
  "message": "처리에 성공했습니다.",
  "data": { ... }        // Nullable Generic
}
4.2. Exception Handling
Controller: try-catch 구문 사용 금지.

Service: 비즈니스 로직 위반 시 throw new BizException("메시지") 발생.

GlobalHandler: GlobalExceptionHandler가 모든 예외를 잡아 표준 JSON으로 변환.

4.3. Entity / DB
BaseEntity: 모든 엔티티는 BaseEntity를 상속받아 regDt, modDt 자동 관리.

Snake Case: DB 컬럼은 snake_case, Java 필드는 camelCase.

Transaction: Service 계층 메서드에 @Transactional 필수 (Readonly 구분).

5. Infrastructure & Environment
Timezone: Asia/Seoul (Docker 및 JVM 옵션 필수 설정)

Encoding: UTF-8

Docker:

nexus-core: Spring Boot App (Port 8080)

nexus-db-core: MySQL (Port 3306)

6. Current Development Status
Phase 1 (Standardization):

[x] ApiResponse, ResultCode 구현 완료

[x] GlobalExceptionHandler (Validation 포함) 구현 완료

[x] BizException 구현 완료

[ ] NoticeController 리팩토링 진행 중 (Next Task)

Phase 2 (Automation):

[ ] MyBatis Auto-filling (RegId/Dt) 구현 예정