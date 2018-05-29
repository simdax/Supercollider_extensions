<template lang="pug">
	main
		#io(v-if="!ready") On attend connard, OK ?
		svg(v-else v-html="svg")
</template>

<script>
import {mapActions, mapState, mapGetters} from 'vuex';
let methods = mapActions(['getSVG']);
let computed = mapGetters(['svg']);

export default {
		methods, computed,
		data() {return {ready: false}},
		mounted() {
				this.getSVG(1).then( () => {
						// this.ready = true;
						// document.querySelectorAll('.Note').forEach(el => {
						// 		console.log(el)
						// 		let move = function (ev) {
						// 				let offset = ev.pageY - this.y
						// 				console.log(ev.pageY, this.y, offset)
						// 				this.el.style = `transform: translateY(${Math.floor(offset / this.height)}px);`
						// 		}
						// 		el.addEventListener('mousedown', ev =>{
						// 				console.log("pute")
						// 				let height = ev.target.getBoundingClientRect().height
						// 				let f = move.bind({height, y:ev.pageY, el:ev.target});
						// 				ev.target.setAttribute('fill', 'red')
						// 				document.addEventListener('mousemove', f);
						// 				document.addEventListener('mouseup', ev =>{
						// 						document.removeEventListener('mousemove', f);
						// 				})
						// 		})	
//					})					
				})
		}
}
</script>

<style scoped>
.Note{
		fill: red;
		cursor: pointer;
}
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
