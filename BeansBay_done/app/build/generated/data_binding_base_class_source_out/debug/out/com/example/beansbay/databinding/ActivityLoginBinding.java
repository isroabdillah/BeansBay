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
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.beansbay.R;
import com.example.beansbay.ui.customview.CustomEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityLoginBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView TVdesk;

  @NonNull
  public final TextView TVteksemaillogin;

  @NonNull
  public final TextView TVtekspasswordlogin;

  @NonNull
  public final TextView TVtitlelogin;

  @NonNull
  public final TextView alreadyHaveAccount;

  @NonNull
  public final CustomEditText edLoginEmail;

  @NonNull
  public final CustomEditText edLoginPassword;

  @NonNull
  public final TextInputLayout emailEditTextLayout;

  @NonNull
  public final Guideline guidelineHorizontal;

  @NonNull
  public final Guideline guidelineVertical;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final Button loginButton;

  @NonNull
  public final TextView navigateToRegister;

  @NonNull
  public final TextInputLayout passwordEditTextLayout;

  private ActivityLoginBinding(@NonNull ConstraintLayout rootView, @NonNull TextView TVdesk,
      @NonNull TextView TVteksemaillogin, @NonNull TextView TVtekspasswordlogin,
      @NonNull TextView TVtitlelogin, @NonNull TextView alreadyHaveAccount,
      @NonNull CustomEditText edLoginEmail, @NonNull CustomEditText edLoginPassword,
      @NonNull TextInputLayout emailEditTextLayout, @NonNull Guideline guidelineHorizontal,
      @NonNull Guideline guidelineVertical, @NonNull ProgressBar loading,
      @NonNull Button loginButton, @NonNull TextView navigateToRegister,
      @NonNull TextInputLayout passwordEditTextLayout) {
    this.rootView = rootView;
    this.TVdesk = TVdesk;
    this.TVteksemaillogin = TVteksemaillogin;
    this.TVtekspasswordlogin = TVtekspasswordlogin;
    this.TVtitlelogin = TVtitlelogin;
    this.alreadyHaveAccount = alreadyHaveAccount;
    this.edLoginEmail = edLoginEmail;
    this.edLoginPassword = edLoginPassword;
    this.emailEditTextLayout = emailEditTextLayout;
    this.guidelineHorizontal = guidelineHorizontal;
    this.guidelineVertical = guidelineVertical;
    this.loading = loading;
    this.loginButton = loginButton;
    this.navigateToRegister = navigateToRegister;
    this.passwordEditTextLayout = passwordEditTextLayout;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityLoginBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_login, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityLoginBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.TVdesk;
      TextView TVdesk = ViewBindings.findChildViewById(rootView, id);
      if (TVdesk == null) {
        break missingId;
      }

      id = R.id.TVteksemaillogin;
      TextView TVteksemaillogin = ViewBindings.findChildViewById(rootView, id);
      if (TVteksemaillogin == null) {
        break missingId;
      }

      id = R.id.TVtekspasswordlogin;
      TextView TVtekspasswordlogin = ViewBindings.findChildViewById(rootView, id);
      if (TVtekspasswordlogin == null) {
        break missingId;
      }

      id = R.id.TVtitlelogin;
      TextView TVtitlelogin = ViewBindings.findChildViewById(rootView, id);
      if (TVtitlelogin == null) {
        break missingId;
      }

      id = R.id.already_have_account;
      TextView alreadyHaveAccount = ViewBindings.findChildViewById(rootView, id);
      if (alreadyHaveAccount == null) {
        break missingId;
      }

      id = R.id.ed_login_email;
      CustomEditText edLoginEmail = ViewBindings.findChildViewById(rootView, id);
      if (edLoginEmail == null) {
        break missingId;
      }

      id = R.id.ed_login_password;
      CustomEditText edLoginPassword = ViewBindings.findChildViewById(rootView, id);
      if (edLoginPassword == null) {
        break missingId;
      }

      id = R.id.emailEditTextLayout;
      TextInputLayout emailEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (emailEditTextLayout == null) {
        break missingId;
      }

      id = R.id.guidelineHorizontal;
      Guideline guidelineHorizontal = ViewBindings.findChildViewById(rootView, id);
      if (guidelineHorizontal == null) {
        break missingId;
      }

      id = R.id.guidelineVertical;
      Guideline guidelineVertical = ViewBindings.findChildViewById(rootView, id);
      if (guidelineVertical == null) {
        break missingId;
      }

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.loginButton;
      Button loginButton = ViewBindings.findChildViewById(rootView, id);
      if (loginButton == null) {
        break missingId;
      }

      id = R.id.navigate_to_register;
      TextView navigateToRegister = ViewBindings.findChildViewById(rootView, id);
      if (navigateToRegister == null) {
        break missingId;
      }

      id = R.id.passwordEditTextLayout;
      TextInputLayout passwordEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (passwordEditTextLayout == null) {
        break missingId;
      }

      return new ActivityLoginBinding((ConstraintLayout) rootView, TVdesk, TVteksemaillogin,
          TVtekspasswordlogin, TVtitlelogin, alreadyHaveAccount, edLoginEmail, edLoginPassword,
          emailEditTextLayout, guidelineHorizontal, guidelineVertical, loading, loginButton,
          navigateToRegister, passwordEditTextLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
