package ua.com.arturmamedov.marest.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.ui.JBSplitter
import ua.com.arturmamedov.marest.entities.Request
import ua.com.arturmamedov.marest.service.RestService
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.*
import ua.com.arturmamedov.marest.entities.HttpBodyType
import javax.swing.*
import java.awt.BorderLayout
import javax.swing.JPanel


class RestToolPanel(var project: Project) : SimpleToolWindowPanel(false, true) {
    private val restService = RestService.instance
    private val requestsTree = RequestsTree(restService);
    private val splitPane = JBSplitter("MARestMainSplitPaneKey", 0.25f)


    init {
        createToolbar()
        requestsTree.requestSelectListener = { r, g -> onSelectRequest(r, g) }
        splitPane.firstComponent = requestsTree;
        splitPane.secondComponent = JPanel() //TODO: create pane for request
        add(splitPane)
    }


    fun onSelectRequest(request: Request, group: String) {

    }


    private fun createToolbar() {
        val actionManager = ActionManager.getInstance()

        val actionGroup = DefaultActionGroup()
        actionGroup.add(AddRequestWindow())


        val toolbar = actionManager.createActionToolbar("MARest", actionGroup, false)
        val buttonsPanel = JPanel(BorderLayout())
        buttonsPanel.add(toolbar.getComponent(), BorderLayout.NORTH)
        setToolbar(buttonsPanel)
    }

    private inner class AddRequestWindow internal constructor() : AnAction(AllIcons.General.Add) {

        override fun actionPerformed(e: AnActionEvent) {

            val groupName = JTextField()
            val requestName = JTextField()
            val inputs = arrayOf<JComponent>(
                JLabel("Group name"), groupName,
                JLabel("Request name"), requestName
            )

            val result = JOptionPane
                .showConfirmDialog(null, inputs, "Create new request", JOptionPane.PLAIN_MESSAGE,0,
                    AllIcons.Vcs.Push)
            if (result == JOptionPane.OK_OPTION) {

                restService.addRequest(groupName.text, Request(
                    requestName.text,
                    "GET",
                    "http://",
                    HttpBodyType.NONE,
                    "",
                    HashMap(),
                    HashMap()
                ));
                requestsTree.updateTree()
            }


        }

    }
}
