// Generated by view binder compiler. Do not edit!
package com.example.beansbay.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.beansbay.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemCheckoutResultBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final ImageView IVgambarproduk;

  @NonNull
  public final TextView TVhargatotal;

  @NonNull
  public final TextView TVnameproduk;

  @NonNull
  public final TextView TVqty;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final TextView teksqty;

  private ItemCheckoutResultBinding(@NonNull CardView rootView, @NonNull ImageView IVgambarproduk,
      @NonNull TextView TVhargatotal, @NonNull TextView TVnameproduk, @NonNull TextView TVqty,
      @NonNull CardView cardView, @NonNull TextView teksqty) {
    this.rootView = rootView;
    this.IVgambarproduk = IVgambarproduk;
    this.TVhargatotal = TVhargatotal;
    this.TVnameproduk = TVnameproduk;
    this.TVqty = TVqty;
    this.cardView = cardView;
    this.teksqty = teksqty;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemCheckoutResultBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemCheckoutResultBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_checkout_result, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemCheckoutResultBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.IVgambarproduk;
      ImageView IVgambarproduk = ViewBindings.findChildViewById(rootView, id);
      if (IVgambarproduk == null) {
        break missingId;
      }

      id = R.id.TVhargatotal;
      TextView TVhargatotal = ViewBindings.findChildViewById(rootView, id);
      if (TVhargatotal == null) {
        break missingId;
      }

      id = R.id.TVnameproduk;
      TextView TVnameproduk = ViewBindings.findChildViewById(rootView, id);
      if (TVnameproduk == null) {
        break missingId;
      }

      id = R.id.TVqty;
      TextView TVqty = ViewBindings.findChildViewById(rootView, id);
      if (TVqty == null) {
        break missingId;
      }

      CardView cardView = (CardView) rootView;

      id = R.id.teksqty;
      TextView teksqty = ViewBindings.findChildViewById(rootView, id);
      if (teksqty == null) {
        break missingId;
      }

      return new ItemCheckoutResultBinding((CardView) rootView, IVgambarproduk, TVhargatotal,
          TVnameproduk, TVqty, cardView, teksqty);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
