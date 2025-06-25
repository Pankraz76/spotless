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
package com.diffplug.spotless.maven.java;

import com.diffplug.spotless.maven.MavenIntegrationHarness;
import org.junit.jupiter.api.Test;

class ReplaceObsoletesStepTest extends MavenIntegrationHarness {
	@Test
	void testSortPomCfg() throws Exception {
		writePomWithJavaSteps("<replaceObsoletes/>");

		String path = "src/main/java/test.java";
		setFile(path).toResource("java/replaceobsoletes/SortPomCfgPre.test");
		mavenRunner().withArguments("spotless:apply").runNoError();
		assertFile(path).sameAsResource("java/replaceobsoletes/SortPomCfgPost.test");
	}

	@Test
	void testSystemLineSeparator() throws Exception {
		writePomWithJavaSteps("<replaceObsoletes/>");

		String path = "src/main/java/test.java";
		setFile(path).toResource("java/replaceobsoletes/SystemLineSeparatorPre.test");
		mavenRunner().withArguments("spotless:apply").runNoError();
		assertFile(path).sameAsResource("java/replaceobsoletes/SystemLineSeparatorPost.test");
	}

	@Test
	void testBooleanInitializers() throws Exception {
		writePomWithJavaSteps("<replaceObsoletes/>");

		String path = "src/main/java/test.java";
		setFile(path).toResource("java/replaceobsoletes/BooleanInitializersPre.test");
		mavenRunner().withArguments("spotless:apply").runNoError();
		assertFile(path).sameAsResource("java/replaceobsoletes/BooleanInitializersPost.test");
	}

	@Test
	void testNullInitializers() throws Exception {
		writePomWithJavaSteps("<replaceObsoletes/>");

		String path = "src/main/java/test.java";
		setFile(path).toResource("java/replaceobsoletes/NullInitializersPre.test");
		mavenRunner().withArguments("spotless:apply").runNoError();
		assertFile(path).sameAsResource("java/replaceobsoletes/NullInitializersPost.test");
	}

}
