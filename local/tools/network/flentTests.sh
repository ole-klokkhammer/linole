#!/bin/sh

flent tcp_download -p totals -l 60 -H 192.168.1.210 -o download.png
flent tcp_upload -p totals -l 60 -H 192.168.1.210 -o upload.png
flent rrul -p ping_cdf -l 60 -H 192.168.1.210 -o cdf.png
flent rrul -p all_scaled -l 60 -H 192.168.1.210 -o all_scaled.png