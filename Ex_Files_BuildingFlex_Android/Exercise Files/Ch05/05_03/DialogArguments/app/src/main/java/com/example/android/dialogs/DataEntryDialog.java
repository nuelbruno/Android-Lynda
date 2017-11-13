package com.example.android.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class DataEntryDialog extends DialogFragment{

    EditText etFirstName, etLastName, etAge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_entry_dialog,
                container, false);

        etFirstName = (EditText) rootView.findViewById(R.id.textFirstName);
        etLastName = (EditText) rootView.findViewById(R.id.textLastName);
        etAge = (EditText) rootView.findViewById(R.id.textAge);

        Button btnOk = (Button) rootView.findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button btnCancel = (Button) rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }
}
