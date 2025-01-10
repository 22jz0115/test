$(function(){
	const ham = document.querySelector('.openbtn1');
    const nav = document.querySelector('#g-nav');

    ham.addEventListener('click', function(){
        ham.classList.toggle('active');
        nav.classList.toggle('panelactive');
    });

    nav.addEventListener('click', function(){
        ham.classList.remove('active');
        nav.classList.remove('panelactive');
    });
});