<template lang="pug">
main
	#io(v-if="!ready") please, choose a file
	svg(ref=svg v-html="svg")
	p(style="visibility: hidden;") {{svg}}
</template>

<script>
import {mapActions, mapState, mapGetters} from 'vuex';
import post_svg from "./mounted_hook"
let computed = mapGetters(['svg']);
let methods = mapActions(['getSVG', 'update_score']);

export default {
		data() {return {ready: false}},
		updated() {
				this.$nextTick(()=>{
						if(!this.ready && this.svg)
						{
								post_svg(this);
								this.ready = true
						}
				})
		},
		computed,
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
