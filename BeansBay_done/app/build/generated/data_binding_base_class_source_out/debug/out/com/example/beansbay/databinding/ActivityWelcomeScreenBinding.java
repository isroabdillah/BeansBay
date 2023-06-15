// Generated by view binder compiler. Do not edit!
package com.example.beansbay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.beansbay.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityWelcomeScreenBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView TVtitlelogin;

  @NonNull
  public final ConstraintLayout background;

  @NonNull
  public final TextView descTextView;

  @NonNull
  public final Guideline guidelineHorizontal;

  @NonNull
  public final Guideline guidelineVertical;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final Button loginButton;

  @NonNull
  public final Button registerButton;

  private ActivityWelcomeScreenBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView TVtitlelogin, @NonNull ConstraintLayout background,
      @NonNull TextView descTextView, @NonNull Guideline guidelineHorizontal,
      @NonNull Guideline guidelineVertical, @NonNull ImageView imageView,
      @NonNull ProgressBar loading, @NonNull Button loginButton, @NonNull Button registerButton) {
    this.rootView = rootView;
    this.TVtitlelogin = TVtitlelogin;
    this.background = background;
    this.descTextView = descTextView;
    this.guidelineHorizontal = guidelineHorizontal;
    this.guidelineVertical = guidelineVertical;
    this.imageView = imageView;
    this.loading = loading;
    this.loginButton = loginButton;
    this.registerButton = registerButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityWelcomeScreenBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityWelcomeScreenBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_welcome_screen, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityWelcomeScreenBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.TVtitlelogin;
      TextView TVtitlelogin = ViewBindings.findChildViewById(rootView, id);
      if (TVtitlelogin == null) {
        break missingId;
      }

      ConstraintLayout background = (ConstraintLayout) rootView;

      id = R.id.descTextView;
      TextView descTextView = ViewBindings.findChildViewById(rootView, id);
      if (descTextView == null) {
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

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.login_button;
      Button loginButton = ViewBindings.findChildViewById(rootView, id);
      if (loginButton == null) {
        break missingId;
      }

      id = R.id.register_button;
      Button registerButton = ViewBindings.findChildViewById(rootView, id);
      if (registerButton == null) {
        break missingId;
      }

      return new ActivityWelcomeScreenBinding((ConstraintLayout) rootView, TVtitlelogin, background,
          descTextView, guidelineHorizontal, guidelineVertical, imageView, loading, loginButton,
          registerButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}