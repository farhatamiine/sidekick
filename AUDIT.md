# Sidekick – Audit & TODO

State checked: commit `5f1b057` ("finishing the test tomorrow").

---

## 1. Fix first (the build is broken)

- [ ] **`DeleteExpenseServiceTest` doesn't compile.** It calls `service.deleteExpense()` with no argument.
  Until this is fixed, `mvn test` runs nothing. Pass `expense.id()` and add a `verify(...)` (see section 3).
- [ ] **`ExpenseControllerTest` can't start.** The controller needs 4 use cases, but only
  `CreateExpenseUseCase` and `GetExpenseUseCase` are `@MockitoBean`. Add `@MockitoBean` for
  `UpdateExpenseUseCase` and `DeleteExpenseUseCase`, or every test in the class fails.
- [ ] **`SIdekickApplicationTests.contextLoads` needs a real Postgres** at `localhost:5432`.
  Without it, it fails. Add Testcontainers so the test runs anywhere (Docker is installed on this PC):
  - pom, all `<scope>test</scope>`, no versions needed (Boot manages them):
    `org.springframework.boot:spring-boot-testcontainers`,
    `org.testcontainers:testcontainers-junit-jupiter`,
    `org.testcontainers:testcontainers-postgresql`
  - a `@TestConfiguration` with a `@Bean @ServiceConnection PostgreSQLContainer`
    (`org.testcontainers.postgresql.PostgreSQLContainer`, e.g. image `postgres:17-alpine`), then `@Import` it in your tests.
  - Spring Boot 4 package names: `@DataJpaTest` is in `org.springframework.boot.data.jpa.test.autoconfigure`,
    `@AutoConfigureTestDatabase` is in `org.springframework.boot.jdbc.test.autoconfigure`
    (use `replace = NONE` so it keeps the container).

---

## 2. Bugs & code issues

| # | Where | Problem | Fix |
|---|---|---|---|
| 1 | `Money` | Checks `amount > 0` **before** rounding to 2 decimals. `0.001` passes, becomes `0.00`, and then the DB `CHECK (amount > 0)` fails. | Round first, then check. |
| 2 | `ExpenseController` | Unknown currency (`"XYZ"`) → `Currency.getInstance` throws `IllegalArgumentException` → no handler → **500**. | Catch it in the controller, or add a handler that returns 400. |
| 3 | `Expense.validate` | `spentOn == null` gives the message *"can't be in the future"*. | Separate null check with message "Date is required". |
| 4 | `Expense` | The public canonical record constructor skips all validation (tests already use it). The private constructor with swapped `description/category` order is confusing. | Use the factory methods only. Consider making it a class, or document that `new Expense(...)` is for rehydration only. |
| 5 | `Expense.validate` | Amount check is redundant: `Money` already refuses ≤ 0. | Keep only the null check. |
| 6 | `UpdateExpenseCommand` | `expenseId` isn't null-checked. | `Objects.requireNonNull(expenseId, ...)`. |
| 7 | `DeleteExpenseService` | Not `@Transactional`, so the load and the delete are not atomic. | Add `@Transactional`. |
| 8 | Services | Use `jakarta.transaction.Transactional`. Works, but has no `readOnly`. | Switch to Spring's `org.springframework.transaction.annotation.Transactional`; use `readOnly = true` on `GetExpenseService` (and put it on `getExpenses()` too). |
| 9 | `ClockConfig` | App clock is `Europe/Paris`, tests use UTC. "Today" differs around midnight. | Fine if intended. Test with a Paris clock in at least one case. |
| 10 | `application.properties` | DB password hard-coded. | Use `${DB_PASSWORD}` env vars. Keep defaults only for local dev. |
| 11 | `pom.xml` | `java.version` is **17** but the PC has JDK **25**. | Pick one (25 if you want the new features). |
| 12 | Cleanup | Unused imports: `SaveExpensePort` and `Clock` in `DeleteExpenseService`, `Optional` in `GetExpenseUseCase`, `Expense` in `DeleteExpensePort`, `Objects` in `Money`, `Optional` in `UpdateExpenseService`. | Remove. |
| 13 | `ExpenseRequest` vs domain | Description max 255 is only checked at the web layer, not in `Expense`. | Optional: add the length rule to the domain too. |

---

## 3. Tests you need

Short list, one line each on what to check. ✅ = already there.

### `ExpenseTest` (domain, plain JUnit)
- ✅ rejects a future date
- [ ] **createsValidExpense**: fields are set, description is trimmed (`"  Lunch "` → `"Lunch"`), `createdAt == clock.instant()`, id not null.
- [ ] **acceptsToday**: `spentOn` = today per the clock is OK (edge of the future-date rule).
- [ ] **rejectsBlankDescription**: `null`, `""` and `"   "` → `DomainValidationException`.
- [ ] **rejectsNullCategory** → `DomainValidationException`.
- [ ] **rejectsNullSpentOn** → `DomainValidationException`.
- [ ] **updateKeepsIdAndCreatedAt**: `update(...)` changes amount/description/category/date but keeps `id` and `createdAt`.
- [ ] **updateValidatesToo**: `update(...)` with a future date → `DomainValidationException`.

### `MoneyTest` (new file)
- [ ] **rejectsZeroAndNegative**: `0` and `-1` → exception. Move `rejectsNonPositiveAmount` here from `ExpenseTest`.
- [ ] **rejectsNulls**: null amount or null currency → exception.
- [ ] **roundsToTwoDecimals**: `89.0` → `89.00`, `12.345` → `12.34` (HALF_EVEN).
- [ ] **rejectsAmountThatRoundsToZero**: `0.001` → exception. **Fails today** (bug #1).

### `CreateExpenseServiceTest`
- ✅ saves the new expense
- [ ] **doesNotSaveInvalidExpense**: future date → exception and `verify(saveExpensePort, never()).save(any())`.

### `GetExpenseServiceTest`
- ✅ found / not found / list
- [ ] **returnsEmptyList** when the port returns nothing.

### `UpdateExpenseServiceTest` (empty right now)
- [ ] **updatesExistingExpense**: port returns an expense, call update, then check: new values returned, same id, `save` called with the updated expense.
- [ ] **throwsWhenNotFound**: port returns `Optional.empty()` → `ExpenseNotFoundException`, `save` never called.
- [ ] **doesNotSaveInvalidUpdate**: future date → `DomainValidationException`, `save` never called.

### `DeleteExpenseServiceTest`
- [ ] **deletesExistingExpense**: fix the compile error, then `verify(deleteExpensePort).deleteExpense(id)`.
- [ ] **throwsWhenNotFound** → `ExpenseNotFoundException`, `deleteExpense` never called.

### `ExpenseControllerTest` (`@WebMvcTest`, mock the 4 use cases)
- ✅ 404 when missing, ✅ 400 on bad UUID
- [ ] **createReturns201**: POST valid JSON → `201`, `Location: /api/expenses/{id}`, body has the id. (Replace the empty `create()`.)
- [ ] **createReturns400OnInvalidBody**: e.g. missing amount or blank description → `400`. One or two cases are enough.
- [ ] **createReturns400OnFutureDate**: use case throws `DomainValidationException` → `400` with the message in `detail`.
- [ ] **createReturns400OnUnknownCurrency**: `"XYZ"` → expect `400`. **Fails today** (bug #2, returns 500).
- [ ] **listReturns200**: GET `/api/expenses` → `200` with a JSON array.
- [ ] **updateReturns200** and **updateReturns404** when the id is unknown.
- [ ] **deleteReturns204** and **deleteReturns404** when the id is unknown.

### `ExpensePersistenceAdapterTest` (new, `@DataJpaTest` + Testcontainers Postgres)
- [ ] **saveThenLoad**: save an expense, `loadById` returns the same values (also tests `ExpenseMapper` and the Flyway migration).
- [ ] **loadUnknownReturnsEmpty** → `Optional.empty()`.
- [ ] **loadAllAndDelete**: save 2, `loadAllExpenses` gives 2; delete 1, then 1 is left.

### `SIdekickApplicationTests`
- [ ] Keep `contextLoads`, but on Testcontainers (see section 1).
- [ ] Optional: **modulesAreValid** with `ApplicationModules.of(SidekickApplication.class).verify()`. You already have Spring Modulith, so this is a free architecture check.

---

## 4. Order to do it

1. Fix the 2 broken tests (section 1) so `./mvnw test` runs.
2. Add Testcontainers.
3. Write the tests in section 3. The two marked **Fails today** show bugs #1 and #2.
4. Fix bugs #1 and #2, then the rest of section 2.
