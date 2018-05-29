<template>
  <div id="app">
    <home msg="Vue.js starter kit with Parcel + Typescript"/>
  </div>
</template>

<script >
import Home from './app/components/Home.vue';

 export default{
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
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
