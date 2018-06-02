import Tone from 'tone'
import * as MidiConvert from 'midiconvert'

const synth = new Tone.PolySynth(8).toMaster()

export default function () {
		let midi = MidiConvert.parse(this.midi)
		// make sure you set the tempo before you schedule the events
		Tone.Transport.bpm.value = midi.header.bpm
		// pass in the note events from one of the tracks as the second argument to Tone.Part 
		var midiPart = new Tone.Part(function(time, note) {
				//use the events to play the synth
				synth.triggerAttackRelease(note.name, note.duration, time, note.velocity)
		}, midi.tracks[1].notes).start()
		// start the transport to hear the events
		Tone.Transport.start()
}
