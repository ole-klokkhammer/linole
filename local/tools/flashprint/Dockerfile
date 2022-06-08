# Base docker image
FROM debian:stretch
MAINTAINER Jesper Henriksen <mail-docker@jesper.io>

# Skips the License agreement. Make sure you actually read it
RUN echo "flashprint flashforge/title note "|debconf-set-selections

RUN apt-get update
# Install wget so we can download the package
RUN apt-get install --yes --no-install-recommends wget

# FlashPrint itself finds info about version updates here:
# http://www.ishare3d.com/3dapp/public/FlashPrint-ii/appInfo1
# You'll get a link to the changelog in the introUrl field

# You can also find later versions here, but FlashForge doesn't always update the page:
# http://www.flashforge.com/support-center/flashprint-support/
COPY ./flashprint.deb /tmp/flashprint.deb
RUN apt-get update --fix-missing
RUN apt-get install --yes --fix-missing /tmp/flashprint.deb 
RUN rm -rf /tmp/flashprint.deb /var/lib/apt/lists/* /tmp/* /var/tmp/*
RUN apt-get clean

RUN adduser --disabled-password --gecos '' --home /home/flashprint flashprint
USER flashprint
CMD ["/usr/share/FlashPrint/FlashPrint"]
