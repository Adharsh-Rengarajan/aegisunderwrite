# AegisUnderwrite – Explainable Auto Insurance Underwriting Engine

AegisUnderwrite is a backend-focused auto insurance underwriting system designed to model real-world underwriting decision engines used by insurance companies. The application evaluates policy applications using deterministic business rules and quantitative risk scoring to produce clear, explainable underwriting decisions.

The system emphasizes correctness, transparency, and auditability, aligning with regulatory and compliance expectations common in insurance and financial services domains.

---

## Problem Statement

Insurance underwriting systems must evaluate applicant risk accurately while maintaining explainability for regulatory compliance and internal review. Many decision engines become opaque or tightly coupled, making them difficult to audit, extend, or reason about.

AegisUnderwrite addresses this by separating risk scoring, rule evaluation, and decision generation into clearly defined components, producing decisions that are both deterministic and explainable.

---

## Key Features

- Rule-based underwriting decisions (Approve, Refer, Decline)
- Quantitative risk scoring with weighted factors
- Priority-based hard and soft underwriting rules
- Explainable decision output with human-readable reasons
- Full audit trail for applications, scores, and decisions
- Configurable and extensible underwriting logic
- Clean domain-driven backend design

---

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Data JPA (Hibernate)
- PostgreSQL
- Flyway (database migrations)
- Maven
- RESTful APIs

---

## High-Level Architecture

Client / API Consumer
|
v
Underwriting REST API
|
v
Application Intake Service
|
v
Risk Scoring Engine
|
v
Rule Evaluation Engine
|
v
Decision & Explanation Builder
|
v
PostgreSQL Database


---

## Domain Overview (Auto Insurance)

### Core Inputs
- Driver profile (age, credit score, license history)
- Vehicle details (make, model, age, usage)
- Policy context (state, coverage type)

### Core Outputs
- Risk score (0–1000)
- Risk band (LOW, MEDIUM, HIGH)
- Underwriting decision
- Explanation and audit log

---

## Underwriting Decision Flow

1. Application submission
2. Risk score calculation using weighted factors
3. Risk band classification
4. Rule evaluation in priority order
5. Decision aggregation and explanation generation
6. Decision persistence for audit and traceability

---

## Sample Decision Output

```json
{
  "applicationId": "b7a1e3c2",
  "decision": "REFER",
  "riskScore": 695,
  "riskBand": "MEDIUM",
  "explanations": [
    "Driver license history less than 2 years",
    "Vehicle value exceeds standard underwriting threshold"
  ]
}
