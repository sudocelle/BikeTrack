// Generated by view binder compiler. Do not edit!
package com.testproject.biketrack.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.testproject.biketrack.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySecondBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button bike1;

  @NonNull
  public final Button bike2;

  @NonNull
  public final Button bike3;

  @NonNull
  public final Button bike4;

  @NonNull
  public final Button goBack;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final TextView textView5;

  private ActivitySecondBinding(@NonNull ConstraintLayout rootView, @NonNull Button bike1,
      @NonNull Button bike2, @NonNull Button bike3, @NonNull Button bike4, @NonNull Button goBack,
      @NonNull ConstraintLayout main, @NonNull TextView textView5) {
    this.rootView = rootView;
    this.bike1 = bike1;
    this.bike2 = bike2;
    this.bike3 = bike3;
    this.bike4 = bike4;
    this.goBack = goBack;
    this.main = main;
    this.textView5 = textView5;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySecondBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySecondBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_second, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySecondBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bike_1;
      Button bike1 = ViewBindings.findChildViewById(rootView, id);
      if (bike1 == null) {
        break missingId;
      }

      id = R.id.bike_2;
      Button bike2 = ViewBindings.findChildViewById(rootView, id);
      if (bike2 == null) {
        break missingId;
      }

      id = R.id.bike_3;
      Button bike3 = ViewBindings.findChildViewById(rootView, id);
      if (bike3 == null) {
        break missingId;
      }

      id = R.id.bike_4;
      Button bike4 = ViewBindings.findChildViewById(rootView, id);
      if (bike4 == null) {
        break missingId;
      }

      id = R.id.go_back;
      Button goBack = ViewBindings.findChildViewById(rootView, id);
      if (goBack == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.textView5;
      TextView textView5 = ViewBindings.findChildViewById(rootView, id);
      if (textView5 == null) {
        break missingId;
      }

      return new ActivitySecondBinding((ConstraintLayout) rootView, bike1, bike2, bike3, bike4,
          goBack, main, textView5);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
