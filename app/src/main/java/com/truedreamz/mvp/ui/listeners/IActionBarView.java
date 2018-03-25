package com.truedreamz.mvp.ui.listeners;


public interface IActionBarView {

    void setUpIconVisibility(boolean visible);

    void setTitle(String titleKey);

    void setSettingsIconVisibility(boolean visibility);

    void setRefreshVisibility(boolean visibility);
}
