

# UserService Microservice – MVP Setup (2025-08-05)

**Author:** Jeet Solanki

## Overview

This document outlines the initial setup and architecture of the UserService microservice for the Brainz project.
It explains what has been implemented, how the service is organized, current capabilities, and outstanding tasks.

---

## ✅ What I Did

### 1. **Project Structure**

Created a modular Java Spring Boot project with the following packages:

* `config/` – Security and app configuration (`SecurityConfig`, CORS, etc.)
* `controller/` – REST API endpoints
* `dto/` – Data Transfer Objects for requests/responses
* `entity/` – JPA entities (e.g., User, Role)
* `exception/` – (Reserved for future custom exceptions)
* `repository/` – Spring Data JPA Repos
* `security/` – (Reserved for future use: JWT filters, custom auth logic)
* `service/` – Business logic layer (UserServiceImpl)
* `util/` – Utility classes

---

### 2. **Environment Configuration**

* Split config files:

  * `application.properties` (shared config)
  * `application-dev.properties` (PostgreSQL, dev mode)
  * `application-test.properties` (H2, test profile)
* JWT secret is **externalized via environment variable** `JWT_SECRET`.

---

### 3. **Security Configuration**

✅ Implemented secure API access using Spring Security:

* Stateless JWT-based authentication.
* Public endpoints:

  * `/api/user/register`
  * `/api/user/validate`
  * `/api/user/search`
  * `/swagger-ui/**`, `/v3/api-docs/**`, `/actuator/**`
* All other endpoints are **protected** by default.
* Enabled method-level security with `@EnableMethodSecurity(prePostEnabled = true)` for `@PreAuthorize` support.

✅ Global CORS configuration:

``` java
registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
```

✅ Passwords are encoded using `BCryptPasswordEncoder`.

---

### 4. **JWT Usage in Endpoints**

* No user ID or username is explicitly passed from the client.
* Extracted from JWT token claims:

```java
String username = (String) token.getToken().getClaims().get("userName");
Long userId = Long.valueOf(token.getToken().getSubject());
```

---

### 5. **CI/CD**

* GitHub Actions pipeline:

  * Runs on `test` profile using H2 in-memory DB.
  * Performs test, build, and Docker image creation.
  * Safe for isolated and reproducible deployments.

---

## ✅ What We Can Do Now

* Register and validate users using secure, token-based APIs.
* Store users in DB (with encoded password and roles).
* Authenticate via JWT, validate access for protected routes.
* Use token claims for user ID/username (no input from client needed).
* Enable Swagger/OpenAPI for API documentation.
* Support future pagination, Elasticsearch, or Trie-based search.

---

## 🛠 What’s Needed for Production

* Securely set `JWT_SECRET` in environment or secrets manager.
* Add global error handling (via `@ControllerAdvice`).
* Enable request/response logging and correlation IDs.
* Add more tests: unit, integration, and security-focused.
* Use Spring Actuator for health monitoring.
* \[Optional] Add rate-limiting, audit logging, and tracing (via Zipkin/OpenTelemetry).
* Harden JWT validation (e.g., expiration checks, issuer/audience validation).

---

## 📌 What Remains / Next Steps

* Write full test coverage.
* Add new features: password reset, profile update, context-saving for LLM.
* Integrate with LLM-core (via async WebClient calls).
* Add caching and optimization on search endpoints.
* Implement exception handling and custom error response DTOs.
* Log user actions for audit purposes.
* Write additional docs per milestone (e.g., `2025-08-15-profile-api.md`).

---

## 📘 How to Use This Documentation

* Keep this file under `docs/` as a permanent snapshot of the MVP setup.
* For future changes/features, create new files like:

  * `2025-08-15-user-profile.md`
  * `2025-08-20-LLM-integration.md`
* Sync with internal wiki or knowledge base when needed.

---

