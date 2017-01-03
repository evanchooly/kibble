package com.antwerkz.kibble.model

import com.antwerkz.kibble.model.Visibility.PUBLIC

class Constructor : Visible, ParameterHolder {
    override var visibility: Visibility = PUBLIC
    override val parameters = mutableListOf<Parameter>()
}