chown cesar:uucp /run/lock -R
chown cesar:uucp /var/lock -R

chmod 775 /run/lock -R
chmod 775 /var/lock -R

chmod 775 /dev/ttyACM1
chown cesar:uucp /dev/ttyACM1

rm /dev/ttyS0

ln -s /dev/ttyACM1 /dev/ttyS0
