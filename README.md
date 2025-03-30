# 기술 스택
- JDK: Java 17
- Framework SpringBoot 3.4.4
- DB: H2
- ORM: JPA
- Build Tool: Gradle


## ✅ 주요 기능

### 👩‍💼 관리자 기능 (`/api/v1/admin`)

- DELETE /chat: 채팅 삭제
- GET /feedback: 피드백 목록 조회 (페이징, 정렬, 타입 필터링 포함)
- PUT /feedback: 피드백 상태 업데이트
- GET /activity: 전체 유저 활동 요약 리포트
- GET /report/chat: 전체 채팅 내역 CSV 다운로드

### 🔐 인증 기능 (/api/v1/auth)
- POST /signup: 이메일 회원가입
- POST /login: 이메일 로그인 및 JWT 발급

### 💬 채팅 기능 (/api/v1/chat)
- GET /: 유저의 전체 채팅 목록 조회 (페이징, 정렬 지원)
- DELETE /: 유저가 생성한 채팅 삭제

### 🗣️ 피드백 기능 (/api/v1/feedback)
- POST /: 유저가 특정 메시지에 대한 피드백 등록
- GET /: 유저의 피드백 목록 조회 (페이징, 정렬, 타입 필터링 포함)

### 📨 메시지 기능 (/api/v1/message)
- POST /: 메시지 생성 (GPT 응답 포함)
- POST /stream: 메시지 스트리밍 응답 (SSE)
- GET /: 유저가 보낸 전체 메시지 조회 (페이징)
