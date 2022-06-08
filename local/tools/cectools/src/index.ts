import express from "express";
import {CECMonitor} from "./cec/CECMonitor";

// ref1: https://github.com/jvanharn/node-hdmi-cec
// ref2: https://github.com/patlux/node-cec

const monitor = new CECMonitor();

process.on('SIGINT', () => {
  if (monitor != null) {
    monitor.stop();
  }
  process.exit();
});

monitor.start();
express().listen(8080);
