<template>
<div>
	<button @click="click">{{msg}}</button>
	<label> choose track</label>
	<select @input="$store.dispatch('getSVG', $event.target.value)">
		<option v-for="t in svgs">{{t}}</option> 
	</select>
</div>
</template>

<script> 
	import {mapActions, mapGetters} from 'vuex'
	import {stop_midi, play_midi} from './play_midi'
	
	let computed = mapGetters(['midi', 'svgs'])
	let methods = mapActions(['allSVG'])
	computed.msg = function(){return this.is_play ? "stop" : "play"}
	methods.play = play_midi
	methods.stop = stop_midi
	methods.click = function(){
	if(!this.is_play)
	this.play();
	else
	this.stop();
	this.is_play=!this.is_play
	}
	export default{
	data(){ return {is_play:false}},
	mounted(){ this.allSVG() },
	computed, methods
	}
</script>
