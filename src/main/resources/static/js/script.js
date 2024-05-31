document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.querySelector('#isbnSearchForm');
    const inputField = document.querySelector('#inputIsbn');
    const oldValue = inputField.value;

    form.addEventListener('submit', (e)=>{
        if (inputField.value === '') {
            alert("Please enter ISBN");
            e.preventDefault();
        }
    })

    inputField.addEventListener('input', () => {
        const newValue = inputField.value;
        if (newValue === '' && newValue !== oldValue) {
            window.location.href = "/";
        }
    });
});
