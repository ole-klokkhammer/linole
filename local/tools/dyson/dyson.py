
# http://aakira.hatenablog.com/entry/2016/08/12/012654

import os
import logging
logging.basicConfig()
logging.getLogger().setLevel(logging.DEBUG)
from libpurecool.dyson import DysonAccount
from dotenv import load_dotenv
load_dotenv()

_LOGGER = logging.getLogger(__name__)
_LOGGER.debug(os.getenv('USER_NAME'))
_LOGGER.debug(os.getenv('PASSWORD'))
_LOGGER.debug(os.getenv('LANG'))
dyson_account = DysonAccount(
    os.getenv('USER_NAME'),
    os.getenv('PASSWORD'),
    "GB",
)

logged = dyson_account.login()
if not logged:
    _LOGGER.error("Not connected to Dyson account. Unable to add devices")
    exit(1)

_LOGGER.info("Connected to Dyson account")
dyson_device = dyson_account.devices()[0]
_LOGGER.debug(dyson_device)
 
try:
    connected = connected = dyson_device.connect(os.getenv('DEVICE_IP')) 
    if connected:
        _LOGGER.info("Connected to device %s", dyson_device)
    else:
        _LOGGER.warning("Unable to connect to device %s", dyson_device)
except OSError as ose:
    _LOGGER.error(
        "Unable to connect to device %s: %s",
        str(dyson_device.network_device),
        str(ose),
    )


