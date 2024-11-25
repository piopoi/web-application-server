# Web Application Server developed in Java

## Description

이 프로젝트는 **외부 네트워크 라이브러리 없이, Java로 Web Application Server를 개발**하는 프로젝트입니다.

총 3가지 다른 방식의 서버를 각각의 모듈로 개발할 계획입니다:

1. 동기, 블로킹 방식의 멀티스레딩 WAS
2. 동기, 논블로킹 방식의 멀티스레딩 WAS
3. 비동기, 논블로킹 방식의 멀티스레딩 WAS

이 프로젝트는 동기/비동기 및 블로킹/논블로킹 방식에 대해 더 깊은 이해를 얻기 위해 진행됩니다.

## Requirements

- JDK 17 & 21
- Gradle 8.10

## Specifications
- 동기/비동기, 블로킹/논블로킹에 대한 이해를 위한 프로젝트이므로 `GET` 요청에 대한 기능만 구현한다.
  - `GET` 메서드 요청을 받고 응답을 반환하는 기능만 구현한다.
- Java 기본 라이브러리 외 외부 네트워크 라이브러리 사용을 금지한다.
- 서버의 설정 값은 별도의 설정파일로 관리한다.
  - Production 환경 설정: `was-sync-blocking/src/main/resources/server.yaml`
  - Test 환경 설정: `was-sync-blocking/src/test/resources/server.yaml`
- 모든 요청을 미리 설정된 보안 규칙에 따라 검사한다.
  - 규칙 추가 방법
    - `SecurityRule` 인터페이스를 구현한 클래스를 작성만 해두면, 
    - Reflection 라이브러리를 이용하여 런타임에 자동으로 보안 규칙 리스트에 추가한다.
  - 규칙 위반 시, 상태코드 `400 Bad Request`를 반환한다.
  - 현재 설정된 규칙
    - HTTP_ROOT 디렉터리의 상위 디렉터리에 접근은 거부한다.
    - 설정된 확장자의 파일에 대한 요청만 허용한다.
    - 설정된 HTTP Method에 대한 요청만 허용한다.
