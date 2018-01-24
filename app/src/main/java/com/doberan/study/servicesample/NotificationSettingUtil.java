package com.doberan.study.servicesample;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/*
* This software is contributed or developed by KYOCERA Corporation.
* (C) 2018 KYOCERA Corporation
*/
public class NotificationSettingUtil {
    // 通知設定トグル変数
    private static boolean allowNotification;
    // 保存用の設定ファイルインスタンス
    private static SharedPreferences sharedPreferences = null;
    // 通知更新時間設定変数
    private static int updateNotificationTime;
    // 次回ダイアログ表示フラグ
    private static boolean nextDisplayShowFlag;

    /**
     * 設定取得用メソッド
     * 設定ファイルから各情報を取得する
     */
    public static void confirmNotification(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        allowNotification = sharedPreferences.getBoolean("is_notification", true);
        updateNotificationTime = sharedPreferences.getInt("update_notice_time", 1);
        nextDisplayShowFlag = sharedPreferences.getBoolean("next_display_show_flag", false);
    }

    /**
     * 設定ファイルを参照しているかチェックする
     * @return 参照していた場合はfalseを返却する
     */
    public static boolean isEmptySharedPreferences(){
        if(NotificationSettingUtil.sharedPreferences == null){
            return true;
        }
        return false;
    }

    /**
     * 通知設定情報のゲッター
     * @return allowNotification
     */
    public static boolean getAllowNotification(){
        return NotificationSettingUtil.allowNotification;
    }

    /**
     * 更新時間設定情報のゲッター
     * @return updateNotificationTime
     */
    public static int getUpdateNotificationTime(){
        return NotificationSettingUtil.updateNotificationTime;
    }

    /**
     * 次回ダイアログ表示フラッグ情報のゲッター
     * @return nextDisplayShowFlag
     */
    public static boolean getNextDisplayShowFlag(){
        return NotificationSettingUtil.nextDisplayShowFlag;
    }


    /**
     * 設定ファイルとメンバ変数に通知設定の情報をセットする
     * @param allowNotification
     */
    public static void setAllowNotification(boolean allowNotification){
        NotificationSettingUtil.allowNotification = allowNotification;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("is_notification", allowNotification);
        editor.commit();
    }

    /**
     * 設定ファイルとメンバ変数に更新時間設定の情報をセットする
     * @param updateNotificationTime
     */
    public static void setUpdateNotificationTime(int updateNotificationTime){
        NotificationSettingUtil.updateNotificationTime = updateNotificationTime;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("update_notice_time", updateNotificationTime);
        editor.commit();
    }

    /**
     * 次回ダイアログ表示フラッグ情報をセットする
     * @param nextDisplayShowFlag
     */
    public static void setNextDisplayShowFlag(boolean nextDisplayShowFlag){
        NotificationSettingUtil.nextDisplayShowFlag = nextDisplayShowFlag;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("next_display_show_flag", nextDisplayShowFlag);
        editor.commit();
    }
}
