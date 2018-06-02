var offset_note;

function move (ev) {
		let offset = ev.pageY - this.y;
		offset_note = parseInt(offset / this.height);
		let style = `transform: translateY(${offset_note * this.height}px);`
		this.stems[this.i].style = style
		this.el.style = style
}

function update (vue_instance, i, f) {
		let ff = function(e){
				vue_instance.update_score({offset_note, i})
				document.removeEventListener('mousemove', f);
				// remove itself
				document.removeEventListener('mouseup', ff);
		}
		return ff
}

function mousedown_callback(i, stems, vue_instance){
		return ev => {
				let f = move.bind({height: 2.5, y: ev.pageY, el: ev.target, i, stems});
				ev.target.setAttribute('fill', 'red')
				document.addEventListener('mousemove', f);
				document.addEventListener('mouseup', update(vue_instance, i, f));
		}
}

export default function (vue_instance) {
		const stems = document.querySelectorAll('.Stem');
		let notes = document.querySelectorAll('.Note')
		for (let i = 0; i < notes.length; i++) {
				notes[i].addEventListener('mousedown', mousedown_callback(i, stems, vue_instance))
		}			
}
