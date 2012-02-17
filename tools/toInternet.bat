ECHO This fanciness is brought to you by Folders!
ECHO You may have to change the text in "Wireless Network Connection"

ECHO Resetting IP Address and Subnet Mask For DHCP
netsh int ip set address name = "Wireless Network Connection 4" source = dhcp

netsh int ip show config

pause