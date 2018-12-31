FROM ubuntu:16.04

MAINTAINER shixiaochuan <abcd63092@gmail.com>

RUN \
    echo UPDATING && apt-get update

RUN \
    apt-get install -y software-properties-common wget vim openssh-server 

# Install Java
RUN \
  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
  add-apt-repository -y ppa:webupd8team/java && \
  apt-get update && \
  apt-get install -y oracle-java8-installer && \
  rm -rf /var/lib/apt/lists/* && \
  rm -rf /var/cache/oracle-jdk8-installer

# Define commonly used JAVA_HOME variable
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

RUN \
    echo INSTALL SCALA
RUN mkdir /opt/apps/ -p
WORKDIR /opt/apps
ADD http://downloads.lightbend.com/scala/2.11.8/scala-2.11.8.tgz /opt/apps/scala-2.11.8.tgz
RUN tar -xvzf scala-2.11.8.tgz -C /opt/apps
ENV SCALA_HOME /opt/apps/scala-2.11.8/

RUN \
    echo INSTALL SBT.
RUN mkdir /root/bin -p
ADD https://repo.typesafe.com/typesafe/ivy-releases/org.scala-sbt/sbt-launch/0.13.13/sbt-launch.jar /root/bin/sbt-launch.jar
WORKDIR /root
ADD sbt /root/bin/sbt
RUN chmod u+x /root/bin/sbt

# Start spark
#CMD ["/opt/apps/spark-2.0.1-bin-hadoop2.7/sbin/start-all.sh"] 