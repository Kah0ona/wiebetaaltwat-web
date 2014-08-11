function restoreFromLocalStorage(){
    var c = localStorage.getItem("wiebetaaltwat");
    document.getElementById('textarea').value = c;
}

function storeInLocalStorage(){
	try {
		if (localStorage) {
			var txtValue = document.getElementById('textarea').value;
			localStorage.setItem("wiebetaaltwat", txtValue);
		}
	}
	catch (err) {
		document.getElementById('msg').innerHTML = err.Description;
	}
}

jQuery(document).ready(function($){
	restoreFromLocalStorage();
});
