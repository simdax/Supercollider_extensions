<template lang="pug">
img(@src="/tmp/29-05-201807-38_out.mid-1.svg)
</template>

<script lang="ts">
		import { Component, Prop, Vue } from 'vue-property-decorator';

@Component
export default class SSVG extends Vue {
	//	@Prop()
		private path: string = "/tmp/29-05-201807-38_out.mid-1.svg"
}

</script>
