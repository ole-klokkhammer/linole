# Raspberry pi stuff 
https://siytek.com/raspberry-pi-rtsp-to-home-assistant/

## Camera option 1
https://github.com/mpromonet/v4l2rtspserver
v4l2rtspserver -W 1400 -H 1050 -F 15 -P 8554 /dev/video0

[Unit]
Description=V4L2 RTSP server
After=network.target

[Service]
Type=simple
Restart=always
RestartSec=5
ExecStartPre=/usr/bin/uv4l --driver raspicam --auto-video_nr --encoding h264
ExecStart=/usr/local/bin/v4l2rtspserver -W 1400 -H 1080 -F 10 -P 8554 /dev/video0
WorkingDirectory=/usr/local/share/v4l2rtspserver

[Install]
WantedBy=multi-user.target



## Camera option 2
https://www.linux-projects.org/uv4l
/etc/systemd/system/uv4l_raspicam.service
https://www.linux-projects.org/uv4l/tutorials/rtsp-server/
uv4l -f -k --sched-fifo --mem-lock --config-file=/etc/uv4l/uv4l-raspicam.conf --driver raspicam --driver-config-file=/etc/uv4l/uv4l-raspicam.conf --server-option=--editable-config-file=/etc/uv4l/uv4l-raspicam.conf

[Unit]
Description=UV4L Raspicam

[Service]
Type=simple
ExecStart=/usr/bin/uv4l -f -k --sched-rr --mem-lock --config-file=/etc/uv4l/uv4l-raspicam.conf --driver raspicam --driver-config-file=/etc/uv4l/uv4l-raspicam.conf --server-option=--editable-config-file=/etc/uv4l/uv4l-raspicam.conf
Restart=on-abnormal
LimitNOFILE=65536

[Install]
WantedBy=multi-user.target
 
## Camera option 3 (gstreamer)
https://einar.slaskete.net/2018/08/16/using-a-raspberry-pi-as-a-surveillance-camera-in-home-assistant/

[Unit]
Description=Raspberry Pi Camera Stream Service
After=network.target

[Service]
Type=simple
User=pi
ExecStartPre=/usr/bin/v4l2-ctl -c video_bitrate=4000000 -c auto_exposure=1 -c exposure_dynamic_framerate=1 -c exposure_time_absolute=150 -p 15
ExecStart=/usr/bin/gst-launch-1.0 v4l2src ! video/x-h264,width=1400,height=1048,framerate=10/1 ! h264parse ! matroskamux streamable=true ! tcpserversink host=::0 port=9000 sync=false sync-method=2

[Install]
WantedBy=multi-user.target

## Camera option 4 (ole gstreamer)
https://www.aeronetworks.ca/2018/04/raspberry-pi-video-streaming.html
https://raspberrypi.stackexchange.com/questions/26675/modern-way-to-stream-h-264-from-the-raspberry-cam

[Unit]
Description=Raspberry Pi Camera Stream Service
After=network.target

[Service]
Type=simple
User=pi
ExecStartPre=/usr/bin/v4l2-ctl --set-fmt-video=width=1920,height=1080,pixelformat=4 -p 15 -c video_bitrate=4000000 -c auto_exposure=1 -c exposure_time_absolute=100 -c brightness=50
ExecStart=/usr/bin/gst-launch-1.0 v4l2src ! video/x-h264, width=1920, height=1080, framerate=15/1 ! h264parse ! matroskamux streamable=true ! tcpserversink host=::0 port=9000 sync=false sync-method=2sync-method=2

[Install]
WantedBy=multi-user.target

## Camera option 5 
https://www.hardill.me.uk/wordpress/2020/05/18/raspberry-pi-streaming-camera/

v4l2-ctl -c video_bitrate=300000
ffmpeg -f video4linux2 -input_format h264 -video_size 640x360 -framerate 30 -i /dev/video0  -vcodec copy -an -f flv rtmp://192.168.1.96/show/pi