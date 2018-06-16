package com.selfedu.yevgen.oliinykov.retrofitgithub.presentation

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.selfedu.yevgen.oliinykov.retrofitgithub.R

class CredentialsDialog : DialogFragment() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private var listener: ICredentialsDialogListener? = null

    interface ICredentialsDialogListener {
        fun onDialogPositiveClick(username: String, password: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is ICredentialsDialogListener) {
            listener = activity as ICredentialsDialogListener?
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_credentials, null)
        usernameEditText = view.findViewById<View>(R.id.username_edittext) as EditText
        passwordEditText = view.findViewById<View>(R.id.password_edittext) as EditText

        usernameEditText.setText(arguments!!.getString("username"))
        passwordEditText.setText(arguments!!.getString("password"))

        val builder = AlertDialog.Builder(activity!!)
                .setView(view)
                .setTitle("Credentials")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Continue") { dialog, which ->
                    if (listener != null) {
                        listener!!.onDialogPositiveClick(usernameEditText.text.toString(), passwordEditText.text.toString())
                    }
                }
        return builder.create()
    }

}
