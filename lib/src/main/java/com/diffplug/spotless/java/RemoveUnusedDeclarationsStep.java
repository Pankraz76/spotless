/*
 * Copyright 2016-2024 DiffPlug
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diffplug.spotless.java;

import com.diffplug.spotless.FormatterStep;
import com.diffplug.spotless.Provisioner;

import java.util.List;
import java.util.Objects;

/** Uses google-java-format or cleanthat.UnnecessaryImport, but only to remove unused imports. */
public interface RemoveUnusedDeclarationsStep {
	String NAME = "removeUnusedImports";
	String DEFAULT_FORMATTER = "palantir-java-format";
	String CLEANTHAT = "cleanthat-javaparser-unnecessaryimport";

	// https://github.com/solven-eu/cleanthat/blob/master/java/src/main/java/eu/solven/cleanthat/engine/java/refactorer/mutators/UnnecessaryImport.java
	String CLEANTHAT_MUTATOR = "UnnecessaryImport";

	static String defaultFormatter() {
		return DEFAULT_FORMATTER;
	}

	static FormatterStep create() {
		return create(DEFAULT_FORMATTER, null);
	}

	static FormatterStep create(String unusedImportRemover, Provisioner provisioner) {
		Objects.requireNonNull(provisioner, "provisioner");
		switch (unusedImportRemover) {
			case DEFAULT_FORMATTER:
				return GoogleJavaFormatStep.createRemoveUnusedImportsOnly(provisioner);
			case CLEANTHAT:
				return CleanthatJavaStep.createWithStepName(NAME, CleanthatJavaStep.defaultGroupArtifact(),
					CleanthatJavaStep.defaultVersion(), "99.9", List.of(CLEANTHAT_MUTATOR),
					List.of(), false, provisioner);
			default:
				throw new IllegalArgumentException("Invalid unusedImportRemover: " + unusedImportRemover);
		}
	}
}
