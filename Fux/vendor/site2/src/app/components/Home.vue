<template lang="pug">
main
	#io(v-if="!ready") loading
	svg(v-else v-html="svg")
</template>

<script>
import {mapActions, mapState, mapGetters} from 'vuex';
let methods = mapActions(['getSVG', 'update_score']);
let computed = mapGetters(['svg']);
import {svg} from "./mounted_hook"

export default {
		props: ["svg_id"],
		mounted() {this.getSVG(this.svg_id).then(this.ready = true)},
		updated() {this.$nextTick(()=>{svg(this)})},
		data() {return {ready: false}},
		methods, computed,
}
</script>

<style>
	svg .Note{
	cursor: pointer;
}
</style>

<style scoped>
.home-container {
		text-align: center;
}
main, svg{
		height: 100%;
		width: 100%;
}
h1 {
		color: #2c3e50;
		margin-top: 60px;
}
</style>
