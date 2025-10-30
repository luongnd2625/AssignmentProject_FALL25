
// Simple interactivity: theme toggle and toast messages
(function(){
	const body = document.documentElement;
	const btn = document.getElementById('toggleTheme');
	const hello = document.getElementById('sayHello');
	const toast = document.getElementById('toast');

	function showToast(message, ms=2200){
		toast.textContent = message;
		toast.classList.add('show');
		clearTimeout(toast._timer);
		toast._timer = setTimeout(()=> toast.classList.remove('show'), ms);
	}

	// Read saved preference
	const saved = localStorage.getItem('theme');
	if(saved === 'dark') document.documentElement.classList.add('dark');

	btn.addEventListener('click', ()=>{
		const isDark = document.documentElement.classList.toggle('dark');
		localStorage.setItem('theme', isDark ? 'dark' : 'light');
		showToast(isDark ? 'Switched to dark mode' : 'Switched to light mode');
	});

	hello.addEventListener('click', ()=> showToast('Hello — nice to meet you!'));

	// Accessible keyboard shortcut: t toggles theme when focused on body
	document.addEventListener('keydown', (e)=>{
		if(e.key.toLowerCase() === 't' && !e.metaKey && !e.ctrlKey){
			btn.click();
		}
	});

})();
