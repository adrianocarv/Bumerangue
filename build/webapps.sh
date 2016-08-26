rm /opt/apache-tomcat-7.0.42/webapps/bumerangue-ceus/ -r -f
mkdir /opt/apache-tomcat-7.0.42/webapps/bumerangue-ceus
tar -xz --directory=/opt/apache-tomcat-7.0.42/webapps/bumerangue-ceus -f bumerangue-ceus.tar.gz
cp -r lib /opt/apache-tomcat-7.0.42/webapps/bumerangue-ceus/WEB-INF

rm /opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-circulante/ -r -f
mkdir /opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-circulante
tar -xz --directory=/opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-circulante -f bumerangue-cce-circulante.tar.gz
cp -r lib /opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-circulante/WEB-INF

rm /opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-literatura/ -r -f
mkdir /opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-literatura
tar -xz --directory=/opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-literatura -f bumerangue-cce-literatura.tar.gz
cp -r lib /opt/apache-tomcat-7.0.42/webapps/bumerangue-cce-literatura/WEB-INF
