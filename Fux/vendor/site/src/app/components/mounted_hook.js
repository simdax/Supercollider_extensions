export function svg (vue_instance) {
		let offset_note;
		let stems = document.querySelectorAll('.Stem');
		
		document.querySelectorAll('.Note').forEach((el, i) => {
				let move = function (ev) {
						let offset = ev.pageY - this.y;
						offset_note = parseInt(offset / this.height);
						let style = `transform: translateY(${offset_note * this.height}px);`
						stems[i].style = style
						this.el.style = style
				}
				el.addEventListener('mousedown', ev =>{
						//												let height = ev.target.getBoundingClientRect().height
						let f = move.bind({height: 2.5, y:ev.pageY, el:ev.target});
						ev.target.setAttribute('fill', 'red')
						document.addEventListener('mousemove', f);
						document.addEventListener('mouseup', ev => {
								vue_instance.update_score({offset_note, i})
								document.removeEventListener('mousemove', f);
						})
				})
		})					
}
