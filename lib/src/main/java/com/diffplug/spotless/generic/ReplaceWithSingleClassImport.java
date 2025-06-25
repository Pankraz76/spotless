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
import java.io.Serializable;
import java.util.Objects;

import com.diffplug.spotless.FormatterFunc;
import com.diffplug.spotless.FormatterStep;
import com.diffplug.spotless.java.ImportOrderStep;

import javax.annotation.Nullable;

public final class ReplaceWithSingleClassImport implements FormatterStep {


	private ReplaceWithSingleClassImport() {
	}

	public static FormatterStep forJava() {
		return new ReplaceWithSingleClassImport();
	}

	@Override
	public String getName() {
		return "";
	}

	@Nullable
	@Override
	public String format(String rawUnix, File file) throws Exception {
		return "";
	}

	@Override
	public void close() throws Exception {

	}
}
