package com.antwerkz.kibble

import com.intellij.openapi.components.BaseComponent
import com.intellij.openapi.extensions.ExtensionPointName
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Condition
import com.intellij.openapi.util.Key
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.messages.MessageBus
import org.picocontainer.PicoContainer

class ProjectWrapper(kotlinProject: org.jetbrains.kotlin.com.intellij.openapi.project.Project) : Project {
    override fun isDisposed(): Boolean {
        TODO("not implemented")
    }

    override fun getWorkspaceFile(): VirtualFile? {
        TODO("not implemented")
    }

    override fun getProjectFilePath(): String? {
        TODO("not implemented")
    }

    override fun getName(): String {
        TODO("not implemented")
    }

    override fun <T : Any?> getExtensions(p0: ExtensionPointName<T>): Array<T> {
        TODO("not implemented")
    }

    override fun getComponent(p0: String): BaseComponent {
        TODO("not implemented")
    }

    override fun <T : Any?> getComponent(p0: Class<T>): T {
        TODO("not implemented")
    }

    override fun <T : Any?> getComponent(p0: Class<T>, p1: T): T {
        TODO("not implemented")
    }

    override fun getBaseDir(): VirtualFile {
        TODO("not implemented")
    }

    override fun <T : Any?> putUserData(p0: Key<T>, p1: T?) {
        TODO("not implemented")
    }

    override fun isOpen(): Boolean {
        TODO("not implemented")
    }

    override fun getPresentableUrl(): String? {
        TODO("not implemented")
    }

    override fun save() {
        TODO("not implemented")
    }

    override fun getDisposed(): Condition<*> {
        TODO("not implemented")
    }

    override fun <T : Any?> getComponents(p0: Class<T>): Array<T> {
        TODO("not implemented")
    }

    override fun getPicoContainer(): PicoContainer {
        TODO("not implemented")
    }

    override fun getProjectFile(): VirtualFile? {
        TODO("not implemented")
    }

    override fun <T : Any?> getUserData(p0: Key<T>): T? {
        TODO("not implemented")
    }

    override fun isInitialized(): Boolean {
        TODO("not implemented")
    }

    override fun hasComponent(p0: Class<*>): Boolean {
        TODO("not implemented")
    }

    override fun getMessageBus(): MessageBus {
        TODO("not implemented")
    }

    override fun isDefault(): Boolean {
        TODO("not implemented")
    }

    override fun getBasePath(): String? {
        TODO("not implemented")
    }

    override fun getLocationHash(): String {
        TODO("not implemented")
    }

    override fun dispose() {
        TODO("not implemented")
    }

}