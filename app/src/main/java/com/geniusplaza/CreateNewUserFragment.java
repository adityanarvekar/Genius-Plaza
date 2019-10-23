package com.geniusplaza;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateNewUserFragment extends DialogFragment {
    EditText username, userjob;
    Button createUser;
    TextView cancelbutton;
    ProgressBar progressBar;
    private APIService mAPIService;
    public CreateNewUserFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.addnewuser, container, false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        Window window = getDialog().getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        username = (EditText) rootView.findViewById(R.id.username);
        userjob = (EditText) rootView.findViewById(R.id.userjob);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        cancelbutton = (TextView) rootView.findViewById(R.id.cancel);
        createUser = (Button) rootView.findViewById(R.id.addbutton);
        Typeface tf2 = FontManager.get(FontManager.FONTAWESOME, getContext());

        username.requestFocus();
        cancelbutton.setTypeface(tf2);
        cancelbutton.setText(getContext().getResources().getString(R.string.Close));
        mAPIService = ApiUtils.getAPIService();
        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    View view = getDialog().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getDialog().dismiss();

            }
        });




        createUser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createUser.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                sendPost(username.getText().toString().trim(), userjob.getText().toString().trim());
            }
        });


        return rootView;
    }
    public void sendPost(String title, String body) {
        mAPIService.saveUser(title, body).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                progressBar.setVisibility(View.GONE);
                if(response.isSuccessful()) {

                    try {
                        View view = getDialog().getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                    getDialog().dismiss();

                    if (getContext() instanceof MainActivity) {
                        ((MainActivity) getContext()).onUserAdded(response.body().toString());
                    }

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                createUser.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
