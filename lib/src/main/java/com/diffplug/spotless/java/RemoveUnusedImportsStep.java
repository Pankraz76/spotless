/*
 * Copyright 2016-2025 DiffPlug
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

import java.util.List;
import java.util.Objects;

import com.diffplug.spotless.FormatterStep;
import com.diffplug.spotless.Provisioner;

import static com.diffplug.spotless.java.RemoveUnusedDeclarationsStep.CLEANTHAT;

/** Provides utility methods for removing unused imports using different formatters. */
public interface RemoveUnusedImportsStep {
	String NAME = "removeUnusedImports";
	String DEFAULT_FORMATTER = "palantir-java-format";
	String CLEANTHAT_MUTATOR = "UnnecessaryImport";

	/**
	 * @return the default formatter name for removing unused imports
	 */
	static String defaultFormatter() {
		return DEFAULT_FORMATTER;
	}

	/**
	 * Creates a FormatterStep using the default import remover.
	 * @param provisioner the provisioner for required dependencies
	 * @return configured FormatterStep
	 */
	static FormatterStep create(Provisioner provisioner) {
		return create(DEFAULT_FORMATTER, provisioner);
	}

	/**
	 * Creates a FormatterStep using the specified unused import remover.
	 * @param unusedImportRemover the import remover to use
	 * @param provisioner the provisioner for required dependencies
	 * @return configured FormatterStep
	 * @throws IllegalArgumentException if the unusedImportRemover is invalid
	 * @throws NullPointerException if provisioner is null
	 */
	static FormatterStep create(String unusedImportRemover, Provisioner provisioner) {
		Objects.requireNonNull(provisioner, "provisioner");
		switch (unusedImportRemover) {
		case DEFAULT_FORMATTER:
			return GoogleJavaFormatStep.createRemoveUnusedImportsOnly(provisioner);
		case CLEANTHAT:
			return CleanthatJavaStep.createWithStepName(NAME, CleanthatJavaStep.defaultGroupArtifact(), CleanthatJavaStep.defaultVersion(), "99.9", List.of(CLEANTHAT_MUTATOR), List.of(), false, provisioner);
		default:
			throw new IllegalArgumentException("Invalid unusedImportRemover: " + unusedImportRemover);
		}
	}
}
