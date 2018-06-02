<template>
<div>
	<select v-if="supercollider == 1"  v-model="msg">
		<option v-for="k in msgs">{{k}}</option>
	</select>
	<input type.number="text"/>
	<button v-if="supercollider == 1"
					@click="click"
					v-html="yes" />
	<p v-else-if="supercollider == 0" v-html="searching"/>
	<p v-else v-html="no"/>
</div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex'

// must return an array of args
let msgs= (v) => {
		return {
				prout (){ return ['' + v.current]},
				new_track (){return [3]},
				play (){return [v.current]},
				patch (){return [v.current]},
		}
}
export default {
		methods: {
				click(){
						this.$store.dispatch('supercollider_msg',
																 {name: this.msg,
																	args: msgs(this)[this.msg]()})						
				}
		},
		data(){ return {
				msgs: Object.keys(msgs()),
				msg: "",
				searching: "searching for SuperCollider",
				yes: "go",
				no: "You don't seem to have Supercollider installed, or at least accessible. Check <a href='https://supercollider.github.io'>this</a>."
		}},
		computed: mapGetters(['supercollider', 'current']),
}
</script>
