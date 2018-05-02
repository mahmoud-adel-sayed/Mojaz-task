package com.mojaz.assignment;

public interface BaseView {

    void showNoInternetConnection();

    void setLoadingIndicator(boolean active);

    boolean isActive();
}
