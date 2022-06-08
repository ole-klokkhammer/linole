
// https://github.com/b3ster/miflora-esp32S
// array of different xiaomi flora MAC addresses
char* FLORA_DEVICES[] = {
    "C4:7C:8D:6A:94:C8"
};

// sleep between to runs in seconds
#define SLEEP_DURATION 30 * 60

// emergency hibernate countdown in seconds
#define EMERGENCY_HIBERNATE 3 * 60

// how often should the battery be read - in run count
#define BATTERY_INTERVAL 6

// how often should a device be retried in a run when something fails
#define RETRY 3

const char*   WIFI_SSID       = "wireless@linole";
const char*   WIFI_PASSWORD   = "6sMkVsK5Z3YW";

// MQTT topic gets defined by "<MQTT_BASE_TOPIC>/<MAC_ADDRESS>/<property>"
// where MAC_ADDRESS is one of the values from FLORA_DEVICES array
// property is either temperature, moisture, conductivity, light or battery

const char*   MQTT_HOST       = "192.168.10.200";
const int     MQTT_PORT       = 6107;
const char*   MQTT_CLIENTID   = "miflora-client";
const char*   MQTT_USERNAME   = "root";
const int     MQTT_RETRY_WAIT = 5000;
