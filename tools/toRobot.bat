@ECHO THIS NEEDS TO BE TUNED FOR EACH COMPUTER. THE NAME OF THIS COMPUTER IS ... NEEDS TO BE MODIFIED
@ECHO This fanciness is brought to you by Folders!
@ECHO You may have to change the text in "Wireless Network Connection"

@REM set /P lastnumberofip=ENTER_LAST_DIGIT_OF_IP_YOU_WANT_TO_USE:

@set varip=10.6.94.%lastnumberofip%
@ECHO ip address %varip%
@set varsm=255.0.0.0

@REM For the most up-to-date-info, visit http://samanathon.com/set-your-ip-address-via-batch-file/

@ECHO Setting IP Address and Subnet Mask
@netsh int ip set address name = "Wireless Network Connection 4" source = static addr = %varip% mask = %varsm%

@netsh int ip show config

@pause