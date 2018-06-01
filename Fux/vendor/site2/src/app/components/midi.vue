<template>
	<div>
		<button @click="play">play</button>
		<label> choose track</label>
		<select @input="$store.dispatch('getSVG', $event.target.value)">
			<option v-for="t in svgs">{{t}}</option> 
		</select>
	</div>
</template>

<script> 
import {mapActions, mapGetters} from 'vuex'
import play_midi from './play_midi'

let computed = mapGetters(['midi', 'svgs'])
let methods = mapActions(['allSVG'])

 methods.play = play_midi

export default{
		mounted(){this.allSVG()},
		computed, methods
}
</script>
