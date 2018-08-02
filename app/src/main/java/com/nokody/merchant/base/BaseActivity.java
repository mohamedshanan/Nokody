package com.nokody.merchant.base;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nokody.merchant.App;
import com.nokody.merchant.R;
import com.nokody.merchant.utils.Utilities;
import com.nokody.merchant.views.splash.SplashActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

    private Unbinder unbinder;
    protected static Navigator mNavigator;
    public Toolbar toolbar;
    private ViewGroup activityView;
    private RelativeLayout baseView;
    private ProgressBar progressBar;
    private LinearLayout errorView;
    private LinearLayout networkView;
    private TextView errorHeader;
    private TextView errorText;
    private ImageView errorIcon;
    private AppCompatButton errorAction;
    private AlertDialog errorDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mNavigator = new Navigator();
        mNavigator.setContext(getApplicationContext());

        setContentView(R.layout.activity_base);
        baseView = findViewById(R.id.layout_base_view);
        progressBar = findViewById(R.id.layout_progress_bar);
        errorView = findViewById(R.id.layout_error_view);
        errorHeader = findViewById(R.id.layout_error_header);
        errorText = findViewById(R.id.layout_error_text);
        errorIcon = findViewById(R.id.layout_error_image);
        errorAction = findViewById(R.id.layout_error_action);
        networkView = findViewById(R.id.layout_no_network_view);
        toolbar = findViewById(R.id.toolbar);

        activityView = (ViewGroup) LayoutInflater.from(this).inflate(getActivityView(), baseView, false);

        baseView.addView(activityView);
        unbinder = ButterKnife.bind(this, baseView);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (getToolbarTitleResource() != 0) {
                getSupportActionBar().setTitle(getToolbarTitleResource());
            } else {
                getSupportActionBar().hide();
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUpEnabled());
            toolbar.setNavigationOnClickListener(v -> onBackPressed());
        }
        if (!(this instanceof SplashActivity)){
            checkNetWork();
        }
        afterInflation(savedInstanceState);
    }


    protected void checkNetWork() {
        if (!Utilities.getNetworkState(this)) {
            showNoConnection();
        }
    }

    protected void showNoConnection() {
        showSnackBar(R.string.no_connection);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return false;
        }

        return super.onOptionsItemSelected(item);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    protected abstract int getActivityView();

    protected abstract int getToolbarTitleResource();

    protected abstract boolean isHomeAsUpEnabled();

    protected abstract void afterInflation(Bundle savedInstance);

    @Override
    public void setLoading(boolean isLoading) {
        errorView.setVisibility(View.GONE);
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
            activityView.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            activityView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setError(@StringRes int errorTextRes,
                         @StringRes int errorActionRes,
                         @DrawableRes int errorIcon,
                         View.OnClickListener errorActionListener) {
        String errorActionString = errorActionRes == 0 ? null : getResources().getString(errorActionRes);
        Drawable errorActionIcon = errorIcon == 0 ? null : ContextCompat.getDrawable(this, errorIcon);
        setError(getResources().getString(errorTextRes),
                errorActionString,
                errorActionIcon,
                errorActionListener);
    }

    @Override
    public void setError(String textString, String actionString, Drawable icon,
                         View.OnClickListener actionListener) {
        progressBar.setVisibility(View.GONE);
        activityView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
        if (actionString != null) {
            errorAction.setOnClickListener(actionListener);
            errorAction.setVisibility(View.VISIBLE);
        }
        if (icon != null) {
            errorIcon.setImageDrawable(icon);
        }
        errorText.setText(textString);
        errorAction.setText(actionString);
    }

    @Override
    public void setError(@StringRes int errorHeaderRes,
                         @StringRes int errorTextRes,
                         @StringRes int errorActionRes,
                         @DrawableRes int errorIcon,
                         @Nullable View.OnClickListener errorActionListener) {

        String errorActionString = errorActionRes == 0 ? null : getResources().getString(errorActionRes);
        Drawable errorActionIcon = errorIcon == 0 ? null : ContextCompat.getDrawable(this, errorIcon);
        setError(getResources().getString(errorHeaderRes), getResources().getString(errorTextRes),
                errorActionString,
                errorActionIcon,
                errorActionListener);
    }

    public void setError(String headerString, String textString, @Nullable String actionString, Drawable icon, @Nullable View.OnClickListener actionListener) {

        progressBar.setVisibility(View.GONE);
        activityView.setVisibility(View.GONE);
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

    @Override
    public void restoreView() {
        progressBar.setVisibility(View.GONE);
        activityView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override
    public void setErrorDialog(@StringRes int message, @StringRes int positive, @StringRes int negative, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        String errorMessage = getResources().getString(message);
        String errorPositive = positive == 0 ? null : getResources().getString(positive);
        String errorNegative = negative == 0 ? null : getResources().getString(negative);
        setErrorDialog(errorMessage, errorPositive, errorNegative, positiveListener, negativeListener);

    }

    @Override
    public void setErrorDialog(String message, String positive, String negative, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {
        errorDialog = new AlertDialog.Builder(this).create();
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

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(baseView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showSnackBar(int resId) {
        Snackbar.make(baseView, resId, Snackbar.LENGTH_LONG).show();
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
        Snackbar.make(baseView, message, Snackbar.LENGTH_LONG)
                .setAction(okActionString, action)
                .show();
    }

    @Override
    public void showOneActionSnackBar(int resId, int okActionResID, View.OnClickListener action) {
        Snackbar.make(baseView, resId, Snackbar.LENGTH_LONG)
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
        Snackbar.make(baseView, message, Snackbar.LENGTH_LONG)
                .setAction(positiveActionString, okAction)
                .show();
    }

    @Override
    public void showTwoActionsSnackBar(int resId, int positiveActionResId, int negativeActionResId, View.OnClickListener okAction, View.OnClickListener cancelAction) {
        Snackbar.make(baseView, resId, Snackbar.LENGTH_LONG)
                .setAction(positiveActionResId, okAction)
                .show();
    }

    @Override
    public void showNoNetwork() {
        networkView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isAvailable() {
        return !(isFinishing() || isDestroyed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
