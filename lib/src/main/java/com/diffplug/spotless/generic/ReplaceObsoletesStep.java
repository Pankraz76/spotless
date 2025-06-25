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
package com.diffplug.spotless.generic;

import java.io.File;

import com.diffplug.spotless.FormatterStep;

public final class ReplaceObsoletesStep implements FormatterStep {
	private ReplaceObsoletesStep() {}

	public static FormatterStep forJava() {
		return new ReplaceObsoletesStep();
	}

	@Override
	public String getName() {
		return "replaceObsoletes";
	}

	@Override
	public String format(String rawUnix, File file) throws Exception {
		String result = rawUnix;
		// Replace System.getProperty("line.separator") with System.lineSeparator()
		result = result.replace(
			"System.getProperty(\"line.separator\")",
			"System.lineSeparator()");

		// Remove = false from boolean fields
		result = result.replaceAll(
			"(public\\s+boolean\\s+\\w+)\\s*=\\s*false",
			"$1");

		// Remove = null from reference fields
		result = result.replaceAll(
			"(public\\s+(?:String|\\w+)\\s+\\w+)\\s*=\\s*null",
			"$1");

		// Remove = 0 from numeric fields (int, long, short, byte)
		result = result.replaceAll(
			"(public\\s+(?:int|long|short|byte)\\s+\\w+)\\s*=\\s*0(?:L)?\\s*;",
			"$1;");

		// Remove = 0.0 from floating-point fields (float, double)
		result = result.replaceAll(
			"(public\\s+(?:float|double)\\s+\\w+)\\s*=\\s*0(?:\\.0)?(?:f|d)?\\s*;",
			"$1;");

		return result;
	}

	@Override
	public void close() throws Exception {}
}
