chown cesar:uucp /run/lock -R
chown cesar:uucp /var/lock -R

chmod 775 /run/lock -R
chmod 775 /var/lock -R

chmod 775 /dev/ttyACM0
chown cesar:uucp /dev/ttyACM0

rm /dev/ttyS0

ln -s /dev/ttyACM0 /dev/ttyS0
