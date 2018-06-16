package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.main

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.selfedu.yevgen.oliinykov.retrofitgithub.R
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubIssue
import com.selfedu.yevgen.oliinykov.retrofitgithub.data.models.GithubRepo
import com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.CredentialsDialog
import com.selfedu.yevgen.oliinykov.retrofitgithub.presentation.base.BaseActivity

class MainView : BaseActivity<MainContract.IView, MainPresenter>(), CredentialsDialog.ICredentialsDialogListener, MainContract.IView {

//    private var username: String = ""
//    private var password: String = ""

    lateinit var repositoriesSpinner: Spinner
    lateinit var issuesSpinner: Spinner
    lateinit var commentEditText: EditText
    lateinit var sendButton: Button
    lateinit var loadReposButtons: Button

    override var mPresenter: MainPresenter = MainPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.my_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        sendButton = findViewById<View>(R.id.send_comment_button) as Button

        repositoriesSpinner = findViewById<View>(R.id.repositories_spinner) as Spinner
        repositoriesSpinner.isEnabled = false
        repositoriesSpinner.adapter = ArrayAdapter(this@MainView, android.R.layout.simple_spinner_dropdown_item, arrayOf("No repositories available"))
        repositoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (parent.selectedItem is GithubRepo) {
                    val githubRepo = parent.selectedItem as GithubRepo
                    mPresenter.onRepoSelected(githubRepo)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        issuesSpinner = findViewById<View>(R.id.issues_spinner) as Spinner
        issuesSpinner.isEnabled = false
        issuesSpinner.adapter = ArrayAdapter(this@MainView, android.R.layout.simple_spinner_dropdown_item, arrayOf("Please select repository"))

        commentEditText = findViewById<View>(R.id.comment_edittext) as EditText

        loadReposButtons = findViewById<View>(R.id.loadRepos_button) as Button
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_credentials -> {
                mPresenter.onOpenCredentialsDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showCredentialsDialog() {
        val dialog = CredentialsDialog()
        val arguments = Bundle()
//        arguments.putString("username", username)
//        arguments.putString("password", password)
        dialog.arguments = arguments

        dialog.show(supportFragmentManager, "credentialsDialog")
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.loadRepos_button -> mPresenter.onLoadUserRepos()
            R.id.send_comment_button -> {
                val newComment = commentEditText.text.toString()
                if (!newComment.isEmpty()) {
                    val selectedItem = issuesSpinner.selectedItem as GithubIssue
                    mPresenter.onAddCommentToIssue(newComment, selectedItem)

                } else {
                    Toast.makeText(this@MainView, "Please enter a comment", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onDialogPositiveClick(username: String, password: String) {
        loadReposButtons.isEnabled = true
        mPresenter.onCredentialsEntered(username, password)
    }

    override fun renderRepoIssues(issues: List<GithubIssue>) {
        if (!issues.isEmpty()) {
            val spinnerAdapter = ArrayAdapter(this@MainView, android.R.layout.simple_spinner_dropdown_item, issues)
            issuesSpinner.isEnabled = true
            commentEditText.isEnabled = true
            sendButton.isEnabled = true
            issuesSpinner.adapter = spinnerAdapter
        } else {
            val spinnerAdapter = ArrayAdapter(this@MainView, android.R.layout.simple_spinner_dropdown_item, arrayOf("Repository has no issues"))
            issuesSpinner.isEnabled = false
            commentEditText.isEnabled = false
            sendButton.isEnabled = false
            issuesSpinner.adapter = spinnerAdapter
        }
    }

    override fun renderUserRepos(repos: List<GithubRepo>) {
        if (!repos.isEmpty()) {
            val spinnerAdapter = ArrayAdapter(this@MainView, android.R.layout.simple_spinner_dropdown_item, repos)
            repositoriesSpinner.adapter = spinnerAdapter
            repositoriesSpinner.isEnabled = true
        } else {
            val spinnerAdapter = ArrayAdapter(this@MainView, android.R.layout.simple_spinner_dropdown_item, arrayOf("User has no repositories"))
            repositoriesSpinner.adapter = spinnerAdapter
            repositoriesSpinner.isEnabled = false
        }
    }

    override fun clearCommentInput() {
        commentEditText.setText("")
    }
}
