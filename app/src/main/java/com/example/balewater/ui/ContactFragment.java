package com.example.balewater.ui;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.balewater.MyFragment;
import com.example.balewater.R;


/**
 * Contacto del menu desplegable
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends MyFragment {

    TextView phone;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Phone
        view.findViewById(R.id.phoneContacte).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNo = "667111222";
                if(!TextUtils.isEmpty(phoneNo)) {
                    String dial = "tel:" + phoneNo;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                }
            }
        });
        //  SUBRAYAR TEXTO phone
        phone = view.findViewById(R.id.phoneContacte);
        SpannableString subrallarPhone = new SpannableString(" 666 333 222");
        subrallarPhone.setSpan(new UnderlineSpan(), 0, subrallarPhone.length(), 0);
        phone.setText(subrallarPhone);
        view.findViewById(R.id.contacteBoton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(requireContext()).setTitle("\t\t    Hem rebut la teva consulte ")
                        .setMessage("\t      ")
                        .setMessage("\t     Rebràs un email de confirmació ")
                        .setCancelable(true)
                        .create()
                        .show();
            }

        });
    }
}

