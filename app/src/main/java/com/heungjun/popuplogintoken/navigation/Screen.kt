package com.heungjun.popuplogintoken.navigation

sealed class Screen(val route: String) {
    object GeneralMemberLogin : Screen("general_member_login")
    object CorporateMemberLogin : Screen("corporate_member_login")
    object GeneralMemberSignUp : Screen("general_member_sign_up")
    object CorporateMemberSignUp : Screen("corporate_member_sign_up")
    object Home : Screen("home_screen")
    object LoginSelection : Screen("login_selection")
    object UserSignUp : Screen("user_signup")
    object CompanySignUp : Screen("company_signup")
}