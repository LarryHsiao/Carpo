package com.silverhetch.carpo.file.phantom

import com.silverhetch.carpo.file.CExecutable

/**
 * Phantom object of [CExecutable].
 */
class PhantomExecutable : CExecutable {
    override fun launch(callback: CExecutable.Callback) {
        // leave it empty as Phantom object
    }
}
