// Generated by view binder compiler. Do not edit!
package com.example.beansbay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
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

public final class ActivitySignup2Binding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextInputLayout AromaEditTextLayout;

  @NonNull
  public final TextInputLayout CategoryEditTextLayout;

  @NonNull
  public final TextInputLayout KuatAromaEditTextLayout;

  @NonNull
  public final TextInputLayout KuatAsamEditTextLayout;

  @NonNull
  public final TextInputLayout SkorAsamEditTextLayout;

  @NonNull
  public final TextView TVteksnama2;

  @NonNull
  public final TextView TVteksnama2Hint;

  @NonNull
  public final TextView TVtitle;

  @NonNull
  public final Button btnSignup;

  @NonNull
  public final AutoCompleteTextView edAroma;

  @NonNull
  public final AutoCompleteTextView edCategoryCoffe;

  @NonNull
  public final AutoCompleteTextView edKelamin;

  @NonNull
  public final CustomEditText edKuatAroma;

  @NonNull
  public final AutoCompleteTextView edKuatAsam;

  @NonNull
  public final CustomEditText edSkorAsam;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final TextView textAroma;

  @NonNull
  public final TextView textAsam;

  @NonNull
  public final TextView textCategory;

  @NonNull
  public final TextView textCategoryHint;

  @NonNull
  public final TextInputLayout textInputLayout;

  @NonNull
  public final TextView textSkorAsam;

  private ActivitySignup2Binding(@NonNull ConstraintLayout rootView,
      @NonNull TextInputLayout AromaEditTextLayout, @NonNull TextInputLayout CategoryEditTextLayout,
      @NonNull TextInputLayout KuatAromaEditTextLayout,
      @NonNull TextInputLayout KuatAsamEditTextLayout,
      @NonNull TextInputLayout SkorAsamEditTextLayout, @NonNull TextView TVteksnama2,
      @NonNull TextView TVteksnama2Hint, @NonNull TextView TVtitle, @NonNull Button btnSignup,
      @NonNull AutoCompleteTextView edAroma, @NonNull AutoCompleteTextView edCategoryCoffe,
      @NonNull AutoCompleteTextView edKelamin, @NonNull CustomEditText edKuatAroma,
      @NonNull AutoCompleteTextView edKuatAsam, @NonNull CustomEditText edSkorAsam,
      @NonNull ProgressBar loading, @NonNull TextView textAroma, @NonNull TextView textAsam,
      @NonNull TextView textCategory, @NonNull TextView textCategoryHint,
      @NonNull TextInputLayout textInputLayout, @NonNull TextView textSkorAsam) {
    this.rootView = rootView;
    this.AromaEditTextLayout = AromaEditTextLayout;
    this.CategoryEditTextLayout = CategoryEditTextLayout;
    this.KuatAromaEditTextLayout = KuatAromaEditTextLayout;
    this.KuatAsamEditTextLayout = KuatAsamEditTextLayout;
    this.SkorAsamEditTextLayout = SkorAsamEditTextLayout;
    this.TVteksnama2 = TVteksnama2;
    this.TVteksnama2Hint = TVteksnama2Hint;
    this.TVtitle = TVtitle;
    this.btnSignup = btnSignup;
    this.edAroma = edAroma;
    this.edCategoryCoffe = edCategoryCoffe;
    this.edKelamin = edKelamin;
    this.edKuatAroma = edKuatAroma;
    this.edKuatAsam = edKuatAsam;
    this.edSkorAsam = edSkorAsam;
    this.loading = loading;
    this.textAroma = textAroma;
    this.textAsam = textAsam;
    this.textCategory = textCategory;
    this.textCategoryHint = textCategoryHint;
    this.textInputLayout = textInputLayout;
    this.textSkorAsam = textSkorAsam;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignup2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignup2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignup2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.AromaEditTextLayout;
      TextInputLayout AromaEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (AromaEditTextLayout == null) {
        break missingId;
      }

      id = R.id.CategoryEditTextLayout;
      TextInputLayout CategoryEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (CategoryEditTextLayout == null) {
        break missingId;
      }

      id = R.id.KuatAromaEditTextLayout;
      TextInputLayout KuatAromaEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (KuatAromaEditTextLayout == null) {
        break missingId;
      }

      id = R.id.KuatAsamEditTextLayout;
      TextInputLayout KuatAsamEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (KuatAsamEditTextLayout == null) {
        break missingId;
      }

      id = R.id.SkorAsamEditTextLayout;
      TextInputLayout SkorAsamEditTextLayout = ViewBindings.findChildViewById(rootView, id);
      if (SkorAsamEditTextLayout == null) {
        break missingId;
      }

      id = R.id.TVteksnama2;
      TextView TVteksnama2 = ViewBindings.findChildViewById(rootView, id);
      if (TVteksnama2 == null) {
        break missingId;
      }

      id = R.id.TVteksnama2_hint;
      TextView TVteksnama2Hint = ViewBindings.findChildViewById(rootView, id);
      if (TVteksnama2Hint == null) {
        break missingId;
      }

      id = R.id.TVtitle;
      TextView TVtitle = ViewBindings.findChildViewById(rootView, id);
      if (TVtitle == null) {
        break missingId;
      }

      id = R.id.btn_signup;
      Button btnSignup = ViewBindings.findChildViewById(rootView, id);
      if (btnSignup == null) {
        break missingId;
      }

      id = R.id.ed_aroma;
      AutoCompleteTextView edAroma = ViewBindings.findChildViewById(rootView, id);
      if (edAroma == null) {
        break missingId;
      }

      id = R.id.ed_category_coffe;
      AutoCompleteTextView edCategoryCoffe = ViewBindings.findChildViewById(rootView, id);
      if (edCategoryCoffe == null) {
        break missingId;
      }

      id = R.id.ed_kelamin;
      AutoCompleteTextView edKelamin = ViewBindings.findChildViewById(rootView, id);
      if (edKelamin == null) {
        break missingId;
      }

      id = R.id.ed_kuat_aroma;
      CustomEditText edKuatAroma = ViewBindings.findChildViewById(rootView, id);
      if (edKuatAroma == null) {
        break missingId;
      }

      id = R.id.ed_kuat_asam;
      AutoCompleteTextView edKuatAsam = ViewBindings.findChildViewById(rootView, id);
      if (edKuatAsam == null) {
        break missingId;
      }

      id = R.id.ed_skor_asam;
      CustomEditText edSkorAsam = ViewBindings.findChildViewById(rootView, id);
      if (edSkorAsam == null) {
        break missingId;
      }

      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.textAroma;
      TextView textAroma = ViewBindings.findChildViewById(rootView, id);
      if (textAroma == null) {
        break missingId;
      }

      id = R.id.textAsam;
      TextView textAsam = ViewBindings.findChildViewById(rootView, id);
      if (textAsam == null) {
        break missingId;
      }

      id = R.id.textCategory;
      TextView textCategory = ViewBindings.findChildViewById(rootView, id);
      if (textCategory == null) {
        break missingId;
      }

      id = R.id.textCategory_hint;
      TextView textCategoryHint = ViewBindings.findChildViewById(rootView, id);
      if (textCategoryHint == null) {
        break missingId;
      }

      id = R.id.textInputLayout;
      TextInputLayout textInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayout == null) {
        break missingId;
      }

      id = R.id.textSkorAsam;
      TextView textSkorAsam = ViewBindings.findChildViewById(rootView, id);
      if (textSkorAsam == null) {
        break missingId;
      }

      return new ActivitySignup2Binding((ConstraintLayout) rootView, AromaEditTextLayout,
          CategoryEditTextLayout, KuatAromaEditTextLayout, KuatAsamEditTextLayout,
          SkorAsamEditTextLayout, TVteksnama2, TVteksnama2Hint, TVtitle, btnSignup, edAroma,
          edCategoryCoffe, edKelamin, edKuatAroma, edKuatAsam, edSkorAsam, loading, textAroma,
          textAsam, textCategory, textCategoryHint, textInputLayout, textSkorAsam);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
