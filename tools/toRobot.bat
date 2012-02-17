ECHO This fanciness is brought to you by Folders!
ECHO You may have to change the text in "Wireless Network Connection"

set varip=10.6.94.30
set varsm=255.0.0.0

REM For the most up-to-date-info, visit http://samanathon.com/set-your-ip-address-via-batch-file/

ECHO Setting IP Address and Subnet Mask
netsh int ip set address name = "Wireless Network Connection 4" source = static addr = %varip% mask = %varsm%

netsh int ip show config

pause