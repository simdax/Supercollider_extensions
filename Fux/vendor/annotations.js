// <![CDATA[

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
for (var i = 0; i < paths.length; i++) {
		let c = paths[i].getAttribute('class');
		if (c === 'Note')
		{
		  	let rect = paths[i].getBoundingClientRect();
		  	create_text(rect.x, rect.y - 15, i, 10, "red");
		}
}	

// ]]>
