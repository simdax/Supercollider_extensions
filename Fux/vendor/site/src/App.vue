<template>
<div id="app">
  <home id="svg" :svg_id="current_id"/>
	<label> choose track</label>
	<input name="choose ?" type="text" />
	<datalist>
		<option v-for="t in track_ids">{{t}}</option>
	</datalist>
</div>
</template>

<script >
import Home from './app/components/Home.vue';
import {mapGetters} from 'vuex'

export default{
		data () { return {current_id: 1 }},
		computed: mapGetters(['track_ids']),
		components: {
				Home
		}
}
</script>

<style>
html, body, #app {
		width: 100%;
		height: 100%;	
}
#svg{ width: 90%; height: 90%}
#app {
		font-family: 'Avenir', Helvetica, Arial, sans-serif;
		-webkit-font-smoothing: antialiased;
		-moz-osx-font-smoothing: grayscale;
}
</style>
