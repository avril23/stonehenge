FROM basebox:0.1

MAINTAINER shixiaochuan <shixc@zetyun.com>

# Create workdir
RUN mkdir /opt/apps/ -p
WORKDIR /opt/apps

# Install Spark
RUN echo INSTALLING SPARK
ADD http://d3kbcqa49mib13.cloudfront.net/spark-2.1.0-bin-hadoop2.7.tgz /opt/apps/spark-2.1.0-bin-hadoop2.7.tgz
RUN tar -xvzf spark-2.1.0-bin-hadoop2.7.tgz -C /opt/apps/
ADD ./spark-env.sh /opt/apps/spark-2.1.0-bin-hadoop2.7/conf/spark-env.sh
ENV SPARK_HOME /opt/apps/spark-2.1.0-bin-hadoop2.7
ENV SPARK_MASTER_HOST 0.0.0.0

# Install Hadoop
RUN echo INSTALLING HADOOP
ADD http://www.apache.org/dyn/closer.cgi/hadoop/common/hadoop-2.7.3/hadoop-2.7.3.tar.gz /opt/apps/hadoop-2.7.3.tar.gz
RUN tar -xvf hadoop-2.7.3.tar.gz -C /opt/apps/
ENV HADOOP_HOME /opt/apps/hadoop-2.7.3

# Config and start SSH

RUN mkdir /root/.ssh/ -p
RUN mkdir /var/run/sshd -p
WORKDIR /root/.ssh/
ADD id_rsa /root/.ssh/id_rsa
ADD id_rsa.pub /root/.ssh/id_rsa.pub
ADD sshd_config /etc/ssh/sshd_config
RUN cat /root/.ssh/id_rsa.pub >> /root/.ssh/authorized_keys
EXPOSE 22

ADD start.sh /root/start.sh
RUN chmod 777 /root/start.sh

# Start all services.
CMD ["/root/start.sh"] 