# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Spotless is a multi-language code formatter that provides a unified API for formatting code across dozens of languages. It integrates with Gradle, Maven, and SBT build systems. The core philosophy centers around composable `Function<String, String>` transformations that can be chained together.

## Build Commands

### Primary Development Commands
```bash
./gradlew spotlessApply    # Format the codebase (Spotless formats itself)
./gradlew spotlessCheck    # Verify formatting
./gradlew build            # Full build and test
./gradlew test             # Run all tests
./gradlew spotbugsMain     # Static analysis
```

### Module-Specific Testing
```bash
./gradlew :plugin-gradle:test                 # Gradle plugin tests
./gradlew :plugin-maven:test                  # Maven plugin tests
./gradlew :lib-extra:test                     # Runs tests against core library which need 3rd party dependencies
./gradlew :testlib:test                       # Runs tests against the core library (no 3rd party dependencies)
./gradlew :lib:test  # no-op,use `:testlib:test` instead if you need to test :lib 
./gradlew test --tests SpecificTestName  # Run individual test
```

### External Tool Integration Tests
```bash
./gradlew testNpm     # Test npm-based formatters (ESLint, Prettier, etc.)
./gradlew testBlack   # Test Python Black formatter
./gradlew testClang   # Test clang-format
./gradlew testGofmt   # Test Go formatter
./gradlew testShfmt   # Test shell formatter
./gradlew testBuf     # Test Protocol Buffer formatter
```

## Architecture

### Core Concepts
- **FormatterStep**: Fundamental unit implementing `String format(String rawUnix, File file)` with serialization/equality
- **Formatter**: Combines encoding, line endings policy, and FormatterStep chain
- **JarState**: Manages external dependencies via reflection to avoid classpath conflicts

### Module Structure
- **`lib/`**: Core library with zero dependencies (hard requirement) - contains all fundamental infrastructure
- **`lib-extra/`**: Optional formatters requiring external dependencies (Eclipse-based formatters)
- **`testlib/`**: Shared test utilities and resources for all formatters
- **`plugin-gradle/`**: Gradle plugin integration with SpotlessExtension DSL
- **`plugin-maven/`**: Maven plugin following Maven conventions

### Creating New FormatterStep
1. Class name must end in "Step"
2. Provide static `create()` method returning FormatterStep
3. Use `FormatterStep.create(name, state, stateToFormatter)` pattern
4. Test with `StepHarness` or `StepHarnessWithFile` from testlib
5. Include equality test using `StepEqualityTester`

### External Dependencies
- Keep `lib/` dependency-free - use `lib-extra/` for external dependencies
- Use `JarState` pattern to resolve dependencies at runtime via reflection
- Support both reflection and compile-only source set approaches

## Testing Patterns

Test resources are located in `testlib/src/main/resources/` organized by formatter type. Use before/after file pairs for formatter testing. The project uses `selfie` for snapshot testing and has comprehensive serialization roundtrip testing.

Special external tool tests may not run if tools aren't available in the environment - these are tagged accordingly.

## Code Quality

- Spotless formats its own codebase using its formatters
- SpotBugs runs on core library with strict LOW confidence level
- Gradle configuration cache and build cache are supported
- All FormatterStep implementations must support proper serialization and equality
