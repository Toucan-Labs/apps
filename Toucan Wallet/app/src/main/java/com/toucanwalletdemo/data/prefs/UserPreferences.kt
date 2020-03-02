package com.toucanwalletdemo.data.prefs

class UserPreferences: SharedPrefs("UserPreferences") {

    var shouldUserLoggedIn by sharedPrefs.boolean("shouldUserLoggedIn", true)
    var isUserRegistered by sharedPrefs.boolean("isUserRegistered", false)
    var isUserVerified by sharedPrefs.boolean("isUserVerified", false)
    var isUserClaimReward by sharedPrefs.boolean("isRewardClaimed", false)
    var acceptMessages by sharedPrefs.boolean("acceptMessages", false)

    var referralCode by sharedPrefs.string("referralCode", null)

    var authToken by sharedPrefs.string("authToken", null)
    var privateKey by sharedPrefs.string("privateKey", null)

    var reward by sharedPrefs.string("reward", null)

    var username by sharedPrefs.string("username", null)
    var email by sharedPrefs.string("email", null)
    var pin by sharedPrefs.string("pin", null)
}