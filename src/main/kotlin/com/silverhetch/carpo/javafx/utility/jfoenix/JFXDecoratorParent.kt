package com.silverhetch.carpo.javafx.utility.jfoenix

import com.jfoenix.controls.JFXDecorator
import com.silverhetch.clotho.Source
import javafx.scene.Parent
import javafx.stage.Stage

/**
 * Source that apply JFXDecorator to given [Parent] node when is possible.
 */
class JFXDecoratorParent(private val stage: Stage, private val parent: Parent) : Source<Parent> {
    override fun fetch(): Parent {
        return if (stage.isShowing) {
            parent
        } else {
            JFXDecorator(stage, parent)
        }
    }
}