package ua.com.arturmamedov.marest.entities

import javax.swing.tree.DefaultMutableTreeNode

class RequestTreeNode(var group:String, var request:Request): DefaultMutableTreeNode() {
    override fun toString(): String {
        return request.name
    }
}
