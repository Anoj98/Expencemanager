package com.group10.expensemanager;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class DashBoardFragment extends Fragment {

    //Floating button

    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;

    //Floating textview
    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    //boolean
    private boolean isOpen=false;

    //Animation
    private Animation FadOpen,FadClose;

    //Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mAuth=FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid();

        mIncomeDatabase= FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase= FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);

        //connect floating button to layout
        FloatingActionButton fab_main_btn = myview.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn=myview.findViewById(R.id.income_ft_btn);
        fab_expense_btn=myview.findViewById(R.id.expense_ft_btn);

        //connecting floating text
        fab_income_txt=myview.findViewById(R.id.income_ft_text);
        fab_expense_txt=myview.findViewById(R.id.expense_ft_text);

        //connecting animations
        FadOpen= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_open);
        FadClose= AnimationUtils.loadAnimation(getActivity(),R.anim.fade_close);

        fab_main_btn.setOnClickListener(view -> {

            addData();

            if (isOpen){

                fab_income_btn.startAnimation(FadClose);
                fab_expense_btn.startAnimation(FadClose);
                fab_income_btn.setClickable(false);
                fab_expense_btn.setClickable(false);

                fab_income_txt.startAnimation(FadClose);
                fab_expense_txt.startAnimation(FadClose);
                fab_income_txt.setClickable(false);
                fab_expense_txt.setClickable(false);
                isOpen=false;

            }else {

                fab_income_btn.startAnimation(FadOpen);
                fab_expense_btn.startAnimation(FadOpen);
                fab_income_btn.setClickable(true);
                fab_expense_btn.setClickable(true);

                fab_income_txt.startAnimation(FadOpen);
                fab_expense_txt.startAnimation(FadOpen);
                fab_income_txt.setClickable(true);
                fab_expense_txt.setClickable(true);
                isOpen=true;

            }

        });

        return myview;
    }

    private void addData(){
        //Fab btn income
        fab_income_btn.setOnClickListener(view -> {

            incomeDataInsert();
        });

        //Fab btn expense
        fab_expense_btn.setOnClickListener(view -> {

        });

    }

    public void incomeDataInsert(){

        AlertDialog.Builder mydialog=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=LayoutInflater.from(getActivity());
        View myview=inflater.inflate(R.layout.custom_layout_for_insertdata,null);
        mydialog.setView(myview);
        AlertDialog dialog=mydialog.create();

        EditText edtAmount=myview.findViewById(R.id.amount_edt);
        EditText edtType=myview.findViewById(R.id.type_edt);
        EditText edtNote=myview.findViewById(R.id.note_edt);

        Button btnSave=myview.findViewById(R.id.btnSave);
        Button btnCancel=myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount=edtAmount.getText().toString().trim();
                String type=edtType.getText().toString().trim();
                String note=edtNote.getText().toString().trim();

                if (TextUtils.isEmpty(amount)){
                    edtAmount.setError("Required field");
                    return;
                }
                int ouramountint=Integer.parseInt(amount);

                if (TextUtils.isEmpty(type)){
                    edtType.setError("Required field");
                    return;
                }
                if (TextUtils.isEmpty(note)){
                    edtNote.setError("Required field");
                    return;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}