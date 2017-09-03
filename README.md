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
Boost 




Python driver for the jog dial writes output to the named pipe
might as well do the button press also

setup gpio for output and use a file writer to send 1 or 0

NamedPipeKeyInputSpike,
firm up test,
remove adapter code pushing up into test
    given i configure it to invoke foo on an a
    when i send a
    then foo is invoked
    
Complete test for "external timer" observable system using named pipe
 - extract the named pipe integration test out of current parsing of rotation button stuff