package com.silverhetch.carpo.workspace

import java.io.File
import java.sql.Connection

/**
 * Represent the workspace and related functions.
 */
interface Workspace {
    /**
     * The root file with [File]
     */
    fun rootJFile(): File

    /**
     * All the file in the workspace. (subdirectories are not included)
     */
    fun files(): Array<File>

    /**
     * The sql connection in this workspace.
     */
    fun sqlConn(): Connection

    /**
     * @return A [FilePipe] for inserting file into this workspace.
     */
    fun insertionPipe(): FilePipe

    /**
     * A pipe that outsource file should be through with.
     */
    interface FilePipe {
        /**
         * @param source the outsource file.
         * @param target the target file in workspace tree.
         */
        fun through(source: File, target: File)
    }
}