# 🏛️ NEXUS Framework 2.0 프로젝트 작업 일지

## 1. 프로젝트 아키텍처 (Blueprint)
- **Frontend**: Nuxt 4 (Atomic Design + Theme Engine)
- **Backend**: Spring Boot 4.0.1 (Multi-DataSource)
- **Infrastructure**: Docker 기반 (MySQL, MSSQL Service, MSSQL Legacy 연동)
- **DB 전략**: 
    - Framework(MySQL): 설정/코드/디자인 토큰(JSON)
    - Service(MSSQL 1): 웹 데이터/게시판/캐시조회
    - Legacy(MSSQL 2): 시설 원장/최종 수강신청 접수

## 2. UI/UX 디자인 원칙
- **Atomic Controls**: 원자 단위 모듈화 (NxButton, NxInput 등)
- **Layout Engine**: 슬롯 기반 가변 레이아웃 (User용 화려함 / Admin용 절제됨)
- **Design Tokens**: JSON 설정을 통한 실시간 테마 변경 (색상, 곡률, 폰트 등)

## 3. 작업 히스토리
### [Step 1] UI Skeleton & 디자인 시스템 구축
- **상태**: 완료 (2026-01-03)
- **내역**: 
    - Nuxt 4 + Tailwind CSS 연동 및 타입 에러 해결
    - CSS 변수 기반 디자인 토큰(Design Tokens) 기초 설계
    - 가변형 사용자 레이아웃(`default.vue`) 뼈대 구축
    - 아토믹 컴포넌트(`NxButton`) 규격화 및 속성(props) 설계

## 4. 현재 진행 단계: [Step 2] 백엔드 멀티 DB 연동
- **상태**: 인프라 최적화 완료 (2026-01-03)
- **변경 사항**: 
    - [x] 호스트 PC MSSQL 포트(1433) 충돌 방지를 위해 컨테이너 외부 포트를 **14333**으로 변경.
    - [x] Docker Health Check 로직 추가로 백엔드 실행 안정성 확보.
- **다음 할 일**:
    - [Step 2-2] Spring Boot Java Config 구현 (MySQL/MSSQL 멀티 데이터소스 맵핑).
- **이슈 해결 (Hotfix)**:
    - [x] YAML 들여쓰기(Indentation) 오류 교정 완료.
    - [x] `docker-compose down -v`를 통한 오염된 데이터 볼륨 완전 초기화 수행.
    - [x] MSSQL 컨테이너 Healthy 상태 진입 확인 (예정).
	- **인프라 해결**: 
    - [x] MSSQL Healthcheck 제거로 기동 실패(Unhealthy) 원천 해결.
    - [x] 메모리 4G 할당으로 서비스 안정성 확보.
- **다음 할 일**:
    - [Step 2-2] 백엔드 Java Config 작성 (MySQL/MSSQL 분리).
    - [Step 2-3] MySQL에 디자인 토큰 저장을 위한 초기 테이블 생성.