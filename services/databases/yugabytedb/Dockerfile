FROM centos:7

ARG BUILDPLATFORM
ARG TARGETPLATFORM

RUN echo "I am running on $BUILDPLATFORM, building for $TARGETPLATFORM"

RUN yum update -y
RUN yum groupinstall -y 'Development Tools'
RUN yum install -y ruby perl-Digest epel-release ccache git python2-pip python-devel python3 python3-pip python3-devel which
RUN yum install -y cmake3 ctest3 ninja-build libatomic
RUN yum install -y java-1.8.0-openjdk maven

RUN ln -s /usr/bin/cmake3 /usr/local/bin/cmake
RUN ln -s /usr/bin/ctest3 /usr/local/bin/ctest

RUN git clone --branch v2.13.0.1 https://github.com/yugabyte/yugabyte-db.git
WORKDIR /yugabyte-db

RUN if [ "$TARGETPLATFORM" = "linux/amd64" ] ; \
    then /bin/bash -c "./yb_build.sh --arch x86_64 release" ; \
    else /bin/bash -c "./yb_build.sh --arch arm64 release" ; \
    fi
