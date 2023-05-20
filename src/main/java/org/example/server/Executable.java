package org.example.server;

import org.example.common.network.CommandResult;
import org.example.common.network.Request;

/**
 * Interface for commands
 */
public interface Executable {
    CommandResult execute(Request<?> request);
}