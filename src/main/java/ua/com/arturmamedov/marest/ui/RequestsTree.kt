package ua.com.arturmamedov.marest.ui

import com.intellij.ui.TreeSpeedSearch
import com.intellij.ui.treeStructure.SimpleTree
import ua.com.arturmamedov.marest.entities.Request
import ua.com.arturmamedov.marest.entities.RequestTreeNode
import ua.com.arturmamedov.marest.service.RestService
import javax.swing.tree.DefaultTreeModel
import javax.swing.tree.DefaultMutableTreeNode


class RequestsTree(var service: RestService) : SimpleTree() {
    private val rootNode: DefaultMutableTreeNode? = DefaultMutableTreeNode("Requests")
    var requestSelectListener: ((Request, String) -> Unit)? = null;

    init {
        model = DefaultTreeModel(rootNode)
        addTreeSelectionListener { onSelectTreeItem() }
        TreeSpeedSearch(this)
        updateTree()
    }

    private fun onSelectTreeItem() {
        val lastSelected = lastSelectedPathComponent as? RequestTreeNode ?: return
        requestSelectListener?.invoke(lastSelected.request, lastSelected.group)
    }

    fun updateTree() {
        rootNode!!.removeAllChildren()
        for (group in service.getGroups()) {
            val node = DefaultMutableTreeNode(group)
            val requests = service.getRequests()[group];
            for (req in requests!!) {
                node.add(RequestTreeNode(group, req))
            }
            rootNode!!.add(node);
        }
        (model as DefaultTreeModel).reload()
    }


}
