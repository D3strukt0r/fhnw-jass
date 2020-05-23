Key generation

https://gitlab.fhnw.ch/bradley.richards/java-projects/blob/master/src/chatroom/Howto_SSL_Certificates_in_Java.odt

```shell script
$ keytool -genkeypair -alias server -keystore server.keystore -keyalg RSA
$ keytool -export -alias server -keystore server.keystore -rfc -file key.cert
$ keytool -import -alias client -file key.cert -keystore client.keystore
```
