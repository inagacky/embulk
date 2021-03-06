package org.embulk.deps.cli;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.embulk.cli.CommandLine;
import org.embulk.deps.EmbulkDependencyClassLoaders;
import org.slf4j.Logger;

public abstract class CommandLineParser {
    public static CommandLineParser create() {
        try {
            return CONSTRUCTOR.newInstance();
        } catch (final IllegalAccessException | IllegalArgumentException | InstantiationException ex) {
            throw new LinkageError("Dependencies for Commons-CLI are not loaded correctly: " + CLASS_NAME, ex);
        } catch (final InvocationTargetException ex) {
            final Throwable targetException = ex.getTargetException();
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException) targetException;
            } else if (targetException instanceof Error) {
                throw (Error) targetException;
            } else {
                throw new RuntimeException("Unexpected Exception in creating: " + CLASS_NAME, ex);
            }
        }
    }

    public abstract CommandLine parse(final List<String> originalArgs, final Logger logger);

    @SuppressWarnings("unchecked")
    private static Class<CommandLineParser> loadImplClass() {
        try {
            return (Class<CommandLineParser>) CLASS_LOADER.loadClass(CLASS_NAME);
        } catch (final ClassNotFoundException ex) {
            throw new LinkageError("Dependencies for Commons-CLI are not loaded correctly: " + CLASS_NAME, ex);
        }
    }

    private static final ClassLoader CLASS_LOADER = EmbulkDependencyClassLoaders.get();
    private static final String CLASS_NAME = "org.embulk.deps.cli.CommandLineParserImpl";

    static {
        final Class<CommandLineParser> clazz = loadImplClass();
        try {
            CONSTRUCTOR = clazz.getConstructor();
        } catch (final NoSuchMethodException ex) {
            throw new LinkageError("Dependencies for Commons-CLI are not loaded correctly: " + CLASS_NAME, ex);
        }
    }

    private static final Constructor<CommandLineParser> CONSTRUCTOR;
}
