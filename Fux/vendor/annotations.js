// <![CDATA[

(async ()=>{

				let url = "http://localhost:5984/master_music/_design/harmo/_view/harmo";
				let filterUrl = "http://localhost:5984/svg/filter"
				let data = await fetch(url).then(e => e.json()).then(e => e.rows);
				let html = await fetch(filterUrl).then(e=>e.json()).then(e=>e.value);
				let ns = "http://www.w3.org/2000/svg";
				svg = document.getElementsByTagName('svg')[0];
				function create_text(x, y, txt, size, color){	
								let text = document.createElementNS(ns, 'text');
								text.setAttribute("x", x);
								text.setAttribute("y", y);
								text.setAttribute("font-size", size);
								text.setAttribute("fill", color);
								text.setAttribute("filter", "url(#solid)");
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
												create_text(rect.x, rect.y - 10, harmo.value, 11, "blue");
												++j;
								}
				}	
})()

// ]]>
