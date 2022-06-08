<CsoundSynthesizer>

<CsOptions>
-iadc 
--nosound
</CsOptions>

<CsInstruments>
sr = 44100
ksmps = 32
nchnls = 1
0dbfs  = 1
pyinit
instr 1
pyruni {{
import thread
import os, sys
sys.path.append(os.getcwd())
from clap import ClapAnalyzer
import requests

def toggleLights():
	try:
		print("Toggling lights...") 
		url = "https://linole.duckdns.org:6102/api/services/light/toggle"
		headers = {
			"Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJiMDc4YTFhZGQwZmI0ZGNhYWRkNzBjMTlmMGVkNjQ0NCIsImlhdCI6MTU5NDMxNjEyNywiZXhwIjoxOTA5Njc2MTI3fQ.6RFGvRb-O2FBPhhXNGdAxhfGPILEYgt5UZBNveehjXI",
			"content-type": "application/json",
		}
		payload = {'entity_id': 'light.kitchen'} 
		response = requests.post(url, headers=headers, json=payload)
		print(response.text) 
	except:
		print("An exception occurred")
def clap_detected():
	print 'Clap detected'
def clap_sequence_detected():
	print 'Matching clap sequence detected!'
	thread.start_new_thread(toggleLights, ())

clap_analyzer = ClapAnalyzer(note_lengths=[0.25, 0.25])
clap_analyzer.on_clap(clap_detected)
clap_analyzer.on_clap_sequence(clap_sequence_detected)
}}

kLastRms init 0
kLastAttack init 0
iRmsDiffThreshold init .05
kTime times
aIn in
kRmsOrig rms aIn
kSmoothingFreq linseg 5, 1, 0.01 ;quicker smoothing to start with
kSmoothRms tonek kRmsOrig, kSmoothingFreq
kSmoothRms max kSmoothRms, 0.001
aNorm = 0.1 * aIn / a(kSmoothRms)
kRms rms aNorm
kRmsDiff = kRms - kLastRms

if (kRmsDiff > iRmsDiffThreshold && kTime - kLastAttack > 0.09) then
	kLastAttack times
	pycall "clap_analyzer.clap", kLastAttack
endif

out aNorm
kLastRms = kRms
endin
</CsInstruments>

<CsScore>
i 1 0 500
e
</CsScore>

</CsoundSynthesizer>