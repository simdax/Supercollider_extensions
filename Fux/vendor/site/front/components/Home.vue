<template lang="pug">
main
	#io(v-if="!ready") please, choose a file
	svg(v-else v-html="svg")
	p(style="visibility: hidden;") {{svg}}
</template>

<script>
import {mapActions, mapState, mapGetters} from 'vuex';
let methods = mapActions(['getSVG', 'update_score']);
let computed = mapGetters(['svg']);
import post_svg from "./mounted_hook"

export default {
		updated() {this.$nextTick(()=>{post_svg(this);
																	 this.ready = true})},
		computed,
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
