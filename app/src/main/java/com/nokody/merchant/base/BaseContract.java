package com.nokody.merchant.base;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

public class BaseContract {

    public interface View {

        void setLoading(boolean isLoading);

        void setError(@StringRes int errorHeaderRes,
                      @StringRes int errorTextRes,
                      @StringRes int errorActionRes,
                      @DrawableRes int errorIcon,
                      @Nullable android.view.View.OnClickListener errorActionListener);

        void setError(@StringRes int errorTextRes,
                      @StringRes int errorActionRes,
                      @DrawableRes int errorIcon,
                      android.view.View.OnClickListener errorActionListener);

        void setError(String textString,
                      String actionString,
                      Drawable errorIcon,
                      android.view.View.OnClickListener actionListener);

        void setError(String headerString,
                      String textString,
                      String actionString,
                      Drawable errorIcon,
                      android.view.View.OnClickListener actionListener);

        void restoreView();

        void setErrorDialog(@StringRes int message, @StringRes int positive, @StringRes int negative,
                            DialogInterface.OnClickListener positiveListener,
                            DialogInterface.OnClickListener negativeListener);

        void setErrorDialog(String message, String positive, String negative,
                            DialogInterface.OnClickListener positiveListener,
                            DialogInterface.OnClickListener negativeListener);

        void showSnackBar(String message);

        void showSnackBar(int resId);

        void showOneActionSnackBar(String message, android.view.View.OnClickListener action);

        void showOneActionSnackBar(int resourceId, android.view.View.OnClickListener action);

        void showOneActionSnackBar(String message, String actionString, android.view.View.OnClickListener action);

        void showOneActionSnackBar(int message, int actionResourceId, android.view.View.OnClickListener action);

        void showTwoActionsSnackBar(String message, android.view.View.OnClickListener okAction, android.view.View.OnClickListener cancelAction);

        void showTwoActionsSnackBar(int message, android.view.View.OnClickListener okAction, android.view.View.OnClickListener cancelAction);


        void showTwoActionsSnackBar(String message, String positiveActionString, String negativeActionString, android.view.View.OnClickListener okAction, android.view.View.OnClickListener cancelAction);

        void showTwoActionsSnackBar(int message, int positiveActionResourceId, int negativeActionResourceId, android.view.View.OnClickListener okAction, android.view.View.OnClickListener cancelAction);

        void showNoNetwork();

        boolean isAvailable();
    }

    public interface Presenter<V extends BaseContract.View> {
        void attachView(V view);
    }

}
