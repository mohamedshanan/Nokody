package com.nokody.merchant.base;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nokody.merchant.R;
import com.nokody.merchant.utils.Utilities;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements BaseContract.View {

    private Unbinder unbinder;
    protected static Navigator mNavigator;
    private ViewGroup fragmentView;
    private ProgressBar progressBar;
    private LinearLayout errorView;
    private TextView errorHeader;
    private TextView errorText;
    private ImageView errorIcon;
    private AppCompatButton errorAction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(getHasOptionsMenu());
        View view = inflater.inflate(R.layout.layout_base, container, false);
        Utilities.loadLanguage(getActivity());
        mNavigator = new Navigator();
        mNavigator.setContext(getActivity());

        RelativeLayout baseView = view.findViewById(R.id.layout_base_view);
        progressBar = view.findViewById(R.id.layout_progress_bar);
        errorView = view.findViewById(R.id.layout_error_view);
        errorHeader = view.findViewById(R.id.layout_error_header);
        errorText = view.findViewById(R.id.layout_error_text);
        errorIcon = view.findViewById(R.id.layout_error_image);
        errorAction = view.findViewById(R.id.layout_error_action);
        fragmentView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(getFragmentView(), container, false);

        baseView.addView(fragmentView);
        ButterKnife.bind(this, baseView);
        afterInflation(savedInstanceState);
        return view;
    }

    protected boolean getHasOptionsMenu() {
        return false;
    }

    protected abstract void afterInflation(Bundle savedInstanceState);

    protected abstract int getFragmentView();

    @Override
    public void setLoading(boolean isLoading) {
        if (isAdded()) {
            errorView.setVisibility(View.GONE);
            if (isLoading) {
                progressBar.setVisibility(View.VISIBLE);
                fragmentView.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.GONE);
                fragmentView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setError(@StringRes int errorTextRes,
                         @StringRes int errorActionRes,
                         @DrawableRes int errorIcon,
                         @Nullable View.OnClickListener errorActionListener) {
        if (isAdded()) {
            String errorActionString = errorActionRes == 0 ? null : getResources().getString(errorActionRes);
            Drawable errorActionIcon = errorIcon == 0 ? null : ContextCompat.getDrawable(getActivity(), errorIcon);
            setError(getResources().getString(errorTextRes),
                    errorActionString,
                    errorActionIcon,
                    errorActionListener);
        }
    }

    @Override
    public void setError(String textString, @Nullable String actionString, Drawable icon, @Nullable View.OnClickListener actionListener) {
        if (isAdded()) {
            progressBar.setVisibility(View.GONE);
            fragmentView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);

            if (actionString != null) {
                errorAction.setOnClickListener(actionListener);
                errorAction.setVisibility(View.VISIBLE);
            }
            if (icon != null) {
                errorIcon.setImageDrawable(icon);
            }
            errorAction.setText(actionString);
            errorText.setText(textString);
        }
    }

    @Override
    public void setError(@StringRes int errorHeaderRes,
                         @StringRes int errorTextRes,
                         @StringRes int errorActionRes,
                         @DrawableRes int errorIcon,
                         @Nullable View.OnClickListener errorActionListener) {
        if (isAdded()) {
            String errorActionString = errorActionRes == 0 ? null : getResources().getString(errorActionRes);
            Drawable errorActionIcon = errorIcon == 0 ? null : ContextCompat.getDrawable(getActivity(), errorIcon);
            setError(getResources().getString(errorHeaderRes), getResources().getString(errorTextRes),
                    errorActionString,
                    errorActionIcon,
                    errorActionListener);
        }
    }

    public void setError(String headerString, String textString, @Nullable String actionString, Drawable icon, @Nullable View.OnClickListener actionListener) {
        if (isAdded()) {
            progressBar.setVisibility(View.GONE);
            fragmentView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);

            if (actionString != null) {
                errorAction.setOnClickListener(actionListener);
                errorAction.setVisibility(View.VISIBLE);
            }
            if (icon != null) {
                errorIcon.setImageDrawable(icon);
            }
            errorAction.setText(actionString);
            errorHeader.setText(headerString);
            errorText.setText(textString);
        }
    }

    public void setViewAfterErrorShown() {
        progressBar.setVisibility(View.GONE);
        fragmentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }


    @Override
    public void restoreView() {
        progressBar.setVisibility(View.GONE);
        fragmentView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void setErrorDialog(@StringRes int message, @StringRes int positive, @StringRes int negative, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        if (isAdded()) {
            String errorMessage = getResources().getString(message);
            String errorPositive = positive == 0 ? null : getResources().getString(positive);
            String errorNegative = negative == 0 ? null : getResources().getString(negative);
            setErrorDialog(errorMessage, errorPositive, errorNegative, positiveListener, negativeListener);
        }

    }

    @Override
    public void setErrorDialog(String message, String positive, String negative, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        if (isAdded()) {
            AlertDialog errorDialog = new AlertDialog.Builder(getActivity()).create();
            errorDialog.setCancelable(false);
            errorDialog.setMessage(message);
            if (positive != null && positiveListener != null) {
                errorDialog.setButton(DialogInterface.BUTTON_POSITIVE, positive, positiveListener);
            }
            if (negative != null && negativeListener != null) {
                errorDialog.setButton(DialogInterface.BUTTON_NEGATIVE, negative, negativeListener);
            }
            if (!errorDialog.isShowing()) {
                errorDialog.show();
            }
        }
    }

    @Override
    public void showNoNetwork() {

    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackBar(int resId) {
        Snackbar.make(fragmentView, resId, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showOneActionSnackBar(String message, android.view.View.OnClickListener action) {
        showOneActionSnackBar(message, getString(R.string.ok), action);
    }

    @Override
    public void showOneActionSnackBar(int resourceId, android.view.View.OnClickListener action) {
        showOneActionSnackBar(resourceId, R.string.ok, action);
    }

    @Override
    public void showOneActionSnackBar(String message, String okActionString, View.OnClickListener action) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_LONG)
                .setAction(okActionString, action)
                .show();
    }

    @Override
    public void showOneActionSnackBar(int resId, int okActionResID, View.OnClickListener action) {
        Snackbar.make(fragmentView, resId, Snackbar.LENGTH_LONG)
                .setAction(okActionResID, action)
                .show();
    }

    @Override
    public void showTwoActionsSnackBar(String message, android.view.View.OnClickListener positiveAction, android.view.View.OnClickListener negativeAction) {
        showTwoActionsSnackBar(message, getString(R.string.ok), getString(R.string.cancel), positiveAction, negativeAction);
    }

    @Override
    public void showTwoActionsSnackBar(int resourceId, android.view.View.OnClickListener positiveAction, android.view.View.OnClickListener negativeAction) {
        showTwoActionsSnackBar(resourceId, R.string.ok, R.string.cancel, positiveAction, negativeAction);
    }

    @Override
    public void showTwoActionsSnackBar(String message, String positiveActionString, String negativeActionString, View.OnClickListener okAction, View.OnClickListener cancelAction) {
        Snackbar.make(fragmentView, message, Snackbar.LENGTH_LONG)
                .setAction(positiveActionString, okAction)
                .setAction(negativeActionString, cancelAction)
                .show();
    }

    @Override
    public void showTwoActionsSnackBar(int resId, int positiveActionResId, int negativeActionResId, View.OnClickListener okAction, View.OnClickListener cancelAction) {
        Snackbar.make(fragmentView, resId, Snackbar.LENGTH_LONG)
                .setAction(positiveActionResId, okAction)
                .setAction(negativeActionResId, cancelAction)
                .show();
    }

    @Override
    public boolean isAvailable() {
        return isAdded();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
