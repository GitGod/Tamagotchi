var btn = document.getElementByName("waterButton");
btn.addEventListener('click', function(event){
    if(this.classList.contains('active')){
        this.classList.remove('active');
    }
    else{
        this.classList.add('active');
    }
});