central heating
rasp pi
tft display
240v relay
jog dial with push button
SDL


Heating boost - 30 / 60 / 90 / 120
Heating Mode - on / off / (timer) / external timer

Temp control - range, but first pass just a list of options

Edit timer - big gui thing


implementing Heating Mode will let me deploy the device


Main menu should look like:
On
Off
External Timer
Boost -> 30,60,90,120
Close



With this failing test (SelectingHeatingBoost.acceptingIncreasedBoostAmount),
I think what I want to do is move to having a generic Option only configure mechanism.
-Try and get rid of the FakeOption stuff, or at the very least remove FakeOption from our FakeModel.-
Maybe the current HeatingRange stuff needs to be an IncrementableOption ie more vague.
However this is good enough for now and is the only incrementable option
I probably dont want to pass in an option as a configuration "command" but rather pass in a choice.

Next up, lets get some of the model actually working!!
How about selecting ON