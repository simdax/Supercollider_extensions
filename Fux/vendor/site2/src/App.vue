<template>
<div id="app">
  <home id="svg" :svg_id="current_id"/>
	<midi></midi>
</div>
</template>

<script >
import OSC from 'osc-js'
//const osc = new OSC({
// plugin: new OSC.DatagramPlugin({ send: { port: 57120 } })
//});
console.log("fdsff")
const osc = new OSC()
osc.open({port: 57120})
const message = new OSC.Message('/test/path', 521.25, 'teststring', 665);
osc.send(message);
console.log("io")
import Home from './app/components/Home.vue';
import Midi from './app/components/midi.vue';
import {mapGetters} from 'vuex'

export default{
		data () { return {current_id: 1 }},
		computed: mapGetters(['track_ids']),
		components: {Home, Midi}
}
</script>

<style>
html, body, #app {
		width: 100%;
		height: 100%;
		user-drag: none; 
		user-select: none;
		-moz-user-select: none;
		-webkit-user-drag: none;
		-webkit-user-select: none;
		-ms-user-select: none;
}
#svg{ width: 90%; height: 90%}
#app {
		font-family: 'Avenir', Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
}
</style>
