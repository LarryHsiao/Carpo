package com.silverhetch.carpo.workspace

import com.silverhetch.carpo.config.CarpoConfigSource
import com.silverhetch.clotho.Source
import java.io.File

/**
 * The default file of workspace
 */
class DefaultWorkspaceFile : Source<File> {
    override fun fetch(): File {
        return File(CarpoConfigSource().fetch().workspacePath())
    }
}