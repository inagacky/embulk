/*
 * Copyright 2015 The Embulk project
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

package org.embulk.spi;

import org.embulk.config.UserDataException;

/**
 * Represents an Exception caused by user's data processed.
 *
 * @since 0.7.1
 */
public class DataException extends RuntimeException implements UserDataException {
    /**
     * Constructs a new {@link DataException} with the specified detail message.
     *
     * @param message  the detail message
     *
     * @since 0.7.1
     */
    public DataException(final String message) {
        super(message);
    }

    /**
     * Constructs a new {@link DataException} with the specified cause and a detail message of the cause.
     *
     * @param cause  the cause
     *
     * @since 0.7.1
     */
    public DataException(final Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@link DataException} with the specified detail message and cause.
     *
     * @param message  the detail message
     * @param cause  the cause
     *
     * @since 0.7.1
     */
    public DataException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
