// Generated by view binder compiler. Do not edit!
package com.example.beansbay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.beansbay.R;
import com.example.beansbay.ui.customview.CustomEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView TVteksemail;

  @NonNull
  public final TextView TVteksnama;

  @NonNull
  public final TextView TVtekspassword;

  @NonNull
  public final TextView TVtitle;

  @NonNull
  public final TextView alreadyHaveAccount;

  @NonNull
  public final Button btnSignupNext;

  @NonNull
  public final CustomEditText edRegisterEmail;

  @NonNull
  public final CustomEditText edRegisterName;

  @NonNull
  public final CustomEditText edRegisterPassword;

  @NonNull
  public final TextInputLayout emailEditTextLayout;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final TextInputLayout nameEditTextLayout;

  @NonNull
  public final TextView navigateToLogin;

  @NonNull
  public final TextInputLayout passwordEditTextLayout;

  private ActivitySignupBinding(@NonNull ConstraintLayout rootView, @NonNull TextView TVteksemail,
      @NonNull TextView TVteksnama, @NonNull TextView TVtekspassword, @NonNull TextView TVtitle,
      @NonNull TextView alreadyHaveAccount, @NonNull Button btnSignupNext,
      @NonNull CustomEditText edRegisterEmail, @NonNull CustomEditText edRegisterName,
      @NonNull CustomEditText edRegisterPassword, @NonNull TextInputLayout emailEditTextLayout,
      @NonNull ProgressBar loading, @NonNull TextInputLayout nameEditTextLayout,
      @NonNull TextView navigateToLogin, @NonNull TextInputLayout passwordEditTextLayout) {
    this.rootView = rootView;
    this.TVteksemail = TVteksemail;
    this.TVteksnama = TVteksnama;
    this.TVtekspassword = TVtekspassword;
    this.TVtitle = TVtitle;
    this.alreadyHaveAccount = alreadyHaveAccount;
    this.btnSignupNext = btnSignupNext;
    this.edRegisterEmail = edRegisterEmail;
    this.edRegisterName = edRegisterName;
    this.edRegisterPassword = edRegisterPassword;
    this.emailEditTextLayout = emailEditTextLayout;
    this.loading = loading;
    this.nameEditTextLayout = nameEditTextLayout;
    this.navigateToLogin = navigateToLogin;
    this.passwordEditTextLayout = passwordEditTextLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.TVteksemail;
      TextView TVteksemail = ViewBindings.findChildViewById(rootView, id);
      if (TVteksemail == null) {
        break missingId;
      }

      id = R.id.TVteksnama;
      TextView TVteksnama = ViewBindings.findChildViewById(rootView, id);
      if (TVteksnama == null) {
        break missingId;
      }

      id = R.id.TVtekspassword;
      TextView TVtekspassword = ViewBindings.findChildViewById(rootView, id);
      if (TVtekspassword == null) {
        break missingId;
      }

      id = R.id.TVtitle;
      TextView TVtitle = ViewBindings.findChildViewById(rootView, id);
      if (TVtitle == null) {
        break missingId;
      }

      id = R.id.already_have_account;
      TextView alreadyHaveAccount = ViewBindings.findChildViewById(rootView, id);
      if (alreadyHaveAccount == null) {
        break missingId;
      }

      id = R.id.btn_signup_next;
      Button btnSignupNext = ViewBindings.findChildViewById(rootView, id);
      if (btnSignupNext == null) {
        break missingId;
      }

      id = R.id.ed_register_email;
      CustomEditText edRegisterEmail = ViewBindings.findChildViewById(rootView, id);
      if (edRegisterEmail == null) {
        break missingId;
      }

      id = R.id.ed_register_name;
      CustomEditText edRegisterName = ViewBindings.findChildViewById(rootView, id);
      if (edRegisterName == null) {
        break missingId;
      }

      id = R.id.ed_register_password;
      CustomEditText edRegisterPassword = ViewBindings.findChildViewById(rootView, id);
      if (edRegisterPassword == null) {
        break missingId;
      }

      id = R.id.emailEditTextLayout;
      TextInputLayout emailEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (emailEditTextLayout == null) {
        break missingId;
      }

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.nameEditTextLayout;
      TextInputLayout nameEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (nameEditTextLayout == null) {
        break missingId;
      }

      id = R.id.navigate_to_login;
      TextView navigateToLogin = ViewBindings.findChildViewById(rootView, id);
      if (navigateToLogin == null) {
        break missingId;
      }

      id = R.id.passwordEditTextLayout;
      TextInputLayout passwordEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (passwordEditTextLayout == null) {
        break missingId;
      }

      return new ActivitySignupBinding((ConstraintLayout) rootView, TVteksemail, TVteksnama,
          TVtekspassword, TVtitle, alreadyHaveAccount, btnSignupNext, edRegisterEmail,
          edRegisterName, edRegisterPassword, emailEditTextLayout, loading, nameEditTextLayout,
          navigateToLogin, passwordEditTextLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
