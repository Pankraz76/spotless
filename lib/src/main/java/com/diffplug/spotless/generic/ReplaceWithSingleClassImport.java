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

import javax.annotation.Nullable;

public final class ReplaceWithSingleClassImport implements FormatterStep {
	private static final long serialVersionUID = 1L;
	private final State state;

	private ReplaceWithSingleClassImport(State state) {
		this.state = state;
	}

	public static FormatterStep create(String target, String replacement) {
		Objects.requireNonNull(target, "target");
		Objects.requireNonNull(replacement, "replacement");
		return new ReplaceWithSingleClassImport(new State(target, replacement));
	}

	@Override
	public String getName() {
		return "replaceWithSingleClassImport";
	}

	@Nullable
	@Override
	public String format(String rawUnix, File file) throws Exception {
		Objects.requireNonNull(rawUnix, "rawUnix");
		Objects.requireNonNull(file, "file");
		return state.toFormatter().apply(rawUnix);
	}

	@Override
	public void close() throws Exception {
		// No resources to close
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ReplaceWithSingleClassImport that = (ReplaceWithSingleClassImport) o;
		return Objects.equals(state, that.state);
	}

	@Override
	public int hashCode() {
		return Objects.hash(state);
	}

	private static final class State implements Serializable {
		private static final long serialVersionUID = 1L;

		private final String target;
		private final String replacement;

		State(CharSequence target, CharSequence replacement) {
			this.target = target.toString();
			this.replacement = replacement.toString();
		}

		FormatterFunc toFormatter() {
			return raw -> raw.replace(target, replacement);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			State state = (State) o;
			return Objects.equals(target, state.target) &&
				Objects.equals(replacement, state.replacement);
		}

		@Override
		public int hashCode() {
			return Objects.hash(target, replacement);
		}
	}
}
