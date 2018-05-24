// <![CDATA[

(async ()=>{
		let data
		let url = "http://localhost:5984/master_music/_design/harmo/_view/time";
		data = await fetch(url, {mode: 'cors'}).then(e => e.json()).then(e => e.rows);
		console.log(data);
		let svg = document.getElementsByTagName('svg')[0];
		let ns = "http://www.w3.org/2000/svg";
		function create_text(x, y, txt, size, color){	
				let text = document.createElementNS(ns, 'text');
				text.setAttribute("x", x);
				text.setAttribute("y", y);
				text.setAttribute("font-size", size);
				text.setAttribute("fill", color);
				text.innerHTML= txt;
				svg.appendChild(text);
		}
		let paths = document.getElementsByTagName('path');
		let j = 0;
		for (var i = paths.length - 1; i > -1; i--) {
				let c = paths[i].getAttribute('class');
				if (c === 'Note')
				{
		  			let rect = paths[i].getBoundingClientRect();
						let harmo = (data[j]) || "p";
//						console.log(j);
		  			create_text(rect.x, rect.y - 15, harmo.value, 10, "red");
						++j;
				}
		}	
})()

// ]]>
