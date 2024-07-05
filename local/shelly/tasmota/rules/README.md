# https://tasmota.github.io/docs/Rules/#rule-trigger

Switch1#State	when a switch changes to state. Will not trigger if SwitchTopic is set.
use Switch1#state=0 and Switch1#state=1 for comparison, not =off or =on
0 = OFF
1 = ON
2 = TOGGLE
3 = HOLD (SwitchTopic 0 must be set for this to trigger)
4 = INC_DEC (increment or decrement dimmer)
5 = INV (change from increment to decrement dimmer and vice versa)
6 = CLEAR (button released for the time set with SetOption32)

## gotchas
- conditional statements needs tasmota compiled with additional parameters