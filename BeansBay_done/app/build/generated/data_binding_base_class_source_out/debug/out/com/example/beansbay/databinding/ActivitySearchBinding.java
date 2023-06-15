// Generated by view binder compiler. Do not edit!
package com.example.beansbay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.beansbay.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySearchBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ProgressBar loading;

  @NonNull
  public final RecyclerView rvSearchQuery;

  @NonNull
  public final TextView searchQuery;

  @NonNull
  public final TextView searchResult;

  private ActivitySearchBinding(@NonNull ConstraintLayout rootView, @NonNull ProgressBar loading,
      @NonNull RecyclerView rvSearchQuery, @NonNull TextView searchQuery,
      @NonNull TextView searchResult) {
    this.rootView = rootView;
    this.loading = loading;
    this.rvSearchQuery = rvSearchQuery;
    this.searchQuery = searchQuery;
    this.searchResult = searchResult;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySearchBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_search, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySearchBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.loading;
      ProgressBar loading = ViewBindings.findChildViewById(rootView, id);
      if (loading == null) {
        break missingId;
      }

      id = R.id.rv_search_query;
      RecyclerView rvSearchQuery = ViewBindings.findChildViewById(rootView, id);
      if (rvSearchQuery == null) {
        break missingId;
      }

      id = R.id.search_query;
      TextView searchQuery = ViewBindings.findChildViewById(rootView, id);
      if (searchQuery == null) {
        break missingId;
      }

      id = R.id.search_result;
      TextView searchResult = ViewBindings.findChildViewById(rootView, id);
      if (searchResult == null) {
        break missingId;
      }

      return new ActivitySearchBinding((ConstraintLayout) rootView, loading, rvSearchQuery,
          searchQuery, searchResult);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
