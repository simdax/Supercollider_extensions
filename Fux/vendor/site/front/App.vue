<template>
<div id="app">
	<supercollider/>
  <home id="svg"/>
	<midi></midi>
</div>
</template>

<script >
import Home from './components/Home.vue';
import Supercollider from './components/supercollider.vue';
import Midi from './components/midi.vue';
import {mapGetters, mapActions} from 'vuex'

export default{
		components: {Home, Midi, Supercollider},
		mounted() { this.$store.dispatch("go") },
}
</script>

<style>
html, body, #app {
		width: 100%;
		height: 100%;
		user-drag: none; 
		user-select: none;
}
#svg{ width: 100%; height: 80%}
#app {
		font-family: 'Avenir', Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
}
</style>
