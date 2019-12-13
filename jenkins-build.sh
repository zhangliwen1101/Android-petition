#!/bin/sh

#echo ======================APK build======================
#gradle ${WORKSPACE}/platform/build.gradle;


echo ======================Move app to tomcat======================
#appDir="/tomcat-downloads/android-petition/"
#if [[ -d "$appDir" ]]; then
#  echo "$appDir exists, delete old apk first!"
#  rm -rf "$appDir"
#else
#  echo "$appDir not exists."
#fi
#
#mkdir "$appDir";
#\cp -r -a ${WORKSPACE}/platform/build/outputs/apk/*release.apk /tomcat-downloads/android-petition

echo "Upload the remote file downloader."
appDir="/tomcat-download/app/android-petition"
ssh root@10.16.3.12 'mkdir /tomcat-download/app/android-petition';
scp -r ${WORKSPACE}/platform/build/outputs/apk/*release.apk root@10.16.3.12:${appDir};


echo ======================Execute sonarqube======================
#gradle sonarqube;
/usr/local/gradle/gradle-3.3/bin/gradle sonarqube;


echo ======================jenkins-build finish======================
