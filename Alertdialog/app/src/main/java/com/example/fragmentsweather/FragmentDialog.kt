package com.example.fragmentsweather

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class FragmentDialog(): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Change type fragment").setItems(context?.resources?.getStringArray(R.array.fragmentTypeDesign), ChangeFragmentListener(parentFragmentManager))
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")

    }

}